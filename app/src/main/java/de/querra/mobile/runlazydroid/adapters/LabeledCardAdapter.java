package de.querra.mobile.runlazydroid.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.querra.mobile.runlazydroid.R;

public class LabeledCardAdapter extends RecyclerView.Adapter{

    private List<String[]> data = new ArrayList<>();

    private class LabeledCardViewHolder extends RecyclerView.ViewHolder {

        private TextView label;
        private TextView value;
        private TextView message;

        LabeledCardViewHolder(View itemView) {
            super(itemView);
            this.label = (TextView) itemView.findViewById(R.id.view_labeled_card__label);
            this.value = (TextView) itemView.findViewById(R.id.view_labeled_card__value);
            this.message = (TextView) itemView.findViewById(R.id.view_labeled_card__message);
        }

        void setLabelText(String labelText){
            this.label.setText(labelText);
        }

        void setValueText(String valueText){
            this.value.setText(valueText);
        }

        void setMessage(String messageText) {
            this.message.setText(messageText);
            this.message.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_labeled_card, parent, false);
        return new LabeledCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LabeledCardViewHolder labeledCard = (LabeledCardViewHolder) holder;
        labeledCard.setLabelText(data.get(position)[0]);
        labeledCard.setValueText(data.get(position)[1]);

        String message = data.get(position)[2];
        if (message != null){
            labeledCard.setMessage(message);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(String label, String value, @Nullable String message){
        String[] items = {label, value, message};
        this.data.add(items);
    }
}
