package com.rjtech.centrallib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.constants.CentralLibraryConstants;
import com.rjtech.centrallib.req.UserReq;
import com.rjtech.centrallib.resp.CentLibMapResp;
import com.rjtech.centrallib.resp.CompanyListMapResp;
import com.rjtech.centrallib.resp.EmpClassMapResp;
import com.rjtech.centrallib.resp.PlantClassMapResp;
import com.rjtech.common.service.CenterLibMapService;

@RestController
@RequestMapping(CentralLibraryConstants.PARH_URL)
public class CenterLibMapController {

    @Autowired
    private CenterLibMapService centerLibMapService;

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANY_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getCompanyListMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getCompanyListMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_MEASUREMENT_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> measurementMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.measurementMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_WEATHERS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getWeatherDetailsMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getWeatherDetailsMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_EMP_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getEmpClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getEmpClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getPlantClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getPlantClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_MATERIAL_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getMaterialClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getMaterialClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COST_CODE_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getCostCodeClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getCostCodeClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_EMP_WAGE_FACTOR_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getEmpWageFactorMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getEmpWageFactorMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PROCURE_CATG_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getProcureCatgClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getProcureCatgClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_SERVICE_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getServiceClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getServiceClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_WARE_HOUSE_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getWareHouseMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getWareHouseMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_SERVICE_HISTORY_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getPlantServiceHistoryMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(centerLibMapService.getPlantServiceHistoryMap(userReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANIES_MAP, method = RequestMethod.POST)
    public ResponseEntity<CompanyListMapResp> getCompaniesMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CompanyListMapResp>(centerLibMapService.getCompaniesMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_EMP_CLASSIFICATION_MAP, method = RequestMethod.POST)
    public ResponseEntity<EmpClassMapResp> getEmpClassificationMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<EmpClassMapResp>(centerLibMapService.getEmpClassificationMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_CLASSIFICATION_MAP, method = RequestMethod.POST)
    public ResponseEntity<PlantClassMapResp> getPlantClassificationMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<PlantClassMapResp>(centerLibMapService.getPlantClassificationMap(userReq),
                HttpStatus.OK);
    }
}
