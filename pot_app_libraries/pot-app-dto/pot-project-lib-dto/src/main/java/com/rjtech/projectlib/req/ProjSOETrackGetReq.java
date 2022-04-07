package com.rjtech.projectlib.req;

import java.util.List;
import com.rjtech.common.dto.ProjectTO;

public class ProjSOETrackGetReq extends ProjectTO {

    private static final long serialVersionUID = -6671175298681215590L;
    private Long soeId;
    private String soeStatus;
    private Character isExternalApprovalRequired;
    private Long internalApprovalUserId;
    private Long externalApprovalUserId;
    private String internalApproverComments;
    private String externalApproverComments;
    private String comments;
    private Long loggedInUser;
    private List<Long> soeIds;

    public Long getSoeId() {
        return soeId;
    }

    public void setSoeId(Long soeId) {
        this.soeId = soeId;
    }

    public void setIsExternalApprovalRequired( Character isExternalApprovalRequired ) {
    	this.isExternalApprovalRequired = isExternalApprovalRequired;
    }
    
    public Character getIsExternalApprovalRequired() {
    	return isExternalApprovalRequired;
    }
    
    public Long getExternalApprovalUserId() {
        return externalApprovalUserId;
    }

    public void setExternalApprovalUserId( Long externalApprovalUserId ) {
        this.externalApprovalUserId = externalApprovalUserId;
    }
    
    public Long getInternalApprovalUserId() {
        return internalApprovalUserId;
    }

    public void setInternalApprovalUserId( Long internalApprovalUserId ) {
        this.internalApprovalUserId = internalApprovalUserId;
    }
    
    public void setSoeStatus( String soeStatus ) {
    	this.soeStatus = soeStatus;
    }
    
    public String getSoeStatus() {
    	return soeStatus;
    }
    
    public void setInternalApproverComments( String internalApproverComments ) {
    	this.internalApproverComments = internalApproverComments;
    }
    
    public String getInternalApproverComments() {
    	return internalApproverComments;
    }
    
    public void setExternalApproverComments( String externalApproverComments ) {
    	this.externalApproverComments = externalApproverComments;
    }
    
    public String getExternalApproverComments() {
    	return externalApproverComments;
    }
    
    public void setComments( String comments ) {
    	this.comments = comments;
    }
    
    public String getComments() {
    	return comments;
    }
    
    public Long getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser( Long loggedInUser ) {
        this.loggedInUser = loggedInUser;
    }
    
    public List<Long> getSoeIds() {
        return soeIds;
    }

    public void setSoeIds(List<Long> soeIds) {
        this.soeIds = soeIds;
    }
    
    public String toString() {
    	return "soeStatus:"+soeStatus+ " internalApprovalUserId:"+internalApprovalUserId+" externalApprovalUserId:"+externalApprovalUserId+" soeId:"+soeId+" isExternalApprovalRequired:"+isExternalApprovalRequired;
    }
}
