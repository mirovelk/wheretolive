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
import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;
import xyz.wheretolive.core.domain.RealityName;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.reality.RealityCrawler;
import xyz.wheretolive.mongo.DatastoreProvider;
import xyz.wheretolive.mongo.GoogleGeocodeRepository;
import xyz.wheretolive.mongo.MongoDatastoreProvider;
import xyz.wheretolive.mongo.MongoGoogleGeocodeRepository;

import static xyz.wheretolive.core.domain.RealityName.*;

@Component
public abstract class MMCrawler extends RealityCrawler {

    private static final String MM_REALITIES_URL = "https://www.mmreality.cz";

    private static final int REALITIES_PER_PAGE = 12;

    @Autowired
    private GoogleGeocoder googleGeocoder;

    protected List<Reality> getRealities(Set<String> urls, Housing.Type type, Housing.Transaction transaction) {
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
                reality.setFloor(parseFloor(pageUrl, pageSourceCode));
                reality.setElevator(parseElevator(pageSourceCode));
                reality.setType(type);
                reality.setTransaction(transaction);
                result.add(reality);
            } catch (Exception e) {
                logger.error("Error while parsing reality of " + getName() + " on URL: " + MM_REALITIES_URL + url, e);
                errorsCount++;
            }
        }
        return result;
    }

    private Coordinates parseLocation(String pageUrl, String pageSourceCode) {
        Pattern addressPattern = Pattern.compile("Adresa: <strong>([^<]+)</strong>");
        Matcher addressMatcher = addressPattern.matcher(pageSourceCode);
        if (addressMatcher.find()) {
            String address = addressMatcher.group(1);
            address = address.replaceAll("\\(.*\\)", "");
            if (address.startsWith("č.p. ")) {
                int commaIndex = address.indexOf(',');
                address = address.substring(commaIndex + 2) + address.substring(0, commaIndex);
                address = address.replace("č.p. ", "");
                address += " Czech Republic";
            }
            return googleGeocoder.translate(address);
        } else {
            throw new IllegalStateException("Location not found in page with url " + pageUrl);
        }
    }

    private double parseArea(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("Plocha užitná</td>\\s*<td >([\\d\\s]+) m<sup>2");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1).replace(" ", ""));
        } else {
            throw new IllegalStateException("Area not found in page with url " + pageUrl);
        }
    }

    protected Integer parseFlatFloor(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("Číslo podlaží</td>\\s*<td >(\\d+)\\.");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new IllegalStateException("Floor not found in page with url " + pageUrl);
        }
    }

    protected abstract Integer parseFloor(String pageUrl, String pageSourceCode);

    protected Integer parseHouseFloor() {
        return null;
    }

    private Boolean parseElevator(String pageSourceCode) {
        Pattern pattern = Pattern.compile("Výtah</td>\\s*<td >(\\w+)</td>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            String group = matcher.group(1);
            return group.equals("Ano") ? true : group.equals("Ne") ? false : null;
        } else {
            return null;
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

    protected int getPageCount(String url) {
        String initialPage = HttpUtils.get(url);
        Pattern pattern = Pattern.compile("offerCount\">([\\d]+)</");
        Matcher matcher = pattern.matcher(initialPage);
        if (matcher.find()) {
            int realitiesCount = Integer.parseInt(matcher.group(1));
            //rounding up integer division:
            return (realitiesCount + REALITIES_PER_PAGE - 1) / REALITIES_PER_PAGE;
        } else {
            throw new IllegalStateException("Realities count not found in page with url " + url);
        }
    }

    protected Set<String> getItemUrls(String url, int pagesCount) {
        Set<String> urls = new HashSet<>();
        for (int page = 1; page <= pagesCount; page++) {
            String pageUrl = url + "?page=" + Integer.toString(page);
            String pageSourceCode = HttpUtils.get(pageUrl);
            Pattern pattern = Pattern.compile("/nemovitosti/[\\d]+/");
            Matcher matcher = pattern.matcher(pageSourceCode);
            while (matcher.find()) {
                urls.add(matcher.group());
            }
        }
        totalCount = urls.size();
        return urls;
    }

    @Override
    @Scheduled(cron = MM_CRON)
    public void execute() {
        super.execute();
    }
    
    @Override
    public String getName() {
        return M_M.getName();
    }

    public static void main(String[] args) {
        GoogleGeocoder googleGeocoder = new GoogleGeocoder();
        DatastoreProvider datastoreProvider = new MongoDatastoreProvider();
        GoogleGeocodeRepository repository = new MongoGoogleGeocodeRepository(datastoreProvider);
        googleGeocoder.setRepository(repository);

        MMCrawler mmCrawler;
        Collection<MapObject> mapObjects;

//        mmCrawler = new MMFlatRentCrawler();
//        mmCrawler.googleGeocoder = googleGeocoder;
//        mapObjects = mmCrawler.crawl();
//        assert mapObjects.size() > 0;

//        mmCrawler = new MMHouseRentCrawler();
//        mmCrawler.googleGeocoder = googleGeocoder;
//        mapObjects = mmCrawler.crawl();
//        assert mapObjects.size() > 0;

//        mmCrawler = new MMHouseSellCrawler();
//        mmCrawler.googleGeocoder = googleGeocoder;
//        mapObjects = mmCrawler.crawl();
//        assert mapObjects.size() > 0;

        mmCrawler = new MMFlatSellCrawler();
        mmCrawler.googleGeocoder = googleGeocoder;
        mapObjects = mmCrawler.crawl();
        assert mapObjects.size() > 0;
    }
}
