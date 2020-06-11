package com.ppioli.url.utils;

import java.util.Iterator;
import java.util.function.Predicate;

public class CollectionHelper {
    public static <T> boolean any( Iterable<T> collection, Predicate<T> condition ) {
        boolean conditionMet = false;
        Iterator<T> it = collection.iterator();
        while ( it.hasNext() && !conditionMet ) {
            conditionMet = condition.test( it.next() );
        }
        return conditionMet;
    }

    public static <T> String toString( Iterable<T> collection, String separator ) {
        StringBuilder builder = new StringBuilder("[ ");
        Iterator<T> it = collection.iterator();
        while ( it.hasNext() ) {
            builder.append( it.next().toString() );
            if(it.hasNext()) {
                builder.append( separator );
            }

        }
        builder.append( " ]" );
        return builder.toString();
    }

    public static <T> String toString( Iterable<T> collection ) {
        return toString( collection, ",\n" );
    }
}
