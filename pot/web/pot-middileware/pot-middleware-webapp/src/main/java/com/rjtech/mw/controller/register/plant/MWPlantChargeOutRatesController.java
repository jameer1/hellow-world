package com.rjtech.mw.controller.register.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsService;
import com.rjtech.mw.service.register.MWPlantRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantChargeOutRatesSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantChargeOutRatesResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWPlantChargeOutRatesController {

    @Autowired
    private MWPlantRegisterService mwPlantRegisterService;

    @Autowired
    private MWProjectSettingsService mwProjectSettingsService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_CHARGEOUT_RATES, method = RequestMethod.POST)
    public ResponseEntity<PlantChargeOutRatesResp> getPlantChargeOutRates(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantChargeOutRatesResp resp = null;
        resp = mwPlantRegisterService.getPlantChargeOutRates(plantProjectDtlGetReq);
        LabelKeyTO currencyLabelKeyTO = mwProjectSettingsService
                .getProjGeneralsCurrencys(plantProjectDtlGetReq.getProjId());
        resp.setProjGeneralLabelKeyTO(currencyLabelKeyTO);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_CHARGEOUT_RATES, method = RequestMethod.POST)
    public ResponseEntity<String> savePlantChargeOutRates(
            @RequestBody PlantChargeOutRatesSaveReq plantChargeOutRatesSaveReq) {
        PlantChargeOutRatesResp resp = null;
        resp = mwPlantRegisterService.savePlantChargeOutRates(plantChargeOutRatesSaveReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        LabelKeyTO currencyLabelKeyTO = mwProjectSettingsService
                .getProjGeneralsCurrencys(plantChargeOutRatesSaveReq.getPlantRegProjTO().getProjId());
        resp.setProjGeneralLabelKeyTO(currencyLabelKeyTO);
        return new ResponseEntity<>(AppUtils.toJson(resp), HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_PAYABLE_RATES, method = RequestMethod.POST)
    public ResponseEntity<String> savePlantPayableRates(
            @RequestBody PlantChargeOutRatesSaveReq plantChargeOutRatesSaveReq) {
        PlantChargeOutRatesResp resp = null;
        resp = mwPlantRegisterService.savePlantPayableRates(plantChargeOutRatesSaveReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        LabelKeyTO currencyLabelKeyTO = mwProjectSettingsService
                .getProjGeneralsCurrencys(plantChargeOutRatesSaveReq.getPlantRegProjTO().getProjId());
        resp.setProjGeneralLabelKeyTO(currencyLabelKeyTO);
        return new ResponseEntity<>(AppUtils.toJson(resp), HttpStatus.OK);
    }

}
