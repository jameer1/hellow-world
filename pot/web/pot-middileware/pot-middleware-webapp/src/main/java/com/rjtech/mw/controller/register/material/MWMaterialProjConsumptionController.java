package com.rjtech.mw.controller.register.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.service.register.MWMaterialService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.resp.MaterialProjConsumptionResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWMaterialProjConsumptionController {

    @Autowired
    private MWMaterialService mwMaterialService;

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_CONSUMPTION, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjConsumptionResp> getMaterialProjConsumption(
            @RequestBody MaterialFilterReq materialFilterReq) {
        return new ResponseEntity<MaterialProjConsumptionResp>(
                mwMaterialService.getMaterialProjConsumption(materialFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_STORE_TRANSIT_CONSUMPTION, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjConsumptionResp> getMaterialStoreTransitConsumption(
            @RequestBody MaterialFilterReq materialFilterReq) {
        return new ResponseEntity<MaterialProjConsumptionResp>(
                mwMaterialService.getMaterialStoreTransitConsumption(materialFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_STOCK_PILED_CONSUMPTION, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjConsumptionResp> getMaterialStockPiledConsumption(
            @RequestBody MaterialFilterReq materialFilterReq) {
        return new ResponseEntity<MaterialProjConsumptionResp>(
                mwMaterialService.getMaterialStockPiledConsumption(materialFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_DAILY_ISSUE_SCH_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjConsumptionResp> getMaterialDailyIssueSchItems(
            @RequestBody MaterialFilterReq materialFilterReq) {
        return new ResponseEntity<MaterialProjConsumptionResp>(
                mwMaterialService.getMaterialDailyIssueSchItems(materialFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_PROJ_LEDGERS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjConsumptionResp> getMaterialProjLeadger(
            @RequestBody MaterialFilterReq materialFilterReq) {
        return new ResponseEntity<MaterialProjConsumptionResp>(
                mwMaterialService.getMaterialProjLeadger(materialFilterReq), HttpStatus.OK);
    }

}
