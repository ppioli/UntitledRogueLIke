package com.ppioli.url.utils.exception;

public class URLException extends RuntimeException {

    private URLException() {
    }

    public URLException( String message ) {
        super( message );
    }

    public URLException( String format, Object... params ) {
        super( String.format( format, params ) );
    }

    public URLException( String message, Throwable cause ) {
        super( message, cause );
    }

    public URLException( Throwable cause ) {
        super( cause );
    }

    public URLException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
