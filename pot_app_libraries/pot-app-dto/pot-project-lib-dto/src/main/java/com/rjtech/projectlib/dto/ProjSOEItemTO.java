package com.rjtech.projectlib.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.ProjectTO;

public class ProjSOEItemTO extends ProjectTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private Long id;
    private String code;
    private String name;
    private boolean item;
    private boolean itemParent = false;
    private boolean expand = false;
    private Long parentId;
    private String parentName;
    private BigDecimal quantity;
    private String comments;
    private Long measureId;
    private MeasureUnitTO measureUnitTO;
    private Long manHours;
    private String soeStatus;
    private Date submittedDate;
    private String internalApprovalStatus;
    private String externalApprovalStatus;
    private Long internalApprovalUserId;
    private Long externalApprovalUserId;
    private Long originatorUserId;
    private Character isExternalApprovalRequired;
    private String internalApproverComments;
    private String externalApproverComments;
    private Integer isItemReturned;
    private Integer internalApproverUserRoleId;
    private Integer externalApproverUserRoleId;
    private Integer originatorUserRoleId;
    public String soeItemStatus;
    public Long notifyId;
    private Date addlDate;
    //private Boolean allowEdit;

    private List<ProjSOEItemTO> childSOEItemTOs = new ArrayList<ProjSOEItemTO>();
    
    public Date getAddlDate() {
        return addlDate;
    }

    public void setAddlDate(Date addlDate) {
        this.addlDate = addlDate;
    }
    
    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public boolean isItemParent() {
        return itemParent;
    }

    public void setItemParent(boolean itemParent) {
        this.itemParent = itemParent;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public MeasureUnitTO getMeasureUnitTO() {
        return measureUnitTO;
    }

    public void setMeasureUnitTO(MeasureUnitTO measureUnitTO) {
        this.measureUnitTO = measureUnitTO;
    }

    public List<ProjSOEItemTO> getChildSOEItemTOs() {
        return childSOEItemTOs;
    }

    public void setChildSOEItemTOs(List<ProjSOEItemTO> childSOEItemTOs) {
        this.childSOEItemTOs = childSOEItemTOs;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjSOEItemTO other = (ProjSOEItemTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

	public Long getManHours() {
		return manHours;
	}

	public void setManHours(Long manHours) {
		this.manHours = manHours;
	}

	public void setInternalApprovalStatus( String internalApprovalStatus ) {
    	this.internalApprovalStatus = internalApprovalStatus;
    }
    
    public String getInternalApprovalStatus() {
    	return internalApprovalStatus;
    }
    
    public void setExternalApprovalStatus( String externalApprovalStatus ) {
    	this.externalApprovalStatus = externalApprovalStatus;
    }
    
    public String getExternalApprovalStatus() {
    	return externalApprovalStatus;
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
    
    public Long getOriginatorUserId() {
        return originatorUserId;
    }

    public void setOriginatorUserId( Long originatorUserId ) {
        this.originatorUserId = originatorUserId;
    }
    
    public String getSoeStatus() {
        return soeStatus;
    }

    public void setSoeStatus( String soeStatus ) {
        this.soeStatus = soeStatus;
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
    
    public Integer getIsItemReturned() {
        return isItemReturned;
    }

    public void setIsItemReturned( Integer isItemReturned ) {
        this.isItemReturned = isItemReturned;
    }
    
    public Integer getInternalApproverUserRoleId() {
        return internalApproverUserRoleId;
    }

    public void setInternalApproverUserRoleId( Integer internalApproverUserRoleId ) {
        this.internalApproverUserRoleId = internalApproverUserRoleId;
    }
    
    public Integer getExternalApproverUserRoleId() {
        return externalApproverUserRoleId;
    }

    public void setExternalApproverUserRoleId( Integer externalApproverUserRoleId ) {
        this.externalApproverUserRoleId = externalApproverUserRoleId;
    }
    
    public Integer getOriginatorUserRoleId() {
        return originatorUserRoleId;
    }

    public void setOriginatorUserRoleId( Integer originatorUserRoleId ) {
        this.originatorUserRoleId = originatorUserRoleId;
    }
    
    public String getSoeItemStatus() {
    	return soeItemStatus;
    }
    
    public void setSoeItemStatus( String soeItemStatus ) {
    	this.soeItemStatus = soeItemStatus;
    }
    	
	public Long getNotifyId() { 
		  return notifyId; 		  
	}
	  
	public void setNotifyId(Long notifyId) { 
		  this.notifyId = notifyId; 
	}
	    
    public String toString() {
    	return " id:"+id+"manHours:"+manHours+" soeStatus:"+soeStatus+" code:"+code+" itemStatus:"+soeItemStatus;
    }
}
