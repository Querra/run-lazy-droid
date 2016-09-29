package de.querra.mobile.runlazydroid.adapters.viewholders;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.querra.mobile.runlazydroid.R;

public class StatisticsCardViewHolder extends RecyclerView.ViewHolder {

    private ImageView typeImage;
    private TextView type;
    private TextView distance;
    private TextView time;
    private TextView date;

    public StatisticsCardViewHolder(View itemView) {
        super(itemView);
        this.typeImage = (ImageView) itemView.findViewById(R.id.view_statistics_card__type_image);
        this.type = (TextView) itemView.findViewById(R.id.view_statistics_card__type);
        this.distance = (TextView) itemView.findViewById(R.id.view_statistics_card__distance);
        this.time = (TextView) itemView.findViewById(R.id.view_statistics_card__time);
        this.date = (TextView) itemView.findViewById(R.id.view_statistics_card__date);
    }

    public void setTypeImage(Drawable drawable){
        this.typeImage.setImageDrawable(drawable);
    }

    public void setTypeText(String typeText) {
        this.type.setText(typeText);
    }

    public void setDistanceText(String distanceText) {
        this.distance.setText(distanceText);
    }

    public void setTimeText(String timeText) {
        this.time.setText(timeText);
    }

    public void setDateText(String dateText) {
        this.date.setText(dateText);
    }

}
