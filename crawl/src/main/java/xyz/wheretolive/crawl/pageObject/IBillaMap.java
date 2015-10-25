package xyz.wheretolive.crawl.pageObject;

/**
 * Created by anthonymottot on 25/10/2015.
 */
public interface IBillaMap {
    String BILLA_SHOPS_CONTAINTER = "//div[@id='WW_myList_control_content']";
//    String ALL_SHOPS = "./div[contains(@class, 'ww_abstract_location_list CZ_')]//table//td[@class='ww_abstract_location_list_table_row_column2']/a[@class='ww_abstract_location_list_table_row_column_address']";
    String ALL_SHOPS = ".//a[@class='ww_abstract_location_list_table_row_column_address']";
    String ADDRESS_LINE1 = ".//div[contains(@class, 'ww_address_line0')]";
    String ADDRESS_LINE3 = ".//div[contains(@class, 'ww_address_line3')]";

}
