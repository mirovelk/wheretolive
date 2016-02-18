package xyz.wheretolive.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.wheretolive.core.domain.Person;

@RequestMapping("/person")
@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Autowired
    private PersonResourceService service;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody Person getIn(@RequestParam(value = "facebookId") String facebookId, @RequestParam(value = "facebookAuthToken") String facebookAuthToken) {
        return service.login(facebookId, facebookAuthToken);
    }
}
