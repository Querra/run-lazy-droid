package de.querra.mobile.runlazydroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.adapters.viewholders.LabeledCardViewHolder;

public class LabeledCardAdapter extends RecyclerView.Adapter{

    private List<Map.Entry<String, String>> data = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_labeled_card, parent, false);
        return new LabeledCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LabeledCardViewHolder labeledCard = (LabeledCardViewHolder) holder;
        labeledCard.setLabelText(data.get(position).getKey());
        labeledCard.setValueText(data.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(String label, String value){
        this.data.add(new AbstractMap.SimpleEntry<>(label, value));
    }
}
