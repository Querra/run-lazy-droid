package de.querra.mobile.runlazydroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.adapters.LabeledCardAdapter;
import de.querra.mobile.runlazydroid.data.RealmCalculator;
import de.querra.mobile.runlazydroid.helper.Formatter;

public class StatisticsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        RecyclerView list = (RecyclerView) view.findViewById(R.id.fragment_statistics__list);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LabeledCardAdapter adapter = new LabeledCardAdapter();


        adapter.addItem("Total distance", Formatter.asKilometers(RealmCalculator.getAllTimeDistance()), null);
        adapter.addItem("Total penalties", String.valueOf(RealmCalculator.getAllTimePenalties()), null);
        adapter.addItem("Total penalty distance", Formatter.asKilometers(RealmCalculator.getAllTimePenaltyDistance()), null);
        adapter.addItem("Total run time", Formatter.minutesToTimeString(RealmCalculator.getAllTimeRunTime()), null);

        list.setAdapter(adapter);

        return view;
    }

}
