package com.ppioli.url.entitiesFactories;

public class SpritePosition {
    public final int x,y;
    public final boolean opaque;
    public final boolean massive;

    public SpritePosition( int x, int y, boolean opaque, boolean massive ) {
        this.x = x;
        this.y = y;
        this.opaque = opaque;
        this.massive = massive;
    }
}
