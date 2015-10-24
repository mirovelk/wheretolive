package xyz.wheretolive.crawl.billa;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by anthonymottot on 25/10/2015.
 */
public interface IBilla {
    String BILLA_SHOPS_URL = "https://www.billa.cz/billa.controls/filialfinder/maps/pinpointv3/pinpoint3.32.html?fx=&dataUrl=https://service.cz.rewe.co.at/filialservice/filialjson.asmx/getfilialenjson?shopcd=%22CZ%22&language=CZE&country=CZ";

    String BILLA_SHOPS_CONTAINTER = "//div[@id='WW_myList_control_content']";
    String BILLA_SHOPS_REGION_LIST = "//div[@id='WW_myList_control_content']/a";
    String BILLA_SHOPS_REGION_NAME_LIST = "//div[@id='WW_myList_control_content']/a//span";

    Set<String> BILLA_SHOPS_REGION_A_LIST = new LinkedHashSet<>();
}
