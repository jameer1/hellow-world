package com.rjtech.centrallib.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class PlantClassFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8872235565392626039L;
    private String plantName;
    private String plantCode;
    private String plantUom;

    public String getPlantUom() {
        return plantUom;
    }

    public void setPlantUom(String plantUom) {
        this.plantUom = plantUom;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

}
