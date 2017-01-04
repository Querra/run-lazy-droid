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
    private String achievementLevel;
    private String flavorText;
    private boolean achieved;
    private int drawableId;
    private int points;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAchievementCategory() {
        return this.achievementCategory;
    }

    public void setAchievementCategory(String achievementCategory) {
        this.achievementCategory = achievementCategory;
    }

    public String getAchievementLevel() {
        return achievementLevel;
    }

    public void setAchievementLevel(String achievementLevel) {
        this.achievementLevel = achievementLevel;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public boolean isAchieved() {
        return this.achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public int getDrawableId() {
        return this.drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
