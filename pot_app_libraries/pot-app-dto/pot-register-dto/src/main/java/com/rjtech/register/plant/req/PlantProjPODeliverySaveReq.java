package com.rjtech.register.plant.req;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.register.plant.dto.PlantProjPODtlTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlantProjPODeliverySaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PlantProjPODtlTO plantProjPODtlTO = new PlantProjPODtlTO();
    private Long plantId;
    private String folderCategory;
    private String mode;

    public PlantProjPODtlTO getPlantProjPODtlTO() {
        return plantProjPODtlTO;
    }

    public void setPlantProjPODtlTO(PlantProjPODtlTO plantProjPODtlTO) {
        this.plantProjPODtlTO = plantProjPODtlTO;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }
    
    public String getFolderCategory() {
        return folderCategory;
    }

    public void setFolderCategory(String folderCategory) {
        this.folderCategory = folderCategory;
    }
    
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    
    public String toString() {
    	return "folderCategory:"+folderCategory+" plantId:"+plantId+" mode:"+mode;
    }
}
