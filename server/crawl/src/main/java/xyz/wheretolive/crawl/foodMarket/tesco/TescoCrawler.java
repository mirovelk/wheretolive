package xyz.wheretolive.crawl.foodMarket.tesco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.foodMarket.FoodMarketCrawler;

@Component
public class TescoCrawler extends FoodMarketCrawler {

    private static final String TESCO = "Tesco";
    private static final String TESCO_URL = "http://www.itesco.cz/com/app/shopLayer:getShops";

    @Override
    public Collection<MapObject> crawl() {
        Gson gson = new Gson();
        String json = HttpUtils.get(TESCO_URL);
        TescoResult tescoResult = gson.fromJson(json, TescoResult.class);

        List<FoodMarket> toReturn = new ArrayList<>();
        for (TescoObject tescoObject : tescoResult.getResult().getShops()) {
            FoodMarket foodMarket = new FoodMarket(new Coordinates(tescoObject.getGps_lat(), tescoObject.getGps_lon()), getName());
            toReturn.add(foodMarket);
        }
        
        return new HashSet<>(toReturn);
    }

    @Override
    @Scheduled(cron = TESCO_CRON)
    public void execute() {
        super.execute();
    }

    @Override
    public String getName() {
        return TESCO;
    }
}
