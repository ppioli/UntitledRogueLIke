package com.ppioli.url.actors.actions;

public abstract class Action implements IAction {

    private long startTime;
    private long duration;

    public abstract boolean execute( long time );


    public Action( long startTime, long duration ) {
        this.startTime = startTime;
        this.duration = duration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime( long startTime ) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration( long duration ) {
        this.duration = duration;
    }

    public long getFinishTime() {
        return this.startTime + this.duration;
    }
}
