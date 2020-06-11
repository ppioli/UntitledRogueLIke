package com.ppioli.url.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PhysicComponent implements Component, Pool.Poolable {

    public int hp;
    public boolean massive;
    public boolean opaque;

    public PhysicComponent set( int hp, boolean massive, boolean opaque ) {
        this.hp = hp;
        this.massive = massive;
        this.opaque = opaque;
        return this;
    }

    @Override
    public void reset() {
        this.hp = 0;
        this.massive = false;
        this.opaque = false;
    }
}
