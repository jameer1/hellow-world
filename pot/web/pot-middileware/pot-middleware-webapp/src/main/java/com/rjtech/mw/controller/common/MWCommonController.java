package com.rjtech.mw.controller.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.centrallib.req.EmpClassFilterReq;
import com.rjtech.centrallib.req.ProcureCatgFilterReq;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.ProcureCatgResp;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.TimeZoneTO;
import com.rjtech.common.req.CountryFilterReq;
import com.rjtech.common.req.CountryGetReq;
import com.rjtech.common.req.CountrySaveReq;
import com.rjtech.common.req.ProjUsersReq;
import com.rjtech.common.req.ProvisionSaveReq;
import com.rjtech.common.req.ResourceCurveGetReq;
import com.rjtech.common.req.ResourceCurveSaveReq;
import com.rjtech.common.req.ResourceCurvesDeactivateReq;
import com.rjtech.common.req.SystemDateReq;
import com.rjtech.common.req.TimezoneReq;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.CountryInfoResp;
import com.rjtech.common.resp.CountryResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.resp.ProvisionResp;
import com.rjtech.common.resp.ResourceCurveResp;
import com.rjtech.common.resp.SystemDateResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ProcurementCatg;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.common.MWCommonService;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.projsettings.req.ProjectTangibleReq;
import com.rjtech.projsettings.resp.ProjectTangibleResp;
import com.rjtech.register.emp.req.EmpRegisterGetReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.common.utils.AppUtils;

@RestController
@RequestMapping(CommonConstants.PARH_URL)
public class MWCommonController {

