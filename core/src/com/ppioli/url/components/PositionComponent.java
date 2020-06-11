package com.ppioli.url.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.ppioli.url.Game;
import com.ppioli.url.utils.geom.Direction;
import com.ppioli.url.utils.geom.IntVect;

import static com.ppioli.url.constants.URLConstants.TILE_SIZE;

public class PositionComponent implements Component, Pool.Poolable {
    private Vector2 offset;
    private Vector2 renderPos;
    private GridPosition gridPosition;
    private Entity entity;
    private boolean needsUpdate;
    private int z;

    public PositionComponent() {
        this.gridPosition = new GridPosition();
        this.renderPos = new Vector2( 0, 0 );
        this.offset = new Vector2( 0, 0 );
        this.needsUpdate = false;
    }

    public PositionComponent set( Entity ent, int x, int y, int z ) {
        this.gridPosition.set( ent, x, y );
        this.z = z;
        updateRenderPos();
        return this;
    }

    @Override
    public void reset() {
        this.gridPosition.clear();
        this.offset.set( 0, 0 );
        this.needsUpdate = false;
        this.z = -1;
    }

    public void move( Direction direction ) {
        gridPosition.move( direction );
        updateRenderPos();
    }

    public void addOffset( float x, float y ) {
        this.offset.x += x;
        this.offset.y += y;
        updateRenderPos();
    }

    public void setOffset( float x, float y ) {
        this.offset.x = x;
        this.offset.y = y;
        updateRenderPos();
    }

    private void updateRenderPos() {
        this.needsUpdate = true;
    }

    public float offsetX() {
        return this.offset.x;
    }

    public float offsetY() {
        return this.offset.y;
    }

    public int getZ() {
        return z;
    }

    public Vector2 getRenderPosition() {
        if ( needsUpdate ) {
            renderPos.x = gridPosition.getX() * TILE_SIZE + offset.x;
            renderPos.y = gridPosition.getY() * TILE_SIZE + offset.y;
        }
        return renderPos;
    }

    public int getX() {
        return gridPosition.getX();
    }

    public int getY() {
        return gridPosition.getY();
    }

    public int distance( PositionComponent to ) {
        return Math.abs( this.getY() - to.getY() ) + Math.abs( this.getX() - to.getX() );
    }


    private class GridPosition {
        private IntVect vect;
        private Entity entity;

        public GridPosition() {
            this.vect = new IntVect( -1, -1 );
        }

        public GridPosition set( Entity ent, int x, int y ){
            this.entity = ent;
            this.setPosition( x, y );
            return this;
        }

        public void move( Direction d ){
            if( this.vect.x != -1 ){
                Game.world.get( vect.x, vect.y ).ifPresentOrElse( c -> c.remove( entity ),
                        () -> Gdx.app.error( GridPosition.class.getCanonicalName(),
                                String.format( "Trying trying to remove from an invalid cell ( %d, %d ).", vect.x, vect.y ) ));
            }
            this.setPosition( vect.x + d.dx, vect.y + d.dy );

        }

        public void setPosition( int x, int y ){
            this.vect.x = x;
            this.vect.y = y;
            Game.world.get( x, y ).ifPresentOrElse( c -> c.add( entity ),
                    () -> Gdx.app.error( GridPosition.class.getCanonicalName(),
                            String.format( "Trying trying to add the entity to an an invalid cell ( %d, %d ).", x, y ) ));
        }

        public void clear(){
            this.vect.x = -1;
            this.vect.y = -1;
            this.entity = null;
        }

        public int getX(){
            return this.vect.x;
        }

        public int getY(){
            return this.vect.y;
        }
    }
}
