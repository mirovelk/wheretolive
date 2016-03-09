package xyz.wheretolive.crawl.reality.realitymat;

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
public class RealityMatCrawler extends RealityCrawler {

    private static final String REALITY_MAT_URL = "http://www.realitymat.cz";
    private static final String REALITY_MAT_FLATS_URL = REALITY_MAT_URL + "/search/?vp-page={PAGE}&filter%5BestateFunction%5D=2&filter%5BestateType%5D=1";
    private static final String REALITY_MAT_FLATS_INITIAL_URL = REALITY_MAT_URL + "/search/?filter%5BestateFunction%5D=2&filter%5BestateType%5D=1";
    private static final String PAGES_COUNT_PREFIX = "next-last\" href=\"/search/?vp-page=";

    public static final String REALITY_MAT = "RealityMat";

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
                String pageUrl = REALITY_MAT_URL + url;
                String pageSourceCode = HttpUtils.get(pageUrl);
                double price = parsePrice(pageUrl, pageSourceCode);
                String realityId = parseRealityId(pageUrl, pageSourceCode);
                double area = parseArea(pageUrl, pageSourceCode);
                Coordinates location = parseLocation(pageUrl, pageSourceCode);
                Reality reality = new Reality(realityId, price, area, getName(), location);
                reality.setFloor(parseFloor(pageUrl, pageSourceCode));
                reality.setElevator(parseElevator(pageSourceCode));
                reality.setBalcony(parseBalcony(pageSourceCode));
                result.add(reality);
            } catch (Exception e) {
                logger.error("Error while parsing flat of " + getName(), e);
            }
        }
        return result;
    }

    private Coordinates parseLocation(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("LatLng\\(([\\d\\.]+), ([\\d\\.]+)");
        Matcher matcher = pattern.matcher(pageSourceCode);
        Pattern addressPattern = Pattern.compile("'address': '([^']+)'");
        Matcher addressMatcher = addressPattern.matcher(pageSourceCode);
        double latitude;
        double longitude;
        if (matcher.find()) {
            latitude = Double.parseDouble(matcher.group(1));
            longitude = Double.parseDouble(matcher.group(1));
            return new Coordinates(latitude, longitude);
        } else if (addressMatcher.find()) {
            return googleGeocoder.translate(addressMatcher.group(1));
        } else {
            throw new IllegalStateException("Location not found in page with url " + pageUrl);
        }
    }

    private double parseArea(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("(\\d+) m<sup>2");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        } else {
            throw new IllegalStateException("Area not found in page with url " + pageUrl);
        }
    }

    private String parseRealityId(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("/estate/(\\d+)-");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalStateException("Reality id not found in page with url " + pageUrl);
        }
    }

    private double parsePrice(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("(\\d[\\d\\s]+) Kč");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return (Double.parseDouble(matcher.group(1).replaceAll("\\s", "")));
        } else {
            throw new IllegalStateException("Price not found in page with url " + pageUrl);
        }
    }

    private Set<String> getFlatUrls(int pagesCount) {
        Set<String> urls = new HashSet<>();
        for (int page = 1; page <= pagesCount; page++) {
            String pageUrl = REALITY_MAT_FLATS_URL.replace("{PAGE}", Integer.toString(page));
            String pageSourceCode = HttpUtils.get(pageUrl);
            Pattern pattern = Pattern.compile("/estate/[^\"]*");
            Matcher matcher = pattern.matcher(pageSourceCode);
            while (matcher.find()) {
                urls.add(matcher.group());
            }
        }
        return urls;
    }

    private int parseFloor(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("Podlaží\\s*</th>\\s*<td>\\s*(\\d+)\\.");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1).trim());
        } else {
            throw new IllegalStateException("Floor not found in page with url " + pageUrl);
        }
    }

    private Boolean parseElevator(String pageSourceCode) {
        Pattern pattern = Pattern.compile("Výtah\\s*</th>\\s*<td>\\s*(\\w+)\\s*</td>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            String group = matcher.group(1).trim();
            return group.equals("Ano") ? true : group.equals("Ne") ? false : null;
        } else {
            return null;
        }
    }

    private Boolean parseBalcony(String pageSourceCode) {
        Pattern pattern = Pattern.compile("Balkón\\s*</th>\\s*<td>\\s*(\\w+)\\s*</td>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            String group = matcher.group(1).trim();
            return group.equals("Ano") ? true : group.equals("Ne") ? false : null;
        } else {
            return null;
        }
    }

    private int getFlatsPageCount() {
        String initialPage = HttpUtils.get(REALITY_MAT_FLATS_INITIAL_URL);
        int pagesCountStart = initialPage.indexOf(PAGES_COUNT_PREFIX) + PAGES_COUNT_PREFIX.length();
        int pagesCountEnd = initialPage.indexOf("&", pagesCountStart);
        String pagesCountString = initialPage.substring(pagesCountStart, pagesCountEnd);
        return Integer.parseInt(pagesCountString);
    }

    @Override
    @Scheduled(cron = REALITY_MAT_CRON)
    public void execute() {
        super.execute();
    }

    @Override
    public String getName() {
        return REALITY_MAT;
    }
    
    public static void main(String[] args) {
        RealityMatCrawler realityMatCrawler = new RealityMatCrawler();
        GoogleGeocoder googleGeocoder = new GoogleGeocoder();
        DatastoreProvider datastoreProvider = new MongoDatastoreProvider();
        GoogleGeocodeRepository repository = new MongoGoogleGeocodeRepository(datastoreProvider);
        googleGeocoder.setRepository(repository);
        realityMatCrawler.googleGeocoder = googleGeocoder;
        
        realityMatCrawler.crawl();
    }
}
