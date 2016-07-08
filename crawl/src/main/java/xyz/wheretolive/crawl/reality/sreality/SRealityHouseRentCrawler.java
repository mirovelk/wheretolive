package xyz.wheretolive.crawl.reality.sreality;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;

public class SRealityHouseRentCrawler extends SRealityCrawler {

    private static final String SREALITY_HOUSE_RENT_URL = "http://www.sreality.cz/api/cs/v1/estates?category_main_cb=2&category_type_cb=2&page=";

    @Override
    public Collection<MapObject> crawl() {
        List<SRealityEstate> estates = getsRealityEstates(SREALITY_HOUSE_RENT_URL);
        List<Reality> realities = getRealities(estates, Housing.Type.HOUSE, Housing.Transaction.RENT);
        return new HashSet<>(realities);
    }

    @Override
    @Scheduled(cron = REAL1_CRON)
    public void execute() {
        super.execute();
    }
}
