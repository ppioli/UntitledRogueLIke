package com.ppioli.url.entitiesFactories.tiles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ppioli.url.entitiesFactories.SpritePosition;
import com.ppioli.url.constants.URLConstants;
import com.ppioli.url.utils.IRenderer;
import com.ppioli.url.utils.SpriteRenderer;
import com.ppioli.url.components.PhysicComponent;
import com.ppioli.url.components.PositionComponent;
import com.ppioli.url.components.VisualComponent;

import java.util.Map;

public class TileClassFactory {
    public final TileType type;
    private final PooledEngine engine;
    private final Map<Integer, SpritePosition> tileDefinitions;
    private final Texture spriteSheet;
    private final int startX;
    private final int startY;

    public TileClassFactory( PooledEngine engine,
                             TileType type,
                             Map<Integer, SpritePosition> tileDefinitions,
                             String texturePath,
                             int startX,
                             int startY ) {
        this.type = type;
        this.engine = engine;
        this.tileDefinitions = tileDefinitions;
        this.spriteSheet = new Texture( Gdx.files.internal( texturePath ));
        this.startX = startX;
        this.startY = startY;
    }

    public Entity getTile( int subtype, int x, int y ) {
        if ( tileDefinitions.containsKey( subtype ) ) {
            return createTile( getSpriteRenderer( tileDefinitions.get( subtype ) ), x, y );
        } else {
            return createTile( URLConstants.SPRITE_404, x, y );
        }
    }

    private SpriteRenderer getSpriteRenderer( SpritePosition pos ) {
        return new SpriteRenderer( new TextureRegion( spriteSheet, startX + pos.x * URLConstants.TILE_SIZE, startY + pos.y * URLConstants.TILE_SIZE,
                URLConstants.TILE_SIZE, URLConstants.TILE_SIZE ) );
    }

    private Entity createTile( IRenderer renderer, int x, int y ){
        Entity entity = engine.createEntity();

        entity.add( engine.createComponent( PositionComponent.class )
            .set( entity, x, y, URLConstants.TILE_Z ));

        entity.add( engine.createComponent( VisualComponent.class )
            .set( renderer ));

        if ( type.massive ){
            entity.add( engine.createComponent( PhysicComponent.class )
                .set( Integer.MAX_VALUE, true, type.opaque ));
        }

        engine.addEntity( entity );

        return entity;
    }
}