    @Autowired
    private MWCommonService mwCommonService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @RequestMapping(value = CommonConstants.GET_SYSTEM_DATE, method = RequestMethod.POST)
    public ResponseEntity<SystemDateResp> getSystemDate(@RequestBody SystemDateReq systemDateReq) {
        return new ResponseEntity<SystemDateResp>(getSystemDateForProject(systemDateReq), HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.GET_COUNTRY_DETAILS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<CountryResp> getCountryDetailsById(@RequestBody CountryGetReq countryGetReq) {
        return new ResponseEntity<CountryResp>(mwCommonService.getCountryDetailsById(countryGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.GET_COUNTRY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<CountryResp> getCountryDetails(@RequestBody CountryGetReq countryGetReq) {
        return new ResponseEntity<CountryResp>(mwCommonService.getCountries(countryGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.SAVE_COUNTRY_PROVISIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveCountryProvisions(@RequestBody ProvisionSaveReq provisionSaveReq) {
        return new ResponseEntity<AppResp>(mwCommonService.saveCountryProvisions(provisionSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.SAVE_COUNTRY_DETAILS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<CountryResp> saveCountryDetailsById(@RequestBody CountrySaveReq countrySaveReq) {

        return new ResponseEntity<CountryResp>(mwCommonService.saveCountryDetailsById(countrySaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.SAVE_COUNTRY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<CountryResp> saveCountries(@RequestBody CountrySaveReq countrySaveReq) {

        return new ResponseEntity<CountryResp>(mwCommonService.saveCountries(countrySaveReq), HttpStatus.OK);
    }

    public Map<Long, LabelKeyTO> empClssificationMap(EmpRegisterGetReq empRegisterGetReq) {
        Map<Long, LabelKeyTO> empempClassMap = new HashMap<Long, LabelKeyTO>();
        EmpClassFilterReq empClassFilterReq = new EmpClassFilterReq();
        empClassFilterReq.setStatus(empRegisterGetReq.getStatus());
        EmpClassesResp empClassesResp = mwCentralLiblService.getEmpClasses(empClassFilterReq);
        LabelKeyTO empclassLabelKeyTO = null;
        for (EmpClassTO empClassTO : empClassesResp.getEmpClassTOs()) {
            empclassLabelKeyTO = new LabelKeyTO();
            empclassLabelKeyTO.setId(empClassTO.getId());
            empclassLabelKeyTO.setCode(empClassTO.getCode());
            empclassLabelKeyTO.setName(empClassTO.getName());

            empempClassMap.put(empClassTO.getId(), empclassLabelKeyTO);
        }
        return empempClassMap;
    }

    public Map<Long, LabelKeyTO> procureCatgMap(EmpRegisterGetReq empRegisterGetReq) {
        Map<Long, LabelKeyTO> emppocureMentCatgMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO procureLabelKeyTO = null;
        ProcureCatgFilterReq procureCatgFilterReq = new ProcureCatgFilterReq();
        procureCatgFilterReq.setStatus(empRegisterGetReq.getStatus());
        procureCatgFilterReq.setSubProcureName(ProcurementCatg.PLANT.getDesc());
        ProcureCatgResp procureCatgResp = mwCentralLiblService.getProcureCatgs(procureCatgFilterReq);
        for (ProcureMentCatgTO procureMentCatgTO : procureCatgResp.getProcureMentCatgTOs()) {
            procureLabelKeyTO = new LabelKeyTO();
            procureLabelKeyTO.setId(procureMentCatgTO.getProcurement().getId().longValue());
            procureLabelKeyTO.setCode(procureMentCatgTO.getCode());
            procureLabelKeyTO.setName(procureMentCatgTO.getDesc());

            emppocureMentCatgMap.put(procureMentCatgTO.getProcurement().getId().longValue(), procureLabelKeyTO);
        }

        return emppocureMentCatgMap;
    }

    @RequestMapping(value = CommonConstants.GET_RESOURCECURVES, method = RequestMethod.POST)
    public ResponseEntity<ResourceCurveResp> getResourceCurves(@RequestBody ResourceCurveGetReq resourceCurveGetReq) {
        return new ResponseEntity<ResourceCurveResp>(mwCommonService.getResourceCurves(resourceCurveGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.SAVE_RESOURCECURVES, method = RequestMethod.POST)
    public ResponseEntity<ResourceCurveResp> SaveResourceCurves(
            @RequestBody ResourceCurveSaveReq resourceCurveSaveReq) {

        return new ResponseEntity<ResourceCurveResp>(mwCommonService.SaveResourceCurves(resourceCurveSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.DEACTIVATE_RESOURCECURVES, method = RequestMethod.POST)
    public ResponseEntity<ResourceCurveResp> resourceCurvesDeactivate(
            @RequestBody ResourceCurvesDeactivateReq resourceCurvesDeactivateReq) {

        return new ResponseEntity<ResourceCurveResp>(
                mwCommonService.resourceCurvesDeactivate(resourceCurvesDeactivateReq), HttpStatus.OK);
    }

    private SystemDateResp getSystemDateForProject(SystemDateReq systemDateReq) {
        SystemDateResp systemDateResp = new SystemDateResp();
        Date date = new Date();
        systemDateResp.setCurrentDate(date);
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.MMM_YYYY_FORMAT, CommonUtil.getMMMYYYFormat(date));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.DD_MMM_YYYY_FORMAT, CommonUtil.getDDMMMYYYFormat(date));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.DD_MM_YYYY_FORMAT, CommonUtil.getDDMMYYYFormat(date));
        systemDateResp.setLabelKeyTO(labelKeyTO);
        return systemDateResp;

    }

    @RequestMapping(value = CommonConstants.GET_EMP_USERS_ONLY, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getEmpUsersOnly(@RequestBody ProjUsersReq projUsersReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwCommonService.getEmpUsersOnly(projUsersReq), HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.GET_PROJ_USERS_ONLY, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjUsersOnly(@RequestBody ProjUsersReq projUsersReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwCommonService.getProjUsersOnly(projUsersReq), HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.GET_COUNTRY_PROVISIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> getCountryProvisions(@RequestBody CountryFilterReq countryFilterReq) {

        return new ResponseEntity<AppResp>(mwCommonService.getCountryProvisions(countryFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.COUNTRY_INFO_JSON, method = RequestMethod.POST)
    public ResponseEntity<CountryInfoResp> getCountryInfoJSON() {
        CountryInfoResp geoNames = mwCommonService.getCountryInfoJSON();
        return new ResponseEntity<CountryInfoResp>(geoNames, HttpStatus.OK);
    }

    @PostMapping(value = CommonConstants.GET_COUNTRY_PROVISIONS_JSON)
    public ResponseEntity<ProvisionResp> getCountryProvisionsJSON(@RequestBody CountryFilterReq countryFilterReq) {
        ProvisionResp provisionsRes = mwCommonService.getCountryProvisionsJSON(countryFilterReq);
        return new ResponseEntity<ProvisionResp>(provisionsRes, HttpStatus.OK);
    }

    @PostMapping(value = CommonConstants.GET_TIMEZONE_JSON)
    public ResponseEntity<TimeZoneTO> getTimezoneJSON(@RequestBody TimezoneReq timezoneReq) {
        TimeZoneTO timezoneRes = mwCommonService.getTimezoneJSON(timezoneReq);
        return new ResponseEntity<TimeZoneTO>(timezoneRes, HttpStatus.OK);
    }
    
    @PostMapping(value = CommonConstants.GET_TANGIBLES_OF_PROJECTS)
    public ResponseEntity<ProjectTangibleResp> getTangiblesOfProjects(@RequestBody ProjectTangibleReq projectTangibleReq) {
        return new ResponseEntity<ProjectTangibleResp>(mwCommonService.getTangiblesOfProjects(projectTangibleReq), HttpStatus.OK);
    }

    @RequestMapping(value = CommonConstants.GET_ALL_PROJ_USERS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getAllProjUsers( @RequestBody ProjUsersReq projUsersReq ) {
        return new ResponseEntity<LabelKeyTOResp>( mwCommonService.getAllProjUsers( projUsersReq ), HttpStatus.OK );
    }
    
}
