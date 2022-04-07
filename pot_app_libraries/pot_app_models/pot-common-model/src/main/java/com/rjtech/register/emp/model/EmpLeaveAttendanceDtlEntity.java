package com.rjtech.register.emp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.constants.EmpLeaveType;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "emp_leave_attendance_dtl")
public class EmpLeaveAttendanceDtlEntity implements Serializable {

    private static final long serialVersionUID = 5062424744624375145L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELAT_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ELAT_LEAVE_TYPE")
    private EmpLeaveType empLeaveType;

    @Column(name = "ELAT_TOTAL_LEAVES")
    private BigDecimal totalLeaves;

    @Column(name = "ELAT_USED_LEAVES")
    private BigDecimal usedLeaves;

    @Column(name = "ELAT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ELAT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELAT_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ELAT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELAT_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "ELAT_ELAY_ID")
    private EmpLeaveAttendanceYearEntity empLeaveAttendanceYearEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpLeaveAttendanceYearEntity getEmpLeaveAttendanceYearEntity() {
        return empLeaveAttendanceYearEntity;
    }

    public void setEmpLeaveAttendanceYearEntity(EmpLeaveAttendanceYearEntity empLeaveAttendanceYearEntity) {
        this.empLeaveAttendanceYearEntity = empLeaveAttendanceYearEntity;
    }

    public BigDecimal getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(BigDecimal totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    public BigDecimal getUsedLeaves() {
        return usedLeaves;
    }

    public void setUsedLeaves(BigDecimal usedLeaves) {
        this.usedLeaves = usedLeaves;
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

    public EmpLeaveType getEmpLeaveType() {
        return empLeaveType;
    }

    public void setEmpLeaveType(EmpLeaveType empLeaveType) {
        this.empLeaveType = empLeaveType;
    }

}
