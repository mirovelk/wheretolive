package xyz.wheretolive.crawl.reality.bezrealitky;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;

public class BezRealitkyFlatRentCrawler extends BezRealitkyCrawler {

    private static final String BEZ_REALITKY_FLATS_RENT = "http://www.bezrealitky.cz/api/search/map?filter=%7B%22order%22:%22time_order_desc%22,%22advertoffertype%22:%22nabidka-pronajem%22,%22estatetype%22:%22byt%22,%22disposition%22:%220%22,%22ownership%22:%22%22,%22equipped%22:%22%22,%22priceFrom%22:null,%22priceTo%22:null,%22construction%22:%22%22,%22description%22:%22%22,%22surfaceFrom%22:%22%22,%22surfaceTo%22:%22%22,%22balcony%22:%22%22,%22terrace%22:%22%22%7D&squares=%5B%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:8,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:12%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:8,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:12%7D%22,%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:12,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:16%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:12,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:16%7D%22,%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:16,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:20%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:16,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:20%7D%22%5D";

    @Override
    public Collection<MapObject> crawl() {
        List<Reality> result = crawl(BEZ_REALITKY_FLATS_RENT, Housing.Type.HOUSE, Housing.Transaction.RENT);
        return new HashSet<>(result);
    }

    @Override
    @Scheduled(cron = BEZ_REALITKY_CRON)
    public void execute() {
        super.execute();
    }
}
