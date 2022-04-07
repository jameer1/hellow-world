package com.rjtech.projectlib.req;

import java.util.List;
import java.util.ArrayList;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSORItemTO;

public class ProjSORItemGetReq extends ProjectTO {

    private static final long serialVersionUID = -6671175298681215590L;
    private Long sorId;
    private String sorStatus;
    private Character isExternalApprovalRequired;
    private Long internalApprovalUserId;
    private Long externalApprovalUserId;
    private String comments;
    private List<Long> sorIds;
    private Integer displayActiveItems;
    private List<ProjSORItemTO> projSORItemTOs = new ArrayList<ProjSORItemTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private List<Long> viewSORIds;
    
    
    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
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
    
    public void setSorStatus( String sorStatus ) {
    	this.sorStatus = sorStatus;
    }
    
    public String getSorStatus() {
    	return sorStatus;
    }
    
    public void setComments( String comments ) {
    	this.comments = comments;
    }
    
    public String getComments() {
    	return comments;
    }
    
    public List<Long> getSorIds() {
        return sorIds;
    }

    public void setSorIds(List<Long> sorIds) {
        this.sorIds = sorIds;
    }
    
    public Integer getDisplayActiveItems() {
        return displayActiveItems;
    }

    public void setDisplayActiveItems( Integer displayActiveItems ) {
        this.displayActiveItems = displayActiveItems;
    }
    
    public void setProjSORItemTOs(List<ProjSORItemTO> projSORItemTOs) {
    	this.projSORItemTOs = projSORItemTOs;
    }
    
    public List<ProjSORItemTO> getProjSORItemTOs(){
    	return projSORItemTOs;
    }
    
    public void setViewSORIds(List<Long> viewSORIds) {
    	this.viewSORIds = viewSORIds;
    }
    
    public List<Long> getViewSORIds(){
    	return viewSORIds;
    }
    
    public String toString() {
    	return "sorStatus:"+sorStatus+" comments:"+comments;
    }
}
