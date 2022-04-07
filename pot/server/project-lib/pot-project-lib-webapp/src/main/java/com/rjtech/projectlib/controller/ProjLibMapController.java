package com.rjtech.projectlib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOMapResp;
import com.rjtech.projectlib.constans.ProjLibURLConstants;
import com.rjtech.projectlib.req.ProjCrewGetReq;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.resp.ProjLibMapResp;
import com.rjtech.projectlib.resp.ProjLibUniqueMapResp;
import com.rjtech.projectlib.service.ProjLibMapService;

@RestController
@RequestMapping(ProjLibURLConstants.PARH_URL)
public class ProjLibMapController {
    @Autowired
    ProjLibMapService projLibMapService;

    @RequestMapping(value = ProjLibURLConstants.GET_PROJSTOCKPILE_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> ProjStockPileMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(projLibMapService.ProjStockPileMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJWORKSHIFT_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> projWorkShiftMapResp(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(projLibMapService.ProjWorkShiftMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJCREWLIST_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> projCrewListMapResp(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(projLibMapService.ProjCrewListMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJSOE_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> projSOEMapResp(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(projLibMapService.ProjSOEMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJSOR_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> projSORMapResp(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(projLibMapService.ProjSORMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJCOSTCODE_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> projCostCodeMapResp(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(projLibMapService.ProjCostCodeMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_EPSLIST_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> getEpsListMap(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<ProjLibMapResp>(projLibMapService.getEpsListMap(projGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJEMPCLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> ProjEmpClassMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(projLibMapService.ProjEmpClassMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJPLANTCLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibUniqueMapResp> ProjPlantClassMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibUniqueMapResp>(projLibMapService.ProjPlantClassMap(projCrewGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_EPSPROJ_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> getEpsProjectMap(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<ProjLibMapResp>(projLibMapService.getEpsProjectMap(projGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_CODE_MAP, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOMapResp> getProjCodes(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<LabelKeyTOMapResp>(projLibMapService.getMultiProjCodeMap(projGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_EPS_CODE_MAP, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOMapResp> getMultiEpsCodeMap(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<LabelKeyTOMapResp>(projLibMapService.getMultiEpsCodeMap(projGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_SOW_ITEMS_MAP, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOMapResp> getMultiProjSOWItemMap(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<LabelKeyTOMapResp>(projLibMapService.getMultiProjSOWItemMap(projGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_SOR_ITEMS_MAP, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOMapResp> getProjSorItemsMap(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<LabelKeyTOMapResp>(projLibMapService.getMultiProjSORItemMap(projGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_COST_CODE_MAP, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOMapResp> getMultiProjCostCodeMap(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<LabelKeyTOMapResp>(projLibMapService.getMultiProjCostCodeMap(projGetReq),
                HttpStatus.OK);
    }

}
