package com.rjtech.projschedule.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.calendar.dto.CalRegularDaysTO;
import com.rjtech.calendar.model.GlobalCalRegularDaysEntity;
import com.rjtech.calendar.repository.GlobalCalRegularDaysRepository;
import com.rjtech.calendar.repository.GlobalCalSpecialDaysRepository;
import com.rjtech.calendar.service.handler.GlobalCalRegularDaysHandler;
import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.centrallib.dto.TangibleClassTO;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.model.TangibleClassificationEntity;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.repository.TangibleClassRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.CostActualHoursTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.proj.settings.common.service.ActualAmountService;
import com.rjtech.proj.settings.common.service.ActualHrsService;
import com.rjtech.projectlib.dto.ProjSORItemTO;
import com.rjtech.projectlib.dto.ProjSOWItemTO;
import com.rjtech.projectlib.dto.TotalActualTO;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.model.ProjSORItemEntity;
//import com.rjtech.projectlib.model.ProjSORItemEntityCopy;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSOEItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSORItemRepository;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSowProcRepository;
import com.rjtech.projectlib.repository.ProjSowTotalActualRepository;
import com.rjtech.projectlib.resp.SOWTotalActualQuantitiesResp;
import com.rjtech.projectlib.service.handler.ProjCostItemHandlerCopy;
import com.rjtech.projectlib.service.handler.ProjSOWItemHandlerCopy;
import com.rjtech.projschedule.dto.GanttChartDependency;
import com.rjtech.projschedule.dto.GanttChartProgress;
import com.rjtech.projschedule.dto.GanttChartRowTO;
import com.rjtech.projschedule.dto.GanttChartTaskTO;
import com.rjtech.projschedule.dto.ProjScheduleCostCodeTO;
import com.rjtech.projschedule.dto.ProjScheduleManPowerTO;
import com.rjtech.projschedule.dto.ProjScheduleMaterialTO;
import com.rjtech.projschedule.dto.ProjSchedulePlantTO;
import com.rjtech.projschedule.dto.ProjScheduleSOWTO;
import com.rjtech.projschedule.dto.ResourceAssignmentDataTO;
import com.rjtech.projschedule.dto.ResourceAssignmentDataValueTO;
import com.rjtech.projschedule.dto.ResourceCurveMappingTO;
import com.rjtech.projschedule.dto.ScheduleActivityDataSetTO;
import com.rjtech.projschedule.dto.ScheduleActivityDataTO;
import com.rjtech.projschedule.model.ProjScheduleAssignedBaseLineEntity;
import com.rjtech.projschedule.model.ProjScheduleBaseLineEntity;
import com.rjtech.projschedule.model.ProjScheduleCostCodeEntity;
import com.rjtech.projschedule.model.ProjScheduleManPowerEntity;
import com.rjtech.projschedule.model.ProjScheduleMaterialEntity;
import com.rjtech.projschedule.model.ProjSchedulePlantEntity;
import com.rjtech.projschedule.model.ProjScheduleSOWEntity;
import com.rjtech.projschedule.model.ResourceAssignmentDataEntity;
import com.rjtech.projschedule.model.ResourceCurveMappingEntity;
import com.rjtech.projschedule.model.ScheduleActivityDataEntity;
import com.rjtech.projschedule.model.ScheduleActivityDataPredecessorSuccessorEntity;
import com.rjtech.projschedule.model.ScheduleActivityDataSetEntity;
import com.rjtech.projschedule.repository.ProjScheduleAssignedBaseLineRepository;
import com.rjtech.projschedule.repository.ProjScheduleBaseLineRepository;
import com.rjtech.projschedule.repository.ProjScheduleCostCodeRepository;
import com.rjtech.projschedule.repository.ProjScheduleManPowerRepository;
import com.rjtech.projschedule.repository.ProjScheduleMaterialRepository;
import com.rjtech.projschedule.repository.ProjSchedulePlantRepository;
import com.rjtech.projschedule.repository.ProjScheduleSOWRepository;
import com.rjtech.projschedule.repository.ProjectPlantsRepository;
import com.rjtech.projschedule.repository.ResourceAssignmentDataRepository;
import com.rjtech.projschedule.repository.ResourceAssignmentDataValueRepository;
import com.rjtech.projschedule.repository.ResourceCurveMappingRepository;
import com.rjtech.projschedule.repository.ScheduleActivityDataPredecessorSuccessorRepository;
import com.rjtech.projschedule.repository.ScheduleActivityDataRepository;
import com.rjtech.projschedule.repository.ScheduleActivityDataSetRepository;
import com.rjtech.projschedule.repository.ScopeOfWorkRepository;
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
import com.rjtech.projschedule.req.ScheduleActivityDataSetReq;
import com.rjtech.projschedule.resp.CalendarSpecialWorkingNonworkingDays;
import com.rjtech.projschedule.resp.ProjScheduleBaseLineResp;
import com.rjtech.projschedule.resp.ProjScheduleCostCodeResp;
import com.rjtech.projschedule.resp.ProjScheduleManPowerResp;
import com.rjtech.projschedule.resp.ProjScheduleMaterialResp;
import com.rjtech.projschedule.resp.ProjSchedulePlantResp;
import com.rjtech.projschedule.resp.ProjScheduleSOWItemResp;
import com.rjtech.projschedule.resp.ProjScheduleSOWResp;
import com.rjtech.projschedule.resp.ScheduleActivityDataSetResp;
import com.rjtech.projschedule.resp.ScheduleActivityGanttDataResp;
import com.rjtech.projschedule.service.ProjScheduleService;
import com.rjtech.projschedule.service.handler.ProjScheduleBaseLineHandler;
import com.rjtech.projschedule.service.handler.ProjScheduleCostCodeHandler;
import com.rjtech.projschedule.service.handler.ProjScheduleManPowerHandler;
import com.rjtech.projschedule.service.handler.ProjScheduleMaterialHandler;
import com.rjtech.projschedule.service.handler.ProjSchedulePlantHandler;
import com.rjtech.projschedule.service.handler.ProjScheduleSOWHandler;
import com.rjtech.projschedule.service.handler.ResourceAssignmentDataHandler;
import com.rjtech.projschedule.service.handler.ResourceCurveMappingHandler;
import com.rjtech.projschedule.service.handler.ScheduleActivityDataHandler;
import com.rjtech.projschedule.service.handler.ScheduleActivityDataSetHandler;
import com.rjtech.projsettings.dto.ProjCostStmtDtlTO;
import com.rjtech.projsettings.dto.ProjManpowerTO;
import com.rjtech.projsettings.dto.ProjectMaterialDtlTO;
import com.rjtech.projsettings.dto.ProjectPlantsDtlTO;
import com.rjtech.projsettings.dto.ProjectReportsTO;
import com.rjtech.projsettings.model.ProjEstimateEntity;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.projsettings.model.ProjManpowerEntity;
import com.rjtech.projsettings.model.ProjectMaterialBudgetEntity;
import com.rjtech.projsettings.model.ProjectPlantsDtlEntity;
import com.rjtech.projsettings.model.ProjectReportsEntity;
import com.rjtech.projsettings.repository.ProjCostStatementsRepository;
import com.rjtech.projsettings.repository.ProjEstimateRepository;
import com.rjtech.projsettings.repository.ProjGeneralRepository;
import com.rjtech.projsettings.repository.ProjManpowerRepository;
import com.rjtech.projsettings.repository.ProjReportsRepository;
import com.rjtech.projsettings.repository.ProjectMaterialRepository;
//import com.rjtech.projsettings.repository.ProjectPlantsRepository;
import com.rjtech.projsettings.req.ProjCostStatementsGetReq;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.projsettings.req.ProjManpowerGetReq;
import com.rjtech.projsettings.req.ProjectMaterialGetReq;
import com.rjtech.projsettings.req.ProjectPlantsGetReq;
import com.rjtech.projsettings.resp.ProjCostStaementsResp;
import com.rjtech.projsettings.resp.ProjGeneralsResp;
import com.rjtech.projsettings.resp.ProjManPowerResp;
import com.rjtech.projsettings.resp.ProjectMaterialsResp;
import com.rjtech.projsettings.resp.ProjectPlantsResp;
import com.rjtech.projsettings.service.ProjSettingsService;
import com.rjtech.projsettings.service.handler.ProjManpowerHandler;
import com.rjtech.projsettings.service.handler.ProjReportsHandler;
import com.rjtech.projsettings.service.handler.ProjectMaterialBudgetHandler;
import com.rjtech.projsettings.service.handler.ProjectPlantsDtlHandler;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timemanagement.timesheet.repository.copy.TimeSheetEmpDtlRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.EmpWageWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.MaterialCostWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.MaterialStatusWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.PlantCostWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.PlantStatusWorkDairyRepositoryCopy;

@Service(value = "projScheduleService")
@RJSService(modulecode = "projScheduleService")
@Transactional
public class ProjScheduleServiceImpl implements ProjScheduleService {

    private static final Logger log = LoggerFactory.getLogger(ProjScheduleServiceImpl.class);
    private static final String[] ganttChartDatasetColors = {"#7db9e8", "#41B3A3", "#659DBD", "#BC986A", "#DAAD86", "#C38D9E", "#E8A87C", "#E27D60", "#8EE4AF", "#FFD64A"};
    
    @Autowired
    private ProjSowProcRepository projSowProcRepository;

    @Autowired
    private ProjSowTotalActualRepository totalActualProcRepository;

    @Autowired
    private ProjScheduleBaseLineRepository projScheduleBaseLineRepository;

    @Autowired
    private ProjScheduleManPowerRepository projScheduleManPowerRepository;

    @Autowired
    private ProjSchedulePlantRepository projSchedulePlantRepository;

    @Autowired
    private ProjScheduleMaterialRepository projScheduleMaterialRepository;

    @Autowired
    private ProjScheduleCostCodeRepository projScheduleCostCodeRepository;

    @Autowired
    private ProjScheduleSOWRepository projScheduleSOWRepository;

    @Autowired
    private ProjManpowerRepository projManpowerRepository;

    @Autowired
    private ProjectPlantsRepository projectPlantsRepository;

    @Autowired
    private ProjectMaterialRepository projectMaterialRepository;

    @Autowired
    private ProjSOWItemRepositoryCopy projSOWItemRepository;

    @Autowired
    private ProjGeneralRepository projGeneralRepository;

    @Autowired
    private GlobalCalRegularDaysRepository globalCalRegularDaysRepository;

    @Autowired
    private ProjReportsRepository projReportsRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private ProjScheduleAssignedBaseLineRepository projScheduleAssignedBaseLineRepository;

    @Autowired
    private ProjCostItemRepositoryCopy projCostItemRepository;

    @Autowired
    private ResourceCurveRepository resourceCurveRepository;

    @Autowired
    private EmpClassRepository empClassRepository;

    @Autowired
    private MaterialClassRepository materialClassRepository;

    @Autowired
    private PlantClassRepository plantClassRepository;

    @Autowired
    private ActualHrsService actualHrsServiceImpl;

    @Autowired
    private ActualAmountService actualAmountService;

    @Autowired
    private ProjCostStatementsRepository projCostStatementsRepository;

    @Autowired
    private TimeSheetEmpDtlRepositoryCopy timeSheetEmpDtlRepositoryCopy;

    @Autowired
    private EmpWageWorkDairyRepositoryCopy empWageWorkDairyRepositoryCopy;

    @Autowired
    private MaterialCostWorkDairyRepositoryCopy materialCostWorkDairyRepositoryCopy;

    @Autowired
    private PlantCostWorkDairyRepositoryCopy plantCostWorkDairyRepositoryCopy;

    @Autowired
    private GlobalCalSpecialDaysRepository globalCalSpecialDaysRepository;

    @Autowired
    private MaterialStatusWorkDairyRepositoryCopy materialStatusWorkDairyRepositoryCopy;

    @Autowired
    private PlantStatusWorkDairyRepositoryCopy plantStatusWorkDairyRepositoryCopy;

    @Autowired
    private ProjSowTotalActualRepository projSowTotalActualRepository;

    @Autowired
    private ProjEstimateRepository projEstimateRepository;
    
    @Autowired
    private EPSProjRepository ePSProjRepository;
    
    @Autowired
    private ProjSOEItemRepositoryCopy projSOEItemRepositoryCopy;
    
    @Autowired 
    private ScheduleActivityDataSetRepository scheduleActivityDataSetRepository;
    
    @Autowired
    private ScheduleActivityDataRepository scheduleActivityDataRepository;
    
    @Autowired
    private LoginRepository loginRepository;
    
    @Autowired
    private ResourceAssignmentDataRepository resourceAssignmentDataRepository;
    
    @Autowired
    private ScheduleActivityDataPredecessorSuccessorRepository scheduleActivityDataPredecessorSuccessorRepository;
    
    @Autowired
    private ProjSORItemRepository projSORItemRepository;
    
    @Autowired
    private TangibleClassRepository tangibleClassRepository;
    
    @Autowired
    private ResourceAssignmentDataValueRepository resourceAssignmentDataValueRepository;
    
    @Autowired
    private ScopeOfWorkRepository scopeOfWorkRepository;
    
    @Autowired
    private ProjSettingsService projSettingsService;
    
    @Autowired
    private ResourceCurveMappingRepository resourceCurveMappingRepository;

    @Override
    public Map<Long, ProjManPowerResp> getMultiProjBudgetManPowerDetails(List<Long> projIds) {
        Map<Long, ProjManPowerResp> resp = new HashMap<>();
        for (Long projId : projIds) {
            ProjScheduleBaseLineGetReq req = new ProjScheduleBaseLineGetReq();
            req.setProjId(projId);
            req.setStatus(1);
            resp.put(projId, getProjBudgetManPowerDetails(req));
        }
        return resp;
    }

