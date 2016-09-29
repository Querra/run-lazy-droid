package de.querra.mobile.runlazydroid.helper;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.entities.RunType;

public class RunTypeHelper {

    public static String toLocalString(RunType runType, Resources resources){
        switch (runType){
            case TRACK:
                return resources.getString(R.string.track);
            case TREADMILL:
                return resources.getString(R.string.treadmill);
            case DEFAULT:
                return resources.getString(R.string.unknown);
        }
        return null;
    }

    public static Drawable getDrawable(RunType runType, Resources resources){
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

    public static String localStringToName(String localRunType, Resources resources){
        if (localRunType == resources.getString(R.string.track)){
            return RunType.TRACK.getName();
        }
        if (localRunType == resources.getString(R.string.treadmill)){
            return RunType.TREADMILL.getName();
        }
        return RunType.DEFAULT.getName();
    }
}
