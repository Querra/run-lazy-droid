package de.querra.mobile.runlazydroid.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.entities.Achievement;
import de.querra.mobile.runlazydroid.entities.AchievementCategory;
import de.querra.mobile.runlazydroid.entities.AchievementLevel;

public class AchievementDialogBuilder {

    private Context context;

    public void show(Context context, Achievement achievement) {
        this.context = context;
        View content = LayoutInflater.from(context).inflate(R.layout.view_achievement_dialog, null);
        fillContent(content, achievement);
        new AlertDialog.Builder(context)
                .setTitle("Achievement earned!")
                .setView(content)
                .show();
    }

    private void fillContent(View content, Achievement achievement) {
        TextView congratulation = (TextView) content.findViewById(R.id.view_achievement_dialog__congratulation);
        ImageView badge = (ImageView) content.findViewById(R.id.view_achievement_dialog__badge);
        TextView name = (TextView) content.findViewById(R.id.view_achievement_dialog__name);
        TextView flavorText = (TextView) content.findViewById(R.id.view_achievement_dialog__flavor_text);
        congratulation.setText(this.context.getString(R.string.congrats).concat(String.valueOf(achievement.getPoints())).concat(this.context.getString(R.string.points)));
        badge.setImageDrawable(getAchievementDrawable(achievement));
        name.setText(achievement.getName());
        flavorText.setText(achievement.getFlavorText());
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    private Drawable getAchievementDrawable(Achievement achievement) {
        if (achievement == null || achievement.getAchievementCategory() == null || achievement.getAchievementLevel() == null){
            return null;
        }
        switch (AchievementCategory.fromString(achievement.getAchievementCategory())){
            case DISTANCE:
                switch (AchievementLevel.fromString(achievement.getAchievementLevel())){
                    case BRONZE:
                        return this.context.getResources().getDrawable(R.drawable.badge_run_bronze);
                    case SILVER:
                        return this.context.getResources().getDrawable(R.drawable.badge_run_silver);
                    case GOLD:
                        return this.context.getResources().getDrawable(R.drawable.badge_run_gold);
                    case PLATINUM:
                        return this.context.getResources().getDrawable(R.drawable.badge_run_platinum);
                    case MITHRIL:
                        return this.context.getResources().getDrawable(R.drawable.badge_run_mithril);
                    case HELL:
                        return this.context.getResources().getDrawable(R.drawable.badge_run_hell);
                }
            case TIME:
                switch (AchievementLevel.fromString(achievement.getAchievementLevel())){
                    case BRONZE:
                        return this.context.getResources().getDrawable(R.drawable.badge_time_bronze);
                    case SILVER:
                        return this.context.getResources().getDrawable(R.drawable.badge_time_silver);
                    case GOLD:
                        return this.context.getResources().getDrawable(R.drawable.badge_time_gold);
                    case PLATINUM:
                        return this.context.getResources().getDrawable(R.drawable.badge_time_platinum);
                    case MITHRIL:
                        return this.context.getResources().getDrawable(R.drawable.badge_time_mithril);
                    case HELL:
                        return this.context.getResources().getDrawable(R.drawable.badge_time_hell);
                }
            case SPEED:
                switch (AchievementLevel.fromString(achievement.getAchievementLevel())){
                    case BRONZE:
                        return this.context.getResources().getDrawable(R.drawable.badge_speed_bronze);
                    case SILVER:
                        return this.context.getResources().getDrawable(R.drawable.badge_speed_silver);
                    case GOLD:
                        return this.context.getResources().getDrawable(R.drawable.badge_speed_gold);
                    case PLATINUM:
                        return this.context.getResources().getDrawable(R.drawable.badge_speed_platinum);
                    case MITHRIL:
                        return this.context.getResources().getDrawable(R.drawable.badge_speed_mithril);
                    case HELL:
                        return this.context.getResources().getDrawable(R.drawable.badge_speed_hell);
                }
            case ACTIVITY:
                switch (AchievementLevel.fromString(achievement.getAchievementLevel())){
                    case BRONZE:
                        return this.context.getResources().getDrawable(R.drawable.badge_activity_bronze);
                    case SILVER:
                        return this.context.getResources().getDrawable(R.drawable.badge_activity_silver);
                    case GOLD:
                        return this.context.getResources().getDrawable(R.drawable.badge_activity_gold);
                    case PLATINUM:
                        return this.context.getResources().getDrawable(R.drawable.badge_activity_platinum);
                    case MITHRIL:
                        return this.context.getResources().getDrawable(R.drawable.badge_activity_mithril);
                    case HELL:
                        return this.context.getResources().getDrawable(R.drawable.badge_activity_hell);
                }
            case SPECIAL:
                switch (AchievementLevel.fromString(achievement.getAchievementLevel())){
                    case BRONZE:
                        return this.context.getResources().getDrawable(R.drawable.badge_special_bronze);
                    case SILVER:
                        return this.context.getResources().getDrawable(R.drawable.badge_special_silver);
                    case GOLD:
                        return this.context.getResources().getDrawable(R.drawable.badge_special_gold);
                    case PLATINUM:
                        return this.context.getResources().getDrawable(R.drawable.badge_special_platinum);
                    case MITHRIL:
                        return this.context.getResources().getDrawable(R.drawable.badge_special_mithril);
                    case HELL:
                        return this.context.getResources().getDrawable(R.drawable.badge_special_hell);
                }
        }
        return null;
    }
}
