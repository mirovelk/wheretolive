package xyz.wheretolive.rest;

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

import xyz.wheretolive.core.domain.Person;
import xyz.wheretolive.core.domain.Settings;

@RequestMapping("/person")
@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Autowired
    private PersonResourceService service;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody Person login(@RequestParam(value = "facebookId") String facebookId, @RequestParam(value = "facebookAuthToken") String facebookAuthToken) {
        return service.login(facebookId, facebookAuthToken);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void logout() {
        service.logout();
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void setSettings(@RequestBody Settings settings) {
        service.setSettings(settings);
    }
}
