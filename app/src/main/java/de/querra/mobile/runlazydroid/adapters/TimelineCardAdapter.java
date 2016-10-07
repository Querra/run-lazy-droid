package de.querra.mobile.runlazydroid.adapters;

import android.app.Activity;
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

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.data.RealmInterface;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.entities.RunType;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.RunTypeHelper;
import de.querra.mobile.runlazydroid.services.internal.ImageService;
import de.querra.mobile.runlazydroid.services.internal.RealmService;
import de.querra.mobile.runlazydroid.widgets.DeleteEntryDialogBuilder;
import io.realm.RealmObject;

public class TimelineCardAdapter extends RecyclerView.Adapter{

    @Inject
    Formatter formatter;
    @Inject
    Resources resources;
    @Inject
    Context context;
    @Inject
    ImageService imageService;
    @Inject
    RunTypeHelper runTypeHelper;
    @Inject
    RealmService realmService;

    private List<RealmObject> data = new ArrayList<>();
    private Activity activity;


    public TimelineCardAdapter(Activity activity){
        super();
        RunLazyDroidApplication.getAppComponent().inject(this);
        this.activity = activity;
    }

    public class TimelineCardViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        private ImageView typeImage;
        private TextView type;
        private TextView distance;
        private TextView time;
        private TextView date;

        TimelineCardViewHolder(View itemView) {
            super(itemView);
            this.typeImage = (ImageView) itemView.findViewById(R.id.view_timeline_card__type_image);
            this.type = (TextView) itemView.findViewById(R.id.view_timeline_card__type);
            this.distance = (TextView) itemView.findViewById(R.id.view_timeline_card__distance);
            this.time = (TextView) itemView.findViewById(R.id.view_timeline_card__time);
            this.date = (TextView) itemView.findViewById(R.id.view_timeline_card__date);
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
            DeleteEntryDialogBuilder.show(activity, (RealmInterface) data.get(getAdapterPosition()), new Runnable() {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_timeline_card, parent, false);
        return new TimelineCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimelineCardViewHolder statisticsCard = (TimelineCardViewHolder) holder;
        RealmObject item = data.get(position);
        if (item instanceof RunEntry) {
            final RunEntry entry = (RunEntry) item;
            RunType runType = RunType.fromString(entry.getType());
            String localRunType = this.runTypeHelper.toLocalString(runType);
            if (runType == RunType.MAP_RUN){
                statisticsCard.setTypeImage(this.imageService.getImage(this.formatter.getFileName(entry.getId())));
            }
            else {
                statisticsCard.setTypeImage(this.runTypeHelper.getDrawable(runType));
            }
            statisticsCard.setTypeText(localRunType);
            statisticsCard.setDistanceText(this.formatter.asKilometers(entry.getDistance()));
            statisticsCard.setDateText(this.formatter.dateToString(entry.getCreated()));
            statisticsCard.setTimeText(this.formatter.inMinutes(entry.getTime()));
        }
        if (item instanceof Penalty) {
            final Penalty penalty = (Penalty) item;
            statisticsCard.setTypeImage(this.resources.getDrawable(R.drawable.ic_thumb_down));
            statisticsCard.setTypeText(this.resources.getString(R.string.penalty));
            float penaltyDistance = penalty.getDistance();
            statisticsCard.setDistanceText(this.formatter.asKilometers(penaltyDistance));
            statisticsCard.setDateText(this.formatter.dateToString(penalty.getCreated()));
            int minutesFromAverage = (int) (penaltyDistance / this.realmService.getAverageSpeed());
            String minutes = this.formatter.asMinutesFromAverage(minutesFromAverage);
            if (minutesFromAverage == 0){
                minutes = this.context.getString(R.string.not_available);
            }
            statisticsCard.setTimeText(minutes);
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
