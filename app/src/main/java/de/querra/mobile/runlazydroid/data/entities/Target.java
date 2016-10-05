package de.querra.mobile.runlazydroid.data.entities;

import java.util.Date;

import io.realm.RealmObject;
import de.querra.mobile.runlazydroid.data.RealmInterface;
import io.realm.annotations.PrimaryKey;

public class Target extends RealmObject implements RealmInterface {

    private static String ID_FIELD = "id";

    @PrimaryKey
    private long id;

    private Date created;
    private Date startDate;
    private Date endDate;
    private float baseDistance;
    private boolean achieved;

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
