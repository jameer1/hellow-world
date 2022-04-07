package com.rjtech.timemanagement.attendance.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.ProjLeaveTypeEntity;
import com.rjtech.common.model.UserMstrEntity;

//import com.rjtech.projectlib.model.ProjLeaveTypeEntityCopy;

/**
 * The persistent class for the proj_emp_attendence_dtl database table.
 * 
 */
@Entity
@Table(name = "proj_emp_attendence_dtl")
public class EmpAttendanceDtlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PEAD_ID")
    private Long id;

    // TODO remove this
    @ManyToOne
    @JoinColumn(name = "PEAD_PJL_ID")
    private ProjLeaveTypeEntity projLeaveId;

    @Column(name = "PEAD_ATTD_CODE")
    private String projAttdCode;

    @OrderColumn
    @Column(name = "PEAD_CHECK_IN")
    private Date attendanceDate;

    @Column(name = "PEAD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PEAD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEAD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PEAD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEAD_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PEAD_PEAT_ID")
    private EmpAttendanceEntity empAttendanceEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpAttendanceEntity getEmpAttendanceEntity() {
        return empAttendanceEntity;
    }

    public void setEmpAttendanceEntity(EmpAttendanceEntity empAttendanceEntity) {
        this.empAttendanceEntity = empAttendanceEntity;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
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

    public ProjLeaveTypeEntity getProjLeaveId() {
        return projLeaveId;
    }

    public void setProjLeaveId(ProjLeaveTypeEntity projLeaveId) {
        this.projLeaveId = projLeaveId;
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

    public String getProjAttdCode() {
        return projAttdCode;
    }

    public void setProjAttdCode(String projAttdCode) {
        this.projAttdCode = projAttdCode;
    }

}
