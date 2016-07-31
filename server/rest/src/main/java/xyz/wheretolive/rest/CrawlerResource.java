package xyz.wheretolive.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import xyz.wheretolive.crawl.Crawler;

@RequestMapping("/crawler")
@Controller
@Produces(MediaType.APPLICATION_JSON)
public class CrawlerResource {

    private static Logger logger = LogManager.getLogger(CrawlerResource.class);

    @Autowired
    private List<Crawler> crawlers;
    
    private final Map<String, Crawler> crawlerMap = new HashMap<>();
    
    @PostConstruct
    private void fillCrawlerMap() {
        for (Crawler crawler : crawlers) {
            crawlerMap.put(crawler.getName().toLowerCase(), crawler);
        }
        
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{simpleName}", method = RequestMethod.PUT)
    public void crawl(@PathVariable String simpleName) {
        crawlerMap.get(simpleName.toLowerCase()).execute();
    }

    @RequestMapping(value = "/all", method = RequestMethod.PUT)
    public void crawlAll() {
        for (Crawler crawler : crawlers) {
            try {
                crawler.execute();
            } catch (Exception e) {
                logger.error("error while running crawler: " + crawler.getName(), e);
            }
        }
    }
}
