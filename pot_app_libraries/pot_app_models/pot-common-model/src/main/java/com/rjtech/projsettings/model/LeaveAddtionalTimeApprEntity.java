package com.rjtech.projsettings.model;

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

import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "proj_leave_approval")
public class LeaveAddtionalTimeApprEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLA_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLA_DATE")
    private Date requisitionDate;

    @Column(name = "PLA_NTF_NUMBER")
    private String notificationNumber;

    @Column(name = "PLA_REQUESTER")
    private String requester;

    @Column(name = "PLA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PLA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLA_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "PLA_IS_LATEST")
    private boolean latest;

    @ManyToOne
    @JoinColumn(name = "PET_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLA_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PLA_PLR_ID")
    private LeaveNormalTimeEntity projLeaveRequestEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotificationNumber() {
        return notificationNumber;
    }

    public void setNotificationNumber(String notificationNumber) {
        this.notificationNumber = notificationNumber;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
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

    public LeaveNormalTimeEntity getProjLeaveRequestEntity() {
        return projLeaveRequestEntity;
    }

    public void setProjLeaveRequestEntity(LeaveNormalTimeEntity projLeaveRequestEntity) {
        this.projLeaveRequestEntity = projLeaveRequestEntity;
    }

}
