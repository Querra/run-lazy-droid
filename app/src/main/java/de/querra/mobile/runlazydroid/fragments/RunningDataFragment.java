package de.querra.mobile.runlazydroid.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.entities.RunType;
import de.querra.mobile.runlazydroid.helper.RealmHelper;
import de.querra.mobile.runlazydroid.helper.RunTypeHelper;

public class RunningDataFragment extends Fragment{

    private OnFragmentInteractionListener mListener;
    private String runType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.runType = RunTypeHelper.toLocalString(RunType.DEFAULT, getResources());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running_data, container, false);

        final EditText distance = (EditText) view.findViewById(R.id.fragment_running_data__distance);
        final EditText time = (EditText) view.findViewById(R.id.fragment_running_data__time);

        final Spinner runTypeSpinner = (Spinner) view.findViewById(R.id.fragment_running_data__run_type_spinner);
        List<String> runTypes = new ArrayList<>();
        for (RunType runType : EnumSet.allOf(RunType.class)){
            runTypes.add(RunTypeHelper.toLocalString(runType, getResources()));
        }
        ArrayAdapter<String> runTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, runTypes);
        runTypeSpinner.setAdapter(runTypeAdapter);
        runTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                RunningDataFragment.this.runType = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                RunningDataFragment.this.runType = RunTypeHelper.toLocalString(RunType.DEFAULT, getResources());
            }
        });

        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.fragment_running_data__submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float distanceInKm = Float.parseFloat(distance.getText().toString());
                float timeInMin = Float.parseFloat(time.getText().toString());
                RunEntry runEntry = new RunEntry();
                Date now = new Date();
                runEntry.setId(now.getTime());
                runEntry.setDate(now);
                runEntry.setDistance(distanceInKm);
                runEntry.setTime(timeInMin);
                runEntry.setType(RunTypeHelper.localStringToName(RunningDataFragment.this.runType, getResources()));
                RealmHelper.saveOrUpdate(runEntry);
            }
        });

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
