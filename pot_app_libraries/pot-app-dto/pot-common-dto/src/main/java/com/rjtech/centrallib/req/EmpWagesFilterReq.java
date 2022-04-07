package com.rjtech.centrallib.req;

import java.io.Serializable;
import com.rjtech.common.dto.ClientTO;

public class EmpWagesFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6427928557666229869L;
    private String empWageName;
    private String empWageCode;

    public String getEmpWageName() {
        return empWageName;
    }

    public void setEmpWageName(String empWageName) {
        this.empWageName = empWageName;
    }

    public String getEmpWageCode() {
        return empWageCode;
    }

    public void setEmpWageCode(String empWageCode) {
        this.empWageCode = empWageCode;
    }

}
