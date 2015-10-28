package xyz.wheretolive.rest;

import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;
import xyz.wheretolive.core.domain.TrafficStop;

@Path("/mapObject")
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MapObjectResource {
    
    @Autowired
    MapObjectResourceService service;
    
    @POST
    @Path("/test")
    public String test() {
        return "test";
    }

    @POST
    @Path("/all")
    public Collection<MapObject> getIn(MapView view) {
        return service.getIn(view);
    }

    @POST
    @Path("/trafficStops")
    public Collection<TrafficStop> getTrafficStopsIn(MapView view) {
        return service.getIn(view, TrafficStop.class);
    }

    @POST
    @Path("/foodMarkets")
    public Collection<FoodMarket> getFoodMarketsIn(MapView view) {
        return service.getIn(view, FoodMarket.class);
    }
}
