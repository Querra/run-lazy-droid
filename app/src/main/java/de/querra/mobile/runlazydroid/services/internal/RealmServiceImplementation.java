package de.querra.mobile.runlazydroid.services.internal;

import android.support.annotation.NonNull;

import java.util.Date;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.data.entities.Target;
import de.querra.mobile.runlazydroid.helper.DateHelper;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmServiceImplementation implements RealmService {

    @Inject
    DateHelper dateHelper;
    @Inject
    PreferencesService preferencesService;
    @Inject
    Realm realm;

    public RealmServiceImplementation() {
        RunLazyDroidApplication.getAppComponent().inject(this);
    }

    @Override
    public float getWeekTargetWithPenalties() {
        return getLastTarget().getBaseDistance() + getTotalPenaltyDistance();
    }

    @Override
    public float getDistanceRun() {
        RealmResults<RunEntry> thisWeeksEntries = getWeekRunEntries();
        float distanceRun = 0f;
        for (RunEntry runEntry : thisWeeksEntries) {
            distanceRun += runEntry.getDistance();
        }
        return distanceRun;
    }


    @NonNull
    private RealmResults<RunEntry> getWeekRunEntries() {
        Date from = this.dateHelper.getLastSunday().toDate();
        Date to = this.dateHelper.getNextSunday().toDate();
        return this.realm.where(RunEntry.class).between(RunEntry.CREATED_FIELD, from, to).findAll();
    }

    @Override
    public float getTotalPenaltyDistance() {
        RealmResults<Penalty> penalties = getWeekPenalties();
        float penaltyDistance = 0f;
        for (Penalty penalty : penalties) {
            penaltyDistance += penalty.getDistance();
        }
        return penaltyDistance;
    }

    @NonNull
    private RealmResults<Penalty> getWeekPenalties() {
        Date from = this.dateHelper.getLastSunday().toDate();
        Date to = this.dateHelper.getNextSunday().toDate();
        return this.realm.where(Penalty.class).between(RunEntry.CREATED_FIELD, from, to).findAll();
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
    public Target getLastTarget() {
        RealmResults<Target> targets = this.realm.where(Target.class).findAllSorted(Target.CREATED_FIELD, Sort.ASCENDING);
        if (targets.size() == 0) {
            return null;
        }
        return targets.last();
    }

    @Override
    public float getAllTimeDistance() {
        RealmResults<RunEntry> entries = this.realm.where(RunEntry.class).findAll();
        float distanceRun = 0f;
        for (RunEntry runEntry : entries) {
            distanceRun += runEntry.getDistance();
        }
        return distanceRun;
    }

    @Override
    public int getAllTimePenalties() {
        int penalties = 0;
        for (Penalty ignored : this.realm.where(Penalty.class).findAll()) {
            penalties++;
        }
        return penalties;
    }

    @Override
    public float getAllTimePenaltyDistance() {
        float penaltyDistance = 0f;
        for (Penalty penalty : this.realm.where(Penalty.class).findAll()) {
            penaltyDistance += penalty.getDistance();
        }
        return penaltyDistance;
    }

    @Override
    public int getAllTimeRunTime() {
        RealmResults<RunEntry> entries = this.realm.where(RunEntry.class).findAll();
        int time = 0;
        for (RunEntry runEntry : entries) {
            time += runEntry.getTime();
        }
        return time;
    }

    @Override
    public boolean targetNeedsUpdate() {
        Target last = getLastTarget();
        return last == null || last.getEndDate().before(new Date());
    }

    @Override
    public boolean newTargetNeedsCopy() {
        Target last = getLastTarget();
        return last != null && !last.isAchieved();
    }

    @Override
    public int getAllTimeTargetsAchieved() {
        return this.realm.where(Target.class).equalTo(Target.ACHIEVED_FIELD, true).findAll().size();
    }

    @Override
    public float getAverageSpeed() {
        return getAllTimeDistance() / getAllTimeRunTime();
    }

    @Override
    public float getAverageWeekSpeed() {
        return getDistanceRun() / getWeekRunTime();
    }

    public float getWeekRunTime() {
        int time = 0;
        for (RunEntry entry : getWeekRunEntries()) {
            time += entry.getTime();
        }
        return (float) time;
    }

}