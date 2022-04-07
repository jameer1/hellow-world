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

//TODO Implmentation is not completed

@Entity
@Table(name = "req_appr_notification")
public class ReqApprNotificationEntity implements Serializable {

    private static final long serialVersionUID = -7233960745221064246L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RAN_ID")
    private Long id;

    @Column(name = "RAN_REQ_CODE")
    private String reqCode;

    @Column(name = "RAN_NOTIFY_CODE")
    private String notifyCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RAN_DATE")
    private Date date;

    @Column(name = "RAN_CATEGORY")
    private String category;

    @Column(name = "RAN_STAGE")
    private String stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAN_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAN_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAN_APPR_USER_ID")
    private UserMstrEntity apprUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAN_REQ_USER_ID")
    private UserMstrEntity reqUserId;

    @Column(name = "RAN_NOTIFIC_STATUS")
    private String notificationStatus;

    @Column(name = "RAN_STATUS")
    private Integer status;

    @Column(name = "RAN_CREATED_BY", updatable = false)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RAN_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "RAN_UPDATED_BY")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RAN_UPDATED_ON")
    private Date updatedOn;

    public ReqApprNotificationEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(String notifyCode) {
        this.notifyCode = notifyCode;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public UserMstrEntity getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(UserMstrEntity apprUserId) {
        this.apprUserId = apprUserId;
    }

    public UserMstrEntity getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(UserMstrEntity reqUserId) {
        this.reqUserId = reqUserId;
    }

}
