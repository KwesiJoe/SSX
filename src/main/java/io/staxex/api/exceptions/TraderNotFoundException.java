package io.staxex.api.exceptions;

public class TraderNotFoundException extends RuntimeException{
    public TraderNotFoundException(String message){
        super(message);
    }
}
