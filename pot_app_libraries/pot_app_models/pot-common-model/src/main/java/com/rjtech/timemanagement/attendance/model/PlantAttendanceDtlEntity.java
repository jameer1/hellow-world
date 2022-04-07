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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the proj_plant_attendence_dtl database table.
 * 
 */
@Entity
@Table(name = "proj_plant_attendence_dtl")
public class PlantAttendanceDtlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPAD_ID")
    private Long id;

    @Column(name = "PPAD_ATTD_CODE")
    private String projAttdCode;

    @Column(name = "PPAD_CHECK_IN")
    private Date attendanceDate;

    @Column(name = "PPAD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PPAD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPAD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PPAD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPAD_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PPAD_PPAT_ID")
    private PlantAttendanceEntity plantAttendanceEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjAttdCode() {
        return projAttdCode;
    }

    public void setProjAttdCode(String projAttdCode) {
        this.projAttdCode = projAttdCode;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public PlantAttendanceEntity getPlantAttendanceEntity() {
        return plantAttendanceEntity;
    }

    public void setPlantAttendanceEntity(PlantAttendanceEntity plantAttendanceEntity) {
        this.plantAttendanceEntity = plantAttendanceEntity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

}
