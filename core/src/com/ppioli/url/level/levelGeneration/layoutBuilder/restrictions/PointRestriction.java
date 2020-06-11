package com.ppioli.url.level.levelGeneration.layoutBuilder.restrictions;

import com.ppioli.url.level.levelGeneration.layoutBuilder.IRestriction;
import com.ppioli.url.level.levelGeneration.scenes.SceneBuilder;
import com.ppioli.url.utils.geom.IntVect;

public class PointRestriction implements IRestriction {

    private IntVect vect;

    public PointRestriction( int x, int y ) {
        this.vect = new IntVect( x, y );
    }

    @Override
    public boolean isAvailable( int x, int y ) {
        return !this.vect.equals( x, y );
    }

}
