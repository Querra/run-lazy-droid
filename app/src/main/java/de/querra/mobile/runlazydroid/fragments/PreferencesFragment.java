package de.querra.mobile.runlazydroid.fragments;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.querra.mobile.runlazydroid.R;

public class PreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        String entries[] = {"1 km", "2 km", "3 km"};
        String values[] = {"1","2","3"};
        ListPreference listPreference = (ListPreference) ((PreferenceCategory) getPreferenceScreen().getPreference(1)).getPreference(0);
        listPreference.setEntries(entries);
        listPreference.setEntryValues(values);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white_alpha, null));
        }
        return view;
    }
}
