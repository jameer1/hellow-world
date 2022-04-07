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
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;

@Entity
@Table(name = "proj_attendence_approval")
public class AttendanceAddtionalTimeApprEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAA_ID")
    private Long id;

    @Column(name = "PAA_NTF")
    private String notification;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAA_FROM")
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAA_TO")
    private Date toDate;

    @Column(name = "PAA_CUT_OFF_DAYS")
    private Integer cutOffDays;

    @Column(name = "PAA_CUT_OFF_HOURS")
    private Integer cutOffHours;

    @Column(name = "PAA_CUT_OFF_MINUTES")
    private Integer cutOffMinutes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAA_REQ_DATE")
    private Date approveReqDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAA_REQ_END_DATE")
    private Date approveReqEndDate;

    @Column(name = "PAA_TYPE")
    private String type;

    @Column(name = "PAA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PAA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PAA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAA_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PAA_PJA_ID")
    private AttendanceNormalTimeEntity attendanceNormalTimeEntity;

    @ManyToOne
    @JoinColumn(name = "PAA_CRW_ID")
    private ProjCrewMstrEntity projCrewMasterEntity;

    public ProjCrewMstrEntity getProjCrewMasterEntity() {
        return projCrewMasterEntity;
    }

    public void setProjCrewMasterEntity(ProjCrewMstrEntity projCrewMasterEntity) {
        this.projCrewMasterEntity = projCrewMasterEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCutOffDays() {
        return cutOffDays;
    }

    public void setCutOffDays(Integer cutOffDays) {
        this.cutOffDays = cutOffDays;
    }

    public Integer getCutOffHours() {
        return cutOffHours;
    }

    public void setCutOffHours(Integer cutOffHours) {
        this.cutOffHours = cutOffHours;
    }

    public Integer getCutOffMinutes() {
        return cutOffMinutes;
    }

    public void setCutOffMinutes(Integer cutOffMinutes) {
        this.cutOffMinutes = cutOffMinutes;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public AttendanceNormalTimeEntity getAttendanceNormalTimeEntity() {
        return attendanceNormalTimeEntity;
    }

    public void setAttendanceNormalTimeEntity(AttendanceNormalTimeEntity attendanceNormalTimeEntity) {
        this.attendanceNormalTimeEntity = attendanceNormalTimeEntity;
    }

    public Date getApproveReqDate() {
        return approveReqDate;
    }

    public Date getApproveReqEndDate() {
        return approveReqEndDate;
    }

    public void setApproveReqDate(Date approveReqDate) {
        this.approveReqDate = approveReqDate;
    }

    public void setApproveReqEndDate(Date approveReqEndDate) {
        this.approveReqEndDate = approveReqEndDate;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
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