package com.rjtech.register.emp.req;

import java.io.Serializable;

public class ProjEmpServiceHistoryReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8552569351143685363L;

    public ProjEmpServiceHistoryReq() {

    }

    private Long empId;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

}
