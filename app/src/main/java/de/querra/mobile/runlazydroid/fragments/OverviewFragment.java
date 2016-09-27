package de.querra.mobile.runlazydroid.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.PreferencesHelper;

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
        /*
        PreferencesHelper.setDistanceRun(10.5f);
        PreferencesHelper.setPenalties(2);
        PreferencesHelper.setStartDate(new Date(1474927200000L));
        PreferencesHelper.setWeekGoal(15f);
*/

        TextView distance = (TextView) view.findViewById(R.id.fragment_overview__distance);
        distance.setText(Formatter.asKilometers(PreferencesHelper.getDistanceRun()));
        TextView start = (TextView) view.findViewById(R.id.fragment_overview__start);
        start.setText(Formatter.dateToString(PreferencesHelper.getStartDate()));
        TextView penalty = (TextView) view.findViewById(R.id.fragment_overview__penalty);
        penalty.setText(Formatter.penaltyToKilometers(PreferencesHelper.getPenalties()));
        TextView goal = (TextView) view.findViewById(R.id.fragment_overview__goal);
        goal.setText(Formatter.asKilometers(PreferencesHelper.getWeekGoal()));

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
