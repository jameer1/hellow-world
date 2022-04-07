package com.rjtech.register.controller.plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.dto.PlantChargeOutRatesTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.req.PlantChargeOutRatesSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantChargeOutRatesResp;
import com.rjtech.register.service.plant.PlantChargeOutRatesService;
import com.rjtech.register.service.plant.PlantDeploymentService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class PlantChargeOutRatesController {

    @Autowired
    private PlantChargeOutRatesService plantChargeOutRatesService;

    @Autowired
    private PlantDeploymentService plantDeploymentService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_CHARGEOUT_RATES, method = RequestMethod.POST)
    public ResponseEntity<PlantChargeOutRatesResp> getPlantChargeOutRates(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantChargeOutRatesResp plantChargeOutRatesResp = plantChargeOutRatesService
                .getPlantChargeOutRates(plantProjectDtlGetReq);
        plantChargeOutRatesResp.setCategory(plantChargeOutRatesService.getPlantChargeOutCatg());
        PlantRegProjTO plantRegProjTO = plantDeploymentService
                .getLatestPlantDeployment(plantProjectDtlGetReq.getPlantId());
        Map<Long, Boolean> exitingDeploymentMap = new HashMap<>();
        if (CommonUtil.isListHasData(plantChargeOutRatesResp.getPlantChargeOutRatesTOs())) {
            for (PlantChargeOutRatesTO chargeOutRatesTO : plantChargeOutRatesResp.getPlantChargeOutRatesTOs()) {
                exitingDeploymentMap.put(chargeOutRatesTO.getPlantRegProjTO().getId(), true);
            }
        }
        if (!exitingDeploymentMap.containsKey(plantRegProjTO.getId())
                || !CommonUtil.isListHasData(plantChargeOutRatesResp.getPlantChargeOutRatesTOs())) {
            PlantChargeOutRatesTO plantChargeOutRatesTO = new PlantChargeOutRatesTO();
            plantChargeOutRatesTO.setEffectiveFrom(plantRegProjTO.getMobDate());
            plantChargeOutRatesTO.setLatest(true);
            plantChargeOutRatesTO.setPlantRegProjTO(plantRegProjTO);
            List<PlantChargeOutRatesTO> plantChargeOutRatesTOs = new ArrayList<>();
            plantChargeOutRatesTOs.add(plantChargeOutRatesTO);
            plantChargeOutRatesTOs.addAll(plantChargeOutRatesResp.getPlantChargeOutRatesTOs());
            plantChargeOutRatesResp.setPlantChargeOutRatesTOs(plantChargeOutRatesTOs);
        }
        return new ResponseEntity<>(plantChargeOutRatesResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_CHARGEOUT_RATES, method = RequestMethod.POST)
    public ResponseEntity<PlantChargeOutRatesResp> savePlantChargeOutRates(
            @RequestBody PlantChargeOutRatesSaveReq plantChargeOutRatesSaveReq) {
        plantChargeOutRatesService.savePlantChargeOutRates(plantChargeOutRatesSaveReq);

        PlantProjectDtlGetReq plantProjectDtlGetReq = new PlantProjectDtlGetReq();
        plantProjectDtlGetReq.setPlantId(plantChargeOutRatesSaveReq.getPlantRegProjTO().getPlantId());
        plantProjectDtlGetReq.setProjId(plantChargeOutRatesSaveReq.getPlantRegProjTO().getProjId());

        PlantChargeOutRatesResp resp = plantChargeOutRatesService.getPlantChargeOutRates(plantProjectDtlGetReq);
        resp.setCategory(plantChargeOutRatesService.getPlantChargeOutCatg());
        return new ResponseEntity<PlantChargeOutRatesResp>(resp, HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_PAYABLE_RATES, method = RequestMethod.POST)
    public ResponseEntity<PlantChargeOutRatesResp> savePlantPayableRates(
            @RequestBody PlantChargeOutRatesSaveReq plantChargeOutRatesSaveReq) {
        plantChargeOutRatesService.savePlantChargeOutRates(plantChargeOutRatesSaveReq);

        PlantProjectDtlGetReq plantProjectDtlGetReq = new PlantProjectDtlGetReq();
        plantProjectDtlGetReq.setPlantId(plantChargeOutRatesSaveReq.getPlantRegProjTO().getPlantId());
        plantProjectDtlGetReq.setProjId(plantChargeOutRatesSaveReq.getPlantRegProjTO().getProjId());

        PlantChargeOutRatesResp resp = plantChargeOutRatesService.getPlantChargeOutRates(plantProjectDtlGetReq);
        resp.setCategory(plantChargeOutRatesService.getPlantChargeOutCatg());
        return new ResponseEntity<PlantChargeOutRatesResp>(resp, HttpStatus.OK);
    }
    
    //SAVE_PLANT_PAYABLE_RATES

}
