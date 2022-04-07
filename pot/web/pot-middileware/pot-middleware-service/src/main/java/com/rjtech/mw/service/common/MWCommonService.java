package com.rjtech.mw.service.common;


import com.rjtech.common.dto.TimeZoneTO;
import com.rjtech.common.req.CountryFilterReq;
import com.rjtech.common.req.CountryGetReq;
import com.rjtech.common.req.CountrySaveReq;
import com.rjtech.common.req.ProjUsersReq;
import com.rjtech.common.req.ProvisionSaveReq;
import com.rjtech.common.req.ResourceCurveGetReq;
import com.rjtech.common.req.ResourceCurveSaveReq;
import com.rjtech.common.req.ResourceCurvesDeactivateReq;
import com.rjtech.common.req.TimezoneReq;
import com.rjtech.common.resp.CountryInfoResp;
import com.rjtech.common.resp.CountryResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.resp.ProvisionResp;
import com.rjtech.common.resp.ResourceCurveResp;
import com.rjtech.projsettings.req.ProjectTangibleReq;
import com.rjtech.projsettings.resp.ProjectTangibleResp;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.resp.EmpRegisterResp;


public interface MWCommonService {

    public CountryResp getCountryDetailsById(CountryGetReq countryGetReq);

    public CountryResp getCountries(CountryGetReq countryGetReq);

    ProvisionResp saveCountryProvisions(ProvisionSaveReq provisionSaveReq);

    CountryResp saveCountries(CountrySaveReq countrySaveReq);

    CountryResp saveCountryDetailsById(CountrySaveReq countrySaveReq);

    public ResourceCurveResp getResourceCurves(ResourceCurveGetReq resourceCurveGetReq);

    public ResourceCurveResp SaveResourceCurves(ResourceCurveSaveReq resourceCurveSaveReq);

    public ResourceCurveResp resourceCurvesDeactivate(ResourceCurvesDeactivateReq resourceCurvesDeactivateReq);

    public LabelKeyTOResp getEmpUsersOnly(ProjUsersReq projUsersReq);

    public LabelKeyTOResp getProjUsersOnly(ProjUsersReq projUsersReq);
    
    public LabelKeyTOResp getAllProjUsers(ProjUsersReq projUsersReq);

    ProvisionResp getCountryProvisions(CountryFilterReq countryFilterReq);

    CountryInfoResp getCountryInfoJSON();

    ProvisionResp getCountryProvisionsJSON(CountryFilterReq countryFilterReq);

    TimeZoneTO getTimezoneJSON(TimezoneReq timezoneReq);
    ProjectTangibleResp getTangiblesOfProjects(ProjectTangibleReq projectTangibleReq);
    
}
