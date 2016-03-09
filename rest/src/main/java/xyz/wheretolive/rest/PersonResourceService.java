package xyz.wheretolive.rest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.FacebookLongTermToken;
import xyz.wheretolive.core.domain.Person;
import xyz.wheretolive.core.domain.Settings;
import xyz.wheretolive.mongo.PersonRepository;
import xyz.wheretolive.rest.facebook.FacebookLongTermTokenService;
import xyz.wheretolive.rest.facebook.FacebookService;

@Component
public class PersonResourceService {

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private FacebookService facebookService;

    @Autowired
    private HttpSession httpSession;
    
    @Autowired
    private FacebookLongTermTokenService longTermTokenService;

    public Person login(String facebookId, String facebookAuthToken) {
        if (facebookId == null || facebookAuthToken == null) {
            return null;
        }
        facebookService.init(facebookAuthToken);
        String id = facebookService.getUserProfile().getId();
        if (!facebookId.equals(id)) {
            throw new IllegalArgumentException("Wrong Facebook credentials");
        }
        Person person = personRepository.load(facebookId, facebookAuthToken);
        if (person == null) {
            person = createNewPerson(facebookId, facebookAuthToken);
            personRepository.store(person);
        }
        httpSession.setAttribute("person", person);
        return person;
    }
    
    public void logout() {
        httpSession.setAttribute("person", null);
    }

    private Person createNewPerson(String facebookId, String facebookAuthToken) {
        Person person = new Person();
        person.setFacebookId(facebookId);
        FacebookLongTermToken longTermToken = longTermTokenService.execute(facebookAuthToken, facebookId);
        person.setLongTermToken(longTermToken);
        return person;
    }

    public void setSettings(Settings settings) {
        Person person = (Person) httpSession.getAttribute("person");
        if (person == null) {
            return;
        }
        if (person.getSettings().equals(settings)) {
            return;
        }
        person.setSettings(settings);
        personRepository.updateSettings(person);
        httpSession.setAttribute("person", person);
    }
}
