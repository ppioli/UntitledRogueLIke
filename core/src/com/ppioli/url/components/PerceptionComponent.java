package com.ppioli.url.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PerceptionComponent implements Component, Pool.Poolable {

    public int visionRadius;


    public PerceptionComponent set( int visionRange ){
        this.visionRadius = visionRange;
        return this;
    }

    @Override
    public void reset() {
        this.visionRadius = -1;
    }
}
