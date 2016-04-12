package xyz.wheretolive.rest;

import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;
import xyz.wheretolive.core.domain.Reality;
import xyz.wheretolive.core.domain.TrafficStop;

@RequestMapping("/mapObject")
@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MapObjectResource {
    
    @Autowired
    private MapObjectResourceService service;
    
    @RequestMapping("/test")
    public @ResponseBody String test() {
        return "test";
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public @ResponseBody Collection<MapObject> getIn(MapView view) {
        return service.getIn(view, MapObject.class);
    }

    @RequestMapping(value = "/trafficStops", method = RequestMethod.POST)
    public @ResponseBody Collection<TrafficStop> getTrafficStopsIn(@RequestBody MapView view) {
        return service.getIn(view, TrafficStop.class);
    }

    @RequestMapping(value = "/foodMarkets", method = RequestMethod.POST)
    public @ResponseBody Collection<FoodMarket> getFoodMarketsIn(@RequestBody MapView view) {
        return service.getIn(view, FoodMarket.class);
    }

}
