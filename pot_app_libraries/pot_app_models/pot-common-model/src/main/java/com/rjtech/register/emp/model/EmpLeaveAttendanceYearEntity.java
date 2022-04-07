package com.rjtech.register.emp.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Entity
@Table(name = "emp_leave_attendance_year")
public class EmpLeaveAttendanceYearEntity implements Serializable {

    private static final long serialVersionUID = 5062424744624375145L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELAY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ELAY_ERD_ID")
    private EmpRegisterDtlEntity empRegId;

    @Column(name = "ELAY_TOTAL_LEAVES")
    private BigDecimal totalLeaves;

    @Column(name = "ELAY_USED_LEAVES")
    private BigDecimal usedLeaves;

    @Column(name = "ELAY_YEAR")
    private String year;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELAY_END_DATE")
    private Date endDate;

    @Column(name = "ELAY_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ELAY_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELAY_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ELAY_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELAY_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "empLeaveAttendanceYearEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmpLeaveAttendanceDtlEntity> empLeaveAttendanceDtlEntities = new ArrayList<EmpLeaveAttendanceDtlEntity>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EmpRegisterDtlEntity getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(EmpRegisterDtlEntity empRegId) {
        this.empRegId = empRegId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<EmpLeaveAttendanceDtlEntity> getEmpLeaveAttendanceDtlEntities() {
        return empLeaveAttendanceDtlEntities;
    }

    public void setEmpLeaveAttendanceDtlEntities(List<EmpLeaveAttendanceDtlEntity> empLeaveAttendanceDtlEntities) {
        this.empLeaveAttendanceDtlEntities = empLeaveAttendanceDtlEntities;
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
