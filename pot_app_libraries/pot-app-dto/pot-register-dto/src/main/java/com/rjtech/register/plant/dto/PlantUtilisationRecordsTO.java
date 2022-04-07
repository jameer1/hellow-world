package com.rjtech.register.plant.dto;

import com.rjtech.common.dto.ProjectTO;

public class PlantUtilisationRecordsTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -2854204876496487988L;
    private Long id;
    private Long plantDeployHistoryId;
    private Long plantRegDtlId;
    private Long workDairyCostItemId;
    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantDeployHistoryId() {
        return plantDeployHistoryId;
    }

    public void setPlantDeployHistoryId(Long plantDeployHistoryId) {
        this.plantDeployHistoryId = plantDeployHistoryId;
    }

    public Long getPlantRegDtlId() {
        return plantRegDtlId;
    }

    public void setPlantRegDtlId(Long plantRegDtlId) {
        this.plantRegDtlId = plantRegDtlId;
    }

    public Long getWorkDairyCostItemId() {
        return workDairyCostItemId;
    }

    public void setWorkDairyCostItemId(Long workDairyCostItemId) {
        this.workDairyCostItemId = workDairyCostItemId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
