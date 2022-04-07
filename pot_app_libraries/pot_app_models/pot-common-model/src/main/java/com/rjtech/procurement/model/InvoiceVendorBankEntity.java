package com.rjtech.procurement.model;

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
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "project_invoice_bank_details")
public class InvoiceVendorBankEntity implements Serializable {

    private static final long serialVersionUID = 1510576643970752797L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PIBD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PIBD_PUR_ID")
    private PurchaseOrderEntity purId;

    @ManyToOne
    @JoinColumn(name = "PIBD_EPM_ID")
    private ProjMstrEntity projId;

    @Column(name = "PIBD_BANK_NAME")
    private String bankName;

    @Column(name = "PIBD_ACC_NAME")
    private String accountName;

    @Column(name = "PIBD_BANK_CODE")
    private String bankCode;

    @Column(name = "PIBD_ACC_NUM")
    private Long accountNum;

    @Column(name = "PIBD_ACC_DTLS_VERIFIED")
    private Integer accDetailsVerified;

    @ManyToOne
    @JoinColumn(name = "PIBD_APPROVER_ID")
    private UserMstrEntity apprId;

    @Column(name = "PIBD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PIBD_CRETARED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PIBD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PIBD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PIBD_UPDATED_ON")
    private Date updatedOn;

    public InvoiceVendorBankEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getAccDetailsVerified() {
        return accDetailsVerified;
    }

    public void setAccDetailsVerified(Integer accDetailsVerified) {
        this.accDetailsVerified = accDetailsVerified;
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

    public Long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Long accountNum) {
        this.accountNum = accountNum;
    }

    public PurchaseOrderEntity getPurId() {
        return purId;
    }

    public void setPurId(PurchaseOrderEntity purId) {
        this.purId = purId;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
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

    public UserMstrEntity getApprId() {
        return apprId;
    }

    public void setApprId(UserMstrEntity apprId) {
        this.apprId = apprId;
    }

}