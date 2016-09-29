package de.querra.mobile.runlazydroid.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.querra.mobile.runlazydroid.R;

public class LabeledCardViewHolder extends RecyclerView.ViewHolder {

    private TextView label;
    private TextView value;

    public LabeledCardViewHolder(View itemView) {
        super(itemView);
        this.label = (TextView) itemView.findViewById(R.id.view_labeled_card__label);
        this.value = (TextView) itemView.findViewById(R.id.view_labeled_card__value);
    }

    public void setLabelText(String labelText){
        this.label.setText(labelText);
    }

    public void setValueText(String valueText){
        this.value.setText(valueText);
    }

}
