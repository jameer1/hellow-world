package com.rjtech.centrallib.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class MaterialClassFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8054854110376498823L;
    private String materialName;
    private String materialCode;
    private Integer materialClassId;

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public Integer getMaterialClassId() {
        return materialClassId;
    }

    public void setMaterialClassId(Integer materialClassId) {
        this.materialClassId = materialClassId;
    }

}
