package xyz.wheretolive.crawl.reality.bezrealitky;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.reality.RealityCrawler;

@Component
public class BezRealitkyCrawler extends RealityCrawler {

    private static final String BEZ_REALITKY_QUERY = "http://www.bezrealitky.cz/api/search/map?filter=";
    
    private static final String FLATS_RENT = "%7B%22order%22:%22time_order_desc%22,%22advertoffertype%22:%22nabidka-pronajem%22,%22estatetype%22:%22byt%22,%22disposition%22:%220%22,%22ownership%22:%22%22,%22equipped%22:%22%22,%22priceFrom%22:null,%22priceTo%22:null,%22construction%22:%22%22,%22description%22:%22%22,%22surfaceFrom%22:%22%22,%22surfaceTo%22:%22%22,%22balcony%22:%22%22,%22terrace%22:%22%22%7D&squares=%5B%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:8,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:12%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:8,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:12%7D%22,%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:12,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:16%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:12,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:16%7D%22,%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:16,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:20%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:16,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:20%7D%22%5D";

    private static final String HOUSES_RENT = "%7B%22order%22:%22time_order_desc%22,%22advertoffertype%22:%22nabidka-pronajem%22,%22estatetype%22:%22dum%22,%22disposition%22:%220%22,%22ownership%22:%22%22,%22equipped%22:%22%22,%22priceFrom%22:null,%22priceTo%22:null,%22construction%22:%22%22,%22description%22:%22%22,%22surfaceFrom%22:%22%22,%22surfaceTo%22:%22%22,%22balcony%22:%22%22,%22terrace%22:%22%22%7D&squares=%5B%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:8,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:12%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:8,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:12%7D%22,%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:12,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:16%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:12,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:16%7D%22,%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:16,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:20%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:16,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:20%7D%22%5D";
    
    private static final String BEZ_REALITKY = "BezRealitky";
    
    @Override
    public Collection<MapObject> crawl() {
        List<Reality> result = new ArrayList<>();
        result.addAll(crawlFlats());
        result.addAll(crawlHouses());
        return new HashSet<>(result);
    }

    private Collection<? extends Reality> crawlFlats() {
        return crawl(BEZ_REALITKY_QUERY + FLATS_RENT);
    }

    private Collection<? extends Reality> crawlHouses() {
        return crawl(BEZ_REALITKY_QUERY + HOUSES_RENT);
    }

    private Collection<? extends Reality> crawl(String url) {
        Gson gson = new Gson();
        String json = HttpUtils.get(url);
        BezRealitkyResult bezRealitkyResult = gson.fromJson(json, BezRealitkyResult.class);

        List<Reality> result = new ArrayList<>();
        for (BezRealitkySquares square: bezRealitkyResult.getSquares()) {
            if (square.getRecords() == null) {
                continue;
            }
            for (BezRealitkyRecord record : square.getRecords()) {
                Reality reality = new Reality(record.getId(), record.getPrice(), record.getSurface(), getName(), new Coordinates(record.getLat(), record.getLng()));
                loadRealityDetails(reality);
                result.add(reality);
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
        Pattern pattern = Pattern.compile("podlaží:</div>\\s*<div class=\"value\">(-?\\d+)</div>");
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
        BezRealitkyCrawler crawler = new BezRealitkyCrawler();
        Collection<MapObject> mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;
    }
    
}
