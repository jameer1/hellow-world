package com.rjtech.centrallib.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class MesureFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8054854110376498823L;
    private String mesureName;
    private String mesureCode;
    private String procureClassName;

    public String getMesureName() {
        return mesureName;
    }

    public void setMesureName(String mesureName) {
        this.mesureName = mesureName;
    }

    public String getMesureCode() {
        return mesureCode;
    }

    public void setMesureCode(String mesureCode) {
        this.mesureCode = mesureCode;
    }

    public String getProcureClassName() {
        return procureClassName;
    }

    public void setProcureClassName(String procureClassName) {
        this.procureClassName = procureClassName;
    }

}
