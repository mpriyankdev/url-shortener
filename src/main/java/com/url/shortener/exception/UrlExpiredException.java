package com.url.shortener.exception;

public class UrlExpiredException extends RuntimeException {

    public UrlExpiredException() {
        super();
    }

    public UrlExpiredException(String message) {
        super(message);
    }

    public UrlExpiredException(String message, Throwable t) {
        super(message, t);
    }
}
