package de.querra.mobile.runlazydroid.data.entities;

import java.util.Date;

import de.querra.mobile.runlazydroid.data.SortableByDate;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RunEntry extends RealmObject implements SortableByDate{

    public static final String CREATED_FIELD = "created";
    public static final String TIME_FIELD = "time";
    public static final String DISTANCE_FIELD = "distance";

    @PrimaryKey
    private long id;

    private String type;
    private Date created;
    private int time;
    private float distance;

    public long getId() {return this.id;}
    public void setId(long id) {
        this.id = id;
    }
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public Date getCreated() {
        return this.created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public int getTime() {
        return this.time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public float getDistance() {
        return this.distance;
    }
    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public Date getSortDate() {
        return this.created;
    }
}
