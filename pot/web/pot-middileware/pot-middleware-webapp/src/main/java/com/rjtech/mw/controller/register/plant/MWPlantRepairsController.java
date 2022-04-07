package com.rjtech.mw.controller.register.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.req.RegisterOnLoadReq;
import com.rjtech.centrallib.resp.CentLibPlantRepairsOnLoadResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.register.MWPlantRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantProjRepairGetReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantRepairSaveReq;
import com.rjtech.register.plant.req.PlantRepairsResp;
import com.rjtech.register.plant.resp.PlantRepairOnLoadResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWPlantRepairsController {

    @Autowired
    private MWPlantRegisterService mwPlantRegisterService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_REPAIRS, method = RequestMethod.POST)
    public ResponseEntity<PlantRepairOnLoadResp> savePlantRepairs(@RequestBody PlantRepairSaveReq plantRepairsSaveReq) {

        PlantRepairOnLoadResp plantRepairsOnLoadResp = new PlantRepairOnLoadResp();
        PlantRepairsResp plantRepairsResp = mwPlantRegisterService.savePlantRepairs(plantRepairsSaveReq);
        plantRepairsOnLoadResp.setPlantRepairsTOs(plantRepairsResp.getPlantRepairsTOs());
        plantRepairsOnLoadResp.cloneAppResp(CommonUtil.getSaveAppResp());
        getOnLoadMap(plantRepairsOnLoadResp);
        return new ResponseEntity<PlantRepairOnLoadResp>(plantRepairsOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.PLANT_REPAIRS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PlantRepairOnLoadResp> plantRepairsOnLoad(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantRepairOnLoadResp plantRepairsOnLoadResp = new PlantRepairOnLoadResp();

        PlantRepairsResp plantRepairsResp = mwPlantRegisterService.getPlantRepairs(plantProjectDtlGetReq);
        plantRepairsOnLoadResp.setPlantRepairsTOs(plantRepairsResp.getPlantRepairsTOs());
        plantRepairsOnLoadResp.setMessage(plantRepairsResp.getMessage());
        plantRepairsOnLoadResp.setStatus(plantRepairsResp.getStatus());

        return new ResponseEntity<PlantRepairOnLoadResp>(plantRepairsOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_MATERIAL_PROJ_DOCKET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPlantMaterialProjDocketDetails(
            @RequestBody PlantProjRepairGetReq plantProjRepairGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(
                mwPlantRegisterService.getPlantMaterialProjDocketDetails(plantProjRepairGetReq), HttpStatus.OK);
    }

    private void getOnLoadMap(PlantRepairOnLoadResp plantRepairsOnLoadResp) {
        RegisterOnLoadReq registerOnLoadReq = new RegisterOnLoadReq();
        CentLibPlantRepairsOnLoadResp resp = mwCentralLiblService.getPlantServiceMaterialClassMap(registerOnLoadReq);
        plantRepairsOnLoadResp.setPlantServiceClassMap(resp.getPlantServiceMap());
        plantRepairsOnLoadResp.setMaterialMap(resp.getClassificationMap());
    }
}
