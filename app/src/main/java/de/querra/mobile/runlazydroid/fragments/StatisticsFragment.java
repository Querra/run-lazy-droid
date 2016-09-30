package de.querra.mobile.runlazydroid.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.adapters.StatisticsCardAdapter;
import de.querra.mobile.runlazydroid.data.RealmInterface;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

public class StatisticsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


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
        StatisticsCardAdapter adapter = new StatisticsCardAdapter(getActivity());

        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> runEntries = realm.where(RunEntry.class).findAll().sort(RunEntry.CREATED_FIELD, Sort.DESCENDING);
        RealmResults<Penalty> penalties = realm.where(Penalty.class).findAll().sort(Penalty.CREATED_FIELD, Sort.DESCENDING);

        List<RealmInterface> dateSortList = new ArrayList<>();
        dateSortList.addAll(runEntries);
        dateSortList.addAll(penalties);
        Collections.sort(dateSortList, new Comparator<RealmInterface>() {
            @Override
            public int compare(RealmInterface realmInterface, RealmInterface t1) {
                return realmInterface.getSortDate().compareTo(t1.getSortDate());
            }
        });
        List<RealmObject> realmObjects = new ArrayList<>();
        for (RealmInterface realmInterface : dateSortList){
            realmObjects.add((RealmObject) realmInterface);
        }

        adapter.addItems(realmObjects);

        list.setAdapter(adapter);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onOverviewFragmentInteraction(uri);
        }
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
        void onOverviewFragmentInteraction(Uri uri);
    }

}
