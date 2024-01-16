package br.com.gurumatch.customerorders.exceptions;

public class LimitOrderExcededException extends RuntimeException {
    public LimitOrderExcededException(String message) {
        super(message);
    }
}
