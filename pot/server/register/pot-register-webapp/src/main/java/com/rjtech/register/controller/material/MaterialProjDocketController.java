package com.rjtech.register.controller.material;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.controller.common.RegisterCommonController;
import com.rjtech.register.material.dto.MaterialProjDocketTO;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.register.material.req.MaterialProjDocketSaveReq;
import com.rjtech.register.material.resp.MaterialProjDocketResp;
import com.rjtech.register.material.resp.MaterialSchItemsResp;
import com.rjtech.register.service.material.MaterialProjDocketSevice;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MaterialProjDocketController extends RegisterCommonController {

    @Autowired
    private MaterialProjDocketSevice materialProjDocketSevice;

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_PROJ_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjDocketResp> getMaterialProjDockets(
            @RequestBody MaterialFilterReq materialGetReq) {
        MaterialProjDocketResp resp = null;
        if (materialGetReq.getProjList().isEmpty()) {
            materialGetReq.setProjList(getUserProjectsList());
        }
        resp = materialProjDocketSevice.getMaterialProjDockets(materialGetReq);
        return new ResponseEntity<MaterialProjDocketResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_PROJ_DOCKETS_BY_DOC_TYPE, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjDocketResp> getMaterialProjDocketsByDockType(
            @RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<MaterialProjDocketResp>(
                materialProjDocketSevice.getMaterialProjDocketsByDockType(materialGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_PROJ_DOCKET_SCH_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<MaterialSchItemsResp> getMaterialSchItemsByProjDocket(
            @RequestBody MaterialGetReq materialGetReq) {
        MaterialSchItemsResp materialSchItemsResp = new MaterialSchItemsResp();
        materialSchItemsResp = materialProjDocketSevice.getMaterialSchItemsByProjDocket(materialGetReq);
        return new ResponseEntity<MaterialSchItemsResp>(materialSchItemsResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_MATERIAL_PROJ_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjDocketResp> saveMaterialProjDockets(@RequestBody MaterialProjDocketSaveReq req) {
        MaterialProjDocketResp resp = null;
        boolean continueCondition = true;
        req.getMaterialProjDocketTO().setProjdocketDate(CommonUtil.convertDateToString(new Date()));
        if (RegisterConstants.GENERATED.equalsIgnoreCase(req.getMaterialProjDocketTO().getApprStatus())) {
            MaterialProjDocketTO materialProjDocketTO = materialProjDocketSevice.saveMaterialProjDocket(req);
            if (materialProjDocketTO.isExist()) {
                resp = new MaterialProjDocketResp();
                resp.setAnyIssueExist(materialProjDocketTO.isExist());
                resp.getMaterialProjDocketTOs().add(materialProjDocketTO);
                continueCondition = false;
            }
        } else {
            materialProjDocketSevice.saveMaterialProjDocketDraft(req);
        }
        if (continueCondition) {
            MaterialFilterReq materialGetReq = new MaterialFilterReq();
            materialGetReq.setProjList(getUserProjectsList());
            resp = materialProjDocketSevice.getMaterialProjDockets(materialGetReq);
            resp.cloneAppResp(CommonUtil.getSaveAppResp());
        }

        return new ResponseEntity<MaterialProjDocketResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_SCH_DETAILS_FOR_TRANESFER, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getMaterialSchDetailsForTransfer(@RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(
                materialProjDocketSevice.getMaterialSchDetailsForTransfer(materialGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_PROJ_DOCKETS_BY_PROJ_ID, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjDocketResp> getMaterialProjDockets( @RequestBody MaterialGetReq materialGetReq ) {
        MaterialProjDocketResp resp = materialProjDocketSevice.getMaterialProjDocketsByProjectId( materialGetReq );
        return new ResponseEntity<MaterialProjDocketResp>(resp, HttpStatus.OK);
    }
}
