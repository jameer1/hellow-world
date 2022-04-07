package com.rjtech.projscheudle.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
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
import com.rjtech.projschedule.service.ProjScheduleService;
import com.rjtech.projsettings.resp.ProjManPowerResp;
import com.rjtech.projsettings.resp.ProjectMaterialsResp;
import com.rjtech.projsettings.resp.ProjectPlantsResp;

@RestController
@RequestMapping(ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL)
@MultipartConfig
public class ProjScheduleController {

    @Autowired
    private ProjScheduleService projScheduleService;

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_BUDGET_MAN_POWER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjManPowerResp> getProjBudgetManPowerDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjManPowerResp>(
                projScheduleService.getProjBudgetManPowerDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjScheduleURLConstants.GET_MULTI_PROJ_BUDGET_MANPOWER_DETAILS)
    public ResponseEntity<Map<Long, ProjManPowerResp>> getMultiProjBudgetManPowerDetails(
            @RequestBody List<Long> projIds) {
        return new ResponseEntity<>(projScheduleService.getMultiProjBudgetManPowerDetails(projIds), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_BUDGET_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjectPlantsResp> getProjBudgetPlantDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<>(projScheduleService.getProjBudgetPlantDetails(projScheduleBaseLineGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = ProjScheduleURLConstants.GET_MULTI_PROJ_BUDGET_PLANT_DETAILS)
    public ResponseEntity<Map<Long, ProjectPlantsResp>> getMultiProjBudgetPlantDetails(
            @RequestBody List<Long> projIds) {
        return new ResponseEntity<>(projScheduleService.getMultiProjBudgetPlantDetails(projIds), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_BUDGET_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjectMaterialsResp> getProjBudgetMaterialDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<>(projScheduleService.getProjBudgetMaterialDetails(projScheduleBaseLineGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_BUDGET_COST_CODE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleCostCodeResp> getProjBudgetCostCodeDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<>(projScheduleService.getProjBudgetCostCodeDetails(projScheduleBaseLineGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_BUDGET_SOW_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleSOWItemResp> getProjBudgetSOWDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<>(projScheduleService.getProjBudgetSOWDetails(projScheduleBaseLineGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_BASE_LINES, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleBaseLineResp> getProjScheduleBaseLines(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleBaseLineResp>(
                projScheduleService.getProjScheduleBaseLines(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_MAN_POWER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleManPowerResp> getProjScheduleManPowerDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleManPowerResp>(
                projScheduleService.getProjScheduleManPowerDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_MAN_POWER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleManPowerResp> saveProjScheduleManPowerDetails(
            @RequestBody ProjScheduleManPowerSaveReq projScheduleManPowerSaveReq) {
        projScheduleService.saveProjScheduleManPowerDetails(projScheduleManPowerSaveReq);
        ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq = new ProjScheduleBaseLineGetReq();
        return new ResponseEntity<ProjScheduleManPowerResp>(
                projScheduleService.getProjScheduleManPowerDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjSchedulePlantResp> getProjSchedulePlantDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjSchedulePlantResp>(
                projScheduleService.getProjSchedulePlantDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjSchedulePlantResp> saveProjSchedulePlantDetails(
            @RequestBody ProjSchedulePlantSaveReq projScheduleManPowerSaveReq) {
        projScheduleService.saveProjSchedulePlantDetails(projScheduleManPowerSaveReq);
        ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq = new ProjScheduleBaseLineGetReq();
        return new ResponseEntity<ProjSchedulePlantResp>(
                projScheduleService.getProjSchedulePlantDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleMaterialResp> getProjScheduleMaterialDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleMaterialResp>(
                projScheduleService.getProjScheduleMaterialDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleMaterialResp> saveProjScheduleMaterialDetails(
            @RequestBody ProjScheduleMaterialSaveReq projScheduleMaterialSaveReq) {
        projScheduleService.saveProjScheduleMaterialDetails(projScheduleMaterialSaveReq);
        ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq = new ProjScheduleBaseLineGetReq();
        return new ResponseEntity<ProjScheduleMaterialResp>(
                projScheduleService.getProjScheduleMaterialDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_COST_CODE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleCostCodeResp> getProjScheduleCostCodeDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleCostCodeResp>(
                projScheduleService.getProjScheduleCostCodeDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_COST_CODE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleCostCodeResp> saveProjScheduleCostCodeDetails(
            @RequestBody ProjScheduleCostCodeSaveReq projScheduleCostCodeSaveReq) {
        projScheduleService.saveProjScheduleCostCodeDetails(projScheduleCostCodeSaveReq);
        ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq = new ProjScheduleBaseLineGetReq();
        return new ResponseEntity<ProjScheduleCostCodeResp>(
                projScheduleService.getProjScheduleCostCodeDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_SOW_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleSOWResp> getProjScheduleSOWDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleSOWResp>(
                projScheduleService.getProjScheduleSOWDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_SOW_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleSOWResp> saveProjScheduleSOWDetails(
            @RequestBody ProjScheduleSOWSaveReq projScheduleSOWSaveReq) {
        projScheduleService.saveProjScheduleSOWDetails(projScheduleSOWSaveReq);
        ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq = new ProjScheduleBaseLineGetReq();
        return new ResponseEntity<ProjScheduleSOWResp>(
                projScheduleService.getProjScheduleSOWDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.DELETE_PROJ_SCHEDULE_BASE_LINES, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleBaseLineResp> deleteProjScheduleBaseLines(
            @RequestBody ProjScheduleBaseLineDelReq projScheduleBaseLineDelReq) {
        projScheduleService.deleteProjScheduleBaseLines(projScheduleBaseLineDelReq);
        ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq = new ProjScheduleBaseLineGetReq();
        projScheduleBaseLineGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjScheduleBaseLineResp scheduleBaseLineResp = projScheduleService
                .getProjScheduleBaseLines(projScheduleBaseLineGetReq);
        scheduleBaseLineResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<ProjScheduleBaseLineResp>(scheduleBaseLineResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_ASSIGNED_BASE_LINE, method = RequestMethod.POST)
    public ResponseEntity<Void> saveAssignedBaseLine(
            @RequestBody ProjScheduleSaveAssignedBaseLineReq projScheduleSaveAssignedBaseLineReq) {
        projScheduleService.saveAssignedBaseLine(projScheduleSaveAssignedBaseLineReq);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_SOW_FOR_REPORTS, method = RequestMethod.POST)
    public ResponseEntity<List<ProjScheduleSOWItemResp>> getSowForReport(
            @RequestBody ProjScheduleSowForReportsGetReq projScheduleSowForReportsGetReq) {
        List<ProjScheduleSOWItemResp> response = projScheduleService
                .getProjBudgetSOWDetailsForReports(projScheduleSowForReportsGetReq);
        return new ResponseEntity<List<ProjScheduleSOWItemResp>>(response, HttpStatus.OK);
    }

    @PostMapping(value = ProjScheduleURLConstants.GET_MULTI_PROJ_BUDGET_SOW_DETAILS)
    public ResponseEntity<Map<Long, ProjScheduleSOWItemResp>> getMultiProjBudgetSOWDetails(
            @RequestBody List<Long> projIds) {
        return new ResponseEntity<>(projScheduleService.getMultiProjBudgetSOWDetails(projIds), HttpStatus.OK);
    }

    @PostMapping(value = ProjScheduleURLConstants.GET_MULTI_PROJ_MULTI_BUDGET_TYPE_DETAILS)
    public ResponseEntity<Map<Long, Map<String, Object>>> getMultiProjMultiBudgetTypeDetails(
            @RequestBody ProjScheduleMultiBudgetTypeReq projScheduleMultiBudgetTypeReq) {
        return new ResponseEntity<>(
                projScheduleService.getMultiProjMultiBudgetTypeDetails(projScheduleMultiBudgetTypeReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.PARSE_SCHEDULE_FILE, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> parseScheduleActivityData(MultipartFile files,
    		String scheduleActivityDataSetReq) throws IOException {
    	ScheduleActivityDataSetReq scheduleActivityDataSetRequest = AppUtils.fromJson(scheduleActivityDataSetReq, ScheduleActivityDataSetReq.class);
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		projScheduleService.parseScheduleActivityData(files, scheduleActivityDataSetRequest), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.PARSE_RESOURCE_FILE, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> parseResourceAssignmentData(MultipartFile files,
    		String scheduleActivityDataSetReq) throws IOException {
    	ScheduleActivityDataSetReq scheduleActivityDataSetRequest = AppUtils.fromJson(scheduleActivityDataSetReq, ScheduleActivityDataSetReq.class);
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		projScheduleService.parseResourceAssignmentData(files, scheduleActivityDataSetRequest), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.SAVE_SCHEDULE_ACTIVITY, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> saveScheduleActivity(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	if (scheduleActivityDataSetReq.getType().equalsIgnoreCase("A"))
	        return new ResponseEntity<ScheduleActivityDataSetResp>(
	        		projScheduleService.saveScheduleActivity(scheduleActivityDataSetReq), HttpStatus.OK);
    	else
    		return new ResponseEntity<ScheduleActivityDataSetResp>(
            		projScheduleService.saveResourceAssignment(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_SCHEDULE_ACTIVITY_DATASET_LIST, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> getScheduleActivityDatasetList(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		projScheduleService.getScheduleActivityDatasetList(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_SCHEDULE_ACTIVITY, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> getScheduleActivity(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		projScheduleService.getScheduleActivity(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.SAVE_SCHEDULE_ACTIVITY_DATA_SETS, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> saveScheduleActivityDatasets(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		projScheduleService.saveScheduleActivityDatasets(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_DATE_WISE_FORECAST_ACTUAL_FOR_RESOURCE, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> getDateWiseForecastActualForResource(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		projScheduleService.getDateWiseForecastActualForResource(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_TANGIBLES, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleSOWResp> getTangibles(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleSOWResp>(
                projScheduleService.getTangibles(projScheduleBaseLineGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_SCHEDULE_OF_RATES, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleSOWResp> getScheduleOfRates(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<>(projScheduleService.getScheduleOfRates(projScheduleBaseLineGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.PREPARE_RESOURCE_ASSIGNMENT_DATA_TABLE, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> prepareResourceAssignmentData(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		projScheduleService.prepareResourceAssignmentData(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_ACTUAL_ACTIVITY_SCHEDULE_FOR_GANTT_CHART, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityGanttDataResp> getActualActivityScheduleForGanttChart(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityGanttDataResp>(
        		projScheduleService.getActualActivityScheduleForGanttChart(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_ACTUAL_ACTIVITY_SCHEDULE_FOR, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> getActualActivityScheduleFor(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		projScheduleService.getActualActivityScheduleFor(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.SAVE_RESOURCE_CURVE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Void> saveResourceCurveMapping(@RequestBody ResourceCurveMappingReq resourceCurveMappingReq) {
    	projScheduleService.saveResourceCurveMapping(resourceCurveMappingReq);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_CALENDAR_SPECIAL_WORKING_NONWORKING_DAYS, method = RequestMethod.POST)
    public ResponseEntity<CalendarSpecialWorkingNonworkingDays> getCalendarSpecialWorkingNonworkingDays(@RequestBody Long projectId) {
        return new ResponseEntity<CalendarSpecialWorkingNonworkingDays>(
        		projScheduleService.getCalendarSpecialWorkingNonworkingDays(projectId), HttpStatus.OK);
    }
}
