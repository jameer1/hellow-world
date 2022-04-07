package com.rjtech.centrallib.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class ProcureFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8585616607084556523L;
    private String ProcureName;
    private String ProcureCode;

    public String getProcureName() {
        return ProcureName;
    }

    public void setProcureName(String procureName) {
        ProcureName = procureName;
    }

    public String getProcureCode() {
        return ProcureCode;
    }

    public void setProcureCode(String procureCode) {
        ProcureCode = procureCode;
    }

}
