package xyz.wheretolive.crawl.foodMarket.albert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.foodMarket.FoodMarketCrawler;

@Component
public class AlbertCrawler extends FoodMarketCrawler {

    private static final String ALBERT = "Albert";
    private static final String ALBERT_SHOPS_URL = "http://www.albert.cz/nase-prodejny/mapa-prodejen?region=REGION&district=&shop_search_tab=shop-search-advanced";
    private static final String[] REGIONS = new String[] {"a", "c", "b", "k", "h", "l", "t", "m", "e", "p", "s", "u", "j", "z"};

    @Override
    public Collection<MapObject> crawl() {
        Collection<FoodMarket> toReturn = new ArrayList<>();
        for (String region : REGIONS) {
            Gson gson = new Gson();
            String json = HttpUtils.get(ALBERT_SHOPS_URL.replace("REGION", region));
            json = json.substring(json.indexOf("new AHPage.ShopMap(") + 19, json.indexOf(", {"));
            json = json.replace("\u200E", "");
            AlbertObject[] albertObjects = gson.fromJson(json, AlbertObject[].class);
            for (AlbertObject albertObject : albertObjects) {
                FoodMarket foodMarket = new FoodMarket(new Coordinates(Double.parseDouble(albertObject.getLatitude()),
                        Double.parseDouble(albertObject.getLongitude())), getName());
                toReturn.add(foodMarket);
            }
        }

        return new HashSet<>(toReturn);
    }

    @Override
    @Scheduled(cron = ALBERT_CRON)
    public void execute() {
        super.execute();
    }

    @Override
    public String getName() {
        return ALBERT;
    }
}
