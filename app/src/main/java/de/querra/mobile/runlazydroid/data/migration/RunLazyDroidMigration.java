package de.querra.mobile.runlazydroid.data.migration;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class RunLazyDroidMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        if (oldVersion == newVersion){
            return;
        }

        RealmSchema realmSchema = realm.getSchema();

        if (oldVersion == 0){
            // Do some migration stuff
            oldVersion++;
        }
    }
}
