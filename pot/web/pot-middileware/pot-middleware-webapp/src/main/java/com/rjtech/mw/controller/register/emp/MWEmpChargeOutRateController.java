package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsService;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.projsettings.dto.ProjGeneralMstrTO;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.projsettings.resp.ProjGeneralsResp;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpChargeOutRateSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpChargeOutRateResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpChargeOutRateController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @Autowired
    private MWProjectSettingsService mwProjectSettingsService;

    @PostMapping(value = RegisterURLConstants.GET_EMP_CHARGEOUT_RATES)
    public ResponseEntity<EmpChargeOutRateResp> getEmpChargeOutRates(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpChargeOutRateResp empChargeOutRatesResp = mwRegisterService.getEmpChargeOutRates(projEmpRegisterGetReq);
        populateCharoutRateMaps(projEmpRegisterGetReq, empChargeOutRatesResp);
        return new ResponseEntity<>(empChargeOutRatesResp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.SAVE_EMP_CHARGEOUT_RATES)
    public ResponseEntity<EmpChargeOutRateResp> saveEmpChargeOutRates(
            @RequestBody EmpChargeOutRateSaveReq empChargeOutRatesSaveReq) {
        return new ResponseEntity<>(mwRegisterService.saveEmpChargeOutRates(empChargeOutRatesSaveReq), HttpStatus.OK);
    }

    private void populateCharoutRateMaps(ProjEmpRegisterGetReq projEmpRegisterGetReq,
            EmpChargeOutRateResp empChargeOutRateResp) {
        ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        projGeneralsGetReq.setProjId(projEmpRegisterGetReq.getProjId());
        ProjGeneralsResp projGenCurrencyResp = mwProjectSettingsService.getProjGenerals(projGeneralsGetReq);

        LabelKeyTO labelKeyTO = new LabelKeyTO();
        ProjGeneralMstrTO projGeneralMstrTO = projGenCurrencyResp.getProjGeneralMstrTO();
        labelKeyTO.setId(projGeneralMstrTO.getId());
        labelKeyTO.setCode(projGeneralMstrTO.getContractNumber());
        if (CommonUtil.objectNotNull(projGeneralMstrTO.getIsoAlpha3())
                && CommonUtil.objectNotNull(projGeneralMstrTO.getCurrency())) {
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.CURRENCY, projGeneralMstrTO.getCurrency());
        }
        empChargeOutRateResp.setProjGeneralLabelTO(labelKeyTO);
    }
}
