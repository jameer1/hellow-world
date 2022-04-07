package com.rjtech.mw.service.projschedule;

import java.util.List;
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

public interface MWProjScheduleService {

    ProjManPowerResp getProjBudgetManPowerDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjScheduleBaseLineResp getProjScheduleBaseLines(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjScheduleManPowerResp getProjScheduleManPowerDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjScheduleManPowerResp saveProjScheduleManPowerDetails(ProjScheduleManPowerSaveReq projScheduleManPowerSaveReq);

    ProjSchedulePlantResp getProjSchedulePlantDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjSchedulePlantResp saveProjSchedulePlantDetails(ProjSchedulePlantSaveReq projSchedulePlantSaveReq);

    ProjScheduleMaterialResp getProjScheduleMaterialDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjScheduleMaterialResp saveProjScheduleMaterialDetails(ProjScheduleMaterialSaveReq projScheduleMaterialSaveReq);

    ProjScheduleCostCodeResp getProjScheduleCostCodeDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjScheduleCostCodeResp saveProjScheduleCostCodeDetails(ProjScheduleCostCodeSaveReq projScheduleCostCodeSaveReq);

    ProjScheduleSOWResp getProjScheduleSOWDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);

    ProjScheduleSOWResp saveProjScheduleSOWDetails(ProjScheduleSOWSaveReq projScheduleSOWSaveReq);

    ProjScheduleBaseLineResp deleteProjScheduleBaseLines(ProjScheduleBaseLineDelReq projScheduleBaseLineDelReq);

    void saveAssignedBaseLine(ProjScheduleSaveAssignedBaseLineReq projScheduleSaveAssignedBaseLineReq);

    List<ProjScheduleSOWItemResp> getSowForReport(ProjScheduleSowForReportsGetReq projScheduleSowForReportsGetReq);
    
    ScheduleActivityDataSetResp parseScheduleActivityData(MultipartFile file, String scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp parseResourceAssignmentData(MultipartFile file, String scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp saveScheduleActivity(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp getScheduleActivity(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp getScheduleActivityDatasetList(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp saveScheduleActivityDatasets(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp getDateWiseForecastActualForResource(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ProjectMaterialsResp getProjBudgetMaterialDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);
    ProjectPlantsResp getProjBudgetPlantDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);
    ProjScheduleCostCodeResp getProjBudgetCostCodeDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);
    ProjScheduleSOWResp getTangibles(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);
    ProjScheduleSOWResp getScheduleOfRates(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq);
    ScheduleActivityDataSetResp getActualActivityScheduleFor(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityGanttDataResp getActualActivityScheduleForGanttChart(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    ScheduleActivityDataSetResp prepareResourceAssignmentData(ScheduleActivityDataSetReq scheduleActivityDataSetReq);
    CalendarSpecialWorkingNonworkingDays getCalendarSpecialWorkingNonworkingDays(Long projectId);
    void saveResourceCurveMapping(ResourceCurveMappingReq resourceCurveMappingReq);
    String getMultiProjBudgetManPowerDetails(List<Long> projIds);
    
    String getMultiProjBudgetPlantDetails(List<Long> projIds);
    
    String getMultiProjBudgetSOWDetails(List<Long> projIds);
    
    String getMultiProjMultiBudgetTypeDetails(ProjScheduleMultiBudgetTypeReq projScheduleMultiBudgetTypeReq);
}
