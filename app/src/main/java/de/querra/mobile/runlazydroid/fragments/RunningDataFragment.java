package de.querra.mobile.runlazydroid.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Date;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.helper.RealmHelper;

public class RunningDataFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running_data, container, false);

        final EditText distance = (EditText) view.findViewById(R.id.fragment_running_data__distance);
        final EditText time = (EditText) view.findViewById(R.id.fragment_running_data__time);
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
