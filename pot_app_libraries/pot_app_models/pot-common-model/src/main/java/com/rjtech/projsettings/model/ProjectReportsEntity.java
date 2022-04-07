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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "project_reports")
public class ProjectReportsEntity implements Serializable {

    private static final long serialVersionUID = 8293813768826094160L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PJR_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PJR_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "PJR_STATUS")
    private Integer status;

    @Column(name = "PJR_WEEK")
    private String week;

    @Column(name = "PJR_MONTH")
    private String month;

    @Column(name = "PJR_YEAR")
    private String year;

    @Column(name = "PJR_FIRST_QUARTER")
    private String firstQuarter;

    @Column(name = "PJR_FIRST_HALF")
    private String firstHalf;

    @ManyToOne
    @JoinColumn(name = "PJR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "PJR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PJR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "PJR_UPDATED_ON")
    private Date updatedOn;

    public ProjectReportsEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFirstQuarter() {
        return firstQuarter;
    }

    public void setFirstQuarter(String firstQuarter) {
        this.firstQuarter = firstQuarter;
    }

    public String getFirstHalf() {
        return firstHalf;
    }

    public void setFirstHalf(String firstHalf) {
        this.firstHalf = firstHalf;
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
