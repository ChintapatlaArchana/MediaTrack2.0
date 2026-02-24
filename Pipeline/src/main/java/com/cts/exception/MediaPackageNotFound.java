package com.cts.exception;

public class MediaPackageNotFound extends RuntimeException{
    public MediaPackageNotFound(String message){
        super(message);
    }
}
