package xyz.wheretolive.crawl.reality.mm;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;

import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;

public class MMFlatSellCrawler extends MMCrawler {

    private static final String MM_FLATS_SELL_URL = "https://www.mmreality.cz/nemovitosti/prodej/byty/";

    @Override
    public Collection<MapObject> crawl() {
        int pagesCount = getPageCount(MM_FLATS_SELL_URL);
        Set<String> urls = getItemUrls(MM_FLATS_SELL_URL, pagesCount);
        List<Reality> result = getRealities(urls, Housing.Type.FLAT, Housing.Transaction.SELL);
        return new HashSet<>(result);
    }

    protected Integer parseFloor(String pageUrl, String pageSourceCode) {
        return parseFlatFloor(pageUrl, pageSourceCode);
    }

    @Override
    @Scheduled(cron = MM_CRON)
    public void execute() {
        super.execute();
    }
}
