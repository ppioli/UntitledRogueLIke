package com.ppioli.url.entitiesFactories.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ppioli.url.actors.ActorComponent;
import com.ppioli.url.ai.ai.TestBrain;
import com.ppioli.url.components.MovementComponent;
import com.ppioli.url.constants.URLConstants;
import com.ppioli.url.utils.SpriteRenderer;
import com.ppioli.url.components.PhysicComponent;
import com.ppioli.url.components.PositionComponent;
import com.ppioli.url.components.VisualComponent;

public class NPCFactory {
    private PooledEngine engine;
    private Texture texture;

    public NPCFactory( PooledEngine engine ){
        this.engine = engine;
        this.texture = new Texture( Gdx.files.internal( "Characters/Undead0.png" ));
    }


    public void create( int x, int y) {
        Entity entity = engine.createEntity();

        entity.add( engine.createComponent( PhysicComponent.class )
                .set( 10, true, false ) );

        entity.add( engine.createComponent( PositionComponent.class ).set( entity, x, y, URLConstants.ACTOR_Z) );

        entity.add( engine.createComponent( VisualComponent.class ).set( new SpriteRenderer( new TextureRegion( texture, URLConstants.TILE_SIZE, URLConstants.TILE_SIZE ) ) ));

        entity.add( engine.createComponent( ActorComponent.class ).set( new TestBrain( entity ), 100, 3, 5 ));

        entity.add( engine.createComponent( MovementComponent.class ).set( 2 ));

        engine.addEntity( entity );

    }
}

