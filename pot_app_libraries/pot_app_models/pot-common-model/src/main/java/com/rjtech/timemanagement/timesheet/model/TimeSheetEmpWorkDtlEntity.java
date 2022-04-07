package com.rjtech.timemanagement.timesheet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.EmpWageMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;

/**
 * The persistent class for the time_sheet_emp_date_wise database table.
 * 
 */
@Entity
@Table(name = "time_sheet_emp_date_wise")
public class TimeSheetEmpWorkDtlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TSWD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSWD_CCS_ID")
    private ProjCostItemEntity projCostItemEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSWD_EWM_ID")
    private EmpWageMstrEntity empWageMstrEntity;

    @Column(name = "TSWD_DAY1")
    private Double day1;

    @Column(name = "TSWD_DAY2")
    private Double day2;

    @Column(name = "TSWD_DAY3")
    private Double day3;

    @Column(name = "TSWD_DAY4")
    private Double day4;

    @Column(name = "TSWD_DAY5")
    private Double day5;

    @Column(name = "TSWD_DAY6")
    private Double day6;

    @Column(name = "TSWD_DAY7")
    private Double day7;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSWD_PARENT_ID")
    private TimeSheetEmpWorkDtlEntity parentTimeSheetEmpWorkDtlEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSWD_APPR_USR_ID")
    private UserMstrEntity apprUserMstrEntity;

    @Column(name = "TSWD_APPR_STATUS")
    private String apprStatus;

    @Column(name = "TSWD_APPR_COMMENTS")
    private String apprComments;

    @Column(name = "TSWD_IS_LATEST")
    private boolean isLatest;

    @Column(name = "TSWD_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSWD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSWD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSWD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSWD_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TSWD_TSED_ID")
    private TimeSheetEmpDtlEntity timeSheetEmpDtlEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSheetEmpWorkDtlEntity getParentTimeSheetEmpWorkDtlEntity() {
        return parentTimeSheetEmpWorkDtlEntity;
    }

    public void setParentTimeSheetEmpWorkDtlEntity(TimeSheetEmpWorkDtlEntity parentTimeSheetEmpWorkDtlEntity) {
        this.parentTimeSheetEmpWorkDtlEntity = parentTimeSheetEmpWorkDtlEntity;
    }

    public UserMstrEntity getApprUserMstrEntity() {
        return apprUserMstrEntity;
    }

    public void setApprUserMstrEntity(UserMstrEntity apprUserMstrEntity) {
        this.apprUserMstrEntity = apprUserMstrEntity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isLatest() {
        return isLatest;
    }

    public void setLatest(boolean isLatest) {
        this.isLatest = isLatest;
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

    public ProjCostItemEntity getProjCostItemEntity() {
        return projCostItemEntity;
    }

    public void setProjCostItemEntity(ProjCostItemEntity projCostItemEntity) {
        this.projCostItemEntity = projCostItemEntity;
    }

    public EmpWageMstrEntity getEmpWageMstrEntity() {
        return empWageMstrEntity;
    }

    public void setEmpWageMstrEntity(EmpWageMstrEntity empWageMstrEntity) {
        this.empWageMstrEntity = empWageMstrEntity;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public Double getDay1() {
        return day1;
    }

    public void setDay1(Double day1) {
        this.day1 = day1;
    }

    public Double getDay2() {
        return day2;
    }

    public void setDay2(Double day2) {
        this.day2 = day2;
    }

    public Double getDay3() {
        return day3;
    }

    public void setDay3(Double day3) {
        this.day3 = day3;
    }

    public Double getDay4() {
        return day4;
    }

    public void setDay4(Double day4) {
        this.day4 = day4;
    }

    public Double getDay5() {
        return day5;
    }

    public void setDay5(Double day5) {
        this.day5 = day5;
    }

    public Double getDay6() {
        return day6;
    }

    public void setDay6(Double day6) {
        this.day6 = day6;
    }

    public Double getDay7() {
        return day7;
    }

    public void setDay7(Double day7) {
        this.day7 = day7;
    }

    public TimeSheetEmpDtlEntity getTimeSheetEmpDtlEntity() {
        return timeSheetEmpDtlEntity;
    }

    public void setTimeSheetEmpDtlEntity(TimeSheetEmpDtlEntity timeSheetEmpDtlEntity) {
        this.timeSheetEmpDtlEntity = timeSheetEmpDtlEntity;
    }

}
