package de.querra.mobile.runlazydroid.entities;

public enum RunType {

    MAP_RUN(RunTypeNames.MAP_RUN_NAME),
    TRACK(RunTypeNames.TRACK_NAME),
    TREADMILL(RunTypeNames.TREADMILL_NAME),
    DEFAULT(RunTypeNames.DEFAULT_NAME);

    private final String name;

    RunType(String name){
        this.name = name;
    }

    public static RunType fromString(String runType){
        switch (runType){
            case RunTypeNames.MAP_RUN_NAME:
                return MAP_RUN;
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
