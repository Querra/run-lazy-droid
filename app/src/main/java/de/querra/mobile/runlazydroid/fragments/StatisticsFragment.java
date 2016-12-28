package de.querra.mobile.runlazydroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import de.querra.mobile.rlblib.helper.Formatter;
import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.adapters.LabeledCardAdapter;
import de.querra.mobile.runlazydroid.services.internal.RealmService;

public class StatisticsFragment extends Fragment {

    RealmService realmService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.realmService = RealmService.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        RecyclerView list = (RecyclerView) view.findViewById(R.id.fragment_statistics__list);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LabeledCardAdapter adapter = new LabeledCardAdapter();


        int allTimeTargetsDone = this.realmService.getAllTimeTargets() - 1;
        int allTimeTargetsAchieved = this.realmService.getAllTimeTargetsAchieved();
        int targetsFailed = allTimeTargetsDone - allTimeTargetsAchieved;
        adapter.addItem(getString(R.string.targets_tackled), String.valueOf(allTimeTargetsDone), null);
        float achievedPercentage = (float) allTimeTargetsAchieved / (float) allTimeTargetsDone * 100;
        if (allTimeTargetsDone == 0){
            achievedPercentage = 0f;
        }
        adapter.addItem(getString(R.string.targets_achieved), String.format(Locale.getDefault(), "%.1f%s  (%d)", achievedPercentage, "%", allTimeTargetsAchieved), null);
        float failedPercentage = (float) targetsFailed / (float) allTimeTargetsDone * 100;
        if (allTimeTargetsDone == 0){
            failedPercentage = 0f;
        }
        adapter.addItem(getString(R.string.targets_failed), String.format(Locale.getDefault(), "%.1f%s  (%d)", failedPercentage, "%", targetsFailed), null);
        adapter.addItem(getString(R.string.total_distance), Formatter.asKilometers(this.realmService.getAllTimeDistance()), null);
        adapter.addItem(getString(R.string.total_pentalties), String.valueOf(this.realmService.getAllTimePenalties()), null);
        adapter.addItem(getString(R.string.total_penalty_distance), Formatter.asKilometers(this.realmService.getAllTimePenaltyDistance()), null);
        adapter.addItem(getString(R.string.total_run_time), Formatter.minutesToTimeString(this.realmService.getAllTimeRunTime()), null);
        float averageSpeed = this.realmService.getAverageSpeed();
        String averageAsKmPerHour = Formatter.averageAsKmPerHour(averageSpeed);
        if (Math.round(averageSpeed * 60) == 0) {
            averageAsKmPerHour = getString(R.string.not_available);
        }
        adapter.addItem(getString(R.string.total_average_speed), averageAsKmPerHour, null);

        list.setAdapter(adapter);

        return view;
    }

}
