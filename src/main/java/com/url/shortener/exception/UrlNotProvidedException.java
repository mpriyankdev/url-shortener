package com.url.shortener.exception;

public class UrlNotProvidedException extends RuntimeException {

    public UrlNotProvidedException() {
        super();
    }

    public UrlNotProvidedException(String message) {
        super(message);
    }

    public UrlNotProvidedException(String message, Throwable t) {
        super(message, t);
    }
}
