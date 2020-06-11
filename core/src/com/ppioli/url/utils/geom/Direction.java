package com.ppioli.url.utils.geom;

import com.ppioli.url.constants.URLConstants;
import com.ppioli.url.utils.exception.URLException;

import static com.ppioli.url.constants.URLConstants.TILE_SIZE;

public enum Direction {
    TOP( 0, 1, 1 ),
    RIGHT( 1, 0, 1 << 2 ),
    BOTTOM( 0, -1, 1 << 3 ),
    LEFT( -1, 0, 1 << 4 );

    public final int dx;
    public final int dy;
    public final int code;
    public final float wx;
    public final float wy;

    Direction( int dx, int dy, int code ) {
        this.dx = dx;
        this.dy = dy;
        this.code = code;
        this.wx = dx * TILE_SIZE;
        this.wy = dy * TILE_SIZE;
    }

    public static Direction random() {
        return Direction.values()[URLConstants.RNGSUS.nextInt( Direction.values().length ) ];
    }

    public Direction inverse() {
        switch ( this ) {
            case TOP:
                return BOTTOM;
            case RIGHT:
                return LEFT;
            case BOTTOM:
                return TOP;
            case LEFT:
                return RIGHT;
            default:
                throw new URLException( "Invalid direction code" );
        }
    }
}
