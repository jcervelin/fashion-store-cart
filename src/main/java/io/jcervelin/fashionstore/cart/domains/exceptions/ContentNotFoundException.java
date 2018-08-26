package io.jcervelin.fashionstore.cart.domains.exceptions;


public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(final String message) {
        super(message);
    }
}
