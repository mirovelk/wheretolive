package xyz.wheretolive.crawl.reality.bezrealitky;

import java.io.IOException;
import java.util.Collection;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.Crawler;

@Component
public class BezRealitkyCrawler implements Crawler {

    private Logger logger = LogManager.getLogger(BezRealitkyCrawler.class);
    
    private static final String BEZ_REALITKY_QUERY = "http://www.bezrealitky.cz/api/search/map?filter=";
    
    private static final String FLATS_RENT = "http://www.bezrealitky.cz/api/search/map?filter=%7B%22order%22:%22time_order_desc%22,%22advertoffertype%22:%22nabidka-pronajem%22,%22estatetype%22:%22byt%22,%22disposition%22:%220%22,%22ownership%22:%22%22,%22equipped%22:%22%22,%22priceFrom%22:null,%22priceTo%22:null,%22construction%22:%22%22,%22description%22:%22%22,%22surfaceFrom%22:%22%22,%22surfaceTo%22:%22%22,%22balcony%22:%22%22,%22terrace%22:%22%22%7D&squares=%5B%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:8,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:12%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:8,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:12%7D%22,%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:12,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:16%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:12,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:16%7D%22,%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:16,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:20%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:16,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:20%7D%22%5D";

    private static final String HOUSES_RENT = "http://www.bezrealitky.cz/api/search/map?filter=%7B%22order%22:%22time_order_desc%22,%22advertoffertype%22:%22nabidka-pronajem%22,%22estatetype%22:%22dum%22,%22disposition%22:%220%22,%22ownership%22:%22%22,%22equipped%22:%22%22,%22priceFrom%22:null,%22priceTo%22:null,%22construction%22:%22%22,%22description%22:%22%22,%22surfaceFrom%22:%22%22,%22surfaceTo%22:%22%22,%22balcony%22:%22%22,%22terrace%22:%22%22%7D&squares=%5B%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:8,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:12%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:8,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:12%7D%22,%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:12,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:16%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:12,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:16%7D%22,%22%7B%5C%22swlat%5C%22:48,%5C%22swlng%5C%22:16,%5C%22nelat%5C%22:50,%5C%22nelng%5C%22:20%7D%22,%22%7B%5C%22swlat%5C%22:50,%5C%22swlng%5C%22:16,%5C%22nelat%5C%22:52,%5C%22nelng%5C%22:20%7D%22%5D";
    
    @Override
    public Collection<MapObject> crawl() {
        String json = getJson();
        Gson gson = new Gson();
        BezRealitkyResult bezRealitkyResult = gson.fromJson(json, BezRealitkyResult.class);
        return null;
    }

    private String getJson() {
        String toReturn = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpget = new HttpGet(BEZ_REALITKY_QUERY + FLATS_RENT);
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                toReturn = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            logger.error("error", e);
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
    
}
