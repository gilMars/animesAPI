package com.animesgun.exceptions;

import org.springframework.http.HttpStatus;

public class ResponseError {

    private HttpStatus status;
    private String message;

    public ResponseError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

}
