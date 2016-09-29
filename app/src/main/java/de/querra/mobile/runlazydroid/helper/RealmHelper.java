package de.querra.mobile.runlazydroid.helper;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmHelper {
    public static void saveOrUpdate(RealmObject realmObject){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(realmObject);

        realm.commitTransaction();
    }
}
