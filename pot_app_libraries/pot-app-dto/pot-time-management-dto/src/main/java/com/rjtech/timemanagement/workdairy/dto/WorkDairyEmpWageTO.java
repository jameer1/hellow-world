package com.rjtech.timemanagement.workdairy.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkDairyEmpWageTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long empDtlId;
    private Long wageId;
    private Long parentId;
    private Long userId;
    private String apprStatus;
    private String apprComments;
    private Integer status;
    private double usedTotal;
    private double idleTotal;
    private String code;

    private List<WorkDairyEmpCostDtlTO> workDairyEmpCostDtlTOs = new ArrayList<WorkDairyEmpCostDtlTO>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpDtlId() {
        return empDtlId;
    }

    public void setEmpDtlId(Long empDtlId) {
        this.empDtlId = empDtlId;
    }

    public Long getWageId() {
        return wageId;
    }

    public void setWageId(Long wageId) {
        this.wageId = wageId;
    }

    public List<WorkDairyEmpCostDtlTO> getWorkDairyEmpCostDtlTOs() {
        return workDairyEmpCostDtlTOs;
    }

    public void setWorkDairyEmpCostDtlTOs(List<WorkDairyEmpCostDtlTO> workDairyEmpCostDtlTOs) {
        this.workDairyEmpCostDtlTOs = workDairyEmpCostDtlTOs;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public double getUsedTotal() {
        return usedTotal;
    }

    public void setUsedTotal(double usedTotal) {
        this.usedTotal = usedTotal;
    }

    public double getIdleTotal() {
        return idleTotal;
    }

    public void setIdleTotal(double idleTotal) {
        this.idleTotal = idleTotal;
    }

}