package com.rjtech.common.dto;

public class CountryProvinceCodeTo extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
   
    
    private Long id;
    private Integer status;
    private String countryCode;
    private String countryName;
    private String provisionName;
    private FinancialYearTo financialYearData;
    private FinancialHalfYearTo financialHalfYearData;
    private FinancialQuarterYearTo financialQuarterYearData;
    
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
   
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getProvisionName() {
        return provisionName;
    }
    public void setProvisionName(String provisionName) {
        this.provisionName = provisionName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getStatus() {
        return status;
    }
 /*   public void setStatus(int status) {
        this.status = status;
    }*/
    public void setStatus(Integer status) {
        this.status = status;
    }
    public FinancialYearTo getFinancialYearData() {
        return financialYearData;
    }
    public void setFinancialYearData(FinancialYearTo financialYearData) {
        this.financialYearData = financialYearData;
    }
    public FinancialHalfYearTo getFinancialHalfYearData() {
        return financialHalfYearData;
    }
    public void setFinancialHalfYearData(FinancialHalfYearTo financialHalfYearData) {
        this.financialHalfYearData = financialHalfYearData;
    }
    public FinancialQuarterYearTo getFinancialQuarterYearData() {
        return financialQuarterYearData;
    }
    public void setFinancialQuarterYearData(FinancialQuarterYearTo financialQuarterYearData) {
        this.financialQuarterYearData = financialQuarterYearData;
    }
    
    

}
