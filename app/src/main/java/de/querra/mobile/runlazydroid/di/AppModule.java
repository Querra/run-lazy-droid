package de.querra.mobile.runlazydroid.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.querra.mobile.runlazydroid.helper.DateHelper;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.MathHelper;
import de.querra.mobile.runlazydroid.helper.RunTypeHelper;
import de.querra.mobile.runlazydroid.services.ImageService;
import de.querra.mobile.runlazydroid.services.ImageServiceImplementation;
import de.querra.mobile.runlazydroid.services.PreferencesService;
import de.querra.mobile.runlazydroid.services.PreferencesServiceImplementation;
import de.querra.mobile.runlazydroid.services.RealmService;
import de.querra.mobile.runlazydroid.services.RealmServiceImplementation;
import io.realm.Realm;

@Module
public class AppModule {

    private static final String SAVED_PREFERENCES = "run_lazy_droid_saved_preferences";
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return this.application;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return this.application.getApplicationContext();
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return this.application.getResources();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return this.application.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    Formatter provideFormatter() {
        return new Formatter();
    }

    @Provides
    @Singleton
    DateHelper provideDateHelper() {
        return new DateHelper();
    }


    @Provides
    @Singleton
    MathHelper provideMathHelper() {
        return new MathHelper();
    }

    @Provides
    @Singleton
    RealmService provideRealmService() {
        return new RealmServiceImplementation();
    }

    @Provides
    @Singleton
    ImageService provideImageService() {
        return new ImageServiceImplementation();
    }

    @Provides
    @Singleton
    PreferencesService providePreferencesService() {
        return new PreferencesServiceImplementation();
    }

    @Provides
    @Singleton
    RunTypeHelper provideRunTypeHelper() {
        return new RunTypeHelper();
    }

    @Provides
    @Singleton
    Realm provideRealm(){
        return Realm.getDefaultInstance();
    }

}
