package com.rjtech.centrallib.model;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "Super_provident_fund")
public class SuperProvidentFundEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPF_ID")
    private Long id;

    @Column(name = "SPF_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "SPF_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SPF_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "SPF_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "SPF_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SPF_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "SPF_CODE")
    private String code;

    @Column(name = "SPF_DESC")
    private String description;
    
    @Column(name = "SPF_IS_TAXABLE")
    private String isTaxable;

    @Column(name = "SPF_CREDIT_RUN_CYCLE")
    private String creditRunCycle;

    @Column(name = "SPF_CREDIT_DUE_DATE")
    private String creditRunDueDate;


    @ManyToOne
    @JoinColumn(name = "SPF_FC_ID")
    private FinanceCenterEntity financeCenterEntity;
    
    @ManyToOne
    @JoinColumn(name = "SPF_FTCT_ID")
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

	public String getIsTaxable() {
		return isTaxable;
	}

	public void setIsTaxable(String isTaxable) {
		this.isTaxable = isTaxable;
	}

	public String getCreditRunCycle() {
		return creditRunCycle;
	}

	public void setCreditRunCycle(String creditRunCycle) {
		this.creditRunCycle = creditRunCycle;
	}

	public String getCreditRunDueDate() {
		return creditRunDueDate;
	}

	public void setCreditRunDueDate(String creditRunDueDate) {
		this.creditRunDueDate = creditRunDueDate;
	}

	public FinanceCenterEntity getFinanceCenterEntity() {
		return financeCenterEntity;
	}

	public void setFinanceCenterEntity(FinanceCenterEntity financeCenterEntity) {
		this.financeCenterEntity = financeCenterEntity;
	}

	public CountryProvinceCodeEntity getCountryProvinceCodeEntity() {
		return CountryProvinceCodeEntity;
	}

	public void setCountryProvinceCodeEntity(CountryProvinceCodeEntity countryProvinceCodeEntity) {
		CountryProvinceCodeEntity = countryProvinceCodeEntity;
	}
 
}