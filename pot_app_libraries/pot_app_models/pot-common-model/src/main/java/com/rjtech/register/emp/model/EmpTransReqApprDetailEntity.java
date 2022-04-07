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
@Table(name = "emp_trans_req_appr_dtl")
public class EmpTransReqApprDetailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ETRAD_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETRAD_EXPECT_TRANS_DATE")
    private Date expectedTransDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETRAD_TRANSFER_DATE")
    private Date transDate;

    @Column(name = "ETRAD_COMMENTS")
    private String comments;

    @Column(name = "ETRAD_APPR_STATUS")
    private String apprStatus;

    @Column(name = "ETRAD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ETRAD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETRAD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ETRAD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "ETRAD_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "ETRAD_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "ETRAD_ETRA_ID")
    private EmpTransferReqApprEntity empTransferReqApprEntity;

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

    public Date getExpectedTransDate() {
        return expectedTransDate;
    }

    public void setExpectedTransDate(Date expectedTransDate) {
        this.expectedTransDate = expectedTransDate;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
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

    public EmpTransferReqApprEntity getEmpTransferReqApprEntity() {
        return empTransferReqApprEntity;
    }

    public void setEmpTransferReqApprEntity(EmpTransferReqApprEntity empTransferReqApprEntity) {
        this.empTransferReqApprEntity = empTransferReqApprEntity;
    }

}
