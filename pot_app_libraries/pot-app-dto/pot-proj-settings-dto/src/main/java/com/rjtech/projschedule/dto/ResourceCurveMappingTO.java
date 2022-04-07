package com.rjtech.projschedule.dto;

import java.io.Serializable;

public class ResourceCurveMappingTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long curveId;
	private Long resourceReferenceId;
	private String resourceReferenceType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCurveId() {
		return curveId;
	}
	public void setCurveId(Long curveId) {
		this.curveId = curveId;
	}
	public Long getResourceReferenceId() {
		return resourceReferenceId;
	}
	public void setResourceReferenceId(Long resourceReferenceId) {
		this.resourceReferenceId = resourceReferenceId;
	}
	public String getResourceReferenceType() {
		return resourceReferenceType;
	}
	public void setResourceReferenceType(String resourceReferenceType) {
		this.resourceReferenceType = resourceReferenceType;
	}
}
