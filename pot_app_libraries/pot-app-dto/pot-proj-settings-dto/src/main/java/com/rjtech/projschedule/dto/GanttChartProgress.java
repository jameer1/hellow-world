package com.rjtech.projschedule.dto;

public class GanttChartProgress {

	private Long percent;
	private String color;
	private String[] classes;
	
	public GanttChartProgress() {
	}
	public GanttChartProgress(Long percent, String color) {
		super();
		this.percent = percent;
		this.color = color;
	}
	public Long getPercent() {
		return percent;
	}
	public void setPercent(Long percent) {
		this.percent = percent;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String[] getClasses() {
		return classes;
	}
	public void setClasses(String[] classes) {
		this.classes = classes;
	}
}
