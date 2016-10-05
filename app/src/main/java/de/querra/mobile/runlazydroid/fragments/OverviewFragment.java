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
import de.querra.mobile.runlazydroid.helper.DateHelper;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.services.PreferencesService;
import de.querra.mobile.runlazydroid.services.RealmService;

public class OverviewFragment extends Fragment {

    @Inject
    Formatter formatter;
    @Inject
    DateHelper dateHelper;
    @Inject
    RealmService realmService;
    @Inject
    PreferencesService preferencesService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RunLazyDroidApplication.getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        RecyclerView list = (RecyclerView) view.findViewById(R.id.fragment_overview__list);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LabeledCardAdapter adapter = new LabeledCardAdapter();

        float penaltyDistance = this.realmService.getTotalPenaltyDistance();
        float distanceRun = this.realmService.getDistanceRun();
        float totalWeekGoal = this.preferencesService.getWeekTarget()+penaltyDistance;
        float distanceLeft = (totalWeekGoal - distanceRun);
        String target = getString(R.string.target);
        boolean targetAchieved = false;
        if(distanceLeft<0f){
            distanceLeft = 0f;
            targetAchieved = true;
        }

        String daysLeft = this.formatter.getDaysLeft(this.dateHelper.getNextSunday());
        adapter.addItem(getString(R.string.time_left), daysLeft, Integer.valueOf(daysLeft.split(" ")[0])<3&&distanceLeft>0f?getString(R.string.hurry_up):null);
        adapter.addItem(getString(R.string.distance_left), this.formatter.asKilometers(distanceLeft), targetAchieved?getString(R.string.done):null);
        adapter.addItem(getString(R.string.distance_run_literal), this.formatter.asKilometers(distanceRun), distanceRun>30f?getString(R.string.wow):distanceRun>25f?getString(R.string.great):distanceRun>20f?getString(R.string.nice):distanceRun<2f?getString(R.string.time_for_a_run):null);
        adapter.addItem(target, this.formatter.asKilometers(totalWeekGoal), targetAchieved?getString(R.string.achieved):null);
        adapter.addItem(getString(R.string.penalty_literal), this.formatter.asKilometers(penaltyDistance), penaltyDistance>0f?getString(R.string.really):null);

        list.setAdapter(adapter);

        return view;
    }
}
