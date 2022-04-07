package com.rjtech.projectlib.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.ProjectTO;

public class ProjSORItemTO extends ProjectTO {
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
    private Long manPowerHrs;
    private BigDecimal total;
    private BigDecimal amount;
    private BigDecimal labourRate;
    private BigDecimal plantRate;
    private BigDecimal materialRate;
    private BigDecimal othersRate;
    private BigDecimal totalRateIfNoSubCategory;
    private String sorStatus;
    private String internalApprovalStatus;
    private String externalApprovalStatus;
    private Long internalApprovalUserId;
    private Long externalApprovalUserId;
    private Long originatorUserId;
    private Character isExternalApprovalRequired;
    private String internalApproverComments;
    private String externalApproverComments;
    private Boolean isUserOriginator;
    private Boolean isUserInternalApprover;
    private Boolean isUserExternalApprover;  
    private Integer isItemReturned;
    private String itemStatus;

    private String comments;
    private Long measureId;
    private MeasureUnitTO measureUnitTO;

    private List<ProjSORItemTO> childSORItemTOs = new ArrayList<ProjSORItemTO>();

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

    public Long getManPowerHrs() {
        return manPowerHrs;
    }

    public void setManPowerHrs(Long manPowerHrs) {
        this.manPowerHrs = manPowerHrs;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getLabourRate() {
        return labourRate;
    }

    public void setLabourRate(BigDecimal labourRate) {
        this.labourRate = labourRate;
    }

    public BigDecimal getPlantRate() {
        return plantRate;
    }

    public void setPlantRate(BigDecimal plantRate) {
        this.plantRate = plantRate;
    }

    public BigDecimal getMaterialRate() {
        return materialRate;
    }

    public void setMaterialRate(BigDecimal materialRate) {
        this.materialRate = materialRate;
    }

    public BigDecimal getOthersRate() {
        return othersRate;
    }

    public void setOthersRate(BigDecimal othersRate) {
        this.othersRate = othersRate;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public MeasureUnitTO getMeasureUnitTO() {
        return measureUnitTO;
    }

    public void setMeasureUnitTO(MeasureUnitTO measureUnitTO) {
        this.measureUnitTO = measureUnitTO;
    }

    public List<ProjSORItemTO> getChildSORItemTOs() {
        return childSORItemTOs;
    }

    public void setChildSORItemTOs(List<ProjSORItemTO> childSORItemTOs) {
        this.childSORItemTOs = childSORItemTOs;
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
        ProjSORItemTO other = (ProjSORItemTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

	public BigDecimal getTotalRateIfNoSubCategory() {
		return totalRateIfNoSubCategory;
	}

	public void setTotalRateIfNoSubCategory(BigDecimal totalRateIfNoSubCategory) {
		this.totalRateIfNoSubCategory = totalRateIfNoSubCategory;
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
    
    public String getSorStatus() {
        return sorStatus;
    }

    public void setSorStatus( String sorStatus ) {
        this.sorStatus = sorStatus;
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
    
    public Boolean getIsUserOriginator() {
        return isUserOriginator;
    }

    public void setIsUserOriginator( Boolean isUserOriginator ) {
        this.isUserOriginator = isUserOriginator;
    }
    
    public Boolean getIsUserInternalApprover() {
        return isUserInternalApprover;
    }

    public void setIsUserInternalApprover( Boolean isUserInternalApprover ) {
        this.isUserInternalApprover = isUserInternalApprover;
    }
    
    public Boolean getIsUserExternalApprover() {
        return isUserExternalApprover;
    }

    public void setIsUserExternalApprover( Boolean isUserExternalApprover ) {
        this.isUserExternalApprover = isUserExternalApprover;
    }
    
    public Integer getIsItemReturned() {
        return isItemReturned;
    }

    public void setIsItemReturned( Integer isItemReturned ) {
        this.isItemReturned = isItemReturned;
    }
    
    public void setItemStatus( String itemStatus ) {
    	this.itemStatus = itemStatus;
    }
    
    public String getItemStatus() {
    	return itemStatus;
    }
    
    public String toString() {
    	return "sorStatus:"+sorStatus+" code:"+code+" id:"+id+" isUserInternalApprover:"+isUserInternalApprover+" isUserInternalApprover:"+isUserInternalApprover+" isUserExternalApprover:"+isUserExternalApprover;
    }
}
