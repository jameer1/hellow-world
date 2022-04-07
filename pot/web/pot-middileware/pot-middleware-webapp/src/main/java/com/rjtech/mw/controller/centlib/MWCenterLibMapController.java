package com.rjtech.mw.controller.centlib;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.constants.CentralLibraryConstants;
import com.rjtech.centrallib.dto.EmployeeWageRateTO;
import com.rjtech.centrallib.req.EmpWagesFilterReq;
import com.rjtech.centrallib.req.UserReq;
import com.rjtech.centrallib.resp.CentLibMapResp;
import com.rjtech.centrallib.resp.CompanyListMapResp;
import com.rjtech.centrallib.resp.EmpClassMapResp;
import com.rjtech.centrallib.resp.EmpWageResp;
import com.rjtech.centrallib.resp.PlantClassMapResp;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.mw.service.centlib.MWCenterLibMapService;
import com.rjtech.mw.service.centlib.MWCentralLibService;

@RestController
@RequestMapping(CentralLibraryConstants.PARH_URL)
public class MWCenterLibMapController {

    @Autowired
    private MWCenterLibMapService mwCenterLibMapService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANY_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getCompanyListMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getCompanyListMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_MEASUREMENT_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> measurementMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.measurementMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_WEATHERS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getWeatherDetailsMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getWeatherDetailsMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_EMP_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getEmpClassesMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getEmpClassesMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getPlantClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getPlantClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_MATERIAL_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getMaterialClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getMaterialClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COST_CODE_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getCostCodeClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getCostCodeClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_EMP_WAGE_FACTOR_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getEmpWageFactorMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getEmpWageFactorMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PROCURE_CATG_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getProcureCatgClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getProcureCatgClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_SERVICE_CLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getServiceClassMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getServiceClassMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_WARE_HOUSE_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getWareHouseMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getWareHouseMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_SERVICE_HISTORY_MAP, method = RequestMethod.POST)
    public ResponseEntity<CentLibMapResp> getPlantServiceHistoryMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CentLibMapResp>(mwCenterLibMapService.getPlantServiceHistoryMap(userReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANIES_MAP, method = RequestMethod.POST)
    public ResponseEntity<CompanyListMapResp> getCompaniesMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<CompanyListMapResp>(mwCenterLibMapService.getCompaniesMap(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_EMP_CLASSIFICATION_MAP, method = RequestMethod.POST)
    public ResponseEntity<EmpClassMapResp> getEmpClassificationMap(@RequestBody UserReq userReq) {
        EmpClassMapResp resp = new EmpClassMapResp();
        EmpWagesFilterReq empWagesFilterReq = new EmpWagesFilterReq();
        EmpWageResp empWageResp = mwCentralLiblService.getEmpWages(empWagesFilterReq);
        Map<Long, LabelKeyTO> map = new HashMap<Long, LabelKeyTO>();
        for (EmployeeWageRateTO employeeWageRateTO : empWageResp.getEmployeeWageRateTOs()) {
            LabelKeyTO wageLabelKeyTO = new LabelKeyTO();
            wageLabelKeyTO.setId(employeeWageRateTO.getWageRateId());
            wageLabelKeyTO.setCode(employeeWageRateTO.getCode());
            wageLabelKeyTO.setName(employeeWageRateTO.getName());
            wageLabelKeyTO.setUnitOfMeasure(String.valueOf(employeeWageRateTO.getWageFactor()));
            map.put(employeeWageRateTO.getWageRateId(), wageLabelKeyTO);
        }
        EmpClassMapResp empClassMap = mwCenterLibMapService.getEmpClassificationMap(userReq);
        resp.setEmpClassMap(empClassMap.getEmpClassMap());
        resp.setWageFactorMap(map);
        return new ResponseEntity<EmpClassMapResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_CLASSIFICATION_MAP, method = RequestMethod.POST)
    public ResponseEntity<PlantClassMapResp> getPlantClassificationMap(@RequestBody UserReq userReq) {
        return new ResponseEntity<PlantClassMapResp>(mwCenterLibMapService.getPlantClassificationMap(userReq),
                HttpStatus.OK);
    }
}
