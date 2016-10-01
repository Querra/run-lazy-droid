package de.querra.mobile.runlazydroid.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.querra.mobile.runlazydroid.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.black_alpha, null));
        }
        return view;
    }
}
