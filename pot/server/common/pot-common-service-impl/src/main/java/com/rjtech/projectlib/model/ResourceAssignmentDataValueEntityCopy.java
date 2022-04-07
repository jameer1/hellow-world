package com.rjtech.projectlib.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "p6_resource_assignment_data_value")
public class ResourceAssignmentDataValueEntityCopy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p6_radv_id")
    private Long id;
	
	@NotNull
	@Temporal(TemporalType.DATE)
    @Column(name = "p6_radv_forecast_dt")
    private Date forecastDate;
	
	@Column(name = "p6_radv_budget_forecast")
    private double budgetUnits;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p6_radv_assignment_dataid")
    private ResourceAssignmentDataEntityCopy resourceAssignmentDataEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getForecastDate() {
		return forecastDate;
	}

	public void setForecastDate(Date forecastDate) {
		this.forecastDate = forecastDate;
	}

	public double getBudgetUnits() {
		return budgetUnits;
	}

	public void setBudgetUnits(double budgetUnits) {
		this.budgetUnits = budgetUnits;
	}

	public ResourceAssignmentDataEntityCopy getResourceAssignmentDataEntity() {
		return resourceAssignmentDataEntity;
	}

	public void setResourceAssignmentDataEntity(ResourceAssignmentDataEntityCopy resourceAssignmentDataEntity) {
		this.resourceAssignmentDataEntity = resourceAssignmentDataEntity;
	}

}
