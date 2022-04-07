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

@Entity
@Table(name = "emp_regular_pay_dtl")
public class EmpRegularPayRateDetailEntity implements Serializable {

    private static final long serialVersionUID = -5863060048205000538L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ERPM_ID")
    private Long id;

    // TODO check and remove this
    @Column(name = "ERPM_FRPM_ID")
    private Long financeRegId;

    @Column(name = "ERPM_PERCENTAGE")
    private BigDecimal percentage;

    @Column(name = "ERPM_FIXED_AMOUNT")
    private BigDecimal fixedAmount;

    @Column(name = "ERPM_TAX_APPLICABLE")
    private String applicable;

    @Column(name = "ERPM_COMMENTS")
    private String comments;

    @Column(name = "ERPM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ERPM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ERPM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ERPM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "ERPM_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "ERPM_ERPR_ID")
    private EmpRegularPayRateEntity empRegularPayRateEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EmpRegularPayRateEntity getEmpRegularPayRateEntity() {
        return empRegularPayRateEntity;
    }

    public void setEmpRegularPayRateEntity(EmpRegularPayRateEntity empRegularPayRateEntity) {
        this.empRegularPayRateEntity = empRegularPayRateEntity;
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

}
