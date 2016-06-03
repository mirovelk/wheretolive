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
import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.reality.RealityCrawler;
import xyz.wheretolive.mongo.DatastoreProvider;
import xyz.wheretolive.mongo.GoogleGeocodeRepository;
import xyz.wheretolive.mongo.MongoDatastoreProvider;
import xyz.wheretolive.mongo.MongoGoogleGeocodeRepository;

public abstract class Real1Crawler extends RealityCrawler {
    
    public static final String REAL1 = "Real1";
    
    @Autowired
    private GoogleGeocoder googleGeocoder;

    protected List<Reality> getRealities(Set<String> urls, Housing.Type type, Housing.Transaction transaction) {
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
                reality.setType(type);
                reality.setTransaction(transaction);
                result.add(reality);
            } catch (Exception e) {
                logger.error("Error while parsing flat of " + getName(), e);
            }
        }
        return result;
    }

    private Coordinates parseLocation(String pageUrl, String pageSourceCode) {
        Pattern addressPattern = Pattern.compile("sq ft, ([^\"<]+)\\s*<");
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

    protected abstract Integer parseFloor(String pageUrl, String pageSourceCode);

    protected Integer parseFlatFloor(String pageUrl, String pageSourceCode) {
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

    protected Integer parseHouseFloor() {
        return null;
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
    
    protected abstract double parsePrice(String pageUrl, String pageSourceCode);

    protected double parseRentPrice(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("ctl0_Main_panProperties_lblrentalpricemonth\">([\\d\\s]+)<");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return (Double.parseDouble(matcher.group(1).replaceAll("\\s", "")));
        } else {
            throw new IllegalStateException("Price not found in page with url " + pageUrl);
        }
    }

    protected double parseSellPrice(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("ctl0_Main_panProperties_lblSalePrice\">([\\d\\s]+)<");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return (Double.parseDouble(matcher.group(1).replaceAll("\\s", "")));
        } else {
            throw new IllegalStateException("Price not found in page with url " + pageUrl);
        }
    }

    protected Set<String> getFlatUrls(String url) {
        Set<String> urls = new HashSet<>();
        int page = 0;
        String pageSourceCode;
        do {
            String pageUrl = url + Integer.toString(page);
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
    public String getName() {
        return REAL1;
    }

    public static void main(String[] args) {
        GoogleGeocoder googleGeocoder = new GoogleGeocoder();
        DatastoreProvider datastoreProvider = new MongoDatastoreProvider();
        GoogleGeocodeRepository repository = new MongoGoogleGeocodeRepository(datastoreProvider);
        googleGeocoder.setRepository(repository);

        Real1Crawler mmCrawler;
        Collection<MapObject> mapObjects;

        mmCrawler = new Real1FlatRentCrawler();
        mmCrawler.googleGeocoder = googleGeocoder;
        mapObjects = mmCrawler.crawl();
        assert mapObjects.size() > 0;

        mmCrawler = new Real1FlatSellCrawler();
        mmCrawler.googleGeocoder = googleGeocoder;
        mapObjects = mmCrawler.crawl();
        assert mapObjects.size() > 0;

        mmCrawler = new Real1HouseRentCrawler();
        mmCrawler.googleGeocoder = googleGeocoder;
        mapObjects = mmCrawler.crawl();
        assert mapObjects.size() > 0;

        mmCrawler = new Real1HouseSellCrawler();
        mmCrawler.googleGeocoder = googleGeocoder;
        mapObjects = mmCrawler.crawl();
        assert mapObjects.size() > 0;
    }
}
