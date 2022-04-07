package com.rjtech.projschedule.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rjtech.common.model.ResourceCurveEntity;

@Entity
@Table(name = "resource_curves_mapping")
public class ResourceCurveMappingEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rcm_id")
    private Long id;
	
	@ManyToOne
    @JoinColumn(name = "rcm_curve_id")
    private ResourceCurveEntity projResourceCurveEntity;
	
	@Column(name = "rcm_ref_id")
    private Long referenceId;
	
	@Column(name = "rcm_ref_type")
	private String referenceType;
	
	public ResourceCurveMappingEntity() {
		
	}

	public ResourceCurveMappingEntity(Long id, ResourceCurveEntity projResourceCurveEntity, Long referenceId, String referenceType) {
		super();
		this.id = id;
		this.projResourceCurveEntity = projResourceCurveEntity;
		this.referenceId = referenceId;
		this.referenceType = referenceType;
	}

	public ResourceCurveMappingEntity(ResourceCurveEntity projResourceCurveEntity, Long referenceId, String referenceType) {
		super();
		this.projResourceCurveEntity = projResourceCurveEntity;
		this.referenceId = referenceId;
		this.referenceType = referenceType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ResourceCurveEntity getProjResourceCurveEntity() {
		return projResourceCurveEntity;
	}

	public void setProjResourceCurveEntity(ResourceCurveEntity projResourceCurveEntity) {
		this.projResourceCurveEntity = projResourceCurveEntity;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

}
