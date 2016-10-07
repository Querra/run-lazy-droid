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
import de.querra.mobile.runlazydroid.services.internal.RealmService;

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


        adapter.addItem(getString(R.string.total_distance), this.formatter.asKilometers(this.realmService.getAllTimeDistance()), null);
        adapter.addItem(getString(R.string.total_pentalties), String.valueOf(this.realmService.getAllTimePenalties()), null);
        adapter.addItem(getString(R.string.total_penalty_distance), this.formatter.asKilometers(this.realmService.getAllTimePenaltyDistance()), null);
        adapter.addItem(getString(R.string.total_run_time), this.formatter.minutesToTimeString(this.realmService.getAllTimeRunTime()), null);
        float averageSpeed = this.realmService.getAverageSpeed();
        String averageAsKmPerHour = this.formatter.averageAsKmPerHour(averageSpeed);
        if (Math.round(averageSpeed) == 0){
            averageAsKmPerHour = getString(R.string.not_available);
        }
        adapter.addItem(getString(R.string.total_average_speed), averageAsKmPerHour, null);

        list.setAdapter(adapter);

        return view;
    }

}
