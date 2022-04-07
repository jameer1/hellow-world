package com.rjtech.notification.model;

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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractEntity;

// TODO Implmentation is not completed

@Entity
@Table(name = "procurement_notifications")
public class ProcurementNotificationsEntityCopy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PNTF_ID")
    private Long id;

    @Column(name = "PNTF_CODE")
    private String code;

    @Column(name = "PNTF_MODULE_CODE")
    private String moduleCode;

    @Column(name = "PNTF_REF_ID")
    private Long notifyRefId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PNTF_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PNTF_FROM_USR_ID")
    private UserMstrEntity fromUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PNTF_TO_USR_ID")
    private UserMstrEntity toUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PNTF_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "PNTF_PROCURE_CATG")
    private String procureCatg;

    @Column(name = "PNTF_PROCURE_STAGE")
    private String procureStage;

    @Column(name = "PNTF_NOTIFICATION_STATUS")
    private String notificationStatus;

    @Column(name = "PNTF_STATUS")
    private Integer status;
    
    @ManyToOne
    @JoinColumn(name = "PNTF_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PNTF_DATE", updatable = false)
    private Date date;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PNTF_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PNTF_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PNTF_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "PNTF_PRECONTRACT_ID")
    private Long preContractId;
    
    @Column(name = "PNTF_APPR_STATUS")
    private Integer apprStatus;
    
    @Column(name = "PNTF_REQ_COMMENTS")
    private String reqComments;
    
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

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Long getNotifyRefId() {
        return notifyRefId;
    }

    public void setNotifyRefId(Long notifyRefId) {
        this.notifyRefId = notifyRefId;
    }

    public String getProcureCatg() {
        return procureCatg;
    }

    public void setProcureCatg(String procureCatg) {
        this.procureCatg = procureCatg;
    }

    public String getProcureStage() {
        return procureStage;
    }

    public void setProcureStage(String procureStage) {
        this.procureStage = procureStage;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
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
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public UserMstrEntity getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(UserMstrEntity fromUserId) {
        this.fromUserId = fromUserId;
    }

    public UserMstrEntity getToUserId() {
        return toUserId;
    }

    public void setToUserId(UserMstrEntity toUserId) {
        this.toUserId = toUserId;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }
    
    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }
    
    public Integer getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(Integer apprStatus) {
        this.apprStatus = apprStatus;
    }
    
    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

}
