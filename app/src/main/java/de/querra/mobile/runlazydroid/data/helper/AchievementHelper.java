package de.querra.mobile.runlazydroid.data.helper;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.querra.mobile.rlblib.entities.AchievementCategory;
import de.querra.mobile.rlblib.entities.AchievementLevel;
import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.entities.Achievement;
import io.realm.Realm;

public class AchievementHelper {

    public static void createAchievements() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Achievement.class).findAll().deleteAllFromRealm();
        for (AchievementCategory category : AchievementCategory.values()) {
            for (AchievementLevel level : AchievementLevel.values()) {
                Date now = new Date();
                Achievement achievement = realm.createObject(Achievement.class, now.getTime()+new Random().nextLong());
                achievement.setCreated(now);
                String categoryName = category.getName();
                String levelName = level.getName();
                achievement.setName(String.format(Locale.getDefault(), "%s %s", categoryName, levelName));
                achievement.setAchievementCategory(categoryName);
                achievement.setAchievementLevel(levelName);
                achievement.setFlavorText(getFlavorText(category, level));
                achievement.setDrawableId(getDrawableId(category, level));
                achievement.setPoints(getPoints(level));
                achievement.setAchieved(false);
            }
        }
        realm.commitTransaction();
    }

    private static String getFlavorText(AchievementCategory category, AchievementLevel level) {
        return "not implemented yet"; //TODO
    }

    private static int getPoints(AchievementLevel level) {
        switch (level) {
            case BRONZE:
                return 5;
            case SILVER:
                return 10;
            case GOLD:
                return 25;
            case PLATINUM:
                return 50;
            case MITHRIL:
                return 75;
            case HELL:
                return 100;
            default:
                return 0;
        }
    }

    private static int getDrawableId(AchievementCategory category, AchievementLevel level) {
        switch (category) {
            case DISTANCE:
                switch (level) {
                    case BRONZE:
                        return R.drawable.badge_run_bronze;
                    case SILVER:
                        return R.drawable.badge_run_silver;
                    case GOLD:
                        return R.drawable.badge_run_gold;
                    case PLATINUM:
                        return R.drawable.badge_run_platinum;
                    case MITHRIL:
                        return R.drawable.badge_run_mithril;
                    case HELL:
                        return R.drawable.badge_run_hell;
                }
            case TIME:
                switch (level) {
                    case BRONZE:
                        return R.drawable.badge_time_bronze;
                    case SILVER:
                        return R.drawable.badge_time_silver;
                    case GOLD:
                        return R.drawable.badge_time_gold;
                    case PLATINUM:
                        return R.drawable.badge_time_platinum;
                    case MITHRIL:
                        return R.drawable.badge_time_mithril;
                    case HELL:
                        return R.drawable.badge_time_hell;
                }
            case SPEED:
                switch (level) {
                    case BRONZE:
                        return R.drawable.badge_speed_bronze;
                    case SILVER:
                        return R.drawable.badge_speed_silver;
                    case GOLD:
                        return R.drawable.badge_speed_gold;
                    case PLATINUM:
                        return R.drawable.badge_speed_platinum;
                    case MITHRIL:
                        return R.drawable.badge_speed_mithril;
                    case HELL:
                        return R.drawable.badge_speed_hell;
                }
            case ACTIVITY:
                switch (level) {
                    case BRONZE:
                        return R.drawable.badge_activity_bronze;
                    case SILVER:
                        return R.drawable.badge_activity_silver;
                    case GOLD:
                        return R.drawable.badge_activity_gold;
                    case PLATINUM:
                        return R.drawable.badge_activity_platinum;
                    case MITHRIL:
                        return R.drawable.badge_activity_mithril;
                    case HELL:
                        return R.drawable.badge_activity_hell;
                }
            case SPECIAL:
                switch (level) {
                    case BRONZE:
                        return R.drawable.badge_special_bronze;
                    case SILVER:
                        return R.drawable.badge_special_silver;
                    case GOLD:
                        return R.drawable.badge_special_gold;
                    case PLATINUM:
                        return R.drawable.badge_special_platinum;
                    case MITHRIL:
                        return R.drawable.badge_special_mithril;
                    case HELL:
                        return R.drawable.badge_special_hell;
                }
            default:
                return 0;
        }
    }
}
