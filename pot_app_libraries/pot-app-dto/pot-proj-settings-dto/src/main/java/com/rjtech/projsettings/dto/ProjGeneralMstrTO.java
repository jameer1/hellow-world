package com.rjtech.projsettings.dto;

import com.rjtech.calendar.dto.CalTO;
import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.finance.dto.ProfitCentreTO;

import java.math.BigDecimal;

public class ProjGeneralMstrTO extends ProjectTO {

    private static final long serialVersionUID = 6227488308998038304L;
    private Long id;
    private Long settingsMasterId;
    private Long companyId;
    private Long userId;
    private Long globalCalId;
    private Long profitCentreId;
    private Long projCalId;
    private Integer defualtHrs;
    private Integer maxHrs;
    private String address;
    private String defaultStatus;
    private String effectiveFrom;
    private String effectiveTo;
    private String isLatest;
    private String isDefault;
    private String contractNumber;

    private String isoAlpha3;
    private String geonameId;
    private String countryName;
    private String provisionName;
    private String currency;
    private String timezone;
    private String contractType;
    private String earnedHourSource;
    private String financeCentre;

    private CalTO calenderTO = new CalTO();
    private ProfitCentreTO profitCentreTO = new ProfitCentreTO();
    /*private CountryTO countryTO = new CountryTO();;*/
    private ResourceCurveTO resourceCurveTO = new ResourceCurveTO();
    private LabelKeyTO userLabelKeyTO = new LabelKeyTO();
    private CompanyTO companyTO = new CompanyTO();
    private BigDecimal percentOverCost;
    private String primaveraIntegration;
    
    public void setFinanceCentre(String financeCentre) {
    	this.financeCentre = financeCentre;
    }
    
    public String getFinanceCentre() {
    	return financeCentre;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public String getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(String geonameId) {
        this.geonameId = geonameId;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSettingsMasterId() {
        return settingsMasterId;
    }

    public void setSettingsMasterId(Long settingsMasterId) {
        this.settingsMasterId = settingsMasterId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProfitCentreId() {
        return profitCentreId;
    }

    public void setProfitCentreId(Long profitCentreId) {
        this.profitCentreId = profitCentreId;
    }

    public Integer getDefualtHrs() {
        return defualtHrs;
    }

    public void setDefualtHrs(Integer defualtHrs) {
        this.defualtHrs = defualtHrs;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getMaxHrs() {
        return maxHrs;
    }

    public void setMaxHrs(Integer maxHrs) {
        this.maxHrs = maxHrs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public Long getGlobalCalId() {
        return globalCalId;
    }

    public void setGlobalCalId(Long globalCalId) {
        this.globalCalId = globalCalId;
    }

    public Long getProjCalId() {
        return projCalId;
    }

    public void setProjCalId(Long projCalId) {
        this.projCalId = projCalId;
    }

    public ProfitCentreTO getProfitCentreTO() {
        return profitCentreTO;
    }

    public void setProfitCentreTO(ProfitCentreTO profitCentreTO) {
        this.profitCentreTO = profitCentreTO;
    }

    public CalTO getCalenderTO() {
        return calenderTO;
    }

    public void setCalenderTO(CalTO calenderTO) {
        this.calenderTO = calenderTO;
    }

    /*public CountryTO getCountryTO() {
    	return countryTO;
    }
    
    public void setCountryTO(CountryTO countryTO) {
    	this.countryTO = countryTO;
    }*/

    public LabelKeyTO getUserLabelKeyTO() {
        return userLabelKeyTO;
    }

    public void setUserLabelKeyTO(LabelKeyTO userLabelKeyTO) {
        this.userLabelKeyTO = userLabelKeyTO;
    }

    public ResourceCurveTO getResourceCurveTO() {
        return resourceCurveTO;
    }

    public void setResourceCurveTO(ResourceCurveTO resourceCurveTO) {
        this.resourceCurveTO = resourceCurveTO;
    }

    public CompanyTO getCompanyTO() {
        return companyTO;
    }

    public void setCompanyTO(CompanyTO companyTO) {
        this.companyTO = companyTO;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(String effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(String isLatest) {
        this.isLatest = isLatest;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getContractType() {
        return contractType;
    }

    public BigDecimal getPercentOverCost() {
        return percentOverCost;
    }

    public void setPercentOverCost(BigDecimal percentOverCost) {
        this.percentOverCost = percentOverCost;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

	public String getPrimaveraIntegration() {
		return primaveraIntegration;
	}

	public void setPrimaveraIntegration(String primaveraIntegration) {
		this.primaveraIntegration = primaveraIntegration;
	}

	public String getEarnedHourSource() {
		return earnedHourSource;
	}

	public void setEarnedHourSource(String earnedHourSource) {
		this.earnedHourSource = earnedHourSource;
	}
}
