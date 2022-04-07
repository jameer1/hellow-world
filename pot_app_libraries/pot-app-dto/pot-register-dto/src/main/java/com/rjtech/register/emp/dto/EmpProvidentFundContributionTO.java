package com.rjtech.register.emp.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmpProvidentFundContributionTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1005295865625030463L;
    private Long id;
    private Long providentFundId;
    private Long financeFundId;
    private BigDecimal percentage;
    private BigDecimal fixedAmount;
    private String comments;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvidentFundId() {
        return providentFundId;
    }

    public void setProvidentFundId(Long providentFundId) {
        this.providentFundId = providentFundId;
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

    public Long getFinanceFundId() {
        return financeFundId;
    }

    public void setFinanceFundId(Long financeFundId) {
        this.financeFundId = financeFundId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
