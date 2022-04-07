package com.rjtech.register.emp.model;

import java.io.Serializable;
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
@Table(name = "emp_bank_account_dtl")
public class EmpBankAccountDtlEntity implements Serializable {

    private static final long serialVersionUID = 5492202133146524907L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EBAD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EBAD_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @Column(name = "EBAD_BANK_NAME")
    private String bankName;

    @Column(name = "EBAD_ADDRESS")
    private String address;

    @Column(name = "EBAD_IFSC_CODE")
    private String ifscCode;

    @Column(name = "EBAD_ACC_NAME")
    private String accName;

    @Column(name = "EBAD_ACC_NUM")
    private Long accNumber;

    @Column(name = "EBAD_ACC_TYPE")
    private String accType;

    @Column(name = "EBAD_ACC_STATUS")
    private String accStatus;

    @Column(name = "EBAD_COMMENTS")
    private String accComments;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EBAD_FROM_DATE")
    private Date fromDate;

    @Column(name = "EBAD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EBAD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EBAD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EBAD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "EBAD_UPDATED_ON")
    private Timestamp updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpRegisterDtlEntity getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntity empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public Long getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(Long accNumber) {
        this.accNumber = accNumber;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getAccComments() {
        return accComments;
    }

    public void setAccComments(String accComments) {
        this.accComments = accComments;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
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
