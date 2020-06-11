package com.ppioli.url.entitiesFactories.tiles;

public enum TileType {
    WALL(true, true),
    FLOOR(false, false);

    public final boolean massive;
    public final boolean opaque;

    TileType( boolean opaque, boolean massive ) {
        this.opaque = opaque;
        this.massive =  massive;
    }
}
