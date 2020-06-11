package com.ppioli.url.actors.actions;

import com.badlogic.ashley.core.Entity;
import com.ppioli.url.Game;
import com.ppioli.url.utils.geom.Direction;

import static com.ppioli.url.constants.URLTypes.*;
import static com.ppioli.url.constants.URLComponentMappers.*;
import static com.ppioli.url.constants.URLConstants.BASE_MOVEMENT;

public class MoveAction extends SteppedAction {

    private Entity entity;
    private Direction direction;

    public MoveAction( Entity entity, Direction direction ) {
        super( new MovementFrame( entity,  direction, BASE_MOVEMENT / MOV_CM.get( entity ).speed ));
        this.entity = entity;
        this.direction = direction;
    }


    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public boolean applicable() {
        return MOBIL.matches( entity ) && !Game.world.isBlocked(
            POS_CM.get(entity).getX() + this.direction.dx,
            POS_CM.get(entity).getY() + this.direction.dy
        );
    }
}
