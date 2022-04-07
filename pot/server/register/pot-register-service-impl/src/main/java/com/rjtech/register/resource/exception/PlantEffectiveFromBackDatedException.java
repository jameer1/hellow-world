package com.rjtech.register.resource.exception;

import com.rjtech.common.service.exception.RJSException;

public class PlantEffectiveFromBackDatedException extends RJSException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PlantEffectiveFromBackDatedException(String code, String message) {
        super(code, message);
    }
}
