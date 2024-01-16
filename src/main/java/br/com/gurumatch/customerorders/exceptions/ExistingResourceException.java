package br.com.gurumatch.customerorders.exceptions;

public class ExistingResourceException extends RuntimeException {

    public ExistingResourceException(String message) {
        super(message);
    }
}
