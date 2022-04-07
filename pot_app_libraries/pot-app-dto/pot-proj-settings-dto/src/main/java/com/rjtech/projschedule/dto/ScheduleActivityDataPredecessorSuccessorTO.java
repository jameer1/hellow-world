package com.rjtech.projschedule.dto;

import java.io.Serializable;
import java.util.List;

public class ScheduleActivityDataPredecessorSuccessorTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String type;
	private ScheduleActivityDataTO scheduleActivityDataTO;
	private List<ScheduleActivityDataTO> predecessorSuccessor;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ScheduleActivityDataTO getScheduleActivityDataTO() {
		return scheduleActivityDataTO;
	}
	public void setScheduleActivityDataTO(ScheduleActivityDataTO scheduleActivityDataTO) {
		this.scheduleActivityDataTO = scheduleActivityDataTO;
	}
	public List<ScheduleActivityDataTO> getPredecessorSuccessor() {
		return predecessorSuccessor;
	}
	public void setPredecessorSuccessor(List<ScheduleActivityDataTO> predecessorSuccessor) {
		this.predecessorSuccessor = predecessorSuccessor;
	}

}
