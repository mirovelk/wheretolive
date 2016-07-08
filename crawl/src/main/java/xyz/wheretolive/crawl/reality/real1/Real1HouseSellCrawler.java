package xyz.wheretolive.crawl.reality.real1;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;

@Component
public class Real1HouseSellCrawler extends Real1Crawler {

    private static final String REAL1_HOUSES_SELL_URL = "http://www.real1.cz/prodej-rodinne-domy/&pgIndex=";

    @Override
    public Collection<MapObject> crawl() {
        Set<String> urls = getItemUrls(REAL1_HOUSES_SELL_URL);
        List<Reality> realities = getRealities(urls, Housing.Type.HOUSE, Housing.Transaction.SELL);
        return new HashSet<>(realities);
    }

    @Override
    protected double parsePrice(String pageUrl, String pageSourceCode) {
        return parseSellPrice(pageUrl, pageSourceCode);
    }
    
    @Override
    protected Integer parseFloor(String pageUrl, String pageSourceCode) {
        return parseHouseFloor();
    }

    @Override
    @Scheduled(cron = REAL1_CRON)
    public void execute() {
        super.execute();
    }

}
