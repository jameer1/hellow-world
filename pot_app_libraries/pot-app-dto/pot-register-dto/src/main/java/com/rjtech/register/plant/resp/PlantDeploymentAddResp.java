package com.rjtech.register.plant.resp;

import java.io.Serializable;

import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantDeploymentAddResp implements Serializable {

    private static final long serialVersionUID = 1L;
    private PlantRegProjTO plantProjectPOTO = new PlantRegProjTO();

    public PlantDeploymentAddResp() {

    }

    public PlantRegProjTO getPlantProjectPOTO() {
        return plantProjectPOTO;
    }

    public void setPlantProjectPOTO(PlantRegProjTO plantProjectPOTO) {
        this.plantProjectPOTO = plantProjectPOTO;
    }

}
