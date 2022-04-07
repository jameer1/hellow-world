package com.rjtech.register.controller.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantProjRepairGetReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantRepairSaveReq;
import com.rjtech.register.plant.req.PlantRepairsResp;
import com.rjtech.register.service.plant.PlantRepairService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class PlantRepairsController {

    @Autowired
    private PlantRepairService plantRepairsService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_REPAIRS, method = RequestMethod.POST)
    public ResponseEntity<PlantRepairsResp> getPlantRepairs(@RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        return new ResponseEntity<PlantRepairsResp>(plantRepairsService.getPlantRepairs(plantProjectDtlGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_REPAIRS, method = RequestMethod.POST)
    public ResponseEntity<PlantRepairsResp> savePlantRepairs(@RequestBody PlantRepairSaveReq plantRepairsSaveReq) {

        plantRepairsService.savePlantRepairs(plantRepairsSaveReq);
        PlantProjectDtlGetReq plantProjectDtlGetReq = new PlantProjectDtlGetReq();
        plantProjectDtlGetReq.setPlantId(plantRepairsSaveReq.getPlantId());
        plantProjectDtlGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PlantRepairsResp plantRepairsResp = plantRepairsService.getPlantRepairs(plantProjectDtlGetReq);
        plantRepairsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(plantRepairsResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_MATERIAL_PROJ_DOCKET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPlantMaterialProjDocketDetails(
            @RequestBody PlantProjRepairGetReq plantProjRepairGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(
                plantRepairsService.getPlantMaterialProjDocketDetails(plantProjRepairGetReq), HttpStatus.OK);
    }
}
