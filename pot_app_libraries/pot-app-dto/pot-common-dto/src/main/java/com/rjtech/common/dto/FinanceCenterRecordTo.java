package com.rjtech.common.dto;

import java.util.List;

public class FinanceCenterRecordTo extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Integer status;
    private String countryName;
    private String countryCode;
    private String effectiveFrom;
    private List<UnitOfPayRate> unitOfPayRates;
    private List<EmpPayRoleCycle> empPayRollCycles;
    private List<NonRegularPayAllowance> nonRegularPayAllowances;
    private List<RegularPayAllowance> regularPayAllowances;
    //private List<SuperProvidentFund> superProvidentFund;
    private List<SuperProvidentFund> superFundCodes;
    private List<TaxRateRulesCodes>  taxRateRulesCodes;
   // private List<TaxRateRulesCodes>  taxCodesTaxRules;
	private List<PayDeductionCodes> payDeductionCodes; 
	//private List<TaxPaymentReceiverCodes> taxPaymentReceiverCodes;
	private List<TaxPaymentReceiverCodes> taxPaymentsReceivers;
    private String displayFinanceCenterId;
    private String provisionName;
    private String provisionCode;
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
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getEffectiveFrom() {
		return effectiveFrom;
	}
	public void setEffectiveFrom(String effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	public List<UnitOfPayRate> getUnitOfPayRates() {
		return unitOfPayRates;
	}
	public void setUnitOfPayRates(List<UnitOfPayRate> unitOfPayRates) {
		this.unitOfPayRates = unitOfPayRates;
	}
	public List<EmpPayRoleCycle> getEmpPayRollCycles() {
		return empPayRollCycles;
	}
	public void setEmpPayRollCycles(List<EmpPayRoleCycle> empPayRollCycles) {
		this.empPayRollCycles = empPayRollCycles;
	}
	public List<NonRegularPayAllowance> getNonRegularPayAllowances() {
		return nonRegularPayAllowances;
	}
	public void setNonRegularPayAllowances(List<NonRegularPayAllowance> nonRegularPayAllowances) {
		this.nonRegularPayAllowances = nonRegularPayAllowances;
	}
	public List<RegularPayAllowance> getRegularPayAllowances() {
		return regularPayAllowances;
	}
	public void setRegularPayAllowances(List<RegularPayAllowance> regularPayAllowances) {
		this.regularPayAllowances = regularPayAllowances;
	}
	public List<SuperProvidentFund> getSuperFundCodes() {
		return superFundCodes;
	}
	public void setSuperFundCodes(List<SuperProvidentFund> superFundCodes) {
		this.superFundCodes = superFundCodes;
	}
	/*public List<TaxRateRulesCodes> getTaxCodesTaxRules() {
		return taxCodesTaxRules;
	}
	public void setTaxCodesTaxRules(List<TaxRateRulesCodes> taxCodesTaxRules) {
		this.taxCodesTaxRules = taxCodesTaxRules;
	}*/
	public List<PayDeductionCodes> getPayDeductionCodes() {
		return payDeductionCodes;
	}
	public void setPayDeductionCodes(List<PayDeductionCodes> payDeductionCodes) {
		this.payDeductionCodes = payDeductionCodes;
	}
	public List<TaxPaymentReceiverCodes> getTaxPaymentsReceivers() {
		return taxPaymentsReceivers;
	}
	public void setTaxPaymentsReceivers(List<TaxPaymentReceiverCodes> taxPaymentsReceivers) {
		this.taxPaymentsReceivers = taxPaymentsReceivers;
	}
	public String getDisplayFinanceCenterId() {
		return displayFinanceCenterId;
	}
	public void setDisplayFinanceCenterId(String displayFinanceCenterId) {
		this.displayFinanceCenterId = displayFinanceCenterId;
	}
	public String getProvisionName() {
		return provisionName;
	}
	public void setProvisionName(String provisionName) {
		this.provisionName = provisionName;
	}
	public String getProvisionCode() {
		return provisionCode;
	}
	public void setProvisionCode(String provisionCode) {
		this.provisionCode = provisionCode;
	}
	/*public List<TaxPaymentReceiverCodes> getTaxPaymentReceiverCodes() {
        return taxPaymentReceiverCodes;
    }

    public void setTaxPaymentReceiverCodes(List<TaxPaymentReceiverCodes> taxPaymentReceiverCodes) {
        this.taxPaymentReceiverCodes = taxPaymentReceiverCodes;
    }*/
    
    public List<TaxRateRulesCodes> getTaxRateRulesCodes() {
        return taxRateRulesCodes;
    }

    public void setTaxRateRulesCodes(List<TaxRateRulesCodes> taxRateRulesCodes) {
        this.taxRateRulesCodes = taxRateRulesCodes;
    }
    
    }
