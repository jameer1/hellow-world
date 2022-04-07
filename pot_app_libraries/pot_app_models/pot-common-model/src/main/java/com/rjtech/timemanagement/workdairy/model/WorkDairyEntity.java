package com.rjtech.timemanagement.workdairy.model;

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
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.projectlib.model.ProjWorkShiftMstrEntity;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
//import com.rjtech.projectlib.model.ProjWorkShiftMstrEntityCopy;

/**
 * The persistent class for the work_dairy_mstr database table.
 * 
 */
@Entity
@Table(name = "work_dairy_mstr", 
uniqueConstraints = @UniqueConstraint(columnNames={"WDM_DATE", "WDM_CRW_ID", "WDM_EPM_ID"}))
public class WorkDairyEntity implements Serializable {

    private static final long serialVersionUID = -3814137502788231816L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDM_ID")
    private Long id;

    @Column(name = "WDM_CODE")
    private String code;

    @Column(name = "WDM_TYPE")
    private String contractType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDM_DATE")
    private Date workDairyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDM_CRW_ID")
    private ProjCrewMstrEntity crewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDM_EPM_ID")
    private ProjMstrEntity projId;

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
    @JoinColumn(name = "WDM_SHF_ID")
    private ProjWorkShiftMstrEntity shiftId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDM_WM_ID")
    private WeatherMstrEntity weatherId;

    @Column(name = "WDM_PUR_CODE")
    private String purCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDM_PUR_ID")
    private PurchaseOrderEntity purId;

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

    public ProjCrewMstrEntity getCrewId() {
        return crewId;
    }

    public void setCrewId(ProjCrewMstrEntity crewId) {
        this.crewId = crewId;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
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

    public ProjWorkShiftMstrEntity getShiftId() {
        return shiftId;
    }

    public void setShiftId(ProjWorkShiftMstrEntity shiftId) {
        this.shiftId = shiftId;
    }

    public WeatherMstrEntity getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(WeatherMstrEntity weatherId) {
        this.weatherId = weatherId;
    }

    public PurchaseOrderEntity getPurId() {
        return purId;
    }

    public void setPurId(PurchaseOrderEntity purId) {
        this.purId = purId;
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