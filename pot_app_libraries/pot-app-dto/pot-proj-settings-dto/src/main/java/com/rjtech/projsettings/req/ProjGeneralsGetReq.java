package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class ProjGeneralsGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 108422042885642369L;

    private List<Long> projIds = new ArrayList<>();
    
    /* The following properties is written in general to retrieve the ids of manpower,plant,material and cost statements under the project budget module for the four tabs*/
    private List<Long> manpowerIds = new ArrayList<>();
    private List<Long> plantIds = new ArrayList<>();
    private List<Long> materialIds = new ArrayList<>();
    private List<Long> costStatementIds = new ArrayList<>();
    private List<Long> costItemIds = new ArrayList<>();
    private String itemStatus;
    private Long approverUserId;
    
    private Long manpowerId;
    private Long plantId;
    private Long materialId;
    private Long costStatementId;
    private Long costItemId;
    private String approvalMode;
    private String comments;
    /* The above properties is written in general to retrieve the ids of manpower,plant,material and cost statements under the project budget module for the four tabs and is not related to any table*/

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }
    
    public List<Long> getManpowerIds() {
        return manpowerIds;
    }

    public void setManpowerIds( List<Long> manpowerIds ) {
        this.manpowerIds = manpowerIds;
    }
    
    public List<Long> getPlantIds() {
        return plantIds;
    }

    public void setPlantIds( List<Long> plantIds ) {
        this.plantIds = plantIds;
    }
    
    public List<Long> getMaterialIds() {
        return materialIds;
    }

    public void setMaterialIds( List<Long> materialIds ) {
        this.materialIds = materialIds;
    }
    
    public List<Long> getCostStatementIds() {
        return costStatementIds;
    }

    public void setCostStatementIds( List<Long> costStatementIds ) {
        this.costStatementIds = costStatementIds;
    }
    
    public List<Long> getCostItemIds() {
        return costItemIds;
    }

    public void setCostItemIds( List<Long> costItemIds ) {
        this.costItemIds = costItemIds;
    }
    
    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus( String itemStatus ) {
        this.itemStatus = itemStatus;
    }
    
    public Long getApproverUserId() {
        return approverUserId;
    }

    public void setApproverUserId( Long approverUserId ) {
        this.approverUserId = approverUserId;
    }
    
    public Long getManpowerId() {
        return manpowerId;
    }

    public void setManpowerId( Long manpowerId ) {
        this.manpowerId = manpowerId;
    }
    
    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId( Long plantId ) {
        this.plantId = plantId;
    }
    
    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId( Long materialId ) {
        this.materialId = materialId;
    }
    
    public Long getCostStatementId() {
        return costStatementId;
    }

    public void setCostStatementId( Long costStatementId ) {
        this.costStatementId = costStatementId;
    }
    
    public Long getCostItemId() {
        return costItemId;
    }

    public void setCostItemId( Long costItemId ) {
        this.costItemId = costItemId;
    }
    
    public String getApprovalMode() {
        return approvalMode;
    }

    public void setApprovalMode( String approvalMode ) {
        this.approvalMode = approvalMode;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments( String comments ) {
        this.comments = comments;
    }
}
