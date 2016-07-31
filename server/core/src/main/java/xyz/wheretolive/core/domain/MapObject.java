package xyz.wheretolive.core.domain;

import java.util.Date;

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
    
    private Date updateTime = new Date();
    
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int hashCode() {
        return location == null ? 0 : location.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MapObject)) {
            return false;
        }
        MapObject other = (MapObject) obj;
        return location == null ? other.location == null : location.equals(other.location);
    }
}
