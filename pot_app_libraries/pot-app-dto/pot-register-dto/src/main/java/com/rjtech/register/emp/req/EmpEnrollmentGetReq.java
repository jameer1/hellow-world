package com.rjtech.register.emp.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmpEnrollmentGetReq implements Serializable {

    private static final long serialVersionUID = 1L;

    public EmpEnrollmentGetReq() {

    }

    private List<Long> projList = new ArrayList<Long>();

    private Long empId;

    private Integer status;

    public Long getEmpId() {
        return empId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Long> getProjList() {
        return projList;
    }

    public void setProjList(List<Long> projList) {
        this.projList = projList;
    }

}
