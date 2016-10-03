package de.querra.mobile.runlazydroid.data;

import android.content.Context;

import java.util.Date;

import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.helper.DateHelper;
import io.realm.Realm;
import io.realm.RealmResults;

import static de.querra.mobile.runlazydroid.helper.PreferencesHelper.getWeekTarget;

public class RealmCalculator {

    public static float getWeekTargetWithPenalties(Context context){
        return getWeekTarget(context) + getTotalPenaltyDistance();
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

    public static float getTotalPenaltyDistance(){
        Date from = DateHelper.getLastSunday().toDate();
        Date to = DateHelper.getNextSunday().toDate();
        Realm realm = Realm.getDefaultInstance();
        float penaltyDistance = 0f;
        for(Penalty penalty : realm.where(Penalty.class).between(RunEntry.CREATED_FIELD, from, to).findAll()){
            penaltyDistance += penalty.getDistance();
        }
        return penaltyDistance;
    }

    public static int getTotalPenalties(){
        Date from = DateHelper.getLastSunday().toDate();
        Date to = DateHelper.getNextSunday().toDate();
        Realm realm = Realm.getDefaultInstance();
        int penalties = 0;
        for(Penalty ignored : realm.where(Penalty.class).between(RunEntry.CREATED_FIELD, from, to).findAll()){
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

    public static float getAllTimeDistance(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> entries = realm.where(RunEntry.class).findAll();
        float distanceRun = 0f;
        for (RunEntry runEntry : entries){
            distanceRun += runEntry.getDistance();
        }
        return distanceRun;
    }

    public static int getAllTimePenalties(){
        Realm realm = Realm.getDefaultInstance();
        int penalties = 0;
        for(Penalty ignored : realm.where(Penalty.class).findAll()){
            penalties++;
        }
        return penalties;
    }

    public static float getAllTimePenaltyDistance(){
        Realm realm = Realm.getDefaultInstance();
        float penaltyDistance = 0f;
        for(Penalty penalty : realm.where(Penalty.class).findAll()){
            penaltyDistance += penalty.getDistance();
        }
        return penaltyDistance;
    }

    public static int getAllTimeRunTime(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> entries = realm.where(RunEntry.class).findAll();
        int time = 0;
        for (RunEntry runEntry : entries){
            time += runEntry.getTime();
        }
        return time;
    }

}
