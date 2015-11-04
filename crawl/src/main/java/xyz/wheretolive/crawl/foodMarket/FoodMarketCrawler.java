package xyz.wheretolive.crawl.foodMarket;

import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.NameableMapObject;
import xyz.wheretolive.crawl.PersistingCrawler;

public abstract class FoodMarketCrawler extends PersistingCrawler {

    @Override
    protected Class<? extends NameableMapObject> getType() {
        return FoodMarket.class;
    }

}
