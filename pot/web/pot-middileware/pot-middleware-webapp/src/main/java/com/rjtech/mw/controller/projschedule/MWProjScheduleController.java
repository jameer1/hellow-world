package com.rjtech.mw.controller.projschedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.mw.service.projschedule.MWProjScheduleService;
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

@RestController
@RequestMapping(ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL)
public class MWProjScheduleController {

    @Autowired
    private MWProjScheduleService mwProjScheduleService;

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_BUDGET_MAN_POWER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjManPowerResp> getProjBudgetManPowerDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjManPowerResp>(
                mwProjScheduleService.getProjBudgetManPowerDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_BASE_LINES, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleBaseLineResp> getProjScheduleBaseLines(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleBaseLineResp>(
                mwProjScheduleService.getProjScheduleBaseLines(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_MAN_POWER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleManPowerResp> getProjScheduleManPowerDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleManPowerResp>(
                mwProjScheduleService.getProjScheduleManPowerDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_MAN_POWER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleManPowerResp> saveProjScheduleManPowerDetails(
            @RequestBody ProjScheduleManPowerSaveReq projScheduleManPowerSaveReq) {
        return new ResponseEntity<ProjScheduleManPowerResp>(
                mwProjScheduleService.saveProjScheduleManPowerDetails(projScheduleManPowerSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjSchedulePlantResp> getProjSchedulePlantDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjSchedulePlantResp>(
                mwProjScheduleService.getProjSchedulePlantDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjSchedulePlantResp> saveProjSchedulePlantDetails(
            @RequestBody ProjSchedulePlantSaveReq projSchedulePlantSaveReq) {
        return new ResponseEntity<ProjSchedulePlantResp>(
                mwProjScheduleService.saveProjSchedulePlantDetails(projSchedulePlantSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleMaterialResp> getProjScheduleMaterialDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleMaterialResp>(
                mwProjScheduleService.getProjScheduleMaterialDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleMaterialResp> saveProjScheduleMaterialDetails(
            @RequestBody ProjScheduleMaterialSaveReq projScheduleMaterialSaveReq) {
        return new ResponseEntity<ProjScheduleMaterialResp>(
                mwProjScheduleService.saveProjScheduleMaterialDetails(projScheduleMaterialSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_COST_CODE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleCostCodeResp> getProjScheduleCostCodeDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleCostCodeResp>(
                mwProjScheduleService.getProjScheduleCostCodeDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_COST_CODE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleCostCodeResp> saveProjScheduleCostCodeDetails(
            @RequestBody ProjScheduleCostCodeSaveReq projScheduleCostCodeSaveReq) {
        return new ResponseEntity<ProjScheduleCostCodeResp>(
                mwProjScheduleService.saveProjScheduleCostCodeDetails(projScheduleCostCodeSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_SCHEDULE_SOW_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleSOWResp> getProjScheduleSOWDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<ProjScheduleSOWResp>(
                mwProjScheduleService.getProjScheduleSOWDetails(projScheduleBaseLineGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_PROJ_SCHEDULE_SOW_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleSOWResp> saveProjScheduleSOWDetails(
            @RequestBody ProjScheduleSOWSaveReq projScheduleSOWSaveReq) {
        return new ResponseEntity<ProjScheduleSOWResp>(
                mwProjScheduleService.saveProjScheduleSOWDetails(projScheduleSOWSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.DELETE_PROJ_SCHEDULE_BASE_LINES, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleBaseLineResp> deleteProjScheduleBaseLines(
            @RequestBody ProjScheduleBaseLineDelReq projScheduleBaseLineDelReq) {
        return new ResponseEntity<ProjScheduleBaseLineResp>(
                mwProjScheduleService.deleteProjScheduleBaseLines(projScheduleBaseLineDelReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.SAVE_ASSIGNED_BASE_LINE, method = RequestMethod.POST)
    public ResponseEntity<Void> saveAssignedBaseLine(
            @RequestBody ProjScheduleSaveAssignedBaseLineReq projScheduleSaveAssignedBaseLineReq) {
        mwProjScheduleService.saveAssignedBaseLine(projScheduleSaveAssignedBaseLineReq);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = ProjScheduleURLConstants.GET_SOW_FOR_REPORTS, method = RequestMethod.POST)
    public ResponseEntity<List<ProjScheduleSOWItemResp>> getSowForReports(
            @RequestBody ProjScheduleSowForReportsGetReq projScheduleSowForReportsGetReq) {
        return new ResponseEntity<List<ProjScheduleSOWItemResp>>(
                mwProjScheduleService.getSowForReport(projScheduleSowForReportsGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.PARSE_SCHEDULE_FILE, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> parseScheduleActivityData(MultipartFile file,
    		String scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		mwProjScheduleService.parseScheduleActivityData(file, scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.PARSE_RESOURCE_FILE, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> parseResourceAssignmentData(MultipartFile file,
    		String scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		mwProjScheduleService.parseResourceAssignmentData(file, scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.SAVE_SCHEDULE_ACTIVITY, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> saveScheduleActivity(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		mwProjScheduleService.saveScheduleActivity(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_SCHEDULE_ACTIVITY_DATASET_LIST, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> getScheduleActivityDatasetList(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		mwProjScheduleService.getScheduleActivityDatasetList(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_SCHEDULE_ACTIVITY, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> getScheduleActivity(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		mwProjScheduleService.getScheduleActivity(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.SAVE_SCHEDULE_ACTIVITY_DATA_SETS, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> saveScheduleActivityDatasets(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		mwProjScheduleService.saveScheduleActivityDatasets(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_DATE_WISE_FORECAST_ACTUAL_FOR_RESOURCE, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> getDateWiseForecastActualForResource(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		mwProjScheduleService.getDateWiseForecastActualForResource(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_BUDGET_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjectMaterialsResp> getProjBudgetMaterialDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<>(mwProjScheduleService.getProjBudgetMaterialDetails(projScheduleBaseLineGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_BUDGET_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjectPlantsResp> getProjBudgetPlantDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<>(mwProjScheduleService.getProjBudgetPlantDetails(projScheduleBaseLineGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_PROJ_BUDGET_COST_CODE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleCostCodeResp> getProjBudgetCostCodeDetails(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<>(mwProjScheduleService.getProjBudgetCostCodeDetails(projScheduleBaseLineGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_TANGIBLES, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleSOWResp> getTangibles(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<>(mwProjScheduleService.getTangibles(projScheduleBaseLineGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_SCHEDULE_OF_RATES, method = RequestMethod.POST)
    public ResponseEntity<ProjScheduleSOWResp> getScheduleOfRates(
            @RequestBody ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        return new ResponseEntity<>(mwProjScheduleService.getScheduleOfRates(projScheduleBaseLineGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_ACTUAL_ACTIVITY_SCHEDULE_FOR, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> getActualActivityScheduleFor(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		mwProjScheduleService.getActualActivityScheduleFor(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_ACTUAL_ACTIVITY_SCHEDULE_FOR_GANTT_CHART, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityGanttDataResp> getActualActivityScheduleForGanttChart(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityGanttDataResp>(
        		mwProjScheduleService.getActualActivityScheduleForGanttChart(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = ProjScheduleURLConstants.GET_MULTI_PROJ_BUDGET_MANPOWER_DETAILS)
    public ResponseEntity<String> getMultiProjBudgetManPowerDetails(
            @RequestBody List<Long> projIds) {
    	String resp = mwProjScheduleService.getMultiProjBudgetManPowerDetails(projIds);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @PostMapping(value = ProjScheduleURLConstants.GET_MULTI_PROJ_BUDGET_PLANT_DETAILS)
    public ResponseEntity<String> getMultiProjBudgetPlantDetails(
            @RequestBody List<Long> projIds) {
    	String resp = mwProjScheduleService.getMultiProjBudgetPlantDetails(projIds);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @PostMapping(value = ProjScheduleURLConstants.GET_MULTI_PROJ_BUDGET_SOW_DETAILS)
    public ResponseEntity<String> getMultiProjBudgetSOWDetails(
            @RequestBody List<Long> projIds) {
    	String resp = mwProjScheduleService.getMultiProjBudgetSOWDetails(projIds);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @PostMapping(value = ProjScheduleURLConstants.GET_MULTI_PROJ_MULTI_BUDGET_TYPE_DETAILS)
    public ResponseEntity<String> getMultiProjMultiBudgetTypeDetails(
            @RequestBody ProjScheduleMultiBudgetTypeReq projScheduleMultiBudgetTypeReq) {
    	String resp = mwProjScheduleService.getMultiProjMultiBudgetTypeDetails(projScheduleMultiBudgetTypeReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.PREPARE_RESOURCE_ASSIGNMENT_DATA_TABLE, method = RequestMethod.POST)
    public ResponseEntity<ScheduleActivityDataSetResp> prepareResourceAssignmentData(@RequestBody ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
        return new ResponseEntity<ScheduleActivityDataSetResp>(
        		mwProjScheduleService.prepareResourceAssignmentData(scheduleActivityDataSetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.SAVE_RESOURCE_CURVE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Void> saveResourceCurveMapping(@RequestBody ResourceCurveMappingReq resourceCurveMappingReq) {
    	mwProjScheduleService.saveResourceCurveMapping(resourceCurveMappingReq);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjScheduleURLConstants.GET_CALENDAR_SPECIAL_WORKING_NONWORKING_DAYS, method = RequestMethod.POST)
    public ResponseEntity<CalendarSpecialWorkingNonworkingDays> getCalendarSpecialWorkingNonworkingDays(@RequestBody Long projectId) {
        return new ResponseEntity<CalendarSpecialWorkingNonworkingDays>(
        		mwProjScheduleService.getCalendarSpecialWorkingNonworkingDays(projectId), HttpStatus.OK);
    }
}
