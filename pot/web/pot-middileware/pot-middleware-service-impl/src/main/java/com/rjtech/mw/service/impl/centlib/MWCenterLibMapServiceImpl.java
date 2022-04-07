package com.rjtech.mw.service.impl.centlib;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.constants.CentralLibraryConstants;
import com.rjtech.centrallib.req.UserReq;
import com.rjtech.centrallib.resp.CentLibMapResp;
import com.rjtech.centrallib.resp.CompanyListMapResp;
import com.rjtech.centrallib.resp.EmpClassMapResp;
import com.rjtech.centrallib.resp.PlantClassMapResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.centlib.MWCenterLibMapService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwCenterLibMapService")
@RJSService(modulecode = "mwCenterLibMapService")
@Transactional
public class MWCenterLibMapServiceImpl extends RestConfigServiceImpl implements MWCenterLibMapService {

    public CentLibMapResp getCompanyListMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_COMPANY_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp measurementMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_MEASUREMENT_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp getWeatherDetailsMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_WEATHERS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp getEmpClassesMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_EMP_CLASS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp getPlantClassMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PLANT_CLASS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp getMaterialClassMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_MATERIAL_CLASS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp getCostCodeClassMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_COST_CODE_CLASS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp getEmpWageFactorMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_EMP_WAGE_FACTOR_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp getProcureCatgClassMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PROCURE_CATG_CLASS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp getServiceClassMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_SERVICE_CLASS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp getWareHouseMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_WARE_HOUSE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CentLibMapResp getPlantServiceHistoryMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PLANT_SERVICE_HISTORY_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibMapResp.class);
    }

    public CompanyListMapResp getCompaniesMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_COMPANIES_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CompanyListMapResp.class);
    }

    public EmpClassMapResp getEmpClassificationMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_EMP_CLASSIFICATION_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), EmpClassMapResp.class);
    }

    public PlantClassMapResp getPlantClassificationMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PLANT_CLASSIFICATION_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), PlantClassMapResp.class);
    }

}
