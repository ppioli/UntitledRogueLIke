package com.ppioli.url.actors.actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;

/**
 * This is an empty action used when a command falls in an error state an can't return a valid action.
 * If the code runs w/o erros, you should never see it. So... see you around.
 */
public class FuckedUpAction implements IAction {

    public final Entity entity;

    public FuckedUpAction( Entity entity ) {
        this.entity = entity;
    }

    @Override
    public float execute( float deltaTime ) {
        Gdx.app.error( FuckedUpAction.class.getCanonicalName(),
                "Somewhere, someone, fucked up. This action is just a place holder so the game doesnt blown up " + entity.toString() );
        return deltaTime;
    }

    @Override
    public int getCost() {
        /* Returns a cost of one to avoid endless loops */
        return 1;
    }

    @Override
    public boolean applicable() {
        return true;
    }
}
