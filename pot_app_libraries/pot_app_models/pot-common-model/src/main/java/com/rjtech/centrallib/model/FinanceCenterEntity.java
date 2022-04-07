package com.rjtech.centrallib.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "finance_center")
public class FinanceCenterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FC_ID")
    private Long id;

    @Column(name = "FC_COUNTRY_NAME")
    private String countryName;
    
    @Column(name = "FC_PROVISION_NAME")
    private String provisionName;
    
    @Column(name = "FC_COUNTRY_CODE")
    private String countryCode;
    
    @Column(name = "FC_PROVISION_CODE")
    private String provisionCode;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "FC_EFFECTIVE_FROM", updatable = false)
    private Date effectiveFrom;
    
    @Column(name = "FC_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FC_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "FC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FC_UPDATED_ON")
    private Date updatedOn;
    
    @OneToMany(mappedBy = "financeCenterEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UnitOfPayRateEntity> unitOfPayRateEntity = new ArrayList<>();
    
    @OneToMany(mappedBy = "financeCenterEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeePayRoleEntity> employeePayRoleEntity = new ArrayList<>();
    
    @OneToMany(mappedBy = "financeCenterEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NonRegularPayAllowanceEntity> nonRegularPayAllowanceEntity = new ArrayList<>();

    @OneToMany(mappedBy = "financeCenterEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegularPayAllowanceEntity> regularPayAllowanceEntity = new ArrayList<>();
    
    @OneToMany(mappedBy = "financeCenterEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SuperProvidentFundEntity> superProvidentFundEntity = new ArrayList<>();
    
    @OneToMany(mappedBy = "financeCenterEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaxRatesRulesCodeEntity> taxRatesRulesCodeEntity = new ArrayList<>();
    
    @OneToMany(mappedBy = "financeCenterEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PayDeductionCodeEntity> payDeductionCodeEntity = new ArrayList<>();
    
    @OneToMany(mappedBy = "financeCenterEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaxPaymentReceiverCodeEntity> taxPaymentReceiverCodeEntity = new ArrayList<>();
   
    
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvisionCode() {
        return provisionCode;
    }

    public void setProvisionCode(String provisionCode) {
        this.provisionCode = provisionCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public List<UnitOfPayRateEntity> getUnitOfPayRateEntity() {
        return unitOfPayRateEntity;
    }

    public void setUnitOfPayRateEntity(List<UnitOfPayRateEntity> unitOfPayRateEntity) {
        this.unitOfPayRateEntity = unitOfPayRateEntity;
    }

    public List<EmployeePayRoleEntity> getEmployeePayRoleEntity() {
        return employeePayRoleEntity;
    }

    public void setEmployeePayRoleEntity(List<EmployeePayRoleEntity> employeePayRoleEntity) {
        this.employeePayRoleEntity = employeePayRoleEntity;
    }

    public List<NonRegularPayAllowanceEntity> getNonRegularPayAllowanceEntity() {
        return nonRegularPayAllowanceEntity;
    }

    public void setNonRegularPayAllowanceEntity(List<NonRegularPayAllowanceEntity> nonRegularPayAllowanceEntity) {
        this.nonRegularPayAllowanceEntity = nonRegularPayAllowanceEntity;
    }


       public List<RegularPayAllowanceEntity> getRegularPayAllowanceEntity() {
       return regularPayAllowanceEntity;
     }

       public void setRegularPayAllowanceEntity(List<RegularPayAllowanceEntity> regularPayAllowanceEntity) {
        this.regularPayAllowanceEntity = regularPayAllowanceEntity;
      }
	
		public List<SuperProvidentFundEntity> getSuperProvidentFundEntity() {
			return superProvidentFundEntity;
		}
	
		public void setSuperProvidentFundEntity(List<SuperProvidentFundEntity> superProvidentFundEntity) {
			this.superProvidentFundEntity = superProvidentFundEntity;
		}
	
		public List<TaxRatesRulesCodeEntity> getTaxRatesRulesCodeEntity() {
			return taxRatesRulesCodeEntity;
		}
	
		public void setTaxRatesRulesCodeEntity(List<TaxRatesRulesCodeEntity> taxRatesRulesCodeEntity) {
			this.taxRatesRulesCodeEntity = taxRatesRulesCodeEntity;
		}
	  
		public List<PayDeductionCodeEntity> getPayDeductionCodeEntity() {
			return payDeductionCodeEntity;
		}
	
		public void setPayDeductionCodeEntity(List<PayDeductionCodeEntity> payDeductionCodeEntity) {
			this.payDeductionCodeEntity = payDeductionCodeEntity;
		}
	  
		public List<TaxPaymentReceiverCodeEntity> getTaxPaymentReceiverCodeEntity() {
			return taxPaymentReceiverCodeEntity;
		}
	
		public void setTaxPaymentReceiverCodeEntity(List<TaxPaymentReceiverCodeEntity> taxPaymentReceiverCodeEntity) {
			this.taxPaymentReceiverCodeEntity = taxPaymentReceiverCodeEntity;
		}
    
}