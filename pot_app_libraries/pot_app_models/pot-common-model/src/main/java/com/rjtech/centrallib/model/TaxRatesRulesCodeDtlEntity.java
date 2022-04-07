package com.rjtech.centrallib.model;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "tax_code_rules_dtl")
public class TaxRatesRulesCodeDtlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TCRD_ID")
    private Long id;
    
    @Column(name = "TCRD_STATUS")
    private Integer status;
	
	@Column(name = "TCRD_TYPE")
    private Integer type;
	
	@Column(name = "TCRD_NOTES")
    private String notes;
	
	@Column(name = "TCRD_MIN_RANGE")
    private String minRange;
	
	@Column(name = "TCRD_MAX_RANGE")
    private String maxRange;
	
	@Column(name = "TCRD_TAX_AMT")
    private Integer taxAmount;
	
	@Column(name = "TCRD_TAX_PERCENTAGE")
    private Integer taxPercentage;
	
    @ManyToOne
    @JoinColumn(name = "TCRD_TCR_ID")
    private TaxRatesRulesCodeEntity taxRatesRulesCodeEntity;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getMinRange() {
		return minRange;
	}

	public void setMinRange(String minRange) {
		this.minRange = minRange;
	}

	public String getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(String maxRange) {
		this.maxRange = maxRange;
	}

	public Integer getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Integer taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Integer getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Integer taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public TaxRatesRulesCodeEntity getTaxRatesRulesCodeEntity() {
		return taxRatesRulesCodeEntity;
	}

	public void setTaxRatesRulesCodeEntity(TaxRatesRulesCodeEntity taxRatesRulesCodeEntity) {
		this.taxRatesRulesCodeEntity = taxRatesRulesCodeEntity;
	}

	

	  
 
}