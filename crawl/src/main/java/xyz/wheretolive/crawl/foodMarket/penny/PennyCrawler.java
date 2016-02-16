package xyz.wheretolive.crawl.foodMarket.penny;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.foodMarket.FoodMarketCrawler;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;

@Component
public class PennyCrawler extends FoodMarketCrawler {

    private static final String PENNY = "Penny";

    public static final String PENNY_SHOPS_URL = "http://service.pycz.rewe.co.at/filialservice/filialjson.asmx/getfilialenjson?shopcd=%22P3%22";

    @Autowired
    GoogleGeocoder geocoder;

    @Override
    public Collection<MapObject> crawl() {
        Gson gson = new Gson();
        String json = HttpUtils.get(PENNY_SHOPS_URL);
        json = json.substring(1, json.length() - 2);//remove initial '(' and trailing ');' characters
        PennyWrapper pennyWrapper = gson.fromJson(json, PennyWrapper.class);

        List<MapObject> toReturn = new LinkedList<>();
        for (Map.Entry<String, PennyWrapper.PennyResult> entry : pennyWrapper.getD().entrySet()) {
            PennyWrapper.PennyResult pennyResult = entry.getValue();
            FoodMarket foodMarket = new FoodMarket(new Coordinates(pennyResult.getLatitude(), pennyResult.getLongitude()), getName());
            toReturn.add(foodMarket);
        }
        return toReturn;
    }

    @Override
    @Scheduled(cron = "0 30 10 2 * *")
    public void execute() {
        super.execute();
    }

    @Override
    public String getName() {
        return PENNY;
    }
}
