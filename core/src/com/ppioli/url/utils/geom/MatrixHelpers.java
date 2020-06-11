package com.ppioli.url.utils.geom;

import com.ppioli.url.Game;
import com.ppioli.url.ai.Board;
import com.ppioli.url.ai.PerceptionSystem;
import com.ppioli.url.level.Cell;
import com.ppioli.url.utils.MathUtils;
import com.ppioli.url.utils.TriConsumer;
import com.ppioli.url.utils.TriFunction;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class MatrixHelpers {

    public static <T> void iterate( T[][] matrix, Consumer<T> consumer ) {
        iterate( matrix, ( cell, x, y ) -> consumer.accept( cell ) );
    }

    public static <T> void iterate( T[][] matrix, TriConsumer<T, Integer, Integer> function ) {
        int mx = matrix.length;
        int my = matrix[0].length;
        for ( int x = 0; x < mx; x++ ) {
            for ( int y = 0; y < my; y++ ) {
                function.accept( matrix[x][y], x, y );
            }
        }
    }

    public static <T> void iterate( T[][] matrix, int x, int y, int radius, TriConsumer<T, Integer, Integer> func ){
        for ( int px = x - radius; px < x + radius; x++ ) {
            for ( int py = y - radius; py < y + radius; y++ ) {
                func.accept( matrix[px][py], px, py );
            }
        }
    }

    public static void iterate( int x, int y, int radius, BiConsumer<Integer, Integer> func ){
        for ( int px = x - radius; px < x + radius; x++ ) {
            for ( int py = y - radius; py < y + radius; y++ ) {
                func.accept( px, py );
            }
        }
    }


    public static <T> void fill( T[][] matrix, BiFunction<Integer, Integer, T> function ) {
        int mx = matrix.length;
        int my = matrix[0].length;
        for ( int x = 0; x < mx; x++ ) {
            for ( int y = 0; y < my; y++ ) {
                matrix[x][y] = function.apply( x, y );
            }
        }
    }

    public static <T> Optional<T> get( T[][] matrix, int x, int y ) {
        if ( !inBounds( matrix, x, y ) ) {
            return Optional.empty();
        } else {
            return matrix[x][y] != null ? Optional.of( matrix[x][y] ) : Optional.empty();
        }
    }

    public static boolean inBounds( Object[][] matrix, int x, int y ) {
        int mx = matrix.length;
        int my = matrix[0].length;
        return ( x >= 0 && x < mx && y >= 0 && y < my );
    }

    public static <T> String toString( T[][] matrix, TriFunction<T, Integer, Integer, String> toString ) {
        StringBuilder builder = new StringBuilder( "[\n" );
        for ( int y = matrix[0].length - 1; y >= 0 ; y-- ) {
            for ( int x =  0; x < matrix.length ; x++ ) {
                builder.append( toString.apply( matrix[x][y], x, y ) );
            }
            builder.append( "\n" );
        }
        builder.append( "]" );
        return builder.toString();
    }

    public static String toString( Board b, int startX, int startY, int radius, TriFunction<Optional<Cell>, Integer, Integer, String> toString ) {
        StringBuilder builder = new StringBuilder( "[\n" );
        for ( int y = startY + radius - 1; y >= startY - radius; y-- ) {
            for ( int x =  startX - radius; x < startX + radius ; x++ ) {
                builder.append( toString.apply( b.get( x, y ), x, y ) );
            }
            builder.append( "\n" );
        }
        builder.append( "]" );
        return builder.toString();
    }

    public static <T> String toString( T[][] matrix, Function<T, String> func ) {
        return toString(matrix, (cell, x, y) -> func.apply(cell));
    }

}
