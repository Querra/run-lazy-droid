package de.querra.mobile.runlazydroid.di;

import javax.inject.Singleton;

import dagger.Component;
import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.activities.BaseNavigationActivity;
import de.querra.mobile.runlazydroid.activities.MapHandler;
import de.querra.mobile.runlazydroid.adapters.TimelineCardAdapter;
import de.querra.mobile.runlazydroid.fragments.OverviewFragment;
import de.querra.mobile.runlazydroid.fragments.PenaltyFragment;
import de.querra.mobile.runlazydroid.fragments.PreferencesFragment;
import de.querra.mobile.runlazydroid.fragments.RunningDataFragment;
import de.querra.mobile.runlazydroid.fragments.StatisticsFragment;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.MathHelper;
import de.querra.mobile.runlazydroid.helper.RunTypeHelper;
import de.querra.mobile.runlazydroid.services.internal.ImageServiceImplementation;
import de.querra.mobile.runlazydroid.services.internal.PreferencesServiceImplementation;
import de.querra.mobile.runlazydroid.services.internal.RealmServiceImplementation;
import de.querra.mobile.runlazydroid.widgets.DeleteEntryDialogBuilder;

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

    void inject(PreferencesFragment fragment);

    // Adapters
    void inject(TimelineCardAdapter adapter);

    // Helper
    void inject(Formatter formatter);

    void inject(MathHelper helper);

    void inject(RunTypeHelper helper);

    // Services
    void inject(PreferencesServiceImplementation service);

    void inject(RealmServiceImplementation service);

    void inject(ImageServiceImplementation service);

    // Special
    void inject(MapHandler mapHandler);

    void inject(DeleteEntryDialogBuilder deleteEntryDialogBuilder);
}
