package com.ppioli.url.level.levelGeneration.layoutBuilder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.RandomXS128;
import com.ppioli.url.level.levelGeneration.layoutBuilder.restrictions.PointRestriction;
import com.ppioli.url.level.levelGeneration.scenes.SceneBuilder;
import com.ppioli.url.utils.geom.IntVect;
import com.sun.org.apache.xpath.internal.objects.XString;

import java.util.List;

public class RestrictionHelper {

    public static IRestriction DoorRestriction( SceneBuilder.Connection connection, RandomXS128 random ) {
        int w = connection.where.endPosition.x - connection.where.startPosition.x;
        int h = connection.where.endPosition.y - connection.where.startPosition.y;
        int dx = w > 0 ? random.nextInt( w ) : 0;
        int dy = h > 0 ? random.nextInt( h ) : 0;
        int x = connection.where.startPosition.x + dx;
        int y = connection.where.startPosition.y + dy;

        return new PointRestriction(  x, y );
    }

    public static boolean isAvailable( int x, int y, List<IRestriction> restrictionList ) {
        Gdx.app.debug( RestrictionHelper.class.getCanonicalName(), String.format( "Tested %d %d ", x,y ) );
        return restrictionList.stream().allMatch( r -> r.isAvailable( x, y ) );
    }
}
