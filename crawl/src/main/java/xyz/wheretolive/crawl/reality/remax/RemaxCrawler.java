package xyz.wheretolive.crawl.reality.remax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

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

public abstract class RemaxCrawler extends RealityCrawler {

    private static final String NAME = "REMAX";

    private static final String REMAX_FLATS_INITIAL_URL = "http://www.remax-czech.cz/reality/byty/pronajem/";
    
    private static final String REMAX_URL = "http://www.remax-czech.cz";

    public static final String PAGE_PARAMETER = "?stranka=";

    @Autowired
    private GoogleGeocoder googleGeocoder;

    @Override
    public Collection<MapObject> crawl() {
        Set<String> urls = getItemUrls(REMAX_FLATS_INITIAL_URL);
        List<Reality> result = getRealities(urls);
        return new HashSet<>(result);
    }

    protected List<Reality> getRealities(Set<String> urls) {
        List<Reality> result = new ArrayList<>();
        for (String url: urls) {
            try {
                String pageUrl = REMAX_URL + url;
                String pageSourceCode = HttpUtils.get(pageUrl);
                double price = parsePrice(pageUrl, pageSourceCode);
                String realityId = parseRealityId(pageUrl);
                double area = parseArea(pageUrl, pageSourceCode);
                Coordinates location = parseLocation(pageUrl, pageSourceCode);
                Reality reality = new Reality(realityId, price, area, getName(), location);
                reality.setFloor(parseFloor(pageUrl, pageSourceCode));
                reality.setElevator(parseElevator(pageSourceCode));
                reality.setBalcony(parseTerace(pageSourceCode));
                result.add(reality);
            } catch (Exception e) {
                logger.error("Error while parsing flat of " + getName(), e);
            }
        }
        return result;
    }

    private Boolean parseTerace(String pageSourceCode) {
        Pattern pattern = Pattern.compile("Terasa\\s*</td>\\s*<td>\\s*(\\w+)\\s*</td>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            String group = matcher.group(1).trim();
            return group.equals("Ano") ? true : group.equals("Ne") ? false : null;
        } else {
            return null;
        }
    }
    
    private Boolean parseElevator(String pageSourceCode) {
        Pattern pattern = Pattern.compile("Výtah\\s*</td>\\s*<td>\\s*(\\w+)\\s*</td>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            String group = matcher.group(1).trim();
            return group.equals("Ano") ? true : group.equals("Ne") ? false : null;
        } else {
            return null;
        }
    }

    protected abstract int parseFloor(String pageUrl, String pageSourceCode);
    
    protected int parseFlatFloor(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("Číslo podlaží v domě\\s*</td>\\s*<td>\\s*(-?\\d+)\\s*</td>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1).trim());
        } else {
            throw new IllegalStateException("Floor not found in page with url " + pageUrl);
        }
    }

    protected int parseHouseFloor(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("Počet nadzemních podlaží objektu\\s*</td>\\s*<td>\\s*(-?\\d+)\\s*</td>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1).trim());
        } else {
            throw new IllegalStateException("Floor not found in page with url " + pageUrl);
        }
    }

    private Coordinates parseLocation(String pageUrl, String pageSourceCode) {
        String address = "";
        Pattern pattern = Pattern.compile("Město</td>\\s*<td>\\s*<strong>([^<]+)</strong>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            address += matcher.group(1);
        }
        pattern = Pattern.compile("Městská část</td>\\s*<td>\\s*<strong>([^<]+)</strong>");
        matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            address += " " + matcher.group(1);
        }
        pattern = Pattern.compile("Ulice</td>\\s*<td>\\s*<strong>([^<]+)</strong>");
        matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            address += " " +matcher.group(1);
        }
        if (address.isEmpty()) {
            throw new IllegalStateException("Location not found in page with url " + pageUrl);
        } else {
            return googleGeocoder.translate(address);
        }
    }

    protected abstract double parseArea(String pageUrl, String pageSourceCode);

    protected double parseFlatArea(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("Celková plocha\\s*</td>\\s*<td>\\s*(\\d+) m²");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        } else {
            throw new IllegalStateException("Area not found in page with url " + pageUrl);
        }
    }

    protected double parseHouseArea(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("Užitná plocha\\s*</td>\\s*<td>\\s*([\\d\\s]+) m²");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1).replaceAll("\\s", ""));
        } else {
            throw new IllegalStateException("Area not found in page with url " + pageUrl);
        }
    }

    private String parseRealityId(String pageUrl) {
        Pattern pattern = Pattern.compile("/detail/(\\d+)/");
        Matcher matcher = pattern.matcher(pageUrl);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalStateException("Reality id not found in page with url " + pageUrl);
        }
    }

    private double parsePrice(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("<strong>\\D*(\\d[\\D\\d]+)CZK");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1).replaceAll("\\D", ""));
        } else {
            throw new IllegalStateException("Price not found in page with url " + pageUrl);
        }
    }

    protected Set<String> getItemUrls(String url) {
        int pagesCount = getFlatsPageCount(REMAX_FLATS_INITIAL_URL);
        Set<String> urls = new HashSet<>();
        for (int page = 1; page <= pagesCount; page++) {
            String pageUrl = url + PAGE_PARAMETER + page;
            String pageSourceCode = HttpUtils.get(pageUrl);
            Pattern pattern = Pattern.compile("image-link\" href=\"([^\"]*)");
            Matcher matcher = pattern.matcher(pageSourceCode);
            while (matcher.find()) {
                urls.add(matcher.group(1));
            }
        }
        return urls;
    }
    
    private int getFlatsPageCount(String url) {
        String initialPage = HttpUtils.get(url);
        Pattern pattern = Pattern.compile("no-border\">\\s*<a href=\"\\?stranka=(\\d+)\">(\\d+)</a>");
        Matcher matcher = pattern.matcher(initialPage);
        if (matcher.find()) {
            assert matcher.group(1).equals(matcher.group(2));
            return Integer.parseInt(matcher.group(1).trim());
        } else {
            throw new IllegalStateException("Number of pages not found in page with url " + REMAX_FLATS_INITIAL_URL);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    @Scheduled(cron = REMAX_CRON)
    public void execute() {
        super.execute();
    }


    public static void main(String[] args) {
        GoogleGeocoder googleGeocoder = new GoogleGeocoder();
        DatastoreProvider datastoreProvider = new MongoDatastoreProvider();
        GoogleGeocodeRepository repository = new MongoGoogleGeocodeRepository(datastoreProvider);
        googleGeocoder.setRepository(repository);
        
        RemaxCrawler crawler;
        Collection<MapObject> mapObjects;
        
//        crawler = new RemaxFlatRentCrawler();
//        crawler.googleGeocoder = googleGeocoder;
//        mapObjects = crawler.crawl();
//        assert mapObjects.size() > 0;
//
//        crawler = new RemaxFlatSellCrawler();
//        crawler.googleGeocoder = googleGeocoder;
//        mapObjects = crawler.crawl();
//        assert mapObjects.size() > 0;

        crawler = new RemaxHouseRentCrawler();
        crawler.googleGeocoder = googleGeocoder;
        mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;

        crawler = new RemaxHouseSellCrawler();
        crawler.googleGeocoder = googleGeocoder;
        mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;
    }
}
