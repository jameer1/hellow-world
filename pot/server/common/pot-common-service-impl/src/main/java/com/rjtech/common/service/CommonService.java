package com.rjtech.common.service;

import com.rjtech.common.req.CountryFilterReq;
import com.rjtech.common.req.ProjUsersReq;
import com.rjtech.common.req.ProvisionSaveReq;
import com.rjtech.common.req.ResourceCurveGetReq;
import com.rjtech.common.req.ResourceCurveSaveReq;
import com.rjtech.common.req.ResourceCurvesDeactivateReq;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.resp.ProvisionResp;
import com.rjtech.common.resp.ResourceCurveResp;

public interface CommonService {

    void saveCountryProvisions(ProvisionSaveReq provisionSaveReq);

    ResourceCurveResp getResourceCurves(ResourceCurveGetReq resourceCurveGetReq);

    void saveResourceCurves(ResourceCurveSaveReq resourceCurveSaveReq);

    void resourceCurvesDeactivate(ResourceCurvesDeactivateReq resourceCurvesDeactivateReq);

    LabelKeyTOResp getEmpUsersOnly(ProjUsersReq projUsersReq);

    LabelKeyTOResp getProjUsersOnly(ProjUsersReq projUsersReq);

    ProvisionResp getAllCountryProvisions(CountryFilterReq countryFilterReq);
    
    LabelKeyTOResp getAllProjUsers(ProjUsersReq projUsersReq);

}
