package com.rjtech.projsettings.model;

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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "proj_procurement_approval")
public class ProcurementAddtionalTimeApprEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRA_ID")
    private Long id;

    @Column(name = "PRA_REQ_DATE")
    private Date requisitionDate;

    @Column(name = "PRA_IS_LATEST")
    private boolean latest;

    @Column(name = "PRA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PRA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PRA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PRA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PRA_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PRA_PPR_ID")
    private ProcurementNormalTimeEntity projProcurementEntity;

    @Column(name = "PRA_NOTIFICATION_MSG")
    private String notificationMessage;
    
    @Column(name = "PRA_NOTIFICATION_STATUS")
    private String notificationStatus;
    
    @Column(name = "PRA_PROCURE_STAGE")
    private String procureStage;
    
    @ManyToOne
    @JoinColumn(name = "PRA_APPR_USER")
    private UserMstrEntity apprUser;
    
    @Column(name = "PRA_EPM_ID")
    private Long projId;
    
    @ManyToOne
    @JoinColumn(name = "PRA_PRC_ID")
    private PreContractEntity preContractEntity;
    
    public ProcurementAddtionalTimeApprEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(Date requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
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

    public ProcurementNormalTimeEntity getProjProcurementEntity() {
        return projProcurementEntity;
    }

    public void setProjProcurementEntity(ProcurementNormalTimeEntity projProcurementEntity) {
        this.projProcurementEntity = projProcurementEntity;
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
    
    public String getNotificationMessage() {
    	return notificationMessage;
    }
    
    public void setNotificationMessage(String notificationMessage) {
    	this.notificationMessage = notificationMessage;
    }
    
    public String getNotificationStatus() {
    	return notificationMessage;
    }
    
    public void setNotificationStatus(String notificationStatus) {
    	this.notificationStatus = notificationStatus;
    }
    
    public String getProcureStage() {
        return procureStage;
    }

    public void setProcureStage(String procureStage) {
        this.procureStage = procureStage;
    }
    
    public UserMstrEntity getApprUser() {
        return apprUser;
    }

    public void setApprUser(UserMstrEntity apprUser) {
        this.apprUser = apprUser;
    }
    
    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }
    
    public PreContractEntity getPreContractEntity() {
        return preContractEntity;
    }

    public void setPreContractEntity(PreContractEntity preContractEntity) {
        this.preContractEntity = preContractEntity;
    }

}