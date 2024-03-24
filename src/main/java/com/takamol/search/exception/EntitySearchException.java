package com.takamol.search.exception;

import com.takamol.search.enums.Error;

public class EntitySearchException extends Exception   {
    private Error errorCode;

    public EntitySearchException(String errorMessage, Error errorCode) {

        super(errorMessage);
        this.errorCode = errorCode;
    }

    public Error getErrorCode() {
        return errorCode;
    }
}
