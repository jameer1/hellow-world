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
@Table(name = "proj_soe_approval")
public class SoeAddtionalTimeApprEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PSA_ID")
    private Long id;

    @Column(name = "PSA_REQ_DATE")
    private Date requisitionDate;

    @Column(name = "PSA_IS_LATEST")
    private boolean latest;

    @Column(name = "PSA_STATUS")
    private String status;

    @ManyToOne
    @JoinColumn(name = "PSA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PSA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSA_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PSA_SOE_ID")
    private SchofEstimateNormalTimeEntity schofEstimateNormalTimeEntity;
    
    @Column(name = "PSA_NOTIFICATION_MSG")
    private String notificationMessage;
    
    @Column(name = "PSA_NOTIFICATION_STATUS")
    private String notificationStatus;
   
    @ManyToOne
    @JoinColumn(name = "PSA_APPR_USER")
    private UserMstrEntity apprUser;
    
    @Column(name = "PSA_EPM_ID")
    private Long projId;
 
    @ManyToOne
    @JoinColumn(name = "PSA_SNTF_ID")
    private SoeNotificationsEntity soeNotificationsEntity;

    public SoeAddtionalTimeApprEntity() {

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public SchofEstimateNormalTimeEntity getSchofEstimateNormalTimeEntity() {
        return schofEstimateNormalTimeEntity;
    }

    public void setSchofEstimateNormalTimeEntity(SchofEstimateNormalTimeEntity schofEstimateNormalTimeEntity) {
        this.schofEstimateNormalTimeEntity = schofEstimateNormalTimeEntity;
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
  
    public SoeNotificationsEntity getSoeNotificationsEntity() {
        return soeNotificationsEntity;
    }

    public void setSoeNotificationsEntity(SoeNotificationsEntity soeNotificationsEntity) {
        this.soeNotificationsEntity = soeNotificationsEntity;
    }

}