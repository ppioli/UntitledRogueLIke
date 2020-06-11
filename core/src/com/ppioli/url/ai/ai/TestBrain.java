package com.ppioli.url.ai.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.ppioli.url.actors.ActorComponent;
import com.ppioli.url.actors.actions.IAction;
import com.ppioli.url.actors.actions.MoveAction;
import com.ppioli.url.utils.geom.Direction;

import java.util.Optional;

public class TestBrain implements Brain {

    private final Entity entity;
    private ComponentMapper<ActorComponent> am = ComponentMapper.getFor( ActorComponent.class );
    int ix = 1;

    public TestBrain( Entity entity ) {
        this.entity = entity;
    }

    @Override
    public Optional<IAction> getAction() {
        ix++;
        if ( ix % 2 == 0 ) {
            return Optional.of( new MoveAction( entity, Direction.random() ) );
        } else {
            return Optional.of( new MoveAction( entity, Direction.random() ) );
        }

    }

    @Override
    public void onInterrupt() {
        Gdx.app.log( TestBrain.class.getCanonicalName(), "Interrupt no an AI brain, this should not happen" );
        am.get( entity ).addAP( -1 );//returns a cost of one to avoid running into a endless loop
    }
}
