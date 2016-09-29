package de.querra.mobile.runlazydroid.adapters;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.adapters.viewholders.StatisticsCardViewHolder;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.entities.RunType;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.RunTypeHelper;
import io.realm.RealmObject;

public class StatisticsCardAdapter extends RecyclerView.Adapter{

    private List<RealmObject> data = new ArrayList<>();
    private Resources resources;

    public StatisticsCardAdapter(Resources resources){
        super();
        this.resources = resources;
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
            RunEntry entry = (RunEntry) item;
            RunType runType = RunType.fromString(entry.getType());
            String localRunType = RunTypeHelper.toLocalString(runType, this.resources);
            Drawable runTypeImage = RunTypeHelper.getDrawable(runType, this.resources);
            statisticsCard.setTypeImage(runTypeImage);
            statisticsCard.setTypeText(localRunType);
            statisticsCard.setDistanceText(Formatter.asKilometers(entry.getDistance()));
            statisticsCard.setDateText(Formatter.dateToString(entry.getCreated()));
            statisticsCard.setTimeText(Formatter.inMinutes(entry.getTime()));
        }
        if (item instanceof Penalty) {
            Penalty penalty = (Penalty) item;
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
