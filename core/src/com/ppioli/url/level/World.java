package com.ppioli.url.level;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.TimeUtils;
import com.ppioli.url.ai.Board;
import com.ppioli.url.entitiesFactories.actors.NPCFactory;
import com.ppioli.url.entitiesFactories.tiles.TileFactory;
import com.ppioli.url.player.PlayerBuilder;
import com.ppioli.url.constants.URLConstants;
import com.ppioli.url.utils.geom.MatrixHelpers;

import java.util.Optional;

import static com.ppioli.url.constants.URLConstants.*;

public class World implements Board {

    public final Area[][] areas;

    public final long SEED;

    private final PooledEngine engine;

    public final RandomXS128 rng;

    //private Map<IntVect, WorldObject> objects;
    private NPCFactory npcFactory;
    private PlayerBuilder playerBuilder;
    private TileFactory tileFactory;

    public World( PooledEngine engine ) {
        //we plan and spawn areas
        this.engine = engine;
        SEED = TimeUtils.nanoTime();
        rng = new RandomXS128( SEED );
        areas = new Area[URLConstants.WORLD_SIZE][URLConstants.WORLD_SIZE];
    }

    public void initialize(){


        MatrixHelpers.fill( areas, ( x, y ) -> new Area( rng.nextInt(), x, y ) );

        npcFactory = new NPCFactory( engine );
        tileFactory = new TileFactory( engine );
        playerBuilder = new PlayerBuilder();

        areas[0][0].initialize();

        npcFactory.create( 2, 2 );
        playerBuilder.spawnPlayer( 4, 4 );

    }

    public Optional<Cell> get( int x, int y ) {
        if ( isOutside( x, y ) ) {
            Optional.empty();
        }
        Area area = areas[x / AREA_SIZE][y / AREA_SIZE];

        return area.get( x % AREA_SIZE, y % AREA_SIZE );
    }

    private boolean isOutside( int x, int y ) {
        return x < 0 || y < 0 || x >= ( WORLD_SIZE * AREA_SIZE ) || y >= ( WORLD_SIZE * AREA_SIZE );
    }

    @Override
    public boolean isBlocked( int x, int y ) {
        Optional<Cell> cellOpt = get( x, y );

        return cellOpt.map( Cell::isBlocked ).orElse( false );
    }

//    @Override
//    public void move( Entity entity, int x, int y, Direction direction ) {
//        Area area = areas[x / AREA_SIZE][y/ AREA_SIZE];
//        if ( !area.isInitialized() ) area.initialize();
//
//        area.move( entity, x % AREA_SIZE, y % AREA_SIZE, direction );
//    }
//
//    @Override
//    public void add( Entity entity, int x, int y ) {
//        Area area = areas[x / AREA_SIZE][y/ AREA_SIZE];
//        if ( !area.isInitialized() ) area.initialize();
//
//        area.add( entity, x % AREA_SIZE, y % AREA_SIZE);
//    }
}
