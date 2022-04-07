package com.rjtech.register.service.plant;

import java.util.List;

import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.req.PlantDeploymentSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantDeploymentResp;

public interface PlantDeploymentService {

    void savePlantDeployment(PlantDeploymentSaveReq plantDeploymentSaveReq);

    PlantDeploymentResp getPlantDeploymentOnLoad(PlantProjectDtlGetReq plantProjectDtlGetReq);

    List<String> getPostDeMobStatus();

    PlantRegProjTO getLatestPlantDeployment(Long plantId);

}
