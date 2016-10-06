package de.querra.mobile.runlazydroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Date;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.data.RealmOperator;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.services.internal.PreferencesService;

public class PenaltyFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_penalty, container, false);

        AppCompatButton submit = (AppCompatButton) view.findViewById(R.id.fragment_penalty__submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Penalty penalty = new Penalty();
                Date created = new Date();
                penalty.setId(created.getTime());
                penalty.setCreated(created);
                penalty.setDistance(preferencesService.getPenaltyDistance());
                RealmOperator.saveOrUpdate(penalty);
                Toast.makeText(getActivity(), R.string.penalty_added, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
