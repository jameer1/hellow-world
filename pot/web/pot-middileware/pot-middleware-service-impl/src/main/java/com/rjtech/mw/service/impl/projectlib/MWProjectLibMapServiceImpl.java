package com.rjtech.mw.service.impl.projectlib;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.rjtech.common.resp.LabelKeyTOMapResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.projlib.MWProjLibMapService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.projectlib.constans.ProjLibURLConstants;
import com.rjtech.projectlib.req.ProjCrewGetReq;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.resp.ProjLibMapResp;
import com.rjtech.projectlib.resp.ProjLibUniqueMapResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwProjLibMapService")
@RJSService(modulecode = "mwProjLibMapService")
@Transactional
public class MWProjectLibMapServiceImpl extends RestConfigServiceImpl implements MWProjLibMapService {

    /*
     * public ProjLibMapResp ProjEmpClassMap(ProjGetReq projGetReq) {
     * ResponseEntity<String> strResponse = null; String url =
     * getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL +
     * ProjLibURLConstants.GET_PROJEMPCLASS_MAP); strResponse =
     * constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq)); return
     * AppUtils.fromJson(strResponse.getBody(), ProjLibMapResp.class); }
     */

    public ProjLibMapResp ProjStockPileMap(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJSTOCKPILE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLibMapResp.class);
    }

    public ProjLibMapResp ProjWorkShiftMap(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJWORKSHIFT_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLibMapResp.class);
    }

    public ProjLibMapResp ProjCrewListMap(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJCREWLIST_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLibMapResp.class);
    }

    public ProjLibMapResp ProjSOEMap(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJSOE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLibMapResp.class);
    }

    public ProjLibMapResp ProjSORMap(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJSOR_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLibMapResp.class);
    }

    public ProjLibMapResp ProjCostCodeMap(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJCOSTCODE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLibMapResp.class);
    }

    public ProjLibMapResp getEpsListMap(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_EPSLIST_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLibMapResp.class);
    }

    public ProjLibMapResp ProjEmpClassMap(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJEMPCLASS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLibMapResp.class);
    }

    public ProjLibUniqueMapResp ProjPlantClassMap(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJPLANTCLASS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLibUniqueMapResp.class);
    }

    public ProjLibMapResp getEpsProjectMap(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_EPSPROJ_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLibMapResp.class);
    }

    public LabelKeyTOMapResp getMultiProjCodeMap(@RequestBody ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTI_PROJ_CODE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOMapResp.class);
    }

    public LabelKeyTOMapResp getMultiProjSOWItemMap(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTI_PROJ_SOW_ITEMS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOMapResp.class);

    }

    public LabelKeyTOMapResp getMultiProjSORItemMap(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTI_PROJ_SOR_ITEMS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOMapResp.class);

    }

    public LabelKeyTOMapResp getMultiProjCostCodeMap(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTI_PROJ_COST_CODE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOMapResp.class);

    }

    public LabelKeyTOMapResp getMultiEPSProjCodeMap(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTI_EPS_CODE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOMapResp.class);
    }

}
