package com.rjtech.register.controller.material;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ProcurementCatg;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.controller.common.RegisterCommonController;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.register.material.req.MaterialProjSaveReq;
import com.rjtech.register.material.req.MaterialSchItemsReq;
import com.rjtech.register.material.resp.MaterialDeliveryDocketResp;
import com.rjtech.register.material.resp.MaterialProjResp;
import com.rjtech.register.material.resp.MaterialSchItemsResp;
import com.rjtech.register.service.material.MaterialProjService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MaterialProjController extends RegisterCommonController {
    
    @Autowired
    private MaterialProjService materialProjService;

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_SCH_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjResp> getProjMaterialSchItems(@RequestBody MaterialGetReq materialGetReq) {
        MaterialProjResp resp = null;
        resp = materialProjService.getProjMaterialSchItems(materialGetReq);
        resp.setRegisterOnLoadTO(getRegisterOnLoadResp(ProcurementCatg.MATERIAL.getDesc()));
        resp.setUserProjMap(getUserProjectsMap());
        return new ResponseEntity<MaterialProjResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_SCH_ITEMS_SEARCH, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjResp> getProjMaterialSch(@RequestBody MaterialFilterReq materialGetReq) {
        MaterialProjResp resp = null;
        if (materialGetReq.getProjList().isEmpty()) {
            materialGetReq.setProjList(getUserProjectsList());
        }
        resp = materialProjService.getProjMaterialSchDetails(materialGetReq);
        resp.setRegisterOnLoadTO(getRegisterOnLoadResp(ProcurementCatg.MATERIAL.getDesc()));
        resp.setUserProjMap(getUserProjectsMap());
        return new ResponseEntity<MaterialProjResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_SCH_ITEMS_BY_PURCHASE_ORDER, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjResp> getProjMaterialSchItemsByPurchaseOrder(
            @RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<MaterialProjResp>(
                materialProjService.getMaterialSchItemsByPurchaseOrder(materialGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PROJ_MATERIAL_SCH_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjResp> saveProjMaterialSchItems(
            @RequestBody MaterialProjSaveReq materialProjectSaveReq) {
        materialProjService.saveProjMaterialSchItems(materialProjectSaveReq);
        MaterialGetReq materialGetReq = new MaterialGetReq();
        materialGetReq.setPurId(materialProjectSaveReq.getPurId());
        materialGetReq.setStatus(materialProjectSaveReq.getStatus());
        MaterialProjResp resp = materialProjService.getMaterialSchItemsByPurchaseOrder(materialGetReq);
        resp.setUserProjMap(getUserProjectsMap());
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<MaterialProjResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PROJ_MATERIAL_SCH_DOCKET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<MaterialDeliveryDocketResp> saveProjMaterialSchDocketDetails(MultipartFile[] files,
            String materialReq) {
        MaterialProjSaveReq materialProjSaveReq = null;
        MaterialDeliveryDocketResp resp = new MaterialDeliveryDocketResp();
        try {
            materialProjSaveReq = new ObjectMapper().readValue(materialReq, MaterialProjSaveReq.class);
        } catch(IOException e) {
            resp.setMessage(e.getMessage());
        }
        if (materialProjSaveReq != null) {
            materialProjService.saveProjMaterialSchDocketDetails(files, materialProjSaveReq);
            MaterialGetReq materialGetReq = new MaterialGetReq();
            materialGetReq.setMaterialId(materialProjSaveReq.getMaterialId());
            materialGetReq.setStatus(materialProjSaveReq.getStatus());
            MaterialDeliveryDocketResp successResp = materialProjService.getMaterialDeliveryDockets(materialGetReq);
            successResp.cloneAppResp(CommonUtil.getSaveAppResp());    
            return new ResponseEntity<MaterialDeliveryDocketResp>(successResp, HttpStatus.OK);
        } else 
            return new ResponseEntity<MaterialDeliveryDocketResp>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_DELIVERY_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<MaterialDeliveryDocketResp> getMaterialDeliveryDockets(
            @RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<MaterialDeliveryDocketResp>(
                materialProjService.getMaterialDeliveryDockets(materialGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_SCH_AVL_DOCKETS_LOCATION, method = RequestMethod.POST)
    public ResponseEntity<MaterialDeliveryDocketResp> getMaterialSchAvlByLocation(
            @RequestBody MaterialGetReq materialGetReq) {

        return new ResponseEntity<MaterialDeliveryDocketResp>(
                materialProjService.getMaterialDeliveryDockets(materialGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_SCH_ITEMS_BY_PROJECT_AND_LOC, method = RequestMethod.POST)
    public ResponseEntity<MaterialSchItemsResp> getMaterialSchItemsByProjectAndLoc(
            @RequestBody MaterialSchItemsReq req) {
        MaterialSchItemsResp resp = null;
        resp = materialProjService.getMaterialSchItemsByProjectAndLoc(req);
        return new ResponseEntity<MaterialSchItemsResp>(resp, HttpStatus.OK);
    }

}
