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
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "plant_notifications")
public class PlantNotificationsEntityCopy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLNT_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLNT_DATE")
    private Date date;

    @Column(name = "PLNT_CODE")
    private String code;

    @ManyToOne
    @JoinColumn(name = "PLNT_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "PLNT_APPR_USR_ID")
    private UserMstrEntity apprUserId;

    @ManyToOne
    @JoinColumn(name = "PLNT_REQ_USR_ID")
    private UserMstrEntity reqUserId;

    @ManyToOne
    @JoinColumn(name = "PLNT_CRM_ID")
    private ClientRegEntity clientId;

    @Column(name = "PLNT_NOTIFICATION_STATUS")
    private String notificationStatus;

    @Column(name = "PLNT_NOTIFICATION_MSG")
    private String notificationMsg;

    @Column(name = "PLNT_TYPE")
    private String type;

    @Column(name = "PLNT_COMMENTS")
    private String comments;

    @Column(name = "PLNT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PLNT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLNT_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PLNT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLNT_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PLNT_FOR_PROJECT")
    private ProjMstrEntity forProject;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ProjMstrEntity getForProject() {
        return forProject;
    }

    public void setForProject(ProjMstrEntity forProject) {
        this.forProject = forProject;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
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

}
