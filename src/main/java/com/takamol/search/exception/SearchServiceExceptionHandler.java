package com.takamol.search.exception;

import com.takamol.search.enums.Error;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebInputException;
//import org.springframework.web.util.WebUtils;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@RestControllerAdvice
public class SearchServiceExceptionHandler  {
    @ExceptionHandler(EntitySearchException.class)
    public ResponseEntity< Map<String, String> > handleException(EntitySearchException ex) {
        //return ResponseEntity.ok("Error From Controller Advice : " + ex.getMessage());
        //HttpHeaders headers = new HttpHeaders();

        log.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        if (ex.getErrorCode()  == Error.NOT_FOUND) {
            //List<String> errors = Collections.singletonList(ex.getMessage());
            return generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        }else {

            log.warn("Unknown exception type: " + ex.getClass().getName());

            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity< Map<String, String> > handleException(Exception ex) {
        //return ResponseEntity.ok("Error From Controller Advice : " + ex.getMessage());
        //HttpHeaders headers = new HttpHeaders();

        log.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());


            log.warn("Unknown exception type: " + ex.getClass().getName());

            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
   // @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        log.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        Map<String,String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(validationError -> errorMap.put(validationError.getField(),validationError.getDefaultMessage()));


        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity handleValidationException(ServerWebInputException ex) {

        log.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

       // Map<String,String> errorMap = new HashMap<>();
        //ex.getBindingResult().getFieldErrors().forEach(validationError -> errorMap.put(validationError.getField(),validationError.getDefaultMessage()));


        return ResponseEntity.badRequest().body(generateResponse(ex.getMessage(),HttpStatus.BAD_REQUEST));
    }
    /*protected ResponseEntity<String> generateResponse(Exception ex, @Nullable String body, HttpStatus status,
                                                               WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }*/
    protected  ResponseEntity<Map<String, String>> generateResponse(String message, HttpStatus status) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("message", message);
        map.put("status", String.valueOf(status.value()));
        //map.put("data", responseObj);

        return new ResponseEntity<Map<String, String>>(map,status);
    }
}
