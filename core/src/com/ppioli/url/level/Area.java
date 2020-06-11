package com.ppioli.url.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.RandomXS128;
import com.ppioli.url.Game;
import com.ppioli.url.ai.Board;
import com.ppioli.url.level.levelGeneration.layoutBuilder.ILayoutBuilder;
import com.ppioli.url.level.levelGeneration.layoutBuilder.SimpleRoomLB;
import com.ppioli.url.level.levelGeneration.scenes.SceneBuilder;
import com.ppioli.url.utils.TreeHelpers;
import com.ppioli.url.utils.geom.IntArea;
import com.ppioli.url.constants.URLConstants;
import com.ppioli.url.utils.geom.MatrixHelpers;

import java.util.Optional;

public class Area implements Board {
    // Map floor and walls ( inert )
    private int x, y;
    private Cell[][] cells;
    private final long seed;

    private boolean initialized;

    public Area( long seed, int x, int y ) {
//        super( IntVect.create( 0, 0 ), IntVect.create( Constants.AREA_SIZE, Constants.AREA_SIZE ));
        this.x = x;
        this.y = y;
        this.seed = seed;
        this.cells = new Cell[URLConstants.AREA_SIZE][URLConstants.AREA_SIZE];

        MatrixHelpers.fill( cells, ( a, b ) -> new Cell(a,b) );
        this.initialized = false;
    }

    public void initialize() {
        RandomXS128 rng = new RandomXS128( seed );
        SceneBuilder builder = new SceneBuilder( new IntArea( 0, 0, URLConstants.AREA_SIZE, URLConstants.AREA_SIZE ), rng );
        Gdx.app.log( Area.class.getCanonicalName(), builder.getRoot().toString() );
        ILayoutBuilder layoutBuilder = new SimpleRoomLB( Game.factories.getTileFactory() );

        TreeHelpers.iterateLeafs( builder.getRoot(), data -> {
            layoutBuilder.fill( this, data.getArea(), data.getRestrictions(), null );
        } );
        this.initialized = true;
    }

    public void setTile( Entity tile, int x, int y ) {
        if ( cells[x][y].count() == 0 ) {
            cells[x][y].add( tile );
        }

    }


    public boolean isBlocked( int x, int y ) {
        Optional<Cell> cell = MatrixHelpers.get( cells, x, y );
        return !cell.isPresent() || cell.get().isBlocked();
    }

//    @Override
//    public void move( Entity entity, int x, int y, Direction direction ) {
//        get( x, y ).get().remove( entity );
//        get( x + direction.dx, y + direction.dy ).get().add( entity );
//    }
//
//    @Override
//    public void add( Entity entity, int x, int y ) {
//        get( x,y ).ifPresent( c -> c.add( entity ) );
//    }

    @Override
    public Optional<Cell> get( int x, int y ) {
        return MatrixHelpers.get( cells, x, y );
    }

    public boolean isInitialized() {
        return initialized;
    }


}
