package de.querra.mobile.rlblib.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.Profile;

public class User implements Parcelable {
    String firstName;
    String lastName;
    String id;

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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.firstName);
        parcel.writeString(this.lastName);
        parcel.writeString(this.id);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // "De-parcel object
    public User(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.id = in.readString();
    }
}
