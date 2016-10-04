package de.querra.mobile.runlazydroid.data.entities;

import java.util.Date;

import de.querra.mobile.runlazydroid.data.RealmInterface;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Achievement extends RealmObject implements RealmInterface {

    public static String ID_FIELD = "id";

    @PrimaryKey
    private long id;

    private Date created;
    private String name;
    private String achievementCategory;
    private String flavorText;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAchievementCategory() {
        return achievementCategory;
    }

    public void setAchievementCategory(String achievementCategory) {
        this.achievementCategory = achievementCategory;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
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
        return Achievement.class;
    }
}
