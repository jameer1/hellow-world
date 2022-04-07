package com.rjtech.mw.controller.register.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.register.MWPlantRegisterService;
import com.rjtech.projectlib.req.ProjectLibOnLoadReq;
import com.rjtech.projectlib.resp.ProjectLibOnLoadResp;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantDeploymentSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantDeploymentResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWPlantDeploymentController {

    @Autowired
    private MWPlantRegisterService mwPlantRegisterService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_DEPLOYMENT, method = RequestMethod.POST)
    public ResponseEntity<PlantDeploymentResp> savePlantDeployment(
            @RequestBody PlantDeploymentSaveReq plantDeploymentSaveReq) {
        PlantDeploymentResp plantProjDtlResp = mwPlantRegisterService.savePlantDeployment(plantDeploymentSaveReq);
        return new ResponseEntity<PlantDeploymentResp>(plantProjDtlResp, HttpStatus.OK);
    }

    private void setOnLoadMap(PlantDeploymentResp resp, Long projId) {
        ProjectLibOnLoadReq req = new ProjectLibOnLoadReq();
        req.setProjId(projId);
        ProjectLibOnLoadResp projLibResp = mwProjLibService.projectUserProjClassify(req);
        resp.getProjectLibTO().setUserProjMap(projLibResp.getUserProjMap());
        resp.getProjectLibTO().setProjClassMap(projLibResp.getProjClassMap());
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_DEPLOYMENT, method = RequestMethod.POST)
    public ResponseEntity<PlantDeploymentResp> getPlantDeploymentOnLoad(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantDeploymentResp plantProjDtlResp = mwPlantRegisterService.getPlantDeploymentOnLoad(plantProjectDtlGetReq);
        return new ResponseEntity<>(plantProjDtlResp, HttpStatus.OK);
    }

}
