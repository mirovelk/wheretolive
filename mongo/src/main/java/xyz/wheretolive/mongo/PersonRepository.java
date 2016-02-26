package xyz.wheretolive.mongo;

import xyz.wheretolive.core.domain.Person;

public interface PersonRepository {

    void store(Person person);

    Person load(String facebookId, String facebookAuthToken);

    void updateVisitedRealities(Person person);
    
    void updateHiddenRealities(Person person);

    void updateSettings(Person person);
}
