package xyz.wheretolive.crawl.pageObject;

/**
 * Created by anthonymottot on 25/10/2015.
 */
public interface IKauflandMap {
    String SEARCH = "//input[@name='searchstore']";
    String DO_SEARCH = "//button[contains(., 'Vyhledat')]";
    String RESULT_CONTAINER = "//div[@class='msf-search']";
    String STORE_ITEM = ".//a[@class='msf-storelist-item']";
    String NEXT_PAGE = "//a[@class='navibar-btn-right']";

}
