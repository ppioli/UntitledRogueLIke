package com.ppioli.url.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class MovementComponent implements Component, Pool.Poolable {
    public float speed;

    public Component set( float speed ) {
        this.speed = speed;
        return this;
    }

    @Override
    public void reset() {
        this.speed = 0;
    }
}
