package com.rjtech.reports.cost.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProgressSCurveTOResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ProgressSCurveTO> progressSCurveTOs = new ArrayList<ProgressSCurveTO>();
	private Date scheduleDate;

	public List<ProgressSCurveTO> getProgressSCurveTOs() {
		return progressSCurveTOs;
	}

	public void setProgressSCurveTOs(List<ProgressSCurveTO> progressSCurveTOs) {
		this.progressSCurveTOs = progressSCurveTOs;
	}

	public void addToPlan(Date date, double planned) {
		boolean updated = false;
		for (ProgressSCurveTO progressSCurveTO : progressSCurveTOs) {
			if (progressSCurveTO.getDate().equals(date)) {
				progressSCurveTO.setPlanned(progressSCurveTO.getPlanned().add(BigDecimal.valueOf(planned)));
				updated = true;
			}
		}
		if (!updated) {
			ProgressSCurveTO progressSCurveTO = new ProgressSCurveTO();
			progressSCurveTO.setDate(date);
			progressSCurveTO.setPlanned(BigDecimal.valueOf(planned));
			this.progressSCurveTOs.add(progressSCurveTO);
		}
	}
	
	public void addToEarnedTodate(Date date, BigDecimal earnedTodate) {
		boolean updated = false;
		for (ProgressSCurveTO progressSCurveTO : progressSCurveTOs) {
			if (progressSCurveTO.getDate().equals(date)) {
				progressSCurveTO.setEarnedTodate(progressSCurveTO.getEarnedTodate().add(earnedTodate));
				updated = true;
			}
		}
		if (!updated) {
			ProgressSCurveTO progressSCurveTO = new ProgressSCurveTO();
			progressSCurveTO.setDate(date);
			progressSCurveTO.setEarnedTodate(earnedTodate);
			this.progressSCurveTOs.add(progressSCurveTO);
		}
	}
	
	public void addEarnedForecast(Date date, double earnedForecast) {
		boolean updated = false;
		for (ProgressSCurveTO progressSCurveTO : progressSCurveTOs) {
			if (progressSCurveTO.getDate().equals(date)) {
				progressSCurveTO.setEarnedForecast(progressSCurveTO.getPlanned().add(BigDecimal.valueOf(earnedForecast)));
				updated = true;
			}
		}
		if (!updated) {
			ProgressSCurveTO progressSCurveTO = new ProgressSCurveTO();
			progressSCurveTO.setDate(date);
			progressSCurveTO.setEarnedForecast(BigDecimal.valueOf(earnedForecast));
			this.progressSCurveTOs.add(progressSCurveTO);
		}
	}
	
	public void addToActual(Date date, double actual) {
		boolean updated = false;
		for (ProgressSCurveTO progressSCurveTO : progressSCurveTOs) {
			if (progressSCurveTO.getDate().equals(date)) {
				progressSCurveTO.setActual(progressSCurveTO.getActual().add(BigDecimal.valueOf(actual)));
				updated = true;
			}
		}
		if (!updated) {
			ProgressSCurveTO progressSCurveTO = new ProgressSCurveTO();
			progressSCurveTO.setDate(date);
			progressSCurveTO.setActual(BigDecimal.valueOf(actual));
			this.progressSCurveTOs.add(progressSCurveTO);
		}
	}
	
	public void addToForecastToComplete(Date date, double estimateToComplete) {
		boolean updated = false;
		for (ProgressSCurveTO progressSCurveTO : progressSCurveTOs) {
			if (progressSCurveTO.getDate().equals(date)) {
				progressSCurveTO.setForecastToComplete(progressSCurveTO.getForecastToComplete().add(BigDecimal.valueOf(estimateToComplete)));
				updated = true;
			}
		}
		if (!updated) {
			ProgressSCurveTO progressSCurveTO = new ProgressSCurveTO();
			progressSCurveTO.setDate(date);
			progressSCurveTO.setForecastToComplete(BigDecimal.valueOf(estimateToComplete));
			this.progressSCurveTOs.add(progressSCurveTO);
		}
	}
	
	public void sortByDate() {
		this.progressSCurveTOs = this.progressSCurveTOs.stream().sorted(Comparator.comparing(ProgressSCurveTO::getDate)).collect(Collectors.toList());
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
}
