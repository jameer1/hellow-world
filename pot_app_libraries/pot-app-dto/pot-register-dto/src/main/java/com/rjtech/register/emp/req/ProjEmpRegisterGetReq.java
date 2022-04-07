package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjEmpRegisterGetReq extends ProjectTO {

    private static final long serialVersionUID = -7764588560956931548L;
    private Long empId;
    private Long purchaseOrderId;
    private Long apprStatus;
    private Long requserId;
    private String fromDate;
    private String toDate;
    private Long id;
    private Integer status;
    private List<Long> empIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public List<Long> getEmpIds() {
        return empIds;
    }

    public void setEmpIds(List<Long> empIds) {
        this.empIds = empIds;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(Long apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Long getRequserId() {
        return requserId;
    }

    public void setRequserId(Long requserId) {
        this.requserId = requserId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
