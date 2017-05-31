package com.caej.order.order;

/**
 * @author chi
 */
public class CheckoutResponse {
    public Error error;

    public static class Error {
        public String errorCode;
        public String errorMessage;
    }
}
