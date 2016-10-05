package de.querra.mobile.runlazydroid.data.entities;

import java.util.Date;

import io.realm.RealmObject;
import de.querra.mobile.runlazydroid.data.RealmInterface;
import io.realm.annotations.PrimaryKey;

public class Target extends RealmObject implements RealmInterface {

    private static final String ID_FIELD = "id";
    public static final String CREATED_FIELD = "created";
    public static final String ACHIEVED_FIELD = "achieved";

    @PrimaryKey
    private long id;

    private Date created;
    private Date startDate;
    private Date endDate;
    private float baseDistance;
    private boolean achieved;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getBaseDistance() {
        return baseDistance;
    }

    public void setBaseDistance(float baseDistance) {
        this.baseDistance = baseDistance;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    @Override
    public Date getSortDate() {
        return this.created;
    }

    @Override
    public long getRealmId() {
        return this.id;
    }

    @Override
    public String getIdField() {
        return ID_FIELD;
    }

    @Override
    public Class getRealmClass() {
        return Target.class;
    }
}
