package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;
import java.util.ArrayList;
import java.util.List;

public class ProjPlannedValueTO extends ProjectTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Double directManHours;
	private Double cost;
	private List<Double> plannedValues = new ArrayList<Double>();
 
	public Double getDirectManHours() {
		return directManHours;
	}

	public void setDirectManHours(Double directManHours) {
		this.directManHours = directManHours;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public List<Double> getPlannedValues(){
		return plannedValues;
	}
	
	public void setPlannedValues(List<Double> plannedValues){
		this.plannedValues = plannedValues;
	}
}
