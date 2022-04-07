package com.rjtech.mw.service.centlib;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.centrallib.resp.CentLibMapResp;
import com.rjtech.centrallib.resp.CompanyListMapResp;
import com.rjtech.centrallib.resp.EmpClassMapResp;
import com.rjtech.centrallib.resp.PlantClassMapResp;

public interface MWCenterLibMapService {

    public CentLibMapResp getCompanyListMap(UserReq userReq);

    public CentLibMapResp measurementMap(UserReq userReq);

    public CentLibMapResp getWeatherDetailsMap(UserReq userReq);

    public CentLibMapResp getEmpClassesMap(UserReq userReq);

    public CentLibMapResp getPlantClassMap(UserReq userReq);

    public CentLibMapResp getMaterialClassMap(UserReq userReq);

    public CentLibMapResp getCostCodeClassMap(UserReq userReq);

    public CentLibMapResp getEmpWageFactorMap(UserReq userReq);

    public CentLibMapResp getProcureCatgClassMap(UserReq userReq);

    public CentLibMapResp getServiceClassMap(UserReq userReq);

    public CentLibMapResp getWareHouseMap(UserReq userReq);

    public CentLibMapResp getPlantServiceHistoryMap(UserReq userReq);

    public CompanyListMapResp getCompaniesMap(UserReq userReq);

    public EmpClassMapResp getEmpClassificationMap(UserReq userReq);

    public PlantClassMapResp getPlantClassificationMap(UserReq userReq);

}
