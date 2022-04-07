package com.rjtech.centrallib.model;

import java.io.Serializable;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.rjtech.centrallib.model.WeatherMstrEntity;
import com.rjtech.common.model.UserMstrEntity;


/**
 * The persistent class for the work_dairy_mstr database table.
 * 
 */
@Entity
@Table(name = "work_dairy_mstr", 
uniqueConstraints = @UniqueConstraint(columnNames={"WDM_DATE"}))
public class WorkDairyEntityWeatherCopy implements Serializable {

    private static final long serialVersionUID = -3814137502788231816L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDM_ID")
    private Long id;

    @Column(name = "WDM_CODE")
    private String code;

    @Column(name = "WDM_TYPE")
    private String contractType;

    @Temporal(TemporalType.DATE)
    @Column(name = "WDM_DATE")
    private Date workDairyDate;

    @Column(name = "WDM_APPR_STATUS")
    private String apprStatus;

    @Column(name = "WDM_CLIENT_APPR_REQUIRED")
    private boolean clientApproval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDM_CLIENT_APPR_USR_ID")
    private UserMstrEntity clientApprUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDM_INTERNAL_APPR_USR_ID")
    private UserMstrEntity internalApprUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDM_WM_ID")
    private WeatherMstrEntity weatherId;

    @Column(name = "WDM_PUR_CODE")
    private String purCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDM_REQ_USR_ID")
    private UserMstrEntity reqUserId;

    @Column(name = "WDM_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDM_UPDATED_ON")
    private Date updatedOn;

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

    public boolean isClientApproval() {
        return clientApproval;
    }

    public void setClientApproval(boolean clientApproval) {
        this.clientApproval = clientApproval;
    }

    public Date getWorkDairyDate() {
        return workDairyDate;
    }

    public void setWorkDairyDate(Date workDairyDate) {
        this.workDairyDate = workDairyDate;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getPurCode() {
        return purCode;
    }

    public void setPurCode(String purCode) {
        this.purCode = purCode;
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

    public UserMstrEntity getClientApprUserId() {
        return clientApprUserId;
    }

    public void setClientApprUserId(UserMstrEntity clientApprUserId) {
        this.clientApprUserId = clientApprUserId;
    }

    public UserMstrEntity getInternalApprUserId() {
        return internalApprUserId;
    }

    public void setInternalApprUserId(UserMstrEntity internalApprUserId) {
        this.internalApprUserId = internalApprUserId;
    }

    public WeatherMstrEntity getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(WeatherMstrEntity weatherId) {
        this.weatherId = weatherId;
    }

    public UserMstrEntity getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(UserMstrEntity reqUserId) {
        this.reqUserId = reqUserId;
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

    

    