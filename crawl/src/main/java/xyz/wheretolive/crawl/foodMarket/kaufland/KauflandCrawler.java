package xyz.wheretolive.crawl.foodMarket.kaufland;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.foodMarket.FoodMarketCrawler;

@Component
public class KauflandCrawler extends FoodMarketCrawler {

    private static final String KAUFLAND = "Kaufland";
    private static final String FILTER_SETTINGS = "{\"filtersettings\":{\"area\":\"61.261076937892,130.6413038490723," +
            "3.0556773533537407,-36.6145555259277\"},\"location\":{\"latitude\":37.52947542912366,\"longitude\":47" +
            ".01337416157228},\"clienttime\":\"20151105140435\"}";
    private static final String KAUFLAND_URL = "http://www.kaufland.cz/Storefinder/finder";

    @Override
    public Collection<MapObject> crawl() {
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("filtersettings", FILTER_SETTINGS));
        data.add(new BasicNameValuePair("loadStores", "true"));
        data.add(new BasicNameValuePair("locale", "CZ"));
        String json = HttpUtils.post(KAUFLAND_URL, data);

        Gson gson = new Gson();
        KauflandObject[] kauflandObjects = gson.fromJson(json, new KauflandObject[]{ }.getClass());
        
        List<MapObject> toReturn = new LinkedList<>();
        for (KauflandObject kauflandObject : kauflandObjects) {
            toReturn.add(new FoodMarket(kauflandObject.getLocation(), KAUFLAND, null));
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
        return KAUFLAND;
    }
}
