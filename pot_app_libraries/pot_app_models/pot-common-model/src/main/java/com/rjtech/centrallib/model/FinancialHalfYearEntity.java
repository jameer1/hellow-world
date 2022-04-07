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
@Table(name = "financial_half_year")
public class FinancialHalfYearEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FHY_ID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "FHY_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FHY_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FHY_CRM_ID", updatable = false)
    private ClientRegEntity clientId;
    

    @ManyToOne
    @JoinColumn(name = "FHY_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FHY_UPDATED_ON")
    private Date updatedOn;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FHY_FIRST_FROM_DATE")
    private Date firstFromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FHY_FIRST_TO_DATE")
    private Date firstToDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FHY_SECOND_FROM_DATE")
    private Date secondFromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FHY_SECOND_TO_DATE")
    private Date secondToDate;
    
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

    public Date getFirstFromDate() {
        return firstFromDate;
    }

    public void setFirstFromDate(Date firstFromDate) {
        this.firstFromDate = firstFromDate;
    }

    public Date getFirstToDate() {
        return firstToDate;
    }

    public void setFirstToDate(Date firstToDate) {
        this.firstToDate = firstToDate;
    }

    public Date getSecondFromDate() {
        return secondFromDate;
    }

    public void setSecondFromDate(Date secondFromDate) {
        this.secondFromDate = secondFromDate;
    }

    public Date getSecondToDate() {
        return secondToDate;
    }

    public void setSecondToDate(Date secondToDate) {
        this.secondToDate = secondToDate;
    }


}
