package xyz.wheretolive.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;
import xyz.wheretolive.core.domain.TrafficStop;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Collection;

@Path("/mapObject")
@Component
public class MapObjectResource {
    
    @Autowired
    MapObjectResourceService service;
    
    @GET
    @Path("/all")
    public Collection<MapObject> getIn(MapView view) {
        return service.getIn(view);
    }

    @GET
    @Path("/trafficStops")
    public Collection<TrafficStop> getTrafficStopsIn(MapView view) {
        return service.getIn(view, TrafficStop.class);
    }

    @GET
    @Path("/foodMarkets")
    public Collection<FoodMarket> getFoodMarketsIn(MapView view) {
        return service.getIn(view, FoodMarket.class);
    }
}
