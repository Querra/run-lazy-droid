package de.querra.mobile.runlazydroid;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import de.querra.mobile.runlazydroid.helper.PreferencesHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RunLazyDroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(getApplicationContext());

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("run_lazy_droid.realm")
                .schemaVersion(0)
                //.migration(new RunLazyDroidMigration())
                .deleteRealmIfMigrationNeeded() // TODO: remove before release
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);



        PreferencesHelper.setDefaultValues(getApplicationContext(), false);
    }
}