    public ProjManPowerResp getProjBudgetManPowerDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ProjManPowerResp projManPowerResp = new ProjManPowerResp();
        if(projScheduleBaseLineGetReq.getProjId() != null) {
        List<ProjManpowerEntity> projManpowerEntities = projManpowerRepository
                .findManpowersByProject(projScheduleBaseLineGetReq.getProjId(), projScheduleBaseLineGetReq.getStatus());

        Long resourceCurveId = projGeneralRepository.getProjDefaultCurve(projScheduleBaseLineGetReq.getProjId());
        ProjEstimateEntity projEstimate = projEstimateRepository
                .findManpowerEstimate(projScheduleBaseLineGetReq.getProjId());
        ProjManpowerTO projManpowerTO = null;
        for (ProjManpowerEntity projManpowerEntity : projManpowerEntities) {
            projManpowerTO = ProjManpowerHandler.convertEntityToPOJO(projManpowerEntity);
            ResourceCurveMappingEntity resourceCurveMappingEntity = resourceCurveMappingRepository.findBy(projManpowerTO.getId(), ResourceAssignmentDataHandler.POT_EMPLOYEE);
    		if (resourceCurveMappingEntity == null)
    			projManpowerTO.setResourceCurveId(resourceCurveId);
    		else
    			projManpowerTO.setResourceCurveId(resourceCurveMappingEntity.getProjResourceCurveEntity().getId());
            if (projEstimate != null) {
                projManpowerTO.setEstimateType(projEstimate.getFormulaType());
            }
            projManpowerTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projManpowerEntity.getId()));
            if (projManpowerTO.getMinStartDateOfBaseline() == null)
            	projManpowerTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projManpowerEntity.getEmpClassMstrEntity().getId()));
            projManpowerTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projManpowerEntity.getId()));
            if (projManpowerTO.getMaxFinishDateOfBaseline() == null)
            	projManpowerTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projManpowerEntity.getEmpClassMstrEntity().getId()));
            projManPowerResp.getProjManpowerTOs().add(projManpowerTO);
        }

        projManPowerResp.setRegularHolidays(
                getCalendarRegularDays(projScheduleBaseLineGetReq.getProjId(), projScheduleBaseLineGetReq.getStatus()));

        List<Object[]> timesheetEmpHrs = timeSheetEmpDtlRepositoryCopy
                .getTimesheetEmpActualHrs(projScheduleBaseLineGetReq.getProjId());
        List<Object[]> workDairyEmpHrs = empWageWorkDairyRepositoryCopy
                .getManPowerActualHrs(projScheduleBaseLineGetReq.getProjId());

        List<LabelKeyTO> dateWiseActualQuantity = new ArrayList<>();
        Date startDate;
        Date endDate;
        int hrsIndex;
        for (Object[] timesheetHrs : timesheetEmpHrs) {
            startDate = (Date) timesheetHrs[1];
            endDate = (Date) timesheetHrs[2];
            hrsIndex = 3;
            while (startDate.before(endDate) || startDate.equals(endDate)) {
                dateWiseActualQuantity.add(getActualValuesLabelTo(timesheetHrs[0], startDate, timesheetHrs[hrsIndex]));
                hrsIndex++;
                startDate = Date.from(Instant.ofEpochMilli(startDate.getTime()).plus(1, ChronoUnit.DAYS));
            }
        }
        for (Object[] workDairyHrs : workDairyEmpHrs) {
            dateWiseActualQuantity
                    .add(getActualValuesLabelTo(workDairyHrs[0], (Date) workDairyHrs[1], workDairyHrs[2]));
        }
        projManPowerResp.setDateWiseActualQuantity(dateWiseActualQuantity);

        projManPowerResp.setActualWorkingDayMap(
                actualHrsServiceImpl.getManpowerActualHrs(projScheduleBaseLineGetReq.getProjId()));

        projManPowerResp.setCalNonWorkingDays(
                globalCalSpecialDaysRepository.findProjCalNonWorkingDays(projScheduleBaseLineGetReq.getProjId()));
        projManPowerResp.setCalSplWorkingDays(
                globalCalSpecialDaysRepository.findProjCalSpecialWorkingDays(projScheduleBaseLineGetReq.getProjId()));

        projManPowerResp.setProjReportsTo(getProjectReports(projScheduleBaseLineGetReq.getProjId()));
    	
    	}

        return projManPowerResp;
    }

    @Override
    public Map<Long, ProjectPlantsResp> getMultiProjBudgetPlantDetails(List<Long> projIds) {
        Map<Long, ProjectPlantsResp> resp = new HashMap<>();
        for (Long projId : projIds) {
            ProjScheduleBaseLineGetReq req = new ProjScheduleBaseLineGetReq();
            req.setProjId(projId);
            req.setStatus(1);
            resp.put(projId, getProjBudgetPlantDetails(req));
        }
        return resp;
    }

    public ProjectPlantsResp getProjBudgetPlantDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ProjectPlantsResp projectPlantsResp = new ProjectPlantsResp();
        List<ProjectPlantsDtlEntity> projectPlantsDtlEntities = projectPlantsRepository
                .findProjectPlants(projScheduleBaseLineGetReq.getProjId(), projScheduleBaseLineGetReq.getStatus());
        Long resourceCurveId = projGeneralRepository.getProjDefaultCurve(projScheduleBaseLineGetReq.getProjId());
        ProjectPlantsDtlTO projectPlantsDtlTO = null;
        for (ProjectPlantsDtlEntity projectPlantsDtlEntity : projectPlantsDtlEntities) {
            projectPlantsDtlTO = ProjectPlantsDtlHandler.convertEntityToPOJO(projectPlantsDtlEntity);
            ResourceCurveMappingEntity resourceCurveMappingEntity = resourceCurveMappingRepository.findBy(projectPlantsDtlTO.getId(), ResourceAssignmentDataHandler.POT_PLANT);
    		if (resourceCurveMappingEntity == null)
    			projectPlantsDtlTO.setResourceCurveId(resourceCurveId);
    		else
    			projectPlantsDtlTO.setResourceCurveId(resourceCurveMappingEntity.getProjResourceCurveEntity().getId());
            projectPlantsDtlTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projectPlantsDtlEntity.getPlantMstrEntity().getId()));
            if (projectPlantsDtlTO.getMinStartDateOfBaseline() == null)
            	projectPlantsDtlTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projectPlantsDtlEntity.getId()));
            projectPlantsDtlTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projectPlantsDtlEntity.getPlantMstrEntity().getId()));
            if (projectPlantsDtlTO.getMaxFinishDateOfBaseline() == null)
            	projectPlantsDtlTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projectPlantsDtlEntity.getId()));
            projectPlantsResp.getProjectPlantsDtlTOs().add(projectPlantsDtlTO);
        }

        projectPlantsResp.setRegularHolidays(
                getCalendarRegularDays(projScheduleBaseLineGetReq.getProjId(), projScheduleBaseLineGetReq.getStatus()));

        List<Object[]> materialQtyList = plantStatusWorkDairyRepositoryCopy
                .getMaterialActualQtyForSchedules(projScheduleBaseLineGetReq.getProjId());
        List<LabelKeyTO> materialActualQuantity = new ArrayList<>();

        for (Object[] materialQty : materialQtyList) {
            materialActualQuantity.add(getActualValuesLabelTo(materialQty[0], (Date) materialQty[1], materialQty[2]));
        }
        projectPlantsResp.setDateWiseActualQuantity(materialActualQuantity);

        projectPlantsResp
                .setActualWorkingDayMap(actualHrsServiceImpl.getPlantActualHrs(projScheduleBaseLineGetReq.getProjId()));
        projectPlantsResp.setCalNonWorkingDays(
                globalCalSpecialDaysRepository.findProjCalNonWorkingDays(projScheduleBaseLineGetReq.getProjId()));

        projectPlantsResp.setProjReportsTo(getProjectReports(projScheduleBaseLineGetReq.getProjId()));
        projectPlantsResp.setCalSplWorkingDays(
                globalCalSpecialDaysRepository.findProjCalSpecialWorkingDays(projScheduleBaseLineGetReq.getProjId()));

        return projectPlantsResp;
    }

    public ProjectMaterialsResp getProjBudgetMaterialDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ProjectMaterialsResp projectMaterialsResp = new ProjectMaterialsResp();
        List<ProjectMaterialBudgetEntity> projectMaterialBudgetEntities = projectMaterialRepository
                .findProjectMaterials(projScheduleBaseLineGetReq.getProjId(), projScheduleBaseLineGetReq.getStatus());
        Long resourceCurveId = projGeneralRepository.getProjDefaultCurve(projScheduleBaseLineGetReq.getProjId());
        ProjectMaterialDtlTO projectMaterialDtlTO = null;
        for (ProjectMaterialBudgetEntity projectMaterialBudgetEntity : projectMaterialBudgetEntities) {
            projectMaterialDtlTO = ProjectMaterialBudgetHandler.convertEntityToPOJO(projectMaterialBudgetEntity);
            ResourceCurveMappingEntity resourceCurveMappingEntity = resourceCurveMappingRepository.findBy(projectMaterialDtlTO.getId(), ResourceAssignmentDataHandler.POT_MATERIAL);
    		if (resourceCurveMappingEntity == null)
    			projectMaterialDtlTO.setResourceCurveId(resourceCurveId);
    		else
    			projectMaterialDtlTO.setResourceCurveId(resourceCurveMappingEntity.getProjResourceCurveEntity().getId());
            projectMaterialDtlTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projectMaterialBudgetEntity.getMaterialClassMstrEntity().getId()));
            if (projectMaterialDtlTO.getMinStartDateOfBaseline() == null)
            	projectMaterialDtlTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projectMaterialBudgetEntity.getId()));
            projectMaterialDtlTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projectMaterialBudgetEntity.getMaterialClassMstrEntity().getId()));
            if (projectMaterialDtlTO.getMaxFinishDateOfBaseline() == null)
            	projectMaterialDtlTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), projectMaterialBudgetEntity.getId()));
            projectMaterialsResp.getProjectMaterialDtlTOs().add(projectMaterialDtlTO);
        }

        projectMaterialsResp.setRegularHolidays(
                getCalendarRegularDays(projScheduleBaseLineGetReq.getProjId(), projScheduleBaseLineGetReq.getStatus()));

        List<Object[]> materialActualQuantity = materialStatusWorkDairyRepositoryCopy
                .getMaterialActualQtyForSchedules(projScheduleBaseLineGetReq.getProjId());
        log.info("Material actual Qty List {}", materialActualQuantity.size());
        List<LabelKeyTO> dateWiseActualQuantity = new ArrayList<>();
        for (Object[] matActualQty : materialActualQuantity) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId((Long) matActualQty[0]);
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_DATE, ((Date) matActualQty[1]).toString());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_HRS,
                    String.valueOf((Double) matActualQty[2]));
            log.info("String.valueOf(((BigDecimal) matActualQty[2]).doubleValue()) {}",
                    String.valueOf((Double) matActualQty[2]));
            dateWiseActualQuantity.add(labelKeyTO);
        }
        projectMaterialsResp.setDateWiseActualQuantity(dateWiseActualQuantity);
        Map<Long, LabelKeyTO> projActualHrs = actualHrsServiceImpl
                .getMaterialActualHrs(projScheduleBaseLineGetReq.getProjId());
        projectMaterialsResp.setActualWorkingDayMap(projActualHrs);
        projectMaterialsResp.setCalNonWorkingDays(
                globalCalSpecialDaysRepository.findProjCalNonWorkingDays(projScheduleBaseLineGetReq.getProjId()));

        projectMaterialsResp.setCalSplWorkingDays(
                globalCalSpecialDaysRepository.findProjCalSpecialWorkingDays(projScheduleBaseLineGetReq.getProjId()));

        projectMaterialsResp.setProjReportsTo(getProjectReports(projScheduleBaseLineGetReq.getProjId()));

        return projectMaterialsResp;
    }

    public ProjScheduleCostCodeResp getProjBudgetCostCodeDetails(
            ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {

        ProjScheduleCostCodeResp projScheduleCostCodeResp = new ProjScheduleCostCodeResp();
        if(projScheduleBaseLineGetReq.getProjId() != null) {
        Long projId = projScheduleBaseLineGetReq.getProjId();
        List<Object[]> projCostCodeScheduleList = projCostStatementsRepository.getProjScheduleCostCodes(projId,projScheduleBaseLineGetReq.getStatus());
        
        List<Long> resourceCurveId = projGeneralRepository.getProjDefaultCurveId(projId);
        for(Long resourceId: resourceCurveId) {
        ProjScheduleCostCodeTO projScheduleCostCodeTO = null;
        BigDecimal actualBudget;
        BigDecimal revisedBudget;
        BigDecimal estimateToComplete;
        BigDecimal estimationToComplete;
        for (Object[] array : projCostCodeScheduleList) {
            projScheduleCostCodeTO = new ProjScheduleCostCodeTO();
            projScheduleCostCodeTO.setCostCodeId((Long) array[0]);
            projScheduleCostCodeTO.setStartDate((String) array[1]);
            projScheduleCostCodeTO.setFinishDate((String) array[2]);
            actualBudget = (BigDecimal) array[3];
            ProjCostItemEntity cost = projCostItemRepository.findOne(projScheduleCostCodeTO.getCostCodeId());
            projScheduleCostCodeTO.setProjCostItemTO(ProjCostItemHandlerCopy.populateProjCostITems(cost, false));
            if (actualBudget != null) {
                projScheduleCostCodeTO.setOriginalQty(actualBudget.setScale(2, RoundingMode.CEILING));
            }
            revisedBudget = (BigDecimal) array[4];
            if (revisedBudget != null) {
                projScheduleCostCodeTO.setRevisedQty(revisedBudget.setScale(2, RoundingMode.CEILING));
            }
            estimateToComplete = (BigDecimal) array[5];
            if (estimateToComplete != null) {
                projScheduleCostCodeTO.setEstimateComplete(estimateToComplete.setScale(2, RoundingMode.CEILING));
            }
            estimationToComplete = (BigDecimal) array[6];
            if (estimationToComplete != null) {
                projScheduleCostCodeTO.setEstimateCompletion(estimationToComplete.setScale(2, RoundingMode.CEILING));
            }
            ResourceCurveMappingEntity resourceCurveMappingEntity = resourceCurveMappingRepository.findBy(projScheduleCostCodeTO.getProjCostItemTO().getId(), ResourceAssignmentDataHandler.POT_COST);
    		if (resourceCurveMappingEntity == null)
    			projScheduleCostCodeTO.setResourceCurveId(resourceId);
    		else
    			projScheduleCostCodeTO.setResourceCurveId(resourceCurveMappingEntity.getProjResourceCurveEntity().getId());            
            projScheduleCostCodeTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projId, cost.getCostMstrEntity().getId()));
            if (projScheduleCostCodeTO.getMinStartDateOfBaseline() == null)
            	projScheduleCostCodeTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projId, cost.getId()));
            projScheduleCostCodeTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projId, cost.getCostMstrEntity().getId()));
            if (projScheduleCostCodeTO.getMaxFinishDateOfBaseline() == null)
            	projScheduleCostCodeTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projId, cost.getId()));
            projScheduleCostCodeResp.getProjScheduleCostCodeTOs().add(projScheduleCostCodeTO);

        }
        }

        projScheduleCostCodeResp
                .setRegularHolidays(getCalendarRegularDays(projId, projScheduleBaseLineGetReq.getStatus()));

        projScheduleCostCodeResp.setDateWiseActualQuantity(getCostCodeActualQuantities(projId));

        projScheduleCostCodeResp.setActualWorkingDayMap(actualAmountService.getCostStmt(projId));
        projScheduleCostCodeResp.setCalNonWorkingDays(globalCalSpecialDaysRepository.findProjCalNonWorkingDays(projId));
        projScheduleCostCodeResp
                .setCalSplWorkingDays(globalCalSpecialDaysRepository.findProjCalSpecialWorkingDays(projId));
        projScheduleCostCodeResp.setProjReportsTo(getProjectReports(projId));
        }
        return projScheduleCostCodeResp;

    }

    @Override
    public Map<Long, ProjScheduleSOWItemResp> getMultiProjBudgetSOWDetails(List<Long> projIds) {
        Map<Long, ProjScheduleSOWItemResp> resp = new HashMap<>();
        for (Long projId : projIds) {
            ProjScheduleBaseLineGetReq req = new ProjScheduleBaseLineGetReq();
            req.setProjId(projId);
            req.setStatus(1);
            resp.put(projId, getProjBudgetSOWDetails(req));
        }
        return resp;
    }

    public ProjScheduleSOWItemResp getProjBudgetSOWDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        ProjScheduleSOWItemResp projSOWItemResp = new ProjScheduleSOWItemResp();
        List<ProjSOWItemEntity> projSOWItemEntities = projSOWItemRepository
                .findSOWItems(projScheduleBaseLineGetReq.getProjId(), projScheduleBaseLineGetReq.getStatus());

        Long resourceCurveId = projGeneralRepository.getProjDefaultCurve(projScheduleBaseLineGetReq.getProjId());
        ProjSOWItemTO projSOWItemTO = null;
        Map<Long, TotalActualTO> actlualQly = new HashMap<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjSOWItemEntity projSOWItemEntity : projSOWItemEntities) {
            projSOWItemTO = ProjSOWItemHandlerCopy.convertEntityToPOJO(projSOWItemEntity);
            projSOWItemTO.setResourceCurveId(resourceCurveId);
            projSOWItemResp.getProjSOWItemTOs().add(projSOWItemTO);

            Long projId = projScheduleBaseLineGetReq.getProjId();
            // Set work dairy min date to respective sow id
            List<Object[]> sowdates = projSowTotalActualRepository.sowWorkDairyMinDate(projId);
            Map<Long, Date> minDates = new HashMap<>();
            for (Object[] dates : sowdates) {
                minDates.put((Long) dates[0], (Date) dates[1]);
            }

            for (ProjSOWItemTO projSOWItemTOs : projSOWItemResp.getProjSOWItemTOs()) {
                if (CommonUtil.objectNotNull(projSOWItemTOs.getId())
                        && CommonUtil.objectNotNull(minDates.get(projSOWItemTOs.getId()))) {
                    projSOWItemTOs
                            .setActualStartDate(CommonUtil.convertDateToString(minDates.get(projSOWItemTOs.getId())));
                }

            }
            // get revisedQty sow
            SOWTotalActualQuantitiesResp totalActualQuantitiesResp = new SOWTotalActualQuantitiesResp();
            totalActualQuantitiesResp.setActualRevisedMap(totalActualProcRepository.findTotalActualQuantities(projId));
            actlualQly = totalActualQuantitiesResp.getActualRevisedMap();

            for (ProjSOWItemTO projSOWItemTOs : projSOWItemResp.getProjSOWItemTOs()) {
                if (CommonUtil.objectNotNull(projSOWItemTOs.getId())
                        && CommonUtil.objectNotNull(actlualQly.get(projSOWItemTOs.getId()))) {
                    TotalActualTO totalActualTO = actlualQly.get(projSOWItemTOs.getId());
                    projSOWItemTOs.setActualQty(BigDecimal.valueOf(totalActualTO.getActualQuantity()));
                }
            }

        }

        List<Object[]> sowHrs = projSowTotalActualRepository
                .getSowActualHrsForSchedules(projScheduleBaseLineGetReq.getProjId());
        List<LabelKeyTO> sowActualhrs = new ArrayList<>();
        for (Object[] obj : sowHrs) {
            sowActualhrs.add(getActualValuesLabelTo(obj[0], (Date) obj[1], obj[2]));
        }
        projSOWItemResp.setDateWiseActualQuantity(sowActualhrs);
        projSOWItemResp.setActualWorkingDayMap(actlualQly);
        projSOWItemResp.setCalNonWorkingDays(
                globalCalSpecialDaysRepository.findProjCalNonWorkingDays(projScheduleBaseLineGetReq.getProjId()));
        projSOWItemResp.setCalSplWorkingDays(
                globalCalSpecialDaysRepository.findProjCalSpecialWorkingDays(projScheduleBaseLineGetReq.getProjId()));

        projSOWItemResp.setProjReportsTo(getProjectReports(projScheduleBaseLineGetReq.getProjId()));
        projSOWItemResp.setRegularHolidays(
                getCalendarRegularDays(projScheduleBaseLineGetReq.getProjId(), projScheduleBaseLineGetReq.getStatus()));

        return projSOWItemResp;
    }

    public ProjScheduleBaseLineResp getProjScheduleBaseLines(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        List<ProjScheduleBaseLineEntity> projScheduleBaseLineEntities = projScheduleBaseLineRepository
                .findProjScheduleBaselines(projScheduleBaseLineGetReq.getProjId(),
                        projScheduleBaseLineGetReq.getStatus(), projScheduleBaseLineGetReq.getScheduleItemType());
        ProjScheduleBaseLineResp projScheduleBaseLineResp = new ProjScheduleBaseLineResp();
        for (ProjScheduleBaseLineEntity projScheduleBaseLineEntity : projScheduleBaseLineEntities) {
            projScheduleBaseLineResp.getProjScheduleBaseLineTOs()
                    .add(ProjScheduleBaseLineHandler.convertEntityToPOJO(projScheduleBaseLineEntity));
        }
        return projScheduleBaseLineResp;
    }

    public ProjScheduleManPowerResp getProjScheduleManPowerDetails(
            ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        List<ProjScheduleManPowerEntity> projScheduleManPowerEntities = projScheduleManPowerRepository
                .findProjScheduleManPowerDetails(projScheduleBaseLineGetReq.getBaseLineId(),
                        projScheduleBaseLineGetReq.getStatus());
        ProjScheduleManPowerResp projScheduleManPowerResp = new ProjScheduleManPowerResp();
        for (ProjScheduleManPowerEntity projScheduleManPowerEntity : projScheduleManPowerEntities) {
            projScheduleManPowerResp.getProjScheduleManPowerTOs()
                    .add(ProjScheduleManPowerHandler.convertEntityToPOJO(projScheduleManPowerEntity));
        }
        return projScheduleManPowerResp;

    }

    public void saveProjScheduleManPowerDetails(ProjScheduleManPowerSaveReq projScheduleManPowerSaveReq) {
        List<ProjScheduleBaseLineEntity> projScheduleBaseLineEntities = new ArrayList<>();
        List<ProjScheduleManPowerEntity> projScheduleManPowerEntities = new ArrayList<>();
        ProjScheduleManPowerEntity projScheduleManPowerEntity = null;
        ProjScheduleBaseLineEntity projScheduleBaseLineEntity = null;
        projScheduleBaseLineEntity = ProjScheduleBaseLineHandler
                .convertPOJOToEntity(projScheduleManPowerSaveReq.getProjScheduleBaseLineTO(), epsProjRepository);
        projScheduleBaseLineEntities.add(projScheduleBaseLineEntity);
        for (ProjScheduleManPowerTO projScheduleManPowerTO : projScheduleManPowerSaveReq.getProjScheduleManPowerTOs()) {
            projScheduleManPowerEntity = ProjScheduleManPowerHandler.convertPOJOToEntity(projScheduleManPowerTO,
                    empClassRepository, projScheduleBaseLineRepository, resourceCurveRepository);
            projScheduleManPowerEntity.setProjScheduleBaseLineEntity(projScheduleBaseLineEntity);
            projScheduleManPowerEntities.add(projScheduleManPowerEntity);
        }
        projScheduleBaseLineRepository.save(projScheduleBaseLineEntities);
        projScheduleManPowerRepository.save(projScheduleManPowerEntities);
    }

    public ProjSchedulePlantResp getProjSchedulePlantDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        List<ProjSchedulePlantEntity> projSchedulePlantEntities = projSchedulePlantRepository
                .findProjSchedulePlantDetails(projScheduleBaseLineGetReq.getBaseLineId(),
                        projScheduleBaseLineGetReq.getStatus());
        ProjSchedulePlantResp projSchedulePlantResp = new ProjSchedulePlantResp();
        for (ProjSchedulePlantEntity projSchedulePlantEntity : projSchedulePlantEntities) {
            projSchedulePlantResp.getProjSchedulePlantTOs()
                    .add(ProjSchedulePlantHandler.convertEntityToPOJO(projSchedulePlantEntity));
        }
        return projSchedulePlantResp;
    }

    public void saveProjSchedulePlantDetails(ProjSchedulePlantSaveReq projSchedulePlantSaveReq) {
        ProjScheduleBaseLineEntity projScheduleBaseLineEntity = null;
        ProjSchedulePlantEntity projSchedulePlantEntity = null;
        projScheduleBaseLineEntity = ProjScheduleBaseLineHandler
                .convertPOJOToEntity(projSchedulePlantSaveReq.getProjScheduleBaseLineTO(), epsProjRepository);
        projScheduleBaseLineRepository.save(projScheduleBaseLineEntity);

        List<ProjSchedulePlantEntity> projSchedulePlantEntities = new ArrayList<>();
        for (ProjSchedulePlantTO projSchedulePlantTO : projSchedulePlantSaveReq.getProjSchedulePlantTOs()) {
            projSchedulePlantEntity = ProjSchedulePlantHandler.convertPOJOToEntity(projSchedulePlantTO,
                    plantClassRepository, projScheduleBaseLineRepository, resourceCurveRepository);
            projSchedulePlantEntity.setProjScheduleBaseLineEntity(projScheduleBaseLineEntity);
            projSchedulePlantEntities.add(projSchedulePlantEntity);

        }
        projSchedulePlantRepository.save(projSchedulePlantEntities);
    }

    public ProjScheduleMaterialResp getProjScheduleMaterialDetails(
            ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        List<ProjScheduleMaterialEntity> projScheduleMaterialEntities = projScheduleMaterialRepository
                .findProjScheduleMaterialDetails(projScheduleBaseLineGetReq.getBaseLineId(),
                        projScheduleBaseLineGetReq.getStatus());
        ProjScheduleMaterialResp projScheduleMaterialResp = new ProjScheduleMaterialResp();
        for (ProjScheduleMaterialEntity projScheduleMaterialEntity : projScheduleMaterialEntities) {
            projScheduleMaterialResp.getProjScheduleMaterialTOs()
                    .add(ProjScheduleMaterialHandler.convertEntityToPOJO(projScheduleMaterialEntity));
        }
        return projScheduleMaterialResp;
    }

    public void saveProjScheduleMaterialDetails(ProjScheduleMaterialSaveReq projScheduleMaterialSaveReq) {

        ProjScheduleBaseLineEntity projScheduleBaseLineEntity = null;
        ProjScheduleMaterialEntity projScheduleMaterialEntity = null;
        projScheduleBaseLineEntity = ProjScheduleBaseLineHandler
                .convertPOJOToEntity(projScheduleMaterialSaveReq.getProjScheduleBaseLineTO(), epsProjRepository);
        projScheduleBaseLineRepository.save(projScheduleBaseLineEntity);

        List<ProjScheduleMaterialEntity> projScheduleMaterialEntities = new ArrayList<>();
        for (ProjScheduleMaterialTO projScheduleMaterialTO : projScheduleMaterialSaveReq.getProjScheduleMaterialTOs()) {
            projScheduleMaterialEntity = ProjScheduleMaterialHandler.convertPOJOToEntity(projScheduleMaterialTO,
                    materialClassRepository, projScheduleBaseLineRepository, resourceCurveRepository);
            projScheduleMaterialEntity.setProjScheduleBaseLineEntity(projScheduleBaseLineEntity);
            projScheduleMaterialEntities.add(projScheduleMaterialEntity);

        }
        projScheduleMaterialRepository.save(projScheduleMaterialEntities);
    }

    public ProjScheduleCostCodeResp getProjScheduleCostCodeDetails(
            ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        List<ProjScheduleCostCodeEntity> projScheduleCostCodeEntities = projScheduleCostCodeRepository
                .findProjScheduleCostCodeDetails(projScheduleBaseLineGetReq.getBaseLineId(),
                        projScheduleBaseLineGetReq.getStatus());
        ProjScheduleCostCodeResp projScheduleCostCodeResp = new ProjScheduleCostCodeResp();
        for (ProjScheduleCostCodeEntity projScheduleCostCodeEntity : projScheduleCostCodeEntities) {
            projScheduleCostCodeResp.getProjScheduleCostCodeTOs()
                    .add(ProjScheduleCostCodeHandler.convertEntityToPOJO(projScheduleCostCodeEntity));
        }
        return projScheduleCostCodeResp;
    }

    public void saveProjScheduleCostCodeDetails(ProjScheduleCostCodeSaveReq projScheduleCostCodeSaveReq) {
        ProjScheduleBaseLineEntity projScheduleBaseLineEntity = null;
        ProjScheduleCostCodeEntity projScheduleCostCodeEntity = null;
        projScheduleBaseLineEntity = ProjScheduleBaseLineHandler
                .convertPOJOToEntity(projScheduleCostCodeSaveReq.getProjScheduleBaseLineTO(), epsProjRepository);
        projScheduleBaseLineRepository.save(projScheduleBaseLineEntity);

        List<ProjScheduleCostCodeEntity> projScheduleCostCodeEntities = new ArrayList<>();
        for (ProjScheduleCostCodeTO projScheduleCostCodeTO : projScheduleCostCodeSaveReq.getProjScheduleCostCodeTOs()) {
            projScheduleCostCodeEntity = ProjScheduleCostCodeHandler.convertPOJOToEntity(projScheduleCostCodeTO,
                    projCostItemRepository, projScheduleBaseLineRepository, resourceCurveRepository);
            projScheduleCostCodeEntity.setProjScheduleBaseLineEntity(projScheduleBaseLineEntity);
            projScheduleCostCodeEntities.add(projScheduleCostCodeEntity);
        }
        projScheduleCostCodeRepository.save(projScheduleCostCodeEntities);

    }

    public ProjScheduleSOWResp getProjScheduleSOWDetails(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
        List<ProjScheduleSOWEntity> projScheduleSOWEntities = projScheduleSOWRepository.findProjScheduleSOWDetails(
                projScheduleBaseLineGetReq.getBaseLineId(), projScheduleBaseLineGetReq.getStatus());
        ProjScheduleSOWResp projScheduleSOWResp = new ProjScheduleSOWResp();
        for (ProjScheduleSOWEntity projScheduleSOWEntity : projScheduleSOWEntities) {
            projScheduleSOWResp.getProjScheduleSOWTOs()
                    .add(ProjScheduleSOWHandler.convertEntityToPOJO(projScheduleSOWEntity));
        }
        return projScheduleSOWResp;
    }

    public void saveProjScheduleSOWDetails(ProjScheduleSOWSaveReq projScheduleSOWSaveReq) {
        ProjScheduleBaseLineEntity projScheduleBaseLineEntity = null;
        ProjScheduleSOWEntity projScheduleSOWEntity = null;
        projScheduleBaseLineEntity = ProjScheduleBaseLineHandler
                .convertPOJOToEntity(projScheduleSOWSaveReq.getProjScheduleBaseLineTO(), epsProjRepository);
        projScheduleBaseLineRepository.save(projScheduleBaseLineEntity);

        List<ProjScheduleSOWEntity> projScheduleSOWEntities = new ArrayList<>();
        for (ProjScheduleSOWTO projScheduleSOWTO : projScheduleSOWSaveReq.getProjScheduleSOWTOs()) {
            projScheduleSOWEntity = ProjScheduleSOWHandler.convertPOJOToEntity(projScheduleSOWTO, projSOWItemRepository,
                    projScheduleBaseLineRepository, resourceCurveRepository);
            projScheduleSOWEntity.setProjScheduleBaseLineEntity(projScheduleBaseLineEntity);
            projScheduleSOWEntities.add(projScheduleSOWEntity);
        }
        projScheduleSOWRepository.save(projScheduleSOWEntities);

    }

    public void deleteProjScheduleBaseLines(ProjScheduleBaseLineDelReq projScheduleBaseLineDelReq) {
        List<ProjScheduleBaseLineEntity> projScheduleBaseLineEntities = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<ProjScheduleBaseLineEntity> scheduleBaseLineEntities = projScheduleBaseLineRepository
                .findAll(projScheduleBaseLineDelReq.getBaseLineIds());
        for (ProjScheduleBaseLineEntity projScheduleBaseLineEntity : scheduleBaseLineEntities) {
            if (CommonUtil.objectNotNull(projScheduleBaseLineEntity)) {
                projScheduleBaseLineEntity.setStatus(projScheduleBaseLineDelReq.getStatus());
                projScheduleBaseLineEntities.add(projScheduleBaseLineEntity);
            }
        }
        projScheduleBaseLineRepository.save(projScheduleBaseLineEntities);
    }

    public void saveAssignedBaseLine(ProjScheduleSaveAssignedBaseLineReq projScheduleSaveAssignedBaseLineReq) {

        ProjScheduleBaseLineEntity projScheduleBaseLineEntity = projScheduleBaseLineRepository
                .findOne(projScheduleSaveAssignedBaseLineReq.getSelectedBaseLineId());

        ProjScheduleAssignedBaseLineEntity projScheduleAssignedBaseLineEntity = null;

        projScheduleAssignedBaseLineEntity = projScheduleAssignedBaseLineRepository
                .findByProjectEntityIdAndBaseLineEntityId(projScheduleSaveAssignedBaseLineReq.getProjId(),
                        projScheduleSaveAssignedBaseLineReq.getSelectedBaseLineId());

        if (projScheduleAssignedBaseLineEntity == null) {
            projScheduleAssignedBaseLineEntity = new ProjScheduleAssignedBaseLineEntity();
        }
        projScheduleAssignedBaseLineEntity.setBaseLineEntity(projScheduleBaseLineEntity);
        projScheduleAssignedBaseLineEntity
                .setProjectEntity(epsProjRepository.findOne(projScheduleSaveAssignedBaseLineReq.getProjId()));
        projScheduleAssignedBaseLineRepository.save(projScheduleAssignedBaseLineEntity);

    }

    private CalRegularDaysTO getCalendarRegularDays(long projId, int status) {
        List<ProjGeneralMstrEntity> projGeneralMstrEntity = projGeneralRepository.findProjGenerals(projId, status);
        ProjGeneralMstrEntity projGeneralEntity = projGeneralMstrEntity.get(0);
        if (projGeneralEntity.getGlobalCalEntity() != null) {
            log.info("Global Calendar {}  {}", projGeneralEntity.getGlobalCalEntity().getId(),
                    projGeneralEntity.getStatus());
            List<GlobalCalRegularDaysEntity> globalCalRegDays = globalCalRegularDaysRepository.findGlobalCalRegularDays(
                    projGeneralEntity.getGlobalCalEntity().getId(), projGeneralEntity.getStatus());
            if (!globalCalRegDays.isEmpty()) {
                return GlobalCalRegularDaysHandler.getRegularHolidays(globalCalRegDays.get(0));
            }
        } else if (projGeneralEntity.getGlobalCalEntity() != null) {
            List<GlobalCalRegularDaysEntity> projCalRegDays = globalCalRegularDaysRepository.findProjCalRegularDays(
                    projGeneralEntity.getGlobalCalEntity().getId(), projGeneralEntity.getStatus());
            if (!projCalRegDays.isEmpty()) {
                return GlobalCalRegularDaysHandler.getRegularHolidays(projCalRegDays.get(0));
            }
        }
        return null;
    }

    public List<ProjScheduleSOWItemResp> getProjBudgetSOWDetailsForReports(
            ProjScheduleSowForReportsGetReq projScheduleSowForReportsGetReq) {

        final String dateFormatString = "yyyy-MM-dd";
        // store values in map and get them using project id as key
        Map<Long, Map<Long, Date>> minDatesMap = new HashMap<>();
        Map<Long, Map<Long, TotalActualTO>> totalActualQuantityMap = new HashMap<>();
        Map<Long, Long> defaultCurveIdMap = new HashMap<>();
        Map<Long, List<LabelKeyTO>> progressActualHrsMap = new HashMap<>();
        Map<Long, List<String>> calNonWorkingDaysMap = new HashMap<>();
        Map<Long, List<ProjectReportsEntity>> projReportsMap = new HashMap<>();
        Map<Long, List<String>> calSplWorkingDaysMap = new HashMap<>();
        Map<Long, CalRegularDaysTO> regularHolidaysMap = new HashMap<>();

        int count = 0;
        Date minStartDate = null;
        Date maxEndDate = null;

        Map<Long, ProjScheduleSOWItemResp> finalReponseMap = new HashMap<>();

        List<ProjSOWItemTO> projSOWItems = projSowProcRepository.getProjSowForReport(
                StringUtils.join(projScheduleSowForReportsGetReq.getProjIds(), ","),
                StringUtils.join(projScheduleSowForReportsGetReq.getSowIds(), ","));

        for (ProjSOWItemTO projSOWItemTO : projSOWItems) {

            Map<Long, TotalActualTO> actlualQly = null;
            Long projId = projSOWItemTO.getProjId();

            ProjScheduleSOWItemResp projSOWItemResp = null;
            if (finalReponseMap.containsKey(projId)) {
                projSOWItemResp = finalReponseMap.get(projId);
            } else {
                projSOWItemResp = new ProjScheduleSOWItemResp();
                finalReponseMap.put(projId, projSOWItemResp);
            }

            Long resourceCurveId = null;
            if (defaultCurveIdMap.containsKey(projId)) {
                resourceCurveId = defaultCurveIdMap.get(projId);
            } else {
                resourceCurveId = projGeneralRepository.getProjDefaultCurve(projId);
                defaultCurveIdMap.put(projId, resourceCurveId);
            }
            projSOWItemTO.setResourceCurveId(resourceCurveId);
            projSOWItemResp.getProjSOWItemTOs().add(projSOWItemTO);

            // Set work dairy min date to respective sow id
            Map<Long, Date> minDates = null;
            if (minDatesMap.containsKey(projId)) {
                minDates = minDatesMap.get(projId);
            } else {
                List<Object[]> sowdates = projSowTotalActualRepository.sowWorkDairyMinDate(projId);
                minDates = new HashMap<>();
                for (Object[] dates : sowdates) {
                    minDates.put((Long) dates[0], (Date) dates[1]);
                }
                minDatesMap.put(projId, minDates);
            }

            for (ProjSOWItemTO projSOWItemTOs : projSOWItemResp.getProjSOWItemTOs()) {
                if (CommonUtil.objectNotNull(projSOWItemTOs.getId())
                        && CommonUtil.objectNotNull(minDates.get(projSOWItemTOs.getId()))) {
                    projSOWItemTOs
                            .setActualStartDate(CommonUtil.convertDateToString(minDates.get(projSOWItemTOs.getId())));
                }

            }
            // get revisedQty sow
            SOWTotalActualQuantitiesResp totalActualQuantitiesResp = new SOWTotalActualQuantitiesResp();

            Map<Long, TotalActualTO> totalActualQuantities = null;
            if (totalActualQuantityMap.containsKey(projId)) {
                totalActualQuantities = totalActualQuantityMap.get(projId);
            } else {
                totalActualQuantities = totalActualProcRepository.findTotalActualQuantities(projId);
                totalActualQuantityMap.put(projId, totalActualQuantities);
            }
            totalActualQuantitiesResp.setActualRevisedMap(totalActualQuantities);

            actlualQly = totalActualQuantitiesResp.getActualRevisedMap();

            for (ProjSOWItemTO projSOWItemTOs : projSOWItemResp.getProjSOWItemTOs()) {
                if (CommonUtil.objectNotNull(projSOWItemTOs.getId())
                        && CommonUtil.objectNotNull(actlualQly.get(projSOWItemTOs.getId()))) {
                    TotalActualTO totalActualTO = actlualQly.get(projSOWItemTOs.getId());
                    projSOWItemTOs.setActualQty(BigDecimal.valueOf(totalActualTO.getActualQuantity()));
                }
            }

            Date sowStartDate = null;
            Date sowFinishDate = null;
            try {
                sowStartDate = AppUtils.getDate(projSOWItemTO.getStartDate(), dateFormatString);
                sowFinishDate = AppUtils.getDate(projSOWItemTO.getFinishDate(), dateFormatString);
            } catch (ParseException e) {
                log.error("Exception while parsin dates: ", e);
            }

            if (count == 0) {
                minStartDate = sowStartDate;
                maxEndDate = sowFinishDate;
            }
            if (sowStartDate != null && sowStartDate.before(minStartDate)) {
                minStartDate = sowStartDate;
            }
            if (sowFinishDate != null && sowFinishDate.after(maxEndDate)) {
                maxEndDate = sowFinishDate;
            }
            count++;

            List<LabelKeyTO> progressActualHrs = null;
            if (progressActualHrsMap.containsKey(projId)) {
                progressActualHrs = progressActualHrsMap.get(projId);
            } else {
                List<Object[]> sowHrs = projSowTotalActualRepository.getSowActualHrsForSchedules(projId);
                progressActualHrs = new ArrayList<>();
                for (Object[] obj : sowHrs) {
                    progressActualHrs.add(getActualValuesLabelTo(obj[0], (Date) obj[2], obj[3]));
                }
                progressActualHrsMap.put(projId, progressActualHrs);
            }
            projSOWItemResp.setDateWiseActualQuantity(progressActualHrs);
            projSOWItemResp.setActualWorkingDayMap(actlualQly);

            List<String> calNonWorkingDays = null;
            if (calNonWorkingDaysMap.containsKey(projId)) {
                calNonWorkingDays = calNonWorkingDaysMap.get(projId);
            } else {
                calNonWorkingDays = globalCalSpecialDaysRepository.findProjCalNonWorkingDays(projId);
                calNonWorkingDaysMap.put(projId, calNonWorkingDays);
            }
            projSOWItemResp.setCalNonWorkingDays(calNonWorkingDays);

            List<String> calSplWorkingDays = null;
            if (calSplWorkingDaysMap.containsKey(projId)) {
                calSplWorkingDays = calSplWorkingDaysMap.get(projId);
            } else {
                calSplWorkingDays = globalCalSpecialDaysRepository.findProjCalSpecialWorkingDays(projId);
                calSplWorkingDaysMap.put(projId, calNonWorkingDays);
            }
            projSOWItemResp.setCalSplWorkingDays(calSplWorkingDays);

            List<ProjectReportsEntity> projReports = null;
            if (projReportsMap.containsKey(projId)) {
                projReports = projReportsMap.get(projId);
            } else {
                projReports = projReportsRepository.findProjReports(projId, 1);
                projReportsMap.put(projId, projReports);
            }
            projSOWItemResp.setProjReportsTo(ProjReportsHandler.convertEntityToPOJO(projReports.get(0)));

            CalRegularDaysTO regularHolidays = null;
            if (regularHolidaysMap.containsKey(projId)) {
                regularHolidays = regularHolidaysMap.get(projId);
            } else {
                regularHolidays = getCalendarRegularDays(projId, StatusCodes.ACTIVE.getValue());
                regularHolidaysMap.put(projId, regularHolidays);
            }
            projSOWItemResp.setRegularHolidays(regularHolidays);

        }

        List<ProjScheduleSOWItemResp> reponse = new ArrayList<>(finalReponseMap.values());

        return reponse;
    }

    @Override
    public Map<Long, Map<String, Object>> getMultiProjMultiBudgetTypeDetails(
            ProjScheduleMultiBudgetTypeReq projScheduleMultiBudgetTypeReq) {
        List<Long> projIds = projScheduleMultiBudgetTypeReq.getProjIds();
        Map<Long, ProjManPowerResp> manpowerBugets = new HashMap<>();
        Map<Long, ProjScheduleCostCodeResp> costBudgets = new HashMap<>();
        Map<Long, Map<String, Object>> projBudgets = new HashMap<>();
        for (String budgetType : projScheduleMultiBudgetTypeReq.getBudgetTypes()) {
            switch (budgetType) {
                case "MAN_POWER":
                    manpowerBugets = getMultiProjBudgetManPowerDetails(projIds);
                    break;
                case "COST_CODE":
                    costBudgets = getMultiProjBudgetCostDetails(projIds);
                    break;
                default:
                    break;
            }
        }
        for (Long projId : projIds) {
            Map<String, Object> map = new HashMap<>();
            map.put("manPower", manpowerBugets.get(projId));
            map.put("costCode", costBudgets.get(projId));
            projBudgets.put(projId, map);
        }
        return projBudgets;

    }

    private Map<Long, ProjScheduleCostCodeResp> getMultiProjBudgetCostDetails(List<Long> projIds) {
        Map<Long, ProjScheduleCostCodeResp> resp = new HashMap<>();
        for (Long projId : projIds) {
            ProjScheduleBaseLineGetReq req = new ProjScheduleBaseLineGetReq();
            req.setProjId(projId);
            req.setStatus(1);
            resp.put(projId, getProjBudgetCostCodeDetails(req));
        }
        return resp;
    }

    private List<LabelKeyTO> getCostCodeActualQuantities(Long projId) {
        List<Object[]> timesheetQuantity = timeSheetEmpDtlRepositoryCopy.getCostCodeActualQuantity(projId);
        List<Object[]> manPowerQuantity = empWageWorkDairyRepositoryCopy.getManPowerCostDetails(projId);
        List<Object[]> materialQuantity = materialCostWorkDairyRepositoryCopy.getMaterialCostDetails(projId);
        List<Object[]> plantQuantity = plantCostWorkDairyRepositoryCopy.getPlantCostDetails(projId);
        Map<String, Double> actualQuantitiesMap = new HashMap<>();

        for (Object[] timesheetDetails : timesheetQuantity) {
            double actualValue = 0;
            Long costId = (Long) timesheetDetails[0];
            Date weekStartdate = (Date) timesheetDetails[1];
            String weekStartdateStr = CommonUtil.convertDateToDDMMYYYYString(weekStartdate);
            if (weekStartdate.before((Date) timesheetDetails[2])) {
                if (timesheetDetails[3] != null)
                    actualValue = Double.sum(actualValue, (Double) timesheetDetails[3]);
                if (timesheetDetails[4] != null)
                    actualValue = Double.sum(actualValue, (Double) timesheetDetails[4]);
                if (timesheetDetails[5] != null)
                    actualValue = Double.sum(actualValue, (Double) timesheetDetails[5]);
                if (timesheetDetails[6] != null)
                    actualValue = Double.sum(actualValue, (Double) timesheetDetails[6]);
                if (timesheetDetails[7] != null)
                    actualValue = Double.sum(actualValue, (Double) timesheetDetails[7]);
                if (timesheetDetails[8] != null)
                    actualValue = Double.sum(actualValue, (Double) timesheetDetails[8]);
                if (timesheetDetails[9] != null)
                    actualValue = Double.sum(actualValue, (Double) timesheetDetails[9]);
            }
            updateCostCodeActualValues(costId, weekStartdateStr, actualValue, actualQuantitiesMap);

        }
        for (Object[] manpowerDetails : manPowerQuantity) {
            updateCostCodeActualValues((Long) manpowerDetails[0], (String) manpowerDetails[1],
                    (Double) manpowerDetails[2], actualQuantitiesMap);
        }
        for (Object[] materialDetails : materialQuantity) {
            updateCostCodeActualValues((Long) materialDetails[0], (String) materialDetails[1],
                    (Double) materialDetails[2], actualQuantitiesMap);
        }
        for (Object[] plantDetails : plantQuantity) {
            updateCostCodeActualValues((Long) plantDetails[0], (String) plantDetails[1], (Double) plantDetails[2],
                    actualQuantitiesMap);
        }
        List<LabelKeyTO> actualQuantities = new ArrayList<>();
        actualQuantitiesMap.forEach((k, v) -> {
            String[] arr = k.split("#");
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(Long.valueOf(arr[0]));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_DATE, arr[1]);
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_HRS, String.valueOf(v));
            actualQuantities.add(labelKeyTO);
        });
        return actualQuantities;
    }

    private void updateCostCodeActualValues(Long costId, String dateStr, Double value,
            Map<String, Double> actualQuantitiesMap) {
        actualQuantitiesMap.merge(costId + "#" + dateStr, value, Double::sum);
    }

    private LabelKeyTO getActualValuesLabelTo(Object id, Date date, Object actualHrs) {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId((Long) id);
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_DATE, CommonUtil.convertDateToString(date));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_HRS, String.valueOf(actualHrs));
        return labelKeyTO;
    }

    private ProjectReportsTO getProjectReports(Long projId) {
        List<ProjectReportsEntity> projReports = projReportsRepository.findProjReports(projId, 1);
        if (!projReports.isEmpty())
            return ProjReportsHandler.convertEntityToPOJO(projReports.get(0));
        return null;
    }
    
    public ScheduleActivityDataSetResp parseResourceAssignmentData(MultipartFile file, ScheduleActivityDataSetReq scheduleActivityDataSetReq) throws IOException {
    	ScheduleActivityDataSetTO scheduleActivityDataSetTO = new ScheduleActivityDataSetTO(); 
    	scheduleActivityDataSetTO.fromScheduleActivityDataSetReq(scheduleActivityDataSetReq);
    	ScheduleActivityDataSetResp scheduleActivityDataSetResp = scheduleActivityDataSetTO.toScheduleActivityDataSetResp();
    	
    	List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
    	String header = "Resource ID,Resource Name,Resource Type,Activity ID,Activity Name,WBS,WBS Name,SOE ID,Start,Finish,Unit of Measure,Budgeted Units,Actual Units,Remaining Units,Calendar,Curve,Spreadsheet Field";
    	boolean isFirstLine = true;
		InputStream is = file.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		Date[] valueHeader = null;
		int lineIndex = 0;
		while ((line = br.readLine()) != null) {
			if (isFirstLine) {
				isFirstLine=false;
				//if (line.toLowerCase().startsWith(header.toLowerCase())) {
				if (line.toLowerCase().startsWith(header.toLowerCase())) {
					valueHeader = parseValueHeader(line);
				} else {
					ResourceAssignmentDataTO resourceAssignmentDataTO = toResourceAssignmentDataTableTO(line, valueHeader, scheduleActivityDataSetTO, lineIndex);
					if (!scheduleActivityDataSetResp.isHasInvalidData())
						scheduleActivityDataSetResp.setHasInvalidData(resourceAssignmentDataTO.isValid());
					resourceAssignmentDataTOs.add(resourceAssignmentDataTO);
				}
			} else {
				ResourceAssignmentDataTO resourceAssignmentDataTO = toResourceAssignmentDataTableTO(line, valueHeader, scheduleActivityDataSetTO, lineIndex );
				if (!scheduleActivityDataSetResp.isHasInvalidData())
					scheduleActivityDataSetResp.setHasInvalidData(resourceAssignmentDataTO.isValid());
				resourceAssignmentDataTOs.add(resourceAssignmentDataTO);
			}
			lineIndex++;
		}
    	scheduleActivityDataSetResp.setResourceAssignmentDataTableTOs(resourceAssignmentDataTOs);
    	scheduleActivityDataSetResp.setScheduleDate(scheduleActivityDataSetReq.getScheduleDate());
    	scheduleActivityDataSetResp.setDatasetName(scheduleActivityDataSetReq.getDatasetName());
    	return scheduleActivityDataSetResp;
    }
    
    private Date[] parseValueHeader(String line) {
    	String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    	Date[] valueHeader = new Date[values.length-17];
    	for (int i = 17; i < values.length; i++) {
    		try {
    			valueHeader[i-17] = new SimpleDateFormat("dd-MMM-yy").parse(values[i]);
	    	} catch (ParseException ex) {
	    		log.info("ParseException [header date] while parsing value: " + values[i]);
	    		log.info("ParseException while parsing line: " + line);
	    		valueHeader = null;
	    	}
    	}
    	return valueHeader;
    }
    
    private ResourceAssignmentDataTO toResourceAssignmentDataTableTO(String line, Date[] valueHeader, ScheduleActivityDataSetTO scheduleActivityDataSetTO, int lineIndex) {
    	boolean isValid = true;
    	String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    	ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();

    	try {
    		resourceAssignmentDataTO.setCode(values[0].trim());
    		resourceAssignmentDataTO.setName(values[1].trim());
    		resourceAssignmentDataTO.setType(values[2].trim());
    		resourceAssignmentDataTO.setActivityCode(values[3].trim());
    		resourceAssignmentDataTO.setActivityName(values[4].trim());
    		resourceAssignmentDataTO.setWbsCode(values[5].trim());
    		resourceAssignmentDataTO.setWbsName(values[6].trim());
    		resourceAssignmentDataTO.setSoeCode(values[7].trim());
    		System.out.println("parsed values:"+values[5].trim()+"->"+values[7].trim());
    		if (values[0].length() == 0) {
    			isValid = false;
    			resourceAssignmentDataTO.addValidationMessage("Resource ID is invalid");
    		} else {
    			switch (resourceAssignmentDataTO.getType().toLowerCase()){
    			case "labor":
    				EmpClassMstrEntity empClassMstrEntity = empClassRepository.findBy(loginRepository.findOne(AppUserUtils.getUserId()).getClientRegEntity().getClientId(), resourceAssignmentDataTO.getCode(), 1);
    				if (empClassMstrEntity != null) {
    					System.out.println("");
    					resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_EMPLOYEE);
    					resourceAssignmentDataTO.setName(empClassMstrEntity.getName());
    					ProjManpowerEntity projManpowerEntity = projManpowerRepository.findBy(scheduleActivityDataSetTO.getProjId(), empClassMstrEntity.getId());
    					if (projManpowerEntity == null) {
    						isValid = false;
        	    			resourceAssignmentDataTO.addValidationMessage("Resource ID is not assigned to project");
    					} else {
    						resourceAssignmentDataTO.setReferenceId(projManpowerEntity.getId());
    					}
    				} else {
    					isValid = false;
    	    			resourceAssignmentDataTO.addValidationMessage("Resource ID unavailable in Employee");
    				}
    				break;
    			case "nonlabor":
    				System.out.println("nonlabor case:"+loginRepository.findOne(AppUserUtils.getUserId()).getClientRegEntity().getClientId()+"->"+resourceAssignmentDataTO.getCode());
    				PlantMstrEntity plantMstrEntity = plantClassRepository.findBy(loginRepository.findOne(AppUserUtils.getUserId()).getClientRegEntity().getClientId(), resourceAssignmentDataTO.getCode(),1);
    				ProjSORItemEntity projSORItemEntityCopy = projSORItemRepository.findBy(scheduleActivityDataSetTO.getProjId(), resourceAssignmentDataTO.getCode());
    				ProjCostItemEntity projCostItemEntityCopy = projCostItemRepository.findBy(scheduleActivityDataSetTO.getProjId(), resourceAssignmentDataTO.getCode());
    				if (plantMstrEntity != null) {
    					resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_PLANT);
    					resourceAssignmentDataTO.setName(plantMstrEntity.getName());
    					System.out.println(scheduleActivityDataSetTO.getProjId()+"->"+plantMstrEntity.getId());
    					ProjectPlantsDtlEntity projectPlantsDtlEntity = projectPlantsRepository.findBy(scheduleActivityDataSetTO.getProjId(), plantMstrEntity.getId());
    					if (projectPlantsDtlEntity == null) {
    						isValid = false;
        	    			resourceAssignmentDataTO.addValidationMessage("Resource ID is not assigned to project");
    					} else {
    						resourceAssignmentDataTO.setReferenceId(projectPlantsDtlEntity.getId());
    					}
    				} else if (projCostItemEntityCopy != null) {
    					resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_COST);
    					resourceAssignmentDataTO.setReferenceId(projCostItemEntityCopy.getId());
    					resourceAssignmentDataTO.setName(projCostItemEntityCopy.getName());
    				} else if (projSORItemEntityCopy != null) {
    					resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_SOR);
    					resourceAssignmentDataTO.setReferenceId(projSORItemEntityCopy.getId());
    					resourceAssignmentDataTO.setName(projSORItemEntityCopy.getName());
    					if (scopeOfWorkRepository.findBySOR(scheduleActivityDataSetTO.getProjId(), resourceAssignmentDataTO.getReferenceId()) == null) {
    						isValid = false;
        	    			resourceAssignmentDataTO.addValidationMessage("Resource ID is not assigned to project");
    					}
    				} else {
    					isValid = false;
    	    			resourceAssignmentDataTO.addValidationMessage("Resource ID unavailable in POT Plant, Cost & SOR");
    				}
    				break;
    			case "material":
    			case "materials":
    				MaterialClassMstrEntity materialClassMstrEntity = materialClassRepository.findBy(loginRepository.findOne(AppUserUtils.getUserId()).getClientRegEntity().getClientId(), resourceAssignmentDataTO.getCode(),1);
    				TangibleClassificationEntity tangibleClassificationEntity = tangibleClassRepository.findBy(loginRepository.findOne(AppUserUtils.getUserId()).getClientRegEntity().getClientId(), resourceAssignmentDataTO.getCode());
    				if (materialClassMstrEntity != null) {
    					resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_MATERIAL);
    					resourceAssignmentDataTO.setName(materialClassMstrEntity.getName());
    					ProjectMaterialBudgetEntity projectMaterialBudgetEntity = projectMaterialRepository.findBy(scheduleActivityDataSetTO.getProjId(), materialClassMstrEntity.getId());
    					if (projectMaterialBudgetEntity == null) {
    						isValid = false;
        	    			resourceAssignmentDataTO.addValidationMessage("Resource ID is not assigned to project");
    					} else {
    						if (projectMaterialBudgetEntity.getOriginalQty() == null) {
    							isValid = false;
    							resourceAssignmentDataTO.addValidationMessage("Material without original quantity cannot be imported. Please add original quantity in Project Budgets");
    						} else {
    							resourceAssignmentDataTO.setReferenceId(projectMaterialBudgetEntity.getId());
    						}
    					}
    				} else if (tangibleClassificationEntity != null) {
    					resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_TANGIBLE);
    					resourceAssignmentDataTO.setReferenceId(tangibleClassificationEntity.getId());
    					resourceAssignmentDataTO.setName(tangibleClassificationEntity.getName());
    					if (scopeOfWorkRepository.findByTangible(scheduleActivityDataSetTO.getProjId(), resourceAssignmentDataTO.getReferenceId()) == null) {
    						isValid = false;
        	    			resourceAssignmentDataTO.addValidationMessage("Resource ID is not assigned to project");
    					}
    				} else {
    					isValid = false;
    	    			resourceAssignmentDataTO.addValidationMessage("Resource ID unavailable in Material & Tangible");
    				}
    				break;
    			default:
    				isValid = false;
	    			resourceAssignmentDataTO.addValidationMessage("Unknown Resource Type");
    			}    			
    		}
    		if (values[2].length() == 0) {
    			isValid = false;
    			resourceAssignmentDataTO.addValidationMessage("Resource Type is invalid");
    		}
    		if (values[3].length() == 0) {
    			isValid = false;
    			resourceAssignmentDataTO.addValidationMessage("Activity ID is invalid");
    		}
    		if (values[7].length() == 0) {
    			isValid = false;
    			resourceAssignmentDataTO.addValidationMessage("SOE is invalid");
    		} else {
	    		int cnt = projSOEItemRepositoryCopy.countBy(scheduleActivityDataSetTO.getProjId(), values[7]);
		    	if (cnt < 1) {
		    		isValid = false;
		    		resourceAssignmentDataTO.addValidationMessage("SOE ID not found");
		    	}
		    	if (cnt > 1) {
		    		isValid = false;
		    		resourceAssignmentDataTO.addValidationMessage("Mutiple SOE ID found");
		    	}
    		}
    		
    		if (values[8].endsWith("A")) {
    			resourceAssignmentDataTO.setStartDateFinal(true);
	    		values[8] = values[8].substring(0, values[8].length() - 1);
	    	}
    		if( !values[8].contains("Start") )
    		{
    			try {
    	    		System.out.println("start date value:"+values[8]);
    	    		resourceAssignmentDataTO.setStartDate(new SimpleDateFormat("dd-MMM-yy").parse(values[8]));
    	    	} catch (ParseException ex) {
    	    		isValid = false;
    	    		resourceAssignmentDataTO.addValidationMessage("Start Date is invalid");
    	    		log.info("ParseException [start date] while parsing value: " + values[8]);
    	    		log.info("ParseException while parsing line: " + line);
    	    	}
    		}
	    	
	    	if (values[9].endsWith("A")) {	    		
    			resourceAssignmentDataTO.setFinishDateFinal(true);
	    		values[9] = values[9].substring(0, values[9].length() - 1);
	    	}
	    	if( !values[9].contains("Finish") )
    		{
	    		try {
		    		System.out.println("finish date value:"+values[9]);
		    		resourceAssignmentDataTO.setFinishDate(new SimpleDateFormat("dd-MMM-yy").parse(values[9]));
		    	} catch (ParseException ex) {
		    		isValid = false;
		    		resourceAssignmentDataTO.addValidationMessage("Finish Date is invalid");
		    		log.info("ParseException [finish date] while parsing value: " + values[9]);
		    		log.info("ParseException while parsing line: " + line);
		    	}
    		}
	    	
	    	resourceAssignmentDataTO.setUnitOfMeasure(values[10]);
	    	if( !values[11].contains("Budgeted Units") )
	    	{
	    		try {
		    		System.out.println("budget units:"+values[11]);
		    		resourceAssignmentDataTO.setBudgetUnits(Long.parseLong(values[11]));
		    	} catch (NumberFormatException ex) {
		    		isValid = false;
		    		resourceAssignmentDataTO.addValidationMessage("Budgeted Units is invalid");
		    		log.info("NumberFormatException [budget units] while parsing value: " + values[11]);
		    		log.info("NumberFormatException while parsing line: " + line);
		    	}
	    	}
	    	
	    	if( !values[12].contains("Actual Units") )
	    	{
		    	try {
		    		System.out.println("actual units:"+values[12]);
		    		resourceAssignmentDataTO.setActualUnits(Long.parseLong(values[12]));
		    	} catch (NumberFormatException ex) {
		    		isValid = false;
		    		resourceAssignmentDataTO.addValidationMessage("Actual Units is invalid");
		    		log.info("NumberFormatException [actual units] while parsing value: " + values[12]);
		    		log.info("NumberFormatException while parsing line: " + line);
		    	}
	    	}
	    	resourceAssignmentDataTO.setCalendar(values[14]);
	    	resourceAssignmentDataTO.setCurve(values[15]);
	    	resourceAssignmentDataTO.setSpreadsheetField(values[16]);
	    	System.out.println(resourceAssignmentDataTO);
	    	if (valueHeader != null)
	    		resourceAssignmentDataTO.setResourceAssignmentDataValueTOs(parseResourceAssignmentDataValue(values, valueHeader));

	    	if (valueHeader == null) {
	    		isValid = false;
	    		resourceAssignmentDataTO.addValidationMessage("Header date is invalid");
	    	}
    	} catch (ArrayIndexOutOfBoundsException ex) {
    		isValid = false;
    		log.info("ArrayIndexOutOfBoundsException while parsing line: " + line);
    	}
    	resourceAssignmentDataTO.setValid(isValid);
    	return resourceAssignmentDataTO;
    }
    
    private List<ResourceAssignmentDataValueTO> parseResourceAssignmentDataValue(String[] values, Date[] valueHeader) {
    	List<ResourceAssignmentDataValueTO> resourceAssignmentDataValueTOs = new ArrayList<ResourceAssignmentDataValueTO>();
    	ResourceAssignmentDataValueTO resourceAssignmentDataValueTO;
    	for (int i = 0; i < valueHeader.length; i++) {
    		resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
    		resourceAssignmentDataValueTO.setForecastDate(valueHeader[i]);
    		try {
    			resourceAssignmentDataValueTO.setBudget(Double.valueOf(values[17+i]));
	    	} catch (NumberFormatException ex) {
	    		resourceAssignmentDataValueTO.setBudget(0D);
	    	}
    		
    		resourceAssignmentDataValueTOs.add(resourceAssignmentDataValueTO);
    	}
    	return resourceAssignmentDataValueTOs;
    }
    
    public ScheduleActivityDataSetResp parseScheduleActivityData(MultipartFile file, ScheduleActivityDataSetReq scheduleActivityDataSetReq) throws IOException {
    	ScheduleActivityDataSetTO scheduleActivityDataSetTO = new ScheduleActivityDataSetTO(); 
    	scheduleActivityDataSetTO.fromScheduleActivityDataSetReq(scheduleActivityDataSetReq);
    	ScheduleActivityDataSetResp scheduleActivityDataSetResp = scheduleActivityDataSetTO.toScheduleActivityDataSetResp();
    	
    	List<ScheduleActivityDataTO> scheduleActivityDataTOs = new ArrayList<ScheduleActivityDataTO>();
    	List<String> headers = new ArrayList<String>();
    	headers.add("Critical,WBS ID,WBS Name,WBS Path,Activity ID,Activity Name,SOE ID,Original Duration,Start,Finish,Predecessors,Successors,Physical % Complete,Calendar,Lag or Lead");
    	boolean isFirstLine = true;
		InputStream is = file.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		
		while ((line = br.readLine()) != null) {
			if (isFirstLine) {
				isFirstLine=false;
				if (!headers.contains(line))
					break;
			} else {
				ScheduleActivityDataTO scheduleActivityDataTableTO = toScheduleActivityDataTableTO(line, scheduleActivityDataSetTO);
				if (!scheduleActivityDataSetResp.isHasInvalidData())
					scheduleActivityDataSetResp.setHasInvalidData(scheduleActivityDataTableTO.isValid());
				scheduleActivityDataTOs.add(scheduleActivityDataTableTO);
			}
		}
		
		boolean hasOrpharnedChildren = true;
		while (hasOrpharnedChildren) {
			hasOrpharnedChildren = false;
			for (ScheduleActivityDataTO scheduleActivityDataTO : scheduleActivityDataTOs) {
				if (scheduleActivityDataTO.getWbsPath() == null || scheduleActivityDataTO.getWbsPath().length() == 0) {
					hasOrpharnedChildren = true;
					break;
				}
			}
			scheduleActivityDataTOs = fixWbsPath(scheduleActivityDataTOs);
		}
		scheduleActivityDataTOs = setWBSGroupHeaders(scheduleActivityDataTOs);
		
    	scheduleActivityDataSetResp.setScheduleActivityDataTOs(scheduleActivityDataTOs);
		return scheduleActivityDataSetResp;
    }
    
    private List<ScheduleActivityDataTO> setWBSGroupHeaders(List<ScheduleActivityDataTO> scheduleActivityDataTOs) {
    	for (ScheduleActivityDataTO scheduleActivityDataTO : scheduleActivityDataTOs) {
    		if (scheduleActivityDataTO.getValidationMessages().size() > 0) {
    			if ((scheduleActivityDataTO.getWbsCode() == null || scheduleActivityDataTO.getWbsCode().length() == 0)
    					&& (scheduleActivityDataTO.getActivityCode() == null || scheduleActivityDataTO.getActivityCode().length() == 0)
    					&& (scheduleActivityDataTO.getActivityName() == null || scheduleActivityDataTO.getActivityName().length() == 0)
    					&& (scheduleActivityDataTO.getSoeCode() == null || scheduleActivityDataTO.getSoeCode().length() == 0)
    					&& (scheduleActivityDataTO.getPredecessors() == null || scheduleActivityDataTO.getPredecessors().length() ==0)
    					&& (scheduleActivityDataTO.getSuccessors() == null || scheduleActivityDataTO.getSuccessors().length() ==0)) {
    				if (hasValidWbsName(scheduleActivityDataTOs, scheduleActivityDataTO.getWbsName())) {
        				scheduleActivityDataTO.setValid(false);
        				scheduleActivityDataTO.getValidationMessages().clear();
        				scheduleActivityDataTO.addValidationMessage("This row looks like a activity group header (shall not be imported)");
        			} else {
        				scheduleActivityDataTO.setValid(true);
        				scheduleActivityDataTO.getValidationMessages().clear();
        			}
    			}
    		}
    	}
    	return scheduleActivityDataTOs;
    }
    
    private boolean hasValidWbsName(List<ScheduleActivityDataTO> scheduleActivityDataTOs, String wbsName) {
    	for (ScheduleActivityDataTO scheduleActivityDataTO : scheduleActivityDataTOs) {
    		if (scheduleActivityDataTO.getWbsName().equals(wbsName) && scheduleActivityDataTO.getValidationMessages().size() == 0)
    			return true;
    	}
    	return false;
    }
    
    private List<ScheduleActivityDataTO> fixWbsPath(List<ScheduleActivityDataTO> scheduleActivityDataTOs) {
    	for (int i=0; i < scheduleActivityDataTOs.size(); i++) {
    		if (scheduleActivityDataTOs.get(i).getWbsPath() == null || scheduleActivityDataTOs.get(i).getWbsPath().length() == 0) {
    			if (i == 0) {
    				scheduleActivityDataTOs.get(i).setWbsPath(Integer.toString(i));
    				scheduleActivityDataTOs.get(i).getValidationMessages().clear();
    				scheduleActivityDataTOs.get(i).setValid(true);
    			}
    			if (i+1 < scheduleActivityDataTOs.size()) {
    				if (scheduleActivityDataTOs.get(i+1).getWbsPath() != null && scheduleActivityDataTOs.get(i+1).getWbsPath().length() > 0) {
    					if (scheduleActivityDataTOs.get(i+1).getWbsPath().contains(".") && (!scheduleActivityDataTOs.get(i).getWbsName().equals(scheduleActivityDataTOs.get(i+1).getWbsName()))) {
    						scheduleActivityDataTOs.get(i).setWbsPath(scheduleActivityDataTOs.get(i+1).getWbsPath().substring(0, scheduleActivityDataTOs.get(i+1).getWbsPath().lastIndexOf(".")));
    						scheduleActivityDataTOs.get(i).getValidationMessages().clear();
        					scheduleActivityDataTOs.get(i).setValid(true);
    					} else if (scheduleActivityDataTOs.get(i).getWbsName().equals(scheduleActivityDataTOs.get(i+1).getWbsName())) {
    						scheduleActivityDataTOs.get(i).setWbsPath(scheduleActivityDataTOs.get(i+1).getWbsPath());
    					}
    				}
    			}
    		}
    	}
    	return scheduleActivityDataTOs;
    }
    
    private ScheduleActivityDataTO toScheduleActivityDataTableTO(String line, ScheduleActivityDataSetTO scheduleActivityDataSetTO) {
    	boolean isValid = true;
    	int i = 0;
    	String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    	List<String> criticalValues = new ArrayList<String>();
    	criticalValues.add("yes");
    	criticalValues.add("no");
    	ScheduleActivityDataTO scheduleActivityDataTableTO = new ScheduleActivityDataTO();
    	try {
    		String columnOne = values[i++].trim();
    		if (criticalValues.contains(columnOne.toLowerCase()))
    			scheduleActivityDataTableTO.setCritical(columnOne.trim().equalsIgnoreCase("yes") ? Integer.parseInt("1") : Integer.parseInt("0"));
    		else
    			scheduleActivityDataTableTO.setWbsName(columnOne);
	    	
	    	scheduleActivityDataTableTO.setWbsCode(values[i++].trim());
	    	if (scheduleActivityDataTableTO.getWbsName() == null)
	    		scheduleActivityDataTableTO.setWbsName(values[i++].trim());
	    	else
	    		i++;
	    	scheduleActivityDataTableTO.setWbsPath(values[i++].trim());
	    	scheduleActivityDataTableTO.setActivityCode(values[i++].trim());
	    	scheduleActivityDataTableTO.setActivityName(values[i++].trim());
	    	scheduleActivityDataTableTO.setSoeCode(values[i++].trim());
	    	
	    	if (scheduleActivityDataTableTO.getWbsCode().length() == 0) {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("WBS can not be empty");
	    	}
	    	if (scheduleActivityDataTableTO.getWbsName().length() == 0) {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("WBS Name can not be empty");
	    	}
	    	if (scheduleActivityDataTableTO.getActivityCode().length() == 0) {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("Activity ID can not be empty");
	    	}
	    	if (scheduleActivityDataTableTO.getActivityName().length() == 0) {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("Activity Name can not be empty");
	    	}
	    	if (scheduleActivityDataTableTO.getSoeCode().length() > 0) {
		    	int cnt = projSOEItemRepositoryCopy.countBy(scheduleActivityDataSetTO.getProjId(), scheduleActivityDataTableTO.getSoeCode());
		    	if (cnt < 1) {
		    		isValid = false;
		    		scheduleActivityDataTableTO.addValidationMessage("SOE ID not found");
		    	}
		    	if (cnt > 1) {
		    		isValid = false;
		    		scheduleActivityDataTableTO.addValidationMessage("Mutiple SOE ID found");
		    	}
	    	} else {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("SEO ID can not be empty");
	    	}
	    	
	    	try {
	    		scheduleActivityDataTableTO.setOriginalDuration(Long.parseLong(values[i++].trim()));
	    	} catch (NumberFormatException ex) {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("Original Duration is invalid");
	    		log.info("NumberFormatException [original duration] while parsing value: " + values[i-1]);
	    		log.info("NumberFormatException while parsing line: " + line);
	    	}
	    	if (values[i].endsWith("A")) {
	    		scheduleActivityDataTableTO.setStartDateFinal(true);
	    		values[i] = values[i].substring(0, values[i].length() - 1);
	    	}
	    	try {
	    		scheduleActivityDataTableTO.setStartDate(new SimpleDateFormat("dd-MMM-yy").parse(values[i++]));
	    	} catch (ParseException ex) {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("Start Date is invalid");
	    		log.info("ParseException [start date] while parsing value: " + values[i-1]);
	    		log.info("ParseException while parsing line: " + line);
	    	}
	    	if (values[i].endsWith("A")) {
	    		scheduleActivityDataTableTO.setFinishDateFinal(true);
	    		values[i] = values[i].substring(0, values[i].length() - 1);
	    	}
	    	try {
	    		scheduleActivityDataTableTO.setFinishDate(new SimpleDateFormat("dd-MMM-yy").parse(values[i++]));
	    	} catch (ParseException ex) {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("Finish Date is invalid");
	    		log.info("ParseException [finish date] while parsing value: " + values[i-1]);
	    		log.info("ParseException while parsing line: " + line);
	    	}
	    	scheduleActivityDataTableTO.setPredecessors(values[i++].trim().replaceAll("\"", ""));
	    	scheduleActivityDataTableTO.setSuccessors(values[i++].trim().replaceAll("\"", ""));
	    	String completion = values[i++].trim().replace("%", "");
	    	if (completion == "") 
	    		completion = "0";
	    	try {
	    		scheduleActivityDataTableTO.setPhysicalComplete(Long.parseLong(completion));
	    	} catch (NumberFormatException ex) {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("Physical Complete is invalid");
	    		log.info("NumberFormatException [physical complete] while parsing value: " + completion);
	    		log.info("NumberFormatException while parsing line: " + line);
	    	}
	    	scheduleActivityDataTableTO.setCalendar(values[i++].trim());
	    	try {
	    		if (values[i].toString().length() > 0)
	    			scheduleActivityDataTableTO.setLeadLag(Integer.parseInt(values[i++]));
	    	} catch (NumberFormatException ex) {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("Lag Lead is invalid");
	    		log.info("NumberFormatException [lag lead] while parsing value: " + values[i-1]);
	    		log.info("NumberFormatException while parsing line: " + line);
	    	}
	    	if (values.length != 15) {
	    		isValid = false;
	    		scheduleActivityDataTableTO.addValidationMessage("Does not have 15 columns");
	    		log.info("Invalid line while parsing: " + line);
	    	}
    	} catch (ArrayIndexOutOfBoundsException ex) {
    		isValid = false;
    		scheduleActivityDataTableTO.addValidationMessage("Does not have 15 columns");
    		log.info("ArrayIndexOutOfBoundsException while parsing line: " + line);
    	}
    	scheduleActivityDataTableTO.setValid(isValid);
    	return scheduleActivityDataTableTO;
    }
    
    public ScheduleActivityDataSetResp saveResourceAssignment(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	System.out.println("saveResourceAssignment function");
    	ScheduleActivityDataSetTO scheduleActivityDataSetTO = new ScheduleActivityDataSetTO(); 
    	scheduleActivityDataSetTO.fromScheduleActivityDataSetReq(scheduleActivityDataSetReq);
    	if (!scheduleActivityDataSetReq.isPrimaveraIntegrated()) {
    		ScheduleActivityDataSetResp scheduleActivityDataSetResp = prepareResourceAssignmentData(scheduleActivityDataSetReq);
    		scheduleActivityDataSetTO.setResourceAssignmentDataTOs(scheduleActivityDataSetResp.getResourceAssignmentDataTableTOs());
    	}
    	scheduleActivityDataSetTO.setCurrent(true);
    	if (scheduleActivityDataSetRepository.findAllBy(scheduleActivityDataSetTO.getProjId(), scheduleActivityDataSetReq.getType()).size() == 0)
    		scheduleActivityDataSetTO.setBaseline(true);
    	scheduleActivityDataSetRepository.updateCurrent(scheduleActivityDataSetTO.getProjId(), scheduleActivityDataSetReq.getType());
    	ScheduleActivityDataSetEntity scheduleActivityDataSetEntity = scheduleActivityDataSetRepository.save(ScheduleActivityDataSetHandler.convertPOJOToEntity(scheduleActivityDataSetTO, ePSProjRepository, loginRepository));
    	List<ResourceAssignmentDataEntity> resourceAssignmentDataEntities = new ArrayList<ResourceAssignmentDataEntity>();
    	List<ProjManpowerEntity> projManpowerEntities = new ArrayList<ProjManpowerEntity>();
    	List<ProjectPlantsDtlEntity> projectPlantsDtlEntities = new ArrayList<ProjectPlantsDtlEntity>();
    	List<ProjectMaterialBudgetEntity> projectMaterialBudgetEntities = new ArrayList<ProjectMaterialBudgetEntity>();
    	List<ProjCostItemEntity> projCostItemEntities = new ArrayList<ProjCostItemEntity>();
    	
    	Map<Long, LabelKeyTO> manpowerActuals = actualHrsServiceImpl.getManpowerActualHrs(scheduleActivityDataSetTO.getProjId());
    	Map<Long, LabelKeyTO> plantActuals = actualHrsServiceImpl.getPlantActualHrs(scheduleActivityDataSetTO.getProjId());
    	Map<Long, LabelKeyTO> materialActuals = actualHrsServiceImpl.getMaterialActualHrs(scheduleActivityDataSetTO.getProjId());
    	Map<Long, CostActualHoursTO> costActuals = actualAmountService.getCostStmt(scheduleActivityDataSetTO.getProjId());
    	List<ProjScheduleBaseLineEntity> projScheduleBaseLineEntities = new ArrayList<ProjScheduleBaseLineEntity>();
    	List<ProjScheduleManPowerEntity> projScheduleManPowerEntities = new ArrayList<ProjScheduleManPowerEntity>();
    	List<ProjSchedulePlantEntity> projSchedulePlantEntities = new ArrayList<ProjSchedulePlantEntity>();
    	List<ProjScheduleMaterialEntity> projScheduleMaterialEntities = new ArrayList<ProjScheduleMaterialEntity>();
    	List<ProjScheduleCostCodeEntity> projScheduleCostCodeEntities = new ArrayList<ProjScheduleCostCodeEntity>();
        List<ProjScheduleSOWEntity> projScheduleSOWEntities = new ArrayList<ProjScheduleSOWEntity>();
        
        ProjCostStatementsGetReq projCostStatementsGetReq = new ProjCostStatementsGetReq();
		projCostStatementsGetReq.setStatus(1);
		projCostStatementsGetReq.setProjId(scheduleActivityDataSetReq.getProjId());
		ProjCostStaementsResp projCostStaementsResp = projSettingsService.getProjCostStatements(projCostStatementsGetReq);
 
    	for (ResourceAssignmentDataTO resourceAssignmentDataTO : scheduleActivityDataSetTO.getResourceAssignmentDataTOs()) {
    		System.out.println(resourceAssignmentDataTO);
    		if (resourceAssignmentDataTO.isValid()) {
	    		ResourceAssignmentDataEntity resourceAssignmentDataEntity = ResourceAssignmentDataHandler.convertPOJOToEntity(resourceAssignmentDataTO, scheduleActivityDataSetEntity.getProjMstrEntity().getProjectId(), projSOEItemRepositoryCopy, loginRepository);
	    		resourceAssignmentDataEntity.setScheduleActivityDataSetEntity(scheduleActivityDataSetEntity);
	    		resourceAssignmentDataEntities.add(resourceAssignmentDataEntity);
	    		
	    		if (resourceAssignmentDataEntity.getReferenceType().equals(ResourceAssignmentDataHandler.POT_COST)) {
	    			if (scheduleActivityDataSetReq.isPrimaveraIntegrated())
	    				setProjCostItemEntityCopyDates(projCostItemEntities, resourceAssignmentDataEntity, scheduleActivityDataSetEntity.getProjMstrEntity().getProjectId());
    				if (projScheduleBaseLineEntities.stream().filter(e -> e.getScheduleItemType().equals("C")).count() == 0)
		    			projScheduleBaseLineEntities.add(ResourceAssignmentDataHandler.toProjScheduleBaseLineEntity(scheduleActivityDataSetTO, resourceAssignmentDataTO, epsProjRepository, "C"));
    				ProjCostStmtDtlTO projCostStmtDtlTO = null;
    				for (ProjCostStmtDtlTO pCostStmtDtlTO : projCostStaementsResp.getProjCostStmtDtlTOs()) {
    					projCostStmtDtlTO = getProjCostStmtDtlTO(resourceAssignmentDataTO.getReferenceId(), pCostStmtDtlTO);
    					if (projCostStmtDtlTO != null) break;
    				}
	    			projScheduleCostCodeEntities.add(ResourceAssignmentDataHandler.toProjScheduleCostCodeEntity(resourceAssignmentDataTO, projCostItemRepository, projCostStmtDtlTO, resourceCurveRepository, projScheduleBaseLineEntities.stream().filter(e -> e.getScheduleItemType().equals("C")).findAny().orElse(null), costActuals));
	    		} else if (resourceAssignmentDataEntity.getReferenceType().equals(ResourceAssignmentDataHandler.POT_EMPLOYEE)) {
	    			if (scheduleActivityDataSetReq.isPrimaveraIntegrated())
	    				setProjManpowerEntityDates(projManpowerEntities, resourceAssignmentDataEntity, scheduleActivityDataSetEntity.getProjMstrEntity().getProjectId());
	    			if (projScheduleBaseLineEntities.stream().filter(e -> e.getScheduleItemType().equals("E")).count() == 0)
		    			projScheduleBaseLineEntities.add(ResourceAssignmentDataHandler.toProjScheduleBaseLineEntity(scheduleActivityDataSetTO, resourceAssignmentDataTO, epsProjRepository, "E"));
	    			projScheduleManPowerEntities.add(ResourceAssignmentDataHandler.toProjScheduleManPowerEntity(resourceAssignmentDataTO, projManpowerRepository, resourceCurveRepository, projScheduleBaseLineEntities.stream().filter(e -> e.getScheduleItemType().equals("E")).findAny().orElse(null), manpowerActuals, empClassRepository, loginRepository, scheduleActivityDataSetReq.getProjId()));
	    		} else if (resourceAssignmentDataEntity.getReferenceType().equals(ResourceAssignmentDataHandler.POT_MATERIAL)) {
	    			if (scheduleActivityDataSetReq.isPrimaveraIntegrated())
	    				setProjectMaterialBudgetEntityDates(projectMaterialBudgetEntities, resourceAssignmentDataEntity, scheduleActivityDataSetEntity.getProjMstrEntity().getProjectId());
	    			if (projScheduleBaseLineEntities.stream().filter(e -> e.getScheduleItemType().equals("M")).count() == 0)
		    			projScheduleBaseLineEntities.add(ResourceAssignmentDataHandler.toProjScheduleBaseLineEntity(scheduleActivityDataSetTO, resourceAssignmentDataTO, epsProjRepository, "M"));
	    			projScheduleMaterialEntities.add(ResourceAssignmentDataHandler.toProjScheduleMaterialEntity(resourceAssignmentDataTO, projectMaterialRepository, resourceCurveRepository, projScheduleBaseLineEntities.stream().filter(e -> e.getScheduleItemType().equals("M")).findAny().orElse(null), materialActuals, materialClassRepository, loginRepository, scheduleActivityDataSetReq.getProjId()));
	    		} else if (resourceAssignmentDataEntity.getReferenceType().equals(ResourceAssignmentDataHandler.POT_PLANT)) {
	    			if (scheduleActivityDataSetReq.isPrimaveraIntegrated())
	    				setProjectPlantsDtlEntityDates(projectPlantsDtlEntities, resourceAssignmentDataEntity, scheduleActivityDataSetEntity.getProjMstrEntity().getProjectId());
	    			if (projScheduleBaseLineEntities.stream().filter(e -> e.getScheduleItemType().equals("P")).count() == 0)
		    			projScheduleBaseLineEntities.add(ResourceAssignmentDataHandler.toProjScheduleBaseLineEntity(scheduleActivityDataSetTO, resourceAssignmentDataTO, epsProjRepository, "P"));
	    			projSchedulePlantEntities.add(ResourceAssignmentDataHandler.toProjSchedulePlantEntity(resourceAssignmentDataTO, projectPlantsRepository, resourceCurveRepository, projScheduleBaseLineEntities.stream().filter(e -> e.getScheduleItemType().equals("P")).findAny().orElse(null), plantActuals, plantClassRepository, loginRepository, scheduleActivityDataSetReq.getProjId()));
	    		} else if (resourceAssignmentDataEntity.getReferenceType().equals(ResourceAssignmentDataHandler.POT_SOR)) {
	    			//if (projScheduleBaseLineEntities.stream().filter(e -> e.getScheduleItemType().equals("S")).count() == 0)
		    			//projScheduleBaseLineEntities.add(ResourceAssignmentDataHandler.toProjScheduleBaseLineEntity(scheduleActivityDataSetTO, resourceAssignmentDataTO, epsProjRepository, "S"));
	    			//projScheduleSOWEntities.add(ResourceAssignmentDataHandler.toProjScheduleSOWEntity(resourceAssignmentDataTO, resourceCurveRepository, projScheduleBaseLineEntities.stream().filter(e -> e.getScheduleItemType().equals("S")).findAny().orElse(null)));
	    		}
    		}
    	}
    	resourceAssignmentDataEntities = resourceAssignmentDataRepository.save(resourceAssignmentDataEntities);
    	if (scheduleActivityDataSetReq.isPrimaveraIntegrated()) {
	    	projCostItemRepository.save(projCostItemEntities);
	    	projManpowerRepository.save(projManpowerEntities);
	    	projectMaterialRepository.save(projectMaterialBudgetEntities);
	    	projectPlantsRepository.save(projectPlantsDtlEntities);
    	}
    	projScheduleBaseLineRepository.save(projScheduleBaseLineEntities);
		projScheduleManPowerRepository.save(projScheduleManPowerEntities);
		projSchedulePlantRepository.save(projSchedulePlantEntities);
		projScheduleMaterialRepository.save(projScheduleMaterialEntities);
		projScheduleCostCodeRepository.save(projScheduleCostCodeEntities);
		projScheduleSOWRepository.save(projScheduleSOWEntities);
		
    	List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
    	for (ResourceAssignmentDataEntity resourceAssignmentDataEntity : resourceAssignmentDataEntities) {
    		resourceAssignmentDataTOs.add(ResourceAssignmentDataHandler.convertEntityToPOJO(resourceAssignmentDataEntity));
    	}
    	
    	ScheduleActivityDataSetTO scheduleActivityDataSetTOOut = ScheduleActivityDataSetHandler.convertEntityToPOJO(scheduleActivityDataSetEntity);
    	scheduleActivityDataSetTOOut.setResourceAssignmentDataTOs(resourceAssignmentDataTOs);
    	return scheduleActivityDataSetTOOut.toScheduleActivityDataSetResp();
    }
    
    private void setProjCostItemEntityCopyDates(List<ProjCostItemEntity> projCostItemEntities, ResourceAssignmentDataEntity resourceAssignmentDataEntity, Long projectId) {
    	boolean found = false;
    	for (ProjCostItemEntity pme : projCostItemEntities) {
			if (pme.getCostMstrEntity().getId().equals(resourceAssignmentDataEntity.getReferenceId())) {
				if (pme.getStartDate().after(resourceAssignmentDataEntity.getStartDate()))
					pme.setStartDate(resourceAssignmentDataEntity.getStartDate());
				if (pme.getFinishDate().before(resourceAssignmentDataEntity.getFinishDate()))
					pme.setFinishDate(resourceAssignmentDataEntity.getFinishDate());
				found = true;
			}
		}
		if (!found) {
			ProjCostItemEntity projCostItemEntityCopy = projCostItemRepository.findBy(resourceAssignmentDataEntity.getReferenceId());
			projCostItemEntityCopy.setStartDate(resourceAssignmentDataEntity.getStartDate());
			projCostItemEntityCopy.setFinishDate(resourceAssignmentDataEntity.getFinishDate());
			projCostItemEntities.add(projCostItemEntityCopy);
		}
    }
    
    private void setProjectMaterialBudgetEntityDates(List<ProjectMaterialBudgetEntity> projectMaterialBudgetEntities, ResourceAssignmentDataEntity resourceAssignmentDataEntity, Long projectId) {
    	boolean found = false;
    	for (ProjectMaterialBudgetEntity pme : projectMaterialBudgetEntities) {
			if (pme.getMaterialClassMstrEntity().getId().equals(resourceAssignmentDataEntity.getReferenceId())) {
				if (pme.getStartDate().after(resourceAssignmentDataEntity.getStartDate()))
					pme.setStartDate(resourceAssignmentDataEntity.getStartDate());
				if (pme.getFinishDate().before(resourceAssignmentDataEntity.getFinishDate()))
					pme.setFinishDate(resourceAssignmentDataEntity.getFinishDate());
				found = true;
			}
		}
		if (!found) {
			ProjectMaterialBudgetEntity projectMaterialBudgetEntity = projectMaterialRepository.findOne(resourceAssignmentDataEntity.getReferenceId());
			projectMaterialBudgetEntity.setStartDate(resourceAssignmentDataEntity.getStartDate());
			projectMaterialBudgetEntity.setFinishDate(resourceAssignmentDataEntity.getFinishDate());
			projectMaterialBudgetEntities.add(projectMaterialBudgetEntity);
		}
    }
    
    private void setProjectPlantsDtlEntityDates(List<ProjectPlantsDtlEntity> projectPlantsDtlEntities, ResourceAssignmentDataEntity resourceAssignmentDataEntity, Long projectId) {
    	boolean found = false;
    	for (ProjectPlantsDtlEntity pme : projectPlantsDtlEntities) {
			if (pme.getPlantMstrEntity().getId().equals(resourceAssignmentDataEntity.getReferenceId())) {
				if (pme.getStartDate().after(resourceAssignmentDataEntity.getStartDate()))
					pme.setStartDate(resourceAssignmentDataEntity.getStartDate());
				if (pme.getFinishDate().before(resourceAssignmentDataEntity.getFinishDate()))
					pme.setFinishDate(resourceAssignmentDataEntity.getFinishDate());
				found = true;
			}
		}
		if (!found) {
			ProjectPlantsDtlEntity projectPlantsDtlEntity = projectPlantsRepository.findOne(resourceAssignmentDataEntity.getReferenceId());
			projectPlantsDtlEntity.setStartDate(resourceAssignmentDataEntity.getStartDate());
			projectPlantsDtlEntity.setFinishDate(resourceAssignmentDataEntity.getFinishDate());
			projectPlantsDtlEntities.add(projectPlantsDtlEntity);
		}
    }
    
    private void setProjManpowerEntityDates(List<ProjManpowerEntity> projManpowerEntities, ResourceAssignmentDataEntity resourceAssignmentDataEntity, Long projectId){
    	boolean found = false;
		for (ProjManpowerEntity pme : projManpowerEntities) {
			if (pme.getEmpClassMstrEntity().getId().equals(resourceAssignmentDataEntity.getReferenceId())) {
				if (pme.getStartDate().after(resourceAssignmentDataEntity.getStartDate()))
					pme.setStartDate(resourceAssignmentDataEntity.getStartDate());
				if (pme.getFinishDate().before(resourceAssignmentDataEntity.getFinishDate()))
					pme.setFinishDate(resourceAssignmentDataEntity.getFinishDate());
				found = true;
			}
		}
		if (!found) {
			ProjManpowerEntity projManpowerEntity = projManpowerRepository.findOne(resourceAssignmentDataEntity.getReferenceId());
			projManpowerEntity.setStartDate(resourceAssignmentDataEntity.getStartDate());
			projManpowerEntity.setFinishDate(resourceAssignmentDataEntity.getFinishDate());
			projManpowerEntities.add(projManpowerEntity);
		}
    }
    
    public ScheduleActivityDataSetResp saveScheduleActivity(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ScheduleActivityDataSetTO scheduleActivityDataSetTO = new ScheduleActivityDataSetTO(); 
    	scheduleActivityDataSetTO.fromScheduleActivityDataSetReq(scheduleActivityDataSetReq);
    	scheduleActivityDataSetTO.setCurrent(true);
    	if (scheduleActivityDataSetRepository.findAllBy(scheduleActivityDataSetTO.getProjId(), scheduleActivityDataSetReq.getType()).size() == 0)
    		scheduleActivityDataSetTO.setBaseline(true);
    	scheduleActivityDataSetRepository.updateCurrent(scheduleActivityDataSetTO.getProjId(), scheduleActivityDataSetReq.getType());
    	ScheduleActivityDataSetEntity scheduleActivityDataSetEntity = scheduleActivityDataSetRepository.save(ScheduleActivityDataSetHandler.convertPOJOToEntity(scheduleActivityDataSetTO, ePSProjRepository, loginRepository));
    	List<ScheduleActivityDataEntity> scheduleActivityDataEntities = new ArrayList<ScheduleActivityDataEntity>();
    	for (ScheduleActivityDataTO scheduleActivityDataTO : scheduleActivityDataSetTO.getScheduleActivityDataTOs()) {
    		if (scheduleActivityDataTO.isValid()) {
	    		ScheduleActivityDataEntity scheduleActivityDataEntity = ScheduleActivityDataHandler.convertPOJOToEntity(scheduleActivityDataTO, scheduleActivityDataSetEntity.getProjMstrEntity().getProjectId(), projSOEItemRepositoryCopy, projSOWItemRepository, loginRepository);
	    		scheduleActivityDataEntity.setScheduleActivityDataSetEntity(scheduleActivityDataSetEntity);
	    		scheduleActivityDataEntities.add(scheduleActivityDataEntity);
    		}
    	}
    	scheduleActivityDataEntities = scheduleActivityDataRepository.save(scheduleActivityDataEntities);
    	
    	List<ProjSOWItemEntity> projSOWItemEntityCopies = new ArrayList<ProjSOWItemEntity>();
    	List<ScheduleActivityDataPredecessorSuccessorEntity> scheduleActivityDataPredecessorSuccessorEntities = new ArrayList<ScheduleActivityDataPredecessorSuccessorEntity>();
    	List<ScheduleActivityDataTO> savedScheduleActivityDataTOs = new ArrayList<ScheduleActivityDataTO>();
    	for (ScheduleActivityDataEntity scheduleActivityDataEntity : scheduleActivityDataEntities) {
    		ScheduleActivityDataTO scheduleActivityDataTO = ScheduleActivityDataHandler.convertEntityToPOJO(scheduleActivityDataEntity);
    		for (ScheduleActivityDataTO sadTO : scheduleActivityDataSetTO.getScheduleActivityDataTOs()) {
    			if (scheduleActivityDataEntity.getCode().equals(sadTO.getActivityCode())) {
    				if (sadTO.getPredecessors() != null && sadTO.getPredecessors().length() > 0) {
						String[] predecessors = sadTO.getPredecessors().split(",");
    					for (String predecessor : predecessors) {
    						for (ScheduleActivityDataEntity sadEntity : scheduleActivityDataEntities) {
    							if (sadEntity.getCode().equals(predecessor)) {
    								ScheduleActivityDataPredecessorSuccessorEntity scheduleActivityDataPredecessorSuccessorEntity = new ScheduleActivityDataPredecessorSuccessorEntity();
    			    				scheduleActivityDataPredecessorSuccessorEntity.setType("P");
    								scheduleActivityDataPredecessorSuccessorEntity.setCreatedBy(loginRepository.findOne(AppUserUtils.getUserId()));
    								scheduleActivityDataPredecessorSuccessorEntity.setUpdatedBy(loginRepository.findOne(AppUserUtils.getUserId()));
    								scheduleActivityDataPredecessorSuccessorEntity.setUpdatedOn(new Date());
    								scheduleActivityDataPredecessorSuccessorEntity.setScheduleActivityData(scheduleActivityDataEntity);
    	    						scheduleActivityDataPredecessorSuccessorEntity.setP6_ps_predecessor_successor_id(sadEntity);
    	    						scheduleActivityDataPredecessorSuccessorEntities.add(scheduleActivityDataPredecessorSuccessorEntity);
    	    						scheduleActivityDataTO.addPredecessor(ScheduleActivityDataHandler.convertEntityToPOJO(sadEntity));
    	    						break;
    							}
    						}
    					}
    				}
    				if (sadTO.getSuccessors() != null && sadTO.getSuccessors().length() > 0) {
						String[] successors = sadTO.getSuccessors().split(",");
    					for (String successor : successors) {
    						for (ScheduleActivityDataEntity sadEntity : scheduleActivityDataEntities) {
    							if (sadEntity.getCode().equals(successor)) {
    								ScheduleActivityDataPredecessorSuccessorEntity scheduleActivityDataPredecessorSuccessorEntity = new ScheduleActivityDataPredecessorSuccessorEntity();
    			    				scheduleActivityDataPredecessorSuccessorEntity.setType("S");
    								scheduleActivityDataPredecessorSuccessorEntity.setCreatedBy(loginRepository.findOne(AppUserUtils.getUserId()));
    								scheduleActivityDataPredecessorSuccessorEntity.setUpdatedBy(loginRepository.findOne(AppUserUtils.getUserId()));
    								scheduleActivityDataPredecessorSuccessorEntity.setUpdatedOn(new Date());
    								scheduleActivityDataPredecessorSuccessorEntity.setScheduleActivityData(scheduleActivityDataEntity);
    	    						scheduleActivityDataPredecessorSuccessorEntity.setP6_ps_predecessor_successor_id(sadEntity);
    	    						scheduleActivityDataPredecessorSuccessorEntities.add(scheduleActivityDataPredecessorSuccessorEntity);
    	    						scheduleActivityDataTO.addSuccessor(ScheduleActivityDataHandler.convertEntityToPOJO(sadEntity));
    	    						break;
    							}
    						}
    					}
    				}
    				break;
    			}
    		}
    		boolean sowFoundInList = false;
    		for (ProjSOWItemEntity projSOWItemEntityCopy : projSOWItemEntityCopies) {
    			if (projSOWItemEntityCopy.getProjSOEItemEntity().getId().equals(scheduleActivityDataTO.getSoeId())) {
    				sowFoundInList = true;
    				if (projSOWItemEntityCopy.getStartDate().after(scheduleActivityDataTO.getStartDate()))
    					projSOWItemEntityCopy.setStartDate(scheduleActivityDataTO.getStartDate());
    				if (projSOWItemEntityCopy.getFinishDate().before(scheduleActivityDataTO.getFinishDate()))
    					projSOWItemEntityCopy.setFinishDate(scheduleActivityDataTO.getFinishDate());
    				projSOWItemEntityCopy.setActualFinishDate(null);
    				if (scheduleActivityDataTO.isFinishDateFinal())
    					projSOWItemEntityCopy.setActualFinishDate(scheduleActivityDataTO.getFinishDate());
    				break;
    			}
    		}
    		if (!sowFoundInList && scheduleActivityDataTO.getSoeId() != null) {
    			ProjSOWItemEntity projSOWItemEntityCopy = projSOWItemRepository.findBy(scheduleActivityDataTO.getSoeId());
    			projSOWItemEntityCopy.setStartDate(scheduleActivityDataTO.getStartDate());
    			projSOWItemEntityCopy.setFinishDate(scheduleActivityDataTO.getFinishDate());
    			projSOWItemEntityCopy.setActualFinishDate(null);
    			if (scheduleActivityDataTO.isFinishDateFinal())
    				projSOWItemEntityCopy.setActualFinishDate(scheduleActivityDataTO.getFinishDate());
    			projSOWItemEntityCopies.add(projSOWItemEntityCopy);
    		}
    		savedScheduleActivityDataTOs.add(scheduleActivityDataTO);
    	}
    	
    	if (scheduleActivityDataPredecessorSuccessorEntities.size() > 0)
    		scheduleActivityDataPredecessorSuccessorRepository.save(scheduleActivityDataPredecessorSuccessorEntities);
    	if (projSOWItemEntityCopies.size() > 0)
    		projSOWItemRepository.save(projSOWItemEntityCopies);
    	
    	ScheduleActivityDataSetTO scheduleActivityDataSetTOOut = ScheduleActivityDataSetHandler.convertEntityToPOJO(scheduleActivityDataSetEntity);
    	scheduleActivityDataSetTOOut.setScheduleActivityDataTOs(savedScheduleActivityDataTOs);
    	
    	return scheduleActivityDataSetTOOut.toScheduleActivityDataSetResp();
    }
    
    public ScheduleActivityDataSetResp getScheduleActivity(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	if (scheduleActivityDataSetReq.getId() == null)
    		scheduleActivityDataSetReq.setId(scheduleActivityDataSetRepository.findOneCurrent(scheduleActivityDataSetReq.getProjId(), "A").getId());
    	ScheduleActivityDataSetTO scheduleActivityDataSetTO = ScheduleActivityDataSetHandler.convertEntityToPOJO(scheduleActivityDataSetRepository.findOne(scheduleActivityDataSetReq.getId()));
    	if (scheduleActivityDataSetTO.getType().equalsIgnoreCase("A"))
    		scheduleActivityDataSetTO.setScheduleActivityDataTOs(loadScheduleActivityDataSet(scheduleActivityDataSetReq.getId()));
    	else
    		scheduleActivityDataSetTO.setResourceAssignmentDataTOs(loadResourceAssignmentDataSet(scheduleActivityDataSetReq.getId()));
    	return scheduleActivityDataSetTO.toScheduleActivityDataSetResp();
    }
    
    private List<ResourceAssignmentDataTO> loadResourceAssignmentDataSet(Long id){
    	List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
    	List<ResourceAssignmentDataEntity> resourceAssignmentDataEntities = resourceAssignmentDataRepository.findAllBy(id);
    	for (ResourceAssignmentDataEntity resourceAssignmentDataEntity : resourceAssignmentDataEntities) {
    		resourceAssignmentDataTOs.add(ResourceAssignmentDataHandler.convertEntityToPOJO(resourceAssignmentDataEntity));
    	}
    	return resourceAssignmentDataTOs;
    }
    
    private List<ScheduleActivityDataTO> loadScheduleActivityDataSet(Long id) {
    	List<ScheduleActivityDataTO> scheduleActivityDataTOs = new ArrayList<ScheduleActivityDataTO>();
    	List<ScheduleActivityDataEntity> scheduleActivityDataEntities = scheduleActivityDataRepository.findAllBy(id);
    	for (ScheduleActivityDataEntity scheduleActivityDataEntity : scheduleActivityDataEntities) {
    		ScheduleActivityDataTO scheduleActivityDataTO = ScheduleActivityDataHandler.convertEntityToPOJO(scheduleActivityDataEntity);
    		List<ScheduleActivityDataPredecessorSuccessorEntity> scheduleActivityDataPredecessorSuccessorEntities = scheduleActivityDataPredecessorSuccessorRepository.findPredecessors(scheduleActivityDataTO.getId());
    		for (ScheduleActivityDataPredecessorSuccessorEntity scheduleActivityDataPredecessorSuccessorEntity : scheduleActivityDataPredecessorSuccessorEntities) {
    			scheduleActivityDataTO.addPredecessor(ScheduleActivityDataHandler.convertEntityToPOJO(scheduleActivityDataPredecessorSuccessorEntity.getP6_ps_predecessor_successor_id()));
    		}
    		scheduleActivityDataPredecessorSuccessorEntities = scheduleActivityDataPredecessorSuccessorRepository.findSucessors(scheduleActivityDataTO.getId());
    		for (ScheduleActivityDataPredecessorSuccessorEntity scheduleActivityDataPredecessorSuccessorEntity : scheduleActivityDataPredecessorSuccessorEntities) {
    			scheduleActivityDataTO.addSuccessor(ScheduleActivityDataHandler.convertEntityToPOJO(scheduleActivityDataPredecessorSuccessorEntity.getP6_ps_predecessor_successor_id()));
    		}
    		scheduleActivityDataTOs.add(scheduleActivityDataTO);
    	}
    	return scheduleActivityDataTOs;
    }
    
    public ScheduleActivityDataSetResp getScheduleActivityDatasetList(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ScheduleActivityDataSetResp scheduleActivityDataSetResp = new ScheduleActivityDataSetResp();
    	List<ScheduleActivityDataSetEntity> scheduleActivityDataSetEntities = scheduleActivityDataSetRepository.findAllBy(scheduleActivityDataSetReq.getProjId(), scheduleActivityDataSetReq.getType());
    	List<ScheduleActivityDataSetTO> scheduleActivityDataSetTOs = new ArrayList<ScheduleActivityDataSetTO>();
    	for (ScheduleActivityDataSetEntity scheduleActivityDataSetEntity : scheduleActivityDataSetEntities)
    		scheduleActivityDataSetTOs.add(ScheduleActivityDataSetHandler.convertEntityToPOJO(scheduleActivityDataSetEntity));
    	
    	scheduleActivityDataSetResp.setScheduleActivityDataSetTOs(scheduleActivityDataSetTOs);
    	return scheduleActivityDataSetResp;
    }
    
    public ScheduleActivityDataSetResp saveScheduleActivityDatasets(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	ScheduleActivityDataSetTO currentScheduleActivityDataSetTO = null;
    	scheduleActivityDataSetRepository.updateCurrentBaseline(scheduleActivityDataSetReq.getScheduleActivityDataSetTOs().get(0).getProjId(), scheduleActivityDataSetReq.getScheduleActivityDataSetTOs().get(0).getType());
    	for (ScheduleActivityDataSetTO scheduleActivityDataSetTO : scheduleActivityDataSetReq.getScheduleActivityDataSetTOs()) {
    		scheduleActivityDataSetRepository.save(ScheduleActivityDataSetHandler.convertPOJOToEntity(scheduleActivityDataSetTO, ePSProjRepository, loginRepository));
    		if (scheduleActivityDataSetTO.isCurrent())
    			currentScheduleActivityDataSetTO = scheduleActivityDataSetTO;
    	}
    	if (currentScheduleActivityDataSetTO.getType().equalsIgnoreCase("A"))
    		currentScheduleActivityDataSetTO.setScheduleActivityDataTOs(loadScheduleActivityDataSet(currentScheduleActivityDataSetTO.getId()));
    	else
    		currentScheduleActivityDataSetTO.setResourceAssignmentDataTOs(loadResourceAssignmentDataSet(currentScheduleActivityDataSetTO.getId()));
    	return currentScheduleActivityDataSetTO.toScheduleActivityDataSetResp();
    }
    
    public ScheduleActivityDataSetResp getActualActivityScheduleFor(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	List<ScheduleActivityDataSetTO> scheduleActivityDataSetTOs = new ArrayList<ScheduleActivityDataSetTO>();
    	for (ScheduleActivityDataSetTO reqScheduleActivityDataSetTO : scheduleActivityDataSetReq.getScheduleActivityDataSetTOs()) {
    		ScheduleActivityDataSetEntity scheduleActivityDataSetEntity = scheduleActivityDataSetRepository.findOne(reqScheduleActivityDataSetTO.getId());
			ScheduleActivityDataSetTO scheduleActivityDataSetTO = ScheduleActivityDataSetHandler.convertEntityToPOJO(scheduleActivityDataSetEntity);
			List<ScheduleActivityDataEntity> scheduleActivityDataEntities = scheduleActivityDataRepository.findAllBy(scheduleActivityDataSetTO.getId());
			List<ScheduleActivityDataTO> emptyScheduleActivityDataTOs = new ArrayList<ScheduleActivityDataTO>();
			scheduleActivityDataSetTO.setScheduleActivityDataTOs(emptyScheduleActivityDataTOs);
			for (ScheduleActivityDataEntity scheduleActivityDataEntity : scheduleActivityDataEntities)
				scheduleActivityDataSetTO.getScheduleActivityDataTOs().add(ScheduleActivityDataHandler.convertEntityToPOJO(scheduleActivityDataEntity));
			scheduleActivityDataSetTOs.add(scheduleActivityDataSetTO);
    	}
    	
    	ScheduleActivityDataSetResp scheduleActivityDataSetResp = new ScheduleActivityDataSetResp();
    	scheduleActivityDataSetResp.setScheduleActivityDataSetTOs(scheduleActivityDataSetTOs);
    	return scheduleActivityDataSetResp;
    }
    
    public ScheduleActivityDataSetResp getDateWiseForecastActualForResource(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	List<ScheduleActivityDataSetTO> scheduleActivityDataSetTOs = new ArrayList<ScheduleActivityDataSetTO>();
    	for (ScheduleActivityDataSetTO reqScheduleActivityDataSetTO : scheduleActivityDataSetReq.getScheduleActivityDataSetTOs()) {
    		if (reqScheduleActivityDataSetTO.getId() == -1) {
    			//current plan...
    			ScheduleActivityDataSetTO scheduleActivityDataSetTO = new ScheduleActivityDataSetTO();
    			scheduleActivityDataSetTO.setId(-1L);
    			List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
    			resourceAssignmentDataTOs.add(new ResourceAssignmentDataTO());
    			ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();
	        	resourceAssignmentDataTO.setResourceAssignmentDataValueTOs(getActualsForResource(scheduleActivityDataSetReq.getProjId(), scheduleActivityDataSetReq.getResourceIds()));
	        	resourceAssignmentDataTOs.add(resourceAssignmentDataTO);
    			scheduleActivityDataSetTO.setResourceAssignmentDataTOs(resourceAssignmentDataTOs);
	        	scheduleActivityDataSetTOs.add(scheduleActivityDataSetTO);
    		} else if (reqScheduleActivityDataSetTO.getId() == 0) {
    			//actuals...
    			ScheduleActivityDataSetTO scheduleActivityDataSetTO = new ScheduleActivityDataSetTO();
    			scheduleActivityDataSetTO.setId(0L);
    	    	List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
    	    	ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();
	        	resourceAssignmentDataTO.setResourceAssignmentDataValueTOs(getActualsForResource(scheduleActivityDataSetReq.getProjId(), scheduleActivityDataSetReq.getResourceIds()));
	        	resourceAssignmentDataTOs.add(resourceAssignmentDataTO);
	        	scheduleActivityDataSetTO.setResourceAssignmentDataTOs(resourceAssignmentDataTOs);
	        	scheduleActivityDataSetTOs.add(scheduleActivityDataSetTO);
    		} else {
    			ScheduleActivityDataSetEntity scheduleActivityDataSetEntity = scheduleActivityDataSetRepository.findOne(reqScheduleActivityDataSetTO.getId());
    			ScheduleActivityDataSetTO scheduleActivityDataSetTO = ScheduleActivityDataSetHandler.convertEntityToPOJO(scheduleActivityDataSetEntity);
    	    	List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
    	    	ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();
	    		List<Object[]> dateWiseForecasts = resourceAssignmentDataValueRepository.getDateWiseForecastForResourceId(reqScheduleActivityDataSetTO.getId(), scheduleActivityDataSetReq.getResourceIds());
	    		List<ResourceAssignmentDataValueTO> resourceAssignmentDataValueTOs = new ArrayList<ResourceAssignmentDataValueTO>();
	    		boolean foundInList = false;
	    		for (Object[] array : dateWiseForecasts) {
	    			foundInList = false;
	    			for (ResourceAssignmentDataValueTO resourceAssignmentDataValueTO : resourceAssignmentDataValueTOs) {
	            		if (resourceAssignmentDataValueTO.getForecastDate().equals((Date) array[0])) {
	            			resourceAssignmentDataValueTO.setBudget(resourceAssignmentDataValueTO.getBudget() + Double.valueOf(array[1].toString()));
	            			foundInList = true;
	            			break;
	            		}
	            	}
	    			if (!foundInList) {
		    			ResourceAssignmentDataValueTO resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
		    			resourceAssignmentDataValueTO.setForecastDate((Date) array[0]);
		    			resourceAssignmentDataValueTO.setBudget(Double.valueOf(array[1].toString()));
		    			resourceAssignmentDataValueTOs.add(resourceAssignmentDataValueTO);
	    			}
	    		}
	    		resourceAssignmentDataTO.setResourceAssignmentDataValueTOs(resourceAssignmentDataValueTOs);
	    		resourceAssignmentDataTOs.add(resourceAssignmentDataTO);
	        	scheduleActivityDataSetTO.setResourceAssignmentDataTOs(resourceAssignmentDataTOs);
	        	scheduleActivityDataSetTOs.add(scheduleActivityDataSetTO);
    		}
    	}

    	ScheduleActivityDataSetResp scheduleActivityDataSetResp = new ScheduleActivityDataSetResp();
    	scheduleActivityDataSetResp.setScheduleActivityDataSetTOs(scheduleActivityDataSetTOs);
    	return scheduleActivityDataSetResp;
    }
    
    private List<ResourceAssignmentDataValueTO> getActualsForResource(Long projectId, List<Long> resourceIds) {
    	List<ResourceAssignmentDataValueTO> actualResourceAssignmentDataValueTOs = getActualsForEmployee(projectId, resourceIds);
    	if (actualResourceAssignmentDataValueTOs.size() > 0)
    		return actualResourceAssignmentDataValueTOs;
    	actualResourceAssignmentDataValueTOs = getActualsForPlant(projectId, resourceIds);
    	if (actualResourceAssignmentDataValueTOs.size() > 0)
    		return actualResourceAssignmentDataValueTOs;
    	actualResourceAssignmentDataValueTOs = getActualsForMaterial(projectId, resourceIds);
    	if (actualResourceAssignmentDataValueTOs.size() > 0)
    		return actualResourceAssignmentDataValueTOs;
    	actualResourceAssignmentDataValueTOs = getActualsForCost(projectId, resourceIds);
    	if (actualResourceAssignmentDataValueTOs.size() > 0)
    		return actualResourceAssignmentDataValueTOs;
    	actualResourceAssignmentDataValueTOs = getActualsForTangibles(projectId, resourceIds);
    	if (actualResourceAssignmentDataValueTOs.size() > 0)
    		return actualResourceAssignmentDataValueTOs;
    	else
    		return null;
    }
    
    private List<ResourceAssignmentDataValueTO> getActualsForEmployee(Long projectId, List<Long> resourceIds) {
    	List<ResourceAssignmentDataValueTO> actualResourceAssignmentDataValueTOs = new ArrayList<ResourceAssignmentDataValueTO>();
    	List<Object[]> timesheetEmpHrs = timeSheetEmpDtlRepositoryCopy.getTimesheetEmpActualHrs(projectId, resourceIds);
        List<Object[]> workDairyEmpHrs = empWageWorkDairyRepositoryCopy.getManPowerActualHrs(projectId, resourceIds);
        Date startDate;
        Date endDate;
        int hrsIndex;
        for (Object[] timesheetHrs : timesheetEmpHrs) {
            startDate = (Date) timesheetHrs[1];
            endDate = (Date) timesheetHrs[2];
            hrsIndex = 3;
            while (startDate.before(endDate) || startDate.equals(endDate)) {
            	ResourceAssignmentDataValueTO resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
    			resourceAssignmentDataValueTO.setForecastDate(startDate);
    			if (timesheetHrs[hrsIndex] == null)
    				resourceAssignmentDataValueTO.setBudget(0D);
    			else    						
    				resourceAssignmentDataValueTO.setBudget(Double.valueOf(timesheetHrs[hrsIndex].toString()));
    			actualResourceAssignmentDataValueTOs.add(resourceAssignmentDataValueTO);
                hrsIndex++;
                startDate = Date.from(startDate.toInstant().plus(1, ChronoUnit.DAYS));
            }
        }
        boolean foundInList = false;
        for (Object[] workDairyHrs : workDairyEmpHrs) {
        	foundInList = false;
        	for (ResourceAssignmentDataValueTO actualResourceAssignmentDataValueTO : actualResourceAssignmentDataValueTOs) {
        		if (actualResourceAssignmentDataValueTO.getForecastDate().equals((Date) workDairyHrs[1])) {
        			actualResourceAssignmentDataValueTO.setBudget(actualResourceAssignmentDataValueTO.getBudget() + Double.valueOf(workDairyHrs[2].toString()));
        			foundInList = true;
        			break;
        		}
        	}
        	if (!foundInList) {
        		ResourceAssignmentDataValueTO resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
    			resourceAssignmentDataValueTO.setForecastDate((Date) workDairyHrs[1]);
    			if (workDairyHrs[2] == null)
    				resourceAssignmentDataValueTO.setBudget(0D);
    			else
    				resourceAssignmentDataValueTO.setBudget(Double.valueOf(workDairyHrs[2].toString()));
    			actualResourceAssignmentDataValueTOs.add(resourceAssignmentDataValueTO);
        	}
        }
        
        return actualResourceAssignmentDataValueTOs;
    }
    
    private List<ResourceAssignmentDataValueTO> getActualsForPlant(Long projectId, List<Long> resourceIds) {
    	List<ResourceAssignmentDataValueTO> actualResourceAssignmentDataValueTOs = new ArrayList<ResourceAssignmentDataValueTO>();
    	List<Object[]> materialQtyList = plantStatusWorkDairyRepositoryCopy.getMaterialActualQtyForSchedules(projectId, resourceIds);
    	boolean foundInList = false;
        for (Object[] materialQty : materialQtyList) {
        	foundInList = false;
        	for (ResourceAssignmentDataValueTO actualResourceAssignmentDataValueTO : actualResourceAssignmentDataValueTOs) {
        		if (actualResourceAssignmentDataValueTO.getForecastDate().equals((Date) materialQty[1])) {
        			actualResourceAssignmentDataValueTO.setBudget(actualResourceAssignmentDataValueTO.getBudget() + Double.valueOf(materialQty[2].toString()));
        			foundInList = true;
        			break;
        		}
        	}
        	if (!foundInList) {
	        	ResourceAssignmentDataValueTO resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
	        	resourceAssignmentDataValueTO.setForecastDate((Date) materialQty[1]);
				if (materialQty[2] == null)
					resourceAssignmentDataValueTO.setBudget(0D);
				else
					resourceAssignmentDataValueTO.setBudget(Double.valueOf(materialQty[2].toString()));
				actualResourceAssignmentDataValueTOs.add(resourceAssignmentDataValueTO);
        	}
        }
        
        return actualResourceAssignmentDataValueTOs;
    }
    
    private List<ResourceAssignmentDataValueTO> getActualsForMaterial(Long projectId, List<Long> resourceIds) {
    	List<ResourceAssignmentDataValueTO> actualResourceAssignmentDataValueTOs = new ArrayList<ResourceAssignmentDataValueTO>();
    	List<Object[]> materialActualQuantity = materialStatusWorkDairyRepositoryCopy.getMaterialActualQtyForSchedules(projectId, resourceIds);
    	boolean foundInList = false;
        for (Object[] matActualQty : materialActualQuantity) {
        	foundInList = false;
        	for (ResourceAssignmentDataValueTO actualResourceAssignmentDataValueTO : actualResourceAssignmentDataValueTOs) {
        		if (actualResourceAssignmentDataValueTO.getForecastDate().equals((Date) matActualQty[1])) {
        			actualResourceAssignmentDataValueTO.setBudget(actualResourceAssignmentDataValueTO.getBudget() + Double.valueOf(matActualQty[2].toString()));
        			foundInList = true;
        			break;
        		}
        	}
        	if (!foundInList) {
	        	ResourceAssignmentDataValueTO resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
	        	resourceAssignmentDataValueTO.setForecastDate((Date) matActualQty[1]);
				if (matActualQty[2] == null)
					resourceAssignmentDataValueTO.setBudget(0D);
				else
					resourceAssignmentDataValueTO.setBudget(Double.valueOf(matActualQty[2].toString()));
				actualResourceAssignmentDataValueTOs.add(resourceAssignmentDataValueTO);
        	}
        }
        
    	return actualResourceAssignmentDataValueTOs;
    }
    
    private List<ResourceAssignmentDataValueTO> getActualsForCost(Long projectId, List<Long> resourceIds) {
    	List<ResourceAssignmentDataValueTO> actualResourceAssignmentDataValueTOs = getActualsForEmployee(projectId, resourceIds);
        List<Object[]> materialQuantity = materialCostWorkDairyRepositoryCopy.getMaterialCostDetails(projectId, resourceIds);
        List<Object[]> plantQuantity = plantCostWorkDairyRepositoryCopy.getPlantCostDetails(projectId, resourceIds);
        
        boolean foundInList = false;
        for (Object[] materialDetails : materialQuantity) {
        	foundInList = false;
        	for (ResourceAssignmentDataValueTO actualResourceAssignmentDataValueTO : actualResourceAssignmentDataValueTOs) {
        		if (actualResourceAssignmentDataValueTO.getForecastDate().equals((Date) materialDetails[1])) {
        			actualResourceAssignmentDataValueTO.setBudget(actualResourceAssignmentDataValueTO.getBudget() + Double.valueOf(materialDetails[2].toString()));
        			foundInList = true;
        			break;
        		}
        	}
        	if (!foundInList) {
        		ResourceAssignmentDataValueTO resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
    			resourceAssignmentDataValueTO.setForecastDate((Date) materialDetails[1]);
    			if (materialDetails[2] == null)
    				resourceAssignmentDataValueTO.setBudget(0D);
    			else
    				resourceAssignmentDataValueTO.setBudget(Double.valueOf(materialDetails[2].toString()));
    			actualResourceAssignmentDataValueTOs.add(resourceAssignmentDataValueTO);
        	}
    	}
        
        for (Object[] plantDetails : plantQuantity) {
        	foundInList = false;
        	for (ResourceAssignmentDataValueTO actualResourceAssignmentDataValueTO : actualResourceAssignmentDataValueTOs) {
        		if (actualResourceAssignmentDataValueTO.getForecastDate().equals((Date) plantDetails[1])) {
        			actualResourceAssignmentDataValueTO.setBudget(actualResourceAssignmentDataValueTO.getBudget() + Double.valueOf(plantDetails[2].toString()));
        			foundInList = true;
        			break;
        		}
        	}
        	if (!foundInList) {
        		ResourceAssignmentDataValueTO resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
    			resourceAssignmentDataValueTO.setForecastDate((Date) plantDetails[1]);
    			if (plantDetails[2] == null)
    				resourceAssignmentDataValueTO.setBudget(0D);
    			else
    				resourceAssignmentDataValueTO.setBudget(Double.valueOf(plantDetails[2].toString()));
    			actualResourceAssignmentDataValueTOs.add(resourceAssignmentDataValueTO);
        	}
        }

        return actualResourceAssignmentDataValueTOs;
    }

    private List<ResourceAssignmentDataValueTO> getActualsForTangibles(Long projectId, List<Long> resourceIds) {
    	List<ResourceAssignmentDataValueTO> actualResourceAssignmentDataValueTOs = new ArrayList<ResourceAssignmentDataValueTO>();
    	List<Object[]> actuals = projSowTotalActualRepository.getActualTangibles(projectId, resourceIds);
    	boolean foundInList = false;
        for (Object[] actual : actuals) {
        	foundInList = false;
        	for (ResourceAssignmentDataValueTO actualResourceAssignmentDataValueTO : actualResourceAssignmentDataValueTOs) {
        		if (actualResourceAssignmentDataValueTO.getForecastDate().equals((Date) actual[1])) {
        			actualResourceAssignmentDataValueTO.setBudget(actualResourceAssignmentDataValueTO.getBudget() + Double.valueOf(actual[2].toString()));
        			foundInList = true;
        			break;
        		}
        	}
        	if (!foundInList) {
	        	ResourceAssignmentDataValueTO resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
	        	resourceAssignmentDataValueTO.setForecastDate((Date) actual[1]);
				if (actual[2] == null)
					resourceAssignmentDataValueTO.setBudget(0D);
				else
					resourceAssignmentDataValueTO.setBudget(Double.valueOf(actual[2].toString()));
				actualResourceAssignmentDataValueTOs.add(resourceAssignmentDataValueTO);
        	}
        }
        
    	return actualResourceAssignmentDataValueTOs;
    }
    
    public ProjScheduleSOWResp getTangibles(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
    	ProjScheduleSOWResp projScheduleSOWResp = new ProjScheduleSOWResp();
    	Long resourceCurveId = projGeneralRepository.getProjDefaultCurve(projScheduleBaseLineGetReq.getProjId());
    	List<Object[]> objects = scopeOfWorkRepository.findAllTangible(projScheduleBaseLineGetReq.getProjId());
    	for (Object[] object : objects) {
    		ProjScheduleSOWTO projScheduleSOWTO = new ProjScheduleSOWTO();
    		projScheduleSOWTO.setTangibleClassTO(new TangibleClassTO());
    		projScheduleSOWTO.getTangibleClassTO().setId(Long.valueOf(object[0].toString()));
    		projScheduleSOWTO.getTangibleClassTO().setCode(object[1].toString());
    		projScheduleSOWTO.getTangibleClassTO().setName(object[2].toString());
    		projScheduleSOWTO.getTangibleClassTO().setMeasureUnitTO(new MeasureUnitTO());
    		projScheduleSOWTO.getTangibleClassTO().getMeasureUnitTO().setName(object[3].toString());
    		projScheduleSOWTO.setOriginalQty(new BigDecimal(object[4].toString()));
    		if (object[5] == null)
    			projScheduleSOWTO.setRevisedQty(new BigDecimal("0"));
    		else
    			projScheduleSOWTO.setRevisedQty(new BigDecimal(object[5].toString()));
    		if (object[6] == null)
    			projScheduleSOWTO.setActualQty(new BigDecimal("0"));
    		else
    			projScheduleSOWTO.setActualQty(new BigDecimal(object[6].toString()));
    		projScheduleSOWTO.setStartDt(CommonUtil.getDateFromStrYYYYMMDDWithDash(object[7].toString()));
    		projScheduleSOWTO.setFinishDt(CommonUtil.getDateFromStrYYYYMMDDWithDash(object[8].toString()));
    		ResourceCurveMappingEntity resourceCurveMappingEntity = resourceCurveMappingRepository.findBy(Long.valueOf(object[0].toString()), ResourceAssignmentDataHandler.POT_TANGIBLE);
    		if (resourceCurveMappingEntity == null)
    			projScheduleSOWTO.setResourceCurveId(resourceCurveId);
    		else
    			projScheduleSOWTO.setResourceCurveId(resourceCurveMappingEntity.getProjResourceCurveEntity().getId());
    		projScheduleSOWTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), Long.valueOf(object[0].toString())));
    		projScheduleSOWTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), Long.valueOf(object[0].toString())));
            projScheduleSOWResp.getProjScheduleSOWTOs().add(projScheduleSOWTO);
    	}
    	
    	projScheduleSOWResp.setRegularHolidays(getCalendarRegularDays(projScheduleBaseLineGetReq.getProjId(), projScheduleBaseLineGetReq.getStatus()));
    	projScheduleSOWResp.setCalNonWorkingDays(globalCalSpecialDaysRepository.findProjCalNonWorkingDays(projScheduleBaseLineGetReq.getProjId()));
    	projScheduleSOWResp.setCalSplWorkingDays(globalCalSpecialDaysRepository.findProjCalSpecialWorkingDays(projScheduleBaseLineGetReq.getProjId()));
    	projScheduleSOWResp.setProjReportsTo(getProjectReports(projScheduleBaseLineGetReq.getProjId()));
    	return projScheduleSOWResp;
    }
    
    public ProjScheduleSOWResp getScheduleOfRates(ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq) {
    	ProjScheduleSOWResp projScheduleSOWResp = new ProjScheduleSOWResp();
    	ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setProjId(projScheduleBaseLineGetReq.getProjId());
        projGeneralsGetReq.setStatus(1);
        Long resourceCurveId = projGeneralRepository.getProjDefaultCurve(projScheduleBaseLineGetReq.getProjId());
    	ProjGeneralsResp projGeneralsResp = projSettingsService.getProjGenerals(projGeneralsGetReq);
    	List<Object[]> objects = scopeOfWorkRepository.findAllScheduleOfRate(projScheduleBaseLineGetReq.getProjId());
    	for (Object[] object : objects) {
    		ProjScheduleSOWTO projScheduleSOWTO = new ProjScheduleSOWTO();
    		projScheduleSOWTO.setScheduleOfRateTO(new ProjSORItemTO());
    		projScheduleSOWTO.getScheduleOfRateTO().setId(Long.valueOf(object[0].toString()));
    		projScheduleSOWTO.getScheduleOfRateTO().setCode(object[1].toString());
    		projScheduleSOWTO.getScheduleOfRateTO().setName(object[2].toString());
    		projScheduleSOWTO.getScheduleOfRateTO().setMeasureUnitTO(new MeasureUnitTO());
    		projScheduleSOWTO.getScheduleOfRateTO().getMeasureUnitTO().setName(projGeneralsResp.getProjGeneralMstrTO().getCurrency());
    		projScheduleSOWTO.setOriginalQty(new BigDecimal(object[3].toString()));
   			projScheduleSOWTO.setRevisedQty(new BigDecimal(object[4].toString()));
   			projScheduleSOWTO.setActualQty(new BigDecimal(object[5].toString()));
    		projScheduleSOWTO.setStartDt(CommonUtil.getDateFromStrYYYYMMDDWithDash(object[6].toString()));
    		projScheduleSOWTO.setFinishDt(CommonUtil.getDateFromStrYYYYMMDDWithDash(object[7].toString()));
    		ResourceCurveMappingEntity resourceCurveMappingEntity = resourceCurveMappingRepository.findBy(Long.valueOf(object[0].toString()), ResourceAssignmentDataHandler.POT_SOR);
    		if (resourceCurveMappingEntity == null)
    			projScheduleSOWTO.setResourceCurveId(resourceCurveId);
    		else
    			projScheduleSOWTO.setResourceCurveId(resourceCurveMappingEntity.getProjResourceCurveEntity().getId());
    		projScheduleSOWTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), Long.valueOf(object[0].toString())));
    		projScheduleSOWTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projScheduleBaseLineGetReq.getProjId(), Long.valueOf(object[0].toString())));
            projScheduleSOWResp.getProjScheduleSOWTOs().add(projScheduleSOWTO);
    	}
    	
    	projScheduleSOWResp.setRegularHolidays(getCalendarRegularDays(projScheduleBaseLineGetReq.getProjId(), projScheduleBaseLineGetReq.getStatus()));
    	projScheduleSOWResp.setCalNonWorkingDays(globalCalSpecialDaysRepository.findProjCalNonWorkingDays(projScheduleBaseLineGetReq.getProjId()));
    	projScheduleSOWResp.setCalSplWorkingDays(globalCalSpecialDaysRepository.findProjCalSpecialWorkingDays(projScheduleBaseLineGetReq.getProjId()));
    	projScheduleSOWResp.setProjReportsTo(getProjectReports(projScheduleBaseLineGetReq.getProjId()));
    	return projScheduleSOWResp;
    }
    
    public ScheduleActivityGanttDataResp getActualActivityScheduleForGanttChart(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
    	List<ScheduleActivityDataSetTO> scheduleActivityDataSetTOs = new ArrayList<ScheduleActivityDataSetTO>();
    	for (ScheduleActivityDataSetTO reqScheduleActivityDataSetTO : scheduleActivityDataSetReq.getScheduleActivityDataSetTOs()) {
    		ScheduleActivityDataSetEntity scheduleActivityDataSetEntity = scheduleActivityDataSetRepository.findOne(reqScheduleActivityDataSetTO.getId());
			ScheduleActivityDataSetTO scheduleActivityDataSetTO = ScheduleActivityDataSetHandler.convertEntityToPOJO(scheduleActivityDataSetEntity);
			List<ScheduleActivityDataEntity> scheduleActivityDataEntities = scheduleActivityDataRepository.findAllBy(scheduleActivityDataSetTO.getId());
			List<ScheduleActivityDataTO> emptyScheduleActivityDataTOs = new ArrayList<ScheduleActivityDataTO>();
			scheduleActivityDataSetTO.setScheduleActivityDataTOs(emptyScheduleActivityDataTOs);
			for (ScheduleActivityDataEntity scheduleActivityDataEntity : scheduleActivityDataEntities) {
				ScheduleActivityDataTO scheduleActivityDataTO = ScheduleActivityDataHandler.convertEntityToPOJO(scheduleActivityDataEntity);
				List<ScheduleActivityDataPredecessorSuccessorEntity> scheduleActivityDataPredecessorSuccessorEntities = scheduleActivityDataPredecessorSuccessorRepository.findPredecessors(scheduleActivityDataTO.getId());
	    		for (ScheduleActivityDataPredecessorSuccessorEntity scheduleActivityDataPredecessorSuccessorEntity : scheduleActivityDataPredecessorSuccessorEntities) {
	    			scheduleActivityDataTO.addPredecessor(ScheduleActivityDataHandler.convertEntityToPOJO(scheduleActivityDataPredecessorSuccessorEntity.getP6_ps_predecessor_successor_id()));
	    		}
	    		scheduleActivityDataPredecessorSuccessorEntities = scheduleActivityDataPredecessorSuccessorRepository.findSucessors(scheduleActivityDataTO.getId());
	    		for (ScheduleActivityDataPredecessorSuccessorEntity scheduleActivityDataPredecessorSuccessorEntity : scheduleActivityDataPredecessorSuccessorEntities) {
	    			scheduleActivityDataTO.addSuccessor(ScheduleActivityDataHandler.convertEntityToPOJO(scheduleActivityDataPredecessorSuccessorEntity.getP6_ps_predecessor_successor_id()));
	    		}
	    		scheduleActivityDataTO.setMinStartDateOfBaseline(scheduleActivityDataRepository.findStartDateOfBaselineBy(scheduleActivityDataSetTO.getProjId(), scheduleActivityDataTO.getActivityCode()));
	    		scheduleActivityDataTO.setMaxFinishDateOfBaseline(scheduleActivityDataRepository.findFinishDateOfBaselineBy(scheduleActivityDataSetTO.getProjId(), scheduleActivityDataTO.getActivityCode()));
	    		scheduleActivityDataSetTO.getScheduleActivityDataTOs().add(scheduleActivityDataTO);
			}
			scheduleActivityDataSetTOs.add(scheduleActivityDataSetTO);
    	}
    	
    	List<ScheduleActivityDataSetTO> scheduleActivityDataSetTOsWithGroup = new ArrayList<ScheduleActivityDataSetTO>();
    	for (ScheduleActivityDataSetTO scheduleActivityDataSetTO : scheduleActivityDataSetTOs) {
    		
    		ScheduleActivityDataSetTO scheduleActivityDataSetTOGroup = new ScheduleActivityDataSetTO();
    		scheduleActivityDataSetTOGroup.setId(scheduleActivityDataSetTO.getId());
    		scheduleActivityDataSetTOGroup.setBaseline(scheduleActivityDataSetTO.isBaseline());
    		scheduleActivityDataSetTOGroup.setCurrent(scheduleActivityDataSetTO.isCurrent());
    		scheduleActivityDataSetTOGroup.setDatasetName(scheduleActivityDataSetTO.getDatasetName());
    		scheduleActivityDataSetTOGroup.setType(scheduleActivityDataSetTO.getType());
    		List<ScheduleActivityDataTO> scheduleActivityDataTOs = new ArrayList<ScheduleActivityDataTO>();
    		scheduleActivityDataSetTOGroup.setScheduleActivityDataTOs(scheduleActivityDataTOs);
    		
			for (ScheduleActivityDataTO scheduleActivityDataTO : scheduleActivityDataSetTO.getScheduleActivityDataTOs()) {
				if (scheduleActivityDataTO.getWbsCode() != null) {
					boolean existance = false;
					for (ScheduleActivityDataTO check : scheduleActivityDataSetTOGroup.getScheduleActivityDataTOs())
						existance = check.getWbsPath().equals(scheduleActivityDataTO.getWbsPath());
					if (!existance) {
						ScheduleActivityDataTO groupTO = new ScheduleActivityDataTO();
						groupTO.setId(scheduleActivityDataTO.getId() * -1);
						groupTO.setActivityCode(null);
						groupTO.setActivityName(null);
						groupTO.setCritical(null);
						groupTO.setLeadLag(0);
						groupTO.setPhysicalComplete(null);
						groupTO.setPredecessors(null);
						groupTO.setSoeCode(null);
						groupTO.setSuccessors(null);
						groupTO.setWbsCode(null);
						groupTO.setWbsPath(scheduleActivityDataTO.getWbsPath());
						groupTO.setWbsName(scheduleActivityDataTO.getWbsName());
						groupTO.setStartDate(scheduleActivityDataTO.getStartDate());
						groupTO.setFinishDate(scheduleActivityDataTO.getFinishDate());
						scheduleActivityDataSetTOGroup.getScheduleActivityDataTOs().add(groupTO);
					}
				}
				scheduleActivityDataSetTOGroup.getScheduleActivityDataTOs().add(scheduleActivityDataTO);
			}
			scheduleActivityDataSetTOsWithGroup.add(scheduleActivityDataSetTOGroup);
		}
    	
    	//transform to gantt chart json
    	ScheduleActivityGanttDataResp scheduleActivityGanttDataResp = new ScheduleActivityGanttDataResp();
    	for (ScheduleActivityDataSetTO scheduleActivityDataSetTO : scheduleActivityDataSetTOsWithGroup) {
    		if (scheduleActivityDataSetTO.isCurrent()) {
    			for (ScheduleActivityDataTO scheduleActivityDataTO : scheduleActivityDataSetTO.getScheduleActivityDataTOs()) {
    				if (scheduleActivityDataTO.getWbsCode() == null) {
	    				List<GanttChartRowTO> ganttChartRowTOs = getActivitiesGanttRows(scheduleActivityDataSetTOsWithGroup, scheduleActivityDataSetTO, scheduleActivityDataTO);
	    				for (GanttChartRowTO ganttChartRowTO : ganttChartRowTOs)
	    					scheduleActivityGanttDataResp.getGanttChartRow().add(ganttChartRowTO);
    				}
    			}
    		}
    	}
    	return scheduleActivityGanttDataResp;
    }
    
    private List<GanttChartRowTO> getActivitiesGanttRows(List<ScheduleActivityDataSetTO> scheduleActivityDataSetTOsWithGroup, ScheduleActivityDataSetTO currentScheduleActivityDataSetTO, ScheduleActivityDataTO scheduleActivityDataTO) {
    	List<GanttChartRowTO> ganttChartRowTOs = new ArrayList<GanttChartRowTO>();
    	List<ScheduleActivityDataTO> wbsPathData = currentScheduleActivityDataSetTO.getScheduleActivityDataTOs().stream().filter(e -> e.getWbsPath().equals(scheduleActivityDataTO.getWbsPath())).collect(Collectors.toList());
    	List<ScheduleActivityDataTO> children = getChildrenOfWbsPath(currentScheduleActivityDataSetTO, scheduleActivityDataTO);
    	    	
    	if (wbsPathData.size() == 1) {
    		GanttChartRowTO ganttChartGroupRowTO = new GanttChartRowTO(wbsPathData.get(0).getId(), wbsPathData.get(0).getWbsName(), wbsPathData.get(0));
    		ganttChartGroupRowTO.setClasses(new String[] {"row-height1"});
    		for (ScheduleActivityDataTO child : children)
    			ganttChartGroupRowTO.getChildren().add(child.getId());
    		ganttChartRowTOs.add(ganttChartGroupRowTO);
    	} else {
    		List<ScheduleActivityDataTO> groupData = wbsPathData.stream().filter(e -> e.getWbsCode() == null).collect(Collectors.toList());
    		GanttChartRowTO ganttChartGroupRowTO = new GanttChartRowTO(groupData.get(0).getId(), groupData.get(0).getWbsName(), groupData.get(0));
    		ganttChartGroupRowTO.setClasses(new String[] {"row-height1"});
    		for (ScheduleActivityDataTO child : children)
    			ganttChartGroupRowTO.getChildren().add(child.getId());
    		ganttChartRowTOs.add(ganttChartGroupRowTO);
    		
    		for (ScheduleActivityDataTO child : wbsPathData) {
    			if (child.getWbsCode() == null) continue;
    			GanttChartRowTO ganttChartTaskRowTO = new GanttChartRowTO(child.getId(), child.getActivityName(), child);
    			GanttChartTaskTO ganttChartTaskTO = new GanttChartTaskTO(child.getId(), child.getActivityCode(), child.getStartDate(), child.getFinishDate(), new GanttChartProgress(child.getPhysicalComplete(), "#00ff00"), 0, ganttChartDatasetColors[0]);
    			ganttChartTaskTO.setClasses(new String[] {"bar-margin", "bar-height" + scheduleActivityDataSetTOsWithGroup.size()});
    			for (ScheduleActivityDataTO predecessors : child.getPredecessorTOs()) {
    				GanttChartDependency ganttChartDependency = new GanttChartDependency();
    				ganttChartDependency.setFrom(predecessors.getId());
    				ganttChartTaskTO.getDependencies().add(ganttChartDependency);
    			}
    			for (ScheduleActivityDataTO successors : child.getSuccessorTOs()) {
    				GanttChartDependency ganttChartDependency = new GanttChartDependency();
    				ganttChartDependency.setTo(successors.getId());
    				ganttChartTaskTO.getDependencies().add(ganttChartDependency);
    			}
    			ganttChartTaskRowTO.getTasks().add(ganttChartTaskTO);
    			
    			int i = 0;
        		for (ScheduleActivityDataSetTO scheduleActivityDataSetTO : scheduleActivityDataSetTOsWithGroup) {
            		if (!scheduleActivityDataSetTO.isCurrent()) {
            			i++;
            			List<ScheduleActivityDataTO> otherDatasetChildren = scheduleActivityDataSetTO.getScheduleActivityDataTOs().stream().filter(e -> (e.getActivityCode() != null && e.getActivityCode().equals(child.getActivityCode()))).collect(Collectors.toList());
            			for (ScheduleActivityDataTO otherChild : otherDatasetChildren) {  
            				GanttChartTaskTO ganttChartOtherTaskTO = new GanttChartTaskTO(otherChild.getId(), otherChild.getActivityCode(), otherChild.getStartDate(), otherChild.getFinishDate(), new GanttChartProgress(otherChild.getPhysicalComplete(), "#00ff00"), i, ganttChartDatasetColors[i]);
            				ganttChartOtherTaskTO.setClasses(new String[] {"bar-margin", "bar-height" + (scheduleActivityDataSetTOsWithGroup.size() - i)});
            				ganttChartTaskRowTO.getTasks().add(ganttChartOtherTaskTO);
            			}
            		}
        		}   			
        		ganttChartTaskRowTO.setClasses(new String[] {"row-height" + ganttChartTaskRowTO.getTasks().size()});
    			ganttChartRowTOs.add(ganttChartTaskRowTO);
			}
    	}
    	return ganttChartRowTOs;
    }
    
    private List<ScheduleActivityDataTO> getChildrenOfWbsPath(ScheduleActivityDataSetTO scheduleActivityDataSetTO, ScheduleActivityDataTO scheduleActivityDataTO) {
    	List<ScheduleActivityDataTO> children = new ArrayList<ScheduleActivityDataTO>();
    	
    	if (scheduleActivityDataTO.getWbsPath().equals("0")) {
    		for (ScheduleActivityDataTO iterator : scheduleActivityDataSetTO.getScheduleActivityDataTOs()) {
    			if (iterator.getWbsPath() != scheduleActivityDataTO.getWbsPath() && iterator.getWbsPath().split("\\.").length == 1 && iterator.getWbsCode() == null)
    				children.add(iterator);
    		}
    	} else {
    		for (ScheduleActivityDataTO iterator : scheduleActivityDataSetTO.getScheduleActivityDataTOs()) {
				if (iterator.getWbsPath() != scheduleActivityDataTO.getWbsPath() && iterator.getWbsPath().startsWith(scheduleActivityDataTO.getWbsPath()) && iterator.getWbsPath().split("\\.").length == scheduleActivityDataTO.getWbsPath().split("\\.").length + 1 && iterator.getWbsCode() == null)
					children.add(iterator);
			}
			for (ScheduleActivityDataTO iterator : scheduleActivityDataSetTO.getScheduleActivityDataTOs()) {
				if (iterator.getWbsPath().equals(scheduleActivityDataTO.getWbsPath()) && iterator.getWbsCode() != null)
					children.add(iterator);
			}
    	}
    	return children;
    }

	@Override
	public ScheduleActivityDataSetResp prepareResourceAssignmentData(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
		ScheduleActivityDataSetResp scheduleActivityDataSetResp = new ScheduleActivityDataSetResp();
		List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
		
		ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setProjId(scheduleActivityDataSetReq.getProjId());
        projGeneralsGetReq.setStatus(1);
		ProjGeneralsResp projGeneralsResp = projSettingsService.getProjGenerals(projGeneralsGetReq);
				
		List<ProjSOWItemEntity> projectSOWItemEntities = projSOWItemRepository.findSOWItems(scheduleActivityDataSetReq.getProjId(), 1);
		
		ProjManpowerGetReq projManpowerGetReq = new ProjManpowerGetReq();
		projManpowerGetReq.setStatus(1);
		projManpowerGetReq.setProjId(scheduleActivityDataSetReq.getProjId());
		ProjManPowerResp projManPowerResp = projSettingsService.getProjManPowers(projManpowerGetReq);
		for (ProjManpowerTO projManpowerTO : projManPowerResp.getProjManpowerTOs())
			resourceAssignmentDataTOs.add(ResourceAssignmentDataHandler.fromProjManpowerTO(projManpowerTO, projGeneralsResp));
		
		ProjectPlantsGetReq projectPlantsGetReq = new ProjectPlantsGetReq();
		projectPlantsGetReq.setStatus(1);
		projectPlantsGetReq.setProjId(scheduleActivityDataSetReq.getProjId());
		ProjectPlantsResp projectPlantsResp = projSettingsService.getProjectPlants(projectPlantsGetReq);
		for (ProjectPlantsDtlTO projectPlantsDtlTO : projectPlantsResp.getProjectPlantsDtlTOs())
			resourceAssignmentDataTOs.add(ResourceAssignmentDataHandler.fromProjectPlantsDtlTO(projectPlantsDtlTO, projGeneralsResp));
		
		ProjectMaterialGetReq projectMaterialGetReq = new ProjectMaterialGetReq();
		projectMaterialGetReq.setStatus(1);
		projectMaterialGetReq.setProjId(scheduleActivityDataSetReq.getProjId());
		ProjectMaterialsResp projectMaterialsResp = projSettingsService.getProjectMaterials(projectMaterialGetReq);
		for (ProjectMaterialDtlTO projectMaterialDtlTO : projectMaterialsResp.getProjectMaterialDtlTOs())
			resourceAssignmentDataTOs.addAll(projectMaterialDtlTOItemsToResourceAssignmentDataTOs(projectMaterialDtlTO, projGeneralsResp));
		
		ProjCostStatementsGetReq projCostStatementsGetReq = new ProjCostStatementsGetReq();
		projCostStatementsGetReq.setStatus(1);
		projCostStatementsGetReq.setProjId(scheduleActivityDataSetReq.getProjId());
		ProjCostStaementsResp projCostStaementsResp = projSettingsService.getProjCostStatements(projCostStatementsGetReq);
		for (ProjCostStmtDtlTO projCostStmtDtlTO : projCostStaementsResp.getProjCostStmtDtlTOs())
			resourceAssignmentDataTOs.addAll(projCostStmtDtlTOItemsToResourceAssignmentDataTOs(projCostStmtDtlTO, projGeneralsResp, projectSOWItemEntities));
		
		List<ProjSORItemEntity> projSORItemEntities = projSORItemRepository.findSORDetails(scheduleActivityDataSetReq.getProjId(), 1);
		for (ProjSORItemEntity projSORItemEntityCopy : projSORItemEntities)
			resourceAssignmentDataTOs.addAll(projSORItemEntityCopyToResourceAssignmentDataTOs(projSORItemEntityCopy, projGeneralsResp, projectSOWItemEntities));
		
		List<ProjSOWItemEntity> projSOWItemEntities = projSOWItemRepository.findAllTangibleItems(scheduleActivityDataSetReq.getProjId(), 1);
		resourceAssignmentDataTOs.addAll(ProjSOWItemEntityCopyToResourceAssignmentDataTOs(projSOWItemEntities, projGeneralsResp));
		
		scheduleActivityDataSetResp.setResourceAssignmentDataTableTOs(setResourceAssignmentDataValueTOs(scheduleActivityDataSetReq.getProjId(), scheduleActivityDataSetReq.getScheduleDate(), projGeneralsResp, resourceAssignmentDataTOs));
		scheduleActivityDataSetResp.setPrimaveraIntegrated(false);
		scheduleActivityDataSetResp.setScheduleDate(scheduleActivityDataSetReq.getScheduleDate());
		scheduleActivityDataSetResp.setDatasetName(scheduleActivityDataSetReq.getDatasetName());
		return scheduleActivityDataSetResp;
	}
	
	private List<ResourceAssignmentDataTO> setResourceAssignmentDataValueTOs(Long projectId, Date scheduleDate, ProjGeneralsResp projGeneralsResp, List<ResourceAssignmentDataTO> resourceAssignmentDataTOs) {
		CalRegularDaysTO calRegularDaysTO = getCalendarRegularDays(projectId, 1);
		List<String> setCalNonWorkingDays = globalCalSpecialDaysRepository.findProjCalNonWorkingDays(projectId);
        List<String> setCalSplWorkingDays = globalCalSpecialDaysRepository.findProjCalSpecialWorkingDays(projectId);
        ResourceCurveEntity projectSettgingresourceCurveEntity = resourceCurveRepository.findResourceCurves(1, AppUserUtils.getClientId()).stream().filter(r -> r.getId() == projGeneralsResp.getProjGeneralMstrTO().getResourceCurveTO().getId()).findAny().orElse(null);
        ResourceCurveEntity resourceCurveEntity = new ResourceCurveEntity();
        Calendar minStartDate = Calendar.getInstance();
        Calendar maxFinishDate = Calendar.getInstance();
		Calendar todaysDate = Calendar.getInstance();
			
		for (ResourceAssignmentDataTO resourceAssignmentDataTO : resourceAssignmentDataTOs) {
			if (resourceAssignmentDataTO.getStartDate() != null) {
				if (DateUtils.isSameDay(minStartDate, todaysDate)) minStartDate.setTime(resourceAssignmentDataTO.getStartDate());
				if (minStartDate.getTime().compareTo(resourceAssignmentDataTO.getStartDate()) > 0) minStartDate.setTime(resourceAssignmentDataTO.getStartDate());
			}
			if (resourceAssignmentDataTO.getFinishDate() != null) {
				if (DateUtils.isSameDay(maxFinishDate, todaysDate)) maxFinishDate.setTime(resourceAssignmentDataTO.getFinishDate());
				if (maxFinishDate.getTime().compareTo(resourceAssignmentDataTO.getFinishDate()) < 0) maxFinishDate.setTime(resourceAssignmentDataTO.getFinishDate());
			}
		}
		
        for (ResourceAssignmentDataTO resourceAssignmentDataTO : resourceAssignmentDataTOs) {
        	Calendar runningDate = Calendar.getInstance();
        	Calendar finishDate = Calendar.getInstance();
        	Calendar yesterday = Calendar.getInstance();
        	yesterday.setTime(scheduleDate);
        	yesterday.set(Calendar.HOUR_OF_DAY, 0);
        	yesterday.set(Calendar.MINUTE, 0);
        	yesterday.set(Calendar.SECOND, 0);
        	yesterday.set(Calendar.MILLISECOND, 0);
        	yesterday.add(Calendar.DAY_OF_YEAR, -1);
        	double numberOfWorkingDays = 0;

        	if (resourceAssignmentDataTO.getStartDate() != null && resourceAssignmentDataTO.getFinishDate() != null) {
	        	runningDate.setTime(resourceAssignmentDataTO.getStartDate());
	        	finishDate.setTime(resourceAssignmentDataTO.getFinishDate());
	        	while (runningDate.before(finishDate) || runningDate.equals(finishDate)) {
	        		if (runningDate.getTime().after(yesterday.getTime()))
	        			if (isWorkingDay(runningDate, calRegularDaysTO, setCalNonWorkingDays, setCalSplWorkingDays)) numberOfWorkingDays++;
	        		runningDate.add(Calendar.DATE, 1);
	        	}
        	}
        	ResourceCurveMappingEntity resourceCurveMappingEntity = resourceCurveMappingRepository.findBy(resourceAssignmentDataTO.getReferenceId(), resourceAssignmentDataTO.getReferenceType());
        	if (resourceCurveMappingEntity != null)
        		resourceCurveEntity = resourceCurveRepository.findOne(resourceCurveMappingEntity.getProjResourceCurveEntity().getId());
        	else 
        		resourceCurveEntity = projectSettgingresourceCurveEntity;
        	resourceAssignmentDataTO.setCurve(resourceCurveEntity.getCurveType());
        	
        	List<Double> perUnitValues = new ArrayList<Double>();
        	perUnitValues.add(((resourceCurveEntity.getRange1().floatValue() / numberOfWorkingDays) / 100) * resourceAssignmentDataTO.getBudgetUnits());
        	perUnitValues.add(((resourceCurveEntity.getRange2().floatValue() / numberOfWorkingDays) / 100) * resourceAssignmentDataTO.getBudgetUnits());
        	perUnitValues.add(((resourceCurveEntity.getRange3().floatValue() / numberOfWorkingDays) / 100) * resourceAssignmentDataTO.getBudgetUnits());
        	perUnitValues.add(((resourceCurveEntity.getRange4().floatValue() / numberOfWorkingDays) / 100) * resourceAssignmentDataTO.getBudgetUnits());
        	perUnitValues.add(((resourceCurveEntity.getRange5().floatValue() / numberOfWorkingDays) / 100) * resourceAssignmentDataTO.getBudgetUnits());
        	perUnitValues.add(((resourceCurveEntity.getRange6().floatValue() / numberOfWorkingDays) / 100) * resourceAssignmentDataTO.getBudgetUnits());
        	perUnitValues.add(((resourceCurveEntity.getRange7().floatValue() / numberOfWorkingDays) / 100) * resourceAssignmentDataTO.getBudgetUnits());
        	perUnitValues.add(((resourceCurveEntity.getRange8().floatValue() / numberOfWorkingDays) / 100) * resourceAssignmentDataTO.getBudgetUnits());
        	perUnitValues.add(((resourceCurveEntity.getRange9().floatValue() / numberOfWorkingDays) / 100) * resourceAssignmentDataTO.getBudgetUnits());
        	perUnitValues.add(((resourceCurveEntity.getRange10().floatValue() / numberOfWorkingDays) / 100) * resourceAssignmentDataTO.getBudgetUnits());
            
        	List<Double> dayWiseCurveDataList = new ArrayList<Double>();
        	double breakdownEachDayToTenParts = numberOfWorkingDays * 10;
            double eachPartInADay = 0, cummulative = 0;
            int index = 0;
            for (int i = 1; i <= breakdownEachDayToTenParts; i++) {
            	eachPartInADay += perUnitValues.get(index);
                if (i % 10 == 0) {
                    dayWiseCurveDataList.add(round(eachPartInADay, 2));
                    cummulative += round(eachPartInADay, 2);
                    eachPartInADay = 0;
                }
                if (i % numberOfWorkingDays == 0) {
                	index += 1;
                }
            }

            if (dayWiseCurveDataList.size() > 0) 
            		dayWiseCurveDataList.set(dayWiseCurveDataList.size() - 1, dayWiseCurveDataList.get(dayWiseCurveDataList.size() - 1) + resourceAssignmentDataTO.getBudgetUnits() - cummulative);
            
        	List<ResourceAssignmentDataValueTO> valuesResourceAssignmentDataValueTOs = new ArrayList<ResourceAssignmentDataValueTO>();
        	runningDate.setTime(minStartDate.getTime());
        	index = 0;
        	while (runningDate.before(maxFinishDate) || runningDate.equals(maxFinishDate)) {
    			ResourceAssignmentDataValueTO resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
    			resourceAssignmentDataValueTO.setForecastDate(runningDate.getTime());
    			resourceAssignmentDataValueTO.setBudget(0D);
    			if (resourceAssignmentDataTO.getStartDate() != null && resourceAssignmentDataTO.getFinishDate() != null) {  				
    				if (resourceAssignmentDataTO.getStartDate().compareTo(resourceAssignmentDataValueTO.getForecastDate()) * resourceAssignmentDataValueTO.getForecastDate().compareTo(resourceAssignmentDataTO.getFinishDate()) >= 0) {    					
    					if (resourceAssignmentDataValueTO.getForecastDate().after(yesterday.getTime())) {    						
	    					if (isWorkingDay(runningDate, calRegularDaysTO, setCalNonWorkingDays, setCalSplWorkingDays)) {	    						
	    						resourceAssignmentDataValueTO.setBudget(dayWiseCurveDataList.get(index));
	    						index++;
	    					}
    					}    					
    				}
    			}
    			valuesResourceAssignmentDataValueTOs.add(resourceAssignmentDataValueTO);
    			runningDate.add(Calendar.DATE, 1);
    		}
        	resourceAssignmentDataTO.setResourceAssignmentDataValueTOs(valuesResourceAssignmentDataValueTOs);
        }
        
		return resourceAssignmentDataTOs;
	}
	
	private static double round(double value, int places) {
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.FLOOR);
	    return bd.doubleValue();
	}
	
	private boolean isWorkingDay(Calendar calendar, CalRegularDaysTO calRegularDaysTO, List<String> setCalNonWorkingDays, List<String> setCalSplWorkingDays) {
		boolean isWorking = false;
		switch(calendar.get(Calendar.DAY_OF_WEEK)) {
		case 1: isWorking = !calRegularDaysTO.isSunday(); break;
		case 2: isWorking = !calRegularDaysTO.isMonday(); break;
		case 3: isWorking = !calRegularDaysTO.isTuesday(); break;
		case 4: isWorking = !calRegularDaysTO.isWednesday(); break;
		case 5: isWorking = !calRegularDaysTO.isThursday(); break;
		case 6: isWorking = !calRegularDaysTO.isFriday(); break;
		case 7: isWorking = !calRegularDaysTO.isSaturday(); break;
		}
		
		if (isWorking) {
			if (setCalNonWorkingDays.contains(CommonUtil.convertDateToString(calendar.getTime())))
				isWorking = false;
		} else {
			if (setCalSplWorkingDays.contains(CommonUtil.convertDateToString(calendar.getTime())))
				isWorking = true;
		}
		return isWorking;
	}
	
	private List<ResourceAssignmentDataTO> projectMaterialDtlTOItemsToResourceAssignmentDataTOs(ProjectMaterialDtlTO projectMaterialDtlTO, ProjGeneralsResp projGeneralsResp) {
		List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
		
		if (projectMaterialDtlTO.isItem())
			resourceAssignmentDataTOs.add(ResourceAssignmentDataHandler.fromProjectMaterialDtlTO(projectMaterialDtlTO, projGeneralsResp));
		else
			for (ProjectMaterialDtlTO ProjectMaterialDtlTO : projectMaterialDtlTO.getProjectMaterialDtlTOs())
				resourceAssignmentDataTOs.addAll(projectMaterialDtlTOItemsToResourceAssignmentDataTOs(ProjectMaterialDtlTO, projGeneralsResp));
		
		return resourceAssignmentDataTOs;
	}
	
	private List<ResourceAssignmentDataTO> projCostStmtDtlTOItemsToResourceAssignmentDataTOs(ProjCostStmtDtlTO projCostStmtDtlTO, ProjGeneralsResp projGeneralsResp, List<ProjSOWItemEntity> projectSOWItemEntities) {
		List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
		
		if (projCostStmtDtlTO.isItem()) {
			ResourceAssignmentDataTO resourceAssignmentDataTO = ResourceAssignmentDataHandler.fromProjCostStmtDtlTO(projCostStmtDtlTO, projGeneralsResp, projectSOWItemEntities);
			boolean updated = false;
			for (ResourceAssignmentDataTO radTO : resourceAssignmentDataTOs) {
				if (resourceAssignmentDataTO.getReferenceId().equals(radTO.getReferenceId())) {
					radTO.setBudgetUnits(radTO.getBudgetUnits() + resourceAssignmentDataTO.getBudgetUnits());
					radTO.setActualUnits(radTO.getActualUnits() + resourceAssignmentDataTO.getActualUnits());
					if (radTO.getStartDate() != null && resourceAssignmentDataTO.getStartDate() != null)
						if (radTO.getStartDate().after(resourceAssignmentDataTO.getStartDate())) radTO.setStartDate(resourceAssignmentDataTO.getStartDate());
					if (radTO.getFinishDate() != null && resourceAssignmentDataTO.getFinishDate() != null)
						if (radTO.getFinishDate().before(resourceAssignmentDataTO.getFinishDate())) radTO.setFinishDate(resourceAssignmentDataTO.getFinishDate());
					updated = true;
				}
			}
			if (!updated) resourceAssignmentDataTOs.add(resourceAssignmentDataTO);
		} else
			for (ProjCostStmtDtlTO ProjCostStmtDtlTO : projCostStmtDtlTO.getProjCostStmtDtlTOs())
				resourceAssignmentDataTOs.addAll(projCostStmtDtlTOItemsToResourceAssignmentDataTOs(ProjCostStmtDtlTO, projGeneralsResp, projectSOWItemEntities));
		
		return resourceAssignmentDataTOs;
	}
	
	private ProjCostStmtDtlTO getProjCostStmtDtlTO(Long id, ProjCostStmtDtlTO projCostStmtDtlTO) {
		if (projCostStmtDtlTO.isItem())
			if (projCostStmtDtlTO.getCostId().equals(id))
				return projCostStmtDtlTO;
			else
				return null;
		for (ProjCostStmtDtlTO ProjCostStmtDtlTO : projCostStmtDtlTO.getProjCostStmtDtlTOs()) {
			ProjCostStmtDtlTO pCostStmtDtlTO = getProjCostStmtDtlTO(id, ProjCostStmtDtlTO);
			if (pCostStmtDtlTO != null) return pCostStmtDtlTO;
		}
		return null;
	}
	
	private List<ResourceAssignmentDataTO> projSORItemEntityCopyToResourceAssignmentDataTOs(ProjSORItemEntity projSORItemEntityCopy, ProjGeneralsResp projGeneralsResp, List<ProjSOWItemEntity> projectSOWItemEntities) {
		List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
		
		if (projSORItemEntityCopy.isItem()) {
			ResourceAssignmentDataTO resourceAssignmentDataTO = ResourceAssignmentDataHandler.fromProjSORItemEntityCopy(projSORItemEntityCopy, projGeneralsResp, projectSOWItemEntities);
			boolean updated = false;
			for (ResourceAssignmentDataTO radTO : resourceAssignmentDataTOs) {
				if (resourceAssignmentDataTO.getReferenceId().equals(radTO.getReferenceId())) {
					radTO.setBudgetUnits(radTO.getBudgetUnits() + resourceAssignmentDataTO.getBudgetUnits());
					radTO.setActualUnits(radTO.getActualUnits() + resourceAssignmentDataTO.getActualUnits());
					if (radTO.getStartDate() != null && resourceAssignmentDataTO.getStartDate() != null)
						if (radTO.getStartDate().after(resourceAssignmentDataTO.getStartDate())) radTO.setStartDate(resourceAssignmentDataTO.getStartDate());
					if (radTO.getFinishDate() != null && resourceAssignmentDataTO.getFinishDate() != null)
						if (radTO.getFinishDate().before(resourceAssignmentDataTO.getFinishDate())) radTO.setFinishDate(resourceAssignmentDataTO.getFinishDate());
					updated = true;
				}
			}
			if (!updated) resourceAssignmentDataTOs.add(resourceAssignmentDataTO);
		} else
			for (ProjSORItemEntity ProjSORItemEntityCopy : projSORItemEntityCopy.getChildEntities())
				resourceAssignmentDataTOs.addAll(projSORItemEntityCopyToResourceAssignmentDataTOs(ProjSORItemEntityCopy, projGeneralsResp, projectSOWItemEntities));
		
		return resourceAssignmentDataTOs;
	}

	private List<ResourceAssignmentDataTO> ProjSOWItemEntityCopyToResourceAssignmentDataTOs(List<ProjSOWItemEntity> projSOWItemEntities, ProjGeneralsResp projGeneralsResp) {
		List<ResourceAssignmentDataTO> resourceAssignmentDataTOs = new ArrayList<ResourceAssignmentDataTO>();
		
		for (ProjSOWItemEntity projSOWItemEntityCopy : projSOWItemEntities) {
			ResourceAssignmentDataTO resourceAssignmentDataTO = ResourceAssignmentDataHandler.fromProjSOWItemEntityCopy(projSOWItemEntityCopy, projGeneralsResp);
			boolean updated = false;
			for (ResourceAssignmentDataTO radTO : resourceAssignmentDataTOs) {
				if (resourceAssignmentDataTO.getReferenceId().equals(radTO.getReferenceId())) {
					radTO.setBudgetUnits(radTO.getBudgetUnits() + resourceAssignmentDataTO.getBudgetUnits());
					radTO.setActualUnits(radTO.getActualUnits() + resourceAssignmentDataTO.getActualUnits());
					if (radTO.getStartDate() != null && resourceAssignmentDataTO.getStartDate() != null)
						if (radTO.getStartDate().after(resourceAssignmentDataTO.getStartDate())) radTO.setStartDate(resourceAssignmentDataTO.getStartDate());
					if (radTO.getFinishDate() != null && resourceAssignmentDataTO.getFinishDate() != null)
						if (radTO.getFinishDate().before(resourceAssignmentDataTO.getFinishDate())) radTO.setFinishDate(resourceAssignmentDataTO.getFinishDate());
					updated = true;
				}
			}
			if (!updated) resourceAssignmentDataTOs.add(resourceAssignmentDataTO);
		}
		
		return resourceAssignmentDataTOs;
	}

	@Override
	public void saveResourceCurveMapping(ResourceCurveMappingReq resourceCurveMappingReq) {
		for (ResourceCurveMappingTO resourceCurveMappingTO : resourceCurveMappingReq.getResourceCurveMappingTOs()) {
			ResourceCurveMappingEntity resourceCurveMappingEntity = resourceCurveMappingRepository.findBy(resourceCurveMappingTO.getResourceReferenceId(), resourceCurveMappingTO.getResourceReferenceType());
			if (resourceCurveMappingEntity != null)
				resourceCurveMappingTO.setId(resourceCurveMappingEntity.getId());
					
		}
		resourceCurveMappingRepository.save(ResourceCurveMappingHandler.toEntities(resourceCurveMappingReq.getResourceCurveMappingTOs(), resourceCurveRepository));
	}

	@Override
	public CalendarSpecialWorkingNonworkingDays getCalendarSpecialWorkingNonworkingDays(Long projectId) {
		CalendarSpecialWorkingNonworkingDays calendarSpecialWorkingNonworkingDays = new CalendarSpecialWorkingNonworkingDays();
		calendarSpecialWorkingNonworkingDays.setWeeklyHolidays(getCalendarRegularDays(projectId, 1));
		calendarSpecialWorkingNonworkingDays.setSpecialNonworkingDays(globalCalSpecialDaysRepository.findProjCalNonWorkingDays(projectId));
		calendarSpecialWorkingNonworkingDays.setSpecialWorkingDays(globalCalSpecialDaysRepository.findProjCalSpecialWorkingDays(projectId));
		return calendarSpecialWorkingNonworkingDays;
	}
}
