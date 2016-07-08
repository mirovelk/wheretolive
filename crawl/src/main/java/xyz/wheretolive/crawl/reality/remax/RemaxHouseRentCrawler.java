package xyz.wheretolive.crawl.reality.remax;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;

import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;

public class RemaxHouseRentCrawler extends RemaxCrawler {

    private static final String REMAX_HOUSE_RENT_URL = "http://www.remax-czech.cz/reality/domy-a-vily/pronajem/";

    @Override
    public Collection<MapObject> crawl() {
        Set<String> urls = getItemUrls(REMAX_HOUSE_RENT_URL);
        List<Reality> result = getRealities(urls, Housing.Type.HOUSE, Housing.Transaction.RENT);
        return new HashSet<>(result);
    }

    @Override
    protected double parseArea(String pageUrl, String pageSourceCode) {
        return parseHouseArea(pageUrl, pageSourceCode);
    }

    @Override
    protected int parseFloor(String pageUrl, String pageSourceCode) {
        return parseHouseFloor(pageUrl, pageSourceCode);
    }

    @Override
    @Scheduled(cron = REMAX_CRON)
    public void execute() {
        super.execute();
    }

}
