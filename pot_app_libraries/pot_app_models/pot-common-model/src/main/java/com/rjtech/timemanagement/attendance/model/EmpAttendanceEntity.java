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
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;

/**
 * The persistent class for the proj_emp_attendance database table.
 * 
 */
@Entity
@Table(name = "proj_emp_attendance")
public class EmpAttendanceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PEAT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PEAT_PEAM_ID")
    private EmpAttendanceMstrEntity empAttendanceMstrEntity;

    @ManyToOne
    @JoinColumn(name = "PEAT_CRW_ID")
    private ProjCrewMstrEntity crewId;

    @ManyToOne
    @JoinColumn(name = "PEAT_ERD_ID")
    private EmpRegisterDtlEntity empId;

    @Column(name = "PEAT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PEAT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEAT_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PEAT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEAT_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "empAttendanceEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<EmpAttendanceDtlEntity> empAttendanceDtlEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpAttendanceMstrEntity getEmpAttendanceMstrEntity() {
        return empAttendanceMstrEntity;
    }

    public void setEmpAttendanceMstrEntity(EmpAttendanceMstrEntity empAttendanceMstrEntity) {
        this.empAttendanceMstrEntity = empAttendanceMstrEntity;
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

    public List<EmpAttendanceDtlEntity> getEmpAttendanceDtlEntities() {
        return empAttendanceDtlEntities;
    }

    public void setEmpAttendanceDtlEntities(List<EmpAttendanceDtlEntity> empAttendanceDtlEntities) {
        this.empAttendanceDtlEntities = empAttendanceDtlEntities;
    }

    public ProjCrewMstrEntity getCrewId() {
        return crewId;
    }

    public void setCrewId(ProjCrewMstrEntity crewId) {
        this.crewId = crewId;
    }

    public EmpRegisterDtlEntity getEmpId() {
        return empId;
    }

    public void setEmpId(EmpRegisterDtlEntity empId) {
        this.empId = empId;
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