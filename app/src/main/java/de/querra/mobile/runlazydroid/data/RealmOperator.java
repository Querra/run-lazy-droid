package de.querra.mobile.runlazydroid.data;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmOperator {
    public static void delete(RealmInterface realmObject){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(realmObject.getRealmClass()).equalTo(realmObject.getIdField(), realmObject.getRealmId()).findAll().deleteFirstFromRealm();
        realm.commitTransaction();
    }

    public static void saveOrUpdate(RealmObject realmObject){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmObject);
        realm.commitTransaction();
    }
}
