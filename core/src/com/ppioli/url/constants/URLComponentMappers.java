package com.ppioli.url.constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.ppioli.url.components.MovementComponent;
import com.ppioli.url.components.PhysicComponent;
import com.ppioli.url.components.PositionComponent;

public class URLComponentMappers {
    public final static ComponentMapper<PositionComponent> POS_CM = ComponentMapper.getFor( PositionComponent.class );
    public final static ComponentMapper<MovementComponent> MOV_CM = ComponentMapper.getFor( MovementComponent.class );
    public final static ComponentMapper<PhysicComponent> PHY_CM = ComponentMapper.getFor( PhysicComponent.class );

}
