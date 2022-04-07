package com.rjtech.mw.service.impl.projschedule;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.projschedule.MWProjScheduleService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.projschedule.constans.ProjScheduleURLConstants;
import com.rjtech.projschedule.req.ScheduleActivityDataSetReq;
import com.rjtech.projschedule.req.ProjScheduleBaseLineDelReq;
import com.rjtech.projschedule.req.ProjScheduleBaseLineGetReq;
import com.rjtech.projschedule.req.ProjScheduleCostCodeSaveReq;
import com.rjtech.projschedule.req.ProjScheduleManPowerSaveReq;
import com.rjtech.projschedule.req.ProjScheduleMaterialSaveReq;
import com.rjtech.projschedule.req.ProjScheduleMultiBudgetTypeReq;
import com.rjtech.projschedule.req.ProjSchedulePlantSaveReq;
import com.rjtech.projschedule.req.ProjScheduleSOWSaveReq;
import com.rjtech.projschedule.req.ProjScheduleSaveAssignedBaseLineReq;
import com.rjtech.projschedule.req.ProjScheduleSowForReportsGetReq;
import com.rjtech.projschedule.req.ResourceCurveMappingReq;
import com.rjtech.projschedule.resp.ScheduleActivityDataSetResp;
import com.rjtech.projschedule.resp.ScheduleActivityGanttDataResp;
import com.rjtech.projschedule.resp.CalendarSpecialWorkingNonworkingDays;
import com.rjtech.projschedule.resp.ProjScheduleBaseLineResp;
import com.rjtech.projschedule.resp.ProjScheduleCostCodeResp;
import com.rjtech.projschedule.resp.ProjScheduleManPowerResp;
import com.rjtech.projschedule.resp.ProjScheduleMaterialResp;
import com.rjtech.projschedule.resp.ProjSchedulePlantResp;
import com.rjtech.projschedule.resp.ProjScheduleSOWItemResp;
import com.rjtech.projschedule.resp.ProjScheduleSOWResp;
import com.rjtech.projsettings.resp.ProjManPowerResp;
import com.rjtech.projsettings.resp.ProjectMaterialsResp;
import com.rjtech.projsettings.resp.ProjectPlantsResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwProjScheduleService")
@RJSService(modulecode = "mwProjScheduleService")
@Transactional
public class MWProjScheduleServiceImpl extends RestConfigServiceImpl implements MWProjScheduleService {

