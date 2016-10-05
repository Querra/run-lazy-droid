package de.querra.mobile.runlazydroid;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.di.AppComponent;
import de.querra.mobile.runlazydroid.di.AppModule;
import de.querra.mobile.runlazydroid.di.DaggerAppComponent;
import de.querra.mobile.runlazydroid.helper.PreferencesHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RunLazyDroidApplication extends Application {

    private static AppComponent appComponent;

    @Inject
    PreferencesHelper preferencesHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        appComponent.inject(this);

        JodaTimeAndroid.init(getApplicationContext());

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("run_lazy_droid.realm")
                .schemaVersion(0)
                //.migration(new RunLazyDroidMigration())
                .deleteRealmIfMigrationNeeded() // TODO: remove before release
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        this.preferencesHelper.setDefaultValues(false);
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }
}
