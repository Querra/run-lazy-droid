package de.querra.mobile.runlazydroid.data.entities;

import java.util.Date;

import de.querra.mobile.runlazydroid.data.SortableByDate;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Penalty extends RealmObject implements SortableByDate{

    public static final String ID_FIELD = "id";
    public static final String CREATED_FIELD = "created";

    @PrimaryKey
    private long id;

    private Date created;

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

    @Override
    public Date getSortDate() {
        return this.created;
    }
}
