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
@Table(name = "finance_tax_payment_dtl")
public class TaxPaymentReceiverEntity implements Serializable {

    private static final long serialVersionUID = 7502523848421517392L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FTPD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FTPD_FTCT_ID")
    private CodeTypesEntity codeTypesEntity;

    @Column(name = "FTPD_CODE")
    private String taxCode;

    @Column(name = "FTPD_DESC")
    private String taxDesc;

    @Column(name = "FTPD_DEPT_NAME")
    private String deptName;

    @Column(name = "FTPD_REG_ADDRESS")
    private String regAddr;

    @Column(name = "FTPD_ACC_NUM")
    private String accNumber;

    @Column(name = "FTPD_BANK_NAME")
    private String bankName;

    @Column(name = "FTPD_BANK_CODE")
    private String bankCode;

    @Column(name = "FTPD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FTPD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FTPD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FTPD_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FTPD_UPDATED_ON")
    private Date updatedOn;

    public TaxPaymentReceiverEntity() {

    }

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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
