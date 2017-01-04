package de.querra.mobile.runlazydroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.adapters.AchievementAdapter;
import de.querra.mobile.runlazydroid.data.entities.Achievement;
import de.querra.mobile.runlazydroid.services.internal.RealmService;
import io.realm.Realm;
import io.realm.RealmResults;

public class AchievementFragment extends Fragment {

    RealmService realmService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.realmService = RealmService.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Achievement> achievements = realm.where(Achievement.class).findAll();

        RecyclerView grid = (RecyclerView) view.findViewById(R.id.fragment_achievement_grid);
        grid.setLayoutManager(new GridLayoutManager(getContext(), 3));
        grid.setAdapter(new AchievementAdapter(achievements, getContext()));

        return view;
    }

}
