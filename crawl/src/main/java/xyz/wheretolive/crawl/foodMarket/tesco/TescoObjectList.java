package xyz.wheretolive.crawl.foodMarket.tesco;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

/**
 * Created by anthonymottot on 30/10/2015.
 */
public class TescoObjectList {

    String status;
    LinkedTreeMap result;
//    Map<String, List<TescoObject>> map = new LinkedHashMap();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Object getResult() {
//        return result;
//    }
//
//    public void setResult(Object result) {
//        this.result = result;
//    }


//    public Map<String, List<TescoObject>> getMap() {
//        return map;
//    }
//
//    public void setMap(Map<String, List<TescoObject>> map) {
//        this.map = map;
//    }


    public LinkedTreeMap getResult() {
        return result;
    }

    public void setResult(LinkedTreeMap result) {
        this.result = result;
    }
}
