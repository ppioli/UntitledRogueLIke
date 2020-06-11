package com.ppioli.url.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.ppioli.url.ai.BoardCell;
import static com.ppioli.url.constants.URLTypes.*;
import static com.ppioli.url.constants.URLComponentMappers.*;
import static com.ppioli.url.constants.URLConstants.*;

public class Cell implements BoardCell {

    private boolean requiresUpdate = false;
    private Array<Entity> entities;
    private boolean massive;
    private boolean opaque;
    private boolean attackable;
    private int x, y;


    public Cell(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.entities = new Array<>(4);
    }

    @Override
    public boolean isBlocked() {
        update();
        return this.massive;
    }

    @Override
    public boolean isOpaque() {
        update();
        return this.opaque;
    }

    @Override
    public boolean isAttackable() {
        update();
        return this.attackable;
    }

    public void remove( Entity entity ) {
        this.entities.removeValue( entity, true );
        this.requiresUpdate = true;
    }

    public void add( Entity entity ) {
        this.entities.add( entity );
        this.requiresUpdate = true;
    }

    public void update(){
        if( !requiresUpdate ) return;
        massive = false;
        for ( Entity ent : entities ) {
            if( PHY_CM.has( ent )  ) {
                massive = massive || PHY_CM.get( ent ).massive;
                opaque = opaque || PHY_CM.get( ent ).opaque;
                attackable = true;
            }
        }
        requiresUpdate = false;
    }

    public Array<Entity> getEntities() {
        return this.entities;
    }

    public int count() {
        return entities.size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
