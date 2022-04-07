package com.rjtech.mw.controller.register.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsService;
import com.rjtech.mw.service.register.MWPlantRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantDepriciationSalvageSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantDepriciationSalvageResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWPlantDepriciationSalvageController {

    @Autowired
    private MWPlantRegisterService mwPlantRegisterService;

    @Autowired
    private MWProjectSettingsService mwProjectSettingsService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_DEPRISIATION_SALVAGE, method = RequestMethod.POST)
    public ResponseEntity<PlantDepriciationSalvageResp> getPlantDepriciationSalvages(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantDepriciationSalvageResp resp = mwPlantRegisterService.getPlantDepriciationSalvages(plantProjectDtlGetReq);
        LabelKeyTO currencyLabelKeyTO = mwProjectSettingsService
                .getProjGeneralsCurrencys(plantProjectDtlGetReq.getProjId());
        resp.setProjGenCurrencyLabelKeyTO(currencyLabelKeyTO);
        return new ResponseEntity<PlantDepriciationSalvageResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_DEPRISIATION_SALVAGE, method = RequestMethod.POST)
    public ResponseEntity<PlantDepriciationSalvageResp> savePlantDepriciationSalvages(
            @RequestBody PlantDepriciationSalvageSaveReq plantDepriciationSalvageSaveReq) {
        PlantDepriciationSalvageResp resp = mwPlantRegisterService
                .savePlantDepriciationSalvages(plantDepriciationSalvageSaveReq);
        LabelKeyTO currencyLabelKeyTO = mwProjectSettingsService.getProjGeneralsCurrencys(
                plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO().getPlantRegProjTO().getProjId());
        resp.setProjGenCurrencyLabelKeyTO(currencyLabelKeyTO);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
