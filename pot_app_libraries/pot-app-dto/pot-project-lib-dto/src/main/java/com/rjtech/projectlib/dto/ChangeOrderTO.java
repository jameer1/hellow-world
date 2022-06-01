package com.rjtech.projectlib.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projectlib.ProjManpowerTO;
public class ChangeOrderTO extends ProjectTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private Long id;
    private String coCode;
    private String contractType;
    private String contractId;
    private String purchaseOrderId;
    private String description;
    private Long internalApprovalUserId;
    private Long externalApprovalUserId;
    private Long originatorUserId;
    private Integer isExternalApprovalRequired;
    private String approvalStatus;
    private String reasonForChange;
    private Long createdBy;
    private Date createdOn;
    private Integer status;
    private List<ProjManpowerTO> projManpowerTOs = new ArrayList<ProjManpowerTO>();
    private String reqApprovalType;
    private LabelKeyTO internalApproverLabelKeyTO = new LabelKeyTO();
    private LabelKeyTO externalApproverLabelKeyTO = new LabelKeyTO();
    private LabelKeyTO requestorLabelKeyTO = new LabelKeyTO();
    
    
    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode( String coCode ) {
        this.coCode = coCode;
    }
    
    public String getContractType() {
        return contractType;
    }

    public void setContractType( String contractType ) {
        this.contractType = contractType;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId( String contractId ) {
        this.contractId = contractId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId( String purchaseOrderId ) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public Long getInternalApprovalUserId() {
        return internalApprovalUserId;
    }

    public void setInternalApprovalUserId( Long internalApprovalUserId ) {
        this.internalApprovalUserId = internalApprovalUserId;
    }
    
    public Long getExternalApprovalUserId() {
        return externalApprovalUserId;
    }

    public void setExternalApprovalUserId( Long externalApprovalUserId ) {
        this.externalApprovalUserId = externalApprovalUserId;
    }
        
    public Long getOriginatorUserId() {
        return originatorUserId;
    }

    public void setOriginatorUserId( Long originatorUserId ) {
        this.originatorUserId = originatorUserId;
    }
    
    public Integer getIsExternalApprovalRequired() {
        return isExternalApprovalRequired;
    }

    public void setIsExternalApprovalRequired( Integer isExternalApprovalRequired ) {
        this.isExternalApprovalRequired = isExternalApprovalRequired;
    }
    
    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus( String approvalStatus ) {
        this.approvalStatus = approvalStatus;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy( Long createdBy ) {
        this.createdBy = createdBy;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus( Integer status ) {
        this.status = status;
    }
    
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn( Date createdOn ) {
        this.createdOn = createdOn;
    }
    
    public String getReasonForChange() {
        return reasonForChange;
    }

    public void setReasonForChange( String reasonForChange ) {
        this.reasonForChange = reasonForChange;
    }
    
    public List<ProjManpowerTO> getProjManpowerTOs() {
        return projManpowerTOs;
    }

    public void setProjManpowerTOs( List<ProjManpowerTO> projManpowerTOs ) {
        this.projManpowerTOs = projManpowerTOs;
    }
    
    public String getReqApprovalType() {
        return reqApprovalType;
    }

    public void setReqApprovalType ( String reqApprovalType ) {
        this.reqApprovalType = reqApprovalType;
    }
    
    public LabelKeyTO getInternalApproverLabelKeyTO() {
        return internalApproverLabelKeyTO;
    }

    public void setInternalApproverLabelKeyTO( LabelKeyTO internalApproverLabelKeyTO ) {
        this.internalApproverLabelKeyTO = internalApproverLabelKeyTO;
    }
    
    public LabelKeyTO getExternalApproverLabelKeyTO() {
        return externalApproverLabelKeyTO;
    }

    public void setExternalApproverLabelKeyTO( LabelKeyTO externalApproverLabelKeyTO ) {
        this.externalApproverLabelKeyTO = externalApproverLabelKeyTO;
    }
    
    public LabelKeyTO getRequestorLabelKeyTO() {
        return requestorLabelKeyTO;
    }

    public void setRequestorLabelKeyTO( LabelKeyTO requestorLabelKeyTO ) {
        this.requestorLabelKeyTO = requestorLabelKeyTO;
    }
    
    public String toString() {
    	return " id:"+id+"approvalStatus:"+approvalStatus+" originatorUserId:"+originatorUserId+" internalApprovalUserId:"+internalApprovalUserId+" approvalType:"+reqApprovalType+"  contractType :"+contractType+ " contractId:  "+contractId;
    }
}
