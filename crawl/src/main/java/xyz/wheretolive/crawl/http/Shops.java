package xyz.wheretolive.crawl.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonymottot on 30/10/2015.
 */
public class Shops {
    List<TescoObject> tescoObjectList = new ArrayList<>();

    public List<TescoObject> getTescoObjectList() {
        return tescoObjectList;
    }

    public void setTescoObjectList(List<TescoObject> tescoObjectList) {
        this.tescoObjectList = tescoObjectList;
    }
}
