package com.socialMedia.BuzzHive.exception;

public class ExceptionResponse {
    String message;

    public ExceptionResponse(String message){
       this.message = message;
   }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
