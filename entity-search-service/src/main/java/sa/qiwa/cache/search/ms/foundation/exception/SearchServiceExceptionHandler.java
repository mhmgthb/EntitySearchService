package sa.qiwa.cache.search.ms.foundation.exception;

import org.springframework.http.ResponseEntity;

public interface SearchServiceExceptionHandler {
    public ResponseEntity handleProductNotFoundException(ServiceException ex);

}
