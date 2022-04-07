package com.rjtech.register.emp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.finance.model.ProvidentFundEntity;
//import com.rjtech.finance.model.ProvidentFundEntityCopy;

@Entity
@Table(name = "emp_pay_deduction_dtl")
public class EmpPayDeductionDtlEntity implements Serializable {

    private static final long serialVersionUID = -802945799544855015L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPDD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EPDD_FPFM_ID")
    private ProvidentFundEntity providentFundEntity;

    @Column(name = "EPDD_APPLICABLE")
    private String applicable;

    @Column(name = "EPDD_BONUS_PAY")
    private BigDecimal percentage;

    @Column(name = "EPDD_FIXED_PAY")
    private BigDecimal fixedPay;

    @Column(name = "EPDD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EPDD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPDD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EPDD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "EPDD_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "EPDD_EPD_ID")
    private EmpPayDeductionEntity empPayDeductionEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFixedPay() {
        return fixedPay;
    }

    public void setFixedPay(BigDecimal fixedPay) {
        this.fixedPay = fixedPay;
    }

    public ProvidentFundEntity getProvidentFundEntity() {
        return providentFundEntity;
    }

    public void setProvidentFundEntity(ProvidentFundEntity providentFundEntity) {
        this.providentFundEntity = providentFundEntity;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public String getApplicable() {
        return applicable;
    }

    public void setApplicable(String applicable) {
        this.applicable = applicable;
    }

    public EmpPayDeductionEntity getEmpPayDeductionEntity() {
        return empPayDeductionEntity;
    }

    public void setEmpPayDeductionEntity(EmpPayDeductionEntity empPayDeductionEntity) {
        this.empPayDeductionEntity = empPayDeductionEntity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

}
