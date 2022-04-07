package com.rjtech.mw.controller.register.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.mw.service.register.MWMaterialService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.register.material.req.MaterialProjDocketSaveReq;
import com.rjtech.register.material.req.MaterialTransReq;
import com.rjtech.register.material.resp.MaterialProjDocketResp;
import com.rjtech.register.material.resp.MaterialSchItemsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWMaterialProjDocketController {

    @Autowired
    private MWMaterialService mwMaterialService;

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_PROJ_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjDocketResp> getMaterialProjDockets(
            @RequestBody MaterialFilterReq materialFilterReq) {
        return new ResponseEntity<MaterialProjDocketResp>(mwMaterialService.getMaterialProjDockets(materialFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_PROJ_DOCKETS_BY_DOC_TYPE, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjDocketResp> getMaterialProjDocketsByDockType(
            @RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<MaterialProjDocketResp>(
                mwMaterialService.getMaterialProjDocketsByDockType(materialGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_PROJ_DOCKET_SCH_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<MaterialSchItemsResp> getMaterialSchItemsByProjDocket(
            @RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<MaterialSchItemsResp>(
                mwMaterialService.getMaterialSchItemsByProjDocket(materialGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_MATERIAL_PROJ_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjDocketResp> saveMaterialProjDocket(
            @RequestBody MaterialProjDocketSaveReq materialProjDocketSaveReq) {

        return new ResponseEntity<MaterialProjDocketResp>(
                mwMaterialService.saveMaterialProjDocket(materialProjDocketSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_SCH_DETAILS_FOR_TRANESFER, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getMaterialSchDetailsForTransfer(@RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwMaterialService.getMaterialSchDetailsForTransfer(materialGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_PROJ_DOCKETS_BY_PROJ_ID, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjDocketResp> getMaterialProjDocketsByProjId( @RequestBody MaterialGetReq materialGetReq ) {
        return new ResponseEntity<MaterialProjDocketResp>( mwMaterialService.getMaterialProjDocketsByProjId( materialGetReq ),
                HttpStatus.OK );
    }
}
