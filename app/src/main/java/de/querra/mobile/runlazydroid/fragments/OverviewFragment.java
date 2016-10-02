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
import de.querra.mobile.runlazydroid.helper.DateHelper;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.PreferencesHelper;

public class OverviewFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        RecyclerView list = (RecyclerView) view.findViewById(R.id.fragment_overview__list);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LabeledCardAdapter adapter = new LabeledCardAdapter();

        float penaltyDistance = RealmCalculator.getTotalPenaltyDistance(getActivity());
        float distanceRun = RealmCalculator.getDistanceRun();
        float totalWeekGoal = PreferencesHelper.getWeekTarget(getActivity())+penaltyDistance;
        float distanceLeft = (totalWeekGoal - distanceRun);
        String target = getString(R.string.target);
        if(distanceLeft<0f){
            distanceLeft = 0f;
            target += " - " + getString(R.string.achieved);
        }

        adapter.addItem(getString(R.string.time_left), Formatter.getDaysLeft(DateHelper.getNextSunday(), getActivity()));
        adapter.addItem(getString(R.string.distance_left), Formatter.asKilometers(distanceLeft));
        adapter.addItem(getString(R.string.distance_run_literal), Formatter.asKilometers(distanceRun));
        adapter.addItem(target, Formatter.asKilometers(totalWeekGoal));
        adapter.addItem(getString(R.string.penalty_literal), Formatter.asKilometers(penaltyDistance));

        list.setAdapter(adapter);

        return view;
    }
}
