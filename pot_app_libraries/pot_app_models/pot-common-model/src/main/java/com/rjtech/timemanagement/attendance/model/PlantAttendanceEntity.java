package com.rjtech.timemanagement.attendance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
//import com.rjtech.register.plant.model.PlantRegisterDtlEntityCopy;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;

/**
 * The persistent class for the proj_plant_attendance database table.
 * 
 */
@Entity
@Table(name = "proj_plant_attendance")
public class PlantAttendanceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPAT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PPAT_CRW_ID")
    private ProjCrewMstrEntity projCrewMstrEntity;

    @ManyToOne
    @JoinColumn(name = "PPAT_PLRD_ID")
    private PlantRegisterDtlEntity plantRegisterDtlEntity;

    @Column(name = "PPAT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PPAT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPAT_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PPAT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPAT_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PPAT_PPAM_ID")
    private PlantAttendanceMstrEntity plantAttendanceMstrEntity;

    @OneToMany(mappedBy = "plantAttendanceEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlantAttendanceDtlEntity> plantAttendanceDtlEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjCrewMstrEntity getProjCrewMstrEntity() {
        return projCrewMstrEntity;
    }

    public void setProjCrewMstrEntity(ProjCrewMstrEntity projCrewMstrEntity) {
        this.projCrewMstrEntity = projCrewMstrEntity;
    }

    public PlantRegisterDtlEntity getPlantRegisterDtlEntity() {
        return plantRegisterDtlEntity;
    }

    public void setPlantRegisterDtlEntity(PlantRegisterDtlEntity plantRegisterDtlEntity) {
        this.plantRegisterDtlEntity = plantRegisterDtlEntity;
    }

    public PlantAttendanceMstrEntity getPlantAttendanceMstrEntity() {
        return plantAttendanceMstrEntity;
    }

    public void setPlantAttendanceMstrEntity(PlantAttendanceMstrEntity plantAttendanceMstrEntity) {
        this.plantAttendanceMstrEntity = plantAttendanceMstrEntity;
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

    public List<PlantAttendanceDtlEntity> getPlantAttendanceDtlEntities() {
        return plantAttendanceDtlEntities;
    }

    public void setPlantAttendanceDtlEntities(List<PlantAttendanceDtlEntity> plantAttendanceDtlEntities) {
        this.plantAttendanceDtlEntities = plantAttendanceDtlEntities;
    }

}
