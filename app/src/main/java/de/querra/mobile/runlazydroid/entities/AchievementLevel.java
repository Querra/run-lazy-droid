package de.querra.mobile.runlazydroid.entities;

import static de.querra.mobile.runlazydroid.entities.AchievementLevelNames.BRONZE_NAME;
import static de.querra.mobile.runlazydroid.entities.AchievementLevelNames.GOLD_NAME;
import static de.querra.mobile.runlazydroid.entities.AchievementLevelNames.HELL_NAME;
import static de.querra.mobile.runlazydroid.entities.AchievementLevelNames.MITHRIL_NAME;
import static de.querra.mobile.runlazydroid.entities.AchievementLevelNames.PLATINUM_NAME;
import static de.querra.mobile.runlazydroid.entities.AchievementLevelNames.SILVER_NAME;

public enum AchievementLevel {
    BRONZE(BRONZE_NAME),
    SILVER(SILVER_NAME),
    GOLD(GOLD_NAME),
    PLATINUM(PLATINUM_NAME),
    MITHRIL(MITHRIL_NAME),
    HELL(HELL_NAME);

    private final String name;

    AchievementLevel(String name){
        this.name = name;
    }

    public static AchievementLevel fromString(String achievementLevel){
        switch (achievementLevel){
            case BRONZE_NAME:
                return BRONZE;
            case SILVER_NAME:
                return SILVER;
            case GOLD_NAME:
                return GOLD;
            case PLATINUM_NAME:
                return PLATINUM;
            case MITHRIL_NAME:
                return MITHRIL;
            case HELL_NAME:
                return HELL;
        }
        return null;
    }

    public String getName(){
        return this.name;
    }
}
