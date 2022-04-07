package com.rjtech.centrallib.model;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PAY_DEDUCTION_CODES")
public class PayDeductionCodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PDC_ID")
    private Long id;

   /* @Column(name = "PDC_STATUS")
    private Integer status;*/

    @ManyToOne
    @JoinColumn(name = "PDC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PDC_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "PDC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDC_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "PDC_CODE")
    private String code;

    @Column(name = "PDC_DESC")
    private String description;


    @ManyToOne
    @JoinColumn(name = "PDC_FC_ID")
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


	public FinanceCenterEntity getFinanceCenterEntity() {
		return financeCenterEntity;
	}


	public void setFinanceCenterEntity(FinanceCenterEntity financeCenterEntity) {
		this.financeCenterEntity = financeCenterEntity;
	}

    


 
}