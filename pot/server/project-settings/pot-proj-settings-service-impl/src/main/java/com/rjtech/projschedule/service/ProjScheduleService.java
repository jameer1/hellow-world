package com.rjtech.projschedule.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

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

public interface ProjScheduleService {

    ProjManPowerResp getProjBudgetManPowerDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjectPlantsResp getProjBudgetPlantDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjectMaterialsResp getProjBudgetMaterialDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjScheduleCostCodeResp getProjBudgetCostCodeDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjScheduleSOWItemResp getProjBudgetSOWDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjScheduleBaseLineResp getProjScheduleBaseLines(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjScheduleManPowerResp getProjScheduleManPowerDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    void saveProjScheduleManPowerDetails(ProjScheduleManPowerSaveReq projScheduleManPowerSaveReq);

    ProjSchedulePlantResp getProjSchedulePlantDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    void saveProjSchedulePlantDetails(ProjSchedulePlantSaveReq projSchedulePlantSaveReq);

    ProjScheduleMaterialResp getProjScheduleMaterialDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    void saveProjScheduleMaterialDetails(ProjScheduleMaterialSaveReq projScheduleMaterialSaveReq);

    ProjScheduleCostCodeResp getProjScheduleCostCodeDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    void saveProjScheduleCostCodeDetails(ProjScheduleCostCodeSaveReq projScheduleCostCodeSaveReq);

    ProjScheduleSOWResp getProjScheduleSOWDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    void saveProjScheduleSOWDetails(ProjScheduleSOWSaveReq projScheduleSOWSaveReq);

    void deleteProjScheduleBaseLines(ProjScheduleBaseLineDelReq projScheduleBaseLineDelReq);

    void saveAssignedBaseLine(ProjScheduleSaveAssignedBaseLineReq projScheduleSaveAssignedBaseLineReq);

    List<ProjScheduleSOWItemResp> getProjBudgetSOWDetailsForReports(
            ProjScheduleSowForReportsGetReq projScheduleSowForReportsGetReq);

    Map<Long, ProjManPowerResp> getMultiProjBudgetManPowerDetails(List<Long> projIds);

    Map<Long, ProjectPlantsResp> getMultiProjBudgetPlantDetails(List<Long> projIds);

    Map<Long, ProjScheduleSOWItemResp> getMultiProjBudgetSOWDetails(List<Long> projIds);

    Map<Long, Map<String, Object>> getMultiProjMultiBudgetTypeDetails(
            ProjScheduleMultiBudgetTypeReq projScheduleMultiBudgetTypeReq);
    
    ScheduleActivityDataSetResp parseScheduleActivityData(MultipartFile file, ScheduleActivityDataSetReq scheduleActivityDataSetReq) throws IOException;
    ScheduleActivityDataSetResp parseResourceAssignmentData(MultipartFile file, ScheduleActivityDataSetReq scheduleActivityDataSetReq) throws IOException;
    ScheduleActivityDataSetResp saveScheduleActivity(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp saveResourceAssignment(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp getScheduleActivity(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp getScheduleActivityDatasetList(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp saveScheduleActivityDatasets(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp getDateWiseForecastActualForResource(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ProjScheduleSOWResp getTangibles(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);
    ProjScheduleSOWResp getScheduleOfRates(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);
    ScheduleActivityDataSetResp getActualActivityScheduleFor(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityGanttDataResp getActualActivityScheduleForGanttChart(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp prepareResourceAssignmentData(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    void saveResourceCurveMapping(ResourceCurveMappingReq resourceCurveMappingReq);
    CalendarSpecialWorkingNonworkingDays getCalendarSpecialWorkingNonworkingDays(Long projectId);
}
