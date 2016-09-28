package de.querra.mobile.runlazydroid;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

import de.querra.mobile.runlazydroid.helper.PreferencesHelper;

public class RunLazyDroidApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        RunLazyDroidApplication.appContext = getApplicationContext();
        JodaTimeAndroid.init(getApplicationContext());
        PreferencesHelper.setDefaultValues(false);
    }

    public static Context getContext(){
        return appContext;
    }

}
