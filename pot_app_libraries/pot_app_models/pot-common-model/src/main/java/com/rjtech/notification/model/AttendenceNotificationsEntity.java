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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;

@Entity
@Table(name = "attendence_notifications")
public class AttendenceNotificationsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANTF_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ANTF_DATE")
    private Date date;

    @Column(name = "ANTF_TYPE")
    private String type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ANTF_FROM_USR_ID")
    private UserMstrEntity fromUserId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ANTF_TO_USR_ID")
    private UserMstrEntity toUserId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ANTF_FROM_DATE")
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ANTF_TO_DATE")
    private Date toDate;

    @Column(name = "ANTF_REQ_COMMENTS")
    private String reqComments;

    @Column(name = "ANTF_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ANTF_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ANTF_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ANTF_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ANTF_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "ANTF_NOTIFICATION_STATUS")
    private String notificationStatus;

    @Column(name = "ANTF_NOTIFICATION_MSG")
    private String notificationMsg;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ANTF_CRW_ID")
    private ProjCrewMstrEntity projCrewMstrEntity;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReqComments() {
        return reqComments;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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

    public AttendenceNotificationsEntity() {
    }

    public ProjCrewMstrEntity getProjCrewMstrEntity() {
        return projCrewMstrEntity;
    }

    public void setProjCrewMstrEntity(ProjCrewMstrEntity projCrewMstrEntity) {
        this.projCrewMstrEntity = projCrewMstrEntity;
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
