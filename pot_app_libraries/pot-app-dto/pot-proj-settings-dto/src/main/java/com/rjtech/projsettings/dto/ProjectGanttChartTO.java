package com.rjtech.projsettings.dto;

import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class ProjectGanttChartTO extends ProjectTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date plannedStartDate;
	private Date plannedFinishDate;
	private Date baselineStartDate;
	private Date baselineFinishDate;
	private double progress;

	public Date getPlannedStartDate() {
		return this.plannedStartDate;
	}

	public void setPlannedStartDate(Date plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public Date getPlannedFinishDate() {
		return this.plannedFinishDate;
	}

	public void setPlannedFinishDate(Date plannedFinishDate) {
		this.plannedFinishDate = plannedFinishDate;
	}

	public Date getBaselineStartDate() {
		return this.baselineStartDate;
	}

	public void setBaselineStartDate(Date baselineStartDate) {
		this.baselineStartDate = baselineStartDate;
	}

	public Date getBaselineFinishDate() {
		return this.baselineFinishDate;
	}

	public void setBaselineFinishDate(Date baselineFinishDate) {
		this.baselineFinishDate = baselineFinishDate;
	}

	public double getProgress() {
		return this.progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}
}
