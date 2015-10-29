package xyz.wheretolive.crawl.pageObject;

import xyz.wheretolive.core.domain.FoodMarket;

import java.util.Set;

/**
 * Created by anthonymottot on 29/10/2015.
 */
public interface ITescoMap {
    String SHOPS_CONTAINER = "//div[@id='store-list']";
    String SHOPS_ITEMS = ".//div[@class='store-item']";
    String SHOP_NAME = "./h3/a";
    String SHOP_ADDRESS = "./span[@class='address']";

    Set<FoodMarket> getShopsList();
}
