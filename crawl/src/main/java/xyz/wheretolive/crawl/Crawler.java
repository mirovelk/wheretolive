package xyz.wheretolive.crawl;

import xyz.wheretolive.core.domain.MapObject;
import java.util.Collection;

public interface Crawler {

    String BILLA_SHOPS_URL = "https://www.billa.cz/billa.controls/filialfinder/maps/pinpointv3/pinpoint3.32.html?fx=&dataUrl=https://service.cz.rewe.co.at/filialservice/filialjson.asmx/getfilialenjson?shopcd=%22CZ%22&language=CZE&country=CZ";
    Collection<MapObject> crawl();
}
