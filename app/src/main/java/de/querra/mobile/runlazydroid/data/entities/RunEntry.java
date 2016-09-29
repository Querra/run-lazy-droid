package de.querra.mobile.runlazydroid.data.entities;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RunEntry extends RealmObject{

    public static final String DATE_FIELD = "date";
    public static final String TIME_FIELD = "time";
    public static final String DISTANCE_FIELD = "distance";

    @PrimaryKey
    long id;

    private Date date;
    private float time;
    private float distance;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTime() {
        return this.time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
