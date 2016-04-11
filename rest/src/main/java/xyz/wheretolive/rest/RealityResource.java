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

import xyz.wheretolive.core.domain.MapView;
import xyz.wheretolive.core.domain.Reality;

@RequestMapping("/reality")
@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RealityResource {

    @Autowired
    private RealityResourceService service;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public @ResponseBody
    Collection<Reality> getHousingIn(@RequestBody MapView view) {
        return service.getFilteredHousingsIn(view);
    }

    @RequestMapping(value = "/meta", method = RequestMethod.POST)
    public @ResponseBody HousingMetaData getHousingMetaIn(@RequestBody MapView view) {
        return service.getHousingMetaDataIn(view);
    }

    @RequestMapping(value = "/visit", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void visit(@RequestParam(value = "realityId") String realityId, @RequestParam(value = "name") String name) {
        service.visit(realityId, name);
    }

    @RequestMapping(value = "/hide", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void hide(@RequestParam(value = "realityId") String realityId, @RequestParam(value = "name") String name) {
        service.hide(realityId, name);
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void favorite(@RequestParam(value = "realityId") String realityId, @RequestParam(value = "name") String name) {
        service.favorite(realityId, name);
    }
}
