package com.rjtech.centrallib.req;

import com.rjtech.common.dto.ClientTO;

public class FinanceCenterCodeGetReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String provinceCode;
    
    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

}
