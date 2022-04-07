package com.rjtech.projschedule.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.calendar.dto.CalRegularDaysTO;

public class CalendarSpecialWorkingNonworkingDays implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CalRegularDaysTO weeklyHolidays = new CalRegularDaysTO();
	private List<String> specialNonworkingDays = new ArrayList<String>();
	private List<String> specialWorkingDays = new ArrayList<String>();
	
	public CalRegularDaysTO getWeeklyHolidays() {
		return weeklyHolidays;
	}
	public void setWeeklyHolidays(CalRegularDaysTO weeklyHolidays) {
		this.weeklyHolidays = weeklyHolidays;
	}
	public List<String> getSpecialNonworkingDays() {
		return specialNonworkingDays;
	}
	public void setSpecialNonworkingDays(List<String> specialNonworkingDays) {
		this.specialNonworkingDays = specialNonworkingDays;
	}
	public List<String> getSpecialWorkingDays() {
		return specialWorkingDays;
	}
	public void setSpecialWorkingDays(List<String> specialWorkingDays) {
		this.specialWorkingDays = specialWorkingDays;
	}
}
