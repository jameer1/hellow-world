package com.rjtech.notification.model;

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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
//import com.rjtech.notification.timemanagement.timesheet.model.TimeSheetEntityCopy;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEntity;

@Entity
@Table(name = "time_sheet_notifications")
public class TimeSheetNotificationsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TNTF_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TNTF_DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "TNTF_CRM_ID")
    private ClientRegEntity clientId;

    @Column(name = "TNTF_NOTIFICATION_STATUS")
    private String notificationStatus;

    @Column(name = "TNTF_NTF_MSG")
    private String notificationMsg;

    @Column(name = "TNTF_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "TNTF_CREATED_BY")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TNTF_CREATED_ON")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "TNTF_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TNTF_UPDATE_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "TNTF_TSM_ID")
    private TimeSheetEntity timeSheetEntity;

    public TimeSheetNotificationsEntity() {
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

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }

    public TimeSheetEntity getTimeSheetEntity() {
        return timeSheetEntity;
    }

    public void setTimeSheetEntity(TimeSheetEntity timeSheetEntity) {
        this.timeSheetEntity = timeSheetEntity;
    }

}
