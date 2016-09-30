package de.querra.mobile.runlazydroid.data.entities.finder;

import de.querra.mobile.runlazydroid.data.RealmInterface;
import io.realm.Realm;

public class RealmOperator {
    public static void delete(RealmInterface realmObject){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(realmObject.getRealmClass()).equalTo(realmObject.getIdField(), realmObject.getRealmId()).findAll().deleteFirstFromRealm();
        realm.commitTransaction();
    }
}
