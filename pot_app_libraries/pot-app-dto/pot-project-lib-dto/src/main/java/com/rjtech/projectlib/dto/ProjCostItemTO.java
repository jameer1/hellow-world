package com.rjtech.projectlib.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.CostCodeTO;
import com.rjtech.common.dto.ProjectTO;

public class ProjCostItemTO extends ProjectTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private Long id;
    private String code;
    private String name;
    private boolean item;
    private boolean itemParent = false;
    private boolean expand = false;
    private Long parentId;
    private String parentName;
    private String startDate;
    private String finishDate;
    private boolean workdairyEntry;
    private String comments;
    private Long costId;
    private Boolean select;
    private CostCodeTO costCodeTO;
    private boolean usedInOtherModule;
    
    private Long approverUserId;
    private String approverComments;
    private String costCodeItemStatus;
    private Long originatorUserId;
    private Long returnedByUserId;
    private String returnComments;
    private Integer isItemReturned;
    private Long originatorUserRoleId;

    private List<ProjCostItemTO> projCostCodeItemTOs = new ArrayList<ProjCostItemTO>();
    
    
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isWorkdairyEntry() {
        return workdairyEntry;
    }

    public void setWorkdairyEntry(boolean workdairyEntry) {
        this.workdairyEntry = workdairyEntry;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public CostCodeTO getCostCodeTO() {
        return costCodeTO;
    }

    public void setCostCodeTO(CostCodeTO costCodeTO) {
        this.costCodeTO = costCodeTO;
    }

    public List<ProjCostItemTO> getProjCostCodeItemTOs() {
        return projCostCodeItemTOs;
    }

    public void setProjCostCodeItemTOs(List<ProjCostItemTO> projCostCodeItemTOs) {
        this.projCostCodeItemTOs = projCostCodeItemTOs;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    public boolean isUsedInOtherModule() {
        return usedInOtherModule;
    }

    public void setUsedInOtherModule(boolean usedInOtherModule) {
        this.usedInOtherModule = usedInOtherModule;
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
        ProjCostItemTO other = (ProjCostItemTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Long getApproverUserId() {
        return approverUserId;
    }

    public void setApproverUserId( Long approverUserId ) {
        this.approverUserId = approverUserId;
    }
    
    public String getApproverComments() {
        return approverComments;
    }

    public void setApproverComments(String approverComments) {
        this.approverComments = approverComments;
    }
    
    public String getCostCodeItemStatus() {
        return costCodeItemStatus;
    }

    public void setCostCodeItemStatus( String costCodeItemStatus ) {
        this.costCodeItemStatus = costCodeItemStatus;
    }
    
    public Long getOriginatorUserId() {
        return originatorUserId;
    }

    public void setOriginatorUserId( Long originatorUserId ) {
        this.originatorUserId = originatorUserId;
    }
    
    public Long getReturnedByUserId() {
        return returnedByUserId;
    }

    public void setReturnedByUserId( Long returnedByUserId ) {
        this.returnedByUserId = returnedByUserId;
    }
    
    public String getReturnComments() {
        return returnComments;
    }

    public void setReturnComments( String returnComments ) {
        this.returnComments = returnComments;
    }

    public Integer getIsItemReturned() {
        return isItemReturned;
    }

    public void setIsItemReturned( Integer isItemReturned ) {
        this.isItemReturned = isItemReturned;
    }
    
    public void setOriginatorUserRoleId( Long originatorUserRoleId ) {
    	this.originatorUserRoleId = originatorUserRoleId;
    }
    
    public Long getOriginatorUserRoleId() {
    	return originatorUserRoleId;
    }
}
