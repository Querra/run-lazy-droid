package de.querra.mobile.runlazydroid.entities;

public enum AchievementCategory {
    DISTANCE(AchievementCategoryNames.DISTANCE_NAME),
    TIME(AchievementCategoryNames.TIME_NAME),
    ACTIVITY(AchievementCategoryNames.ACTIVITY_NAME),
    SPECIAL(AchievementCategoryNames.SPECIAL_NAME);

    private final String name;

    AchievementCategory(String name){
        this.name = name;
    }

    public static AchievementCategory fromString(String achievementCategory){
        switch (achievementCategory){
            case AchievementCategoryNames.DISTANCE_NAME:
                return DISTANCE;
            case AchievementCategoryNames.TIME_NAME:
                return TIME;
            case AchievementCategoryNames.ACTIVITY_NAME:
                return ACTIVITY;
            case AchievementCategoryNames.SPECIAL_NAME:
                return SPECIAL;
        }
        return null;
    }

    public String getName(){
        return this.name;
    }
}
