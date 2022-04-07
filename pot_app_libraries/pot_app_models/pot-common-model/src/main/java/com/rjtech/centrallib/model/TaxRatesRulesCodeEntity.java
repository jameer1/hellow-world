package com.rjtech.centrallib.model;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Tax_code_rules")
public class TaxRatesRulesCodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TCR_ID")
    private Long id;

    @Column(name = "TCR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "TCR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TCR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "TCR_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "TCR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TCR_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "TCR_CODE")
    private String code;

    @Column(name = "TCR_DESC")
    private String description;
    
   /* @Column(name = "TCR_APPLICABLE_FOR")
    private String applicableFor;*/

    @Column(name = "TCR_CREDIT_RUNCYCLE")
    private String creditRunCycle;

   
    @Column(name = "TCR_DUE_DATE")
    private String creditRunDueDate;

    @Column(name = "TCR_RULES")
    private String taxRateRules;
    
    @ManyToOne
    @JoinColumn(name = "TCR_FC_ID")
    private FinanceCenterEntity financeCenterEntity;
    
    @OneToMany(mappedBy = "taxRatesRulesCodeEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaxRatesRulesCodeDtlEntity> taxRatesRulesCodeDtlEntity = new ArrayList<>();

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

	/*public String getApplicableFor() {
		return applicableFor;
	}

	public void setApplicableFor(String applicableFor) {
		this.applicableFor = applicableFor;
	}*/

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

	public String getTaxRateRules() {
		return taxRateRules;
	}

	public void setTaxRateRules(String taxRateRules) {
		this.taxRateRules = taxRateRules;
	}

	public FinanceCenterEntity getFinanceCenterEntity() {
		return financeCenterEntity;
	}

	public void setFinanceCenterEntity(FinanceCenterEntity financeCenterEntity) {
		this.financeCenterEntity = financeCenterEntity;
	}
	
	public List<TaxRatesRulesCodeDtlEntity> getTaxRatesRulesCodeDtlEntity() {
		return taxRatesRulesCodeDtlEntity;
	}

	public void setTaxRatesRulesCodeDtlEntity(List<TaxRatesRulesCodeDtlEntity> taxRatesRulesCodeDtlEntity) {
		this.taxRatesRulesCodeDtlEntity = taxRatesRulesCodeDtlEntity;
	}


      
 
}