package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.LabelKeyTO;

public class AssignCostCodesTO extends ClientTO { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer costCodeId;
	private String costCodeDescription;
	private String costCodePercentage;
	private String costCodeAmount;
	private LabelKeyTO labelKeyTO;
		
	
	public Integer getCostCodeId() {
		return costCodeId;
	}
	public void setCostCodeId(Integer costCodeId) {
		this.costCodeId = costCodeId;
	}
	public String getCostCodeDescription() {
		return costCodeDescription;
	}
	public void setCostCodeDescription(String costCodeDescription) {
		this.costCodeDescription = costCodeDescription;
	}
	public String getCostCodePercentage() {
		return costCodePercentage;
	}
	public void setCostCodePercentage(String costCodePercentage) {
		this.costCodePercentage = costCodePercentage;
	}
	public String getCostCodeAmount() {
		return costCodeAmount;
	}
	public void setCostCodeAmount(String costCodeAmount) {
		this.costCodeAmount = costCodeAmount;
	}
	public LabelKeyTO getLabelKeyTO() {
		return labelKeyTO;
	}
	public void setLabelKeyTO(LabelKeyTO labelKeyTO) {
		this.labelKeyTO = labelKeyTO;
	}
	

}
