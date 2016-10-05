package de.querra.mobile.runlazydroid.di;

import javax.inject.Singleton;

import dagger.Component;
import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.activities.BaseNavigationActivity;
import de.querra.mobile.runlazydroid.activities.MapHandler;
import de.querra.mobile.runlazydroid.adapters.StatisticsCardAdapter;
import de.querra.mobile.runlazydroid.fragments.PenaltyFragment;
import de.querra.mobile.runlazydroid.helper.PreferencesHelper;
import de.querra.mobile.runlazydroid.helper.RealmCalculator;
import de.querra.mobile.runlazydroid.fragments.OverviewFragment;
import de.querra.mobile.runlazydroid.fragments.RunningDataFragment;
import de.querra.mobile.runlazydroid.fragments.StatisticsFragment;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.ImageHelper;
import de.querra.mobile.runlazydroid.helper.MathHelper;
import de.querra.mobile.runlazydroid.helper.RunTypeHelper;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    // Application
    void inject(RunLazyDroidApplication application);

    // Activities
    void inject(BaseNavigationActivity activity);

    // Fragments
    void inject(OverviewFragment fragment);

    void inject(RunningDataFragment fragment);

    void inject(PenaltyFragment fragment);

    void inject(StatisticsFragment fragment);

    // Adapters
    void inject(StatisticsCardAdapter adapter);

    // Helper
    void inject(Formatter formatter);

    void inject(RealmCalculator calculator);

    void inject(ImageHelper helper);

    void inject(MathHelper helper);

    void inject(PreferencesHelper helper);

    void inject(RunTypeHelper helper);

    // Special
    void inject(MapHandler mapHandler);
}
