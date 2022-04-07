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

@Entity
@Table(name = "emp_provident_fund_tax")
public class EmpProvidentFundTaxEntity implements Serializable {

    private static final long serialVersionUID = -6711592227267110617L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPT_ID")
    private Long id;

    @Column(name = "EPT_PERCENTAGE")
    private BigDecimal percentage;

    @Column(name = "EPT_FIXED_AMOUNT")
    private BigDecimal fixedAmount;

    @Column(name = "EPT_COMMENTS")
    private String comments;

    @Column(name = "EPT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EPT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPT_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EPT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPT_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "EPT_EPFD_ID")
    private EmpProvidentFundEntity empProvidentFundEntity;

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
