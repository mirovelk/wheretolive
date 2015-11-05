package xyz.wheretolive.crawl.foodMarket.tesco;

import java.util.List;

public class TescoResult {

    private int status;
    private TescoShopList result;

    public TescoShopList getResult() {
        return result;
    }

    public void setResult(TescoShopList result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class TescoShopList {
        private List<TescoObject> shops;

        public List<TescoObject> getShops() {
            return shops;
        }

        public void setShops(List<TescoObject> shops) {
            this.shops = shops;
        }
    }
}
