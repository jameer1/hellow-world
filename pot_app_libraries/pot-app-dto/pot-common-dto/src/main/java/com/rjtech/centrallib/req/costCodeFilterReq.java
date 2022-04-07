package com.rjtech.centrallib.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class costCodeFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1142030956217707045L;
    private String costCodeName;
    private String costCodeCode;

    public String getCostCodeName() {
        return costCodeName;
    }

    public void setCostCodeName(String costCodeName) {
        this.costCodeName = costCodeName;
    }

    public String getCostCodeCode() {
        return costCodeCode;
    }

    public void setCostCodeCode(String costCodeCode) {
        this.costCodeCode = costCodeCode;
    }

}
