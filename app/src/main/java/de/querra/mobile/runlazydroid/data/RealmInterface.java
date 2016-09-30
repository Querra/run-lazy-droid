package de.querra.mobile.runlazydroid.data;

import java.util.Date;

public interface RealmInterface {
    Date getSortDate();

    long getRealmId();

    String getIdField();

    Class getRealmClass();
}
