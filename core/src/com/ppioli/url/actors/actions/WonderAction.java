package com.ppioli.url.actors.actions;

import com.badlogic.gdx.Gdx;

public class WonderAction extends SteppedAction {

    public WonderAction() {
        super(
                new InstantActionStep( () -> Gdx.app.log( WonderAction.class.getCanonicalName(),
                        "I wonder the meaning of life" ) ),
                new DelayActionStep( 1f )
        );
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public boolean applicable() {
        return true;
    }

}
