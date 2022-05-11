package com.rjtech.projsettings.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjtech.calendar.dto.CalTO;
import com.rjtech.calendar.model.GlobalCalEntity;
import com.rjtech.calendar.repository.GlobalCalendarRepository;
import com.rjtech.calendar.service.handler.GlobalCalHandler;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.ProjLeaveTypeEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.repository.ProjLeaveTypeRepository;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.centrallib.service.handler.EmpClassHandler;
import com.rjtech.centrallib.service.handler.MaterialClassHandler;
import com.rjtech.centrallib.service.handler.PlantClassHandler;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.CostActualHoursTO;
import com.rjtech.common.dto.FinancePeriodPayCyclesTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjStatusActualTo;
import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.repository.CommonRepository;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.common.service.impl.CommonEmailServiceImpl;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.Budget;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodes;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.PayPeriodCycles;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.WeekDays;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.finance.repository.ProfitCentreRepositoryCopy;
import com.rjtech.notification.model.AttendenceNotificationsEntity;
import com.rjtech.notification.model.EmployeeNotificationsEntity;
import com.rjtech.notification.model.EmployeeNotificationsEntityCopy;
import com.rjtech.notification.model.MaterialNotificationsEntity;
import com.rjtech.notification.model.PlantNotificationsEntityCopy;
//import com.rjtech.notification.model.ProjCrewMstrEntity;
import com.rjtech.notification.model.TimeSheetNotificationsEntity;
import com.rjtech.notification.model.WorkDairyAdditionalTimeEntity;
import com.rjtech.notification.model.WorkDairyNotificationsEntity;
import com.rjtech.notification.repository.AttendenceNotificationsRepositoryCopy;
import com.rjtech.notification.repository.EmployeeNotificationsRepositoryCopy;
import com.rjtech.notification.repository.EntpsProjRepositorCopy;
import com.rjtech.notification.repository.PlantNotificationsRepositoryCopy;
import com.rjtech.notification.repository.TimeSheetNotificationsRepositoryCopy;
import com.rjtech.notification.repository.WorkDairyAdditionalTimeRepositoryCopy;
import com.rjtech.notification.repository.WorkDairyNotificationRepositoryCopy;
//import com.rjtech.notification.repository.WorkDairyNotificationRepositoryCopy;
import com.rjtech.notification.service.handler.PlantNotificationsHandlerCopy;
import com.rjtech.notification.service.handler.TimeSheetNotificationsHandlerCopy;
import com.rjtech.procurement.repository.PurchaseOrderRepositoryCopy;
import com.rjtech.progress.reports.req.ProgressReportGetReq;
import com.rjtech.proj.settings.common.service.ActualAmountService;
import com.rjtech.proj.settings.common.service.ActualHrsService;
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.model.ProjSOEItemEntity;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjCrewRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSowTotalActualRepository;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.projschedule.dto.ProjScheduleCostCodeTO;
import com.rjtech.projschedule.model.ResourceAssignmentDataEntity;
import com.rjtech.projschedule.model.ResourceAssignmentDataValueEntity;
import com.rjtech.projschedule.repository.ProjectPlantsRepository;
import com.rjtech.projschedule.repository.ResourceAssignmentDataRepository;
import com.rjtech.projschedule.repository.ResourceAssignmentDataValueRepository;
import com.rjtech.projschedule.req.ProjScheduleBaseLineGetReq;
import com.rjtech.projschedule.resp.ProjScheduleCostCodeResp;
import com.rjtech.projschedule.service.ProjScheduleService;
import com.rjtech.projschedule.service.handler.ResourceAssignmentDataHandler;
import com.rjtech.projsettings.constans.ProjEstimateConstants;
import com.rjtech.projsettings.dto.ProjAttendceApprTO;
import com.rjtech.projsettings.dto.ProjCostBudgetTO;
import com.rjtech.projsettings.dto.ProjCostStatementsSummaryTO;
import com.rjtech.projsettings.dto.ProjCostStmtDtlTO;
import com.rjtech.projsettings.dto.ProjCostSummaryBudgetTO;
import com.rjtech.projsettings.dto.ProjEmpTransApprTO;
import com.rjtech.projsettings.dto.ProjGenCurrencyResp;
import com.rjtech.projsettings.dto.ProjLeaveApprTO;
import com.rjtech.projsettings.dto.ProjManPowerStatusTO;
import com.rjtech.projsettings.dto.ProjManpowerTO;
import com.rjtech.projsettings.dto.ProjMaterialTransApprTO;
import com.rjtech.projsettings.dto.ProjNoteBookTO;
import com.rjtech.projsettings.dto.ProjPlannedValueTO;
import com.rjtech.projsettings.dto.ProjPlantTransApprTO;
import com.rjtech.projsettings.dto.ProjProcurementApprTO;
import com.rjtech.projsettings.dto.ProjProgressTO;
import com.rjtech.projsettings.dto.ProjSoeApprTO;
import com.rjtech.projsettings.dto.ProjStatusDatesTO;
import com.rjtech.projsettings.dto.ProjStatusMilestonesTO;
import com.rjtech.projsettings.dto.ProjTimeSheetApprTO;
import com.rjtech.projsettings.dto.ProjWorkDairyApprTO;
import com.rjtech.projsettings.dto.ProjectGanttChartTO;
import com.rjtech.projsettings.dto.ProjectMaterialDtlTO;
import com.rjtech.projsettings.dto.ProjectPlantsDtlTO;
import com.rjtech.projsettings.dto.ProjectPlantsStatusTO;
import com.rjtech.projsettings.dto.ProjectTangibleTO;
import com.rjtech.projsettings.model.AttendanceNormalTimeEntity;
import com.rjtech.projsettings.model.ChangeOrderNormalTimeEntity;
import com.rjtech.projsettings.model.EmpTransNormalTimeEntity;
import com.rjtech.projsettings.model.LeaveAddtionalTimeApprEntity;
import com.rjtech.projsettings.model.LeaveNormalTimeEntity;
import com.rjtech.projsettings.model.MaterialTransAddtionalTimeApprEntity;
import com.rjtech.projsettings.model.MaterialTransNormalTimeEntity;
import com.rjtech.projsettings.model.PlantTransNormalTimeEntity;
import com.rjtech.projsettings.model.ProcurementAddtionalTimeApprEntity;
import com.rjtech.projsettings.model.ProcurementNormalTimeEntity;
import com.rjtech.projsettings.model.ProcurementNotificationsEntity;
import com.rjtech.projsettings.model.ProgressClaimNormalTimeEntity;
import com.rjtech.projsettings.model.ProgressClaimPeriodCycleEntity;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;
import com.rjtech.projsettings.model.ProjEstimateEntity;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.projsettings.model.ProjManpowerEntity;
import com.rjtech.projsettings.model.ProjNoteBookEntity;
import com.rjtech.projsettings.model.ProjPayRollCycleEntity;
import com.rjtech.projsettings.model.ProjPerformenceThresholdEntity;
import com.rjtech.projsettings.model.ProjProgressEntity;
import com.rjtech.projsettings.model.ProjStatusDatesEntity;
import com.rjtech.projsettings.model.ProjStatusMileStonesEntity;
import com.rjtech.projsettings.model.ProjTimeSheetWeekDtlEntity;
import com.rjtech.projsettings.model.ProjectMaterialBudgetEntity;
import com.rjtech.projsettings.model.ProjectPlantsDtlEntity;
import com.rjtech.projsettings.model.ProjectReportsEntity;
import com.rjtech.projsettings.model.ResourceBudgetNormalTimeEntity;
import com.rjtech.projsettings.model.SchofEstimateNormalTimeEntity;
import com.rjtech.projsettings.model.SchofRatesNormalTimeEntity;
import com.rjtech.projsettings.model.SoeAddtionalTimeApprEntity;
import com.rjtech.projsettings.model.TimesheetNormalTimeEntity;
import com.rjtech.projsettings.model.WorkDairyNormalTimeEntity;
import com.rjtech.projsettings.register.emp.dto.EmpChargeOutRateTO;
import com.rjtech.projsettings.register.plant.dto.PlantChargeOutRateTO;
import com.rjtech.projsettings.repository.ChangeOrderNormaltimeEntityRepository;
//import com.rjtech.projsettings.repository.ChangeOrderNormaltimeEntityRepository;
import com.rjtech.projsettings.repository.ProjAttendenceApprRepository;
import com.rjtech.projsettings.repository.ProjAttendenceRepository;
import com.rjtech.projsettings.repository.ProjCostStatementsRepository;
import com.rjtech.projsettings.repository.ProjEmpTransRepository;
import com.rjtech.projsettings.repository.ProjEstimateRepository;
import com.rjtech.projsettings.repository.ProjGeneralRepository;
import com.rjtech.projsettings.repository.ProjLeaveApprRepository;
import com.rjtech.projsettings.repository.ProjLeaveRequestRepository;
import com.rjtech.projsettings.repository.ProjManpowerRepository;
import com.rjtech.projsettings.repository.ProjMaterialTransApprRepository;
import com.rjtech.projsettings.repository.ProjMaterialTransRepository;
import com.rjtech.projsettings.repository.ProjNoteBookRepository;
import com.rjtech.projsettings.repository.ProjPayRollCycleRepository;
import com.rjtech.projsettings.repository.ProjPerformenceThresholdRepository;
import com.rjtech.projsettings.repository.ProjPlantTransRepository;
import com.rjtech.projsettings.repository.ProjProcureApprRepository;
import com.rjtech.projsettings.repository.ProjProcureNotificationRepository;
import com.rjtech.projsettings.repository.ProjProcureRepository;
import com.rjtech.projsettings.repository.ProjProgressClaimApprRepository;
import com.rjtech.projsettings.repository.ProjProgressClaimRepository;
import com.rjtech.projsettings.repository.ProjProgressClaimePeriodRepository;
import com.rjtech.projsettings.repository.ProjProgressRepository;
import com.rjtech.projsettings.repository.ProjReportsRepository;
import com.rjtech.projsettings.repository.ProjStatusDatesRepository;
import com.rjtech.projsettings.repository.ProjStatusMileStonesRepository;
import com.rjtech.projsettings.repository.ProjTimeSheetRepository;
//import com.rjtech.projsettings.repository.ProjTimeSheetRepositoryCopy;
import com.rjtech.projsettings.repository.ProjTimeSheetRepositorySettings;
import com.rjtech.projsettings.repository.ProjTimeSheetWeekRepository;
import com.rjtech.projsettings.repository.ProjWorkDairyRepository;
import com.rjtech.projsettings.repository.ProjectMaterialRepository;
//import com.rjtech.projsettings.repository.ProjectPlantsRepository;
import com.rjtech.projsettings.repository.ResourceBudgetRepository;
import com.rjtech.projsettings.repository.SchofEstimatesRepository;
import com.rjtech.projsettings.repository.SchofRatesRepository;
import com.rjtech.projsettings.repository.SoeAddltionalTimeRepository;
import com.rjtech.projsettings.req.ChangeOrderDetailsGetReq;
import com.rjtech.projsettings.req.ChangeOrderDetailsSaveReq;
import com.rjtech.projsettings.req.ProjAttendenceApprSaveReq;
import com.rjtech.projsettings.req.ProjAttendenceGetReq;
import com.rjtech.projsettings.req.ProjAttendenceSaveReq;
import com.rjtech.projsettings.req.ProjCostCodeStatusGetReq;
import com.rjtech.projsettings.req.ProjCostCodesSaveReq;
import com.rjtech.projsettings.req.ProjCostStatementsGetReq;
import com.rjtech.projsettings.req.ProjCostStatementsSaveReq;
import com.rjtech.projsettings.req.ProjEmpTransApprSaveReq;
import com.rjtech.projsettings.req.ProjEmpTransGetReq;
import com.rjtech.projsettings.req.ProjEmpTransSaveReq;
import com.rjtech.projsettings.req.ProjEstimateGetReq;
import com.rjtech.projsettings.req.ProjEstimateSaveReq;
import com.rjtech.projsettings.req.ProjGeneralSaveReq;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.projsettings.req.ProjLeaveApprSaveReq;
import com.rjtech.projsettings.req.ProjLeaveRequestGetReq;
import com.rjtech.projsettings.req.ProjLeaveRequestSaveReq;
import com.rjtech.projsettings.req.ProjManpowerGetReq;
import com.rjtech.projsettings.req.ProjManpowerSaveReq;
import com.rjtech.projsettings.req.ProjMaterialTransApprSaveReq;
import com.rjtech.projsettings.req.ProjMaterialTransGetReq;
import com.rjtech.projsettings.req.ProjMaterialTransSaveReq;
import com.rjtech.projsettings.req.ProjMileStonesDateSaveReq;
import com.rjtech.projsettings.req.ProjNoteBookGetReq;
import com.rjtech.projsettings.req.ProjNoteBookSaveReq;
import com.rjtech.projsettings.req.ProjPayRollCycleGetReq;
import com.rjtech.projsettings.req.ProjPayRollCycleSaveReq;
import com.rjtech.projsettings.req.ProjPerfamanceDefaultSaveReq;
import com.rjtech.projsettings.req.ProjPerfomanceDelReq;
import com.rjtech.projsettings.req.ProjPerformenceThresholdGetReq;
import com.rjtech.projsettings.req.ProjPerformenceThresholdSaveReq;
import com.rjtech.projsettings.req.ProjPlantTransApprSaveReq;
import com.rjtech.projsettings.req.ProjPlantTransGetReq;
import com.rjtech.projsettings.req.ProjPlantTransSaveReq;
import com.rjtech.projsettings.req.ProjProcureApprSaveReq;
import com.rjtech.projsettings.req.ProjProcureGetReq;
import com.rjtech.projsettings.req.ProjProcureSaveReq;
import com.rjtech.projsettings.req.ProjProgressClaimApprSaveReq;
import com.rjtech.projsettings.req.ProjProgressClaimGetReq;
import com.rjtech.projsettings.req.ProjProgressClaimSaveReq;
import com.rjtech.projsettings.req.ProjProgressClaimePeroidSaveReq;
import com.rjtech.projsettings.req.ProjProgressGetReq;
import com.rjtech.projsettings.req.ProjProgressSaveReq;
import com.rjtech.projsettings.req.ProjReportsGetReq;
import com.rjtech.projsettings.req.ProjReportsSaveReq;
import com.rjtech.projsettings.req.ProjResourceCurveGetReq;
import com.rjtech.projsettings.req.ProjSettingsFilterReq;
import com.rjtech.projsettings.req.ProjSoeApprSaveReq;
import com.rjtech.projsettings.req.ProjSowsSaveReq;
import com.rjtech.projsettings.req.ProjStatusActualReq;
import com.rjtech.projsettings.req.ProjStatusDatesSaveReq;
import com.rjtech.projsettings.req.ProjStatusGetReq;
import com.rjtech.projsettings.req.ProjSummaryGetReq;
import com.rjtech.projsettings.req.ProjTimeSheetApprSaveReq;
import com.rjtech.projsettings.req.ProjTimeSheetGetReq;
import com.rjtech.projsettings.req.ProjTimeSheetSaveReq;
import com.rjtech.projsettings.req.ProjTimeSheetWeekSaveReq;
import com.rjtech.projsettings.req.ProjWorkDairyApprSaveReq;
import com.rjtech.projsettings.req.ProjWorkDairyGetReq;
import com.rjtech.projsettings.req.ProjWorkDairySaveReq;
import com.rjtech.projsettings.req.ProjectDefaultSaveReq;
import com.rjtech.projsettings.req.ProjectMaterialGetReq;
import com.rjtech.projsettings.req.ProjectMaterialSaveReq;
import com.rjtech.projsettings.req.ProjectPlantsGetReq;
import com.rjtech.projsettings.req.ProjectPlantsSaveReq;
import com.rjtech.projsettings.req.ProjectTangibleReq;
import com.rjtech.projsettings.req.ResourceBudgetGetReq;
import com.rjtech.projsettings.req.ResourceBudgetSaveReq;
import com.rjtech.projsettings.req.SchofEstimatesGetReq;
import com.rjtech.projsettings.req.SchofEstimatesSaveReq;
import com.rjtech.projsettings.req.SchofRatesGetReq;
import com.rjtech.projsettings.req.SchofRatesSaveReq;
import com.rjtech.projsettings.resp.ChangeOrderDetailsResp;
import com.rjtech.projsettings.resp.ProjAttendenceResp;
import com.rjtech.projsettings.resp.ProjBudgetResp;
import com.rjtech.projsettings.resp.ProjCostCodeStatusResp;
import com.rjtech.projsettings.resp.ProjCostStaementsResp;
import com.rjtech.projsettings.resp.ProjCostStatementsSummaryResp;
import com.rjtech.projsettings.resp.ProjEmpTransResp;
import com.rjtech.projsettings.resp.ProjEstimateResp;
import com.rjtech.projsettings.resp.ProjGeneralsResp;
import com.rjtech.projsettings.resp.ProjLeaveRequestResp;
import com.rjtech.projsettings.resp.ProjManPowerResp;
import com.rjtech.projsettings.resp.ProjManPowerStatusResp;
import com.rjtech.projsettings.resp.ProjMaterialTransResp;
import com.rjtech.projsettings.resp.ProjMileStonesResp;
import com.rjtech.projsettings.resp.ProjNoteBookResp;
import com.rjtech.projsettings.resp.ProjPayRollCycleResp;
import com.rjtech.projsettings.resp.ProjPerformenceThresholdResp;
import com.rjtech.projsettings.resp.ProjPlannedValueResp;
import com.rjtech.projsettings.resp.ProjPlantTransResp;
import com.rjtech.projsettings.resp.ProjProcureResp;
import com.rjtech.projsettings.resp.ProjProgressClaimResp;
import com.rjtech.projsettings.resp.ProjProgressClaimePeroidResp;
import com.rjtech.projsettings.resp.ProjProgressResp;
import com.rjtech.projsettings.resp.ProjReportsResp;
import com.rjtech.projsettings.resp.ProjResourceBudgetResp;
import com.rjtech.projsettings.resp.ProjResourceCurveResp;
import com.rjtech.projsettings.resp.ProjSchofEstimatesResp;
import com.rjtech.projsettings.resp.ProjSchofRatesResp;
import com.rjtech.projsettings.resp.ProjStatusActualResp;
import com.rjtech.projsettings.resp.ProjStatusDatesResp;
import com.rjtech.projsettings.resp.ProjStatusResp;
import com.rjtech.projsettings.resp.ProjSummaryResp;
import com.rjtech.projsettings.resp.ProjTimeSheetResp;
import com.rjtech.projsettings.resp.ProjTimeSheetWeekResp;
import com.rjtech.projsettings.resp.ProjWorkDairyResp;
import com.rjtech.projsettings.resp.ProjectGanntChartResp;
import com.rjtech.projsettings.resp.ProjectMaterialsResp;
import com.rjtech.projsettings.resp.ProjectPlantsResp;
import com.rjtech.projsettings.resp.ProjectPlantsStatusResp;
import com.rjtech.projsettings.resp.ProjectTangibleResp;
import com.rjtech.projsettings.service.CostCodeActualDetailsService;
import com.rjtech.projsettings.service.ProjSettingsService;
import com.rjtech.projsettings.service.handler.ProjAttendenceApprHandler;
import com.rjtech.projsettings.service.handler.ProjAttendenceHandler;
import com.rjtech.projsettings.service.handler.ProjChangeorderdetailsHandler;
import com.rjtech.projsettings.service.handler.ProjCostStmtDtlHandler;
import com.rjtech.projsettings.service.handler.ProjEmpTransHandler;
import com.rjtech.projsettings.service.handler.ProjEstimateHandler;
import com.rjtech.projsettings.service.handler.ProjGeneralHandler;
import com.rjtech.projsettings.service.handler.ProjLeaveApprHandler;
import com.rjtech.projsettings.service.handler.ProjLeaveRequestHandler;
import com.rjtech.projsettings.service.handler.ProjManpowerHandler;
import com.rjtech.projsettings.service.handler.ProjMaterialTransHandler;
import com.rjtech.projsettings.service.handler.ProjNoteBookHandler;
import com.rjtech.projsettings.service.handler.ProjPayRollCycleHandler;
import com.rjtech.projsettings.service.handler.ProjPerformenceThresholdHandler;
import com.rjtech.projsettings.service.handler.ProjPlantTransHandler;
import com.rjtech.projsettings.service.handler.ProjProcurementHandler;
import com.rjtech.projsettings.service.handler.ProjProgressClaimApprHandler;
import com.rjtech.projsettings.service.handler.ProjProgressClaimHandler;
import com.rjtech.projsettings.service.handler.ProjProgressClaimePeriodHandler;
import com.rjtech.projsettings.service.handler.ProjProgressHandler;
import com.rjtech.projsettings.service.handler.ProjReportsHandler;
import com.rjtech.projsettings.service.handler.ProjResourceCurveHandler;
import com.rjtech.projsettings.service.handler.ProjStatusDatesHandler;
import com.rjtech.projsettings.service.handler.ProjStatusMileStonesHandler;
import com.rjtech.projsettings.service.handler.ProjTimeSheetHandler;
import com.rjtech.projsettings.service.handler.ProjTimeSheetWeekHandler;
import com.rjtech.projsettings.service.handler.ProjWorkDairyHandler;
import com.rjtech.projsettings.service.handler.ProjectMaterialBudgetHandler;
import com.rjtech.projsettings.service.handler.ProjectPlantsDtlHandler;
import com.rjtech.projsettings.service.handler.ResourceBudgetHandler;
import com.rjtech.projsettings.service.handler.SchofEstimatesHandler;
import com.rjtech.projsettings.service.handler.SchofRatesHandler;
//import com.rjtech.register.EmpProjRigisterEntityCopy;
//import com.rjtech.register.EmpRegisterDtlEntityCopy;
//import com.rjtech.register.PlantRegisterDtlEntityCopy;
import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.emp.model.EmpLeaveReqApprEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.emp.model.EmpTransferReqApprEntity;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpLeaveReqApprRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepositoryCopy;
import com.rjtech.register.repository.emp.EmpTransferReqAprRepository;
import com.rjtech.register.repository.material.MaterialNotificationsRepositoryCopy;
import com.rjtech.register.repository.plant.PlantChargeOutRateRepositoryCopy;
import com.rjtech.register.repository.plant.PlantNotificationRepositoryCopy;
//import com.rjtech.register.timemanagement.attendance.repository.EmpAttendanceRepository;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.CostReportTO;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.reports.cost.resp.ProgressSCurveTO;
import com.rjtech.reports.cost.resp.ProgressSCurveTOResp;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceRepository;
import com.rjtech.timemanagement.attendence.service.handler.EmpAttendanceHandler;
import com.rjtech.timemanagement.timesheet.model.TimeSheetAdditionalTimeEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpWorkDtlEntity;
import com.rjtech.timemanagement.timesheet.repository.copy.TimeSheetAdditionalTimeRepositoryCopy;
import com.rjtech.timemanagement.timesheet.repository.copy.TimeSheetEmpDtlRepositoryCopy;
import com.rjtech.timemanagement.timesheet.repository.copy.TimeSheetEmpExpenseRepositoryCopy;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpDtlEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpWageEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantDtlEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyProgressDtlEntity;
import com.rjtech.timemanagement.workdairy.repository.copy.EmpCostWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.EmpWageWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.MaterialStatusWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.PlantCostWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.ProgressWorkDairyRepositoryCopy;
//import com.rjtech.timemanagement.workdairy.repository.copy.WorkDairyAdditionalTimeRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.WorkDairyEmpDtlRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.WorkDairyRepositoryCopy;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.model.UserEntity;
import com.rjtech.user.repository.UserRepository;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;
//import com.rjtech.workdairy.WorkDairyPlantDtlEntityCopy;
@Service(value = "projSettingsService")
@RJSService(modulecode = "projSettingsService")
@Transactional
public class ProjSettingsServiceImpl implements ProjSettingsService {

	private static String pot = "\"Project on Track\"";

	@Autowired
	private EmpProjRegisterRepositoryCopy empProjRegisterRepository;
	
	@Autowired
	private ChangeOrderNormaltimeEntityRepository changeOrderNormaltimeEntityRepository;

	@Autowired
	private EPSProjRepository projRepository;

	@Autowired
	private EmpAttendanceRepository empAttendanceRepository;

	@Autowired
	private ProjLeaveTypeRepository leaveTypeRepository;

	@Autowired
	private EmpCostWorkDairyRepositoryCopy empCostWorkDairyRepository;

	@Autowired
	private TimeSheetEmpDtlRepositoryCopy timeSheetEmpDtlRepository;

	@Autowired
	private TimeSheetEmpExpenseRepositoryCopy timeSheetEmpExpenseRepository;

	@Autowired
	private PlantChargeOutRateRepositoryCopy plantChargeOutRateRepository;

	@Autowired
	private PlantCostWorkDairyRepositoryCopy plantCostWorkDairyRepository;

	@Autowired
	private MaterialStatusWorkDairyRepositoryCopy materialStatusWorkDairyRepository;

	@Autowired
	private EPSProjService epsProjService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Qualifier("projSettingsPlantNotificationsRepository")
	private PlantNotificationRepositoryCopy plantNotificationRepository;

	@Autowired
	private PlantNotificationsRepositoryCopy plantNotificationsRepository;

	@Autowired
	private EmployeeNotificationsRepositoryCopy empNotificationsRepository;

	@Autowired
	@Qualifier("projSettingsMaterialNotificationsRepository")
	private MaterialNotificationsRepositoryCopy materialNotificationsRepository;

	@Autowired
	private CommonEmailServiceImpl commonEmail;

	@Autowired
	private AttendenceNotificationsRepositoryCopy attendenceNotificationsRepository;

	@Autowired
	private ProjAttendenceApprRepository projAttendenceApprRepository;

	@Autowired
	private ProjGeneralRepository projGeneralRepository;

	@Autowired
	private ProjAttendenceRepository projAttendenceRepository;

	@Autowired
	private ProjWorkDairyRepository projWorkDairyRepository;

	@Autowired
	private ProjTimeSheetRepositorySettings projTimeSheetRepository;

	@Autowired
	private ProjProcureRepository projProcureRepository;

	@Autowired
	private ProjProcureApprRepository projProcureApprRepository;

	@Autowired
	private ProjProcureNotificationRepository projProcureNotificationRepository;

	@Autowired
	private ProjEmpTransRepository projEmpTransRepository;

	@Autowired
	private ProjPlantTransRepository projPlantTransRepository;

	@Autowired
	private ProjMaterialTransRepository projMaterialTransRepository;

	@Autowired
	private ProjMaterialTransApprRepository projMaterialTransApprRepository;

	@Autowired
	private ProjEstimateRepository projEstimateRepository;

	@Autowired
	private ProjectMaterialRepository projectMaterialRepository;

	@Autowired
	private MaterialClassRepository materialClassRepository;

	@Autowired
	private ProjCostStatementsRepository projCostStatementsRepository;

	@Autowired
	private ProjectPlantsRepository projectPlantsRepository;

	@Autowired
	private ProjReportsRepository projReportsRepository;

	@Autowired
	private ProjManpowerRepository projManpowerRepository;

	@Autowired
	private ProjProgressRepository projProgressRepository;

	@Autowired
	private ResourceCurveRepository projResourceCurveRepository;

	@Autowired
	private CommonRepository commonRepository;

	@Autowired
	private ProjProgressClaimRepository projProgressClaimRepository;

	@Autowired
	private ProjProgressClaimApprRepository projProgressClaimApprRepository;

	@Autowired
	private ProjPayRollCycleRepository projPayRollCycleRepository;

	@Autowired
	private ProjProgressClaimePeriodRepository projProgressClaimePeriodRepository;

	@Autowired
	private ProjTimeSheetWeekRepository projTimeSheetWeekRepository;

	@Autowired
	private ProjNoteBookRepository projNoteBookRepository;

	@Autowired
	private ProjPerformenceThresholdRepository projPerformenceThresholdRepository;

	@Autowired
	private ProjLeaveRequestRepository leaveRequestRepository;

	@Autowired
	private ProjCostItemRepositoryCopy projCostItemRepository;

	@Autowired
	private ProjLeaveApprRepository projLeaveApprRepository;

	@Autowired
	private ProjStatusDatesRepository projStatusDatesRepository;

	@Autowired
	private GlobalCalendarRepository globalCalendarRepository;

	@Autowired
	private ProjCrewRepositoryCopy projCrewRepository;

	@Autowired
	private ProjStatusMileStonesRepository projStatusMileStonesRepository;

	@Autowired
	private ProjSOWItemRepositoryCopy projSOWItemRepository;

	@Autowired
	private ProjScheduleService projScheduleService;

	@Autowired
	private EPSProjRepository epsProjRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ProfitCentreRepositoryCopy profitCentreRepository;

	@Autowired
	private ResourceCurveRepository resourceCurveRepository;

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private ProcureCatgRepository procureCatgRepository;

	@Autowired
	private EmpClassRepository empClassRepository;

	@Autowired
	private MeasurementRepository measurementRepository;

	@Autowired
	private PlantClassRepository plantClassRepository;

	@Autowired
	private ActualHrsService actualHrsServiceImpl;

	@Autowired
	private ActualAmountService actualAmountService;

	@Autowired
	private ClientRegRepository clientRegRepository;

	@Autowired
	private ProjSowTotalActualRepository projSowTotalActualRepository;

	@Autowired
	private PurchaseOrderRepositoryCopy purchaseOrderRepositoryCopy;

	@Autowired
	private WorkDairyAdditionalTimeRepositoryCopy workDairyAdditionalTimeRepository;

	@Autowired
	private TimeSheetAdditionalTimeRepositoryCopy timeSheetAdditionalTimeRepository;

	@Autowired
	private WorkDairyNotificationRepositoryCopy workDairyNotificationRepository;

	@Autowired
	private ResourceAssignmentDataRepository resourceAssignmentDataRepository;

	@Autowired
	private TimeSheetNotificationsRepositoryCopy timeSheetNotificationsRepositoryCopy;

	@Autowired
	private EntpsProjRepositorCopy entpsProjRepositorCopy;

	@Autowired
	private EmpLeaveReqApprRepository empLeaveReqApprRepository;

	@Autowired
	private EmpTransferReqAprRepository empTransferReqAprRepository;
	
	@Autowired
	private ResourceAssignmentDataValueRepository resourceAssignmentDataValueRepository;
	
	@Autowired
	private CostCodeActualDetailsService costCodeActualDetailsService;
	
	@Autowired
	private EmpWageWorkDairyRepositoryCopy empWageWorkDairyRepository;
	
	@Autowired
	private ProgressWorkDairyRepositoryCopy progressWorkDairyRepository;
	
	@Autowired
	private WorkDairyRepositoryCopy workDairyRepositoryCopy;
	
	@Autowired
	private WorkDairyEmpDtlRepositoryCopy workDairyEmpDtlRepositoryCopy;
	
	@Autowired
	private EmpCostWorkDairyRepositoryCopy empCostWorkDairyRepositoryCopy;
	
	@Autowired
	private SchofEstimatesRepository schofEstimatesRepository;
	
	@Autowired 
	private	SchofRatesRepository schofRatesRepository;
	
	@Autowired
	private ResourceBudgetRepository resourceBudgetRepository;
	
	@Autowired
	private SoeAddltionalTimeRepository soeAddltionalTimeRepository;
	
	public void saveProjAttendenceAppr(ProjAttendenceApprSaveReq projAttendenceApprSaveReq) {

		for (ProjAttendceApprTO projTO : projAttendenceApprSaveReq.getProjAttendceApprTOs()) {
			AttendenceNotificationsEntity attendenceNotificationsEntity = attendenceNotificationsRepository
					.findOne(projTO.getNotificationId());
			attendenceNotificationsEntity.setNotificationStatus("Approved");
			attendenceNotificationsEntity.setFromDate(CommonUtil.convertStringToDate(projTO.getFromDate()));
			attendenceNotificationsEntity.setToDate(CommonUtil.convertStringToDate(projTO.getToDate()));

			sendProjAttendanceApprMail(attendenceNotificationsEntity);
		}
		projAttendenceApprRepository.save(ProjAttendenceApprHandler.convertPOJOToEntity(
				projAttendenceApprSaveReq.getProjAttendceApprTOs(), projCrewRepository, projAttendenceRepository));
	}

