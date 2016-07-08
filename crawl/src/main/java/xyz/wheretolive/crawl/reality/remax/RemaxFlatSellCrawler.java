package xyz.wheretolive.crawl.reality.remax;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;

public class RemaxFlatSellCrawler extends RemaxCrawler {

    private static final String REMAX_FLAT_SELL_URL = "http://www.remax-czech.cz/reality/byty/prodej/";

    @Override
    public Collection<MapObject> crawl() {
        Set<String> urls = getItemUrls(REMAX_FLAT_SELL_URL);
        List<Reality> result = getRealities(urls);
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
