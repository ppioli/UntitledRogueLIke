package com.ppioli.url.actors.actions;

import com.ppioli.url.utils.exception.URLException;

public abstract class SteppedAction implements IAction {

    private IActionStep[] frames;
    private int currentFrame = 0;
    private int lastFrame = -1;

    public SteppedAction( IActionStep... frames ){
        if( frames.length == 0 ) {
            throw new URLException( "Must provide at least one frame for the actions" );
        }
        this.frames = frames;
    }

    @Override
    public float execute( float deltaTime ) {
        float remainingTime = deltaTime;
        while ( remainingTime > 0 && currentFrame < frames.length ){
            if( currentFrame != lastFrame ) {
                frames[currentFrame].start();
            }
            remainingTime = frames[currentFrame].execute( remainingTime );
            lastFrame = currentFrame;
            // we have time left, so we finished the action
            if( remainingTime > 0 ) currentFrame++;
        }
        return remainingTime;
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public abstract boolean applicable();
}
