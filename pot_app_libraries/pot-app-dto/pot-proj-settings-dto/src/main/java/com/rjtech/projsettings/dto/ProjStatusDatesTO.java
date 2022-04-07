package com.rjtech.projsettings.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class ProjStatusDatesTO extends ProjectTO {

    private static final long serialVersionUID = -4567482312275511930L;

    private Long id;
    private String startDate;
    private String finishDate;
    private String scheduleStartDate;
    private String scheduleFinishDate;
    private String forecastStartDate;
    private String forecastFinishDate;
    private String resumeDate;
    private String suspendedDate;
    private String originalDuration;
    private String foreCastDuration;
    private String actualDuration;
    private String remainingDuration;
    private String completionDuration;
    private Long earnedValue;
    private BigDecimal plannedValue;
    private String currentPhase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getScheduleStartDate() {
        return scheduleStartDate;
    }

    public void setScheduleStartDate(String scheduleStartDate) {
        this.scheduleStartDate = scheduleStartDate;
    }

    public String getScheduleFinishDate() {
        return scheduleFinishDate;
    }

    public void setScheduleFinishDate(String scheduleFinishDate) {
        this.scheduleFinishDate = scheduleFinishDate;
    }

    public String getForecastStartDate() {
        return forecastStartDate;
    }

    public void setForecastStartDate(String forecastStartDate) {
        this.forecastStartDate = forecastStartDate;
    }

    public String getForecastFinishDate() {
        return forecastFinishDate;
    }

    public void setForecastFinishDate(String forecastFinishDate) {
        this.forecastFinishDate = forecastFinishDate;
    }

    public String getResumeDate() {
        return resumeDate;
    }

    public void setResumeDate(String resumeDate) {
        this.resumeDate = resumeDate;
    }

    public String getSuspendedDate() {
        return suspendedDate;
    }

    public void setSuspendedDate(String suspendedDate) {
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

    public Long getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(Long earnedValue) {
        this.earnedValue = earnedValue;
    }

    public BigDecimal getPlannedValue() {
        return plannedValue;
    }

    public void setPlannedValue(BigDecimal plannedValue) {
        this.plannedValue = plannedValue;
    }

	public String getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}

}
