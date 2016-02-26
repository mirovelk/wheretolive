package xyz.wheretolive.mongo;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xyz.wheretolive.core.domain.Person;

@Repository
public class MongoPersonRepository implements PersonRepository {

    private DatastoreProvider datastoreProvider;
    
    @Autowired
    public MongoPersonRepository(DatastoreProvider datastoreProvider) {
        this.datastoreProvider = datastoreProvider;
    }

    @Override
    public void store(Person person) {
        datastoreProvider.getDatastore().save(person);   
    }

    @Override
    public Person load(String facebookId, String facebookAuthToken) {
        Query<Person> query = datastoreProvider.getDatastore().find(Person.class, "facebookId", facebookId);
        return query.get();
    }

    @Override
    public void updateVisitedRealities(Person person) {
        Datastore datastore = datastoreProvider.getDatastore();
        Query<Person> updateQuery = datastore.createQuery(Person.class).field("_id").equal(person.getId());
        UpdateOperations<Person> unset = datastore.createUpdateOperations(Person.class).unset("visitedRealities");
        datastore.update(updateQuery, unset);
        UpdateOperations<Person> set = datastore.createUpdateOperations(Person.class).set("visitedRealities", person.getVisitedRealities());
        datastore.update(updateQuery, set);
    }

    @Override
    public void updateHiddenRealities(Person person) {
        Datastore datastore = datastoreProvider.getDatastore();
        Query<Person> updateQuery = datastore.createQuery(Person.class).field("_id").equal(person.getId());
        UpdateOperations<Person> unset = datastore.createUpdateOperations(Person.class).unset("hiddenRealities");
        datastore.update(updateQuery, unset);
        UpdateOperations<Person> set = datastore.createUpdateOperations(Person.class).set("hiddenRealities", person.getHiddenRealities());
        datastore.update(updateQuery, set);
    }

    @Override
    public void updateSettings(Person person) {
        Datastore datastore = datastoreProvider.getDatastore();
        Query<Person> updateQuery = datastore.createQuery(Person.class).field("_id").equal(person.getId());
        UpdateOperations<Person> unset = datastore.createUpdateOperations(Person.class).unset("settings");
        datastore.update(updateQuery, unset);
        UpdateOperations<Person> set = datastore.createUpdateOperations(Person.class).set("settings", person.getSettings());
        datastore.update(updateQuery, set);
    }
}
