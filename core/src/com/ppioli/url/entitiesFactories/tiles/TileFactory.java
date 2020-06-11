package com.ppioli.url.entitiesFactories.tiles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.ppioli.url.constants.URLConstants;
import com.ppioli.url.entitiesFactories.SpritePosition;
import com.ppioli.url.utils.geom.Direction;
import com.ppioli.url.utils.exception.URLException;

import java.util.HashMap;
import java.util.Map;

public class TileFactory {

    public final Map<TileType, TileClassFactory> subFactories;

    public TileFactory( PooledEngine engine ) {
        this.subFactories = new HashMap<>();
        TileClassFactory wallTiles = new TileClassFactory( engine, TileType.WALL, getWallTileDefinition(),
                "level/Wall.png",
                0,
                3 * URLConstants.TILE_SIZE );
        TileClassFactory floorTiles = new TileClassFactory( engine, TileType.FLOOR, getFloorTileDefinition(),
                "level/Floor.png",
                0 ,
                3 * URLConstants.TILE_SIZE );

        registerSubfactory( wallTiles );
        registerSubfactory( floorTiles );

    }

    private void registerSubfactory( TileClassFactory factory ) {
        if ( subFactories.containsKey( factory.type )){
            throw new URLException( "Factory for type " + factory.type +" already registered" );
        }
        subFactories.put( factory.type, factory );

    }


    public Entity getTile( TileType type, int subtype, int x, int y ) {

        if( !subFactories.containsKey( type ) ) {
            throw new URLException( "Unregistered tile factory for type " + type );
        }

        return subFactories.get( type ).getTile( subtype, x, y );
    }

    private static Map<Integer, SpritePosition> getWallTileDefinition() {
        Map<Integer, SpritePosition> definition = new HashMap<>();
        definition.put( Direction.TOP.code,
                new SpritePosition( 1,1, true, true ) );
        definition.put( Direction.BOTTOM.code,
                new SpritePosition( 0,1, true, true ) );
        definition.put( Direction.LEFT.code,
                new SpritePosition( 1,0, true, true ) );
        definition.put( Direction.RIGHT.code,
                new SpritePosition( 1,0, true, true ) );
        //pair combinations
        definition.put( Direction.RIGHT.code | Direction.BOTTOM.code,
                new SpritePosition( 0,0, true, true ) );
        definition.put( Direction.LEFT.code | Direction.RIGHT.code,
                new SpritePosition( 1,0, true, true ) );
        definition.put( Direction.LEFT.code | Direction.BOTTOM.code,
                new SpritePosition( 2,0, true, true ) );
        definition.put( Direction.TOP.code | Direction.BOTTOM.code,
                new SpritePosition( 0,1, true, true ) );
        definition.put( 0,
                new SpritePosition( 1,1, true, true ) );
        definition.put( Direction.RIGHT.code | Direction.TOP.code,
                new SpritePosition( 0,2, true, true ) );
        definition.put( Direction.LEFT.code | Direction.TOP.code,
                new SpritePosition( 2,2, true, true ) );

        return definition;
    }

    private static Map<Integer, SpritePosition> getFloorTileDefinition() {
        Map<Integer, SpritePosition> definition = new HashMap<>();

        definition.put( Direction.TOP.code | Direction.LEFT.code,
                new SpritePosition( 0,0, false, false ) );
        definition.put( Direction.TOP.code,
                new SpritePosition( 1,0, false, false ) );
        definition.put( Direction.TOP.code  | Direction.RIGHT.code,
                new SpritePosition( 2,0, false, false ) );
        definition.put( Direction.LEFT.code,
                new SpritePosition( 0,1, false, false ) );
        definition.put( 0,
                new SpritePosition( 1,1, false, false ) );
        definition.put( Direction.RIGHT.code,
                new SpritePosition( 2,1, false, false ) );
        definition.put( Direction.BOTTOM.code | Direction.LEFT.code,
                new SpritePosition( 0,2, false, false ) );
        definition.put( Direction.BOTTOM.code,
                new SpritePosition( 1,2, false, false ) );
        definition.put( Direction.RIGHT.code | Direction.BOTTOM.code,
                new SpritePosition( 2,2, false, false ) );

        definition.put( Direction.TOP.code | Direction.BOTTOM.code,
                new SpritePosition( 5,1, false, false ) );
        definition.put( Direction.LEFT.code | Direction.RIGHT.code,
                new SpritePosition( 3,1, false, false ) );



        return definition;
    }


}
