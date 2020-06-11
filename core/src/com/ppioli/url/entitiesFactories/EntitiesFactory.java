package com.ppioli.url.entitiesFactories;

import com.badlogic.ashley.core.PooledEngine;
import com.ppioli.url.entitiesFactories.actors.NPCFactory;
import com.ppioli.url.entitiesFactories.tiles.TileFactory;

public class EntitiesFactory {
    private NPCFactory npcFactory;
    private TileFactory tileFactory;

    public EntitiesFactory( PooledEngine engine ){
        npcFactory = new NPCFactory( engine );
        tileFactory = new TileFactory( engine );
    }

    public TileFactory getTileFactory() {
        return tileFactory;
    }
}
