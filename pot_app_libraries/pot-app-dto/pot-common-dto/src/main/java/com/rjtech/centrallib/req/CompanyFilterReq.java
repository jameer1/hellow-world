package com.rjtech.centrallib.req;

import com.rjtech.common.dto.ClientTO;

public class CompanyFilterReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1869163951033502536L;
    private String cmpName;
    private String cmpCode;

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

}
