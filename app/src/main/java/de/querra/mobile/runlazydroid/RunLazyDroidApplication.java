package de.querra.mobile.runlazydroid;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import de.querra.mobile.runlazydroid.helper.PreferencesHelper;

public class RunLazyDroidApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        RunLazyDroidApplication.appContext = getApplicationContext();
        PreferencesHelper.setDefaultValues(false);
    }

    public static Context getContext(){
        return appContext;
    }

}
