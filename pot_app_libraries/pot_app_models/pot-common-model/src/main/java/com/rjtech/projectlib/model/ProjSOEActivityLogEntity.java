package com.rjtech.projectlib.model;

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

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the soe_activity_log database table.
 * 
 */
@Entity
@Table(name = "soe_activity_log")
public class ProjSOEActivityLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID")
    private Long logId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "USER_COMMENTS")
    private String userComments;
    
    @ManyToOne
    @JoinColumn(name = "SOE_ID")
    private ProjSOEItemEntity projSOEItemEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_USER")
    private UserMstrEntity fromUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON")
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_USER")
    private UserMstrEntity toUser;  
    
    @Column(name = "FROM_ESTIMATION_VALUE")
    private Long fromEstimationValue;
    
    @Column(name = "TO_ESTIMATION_VALUE")
    private Long toEstimationValue;
    
    @Column(name = "FROM_MAN_HRS_VALUE")
    private Long fromManHrsValue;
    
    @Column(name = "TO_MAN_HRS_VALUE")
    private Long toManHrsValue;
    
    @Column(name = "ACTIVITY_TYPE")
    private String activityType;

    public Long getLogId() {
        return logId;
    }

    public void setLogId( Long logId ) {
        this.logId = logId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments( String userComments ) {
        this.userComments = userComments;
    }

    public ProjSOEItemEntity getProjSOEItemEntity() {
        return projSOEItemEntity;
    }

    public void setProjSOEItemEntity( ProjSOEItemEntity projSOEItemEntity ) {
        this.projSOEItemEntity = projSOEItemEntity;
    }
    
    public UserMstrEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser( UserMstrEntity fromUser ) {
        this.fromUser = fromUser;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn( Date createdOn ) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getToUser() {
        return toUser;
    }

    public void setToUser( UserMstrEntity toUser ) {
        this.toUser = toUser;
    }
    
    public Long getFromEstimationValue() {
        return fromEstimationValue;
    }

    public void setFromEstimationValue( Long fromEstimationValue ) {
        this.fromEstimationValue = fromEstimationValue;
    }
    
    public Long getToEstimationValue() {
        return toEstimationValue;
    }

    public void setToEstimationValue( Long toEstimationValue ) {
        this.toEstimationValue = toEstimationValue;
    }
    
    public Long getFromManHrsValue() {
        return fromManHrsValue;
    }

    public void setFromManHrsValue( Long fromManHrsValue ) {
        this.fromManHrsValue = fromManHrsValue;
    }
    
    public Long getToManHrsValue() {
        return toManHrsValue;
    }

    public void setToManHrsValue( Long toManHrsValue ) {
        this.toManHrsValue = toManHrsValue;
    }
    
    public String getActivityType() {
        return activityType;
    }

    public void setActivityType( String activityType ) {
        this.activityType = activityType;
    }
}
