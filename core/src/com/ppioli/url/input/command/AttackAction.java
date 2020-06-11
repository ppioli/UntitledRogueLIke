package com.ppioli.url.input.command;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.ppioli.url.actors.actions.IAction;

import static com.ppioli.url.constants.URLTypes.*;
import static com.ppioli.url.constants.URLComponentMappers.*;
import static com.ppioli.url.constants.URLConstants.*;

public class AttackAction implements IAction {
    private final Entity source;
    private final Entity target;

    public AttackAction( Entity source, Entity target ) {
        this.target = target;
        this.source = source;
    }

    @Override
    public float execute( float deltaTime ) {
        Gdx.app.log( AttackCommand.class.getCanonicalName(), "You attacked thing" );
        return deltaTime;
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public boolean applicable() {
        return CORPOREAL.matches( target );
    }
}
