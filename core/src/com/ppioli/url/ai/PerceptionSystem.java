package com.ppioli.url.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.ppioli.url.Game;
import com.ppioli.url.components.PerceptionComponent;
import com.ppioli.url.components.PositionComponent;
import com.ppioli.url.utils.MathUtils;
import com.ppioli.url.utils.geom.MatrixHelpers;


public class PerceptionSystem {
    private static ComponentMapper<PositionComponent> posm = ComponentMapper.getFor( PositionComponent.class );
    private static ComponentMapper<PerceptionComponent> perm = ComponentMapper.getFor( PerceptionComponent.class );

    public static Array<POI> FindPOIs( Entity entity ) {

        Array<POI> result = new Array<>();
        PerceptionComponent perception = perm.get( entity );
        PositionComponent position = posm.get( entity );

        MatrixHelpers.iterate( position.getX(), position.getY(), perception.visionRadius, ( x, y ) -> {
            Game.world.get( x, y ).ifPresent( cell -> {
                cell.getEntities().forEach( otherEntity -> {
                    if ( otherEntity != entity ) {
                        result.add( new POI( otherEntity,
                                MathUtils.distance( position.getX(), position.getY(), x, y ) ) );
                    }
                } );
            } );
        } );
        return result;
    }
}