	private void sendProjAttendanceApprMail(AttendenceNotificationsEntity attendenceNotificationsEntity) {
		String projName = "";
		String toEmail = "";
		String ccEmail = "";
		String userName = "";
		String crewName = "";
		String generatedCode = "";
		String month = "";
		String epsName = "";
		String fromDate = null;
		String toDate = null;
		String forMonthYear = "";

		if (null != attendenceNotificationsEntity) {
			if (null != attendenceNotificationsEntity.getProjCrewMstrEntity()) {
				if (null != attendenceNotificationsEntity.getProjCrewMstrEntity().getProjId()) {
					projName = attendenceNotificationsEntity.getProjCrewMstrEntity().getProjId().getProjName();
					crewName = attendenceNotificationsEntity.getProjCrewMstrEntity().getDesc();
					epsName = attendenceNotificationsEntity.getProjCrewMstrEntity().getProjId()
							.getParentProjectMstrEntity().getProjName();
				}
			}
			toEmail = attendenceNotificationsEntity.getToUserId().getEmail();
			userName = attendenceNotificationsEntity.getToUserId().getUserName();
			generatedCode = EmpAttendanceHandler.generateCode(attendenceNotificationsEntity);
			month = CommonUtil.getMonth(attendenceNotificationsEntity.getCreatedOn());
			fromDate = CommonUtil.convertDateToString(attendenceNotificationsEntity.getFromDate());
			toDate = CommonUtil.convertDateToString(attendenceNotificationsEntity.getToDate());
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
			forMonthYear = dateFormat.format(attendenceNotificationsEntity.getFromDate());
		}
		String toSubject = "Approval for Extension of time for Attendance Records - " + crewName + " Crew for "
				+ forMonthYear;
		String text = "<html><body><p>" + userName + ",</p>"
				+ "<p>I have approved your request for extension of time for completion of time elapsed attendance records "
				+ "pertains to " + crewName + " crew for the month of " + forMonthYear + ", through " + pot
				+ ", as per details mentioned here below.</p>"
				+ "<table border='1' bordercolor='#000' cellspacing='0' cellpadding='5'>" + "<tr><td>EPS </td><td>"
				+ epsName + "</td></tr><tr><td>Project </td><td>" + projName + "</td></tr><tr><td>Crew Name </td><td>"
				+ crewName + "</td></tr><tr><td>Month </td><td>" + forMonthYear + "</td></tr><tr><td>Affected </td><td>"
				+ fromDate + " TO " + toDate + "</td></tr><tr><td>Notification number </td><td>" + generatedCode
				+ "</td></tr></table><p>This is for your information please.</p>" + "<p>Regards,</p>" + "<p>"
				+ AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";

		commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);

	}

	@Override
	public ProjGeneralsResp getProjGenerals(ProjGeneralsGetReq projGeneralsGetReq) {
		ProjGeneralsResp projGeneralsResp = new ProjGeneralsResp();

		List<ProjGeneralMstrEntity> projGeneralMstrEntites = projGeneralRepository
				.findProjGenerals(projGeneralsGetReq.getProjId(), projGeneralsGetReq.getStatus());
		for (ProjGeneralMstrEntity projGeneralMstrEntity : projGeneralMstrEntites) {
			projGeneralsResp.setProjGeneralMstrTO(ProjGeneralHandler.convertEntityToPOJO(projGeneralMstrEntity));

		}
		return projGeneralsResp;
	}

	@Override
	public ProjGeneralsResp getMultiProjGenerals(ProjGeneralsGetReq projGeneralsGetReq) {
		ProjGeneralsResp projGeneralsResp = new ProjGeneralsResp();

		List<ProjGeneralMstrEntity> projGeneralMstrEntites = projGeneralRepository
				.findProjGenerals(projGeneralsGetReq.getProjIds(), projGeneralsGetReq.getStatus());
		for (ProjGeneralMstrEntity projGeneralMstrEntity : projGeneralMstrEntites) {
			projGeneralsResp.getProjGeneralMstrTOs().add(ProjGeneralHandler.convertEntityToPOJO(projGeneralMstrEntity));
		}
		return projGeneralsResp;
	}

	public void saveProjGenerals(ProjGeneralSaveReq projGeneralSaveReq) {
		ProjGeneralMstrEntity projGeneralEntity = null;
		if (CommonUtil.objectNotNull(projGeneralSaveReq.getProjGeneralMstrTO())) {
			Long projSettingsId = projGeneralSaveReq.getProjGeneralMstrTO().getId();
			if (projSettingsId != null)
				projGeneralEntity = projGeneralRepository.findOne(projSettingsId);
			if (CommonUtil.objectNotNull(projGeneralEntity)) {
				projGeneralEntity.setId(projGeneralSaveReq.getProjGeneralMstrTO().getId());
			} else {
				projGeneralEntity = new ProjGeneralMstrEntity();
			}
			projGeneralRepository.save(ProjGeneralHandler.convertOnePojoToEntity(projGeneralEntity,
					projGeneralSaveReq.getProjGeneralMstrTO(), companyRepository, epsProjRepository,
					globalCalendarRepository, profitCentreRepository, resourceCurveRepository, loginRepository));
		}
	}

	public void saveWorkDairyAppr(ProjWorkDairyApprSaveReq projWorkDairyApprSaveReq) {
		String status = "Approved";
		for (ProjWorkDairyApprTO projWorkDairyApprTO : projWorkDairyApprSaveReq.getProjWorkDairyApprTOs()) {
			// setting to AdditionalTime Entity
			WorkDairyAdditionalTimeEntity workDairyAdditionalTimeEntity = workDairyAdditionalTimeRepository
					.findOne(projWorkDairyApprTO.getNotificationId());

			workDairyAdditionalTimeEntity.setStatus(status);
			workDairyAdditionalTimeEntity
					.setFromDate(CommonUtil.convertStringToDate(projWorkDairyApprTO.getFromDate()));
			workDairyAdditionalTimeEntity.setToDate(CommonUtil.convertStringToDate(projWorkDairyApprTO.getToDate()));
			workDairyAdditionalTimeEntity.setApprovedDate(new Date());

			// setting to notification Entity
			List<WorkDairyNotificationsEntity> entities = workDairyNotificationRepository.findByWorkDairyId(
					"Request for Additional Time", "Pending",
					workDairyAdditionalTimeEntity.getWorkDairyEntity().getId());
			for (WorkDairyNotificationsEntity workDairyNotificationsEntity : entities) {
				workDairyNotificationsEntity.setNotificationStatus("Approved");
			}
			sendWorkDairyApprMail(workDairyAdditionalTimeEntity);
		}
	}

	private void sendWorkDairyApprMail(WorkDairyAdditionalTimeEntity workDairyAdditionalTimeEntity) {

		String type = null;
		if ("Original".equalsIgnoreCase(workDairyAdditionalTimeEntity.getType())
				|| workDairyAdditionalTimeEntity.getType() == null) {
			type = "Approval for Additional Time Work Diary Original - ";
		} else if ("Internal".equalsIgnoreCase(workDairyAdditionalTimeEntity.getType())) {
			type = "Approval for Additional Time Work Diary Internal - ";
		} else {
			type = "Approval for Additional Time Work Diary External - ";
		}
		String apprName = "";
		String epsName = "";
		String projName = "";
		String crewName = "";
		String toEmail = null;
		String ccEmail = "";
		String code = "";
		String code1 = "";
		String code2 = "";
		String code3 = "";
		String text = null;
		String timeSheetNumber = "";
		Long projId = null;
		String generatedCode = "";
		Date weekStartDate = new Date();

		if (null != workDairyAdditionalTimeEntity) {
			ProjCrewMstrEntity projCrewMstrEntity = workDairyAdditionalTimeEntity.getCrewId();
			if (projCrewMstrEntity != null) {
				if (null != projCrewMstrEntity.getDesc())
					crewName = projCrewMstrEntity.getDesc();
				code = projCrewMstrEntity.getCode();
				if (projCrewMstrEntity.getProjId() != null)
					epsName = projCrewMstrEntity.getProjId().getParentProjectMstrEntity().getProjName();
				if (projCrewMstrEntity.getProjId() != null)
					projName = projCrewMstrEntity.getProjId().getProjName();
			}
			apprName = workDairyAdditionalTimeEntity.getApprUser().getUserName();
			toEmail = workDairyAdditionalTimeEntity.getApprUser().getEmail();

			String date = CommonUtil.convertDateToString(workDairyAdditionalTimeEntity.getCreatedOn());
			String toSubject = type + code + " dated " + date;
			log.info("projName " + projName);
			log.info("crewName " + crewName);
			log.info("epsName " + epsName);
			log.info("code " + code);
			log.info("code1 " + code1);
			log.info("code2 " + code2);
			log.info("code3 " + code3);
			log.info("apprName " + apprName);
			log.info("toEmail " + toEmail);
			log.info("date " + date);
			log.info("toSubject " + toSubject);

			text = "<html><body><p>" + apprName + ",</p>" + "<p>I have approved Work Diary through " + pot
					+ ", as per details mentioned here below. </p>" + "<table border='1'><tr><td>EPS </td><td>"
					+ epsName + "</td></tr><tr><td> Project </td><td>" + projName + "</td></tr><tr><td>Crew </td><td>"
					+ crewName + "</td></tr><tr><td>Work Diary Number</td><td>" + code
					+ "</td></tr><tr><td>Date</td><td>" + new Date()
					+ "</td></tr></table><br>This is for your Information please." + "<p>Regards,</p>" + "<p>"
					+ AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
			log.info("text " + text);
			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
		}

	}

	public ProjAttendenceResp getProjAttendence(ProjAttendenceGetReq projAttendenceGetReq) {
		ProjAttendenceResp projAttendenceResp = new ProjAttendenceResp();
		List<AttendanceNormalTimeEntity> projAttendenceEntites = projAttendenceRepository.findProjAttendences(
				projAttendenceGetReq.getProjId(), projAttendenceGetReq.getType(), projAttendenceGetReq.getStatus());
		for (AttendanceNormalTimeEntity projAttendenceEntity : projAttendenceEntites) {
			projAttendenceResp.getProjAttendenceTOs()
					.add(ProjAttendenceHandler.convertEntityToPOJO(projAttendenceEntity));
		}
		return projAttendenceResp;
	}

	public void saveProjAttendence(ProjAttendenceSaveReq projAttendenceSaveReq) {

		projAttendenceRepository.save(ProjAttendenceHandler
				.convertPOJOToEntity(projAttendenceSaveReq.getProjAttendenceTOs(), epsProjRepository));
	}

	public ProjWorkDairyResp getWorkDairy(ProjWorkDairyGetReq projWorkDairyGetReq) {
		ProjWorkDairyResp projWorkDairyResp = new ProjWorkDairyResp();
		List<WorkDairyNormalTimeEntity> projWorkDairyMstrEntites = projWorkDairyRepository
				.findProjWorkDairy(projWorkDairyGetReq.getProjId(), projWorkDairyGetReq.getStatus());
		for (WorkDairyNormalTimeEntity projWorkDairyMstrEntity : projWorkDairyMstrEntites) {
			projWorkDairyResp.getProjWorkDairyMstrTOs()
					.add(ProjWorkDairyHandler.convertEntityToPOJO(projWorkDairyMstrEntity));
		}
		return projWorkDairyResp;
	}

	public void saveWorkDairy(ProjWorkDairySaveReq projWorkDairySaveReq) {

		projWorkDairyRepository.save(ProjWorkDairyHandler
				.convertPOJOToEntity(projWorkDairySaveReq.getProjWorkDairyMstrTOs(), epsProjRepository));
	}

	public ProjTimeSheetResp getProjTimeSheet(ProjTimeSheetGetReq projTimeSheetGetReq) {

		ProjTimeSheetResp projTimeSheetResp = new ProjTimeSheetResp();
		List<TimesheetNormalTimeEntity> projTimeSheetEntites = projTimeSheetRepository
				.findProjTimeSheet(projTimeSheetGetReq.getProjId(), projTimeSheetGetReq.getStatus());

		if (CommonUtil.isListHasData(projTimeSheetEntites)) {
			for (TimesheetNormalTimeEntity projTimeSheetEntity : projTimeSheetEntites) {
				projTimeSheetResp.getProjTimeSheetTOs()
						.add(ProjTimeSheetHandler.convertEntityToPOJO(projTimeSheetEntity));
			}
		}

		ProjTimeSheetWeekDtlEntity projTimeSheetWeekDtlEntity = projTimeSheetWeekRepository
				.findProjTimeSheetWeekDtl(projTimeSheetGetReq.getProjId(), projTimeSheetGetReq.getStatus());

		if (CommonUtil.objectNotNull(projTimeSheetWeekDtlEntity)) {
			projTimeSheetResp.setProjTimeSheetWeekDtlTO(
					ProjTimeSheetWeekHandler.convertEntityToPOJO(projTimeSheetWeekDtlEntity));
		}
		return projTimeSheetResp;
	}

	public void saveProjTimeSheet(ProjTimeSheetSaveReq projTimeSheetSaveReq) {

		List<TimesheetNormalTimeEntity> projTimeSheets = ProjTimeSheetHandler
				.convertPOJOToEntity(projTimeSheetSaveReq.getProjTimeSheetTOs(), epsProjRepository);
		projTimeSheetRepository.save(projTimeSheets);

		ProjTimeSheetWeekDtlEntity entity = ProjTimeSheetWeekHandler
				.convertOnePojoToOneEntity(projTimeSheetSaveReq.getProjTimeSheetWeekDtlTO(), epsProjRepository);

		ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projTimeSheetSaveReq.getProjId());
		entity.setProjMstrEntity(projMstrEntity);

		projTimeSheetWeekRepository.save(entity);
	}

	public void saveProjTimeSheetAppr(ProjTimeSheetApprSaveReq projTimeSheetApprSaveReq) {
		log.info("Test saveProjTimeSheetAppr1 Method");
		Long timeSheetId = null;
		Long notificationId = null;
		for (ProjTimeSheetApprTO projTimeSheetApprTO : projTimeSheetApprSaveReq.getProjTimeSheetApprTOs()) {
			TimeSheetAdditionalTimeEntity timeSheetAdditionalTimeEntity = timeSheetAdditionalTimeRepository
					.findOne(projTimeSheetApprTO.getNotificationId());

			// setting to AdditionalTime Entity
			timeSheetAdditionalTimeEntity.setNotificationStatus("Approved");
			timeSheetAdditionalTimeEntity.setGrantHrs(projTimeSheetApprTO.getGrantHrs());

			timeSheetId = timeSheetAdditionalTimeEntity.getTimeSheetId();

			List<TimeSheetNotificationsEntity> timeSheetNotificationsEntities = new ArrayList<>();
			timeSheetNotificationsEntities = timeSheetNotificationsRepositoryCopy.findNotificationId(timeSheetId,
					"Pending", "Request for Additional Time");

			for (TimeSheetNotificationsEntity timeSheetNotificationsEntity : timeSheetNotificationsEntities) {
				notificationId = timeSheetNotificationsEntity.getId();
				timeSheetNotificationsRepositoryCopy.updateNotificationStatus("Approved", notificationId);
			}
			sendProjTimeSheetApprMail(timeSheetAdditionalTimeEntity);
		}
	}

	private void sendProjTimeSheetApprMail(TimeSheetAdditionalTimeEntity timeSheetAdditionalTimeEntity) {
		String apprName = "";
		String epsName = "";
		String projName = "";
		String toEmail = null;
		String ccEmail = "";
		String code = "";
		String text = null;
		String timeSheetNumber = "";
		Long projId = null;
		String generatedCode = "";
		Date weekStartDate = new Date();
		if (null != timeSheetAdditionalTimeEntity) {
			log.info("timeSheetAdditionalTimeEntity IS NOT NULL");
		} else {
			log.info("timeSheetAdditionalTimeEntity IS NULL");
		}
		if (null != timeSheetAdditionalTimeEntity) {

			ProjMstrEntity projMstrEntity = timeSheetAdditionalTimeEntity.getProjCrewMstrEntity().getProjId();

			projId = projMstrEntity.getProjectId();

			List<ProjMstrEntity> projEntityCode = entpsProjRepositorCopy.getProjCode(projId);
			// List<ProjMstrEntity> projEntityCode =
			// entpsProjRepositorCopy.getProjCode(timeSheetNotificationsEntity.getTimeSheetEntity().getProjMstrEntity().getProjectId());
			if (CommonUtil.objectNotNull(projEntityCode)) {
				for (ProjMstrEntity projMstr : projEntityCode) {
					code = projMstr.getCode();
					projName = projMstr.getProjName();
					epsName = projMstr.getParentProjectMstrEntity().getProjName();
				}
			}

			// TimeSheetEntityCopy timesheetentitycopy =
			// timeSheetRepositoryCopy.findOne(timeSheetAdditionalTimeEntity.getTimeSheetId());
			// weekStartDate = timesheetentitycopy.getWeekStartDate();
			// weekStartDate =
			// timeSheetNotificationsEntity.getTimeSheetEntity().getWeekStartDate();

			if (timeSheetAdditionalTimeEntity.getApprUser() != null) {
				apprName = timeSheetAdditionalTimeEntity.getApprUser().getUserName();
			}
			generatedCode = TimeSheetNotificationsHandlerCopy.generateCode(timeSheetAdditionalTimeEntity, code);
			log.info("generatedCode " + generatedCode);
			toEmail = timeSheetAdditionalTimeEntity.getApprUser().getEmail();
			log.info("toEmail " + toEmail);
			String toSubject = "Approval of Additional Time of Time Sheet - " + code + " for Week Commencing "
					+ weekStartDate;
			log.info("projName " + projName);
			log.info("code " + code);
			log.info("toSubject " + toSubject);

			text = "<html><body><p>" + apprName + ",</p>" + "<p>I have approved Time Sheet through " + pot
					+ ", as per details mentioned here below.</p>" + "<table border='1'><tr><td>EPS </td><td>" + epsName
					+ "</td></tr><tr><td>Project </td><td>" + projName + "</td></tr><tr><td>Time Sheet Number</td><td>"
					+ generatedCode + "</td></tr><tr><td>Week Commencing </td><td>" + weekStartDate
					+ "</td></tr></table><br>This is for your Information please." + "<p>Regards,</p>" + "<p>"
					+ AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
		}

	}

	public ProjProcureResp getProjProcurement(ProjProcureGetReq projProcureGetReq) {
		ProjProcureResp projProcureResp = new ProjProcureResp();
		List<ProcurementNormalTimeEntity> projProcurementEntites = projProcureRepository
				.findProjProcure(projProcureGetReq.getProjId(), projProcureGetReq.getStatus());
		for (ProcurementNormalTimeEntity projProcurementEntity : projProcurementEntites) {
			projProcureResp.getProjProcurementTOs()
					.add(ProjProcurementHandler.convertEntityToPOJO(projProcurementEntity));

		}
		return projProcureResp;
	}

	public void saveProjProcurement(ProjProcureSaveReq projProcureSaveReq) {

		projProcureRepository.save(ProjProcurementHandler
				.convertPOJOToEntity(projProcureSaveReq.getProjProcurementTOs(), epsProjRepository));
	}

	public void saveProjProcurementAppr(ProjProcureApprSaveReq projProcureApprSaveReq) {
		log.info("Approving Additional TIme in procurement");
		// projProcureApprRepository.save(ProjProcurementApprHandler
		// .convertPOJOToEntity(projProcureApprSaveReq.getProjProcurementApprTOs(),
		// projProcureRepository, commonRepository));

		List<ProjProcurementApprTO> projProcurementApprTO = projProcureApprSaveReq.getProjProcurementApprTOs();
		for (ProjProcurementApprTO projProcurApprTO : projProcurementApprTO) {
			// Approve Additional Time (Updating ProcurementAddtionalTimeApprEntity)

			ProcurementAddtionalTimeApprEntity procurementAddtionalTimeApprEntity = projProcureApprRepository
					.findOne(projProcurApprTO.getId());

			procurementAddtionalTimeApprEntity.setRequisitionDate(new Date());
			procurementAddtionalTimeApprEntity.setLatest(true);
			procurementAddtionalTimeApprEntity.setStatus(projProcurApprTO.getStatus());
			procurementAddtionalTimeApprEntity.setNotificationStatus("Approved");
			procurementAddtionalTimeApprEntity.setProcureStage(projProcurApprTO.getStage());
			procurementAddtionalTimeApprEntity.setApprUser(commonRepository.findOne(projProcurApprTO.getApprUserId()));

			// Updating ProcurementNotificationsEntity for Status and Message)
			ProcurementNotificationsEntity procurementNotificationsEntity = projProcureNotificationRepository
					.findOne(projProcurApprTO.getNotificationId());
			procurementNotificationsEntity.setApprStatus(2);
			if (projProcurApprTO.getStage().equalsIgnoreCase("Stage 1 Internal Approval")) {
				procurementNotificationsEntity.setNotificationStatus("Request for Stage 1 Procurement Approval");
			} else {
				procurementNotificationsEntity.setNotificationStatus("Request for Stage 2 Procurement Approval");
			}
			procurementNotificationsEntity.setNotificationStatus("Request for Stage 1 Procurement Approval");
			procurementNotificationsEntity.setReqComments("Additional Time Approved");

			sendProcurementAdditionalTimeApprovalMail(procurementNotificationsEntity,
					procurementAddtionalTimeApprEntity);// Send mail here...

		}

	}

	private void sendProcurementAdditionalTimeApprovalMail(
			ProcurementNotificationsEntity procurementNotificationsEntity1,
			ProcurementAddtionalTimeApprEntity procurementAddtionalTimeApprEntity) {
		String apprName = "";
		String epsName = "";
		String projName = "";
		String toEmail = null;
		String toSubject = null;
		String stage = null;
		String procCateg = null;
		String apprStage = null;
		String approvalStage = null;
		String reqId = null;
		String notId = null;
		String prString = null;
		String text = null;
		String reqDate = null;
		String timeUpTo = null;
		Long normalTimeId = null;
		LocalDate localDate = null;
		LocalDateTime localdateTime = null;

		if (null != procurementNotificationsEntity1) {

			ProcurementNormalTimeEntity ProcurementNormalTimeEntity = projProcureRepository
					.findOne(procurementAddtionalTimeApprEntity.getProjProcurementEntity().getId());

			if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Request")
					|| procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Approval")) {
				approvalStage = "1";
			} else {
				approvalStage = "2";
			}

			if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Request")) {
				prString = "PRS1";
			} else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Approval")) {
				prString = "PAS1";
			} else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 2 Request")) {
				prString = "PRS2";
			} else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 2 Approval")) {
				prString = "PAS2";
			}

			epsName = procurementNotificationsEntity1.getProjId().getParentProjectMstrEntity().getProjName();
			projName = procurementNotificationsEntity1.getProjId().getProjName();
			procCateg = procurementNotificationsEntity1.getProcureCatg();
			apprName = procurementNotificationsEntity1.getToUserId().getFirstName() + " "
					+ procurementNotificationsEntity1.getToUserId().getLastName();
			apprStage = approvalStage;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy");
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
			if (procurementAddtionalTimeApprEntity != null) {
				localDate = procurementAddtionalTimeApprEntity.getRequisitionDate().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
				localdateTime = localDate.atStartOfDay();
				localdateTime = localdateTime.plusDays(1);
				localdateTime = localdateTime.plusDays(ProcurementNormalTimeEntity.getCutOffDays());
				localdateTime = localdateTime.plusHours(ProcurementNormalTimeEntity.getCutOffHours());
				localdateTime = localdateTime.plusMinutes(ProcurementNormalTimeEntity.getCutOffMinutes());
			}

			reqDate = dateFormat.format(procurementNotificationsEntity1.getDate());
