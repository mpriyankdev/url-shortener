package com.url.shortener.exception;

public class AliasAlreadyUsedException extends RuntimeException {

    public AliasAlreadyUsedException() {
        super();
    }

    public AliasAlreadyUsedException(String message) {
        super(message);
    }

    public AliasAlreadyUsedException(String message, Throwable t) {
        super(message, t);
    }
}
