package com.rjtech.centrallib.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class EmpClassFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6483281368154481695L;
    private String empName;
    private String empCode;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

}