    public ProjManPowerResp getProjBudgetManPowerDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.GET_PROJ_BUDGET_MAN_POWER_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjManPowerResp.class);
    }

    public ProjScheduleBaseLineResp getProjScheduleBaseLines(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.GET_PROJ_SCHEDULE_BASE_LINES);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleBaseLineResp.class);
    }

    public ProjScheduleManPowerResp getProjScheduleManPowerDetails(
            ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.GET_PROJ_SCHEDULE_MAN_POWER_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleManPowerResp.class);
    }

    public ProjScheduleManPowerResp saveProjScheduleManPowerDetails(
            ProjScheduleManPowerSaveReq projScheduleManPowerSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleManPowerSaveReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_MAN_POWER_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleManPowerResp.class);
    }

    public ProjSchedulePlantResp getProjSchedulePlantDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.GET_PROJ_SCHEDULE_PLANT_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjSchedulePlantResp.class);
    }

    public ProjSchedulePlantResp saveProjSchedulePlantDetails(ProjSchedulePlantSaveReq projSchedulePlantSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSchedulePlantSaveReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_PLANT_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjSchedulePlantResp.class);
    }

    public ProjScheduleMaterialResp getProjScheduleMaterialDetails(
            ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.GET_PROJ_SCHEDULE_MATERIAL_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleMaterialResp.class);
    }

    public ProjScheduleMaterialResp saveProjScheduleMaterialDetails(
            ProjScheduleMaterialSaveReq projScheduleMaterialSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleMaterialSaveReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_MATERIAL_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleMaterialResp.class);
    }

    public ProjScheduleCostCodeResp getProjScheduleCostCodeDetails(
            ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.GET_PROJ_SCHEDULE_COST_CODE_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleCostCodeResp.class);
    }

    public ProjScheduleCostCodeResp saveProjScheduleCostCodeDetails(
            ProjScheduleCostCodeSaveReq projScheduleCostCodeSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleCostCodeSaveReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_COST_CODE_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleCostCodeResp.class);
    }

    public ProjScheduleSOWResp getProjScheduleSOWDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.GET_PROJ_SCHEDULE_SOW_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleSOWResp.class);
    }

    public ProjScheduleSOWResp saveProjScheduleSOWDetails(ProjScheduleSOWSaveReq projScheduleSOWSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleSOWSaveReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_SOW_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleSOWResp.class);
    }

    public ProjScheduleBaseLineResp deleteProjScheduleBaseLines(ProjScheduleBaseLineDelReq projScheduleBaseLineDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineDelReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL
                        + ProjScheduleURLConstants.DELETE_PROJ_SCHEDULE_BASE_LINES);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleBaseLineResp.class);
    }

    public void saveAssignedBaseLine(ProjScheduleSaveAssignedBaseLineReq projScheduleSaveAssignedBaseLineReq) {
        getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleSaveAssignedBaseLineReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.SAVE_ASSIGNED_BASE_LINE);
    }

    public List<ProjScheduleSOWItemResp> getSowForReport(
            ProjScheduleSowForReportsGetReq projScheduleSowForReportsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleSowForReportsGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_SOW_FOR_REPORTS);
        return AppUtils.fromJson(strResponse.getBody(), List.class);
    }
    
    public ScheduleActivityDataSetResp parseScheduleActivityData(MultipartFile file,
    		String scheduleActivityDataSetReq) {
    	ResponseEntity<String> strResponse = constructPOSTRestTemplateWithMultipartFile(getProjectSettingExchangeUrl(
                (ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.PARSE_SCHEDULE_FILE)), file,
    			scheduleActivityDataSetReq, "scheduleActivityDataSetReq");
        return AppUtils.fromJson(strResponse.getBody(), ScheduleActivityDataSetResp.class);
    }
    
    public ScheduleActivityDataSetResp parseResourceAssignmentData(MultipartFile file,
    		String scheduleActivityDataSetReq) {
    	ResponseEntity<String> strResponse = constructPOSTRestTemplateWithMultipartFile(getProjectSettingExchangeUrl(
                (ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.PARSE_RESOURCE_FILE)), file,
    			scheduleActivityDataSetReq, "scheduleActivityDataSetReq");
        return AppUtils.fromJson(strResponse.getBody(), ScheduleActivityDataSetResp.class);
    }
    
    public ScheduleActivityDataSetResp saveScheduleActivity(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(scheduleActivityDataSetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.SAVE_SCHEDULE_ACTIVITY);
        return AppUtils.fromJson(strResponse.getBody(), ScheduleActivityDataSetResp.class);
    }
    
    public ScheduleActivityDataSetResp getScheduleActivityDatasetList(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(scheduleActivityDataSetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_SCHEDULE_ACTIVITY_DATASET_LIST);
        return AppUtils.fromJson(strResponse.getBody(), ScheduleActivityDataSetResp.class);
    }
    
    public ScheduleActivityDataSetResp getScheduleActivity(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(scheduleActivityDataSetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_SCHEDULE_ACTIVITY);
        return AppUtils.fromJson(strResponse.getBody(), ScheduleActivityDataSetResp.class);
    }
    
    public ScheduleActivityDataSetResp saveScheduleActivityDatasets(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(scheduleActivityDataSetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.SAVE_SCHEDULE_ACTIVITY_DATA_SETS);
        return AppUtils.fromJson(strResponse.getBody(), ScheduleActivityDataSetResp.class);
    }
    
    public ScheduleActivityDataSetResp getDateWiseForecastActualForResource(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(scheduleActivityDataSetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_DATE_WISE_FORECAST_ACTUAL_FOR_RESOURCE);
        return AppUtils.fromJson(strResponse.getBody(), ScheduleActivityDataSetResp.class);
    }
    
    public ProjectMaterialsResp getProjBudgetMaterialDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_PROJ_BUDGET_MATERIAL_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjectMaterialsResp.class);
    }
    
    public ProjectPlantsResp getProjBudgetPlantDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_PROJ_BUDGET_PLANT_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjectPlantsResp.class);
    }
    
    public ProjScheduleCostCodeResp getProjBudgetCostCodeDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_PROJ_BUDGET_COST_CODE_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleCostCodeResp.class);
    }
    
    public ProjScheduleSOWResp getTangibles(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_TANGIBLES);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleSOWResp.class);
    }
    
    public ProjScheduleSOWResp getScheduleOfRates(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleBaseLineGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_SCHEDULE_OF_RATES);
        return AppUtils.fromJson(strResponse.getBody(), ProjScheduleSOWResp.class);
    }
    
    public ScheduleActivityDataSetResp getActualActivityScheduleFor(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(scheduleActivityDataSetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_ACTUAL_ACTIVITY_SCHEDULE_FOR);
        return AppUtils.fromJson(strResponse.getBody(), ScheduleActivityDataSetResp.class);
    }
    
    public ScheduleActivityGanttDataResp getActualActivityScheduleForGanttChart(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(scheduleActivityDataSetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_ACTUAL_ACTIVITY_SCHEDULE_FOR_GANTT_CHART);
        return AppUtils.fromJson(strResponse.getBody(), ScheduleActivityGanttDataResp.class);
    }
    
    public ScheduleActivityDataSetResp prepareResourceAssignmentData(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(scheduleActivityDataSetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.PREPARE_RESOURCE_ASSIGNMENT_DATA_TABLE);
        return AppUtils.fromJson(strResponse.getBody(), ScheduleActivityDataSetResp.class);
    }
    
    public void saveResourceCurveMapping(ResourceCurveMappingReq resourceCurveMappingReq) {
        getProjectSettingsPOSTRestTemplate(AppUtils.toJson(resourceCurveMappingReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.SAVE_RESOURCE_CURVE_MAPPING);
    }

	@Override
	public String getMultiProjBudgetManPowerDetails(List<Long> projIds) {
		ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projIds),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_MULTI_PROJ_BUDGET_MANPOWER_DETAILS);
		return strResponse.getBody();
	}
	
	@Override
	public String getMultiProjBudgetPlantDetails(List<Long> projIds) {
		ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projIds),
				ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_MULTI_PROJ_BUDGET_PLANT_DETAILS);
		return strResponse.getBody();
	}

	@Override
	public String getMultiProjBudgetSOWDetails(List<Long> projIds) {
		ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projIds),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_MULTI_PROJ_BUDGET_SOW_DETAILS);
		return strResponse.getBody();
	}

	@Override
	public String getMultiProjMultiBudgetTypeDetails(
			ProjScheduleMultiBudgetTypeReq projScheduleMultiBudgetTypeReq) {
		ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projScheduleMultiBudgetTypeReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_MULTI_PROJ_MULTI_BUDGET_TYPE_DETAILS);
		return strResponse.getBody();
	}

	@Override
	public CalendarSpecialWorkingNonworkingDays getCalendarSpecialWorkingNonworkingDays(Long projectId) {
		ResponseEntity<String> strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projectId),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_CALENDAR_SPECIAL_WORKING_NONWORKING_DAYS);
		return AppUtils.fromJson(strResponse.getBody(), CalendarSpecialWorkingNonworkingDays.class);
	}
}
