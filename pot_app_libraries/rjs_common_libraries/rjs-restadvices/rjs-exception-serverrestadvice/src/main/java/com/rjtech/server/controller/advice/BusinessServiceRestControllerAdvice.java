package com.rjtech.server.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rjtech.exception.root.RjsException;
import com.rjtech.exception.root.RjsUnHandledException;

@ControllerAdvice
public class BusinessServiceRestControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessServiceRestControllerAdvice.class);

    @ExceptionHandler(RjsException.class)
    public ResponseEntity<?> handleException(RjsException unhandledException) {
        LOGGER.error("########## BusinessServiceRestControllerAdvice handleException >>>>>Exception:: Reason - ",
                unhandledException.getMessage());
        return new ResponseEntity(getRjsExceptionErrorHeaders(unhandledException), HttpStatus.OK);

    }

    @ExceptionHandler(RjsUnHandledException.class)
    public ResponseEntity<?> handleException(RjsUnHandledException rjsUnHandledException) {
        LOGGER.error("########## BusinessServiceRestControllerAdvice handleException >>>>>Exception:: Reason - "
                + rjsUnHandledException.getMessage());
        return new ResponseEntity(getRjsUnHandledException(rjsUnHandledException), HttpStatus.OK);

    }

    private MultiValueMap<String, String> getRjsUnHandledException(RjsUnHandledException rjsUnHandledException) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("code", rjsUnHandledException.getCode());
        params.set("message", rjsUnHandledException.getMessage());
        params.set("errorType", rjsUnHandledException.getErrorType());
        return params;
    }

    private MultiValueMap<String, String> getRjsExceptionErrorHeaders(RjsException rjsException) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("code", rjsException.getCode());
        params.set("message", rjsException.getMessage());
        params.set("errorType", rjsException.getErrorType());
        return params;
    }

}
