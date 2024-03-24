package com.takamol.search.enums;

public enum Error {
    BAD_REQUEST(400),
    NOT_FOUND(404),
    SERVER_ERROR(500),
    UNKNOWN(111);

    public final int error;

    private Error(int error) {
        this.error = error;
    }
}
