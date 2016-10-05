package de.querra.mobile.runlazydroid.services;

import java.util.Date;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.helper.DateHelper;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmServiceImplementation implements RealmService {

    @Inject
    DateHelper dateHelper;
    @Inject
    PreferencesService preferencesService;

    public RealmServiceImplementation() {
        RunLazyDroidApplication.getAppComponent().inject(this);
    }

    @Override
    public float getWeekTargetWithPenalties() {
        return this.preferencesService.getWeekTarget() + getTotalPenaltyDistance();
    }

    @Override
    public float getDistanceRun() {
        Date from = this.dateHelper.getLastSunday().toDate();
        Date to = this.dateHelper.getNextSunday().toDate();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> thisWeeksEntries = realm.where(RunEntry.class).between(RunEntry.CREATED_FIELD, from, to).findAll();
        float distanceRun = 0f;
        for (RunEntry runEntry : thisWeeksEntries) {
            distanceRun += runEntry.getDistance();
        }
        return distanceRun;
    }

    @Override
    public float getTotalPenaltyDistance() {
        Date from = this.dateHelper.getLastSunday().toDate();
        Date to = this.dateHelper.getNextSunday().toDate();
        Realm realm = Realm.getDefaultInstance();
        float penaltyDistance = 0f;
        for (Penalty penalty : realm.where(Penalty.class).between(RunEntry.CREATED_FIELD, from, to).findAll()) {
            penaltyDistance += penalty.getDistance();
        }
        return penaltyDistance;
    }

    @Override
    public int getProgress() {
        return (int) (getDistanceRun() / getWeekTargetWithPenalties() * 100f);
    }

    @Override
    public float getDistanceLeft() {
        return getWeekTargetWithPenalties() - getDistanceRun();
    }

    @Override
    public float getAllTimeDistance() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> entries = realm.where(RunEntry.class).findAll();
        float distanceRun = 0f;
        for (RunEntry runEntry : entries) {
            distanceRun += runEntry.getDistance();
        }
        return distanceRun;
    }

    @Override
    public int getAllTimePenalties() {
        Realm realm = Realm.getDefaultInstance();
        int penalties = 0;
        for (Penalty ignored : realm.where(Penalty.class).findAll()) {
            penalties++;
        }
        return penalties;
    }

    @Override
    public float getAllTimePenaltyDistance() {
        Realm realm = Realm.getDefaultInstance();
        float penaltyDistance = 0f;
        for (Penalty penalty : realm.where(Penalty.class).findAll()) {
            penaltyDistance += penalty.getDistance();
        }
        return penaltyDistance;
    }

    @Override
    public int getAllTimeRunTime() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RunEntry> entries = realm.where(RunEntry.class).findAll();
        int time = 0;
        for (RunEntry runEntry : entries) {
            time += runEntry.getTime();
        }
        return time;
    }

}
