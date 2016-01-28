package xyz.wheretolive.core.domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("mapObjects")
public class MapObject implements Localizable {
    
    @Id
    private ObjectId id;

    @Embedded
    private Coordinates location;
    
    public MapObject(Coordinates location) {
        this.location = location;
    }

    public MapObject() {
    }

    @Override
    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }
}
