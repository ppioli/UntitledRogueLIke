package com.ppioli.url.utils.geom;

import java.util.Objects;

public class IntVect {
    public int x,y;

    public IntVect( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public IntVect( IntVect vect, Direction d ) {
        this.x = vect.x + d.dx;
        this.y = vect.y + d.dy;
    }

    public IntVect add( Direction direction ){
        this.x += direction.dx;
        this.y += direction.dy;
        return this;
    }

    public boolean equals( int x, int y ){
        return this.x == x && this.y == y;
    }

    public boolean lowerOrEqualsThan( IntVect vect ){
        return this.x <= vect.x && this.y <= vect.y;
    }

    @Override
    public boolean equals( Object o ) {
        IntVect intVect = ( IntVect ) o;
        return x == intVect.x &&
                y == intVect.y;
    }

    public int distance( IntVect to ) {
        return Math.abs( x - to.x ) + Math.abs( y - to.y );
    }

    @Override
    public int hashCode() {
        return Objects.hash( x, y );
    }

    @Override
    public String toString() {
        return "IntVect{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
