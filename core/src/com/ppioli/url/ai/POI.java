package com.ppioli.url.ai;

import com.badlogic.ashley.core.Entity;

public class POI {

    public Entity entity;
    public int distance;

    public POI( Entity entity, int distance ) {
        this.entity = entity;
        this.distance = distance;
    }

}
