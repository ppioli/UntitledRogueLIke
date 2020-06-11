package com.ppioli.url.constants;

import com.badlogic.ashley.core.Family;
import com.ppioli.url.components.MovementComponent;
import com.ppioli.url.components.PhysicComponent;
import com.ppioli.url.components.PositionComponent;

public class URLTypes {

    public final static Family MOBIL = Family.all( MovementComponent.class, PositionComponent.class ).get();
    public final static Family CORPOREAL = Family.all( PositionComponent.class, PhysicComponent.class ).get();
}
