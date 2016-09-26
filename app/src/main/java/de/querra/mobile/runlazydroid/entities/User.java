package de.querra.mobile.runlazydroid.entities;

import com.facebook.Profile;

import java.util.Locale;

public class User {
    private String firstName;
    private String lastName;
    private String id;

    public User(Profile profile) {
        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
        this.id = profile.getId();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName(){
        return String.format(Locale.GERMANY, "%s %s", this.firstName, this.lastName);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
