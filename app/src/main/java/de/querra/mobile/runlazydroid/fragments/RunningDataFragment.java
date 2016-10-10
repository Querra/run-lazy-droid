package de.querra.mobile.runlazydroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.entities.RunType;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.MathHelper;
import de.querra.mobile.runlazydroid.helper.RunTypeHelper;
import de.querra.mobile.runlazydroid.services.internal.RealmService;

public class RunningDataFragment extends Fragment {

    private static final int DISTANCE_MAX = 25;
    private static final int DISTANCE_SEEKBAR_MAX = 250;
    private static final int TIME_MAX = 180;
    private static final int TIME_SEEKBAR_MAX = 180;

    @Inject
    Formatter formatter;
    @Inject
    MathHelper mathHelper;
    @Inject
    RunTypeHelper runTypeHelper;
    @Inject
    RealmService realmService;

    private String runType;
    private float roundedDistance;
    private int roundedTime;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RunLazyDroidApplication.getAppComponent().inject(this);
        this.runType = this.runTypeHelper.toLocalString(RunType.DEFAULT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running_data, container, false);

        final TextView distance = (TextView) view.findViewById(R.id.fragment_running_data__distance);
        distance.setText(this.formatter.asKilometers(0f));
        final SeekBar distanceBar = (SeekBar) view.findViewById(R.id.fragment_running_data__distance_bar);
        distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean userChange) {
                roundedDistance = mathHelper.round((float) progress / (float) DISTANCE_SEEKBAR_MAX * DISTANCE_MAX, 1);
                distance.setText(String.format(Locale.getDefault(), "%.1f km", roundedDistance));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //NOOP
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //NOOP
            }
        });

        final TextView time = (TextView) view.findViewById(R.id.fragment_running_data__time);
        time.setText(this.formatter.inMinutes(0));
        final SeekBar timeBar = (SeekBar) view.findViewById(R.id.fragment_running_data__time_bar);
        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean userChange) {
                roundedTime = (int) (progress / (float) TIME_SEEKBAR_MAX * TIME_MAX);
                time.setText(formatter.inMinutes(roundedTime));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //NOOP
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //NOOP
            }
        });

        final Spinner runTypeSpinner = (Spinner) view.findViewById(R.id.fragment_running_data__run_type_spinner);
        List<String> runTypes = new ArrayList<>();
        for (RunType runType : EnumSet.allOf(RunType.class)){
            if (runType == RunType.MAP_RUN){
                continue;
            }
            runTypes.add(this.runTypeHelper.toLocalString(runType));
        }
        ArrayAdapter<String> runTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, runTypes);
        runTypeSpinner.setAdapter(runTypeAdapter);
        runTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                runType = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                runType = runTypeHelper.toLocalString(RunType.DEFAULT);
            }
        });

        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.fragment_running_data__submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RunEntry runEntry = new RunEntry();
                Date now = new Date();
                runEntry.setId(now.getTime());
                runEntry.setCreated(now);
                runEntry.setDistance(roundedDistance);
                runEntry.setTime(roundedTime);
                runEntry.setType(runTypeHelper.localStringToName(runType));
                realmService.saveOrUpdate(runEntry);
                Toast.makeText(getActivity(), R.string.entry_added, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
