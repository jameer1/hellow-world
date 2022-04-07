package com.rjtech.reports.cost.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProgressSCurveTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date date;
	private BigDecimal planned = BigDecimal.valueOf(0);
	private BigDecimal actual = BigDecimal.valueOf(0);
	private BigDecimal earnedTodate = BigDecimal.valueOf(0);
	private BigDecimal earnedForecast = BigDecimal.valueOf(0);
	private BigDecimal forecastToComplete = BigDecimal.valueOf(0);
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getPlanned() {
		return planned;
	}
	public void setPlanned(BigDecimal planned) {
		this.planned = planned;
	}
	public BigDecimal getActual() {
		return actual;
	}
	public void setActual(BigDecimal actual) {
		this.actual = actual;
	}
	public BigDecimal getEarnedForecast() {
		return earnedForecast;
	}
	public void setEarnedForecast(BigDecimal earnedForecast) {
		this.earnedForecast = earnedForecast;
	}
	public BigDecimal getEarnedTodate() {
		return earnedTodate;
	}
	public void setEarnedTodate(BigDecimal earnedTodate) {
		this.earnedTodate = earnedTodate;
	}
	public BigDecimal getForecastToComplete() {
		return forecastToComplete;
	}
	public void setForecastToComplete(BigDecimal estimateToComplete) {
		this.forecastToComplete = estimateToComplete;
	}
}
