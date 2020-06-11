package com.ppioli.url.actors.actions;

public abstract class ActionFrame {
    public float duration;
    public Runnable action;
    public abstract boolean execute();
}
