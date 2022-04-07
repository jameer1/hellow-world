package com.rjtech.register.resource.exception;

import com.rjtech.common.service.exception.RJSException;

public class PlantProjectDetailsNotFoundException extends RJSException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PlantProjectDetailsNotFoundException(String code, String message) {
        super(code, message);
    }

}
