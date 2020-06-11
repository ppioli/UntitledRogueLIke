package com.ppioli.url.utils.exception;

public class NotImplementedException extends URLException {

    public NotImplementedException() {
        super( "Not implemented method" );
    }

    private NotImplementedException( String format, Object... params ) {
        super( format, params );
    }

    private NotImplementedException( String message, Throwable cause ) {
        super( message, cause );
    }

    private NotImplementedException( Throwable cause ) {
        super( cause );
    }

    private NotImplementedException( String message,
                                    Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
