package com.rjtech.notification.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;

@Entity
@Table(name = "work_dairy_notifications")
public class WorkDairyNotificationsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WNTF_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WNTF_DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "WNTF_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "WNTF_STATUS")
    private Integer status;

    @Column(name = "WNTF_NTF_STATUS")
    private String notificationStatus;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "WNTF_WDM_ID")
    private WorkDairyEntity workDairyEntity;

    @Column(name = "WNTF_NTF_MSG")
    private String notificationMsg;

    @ManyToOne
    @JoinColumn(name = "WNTF_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WNTF_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "WNTF_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WNTF_UPDATED_ON")
    private Date updatedOn;

    public WorkDairyNotificationsEntity() {
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public WorkDairyEntity getWorkDairyEntity() {
        return workDairyEntity;
    }

    public void setWorkDairyEntity(WorkDairyEntity workDairyEntity) {
        this.workDairyEntity = workDairyEntity;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }

}
