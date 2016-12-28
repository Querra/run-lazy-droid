package de.querra.mobile.runlazydroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Date;
import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.services.internal.PreferencesService;
import de.querra.mobile.runlazydroid.services.internal.RealmService;

public class PenaltyFragment extends Fragment {

    PreferencesService preferencesService;
    RealmService realmService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.preferencesService = PreferencesService.getInstance();
        this.realmService = RealmService.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_penalty, container, false);

        AppCompatButton submit = (AppCompatButton) view.findViewById(R.id.fragment_penalty__submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Penalty penalty = new Penalty();
                Date created = new Date();
                penalty.setId(created.getTime());
                penalty.setCreated(created);
                penalty.setDistance(preferencesService.getPenaltyDistance(getActivity()));
                realmService.saveOrUpdate(penalty);
                Toast.makeText(getActivity(), R.string.penalty_added, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
