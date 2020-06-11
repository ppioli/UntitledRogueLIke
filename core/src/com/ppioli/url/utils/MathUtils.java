package com.ppioli.url.utils;

public class MathUtils {

    public static int distance( int x1, int y1, int x2, int y2 ){
        return Math.abs( x1 - x2 ) + Math.abs( y1 - y2 );
    }
}
