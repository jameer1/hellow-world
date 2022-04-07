package com.rjtech.mw.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rjtech.exception.root.RjsException;
import com.rjtech.exception.root.RjsUnHandledException;
import com.rjtech.mw.app.exception.AppMWException;
import com.rjtech.mw.error.resp.message.AppErrorMessage;

@ControllerAdvice
public class AppMWControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppMWControllerAdvice.class);

    @ExceptionHandler(AppMWException.class)
    public ResponseEntity<AppErrorMessage> handleException(AppMWException appMWException) {
        LOGGER.error("########## RjtechMWControllerAdvice handleException >>>>>Exception:: Reason - "
                + appMWException.getMessage());
        HttpStatus httpStatus = HttpStatus.OK;
        // if error code is 500 then throw INTERNAL_SERVER_ERROR
        if (String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()).equals(appMWException.getCode())) {
            httpStatus = HttpStatus.PRECONDITION_FAILED;
        }
        return new ResponseEntity<AppErrorMessage>(new AppErrorMessage(appMWException.getCode(),
                appMWException.getMessage(), appMWException.getErrorType()), httpStatus);
    }

    @ExceptionHandler(RjsException.class)
    public ResponseEntity<AppErrorMessage> handleException(RjsException rjsException) {
        LOGGER.error("########## RjtechMWControllerAdvice handleException >>>>>RjsException:: Reason - "
                + rjsException.getMessage());
        return new ResponseEntity<AppErrorMessage>(
                new AppErrorMessage(rjsException.getCode(), rjsException.getMessage(), rjsException.getErrorType()),
                HttpStatus.OK);
    }

    @ExceptionHandler(RjsUnHandledException.class)
    public ResponseEntity<AppErrorMessage> handleException(RjsUnHandledException rjsUnHandledException) {
        LOGGER.error("########## RjtechMWControllerAdvice handleException >>>>>RjsUnHandledException:: Reason - "
                + rjsUnHandledException.getMessage());
        return new ResponseEntity<AppErrorMessage>(new AppErrorMessage(rjsUnHandledException.getCode(),
                rjsUnHandledException.getMessage(), rjsUnHandledException.getErrorType()), HttpStatus.OK);
    }
}
