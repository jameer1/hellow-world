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

import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "finance_emp_wise_cycle")
public class EmployeeWiseCycleEntity implements Serializable {

    private static final long serialVersionUID = -7048542791119494549L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEWC_ID")
    private Long id;

    @Column(name = "FEWC_TAX_CODE")
    private String taxCode;

    @Column(name = "FEWC_TAX_DESC")
    private String taxDesc;

    @ManyToOne
    @JoinColumn(name = "FEWC_PCD_ID")
    private ProcureCatgDtlEntity procureCategoryId;

    @Column(name = "FEWC_EMP_CATEGORY")
    private String empCategory;

    @Column(name = "FEWC_COMMENTS")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "FEWC_FPCM_ID")
    private PayRollEntity payRollEntityId;

    @ManyToOne
    @JoinColumn(name = "FEWC_FTCT_ID")
    private CodeTypesEntity codeTypesEntity;

    @Column(name = "FEWC_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FEWC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FEWC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEWC_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEWC_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxDesc() {
        return taxDesc;
    }

    public void setTaxDesc(String taxDesc) {
        this.taxDesc = taxDesc;
    }

    public CodeTypesEntity getCodeTypesEntity() {
        return codeTypesEntity;
    }

    public void setCodeTypesEntity(CodeTypesEntity codeTypesEntity) {
        this.codeTypesEntity = codeTypesEntity;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ProcureCatgDtlEntity getProcureCategoryId() {
        return procureCategoryId;
    }

    public void setProcureCategoryId(ProcureCatgDtlEntity procureCategoryId) {
        this.procureCategoryId = procureCategoryId;
    }

    public String getEmpCategory() {
        return empCategory;
    }

    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
    }

    public PayRollEntity getPayRollEntityId() {
        return payRollEntityId;
    }

    public void setPayRollEntityId(PayRollEntity payRollEntityId) {
        this.payRollEntityId = payRollEntityId;
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
