package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.calendar.model.GlobalCalEntity;
import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.finance.model.ProfitCentreEntity;
//import com.rjtech.finance.model.ProfitCentreEntityCopy;

@Entity
@Table(name = "proj_general_values")
public class ProjGeneralMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGV_ID")
    private Long id;

    @Column(name = "PGV_CONTRACT_NUM")
    private String contractNumber;

    @ManyToOne
    @JoinColumn(name = "PGV_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @ManyToOne
    @JoinColumn(name = "PGV_USR_ID")
    private UserMstrEntity respManager;

    @Column(name = "PGV_DFL_HRS")
    private Integer defualtHrs;

    @Column(name = "PGV_MAX_HRS")
    private Integer maxHrs;

    @Column(name = "PGV_ADDRESS")
    private String address;

    @Column(name = "PGV_STATUS")
    private Integer status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PGV_EFFECTIVE_FROM")
    private Date effectiveFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PGV_EFFECTIVE_TO")
    private Date effectiveTo;

    @Column(name = "PGV_IS_LATEST")
    private String isLatest;

    @Column(name = "PGV_IS_DEFAULT")
    private String isDefault;

    @ManyToOne
    @JoinColumn(name = "PGV_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PGV_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PGV_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PGV_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PGV_GCAL_ID")
    private GlobalCalEntity globalCalEntity;

    @Column(name = "PGV_CON_CODE")
    private String isoAlpha3;

    @Column(name = "PGV_GEONAME_ID")
    private String geonameId;

    @Column(name = "PGV_CON_NAME")
    private String countryName;

    @Column(name = "PGV_PROVISION_NAME")
    private String provisionName;

    @Column(name = "PGV_CURRENCY")
    private String currency;

    @Column(name = "PGV_TIMEZONE")
    private String timezone;

    @Column(name = "PGV_CONTRACT_TYPE")
    private String contractType;

    @ManyToOne
    @JoinColumn(name = "PGV_FPC_ID")
    private ProfitCentreEntity profitCentreEntity;

    @ManyToOne
    @JoinColumn(name = "PGV_RC_ID")
    private ResourceCurveEntity projResourceCurveEntity;

    @ManyToOne
    @JoinColumn(name = "PGV_CMP_ID")
    private CompanyMstrEntity companyMstrEntity;

    @Column(name = "PGV_DEF_STATUS")
    private String defaultStatus;

    @Column(name = "PGV_OVERCOST")
    private BigDecimal percentOverCost;
    
    @Column(name = "pgv_p6_integration_status")
    private String primaveraIntegration;
    
    @Column(name = "earned_hrs_source")
    private String earnedHourSource;
    
    @Column(name = "FINANCE_CENTRE")
    private String financeCentre;
    
    
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

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public UserMstrEntity getRespManager() {
        return respManager;
    }

    public void setRespManager(UserMstrEntity respManager) {
        this.respManager = respManager;
    }

    public Integer getDefualtHrs() {
        return defualtHrs;
    }

    public void setDefualtHrs(Integer defualtHrs) {
        this.defualtHrs = defualtHrs;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(String isLatest) {
        this.isLatest = isLatest;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public GlobalCalEntity getGlobalCalEntity() {
        return globalCalEntity;
    }

    public void setGlobalCalEntity(GlobalCalEntity globalCalEntity) {
        this.globalCalEntity = globalCalEntity;
    }

    public ProfitCentreEntity getProfitCentreEntity() {
        return profitCentreEntity;
    }

    public void setProfitCentreEntity(ProfitCentreEntity profitCentreEntity) {
        this.profitCentreEntity = profitCentreEntity;
    }

    public ResourceCurveEntity getProjResourceCurveEntity() {
        return projResourceCurveEntity;
    }

    public void setProjResourceCurveEntity(ResourceCurveEntity projResourceCurveEntity) {
        this.projResourceCurveEntity = projResourceCurveEntity;
    }

    public CompanyMstrEntity getCompanyMstrEntity() {
        return companyMstrEntity;
    }

    public void setCompanyMstrEntity(CompanyMstrEntity companyMstrEntity) {
        this.companyMstrEntity = companyMstrEntity;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public BigDecimal getPercentOverCost() {
        return percentOverCost;
    }

    public void setPercentOverCost(BigDecimal percentOverCost) {
        this.percentOverCost = percentOverCost;
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
