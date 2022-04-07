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
@Table(name = "employee_notifications")
public class EmployeeNotificationsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENTF_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTF_DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "ENTF_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @ManyToOne
    @JoinColumn(name = "ENTF_APPR_USR_ID")
    private UserMstrEntity apprUserId;

    @ManyToOne
    @JoinColumn(name = "ENTF_REQ_USR_ID")
    private UserMstrEntity reqUserId;

    @ManyToOne
    @JoinColumn(name = "ENTF_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "ENTF_FOR_PROJECT")
    private Long forProject;

    @Column(name = "ENTF_STATUS")
    private Integer status;

    @Column(name = "ENTF_TYPE")
    private String type;

    @Column(name = "ENTF_NOTIFICATION_STATUS")
    private String notifyStatus;

    @Column(name = "ENTF_NOTIFICATION_MSG")
    private String notificationMsg;

    @ManyToOne
    @JoinColumn(name = "ENTF_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTF_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ENTF_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTF_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "ENTF_EMP_STATUS")
    private String empStatus;

    @Column(name = "ENTF_REF_EMP_ID")
    private Long refempID;

    @Temporal(TemporalType.DATE)
    @Column(name = "ENTF_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ENTF_END_DATE")
    private Date endDate;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
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

    public Long getForProject() {
        return forProject;
    }

    public void setForProject(Long forProject) {
        this.forProject = forProject;
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

    public Long getRefempID() {
        return refempID;
    }

    public void setRefempID(Long refempID) {
        this.refempID = refempID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }
}
