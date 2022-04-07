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
//import com.rjtech.finance.model.NonRegularPayAllowanceEntityCopy;
import com.rjtech.finance.model.NonRegularPayAllowanceEntity;
//import com.rjtech.finance.model.NonRegularPayAllowanceEntityCopy;

@Entity
@Table(name = "emp_nregular_pay_dtl")
public class EmpNonRegularPayDetailEntity implements Serializable {

    private static final long serialVersionUID = -802945799544855015L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENPM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ENPM_FNRA_ID")
    private NonRegularPayAllowanceEntity regularPayAllowanceEntity;

    @Column(name = "ENPM_BONUS_PAY")
    private BigDecimal bonusPay;

    @Column(name = "ENPM_FIXED_PAY")
    private BigDecimal fixedPay;

    @Column(name = "ENPM_APPLICABLE")
    private String applicable;

    @Column(name = "ENPM_COMMENTS")
    private String comments;

    @Column(name = "ENPM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ENPM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENPM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ENPM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "ENPM_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "ENPM_ENPR_ID")
    private EmpNonRegularPayRateEntity empNonRegularPayRateEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NonRegularPayAllowanceEntity getRegularPayAllowanceEntity() {
        return regularPayAllowanceEntity;
    }

    public void setRegularPayAllowanceEntity(NonRegularPayAllowanceEntity allowanceEntity) {
        this.regularPayAllowanceEntity = allowanceEntity;
    }

    public BigDecimal getBonusPay() {
        return bonusPay;
    }

    public void setBonusPay(BigDecimal bonusPay) {
        this.bonusPay = bonusPay;
    }

    public BigDecimal getFixedPay() {
        return fixedPay;
    }

    public void setFixedPay(BigDecimal fixedPay) {
        this.fixedPay = fixedPay;
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

    public EmpNonRegularPayRateEntity getEmpNonRegularPayRateEntity() {
        return empNonRegularPayRateEntity;
    }

    public void setEmpNonRegularPayRateEntity(EmpNonRegularPayRateEntity empNonRegularPayRateEntity) {
        this.empNonRegularPayRateEntity = empNonRegularPayRateEntity;
    }

}
