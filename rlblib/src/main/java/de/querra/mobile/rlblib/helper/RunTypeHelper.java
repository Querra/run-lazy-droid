package de.querra.mobile.rlblib.helper;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import de.querra.mobile.rlblib.R;
import de.querra.mobile.rlblib.entities.RunType;

public class RunTypeHelper {

    public static String toLocalString(Resources resources, RunType runType){
        switch (runType){
            case MAP_RUN:
                return resources.getString(R.string.map_run);
            case TRACK:
                return resources.getString(R.string.track);
            case TREADMILL:
                return resources.getString(R.string.treadmill);
            case DEFAULT:
                return resources.getString(R.string.unknown);
        }
        return null;
    }

    public static Drawable getDrawable(Resources resources, RunType runType){
        switch (runType){
            case TRACK:
                return resources.getDrawable(R.drawable.ic_road);
            case TREADMILL:
                return resources.getDrawable(R.drawable.ic_treadmill);
            case DEFAULT:
                return resources.getDrawable(R.drawable.ic_questionmark);
        }
        return null;
    }

    public static String localStringToName(Resources resources, String localRunType){
        if (localRunType.equals(resources.getString(R.string.track))){
            return RunType.TRACK.getName();
        }
        if (localRunType.equals(resources.getString(R.string.treadmill))){
            return RunType.TREADMILL.getName();
        }
        return RunType.DEFAULT.getName();
    }
}
