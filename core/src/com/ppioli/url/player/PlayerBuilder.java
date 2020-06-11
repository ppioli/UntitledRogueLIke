package com.ppioli.url.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ppioli.url.Game;
import com.ppioli.url.actors.ActorComponent;
import com.ppioli.url.components.MovementComponent;
import com.ppioli.url.components.PhysicComponent;
import com.ppioli.url.components.PositionComponent;
import com.ppioli.url.components.VisualComponent;
import com.ppioli.url.constants.URLConstants;
import com.ppioli.url.utils.SpriteRenderer;

public class PlayerBuilder {
    private final Texture texture;

    public PlayerBuilder(){
        this.texture = new Texture( Gdx.files.internal( "Characters/Undead0.png" ));
    }


    public void spawnPlayer(int x, int y){
        Entity entity = Game.engine.createEntity();

        entity.add( Game.engine.createComponent( PhysicComponent.class )
                .set( 10, true, false ) );

        entity.add( Game.engine.createComponent( PositionComponent.class ).set( entity, x, y, URLConstants.ACTOR_Z) );

        entity.add( Game.engine.createComponent( VisualComponent.class ).set( new SpriteRenderer( new TextureRegion( texture, URLConstants.TILE_SIZE, URLConstants.TILE_SIZE ) ) ));

        entity.add( Game.engine.createComponent( ActorComponent.class ).set( new PlayerBrain( entity ), 100, 3, 5 ));

        entity.add( Game.engine.createComponent( MovementComponent.class ).set( 2 ));

        Game.engine.addEntity( entity );

    }
}
