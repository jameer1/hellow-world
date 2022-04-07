package com.rjtech.register.emp.dto;

import java.io.Serializable;

public class EmpPayDeductionQuestionTO implements Serializable {

    private static final long serialVersionUID = 4768660206431955600L;
    private Long id;
    private Long payDeductionId;
    private EmpPayQuestionMstrTO empPayQuestionMstrTO = new EmpPayQuestionMstrTO();
    private String comments;
    private String toDate;
    private String fromDate;
    private boolean latest;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Long getPayDeductionId() {
        return payDeductionId;
    }

    public void setPayDeductionId(Long payDeductionId) {
        this.payDeductionId = payDeductionId;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public EmpPayQuestionMstrTO getEmpPayQuestionMstrTO() {
        return empPayQuestionMstrTO;
    }

    public void setEmpPayQuestionMstrTO(EmpPayQuestionMstrTO empPayQuestionMstrTO) {
        this.empPayQuestionMstrTO = empPayQuestionMstrTO;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
