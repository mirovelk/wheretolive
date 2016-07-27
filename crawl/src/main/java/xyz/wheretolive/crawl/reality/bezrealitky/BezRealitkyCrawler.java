package xyz.wheretolive.crawl.reality.bezrealitky;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.reality.RealityCrawler;

@Component
public abstract class BezRealitkyCrawler extends RealityCrawler {

    private static final String BEZ_REALITKY = "BezRealitky";
    
    protected List<Reality> crawl(String url, Housing.Type type, Housing.Transaction transaction) {
        Gson gson = new Gson();
        String json = HttpUtils.get(url);
        BezRealitkyResult bezRealitkyResult = gson.fromJson(json, BezRealitkyResult.class);

        List<Reality> result = new ArrayList<>();
        for (BezRealitkySquares square: bezRealitkyResult.getSquares()) {
            if (square.getRecords() == null) {
                continue;
            }
            for (BezRealitkyRecord record : square.getRecords()) {
                try {
                    Reality reality = new Reality(record.getId(), record.getPrice(), record.getSurface(), getName(),
                            new Coordinates(record.getLat(), record.getLng()));
                    loadRealityDetails(reality);
                    result.add(reality);
                } catch (Exception e) {
                    logger.error("Error while parsing item of " + getName(), e);
                }
            }
        }
        return result;
    }

    private void loadRealityDetails(Reality reality) {
        String pageUrl = "http://www.bezrealitky.cz/nemovitosti-byty-domy/" + reality.getRealityId();
        String pageSourceCode = HttpUtils.get(pageUrl);
        reality.setFloor(parseFloor(pageUrl, pageSourceCode));
        reality.setBalcony(parseBalcony(pageUrl, pageSourceCode));
        reality.setTerrace(parseTerrace(pageUrl, pageSourceCode));
    }

    private int parseFloor(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("podlaží:</div>\\s*<div class=\"value\">(\\-?\\d+)</div>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new IllegalStateException("Floor not found in page with url " + pageUrl);
        }
    }

    private Boolean parseBalcony(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("balkón:</div>\\s*<div class=\"value\">(\\w+)</div>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            String group = matcher.group(1);
            return group.equals("Ano") ? true : group.equals("Ne") ? false : null;
        } else {
            throw new IllegalStateException("Balcony not found in page with url " + pageUrl);
        }
    }

    private Boolean parseTerrace(String pageUrl, String pageSourceCode) {
        Pattern pattern = Pattern.compile("terasa:</div>\\s*<div class=\"value\">(\\w+)</div>");
        Matcher matcher = pattern.matcher(pageSourceCode);
        if (matcher.find()) {
            String group = matcher.group(1);
            return group.equals("Ano") ? true : group.equals("Ne") ? false : null;
        } else {
            throw new IllegalStateException("Terrace not found in page with url " + pageUrl);
        }
    }

    @Override
    @Scheduled(cron = BEZ_REALITKY_CRON)
    public void execute() {
        super.execute();
    }

    @Override
    public String getName() {
        return BEZ_REALITKY;
    }

    public static void main(String[] args) {
        BezRealitkyCrawler crawler = new BezRealitkyFlatRentCrawler();
        Collection<MapObject> mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;

        crawler = new BezRealitkyFlatSellCrawler();
        mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;

        crawler = new BezRealitkyHouseRentCrawler();
        mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;

        crawler = new BezRealitkyHouseSellCrawler();
        mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;
    }
    
}
