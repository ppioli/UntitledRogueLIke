package com.ppioli.url.utils;


import java.util.Objects;

/**
 * Credits to https://www.javatips.net/api/mdk-master/src/main/java/gov/nasa/jpl/mbee/mdk/api/function/TriConsumer.java
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     */
    void accept( T t, U u, V v );

    /**
     * Returns a composed {@code BiConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code BiConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default TriConsumer<T, U, V> andThen( TriConsumer<? super T, ? super U, ? super V> after ) {
        Objects.requireNonNull( after );

        return ( l, r, s ) -> {
            accept( l, r, s );
            after.accept( l, r, s );
        };
    }
}

