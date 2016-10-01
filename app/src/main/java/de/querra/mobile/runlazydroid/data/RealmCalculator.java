package de.querra.mobile.runlazydroid.data;

import android.content.Context;

import java.util.Date;

import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.helper.DateHelper;
import de.querra.mobile.runlazydroid.helper.PreferencesHelper;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmCalculator {

    public static float getWeekTargetWithPenalties(Context context){
        return PreferencesHelper.getWeekTarget(context) + getTotalPenaltyDistance(context);
    }

    public static float getDistanceRun(){
        Date from = DateHelper.getLastSunday().toDate();
        Date to = DateHelper.getNextSunday().toDate();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> thisWeeksEntries = realm.where(RunEntry.class).between(RunEntry.CREATED_FIELD, from, to).findAll();
        float distanceRun = 0f;
        for (RunEntry runEntry : thisWeeksEntries){
            distanceRun += runEntry.getDistance();
        }
        return distanceRun;
    }

    public static float getTotalPenaltyDistance(Context context){
        return PreferencesHelper.getPenaltyDistance(context) * getTotalPenalties();
    }

    public static int getTotalPenalties(){
        Date from = DateHelper.getLastSunday().toDate();
        Date to = DateHelper.getNextSunday().toDate();
        Realm realm = Realm.getDefaultInstance();
        int penalties = 0;
        for(Penalty penalty : realm.where(Penalty.class).between(RunEntry.CREATED_FIELD, from, to).findAll()){
            penalties++;
        }
        return penalties;
    }

    public static int getProgress(Context context){
        return (int) (getDistanceRun()/getWeekTargetWithPenalties(context)*100f);
    }

    public static float getDistanceLeft(Context context){
        return getWeekTargetWithPenalties(context) - getDistanceRun();
    }
}
