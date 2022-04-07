package com.rjtech.register.material.req;

import java.io.Serializable;

public class MaterialFilterResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String inputFrom;
    private String inputTO;

    public String getInputFrom() {
        return inputFrom;
    }

    public String getInputTO() {
        return inputTO;
    }

    public void setInputFrom(String inputFrom) {
        this.inputFrom = inputFrom;
    }

    public void setInputTO(String inputTO) {
        this.inputTO = inputTO;
    }

}