//			timeUpTo = dateTimeFormat.format(localdateTime);
			reqId = procurementNotificationsEntity1.getModuleCode() + " dated " + reqDate;
			notId = prString + "-" + procurementNotificationsEntity1.getId() + " dated " + reqDate;
			toEmail = procurementNotificationsEntity1.getToUserId().getEmail();

			stage = procurementNotificationsEntity1.getProcureStage();

			toSubject = "Granting of  additional time - Procurement Stage " + approvalStage + "  Approval ";
			text = "<html><body><p>" + apprName + ",</p>" + "<p>I have granted additional time for Procurement stage "
					+ approvalStage + " approval through " + pot + ", as per details mentioned here below.</p>"
					+ "<table border='1'>" + "<tr><td>EPS </td><td>" + epsName + "</td></tr>"
					+ "<tr><td>Project </td><td>" + projName + "</td></tr>" + "<tr><td>Procurement Category </td><td>"
					+ procCateg + "</td></tr>" + "<tr><td>Approval  Stage</td><td>" + apprStage + "</td></tr>"
					+ "<tr><td>Requisition ID</td><td>" + reqId + "</td></tr>" + "<tr><td>Notification ID</td><td>"
					+ notId + "</td></tr>" + "<tr><td>Addl. Time granted up to </td><td>" + "</td></tr>"
					+ "</table><br>This is for your Information and for further action please." + "<p>Regards,</p>"
					+ "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
			if (CommonUtil.isNotBlankStr(toEmail))
				commonEmail.sendEmailNotification(toEmail, "", toSubject, text);
		}

	}

	public ProjEmpTransResp getEmpTrans(ProjEmpTransGetReq projEmpTransGetReq) {
		ProjEmpTransResp projEmpTransResp = new ProjEmpTransResp();
		List<EmpTransNormalTimeEntity> projEmpTransEntites = projEmpTransRepository
				.findProjEmpTrans(projEmpTransGetReq.getProjId(), projEmpTransGetReq.getStatus());
		for (EmpTransNormalTimeEntity projEmpTransEntity : projEmpTransEntites) {
			projEmpTransResp.getProjEmpTransTOs().add(ProjEmpTransHandler.convertEntityToPOJO(projEmpTransEntity));
		}
		return projEmpTransResp;
	}

	public ProjEmpTransResp saveEmpTrans(ProjEmpTransSaveReq projEmpTransSaveReq) {
		ProjEmpTransResp projEmpResponse = new ProjEmpTransResp();
		List<EmpTransNormalTimeEntity> projEmpTransEntites = projEmpTransRepository.save(
				ProjEmpTransHandler.convertPOJOToEntity(projEmpTransSaveReq.getProjEmpTransTOs(), epsProjRepository));
		for (EmpTransNormalTimeEntity projEmpTransEntity : projEmpTransEntites) {
			projEmpResponse.getProjEmpTransTOs().add(ProjEmpTransHandler.convertEntityToPOJO(projEmpTransEntity));
		}
		return projEmpResponse;
	}

	public void saveEmpTransAppr(ProjEmpTransApprSaveReq projEmpTransApprSaveReq) {

		if (CommonUtil.objectNotNull(projEmpTransApprSaveReq.getProjEmpTransApprTOs())) {
			for (ProjEmpTransApprTO projEmpTransApprTO : projEmpTransApprSaveReq.getProjEmpTransApprTOs()) {
				// EmployeeNotificationsEntityCopy employeeNotificationsEntity =
				// empNotificationsRepository.findOne(projEmpTransApprTO.getNotificationId());
				EmployeeNotificationsEntityCopy employeeNotificationsEntity = empNotificationsRepository
						.findEmpTransReqApprRecord(projEmpTransApprTO.getNotificationId(), "TRANSFER");
				String AddlTimeLeaveType = null;
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				String apprDate = null;

				EmpTransferReqApprEntity empTransferReqApprEntity = empTransferReqAprRepository
						.findEmpTransReqApprRecord(projEmpTransApprTO.getId());

				//making changes
				EmpTransNormalTimeEntity empTransNormalTimeEntity = projEmpTransRepository.findEmpTransNormalTimeByProjId(
						employeeNotificationsEntity.getProjId().getProjectId(), 1);
				
				empTransNormalTimeEntity.setCutOffDays(2);
				empTransNormalTimeEntity.setCutOffHours(10);
				
				//
				projEmpTransRepository.save(empTransNormalTimeEntity);
		//		EmpTransReqApprHandler.convertEntityToPOJO(empTransferReqApprEntity);
				/*
				 * EmpLeaveReqApprEntity leaveReqApprEntity =
				 * empLeaveReqApprRepository.findEmpLeaveReqApprRecord(projLeaveApprTO.getId());
				 * if (leaveReqApprEntity != null) { if (leaveReqApprEntity.getStartDate() !=
				 * null) {
				 * 
				 * apprDate = dateFormat.format(leaveReqApprEntity.getStartDate()); }
				 * AddlTimeLeaveType = "Additiaonal time granted for approval of leave from " +
				 * apprDate + " for " + leaveReqApprEntity.getTotalDays()+ " days"; }
				 * 
				 * employeeNotificationsEntity.setType(AddlTimeLeaveType);
				 */
				employeeNotificationsEntity.setNotifyStatus(projEmpTransApprTO.getNotificationStatus());
				employeeNotificationsEntity.setDate(new Date());

				// sending mail
				sendEmployeeAdditionalTimeApprovalMail(employeeNotificationsEntity);
			}
		}
	}

	private void sendEmployeeAdditionalTimeApprovalMail(EmployeeNotificationsEntityCopy employeeNotificationsEntity) {
		// Addtional time Is Approved and send Email;
		String epsName = null;
		String epsId = null;
		String projName = null;
		String ccEmail = "";
		String toEmail = "";
		String toSubject = "";
		String text = "";
		String toepsName = null;
		String toprojName = null;
		String userName = null;
		String reqDate = null;

		UserProjGetReq userProjGetReq = new UserProjGetReq();
		UserProjResp userProjResp = epsProjService.getAllUserProjects(userProjGetReq);
		Map<Long, LabelKeyTO> userProjMap = new HashMap<>();
		LabelKeyTO userProjLabelKeyTO = null;
		for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
			userProjLabelKeyTO = new LabelKeyTO();
			userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
			userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
			userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
			userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
		}
		for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
			if (CommonUtil.objectNotNull(employeeNotificationsEntity.getProjId())
					&& employeeNotificationsEntity.getProjId().getProjectId().equals(entry.getKey())) {
				epsName = entry.getValue().getCode();
				projName = entry.getValue().getName();
			}
			if (employeeNotificationsEntity.getForProject().getProjectId().equals(entry.getKey())) {
				toepsName = entry.getValue().getCode();
				toprojName = entry.getValue().getName();
			}

		}

		UserMstrEntity userMstr = employeeNotificationsEntity.getReqUserId();
		UserMstrEntity userMstr1 = employeeNotificationsEntity.getApprUserId();

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
		reqDate = dateFormat1.format(employeeNotificationsEntity.getDate());

		toEmail = userMstr1.getEmail();
		ccEmail = userMstr.getEmail();

		String code = ProjEmpTransHandler.generateCode(employeeNotificationsEntity) + " dated " + reqDate;
		String reqCode = ProjEmpTransHandler.generateReqCode(employeeNotificationsEntity) + " dated " + reqDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String apprDate = dateFormat.format(employeeNotificationsEntity.getDate());

		EmpTransNormalTimeEntity empTransNormalTimeEntity = projEmpTransRepository.findEmpTransNormalTimeByProjId(
				employeeNotificationsEntity.getProjId().getProjectId(), employeeNotificationsEntity.getStatus());

		LocalDate today = LocalDate.now();
		LocalDateTime grantedUpTo = today.atStartOfDay();
		grantedUpTo = grantedUpTo.plusDays(1);
		grantedUpTo = grantedUpTo.plusDays(empTransNormalTimeEntity.getCutOffDays());
		grantedUpTo = grantedUpTo.plusHours(empTransNormalTimeEntity.getCutOffHours());
		grantedUpTo = grantedUpTo.plusMinutes(empTransNormalTimeEntity.getCutOffMinutes());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
		String addlTimeGrantedUpTo = grantedUpTo.format(formatter);

		log.info("Granted Upto is :::::::: " + addlTimeGrantedUpTo);

		toSubject = "Grant of additional time for approval of Employee transfer to Project  -  " + toprojName;
		text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>" + "<p>Reference Notification ID " + code + "</p>"
				+ "<p>I have Granted additional time for approval of Employee transfer through " + pot
				+ ", as per details mentioned here below.</p>" + "<table border='1'>" + "<tr><td>From EPS </td><td>"
				+ epsName + "</td></tr>" + "<tr><td>From Project </td><td>" + projName + "</td></tr>"
				+ "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>" + "<tr><td>To Project </td><td>" + toprojName
				+ "</td></tr>" + "<tr><td>Requisition ID</td><td>" + reqCode + "</td></tr>"
				+ "<tr><td>Notification ID</td><td>" + code + "</td></tr>"
				+ "<tr><td>Addl. Time granted up to </td><td>" + addlTimeGrantedUpTo + "</td></tr>" + "</table>"
				+ "<p>This is for your information please.</p>" + "<p>Regards,</p>" + "<p>" + AppUserUtils.getName()
				+ "<br/>" + AppUserUtils.getDisplayRole() + "<br/>" + apprDate + " </p></body></html>";

		commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
	}

	public ProjPlantTransResp getProjPlantTrans(ProjPlantTransGetReq projPlantTransGetReq) {
		ProjPlantTransResp projPlantTransResp = new ProjPlantTransResp();
		List<PlantTransNormalTimeEntity> projPlantTransEntites = projPlantTransRepository
				.findProjPlantTrans(projPlantTransGetReq.getProjId(), projPlantTransGetReq.getStatus());
		for (PlantTransNormalTimeEntity projPlantTransEntity : projPlantTransEntites) {
			projPlantTransResp.getProjPlantTransTOs()
					.add(ProjPlantTransHandler.convertEntityToPOJO(projPlantTransEntity));
		}
		return projPlantTransResp;
	}

	public void saveProjPlantTrans(ProjPlantTransSaveReq projPlantTransSaveReq) {

		projPlantTransRepository.save(ProjPlantTransHandler
				.convertPOJOToEntity(projPlantTransSaveReq.getProjPlantTransTOs(), epsProjRepository));

	}

	public void saveProjPlantTransAppr(ProjPlantTransApprSaveReq projPlantTransApprSaveReq) {
		PlantNotificationsEntityCopy plantNotificationsEntity = null;
		for (ProjPlantTransApprTO projPlantTransApprTO : projPlantTransApprSaveReq.getProjPlantTransApprTOs()) {
			plantNotificationsEntity = plantNotificationsRepository.findOne(projPlantTransApprTO.getNotificationId());
			plantNotificationsEntity.setNotificationStatus("APPROVED");
			plantNotificationsEntity
					.setType("Plant transfer to Project -" + plantNotificationsEntity.getForProject().getProjName());
			plantNotificationsEntity.setDate(new Date());
			//setting additional time 
			PlantTransNormalTimeEntity platTransNormalTimeEntity = projPlantTransRepository.findPlantTransNormalTimeByProjId(plantNotificationsEntity.getProjId().getProjectId(), plantNotificationsEntity.getStatus());
			platTransNormalTimeEntity.setCutOffDays(2);
			platTransNormalTimeEntity.setCutOffHours(10);
			
			projPlantTransRepository.save(platTransNormalTimeEntity);
			sendEmailForAdditionalTimePlantApproval(plantNotificationsEntity);
		}
	}

	private void sendEmailForAdditionalTimePlantApproval(PlantNotificationsEntityCopy plantNotificationsEntity) {
		String epsName = null;
		String projName = null;
		String ccEmail = null;
		String toEmail = null;
		String toSubject = null;
		String text = null;
		String toepsName = null;
		String toprojName = null;
		String userName = null;
		String reqCode = null;
		String code = null;
		String apprDate = null;

		if (null != plantNotificationsEntity) {
			UserEntity userMstr1 = userRepository.findOne(plantNotificationsEntity.getReqUserId().getUserId());
			UserEntity userMstr = userRepository.findOne(plantNotificationsEntity.getApprUserId().getUserId());
			toEmail = userMstr1.getEmail();
			ccEmail = userMstr.getEmail();

			PlantTransNormalTimeEntity plantTransNormalTimeEntity = projPlantTransRepository
					.findPlantTransNormalTimeByProjId(plantNotificationsEntity.getProjId().getProjectId(),
							plantNotificationsEntity.getStatus());

			LocalDate today = LocalDate.now();
			LocalDateTime grantedUpTo = today.atStartOfDay();
			grantedUpTo = grantedUpTo.plusDays(1);
			grantedUpTo = grantedUpTo.plusDays(plantTransNormalTimeEntity.getCutOffDays());
			grantedUpTo = grantedUpTo.plusHours(plantTransNormalTimeEntity.getCutOffHours());
			grantedUpTo = grantedUpTo.plusMinutes(plantTransNormalTimeEntity.getCutOffMinutes());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
			String addlTimeGrantedUpTo = grantedUpTo.format(formatter);

			log.info("Granted Upto is :::::::: " + addlTimeGrantedUpTo);

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			apprDate = dateFormat.format(new Date());
			userName = plantNotificationsEntity.getReqUserId().getDisplayName();
			epsName = plantNotificationsEntity.getProjId().getParentProjectMstrEntity().getProjName();
			projName = plantNotificationsEntity.getProjId().getProjName();
			toprojName = plantNotificationsEntity.getForProject().getProjName();
			toepsName = plantNotificationsEntity.getForProject().getParentProjectMstrEntity().getProjName();
			reqCode = PlantNotificationsHandlerCopy.generateNotifyReqCode(plantNotificationsEntity);
			code = PlantNotificationsHandlerCopy.generateNotifyCode(plantNotificationsEntity);
			toSubject = "Grant of additional time for approval of Plant transfer to Project - " + toprojName;

			text = "<html><body><p>" + userName + ",</p>" + "<p>Reference Notification ID " + code + "</p>"
					+ "<p>I have granted additional time for approval of Plant Transfer through " + pot
					+ ", as per details mentioned here below.</p>" + "<table border='1'>" + "<tr><td>From EPS </td><td>"
					+ epsName + "</td></tr>" + "<tr><td>From Project </td><td>" + projName + "</td></tr>"
					+ "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>" + "<tr><td>To Project </td><td>"
					+ toprojName + "</td></tr>" + "<tr><td>Requisition ID</td><td>" + reqCode + "</td></tr>"
					+ "<tr><td>Notification ID</td><td>" + code + "</td></tr>"
					+ "<tr><td>Addl. Time granted up to </td><td>" + addlTimeGrantedUpTo + "</td></tr>" + "</table>"
					+ "<p>This is for your information please.</p>" + "<p>Regards,</p>" + "<p>" + AppUserUtils.getName()
					+ "<br/>" + AppUserUtils.getDisplayRole() + " <br/>" + apprDate + "</p>" + "</body></html>";

			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
		}
	}

	public ProjMaterialTransResp getProjMaterialTrans(ProjMaterialTransGetReq projMaterialTransGetReq) {
		ProjMaterialTransResp projMaterialTransResp = new ProjMaterialTransResp();
		List<MaterialTransNormalTimeEntity> projMaterialTransEntites = projMaterialTransRepository
				.findProjMaterialTrans(projMaterialTransGetReq.getProjId(), projMaterialTransGetReq.getStatus());
		for (MaterialTransNormalTimeEntity projMaterialTransEntity : projMaterialTransEntites) {
			projMaterialTransResp.getProjMaterialTransTOs()
					.add(ProjMaterialTransHandler.convertEntityToPOJO(projMaterialTransEntity));
		}
		return projMaterialTransResp;
	}

	public void saveProjMaterialTrans(ProjMaterialTransSaveReq projMaterialTransSaveReq) {

		projMaterialTransRepository.save(ProjMaterialTransHandler
				.convertPOJOToEntity(projMaterialTransSaveReq.getProjMaterialTransTOs(), epsProjRepository));

	}

	public void saveProjMaterialTransAppr(ProjMaterialTransApprSaveReq projMaterialTransApprSaveReq) {

		if (CommonUtil.objectNotNull(projMaterialTransApprSaveReq.getProjMaterialTransApprTOs())) {
			MaterialTransAddtionalTimeApprEntity projMaterialTransApprEntity = projMaterialTransApprRepository
					.findLatestApproval();
			if (CommonUtil.objectNotNull(projMaterialTransApprEntity)) {
				projMaterialTransApprEntity.setLatest(false);
			}

			MaterialNotificationsEntity materialNotificationsEntity = null;
			for (ProjMaterialTransApprTO projMaterialTransApprTO : projMaterialTransApprSaveReq
					.getProjMaterialTransApprTOs()) {
				materialNotificationsEntity = materialNotificationsRepository
						.findOne(projMaterialTransApprTO.getNotificationId());
				log.info("projMaterialTransApprTO.getNotificationId() " + projMaterialTransApprTO.getNotificationId());
				log.info("projMaterialTransApprTO.getNotificationStatus() "
						+ projMaterialTransApprTO.getNotificationStatus());
				materialNotificationsEntity.setNotificationStatus("APPROVED");
				materialNotificationsEntity.setDate(new Date());
				if (materialNotificationsEntity.getFromProjId().equals(materialNotificationsEntity.getProjId())) {
					materialNotificationsEntity
							.setType("Grant of additional time for approval of Internal project material transfer");
				} else {
					materialNotificationsEntity
							.setType("Grant of additional time for approval of External project material transfer");
				}

				sendEmpMaterialAdditionalTimeMail(materialNotificationsEntity);
			}
		}
	}

	private void sendEmpMaterialAdditionalTimeMail(MaterialNotificationsEntity materialNotificationsEntity) {
		String epsName = null;
		String projName = null;
		String ccEmail;
		String toEmail;
		String toSubject;
		String text;
		String toepsName = null;
		String toprojName = null;
		String reqDate = null;

		UserProjGetReq userProjGetReq = new UserProjGetReq();
		UserProjResp userProjResp = epsProjService.getAllUserProjects(userProjGetReq);
		Map<Long, LabelKeyTO> userProjMap = new HashMap<>();
		LabelKeyTO userProjLabelKeyTO = null;
		for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
			userProjLabelKeyTO = new LabelKeyTO();
			userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
			userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
			userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
			userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
		}
		for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
			if (materialNotificationsEntity.getFromProjId().getProjectId().equals(entry.getKey())) {
				epsName = entry.getValue().getCode();
				projName = entry.getValue().getName();
			}
			if (materialNotificationsEntity.getProjId().getProjectId().equals(entry.getKey())) {
				toepsName = entry.getValue().getCode();
				toprojName = entry.getValue().getName();
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String apprDate = dateFormat.format(new Date());

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
		reqDate = dateFormat1.format(materialNotificationsEntity.getDate());

		String requestCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
				+ ModuleCodesPrefixes.APPROVE_PREFIX.getDesc() + "-" + ModuleCodes.MATERIAL_TRANSFER.getDesc() + "-"
				+ materialNotificationsEntity.getFromProjId().getCode() + "-" + materialNotificationsEntity.getId()
				+ " dated " + reqDate;

		String notifyCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
				+ ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc() + "-"
				+ materialNotificationsEntity.getFromProjId().getCode() + "-" + materialNotificationsEntity.getId()
				+ " dated " + reqDate;

		if (materialNotificationsEntity.getFromProjId().equals(materialNotificationsEntity.getProjId())) {
			// String type = "Internal project material transfer approved";
			// String notifyStatus = "Additonal Time For Approved";
			// materialNotificationsRepository.addtionalTimeMaterialApproved(materialNotificationsEntity.getId(),
			// type, notifyStatus);

			MaterialTransNormalTimeEntity materialTransNormalTimeEntity = projMaterialTransRepository
					.findMaterialInternalTransNormalTimeByProjId(
							materialNotificationsEntity.getFromProjId().getProjectId(),
							materialNotificationsEntity.getStatus(), "Internal Transfer");

			LocalDate today = LocalDate.now();
			LocalDateTime grantedUpTo = today.atStartOfDay();
			grantedUpTo = grantedUpTo.plusDays(1);
			grantedUpTo = grantedUpTo.plusDays(materialTransNormalTimeEntity.getCutOffDays());
			grantedUpTo = grantedUpTo.plusHours(materialTransNormalTimeEntity.getCutOffHours());
			grantedUpTo = grantedUpTo.plusMinutes(materialTransNormalTimeEntity.getCutOffMinutes());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
			String addlTimeGrantedUpTo = grantedUpTo.format(formatter);

			log.info("Granted Upto is :::::::: " + addlTimeGrantedUpTo);

			UserEntity userMstr1 = userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId());
			UserEntity userMstr = userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId());
			toEmail = userMstr1.getEmail();
			ccEmail = userMstr.getEmail();
			toSubject = "Grant of additional time for approval of \"internal Project Material transfer\" in the Project – "
					+ toprojName;
			text = "<html><body><p>" + userMstr1.getDisplayName() + ",</p>" + "<p>Reference Notification ID "
					+ notifyCode + "</p>"
					+ "<p>I have granted additional time for \"Internal Project Material Transfer\" through " + pot
					+ ", as per details mentioned here below.</p>" + "<table border='1'>" + "<tr><td>From EPS </td><td>"
					+ epsName + "</td></tr>" + "<tr><td>From Project </td><td>" + projName + "</td></tr>"

					// * + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>" +
					// * "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"

					+ "<tr><td>Requisition ID</td><td>" + requestCode + "</td></tr>"
					+ "<tr><td>Notification ID</td><td>" + notifyCode + "</td></tr>"
					+ "<tr><td>Addl. Time granted up to </td><td>" + addlTimeGrantedUpTo + "</td></tr>" + "</table>"
					+ "<p>This is for your information please.</p>" + "<p>Regards,</p>" + "<p>" + AppUserUtils.getName()
					+ "<br/>" + AppUserUtils.getDisplayRole() + " <br/> " + apprDate + "</p>" + "</body></html>";

			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);

		} else {
			log.info("TO: materialNotificationsEntity.getProjId().getProjectId() "
					+ materialNotificationsEntity.getFromProjId().getProjectId());
			log.info("FROM: materialNotificationsEntity.getProjId().getProjectId() "
					+ materialNotificationsEntity.getProjId().getProjectId());
			log.info("materialNotificationsEntity.getStatus() " + materialNotificationsEntity.getStatus());
			MaterialTransNormalTimeEntity materialTransNormalTimeEntity = projMaterialTransRepository
					.findMaterialInternalTransNormalTimeByProjId(
							materialNotificationsEntity.getFromProjId().getProjectId(),
							materialNotificationsEntity.getStatus(), "External Transfer");

			LocalDate today = LocalDate.now();
			LocalDateTime grantedUpTo = today.atStartOfDay();
			grantedUpTo = grantedUpTo.plusDays(1);
			grantedUpTo = grantedUpTo.plusDays(materialTransNormalTimeEntity.getCutOffDays());
			grantedUpTo = grantedUpTo.plusHours(materialTransNormalTimeEntity.getCutOffHours());
			grantedUpTo = grantedUpTo.plusMinutes(materialTransNormalTimeEntity.getCutOffMinutes());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
			String addlTimeGrantedUpTo = grantedUpTo.format(formatter);

			log.info("Granted Upto is :::::::: " + addlTimeGrantedUpTo);

			UserEntity userMstr1 = userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId());
			UserEntity userMstr = userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId());
			toEmail = userMstr1.getEmail();
			ccEmail = userMstr.getEmail();
			toSubject = "Granting of additional time for approval of \"External project material transfer\" to the project - "
					+ toepsName;
			text = "<html><body><p>" + userMstr1.getDisplayName() + ",</p>" + "<p>Reference Notification ID "
					+ notifyCode + "</p>"
					+ "<p>I have granted additional time for approval of \"External Project Material Transfer\" through "
					+ pot + ", as per details mentioned here below.</p>" + "<table border='1'>"
					+ "<tr><td>From EPS </td><td>" + epsName + "</td></tr>" + "<tr><td>From Project </td><td>"
					+ projName + "</td></tr>" + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
					+ "<tr><td>To Project </td><td>" + toprojName + "</td></tr>" + "<tr><td>Requisition ID</td><td>"
					+ requestCode + "</td></tr>" + "<tr><td>Notification ID</td><td>" + notifyCode + "</td></tr>"
					+ "<tr><td>Addl. Time granted up to </td><td>" + addlTimeGrantedUpTo + "</td></tr>" + "</table>"
					+ "<p>This is for your information please.</p>" + "<p>Regards,</p>" + "<p>" + AppUserUtils.getName()
					+ "<br/>" + AppUserUtils.getDisplayRole() + " <br/> " + apprDate + "</p>" + "</body></html>";
			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
		}
	}

	/*
	 * if (CommonUtil.objectNotNull(projMaterialTransApprSaveReq.
	 * getProjMaterialTransApprTOs())) { MaterialTransAddtionalTimeApprEntity
	 * projMaterialTransApprEntity = projMaterialTransApprRepository
	 * .findLatestApproval(); if
	 * (CommonUtil.objectNotNull(projMaterialTransApprEntity)) {
	 * projMaterialTransApprEntity.setLatest(false); } }
	 * MaterialNotificationsEntityCopy materialNotificationsEntity = null; //
	 * addtional time approved material transfer for (ProjMaterialTransApprTO
	 * projMaterialTransApprTO : projMaterialTransApprSaveReq
	 * .getProjMaterialTransApprTOs()) { materialNotificationsEntity =
	 * materialNotificationsRepository
	 * .findOne(projMaterialTransApprTO.getNotificationId()); } // email material
	 * additonal time apporved notification String epsName = null; String projName =
	 * null; String ccEmail; String toEmail; String toSubject; String text; String
	 * toepsName = null; String toprojName = null;
	 * 
	 * UserProjGetReq userProjGetReq = new UserProjGetReq(); UserProjResp
	 * userProjResp = epsProjService.getAllUserProjects(userProjGetReq); Map<Long,
	 * LabelKeyTO> userProjMap = new HashMap<>(); LabelKeyTO userProjLabelKeyTO =
	 * null; for (UserProjDetailsTO userProjDetailsTO :
	 * userProjResp.getUserProjDetailsTOs()) { userProjLabelKeyTO = new
	 * LabelKeyTO(); userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
	 * userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
	 * userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
	 * userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO); } for
	 * (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) { if
	 * (materialNotificationsEntity.getFromProjId().getProjectId().equals(entry.
	 * getKey())) { epsName = entry.getValue().getCode(); projName =
	 * entry.getValue().getName(); } if
	 * (materialNotificationsEntity.getProjId().getProjectId().equals(entry.getKey()
	 * )) { toepsName = entry.getValue().getCode(); toprojName =
	 * entry.getValue().getName(); } }
	 * 
	 * SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); String
	 * apprDate = dateFormat.format(new Date());
	 * 
	 * if (materialNotificationsEntity.getFromProjId().equals(
	 * materialNotificationsEntity.getProjId())) { String type =
	 * "Internal project material transfer approved"; String notifyStatus =
	 * "Additonal Time For Approved";
	 * materialNotificationsRepository.addtionalTimeMaterialApproved(
	 * materialNotificationsEntity.getId(), type, notifyStatus);
	 * 
	 * 
	 * 
	 * UserEntity userMstr1 =
	 * userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId()
	 * ); UserEntity userMstr =
	 * userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId(
	 * )); toEmail = userMstr1.getEmail(); ccEmail = userMstr.getEmail(); toSubject
	 * =
	 * "Grant of additional time for approval of “internal Project  Material transfer” in the  Project – "
	 * + toprojName; text = "<html><body><p>" + userMstr1.getDisplayName() + ",</p>"
	 * + "<p>Reference notification ID " + materialNotificationsEntity.getCode() +
	 * "</p>" +
	 * "<p>I have granted additional time for “Internal Project Material Transfer” through "
	 * + pot + ", as per details mentioned here below.</p>" + "<table border='1'>" +
	 * "<tr><td>From EPS </td><td>" + epsName + "</td></tr>" +
	 * "<tr><td>From Project </td><td>" + projName + "</td></tr>"
	 * 
	 * // * + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>" + // *
	 * "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
	 * 
	 * + "<tr><td>Requisition Code</td><td>" +
	 * materialNotificationsEntity.getReqCode() + "</td></tr>" +
	 * "<tr><td>Notification Code</td><td>" + materialNotificationsEntity.getCode()
	 * + "</td></tr>" + "</table>" + "<p>This is for your information please.</p>" +
	 * "<p>Regards,</p>" + "<p>" + AppUserUtils.getName() + "<br/>" +
	 * AppUserUtils.getDisplayRole() + " <br/> " + apprDate + "</p>" +
	 * "</body></html>";
	 * 
	 * commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
	 * 
	 * } else { String type = "External project material transfer approved"; String
	 * notifyStatus = "Additonal Time For Approved";
	 * materialNotificationsRepository.addtionalTimeMaterialApproved(
	 * materialNotificationsEntity.getId(), type, notifyStatus); UserEntity
	 * userMstr1 =
	 * userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId()
	 * ); UserEntity userMstr =
	 * userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId(
	 * )); toEmail = userMstr1.getEmail(); ccEmail = userMstr.getEmail(); toSubject
	 * =
	 * "Granting of additional time for approval of “External project material transfer” to the project - "
	 * + toepsName; text = "<html><body><p>" + userMstr1.getDisplayName() + ",</p>"
	 * + "<p>Reference notification ID " + materialNotificationsEntity.getCode() +
	 * "</p>" +
	 * "<p>I have granted additional time for approval of “External Project Material Transfer” through "
	 * + pot + ", as per details mentioned here below.</p>" + "<table border='1'>" +
	 * "<tr><td>From EPS </td><td>" + epsName + "</td></tr>" +
	 * "<tr><td>From Project </td><td>" + projName + "</td></tr>" +
	 * "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>" +
	 * "<tr><td>To Project </td><td>" + toprojName + "</td></tr>" +
	 * "<tr><td>Requisition Code</td><td>" +
	 * materialNotificationsEntity.getReqCode() + "</td></tr>" +
	 * "<tr><td>Notification Code</td><td>" + materialNotificationsEntity.getCode()
	 * + "</td></tr>" + "</table>" + "<p>This is for your information please.</p>" +
	 * "<p>Regards,</p>" + "<p>" + AppUserUtils.getName() + "<br/>" +
	 * AppUserUtils.getDisplayRole() + " <br/> " + apprDate + "</p>" +
	 * "</body></html>"; commonEmail.sendEmailNotification(toEmail, ccEmail,
	 * toSubject, text); }
	 * 
	 * projMaterialTransApprRepository.save(ProjMaterialTransApprHandler
	 * .convertPOJOToEntity(projMaterialTransApprSaveReq.getProjMaterialTransApprTOs
	 * ()));
	 */

	public ProjEstimateResp getProjEstimate(ProjEstimateGetReq projEstimateGetReq) {
		ProjEstimateResp projEstimateResp = new ProjEstimateResp();
		List<ProjEstimateEntity> projEstimateEntites = projEstimateRepository
				.findProjEstimate(projEstimateGetReq.getProjId());
		projEstimateResp.setProjEstimateTOs(ProjEstimateHandler.convertEntitesToPOJOs(projEstimateEntites));
		return projEstimateResp;
	}

	public void saveProjEstimate(ProjEstimateSaveReq projEstimateSaveReq) {
		List<ProjEstimateEntity> estimateEntities = ProjEstimateHandler
				.convertPOJOToEntity(projEstimateSaveReq.getProjEstimateTOs(), epsProjRepository);
		projEstimateRepository.save(estimateEntities);
	}

	public ProjectMaterialsResp getProjectMaterials(ProjectMaterialGetReq projectMaterialGetReq) {
		ProjectMaterialsResp projectMaterialsResp = new ProjectMaterialsResp();
		projectMaterialsResp.getProjectMaterialDtlTOs()
				.addAll(getMaterialsWithParents(getMaterialParents(projectMaterialGetReq)));
		return projectMaterialsResp;
	}

	private List<ProjectMaterialDtlTO> getMaterialParents(ProjectMaterialGetReq projectMaterialGetReq) {
		List<ProjectMaterialBudgetEntity> projectMaterialDtlEntites = projectMaterialRepository
				.findProjectMaterials(projectMaterialGetReq.getProjId());
		System.out.println("projectmaterial entity size:"+projectMaterialDtlEntites.size());
		Map<Long, LabelKeyTO> actualResp = actualHrsServiceImpl.getMaterialActualHrs(projectMaterialGetReq.getProjId());
		List<ProjectMaterialDtlTO> parents = new ArrayList<>();
		projectMaterialDtlEntites.forEach((projectMaterialDtlEntity) -> {
			System.out.println(projectMaterialDtlEntity);
			MaterialClassMstrEntity actualMaterial = projectMaterialDtlEntity.getMaterialClassMstrEntity();

			ProjectMaterialDtlTO actualMaterialTo = ProjectMaterialBudgetHandler.populateProjMaterials(actualMaterial,
					projectMaterialDtlEntity, false);
			actualMaterialTo.setActualQty(actualResp.containsKey(actualMaterial.getId())
					? new BigDecimal(actualResp.get(actualMaterial.getId()).getCode())
					: null);
			MaterialClassMstrEntity parentMaterial = actualMaterial.getMaterialClassMstrEntity();
			ProjectMaterialDtlTO parentMaterialTo = ProjectMaterialBudgetHandler.populateProjMaterials(parentMaterial,
					projectMaterialDtlEntity, false);
			parentMaterialTo.getProjectMaterialDtlTOs().add(actualMaterialTo);
			while (parentMaterial.getMaterialClassMstrEntity() != null) {
				parentMaterial = parentMaterial.getMaterialClassMstrEntity();
				ProjectMaterialDtlTO tempMaterialTo = ProjectMaterialBudgetHandler.populateProjMaterials(parentMaterial,
						projectMaterialDtlEntity, false);
				tempMaterialTo.getProjectMaterialDtlTOs().add(parentMaterialTo);
				parentMaterialTo = tempMaterialTo;
			}
			parents.add(parentMaterialTo);
		});
		return parents;
	}

	private List<ProjectMaterialDtlTO> getMaterialsWithParents(List<ProjectMaterialDtlTO> parents) {
		List<ProjectMaterialDtlTO> materials = new ArrayList<>();
		// Iterating over all the parents and fetching only projects assigned to user
		for (int i = 0; i < parents.size(); i++) {
			ProjectMaterialDtlTO materialClassTo = parents.get(i);

			// If the projects list is empty, we add the first project.
			if (materials.isEmpty()) {
				setProjectMaterialDtlTOStartFinishDatesOfBaseline(materialClassTo);
				materials.add(materialClassTo);
				continue;
			}
			boolean existingMaterial = false;

			// Iterating over the projects which are added already to the list to see,
			// if the new projects parent is available
			for (ProjectMaterialDtlTO material : materials) {
				// condition will be successful if the parent is already available
				if (material.getId() == materialClassTo.getId()) {
					existingMaterial = true;
					List<ProjectMaterialDtlTO> availableParents = new ArrayList<>();
					// getting the innermost child which is the project from the parent.
					ProjectMaterialDtlTO child = materialClassTo;
					while (!child.getProjectMaterialDtlTOs().isEmpty()) {
						child = child.getProjectMaterialDtlTOs().get(0);
						setProjectMaterialDtlTOStartFinishDatesOfBaseline(child);
						if (!child.getProjectMaterialDtlTOs().isEmpty())
							availableParents.add(child);
					}

					// If the project is a direct child of root project, we add it to the childs
					// list of root parent.
					if (child.getParentId().equals(material.getId())) {
						setProjectMaterialDtlTOStartFinishDatesOfBaseline(child);
						material.getProjectMaterialDtlTOs().add(child);
						break;
					}

					Map<Long, ProjectMaterialDtlTO> materialProjMap = new HashMap<>();
					materialProjMap.put(material.getId(), material);
					getParentsMap(material, materialProjMap);

					boolean found = false;
					do {
						ProjectMaterialDtlTO parent = materialProjMap.get(child.getParentId());
						if (parent != null) {
							ProjEstimateEntity projEstimate = projEstimateRepository
									.findMaterialEstimate(child.getProjId());
							if (projEstimate != null) {
								child.setEstimateType(projEstimate.getFormulaType());
							}
							parent.getProjectMaterialDtlTOs().add(child);
							found = true;
						} else {
							child = getParentById(availableParents, child.getParentId());
						}
					} while (!found);

				}
			}

			if (!existingMaterial) {
				setProjectMaterialDtlTOStartFinishDatesOfBaseline(materialClassTo);
				materials.add(materialClassTo);
			}
		}
		return materials;
	}

	private ProjectMaterialDtlTO setProjectMaterialDtlTOStartFinishDatesOfBaseline(
			ProjectMaterialDtlTO materialClassTo) {
		if (materialClassTo.getProjectMaterialDtlTOs().isEmpty()) {
			ProjEstimateEntity projEstimate = projEstimateRepository.findMaterialEstimate(materialClassTo.getProjId());
			if (projEstimate != null) {
				materialClassTo.setEstimateType(projEstimate.getFormulaType());
			}
			materialClassTo.setMinStartDateOfBaseline(resourceAssignmentDataRepository
					.findMinimumStartDateOfBaselineBy(materialClassTo.getProjId(), materialClassTo.getParentId()));
			if (materialClassTo.getMinStartDateOfBaseline() == null)
				materialClassTo.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(materialClassTo.getProjId(), materialClassTo.getId()));
			materialClassTo.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository
					.findMaximumFinishDateOfBaselineBy(materialClassTo.getProjId(), materialClassTo.getParentId()));
			if (materialClassTo.getMaxFinishDateOfBaseline() == null)
				materialClassTo.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(materialClassTo.getProjId(), materialClassTo.getId()));
		} else {
			setProjectMaterialDtlTOStartFinishDatesOfBaseline(materialClassTo.getProjectMaterialDtlTOs().get(0));
		}
		return materialClassTo;
	}

	private ProjectMaterialDtlTO getParentById(List<ProjectMaterialDtlTO> availableParents, Long parentId) {
		ProjectMaterialDtlTO parent = null;
		for (ProjectMaterialDtlTO parentFromList : availableParents) {
			if (parentFromList.getId() == parentId) {
				parent = parentFromList;
				return parent;
			}
		}
		return parent;
	}

	private void getParentsMap(ProjectMaterialDtlTO material, Map<Long, ProjectMaterialDtlTO> materialsMap) {
		for (ProjectMaterialDtlTO child : material.getProjectMaterialDtlTOs()) {
			if (!child.isItem()) {
				materialsMap.put(child.getId(), child);
				if (!child.getProjectMaterialDtlTOs().isEmpty()) {
					getParentsMap(child, materialsMap);
				}
			}
		}
	}

	public void saveProjectMaterials(ProjectMaterialSaveReq projectMaterialSaveReq) {
		List<ProjectMaterialBudgetEntity> budgetEntities = ProjectMaterialBudgetHandler.convertPOJOToEntity(
				projectMaterialSaveReq.getProjectMaterialDtlTOs(), epsProjRepository, materialClassRepository);
		projectMaterialRepository.save(budgetEntities);
	}

	@Override
	public DateWiseCostReportResp getActualCostDetails(CostReportReq costReportReq) {

		List<Long> costcodeIds = projCostItemRepository.findMultiProjCostIds(costReportReq.getProjIds());
		System.out.println("costcodeIds : size:" + costcodeIds.size());
		costReportReq.setCostcodeIds(costcodeIds);

		costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));

		ArrayList<String> categories = new ArrayList<String>();
		categories.add("direct");
		categories.add("in-direct");
		costReportReq.setCategories(categories);

		costReportReq.setFromDate("01-Jan-2000");
		costReportReq.setToDate(CommonUtil.convertDateToString(new Date()));

		DateWiseCostReportResp costReportResp = new DateWiseCostReportResp();
		List<CostReportTO> list = costReportResp.getCostReportResps();
		list.addAll(getEmpData(costReportReq));
		list.addAll(getPlantData(costReportReq));
		list.addAll(getMaterialActual(costReportReq));
		/*
		 * Double amt = Double.valueOf(0); for(int i = 0; i < list.size(); i++) {
		 * if(list.get(i).getCostAmount() != 0.00) {
		 * System.out.println(list.get(i).getCostAmount());
		 * amt+=list.get(i).getCostAmount(); } } System.out.println("AMT " + amt);
		 */
		list.stream().sorted(Comparator.comparing(CostReportTO::getDateObj).thenComparing(CostReportTO::getProjId)
				.thenComparing(CostReportTO::getCostItemId)).collect(Collectors.toList());

		return costReportResp;

	}

	private List<CostReportTO> getEmpData(CostReportReq costReportReq) {
		List<CostReportTO> list = new ArrayList<>();
		list.addAll(findEmpMobRates(costReportReq));
		list.addAll(findEmpDemobRates(costReportReq));
		Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> chargeRatesByProj = actualAmountService
				.getEmpChargeOutRates(new HashSet<Long>(costReportReq.getProjIds()));
		list.addAll(findEmpLeaveRates(costReportReq, chargeRatesByProj));
		list.addAll(findEmpWorkDairyDetails(costReportReq, chargeRatesByProj));
		list.addAll(findEmpTimeSheetDetails(costReportReq, chargeRatesByProj));
		list.addAll(findEmpTimeSheetExpenses(costReportReq, chargeRatesByProj));
		return list;
	}

	private List<CostReportTO> findEmpMobRates(CostReportReq costReportReq) {
		List<CostReportTO> list = new ArrayList<>();
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
		List<Object[]> objList = empProjRegisterRepository.findMobRates(costReportReq.getProjIds(),
				costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), costReportReq.getCategories(), fromDate,
				toDate);
		for (Object[] mobRate : objList) {
			EmpProjRigisterEntity empProj = (EmpProjRigisterEntity) mobRate[0];
			String category = (String) mobRate[1];
			list.addAll(processEmpMobToCostReportResp(empProj, category, true));
		}
		return list;
	}

	private List<CostReportTO> findEmpDemobRates(CostReportReq costReportReq) {
		List<CostReportTO> list = new ArrayList<>();
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
		List<Object[]> objList = empProjRegisterRepository.findDemobRates(costReportReq.getProjIds(),
				costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), costReportReq.getCategories(), fromDate,
				toDate);
		for (Object[] mobRate : objList) {
			EmpProjRigisterEntity empProj = (EmpProjRigisterEntity) mobRate[0];
			String category = (String) mobRate[1];
			list.addAll(processEmpMobToCostReportResp(empProj, category, false));
		}
		return list;
	}

	private List<CostReportTO> findEmpLeaveRates(CostReportReq costReportReq,
			Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> chargeRatesByProj) {
		List<CostReportTO> list = new ArrayList<>();
		// Get paid leave types
		Map<Long, Map<Date, Map<Long, List<String>>>> paidLeavesByProj = getLeaveTypes(costReportReq.getProjIds());
		if (paidLeavesByProj.keySet().isEmpty())
			return list;
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
		// Iterate paid leave for each project
		for (Entry<Long, Map<Date, Map<Long, List<String>>>> entry : paidLeavesByProj.entrySet()) {
			if (entry.getValue().isEmpty())
				continue;
			Long projId = entry.getKey();
			ProjMstrEntity projMstr = projRepository.findOne(projId);
			Set<Long> procureIds = new HashSet<>();
			entry.getValue().values().forEach(p -> procureIds.addAll(p.keySet()));
			// Fetch Emp attendance for this project
			List<Object[]> empAttdList = empAttendanceRepository.findAttendanceByProjs(projMstr, procureIds,
					costReportReq.getCategories(), costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate,
					toDate);
			if (empAttdList.isEmpty())
				continue;

			Map<Date, Map<Long, List<String>>> procurePaidLeaveDateMap = entry.getValue();
			// Effective dates of leave types
			Set<Date> effectiveDates = procurePaidLeaveDateMap.keySet().stream()
					.collect(Collectors.toCollection(TreeSet::new));
			Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates = chargeRatesByProj.get(projId);
			// Iterate for each emp and their attendance date
			empAttdList.forEach(emp -> {
				Date attdMonth = (Date) emp[0];
				EmpRegisterDtlEntity empReg = (EmpRegisterDtlEntity) emp[1];
				Long procureId = empReg.getProcureCatgDtlEntity().getId();
				Date attdDate = (Date) emp[2];
				// Find the effective date for this employee attendance month
				List<Date> mappedDate = effectiveDates.stream()
						.filter(d -> d.getTime() == attdMonth.getTime() || d.before(attdMonth))
						.collect(Collectors.toList());
				if (mappedDate.isEmpty())
					return;
				Date leaveEffectiveDate = mappedDate.get(mappedDate.size() - 1);
				String attdCode = (String) emp[3];
				// Check if this attendance code is paid leave and calculate rate
				if (procurePaidLeaveDateMap.get(leaveEffectiveDate).get(procureId).contains(attdCode)) {
					CostReportTO resp = new CostReportTO();
					setProjectInfo(projMstr, resp);
					setEmpInfo(empReg, resp);
					Map<Date, EmpChargeOutRateTO> ratesByDate = chargeRates.get(empReg.getId());
					if (ratesByDate != null) {
						Set<Date> effectiveEmpDates = ratesByDate.keySet().stream()
								.collect(Collectors.toCollection(TreeSet::new));
						List<Date> mappedAttdDate = effectiveEmpDates.stream()
								.filter(d -> d.getTime() == attdDate.getTime() || d.before(attdDate))
								.collect(Collectors.toList());
						if (mappedAttdDate.isEmpty())
							return;
						Date effectiveAttdDate = mappedAttdDate.get(mappedAttdDate.size() - 1);
						EmpChargeOutRateTO rate = ratesByDate.get(effectiveAttdDate);
						if (rate != null && rate.getLeaveCostItemId() != null) {
							ProjCostItemEntity costCode = projCostItemRepository.findOne(rate.getLeaveCostItemId());
							setCostCodeInfo(costCode, resp);
							resp.setQuantity(Double.valueOf(1));
							resp.setCostAmount(rate.getLeaveRate().doubleValue());
							resp.setRatePerUnit(rate.getLeaveRate().doubleValue());
						}
					}
					resp.setDate(CommonUtil.convertDateToString(attdDate));
					resp.setCategory((String) emp[4]);
					resp.setType("Paid Leave");
					resp.setCurrencyCode((String) emp[5] != null ? (String) emp[5] : "");
					list.add(resp);
				}
			});
		}
		return list;
	}

	private void setProjectInfo(ProjMstrEntity projMstr, CostReportTO resp) {
		if (null != projMstr) {
			resp.setProjId(projMstr.getProjectId());
			resp.setProjName(projMstr.getProjName());
			ProjMstrEntity parentMstr = projMstr.getParentProjectMstrEntity();
			if (parentMstr != null) {
				resp.setEpsId(parentMstr.getProjectId());
				resp.setEpsName(parentMstr.getProjName());
			}
		}
	}

	private void setEmpInfo(EmpRegisterDtlEntity empReg, CostReportTO resp) {
		resp.setEmpId(empReg.getId());
		resp.setResourceId(empReg.getCode());
		resp.setEmpFirstName(empReg.getFirstName());
		resp.setEmpLastName(empReg.getLastName());
		resp.setCmpId(empReg.getCompanyMstrEntity().getId());
		resp.setCmpName(empReg.getCompanyMstrEntity().getName());
	}

	private void setCostCodeInfo(ProjCostItemEntity costCode, CostReportTO resp) {
		if (costCode != null) {
			resp.setCostItemId(costCode.getId());
			resp.setCostItemCode(costCode.getCode());
			resp.setCostItemName(costCode.getName());
			ProjCostItemEntity parentCost = costCode.getProjCostItemEntity();
			if (parentCost != null) {
				resp.setCostSubGroupId(parentCost.getId());
				resp.setCostSubGroupCode(parentCost.getCode());
				resp.setCostSubGroupName(parentCost.getName());
			}
		}
	}

	private void setPlantInfo(PlantRegisterDtlEntity plantReg, CostReportTO resp) {
		resp.setResourceId(plantReg.getAssertId());
		resp.setPlantId(plantReg.getId());
		resp.setPlantDesc(plantReg.getDesc());
		resp.setPlantMake(plantReg.getManfacture());
		resp.setPlantModel(plantReg.getModel());
		resp.setPlantRegNumber(plantReg.getRegNumber());
		resp.setCmpId(plantReg.getCmpId().getId());
		resp.setCmpName(plantReg.getCmpId().getName());
	}

	private Map<Long, Map<Date, Map<Long, List<String>>>> getLeaveTypes(List<Long> projIds) {
		Map<Long, Map<Date, Map<Long, List<String>>>> paidLeavesByProj = new HashMap<>();
		for (Long projId : projIds) {
			ProjMstrEntity projMstrEntity = projRepository.findOne(projId);
			System.out.println("getLeaveTypes : findCountry : projId: " + projId);
			String countryCode = projGeneralRepository.findCountry(projMstrEntity);
			List<ProjLeaveTypeEntity> procurePaidLeaveList = leaveTypeRepository.findPaidLeaveCodes(countryCode);
			Map<Date, Map<Long, List<String>>> procurePaidLeaveDateMap = new HashMap<>();
			for (ProjLeaveTypeEntity entity : procurePaidLeaveList) {
				Date effectiveFrom = entity.getEffectiveFrom();
				procurePaidLeaveDateMap.computeIfAbsent(effectiveFrom, v -> new HashMap<>());
				procurePaidLeaveDateMap.computeIfPresent(effectiveFrom, (k, v) -> {
					entity.getProjLeaveCategoryTypes().forEach(t -> {
						Long procureId = t.getProcureCatgDtlEntity().getId();
						String leaveType = t.getProjLeaveTypeEntity().getCode();
						if ("paid".equalsIgnoreCase(t.getPayType())) {
							v.computeIfAbsent(procureId, v1 -> new ArrayList<>());
							v.computeIfPresent(procureId, (k1, v1) -> {
								v1.add(leaveType);
								return v1;
							});
						}
					});
					return v;
				});
			}
			paidLeavesByProj.put(projId, procurePaidLeaveDateMap);
		}
		return paidLeavesByProj;
	}

	private List<CostReportTO> findEmpWorkDairyDetails(CostReportReq costReportReq,
			Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> chargeRatesByProj) {
		List<CostReportTO> list = new ArrayList<>();
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
		List<Object[]> workDairyHrs = empCostWorkDairyRepository.getManpowerWorkDiaryActualHrs(
				costReportReq.getProjIds(), costReportReq.getCategories(), costReportReq.getCmpIds(),
				costReportReq.getCostcodeIds(), fromDate, toDate);
		for (Object[] workDairyHr : workDairyHrs) {
			Date workDairyDate = (Date) workDairyHr[5];
			EmpRegisterDtlEntity empReg = (EmpRegisterDtlEntity) workDairyHr[0];
			ProjMstrEntity projMstr = (ProjMstrEntity) workDairyHr[6];

			ProjCostItemEntity costCode = (ProjCostItemEntity) workDairyHr[1];
			Float wageFactor = (Float) workDairyHr[2];
			Double usedHrs = (Double) workDairyHr[3];
			Double idleHrs = (Double) workDairyHr[4];

			Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates = chargeRatesByProj.get(projMstr.getProjectId());
			Map<Date, EmpChargeOutRateTO> empRateWithDates = chargeRates.get(empReg.getId());
			if (empRateWithDates == null || empRateWithDates.values().isEmpty())
				continue;
			Set<Date> effectiveDates = empRateWithDates.keySet().stream()
					.collect(Collectors.toCollection(TreeSet::new));
			List<Date> mappedDate = effectiveDates.stream()
					.filter(d -> d.getTime() == workDairyDate.getTime() || d.before(workDairyDate))
					.collect(Collectors.toList());
			if (mappedDate.isEmpty())
				continue;
			Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
			EmpChargeOutRateTO empRate = empRateWithDates.get(effectiveDate);
			CostReportTO resp = new CostReportTO();
			resp.setDate(CommonUtil.convertDateToString(workDairyDate));
			if (projMstr != null)
				setProjectInfo(projMstr, resp);
			setEmpInfo(empReg, resp);
			resp.setCategory((String) workDairyHr[7]);
			if (CommonUtil.isNonBlankDouble(usedHrs)) {
				CostReportTO empInfoReportClone = new CostReportTO();
				BeanUtils.copyProperties(resp, empInfoReportClone);
				empInfoReportClone.setType("Used WorkDairy");
				setCostCodeInfo(costCode, empInfoReportClone);
				empInfoReportClone.setQuantity(usedHrs);
				if (empRate != null && empRate.getNormalRate() != null) {
					empInfoReportClone.setCostAmount(usedHrs * wageFactor * empRate.getNormalRate().doubleValue());
					empInfoReportClone.setRatePerUnit(empRate.getNormalRate().doubleValue());
					empInfoReportClone.setCurrencyCode(empRate.getCurrency());
				}
				list.add(empInfoReportClone);
			}

			if (CommonUtil.isNonBlankDouble(idleHrs)) {
				CostReportTO empInfoReportClone = new CostReportTO();
				BeanUtils.copyProperties(resp, empInfoReportClone);
				empInfoReportClone.setType("Idle WorkDairy");
				setCostCodeInfo(costCode, empInfoReportClone);
				empInfoReportClone.setQuantity(idleHrs);
				if (empRate != null && empRate.getIdleRate() != null) {
					empInfoReportClone.setCostAmount(idleHrs * wageFactor * empRate.getIdleRate().doubleValue());
					empInfoReportClone.setRatePerUnit(empRate.getIdleRate().doubleValue());
					empInfoReportClone.setCurrencyCode(empRate.getCurrency());
				}
				list.add(empInfoReportClone);
			}
		}
		return list;
	}

	private List<CostReportTO> findEmpTimeSheetDetails(CostReportReq costReportReq,
			Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> chargeRatesByProj) {
		List<CostReportTO> list = new ArrayList<>();
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
		List<Object[]> timeSheetHrs = timeSheetEmpDtlRepository.getTimesheetEmpActualHrs(costReportReq.getProjIds(),
				costReportReq.getCategories(), costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate,
				toDate);
		for (Object[] timeSheetHr : timeSheetHrs) {
			ProjMstrEntity projMstr = (ProjMstrEntity) timeSheetHr[11];
			EmpRegisterDtlEntity empReg = (EmpRegisterDtlEntity) timeSheetHr[0];
			Set<Date> effectiveDates = new HashSet<>();
			Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates = chargeRatesByProj.get(projMstr.getProjectId());
			Map<Date, EmpChargeOutRateTO> empRateWithDates = new HashMap<>();
			if (chargeRates != null) {
				empRateWithDates = chargeRates.get(empReg.getId());
				if (empRateWithDates != null && !empRateWithDates.values().isEmpty())
					effectiveDates = empRateWithDates.keySet().stream().collect(Collectors.toCollection(TreeSet::new));
			}
			ProjCostItemEntity costCode = (ProjCostItemEntity) timeSheetHr[1];
			Float wageFactor = (Float) timeSheetHr[2];

			Map<Date, Double> hrsPerDays = actualAmountService.calculateTimesheetDates(timeSheetHr);
			CostReportTO resp = new CostReportTO();
			if (projMstr != null)
				setProjectInfo(projMstr, resp);
			setEmpInfo(empReg, resp);
			setCostCodeInfo(costCode, resp);
			resp.setType("Timesheet Hrs");
			resp.setCategory((String) timeSheetHr[12]);
			for (Entry<Date, Double> hrsPerDay : hrsPerDays.entrySet()) {
				Date timesheetDate = hrsPerDay.getKey();
				if (fromDate.compareTo(timesheetDate) * timesheetDate.compareTo(toDate) < 0)
					continue;
				Double usedHrs = hrsPerDay.getValue();
				CostReportTO empInfoReportClone = new CostReportTO();
				BeanUtils.copyProperties(resp, empInfoReportClone);
				empInfoReportClone.setQuantity(usedHrs);
				empInfoReportClone.setDate(CommonUtil.convertDateToString(timesheetDate));
				list.add(empInfoReportClone);
				List<Date> mappedDate = effectiveDates.stream()
						.filter(d -> d.getTime() == timesheetDate.getTime() || d.before(timesheetDate))
						.collect(Collectors.toList());
				if (!mappedDate.isEmpty()) {
					Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
					EmpChargeOutRateTO empRate = empRateWithDates.get(effectiveDate);
					if (empRate != null && empRate.getNormalRate() != null) {
						empInfoReportClone.setRatePerUnit(empRate.getNormalRate().doubleValue());
						empInfoReportClone.setCostAmount(usedHrs * wageFactor * empRate.getNormalRate().doubleValue());
						empInfoReportClone.setCurrencyCode(empRate.getCurrency());
					}
				}

			}
		}
		return list;
	}

	private List<CostReportTO> findEmpTimeSheetExpenses(CostReportReq costReportReq,
			Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> chargeRatesByProj) {
		List<CostReportTO> list = new ArrayList<>();
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
		List<Object[]> expenses = timeSheetEmpExpenseRepository.findExpenses(costReportReq.getProjIds(),
				costReportReq.getCategories(), costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate,
				toDate);
		for (Object[] expense : expenses) {
			ProjMstrEntity projMstr = (ProjMstrEntity) expense[0];
			EmpRegisterDtlEntity empReg = (EmpRegisterDtlEntity) expense[1];
			ProjCostItemEntity costCode = (ProjCostItemEntity) expense[2];

			CostReportTO resp = new CostReportTO();
			setProjectInfo(projMstr, resp);
			setEmpInfo(empReg, resp);
			setCostCodeInfo(costCode, resp);
			resp.setType("Timesheet Expenses");
			resp.setCategory((String) expense[4]);
			resp.setCostAmount(((BigDecimal) expense[3]).doubleValue());
			Date date = (Date) expense[5];
			resp.setDate(CommonUtil.convertDateToString(date));
			list.add(resp);
			Map<Long, Map<Date, EmpChargeOutRateTO>> projRates = chargeRatesByProj.get(projMstr.getProjectId());
			if (projRates.isEmpty())
				continue;
			Map<Date, EmpChargeOutRateTO> empRates = projRates.get(empReg.getId());
			if (empRates.isEmpty())
				continue;
			Set<Date> effectiveDates = empRates.keySet().stream().collect(Collectors.toCollection(TreeSet::new));
			if (effectiveDates.isEmpty())
				continue;
			List<Date> mappedDate = effectiveDates.stream().filter(d -> d.getTime() == date.getTime() || d.before(date))
					.collect(Collectors.toList());
			if (mappedDate.isEmpty())
				continue;
			Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
			EmpChargeOutRateTO empRate = empRates.get(effectiveDate);
			if (empRate != null) {
				resp.setCurrencyCode(empRate.getCurrency());
			}
		}
		return list;
	}

	private List<CostReportTO> getPlantData(CostReportReq costReportReq) {
		List<CostReportTO> list = new ArrayList<>();
		list.addAll(findPlantMobRates(costReportReq));
		list.addAll(findPlantDeMobRates(costReportReq));
		list.addAll(findPlantWorkDairyDetails(costReportReq));
		return list;
	}

	private List<CostReportTO> findPlantMobRates(CostReportReq costReportReq) {
		List<CostReportTO> list = new ArrayList<>();
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
		List<Object[]> mobRates = plantChargeOutRateRepository.findMobRates(costReportReq.getProjIds(),
				costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate, toDate);
		for (Object[] mobRate : mobRates) {
			ProjMstrEntity projMstr = (ProjMstrEntity) mobRate[0];
			ProjCostItemEntity costCode = (ProjCostItemEntity) mobRate[1];
			PlantRegisterDtlEntity plantReg = (PlantRegisterDtlEntity) mobRate[2];
			Date mobDate = (Date) mobRate[3];
			CostReportTO resp = new CostReportTO();
			resp.setDate(CommonUtil.convertDateToString(mobDate));
			if (projMstr != null)
				setProjectInfo(projMstr, resp);
			setPlantInfo(plantReg, resp);
			setCostCodeInfo(costCode, resp);
			resp.setType("Mobilization");
			resp.setPlantRateType((String) mobRate[5]);
			resp.setCurrencyCode((String) mobRate[6]);
			BigDecimal rate = (BigDecimal) mobRate[4];
			if (rate != null)
				resp.setCostAmount(rate.doubleValue());
			list.add(resp);
		}
		return list;
	}

	private List<CostReportTO> findPlantDeMobRates(CostReportReq costReportReq) {
		List<CostReportTO> list = new ArrayList<>();
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
		List<Object[]> mobRates = plantChargeOutRateRepository.findDeMobRates(costReportReq.getProjIds(),
				costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate, toDate);
		for (Object[] mobRate : mobRates) {
			ProjMstrEntity projMstr = (ProjMstrEntity) mobRate[0];
			ProjCostItemEntity costCode = (ProjCostItemEntity) mobRate[1];
			PlantRegisterDtlEntity plantReg = (PlantRegisterDtlEntity) mobRate[2];
			Date mobDate = (Date) mobRate[3];
			CostReportTO resp = new CostReportTO();
			resp.setDate(CommonUtil.convertDateToString(mobDate));
			if (projMstr != null)
				setProjectInfo(projMstr, resp);
			setPlantInfo(plantReg, resp);
			setCostCodeInfo(costCode, resp);
			resp.setType("DeMobilization");
			resp.setPlantRateType((String) mobRate[5]);
			resp.setCurrencyCode((String) mobRate[6]);
			BigDecimal rate = (BigDecimal) mobRate[4];
			if (rate != null)
				resp.setCostAmount(rate.doubleValue());
			list.add(resp);
		}
		return list;
	}

	private List<CostReportTO> findPlantWorkDairyDetails(CostReportReq costReportReq) {
		List<CostReportTO> list = new ArrayList<>();
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
		List<Object[]> workDairyHrs = plantCostWorkDairyRepository.getPlantWorkDiaryActualHrs(
				costReportReq.getProjIds(), costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate,
				toDate);

		if (workDairyHrs.isEmpty())
			return list;
		Map<Long, Map<Long, Map<Date, PlantChargeOutRateTO>>> chargeRatesByProj = actualAmountService
				.getPlantChargeOutRates(new HashSet<Long>(costReportReq.getProjIds()));
		// System.out.println("MAP chargeRatesByProj " + chargeRatesByProj);
		for (Object[] workDairyHr : workDairyHrs) {
			ProjMstrEntity projMstr = (ProjMstrEntity) workDairyHr[0];
			Date workDairyDate = (Date) workDairyHr[1];
			WorkDairyPlantDtlEntity plantDtl = (WorkDairyPlantDtlEntity) workDairyHr[2];
			PlantRegisterDtlEntity plantReg = plantDtl.getPlantRegId();
			CostReportTO resp = new CostReportTO();
			resp.setDate(CommonUtil.convertDateToString(workDairyDate));
			setProjectInfo(projMstr, resp);
			setPlantInfo(plantReg, resp);
			setCostCodeInfo((ProjCostItemEntity) workDairyHr[3], resp);
			resp.setCurrencyCode((String) workDairyHr[6]);
			Map<Long, Map<Date, PlantChargeOutRateTO>> chargeRates = chargeRatesByProj.get(projMstr.getProjectId());
			// System.out.println("MAP chargeRates " + chargeRates);
			Map<Date, PlantChargeOutRateTO> plantRateWithDates = chargeRates.get(plantReg.getId());
			// System.out.println("MAP plantRateWithDates " + plantRateWithDates);
			PlantChargeOutRateTO plantRate = null;
			Double usedHrs = (Double) workDairyHr[4];
			Double idleHrs = (Double) workDairyHr[5];
			if (plantRateWithDates != null && !plantRateWithDates.values().isEmpty()) {
				// System.out.println("plantRateWithDates IS NOT NULL ");
				Set<Date> effectiveDates = plantRateWithDates.keySet().stream()
						.collect(Collectors.toCollection(TreeSet::new));
				// System.out.println("effectiveDates " + effectiveDates);
				List<Date> mappedDate = effectiveDates.stream()
						.filter(d -> d.getTime() == workDairyDate.getTime() || d.before(workDairyDate))
						.collect(Collectors.toList());
				// System.out.println("mappedDate " + mappedDate);
				if (mappedDate.isEmpty())
					continue;
				Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
				// System.out.println("effectiveDate " + effectiveDate);
				plantRate = plantRateWithDates.get(effectiveDate);
				// System.out.println("plantRate " + plantRate);
			}
			if (CommonUtil.isNonBlankDouble(usedHrs)) {
				// System.out.println("usedHrs isNonBlankDouble " + usedHrs);
				CostReportTO plantInfoReportClone = new CostReportTO();
				BeanUtils.copyProperties(resp, plantInfoReportClone);
				plantInfoReportClone.setType("Used WorkDairy");
				plantInfoReportClone.setQuantity(usedHrs);

				if (plantRate != null && plantRate.getNormalRate() != null) {
					// System.out.println("plantRate.getNormalRate() " + plantRate.getNormalRate());
					double pRate;
					// System.out.println("plantDtl.getShiftName() " + plantDtl.getShiftName());
					if (plantDtl.getShiftName() != null && plantDtl.getShiftName().toLowerCase().contains("single")) {
						pRate = plantRate.getNormalRate().doubleValue();
						// System.out.println("IF pRate " + pRate);
					} else {
						pRate = plantRate.getDoubleRate().doubleValue();
						// System.out.println("ELSE pRate " + pRate);
					}
					Double amount = Double.valueOf(0);
					if (CommonUtil.isNonBlankDouble(pRate)) {
						amount = ((Double) usedHrs) * pRate;
						// System.out.println("usedHrs amount " + amount);
					}
					plantInfoReportClone.setPlantRateType(plantRate.getPlantRateType());
					plantInfoReportClone.setCostAmount(amount);
					plantInfoReportClone.setRatePerUnit(pRate);
				}
				list.add(plantInfoReportClone);
			}
			if (CommonUtil.isNonBlankDouble(idleHrs)) {
				// System.out.println("idleHrs isNonBlankDouble " + idleHrs);
				CostReportTO plantInfoReportClone = new CostReportTO();
				BeanUtils.copyProperties(resp, plantInfoReportClone);
				plantInfoReportClone.setType("Idle WorkDairy");
				plantInfoReportClone.setQuantity(idleHrs);

				if (plantRate != null && plantRate.getIdleRate() != null) {
					// System.out.println("plantRate.getIdleRate() " + plantRate.getIdleRate());
					Double amount = Double.valueOf(0);
					if (CommonUtil.objectNotNull(plantRate.getIdleRate())
							&& CommonUtil.isNonBlankDouble(plantRate.getIdleRate().doubleValue())) {
						amount = ((Double) idleHrs) * plantRate.getIdleRate().doubleValue();
						// System.out.println("idleHrs amount " + amount);
					}
					plantInfoReportClone.setPlantRateType(plantRate.getPlantRateType());
					plantInfoReportClone.setCostAmount(amount);
					double pRate = 0;
					if (plantRate.getIdleRate() != null) {
						pRate = plantRate.getIdleRate().doubleValue();
					}
					plantInfoReportClone.setRatePerUnit(pRate);
				}
				list.add(plantInfoReportClone);
			}
		}
		return list;
	}

	private List<CostReportTO> getMaterialActual(CostReportReq costReportReq) {
		List<CostReportTO> list = new ArrayList<>();
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
		List<Object[]> workDairyHrs = materialStatusWorkDairyRepository.getWorkDairyActualAmount(
				costReportReq.getProjIds(), costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate,
				toDate);
		for (Object[] workDairyHr : workDairyHrs) {
			CostReportTO resp = new CostReportTO();
			ProjMstrEntity projMstr = (ProjMstrEntity) workDairyHr[0];
			setProjectInfo(projMstr, resp);
			setCostCodeInfo((ProjCostItemEntity) workDairyHr[2], resp);
			resp.setDate(CommonUtil.convertDateToString((Date) workDairyHr[6]));
			resp.setCurrencyCode((String) workDairyHr[5]);

			MaterialClassMstrEntity materialClass = (MaterialClassMstrEntity) workDairyHr[1];
			resp.setResourceId(materialClass.getCode());
			resp.setMatId(materialClass.getId());
			resp.setType("WorkDairy");
			MaterialClassMstrEntity parent = materialClass.getMaterialClassMstrEntity();
			if (null != parent) {
				resp.setMaterialSubGroupId(parent.getId());
				resp.setMaterialSubGroupCode(parent.getCode());
				resp.setMaterialSubGroupName(parent.getName());
			}
			double rate = ((BigDecimal) workDairyHr[3]).doubleValue();
			Double quantity = (Double) workDairyHr[4];
			resp.setRatePerUnit(rate);
			resp.setQuantity(quantity);
			resp.setCostAmount(quantity * rate);
			list.add(resp);
		}
		return list;
	}

	private List<CostReportTO> processEmpMobToCostReportResp(EmpProjRigisterEntity mobRate, String category,
			boolean mob) {
		List<CostReportTO> list = new ArrayList<>();
		EmpRegisterDtlEntity empReg = mobRate.getEmpRegisterDtlEntity();
		if (empReg == null)
			return list;
		CostReportTO resp = new CostReportTO();
		resp.setDate(CommonUtil
				.convertDateToString(mob ? mobRate.getMobilaizationDate() : mobRate.getDeMobilaizationDate()));
		ProjMstrEntity projMstr = mobRate.getProjMstrEntity();
		if (projMstr != null)
			setProjectInfo(projMstr, resp);
		setEmpInfo(empReg, resp);
		for (EmpChargeOutRateEntity charge : mobRate.getEmpchargeOutRateEntities()) {
			CostReportTO empInfoReportClone = new CostReportTO();
			BeanUtils.copyProperties(resp, empInfoReportClone);
			if (mob) {
				if (charge.getMobCostItemEntity() == null || charge.getMobRate() == null)
					continue;
				empInfoReportClone.setType("Mobilization");
				setCostCodeInfo(charge.getMobCostItemEntity(), empInfoReportClone);
				empInfoReportClone.setCostAmount(charge.getMobRate().doubleValue());
			} else {
				if (charge.getDeMobCostItemEntity() == null || charge.getDeMobRate() == null)
					continue;
				empInfoReportClone.setType("DeMobilization");
				setCostCodeInfo(charge.getDeMobCostItemEntity(), empInfoReportClone);
				empInfoReportClone.setCostAmount(charge.getDeMobRate().doubleValue());
			}
			empInfoReportClone.setCategory(category);
			empInfoReportClone.setCurrencyCode(
					charge.getProjGeneralMstrEntity() != null ? charge.getProjGeneralMstrEntity().getCurrency() : "");
			list.add(empInfoReportClone);
		}
		return list;
	}

	@Override
	public DateWiseCostReportResp getPlanActualEarned(CostReportReq costReportReq) {
		log.info("getPlanActualEarned Method");
		costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));
		DateWiseCostReportResp resp = getActualCostDetails(costReportReq);
		System.out.println("getActualCostDetails resp " + resp);
		Map<String, Double> earnedValues = new HashMap<>();
		log.info("getCostReportResps is Empty - TRUE OR FALSE = " + !resp.getCostReportResps().isEmpty());
		if (!resp.getCostReportResps().isEmpty()) {
			Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
			Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
			List<Object[]> dailyEarnedValues = projSowTotalActualRepository
					.getDailyEarnedValuePerCostCode(costReportReq.getProjIds(), fromDate, toDate);
			for (Object[] dailyEarned : dailyEarnedValues) {
				Long costId = (Long) dailyEarned[0];
				Date date = (Date) dailyEarned[1];
				double value = (double) dailyEarned[2];
				BigDecimal rate = (BigDecimal) dailyEarned[3];
				if (rate != null)
					earnedValues.merge(CommonUtil.convertDateToString(date) + "-" + costId, value * rate.doubleValue(),
							Double::sum);
			}
		}
		Map<String, CostReportTO> map = new HashMap<>();
		for (CostReportTO costReportResp : resp.getCostReportResps()) {
			String key = costReportResp.getDate() + "-" + costReportResp.getCostItemId();
			costReportResp.setEarnedValue(CommonUtil.ifNullGetDefaultValue(earnedValues.get(key)));
			if (map.containsKey(key)) {
				CostReportTO val = map.get(key);
				val.setCostAmount(val.getCostAmount() + costReportResp.getCostAmount());
				val.setEarnedValue(costReportResp.getEarnedValue());
			} else {
				map.put(key, costReportResp);
			}
		}
		DateWiseCostReportResp newResp = new DateWiseCostReportResp();

		newResp.getCostReportResps()
				.addAll(map
						.values().stream().sorted(Comparator.comparing(CostReportTO::getDateObj)
								.thenComparing(CostReportTO::getProjId).thenComparing(CostReportTO::getCostItemId))
						.collect(Collectors.toList()));
		return newResp;
	}

	public ProjCostStaementsResp getProjCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
		
		System.out.println("ProjectSettingsServiceImpk:getProjCostStatements 55");
		
		if(projCostStatementsGetReq.getProjId() != null) {
			projCostStatementsGetReq.getProjIds().add(projCostStatementsGetReq.getProjId());
		}
		log.info("Project Idssss " + projCostStatementsGetReq.getProjIds());
		List<Long> projIds = projCostStatementsGetReq.getProjIds();
		ProjCostStaementsResp projCostStaementsResp = new ProjCostStaementsResp();
		for (Long projId : projIds) {
			log.info("projIddddddddd " + projId);

			Map<Long, ProjCostStmtDtlEntity> projCostStmtMap = new HashMap<>();
			/*List<ProjCostItemEntityCopy> projCostItemEntities = projCostItemRepository
					.findCostDetails(projId, StatusCodes.ACTIVE.getValue());*/
			List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository.findCostDetailsByProjId(projId);
			/* List<ProjCostStmtDtlEntity> pojCostStmtDtlEntities = projCostStatementsRepository
					.findProjCostStatements(projId, StatusCodes.ACTIVE.getValue()); */
			List<ProjCostStmtDtlEntity> pojCostStmtDtlEntities = projCostStatementsRepository.findProjCostStatements(projId);
			for (ProjCostStmtDtlEntity projCostStmtDtlEntity : pojCostStmtDtlEntities) {
				System.out.println(projCostStmtDtlEntity);
				log.info("---------------");
				projCostStmtMap.put(projCostStmtDtlEntity.getProjCostItemEntity().getId(), projCostStmtDtlEntity);
				log.info("---------------");
			}
			Map<Long, CostActualHoursTO> actualHrs = actualAmountService.getCostStmt(projId);
			log.info("actualHrs " + actualHrs);
			ProjEstimateEntity projEstimate = projEstimateRepository
					.findEstimateCostEstimate(projId);
			Map<Long, BigDecimal> earnedValues = getEarnedValueForCostId(projId);
			log.info("earnedValues " + earnedValues);
			log.info("projCostItemEntities " + projCostItemEntities.size());
			for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
				System.out.println(projCostItemEntity);
				ProjCostStmtDtlTO costStmts = ProjCostStmtDtlHandler.populateProjCostStmts(projCostItemEntity,
						projCostStmtMap);
				log.info("costStmts size " + costStmts.getProjCostStmtDtlTOs().size());
				for (ProjCostStmtDtlTO projCostStmtDtlTO : costStmts.getProjCostStmtDtlTOs()) {
					log.info("earnedValues.get(projCostStmtDtlTO.getCostId() "
							+ earnedValues.get(projCostStmtDtlTO.getCostId()));
					if (earnedValues.get(projCostStmtDtlTO.getCostId()) != null) {
						if (projCostItemEntity.getCostMstrEntity() != null) {
							projCostStmtDtlTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository
									.findMinimumStartDateOfBaselineBy(projCostItemEntity.getProjId().getProjectId(),
											projCostItemEntity.getCostMstrEntity().getId()));
							projCostStmtDtlTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository
									.findMinimumStartDateOfBaselineBy(projCostItemEntity.getProjId().getProjectId(),
											projCostItemEntity.getCostMstrEntity().getId()));
						}
						projCostStmtDtlTO.setEarnedValue(earnedValues.get(projCostStmtDtlTO.getCostId()));
					}
					if (projCostStmtDtlTO.getMinStartDateOfBaseline() == null)
						projCostStmtDtlTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository
								.findMinimumStartDateOfBaselineBy(projCostItemEntity.getProjId().getProjectId(), projCostStmtDtlTO.getCostId()));
					if (projCostStmtDtlTO.getMaxFinishDateOfBaseline() == null)
						projCostStmtDtlTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository
								.findMaximumFinishDateOfBaselineBy(projCostItemEntity.getProjId().getProjectId(), projCostStmtDtlTO.getCostId()));
					log.info("calculateValues 1");
					calculateValues(projCostStmtDtlTO, actualHrs, projEstimate);
					for (ProjCostStmtDtlTO childCostStmts : projCostStmtDtlTO.getProjCostStmtDtlTOs()) {
						if (earnedValues.get(childCostStmts.getCostId()) != null) {
							childCostStmts.setEarnedValue(earnedValues.get(childCostStmts.getCostId()));
						}
						if (projCostItemEntity.getCostMstrEntity() != null) {
							childCostStmts.setMinStartDateOfBaseline(resourceAssignmentDataRepository
									.findMinimumStartDateOfBaselineBy(projCostItemEntity.getProjId().getProjectId(),
											projCostItemEntity.getCostMstrEntity().getId()));
							childCostStmts.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository
									.findMinimumStartDateOfBaselineBy(projCostItemEntity.getProjId().getProjectId(),
											projCostItemEntity.getCostMstrEntity().getId()));
						} 
						if (projCostStmtDtlTO.getMinStartDateOfBaseline() == null)
							projCostStmtDtlTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository
									.findMinimumStartDateOfBaselineBy(projCostItemEntity.getProjId().getProjectId(), projCostStmtDtlTO.getCostId()));
						if (projCostStmtDtlTO.getMaxFinishDateOfBaseline() == null)
							projCostStmtDtlTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository
									.findMaximumFinishDateOfBaselineBy(projCostItemEntity.getProjId().getProjectId(), projCostStmtDtlTO.getCostId()));
						log.info("calculateValues 2");
						calculateValues(childCostStmts, actualHrs, projEstimate);
					}
				}
				projCostStaementsResp.getProjCostStmtDtlTOs().add(costStmts);
			}
		}
		return projCostStaementsResp;
	}

	@Override
	public ProjCostStaementsResp getMultiProjCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
		System.out.println("ProjSettingsServiceImpl > getMultiProjCostStatements ");
		List<Long> projIds = projCostStatementsGetReq.getProjIds();
		ProjCostStaementsResp costStmtResponse = new ProjCostStaementsResp();
		for (Long projId : projIds) {
			ProjCostStatementsGetReq ProjCostStatements = new ProjCostStatementsGetReq();
			ProjCostStatements.setProjId(projId);
			costStmtResponse.getProjCostStmtDtlTOs()
					.addAll(getProjCostStatements(ProjCostStatements).getProjCostStmtDtlTOs());
		}
		return costStmtResponse;
	}

	private void calculateValues(ProjCostStmtDtlTO childCostStmt, Map<Long, CostActualHoursTO> actualHrs,
			ProjEstimateEntity projEstimate) {
		log.info("childCostStmt " + childCostStmt.toString());
		log.info("childCostStmt.getCostId() " + childCostStmt.getCostId());
		if (childCostStmt != null && childCostStmt.getCostId() != null) {
			CostActualHoursTO actualData = actualHrs.get(childCostStmt.getCostId());
			ProjCostBudgetTO budgetObj = childCostStmt.getActualCostBudget();
			setActualBudgetValues(budgetObj, actualData, actualHrs.get(childCostStmt.getId()));
			childCostStmt.setActualCostBudget(budgetObj);
			if (projEstimate != null) {
				childCostStmt.setEstimateType(projEstimate.getFormulaType());
				log.info("childCostStmt.getEstimateType() " + childCostStmt.getEstimateType());
				calculatePerformance(childCostStmt);
			}
		}
	}

	private void setActualBudgetValues(ProjCostBudgetTO budgetObj, CostActualHoursTO actualData,
			CostActualHoursTO actualIdData) {
		log.info("setActualBudgetValues method ");
		budgetObj.setBudgetType(3L);

		if (actualData != null) {
			budgetObj.setLabourCost(
					actualData.getLabourCost() == null ? null : BigDecimal.valueOf(actualData.getLabourCost()));
			budgetObj.setMaterialCost(
					actualData.getMaterialCost() == null ? null : BigDecimal.valueOf(actualData.getMaterialCost()));
			budgetObj.setPlantCost(
					actualData.getPlantCost() == null ? null : BigDecimal.valueOf(actualData.getPlantCost()));
			budgetObj.setOtherCost(
					actualData.getOthersCost() == null ? null : BigDecimal.valueOf(actualData.getOthersCost()));
		}
		// log.info("actualData.getLabourCost() " + actualData.getLabourCost());
		// log.info("actualData.getMaterialCost() " + actualData.getMaterialCost());
		// log.info("actualData.getPlantCost() " + actualData.getPlantCost());
		// log.info("actualData.getOthersCost() " + actualData.getOthersCost());
		if (actualIdData != null) {
			if (budgetObj.getOtherCost() != null) {
				budgetObj.setOtherCost(budgetObj.getOtherCost().add(BigDecimal.valueOf(actualIdData.getOthersCost())));
			} else {
				budgetObj.setOtherCost(BigDecimal.valueOf(actualIdData.getOthersCost()));
			}
		}
		// log.info("actualData.getOthersCost() " + actualData.getOthersCost());
	}

	private void calculatePerformance(ProjCostStmtDtlTO childCostStmt) {
		log.info("calculatePerformance method ");
		ProjCostBudgetTO original = childCostStmt.getOriginalCostBudget();
		if (original == null)
			return;
		log.info("original " + original);
		ProjCostBudgetTO actual = childCostStmt.getActualCostBudget();
		log.info("actual " + actual);
		BigDecimal actualTotal = (CommonUtil.ifNullGetDefaultValue(actual.getLabourCost()))
				.add(CommonUtil.ifNullGetDefaultValue(actual.getMaterialCost()))
				.add(CommonUtil.ifNullGetDefaultValue(actual.getPlantCost()))
				.add(CommonUtil.ifNullGetDefaultValue(actual.getOtherCost()));
		log.info("actualTotal " + actualTotal);
		ProjCostBudgetTO revised = childCostStmt.getRevisedCostBudget();
		log.info("revised " + revised);
		BigDecimal originalTotal = (revised != null
				&& CommonUtil.ifNullGetDefaultValue(revised.getLabourCost()).intValue() > 0
						? CommonUtil.ifNullGetDefaultValue(revised.getLabourCost())
						: CommonUtil.ifNullGetDefaultValue(original.getLabourCost()))
								.add((revised != null
										&& CommonUtil.ifNullGetDefaultValue(revised.getMaterialCost()).intValue() > 0
												? CommonUtil.ifNullGetDefaultValue(revised.getMaterialCost())
												: CommonUtil.ifNullGetDefaultValue(original.getMaterialCost())))
								.add((revised != null
										&& CommonUtil.ifNullGetDefaultValue(revised.getPlantCost()).intValue() > 0
												? CommonUtil.ifNullGetDefaultValue(revised.getPlantCost())
												: CommonUtil.ifNullGetDefaultValue(original.getPlantCost())))
								.add((revised != null
										&& CommonUtil.ifNullGetDefaultValue(revised.getOtherCost()).intValue() > 0
												? CommonUtil.ifNullGetDefaultValue(revised.getOtherCost())
												: CommonUtil.ifNullGetDefaultValue(original.getOtherCost())));
		log.info("!originalTotal.equals(BigDecimal.ZERO) " + !originalTotal.equals(BigDecimal.ZERO));
		if (!originalTotal.equals(BigDecimal.ZERO)) {
			log.info("actualTotal " + actualTotal);
			log.info("originalTotal " + originalTotal);
			childCostStmt.setSpentCost(
					actualTotal.divide(originalTotal, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
			log.info("childCostStmt.getSpentCost() " + childCostStmt.getSpentCost());
			if (childCostStmt.getEarnedValue() != null && !BigDecimal.ZERO.equals(childCostStmt.getEarnedValue())) {
				log.info("childCostStmt.getEarnedValue() " + childCostStmt.getEarnedValue());
				childCostStmt.setWorkProgress(childCostStmt.getEarnedValue().divide(originalTotal, RoundingMode.HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
				log.info("childCostStmt.getWorkProgress() " + childCostStmt.getWorkProgress());
			}
		}
		log.info("!actualTotal.equals(BigDecimal.ZERO) " + !actualTotal.equals(BigDecimal.ZERO));
		log.info("childCostStmt.getEarnedValue() " + childCostStmt.getEarnedValue());
		log.info("!BigDecimal.ZERO.equals(childCostStmt.getEarnedValue()) "
				+ !BigDecimal.ZERO.equals(childCostStmt.getEarnedValue()));
		if (!actualTotal.equals(BigDecimal.ZERO) && childCostStmt.getEarnedValue() != null
				&& !BigDecimal.ZERO.equals(childCostStmt.getEarnedValue())) {
			childCostStmt
					.setProductivityFactor(childCostStmt.getEarnedValue().divide(actualTotal, RoundingMode.HALF_UP));
		}
	}

	public ProjCostStaementsResp getProjExitCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
		ProjCostStaementsResp projCostStaementsResp = new ProjCostStaementsResp();
		Map<Long, ProjCostStmtDtlEntity> projCostStmtMap = new HashMap<>();
		List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository
				.findCostDetails(projCostStatementsGetReq.getProjId(), StatusCodes.ACTIVE.getValue());

		List<ProjCostStmtDtlEntity> pojCostStmtDtlEntities = projCostStatementsRepository
				.findProjCostStatements(projCostStatementsGetReq.getProjId(), projCostStatementsGetReq.getStatus());
		for (ProjCostStmtDtlEntity projCostStmtDtlEntity : pojCostStmtDtlEntities) {
			projCostStmtMap.put(projCostStmtDtlEntity.getProjCostItemEntity().getId(), projCostStmtDtlEntity);
		}
		ProjCostStmtDtlTO projCostStmtDtlTO = null;
		for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
			projCostStmtDtlTO = ProjCostStmtDtlHandler.populateProjExitCostStmts(projCostItemEntity, projCostStmtMap);
			if (CommonUtil.objectNotNull(projCostStmtDtlTO)) {
				projCostStaementsResp.getProjCostStmtDtlTOs().add(projCostStmtDtlTO);
			}
		}
		return projCostStaementsResp;
	}

	public void saveProjCostCodes(ProjCostCodesSaveReq projCostCodesSaveReq) {
		/*
		 * projCostStatementsRepository.save(ProjCostStmtDtlHandler.
		 * populateCostCodesEntities(projCostCodesSaveReq));
		 */
	}

	public void saveProjCostStatements(ProjCostStatementsSaveReq projCostStatementsSaveReq) {
		List<ProjCostStmtDtlEntity> entities = ProjCostStmtDtlHandler
				.populateCostStatementEntities(projCostStatementsSaveReq, projCostItemRepository, epsProjRepository);
		projCostStatementsRepository.save(entities);
	}

	public ProjectPlantsResp getProjectPlants(ProjectPlantsGetReq projectPlantsGetReq) {
		Long projectId = projectPlantsGetReq.getProjId();
		ProjectPlantsResp projectPlantsResp = new ProjectPlantsResp();
		List<ProjectPlantsDtlEntity> projectPlantsDtlEntites = projectPlantsRepository.findProjectPlants(projectId);
		System.out.println("projectPlantsDtlEntites size:"+projectPlantsDtlEntites.size());
		ProjEstimateEntity projEstimates = projEstimateRepository.findPlantEstimate(projectId);

		Map<Long, LabelKeyTO> actualResp = actualHrsServiceImpl.getPlantActualHrs(projectId);

		for (ProjectPlantsDtlEntity projectPlantsDtlEntity : projectPlantsDtlEntites) {

			ProjectPlantsDtlTO projectPlantsDtlTO = ProjectPlantsDtlHandler.convertEntityToPOJO(projectPlantsDtlEntity);
			projectPlantsDtlTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projectPlantsGetReq.getProjId(), projectPlantsDtlEntity.getPlantMstrEntity().getId()));
			if (projectPlantsDtlTO.getMinStartDateOfBaseline() == null)
				projectPlantsDtlTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projectPlantsGetReq.getProjId(), projectPlantsDtlEntity.getId()));
            projectPlantsDtlTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projectPlantsGetReq.getProjId(), projectPlantsDtlEntity.getPlantMstrEntity().getId()));
            if (projectPlantsDtlTO.getMaxFinishDateOfBaseline() == null)
            	projectPlantsDtlTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projectPlantsGetReq.getProjId(), projectPlantsDtlEntity.getId()));

			if (actualResp.get(projectPlantsDtlTO.getPlantClassTO().getId()) != null) {
				projectPlantsDtlTO.setActualQty(
						new BigDecimal(actualResp.get(projectPlantsDtlTO.getPlantClassTO().getId()).getCode()));
			}
			if (projEstimates != null) {
				projectPlantsDtlTO.setEstimateType(projEstimates.getFormulaType());
				if (projEstimates.getFormulaType().toLowerCase()
						.equalsIgnoreCase(ProjEstimateConstants.REMAINING_UNITS)) {
					BigDecimal actualQty = new BigDecimal(0);
					if (projectPlantsDtlTO.getActualQty() != null) {
						actualQty = projectPlantsDtlTO.getActualQty();
					}
					// calculate estimate to complete
					BigDecimal budgetAtCompletion = (projectPlantsDtlTO.getRevisedQty() != null
							? projectPlantsDtlTO.getRevisedQty()
							: projectPlantsDtlTO.getOriginalQty());
					budgetAtCompletion = budgetAtCompletion != null ? budgetAtCompletion : new BigDecimal(0);
					projectPlantsDtlTO.setEstimateComplete(budgetAtCompletion.subtract(actualQty));
				}
			}
			projectPlantsResp.getProjectPlantsDtlTOs().add(projectPlantsDtlTO);
		}
		return projectPlantsResp;
	}

	public void saveProjectPlants(ProjectPlantsSaveReq projectPlantsSaveReq) {
		List<ProjectPlantsDtlEntity> plantsDtlEntities = ProjectPlantsDtlHandler.convertPOJOToEntity(
				projectPlantsSaveReq.getProjectPlantsDtlTOs(), epsProjRepository, plantClassRepository,
				measurementRepository);
		projectPlantsRepository.save(plantsDtlEntities);
	}

	public ProjReportsResp getProjReports(ProjReportsGetReq projReportsGetReq) {
		ProjReportsResp projReportsResp = new ProjReportsResp();
		List<ProjectReportsEntity> projectReportsEntities;
		List<Long> projIds = projReportsGetReq.getProjIds();
		if (projIds.isEmpty()) {
			projectReportsEntities = projReportsRepository.findProjReports(projReportsGetReq.getProjId(),
					projReportsGetReq.getStatus());
		} else {
			projectReportsEntities = projReportsRepository.findProjReports(projIds);
		}

		for (ProjectReportsEntity projectReportsEntity : projectReportsEntities) {
			projReportsResp.getProjectReportsTOs().add(ProjReportsHandler.convertEntityToPOJO(projectReportsEntity));
		}
		return projReportsResp;
	}

	public void saveProjReports(ProjReportsSaveReq projReportsSaveReq) {
		long projId = projReportsSaveReq.getProjId();
		List<ProjectReportsEntity> projectReports = projReportsRepository.findProjReports(projId,
				projReportsSaveReq.getStatus());
		if (projectReports != null && !projectReports.isEmpty()) {
			projReportsRepository.save(ProjReportsHandler.convertPOJOToEntityExisting(projectReports,
					projReportsSaveReq.getProjectReportsTOs(), projId, epsProjRepository));
		} else {
			projReportsRepository.save(ProjReportsHandler.convertPOJOToEntity(projReportsSaveReq.getProjectReportsTOs(),
					projId, epsProjRepository));
		}
	}

	public ProjManPowerResp getProjManPowers(ProjManpowerGetReq projManpowerGetReq) {

		ProjManPowerResp projManPowerResp = new ProjManPowerResp();
		List<Long> projIds = projManpowerGetReq.getProjIds();

		if (projManpowerGetReq.getProjId() != null)
			projIds.add(projManpowerGetReq.getProjId());
		Map<Long, Double> totalEarnedValues = projSowTotalActualRepository.findManpowerEarnedValueByProj(projIds);
		for (Long projId : projIds) {
			List<ProjManpowerEntity> projectManEntites = projManpowerRepository.findManpowersByProject(projId);

			Map<Long, LabelKeyTO> actualHrs = actualHrsServiceImpl.getManpowerActualHrs(projId);
			ProjEstimateEntity projEstimate = projEstimateRepository.findManpowerEstimate(projId);
			Double totalBudget = Double.valueOf(0);
			for (ProjManpowerEntity projManpowerEntity : projectManEntites) {
				ProjManpowerTO projManpowerTO = ProjManpowerHandler.convertEntityToPOJO(projManpowerEntity);
				projManpowerTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository
						.findMinimumStartDateOfBaselineBy(projId, projManpowerEntity.getEmpClassMstrEntity().getId()));
				if (projManpowerTO.getMinStartDateOfBaseline() == null)
					projManpowerTO.setMinStartDateOfBaseline(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projId, projManpowerEntity.getId()));
				projManpowerTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository
						.findMaximumFinishDateOfBaselineBy(projId, projManpowerEntity.getEmpClassMstrEntity().getId()));
				if (projManpowerTO.getMaxFinishDateOfBaseline() == null)
					projManpowerTO.setMaxFinishDateOfBaseline(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projId, projManpowerEntity.getId()));
				if (actualHrs.get(projManpowerTO.getEmpClassId()) != null) {
					projManpowerTO.setActualQty(new Double(actualHrs.get(projManpowerTO.getEmpClassId()).getCode()));
				}
				Double budget = calculateBudgetAtCompletion(projManpowerTO);
				projManpowerTO.setBudgetAtCompletion(budget);
				totalBudget += budget;

				if (projEstimate != null) {
					projManpowerTO.setEstimateType(projEstimate.getFormulaType());
				}
				projManPowerResp.getProjManpowerTOs().add(projManpowerTO);
			}
			Double totalEV = CommonUtil.ifNullGetDefaultValue(totalEarnedValues.get(projId));
			for (ProjManpowerTO manpowerTO : projManPowerResp.getProjManpowerTOs()) {
				if(manpowerTO.getProjId().equals(projId)) {
					if (manpowerTO.getProjEmpCategory().equalsIgnoreCase("direct")) {
						if ((manpowerTO.getBudgetAtCompletion() == 0 && totalBudget == 0) || totalBudget == 0)
							continue;
						Double budgetPer = (manpowerTO.getBudgetAtCompletion() / totalBudget) * 100;
						Double earnedValue = (totalEV * budgetPer) / 100;
						earnedValue = Double.valueOf(new DecimalFormat("#.##").format(earnedValue));
						manpowerTO.setEarnedValue(earnedValue);
						manpowerTO.setProductivityFactor(earnedValue / manpowerTO.getActualQty());
					} else if (manpowerTO.getProjEmpCategory().equalsIgnoreCase("in-direct")) {
						manpowerTO.setEarnedValue(manpowerTO.getActualQty());
						manpowerTO.setProductivityFactor(manpowerTO.getEarnedValue() / manpowerTO.getActualQty());
					}
				}
			}
		}
		return projManPowerResp;
	}

	private Double calculateBudgetAtCompletion(ProjManpowerTO projManpowerTO) {
		Double budgetAtCompletion = projManpowerTO.getRevisedQty() != null && projManpowerTO.getRevisedQty() > 0
				? projManpowerTO.getRevisedQty()
				: projManpowerTO.getOriginalQty();
		return CommonUtil.ifNullGetDefaultValue(budgetAtCompletion);
	}

	public void saveProjManPowers(ProjManpowerSaveReq projManpowerSaveReq) {
		List<ProjManpowerEntity> entities = ProjManpowerHandler.convertPOJOToEntity(
				projManpowerSaveReq.getProjManpowerTOs(), epsProjRepository, empClassRepository, measurementRepository);
		projManpowerRepository.save(entities);
	}

	public ProjProgressResp getProjProgress(ProjProgressGetReq projProgressGetReq) {

		List<ProjProgressEntity> projProgressEntites = projProgressRepository
				.findProjProgress(projProgressGetReq.getProjId(), projProgressGetReq.getStatus());
		Map<Long, ProjProgressEntity> projProgressmap = new HashMap<>();
		for (ProjProgressEntity projProgressEntity : projProgressEntites) {
			projProgressmap.put(projProgressEntity.getProjSOWItemEntity().getProjSOEItemEntity().getId(),
					projProgressEntity);
		}

		List<ProjSOEItemEntity> projSOEItemEntities = projProgressRepository
				.findProjProgressSOEDetails(projProgressGetReq.getProjId(), projProgressGetReq.getStatus());

		ProjProgressResp projProgressResp = new ProjProgressResp();
		for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
			ProjProgressTO projProgressTO = ProjProgressHandler.populateProjProgress(projSOEItemEntity,
					projProgressmap);
			if (CommonUtil.objectNotNull(projProgressTO) && !projProgressTO.getChildProjProgressTOs().isEmpty()) {
				projProgressResp.getProjProgressTOs().add(projProgressTO);
			}
		}
		return projProgressResp;
	}

	public void saveProjProgress(ProjProgressSaveReq projProgressSaveReq) {

		projProgressRepository.save(ProjProgressHandler.papulateProjProgressEntities(
				projProgressSaveReq.getProjProgressTOs(), epsProjRepository, projSOWItemRepository));

	}

	public void saveProjSows(ProjSowsSaveReq projSowsSaveReq) {
		projProgressRepository
				.save(ProjProgressHandler.populateSowEnties(projSowsSaveReq, epsProjRepository, projSOWItemRepository));
	}

	public ProjCostCodeStatusResp getProjCostCodeStatus(ProjCostCodeStatusGetReq projCostCodeStatusGetReq) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProjSummaryResp getProjSummary(ProjSummaryGetReq projSummaryGetReq) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProjStatusResp getProjStatus(ProjStatusGetReq projStatusGetReq) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getWeekDays(List<FinancePeriodPayCyclesTO> list) {
		List<String> weekdays = new ArrayList<>();

		for (FinancePeriodPayCyclesTO financePeriodPayCyclesTO : list) {
			weekdays.add(financePeriodPayCyclesTO.getDesc());
		}
		return weekdays;
	}

	public List<String> getWeekDays() {

		List<String> weekdays = new ArrayList<>();
		for (WeekDays weekDays : WeekDays.values()) {
			weekdays.add(weekDays.getName());
		}
		return weekdays;
	}

	public ProjResourceCurveResp getResourceCurves(ProjResourceCurveGetReq projResourceCurveGetReq) {
		ProjResourceCurveResp projResourceCurveResp = new ProjResourceCurveResp();
		List<ResourceCurveEntity> projResourceCurveEntites = projResourceCurveRepository
				.findProjResourceCurves(AppUserUtils.getClientId(), projResourceCurveGetReq.getStatus());

		for (ResourceCurveEntity projResourceCurveEntity : projResourceCurveEntites) {
			projResourceCurveResp.getProjResourceCurveTOs()
					.add(ProjResourceCurveHandler.convertEntityToPOJO(projResourceCurveEntity));
		}
		return projResourceCurveResp;
	}

	public ProjProgressClaimResp getProjProgressClaim(ProjProgressClaimGetReq projProgressClaimGetReq) {
		ProjProgressClaimResp projProgressClaimResp = new ProjProgressClaimResp();
		List<ProgressClaimNormalTimeEntity> projProgressClaimEntites = projProgressClaimRepository
				.findProjProgressClaim(projProgressClaimGetReq.getProjId(), projProgressClaimGetReq.getStatus());

		for (ProgressClaimNormalTimeEntity projProgressClaimEntity : projProgressClaimEntites) {
			projProgressClaimResp.getProjProgressClaimTOs()
					.add(ProjProgressClaimHandler.convertEntityToPOJO(projProgressClaimEntity));
		}
		return projProgressClaimResp;
	}

	public void saveProjProgressClaim(ProjProgressClaimSaveReq projProgressClaimSaveReq) {
		projProgressClaimRepository.save(ProjProgressClaimHandler
				.convertPOJOToEntity(projProgressClaimSaveReq.getProjProgressClaimTOs(), epsProjRepository));
	}

	public void saveProjProgressClaimAppr(ProjProgressClaimApprSaveReq projProgressClaimApprSaveReq) {
		projProgressClaimApprRepository.save(ProjProgressClaimApprHandler.convertPOJOToEntity(
				projProgressClaimApprSaveReq.getProjProgressClaimApprTOs(), projProgressClaimRepository,
				loginRepository));
	}

	public ProjProgressClaimePeroidResp getProjProgressClaimePeriodCycle(
			ProjProgressClaimGetReq projProgressClaimGetReq) {
		ProjProgressClaimePeroidResp projProgressClaimePeriodResp = new ProjProgressClaimePeroidResp();
		List<ProgressClaimPeriodCycleEntity> projProgressClaimePeriodEntities = projProgressClaimePeriodRepository
				.findProjProgressClaimePeroid(projProgressClaimGetReq.getProjId(), projProgressClaimGetReq.getStatus());

		for (ProgressClaimPeriodCycleEntity projProgressClaimePeriodEntity : projProgressClaimePeriodEntities) {
			projProgressClaimePeriodResp.getProjProgressClaimePeriodTOs()
					.add(ProjProgressClaimePeriodHandler.convertEntityToPOJO(projProgressClaimePeriodEntity));
		}
		return projProgressClaimePeriodResp;
	}

	public void saveProjProgressClaimePeriodCycle(ProjProgressClaimePeroidSaveReq projProgressClaimePeroidSaveReq) {
		projProgressClaimePeriodRepository.save(ProjProgressClaimePeriodHandler.convertPOJOToEntity(
				projProgressClaimePeroidSaveReq.getProjProgressClaimePeriodTOs(), epsProjRepository));

	}

	public void saveProjPayRollCycle(ProjPayRollCycleSaveReq projPayRollCycleSaveReq) {
		List<ProjPayRollCycleEntity> cycleEntities = ProjPayRollCycleHandler.convertPOJOToEntity(
				projPayRollCycleSaveReq.getProjPayRollCycleTOs(), epsProjRepository, procureCatgRepository);
		projPayRollCycleRepository.save(cycleEntities);
	}

	public List<String> getYears(List<FinancePeriodPayCyclesTO> list) {
		List<String> years = new ArrayList<>();
		for (FinancePeriodPayCyclesTO financePeriodPayCyclesTO : list) {
			years.add(financePeriodPayCyclesTO.getDesc());
		}
		return years;
	}

	public ProjTimeSheetWeekResp getProjTimeSheetWeek(ProjTimeSheetGetReq projTimeSheetGetReq) {
		ProjTimeSheetWeekResp projTimeSheetWeekResp = new ProjTimeSheetWeekResp();

		ProjTimeSheetWeekDtlEntity projTimeSheetWeekDtlEntity = projTimeSheetWeekRepository
				.findProjTimeSheetWeekDtl(projTimeSheetGetReq.getProjId(), projTimeSheetGetReq.getStatus());

		projTimeSheetWeekResp
				.setProjTimeSheetWeekDtlTO(ProjTimeSheetWeekHandler.convertEntityToPOJO(projTimeSheetWeekDtlEntity));

		return projTimeSheetWeekResp;
	}

	public void saveProjTimeSheetWeek(ProjTimeSheetWeekSaveReq projTimeSheetWeekSaveReq) {
		projTimeSheetWeekRepository.save(ProjTimeSheetWeekHandler
				.convertOnePojoToOneEntity(projTimeSheetWeekSaveReq.getProjTimeSheetWeekDtlTO(), epsProjRepository));
	}

	public ProjCostStaementsResp getProjCostCodeStmts(ProjCostStatementsGetReq projCostStatementsGetReq) {
		ProjCostStaementsResp projCostStaementsResp = new ProjCostStaementsResp();
		List<ProjCostStmtDtlEntity> projCostStmtDtlEntites = projCostStatementsRepository
				.findProjCostStatements(projCostStatementsGetReq.getProjId(), projCostStatementsGetReq.getStatus());

		for (ProjCostStmtDtlEntity projCostStmtDtlEntity : projCostStmtDtlEntites) {
			projCostStaementsResp.getProjCostStmtDtlTOs()
					.add(ProjCostStmtDtlHandler.convertEntityToPOJO(projCostStmtDtlEntity));
		}

		return projCostStaementsResp;
	}

	public List<LabelKeyTO> getBudgets() {
		List<LabelKeyTO> budgets = new ArrayList<>();
		LabelKeyTO labelKeyTO = null;

		for (Budget budget : Budget.values()) {
			labelKeyTO = new LabelKeyTO();
			labelKeyTO.setId(budget.getValue());
			labelKeyTO.setCode(budget.getName());
			labelKeyTO.setName(budget.getName());
			budgets.add(labelKeyTO);
		}
		return budgets;
	}

	public ProjManPowerStatusResp getProjManPowerstatus(ProjManpowerGetReq projManpowerGetReq) {
		ProjManPowerStatusResp projManPowerStatusResp = new ProjManPowerStatusResp();

		ProjManPowerResp projManPowers = getProjManPowers(projManpowerGetReq);
		
		Map<String, ProjManPowerStatusTO> map = new HashMap<>();

		ProjEstimateEntity projEstimate = projEstimateRepository.findManpowerEstimate(projManpowerGetReq.getProjId());
		log.info("projManpowerGetReq.getProjId() " + projManpowerGetReq.getProjId());
		log.info("projEstimate.getFormulaType() " + projEstimate.getFormulaType());

		for (ProjManpowerTO projManpowerTO : projManPowers.getProjManpowerTOs()) {
			String catName = projManpowerTO.getProjEmpCategory();
			projManpowerTO.setEstimateType(projEstimate.getFormulaType());

			if (catName != null) {
				if (map.get(catName) == null) {
					ProjManPowerStatusTO value = new ProjManPowerStatusTO();
					value.setEstimateType(projManpowerTO.getEstimateType());
					value.setEmpCatgName(catName);
					value.setOriginalQty(CommonUtil.getDoubleValue(projManpowerTO.getOriginalQty()));
					Double revisedQty = (CommonUtil.getDoubleValue(projManpowerTO.getRevisedQty()) != 0
							|| CommonUtil.getDoubleValue(projManpowerTO.getRevisedQty()) != null)
									? CommonUtil.getDoubleValue(projManpowerTO.getRevisedQty())
									: CommonUtil.getDoubleValue(projManpowerTO.getOriginalQty());
					value.setRevisedQty(revisedQty);
					value.setEarnedValue(CommonUtil.getDoubleValue(projManpowerTO.getEarnedValue()));
					value.setPlannedValue(CommonUtil.getDoubleValue(projManpowerTO.getPlannedValue()));
					value.setActualQty(CommonUtil.getDoubleValue(projManpowerTO.getActualQty()));
					value.setRemainingQty(value.getOriginalQty() - value.getActualQty());
					if (value.getEstimateType().equals("Remaining Units")) {
						value.setEstimateComplete(value.getOriginalQty() - value.getActualQty());
					} else if (value.getEstimateType().equals("BAC-EV")) {
						value.setEstimateComplete(
								(value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())
										- value.getEarnedValue());
					} else if (value.getEstimateType().equals("(BAC-EV)/PF")) {
						Double PF = (value.getEarnedValue() > 0 || value.getActualQty() > 0)
								? (value.getEarnedValue() / value.getActualQty())
								: 0;
						value.setEstimateComplete(PF > 0
								? ((value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())
										- value.getEarnedValue()) / PF
								: (value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())
										- value.getEarnedValue());
					} else if (value.getEstimateType().equals("(BAC-EV)/(PF*SPI)")) {
						Double PF = (value.getEarnedValue() > 0 || value.getActualQty() > 0)
								? (value.getEarnedValue() / value.getActualQty())
								: 0;
						Double SPI = (value.getEarnedValue() > 0 && value.getPlannedValue() > 0)
								? (value.getEarnedValue() / value.getPlannedValue())
								: 0;
						value.setEstimateComplete((PF > 0 && SPI > 0)
								? ((value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())
										- value.getEarnedValue()) / (PF * SPI)
								: (value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())
										- value.getEarnedValue());
					} else if (value.getEstimateType().equals("New Estimate")) {
						value.setEstimateComplete(CommonUtil.getDoubleValue(projManpowerTO.getEstimateComplete()));
					}
					value.setEstimateCompletion(value.getActualQty() + value.getEstimateComplete());
					value.setCompVariance((value.getActualQty() + value.getEstimateComplete())
							- (value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty()));
					value.setPercentageSpent((value.getActualQty()
							/ (value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())) * 100);
					map.put(catName, value);
				} else {
					ProjManPowerStatusTO value = map.get(catName);
					value.setEstimateType(projManpowerTO.getEstimateType());
					value.setOriginalQty(
							value.getOriginalQty() + CommonUtil.getDoubleValue(projManpowerTO.getOriginalQty()));
					Double revisedQty = (CommonUtil.getDoubleValue(projManpowerTO.getRevisedQty()) != 0
							|| CommonUtil.getDoubleValue(projManpowerTO.getRevisedQty()) != null)
									? CommonUtil.getDoubleValue(projManpowerTO.getRevisedQty())
									: CommonUtil.getDoubleValue(projManpowerTO.getOriginalQty());
					value.setRevisedQty(value.getRevisedQty() + revisedQty);
					value.setEarnedValue(value.getEarnedValue() + projManpowerTO.getEarnedValue());
					value.setPlannedValue(
							value.getPlannedValue() + CommonUtil.getDoubleValue(projManpowerTO.getPlannedValue()));
					value.setActualQty(value.getActualQty() + CommonUtil.getDoubleValue(projManpowerTO.getActualQty()));
					value.setRemainingQty(value.getOriginalQty() - value.getActualQty());
					if (value.getEstimateType().equals("Remaining Units")) {
						value.setEstimateComplete(value.getOriginalQty() - value.getActualQty());
					} else if (value.getEstimateType().equals("BAC-EV")) {
						value.setEstimateComplete(
								(value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())
										- value.getEarnedValue());
					} else if (value.getEstimateType().equals("(BAC-EV)/PF")) {
						Double PF = (value.getEarnedValue() > 0 || value.getActualQty() > 0)
								? (value.getEarnedValue() / value.getActualQty())
								: 0;
						value.setEstimateComplete(PF > 0
								? ((value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())
										- value.getEarnedValue()) / PF
								: (value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())
										- value.getEarnedValue());
					} else if (value.getEstimateType().equals("(BAC-EV)/(PF*SPI)")) {
						Double PF = (value.getEarnedValue() > 0 || value.getActualQty() > 0)
								? (value.getEarnedValue() / value.getActualQty())
								: 0;
						Double SPI = (value.getEarnedValue() > 0 && value.getPlannedValue() > 0)
								? (value.getEarnedValue() / value.getPlannedValue())
								: 0;
						value.setEstimateComplete((PF > 0 && SPI > 0)
								? ((value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())
										- value.getEarnedValue()) / (PF * SPI)
								: (value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())
										- value.getEarnedValue());
					} else if (value.getEstimateType().equals("New Estimate")) {
						value.setEstimateComplete(value.getEstimateComplete()
								+ CommonUtil.getDoubleValue(projManpowerTO.getEstimateComplete()));
					}
					value.setEstimateCompletion(value.getActualQty() + value.getEstimateComplete());
					value.setCompVariance((value.getActualQty() + value.getEstimateComplete())
							- (value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty()));
					value.setPercentageSpent((value.getActualQty()
							/ (value.getRevisedQty() > 0 ? value.getRevisedQty() : value.getOriginalQty())) * 100);
				}
			}

		}
		for (Map.Entry<String, ProjManPowerStatusTO> entry : map.entrySet()) {
			projManPowerStatusResp.getProjManPowerStatusTOs().add(entry.getValue());
		}

		return projManPowerStatusResp;
	}

	public ProjectPlantsStatusResp getProjectPlantsStatus(ProjectPlantsGetReq projectPlantsGetReq) {
		ProjectPlantsStatusResp projectPlantsStatusResp = new ProjectPlantsStatusResp();
		Long projectId = projectPlantsGetReq.getProjId();
		Set<Long> plantIds = new HashSet<>();

		List<Object[]> projectPlantsStatus = projectPlantsRepository.findProjectPlantsStatus(projectId,
				projectPlantsGetReq.getStatus());

		for (Object[] obj : projectPlantsStatus) {
			plantIds.add((Long) obj[0]);
		}
		Map<Long, Object[]> plantDetailsMap = new HashMap<>();
		if (!plantIds.isEmpty())
			for (Object[] obj : plantClassRepository.findPlantPartialDetails(plantIds)) {

				plantDetailsMap.put((Long) obj[0], obj);
			}
		ProjectPlantsStatusTO projectPlantsStatusTO = null;

		ProjEstimateEntity projEstimates = projEstimateRepository.findPlantEstimate(projectId);

		Map<Long, LabelKeyTO> actualHrs = actualHrsServiceImpl.getPlantActualHrs(projectId);
		Long plantId = null;
		Object[] plantDetails = null;
		for (Object[] quantityValues : projectPlantsStatus) {
			projectPlantsStatusTO = new ProjectPlantsStatusTO();
			plantId = (Long) quantityValues[0];

			projectPlantsStatusTO.setPlantId(plantId);
			projectPlantsStatusTO.setOriginalQty(quantityValues[1]);
			projectPlantsStatusTO.setRevisedQty(quantityValues[2]);
			projectPlantsStatusTO.setEstimate(quantityValues[3]);

			if (actualHrs.get(projectPlantsStatusTO.getPlantId()) != null) {
				projectPlantsStatusTO.setActualQty(
						new BigDecimal((String) actualHrs.get(projectPlantsStatusTO.getPlantId()).getCode()));
			}

			BigDecimal actualQuantity = new BigDecimal(0);
			if (projectPlantsStatusTO.getActualQty() != null) {
				actualQuantity = (BigDecimal) projectPlantsStatusTO.getActualQty();
			}

			BigDecimal origQty = new BigDecimal(0);
			if (projectPlantsStatusTO.getOriginalQty() != null) {
				origQty = (BigDecimal) projectPlantsStatusTO.getOriginalQty();
			}

			BigDecimal revisQty = new BigDecimal(0);
			if (projectPlantsStatusTO.getRevisedQty() != null) {
				revisQty = (BigDecimal) projectPlantsStatusTO.getRevisedQty();
			}

			if (revisQty.compareTo(BigDecimal.ZERO) > 0) {
				projectPlantsStatusTO.setRemainingQty(revisQty.subtract(actualQuantity));
			} else {
				projectPlantsStatusTO.setRemainingQty(origQty.subtract(actualQuantity));
			}

			plantDetails = plantDetailsMap.get(plantId);
			projectPlantsStatusTO.setPlantsName((String) plantDetails[1]);
			projectPlantsStatusTO.setMesureName((String) plantDetails[2]);
			projectPlantsStatusTO.setPlantsCode((String) plantDetails[3]);

			if (projEstimates != null) {
				if (projEstimates.getFormulaType().toLowerCase()
						.equalsIgnoreCase(ProjEstimateConstants.REMAINING_UNITS)) {
					// calculate estimate to complete
					projectPlantsStatusTO.setEstimate(origQty.subtract(actualQuantity));
				}
			}

			BigDecimal estToComplete = new BigDecimal(0);
			if (projectPlantsStatusTO.getEstimate() != null) {
				estToComplete = (BigDecimal) projectPlantsStatusTO.getEstimate();
			}

			projectPlantsStatusTO.setCompletion(actualQuantity.add(estToComplete));

			if (revisQty.compareTo(BigDecimal.ZERO) > 0) {
				projectPlantsStatusTO.setCompVariance((actualQuantity.add(estToComplete)).subtract(revisQty));
			} else {
				projectPlantsStatusTO.setCompVariance((actualQuantity.add(estToComplete)).subtract(origQty));
			}

			if (revisQty.compareTo(BigDecimal.ZERO) > 0) {
				projectPlantsStatusTO.setPercentageSpent(
						(actualQuantity.divide(revisQty, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(100)));
			} else {
				projectPlantsStatusTO.setPercentageSpent(
						(actualQuantity.divide(origQty, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(100)));
			}

			if (projectPlantsStatusTO.getActualQty() == null) {
				projectPlantsStatusTO.setActualQty(0);
			}

			if (projectPlantsStatusTO.getEstimate() == null) {
				projectPlantsStatusTO.setEstimate(0);
			}

			projectPlantsStatusResp.getProjectPlantsStatusTOs().add(projectPlantsStatusTO);
		}

		return projectPlantsStatusResp;
	}

	private void parseCostStatement(List<ProjCostSummaryBudgetTO> list, Long budgetType, BigDecimal cost,
			Map<String, List<ProjCostSummaryBudgetTO>> map, String key) {
		// log.info("parseCostStatement methoddddd");
		// log.info("cost " + cost);
		// log.info("list " + list);
		if (cost != null) {
			if (list != null) {
				boolean added = false;
				for (ProjCostSummaryBudgetTO labour : list) {
					if (labour.getBudgetType() != null) {
						if (labour.getBudgetType().equals(budgetType)) {
							labour.setCost(((BigDecimal) labour.getCost()).add(cost));
							added = true;
							break;
						}
					}
				}
				if (!added) {
					ProjCostSummaryBudgetTO budgetTO = new ProjCostSummaryBudgetTO();
					budgetTO.setBudgetType(budgetType);
					budgetTO.setCost(cost);
					list.add(budgetTO);
				}
			} else {
				List<ProjCostSummaryBudgetTO> costList = new ArrayList<>();
				ProjCostSummaryBudgetTO budgetTO = new ProjCostSummaryBudgetTO();
				budgetTO.setBudgetType(budgetType);
				budgetTO.setCost(cost);
				costList.add(budgetTO);
				map.put(key, costList);
			}
		}
	}

	private BigDecimal getCostValue(BigDecimal cost, Long budgetType, BigDecimal originalCost) {
		if (budgetType != null && budgetType == 2) {
			// while calculating value for revised units
			if (cost != null && cost.intValue() != 0) {
				// if value is not zero, consider it
				return cost;
			} else {
				// if value is zero or null, consider original budget value as revised value
				return originalCost;
			}
		} else {
			return cost;
		}
	}

	private void parseCostStatements(Map<String, List<ProjCostSummaryBudgetTO>> map, ProjCostBudgetTO cost,
			Long budgetType, ProjCostStmtDtlTO childProjCostStmtDtlTO) {
		// log.info("parseCostStatements method");
		BigDecimal costValue = null;
		BigDecimal originalCostValue = null;

		originalCostValue = childProjCostStmtDtlTO.getOriginalCostBudget() != null
				? childProjCostStmtDtlTO.getOriginalCostBudget().getLabourCost()
				: new BigDecimal(0);
		costValue = getCostValue(cost.getLabourCost(), budgetType, originalCostValue);

		parseCostStatement(map.get("Labour"), budgetType, costValue, map, "Labour");

		originalCostValue = childProjCostStmtDtlTO.getOriginalCostBudget() != null
				? childProjCostStmtDtlTO.getOriginalCostBudget().getPlantCost()
				: new BigDecimal(0);
		costValue = getCostValue(cost.getPlantCost(), budgetType, originalCostValue);

		parseCostStatement(map.get("Plant"), budgetType, costValue, map, "Plant");

		originalCostValue = childProjCostStmtDtlTO.getOriginalCostBudget() != null
				? childProjCostStmtDtlTO.getOriginalCostBudget().getMaterialCost()
				: new BigDecimal(0);
		costValue = getCostValue(cost.getMaterialCost(), budgetType, originalCostValue);

		parseCostStatement(map.get("Material"), budgetType, costValue, map, "Material");

		originalCostValue = childProjCostStmtDtlTO.getOriginalCostBudget() != null
				? childProjCostStmtDtlTO.getOriginalCostBudget().getOtherCost()
				: new BigDecimal(0);
		costValue = getCostValue(cost.getOtherCost(), budgetType, originalCostValue);

		parseCostStatement(map.get("Other"), budgetType, costValue, map, "Other");

	}

	public ProjCostStatementsSummaryResp getProjCostStatusSummary(ProjCostStatementsGetReq projCostStatementsGetReq) {
        ProjCostStatementsSummaryResp projCostStatementsSummaryResp = new ProjCostStatementsSummaryResp();
        log.info("Project Project Status > Project Summary > Cost");
        ProjCostStaementsResp costStatementsResp = getProjCostStatements(projCostStatementsGetReq);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        ObjectMapper om = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = om.writeValueAsString(costStatementsResp);
        } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info("=======================================");
        log.info(jsonString);
        log.info("=======================================");
      //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        log.info("##################################################################################");
        Map<String, List<ProjCostSummaryBudgetTO>> map = new LinkedHashMap<>();
        log.info("costStatementsResp.getProjCostStmtDtlTOs().size() " + costStatementsResp.getProjCostStmtDtlTOs().size());
        Map<Long, BigDecimal> earnedValues = getEarnedValueForCostId(projCostStatementsGetReq.getProjId());

        ProjEstimateEntity projEstimate = projEstimateRepository.findEstimateCostEstimate(projCostStatementsGetReq.getProjId());
        
        BigDecimal ernTotal = BigDecimal.valueOf(0);
        
        Double totalOriginalCost = (double) 0;
		Double totalRevisedCost = (double) 0;
		Double totalActualCost = (double) 0;
		Double totalRemainingCost = (double) 0;
		Double totalNewEstimateCost = (double) 0;
		Double totalEarnedCost;
		Double totalPlannedCost;
		Double totalETC = (double) 0;
		Double BAC_EV = (double) 0;
		Double CPI = (double) 0;
        
        Double mainTotalOriginal1 = (double) 0;
        Double mainTotalRevised1 = (double) 0;
        Double mainTotalActual1 = (double) 0;
        Double mainTotalETC1 = (double) 0;
        Double mainTotalEAC1 = (double) 0;
        Double mainCompVarience1 = (double) 0;
        
        for (ProjCostStmtDtlTO projCostStmtDtlTO : costStatementsResp.getProjCostStmtDtlTOs()) {
        	log.info("projCostStmtDtlTO.getProjCostStmtDtlTOs().size() " + projCostStmtDtlTO.getProjCostStmtDtlTOs().size());
        	
            for (ProjCostStmtDtlTO subProjCostStmtDtlTO : projCostStmtDtlTO.getProjCostStmtDtlTOs()) {
            	log.info("subProjCostStmtDtlTO.getProjCostStmtDtlTOs().size() " + subProjCostStmtDtlTO.getProjCostStmtDtlTOs().size());
            	log.info("subProjCostStmtDtlTO.getProjCostStmtDtlTOs().size() " + subProjCostStmtDtlTO.isItem());
            	log.info("projEstimate.getFormulaType() " + projEstimate.getFormulaType());
            	
            	if(subProjCostStmtDtlTO.isItem()) {
            		
    				if(subProjCostStmtDtlTO.getEarnedValue() == null) {
    					totalEarnedCost = (double) 0;
    				} else {
    					totalEarnedCost = subProjCostStmtDtlTO.getEarnedValue().doubleValue();
    				}
    				
    				if (subProjCostStmtDtlTO.getActualCostBudget().getLabourCost() == null) {
    					subProjCostStmtDtlTO.getActualCostBudget().setLabourCost(new BigDecimal(0));
        			}
        			if (subProjCostStmtDtlTO.getActualCostBudget().getMaterialCost() == null) {
        				subProjCostStmtDtlTO.getActualCostBudget().setMaterialCost(new BigDecimal(0));
        			}
        			if (subProjCostStmtDtlTO.getActualCostBudget().getPlantCost() == null) {
        				subProjCostStmtDtlTO.getActualCostBudget().setPlantCost(new BigDecimal(0));
        			}
        			if (subProjCostStmtDtlTO.getActualCostBudget().getOtherCost() == null) {
        				subProjCostStmtDtlTO.getActualCostBudget().setOtherCost(new BigDecimal(0));
        			}
        			if (subProjCostStmtDtlTO.getEstimateCompleteBudget().getLabourCost() == null) {
        				subProjCostStmtDtlTO.getEstimateCompleteBudget().setLabourCost(new BigDecimal(0));
        			}
        			if (subProjCostStmtDtlTO.getEstimateCompleteBudget().getMaterialCost() == null) {
        				subProjCostStmtDtlTO.getEstimateCompleteBudget().setMaterialCost(new BigDecimal(0));
        			}
        			if (subProjCostStmtDtlTO.getEstimateCompleteBudget().getPlantCost() == null) {
        				subProjCostStmtDtlTO.getEstimateCompleteBudget().setPlantCost(new BigDecimal(0));
        			}
        			if (subProjCostStmtDtlTO.getEstimateCompleteBudget().getOtherCost() == null) {
        				subProjCostStmtDtlTO.getEstimateCompleteBudget().setOtherCost(new BigDecimal(0));
        			}
    				
    				totalOriginalCost = subProjCostStmtDtlTO.getOriginalCostBudget().getLabourCost().doubleValue()
    						+ (subProjCostStmtDtlTO.getOriginalCostBudget().getMaterialCost().doubleValue() 
    						+ (subProjCostStmtDtlTO.getOriginalCostBudget().getPlantCost().doubleValue() 
    						+ (subProjCostStmtDtlTO.getOriginalCostBudget().getOtherCost().doubleValue())));
    				totalRevisedCost = subProjCostStmtDtlTO.getRevisedCostBudget().getLabourCost().doubleValue() 
    						+ (subProjCostStmtDtlTO.getRevisedCostBudget().getMaterialCost().doubleValue() 
    						+ (subProjCostStmtDtlTO.getRevisedCostBudget().getPlantCost().doubleValue() 
    						+ (subProjCostStmtDtlTO.getRevisedCostBudget().getOtherCost().doubleValue())));
    				totalActualCost = subProjCostStmtDtlTO.getActualCostBudget().getLabourCost().doubleValue() 
    						+ (subProjCostStmtDtlTO.getActualCostBudget().getMaterialCost().doubleValue() 
    						+ (subProjCostStmtDtlTO.getActualCostBudget().getPlantCost().doubleValue() 
    						+ (subProjCostStmtDtlTO.getActualCostBudget().getOtherCost().doubleValue() )));
    				totalNewEstimateCost = subProjCostStmtDtlTO.getEstimateCompleteBudget().getLabourCost().doubleValue()
    						+ (subProjCostStmtDtlTO.getEstimateCompleteBudget().getMaterialCost().doubleValue() 
    						+ (subProjCostStmtDtlTO.getEstimateCompleteBudget().getPlantCost().doubleValue() 
    						+ (subProjCostStmtDtlTO.getEstimateCompleteBudget().getOtherCost().doubleValue() )));
    				
    				if (totalRevisedCost > 0) {
    					BAC_EV = totalRevisedCost- totalEarnedCost;
    				} else {
    					BAC_EV = totalOriginalCost - totalEarnedCost;
    				}
    				
    				if (totalEarnedCost == 0 || totalActualCost == 0 ) {
    					CPI = (double) 0;
    				} else {
    					CPI = totalEarnedCost / totalActualCost;
    				}
    				if (totalRevisedCost > 0) {
    					totalRemainingCost = totalRevisedCost - totalActualCost;
    				} else {
    					totalRemainingCost = totalOriginalCost - totalActualCost;
    				}
        			if ( projEstimate.getFormulaType().equals("Remaining Units")) {
        				if (totalRevisedCost > 0) {
        					totalETC = totalRevisedCost - totalActualCost;
        				} else {
        					totalETC = totalOriginalCost - totalActualCost;
        				}
        			} else if (projEstimate.getFormulaType().equals("BAC-EV")) {
        				if (totalRevisedCost > 0) {
        					totalETC = totalRevisedCost - totalEarnedCost;
        				} else {
        					totalETC = totalOriginalCost - totalEarnedCost;
        				}
        			} else if (projEstimate.getFormulaType().equals("(BAC-EV)/CPI")) { 
        				if (CPI == 0) {
        					totalETC = BAC_EV;
        				} else {
        					totalETC = BAC_EV / CPI;
        				}
        			} else if (projEstimate.getFormulaType().equals("New Estimate")) {
        				totalETC = totalNewEstimateCost;
        			}
        			
        			mainTotalOriginal1 = mainTotalOriginal1 + totalOriginalCost;
        			mainTotalRevised1 = mainTotalRevised1 + totalRevisedCost;
        			mainTotalActual1 = mainTotalActual1 + totalActualCost;
        			mainTotalETC1 = mainTotalETC1 + totalETC;
        			mainTotalEAC1 = mainTotalETC1 + mainTotalActual1;
        			
        			if (mainTotalRevised1 > 0) {
        				mainCompVarience1 = mainTotalEAC1 - mainTotalRevised1;
        			} else {
        				mainCompVarience1 = mainTotalEAC1 - mainTotalOriginal1;
        			}
        			
        			log.info("Estimate to Complete: " + mainTotalETC1);
        			log.info("Estimate At Completion: " + mainTotalEAC1);
        			log.info("Completion Variance: " + mainCompVarience1);
        			
            		if(subProjCostStmtDtlTO.getEarnedValue() != null) {
        				ernTotal =  ernTotal.add(subProjCostStmtDtlTO.getEarnedValue());
        			}
            		if (subProjCostStmtDtlTO.getOriginalCostBudget() != null) {
                        parseCostStatements(map, subProjCostStmtDtlTO.getOriginalCostBudget(),
                        		subProjCostStmtDtlTO.getOriginalCostBudget().getBudgetType(), subProjCostStmtDtlTO);
                    }
                    if (subProjCostStmtDtlTO.getRevisedCostBudget() != null) {
                        parseCostStatements(map, subProjCostStmtDtlTO.getRevisedCostBudget(),
                        		subProjCostStmtDtlTO.getRevisedCostBudget().getBudgetType(), subProjCostStmtDtlTO);
                    } else if (subProjCostStmtDtlTO.getOriginalCostBudget() != null) {
                        parseCostStatements(map, subProjCostStmtDtlTO.getOriginalCostBudget(), 2L,
                        		subProjCostStmtDtlTO);
                    }
                    if (subProjCostStmtDtlTO.getActualCostBudget() != null) {
                        parseCostStatements(map, subProjCostStmtDtlTO.getActualCostBudget(),
                        		subProjCostStmtDtlTO.getActualCostBudget().getBudgetType(), subProjCostStmtDtlTO);
                    }
                    if (subProjCostStmtDtlTO.getEstimateCompleteBudget() != null) {
                        parseCostStatements(map, subProjCostStmtDtlTO.getEstimateCompleteBudget(),
                        		subProjCostStmtDtlTO.getEstimateCompleteBudget().getBudgetType(),
                        		subProjCostStmtDtlTO);
                    }
            	} else {
            		for (ProjCostStmtDtlTO childProjCostStmtDtlTO : subProjCostStmtDtlTO.getProjCostStmtDtlTOs()) {
            			if(childProjCostStmtDtlTO.getEarnedValue() != null) {
            				ernTotal =  ernTotal.add(childProjCostStmtDtlTO.getEarnedValue());
            			}
            			
            			if(childProjCostStmtDtlTO.getEarnedValue() == null) {
        					totalEarnedCost = (double) 0;
        				} else {
        					totalEarnedCost = childProjCostStmtDtlTO.getEarnedValue().doubleValue();
        				}
        				
            			if (childProjCostStmtDtlTO.getActualCostBudget().getLabourCost() == null) {
            				childProjCostStmtDtlTO.getActualCostBudget().setLabourCost(new BigDecimal(0));
            			}
            			if (childProjCostStmtDtlTO.getActualCostBudget().getMaterialCost() == null) {
            				childProjCostStmtDtlTO.getActualCostBudget().setMaterialCost(new BigDecimal(0));
            			}
            			if (childProjCostStmtDtlTO.getActualCostBudget().getPlantCost() == null) {
            				childProjCostStmtDtlTO.getActualCostBudget().setPlantCost(new BigDecimal(0));
            			}
            			if (childProjCostStmtDtlTO.getActualCostBudget().getOtherCost() == null) {
            				childProjCostStmtDtlTO.getActualCostBudget().setOtherCost(new BigDecimal(0));
            			}
            			if (childProjCostStmtDtlTO.getEstimateCompleteBudget().getLabourCost() == null) {
            				childProjCostStmtDtlTO.getEstimateCompleteBudget().setLabourCost(new BigDecimal(0));
            			}
            			if (childProjCostStmtDtlTO.getEstimateCompleteBudget().getMaterialCost() == null) {
            				childProjCostStmtDtlTO.getEstimateCompleteBudget().setMaterialCost(new BigDecimal(0));
            			}
            			if (childProjCostStmtDtlTO.getEstimateCompleteBudget().getPlantCost() == null) {
            				childProjCostStmtDtlTO.getEstimateCompleteBudget().setPlantCost(new BigDecimal(0));
            			}
            			if (childProjCostStmtDtlTO.getEstimateCompleteBudget().getOtherCost() == null) {
            				childProjCostStmtDtlTO.getEstimateCompleteBudget().setOtherCost(new BigDecimal(0));
            			}
            			
        				totalOriginalCost = childProjCostStmtDtlTO.getOriginalCostBudget().getLabourCost().doubleValue()
        						+ (childProjCostStmtDtlTO.getOriginalCostBudget().getMaterialCost().doubleValue() 
        						+ (childProjCostStmtDtlTO.getOriginalCostBudget().getPlantCost().doubleValue() 
        						+ (childProjCostStmtDtlTO.getOriginalCostBudget().getOtherCost().doubleValue())));
        				totalRevisedCost = childProjCostStmtDtlTO.getRevisedCostBudget().getLabourCost().doubleValue() 
        						+ (childProjCostStmtDtlTO.getRevisedCostBudget().getMaterialCost().doubleValue() 
        						+ (childProjCostStmtDtlTO.getRevisedCostBudget().getPlantCost().doubleValue() 
        						+ (childProjCostStmtDtlTO.getRevisedCostBudget().getOtherCost().doubleValue())));
        				totalActualCost = childProjCostStmtDtlTO.getActualCostBudget().getLabourCost().doubleValue() 
        						+ (childProjCostStmtDtlTO.getActualCostBudget().getMaterialCost().doubleValue() 
        						+ (childProjCostStmtDtlTO.getActualCostBudget().getPlantCost().doubleValue() 
        						+ (childProjCostStmtDtlTO.getActualCostBudget().getOtherCost().doubleValue() )));
        				totalNewEstimateCost = childProjCostStmtDtlTO.getEstimateCompleteBudget().getLabourCost().doubleValue()
        						+ (childProjCostStmtDtlTO.getEstimateCompleteBudget().getMaterialCost().doubleValue() 
        						+ (childProjCostStmtDtlTO.getEstimateCompleteBudget().getPlantCost().doubleValue() 
        						+ (childProjCostStmtDtlTO.getEstimateCompleteBudget().getOtherCost().doubleValue() )));
        				
        				if (totalRevisedCost > 0) {
        					BAC_EV = totalRevisedCost- totalEarnedCost;
        				} else {
        					BAC_EV = totalOriginalCost - totalEarnedCost;
        				}
        				
        				if (totalEarnedCost == 0 || totalActualCost == 0 ) {
        					CPI = (double) 0;
        				} else {
        					CPI = totalEarnedCost / totalActualCost;
        				}
        				if (totalRevisedCost > 0) {
        					totalRemainingCost = totalRevisedCost - totalActualCost;
        				} else {
        					totalRemainingCost = totalOriginalCost - totalActualCost;
        				}
            			if ( projEstimate.getFormulaType().equals("Remaining Units")) {
            				if (totalRevisedCost > 0) {
            					totalETC = totalRevisedCost - totalActualCost;
            				} else {
            					totalETC = totalOriginalCost - totalActualCost;
            				}
            			} else if (projEstimate.getFormulaType().equals("BAC-EV")) {
            				if (totalRevisedCost > 0) {
            					totalETC = totalRevisedCost - totalEarnedCost;
            				} else {
            					totalETC = totalOriginalCost - totalEarnedCost;
            				}
            			} else if (projEstimate.getFormulaType().equals("(BAC-EV)/CPI")) { 
            				if (CPI == 0) {
            					totalETC = BAC_EV;
            				} else {
            					totalETC = BAC_EV / CPI;
            				}
            			} else if (projEstimate.getFormulaType().equals("New Estimate")) {
            				totalETC = totalNewEstimateCost;
            			}
            			
            			mainTotalOriginal1 = mainTotalOriginal1 + totalOriginalCost;
            			mainTotalRevised1 = mainTotalRevised1 + totalRevisedCost;
            			mainTotalActual1 = mainTotalActual1 + totalActualCost;
            			mainTotalETC1 = mainTotalETC1 + totalETC;
            			mainTotalEAC1 = mainTotalETC1 + mainTotalActual1;
            			
            			if (mainTotalRevised1 > 0) {
            				mainCompVarience1 = mainTotalEAC1 - mainTotalRevised1;
            			} else {
            				mainCompVarience1 = mainTotalEAC1 - mainTotalOriginal1;
            			}
            			
            			log.info("Estimate to Complete: " + mainTotalETC1);
            			log.info("Estimate At Completion: " + mainTotalEAC1);
            			log.info("Completion Variance: " + mainCompVarience1);
            			
	                    if (childProjCostStmtDtlTO.getOriginalCostBudget() != null) {
	                        parseCostStatements(map, childProjCostStmtDtlTO.getOriginalCostBudget(),
	                                childProjCostStmtDtlTO.getOriginalCostBudget().getBudgetType(), childProjCostStmtDtlTO);
	                    }
	                    if (childProjCostStmtDtlTO.getRevisedCostBudget() != null) {
	                        parseCostStatements(map, childProjCostStmtDtlTO.getRevisedCostBudget(),
	                                childProjCostStmtDtlTO.getRevisedCostBudget().getBudgetType(), childProjCostStmtDtlTO);
	                    } else if (childProjCostStmtDtlTO.getOriginalCostBudget() != null) {
	                        parseCostStatements(map, childProjCostStmtDtlTO.getOriginalCostBudget(), 2L,
	                                childProjCostStmtDtlTO);
	                    }
	                    if (childProjCostStmtDtlTO.getActualCostBudget() != null) {
	                        parseCostStatements(map, childProjCostStmtDtlTO.getActualCostBudget(),
	                                childProjCostStmtDtlTO.getActualCostBudget().getBudgetType(), childProjCostStmtDtlTO);
	                    }
	                    if (childProjCostStmtDtlTO.getEstimateCompleteBudget() != null) {
	                        parseCostStatements(map, childProjCostStmtDtlTO.getEstimateCompleteBudget(),
	                                childProjCostStmtDtlTO.getEstimateCompleteBudget().getBudgetType(),
	                                childProjCostStmtDtlTO);
	                    }
	                }
            	}
            }
        }
        
        ProjCostStatementsSummaryTO projCostStatementsSummaryTO = null;
        Long projId = projCostStatementsGetReq.getProjId();
        
        log.info("MAP " + map);
        log.info("MAP.toString() " + map.toString());
        log.info("MAP.size() " + map.size());
        log.info("ernTotal " + ernTotal);
      
        for (Entry<String, List<ProjCostSummaryBudgetTO>> entry : map.entrySet()) {
            projCostStatementsSummaryTO = new ProjCostStatementsSummaryTO();
            projCostStatementsSummaryTO.setCatgType(entry.getKey());
            projCostStatementsSummaryTO.setEstimateType(projEstimate.getFormulaType());
            projCostStatementsSummaryTO.setProjId(projId);
            projCostStatementsSummaryTO.setEarnedValue(ernTotal);
            projCostStatementsSummaryTO.setEstimateToComplete(mainTotalETC1);
            projCostStatementsSummaryTO.setEstimateAtCompletion(mainTotalEAC1);
            projCostStatementsSummaryTO.setCompletionVariance(mainCompVarience1);
            for (ProjCostSummaryBudgetTO value : entry.getValue()) {
            	if (value.getBudgetType() != null) {
            		if (value.getBudgetType() == 1) {
                        projCostStatementsSummaryTO.setOriginalCostBudget(value);
                    } else if (value.getBudgetType() == 2) {
                        projCostStatementsSummaryTO.setRevisedCostBudget(value);
                    } else if (value.getBudgetType() == 3) {
                        projCostStatementsSummaryTO.setActualCostBudget(value);
                    } else if (value.getBudgetType() == 4) {
                        projCostStatementsSummaryTO.setEstimateCompleteBudget(value);
                    }
            	}
            }

            projCostStatementsSummaryResp.getProjCostStatementsSummaryTOs().add(projCostStatementsSummaryTO);
        }

        return projCostStatementsSummaryResp;
    }

	/*
	 * private Object recurvise(Object a) { Object returnValue;
	 * 
	 * if (a.hasChildren()) { for(int i=0; i<a.children.length; i++) {
	 * returnValue.add(recursive(a.children[i])); } } else { /// } return
	 * returnvalue; }
	 */
	@Override
	public ProjCostStatementsSummaryResp getMultiProjCostStatusSummary(
			ProjCostStatementsGetReq projCostStatementsGetReq) {
		System.out.println("getMultiProjCostStatusSummary -> projId " +  projCostStatementsGetReq.getProjId());
		System.out.println("getMultiProjCostStatusSummary -> projIds " +  projCostStatementsGetReq.getProjIds());
		List<Long> projIds = projCostStatementsGetReq.getProjIds();
		ProjCostStatementsSummaryResp projCostStatementsSummaryResp = new ProjCostStatementsSummaryResp();
		for (Long projId1 : projIds) {
			ProjCostStatementsGetReq costStatusSummary = new ProjCostStatementsGetReq();
			costStatusSummary.setProjId(projId1);
			List<ProjCostStatementsSummaryTO> costStatementsSummaryTOs = getProjCostStatusSummary(
					costStatusSummary).getProjCostStatementsSummaryTOs();
			projCostStatementsSummaryResp.getProjCostStatementsSummaryTOs().addAll(costStatementsSummaryTOs);
		}

		return projCostStatementsSummaryResp;
	}

	public ProjNoteBookResp getProjNoteBook(ProjNoteBookGetReq projNoteBookGetReq) {
		ProjNoteBookResp projNoteBookResp = new ProjNoteBookResp();
		List<ProjNoteBookEntity> projNoteBookEntities = projNoteBookRepository
				.findProjNoteBook(projNoteBookGetReq.getProjId(), projNoteBookGetReq.getStatus());
		for (ProjNoteBookEntity projNoteBookEntity : projNoteBookEntities) {
			projNoteBookResp.getProjNoteBookTOs().add(ProjNoteBookHandler.convertEntityToPOJO(projNoteBookEntity));
		}
		return projNoteBookResp;
	}

	public void saveProjNoteBook(ProjNoteBookSaveReq projNoteBookSaveReq) {
		List<ProjNoteBookTO> projNoteBookTOs = projNoteBookSaveReq.getProjNoteBookTOs();
		List<ProjNoteBookEntity> entites = ProjNoteBookHandler.convertPOJOToEntity(projNoteBookTOs, epsProjRepository);
		projNoteBookRepository.save(entites);
	}

	private static final Logger log = LoggerFactory.getLogger(ProjSettingsServiceImpl.class);

	public void saveProjectDefaultSettinges(ProjectDefaultSaveReq projectDefaultSaveReq) {

		log.info("Saving default settings for " + projectDefaultSaveReq.getProjIds().size());

		List<ProgressClaimPeriodCycleEntity> projProgressClaimePeriodEntities = new ArrayList<>();
		ProgressClaimPeriodCycleEntity projProgressClaimePeriodEntity = null;
		List<ProgressClaimPeriodCycleEntity> exProjProgressClaimePeriodEntities = projProgressClaimePeriodRepository
				.findDefaultProjProgressClaimePeriods();

		List<LeaveNormalTimeEntity> projLeaveRequestEntities = new ArrayList<>();
		LeaveNormalTimeEntity projLeaveRequestEntity = null;
		List<LeaveNormalTimeEntity> exProjLeaveRequestEntities = leaveRequestRepository.findDefalutProjLeaveApproval();

		List<ProjectReportsEntity> projectReportsEntities = new ArrayList<>();
		ProjectReportsEntity projectReportsEntity = null;
		List<ProjectReportsEntity> exProjectReportsEntities = projReportsRepository.findAllProjReports();

		List<AttendanceNormalTimeEntity> projAttendenceEntities = new ArrayList<>();
		AttendanceNormalTimeEntity projAttendenceEntity = null;
		List<AttendanceNormalTimeEntity> exprojAttendenceEntites = projAttendenceRepository.findDefaultProjAttendence();

		List<WorkDairyNormalTimeEntity> projWorkDairyMstrEntites = new ArrayList<>();
		WorkDairyNormalTimeEntity projWorkDairyMstrEntity = null;
		List<WorkDairyNormalTimeEntity> exProjWorkDairyMstrEntites = projWorkDairyRepository.findDefaultProjWorkDairy();
		
		//1. requirement sch of estimate 
		List<SchofEstimateNormalTimeEntity> projSchofEstimates = new ArrayList<>();
		SchofEstimateNormalTimeEntity schofEstimateNormalTimeEntity = null;
		List<SchofEstimateNormalTimeEntity> exProjSchofEstimate = schofEstimatesRepository.findDefaultProjSchofEstimate();
		System.out.println("SchofEstimateNormalTimeEntity size is: "+exProjSchofEstimate.size());
		
		//2. requirement sch of rates
		List<SchofRatesNormalTimeEntity> projSchofRates = new ArrayList<>();
		SchofRatesNormalTimeEntity projSchofRateNormalTimeEntity = null;
		List<SchofRatesNormalTimeEntity> exProjSchofRate = schofRatesRepository.findDefaultProjSchofRates();
		System.out.println("SchofRatesNormalTimeEntity size is: "+exProjSchofRate.size());
		
		//3. requirement resource budget
		List<ResourceBudgetNormalTimeEntity> projResBudget = new ArrayList<>();
		ResourceBudgetNormalTimeEntity projResourceBudgetNormalTimeEntity = null;
		List<ResourceBudgetNormalTimeEntity> exProjResBudget = resourceBudgetRepository.findDefaultProjResBudget();
		System.out.println("ResourceBudgetNormalTimeEntity size is: "+exProjResBudget.size());
				
		List<TimesheetNormalTimeEntity> projTimeSheetEntites = new ArrayList<>();
		TimesheetNormalTimeEntity projTimeSheetEntity = null;
		List<TimesheetNormalTimeEntity> exProjTimeSheetEntites = projTimeSheetRepository.findDefaultProjTimeSheet();

		ProjTimeSheetWeekDtlEntity projTimeSheetWeekDtlEntity = null;
		List<ProjTimeSheetWeekDtlEntity> projTimeSheetWeekDtlEntities = new ArrayList<>();
		ProjTimeSheetWeekDtlEntity exProjTimeSheetWeekDtlEntity = projTimeSheetWeekRepository
				.findDefaultProjTimeSheetWeek();

		List<ProcurementNormalTimeEntity> projProcurementEntites = new ArrayList<>();
		ProcurementNormalTimeEntity projProcurementEntity = null;
		List<ProcurementNormalTimeEntity> exProjProcurementEntites = projProcureRepository.findDefaultProjProcure();

		List<EmpTransNormalTimeEntity> projEmpTransEntites = new ArrayList<>();
		EmpTransNormalTimeEntity projEmpTransEntity = null;
		List<EmpTransNormalTimeEntity> exProjEmpTransEntites = projEmpTransRepository.findDefaultProjEmpTrans();

		List<PlantTransNormalTimeEntity> projPlantTransEntites = new ArrayList<>();
		PlantTransNormalTimeEntity projPlantTransEntity = null;
		List<PlantTransNormalTimeEntity> exProjPlantTransEntites = projPlantTransRepository.findDefaultProjPlantTrans();

		List<MaterialTransNormalTimeEntity> projMaterialTransEntites = new ArrayList<>();
		MaterialTransNormalTimeEntity projMaterialTransEntity = null;
		List<MaterialTransNormalTimeEntity> exProjMaterialTransEntites = projMaterialTransRepository
				.findDefaultProjMaterialTrans();

		List<ProjEstimateEntity> projEstimateEntities = new ArrayList<>();
		ProjEstimateEntity projEstimateEntity = null;
		List<ProjEstimateEntity> exProjEstimateEntities = projEstimateRepository.findDefaultProjEstimate();

		List<ProjPerformenceThresholdEntity> projPerformenceThresholdEntites = new ArrayList<>();
		ProjPerformenceThresholdEntity projPerformenceThresholdEntity = null;
		List<ProjPerformenceThresholdEntity> exProjPerformenceThresholdEntites = projPerformenceThresholdRepository
				.findDefaultProjPerformenceThresholds();	
		
		//1. requirement of changeorder  
		List<ChangeOrderNormalTimeEntity> projCONEntities = new ArrayList<>();
		ChangeOrderNormalTimeEntity changeOrderNormalTimeEntity = null;
		List<ChangeOrderNormalTimeEntity> exProjCONEntities = changeOrderNormaltimeEntityRepository.findDefaultCON();	

		
		List<ProgressClaimNormalTimeEntity> projProgressClaimEntities = new ArrayList<>();
		ProgressClaimNormalTimeEntity projProgressClaimEntity = null;
		List<ProgressClaimNormalTimeEntity> exProjProgressClaimEntities = projProgressClaimRepository
				.findDefaultProjProgressClaim();
		
		List<ProjGeneralMstrEntity> projGeneralMstrEntities = new ArrayList<>();
		ProjGeneralMstrEntity projGeneralMstrEntity = null;
		ProjGeneralMstrEntity exprojGeneralMstrEntity = projGeneralRepository.findProjGeneralsDefault();

		for (Long projId : projectDefaultSaveReq.getProjIds()) {
			log.info("Saving default project for -- " + projId);
			projGeneralMstrEntity = new ProjGeneralMstrEntity();
			BeanUtils.copyProperties(exprojGeneralMstrEntity, projGeneralMstrEntity);
			projGeneralMstrEntity.setId(null);

			ProjMstrEntity projEntity = epsProjRepository.findOne(projId);
			projGeneralMstrEntity.setProjMstrEntity(projEntity);

			projGeneralMstrEntity.setIsLatest("Y");
			projGeneralMstrEntity.setIsDefault("Y");
			GlobalCalEntity globalCalEntity = globalCalendarRepository
					.getDefaultGlobalCalendar(AppUserUtils.getClientId());
			projGeneralMstrEntity.setGlobalCalEntity(globalCalEntity);
			projGeneralMstrEntities.add(projGeneralMstrEntity);

			for (AttendanceNormalTimeEntity entity : exprojAttendenceEntites) {
				projAttendenceEntity = new AttendanceNormalTimeEntity();

				BeanUtils.copyProperties(entity, projAttendenceEntity);
				projAttendenceEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
				if (null != projMstrEntity) {
					projAttendenceEntity.setProjId(projMstrEntity);
				}
				projAttendenceEntity.setIsDefault("Y");
				projAttendenceEntities.add(projAttendenceEntity);
			}

			for (WorkDairyNormalTimeEntity exProjWorkDairyMstrEntity : exProjWorkDairyMstrEntites) {
				projWorkDairyMstrEntity = new WorkDairyNormalTimeEntity();
				BeanUtils.copyProperties(exProjWorkDairyMstrEntity, projWorkDairyMstrEntity);
				projWorkDairyMstrEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
				if (null != projMstrEntity) {
					projWorkDairyMstrEntity.setProjId(projMstrEntity);
				}
				projWorkDairyMstrEntity.setIsDefault("Y");
				projWorkDairyMstrEntites.add(projWorkDairyMstrEntity);
			}
			
			//1. requirement sch of estimate 	
			for(SchofEstimateNormalTimeEntity schofEstimateEntity : exProjSchofEstimate) {
				schofEstimateNormalTimeEntity = new SchofEstimateNormalTimeEntity();
				BeanUtils.copyProperties(schofEstimateEntity, schofEstimateNormalTimeEntity);
				schofEstimateNormalTimeEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
				if(null != projMstrEntity) {
					schofEstimateNormalTimeEntity.setProjId(projMstrEntity);
				}
				schofEstimateNormalTimeEntity.setIsDefault("Y");
				projSchofEstimates.add(schofEstimateNormalTimeEntity);
			}
			
			//2. requirement sch of rates
			for(SchofRatesNormalTimeEntity schofRateEntity: exProjSchofRate) {
				projSchofRateNormalTimeEntity = new SchofRatesNormalTimeEntity();
				BeanUtils.copyProperties(schofRateEntity, projSchofRateNormalTimeEntity);
				projSchofRateNormalTimeEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
				if( null != projMstrEntity) {
					projSchofRateNormalTimeEntity.setProjId(projMstrEntity);
				}
				projSchofRateNormalTimeEntity.setIsDefault("Y");
				projSchofRates.add(projSchofRateNormalTimeEntity);
			}
			
			//3.requiremnt for resource budget
			for(ResourceBudgetNormalTimeEntity resourcebudget: exProjResBudget) {
				projResourceBudgetNormalTimeEntity = new ResourceBudgetNormalTimeEntity();
				BeanUtils.copyProperties(resourcebudget, projResourceBudgetNormalTimeEntity);
				projResourceBudgetNormalTimeEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
				if( null != projMstrEntity) {
					projResourceBudgetNormalTimeEntity.setProjId(projMstrEntity);
				}
				projResourceBudgetNormalTimeEntity.setIsDefault("Y");
				projResBudget.add(projResourceBudgetNormalTimeEntity);
			}
			
			for (TimesheetNormalTimeEntity exProjTimeSheetEntity : exProjTimeSheetEntites) {
				projTimeSheetEntity = new TimesheetNormalTimeEntity();
				BeanUtils.copyProperties(exProjTimeSheetEntity, projTimeSheetEntity);
				projTimeSheetEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);

				if (null != projMstrEntity) {
					projTimeSheetEntity.setProjId(projMstrEntity);
				}
				projTimeSheetEntity.setIsDefault("Y");
				projTimeSheetEntites.add(projTimeSheetEntity);
			}

			projTimeSheetWeekDtlEntity = new ProjTimeSheetWeekDtlEntity();
			BeanUtils.copyProperties(exProjTimeSheetWeekDtlEntity, projTimeSheetWeekDtlEntity);
			projTimeSheetWeekDtlEntity.setId(null);
			projTimeSheetWeekDtlEntity.setIsLatest("Y");
			projTimeSheetWeekDtlEntity.setProjMstrEntity(projEntity);
			projTimeSheetWeekDtlEntity.setIsDefault("Y");
			projTimeSheetWeekDtlEntities.add(projTimeSheetWeekDtlEntity);

			for (ProcurementNormalTimeEntity exProjProcurementEntity : exProjProcurementEntites) {
				projProcurementEntity = new ProcurementNormalTimeEntity();
				BeanUtils.copyProperties(exProjProcurementEntity, projProcurementEntity);
				projProcurementEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);

				if (null != projMstrEntity) {
					projProcurementEntity.setProjId(projMstrEntity);
				}
				projProcurementEntity.setIsDefault("Y");
				projProcurementEntites.add(projProcurementEntity);
			}

			for (EmpTransNormalTimeEntity exProjEmpTransEntity : exProjEmpTransEntites) {
				projEmpTransEntity = new EmpTransNormalTimeEntity();
				BeanUtils.copyProperties(exProjEmpTransEntity, projEmpTransEntity);
				projEmpTransEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
				if (null != projMstrEntity) {
					projEmpTransEntity.setProjId(projMstrEntity);
				}
				projEmpTransEntity.setIsDefault("Y");
				projEmpTransEntites.add(projEmpTransEntity);
			}

			for (PlantTransNormalTimeEntity exProjPlantTransEntity : exProjPlantTransEntites) {
				projPlantTransEntity = new PlantTransNormalTimeEntity();
				BeanUtils.copyProperties(exProjPlantTransEntity, projPlantTransEntity);
				projPlantTransEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
				if (null != projMstrEntity) {
					projPlantTransEntity.setProjId(projMstrEntity);
				}
				projPlantTransEntity.setIsDefault("Y");
				projPlantTransEntites.add(projPlantTransEntity);
			}

			for (MaterialTransNormalTimeEntity exProjMaterialTransEntity : exProjMaterialTransEntites) {
				projMaterialTransEntity = new MaterialTransNormalTimeEntity();
				BeanUtils.copyProperties(exProjMaterialTransEntity, projMaterialTransEntity);
				projMaterialTransEntity.setId(null);

				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
				if (null != projMstrEntity) {
					projMaterialTransEntity.setProjId(projMstrEntity);
				}
				projMaterialTransEntity.setIsDefault("Y");
				projMaterialTransEntites.add(projMaterialTransEntity);
			}

			for (ProjEstimateEntity estimateMstrEntity : exProjEstimateEntities) {
				projEstimateEntity = new ProjEstimateEntity();
				projEstimateEntity.setProjMstrEntity(projEntity);
				projEstimateEntity.setFormulaType(estimateMstrEntity.getFormulaType());
				projEstimateEntity.setResourceType(estimateMstrEntity.getResourceType());
				projEstimateEntities.add(projEstimateEntity);
			}

			for (ProjPerformenceThresholdEntity exProjPerformenceThresholdEntity : exProjPerformenceThresholdEntites) {
				projPerformenceThresholdEntity = new ProjPerformenceThresholdEntity();
				BeanUtils.copyProperties(exProjPerformenceThresholdEntity, projPerformenceThresholdEntity);
				projPerformenceThresholdEntity.setId(null);
				projPerformenceThresholdEntity.setProjMstrEntity(projEntity);
				projPerformenceThresholdEntity.setIsDefault("Y");
				projPerformenceThresholdEntites.add(projPerformenceThresholdEntity);
			}
			
			for( ChangeOrderNormalTimeEntity exProjCONEntity : exProjCONEntities) {
				changeOrderNormalTimeEntity = new ChangeOrderNormalTimeEntity();
				BeanUtils.copyProperties(exProjCONEntity, changeOrderNormalTimeEntity);
				changeOrderNormalTimeEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
				if(null != projMstrEntity) {
					changeOrderNormalTimeEntity.setProjId(projMstrEntity);
				}
				changeOrderNormalTimeEntity.setIsDefault("Y");
				projCONEntities.add(changeOrderNormalTimeEntity);
				
			}
		
			
			
			for (ProgressClaimNormalTimeEntity exProjProgressClaimEntity : exProjProgressClaimEntities) {
				projProgressClaimEntity = new ProgressClaimNormalTimeEntity();
				BeanUtils.copyProperties(exProjProgressClaimEntity, projProgressClaimEntity);
				projProgressClaimEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);

				if (null != projMstrEntity) {
					projProgressClaimEntity.setProjId(projMstrEntity);
				}
				projProgressClaimEntity.setIsDefault("Y");
				projProgressClaimEntities.add(projProgressClaimEntity);
			}

			for (ProjectReportsEntity exProjectReportsEntity : exProjectReportsEntities) {
				projectReportsEntity = new ProjectReportsEntity();
				BeanUtils.copyProperties(exProjectReportsEntity, projectReportsEntity);
				projectReportsEntity.setProjMstrEntity(projEntity);
				projectReportsEntity.setWeek(exProjectReportsEntity.getWeek());
				projectReportsEntity.setMonth(exProjectReportsEntity.getMonth());
				projectReportsEntity.setYear(exProjectReportsEntity.getYear());
				projectReportsEntity.setFirstHalf(exProjectReportsEntity.getFirstHalf());
				projectReportsEntity.setFirstQuarter(exProjectReportsEntity.getFirstQuarter());
				projectReportsEntities.add(projectReportsEntity);
			}

			for (LeaveNormalTimeEntity exProjLeaveRequestEntity : exProjLeaveRequestEntities) {
				projLeaveRequestEntity = new LeaveNormalTimeEntity();
				BeanUtils.copyProperties(exProjLeaveRequestEntity, projLeaveRequestEntity);
				projLeaveRequestEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
				if (null != projMstrEntity) {
					projLeaveRequestEntity.setProjId(projMstrEntity);
				}
				projLeaveRequestEntity.setIsDefault("Y");
				projLeaveRequestEntities.add(projLeaveRequestEntity);
			}

			for (ProgressClaimPeriodCycleEntity exProjProgressClaimePeriodEntity : exProjProgressClaimePeriodEntities) {
				projProgressClaimePeriodEntity = new ProgressClaimPeriodCycleEntity();
				BeanUtils.copyProperties(exProjProgressClaimePeriodEntity, projProgressClaimePeriodEntity);
				projProgressClaimePeriodEntity.setId(null);
				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);

				if (null != projMstrEntity) {
					projProgressClaimePeriodEntity.setProjId(projMstrEntity);
				}
				projProgressClaimePeriodEntity.setIsDefault("Y");
				projProgressClaimePeriodEntities.add(projProgressClaimePeriodEntity);
			}
		}
		projGeneralRepository.save(projGeneralMstrEntities);
		projAttendenceRepository.save(projAttendenceEntities);
		projWorkDairyRepository.save(projWorkDairyMstrEntites);
		projTimeSheetRepository.save(projTimeSheetEntites);
		projTimeSheetWeekRepository.save(projTimeSheetWeekDtlEntities);
		projProcureRepository.save(projProcurementEntites);
		projEmpTransRepository.save(projEmpTransEntites);
		projPlantTransRepository.save(projPlantTransEntites);
		projMaterialTransRepository.save(projMaterialTransEntites);
		projEstimateRepository.save(projEstimateEntities);
		projPerformenceThresholdRepository.save(projPerformenceThresholdEntites);
		projProgressClaimRepository.save(projProgressClaimEntities);
		projReportsRepository.save(projectReportsEntities);
		leaveRequestRepository.save(projLeaveRequestEntities);
		projProgressClaimePeriodRepository.save(projProgressClaimePeriodEntities);
		schofEstimatesRepository.save(projSchofEstimates); // requirement 1
		schofRatesRepository.save(projSchofRates); // requirement 2
		resourceBudgetRepository.save(projResBudget); // requirement 3
		changeOrderNormaltimeEntityRepository.save(projCONEntities);//req1
	}

	public ProjGenCurrencyResp getProjGeneralsCurrencys(ProjGeneralsGetReq projGeneralsGetReq) {
		ProjGenCurrencyResp projGenCurrencyResp = new ProjGenCurrencyResp();
		List<Object[]> projGenCurrency = projGeneralRepository.findProjGeneralsCurrencys(projGeneralsGetReq.getProjId(),
				projGeneralsGetReq.getStatus());

		LabelKeyTO labelKeyTO = new LabelKeyTO();
		for (Object[] object : projGenCurrency) {
			labelKeyTO.setId(Long.valueOf(object[0].toString().trim()));
			String currency = (((String) (object[1])) == null) ? "" : ((String) (object[1])).trim();
			labelKeyTO.setName(currency);
			labelKeyTO.setCode(currency);
			labelKeyTO.getDisplayNamesMap().put(CommonConstants.CURRENCY_ID, currency);
			projGenCurrencyResp.setLabelKeyTO(labelKeyTO);
		}
		return projGenCurrencyResp;
	}

	public ProjPerformenceThresholdResp getProjPerformenceThreshold(
			ProjPerformenceThresholdGetReq projPerformenceThresholdGetReq) {
		log.info("getProjPerformenceThreshold ");
		log.info("getProjPerformenceThreshold getProjId " + projPerformenceThresholdGetReq.getProjId());
		log.info("getProjPerformenceThreshold getProjIds " + projPerformenceThresholdGetReq.getProjIds());
		
		ProjPerformenceThresholdResp projPerformenceThresholdResp = new ProjPerformenceThresholdResp();
		List<ProjPerformenceThresholdEntity> projPerformenceThresholdEnties = null;
		if (projPerformenceThresholdGetReq.getProjIds().isEmpty()) {
			projPerformenceThresholdEnties = projPerformenceThresholdRepository.findProjPerformenceThresholds(
					projPerformenceThresholdGetReq.getProjId(), projPerformenceThresholdGetReq.getStatus());
		} else {
			projPerformenceThresholdEnties = projPerformenceThresholdRepository.findProjPerformenceThresholds(
					projPerformenceThresholdGetReq.getProjIds(), projPerformenceThresholdGetReq.getStatus());
		}

		for (ProjPerformenceThresholdEntity projPerformenceThresholdEntity : projPerformenceThresholdEnties) {
			projPerformenceThresholdResp.getProjPerformenceThresholdTOs()
					.add(ProjPerformenceThresholdHandler.convertEntityToPOJO(projPerformenceThresholdEntity));
		}
		return projPerformenceThresholdResp;
	}

	public void saveProjPerformenceThreshold(ProjPerformenceThresholdSaveReq projPerformenceThresholdSaveReq) {
		projPerformenceThresholdRepository.save(ProjPerformenceThresholdHandler.convertPOJOsToEntitys(
				projPerformenceThresholdSaveReq.getProjPerformenceThresholdTOs(), epsProjRepository));

	}

	public List<String> getpayPeriodCycles() {
		List<String> payPeriodCycles = new ArrayList<>();
		for (PayPeriodCycles payPeriodCycle : PayPeriodCycles.values()) {
			payPeriodCycles.add(payPeriodCycle.getValue());
		}
		return payPeriodCycles;
	}

	public List<String> getMonths(List<FinancePeriodPayCyclesTO> financePeriodPayCyclesTOs) {
		List<String> months = new ArrayList<>();
		for (FinancePeriodPayCyclesTO financePeriodPayCyclesTO : financePeriodPayCyclesTOs) {
			months.add(financePeriodPayCyclesTO.getDesc());
		}
		return months;
	}

	public ProjPayRollCycleResp getprojPayRollCycle(ProjPayRollCycleGetReq projPayRollCycleGetReq) {

		ProjPayRollCycleResp projPayRollCycleResp = new ProjPayRollCycleResp();
		List<ProjPayRollCycleEntity> projPayRollCycleEnties = projPayRollCycleRepository
				.findProjPayRollCycle(projPayRollCycleGetReq.getProjId(), projPayRollCycleGetReq.getStatus());

		for (ProjPayRollCycleEntity projPayRollCycleEntity : projPayRollCycleEnties) {
			projPayRollCycleResp.getProjPayRollCycleTOs()
					.add(ProjPayRollCycleHandler.convertEntityToPOJO(projPayRollCycleEntity));
		}
		return projPayRollCycleResp;
	}

	public ProjLeaveRequestResp getProjLeaveRequest(ProjLeaveRequestGetReq projLeaveRequestGetReq) {
		ProjLeaveRequestResp projLeaveRequestResp = new ProjLeaveRequestResp();
		List<LeaveNormalTimeEntity> projLeaveRequestEntities = leaveRequestRepository
				.findProjLeaveApproval(projLeaveRequestGetReq.getProjId(), projLeaveRequestGetReq.getStatus());
		for (LeaveNormalTimeEntity projLeaveRequestEntity : projLeaveRequestEntities) {
			projLeaveRequestResp.getProjLeaveRequestTOs()
					.add(ProjLeaveRequestHandler.convertEntityToPOJO(projLeaveRequestEntity));

		}
		return projLeaveRequestResp;
	}

	public ProjLeaveRequestResp saveProjLeaveRequest(ProjLeaveRequestSaveReq projLeaveRequestSaveReq) {
		ProjLeaveRequestResp projLeaveRequestResp = new ProjLeaveRequestResp();
		List<LeaveNormalTimeEntity> savedLeaveItems = leaveRequestRepository.save(ProjLeaveRequestHandler
				.convertPOJOToEntity(projLeaveRequestSaveReq.getProjLeaveRequestTOs(), epsProjRepository));
		for (LeaveNormalTimeEntity projLeaveRequestEntity : savedLeaveItems) {
			projLeaveRequestResp.getProjLeaveRequestTOs()
					.add(ProjLeaveRequestHandler.convertEntityToPOJO(projLeaveRequestEntity));
		}
		return projLeaveRequestResp;
	}

	public void saveProjPerfomanceDefaultSettings(ProjPerfamanceDefaultSaveReq projPerfamanceDefaultSaveReq) {

		projPerformenceThresholdRepository.deactivateProjPerformenceThreshold(projPerfamanceDefaultSaveReq.getProjId(),
				projPerfamanceDefaultSaveReq.getStatus());

		List<ProjPerformenceThresholdEntity> projPerformenceThresholdEntites = new ArrayList<>();
		ProjPerformenceThresholdEntity projPerformenceThresholdEntity = null;
		List<ProjPerformenceThresholdEntity> exProjPerformenceThresholdEntites = projPerformenceThresholdRepository
				.findDefaultProjPerformenceThresholds();

		for (ProjPerformenceThresholdEntity exProjPerformenceThresholdEntity : exProjPerformenceThresholdEntites) {
			projPerformenceThresholdEntity = new ProjPerformenceThresholdEntity();
			BeanUtils.copyProperties(exProjPerformenceThresholdEntity, projPerformenceThresholdEntity);
			projPerformenceThresholdEntity.setId(null);
			if (CommonUtil.isNonBlankLong(projPerfamanceDefaultSaveReq.getProjId())) {
				ProjMstrEntity projEntity = epsProjRepository.findOne(projPerfamanceDefaultSaveReq.getProjId());
				projPerformenceThresholdEntity.setProjMstrEntity(projEntity);
			}
			projPerformenceThresholdEntites.add(projPerformenceThresholdEntity);
		}

		projPerformenceThresholdRepository.save(projPerformenceThresholdEntites);

	}

	public void deleteProjPerformenceThreshold(ProjPerfomanceDelReq projPerfomanceDelReq) {
		projPerformenceThresholdRepository.deactivateProjPerformenceThreshold(projPerfomanceDelReq.getProjId(),
				projPerfomanceDelReq.getStatus());
	}

	public void saveProjLeaveApproval(ProjLeaveApprSaveReq projLeaveApprSaveReq) {

		if (CommonUtil.objectNotNull(projLeaveApprSaveReq.getProjLeaveApprTOs())) {
			LeaveAddtionalTimeApprEntity projLeaveApprEntity = projLeaveApprRepository.findLatestApproval();
			if (CommonUtil.objectNotNull(projLeaveApprEntity)) {
				projLeaveApprEntity.setLatest(false);
			}
			for (ProjLeaveApprTO projLeaveApprTO : projLeaveApprSaveReq.getProjLeaveApprTOs()) {
				log.info("projLeaveApprTO.getNotificationId() " + projLeaveApprTO.getNotificationId());
				EmployeeNotificationsEntity employeeNotificationsEntity = empNotificationsRepository
						.findEmpLeaveReqApprRecord(projLeaveApprTO.getNotificationId(), "LEAVE");
				employeeNotificationsEntity.setNotifyStatus(projLeaveApprTO.getNotificationStatus());
				
				//made changes
				LeaveNormalTimeEntity leaveNormalTimeEntity = leaveRequestRepository.findLeaveNormalTimeByProjId(
						employeeNotificationsEntity.getProjMstrEntity().getProjectId(), 1);
				leaveNormalTimeEntity.setCutOffDays(2);
				leaveNormalTimeEntity.setCutOffHours(10);

				String AddlTimeLeaveType = null;
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				String apprDate = null;
				EmpLeaveReqApprEntity leaveReqApprEntity = empLeaveReqApprRepository
						.findEmpLeaveReqApprRecord(projLeaveApprTO.getId());
				if (leaveReqApprEntity != null) {
					if (leaveReqApprEntity.getStartDate() != null) {

						apprDate = dateFormat.format(leaveReqApprEntity.getStartDate());
					}
					AddlTimeLeaveType = "Additiaonal time granted for approval of leave from " + apprDate + " for "
							+ leaveReqApprEntity.getTotalDays() + " days";
				}
				employeeNotificationsEntity.setType(AddlTimeLeaveType);
				employeeNotificationsEntity.setDate(new Date());

				sendEmpLeaveAdditionalTimeMail(employeeNotificationsEntity);
			}
		}
		projLeaveApprRepository
				.save(ProjLeaveApprHandler.convertPOJOToEntity(projLeaveApprSaveReq.getProjLeaveApprTOs()));
	}

	private void sendEmpLeaveAdditionalTimeMail(EmployeeNotificationsEntity employeeNotificationsEntity) {
		// Addtional time Is Approved and send Email;
		String epsName = null;
		String projName = null;
		String ccEmail = "";
		String toEmail = "";
		String toSubject = "";
		String text = "";
		String toepsName = null;
		String toprojName = null;
		String userName = null;
		String leaveStartDate = null;
		String noOfDays = null;
		String reqDate = null;

		EmpLeaveReqApprEntity leaveReqApprEntity = empLeaveReqApprRepository
				.findEmpLeaveReqApprRecord(employeeNotificationsEntity.getId());

		UserProjGetReq userProjGetReq = new UserProjGetReq();
		UserProjResp userProjResp = epsProjService.getAllUserProjects(userProjGetReq);
		Map<Long, LabelKeyTO> userProjMap = new HashMap<>();
		LabelKeyTO userProjLabelKeyTO = null;
		for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
			userProjLabelKeyTO = new LabelKeyTO();
			userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
			userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
			userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
			userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
		}
		for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
			if (CommonUtil.objectNotNull(employeeNotificationsEntity.getProjMstrEntity())
					&& employeeNotificationsEntity.getProjMstrEntity().getProjectId().equals(entry.getKey())) {
				epsName = entry.getValue().getCode();
				projName = entry.getValue().getName();
			}
		}

		SimpleDateFormat dateFormatStartDate = new SimpleDateFormat("dd-MMM-yyyy");
		if (leaveReqApprEntity.getStartDate() != null) {
			leaveStartDate = dateFormatStartDate.format(leaveReqApprEntity.getStartDate());
		}
		if (leaveReqApprEntity.getTotalDays() != null) {
			noOfDays = leaveReqApprEntity.getTotalDays().toString();
		}

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
		reqDate = dateFormat1.format(employeeNotificationsEntity.getDate());

		String reqCode = ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
				.concat("-" + ModuleCodesPrefixes.REQUEST_PREFIX.getDesc())
				.concat("-" + leaveReqApprEntity.getProjMstrEntity().getCode()).concat("-" + leaveReqApprEntity.getId())
				+ " dated " + reqDate;

		String notifyCode = ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
				.concat("-" + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc())
				.concat("-" + leaveReqApprEntity.getProjMstrEntity().getCode()).concat("-" + leaveReqApprEntity.getId())
				+ " dated " + reqDate;

		LeaveNormalTimeEntity leaveNormalTimeEntity = leaveRequestRepository.findLeaveNormalTimeByProjId(
				employeeNotificationsEntity.getProjMstrEntity().getProjectId(), employeeNotificationsEntity.getStatus());

		LocalDate today = LocalDate.now();
		LocalDateTime grantedUpTo = today.atStartOfDay();
		grantedUpTo = grantedUpTo.plusDays(1);
		grantedUpTo = grantedUpTo.plusDays(leaveNormalTimeEntity.getCutOffDays());
		grantedUpTo = grantedUpTo.plusHours(leaveNormalTimeEntity.getCutOffHours());
		grantedUpTo = grantedUpTo.plusMinutes(leaveNormalTimeEntity.getCutOffMinutes());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
		String addlTimeGrantedUpTo = grantedUpTo.format(formatter);

		log.info("Granted Upto is :::::::: " + addlTimeGrantedUpTo);

		UserMstrEntity userMstr = employeeNotificationsEntity.getReqUserId();
		UserMstrEntity userMstr1 = employeeNotificationsEntity.getApprUserId();

		toEmail = userMstr1.getEmail();
		ccEmail = userMstr.getEmail();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String apprDate = dateFormat.format(employeeNotificationsEntity.getDate());

		toSubject = "Grant additional time for approval leave";
		text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>" + "<p>Reference Notification ID " + notifyCode
				+ "</p>" + "<p>I have granted additional time for leave approval through " + pot
				+ ", as per details mentioned here below.</p>" + "<table border='1'>" + "<tr><td>Company </td><td>"
				+ epsName + "</td></tr>" + "<tr><td>Project </td><td>" + projName + "</td></tr>"
				+ "<tr><td>Employee ID </td><td>" + leaveReqApprEntity.getEmpRegisterDtlEntity().getCode()
				+ "</td></tr>" + "<tr><td>Employee Name</td><td>"
				+ leaveReqApprEntity.getEmpRegisterDtlEntity().getFirstName() + " "
				+ leaveReqApprEntity.getEmpRegisterDtlEntity().getLastName() + "</td></tr>"
				+ "<tr><td>Requisition ID</td><td>" + reqCode + "</td></tr>" + "<tr><td>Notification ID</td><td>"
				+ notifyCode + "</td></tr>" + "<tr><td>Leave Start Date</td><td>" + leaveStartDate + "</td></tr>"
				+ "<tr><td>Number of Days</td><td>" + noOfDays + "</td></tr>"
				+ "<tr><td>Addl. Time granted up to </td><td>" + addlTimeGrantedUpTo + "</td></tr>" + "</table>"
				+ "<p>This is for your information please.</p>" + "<p>Regards,</p>" + "<p>" + AppUserUtils.getName()
				+ "<br/>" + AppUserUtils.getDisplayRole() + "<br/>" + apprDate + "</p>" + "</body></html>";

		commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
	}

	public ProjStatusDatesResp getProjStatusDates(ProjSettingsFilterReq projSettingsFilterReq) {
		
		if(projSettingsFilterReq.getProjId() != null) {
			projSettingsFilterReq.getProjIds().add(projSettingsFilterReq.getProjId());
		}
		log.info("Project Idssss " + projSettingsFilterReq.getProjIds());
		List<Long> projIds = projSettingsFilterReq.getProjIds();
		ProjStatusDatesResp projStatusDatesResp = new ProjStatusDatesResp();
		for (Long projId : projIds) {
			log.info("Project ID: " + projId);
		
		 
			log.info("Project Status > Project Status > Progress Status");
			
			List<ProjStatusDatesEntity> projStatusDatesEntities = projStatusDatesRepository
					.findProjStatusDates(projId, StatusCodes.ACTIVE.getValue());
	
			Long earnedValue = getTotalEarnedValue(projId);
			log.info("Project Status > Project Status > Progress Status > earnedValue = " + earnedValue);
			BigDecimal plannedCost = getTotalPlannedValue(projId);
			/* ========================= Beginning of Planned Value change ================================ */
			List<ResourceAssignmentDataEntity> resourceAssignmentDataEntities = resourceAssignmentDataRepository.findByBaseline(projId);
			BigDecimal plannedValue = BigDecimal.ZERO;
			for (ResourceAssignmentDataEntity resourceAssignmentDataEntity : resourceAssignmentDataEntities) {
				if (resourceAssignmentDataEntity.getReferenceType().contentEquals("POT_COST")) {
					for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataEntity.getResourceAssignmentDataValueEntities()) {
						if (resourceAssignmentDataValueEntity.getForecastDate().before(new Date())) {
							plannedValue = plannedValue.add(BigDecimal.valueOf(resourceAssignmentDataValueEntity.getBudgetUnits()));
						}
					}
				}
			}
			log.info("========================================= planned value ============ " + plannedValue);
			/* ========================= End of Planned Value change ================================ */
			log.info("Project Status > Project Status > Progress Status > plannedCost = " + plannedCost);
			if (CommonUtil.isListHasData(projStatusDatesEntities)) {
				log.info("Project Status > Project Status > Progress Status > HAVING " + projStatusDatesEntities.size() + " LIST ");
				for (ProjStatusDatesEntity projStatusDatesEntity : projStatusDatesEntities) {
					ProjStatusDatesTO projStatusDatesTO = ProjStatusDatesHandler.convertEntityToPOJO(projStatusDatesEntity);
					projStatusDatesTO.setEarnedValue(earnedValue);
					projStatusDatesTO.setPlannedValue(plannedValue);
					projStatusDatesResp.getProjStatusDatesTOs().add(projStatusDatesTO);
				}
			} else {
				log.info("Project Status > Project Status > Progress Status > DOES NOT HAVE ANY LIST ");
				ProjStatusDatesTO projStatusDatesTO = new ProjStatusDatesTO();
				projStatusDatesTO.setEarnedValue(earnedValue);
				projStatusDatesTO.setPlannedValue(plannedValue);
				projStatusDatesResp.getProjStatusDatesTOs().add(projStatusDatesTO);
			}
		}
		return projStatusDatesResp;
	}

	public ProjStatusDatesResp getMultiProjStatusDates(ProjSettingsFilterReq projSettingsFilterReq) {
		ProjStatusDatesResp projStatusDatesResp = new ProjStatusDatesResp();
		List<Long> projIds = projSettingsFilterReq.getProjIds();
		for (Long projId : projIds) {
			ProjSettingsFilterReq projReq = new ProjSettingsFilterReq();
			projReq.setProjId(projId);
			projReq.setStatus(projSettingsFilterReq.getStatus());
			List<ProjStatusDatesTO> projStatusDatesTOs = getProjStatusDates(projReq).getProjStatusDatesTOs();
			if (!projStatusDatesTOs.isEmpty()) {
				projStatusDatesResp.getProjStatusDatesTOs().addAll(projStatusDatesTOs);
			}
		}
		return projStatusDatesResp;
	}

	public void saveProjStatusDates(ProjStatusDatesSaveReq projStatusDatesSaveReq) {

		ProjStatusDatesEntity projStatusDatesEntity = null;
		for (ProjStatusDatesTO projStatusDatesTO : projStatusDatesSaveReq.getProjStatusDatesTOs()) {
			if (projStatusDatesTO.getId() != null) {
				projStatusDatesEntity = projStatusDatesRepository.findOne(projStatusDatesTO.getId());
			}
			if (CommonUtil.objectNotNull(projStatusDatesEntity)) {
				projStatusDatesEntity.setId(projStatusDatesTO.getId());
			} else {
				projStatusDatesEntity = new ProjStatusDatesEntity();
			}
		}
		List<ProjStatusDatesEntity> projStatusDatesTOs = ProjStatusDatesHandler.convertPOJOToEntity(
				projStatusDatesEntity, projStatusDatesSaveReq.getProjStatusDatesTOs(), epsProjRepository);
		projStatusDatesRepository.save(projStatusDatesTOs);
	}

	public CalTO getGlobalCalendar() {
		return GlobalCalHandler
				.convertEntityToPOJO(globalCalendarRepository.getDefaultGlobalCalendar(AppUserUtils.getClientId()));
	}

	public ProjCostStaementsResp getProjExitMaterialCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
		ProjCostStaementsResp projCostStaementsResp = new ProjCostStaementsResp();
		Map<Long, ProjCostStmtDtlEntity> projCostStmtMap = new HashMap<>();
		List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository
				.findCostDetails(projCostStatementsGetReq.getProjId(), StatusCodes.ACTIVE.getValue());

		List<ProjCostStmtDtlEntity> pojCostStmtDtlEntities = projCostStatementsRepository
				.findProjMaterialCostStatements(projCostStatementsGetReq.getProjId(),
						projCostStatementsGetReq.getStatus());
		for (ProjCostStmtDtlEntity projCostStmtDtlEntity : pojCostStmtDtlEntities) {
			projCostStmtMap.put(projCostStmtDtlEntity.getProjCostItemEntity().getId(), projCostStmtDtlEntity);
		}
		ProjCostStmtDtlTO projCostStmtDtlTO = null;
		for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
			projCostStmtDtlTO = ProjCostStmtDtlHandler.populateProjExitCostStmts(projCostItemEntity, projCostStmtMap);
			if (CommonUtil.objectNotNull(projCostStmtDtlTO)) {
				projCostStaementsResp.getProjCostStmtDtlTOs().add(projCostStmtDtlTO);
			}
		}
		return projCostStaementsResp;

	}

	public ProjCostStaementsResp getProjExitPlantCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
		ProjCostStaementsResp projCostStaementsResp = new ProjCostStaementsResp();
		Map<Long, ProjCostStmtDtlEntity> projCostStmtMap = new HashMap<>();
		List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository
				.findCostDetails(projCostStatementsGetReq.getProjId(), StatusCodes.ACTIVE.getValue());

		List<ProjCostStmtDtlEntity> pojCostStmtDtlEntities = projCostStatementsRepository.findProjPlantCostStatements(
				projCostStatementsGetReq.getProjId(), projCostStatementsGetReq.getStatus());
		for (ProjCostStmtDtlEntity projCostStmtDtlEntity : pojCostStmtDtlEntities) {
			projCostStmtMap.put(projCostStmtDtlEntity.getProjCostItemEntity().getId(), projCostStmtDtlEntity);
		}
		ProjCostStmtDtlTO projCostStmtDtlTO = null;
		for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
			projCostStmtDtlTO = ProjCostStmtDtlHandler.populateProjExitCostStmts(projCostItemEntity, projCostStmtMap);
			if (CommonUtil.objectNotNull(projCostStmtDtlTO)) {
				projCostStaementsResp.getProjCostStmtDtlTOs().add(projCostStmtDtlTO);
			}
		}
		return projCostStaementsResp;

	}

	public ProjCostStaementsResp getProjExitManpowerCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
		ProjCostStaementsResp projCostStaementsResp = new ProjCostStaementsResp();
		Map<Long, ProjCostStmtDtlEntity> projCostStmtMap = new HashMap<>();
		List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository
				.findCostDetails(projCostStatementsGetReq.getProjId(), StatusCodes.ACTIVE.getValue());

		List<ProjCostStmtDtlEntity> pojCostStmtDtlEntities = projCostStatementsRepository
				.findProjManpowerCostStatements(projCostStatementsGetReq.getProjId(),
						projCostStatementsGetReq.getStatus());
		for (ProjCostStmtDtlEntity projCostStmtDtlEntity : pojCostStmtDtlEntities) {
			projCostStmtMap.put(projCostStmtDtlEntity.getProjCostItemEntity().getId(), projCostStmtDtlEntity);
		}
		ProjCostStmtDtlTO projCostStmtDtlTO = null;
		for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
			projCostStmtDtlTO = ProjCostStmtDtlHandler.populateProjExitCostStmts(projCostItemEntity, projCostStmtMap);
			if (CommonUtil.objectNotNull(projCostStmtDtlTO)) {
				projCostStaementsResp.getProjCostStmtDtlTOs().add(projCostStmtDtlTO);
			}
		}
		return projCostStaementsResp;
	}

	public ProjCostStaementsResp getProjExitServiceCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
		ProjCostStaementsResp projCostStaementsResp = new ProjCostStaementsResp();
		Map<Long, ProjCostStmtDtlEntity> projCostStmtMap = new HashMap<>();
		List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository
				.findCostDetails(projCostStatementsGetReq.getProjId(), StatusCodes.ACTIVE.getValue());

		List<ProjCostStmtDtlEntity> pojCostStmtDtlEntities = projCostStatementsRepository.findProjServiceCostStatements(
				projCostStatementsGetReq.getProjId(), projCostStatementsGetReq.getStatus());
		for (ProjCostStmtDtlEntity projCostStmtDtlEntity : pojCostStmtDtlEntities) {
			projCostStmtMap.put(projCostStmtDtlEntity.getProjCostItemEntity().getId(), projCostStmtDtlEntity);
		}

		ProjCostStmtDtlTO projCostStmtDtlTO = null;
		for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
			projCostStmtDtlTO = ProjCostStmtDtlHandler.populateProjExitCostStmts(projCostItemEntity, projCostStmtMap);
			if (CommonUtil.objectNotNull(projCostStmtDtlTO)) {
				projCostStaementsResp.getProjCostStmtDtlTOs().add(projCostStmtDtlTO);
			}
		}
		return projCostStaementsResp;
	}

	public ProjStatusActualResp getProjStatusActualQty(ProjStatusActualReq projStatusActualReq) {
		ProjStatusActualResp resp = new ProjStatusActualResp();
		Map<Long, ProjStatusActualTo> respMap = new HashMap<>();
		List<Object[]> beforeQuantity = null;
		List<Object[]> betweenQuantity = null;
		List<Object[]> originalQuantity = new ArrayList<>();
		Date fromDate = null;
		Date toDate = null;
		final String contractCode = projStatusActualReq.getContractCode();
		try {
			fromDate = AppUtils.getDate(projStatusActualReq.getFromDate(), "dd-MMM-yyyy");
			toDate = AppUtils.getDate(projStatusActualReq.getToDate(), "dd-MMM-yyyy");
		} catch (ParseException e) {
			log.error("Date parse error: ", e);
		}
		List<ProjStatusActualTo> actualTos = new ArrayList<>();
		if (StringUtils.isNotBlank(contractCode)) {

			beforeQuantity = projSowTotalActualRepository.getSubContractActualQuantitiesBeforeStartDate(
					projStatusActualReq.getProjId(), fromDate, contractCode);

			betweenQuantity = projSowTotalActualRepository.getSubContractActualQuantitiesBetweenStartDateAndDate(
					projStatusActualReq.getProjId(), fromDate, toDate, contractCode);

			originalQuantity = purchaseOrderRepositoryCopy.getOriginalQtyOfPo(projStatusActualReq.getPurId());

		} else {
			beforeQuantity = projSowTotalActualRepository
					.getActualQuantitiesBeforeStartDate(projStatusActualReq.getProjId(), fromDate);

			betweenQuantity = projSowTotalActualRepository
					.getActualQuantitiesBetweenStartDateAndDate(projStatusActualReq.getProjId(), fromDate, toDate);

		}

		for (Object[] actualTo : beforeQuantity) {
			ProjStatusActualTo projStatusActualTo = new ProjStatusActualTo();
			projStatusActualTo.setId((Long) actualTo[0]);
			projStatusActualTo.setBeforeQty((Double) actualTo[1]);
			projStatusActualTo.setBtwnQty(Double.valueOf(0));
			projStatusActualTo.setOrigQty(Double.valueOf(0));
			actualTos.add(projStatusActualTo);
		}

		for (Object[] actualTo : betweenQuantity) {
			ProjStatusActualTo projStatusActualTo = new ProjStatusActualTo();
			projStatusActualTo.setId((Long) actualTo[0]);
			projStatusActualTo.setBeforeQty(Double.valueOf(0));
			projStatusActualTo.setBtwnQty((Double) actualTo[1]);
			projStatusActualTo.setOrigQty(Double.valueOf(0));
			actualTos.add(projStatusActualTo);
		}

		for (Object[] actualTo : originalQuantity) {
			ProjStatusActualTo projStatusActualTo = new ProjStatusActualTo();
			projStatusActualTo.setId((Long) actualTo[0]);
			projStatusActualTo.setBeforeQty(Double.valueOf(0));
			projStatusActualTo.setBtwnQty(Double.valueOf(0));
			projStatusActualTo.setOrigQty(((BigDecimal) actualTo[1]).doubleValue());
			actualTos.add(projStatusActualTo);
		}

		for (ProjStatusActualTo actualTo : actualTos) {
			if (respMap.get(actualTo.getId()) != null) {
				ProjStatusActualTo projStatusActualTo = respMap.get(actualTo.getId());
				projStatusActualTo.setBeforeQty(projStatusActualTo.getBeforeQty() + actualTo.getBeforeQty());
				projStatusActualTo.setBtwnQty(projStatusActualTo.getBtwnQty() + actualTo.getBtwnQty());
				projStatusActualTo.setOrigQty(projStatusActualTo.getOrigQty() + actualTo.getOrigQty());
			} else {
				respMap.put(actualTo.getId(), actualTo);
			}

		}

		resp.setProjStatusActualTos(respMap);
		return resp;

	}

	public void saveProjDurationStatus(ProjStatusDatesSaveReq projStatusDatesSaveReq) {
		ProjStatusDatesEntity projStatusDatesEntity = null;
		for (ProjStatusDatesTO projStatusDatesTO : projStatusDatesSaveReq.getProjStatusDatesTOs()) {
			if (CommonUtil.isNonBlankLong(projStatusDatesTO.getId())) {
				projStatusDatesEntity = projStatusDatesRepository.findOne(projStatusDatesTO.getId());
			}
			if (CommonUtil.objectNotNull(projStatusDatesEntity)) {
				projStatusDatesEntity.setId(projStatusDatesTO.getId());
			} else {
				projStatusDatesEntity = new ProjStatusDatesEntity();
			}
		}
		projStatusDatesRepository.save(ProjStatusDatesHandler.convertPOJOToEntity(projStatusDatesEntity,
				projStatusDatesSaveReq.getProjStatusDatesTOs(), epsProjRepository));
	}

	// milestones
	public void saveProjStatusMileStones(ProjMileStonesDateSaveReq projMileStonesDateSaveReq) {

		ProjStatusMileStonesEntity projStatusMileStonesEntity = null;
		for (ProjStatusMilestonesTO projStatusMilestonesTO : projMileStonesDateSaveReq.getProjMileStonesTOs()) {
			if (projStatusMilestonesTO.getId() != null) {
				projStatusMileStonesEntity = projStatusMileStonesRepository.findOne(projStatusMilestonesTO.getId());
			}
			if (CommonUtil.objectNotNull(projStatusMileStonesEntity)) {
				projStatusMileStonesEntity.setId(projStatusMilestonesTO.getId());
			} else {
				projStatusMileStonesEntity = new ProjStatusMileStonesEntity();
			}
		}
		projStatusMileStonesRepository.save(ProjStatusMileStonesHandler.convertPOJOToEntity(projStatusMileStonesEntity,
				projMileStonesDateSaveReq.getProjMileStonesTOs(), epsProjRepository));

	}

	public ProjMileStonesResp getProjStatusMileStones(ProjSettingsFilterReq projSettingsFilterReq) {
		ProjMileStonesResp projMileStonesResp = new ProjMileStonesResp();
		List<ProjStatusMileStonesEntity> projStatusMileStonesEntities = projStatusMileStonesRepository
				.findProjStatusMileStones(projSettingsFilterReq.getProjId(), projSettingsFilterReq.getStatus());
		if (CommonUtil.isListHasData(projStatusMileStonesEntities)) {
			for (ProjStatusMileStonesEntity projStatusMileStonesEntity : projStatusMileStonesEntities) {
				projMileStonesResp.getProjMileStonesTOs()
						.add(ProjStatusMileStonesHandler.convertEntityToPOJO(projStatusMileStonesEntity));
			}
		}
		return projMileStonesResp;
	}

	public void deleteProjStatusMileStones(ProjMileStonesDateSaveReq projMileStonesDateSaveReq) {
		for (ProjStatusMilestonesTO projStatusMilestonesTO : projMileStonesDateSaveReq.getProjMileStonesTOs()) {
			if (projStatusMilestonesTO.getId() != null) {
				projStatusMileStonesRepository.deleteProjStatusMileStones(projStatusMilestonesTO.getId(),
						projStatusMilestonesTO.getStatus());
			}
		}
	}

	private Long getTotalEarnedValue(long projId) {
		Map<Long, BigDecimal> earnedValues = getEarnedValueForCostId(projId);
		Long earnedValue = 0L;
		for (BigDecimal earned : earnedValues.values()) {
			earnedValue += earned.longValue();
		}
		return earnedValue;
	}

	private Map<Long, BigDecimal> getEarnedValueForCostId(long projId) {
		log.info("getEarnedValueForCostId method");
		Map<Long, BigDecimal> earnedValues = new HashMap<>();
		List<Object[]> resp = projSowTotalActualRepository.getEarnedValuePerCostCode(projId);
		for (Object[] object : resp) {
			if ((BigDecimal) object[2] != null && object[1] != null) {
				BigDecimal rate = ((BigDecimal) object[2]).multiply(BigDecimal.valueOf((Double) object[1]));
				earnedValues.merge((Long) object[0], rate, BigDecimal::add);
			}
		}
		log.info("earnedValues " + earnedValues);
		return earnedValues;
	}

	private BigDecimal getTotalPlannedValue(long projId) {
		Map<Long, BigDecimal> earnedValues = getPlannedValuesForCostCode(projId);
		BigDecimal earnedValue = new BigDecimal(0);
		for (Entry<Long, BigDecimal> entry : earnedValues.entrySet()) {
			earnedValue = earnedValue.add(entry.getValue());
		}
		return earnedValue;
	}

	private Map<Long, BigDecimal> getPlannedValuesForCostCode(long projId) {
		Map<Long, BigDecimal> planeedValues = new HashMap<>();
		ProjScheduleBaseLineGetReq projScheduleBaseLineGetReq = new ProjScheduleBaseLineGetReq();
		projScheduleBaseLineGetReq.setProjId(projId);
		projScheduleBaseLineGetReq.setStatus(1);
		ProjScheduleCostCodeResp resp = projScheduleService.getProjBudgetCostCodeDetails(projScheduleBaseLineGetReq);
		for (ProjScheduleCostCodeTO projScheduleCostCodeTO : resp.getProjScheduleCostCodeTOs()) {
			Long costId = projScheduleCostCodeTO.getCostCodeId();
			BigDecimal actualValue = BigDecimal.ZERO;
			CostActualHoursTO actualCost = resp.getActualWorkingDayMap().get(costId);
			if (actualCost != null) {
				actualValue = (actualCost.getLabourCost() == null ? BigDecimal.ZERO
						: BigDecimal.valueOf(actualCost.getLabourCost()))
								.add(actualCost.getMaterialCost() == null ? BigDecimal.ZERO
										: BigDecimal.valueOf(actualCost.getMaterialCost()))
								.add(actualCost.getPlantCost() == null ? BigDecimal.ZERO
										: BigDecimal.valueOf(actualCost.getPlantCost()))
								.add(actualCost.getOthersCost() == null ? BigDecimal.ZERO
										: BigDecimal.valueOf(actualCost.getOthersCost()));

			}

			BigDecimal amount = projScheduleCostCodeTO.getRevisedQty() != null ? projScheduleCostCodeTO.getRevisedQty()
					: projScheduleCostCodeTO.getOriginalQty();

			amount = amount == null ? new BigDecimal(0) : amount;

			BigDecimal plannedValues = amount.subtract(actualValue);

			planeedValues.put(costId, plannedValues);
		}
		return planeedValues;
	}

	public ProjEmpTransResp findEmpTransNormalTime(ProjEmpTransGetReq empTransGetReq) {
		ProjEmpTransResp projEmpTransResp = new ProjEmpTransResp();
		log.info("empTransGetReq.getEmptransType() " + empTransGetReq.getEmptransType());
		EmpTransNormalTimeEntity empTransNormalTimeEntity = projEmpTransRepository
				.findByProjIdTransType(empTransGetReq.getProjId(), empTransGetReq.getEmptransType());
		projEmpTransResp.getProjEmpTransTOs().add(ProjEmpTransHandler.convertEntityToPOJO(empTransNormalTimeEntity));
		return projEmpTransResp;
	}

	public EmpClassesResp getEmpClasses(ProjManpowerGetReq projManpowerGetReq) {
		return EmpClassHandler.convertEntityToPOJO(
				projManpowerRepository.getEmpClasses(clientRegRepository.findOne(AppUserUtils.getClientId()),
						epsProjRepository.findOne(projManpowerGetReq.getProjId()), 1));
	}

	public PlantClassResp getPlantClasses(ProjManpowerGetReq projManpowerGetReq) {
		return PlantClassHandler.convertEntityToPOJO(
				projectPlantsRepository.getPlantsForBudget(clientRegRepository.findOne(AppUserUtils.getClientId()),
						epsProjRepository.findOne(projManpowerGetReq.getProjId()), 1));
	}

	public MaterialClassResp getMaterialGroups(ProjManpowerGetReq projManpowerGetReq) {
		MaterialClassResp materialClassResp = new MaterialClassResp();
		List<MaterialClassMstrEntity> materialClassMstrEntities = null;
		materialClassMstrEntities = projectMaterialRepository.getMaterialsForBudget(
				epsProjRepository.findOne(projManpowerGetReq.getProjId()),
				clientRegRepository.findOne(AppUserUtils.getClientId()), 1);
		for (MaterialClassMstrEntity materialClassMstrEntity : materialClassMstrEntities) {
			materialClassResp.getMaterialClassTOs()
					.add(MaterialClassHandler.populateMaterialItems(materialClassMstrEntity, true));
		}
		return materialClassResp;
	}

	@Override
	public Map<Long, Double> getCostCodeActualQty(ProjManpowerGetReq projManpowerGetReq) {
		Map<Long, Double> actualCost = new HashMap<>();
		for (Long projId : projManpowerGetReq.getProjIds()) {
			Map<Long, Double> costCodeMap = actualAmountService.getManpowerActualAmount(projId);
			actualCost.put(projId, costCodeMap.values().stream().reduce((double) 0, Double::sum));
		}

		return actualCost;
	}

	private String generateCode(EmployeeNotificationsEntityCopy employeeNotificationsEntity) {
		return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
				.concat("-" + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc()
						.concat("-" + employeeNotificationsEntity.getProjId().getCode())
						.concat("-" + employeeNotificationsEntity.getId()));
	}

	private String generateReqCode(EmployeeNotificationsEntityCopy employeeNotificationsEntity) {
		return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
				.concat("-" + ModuleCodesPrefixes.REQUEST_PREFIX.getDesc()
						.concat("-" + employeeNotificationsEntity.getProjId().getCode())
						.concat("-" + employeeNotificationsEntity.getId()));
	}
	
	public ProjStatusDatesResp getProjectsDatesForProgressSCurveReport(ProjSettingsFilterReq projSettingsFilterReq) {
		ProjStatusDatesResp projStatusDatesResp = new ProjStatusDatesResp();
		List<Long> projIds = projSettingsFilterReq.getProjIds();
		for (Long projId : projIds) {
			ProjSettingsFilterReq projReq = new ProjSettingsFilterReq();
			projReq.setProjId(projId);
			projReq.setStatus(projSettingsFilterReq.getStatus());
			List<ProjStatusDatesTO> projStatusDatesTOs = getProjStatusDates(projReq).getProjStatusDatesTOs();
			if (projStatusDatesTOs.isEmpty()) {
				ProjStatusDatesTO projStatusDatesTO = new ProjStatusDatesTO();
				projStatusDatesTO.setProjId(projId);
				projStatusDatesTO.setScheduleStartDate(CommonUtil.convertDateToString(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projId)));
				projStatusDatesTO.setScheduleFinishDate(CommonUtil.convertDateToString(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projId)));
				projStatusDatesResp.getProjStatusDatesTOs().add(projStatusDatesTO);
			} else if (projStatusDatesTOs.get(0).getScheduleStartDate() == null || projStatusDatesTOs.get(0).getScheduleFinishDate() == null) {
				ProjStatusDatesTO projStatusDatesTO = new ProjStatusDatesTO();
				projStatusDatesTO.setProjId(projId);
				projStatusDatesTO.setScheduleStartDate(CommonUtil.convertDateToString(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projId)));
				projStatusDatesTO.setScheduleFinishDate(CommonUtil.convertDateToString(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projId)));
				projStatusDatesResp.getProjStatusDatesTOs().add(projStatusDatesTO);
			} else {
				projStatusDatesResp.getProjStatusDatesTOs().addAll(projStatusDatesTOs);
			}
		}
		return projStatusDatesResp;
	}

	public ProjPlannedValueResp getProjPlannedValue(ProjCostStatementsGetReq projCostStatementsGetReq) {
		ProjPlannedValueResp projPlannedValueResp = new ProjPlannedValueResp();
		List<Double> planned = new ArrayList<>();

		for (Long projectId : projCostStatementsGetReq.getProjIds()) {
			ProjPlannedValueTO projPlannedValueTO = new ProjPlannedValueTO();
			projPlannedValueTO.setProjId(projectId);
			List<ResourceAssignmentDataEntity> resourceAssignmentDataEntities = resourceAssignmentDataRepository.findByBaseline(projectId);
			Double plannedValue = Double.valueOf(0);
			Double directManHours = Double.valueOf(0);
			Long id = 0L;
			for (ResourceAssignmentDataEntity resourceAssignmentDataEntity : resourceAssignmentDataEntities) {
				if (resourceAssignmentDataEntity.getReferenceType().contentEquals("POT_COST")) {
					for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataEntity.getResourceAssignmentDataValueEntities()) {
						if (resourceAssignmentDataValueEntity.getForecastDate().before(new Date()))
							plannedValue += resourceAssignmentDataValueEntity.getBudgetUnits();
					}
				}
								
				if (resourceAssignmentDataEntity.getReferenceType().contentEquals("POT_EMPLOYEE")) {
					id = resourceAssignmentDataEntity.getReferenceId();
					Double budget = Double.valueOf(0);
					List<ProjManpowerEntity> projManpowerEntity = projManpowerRepository.findbyIds(resourceAssignmentDataEntity.getReferenceId());
					//if (projManpowerEntity.get(0).getProjEmpCategory().equals("DIRECT")) {
						
						for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataEntity.getResourceAssignmentDataValueEntities()) {
							if (resourceAssignmentDataValueEntity.getForecastDate().before(new Date()))
								if(id == resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId()) {
									budget += resourceAssignmentDataValueEntity.getBudgetUnits();
								}
									
																
								directManHours += resourceAssignmentDataValueEntity.getBudgetUnits();
						}
						planned.add(budget);
				//	}
				}
			}
			projPlannedValueTO.setPlannedValues(planned);
			projPlannedValueTO.setCost(plannedValue);
			projPlannedValueTO.setDirectManHours(directManHours);
			projPlannedValueResp.getProjPlannedValueTO().add(projPlannedValueTO);			
		}
		
		return projPlannedValueResp;
	}
	
	@Override
	public ProgressSCurveTOResp getProgressSCurveReportData(ProgressReportGetReq progressReportGetReq) {
		ProgressSCurveTOResp progressSCurveTOResp = new ProgressSCurveTOResp();
		
		List<ResourceAssignmentDataValueEntity> baselineResourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllBaselines(progressReportGetReq.getProjIds(), CommonUtil.convertStringToDate(progressReportGetReq.getFromDate()), CommonUtil.convertStringToDate(progressReportGetReq.getToDate())); 
		for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : baselineResourceAssignmentDataValueEntities) { 
			if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals(ResourceAssignmentDataHandler.POT_COST)) continue;
			progressSCurveTOResp.addToPlan(resourceAssignmentDataValueEntity.getForecastDate(), resourceAssignmentDataValueEntity.getBudgetUnits()); 
		}
		
		List<ResourceAssignmentDataValueEntity> currentResourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllCurrent(progressReportGetReq.getProjIds(), CommonUtil.convertStringToDate(progressReportGetReq.getFromDate()), CommonUtil.convertStringToDate(progressReportGetReq.getToDate()));
		if (currentResourceAssignmentDataValueEntities.size() > 0)
			progressSCurveTOResp.setScheduleDate(currentResourceAssignmentDataValueEntities.get(0).getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getScheduleDate());
		for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : currentResourceAssignmentDataValueEntities) { 
			if (progressSCurveTOResp.getScheduleDate() != null && resourceAssignmentDataValueEntity.getForecastDate().after(progressSCurveTOResp.getScheduleDate())) {
				switch (resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType()) {
				case "POT_SOR":
					progressSCurveTOResp.addEarnedForecast(resourceAssignmentDataValueEntity.getForecastDate(), resourceAssignmentDataValueEntity.getBudgetUnits());
					break;
				case "POT_COST":
					progressSCurveTOResp.addToForecastToComplete(resourceAssignmentDataValueEntity.getForecastDate(), resourceAssignmentDataValueEntity.getBudgetUnits());
					break;
				}
			}
		}
		  
        CostReportReq costReportReq = new CostReportReq();
        costReportReq.setProjIds(progressReportGetReq.getProjIds());
        costReportReq.setFromDate(progressReportGetReq.getFromDate());
        costReportReq.setToDate(progressReportGetReq.getToDate());
        costReportReq.setCategories(new ArrayList<String>());
        costReportReq.setCostcodeIds(new ArrayList<Long>());
        costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));
        DateWiseCostReportResp dateWiseCostReportResp = costCodeActualDetailsService.getDatewiseActualCostDetails(costReportReq); 
        for (CostReportTO costReportResp : dateWiseCostReportResp.getCostReportResps()) {
        	if (progressSCurveTOResp.getScheduleDate() != null && costReportResp.getDateObj().before(progressSCurveTOResp.getScheduleDate())) {
        		if (costReportResp.getCostAmount() > 0) 
        			progressSCurveTOResp.addToActual(costReportResp.getDateObj(), costReportResp.getCostAmount());
        	}
        }
        List<Object[]> dailyEarnedValues = projSowTotalActualRepository.getDailyEarnedValuePerCostCode(progressReportGetReq.getProjIds(), CommonUtil.convertStringToDate(progressReportGetReq.getFromDate()), CommonUtil.convertStringToDate(progressReportGetReq.getToDate()));
        for (Object[] dailyEarned : dailyEarnedValues) {
        	if (progressSCurveTOResp.getScheduleDate() != null && ((Date) dailyEarned[1]).before(progressSCurveTOResp.getScheduleDate())) {
	        	double value = (double) dailyEarned[2];
	        	BigDecimal rate = (BigDecimal) dailyEarned[3];
	        	progressSCurveTOResp.addToEarnedTodate((Date) dailyEarned[1], BigDecimal.valueOf(value * rate.doubleValue()));
        	}
        }
        
        progressSCurveTOResp.sortByDate();
		return progressSCurveTOResp;
	}

	@Override
	public ProgressSCurveTOResp getProgressSCurveManpowerReportData(ProgressReportGetReq progressReportGetReq) {
		ProgressSCurveTOResp progressSCurveTOResp = new ProgressSCurveTOResp();
		
		List<ResourceAssignmentDataValueEntity> baselineResourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllBaselines(progressReportGetReq.getProjIds(), CommonUtil.convertStringToDate(progressReportGetReq.getFromDate()), CommonUtil.convertStringToDate(progressReportGetReq.getToDate())); 
		for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : baselineResourceAssignmentDataValueEntities) { 
			if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals(ResourceAssignmentDataHandler.POT_EMPLOYEE)) continue;
			List<ProjManpowerEntity> projManpowerEntity = projManpowerRepository.findIds(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId());
			for(ProjManpowerEntity projManpowerEntiti: projManpowerEntity) {
			if (projManpowerEntiti.getProjEmpCategory().equals("DIRECT"))
				progressSCurveTOResp.addToPlan(resourceAssignmentDataValueEntity.getForecastDate(), resourceAssignmentDataValueEntity.getBudgetUnits()); 
			}
		}
		
		List<ResourceAssignmentDataValueEntity> currentResourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllCurrent(progressReportGetReq.getProjIds(), CommonUtil.convertStringToDate(progressReportGetReq.getFromDate()), CommonUtil.convertStringToDate(progressReportGetReq.getToDate()));
		if (currentResourceAssignmentDataValueEntities.size() > 0)
			progressSCurveTOResp.setScheduleDate(currentResourceAssignmentDataValueEntities.get(0).getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getScheduleDate());
		for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : currentResourceAssignmentDataValueEntities) {
			if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals(ResourceAssignmentDataHandler.POT_EMPLOYEE)) continue;
			List<ProjManpowerEntity> projManpowerEntity = projManpowerRepository.findIds(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId());
			for(ProjManpowerEntity projManpowerEntiti: projManpowerEntity) {
			if (projManpowerEntiti.getProjEmpCategory().equals("DIRECT")) {
				if (progressSCurveTOResp.getScheduleDate() != null && resourceAssignmentDataValueEntity.getForecastDate().after(progressSCurveTOResp.getScheduleDate()))
					progressSCurveTOResp.addEarnedForecast(resourceAssignmentDataValueEntity.getForecastDate(), resourceAssignmentDataValueEntity.getBudgetUnits());
			}
			}
		}
		
        List<TimeSheetEmpWorkDtlEntity> timeSheetWorkHrs = timeSheetEmpDtlRepository
                .getManpowerActualHrsDateWise(progressReportGetReq.getProjIds(), CommonUtil.convertStringToDate(progressReportGetReq.getFromDate()), CommonUtil.convertStringToDate(progressReportGetReq.getToDate()));
        List<WorkDairyEmpWageEntity> workDairyHrs = empWageWorkDairyRepository
                .getWorkDairyManPowerDateWiseActualHrs(progressReportGetReq.getProjIds(), CommonUtil.convertStringToDate(progressReportGetReq.getFromDate()), CommonUtil.convertStringToDate(progressReportGetReq.getToDate()));
        List<Object[]> earnedValuesList = progressWorkDairyRepository.getManpowerEarnedValues(progressReportGetReq.getProjIds());
        
        for (TimeSheetEmpWorkDtlEntity timeSheetWork : timeSheetWorkHrs) {
        	Calendar calendar = Calendar.getInstance();
        	calendar.setTime(timeSheetWork.getTimeSheetEmpDtlEntity().getTimeSheetEntity().getWeekStartDate());
        	if (progressSCurveTOResp.getScheduleDate() != null && calendar.getTime().before(progressSCurveTOResp.getScheduleDate()))
        		progressSCurveTOResp.addToActual(calendar.getTime(), timeSheetWork.getDay1());
        	calendar.add(Calendar.DAY_OF_MONTH, 1);
        	if (progressSCurveTOResp.getScheduleDate() != null && calendar.getTime().before(progressSCurveTOResp.getScheduleDate()))
        		progressSCurveTOResp.addToActual(calendar.getTime(), timeSheetWork.getDay2());
        	calendar.add(Calendar.DAY_OF_MONTH, 1);
        	if (progressSCurveTOResp.getScheduleDate() != null && calendar.getTime().before(progressSCurveTOResp.getScheduleDate()))
        		progressSCurveTOResp.addToActual(calendar.getTime(), timeSheetWork.getDay3());
        	calendar.add(Calendar.DAY_OF_MONTH, 1);
        	if (progressSCurveTOResp.getScheduleDate() != null && calendar.getTime().before(progressSCurveTOResp.getScheduleDate()))
        		progressSCurveTOResp.addToActual(calendar.getTime(), timeSheetWork.getDay4());
        	calendar.add(Calendar.DAY_OF_MONTH, 1);
        	if (progressSCurveTOResp.getScheduleDate() != null && calendar.getTime().before(progressSCurveTOResp.getScheduleDate()))
        		progressSCurveTOResp.addToActual(calendar.getTime(), timeSheetWork.getDay5());
        	calendar.add(Calendar.DAY_OF_MONTH, 1);
        	if (progressSCurveTOResp.getScheduleDate() != null && calendar.getTime().before(progressSCurveTOResp.getScheduleDate()))
        		progressSCurveTOResp.addToActual(calendar.getTime(), timeSheetWork.getDay6());
        	calendar.add(Calendar.DAY_OF_MONTH, 1);
        	if (progressSCurveTOResp.getScheduleDate() != null && calendar.getTime().before(progressSCurveTOResp.getScheduleDate()))
        		progressSCurveTOResp.addToActual(calendar.getTime(), timeSheetWork.getDay7());
        }
        for (WorkDairyEmpWageEntity woDairyEmpWageEntity : workDairyHrs) {
        	WorkDairyEntity workDairy = woDairyEmpWageEntity.getWorkDairyEmpDtlEntity().getWorkDairyEntity();
            List<WorkDairyEmpCostEntity> empCosts = empCostWorkDairyRepository.getApprovedWorkDairyEmpTime(woDairyEmpWageEntity.getId());
            if (!empCosts.isEmpty()) {
                for (WorkDairyEmpCostEntity empCost : empCosts) {
                    if (empCost.getIsLatest()) {
                        if (workDairy != null) {
                        	if (progressSCurveTOResp.getScheduleDate() != null && workDairy.getWorkDairyDate().before(progressSCurveTOResp.getScheduleDate()))
                        		progressSCurveTOResp.addToActual(workDairy.getWorkDairyDate(), empCost.getUsedTime());
                        }
                    }
                }
            }
        }
        for (Object[] earnedValue : earnedValuesList) {
            if (progressSCurveTOResp.getScheduleDate() != null && ((Date) earnedValue[1]).before(progressSCurveTOResp.getScheduleDate())) {
	            Double value = (Double) earnedValue[2];
	            progressSCurveTOResp.addToEarnedTodate((Date) earnedValue[1], BigDecimal.valueOf(value));
            }
        }
        
        for (ProgressSCurveTO progressSCurveTO : progressSCurveTOResp.getProgressSCurveTOs()) {
        	if (progressSCurveTOResp.getScheduleDate() != null && progressSCurveTO.getDate().after(progressSCurveTOResp.getScheduleDate()))
        		progressSCurveTOResp.addToForecastToComplete(progressSCurveTO.getDate(), progressSCurveTO.getActual().add(progressSCurveTO.getEarnedForecast()).doubleValue());
        }
        
        progressSCurveTOResp.sortByDate();
		return progressSCurveTOResp;
	}
	
	@Override
	public ProjectTangibleResp getTangiblesOfProjects(ProjectTangibleReq projectTangibleReq) {
		ProjectTangibleResp projectTangibleResp = new ProjectTangibleResp();
		
		if (projectTangibleReq.isShowOnlyActuals()) {
			List<WorkDairyProgressDtlEntity> workDairyProgressDtlEntities = progressWorkDairyRepository.findAllTangibles(projectTangibleReq.getProjectIds());
			for (WorkDairyProgressDtlEntity workDairyProgressDtlEntity : workDairyProgressDtlEntities) {
				if (!projectTangibleResp.getProjectTangibleTOs().stream().anyMatch(e -> e.getTangibleItemId().equals(workDairyProgressDtlEntity.getSowId().getTangibleClassificationEntity().getId()))) {
					ProjectTangibleTO projectTangibleTO = new ProjectTangibleTO();
					projectTangibleTO.setTangibleCode(workDairyProgressDtlEntity.getSowId().getTangibleClassificationEntity().getCode());
					projectTangibleTO.setTangibleItemId(workDairyProgressDtlEntity.getSowId().getTangibleClassificationEntity().getId());
					projectTangibleTO.setTangibleName(workDairyProgressDtlEntity.getSowId().getTangibleClassificationEntity().getName());
					projectTangibleTO.setProjId(workDairyProgressDtlEntity.getSowId().getProjMstrEntity().getProjectId());
					projectTangibleTO.setName(workDairyProgressDtlEntity.getSowId().getProjMstrEntity().getProjName());
					projectTangibleTO.setCode(workDairyProgressDtlEntity.getSowId().getProjMstrEntity().getCode());
					projectTangibleTO.setParentProjId(workDairyProgressDtlEntity.getSowId().getProjMstrEntity().getParentProjectMstrEntity().getProjectId());
					projectTangibleTO.setParentCode(workDairyProgressDtlEntity.getSowId().getProjMstrEntity().getParentProjectMstrEntity().getCode());
					projectTangibleTO.setParentName(workDairyProgressDtlEntity.getSowId().getProjMstrEntity().getParentProjectMstrEntity().getProjName());
					projectTangibleResp.getProjectTangibleTOs().add(projectTangibleTO);
				}
			}
		} else {
			for (Long projectId : projectTangibleReq.getProjectIds()) {
				List<ProjSOWItemEntity> projSOWItemEntities = projSOWItemRepository.findAllTangibleItems(projectId, 1);
				for (ProjSOWItemEntity projSOWItemEntity : projSOWItemEntities) {
					if (projSOWItemEntity.getTangibleClassificationEntity() != null) {
						ProjectTangibleTO projectTangibleTO = new ProjectTangibleTO();
						projectTangibleTO.setTangibleCode(projSOWItemEntity.getTangibleClassificationEntity().getCode());
						projectTangibleTO.setTangibleItemId(projSOWItemEntity.getTangibleClassificationEntity().getId());
						projectTangibleTO.setTangibleName(projSOWItemEntity.getTangibleClassificationEntity().getName());
						projectTangibleTO.setProjId(projSOWItemEntity.getProjMstrEntity().getProjectId());
						projectTangibleTO.setName(projSOWItemEntity.getProjMstrEntity().getProjName());
						projectTangibleTO.setCode(projSOWItemEntity.getProjMstrEntity().getCode());
						projectTangibleTO.setParentProjId(projSOWItemEntity.getProjMstrEntity().getParentProjectMstrEntity().getProjectId());
						projectTangibleTO.setParentCode(projSOWItemEntity.getProjMstrEntity().getParentProjectMstrEntity().getCode());
						projectTangibleTO.setParentName(projSOWItemEntity.getProjMstrEntity().getParentProjectMstrEntity().getProjName());
						projectTangibleResp.getProjectTangibleTOs().add(projectTangibleTO);
					}
				}
			}
		}

		return projectTangibleResp;
	}

	@Override
	public ProjectTangibleResp getManpowerProductivityAnalysisReportData(ProjectTangibleReq projectTangibleReq) {
		ProjectTangibleResp projectTangibleResp = new ProjectTangibleResp();
		List<WorkDairyEntity> workDairyEntities = workDairyRepositoryCopy.findAll(projectTangibleReq.getProjectIds());
		for (WorkDairyEntity workDairyEntity : workDairyEntities) {
			List<WorkDairyProgressDtlEntity> workDairyProgressDtlEntities = progressWorkDairyRepository.findWorkDairyProgressDetails(workDairyEntity.getId(), workDairyEntity.getStatus());

			/* Commented the for loop because we are getting the 3 values from the database but we want only one value i.e.., Approved or Client Approved Or Null thats why i am taking the 1st value */
				
			//	for (WorkDairyProgressDtlEntityCopy workDairyProgressDtlEntity : workDairyProgressDtlEntities) {
			if(workDairyProgressDtlEntities.size() > 0) {
				if (projectTangibleReq.getProjectTangibleTOs().stream().anyMatch(pt -> pt.getTangibleItemId().equals(workDairyProgressDtlEntities.get(0).getSowId().getTangibleClassificationEntity().getId()))) {
					ProjectTangibleTO projectTangibleTO = new ProjectTangibleTO();
					projectTangibleTO.setDate(workDairyProgressDtlEntities.get(0).getWorkDairyId().getWorkDairyDate());
					projectTangibleTO.setTangibleCode(workDairyProgressDtlEntities.get(0).getSowId().getTangibleClassificationEntity().getCode());
					projectTangibleTO.setTangibleItemId(workDairyProgressDtlEntities.get(0).getSowId().getTangibleClassificationEntity().getId());
					projectTangibleTO.setTangibleName(workDairyProgressDtlEntities.get(0).getSowId().getTangibleClassificationEntity().getName());
					projectTangibleTO.setProjId(workDairyProgressDtlEntities.get(0).getSowId().getProjMstrEntity().getProjectId());
					projectTangibleTO.setName(workDairyProgressDtlEntities.get(0).getSowId().getProjCostItemEntity().getName());
					projectTangibleTO.setCode(workDairyProgressDtlEntities.get(0).getSowId().getProjCostItemEntity().getCode());
					projectTangibleTO.setParentProjId(workDairyProgressDtlEntities.get(0).getSowId().getProjMstrEntity().getParentProjectMstrEntity().getProjectId());
					projectTangibleTO.setParentCode(workDairyProgressDtlEntities.get(0).getSowId().getProjMstrEntity().getParentProjectMstrEntity().getCode());
					projectTangibleTO.setParentName(workDairyProgressDtlEntities.get(0).getSowId().getProjMstrEntity().getCode());
					projectTangibleTO.setSowId(workDairyProgressDtlEntities.get(0).getSowId().getId());
					projectTangibleTO.setSowCode(workDairyProgressDtlEntities.get(0).getSowId().getCode());
					projectTangibleTO.setSowName(workDairyProgressDtlEntities.get(0).getSowId().getName());
					projectTangibleTO.setUom(workDairyProgressDtlEntities.get(0).getSowId().getProjSOEItemEntity().getMeasurmentMstrEntity().getCode());
					projectTangibleTO.setEstimatedQuantity(workDairyProgressDtlEntities.get(0).getSowId().getProjSOEItemEntity().getQuantity());
					double actqty = 0.00;
					if(workDairyProgressDtlEntities.get(0).getApprStatus() != null && workDairyProgressDtlEntities.get(0).getApprStatus().equals("Approved")) {
						actqty = workDairyProgressDtlEntities.get(0).getValue();
					}
					projectTangibleTO.setEstimatedHours(BigDecimal.valueOf(workDairyProgressDtlEntities.get(0).getSowId().getProjSOEItemEntity().getManHours()));
					projectTangibleTO.setActualQuantity(BigDecimal.valueOf(workDairyProgressDtlEntities.get(0).getValue()));
					projectTangibleTO.setActualHours(BigDecimal.ZERO);
					projectTangibleResp.upsert(projectTangibleTO);
				}
			}
		//	}

			List<WorkDairyEmpDtlEntity> workDairyEmpDtlEntities = workDairyEmpDtlRepositoryCopy.findWorkDairyEmpDetails(workDairyEntity.getId(), workDairyEntity.getStatus());
			for (WorkDairyEmpDtlEntity workDairyEmpDtlEntity : workDairyEmpDtlEntities) {
	            for (WorkDairyEmpWageEntity workDairyEmpWageEntity : workDairyEmpDtlEntity.getWorkDairyEmpWageEntities()) {
	                for (WorkDairyEmpCostEntity workDairyEmpCostEntity : workDairyEmpWageEntity.getWorkDairyEmpCostEntities()) {
	                	List<ProjSOWItemEntity> projSOWItemEntities = projSOWItemRepository.findAllTangibleItems(workDairyEmpCostEntity.getWorkDairyId().getProjId().getProjectId(), 1);
	                	for (ProjSOWItemEntity projSOWItemEntity : projSOWItemEntities) {
	                		if (projSOWItemEntity.getProjCostItemEntity().getId().equals(workDairyEmpCostEntity.getCostId().getId())) {
	                			if (projectTangibleReq.getProjectTangibleTOs().stream().anyMatch(pt -> pt.getTangibleItemId().equals(projSOWItemEntity.getTangibleClassificationEntity().getId()))) {
	                				ProjectTangibleTO projectTangibleTO = new ProjectTangibleTO();
	            					projectTangibleTO.setDate(workDairyEmpDtlEntity.getWorkDairyEntity().getWorkDairyDate());
	            					projectTangibleTO.setTangibleCode(projSOWItemEntity.getTangibleClassificationEntity().getCode());
	            					projectTangibleTO.setTangibleItemId(projSOWItemEntity.getTangibleClassificationEntity().getId());
	            					projectTangibleTO.setTangibleName(projSOWItemEntity.getTangibleClassificationEntity().getName());
	            					projectTangibleTO.setProjId(projSOWItemEntity.getProjMstrEntity().getProjectId());
	            					projectTangibleTO.setName(projSOWItemEntity.getProjCostItemEntity().getName());
	            					projectTangibleTO.setCode(projSOWItemEntity.getProjCostItemEntity().getCode());
	            					projectTangibleTO.setParentProjId(projSOWItemEntity.getProjMstrEntity().getParentProjectMstrEntity().getProjectId());
	            					projectTangibleTO.setParentCode(projSOWItemEntity.getProjMstrEntity().getParentProjectMstrEntity().getCode());
	            					projectTangibleTO.setParentName(projSOWItemEntity.getProjMstrEntity().getCode());
	            					projectTangibleTO.setSowId(projSOWItemEntity.getId());
	            					projectTangibleTO.setSowCode(projSOWItemEntity.getCode());
	            					projectTangibleTO.setSowName(projSOWItemEntity.getName());
	            					projectTangibleTO.setUom(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity().getCode());
	            					projectTangibleTO.setEstimatedQuantity((projSOWItemEntity.getProjSOEItemEntity().getQuantity()));
	            					projectTangibleTO.setEstimatedHours(BigDecimal.valueOf(projSOWItemEntity.getProjSOEItemEntity().getManHours()));
	            					projectTangibleTO.setActualQuantity(BigDecimal.ZERO);
	            					double actualHours = 0.0;
	            					for (WorkDairyEmpWageEntity wd : workDairyEmpDtlEntity.getWorkDairyEmpWageEntities()) {
	            						if (wd.getApprStatus() != null && wd.getApprStatus().equals("Approved")) {
		            						actualHours += (wd.getUsedTotal()+wd.getIdleTotal());
	            					}}
	            					if(workDairyEntity.getApprStatus() != null && workDairyEntity.getApprStatus().equalsIgnoreCase("Approved")) {
	            					projectTangibleTO.setActualHours(BigDecimal.valueOf(actualHours/4));
	            					}else if(workDairyEntity.getApprStatus() != null && workDairyEntity.getApprStatus().equalsIgnoreCase("Client Approved")) {
		            					projectTangibleTO.setActualHours(BigDecimal.valueOf(actualHours/16));
		            					}
	            					projectTangibleResp.upsert(projectTangibleTO);
	                			}
	                		}
	                	}
	                }
	            }
			}
		}

		return projectTangibleResp;
	}

	@Override
	public ProjectGanntChartResp getProjectsGanttChartReportData(ProjGeneralsGetReq projGeneralsGetReq) {
		ProjectGanntChartResp projectGanntChartResp = new ProjectGanntChartResp();
		
		for (Long projectId : projGeneralsGetReq.getProjIds()) {
			if (resourceAssignmentDataRepository.findMinimumStartDateOfCurrentBy(projectId) == null) continue;
			ProjectGanttChartTO projectGanttChartTO = new ProjectGanttChartTO();
			List<ProjMstrEntity> projMstrEntities = entpsProjRepositorCopy.getProjCode(projectId);
			projectGanttChartTO.setProjId(projMstrEntities.get(0).getProjectId());
			projectGanttChartTO.setCode(projMstrEntities.get(0).getCode());
			projectGanttChartTO.setName(projMstrEntities.get(0).getProjName());
			projectGanttChartTO.setParentProjId(projMstrEntities.get(0).getParentProjectMstrEntity().getProjectId());
			projectGanttChartTO.setParentCode(projMstrEntities.get(0).getParentProjectMstrEntity().getCode());
			projectGanttChartTO.setParentName(projMstrEntities.get(0).getParentProjectMstrEntity().getProjName());
			projectGanttChartTO.setPlannedStartDate(resourceAssignmentDataRepository.findMinimumStartDateOfCurrentBy(projectId));
			projectGanttChartTO.setPlannedFinishDate(resourceAssignmentDataRepository.findMaximumFinishDateOfCurrentBy(projectId));
			projectGanttChartTO.setBaselineStartDate(resourceAssignmentDataRepository.findMinimumStartDateOfBaselineBy(projectId));
			projectGanttChartTO.setBaselineFinishDate(resourceAssignmentDataRepository.findMaximumFinishDateOfBaselineBy(projectId));
			
			List<Long> projectIds = new ArrayList<Long>();
			projectIds.add(projectId);
			Date minStartDate  = projectGanttChartTO.getBaselineStartDate();
			Date maxFinishDate = projectGanttChartTO.getBaselineStartDate();
			if (minStartDate.after(projectGanttChartTO.getPlannedStartDate())) minStartDate = projectGanttChartTO.getPlannedStartDate();
			if (maxFinishDate.before(projectGanttChartTO.getPlannedStartDate())) maxFinishDate = projectGanttChartTO.getPlannedStartDate();
			double budget = 0.0;
			List<ResourceAssignmentDataValueEntity> currentResourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllCurrent(projectIds, minStartDate, new Date()); 
			for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : currentResourceAssignmentDataValueEntities) { 
				if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals(ResourceAssignmentDataHandler.POT_COST)) continue;
				budget += resourceAssignmentDataValueEntity.getBudgetUnits();
			}
			
			List<Object[]> dailyEarnedValues = projSowTotalActualRepository.getDailyEarnedValuePerCostCode(projectIds, minStartDate, new Date());
			double earnedValue = 0.0;
			for (Object[] dailyEarned : dailyEarnedValues) {
		        	double value = (double) dailyEarned[2];
		        	BigDecimal rate = (BigDecimal) dailyEarned[3];
		        	earnedValue += value * rate.doubleValue();
	        }
			
			projectGanttChartTO.setProgress((earnedValue / budget) * 100);
			projectGanntChartResp.getProjectGanttChartTOs().add(projectGanttChartTO);
		}
		
		return projectGanntChartResp;
	}
	
	//This function used for approval process of the project budgets like Submit For Approval and Approval functionality
	public ProjBudgetResp projectBudgetApproval( ProjGeneralsGetReq projGeneralsGetReq ) {
		System.out.println("projectBudgetApproval function of ProjSettingsServiceImpl class");
		ProjBudgetResp projBudgetResp = new ProjBudgetResp();
		System.out.println("manpower ids size:"+projGeneralsGetReq.getManpowerIds().size());
		System.out.println("getPlantIds ids size:"+projGeneralsGetReq.getPlantIds().size());
		System.out.println("getMaterialIds ids size:"+projGeneralsGetReq.getMaterialIds().size());
		System.out.println("getCostStatementIds ids size:"+projGeneralsGetReq.getCostStatementIds().size());	
		System.out.println("getCostItemIds ids size:"+projGeneralsGetReq.getCostItemIds().size());	
		Long loggedInUser = AppUserUtils.getUserId();
		
		if( projGeneralsGetReq.getManpowerIds().size() != 0 )
		{
			if( projGeneralsGetReq.getItemStatus().equals("SUBMITTED_FOR_APPROVAL") )
			{
				projManpowerRepository.updateApproverDetailsByIds( projGeneralsGetReq.getManpowerIds(), projGeneralsGetReq.getApproverUserId(), loggedInUser, projGeneralsGetReq.getItemStatus() );
			}
			else
			{
				projManpowerRepository.updateApprovalStatusByIds( projGeneralsGetReq.getManpowerIds(), projGeneralsGetReq.getItemStatus(), StatusCodes.ACTIVE.getValue() );
			}			
			for( Long manpowerId : projGeneralsGetReq.getManpowerIds() )
			{
				projBudgetResp.getManpowerIds().add( manpowerId );
			}
		}
		if( projGeneralsGetReq.getPlantIds().size() != 0 ) {
			//projectPlantsRepository.updateApprovalStatusByIds( projGeneralsGetReq.getPlantIds(), projGeneralsGetReq.getApproverUserId(), projGeneralsGetReq.getItemStatus() );
			if( projGeneralsGetReq.getItemStatus().equals("SUBMITTED_FOR_APPROVAL") )
			{
				projectPlantsRepository.updateApproverDetailsByIds( projGeneralsGetReq.getPlantIds(), projGeneralsGetReq.getApproverUserId(), loggedInUser, projGeneralsGetReq.getItemStatus() );
			}
			else
			{
				projectPlantsRepository.updateApprovalStatusByIds( projGeneralsGetReq.getPlantIds(), projGeneralsGetReq.getItemStatus(), StatusCodes.ACTIVE.getValue() );
			}
			for( Long plantId : projGeneralsGetReq.getPlantIds() )
			{
				projBudgetResp.getPlantIds().add( plantId );
			}
		}
		if( projGeneralsGetReq.getMaterialIds().size() != 0 ) {
			//projectMaterialRepository.updateApprovalStatusByIds( projGeneralsGetReq.getMaterialIds(), projGeneralsGetReq.getApproverUserId(), projGeneralsGetReq.getItemStatus() );
			if( projGeneralsGetReq.getItemStatus().equals("SUBMITTED_FOR_APPROVAL") )
			{
				projectMaterialRepository.updateApproverDetailsByIds( projGeneralsGetReq.getMaterialIds(), projGeneralsGetReq.getApproverUserId(), loggedInUser, projGeneralsGetReq.getItemStatus() );
			}
			else
			{
				projectMaterialRepository.updateApprovalStatusByIds( projGeneralsGetReq.getMaterialIds(), projGeneralsGetReq.getItemStatus(), StatusCodes.ACTIVE.getValue() );
			}
			for( Long materialId : projGeneralsGetReq.getMaterialIds() )
			{
				projBudgetResp.getMaterialIds().add( materialId );
			}
		}
		if( projGeneralsGetReq.getCostStatementIds().size() != 0 ) {
			if( projGeneralsGetReq.getCostItemIds().size() != 0 )
			{
				System.out.println("costItemIds size:"+projGeneralsGetReq.getCostItemIds().size());
				for( Long costItemId : projGeneralsGetReq.getCostItemIds() )
				{
					System.out.println("costItemId:"+costItemId);
				}
			}
			//projCostStatementsRepository.updateApprovalStatusByIds( projGeneralsGetReq.getCostStatementIds(), projGeneralsGetReq.getApproverUserId(), projGeneralsGetReq.getItemStatus() );
			if( projGeneralsGetReq.getItemStatus().equals("SUBMITTED_FOR_APPROVAL") )
			{
				projCostStatementsRepository.updateApproverDetailsByIds( projGeneralsGetReq.getCostStatementIds(), projGeneralsGetReq.getApproverUserId(), loggedInUser, projGeneralsGetReq.getItemStatus() );
				projCostItemRepository.updateProjCostItemStatus( projGeneralsGetReq.getCostItemIds(), projGeneralsGetReq.getItemStatus(), StatusCodes.DEFAULT.getValue() );
			}
			else
			{
				projCostStatementsRepository.updateApprovalStatusByIds( projGeneralsGetReq.getCostStatementIds(), projGeneralsGetReq.getItemStatus(), StatusCodes.ACTIVE.getValue() );
				projCostItemRepository.updateProjCostItemStatus( projGeneralsGetReq.getCostItemIds(), projGeneralsGetReq.getItemStatus(), StatusCodes.ACTIVE.getValue() );
			}
			for( Long costStatementId : projGeneralsGetReq.getCostStatementIds() )
			{
				projBudgetResp.getCostStatementIds().add( costStatementId );
			}
		}
		return projBudgetResp;
	}
	
	// This function used for return with comments of the project budgets
	public ProjBudgetResp projectBudgetReturn( ProjGeneralsGetReq projGeneralsGetReq ) {
		System.out.println("projectBudgetReturn function of ProjSettingsServiceImpl");
		ProjBudgetResp projBudgetResp = new ProjBudgetResp();
		for( Long manpowerId : projGeneralsGetReq.getManpowerIds() )
		{
			Integer isItemReturned = 0;
			if( projGeneralsGetReq.getManpowerId() != null && manpowerId.compareTo( projGeneralsGetReq.getManpowerId() ) == 0 )
			{
				projManpowerRepository.updateReturnItemDetailsById( projGeneralsGetReq.getManpowerId(), AppUserUtils.getUserId(), 1, projGeneralsGetReq.getComments(), projGeneralsGetReq.getItemStatus() );
			}
			else
			{
				projManpowerRepository.updateManpowerItemStatusById( manpowerId, projGeneralsGetReq.getItemStatus() );
			}
			//projManpowerRepository.updateReturnItemDetailsById( projGeneralsGetReq.getManpowerId(), AppUserUtils.getUserId(), 1, projGeneralsGetReq.getItemStatus() );
			projBudgetResp.getManpowerIds().add( manpowerId );
		}
		for( Long plantId : projGeneralsGetReq.getPlantIds() )
		{
			Integer isItemReturned = 0;
			if( projGeneralsGetReq.getPlantId() != null && plantId.compareTo( projGeneralsGetReq.getPlantId() ) == 0 )
			{
				projectPlantsRepository.updateReturnItemDetailsById( projGeneralsGetReq.getPlantId(), AppUserUtils.getUserId(), 1, projGeneralsGetReq.getComments(), projGeneralsGetReq.getItemStatus() );
			}
			else
			{
				projectPlantsRepository.updatePlantItemStatusById( plantId, projGeneralsGetReq.getItemStatus() );
			}
			projBudgetResp.getPlantIds().add( plantId );
		}	
		for( Long materialId : projGeneralsGetReq.getMaterialIds() )
		{
			Integer isItemReturned = 0;
			if( projGeneralsGetReq.getMaterialId() != null && materialId.compareTo( projGeneralsGetReq.getMaterialId() ) == 0 )
			{
				projectMaterialRepository.updateReturnItemDetailsById( projGeneralsGetReq.getMaterialId(), AppUserUtils.getUserId(), 1, projGeneralsGetReq.getComments(), projGeneralsGetReq.getItemStatus() );
			}
			else
			{
				projectMaterialRepository.updateBudgetMaterialItemStatusById( materialId, projGeneralsGetReq.getItemStatus() );
			}
			projBudgetResp.getMaterialIds().add( materialId );
		}
		for( Long costStatementId : projGeneralsGetReq.getCostStatementIds() )
		{
			Integer isItemReturned = 0;
			if( projGeneralsGetReq.getCostStatementId() != null && costStatementId.compareTo( projGeneralsGetReq.getCostStatementId() ) == 0 )
			{
				projCostStatementsRepository.updateReturnItemDetailsById( projGeneralsGetReq.getCostStatementId(), AppUserUtils.getUserId(), 1, projGeneralsGetReq.getComments(), projGeneralsGetReq.getItemStatus() );
			}
			else
			{
				projCostStatementsRepository.updateBudgetCostStmtItemStatusById( costStatementId, projGeneralsGetReq.getItemStatus() );
			}
			projBudgetResp.getCostStatementIds().add( costStatementId );
		}
		if( projGeneralsGetReq.getCostItemIds().size() != 0 )
		{
			for( Long costItemId : projGeneralsGetReq.getCostItemIds() )
			{
				if( costItemId.compareTo( projGeneralsGetReq.getCostItemId() ) == 0 )
				{
					projCostItemRepository.updateReturnCostItemStatusById( costItemId, projGeneralsGetReq.getItemStatus(), 1 );
				}
				else
				{
					projCostItemRepository.updateReturnCostItemStatusById( costItemId, projGeneralsGetReq.getItemStatus() );
				}				
				projBudgetResp.getCostItemIds().add( costItemId );
			}
		}
		return projBudgetResp;
	}
	
	public void saveProjSchofEstimates(SchofEstimatesSaveReq schofEstimatesSaveReq) {
		schofEstimatesRepository.save(SchofEstimatesHandler.convertPOJOToEntity(schofEstimatesSaveReq.getSchofEstimatesApprTOs(), epsProjRepository));
	}
	
	public ProjSchofEstimatesResp getProjSchofEstimates(SchofEstimatesGetReq schofEstimatesGetReq) {
		ProjSchofEstimatesResp projSchofEstimatesResp = new ProjSchofEstimatesResp();
		List<SchofEstimateNormalTimeEntity> schofEstimates = schofEstimatesRepository.findProjSchofEstimates(schofEstimatesGetReq.getProjId(), schofEstimatesGetReq.getStatus());
		for(SchofEstimateNormalTimeEntity schofEstimateNormalTimeEntity : schofEstimates) {
			projSchofEstimatesResp.getSchofEstimatesApprTOs().add(SchofEstimatesHandler.convertEntityToPOJO(schofEstimateNormalTimeEntity));
		}
		return projSchofEstimatesResp;
	}
	
	public void saveProjSchofRates(SchofRatesSaveReq schofRatesSaveReq) {
		schofRatesRepository.save(SchofRatesHandler.convertPOJOToEntity(schofRatesSaveReq.getSchofRatesApprTOs(), epsProjRepository));
	}
	
	public ProjSchofRatesResp getProjSchofRates(SchofRatesGetReq schofRatesGetReq) {
		ProjSchofRatesResp projSchofRates = new ProjSchofRatesResp();
		List<SchofRatesNormalTimeEntity> schofRates = schofRatesRepository.findProjSchofRates(schofRatesGetReq.getProjId(), schofRatesGetReq.getStatus());
		for(SchofRatesNormalTimeEntity schofRatesNormalTimeEntity : schofRates) {
			projSchofRates.getSchofRatesApprTOs().add(SchofRatesHandler.convertEntityToPOJO(schofRatesNormalTimeEntity));
		}
		return projSchofRates;
	}
	
	public void saveProjResBudget(ResourceBudgetSaveReq resBudgetSaveReq) {
		resourceBudgetRepository.save(ResourceBudgetHandler.convertPOJOToEntity(resBudgetSaveReq.getResourceBudgetTOs(), epsProjRepository));
	}
	
	public ProjResourceBudgetResp getProjResBudget(ResourceBudgetGetReq resBudgetGetReq) {
		ProjResourceBudgetResp projResourceBudget = new ProjResourceBudgetResp();
		List<ResourceBudgetNormalTimeEntity> resourceBudget = resourceBudgetRepository.findProjResBudget(resBudgetGetReq.getProjId(), resBudgetGetReq.getStatus());
		System.out.println(resourceBudget.size());
		for(ResourceBudgetNormalTimeEntity resourceBudgetNormalTimeEntity : resourceBudget) {
			projResourceBudget.getResourceBudgetTOs().add(ResourceBudgetHandler.convertEntityToPOJO(resourceBudgetNormalTimeEntity));
		}
		return projResourceBudget;
	}
	
	public void saveSoeAppr(ProjSoeApprSaveReq projSoeApprSaveReq) {
		System.out.println("projSoeApprSaveReq.getProjSoeApprTOs()"+projSoeApprSaveReq.getProjSoeApprTOs());
		String status = "Approved";
		for (ProjSoeApprTO projsoeApprTO : projSoeApprSaveReq.getProjSoeApprTOs()) {
			// setting to AdditionalTime Entity
			System.out.println("projsoeApprTO.getNotificationId()"+projsoeApprTO.getNotificationId());
			SoeAddtionalTimeApprEntity soeAddlTimeApprEntity = soeAddltionalTimeRepository
					.findOne(projsoeApprTO.getNotificationId());  

			soeAddlTimeApprEntity.setStatus("Approved");
			soeAddlTimeApprEntity.setNotificationStatus("Approved");
			sendProjSoeApprMail(soeAddlTimeApprEntity);
		}
		
	}
	private void sendProjSoeApprMail(SoeAddtionalTimeApprEntity soeAddlTimeApprEntity) {
		String apprName = "";
		String epsName = "";
		String projName = "";
		String toEmail = null;
		String ccEmail = "";
		String code = "";
		String text = null;
		String timeSheetNumber = "";
		Long projId = null;
		String generatedCode = "";
		Date weekStartDate = new Date();
		/*
		 * if (null != timeSheetAdditionalTimeEntity) {
		 * log.info("timeSheetAdditionalTimeEntity IS NOT NULL"); } else {
		 * log.info("timeSheetAdditionalTimeEntity IS NULL"); }
		 */
		if (null != soeAddlTimeApprEntity) {

			ProjMstrEntity projMstrEntity = soeAddlTimeApprEntity.getSoeNotificationsEntity().getProjSOEItemEntity().getProjMstrEntity();

			projId = projMstrEntity.getProjectId();

			List<ProjMstrEntity> projEntityCode = entpsProjRepositorCopy.getProjCode(projId);
			// List<ProjMstrEntity> projEntityCode =
			// entpsProjRepositorCopy.getProjCode(timeSheetNotificationsEntity.getTimeSheetEntity().getProjMstrEntity().getProjectId());
			if (CommonUtil.objectNotNull(projEntityCode)) {
				for (ProjMstrEntity projMstr : projEntityCode) {
					code = projMstr.getCode();
					projName = projMstr.getProjName();
					epsName = projMstr.getParentProjectMstrEntity().getProjName();
				}
			}

			if (soeAddlTimeApprEntity.getApprUser() != null) {
				apprName = soeAddlTimeApprEntity.getApprUser().getDisplayName();
			}
			toEmail = soeAddlTimeApprEntity.getApprUser().getEmail();
			log.info("toEmail " + toEmail);
			String toSubject = "Approval of Additional Time of SOE - " + code;
			text = "<html><body><p>" + apprName + ",</p>" + "<p>I have approved SOE through " + pot
					+ ", as per details mentioned here below.</p>" + "<table border='1'><tr><td>EPS </td><td>" + epsName
					+ "</td></tr><tr><td>Project </td><td>" + projName + "</td></tr><tr><td>Notification ID</td><td>"
					+"SOE-"+ epsName +"-"+ soeAddlTimeApprEntity.getProjId() + "</td></tr><tr><td>Date </td><td>" + weekStartDate
					+ "</td></tr></table><br>This is for your Information please." + "<p>Regards,</p>" + "<p>"
					+ AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
		}

	}
	
	

	
	// ....get change order details...

	public ChangeOrderDetailsResp getProjChangeOrderDetail(ChangeOrderDetailsGetReq ChangeOrderDetailsGetReq) {
		ChangeOrderDetailsResp changeOrderDetailsResp = new ChangeOrderDetailsResp();
		List<ChangeOrderNormalTimeEntity> ChangeorderDetails= changeOrderNormaltimeEntityRepository.findProjChangeOrder(ChangeOrderDetailsGetReq.getProjId(), ChangeOrderDetailsGetReq.getStatus());
		for(ChangeOrderNormalTimeEntity changeorderDetails : ChangeorderDetails) {
			changeOrderDetailsResp.getProjChangeOrderDetailsTOs().add(ProjChangeorderdetailsHandler.convertEntityToPOJO(changeorderDetails));
		}
		return changeOrderDetailsResp;
	}
	
	//save change order details
	public void saveProjChangeOrderDetail(ChangeOrderDetailsSaveReq changeOrderDetailsSaveReq) {
		changeOrderNormaltimeEntityRepository.save(ProjChangeorderdetailsHandler.convertPOJOToEntity(changeOrderDetailsSaveReq.getProjChangeOrderDetailsTOs(), epsProjRepository));
	}	
	
	
	
	
}