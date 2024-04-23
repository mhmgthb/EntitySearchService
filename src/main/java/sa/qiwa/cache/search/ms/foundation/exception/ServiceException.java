package sa.qiwa.cache.search.ms.foundation.exception;

import sa.qiwa.cache.search.ms.foundation.enums.ErrorCodes;

public class ServiceException extends RuntimeException   {
    private ErrorCodes errorCode;

    public ServiceException() {
        super();
    }
    public ServiceException(String errorMessage, ErrorCodes errorCode) {

        super(errorMessage);
        this.errorCode = errorCode;
    }
    public ServiceException(String message, Throwable ex, ErrorCodes errorCode) {
        super(message, ex);
        this.errorCode = errorCode;
    }
    public ServiceException(String message, Throwable ex, boolean enableSuppression, boolean writableStackTrace) {
        super(message, ex, enableSuppression, writableStackTrace);
    }
    public ServiceException(String message, Throwable ex) {
        super(message, ex);
    }
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(Throwable ex) {
        super(ex);
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }
}
