package xyz.wheretolive.crawl.reality.real1;

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
public class Real1Crawler extends RealityCrawler {

    private static final String REAL1_FLATS_URL = "http://www.real1.cz/pronajem-bytu/&pgIndex=";
    
    public static final String REAL1 = "Real1";

    @Autowired
    private GoogleGeocoder googleGeocoder;

    @Override
    public Collection<MapObject> crawl() {
        Set<String> urls = getFlatUrls();
        List<Reality> result = getRealities(urls);
        return new HashSet<>(result);
    }

    private List<Reality> getRealities(Set<String> urls) {
        List<Reality> result = new ArrayList<>();
        for (String url: urls) {
            try {
                String pageSourceCode = HttpUtils.get(url);
                double price = parsePrice(url, pageSourceCode);
                String realityId = parseRealityId(url);
                double area = parseArea(url, pageSourceCode);
                Coordinates location = parseLocation(url, pageSourceCode);
                Reality reality = new Reality(realityId, price, area, getName(), location);
                reality.setFloor(parseFloor(url, pageSourceCode));
                reality.setElevator(parseElevator(pageSourceCode));
                result.add(reality);
            } catch (Exception e) {
                logger.error("Error while parsing flat of " + getName(), e);
            }
        }
        return result;
    }

    private Coordinates parseLocation(String pageUrl, String pageSourceCode) {
        Pattern addressPattern = Pattern.compile("sq ft, ([^\"]+)\\s*<");
        Matcher addressMatcher = addressPattern.matcher(pageSourceCode);
        if (addressMatcher.find()) {
            return googleGeocoder.translate(addressMatcher.group(1));
        } else {
            throw new IllegalStateException("Location not found in page with url " + pageUrl);
        }
    }

    private double parseArea(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("Plocha<[\\D]+(\\d+)");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        } else {
            throw new IllegalStateException("Area not found in page with url " + pageUrl);
        }
    }

    private int parseFloor(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("<span>\\s*(\\d+)\\. patro");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else if (pageSourceCode.contains("přízemí")) {
            return 0;
        } else {
            throw new IllegalStateException("Floor not found in page with url " + pageUrl);
        }
    }

    private Boolean parseElevator(String pageSourceCode) {
        Pattern pattern = Pattern.compile("Výtah</span></strong> - <span><img class='ticker' align='middle' src='[^']+'\\salt='(\\w+)'");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            String group = matcher.group(1);
            return group.equals("Ano") ? true : group.equals("Ne") ? false : null;
        } else {
            return null;
        }
    }

    private String parseRealityId(String pageUrl) {
        int lastIndex = pageUrl.lastIndexOf('/');
        return pageUrl.substring(lastIndex + 1);
    }

    private double parsePrice(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("ctl0_Main_panProperties_lblrentalpricemonth\">([\\d\\s]+)<");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return (Double.parseDouble(matcher.group(1).replaceAll("\\s", "")));
        } else {
            throw new IllegalStateException("Price not found in page with url " + pageUrl);
        }
    }

    private Set<String> getFlatUrls() {
        Set<String> urls = new HashSet<>();
        int page = 0;
        String pageSourceCode;
        do {
            String pageUrl = REAL1_FLATS_URL + Integer.toString(page);
            pageSourceCode = HttpUtils.get(pageUrl);
            Pattern pattern = Pattern.compile("http://www.real1.cz/detail/[\\d]+");
            Matcher matcher = pattern.matcher(pageSourceCode);
            while (matcher.find()) {
                urls.add(matcher.group());
            }
            page++;
        } while (pageSourceCode.contains("primaryDetail"));
        return urls;
    }

    @Override
    @Scheduled(cron = REAL1_CRON)
    public void execute() {
        super.execute();
    }
    
    @Override
    public String getName() {
        return REAL1;
    }

    public static void main(String[] args) {
        Real1Crawler mmCrawler = new Real1Crawler();
        GoogleGeocoder googleGeocoder = new GoogleGeocoder();
        DatastoreProvider datastoreProvider = new MongoDatastoreProvider();
        GoogleGeocodeRepository repository = new MongoGoogleGeocodeRepository(datastoreProvider);
        googleGeocoder.setRepository(repository);
        mmCrawler.googleGeocoder = googleGeocoder;

        Collection<MapObject> mapObjects = mmCrawler.crawl();
        assert mapObjects.size() > 0;
    }
}
