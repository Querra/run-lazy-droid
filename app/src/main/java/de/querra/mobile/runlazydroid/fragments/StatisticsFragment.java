package de.querra.mobile.runlazydroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.adapters.LabeledCardAdapter;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.services.RealmService;

public class StatisticsFragment extends Fragment {

    @Inject
    Formatter formatter;
    @Inject
    RealmService realmService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RunLazyDroidApplication.getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        RecyclerView list = (RecyclerView) view.findViewById(R.id.fragment_statistics__list);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LabeledCardAdapter adapter = new LabeledCardAdapter();


        adapter.addItem("Total distance", this.formatter.asKilometers(this.realmService.getAllTimeDistance()), null);
        adapter.addItem("Total penalties", String.valueOf(this.realmService.getAllTimePenalties()), null);
        adapter.addItem("Total penalty distance", this.formatter.asKilometers(this.realmService.getAllTimePenaltyDistance()), null);
        adapter.addItem("Total run time", this.formatter.minutesToTimeString(this.realmService.getAllTimeRunTime()), null);

        list.setAdapter(adapter);

        return view;
    }

}
