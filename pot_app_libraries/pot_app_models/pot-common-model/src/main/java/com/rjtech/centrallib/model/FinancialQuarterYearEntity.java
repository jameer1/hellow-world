package com.rjtech.centrallib.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "financial_quarter_year")
public class FinancialQuarterYearEntity implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FQY_ID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "FQY_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FQY_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FQY_CRM_ID", updatable = false)
    private ClientRegEntity clientId;
    
    @ManyToOne
    @JoinColumn(name = "FQY_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FQY_UPDATED_ON")
    private Date updatedOn;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FQY_FIRST_QUARTER_FROM_DATE")
    private Date quarterFirstFromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FQY_FIRST_QUARTER_TO_DATE")
    private Date quarterFirstToDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FQY_SECOND_QUARTER_FROM_DATE")
    private Date quarterSecondFromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FQY_SECOND_QUARTER_TO_DATE")
    private Date quarterSecondToDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FQY_THIRD_QUARTER_FROM_DATE")
    private Date quarterThirdFromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FQY_THIRD_QUARTER_TO_DATE")
    private Date quarterThirdToDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FQY_FOURTH_QUARTER_FROM_DATE")
    private Date quarterFourthFromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FQY_FOURTH_QUARTER_TO_DATE")
    private Date quarterFourthToDate;
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
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

    public Date getQuarterFirstFromDate() {
        return quarterFirstFromDate;
    }

    public void setQuarterFirstFromDate(Date quarterFirstFromDate) {
        this.quarterFirstFromDate = quarterFirstFromDate;
    }

    public Date getQuarterFirstToDate() {
        return quarterFirstToDate;
    }

    public void setQuarterFirstToDate(Date quarterFirstToDate) {
        this.quarterFirstToDate = quarterFirstToDate;
    }

    public Date getQuarterSecondFromDate() {
        return quarterSecondFromDate;
    }

    public void setQuarterSecondFromDate(Date quarterSecondFromDate) {
        this.quarterSecondFromDate = quarterSecondFromDate;
    }

    public Date getQuarterSecondToDate() {
        return quarterSecondToDate;
    }

    public void setQuarterSecondToDate(Date quarterSecondToDate) {
        this.quarterSecondToDate = quarterSecondToDate;
    }

    public Date getQuarterThirdFromDate() {
        return quarterThirdFromDate;
    }

    public void setQuarterThirdFromDate(Date quarterThirdFromDate) {
        this.quarterThirdFromDate = quarterThirdFromDate;
    }

    public Date getQuarterThirdToDate() {
        return quarterThirdToDate;
    }

    public void setQuarterThirdToDate(Date quarterThirdToDate) {
        this.quarterThirdToDate = quarterThirdToDate;
    }

    public Date getQuarterFourthFromDate() {
        return quarterFourthFromDate;
    }

    public void setQuarterFourthFromDate(Date quarterFourthFromDate) {
        this.quarterFourthFromDate = quarterFourthFromDate;
    }

    public Date getQuarterFourthToDate() {
        return quarterFourthToDate;
    }

    public void setQuarterFourthToDate(Date quarterFourthToDate) {
        this.quarterFourthToDate = quarterFourthToDate;
    }


    
}
