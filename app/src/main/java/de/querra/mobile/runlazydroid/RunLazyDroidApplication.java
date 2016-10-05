package de.querra.mobile.runlazydroid;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.di.AppComponent;
import de.querra.mobile.runlazydroid.di.AppModule;
import de.querra.mobile.runlazydroid.di.DaggerAppComponent;
import de.querra.mobile.runlazydroid.services.PreferencesService;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RunLazyDroidApplication extends Application {

    private static AppComponent appComponent;

    @Inject
    PreferencesService preferencesService;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        JodaTimeAndroid.init(getApplicationContext());

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("run_lazy_droid.realm")
                .schemaVersion(0)
                //.migration(new RunLazyDroidMigration())
                .deleteRealmIfMigrationNeeded() // TODO: remove before release
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        appComponent.inject(this);

        this.preferencesService.setDefaultValues(false);
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }
}
