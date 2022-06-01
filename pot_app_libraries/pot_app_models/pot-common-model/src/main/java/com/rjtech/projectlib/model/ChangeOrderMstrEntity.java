package com.rjtech.projectlib.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projsettings.model.COProjCostBudgetEntity;
import com.rjtech.projsettings.model.COProjManpowerEntity;
import com.rjtech.projsettings.model.COProjectMaterialBudgetEntity;
import com.rjtech.projsettings.model.COProjectPlantsDtlEntity;

/**
 * The persistent class for the change_order_mstr database table.
 * 
 */

@Entity
@Table(name = "change_order_mstr")
public class ChangeOrderMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_ID")
    private Long id;

    /*
CO_CONTRACT_ID                            VARCHAR2(150) 
CO_PURCHASE_ORDER_ID                      VARCHAR2(150)
     */

    @Column(name = "CO_CODE")
    private String code;
    
    @Column(name = "CONTRACT_TYPE")
    private String contractType;  
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROJ_ID")
    private ProjMstrEntity projectId;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "IS_EXT_APPRVL_REQ")
    private Integer isExternalApprovalRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORIGINATOR_USER_ID", updatable = false)
    private UserMstrEntity originatorUserId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INTRNL_APPR_USER_ID", updatable = false)
    private UserMstrEntity internalApproverUserId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXTERNAL_APPR_USER_ID", updatable = false)
    private UserMstrEntity externalApproverUserId;
    
    @Column(name = "REASON_FOR_CHANGE")
    private String reasonForChange;
    
    @Column(name = "APPROVAL_STATUS")
    private String approvalStatus;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "STATUS")
    private Integer status;
    
    
    @OneToMany(mappedBy = "changeOrderMstr", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ChangeOrderSOWEntity> ChangeOrderSow = new HashSet<ChangeOrderSOWEntity>();
    
    @OneToMany(mappedBy = "changeOrderMstr", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<COProjCostBudgetEntity> coProjCostBudget = new HashSet<COProjCostBudgetEntity>();
    
    @OneToMany(mappedBy = "changeOrderMstr", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<COProjectMaterialBudgetEntity> coProjectMaterialBudget = new HashSet<COProjectMaterialBudgetEntity>();
    
    @OneToMany(mappedBy = "changeOrderMstr", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<COProjectPlantsDtlEntity> coProjectPlantsDtl = new HashSet<COProjectPlantsDtlEntity>();
    
    @OneToMany(mappedBy = "changeOrderMstr", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<COProjManpowerEntity> coProjManpowerEntity = new HashSet<COProjManpowerEntity>();

    
    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType( String contractType ) {
        this.contractType = contractType;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn( Date createdOn ) {
        this.createdOn = createdOn;
    }
   
    public ProjMstrEntity getProjectId() {
        return projectId;
    }

    public void setProjectId( ProjMstrEntity projectId ) {
        this.projectId = projectId;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy( UserMstrEntity createdBy ) {
        this.createdBy = createdBy;
    }
    
    public UserMstrEntity getOriginatorUserId() {
        return originatorUserId;
    }

    public void setOriginatorUserId( UserMstrEntity originatorUserId ) {
        this.originatorUserId = originatorUserId;
    }
    
    public UserMstrEntity getInternalApproverUserId() {
        return internalApproverUserId;
    }

    public void setInternalApproverUserId( UserMstrEntity internalApproverUserId ) {
        this.internalApproverUserId = internalApproverUserId;
    }
    
    public UserMstrEntity getExternalApproverUserId() {
        return externalApproverUserId;
    }

    public void setExternalApproverUserId( UserMstrEntity externalApproverUserId ) {
        this.externalApproverUserId = externalApproverUserId;
    }
    
    public Integer getIsExternalApprovalRequired() {
        return isExternalApprovalRequired;
    }

    public void setIsExternalApprovalRequired( Integer isExternalApprovalRequired ) {
        this.isExternalApprovalRequired = isExternalApprovalRequired;
    }
    
    public String getReasonForChange() {
        return reasonForChange;
    }

    public void setReasonForChange( String reasonForChange ) {
        this.reasonForChange = reasonForChange;
    }
    
    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus( String approvalStatus ) {
        this.approvalStatus = approvalStatus;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus( Integer status ) {
        this.status = status;
    }

	public Set<ChangeOrderSOWEntity> getChangeOrderSow() {
		return ChangeOrderSow;
	}

	public void setChangeOrderSow(Set<ChangeOrderSOWEntity> changeOrderSow) {
		ChangeOrderSow = changeOrderSow;
	}

	public Set<COProjCostBudgetEntity> getCoProjCostBudget() {
		return coProjCostBudget;
	}

	public void setCoProjCostBudget(Set<COProjCostBudgetEntity> coProjCostBudget) {
		this.coProjCostBudget = coProjCostBudget;
	}

	public Set<COProjectMaterialBudgetEntity> getCoProjectMaterialBudget() {
		return coProjectMaterialBudget;
	}

	public void setCoProjectMaterialBudget(Set<COProjectMaterialBudgetEntity> coProjectMaterialBudget) {
		this.coProjectMaterialBudget = coProjectMaterialBudget;
	}

	public Set<COProjectPlantsDtlEntity> getCoProjectPlantsDtl() {
		return coProjectPlantsDtl;
	}

	public void setCoProjectPlantsDtl(Set<COProjectPlantsDtlEntity> coProjectPlantsDtl) {
		this.coProjectPlantsDtl = coProjectPlantsDtl;
	}

	public Set<COProjManpowerEntity> getCoProjManpowerEntity() {
		return coProjManpowerEntity;
	}

	public void setCoProjManpowerEntity(Set<COProjManpowerEntity> coProjManpowerEntity) {
		this.coProjManpowerEntity = coProjManpowerEntity;
	}
    
   /* public String toString() {
    	return " id:"+id+" code:"+code+" status:"+status+" internalApproverUserId:"+this.internalApproverUserId.getUserId();
    }*/
}