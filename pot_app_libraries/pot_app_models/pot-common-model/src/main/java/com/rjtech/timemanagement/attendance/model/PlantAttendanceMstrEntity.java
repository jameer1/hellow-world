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
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;

/**
 * The persistent class for the proj_plant_attendence_mstr database table.
 * 
 */
@Entity
@Table(name = "proj_plant_attendence_mstr")
public class PlantAttendanceMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPAM_ID")
    private Long id;

    @Column(name = "PPAM_NAME")
    private String code;

    @ManyToOne
    @JoinColumn(name = "PPAM_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @ManyToOne
    @JoinColumn(name = "PPAM_CRW_ID")
    private ProjCrewMstrEntity projCrewMstrEntity;

    @Column(name = "PPAM_COPY_TEMPLATE")
    private boolean copyTemplate;

    @Column(name = "PPAM_MONTH")
    private Date attendanceMonth;

    @Column(name = "PPAM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PPAM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPAM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PPAM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPAM_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public ProjCrewMstrEntity getProjCrewMstrEntity() {
        return projCrewMstrEntity;
    }

    public void setProjCrewMstrEntity(ProjCrewMstrEntity projCrewMstrEntity) {
        this.projCrewMstrEntity = projCrewMstrEntity;
    }

    public boolean isCopyTemplate() {
        return copyTemplate;
    }

    public void setCopyTemplate(boolean copyTemplate) {
        this.copyTemplate = copyTemplate;
    }

    public Date getAttendanceMonth() {
        return attendanceMonth;
    }

    public void setAttendanceMonth(Date attendanceMonth) {
        this.attendanceMonth = attendanceMonth;
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
