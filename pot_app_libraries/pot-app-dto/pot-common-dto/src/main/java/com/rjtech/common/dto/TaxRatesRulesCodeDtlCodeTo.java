package com.rjtech.common.dto;

public class TaxRatesRulesCodeDtlCodeTo extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
   
    
    private Long id;
    private Integer status;
    private Integer type;
    private String notes;
    private String minRange;
    private String maxRange;
    private Integer taxAmount;
    private Integer taxPercentage;
    
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

    /*public String toString() {
    	return "taxPercentage:"+taxPercentage+" taxAmount:"+taxAmount+" maxRange:"+maxRange+" minRange:"+minRange+" notes:"+notes;
    }*/
    

}
