package xyz.wheretolive.core;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("mapObjects")
public class MapObject implements Localizable {
    
    @Id
    private ObjectId id;
    
    @Embedded
    private final Coordinates location;
    
    public MapObject(Coordinates location) {
        this.location = location;
    }

    @Override
    public Coordinates getLocation() {
        return location;
    }
}
