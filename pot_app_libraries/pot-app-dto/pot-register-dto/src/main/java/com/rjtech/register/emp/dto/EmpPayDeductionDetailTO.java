package com.rjtech.register.emp.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class EmpPayDeductionDetailTO extends ProjectTO {

    private static final long serialVersionUID = 4768660206431955600L;
    private Long id;
    private Long payDeductionId;
    private Long financeDeductionId;
    private BigDecimal percentage;
    private BigDecimal fixedAmount;
    private String applicable;
    private String comments;
    private String toDate;
    private String fromDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPayDeductionId() {
        return payDeductionId;
    }

    public void setPayDeductionId(Long payDeductionId) {
        this.payDeductionId = payDeductionId;
    }

    public Long getFinanceDeductionId() {
        return financeDeductionId;
    }

    public void setFinanceDeductionId(Long financeDeductionId) {
        this.financeDeductionId = financeDeductionId;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public String getApplicable() {
        return applicable;
    }

    public void setApplicable(String applicable) {
        this.applicable = applicable;
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

}
