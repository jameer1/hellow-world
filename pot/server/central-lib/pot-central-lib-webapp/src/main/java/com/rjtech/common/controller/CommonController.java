package com.rjtech.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.req.CountryFilterReq;
import com.rjtech.common.req.ProjUsersReq;
import com.rjtech.common.req.ProvisionSaveReq;
import com.rjtech.common.req.ResourceCurveGetReq;
import com.rjtech.common.req.ResourceCurveSaveReq;
import com.rjtech.common.req.ResourceCurvesDeactivateReq;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.resp.ProvisionResp;
import com.rjtech.common.resp.ResourceCurveResp;
import com.rjtech.common.service.CommonService;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;

@RestController
@RequestMapping(CommonConstants.PARH_URL)
public class CommonController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = CommonConstants.SAVE_COUNTRY_PROVISIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveCountryProvisions(@RequestBody ProvisionSaveReq provisionSaveReq) {
        commonService.saveCountryProvisions(provisionSaveReq);

        CountryFilterReq countryFilterReq = new CountryFilterReq();
        ProvisionResp provisionResp = commonService.getAllCountryProvisions(countryFilterReq);
        return new ResponseEntity<AppResp>(provisionResp, HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.GET_COUNTRY_PROVISIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> getCountryProvisions(@RequestBody CountryFilterReq countryFilterReq) {
        ProvisionResp provisionResp = commonService.getAllCountryProvisions(countryFilterReq);
        return new ResponseEntity<AppResp>(provisionResp, HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.GET_RESOURCECURVES, method = RequestMethod.POST)
    public ResponseEntity<ResourceCurveResp> getResourceCurves(@RequestBody ResourceCurveGetReq resourceCurveGetReq) {
        return new ResponseEntity<ResourceCurveResp>(commonService.getResourceCurves(resourceCurveGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.SAVE_RESOURCECURVES, method = RequestMethod.POST)
    public ResponseEntity<ResourceCurveResp> SaveResourceCurves(
            @RequestBody ResourceCurveSaveReq resourceCurveSaveReq) {
        commonService.saveResourceCurves(resourceCurveSaveReq);
        ResourceCurveGetReq resourceCurveGetReq = new ResourceCurveGetReq();
        resourceCurveGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ResourceCurveResp resourceCurveResp = commonService.getResourceCurves(resourceCurveGetReq);
        resourceCurveResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ResourceCurveResp>(resourceCurveResp, HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.DEACTIVATE_RESOURCECURVES, method = RequestMethod.POST)
    public ResponseEntity<ResourceCurveResp> resourceCurvesDeactivate(
            @RequestBody ResourceCurvesDeactivateReq resourceCurvesDeactivateReq) {
        commonService.resourceCurvesDeactivate(resourceCurvesDeactivateReq);
        ResourceCurveGetReq resourceCurveGetReq = new ResourceCurveGetReq();
        resourceCurveGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ResourceCurveResp resourceCurveResp = commonService.getResourceCurves(resourceCurveGetReq);
        resourceCurveResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ResourceCurveResp>(resourceCurveResp, HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.GET_EMP_USERS_ONLY, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getEmpUsersOnly(@RequestBody ProjUsersReq projUsersReq) {
        return new ResponseEntity<LabelKeyTOResp>(commonService.getEmpUsersOnly(projUsersReq), HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.GET_PROJ_USERS_ONLY, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjUsersOnly(@RequestBody ProjUsersReq projUsersReq) {
        return new ResponseEntity<LabelKeyTOResp>(commonService.getProjUsersOnly(projUsersReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = CommonConstants.GET_ALL_PROJ_USERS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getAllProjUsers(@RequestBody ProjUsersReq projUsersReq) {
        return new ResponseEntity<LabelKeyTOResp>(commonService.getAllProjUsers(projUsersReq), HttpStatus.OK);
    }

}
