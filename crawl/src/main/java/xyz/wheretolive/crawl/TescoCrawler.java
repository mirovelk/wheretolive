package xyz.wheretolive.crawl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.http.HttpTesco;

@Component
public class TescoCrawler implements Crawler {

    private Logger logger = LogManager.getLogger(TescoCrawler.class);
    
    private final String TESCO = "Tesco";

    @Autowired
    GoogleGeocoder geocoder;

    @Override
    public Collection<MapObject> crawl() {

        HttpTesco httpTesco = new HttpTesco();
        Set<FoodMarket> shopsList = httpTesco.getShopsList(httpTesco.getTescoJson());

        return new HashSet<>(shopsList);
    }
}
