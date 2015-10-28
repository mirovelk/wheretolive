package xyz.wheretolive.crawl.pageObject;

public interface IAlbertMap {
    String SELECT_REGION = "//select[@id = 'select-region']";
    String SELECT_DISTRICT = "//select[@id = 'select-district']";
    String BUTTON_SEARCH = "//button[contains(., 'Vyhledat')]";
    String UL_LIST_SHOP = "//ul[@class='tabular-list']";
    String LINK_SHOPS = "//a[@class='clickable-more']";
}
