package com.rjtech.register.emp.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class EmpPaybleRateDetailTO extends ProjectTO {

    private static final long serialVersionUID = 7980674134093964632L;
    private Long id;
    private Long empPaybaleRateId;
    private Long financeRegId;
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

    public Long getEmpPaybaleRateId() {
        return empPaybaleRateId;
    }

    public void setEmpPaybaleRateId(Long empPaybaleRateId) {
        this.empPaybaleRateId = empPaybaleRateId;
    }

    public Long getFinanceRegId() {
        return financeRegId;
    }

    public void setFinanceRegId(Long financeRegId) {
        this.financeRegId = financeRegId;
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

}
