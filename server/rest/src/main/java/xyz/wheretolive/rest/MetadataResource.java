package xyz.wheretolive.rest;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.wheretolive.core.domain.CrawlerLog;

@RequestMapping("/metadata")
@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MetadataResource {

    @Autowired
    private MetadataResourceService service;

    @RequestMapping(value = "/crawlerLog", method = RequestMethod.GET)
    public @ResponseBody List<CrawlerLog> crawlerLogs() {
        return service.loadLogs();
    }
}
