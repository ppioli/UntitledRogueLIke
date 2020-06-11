package com.ppioli.url.actors.actions;

public class InstantActionStep implements IActionStep {
    private Runnable action;

    public InstantActionStep( Runnable action ) {
        this.action = action;
    }

    @Override
    public float execute( float delta ) {
        action.run();
        return delta;
    }

    @Override
    public void start() {

    }
}
