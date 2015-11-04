package xyz.wheretolive.crawl.foodMarket.tesco;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.foodMarket.FoodMarketCrawler;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;

@Component
public class TescoCrawler extends FoodMarketCrawler {

    static final String TESCO = "Tesco";

    @Autowired
    GoogleGeocoder geocoder;

    @Override
    public Collection<MapObject> crawl() {
        HttpTesco httpTesco = new HttpTesco();
        Set<FoodMarket> shopsList = httpTesco.getShopsList(httpTesco.getTescoJson());

        return new HashSet<>(shopsList);
    }

    @Override
    @Scheduled(cron = "30 10 1 * * *")
    public void execute() {
        super.execute();
    }

    @Override
    protected String getName() {
        return TESCO;
    }
}
