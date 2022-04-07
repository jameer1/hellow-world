package com.rjtech.timemanagement.timesheet.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class TimeSheetEmpExpenseTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long empDtlId;
    private Long costId;
    private String date;
    private BigDecimal amount;
    private String comments;
    private Long parentId;
    private Long apprUsrId;
    private String apprStatus;

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

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getApprUsrId() {
        return apprUsrId;
    }

    public void setApprUsrId(Long apprUsrId) {
        this.apprUsrId = apprUsrId;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

}