package com.ppioli.url.utils.geom;

import com.ppioli.url.utils.exception.URLException;

import java.util.Optional;

public class IntArea {
    // area starting point ( inclusive )
    public IntVect startPosition;
    //area ending point ( exclusive )
    public IntVect endPosition;


    public IntArea( IntVect startPosition, IntVect endPosition ) {
        if( !startPosition.lowerOrEqualsThan( endPosition ) ) throw new URLException( "Start and end point of an area must be normalized" );
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * Creates a new IntArea
     * x1, y1 start position of the area ( inclusive )
     * x2, y2   end position of the area ( exclusive )
     */
    public IntArea( int x1, int y1, int x2, int y2 ) {
        setPoints( x1, y1, x2, y2 );
    }

    public void setPoints( int x1, int y1, int x2, int y2 ) {
        this.startPosition = new IntVect( Math.min( x1, x2 ),
                Math.min( y1, y2 ) );
        this.endPosition = new IntVect( Math.max( x1, x2 ),
                Math.max( y1, y2 ) );
    }

    public int getHeight() {
        return this.endPosition.y - this.startPosition.y;
    }

    public int getWidth() {
        return this.endPosition.x - this.startPosition.x;
    }

    public int getArea() {
        return this.getWidth() * this.getHeight();
    }

    /**
     * Finds intersection between two areas
     *
     * @param area
     * @return Optional with the intersection area or empty if there is none.
     */
    public Optional<IntArea> intersection( IntArea area ) {
        return intersection( this, area );
    }

    public static Optional<IntArea> intersection( IntArea a, IntArea b ) {

        int x1 = Math.max( a.startPosition.x, b.startPosition.x );
        int y1 = Math.max( a.startPosition.y, b.startPosition.y );
        int x2 = Math.min( a.endPosition.x, b.endPosition.x );
        int y2 = Math.min( a.endPosition.y, b.endPosition.y );

        if( x1 > x2 || y1 > y2 ) return Optional.empty();
        else {
            return Optional.of( new IntArea( x1, y1 , x2, y2  ) );
        }
    }


    public IntVect getMiddlePoint() {
        return new IntVect( this.startPosition.x + this.getWidth() / 2,
                this.startPosition.y + this.getHeight() / 2 );
    }

    /**
     * @return Returns the coefficient between width and height. If the value == 1, the area is a square.
     * The value tends to infinity when width >> height.
     */
    public float getWidenessRatio() {
        if ( getArea() == 0 ) throw new URLException( "Error getting wideness ratio. Area == 0" );
        return ( ( float ) getWidth() ) / getHeight();
    }

    @Override
    public String toString() {
        return "IntArea{" +
                "startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                '}';
    }
}
