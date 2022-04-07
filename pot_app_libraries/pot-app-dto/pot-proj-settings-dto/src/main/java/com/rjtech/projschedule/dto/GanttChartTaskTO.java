package com.rjtech.projschedule.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GanttChartTaskTO {

	private Long id;
	private String name;
	private String color;
	private Date from;
	private Date to;
	private GanttChartProgress progress;
	private int priority;
	private String[] classes;
	private List<GanttChartDependency> dependencies = new ArrayList<GanttChartDependency>();
	

	public GanttChartTaskTO() {
	}
	public GanttChartTaskTO(Long id, String name, Date from, Date to, GanttChartProgress progress, int priority, String color) {
		this.id = id;
		this.color = color;
		this.from = from;
		this.to = to;
		this.progress = progress;
		this.priority = priority;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public GanttChartProgress getProgress() {
		return progress;
	}
	public void setProgress(GanttChartProgress progress) {
		this.progress = progress;
	}
	public String[] getClasses() {
		return classes;
	}
	public void setClasses(String[] classes) {
		this.classes = classes;
	}
	public List<GanttChartDependency> getDependencies() {
		return dependencies;
	}
	public void setDependencies(List<GanttChartDependency> dependencies) {
		this.dependencies = dependencies;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
