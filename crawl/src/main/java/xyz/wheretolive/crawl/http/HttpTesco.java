package xyz.wheretolive.crawl.http;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.FoodMarket;

import java.io.IOException;
import java.util.*;

/**
 * Created by anthonymottot on 30/10/2015.
 */
public class HttpTesco {

    private Logger logger = LogManager.getLogger(HttpTesco.class);
    private final String TESCO_URL = "http://www.itesco.cz/com/app/shopLayer:getShops";

    public String getTescoJson() {
        String toReturn = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpget = new HttpGet(TESCO_URL);
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                toReturn = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return toReturn;
    }

    public Set<FoodMarket> getShopsList(String json) {
        Set<FoodMarket> toReturn = new LinkedHashSet<>();

        Gson gson = new Gson();
        gson = new GsonBuilder()
//                    .registerTypeAdapter(Class.class, new ClassTypeAdapter())
//                        .registerTypeAdapter(List.class, new JsonSerializer<List<?>>() {
//                            @Override
//                            public JsonElement serialize(List<?> list, Type t, JsonSerializationContext jsc) {
//                                if (list.size() == 1) {
//                                    // Don't put single element lists in a json array
//                                    return new Gson().toJsonTree(list.get(0));
//                                } else {
//                                    return new Gson().toJsonTree(list);
//                                }
//                            }
//                        }
                    .create();
//        List<TescoObject> tescoObjectList = gson.fromJson(json, new TypeToken<List<TescoObject>>(){}.getType());
        TescoObjectList tescoObjectList = gson.fromJson(json, TescoObjectList.class);
        for ( LinkedTreeMap<String, Object> currentTesco : (ArrayList<LinkedTreeMap<String, Object>>) tescoObjectList.getResult().get("shops") ) {
            logger.debug("geo localization for "+ currentTesco.get("name") + " : " + currentTesco.get("gps_lat") + ", " + currentTesco.get("gps_lon"));
            Coordinates coordinates = new Coordinates(Double.parseDouble((String)currentTesco.get("gps_lat")), Double.parseDouble((String)currentTesco.get("gps_lon")));
            toReturn.add(new FoodMarket(coordinates, (String)currentTesco.get("name")));
        }

        return toReturn;
    }
}
