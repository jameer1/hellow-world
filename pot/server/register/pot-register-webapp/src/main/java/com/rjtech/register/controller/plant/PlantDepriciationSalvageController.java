package com.rjtech.register.controller.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantDepriciationSalvageSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantDepriciationSalvageResp;
import com.rjtech.register.service.plant.PlantDepriciationSalvageService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class PlantDepriciationSalvageController {

    @Autowired
    private PlantDepriciationSalvageService plantDepriciationSalvageService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_DEPRISIATION_SALVAGE, method = RequestMethod.POST)
    public ResponseEntity<PlantDepriciationSalvageResp> getPlantDepriciationSalvages(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantDepriciationSalvageResp plantDepriciationSalvageResp = plantDepriciationSalvageService
                .getPlantDepriciationSalvages(plantProjectDtlGetReq);
        return new ResponseEntity<PlantDepriciationSalvageResp>(plantDepriciationSalvageResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_DEPRISIATION_SALVAGE, method = RequestMethod.POST)
    public ResponseEntity<PlantDepriciationSalvageResp> savePlantDepriciationSalvages(
            @RequestBody PlantDepriciationSalvageSaveReq plantDepriciationSalvageSaveReq) {
        plantDepriciationSalvageService.savePlantDepriciationSalvages(plantDepriciationSalvageSaveReq);
        PlantProjectDtlGetReq plantProjectDtlGetReq = new PlantProjectDtlGetReq();
        plantProjectDtlGetReq.setPlantId(
                plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO().getPlantRegProjTO().getPlantId());
        PlantDepriciationSalvageResp plantDepriciationSalvageResp = plantDepriciationSalvageService
                .getPlantDepriciationSalvages(plantProjectDtlGetReq);
        return new ResponseEntity<PlantDepriciationSalvageResp>(plantDepriciationSalvageResp, HttpStatus.OK);
    }
}
