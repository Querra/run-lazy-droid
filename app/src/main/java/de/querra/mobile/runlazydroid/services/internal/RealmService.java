package de.querra.mobile.runlazydroid.services.internal;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import de.querra.mobile.rlblib.helper.DateHelper;
import de.querra.mobile.runlazydroid.data.RealmInterface;
import de.querra.mobile.runlazydroid.data.entities.Penalty;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.data.entities.Target;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmService {

    private static RealmService INSTANCE;

    Realm realm = Realm.getDefaultInstance();

    private RealmService() {
        // no external instantiation of singleton
    }

    public void delete(RealmInterface realmObject){
        this.realm.beginTransaction();
        this.realm.where(realmObject.getRealmClass()).equalTo(realmObject.getIdField(), realmObject.getRealmId()).findAll().deleteFirstFromRealm();
        this.realm.commitTransaction();
    }

    public void saveOrUpdate(RealmObject realmObject){
        this.realm.beginTransaction();
        this.realm.copyToRealmOrUpdate(realmObject);
        this.realm.commitTransaction();
    }

    public int getAllTimeTargets() {
        return this.realm.where(Target.class).findAll().size();
    }

    public float getWeekTargetWithPenalties() {
        return getLastTarget().getBaseDistance() + getTotalPenaltyDistance();
    }

    public float getDistanceRun() {
        RealmResults<RunEntry> thisWeeksEntries = getWeekRunEntries();
        return calculateRunDistance(thisWeeksEntries);
    }

    @NonNull
    private RealmResults<RunEntry> getWeekRunEntries() {
        Date from = DateHelper.getLastSunday().toDate();
        Date to = DateHelper.getNextSunday().toDate();
        return this.realm.where(RunEntry.class).between(RunEntry.CREATED_FIELD, from, to).findAll();
    }

    public float getTotalPenaltyDistance() {
        RealmResults<Penalty> penalties = getWeekPenalties();
        return calculatePenaltyDistance(penalties);
    }

    @NonNull
    private RealmResults<Penalty> getWeekPenalties() {
        Date from = DateHelper.getLastSunday().toDate();
        Date to = DateHelper.getNextSunday().toDate();
        return this.realm.where(Penalty.class).between(RunEntry.CREATED_FIELD, from, to).findAll();
    }

    public int getProgress() {
        return (int) (getDistanceRun() / getWeekTargetWithPenalties() * 100f);
    }

    public float getDistanceLeft() {
        return getWeekTargetWithPenalties() - getDistanceRun();
    }

    public Target getLastTarget() {
        RealmResults<Target> targets = this.realm.where(Target.class).findAllSorted(Target.CREATED_FIELD, Sort.ASCENDING);
        if (targets.size() == 0) {
            return null;
        }
        return targets.last();
    }

    public float getAllTimeDistance() {
        RealmResults<RunEntry> entries = this.realm.where(RunEntry.class).findAll();
        return calculateRunDistance(entries);
    }

    public int getAllTimePenalties() {
        int penalties = 0;
        for (Penalty ignored : this.realm.where(Penalty.class).findAll()) {
            penalties++;
        }
        return penalties;
    }

    public float getAllTimePenaltyDistance() {
        RealmResults<Penalty> allPenalties = this.realm.where(Penalty.class).findAll();
        return calculatePenaltyDistance(allPenalties);
    }

    public int getAllTimeRunTime() {
        RealmResults<RunEntry> entries = this.realm.where(RunEntry.class).findAll();
        return calculateRunTime(entries);
    }

    public boolean targetNeedsUpdate() {
        Target last = getLastTarget();
        return last == null || last.getEndDate().before(new Date());
    }

    public boolean newTargetNeedsCopy() {
        Target last = getLastTarget();
        return last != null && !last.isAchieved();
    }

    public int getAllTimeTargetsAchieved() {
        return this.realm.where(Target.class).equalTo(Target.ACHIEVED_FIELD, true).findAll().size();
    }

    public float getAverageSpeed() {
        return getAllTimeDistance() / (float)getAllTimeRunTime();
    }

    public float getAverageWeekSpeed() {
        return getDistanceRun() / getWeekRunTime();
    }

    public float getWeekRunTime() {
        return (float) calculateRunTime(getWeekRunEntries());
    }

    public void handleTargetAchieved() {
        Target target = getLastTarget();
        if (target == null){
            return;
        }
        if (targetAchieved(target)){
            this.realm.beginTransaction();
            target.setAchieved(true);
            this.realm.commitTransaction();
        }
    }

    private boolean targetAchieved(Target target) {
        float targetBaseDistance = target.getBaseDistance();
        RealmResults<Penalty> penalties = this.realm.where(Penalty.class).between(Penalty.CREATED_FIELD, target.getStartDate(), target.getEndDate()).findAll();
        float penaltyDistance = calculatePenaltyDistance(penalties);
        RealmResults<RunEntry> targetEntries = this.realm.where(RunEntry.class).between(RunEntry.CREATED_FIELD, target.getStartDate(), target.getEndDate()).findAll();
        float runDistance = calculateRunDistance(targetEntries);
        return (targetBaseDistance + penaltyDistance) < runDistance;
    }


    private float calculateRunDistance(List<RunEntry> runEntries){
        float runDistance = 0f;
        for (RunEntry runEntry : runEntries){
            runDistance += runEntry.getDistance();
        }
        return runDistance;
    }

    private float calculatePenaltyDistance(List<Penalty> penalties){
        float penaltyDistance = 0f;
        for (Penalty penalty : penalties){
            penaltyDistance += penalty.getDistance();
        }
        return penaltyDistance;
    }

    private int calculateRunTime(List<RunEntry> entries) {
        int time = 0;
        for (RunEntry runEntry : entries) {
            time += runEntry.getTime();
        }
        return time;
    }

    public static RealmService getInstance(){
        if (INSTANCE == null){
            synchronized (RealmService.class){
                if (INSTANCE == null){
                    INSTANCE = new RealmService();
                }
            }
        }
        return INSTANCE;
    }
}
