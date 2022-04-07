package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjEstimateTO;

public class ProjBudgetResp extends AppResp {
    
    private static final long serialVersionUID = -187824831647801020L;
    private List<Long> manpowerIds = new ArrayList<>();
    private List<Long> plantIds = new ArrayList<>();
    private List<Long> materialIds = new ArrayList<>();
    private List<Long> costStatementIds = new ArrayList<>();
    private List<Long> costItemIds = new ArrayList<>();
    
    private Long manpowerId;
    private Long plantId;
    private Long materialId;
    private Long costStatementId;
    
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
}
