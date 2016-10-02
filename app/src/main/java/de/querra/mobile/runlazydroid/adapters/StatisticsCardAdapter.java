package de.querra.mobile.runlazydroid.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.RealmInterface;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.entities.RunType;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.ImageHelper;
import de.querra.mobile.runlazydroid.helper.RunTypeHelper;
import de.querra.mobile.runlazydroid.widgets.DeleteEntryDialogBuilder;
import io.realm.RealmObject;

public class StatisticsCardAdapter extends RecyclerView.Adapter{

    private static final int RUN = 0;
    private static final int PENALTY = 1;

    private List<RealmObject> data = new ArrayList<>();
    private Resources resources;
    private Context context;

    public StatisticsCardAdapter(Context context){
        super();
        this.context = context;
        this.resources = context.getResources();
    }

    private class StatisticsCardViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        private ImageView typeImage;
        private TextView type;
        private TextView distance;
        private TextView time;
        private TextView date;

        StatisticsCardViewHolder(View itemView) {
            super(itemView);
            this.typeImage = (ImageView) itemView.findViewById(R.id.view_statistics_card__type_image);
            this.type = (TextView) itemView.findViewById(R.id.view_statistics_card__type);
            this.distance = (TextView) itemView.findViewById(R.id.view_statistics_card__distance);
            this.time = (TextView) itemView.findViewById(R.id.view_statistics_card__time);
            this.date = (TextView) itemView.findViewById(R.id.view_statistics_card__date);
            itemView.setOnLongClickListener(this);
        }

        void setTypeImage(Drawable drawable){
            this.typeImage.setImageDrawable(drawable);
        }
        void setTypeImage(Bitmap bitmap){
            this.typeImage.setImageBitmap(bitmap);
        }

        void setTypeText(String typeText) {
            this.type.setText(typeText);
        }

        void setDistanceText(String distanceText) {
            this.distance.setText(distanceText);
        }

        void setTimeText(String timeText) {
            this.time.setText(timeText);
        }

        void setDateText(String dateText) {
            this.date.setText(dateText);
        }

        @Override
        public boolean onLongClick(View v) {
            DeleteEntryDialogBuilder.show(StatisticsCardAdapter.this.context, (RealmInterface) data.get(getAdapterPosition()), new Runnable() {
                @Override
                public void run() {
                    data.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
            return true;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_statistics_card, parent, false);
        return new StatisticsCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StatisticsCardViewHolder statisticsCard = (StatisticsCardViewHolder) holder;
        RealmObject item = data.get(position);
        if (item instanceof RunEntry) {
            final RunEntry entry = (RunEntry) item;
            RunType runType = RunType.fromString(entry.getType());
            String localRunType = RunTypeHelper.toLocalString(runType, this.resources);
            if (runType == RunType.MAP_RUN){
                statisticsCard.setTypeImage(ImageHelper.getImage(Formatter.getFileName(entry.getId())));
            }
            else {
                statisticsCard.setTypeImage(RunTypeHelper.getDrawable(runType, this.resources));
            }
            statisticsCard.setTypeText(localRunType);
            statisticsCard.setDistanceText(Formatter.asKilometers(entry.getDistance()));
            statisticsCard.setDateText(Formatter.dateToString(entry.getCreated()));
            statisticsCard.setTimeText(Formatter.inMinutes(entry.getTime(), this.context));
        }
        if (item instanceof Penalty) {
            final Penalty penalty = (Penalty) item;
            statisticsCard.setTypeImage(this.resources.getDrawable(R.drawable.ic_thumb_down));
            statisticsCard.setTypeText(this.resources.getString(R.string.penalty));
            statisticsCard.setDistanceText(this.resources.getString(R.string.penalty_text1));
            statisticsCard.setDateText(Formatter.dateToString(penalty.getCreated()));
            statisticsCard.setTimeText(this.resources.getString(R.string.penalty_text2));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(RunEntry runEntry){
        this.data.add(runEntry);
    }

    public void addItems(List<RealmObject> realmObjects){
        this.data.addAll(realmObjects);
    }
}
