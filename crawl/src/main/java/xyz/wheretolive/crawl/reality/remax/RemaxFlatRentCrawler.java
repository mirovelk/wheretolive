package xyz.wheretolive.crawl.reality.remax;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;

import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;

public class RemaxFlatRentCrawler extends RemaxCrawler {

    private static final String REMAX_FLAT_RENT_URL = "http://www.remax-czech.cz/reality/byty/pronajem/";

    @Override
    public Collection<MapObject> crawl() {
        Set<String> urls = getItemUrls(REMAX_FLAT_RENT_URL);
        List<Reality> result = getRealities(urls, Housing.Type.FLAT, Housing.Transaction.RENT);
        return new HashSet<>(result);
    }

    @Override
    protected double parseArea(String pageUrl, String pageSourceCode) {
        return parseFlatArea(pageUrl, pageSourceCode);
    }

    @Override
    protected int parseFloor(String pageUrl, String pageSourceCode) {
        return parseFlatFloor(pageUrl, pageSourceCode);
    }

    @Override
    @Scheduled(cron = REMAX_CRON)
    public void execute() {
        super.execute();
    }

}
