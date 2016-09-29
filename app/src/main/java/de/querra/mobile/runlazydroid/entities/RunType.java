package de.querra.mobile.runlazydroid.entities;

public enum RunType {

    TRACK(RunTypeNames.TRACK_NAME),
    TREADMILL(RunTypeNames.TREADMILL_NAME),
    DEFAULT(RunTypeNames.DEFAULT_NAME);

    private final String name;

    RunType(String name){
        this.name = name;
    }

    public static RunType fromString(String runType){
        switch (runType){
            case RunTypeNames.TRACK_NAME:
                return TRACK;
            case RunTypeNames.TREADMILL_NAME:
                return TREADMILL;
            default:
                return DEFAULT;
        }
    }

    public String getName(){
        return this.name;
    }
}
