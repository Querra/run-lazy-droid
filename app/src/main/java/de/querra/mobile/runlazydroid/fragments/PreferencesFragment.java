package de.querra.mobile.runlazydroid.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.entities.Target;
import de.querra.mobile.runlazydroid.services.internal.PreferencesService;
import de.querra.mobile.runlazydroid.services.internal.RealmService;
import io.realm.Realm;

public class PreferencesFragment extends PreferenceFragmentCompat {

    PreferencesService preferencesService;
    RealmService realmService;
    Realm realm = Realm.getDefaultInstance();

    private SharedPreferences.OnSharedPreferenceChangeListener preferencesListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(getString(R.string.preference__week_target))){
                Target lastTarget = realmService.getLastTarget();
                realm.beginTransaction();
                lastTarget.setBaseDistance(preferencesService.getWeekTarget(getActivity()));
                realm.commitTransaction();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.preferencesService = PreferencesService.getInstance();
        this.realmService = RealmService.getInstance();

        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setSharedPreferencesName(PreferencesService.SAVED_PREFERENCES);
        preferenceManager.setSharedPreferencesMode(Context.MODE_PRIVATE);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreatePreferencesFix(Bundle savedInstanceState, String rootKey) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white_alpha, null));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this.preferencesListener);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this.preferencesListener);
        super.onPause();
    }

}
