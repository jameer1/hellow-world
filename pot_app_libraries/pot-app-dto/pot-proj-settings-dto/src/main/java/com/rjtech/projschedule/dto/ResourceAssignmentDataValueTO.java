package com.rjtech.projschedule.dto;

import java.io.Serializable;
import java.util.Date;

public class ResourceAssignmentDataValueTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date forecastDate;
	private Double budget;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getForecastDate() {
		return forecastDate;
	}
	public void setForecastDate(Date forecastDate) {
		this.forecastDate = forecastDate;
	}
	public Double getBudget() {
		return budget;
	}
	public void setBudget(Double budget) {
		this.budget = budget;
	}

}
