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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "proj_status_dates")
public class ProjStatusDatesEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7503001874754959515L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PSD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PSD_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSD_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSD_FINISH_DATE")
    private Date finishDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSD_SCHEDULE_START_DATE")
    private Date scheduleStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSD_SCHEDULE_FINISH_DATE")
    private Date scheduleFinishDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSD_FORECAST_START_DATE")
    private Date forecastStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSD_FORECAST_FINISH_DATE")
    private Date forecastFinishDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSD_RESUME_DATE")
    private Date resumeDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSD_SUSPENDED_DATE")
    private Date suspendedDate;

    @Column(name = "PSD_ORIGINAL_DURATION")
    private String originalDuration;

    @Column(name = "PSD_FORECAST_DURATION")
    private String foreCastDuration;

    @Column(name = "PSD_ACTUAL_DURATION")
    private String actualDuration;

    @Column(name = "PSD_REMAINING_DURATION")
    private String remainingDuration;

    @Column(name = "PSD_COMPLETION_DURATION")
    private String completionDuration;

    @Column(name = "PSD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PSD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PSD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PSD_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "PSD_CURRENT_PHASE")
    private String currentPhase;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getResumeDate() {
        return resumeDate;
    }

    public void setResumeDate(Date resumeDate) {
        this.resumeDate = resumeDate;
    }

    public Date getSuspendedDate() {
        return suspendedDate;
    }

    public void setSuspendedDate(Date suspendedDate) {
        this.suspendedDate = suspendedDate;
    }

    public String getOriginalDuration() {
        return originalDuration;
    }

    public void setOriginalDuration(String originalDuration) {
        this.originalDuration = originalDuration;
    }

    public String getForeCastDuration() {
        return foreCastDuration;
    }

    public void setForeCastDuration(String foreCastDuration) {
        this.foreCastDuration = foreCastDuration;
    }

    public String getActualDuration() {
        return actualDuration;
    }

    public void setActualDuration(String actualDuration) {
        this.actualDuration = actualDuration;
    }

    public String getRemainingDuration() {
        return remainingDuration;
    }

    public void setRemainingDuration(String remainingDuration) {
        this.remainingDuration = remainingDuration;
    }

    public String getCompletionDuration() {
        return completionDuration;
    }

    public void setCompletionDuration(String completionDuration) {
        this.completionDuration = completionDuration;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
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

    public Date getScheduleStartDate() {
        return scheduleStartDate;
    }

    public void setScheduleStartDate(Date scheduleStartDate) {
        this.scheduleStartDate = scheduleStartDate;
    }

    public Date getScheduleFinishDate() {
        return scheduleFinishDate;
    }

    public void setScheduleFinishDate(Date scheduleFinishDate) {
        this.scheduleFinishDate = scheduleFinishDate;
    }

    public Date getForecastStartDate() {
        return forecastStartDate;
    }

    public void setForecastStartDate(Date forecastStartDate) {
        this.forecastStartDate = forecastStartDate;
    }

    public Date getForecastFinishDate() {
        return forecastFinishDate;
    }

    public void setForecastFinishDate(Date forecastFinishDate) {
        this.forecastFinishDate = forecastFinishDate;
    }
    
    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }
    
}
