package com.rjtech.mw.controller.register.material;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.register.MWMaterialService;
import com.rjtech.projectlib.req.ProMaterialClassGetReq;
import com.rjtech.projectlib.resp.ProjRestrictedMaterialClassResp;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialFilterResp;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.register.material.req.MaterialProjSaveReq;
import com.rjtech.register.material.req.MaterialSchItemsReq;
import com.rjtech.register.material.resp.MaterialDeliveryDocketResp;
import com.rjtech.register.material.resp.MaterialProjResp;
import com.rjtech.register.material.resp.MaterialSchItemsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWMaterialProjController {

    @Autowired
    private MWMaterialService mwMaterialService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_SCH_HOME_SEARCH, method = RequestMethod.POST)
    public ResponseEntity<MaterialFilterResp> materialHomedefaultSearch(@RequestBody MaterialGetReq materialGetReq) {
        MaterialFilterResp resp = null;
        resp = new MaterialFilterResp();
        Date fromDate;
        Date toDate;
        toDate = new Date();
        fromDate = CommonUtil.substarctInputMonths(toDate, -1);
        resp.setInputFrom(CommonUtil.convertDateToString(fromDate));
        resp.setInputTO(CommonUtil.convertDateToString(toDate));
        return new ResponseEntity<MaterialFilterResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_SCH_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjResp> getProjMaterialSchItems(@RequestBody MaterialGetReq materialGetReq) {
        MaterialProjResp resp = null;
        resp = mwMaterialService.getProjMaterialSchItems(materialGetReq);
        return new ResponseEntity<MaterialProjResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_SCH_ITEMS_BY_PURCHASE_ORDER, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjResp> getProjMaterialSchItemsByPurchaseOrder(
            @RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<MaterialProjResp>(
                mwMaterialService.getMaterialSchItemsByPurchaseOrder(materialGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PROJ_MATERIAL_SCH_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjResp> saveProjMaterialSchItems(
            @RequestBody MaterialProjSaveReq materialProjectSaveReq) {
        return new ResponseEntity<MaterialProjResp>(mwMaterialService.saveProjMaterialSchItems(materialProjectSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PROJ_MATERIAL_SCH_DOCKET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<MaterialDeliveryDocketResp> saveProjMaterialSchDocketDetails(MultipartFile[] files,
            String materialReq) {
    	return new ResponseEntity<MaterialDeliveryDocketResp>(mwMaterialService.saveProjMaterialSchDocketDetails(files, materialReq),
                HttpStatus.OK);
        	
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_SCH_ITEMS_SEARCH, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjResp> getProjMaterialSch(@RequestBody MaterialFilterReq materialGetReq) {
        MaterialProjResp resp = null;
        resp = mwMaterialService.getProjMaterialSch(materialGetReq);
        return new ResponseEntity<MaterialProjResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_DELIVERY_DOCKET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getMaterialDeliveryDocketDetails(@RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwMaterialService.getMaterialDeliveryDocketDetails(materialGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_DELIVERY_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<MaterialDeliveryDocketResp> getMaterialDeliveryDockets(
            @RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<MaterialDeliveryDocketResp>(
                mwMaterialService.getMaterialDeliveryDockets(materialGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_SCH_ITEM_DELIVERY_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<MaterialDeliveryDocketResp> getMaterialSchItemDeliveryDockets(
            @RequestBody MaterialFilterReq materialFilterReq) {
        return new ResponseEntity<MaterialDeliveryDocketResp>(
                mwMaterialService.getMaterialSchItemDeliveryDockets(materialFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_SCH_ITEMS_BY_PROJECT_AND_LOC, method = RequestMethod.POST)
    public ResponseEntity<MaterialSchItemsResp> getMaterialSchItemsByProjectAndLoc(
            @RequestBody MaterialSchItemsReq materialSchItemsReq) {
        MaterialSchItemsResp resp = null;
        ProMaterialClassGetReq projRestrictMaterialReq = new ProMaterialClassGetReq();
        projRestrictMaterialReq.setProjId(materialSchItemsReq.getReqProjLabelKeyTO().getId());
        projRestrictMaterialReq.setTransferType(materialSchItemsReq.getMaterialDockReqType());
        ProjRestrictedMaterialClassResp projRestrictMaterialResp = mwProjLibService
                .getProjRestrictedMaterialClasses(projRestrictMaterialReq);
        materialSchItemsReq.setMaterialClassList(projRestrictMaterialResp.getRestrictedProjMaterail());
        if (!(materialSchItemsReq.getMaterialClassList().size() > 0)) {
            materialSchItemsReq.getMaterialClassList().add(new Long(0));
        }
        resp = mwMaterialService.getMaterialSchItemsByProjectAndLoc(materialSchItemsReq);
        return new ResponseEntity<MaterialSchItemsResp>(resp, HttpStatus.OK);
    }

}
