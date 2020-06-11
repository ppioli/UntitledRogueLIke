package com.ppioli.url.level.levelGeneration;

import com.ppioli.url.utils.geom.Direction;
import com.ppioli.url.entitiesFactories.tiles.TileType;

import java.util.Optional;

public class LayoutHelpers {

    public static int getNeighbours( TileType[][] layout, int x, int y ) {
        int result = 0;
        for ( Direction d: Direction.values() ) {
            Optional<TileType> neigh = getNeighbour( layout, x + d.dx, y + d.dy );
            if ( neigh.isPresent() && neigh.get() == TileType.WALL ) {
                result = result | d.code;
            }
        }
        return result;
    }

    public static boolean inBounds( Object[][] matrix, int x, int y ) {
        return x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length;
    }

    public static <T> Optional<T> getNeighbour( T[][] matrix, int x, int y ) {
        if( inBounds( matrix, x, y ) ) return Optional.of(matrix[x][y]);
        else return Optional.empty();
    }
}
