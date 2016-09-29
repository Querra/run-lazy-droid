package de.querra.mobile.runlazydroid.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.activities.MainActivity;
import de.querra.mobile.runlazydroid.adapters.LabeledCardAdapter;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.helper.DateHelper;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.PreferencesHelper;
import io.realm.Realm;
import io.realm.RealmResults;

public class OverviewFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fragment_overview__add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onAddEntryRequested();
            }
        });

        RecyclerView list = (RecyclerView) view.findViewById(R.id.fragment_overview__list);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LabeledCardAdapter adapter = new LabeledCardAdapter();

        Date from = DateHelper.getLastSunday().toDate();
        Date to = DateHelper.getNextSunday().toDate();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> thisWeeksEntries = realm.where(RunEntry.class).between(RunEntry.CREATED_FIELD, from, to).findAll();
        float distanceRun = 0f;
        for (RunEntry runEntry : thisWeeksEntries){
            distanceRun += runEntry.getDistance();
        }

        RealmResults<Penalty> penalties = realm.where(Penalty.class).between(Penalty.CREATED_FIELD, from, to).findAll();
        int numberOfPenalties = 0;
        for (Penalty penalty : penalties){
            numberOfPenalties += 1;
        }
        float penaltyDistance = Formatter.getPenaltyDistance(getActivity(), numberOfPenalties);

        float weekGoal = PreferencesHelper.getWeekGoal(getActivity())+penaltyDistance;
        float distanceLeft = (weekGoal-distanceRun);
        String target = "Target";
        if(distanceLeft<0f){
            distanceLeft = 0f;
            target += " - achieved";
        }

        adapter.addItem("Distance run", Formatter.asKilometers(distanceRun));
        adapter.addItem(target, Formatter.asKilometers(weekGoal));
        adapter.addItem("Distance left", Formatter.asKilometers(distanceLeft));
        adapter.addItem("Penalty", Formatter.penaltyToKilometers(getActivity(), numberOfPenalties));
        adapter.addItem("Time left", Formatter.getDaysLeft(DateHelper.getNextSunday()));

        list.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onAddEntryRequested();
    }

}
