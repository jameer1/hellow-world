package com.rjtech.register.controller.material;

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
import com.rjtech.material.ledger.LedgerResTo;
import com.rjtech.material.ledger.MaterialLedgerResTo;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.controller.common.RegisterCommonController;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.resp.MaterialProjConsumptionResp;
import com.rjtech.register.service.material.MaterialProjConsumptionSevice;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MaterialProjConsumptionController extends RegisterCommonController {

    @Autowired
    private MaterialProjConsumptionSevice materialProjConsumptionSevice;

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_STORE_TRANSIT_CONSUMPTION, method = RequestMethod.POST)
    public ResponseEntity<LedgerResTo> getMaterialStoreTransitConsumption(
            @RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();

        if (materialFilterReq.getProjList().isEmpty()) {
            materialFilterReq.setProjList(getUserProjectsList());
        }
        if (!CommonUtil.isListHasData(materialFilterReq.getProjList())) {
            return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
        }
        
        List<MaterialLedgerResTo> inTransitDocketItems = materialProjConsumptionSevice.getMaterialStoreTransitConsumption(materialFilterReq);
        Map<String, MaterialLedgerResTo> docketsMap = new HashMap<String, MaterialLedgerResTo>();
        inTransitDocketItems.forEach((materialDocket) -> {
            docketsMap.put(materialDocket.getDocketId(), materialDocket);
        });
        
        ledgerResTo.setDocketLedgerMap(docketsMap);
        
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_STOCK_PILED_CONSUMPTION, method = RequestMethod.POST)
    public ResponseEntity<LedgerResTo> getMaterialStockPiledConsumption(
            @RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();

        if (materialFilterReq.getProjList().isEmpty()) {
            materialFilterReq.setProjList(getUserProjectsList());
        }
        if (!CommonUtil.isListHasData(materialFilterReq.getProjList())) {
            return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
        }
        List<MaterialLedgerResTo> stockPiledDocketItems = materialProjConsumptionSevice.getMaterialStockPiledConsumption(materialFilterReq);
        Map<String, MaterialLedgerResTo> docketsMap = new HashMap<String, MaterialLedgerResTo>();
        stockPiledDocketItems.forEach((materialDocket) -> {
            docketsMap.put(materialDocket.getDocketId(), materialDocket);
        });
        
        ledgerResTo.setDocketLedgerMap(docketsMap);
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_DAILY_ISSUE_SCH_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjConsumptionResp> getMaterialDailyIssueSchItems(
            @RequestBody MaterialFilterReq materialFilterReq) {
        MaterialProjConsumptionResp materialProjConsumptionResp = new MaterialProjConsumptionResp();

        if (materialFilterReq.getProjList().isEmpty()) {
            materialFilterReq.setProjList(getUserProjectsList());
        }
        if (!CommonUtil.isListHasData(materialFilterReq.getProjList())) {
            return new ResponseEntity<MaterialProjConsumptionResp>(materialProjConsumptionResp, HttpStatus.OK);
        }
        materialProjConsumptionResp.setLabelKeyTOs(
                materialProjConsumptionSevice.getMaterialDailyIssueSchItems(materialFilterReq).getLabelKeyTOs());
        return new ResponseEntity<MaterialProjConsumptionResp>(materialProjConsumptionResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_PROJ_LEDGERS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjConsumptionResp> getMaterialProjLedgers(
            @RequestBody MaterialFilterReq materialFilterReq) {
        MaterialProjConsumptionResp materialProjConsumptionResp = new MaterialProjConsumptionResp();

        if (materialFilterReq.getProjList().isEmpty()) {
            materialFilterReq.setProjList(getUserProjectsList());
        }
        if (!CommonUtil.isListHasData(materialFilterReq.getProjList())) {
            return new ResponseEntity<MaterialProjConsumptionResp>(materialProjConsumptionResp, HttpStatus.OK);
        }
        materialProjConsumptionResp.setLabelKeyTOs(
                materialProjConsumptionSevice.getMaterialProjLedgers(materialFilterReq).getLabelKeyTOs());
        return new ResponseEntity<MaterialProjConsumptionResp>(materialProjConsumptionResp, HttpStatus.OK);
    }

}
