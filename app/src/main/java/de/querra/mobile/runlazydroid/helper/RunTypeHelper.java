package de.querra.mobile.runlazydroid.helper;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.entities.RunType;

public class RunTypeHelper {

    @Inject
    Resources resources;

    public RunTypeHelper(){
        RunLazyDroidApplication.getAppComponent().inject(this);
    }

    public String toLocalString(RunType runType){
        switch (runType){
            case MAP_RUN:
                return this.resources.getString(R.string.map_run);
            case TRACK:
                return this.resources.getString(R.string.track);
            case TREADMILL:
                return this.resources.getString(R.string.treadmill);
            case DEFAULT:
                return this.resources.getString(R.string.unknown);
        }
        return null;
    }

    public Drawable getDrawable(RunType runType){
        switch (runType){
            case TRACK:
                return this.resources.getDrawable(R.drawable.ic_road);
            case TREADMILL:
                return this.resources.getDrawable(R.drawable.ic_treadmill);
            case DEFAULT:
                return this.resources.getDrawable(R.drawable.ic_questionmark);
        }
        return null;
    }

    public String localStringToName(String localRunType){
        if (localRunType.equals(this.resources.getString(R.string.track))){
            return RunType.TRACK.getName();
        }
        if (localRunType.equals(this.resources.getString(R.string.treadmill))){
            return RunType.TREADMILL.getName();
        }
        return RunType.DEFAULT.getName();
    }
}
