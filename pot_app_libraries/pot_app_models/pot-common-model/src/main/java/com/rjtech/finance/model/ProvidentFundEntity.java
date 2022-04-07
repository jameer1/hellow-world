package com.rjtech.finance.model;

import java.io.Serializable;
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
@Table(name = "finance_provident_fund")
public class ProvidentFundEntity implements Serializable {

    private static final long serialVersionUID = 4778832213745394806L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FPFM_ID")
    private Long id;

    @Column(name = "FPFM_CODE")
    private String code;

    @Column(name = "FPFM_NAME")
    private String name;

    @Column(name = "FPFM_COMMENTS")
    private String comments;

    @Column(name = "FPFM_TAX_STATUS")
    private String taxStatus;

    @Column(name = "FPFM_CREDIT_CYCLE")
    private String creditCycle;

    @Column(name = "FPFM_CREDIT_DATE")
    private String creditDate;

    @Column(name = "FPFM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FPFM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FPFM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPFM_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPFM_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "FPFM_FTCT_ID")
    private CodeTypesEntity codeTypesEntity;

    public ProvidentFundEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public String getCreditCycle() {
        return creditCycle;
    }

    public void setCreditCycle(String creditCycle) {
        this.creditCycle = creditCycle;
    }

    public String getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(String creditDate) {
        this.creditDate = creditDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CodeTypesEntity getCodeTypesEntity() {
        return codeTypesEntity;
    }

    public void setCodeTypesEntity(CodeTypesEntity codeTypesEntity) {
        this.codeTypesEntity = codeTypesEntity;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }
}
