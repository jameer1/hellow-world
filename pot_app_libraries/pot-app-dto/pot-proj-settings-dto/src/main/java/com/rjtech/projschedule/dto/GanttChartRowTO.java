package com.rjtech.projschedule.dto;

import java.util.ArrayList;
import java.util.List;

public class GanttChartRowTO {
	private Long id;
	private String name;
	private List<Long> children = new ArrayList<Long>();
	private List<GanttChartTaskTO> tasks = new ArrayList<GanttChartTaskTO>();
	private String[] classes;
	private ScheduleActivityDataTO data;

	public GanttChartRowTO() {
	}
	public GanttChartRowTO(Long id, String name, ScheduleActivityDataTO data) {
		this.id = id;
		this.name = name;
		this.data = data;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Long> getChildren() {
		return children;
	}
	public void setChildren(List<Long> children) {
		this.children = children;
	}
	public List<GanttChartTaskTO> getTasks() {
		return tasks;
	}
	public void setTasks(List<GanttChartTaskTO> tasks) {
		this.tasks = tasks;
	}
	public String[] getClasses() {
		return classes;
	}
	public void setClasses(String[] classes) {
		this.classes = classes;
	}
	public ScheduleActivityDataTO getData() {
		return data;
	}
	public void setData(ScheduleActivityDataTO data) {
		this.data = data;
	}
}
