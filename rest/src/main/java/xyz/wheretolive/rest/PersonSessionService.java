package xyz.wheretolive.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.wheretolive.core.domain.Person;

import javax.servlet.http.HttpSession;

@Component
public class PersonSessionService {

    @Autowired
    private HttpSession httpSession;

    public void storePerson(Person person) {
        httpSession.setAttribute("person", person);
    }

    public Person loadPerson() {
        return (Person) httpSession.getAttribute("person");
    }
}
