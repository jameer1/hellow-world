package com.rjtech.centrallib.model;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Tax_payments_receiver_dtl")
public class TaxPaymentReceiverCodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TPR_ID")
    private Long id;

   /* @Column(name = "TRD_STATUS")
    private Integer status;*/

    @ManyToOne
    @JoinColumn(name = "TPR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TPR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "TPR_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "TPR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TPR_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "TPR_TAX_CODE")
    private String code;

    @Column(name = "TPR_TAX_DESC")
    private String description;

    @Column(name = "TPR_DEPT_NAME")
    private String nameofDepartment;

    @Column(name = "TPR_REG_ADDR")
    private String registerdAddress;

    @Column(name = "TPR_ACCOUNT_NO")
    private String accountNumber;

    @Column(name = "TPR_BANK_NAME")
    private String bankInstituteName;

    @Column(name = "TPR_BANK_CODE")
    private String bankCode;

    @ManyToOne
    @JoinColumn(name = "TPR_FC_ID")
    private FinanceCenterEntity financeCenterEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}*/

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

	/*public String getNameOfDept() {
		return nameOfDept;
	}

	public void setNameOfDept(String nameOfDept) {
		this.nameOfDept = nameOfDept;
	}

	public String getRegAddrs() {
		return regAddrs;
	}

	public void setRegAddrs(String regAddrs) {
		this.regAddrs = regAddrs;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}*/
	
	public String getNameofDepartment() {
        return nameofDepartment;
    }

    public void setNameofDepartment(String nameofDepartment) {
        this.nameofDepartment = nameofDepartment;
    }

    public String getRegisterdAddress() {
        return registerdAddress;
    }

    public void setRegisterdAddress(String registerdAddress) {
        this.registerdAddress = registerdAddress;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankInstituteName() {
        return bankInstituteName;
    }

    public void setBankInstituteName(String bankInstituteName) {
        this.bankInstituteName = bankInstituteName;
    }

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public FinanceCenterEntity getFinanceCenterEntity() {
		return financeCenterEntity;
	}

	public void setFinanceCenterEntity(FinanceCenterEntity financeCenterEntity) {
		this.financeCenterEntity = financeCenterEntity;
	}
	
	 /*public String toString() {
	    	return "accountNumber:"+accountNumber+" bankCode:"+bankCode+" nameofDepartment:"+nameofDepartment+" code:"+code+" bankCode:"+bankCode;
	    }*/

    }