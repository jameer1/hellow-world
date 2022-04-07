package com.rjtech.mw.service.impl.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.CountryGeoNames;
import com.rjtech.common.dto.ProvisionGeoNames;
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
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.mw.service.common.MWCommonService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.projsettings.constans.ProjSettingsURLConstants;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.projsettings.req.ProjectTangibleReq;
import com.rjtech.projsettings.resp.ProjectTangibleResp;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.register.emp.req.EmpRegisterReq;

@Service(value = "mwCommonService")
@RJSService(modulecode = "mwCommonService")
@Transactional
public class MWCommonServiceImpl extends RestConfigServiceImpl implements MWCommonService {

    private static final Logger log = LoggerFactory.getLogger(MWCommonServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    public CountryResp getCountryDetailsById(CountryGetReq countryGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.GET_COUNTRY_DETAILS_BY_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(countryGetReq));
        return AppUtils.fromJson(strResponse.getBody(), CountryResp.class);
    }

    public CountryResp getCountries(CountryGetReq countryGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.GET_COUNTRY_DETAILS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(countryGetReq));
        return AppUtils.fromJson(strResponse.getBody(), CountryResp.class);
    }

    public ProvisionResp saveCountryProvisions(ProvisionSaveReq provisionSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.SAVE_COUNTRY_PROVISIONS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(provisionSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProvisionResp.class);
    }

    public CountryResp saveCountryDetailsById(CountrySaveReq countrySaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.SAVE_COUNTRY_DETAILS_BY_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(countrySaveReq));
        return AppUtils.fromJson(strResponse.getBody(), CountryResp.class);
    }

    public CountryResp saveCountries(CountrySaveReq countrySaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.SAVE_COUNTRY_DETAILS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(countrySaveReq));
        return AppUtils.fromJson(strResponse.getBody(), CountryResp.class);
    }

    public ResourceCurveResp getResourceCurves(ResourceCurveGetReq resourceCurveGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.GET_RESOURCECURVES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(resourceCurveGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ResourceCurveResp.class);
    }

    public ResourceCurveResp SaveResourceCurves(ResourceCurveSaveReq resourceCurveSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.SAVE_RESOURCECURVES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(resourceCurveSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ResourceCurveResp.class);
    }

    public ResourceCurveResp resourceCurvesDeactivate(ResourceCurvesDeactivateReq resourceCurvesDeactivateReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.DEACTIVATE_RESOURCECURVES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(resourceCurvesDeactivateReq));
        return AppUtils.fromJson(strResponse.getBody(), ResourceCurveResp.class);
    }

    public LabelKeyTOResp getEmpUsersOnly(ProjUsersReq projUsersReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.GET_EMP_USERS_ONLY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projUsersReq));
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public LabelKeyTOResp getProjUsersOnly(ProjUsersReq projUsersReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.GET_PROJ_USERS_ONLY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projUsersReq));
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }
    
    public LabelKeyTOResp getAllProjUsers(ProjUsersReq projUsersReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.GET_ALL_PROJ_USERS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projUsersReq));
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public ProvisionResp getCountryProvisions(CountryFilterReq countryFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.GET_COUNTRY_PROVISIONS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(countryFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), ProvisionResp.class);
    }

    public CountryInfoResp getCountryInfoJSON() {
        String requestUrl = "http://api.geonames.org/countryInfoJSON?username=amit11patel6";
        CountryGeoNames resp = restTemplate.getForObject(requestUrl, CountryGeoNames.class);
        CountryInfoResp countryInfoResp = new CountryInfoResp();
        countryInfoResp.setCountryInfoTOs(resp.getGeonames());
        return countryInfoResp;
    }

    public ProvisionResp getCountryProvisionsJSON(CountryFilterReq countryFilterReq) {

        String requestUrl = "http://api.geonames.org/childrenJSON?username=amit11patel6&geonameId="
                + countryFilterReq.getGeonameId();
        log.info("Provision Fetch Url {}", requestUrl);
        ProvisionGeoNames resp = restTemplate.getForObject(requestUrl, ProvisionGeoNames.class);
        ProvisionResp provisionResp = new ProvisionResp();
        if (CommonUtil.objectNotNull(resp))
            provisionResp.getProvisionTOs().addAll(resp.getGeonames());
        return provisionResp;
    }

    public TimeZoneTO getTimezoneJSON(TimezoneReq timezoneReq) {
        String requestUrl = "http://api.geonames.org/timezoneJSON?username=amit11patel6&lat=" + timezoneReq.getLat()
                + "&lng=" + timezoneReq.getLng();
        return restTemplate.getForObject(requestUrl, TimeZoneTO.class);
    }

	@Override
	public ProjectTangibleResp getTangiblesOfProjects(ProjectTangibleReq projectTangibleReq) {
		ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projectTangibleReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + CommonConstants.GET_TANGIBLES_OF_PROJECTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjectTangibleResp.class);
	}
	
}
