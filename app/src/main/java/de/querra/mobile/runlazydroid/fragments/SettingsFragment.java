package de.querra.mobile.runlazydroid.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import de.querra.mobile.runlazydroid.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
