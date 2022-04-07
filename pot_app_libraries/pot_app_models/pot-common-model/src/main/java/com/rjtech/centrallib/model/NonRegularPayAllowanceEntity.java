package com.rjtech.centrallib.model;

import java.io.Serializable;
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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the weather_mstr database table.
 * 
 */
@Entity
@Table(name = "Finance_nregular_allowances")
public class NonRegularPayAllowanceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FNRA_ID")
    private Long id;
    
    @Column(name = "FNRA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FNRA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FNRA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FNRA_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "FNRA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FNRA_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "FNRA_CODE")
    private String code;

    @Column(name = "FNRA_COMMENTS")
    private String description;
    
    @Column(name = "FNRA_NAME")
    private String name;
    
    @Column(name = "FNRA_TAX_APPLICABLE")
    private String isTaxable;
    
    @Column(name = "FNRA_TAX_TYPE")
    private String taxType;

    @ManyToOne
    @JoinColumn(name = "FNRA_FC_ID")
    private FinanceCenterEntity financeCenterEntity;
    
   /* @ManyToOne
    @JoinColumn(name = "FNRA_CRM_ID", updatable = false)
    private ClientRegEntity clientId;*/
    
    @ManyToOne
    @JoinColumn(name = "FNRA_FTCT_ID")
    private CountryProvinceCodeEntity CountryProvinceCodeEntity;

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

	public ClientRegEntity getClientId() {
		return clientId;
	}

	public void setClientId(ClientRegEntity clientId) {
		this.clientId = clientId;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsTaxable() {
		return isTaxable;
	}

	public void setIsTaxable(String isTaxable) {
		this.isTaxable = isTaxable;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public FinanceCenterEntity getFinanceCenterEntity() {
		return financeCenterEntity;
	}

	public void setFinanceCenterEntity(FinanceCenterEntity financeCenterEntity) {
		this.financeCenterEntity = financeCenterEntity;
	}

	/*public ClientRegEntity getClientId() {
		return clientId;
	}

	public void setClientId(ClientRegEntity clientId) {
		this.clientId = clientId;
	}*/

	public CountryProvinceCodeEntity getCountryProvinceCodeEntity() {
		return CountryProvinceCodeEntity;
	}

	public void setCountryProvinceCodeEntity(CountryProvinceCodeEntity countryProvinceCodeEntity) {
		CountryProvinceCodeEntity = countryProvinceCodeEntity;
	}
    
      
 
}