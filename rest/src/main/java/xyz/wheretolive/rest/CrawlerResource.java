package xyz.wheretolive.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.wheretolive.crawl.Crawler;

@RequestMapping("/crawler")
@Controller
@Produces(MediaType.APPLICATION_JSON)
public class CrawlerResource {

    @Autowired
    private List<Crawler> crawlers;
    
    private final Map<String, Crawler> crawlerMap = new HashMap<>();
    

    @PostConstruct
    private void fillCrawlerMap() {
        for (Crawler crawler : crawlers) {
            crawlerMap.put(crawler.getClass().getSimpleName(), crawler);
        }
        
    }

    @RequestMapping(value = "/{simpleName}", method = RequestMethod.PUT)
    public void crawl(@PathVariable String simpleName) {
        crawlerMap.get(simpleName).execute();
    }
}
