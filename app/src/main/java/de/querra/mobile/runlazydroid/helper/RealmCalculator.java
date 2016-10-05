package de.querra.mobile.runlazydroid.helper;

import java.util.Date;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmCalculator {

    @Inject
    DateHelper dateHelper;
    @Inject
    PreferencesHelper preferencesHelper;

    public RealmCalculator(){
        RunLazyDroidApplication.getAppComponent().inject(this);
    }

    private float getWeekTargetWithPenalties(){
        return this.preferencesHelper.getWeekTarget() + getTotalPenaltyDistance();
    }

    public float getDistanceRun(){
        Date from = this.dateHelper.getLastSunday().toDate();
        Date to = this.dateHelper.getNextSunday().toDate();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> thisWeeksEntries = realm.where(RunEntry.class).between(RunEntry.CREATED_FIELD, from, to).findAll();
        float distanceRun = 0f;
        for (RunEntry runEntry : thisWeeksEntries){
            distanceRun += runEntry.getDistance();
        }
        return distanceRun;
    }

    public float getTotalPenaltyDistance(){
        Date from = this.dateHelper.getLastSunday().toDate();
        Date to = this.dateHelper.getNextSunday().toDate();
        Realm realm = Realm.getDefaultInstance();
        float penaltyDistance = 0f;
        for(Penalty penalty : realm.where(Penalty.class).between(RunEntry.CREATED_FIELD, from, to).findAll()){
            penaltyDistance += penalty.getDistance();
        }
        return penaltyDistance;
    }

    public int getProgress(){
        return (int) (getDistanceRun()/getWeekTargetWithPenalties()*100f);
    }

    public float getDistanceLeft(){
        return getWeekTargetWithPenalties() - getDistanceRun();
    }

    public float getAllTimeDistance(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> entries = realm.where(RunEntry.class).findAll();
        float distanceRun = 0f;
        for (RunEntry runEntry : entries){
            distanceRun += runEntry.getDistance();
        }
        return distanceRun;
    }

    public int getAllTimePenalties(){
        Realm realm = Realm.getDefaultInstance();
        int penalties = 0;
        for(Penalty ignored : realm.where(Penalty.class).findAll()){
            penalties++;
        }
        return penalties;
    }

    public float getAllTimePenaltyDistance(){
        Realm realm = Realm.getDefaultInstance();
        float penaltyDistance = 0f;
        for(Penalty penalty : realm.where(Penalty.class).findAll()){
            penaltyDistance += penalty.getDistance();
        }
        return penaltyDistance;
    }

    public int getAllTimeRunTime(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> entries = realm.where(RunEntry.class).findAll();
        int time = 0;
        for (RunEntry runEntry : entries){
            time += runEntry.getTime();
        }
        return time;
    }

}
