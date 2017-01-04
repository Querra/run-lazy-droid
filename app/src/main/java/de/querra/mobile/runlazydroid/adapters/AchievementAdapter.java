package de.querra.mobile.runlazydroid.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.entities.Achievement;

public class AchievementAdapter extends RecyclerView.Adapter{

    private final List<Achievement> achievements;
    private final Context context;

    private class AchievementViewHolder extends RecyclerView.ViewHolder{

        private ImageView badge;
        private TextView name;
        private TextView flavorText;

        public AchievementViewHolder(View itemView) {
            super(itemView);
            this.badge = (ImageView) itemView.findViewById(R.id.view_achievement__badge);
            this.name = (TextView) itemView.findViewById(R.id.view_achievement__name);
            this.flavorText = (TextView) itemView.findViewById(R.id.view_achievement__flavor_text);
        }

        public void setBadge(int drawableId){
            this.badge.setImageDrawable(ContextCompat.getDrawable(context, drawableId));
        }

        public void setName(String name){
            this.name.setText(name);
        }

        public void setFlavorText(String flavorText){
            this.flavorText.setText(flavorText);
        }

        public void setAchieved(boolean achieved){
            if (achieved){
                setAlpha(1);
            }
            else {
                setAlpha(0.3f);
            }
        }

        private void setAlpha(float alpha){
            this.badge.setAlpha(alpha);
        }
      }

    public AchievementAdapter(List<Achievement> achievements, Context context){
        this.achievements = achievements;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_achievement, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AchievementViewHolder achievementView = (AchievementViewHolder) holder;
        Achievement achievement = this.achievements.get(position);
        achievementView.setBadge(achievement.getDrawableId());
        achievementView.setName(achievement.getName());
        achievementView.setFlavorText(achievement.getFlavorText());
        achievementView.setAchieved(achievement.isAchieved());
    }

    @Override
    public int getItemCount() {
        return this.achievements.size();
    }
}
