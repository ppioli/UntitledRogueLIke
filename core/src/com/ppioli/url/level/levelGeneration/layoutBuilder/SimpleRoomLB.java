package com.ppioli.url.level.levelGeneration.layoutBuilder;

import com.badlogic.ashley.core.Entity;
import com.ppioli.url.level.Area;
import com.ppioli.url.entitiesFactories.tiles.TileFactory;
import com.ppioli.url.entitiesFactories.tiles.TileType;
import com.ppioli.url.utils.geom.IntArea;
import com.ppioli.url.utils.geom.IntVect;
import com.ppioli.url.utils.geom.MatrixHelpers;

import static com.ppioli.url.level.levelGeneration.LayoutHelpers.*;

import java.util.List;

public class SimpleRoomLB implements ILayoutBuilder {

    private TileFactory tileFactory;

    public SimpleRoomLB( TileFactory tileFactory ) {
        this.tileFactory = tileFactory;
    }


    @Override
    public void fill( Area area, IntArea section, List<IRestriction> restrictions, List<IRequirement> requirements ) {
        TileType[][] definition = new TileType[section.getWidth()][section.getHeight()];

        MatrixHelpers.fill( definition, ( x, y ) -> isWall( section, x, y, restrictions ) ? TileType.WALL : TileType.FLOOR );

        applyDefinition( definition, area, section.startPosition );
    }

    public void applyDefinition( TileType[][] definition, Area area, IntVect startPos ) {
        MatrixHelpers.iterate( definition, ( t, x, y ) -> {
            Entity tile = tileFactory.getTile( t, getNeighbours( definition, x, y ), startPos.x + x, startPos.y + y );
            area.setTile( tile, x, y );
        } );
    }


    private boolean isWall( IntArea section, int x, int y, List<IRestriction> restrictions ) {

        return ( x == 0 || x == section.getWidth() - 1 || y == 0 || y == section.getHeight() - 1 ) &&
                RestrictionHelper.isAvailable( section.startPosition.x + x,section.startPosition.y + y, restrictions );
    }


}
