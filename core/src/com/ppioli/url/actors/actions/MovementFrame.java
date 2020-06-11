package com.ppioli.url.actors.actions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.ppioli.url.components.PositionComponent;
import com.ppioli.url.utils.geom.Direction;


import static com.ppioli.url.constants.URLConstants.TILE_SIZE;

public class MovementFrame implements IActionStep {
    private static ComponentMapper<PositionComponent> pm = ComponentMapper.getFor( PositionComponent.class );
    private float durationLeft;
    private Direction direction;
    private float dx;
    private float dy;
    private PositionComponent positionComponent;

    public MovementFrame( Entity entity,
            Direction direction,
          float duration ) {

        this.positionComponent = pm.get( entity );
        this.durationLeft = duration;
        this.direction = direction;
    }

    @Override
    public void start() {
        this.positionComponent.move( direction );
        this.positionComponent.setOffset( -direction.dx * TILE_SIZE, -direction.dy * TILE_SIZE );
        this.dx = -positionComponent.offsetX() / durationLeft;
        this.dy = -positionComponent.offsetY() / durationLeft;
    }

    @Override
    public float execute( float delta ) {
        if ( delta > durationLeft ) {
            // we just set it to avoid decimal precision loss
            this.positionComponent.setOffset( 0, 0 );
            return delta - durationLeft;
        } else {
            this.positionComponent.addOffset( dx * delta, dy * delta );
            this.durationLeft -= delta;
            return 0;
        }
    }


}
