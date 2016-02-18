package xyz.wheretolive.crawl.reality.mm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.reality.RealityCrawler;
import xyz.wheretolive.mongo.DatastoreProvider;
import xyz.wheretolive.mongo.GoogleGeocodeRepository;
import xyz.wheretolive.mongo.MongoDatastoreProvider;
import xyz.wheretolive.mongo.MongoGoogleGeocodeRepository;

@Component
public class MMCrawler extends RealityCrawler {

    private static final String MM_REALITIES_URL = "https://www.mmreality.cz";
    private static final String MM_FLATS_URL = "https://www.mmreality.cz/nemovitosti/pronajem/byty/";
    private static final int REALITIES_PER_PAGE = 12;

    public static final String M_M = "M&M";

    @Autowired
    private GoogleGeocoder googleGeocoder;

    @Override
    public Collection<MapObject> crawl() {
        int pagesCount = getFlatsPageCount();
        Set<String> urls = getFlatUrls(pagesCount);
        List<Reality> result = getRealities(urls);
        return new HashSet<>(result);
    }

    private List<Reality> getRealities(Set<String> urls) {
        List<Reality> result = new ArrayList<>();
        for (String url: urls) {
            try {
                String pageUrl = MM_REALITIES_URL + url;
                String pageSourceCode = HttpUtils.get(pageUrl);
                double price = parsePrice(pageUrl, pageSourceCode);
                String realityId = parseRealityId(url);
                double area = parseArea(pageUrl, pageSourceCode);
                Coordinates location = parseLocation(pageUrl, pageSourceCode);
                Reality reality = new Reality(realityId, price, area, getName(), location);
                result.add(reality);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Coordinates parseLocation(String pageUrl, String pageSourceCode) {
        Pattern addressPattern = Pattern.compile("Adresa: <strong>([^<]+)</strong>");
        Matcher addressMatcher = addressPattern.matcher(pageSourceCode);
        if (addressMatcher.find()) {
            return googleGeocoder.translate(addressMatcher.group(1));
        } else {
            throw new IllegalStateException("Location not found in page with url " + pageUrl);
        }
    }

    private double parseArea(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("Plocha užitná</td>\\s*<td >(\\d+) m<sup>2");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        } else {
            throw new IllegalStateException("Area not found in page with url " + pageUrl);
        }
    }

    private String parseRealityId(String pageUrl) {
        Pattern pattern = Pattern.compile("/nemovitosti/(\\d+)/");
        Matcher matcher = pattern.matcher(pageUrl);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalStateException("Reality id not found in page with url " + pageUrl);
        }
    }

    private double parsePrice(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("([\\d\\s]+) Kč/měs");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return (Double.parseDouble(matcher.group(1).replaceAll("\\s", "")));
        } else {
            throw new IllegalStateException("Price not found in page with url " + pageUrl);
        }
    }

    private int getFlatsPageCount() {
        String initialPage = HttpUtils.get(MM_FLATS_URL);
        Pattern pattern = Pattern.compile("offerCount\">([\\d]+)</");
        Matcher matcher = pattern.matcher(initialPage);
        if (matcher.find()) {
            int realitiesCount = Integer.parseInt(matcher.group(1));
            //rounding up integer division:
            return (realitiesCount + REALITIES_PER_PAGE - 1) / REALITIES_PER_PAGE;
        } else {
            throw new IllegalStateException("Realities count not found in page with url " + MM_FLATS_URL);
        }
    }

    private Set<String> getFlatUrls(int pagesCount) {
        Set<String> urls = new HashSet<>();
        for (int page = 1; page <= pagesCount; page++) {
            String pageUrl = MM_FLATS_URL + "?page=" + Integer.toString(page);
            String pageSourceCode = HttpUtils.get(pageUrl);
            Pattern pattern = Pattern.compile("/nemovitosti/[\\d]+/");
            Matcher matcher = pattern.matcher(pageSourceCode);
            while (matcher.find()) {
                urls.add(matcher.group());
            }
        }
        return urls;
    }

    @Override
    @Scheduled(cron = MM_CRON)
    public void execute() {
        super.execute();
    }
    
    @Override
    public String getName() {
        return M_M;
    }

    public static void main(String[] args) {
        MMCrawler mmCrawler = new MMCrawler();
        GoogleGeocoder googleGeocoder = new GoogleGeocoder();
        DatastoreProvider datastoreProvider = new MongoDatastoreProvider();
        GoogleGeocodeRepository repository = new MongoGoogleGeocodeRepository(datastoreProvider);
        googleGeocoder.setRepository(repository);
        mmCrawler.googleGeocoder = googleGeocoder;

        Collection<MapObject> mapObjects = mmCrawler.crawl();
        assert mapObjects.size() > 0;
    }
}
