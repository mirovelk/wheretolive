package xyz.wheretolive.crawl.foodMarket.billa;

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
public class BillaCrawler extends FoodMarketCrawler {

    private static final String BILLA = "Billa";

    public static final String BILLA_SHOPS_URL = "https://service.cz.rewe.co.at/filialservice/filialjson.asmx/getfilialenjson?shopcd=%22CZ%22";

    @Autowired
    GoogleGeocoder geocoder;

    @Override
    public Collection<MapObject> crawl() {
        Gson gson = new Gson();
        String json = HttpUtils.get(BILLA_SHOPS_URL);
        json = json.substring(1, json.length() - 2);//remove initial '(' and trailing ');' characters
        BillaWrapper billaWrapper = gson.fromJson(json, BillaWrapper.class);

        List<MapObject> toReturn = new LinkedList<>();
        for (Map.Entry<String, BillaWrapper.BillaResult> entry : billaWrapper.getD().entrySet()) {
            BillaWrapper.BillaResult billaResult = entry.getValue();
            FoodMarket foodMarket = new FoodMarket(
                    new Coordinates(billaResult.getLatitude(), billaResult.getLongitude()), BILLA, null);
            toReturn.add(foodMarket);
        }
        return toReturn;
    }

    @Override
    @Scheduled(cron = "30 10 1 * * *")
    public void execute() {
        super.execute();
    }

    @Override
    protected String getName() {
        return BILLA;
    }
}
