package com.rjtech.register.controller.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantDeploymentSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantDeploymentResp;
import com.rjtech.register.service.plant.PlantDeploymentService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class PlantRegDeployemntController {

    @Autowired
    private PlantDeploymentService regPlantDeploymentService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_DEPLOYMENT, method = RequestMethod.POST)
    public ResponseEntity<PlantDeploymentResp> getPlantDeploymentOnLoad(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantDeploymentResp deploymentOnLoadResp = regPlantDeploymentService
                .getPlantDeploymentOnLoad(plantProjectDtlGetReq);
        deploymentOnLoadResp.setDeMobStatus(regPlantDeploymentService.getPostDeMobStatus());
        return new ResponseEntity<PlantDeploymentResp>(deploymentOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_DEPLOYMENT, method = RequestMethod.POST)
    public ResponseEntity<PlantDeploymentResp> savePlantProjectDtls(
            @RequestBody PlantDeploymentSaveReq plantDeploymentSaveReq) {
        regPlantDeploymentService.savePlantDeployment(plantDeploymentSaveReq);
        PlantProjectDtlGetReq plantProjectDtlGetReq = new PlantProjectDtlGetReq();
        plantProjectDtlGetReq.setPlantId(plantDeploymentSaveReq.getPlantRegProjTO().getPlantId());
        plantProjectDtlGetReq.setStatus(plantDeploymentSaveReq.getStatus());
        PlantDeploymentResp resp = regPlantDeploymentService.getPlantDeploymentOnLoad(plantProjectDtlGetReq);
        resp.setDeMobStatus(regPlantDeploymentService.getPostDeMobStatus());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
