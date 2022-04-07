package com.rjtech.register.emp.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "emp_provident_fund_contribution")
public class EmpProvidentFundContributionEntity implements Serializable {

    private static final long serialVersionUID = -6711592227267110617L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ECFM_ID")
    private Long id;

    @Column(name = "ECFM_PERCENTAGE")
    private BigDecimal percentage;

    @Column(name = "ECFM_FIXED_AMOUNT")
    private BigDecimal fixedAmount;

    @Column(name = "ECFM_COMMENTS")
    private String comments;

    @Column(name = "ECFM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ECFM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ECFM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ECFM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ECFM_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "ECFM_EPFM_ID")
    private EmpProvidentFundEntity empProvidentFundEntity;

    @ManyToOne
    @JoinColumn(name = "ECFM_FPFM_ID")
    private ProvidentFundEntity providentFundEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpProvidentFundEntity getEmpProvidentFundEntity() {
        return empProvidentFundEntity;
    }

    public void setEmpProvidentFundEntity(EmpProvidentFundEntity empProvidentFundEntity) {
        this.empProvidentFundEntity = empProvidentFundEntity;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getStatus() {
        return status;
    }

    public ProvidentFundEntity getProvidentFundEntity() {
        return providentFundEntity;
    }

    public void setProvidentFundEntity(ProvidentFundEntity providentFundEntity) {
        this.providentFundEntity = providentFundEntity;
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

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

}
