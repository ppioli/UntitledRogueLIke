package com.ppioli.url.actors.actions;

public class DelayActionStep implements IActionStep {
    private float delay;

    public DelayActionStep( float delay ) {
        this.delay = delay;
    }

    @Override
    public float execute( float delta ) {
        if( delta > delay ) {
            return delta - delay;
        }
        delay -= delta;
        return 0;
    }

    @Override
    public void start() {

    }
}
