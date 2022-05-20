package com.rjtech.projectlib.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.ProjLeaveCategoryType;
import com.rjtech.centrallib.model.ProjLeaveTypeEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.CostCodeRepository;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.repository.ProjLeaveTypeRepository;
import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.CostActualHoursTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.service.impl.CommonEmailServiceImpl;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.proj.settings.common.service.ActualAmountService;
import com.rjtech.projectlib.dto.ChangeOrderTO;
import com.rjtech.projectlib.dto.CoProjManpowerTO;
import com.rjtech.projectlib.dto.CoProjPlantTO;
import com.rjtech.projectlib.dto.PeriodCostTO;
import com.rjtech.projectlib.dto.ProjCostBudgetTOCopy;
import com.rjtech.projectlib.dto.ProjCostItemTO;
import com.rjtech.projectlib.dto.ProjCostStmtDtlTOCopy;
import com.rjtech.projectlib.dto.ProjCrewTO;
import com.rjtech.projectlib.dto.ProjLeaveTypeTO;
import com.rjtech.projectlib.dto.ProjMaterialClassTO;
import com.rjtech.projectlib.dto.ProjPMCMItemTO;
import com.rjtech.projectlib.dto.ProjSOEActivityLogTO;
import com.rjtech.projectlib.dto.ProjSOEItemTO;
import com.rjtech.projectlib.dto.ProjSORActivityLogTO;
import com.rjtech.projectlib.dto.ProjSORItemTO;
import com.rjtech.projectlib.dto.ProjSOWItemTO;
import com.rjtech.projectlib.dto.ProjStoreStockTO;
import com.rjtech.projectlib.dto.ProjWorkShiftTO;
import com.rjtech.projectlib.dto.ReportPMCMValueTO;
import com.rjtech.projectlib.model.ChangeOrderMapEntity;
import com.rjtech.projectlib.model.ChangeOrderMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.projectlib.model.ProjEmpClassMstrEntity;
import com.rjtech.projectlib.model.ProjMaterialMstrEntity;
import com.rjtech.projectlib.model.ProjPMCMItemEntity;
import com.rjtech.projectlib.model.ProjPlantClassMstrEntity;
import com.rjtech.projectlib.model.ProjSOEActivityLogEntity;
import com.rjtech.projectlib.model.ProjSOEItemEntity;
import com.rjtech.projectlib.model.ProjSOETrackRecordsEntity;
import com.rjtech.projectlib.model.ProjSORActivityLogEntity;
import com.rjtech.projectlib.model.ProjSORItemEntity;
import com.rjtech.projectlib.model.ProjSORTrackRecordsEntity;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
import com.rjtech.projectlib.model.ProjWorkShiftMstrEntity;
//import com.rjtech.projectlib.model.ProjectPlantsDtlEntityCopy;
import com.rjtech.projectlib.model.SoeNotificationsEntity;
import com.rjtech.projectlib.repository.ChangeOrderMapRepository;
import com.rjtech.projectlib.repository.ChangeOrderRepository;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
//import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjCrewRepository;
import com.rjtech.projectlib.repository.ProjEmpClassRepository;
//import com.rjtech.projectlib.repository.ProjManpowerRepositoryCopy;
import com.rjtech.projectlib.repository.ProjMaterialClassRepository;
import com.rjtech.projectlib.repository.ProjPMCMItemRepository;
import com.rjtech.projectlib.repository.ProjPlantClassRepository;
import com.rjtech.projectlib.repository.ProjSOEActivityLogRepository;
import com.rjtech.projectlib.repository.ProjSOEItemRepository;
import com.rjtech.projectlib.repository.ProjSOETrackRepository;
import com.rjtech.projectlib.repository.ProjSORActivityLogRepository;
import com.rjtech.projectlib.repository.ProjSORItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSORTrackRepository;
import com.rjtech.projectlib.repository.ProjSOWItemRepository;
import com.rjtech.projectlib.repository.ProjSowTotalActualProcRepository;
import com.rjtech.projectlib.repository.ProjSowTotalActualRepository;
import com.rjtech.projectlib.repository.ProjStoreStockRepository;
import com.rjtech.projectlib.repository.ProjWorkShiftRepository;
import com.rjtech.projectlib.repository.ProjectPlantsRepositoryCopy;
import com.rjtech.projectlib.repository.SoeNotificationRepository;
import com.rjtech.projectlib.req.ChangeOrderReq;
import com.rjtech.projectlib.req.ProMaterialClassGetReq;
import com.rjtech.projectlib.req.ProjCostItemDelReq;
import com.rjtech.projectlib.req.ProjCostItemGetReq;
import com.rjtech.projectlib.req.ProjCostItemSaveReq;
import com.rjtech.projectlib.req.ProjCrewDelReq;
import com.rjtech.projectlib.req.ProjCrewGetReq;
import com.rjtech.projectlib.req.ProjCrewSaveReq;
import com.rjtech.projectlib.req.ProjEmpClassDelReq;
import com.rjtech.projectlib.req.ProjEmpClassGetReq;
import com.rjtech.projectlib.req.ProjEmpClassSaveReq;
import com.rjtech.projectlib.req.ProjLeaveTypeDelReq;
import com.rjtech.projectlib.req.ProjLeaveTypeGetReq;
import com.rjtech.projectlib.req.ProjLeaveTypeSaveReq;
import com.rjtech.projectlib.req.ProjMaterialClassSaveReq;
import com.rjtech.projectlib.req.ProjMaterialDelReq;
import com.rjtech.projectlib.req.ProjPMCMItemGetReq;
import com.rjtech.projectlib.req.ProjPMCMItemSaveReq;
import com.rjtech.projectlib.req.ProjPMCPItemGetReq;
import com.rjtech.projectlib.req.ProjPlantClassDelReq;
import com.rjtech.projectlib.req.ProjPlantClassGetReq;
import com.rjtech.projectlib.req.ProjPlantClassSaveReq;
import com.rjtech.projectlib.req.ProjSOEItemDelReq;
import com.rjtech.projectlib.req.ProjSOEItemGetReq;
import com.rjtech.projectlib.req.ProjSOEItemSaveReq;
import com.rjtech.projectlib.req.ProjSOETrackGetReq;
import com.rjtech.projectlib.req.ProjSOETrackSaveReq;
import com.rjtech.projectlib.req.ProjSORItemDelReq;
import com.rjtech.projectlib.req.ProjSORItemGetReq;
import com.rjtech.projectlib.req.ProjSORItemSaveReq;
import com.rjtech.projectlib.req.ProjSORTrackGetReq;
import com.rjtech.projectlib.req.ProjSOWItemDelReq;
import com.rjtech.projectlib.req.ProjSOWItemGetReq;
import com.rjtech.projectlib.req.ProjSOWItemSaveReq;
import com.rjtech.projectlib.req.ProjStoreStockDelReq;
import com.rjtech.projectlib.req.ProjStoreStockGetReq;
import com.rjtech.projectlib.req.ProjStoreStockSaveReq;
import com.rjtech.projectlib.req.ProjWorkShiftDelReq;
import com.rjtech.projectlib.req.ProjWorkShiftGetReq;
import com.rjtech.projectlib.req.ProjWorkShiftSaveReq;
import com.rjtech.projectlib.req.ProjectLibOnLoadReq;
import com.rjtech.projectlib.resp.ChangeOrderResp;
import com.rjtech.projectlib.resp.ProjCostItemResp;
import com.rjtech.projectlib.resp.ProjCrewResp;
import com.rjtech.projectlib.resp.ProjEmpClassResp;
import com.rjtech.projectlib.resp.ProjLeaveTypeResp;
import com.rjtech.projectlib.resp.ProjMaterialClassResp;
import com.rjtech.projectlib.resp.ProjPMCMItemResp;
import com.rjtech.projectlib.resp.ProjPMCPCostStatementsResp;
import com.rjtech.projectlib.resp.ProjPMCPItemResp;
import com.rjtech.projectlib.resp.ProjPlantClassResp;
import com.rjtech.projectlib.resp.ProjRestrictedMaterialClassResp;
import com.rjtech.projectlib.resp.ProjSOEActivityLogResp;
import com.rjtech.projectlib.resp.ProjSOEItemResp;
import com.rjtech.projectlib.resp.ProjSOETrackLogResp;
import com.rjtech.projectlib.resp.ProjSORActivityLogResp;
import com.rjtech.projectlib.resp.ProjSORItemResp;
import com.rjtech.projectlib.resp.ProjSORTrackDetailsResp;
import com.rjtech.projectlib.resp.ProjSOWItemResp;
import com.rjtech.projectlib.resp.ProjSowItemsMapResp;
import com.rjtech.projectlib.resp.ProjStoreStockResp;
import com.rjtech.projectlib.resp.ProjWorkShiftResp;
import com.rjtech.projectlib.resp.SOWTotalActualQuantitiesResp;
import com.rjtech.projectlib.service.ProjLibService;
import com.rjtech.projectlib.service.handler.ProjCostItemHandler;
import com.rjtech.projectlib.service.handler.ProjCostStmtDtlHandlerCopy;
import com.rjtech.projectlib.service.handler.ProjCrewHandler;
import com.rjtech.projectlib.service.handler.ProjEmpClassHandler;
import com.rjtech.projectlib.service.handler.ProjLeaveTypeHandler;
import com.rjtech.projectlib.service.handler.ProjLibServiceHandler;
import com.rjtech.projectlib.service.handler.ProjMaterialClassHandler;
import com.rjtech.projectlib.service.handler.ProjPMCMItemHandler;
import com.rjtech.projectlib.service.handler.ProjPlantClassHandler;
import com.rjtech.projectlib.service.handler.ProjSOEItemHandler;
import com.rjtech.projectlib.service.handler.ProjSORItemHandler;
import com.rjtech.projectlib.service.handler.ProjSOWItemHandler;
import com.rjtech.projectlib.service.handler.ProjStoreStockHandler;
import com.rjtech.projectlib.service.handler.ProjWorkShiftHandler;
import com.rjtech.projschedule.repository.ProjManpowerRepositoryCopy;
import com.rjtech.projschedule.repository.ScheduleActivityDataRepository;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;
import com.rjtech.projsettings.model.ProjEstimateEntity;
import com.rjtech.projsettings.model.ProjManpowerEntity;
import com.rjtech.projsettings.model.ProjectPlantsDtlEntity;
import com.rjtech.projsettings.model.SoeAddtionalTimeApprEntity;
import com.rjtech.projsettings.register.emp.dto.EmpChargeOutRateTO;
import com.rjtech.projsettings.register.plant.dto.PlantChargeOutRateTO;
import com.rjtech.projsettings.repository.ProjCostStatementsRepository;
import com.rjtech.projsettings.repository.ProjEstimateRepository;
import com.rjtech.projsettings.repository.ProjGeneralRepository;
import com.rjtech.projsettings.repository.SoeAddltionalTimeRepository;
import com.rjtech.projsettings.service.handler.ProjManpowerHandler;
import com.rjtech.projsettings.service.handler.ProjectPlantsDtlHandler;
//import com.rjtech.register.PlantRegisterDtlEntityCopy;
import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpProjRegisterRepositoryCopy;
//import com.rjtech.register.repository.emp.EmpProjRegisterRepositoryCopy;
import com.rjtech.register.repository.plant.PlantChargeOutRateRepositoryCopy;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.CostItemReportTO;
import com.rjtech.reports.cost.resp.CostReportTO;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceRepository;
import com.rjtech.timemanagement.timesheet.repository.copy.TimeSheetEmpDtlRepositoryCopy;
import com.rjtech.timemanagement.timesheet.repository.copy.TimeSheetEmpExpenseRepositoryCopy;
import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantDtlEntity;
import com.rjtech.timemanagement.workdairy.repository.copy.EmpCostWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.MaterialStatusWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.PlantCostWorkDairyRepositoryCopy;
import com.rjtech.user.repository.UserRoleMapRepositoryCpy;
//import com.rjtech.workdairy.WorkDairyPlantDtlEntityCopy;
@Service(value = "projLibService")
@RJSService(modulecode = "projLibService")
@Transactional
public class ProjLibServiceImpl implements ProjLibService {
	
	private static String pot = "\"Project on Track\"";
	
    @Autowired
    private ProjEmpClassRepository projEmpClassRepository;

	@Autowired
	private ProjPlantClassRepository projPlantClassRepository;

	@Autowired
	private ProjSOEItemRepository projSOEItemRepository;

	@Autowired
	private ProjSORItemRepositoryCopy projSORItemRepository;

	@Autowired
	private ProjSOWItemRepository projSOWItemRepository;

	@Autowired
	private ProjMaterialClassRepository projMaterialClassRepository;

	@Autowired
	private ProjWorkShiftRepository projWorkShiftRepository;
	@Autowired
	private ProjStoreStockRepository projStoreStockRepository;
	@Autowired
	private ProjCrewRepository projCrewListRepository;

	@Autowired
	private ProjCostItemRepository projCostItemRepository;

	@Autowired
	private ProjLeaveTypeRepository projLeaveTypeRepository;

	@Autowired
	private MaterialClassRepository materialClassRepository;

	@Autowired
	private EmpClassRepository empClassRepository;

	@Autowired
	private PlantClassRepository plantClassRepository;

	@Autowired
	private ProjSowTotalActualProcRepository totalActualProcRepository;

	@Autowired
	private EPSProjRepository projRepository;

	@Autowired
	private CostCodeRepository costCodeRepository;

	@Autowired
	private MeasurementRepository measurementRepository;

	@Autowired
	private ClientRegRepository clientRegRepository;

	@Autowired
	private ProcureCatgRepository procureCatgRepository;

	@Autowired
	private ProjPMCMItemRepository projPMCMItemRepository;
	// for Actual Cost
	@Autowired
	private ProjCostStatementsRepository projCostStatementsRepository;

	@Autowired
	private ActualAmountService actualAmountService;

	@Autowired
	private ProjEstimateRepository projEstimateRepository;

	@Autowired
	private ProjSowTotalActualRepository projSowTotalActualRepository;

	@Autowired
	private ProjCostItemRepositoryCopy projCostItemRepositoryCopy;

	@Autowired
	private CompanyRepository companyRepository;

	// for Actual Cost Report
	@Autowired
	private PlantChargeOutRateRepositoryCopy plantChargeOutRateRepository;

	@Autowired
	private TimeSheetEmpExpenseRepositoryCopy timeSheetEmpExpenseRepository;

	@Autowired
	private MaterialStatusWorkDairyRepositoryCopy materialStatusWorkDairyRepository;

	@Autowired
	private EmpAttendanceRepository empAttendanceRepository;

	@Autowired
	private ProjGeneralRepository projGeneralRepository;

	@Autowired
	private EmpCostWorkDairyRepositoryCopy empCostWorkDairyRepository;

	@Autowired
	private EmpProjRegisterRepositoryCopy empProjRegisterRepository;

	@Autowired
	private ProjLeaveTypeRepository leaveTypeRepository;

	@Autowired
	private TimeSheetEmpDtlRepositoryCopy timeSheetEmpDtlRepository;

	@Autowired
	private PlantCostWorkDairyRepositoryCopy plantCostWorkDairyRepository;

	@Autowired
	private ScheduleActivityDataRepository scheduleActivityDataRepository;

	@Autowired
	private ProjSOEActivityLogRepository projSOEActivityLogRepository;

	@Autowired
	private ProjSORActivityLogRepository projSORActivityLogRepository;

	@Autowired
	private UserRoleMapRepositoryCpy userRoleMapRepository;

	@Autowired
	private CommonEmailServiceImpl commonEmail;

	@Autowired
	private SoeNotificationRepository soeNotificationRepository;

	@Autowired
	private ChangeOrderRepository changeOrderMstrRepository;

	@Autowired
	private ChangeOrderMapRepository coMstrMapRepository;

	@Autowired
	private ProjManpowerRepositoryCopy projManpowerRepositoryCopy;

	@Autowired
	private SoeAddltionalTimeRepository soeAddltionalTimeRepository;
	
	@Autowired
	private ProjSOETrackRepository projSOETrackRepository;
	
	@Autowired
	private ProjSORTrackRepository projSORTrackRepository;

	public ProjMaterialClassResp getProjMaterialClasses(ProMaterialClassGetReq proMaterialClassGetReq) {

		ProjMaterialClassResp projMaterialClassResp = new ProjMaterialClassResp();
		Map<Long, ProjMaterialMstrEntity> projMaterialClassMap = new HashMap<Long, ProjMaterialMstrEntity>();
		List<MaterialClassMstrEntity> materialClassMstrEntities = materialClassRepository
				.findAllMaterialsForClient(AppUserUtils.getClientId(), StatusCodes.ACTIVE.getValue());

		List<ProjMaterialMstrEntity> projMaterialMstrEntites = projMaterialClassRepository
				.findByProjectId(proMaterialClassGetReq.getProjId(), proMaterialClassGetReq.getStatus());
		for (ProjMaterialMstrEntity projMaterialMstrEntity : projMaterialMstrEntites) {
			projMaterialClassMap.put(projMaterialMstrEntity.getGroupId().getId(), projMaterialMstrEntity);
		}
		for (MaterialClassMstrEntity materialClassMstrEntity : materialClassMstrEntities) {
			projMaterialClassResp.getProjMaterialClassTOs()
					.add(ProjMaterialClassHandler.populateProjMaterials(materialClassMstrEntity, projMaterialClassMap));
		}
		return projMaterialClassResp;
	}

	public ProjRestrictedMaterialClassResp getProjRestrictedMaterialClasses(
			ProMaterialClassGetReq proMaterialClassGetReq) {
		ProjRestrictedMaterialClassResp resp = new ProjRestrictedMaterialClassResp();
		List<ProjMaterialMstrEntity> projMaterialMstrEntites = null;
		projMaterialMstrEntites = projMaterialClassRepository
				.getExternalProjMaterialMstrEntity(proMaterialClassGetReq.getProjId(), StatusCodes.ACTIVE.getValue());
		if (CommonUtil.isListHasData(projMaterialMstrEntites)) {
			for (ProjMaterialMstrEntity projMaterialMstrEntity : projMaterialMstrEntites) {
				resp.getRestrictedProjMaterail().add(projMaterialMstrEntity.getGroupId().getId());
			}
		}
		return resp;
	}

	public void saveProjMaterialClasses(ProjMaterialClassSaveReq projMaterialClassSaveReq) {

		List<ProjMaterialMstrEntity> projMaterialMstrEntites = new ArrayList<ProjMaterialMstrEntity>(
				ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		for (ProjMaterialClassTO projMaterialClassTO : projMaterialClassSaveReq.getProjMaterialClassTOs()) {
			if (CommonUtil.isNonBlankLong(projMaterialClassTO.getId())
					|| CommonUtil.objectNotNull(projMaterialClassTO.getInternalApproved())
					|| CommonUtil.objectNotNull(projMaterialClassTO.getExternalApproved())) {
				projMaterialMstrEntites.add(ProjMaterialClassHandler.convertPOJOToEntity(projMaterialClassTO,
						projRepository, materialClassRepository));
			}
		}
		projMaterialClassRepository.save(projMaterialMstrEntites);
	}

	public void deactivateMaterialClasses(ProjMaterialDelReq projMaterialDelReq) {
		List<ProjMaterialMstrEntity> projMaterialMstrEntities = new ArrayList<ProjMaterialMstrEntity>(
				ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		List<ProjMaterialMstrEntity> materialMstrEntities = projMaterialClassRepository
				.findAll(projMaterialDelReq.getProjMaterialClassIds());
		for (ProjMaterialMstrEntity projMaterialMstrEntity : materialMstrEntities) {
			if (CommonUtil.objectNotNull(projMaterialMstrEntity)) {
				projMaterialMstrEntity.setStatus(projMaterialDelReq.getStatus());
				projMaterialMstrEntities.add(projMaterialMstrEntity);
			}
		}
		projMaterialClassRepository.save(projMaterialMstrEntities);

	}

	@Override
	public ProjLeaveTypeResp getLeaveTypesByCountry(ProjLeaveTypeGetReq projLeaveTypeGetReq) {
		List<ProjLeaveTypeEntity> projLeaveTypeEntities = projLeaveTypeRepository.findByCountryCodeAndEffectiveFrom(
				projLeaveTypeGetReq.getCountryCode(), projLeaveTypeGetReq.getEffectiveFrom(),
				AppUserUtils.getClientId());
		ProjLeaveTypeResp projLeaveTypeResp = new ProjLeaveTypeResp();
		for (ProjLeaveTypeEntity projLeaveTypeEntity : projLeaveTypeEntities) {
			projLeaveTypeResp.getProjLeaveTypeTOs()
					.add(ProjLeaveTypeHandler.convertEntityToPOJO(projLeaveTypeEntity, true));
		}
		return projLeaveTypeResp;
	}

	public void saveProjLeaveTypes(ProjLeaveTypeSaveReq projLeaveTypeSaveReq) {
		List<ProjLeaveTypeEntity> leaveTypes = ProjLeaveTypeHandler.convertPOJOToEntity(
				projLeaveTypeSaveReq.getProjLeaveTypeTos(), procureCatgRepository, clientRegRepository);
		projLeaveTypeRepository.save(leaveTypes);
	}

	public void deleteProjLeaveTypes(ProjLeaveTypeDelReq projLeaveTypeDelReq) {
		List<ProjLeaveTypeEntity> ProjLeaveTypeEntites = new ArrayList<ProjLeaveTypeEntity>();
		List<ProjLeaveTypeEntity> leaveTypeEntites = projLeaveTypeRepository
				.findAll(projLeaveTypeDelReq.getProjLeaveTypeIds());
		for (ProjLeaveTypeEntity projLeaveTypeEntity : leaveTypeEntites) {
			if (CommonUtil.objectNotNull(projLeaveTypeEntity)) {
				projLeaveTypeEntity.setStatus(projLeaveTypeDelReq.getStatus());
				ProjLeaveTypeEntites.add(projLeaveTypeEntity);
			}
		}
		projLeaveTypeRepository.save(ProjLeaveTypeEntites);

	}

	public ProjEmpClassResp getProjEmpClasses(ProjEmpClassGetReq projEmpClassGetReq) {
		ProjEmpClassResp projEmpClassResp = new ProjEmpClassResp();
		Map<Long, ProjEmpClassMstrEntity> projEmpClassMap = new HashMap<Long, ProjEmpClassMstrEntity>();
		List<EmpClassMstrEntity> empClassMstrEntities = empClassRepository.findByClientId(AppUserUtils.getClientId(),
				StatusCodes.ACTIVE.getValue());

		List<ProjEmpClassMstrEntity> projEmpClassMstrEntities = projEmpClassRepository
				.findProjEmpClasses(projEmpClassGetReq.getProjId(), projEmpClassGetReq.getStatus());
		EmpClassMstrEntity empCassMstr = null;
		for (ProjEmpClassMstrEntity projEmpClassMstrEntity : projEmpClassMstrEntities) {
			empCassMstr = projEmpClassMstrEntity.getEmpClassMstrEntity();
			if (empCassMstr != null)
				projEmpClassMap.put(empCassMstr.getId(), projEmpClassMstrEntity);
		}
		for (EmpClassMstrEntity empClassMstrEntity : empClassMstrEntities) {
			projEmpClassResp.getProjEmpClassTOs()
					.add(ProjEmpClassHandler.populateProjEmployees(empClassMstrEntity, projEmpClassMap));
		}
		return projEmpClassResp;
	}

	public void saveProjEmpClasses(ProjEmpClassSaveReq empClassSaveReq) {

		List<ProjEmpClassMstrEntity> projEmpClassMstrEntities = new ArrayList<ProjEmpClassMstrEntity>(
				ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		projEmpClassMstrEntities.addAll(ProjEmpClassHandler.convertPOJOToEntity(empClassSaveReq.getProjEmpClassTOs(),
				empClassSaveReq.getProjId(), empClassRepository, projRepository));
		projEmpClassRepository.save(projEmpClassMstrEntities);
	}

	public void deleteEmpClasses(ProjEmpClassDelReq projEmpClassDelReq) {
		projEmpClassRepository.deactivateProjEmpClass(projEmpClassDelReq.getProjEmpClassIds(),
				projEmpClassDelReq.getStatus());
	}

	public ProjSOEItemResp getProjSOEDetails(ProjSOEItemGetReq projSOEItemGetReq) {
		ProjSOEItemResp projSOEItemResp = new ProjSOEItemResp();
		String status = "Approved";
		SoeAddtionalTimeApprEntity soeAddtionalTimeApprEntity = soeAddltionalTimeRepository
				.findOne(projSOEItemGetReq.getProjId(), status);
		List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository
				.findSOEDetails(projSOEItemGetReq.getProjId());
		for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
			projSOEItemResp.getProjSOEItemTOs()
					.add(ProjSOEItemHandler.populateProjSOEITems(projSOEItemEntity, true, userRoleMapRepository));

		}
		for (ProjSOEItemTO projSOEItemTO : projSOEItemResp.getProjSOEItemTOs()) {
			if (CommonUtil.objectNotNull(soeAddtionalTimeApprEntity)) {
				projSOEItemTO.setAddlDate(soeAddtionalTimeApprEntity.getUpdatedOn());
			}

		}
		return projSOEItemResp;
	}

	public ProjSOEItemResp getProjSOEDetailsById(ProjSOEItemGetReq projSOEItemGetReq) {
		ProjSOEItemResp projSOEItemResp = new ProjSOEItemResp();
		List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository
				.findSOEItemsById(projSOEItemGetReq.getProjId(), projSOEItemGetReq.getSoeId());
		for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
			projSOEItemResp.getProjSOEItemTOs()
					.add(ProjSOEItemHandler.populateProjSOEITems(projSOEItemEntity, true, userRoleMapRepository));
		}
		return projSOEItemResp;
	}

	public void saveProjSOEDetails(ProjSOEItemSaveReq projSOEItemSaveReq) {
		projSOEItemRepository.save(
				ProjSOEItemHandler.populateEntitisFromPOJO(projSOEItemSaveReq, projRepository, measurementRepository,
						projSOEActivityLogRepository, projSOEItemRepository, projSOEItemSaveReq.getLoggedInUser()));
	}

	public ProjSOETrackLogResp getProjSOETrackDetails(ProjSOETrackGetReq projSOETrackGetReq) {
		ProjSOETrackLogResp projSOETrackLogResp = new ProjSOETrackLogResp();
		System.out.println("projSOETrackGetReq416 "+projSOETrackGetReq.getProjId());
		System.out.println("projSOETrackGetReq417 "+projSOETrackGetReq.getSoeStatus());
		if(projSOETrackGetReq.getSoeStatus() != null) {
			List<ProjSOETrackRecordsEntity> projSOETrackEntities = projSOETrackRepository
					.findSOEDetails(projSOETrackGetReq.getProjId(), projSOETrackGetReq.getSoeStatus());
			System.out.println("projSOETrackEntities419 "+projSOETrackEntities.size());
			for (ProjSOETrackRecordsEntity projSOETrackRecordsEntity : projSOETrackEntities) {
				projSOETrackLogResp.getProjSOETrackTOs()
						.add(ProjSOEItemHandler.populateProjSOETrackRecords(projSOETrackRecordsEntity, true, userRoleMapRepository));

			}
		}
		return projSOETrackLogResp;
	}
	
	public ProjSORTrackDetailsResp getProjSORTrackDetails(ProjSORTrackGetReq projSOETrackGetReq) {
		ProjSORTrackDetailsResp projSORTrackDetailsResp = new ProjSORTrackDetailsResp();
		List<ProjSORTrackRecordsEntity> projSORTrackRecordsEntites = projSORTrackRepository.findSTRDetails(projSOETrackGetReq.getProjId(), projSOETrackGetReq.getSorStatus());
		for(ProjSORTrackRecordsEntity projSORTrackRecordsEntity : projSORTrackRecordsEntites) {
			projSORTrackDetailsResp.getProjSORTrackTOs().add(ProjSORItemHandler.convertSORTrackDetailsToPOJO(projSORTrackRecordsEntity, true, userRoleMapRepository));
		}
	 	return projSORTrackDetailsResp;
	}

	public void saveProjSOETrackDetails(ProjSOETrackSaveReq projSOETrackSaveReq) {
		System.out.println("projSOEItemSaveReqqqqqqqqqqqqq407 " + projSOETrackSaveReq);
		projSOETrackRepository.save(
				ProjSOEItemHandler.populateEntitesFromPOJO(projSOETrackSaveReq, projRepository, measurementRepository,
						 projSOEItemRepository, projSOETrackSaveReq.getLoggedInUser(),projSOETrackRepository));
	}


	public void deactivateProjSOEDetails(ProjSOEItemDelReq projSOEItemDelReq) {
		projSOEItemRepository.deactivateSOEDetails(projSOEItemDelReq.getProjSOEItemIds(),
				projSOEItemDelReq.getStatus());

	}

	public ProjSORItemResp getProjSORDetails(ProjSORItemGetReq projSORItemGetReq) {
		ProjSORItemResp projSORItemResp = new ProjSORItemResp();
		List<ProjSORItemEntity> projSORItemEntities = null;
		if (projSORItemGetReq.getDisplayActiveItems() != null) {
			projSORItemEntities = projSORItemRepository.findSORDetails(projSORItemGetReq.getProjId());
		} else {
			projSORItemEntities = projSORItemRepository.findSORDetails(projSORItemGetReq.getProjId(),
					projSORItemGetReq.getStatus());
		}

		Long loggedInUser = AppUserUtils.getUserId();
		for (ProjSORItemEntity projSORItemEntity : projSORItemEntities) {
			System.out.println(projSORItemEntity);
			projSORItemResp.getProjSORItemTOs()
					.add(ProjSORItemHandler.convertSORItemsToPOJO(projSORItemEntity, true, userRoleMapRepository));
			// getChildrenEntities( projSORItemEntity );
			/*
			 * ProjSORItemEntity projSORItemEntityRes = getChildrenEntities(
			 * projSORItemEntity );
			 */
		}
		return projSORItemResp;
	}

	public ProjSORItemResp getProjSORDetailsById(ProjSORItemGetReq projSORItemGetReq) {
		ProjSORItemResp projSORItemResp = new ProjSORItemResp();
		List<ProjSORItemEntity> projSORItemEntities = projSORItemRepository
				.findSORDetailsById(projSORItemGetReq.getProjId(), projSORItemGetReq.getSorId());
		for (ProjSORItemEntity projSORItemEntity : projSORItemEntities) {
			projSORItemResp.getProjSORItemTOs().add(ProjSORItemHandler.populateSORITems(projSORItemEntity, true));
		}
		return projSORItemResp;
	}

	public void saveProjSORDetails(ProjSORItemSaveReq projSORItemSaveReq) {
		projSORItemRepository.save(
				ProjSORItemHandler.populateEntitisFromPOJO(projSORItemSaveReq, projRepository, measurementRepository, projSORItemRepository));
	}
	
	public void saveProjSORTrackDetails(ProjSORItemSaveReq projSORItemSaveReq) {
		List<ProjSORItemTO> projSORItemTOs = projSORItemSaveReq.getProjSORItemTOs();
		projSORTrackRepository.save(
				ProjSORItemHandler.populateEntitiesFromPOJO(projSORItemTOs, projRepository, measurementRepository, projSORItemRepository, projSORItemSaveReq.getActionType()));
	}

	public void deactivateProjSORDetails(ProjSORItemDelReq projSORItemDelReq) {
		projSORItemRepository.deactivateSORDetails(projSORItemDelReq.getProjSORItemIds(),
				projSORItemDelReq.getStatus());
	}

	public ProjSOWItemResp getProjSOWDetails(ProjSOWItemGetReq projSOWItemGetReq) {
		ProjSOWItemResp projSOWItemResp = new ProjSOWItemResp();
		List<ProjSOWItemEntity> projSOWItemEntities = projSOWItemRepository
				.findSOWDetails(projSOWItemGetReq.getProjId(), projSOWItemGetReq.getStatus());
		Map<Long, ProjSOWItemEntity> projSOWItemMap = new HashMap<Long, ProjSOWItemEntity>();
		for (ProjSOWItemEntity projSOWItemEntity : projSOWItemEntities) {
			projSOWItemMap.put(projSOWItemEntity.getProjSOEItemEntity().getId(), projSOWItemEntity);
		}

		List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository
				.findSOEDetails(projSOWItemGetReq.getProjId());
		System.out.println("for loop from getProjSOWDetails function:");
		for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
			ProjSOWItemTO projSOWItemTOres = ProjSOWItemHandler.populateSOWITems(projSOEItemEntity, projSOWItemMap,
					scheduleActivityDataRepository);
			System.out.println("projSOItemTOres size:" + projSOWItemTOres.getChildSOWItemTOs().size());
			System.out.println(projSOWItemTOres);
			if (projSOWItemTOres.getChildSOWItemTOs().size() > 0) {
				projSOWItemResp.getProjSOWItemTOs().add(projSOWItemTOres);
			}
		}

		ProjSowItemsMapResp projSowItemsMapResp = new ProjSowItemsMapResp();

		List<ProjSOWItemEntity> projSOWItemEntites = projSOWItemRepository.findSOWItems(projSOWItemGetReq.getProjId(),
				ApplicationConstants.STATUS_ACTIVE);
		LabelKeyTO labelKeyTO = null;

		if (CommonUtil.isListHasData(projSOWItemEntites)) {
			System.out.println("if condition of projSOWItemEntites");
			System.out.println(projSOWItemEntites.size());
			for (ProjSOWItemEntity projSOWItemEntity : projSOWItemEntites) {
				System.out.println(projSOWItemEntity);
				labelKeyTO = new LabelKeyTO();
				labelKeyTO.setId(projSOWItemEntity.getId());
				labelKeyTO.setCode(projSOWItemEntity.getCode());
				labelKeyTO.setName(projSOWItemEntity.getName());

				if (CommonUtil.objectNotNull(projSOWItemEntity.getProjCostItemEntity())) {
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_CODE,
							projSOWItemEntity.getProjCostItemEntity().getCode());
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_NAME,
							projSOWItemEntity.getProjCostItemEntity().getName());
				}

				if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSORItemEntity())) {
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOR_CODE,
							projSOWItemEntity.getProjSORItemEntity().getCode());
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOR_NAME,
							projSOWItemEntity.getProjSORItemEntity().getName());
				}

				if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity())) {
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOE_CODE,
							projSOWItemEntity.getProjSOEItemEntity().getCode());
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOE_NAME,
							projSOWItemEntity.getProjSOEItemEntity().getName());
					labelKeyTO.setUnitOfMeasure(
							projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity().getName());
				}

				projSowItemsMapResp.getSowItemMap().put(projSOWItemEntity.getId(), labelKeyTO);
			}
			System.out.println("==========");
		}
		return projSOWItemResp;
	}

	@Override
	public ProjSOWItemResp getProjSOWDetailsByCostCode(ProjSOWItemGetReq projSOWItemGetReq) {
		List<ProjSOWItemEntity> allProjSowItemEntities = projSOWItemRepository.getSowByCostCode(
				projRepository.findOne(projSOWItemGetReq.getProjId()),
				projCostItemRepository.findAll(projSOWItemGetReq.getCostCodeIds()), projSOWItemGetReq.getWorkDairyId());

		List<ProjSOWItemEntity> finalProjSowItemEntites = new ArrayList<ProjSOWItemEntity>();
		allProjSowItemEntities.forEach((projSowItemEntity) -> {
			ProjSOWItemEntity projSOWItemEntityCopy = new ProjSOWItemEntity();
			projSOWItemEntityCopy.setId(projSowItemEntity.getId());
			Double actualQty = totalActualProcRepository.getActualQtyOfSow(projSOWItemEntityCopy);
			actualQty = (actualQty == null) ? 0 : actualQty;
			double originalQty = (projSowItemEntity.getRevisedQty() == null)
					? projSowItemEntity.getProjSOEItemEntity().getQuantity().doubleValue()
					: projSowItemEntity.getRevisedQty().doubleValue();
			if (actualQty < originalQty) {
				projSowItemEntity.setActualQty(new BigDecimal(actualQty));
				finalProjSowItemEntites.add(projSowItemEntity);
			}
		});
		return convertSowDetails(finalProjSowItemEntites);
	}

	@Override
	public ProjSOWItemResp getProjSOWDetailsExceptCostCode(ProjSOWItemGetReq projSOWItemGetReq) {
		return convertSowDetails(
				projSOWItemRepository.getSowExceptCostCode(projRepository.findOne(projSOWItemGetReq.getProjId()),
						projCostItemRepository.findAll(projSOWItemGetReq.getCostCodeIds()),
						projSOWItemGetReq.getWorkDairyId()));
	}

	private ProjSOWItemResp convertSowDetails(List<ProjSOWItemEntity> projSowItemEntities) {
		ProjSOWItemResp projSOWItemResp = new ProjSOWItemResp();
		List<ProjSOWItemTO> parents = new ArrayList<ProjSOWItemTO>();
		projSowItemEntities.forEach((projSowItem) -> {
			ProjSOEItemEntity projSoeItemEntity = projSowItem.getProjSOEItemEntity();
			ProjSOWItemTO actualSowItemTo = ProjSOWItemHandler.populateSOWITems(projSoeItemEntity, projSowItem, false);
			ProjSOEItemEntity parentSoeItemEntity = projSoeItemEntity.getProjSOEItemEntity();
			ProjSOWItemTO parentSowItemTo = ProjSOWItemHandler.populateSOWITems(parentSoeItemEntity, projSowItem,
					false);
			parentSowItemTo.getChildSOWItemTOs().add(actualSowItemTo);
			while (parentSoeItemEntity.getProjSOEItemEntity() != null) {
				parentSoeItemEntity = parentSoeItemEntity.getProjSOEItemEntity();
				ProjSOWItemTO tempSowItemTo = ProjSOWItemHandler.populateSOWITems(parentSoeItemEntity, projSowItem,
						false);
				tempSowItemTo.getChildSOWItemTOs().add(parentSowItemTo);
				parentSowItemTo = tempSowItemTo;
			}
			parents.add(parentSowItemTo);
		});
		projSOWItemResp.setProjSOWItemTOs(mergeCommonParents(parents));
		return projSOWItemResp;
	}

	private List<ProjSOWItemTO> mergeCommonParents(List<ProjSOWItemTO> parents) {
		List<ProjSOWItemTO> sowItems = new ArrayList<ProjSOWItemTO>();

		for (int i = 0; i < parents.size(); i++) {
			ProjSOWItemTO parentSowTo = parents.get(i);
			if (sowItems.size() == 0) {
				sowItems.add(parentSowTo);
				continue;
			}

			boolean existing = false;

			for (ProjSOWItemTO sowItem : sowItems) {
				if (sowItem.getId() == parentSowTo.getId()) {
					existing = true;
					List<ProjSOWItemTO> availableSowParents = new ArrayList<ProjSOWItemTO>();

					ProjSOWItemTO child = parentSowTo;
					while (child.getChildSOWItemTOs().size() > 0) {
						child = child.getChildSOWItemTOs().get(0);
						if (child.getChildSOWItemTOs().size() != 0)
							availableSowParents.add(child);
					}

					if (child.getParentId() == sowItem.getId()) {
						sowItem.getChildSOWItemTOs().add(child);
						break;
					}

					Map<Long, ProjSOWItemTO> sowItemsMap = new HashMap<Long, ProjSOWItemTO>();
					sowItemsMap.put(sowItem.getId(), sowItem);
					getParentsMap(sowItem, sowItemsMap);

					boolean found = false;

					do {
						ProjSOWItemTO parent = sowItemsMap.get(child.getParentId());
						if (parent != null) {
							parent.getChildSOWItemTOs().add(child);
							found = true;
						} else {
							child = getParentById(availableSowParents, child.getParentId());
						}
					} while (!found);
				}
			}

			if (!existing)
				sowItems.add(parentSowTo);
		}
		return sowItems;
	}

	private ProjSOWItemTO getParentById(List<ProjSOWItemTO> availableParents, Long parentId) {
		ProjSOWItemTO parent = null;
		for (ProjSOWItemTO parentFromList : availableParents) {
			if (parentFromList.getId() == parentId) {
				parent = parentFromList;
				return parent;
			}
		}
		return parent;
	}

	private void getParentsMap(ProjSOWItemTO sowItem, Map<Long, ProjSOWItemTO> sowItemsMap) {
		for (ProjSOWItemTO child : sowItem.getChildSOWItemTOs()) {
			if (!child.isItem()) {
				sowItemsMap.put(child.getId(), child);
				if (child.getChildSOWItemTOs().size() > 0) {
					getParentsMap(child, sowItemsMap);
				}
			}
		}
	}

	public ProjSOWItemResp getProjSOWDetailsById(ProjSOWItemGetReq projSOWItemGetReq) {
		ProjSOWItemResp projSOWItemResp = new ProjSOWItemResp();
		List<ProjSOWItemEntity> projSOWItemEntities = projSOWItemRepository
				.findSOWDetails(projSOWItemGetReq.getProjId(), projSOWItemGetReq.getStatus());
		Map<Long, ProjSOWItemEntity> projSOWItemMap = new HashMap<Long, ProjSOWItemEntity>();
		for (ProjSOWItemEntity projSOWItemEntity : projSOWItemEntities) {
			projSOWItemMap.put(projSOWItemEntity.getProjSOEItemEntity().getId(), projSOWItemEntity);
		}

		List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository.findSOEDetailsById(
				projSOWItemGetReq.getProjId(), projSOWItemGetReq.getSowId(), projSOWItemGetReq.getStatus());
		for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
			projSOWItemResp.getProjSOWItemTOs().add(ProjSOWItemHandler.populateSOWITems(projSOEItemEntity,
					projSOWItemMap, scheduleActivityDataRepository));
		}
		return projSOWItemResp;
	}

	public void saveProjSOWDetails(ProjSOWItemSaveReq projSOWItemSaveReq) {
		projSOWItemRepository.save(ProjSOWItemHandler.populateEntitisFromPOJO(projSOWItemSaveReq, projRepository));
	}

	public void deactivateProjSOWDetails(ProjSOWItemDelReq projSOWItemDelReq) {
		projSOWItemRepository.deactivateSOWDetails(projSOWItemDelReq.getProjSOWItemIds(),
				projSOWItemDelReq.getStatus());
	}

	public ProjCostItemResp getProjCostDetails(ProjCostItemGetReq projCostItemGetReq) {
		ProjCostItemResp projCostItemResp = new ProjCostItemResp();
		List<ProjCostItemEntity> projCostItemEntities = null;
		if (projCostItemGetReq.getDisplayActiveItems() != null) {
			projCostItemEntities = projCostItemRepository.findCostDetailsByProjId(projCostItemGetReq.getProjId());
		} else {
			projCostItemEntities = projCostItemRepository.findCostDetails(projCostItemGetReq.getProjId(),
					projCostItemGetReq.getStatus());
		}
		Set<Long> usedCostIdSet = new HashSet<Long>();
		if (projCostItemGetReq.getProjId() != null) {
			List<Long> usedCostIds = projCostItemRepository.getListOfProjCostDetailsUsedInOtherModules(
					projCostItemGetReq.getProjId(), projCostItemGetReq.getStatus());
			usedCostIdSet = ProjCostItemHandler.convertHibernateIntegerListToLong(usedCostIds);
		}

		for (ProjCostItemEntity projCostCodeItemEntity : projCostItemEntities) {
			projCostItemResp.getProjCostItemTOs()
					.add(ProjCostItemHandler.populateProjCostITems(projCostCodeItemEntity, true));
		}
		for (ProjCostItemTO toObject : projCostItemResp.getProjCostItemTOs()) {
			/*
			 * Long originatorUserId =
			 * projCostItemResp.getProjCostItemTOs().getOriginatorUserId().getUserId();
			 * UserRoleMapEntityCopy userRoleMapEntity =
			 * userRoleMapRepository.findUserRolesByUserId( originatorUserId );
			 * toObject.setOriginatorUserRoleId( userRoleMapEntity.getRoleMstr().getRoleId()
			 * );
			 */
			ProjCostItemHandler.checkCostEntityUsedInOtherModule(toObject, true, usedCostIdSet);
		}
		return projCostItemResp;
	}

	public ProjCostItemResp getProjCostDetailsById(ProjCostItemGetReq projCostItemGetReq) {
		ProjCostItemResp projCostItemResp = new ProjCostItemResp();
		List<ProjCostItemEntity> projSOEItemEntities = projCostItemRepository
				.findCostDetailsById(projCostItemGetReq.getProjId(), projCostItemGetReq.getProjCostId());
		for (ProjCostItemEntity projCostCodeItemEntity : projSOEItemEntities) {
			projCostItemResp.getProjCostItemTOs()
					.add(ProjCostItemHandler.populateProjCostITems(projCostCodeItemEntity, true));
		}
		return projCostItemResp;
	}

	public void saveProjCostDetails(ProjCostItemSaveReq projCostItemSaveReq) {
		projCostItemRepository.save(
				ProjCostItemHandler.populateEntitisFromPOJO(projCostItemSaveReq, projRepository, costCodeRepository));
	}

	public void deactivateProjCostDetails(ProjCostItemDelReq projCostItemDelReq) {
		projCostItemRepository.deactivateProjCostDetails(projCostItemDelReq.getProjCostItemIds(),
				projCostItemDelReq.getStatus());
	}

	public ProjPlantClassResp getProjPlantClasses(ProjPlantClassGetReq projPlantClassGetReq) {
		ProjPlantClassResp projPlantClassResp = new ProjPlantClassResp();
		Map<Long, ProjPlantClassMstrEntity> projPlantClassMap = new HashMap<Long, ProjPlantClassMstrEntity>();
		List<PlantMstrEntity> plantMstrEntities = plantClassRepository.findByClientId(AppUserUtils.getClientId(),
				StatusCodes.ACTIVE.getValue());

		List<ProjPlantClassMstrEntity> projPlantClassMstrEntities = projPlantClassRepository
				.findProjPlantClasses(projPlantClassGetReq.getProjId(), projPlantClassGetReq.getStatus());
		PlantMstrEntity plantMstr = null;
		for (ProjPlantClassMstrEntity projPlantClassMstrEntity : projPlantClassMstrEntities) {
			plantMstr = projPlantClassMstrEntity.getPlantMstrEntity();
			if (CommonUtil.objectNotNull(plantMstr))
				projPlantClassMap.put(plantMstr.getId(), projPlantClassMstrEntity);
		}
		for (PlantMstrEntity plantMstrEntity : plantMstrEntities) {
			projPlantClassResp.getProjPlantClassTOs()
					.add(ProjPlantClassHandler.populateProjPlants(plantMstrEntity, projPlantClassMap));
		}
		return projPlantClassResp;
	}

	public void saveProjPlantClasses(ProjPlantClassSaveReq projPlantClassSaveReq) {

		List<ProjPlantClassMstrEntity> projPlantClassMstrEntities = new ArrayList<ProjPlantClassMstrEntity>(
				ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		projPlantClassMstrEntities
				.addAll(ProjPlantClassHandler.convertPOJOToEntity(projPlantClassSaveReq.getProjPlantClassTOs(),
						projPlantClassSaveReq.getProjId(), projRepository, plantClassRepository));
		projPlantClassRepository.save(projPlantClassMstrEntities);
	}

	/*
	 * public ProjPlantClassResp getProjPlantClasses(ProjPlantClassGetReq
	 * projEmpClassGetReq) { ProjPlantClassResp projPlantClassResp = new
	 * ProjPlantClassResp(); List<ProjPlantClassMstrEntity> projEmpClassMstrEntities
	 * = projPlantClassRepository
	 * .findProjPlantClasses(projEmpClassGetReq.getProjId(),
	 * projEmpClassGetReq.getStatus()); for (ProjPlantClassMstrEntity
	 * projEmpClassMstrEntity : projEmpClassMstrEntities) {
	 * projPlantClassResp.getProjPlantClassTOs()
	 * .add(ProjPlantClassHandler.convertEntityToPOJO(projEmpClassMstrEntity)); }
	 * return projPlantClassResp; }
	 * 
	 * public void saveProjPlantClasses(ProjPlantClassSaveReq projPlantClassSaveReq)
	 * { projPlantClassRepository
	 * .save(ProjPlantClassHandler.convertPOJOToEntity(projPlantClassSaveReq.
	 * getProjPlantClassTOs()));
	 * 
	 * }
	 */

	public void deleteProjPlantClasses(ProjPlantClassDelReq projPlantClassDelReq) {
		projPlantClassRepository.deactivateProjCrewLists(projPlantClassDelReq.getProjPlantClassIds(),
				projPlantClassDelReq.getStatus());
	}

	public ProjWorkShiftResp getProjWorkShifts(ProjWorkShiftGetReq workShiftGetReq) {
		ProjWorkShiftResp projWorkShiftResp = new ProjWorkShiftResp();
		List<ProjWorkShiftMstrEntity> workShiftMstrEntities = projWorkShiftRepository
				.findProjWorkShifts(workShiftGetReq.getProjId(), workShiftGetReq.getStatus());
		for (ProjWorkShiftMstrEntity projWorkShiftMstrEntity : workShiftMstrEntities) {
			projWorkShiftResp.getProjWorkShiftTOs()
					.add(ProjWorkShiftHandler.convertEntityToPOJO(projWorkShiftMstrEntity));
		}
		return projWorkShiftResp;
	}

	public void saveProjWorkShifts(ProjWorkShiftSaveReq workShiftSaveReq) {
		List<ProjWorkShiftMstrEntity> projWorkShiftMstrEntities = new ArrayList<ProjWorkShiftMstrEntity>(
				ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		for (ProjWorkShiftTO projWorkShiftTO : workShiftSaveReq.getProjWorkShiftTOs()) {
			projWorkShiftMstrEntities.add(ProjWorkShiftHandler.convertPOJOToEntity(projWorkShiftTO, projRepository));
		}
		projWorkShiftRepository.save(projWorkShiftMstrEntities);
	}

	public void deleteProjWorkShifts(ProjWorkShiftDelReq projworkShiftDelReq) {

		projWorkShiftRepository.deactivateProjWorkShifts(projworkShiftDelReq.getProjWorkShiftIds(),
				projworkShiftDelReq.getStatus());

	}

	public ProjStoreStockResp getProjStoreStocks(ProjStoreStockGetReq storeStockGetReq) {
		ProjStoreStockResp projStoreStockResp = new ProjStoreStockResp();
		List<ProjStoreStockMstrEntity> storeStockMstrEntities = projStoreStockRepository
				.findProjStoreStocks(storeStockGetReq.getProjId(), storeStockGetReq.getStatus());
		for (ProjStoreStockMstrEntity projStoreStockMstrEntity : storeStockMstrEntities) {
			projStoreStockResp.getProjStoreStockTOs()
					.add(ProjStoreStockHandler.convertEntityToPOJO(projStoreStockMstrEntity));
		}
		return projStoreStockResp;
	}

	public void saveProjStoreStocks(ProjStoreStockSaveReq storeStockSaveReq) {

		List<ProjStoreStockMstrEntity> projStoreStockMstrEntities = new ArrayList<ProjStoreStockMstrEntity>(
				ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		for (ProjStoreStockTO projStoreStockTO : storeStockSaveReq.getProjStoreStockTOs()) {
			projStoreStockMstrEntities.add(ProjStoreStockHandler.convertPOJOToEntity(projStoreStockTO, projRepository));
		}
		projStoreStockRepository.save(projStoreStockMstrEntities);
	}

	public void deleteProjStoreStocks(ProjStoreStockDelReq projStoreStockDelReq) {
		projStoreStockRepository.deactivateProjStoreStocks(projStoreStockDelReq.getProjStoreStockIds(),
				projStoreStockDelReq.getStatus());
	}

	public ProjCrewResp getProjCrewLists(ProjCrewGetReq projCrewGetReq) {
		ProjCrewResp projCrewListResp = new ProjCrewResp();
		List<ProjCrewMstrEntity> projCrewListMstrEntities = projCrewListRepository
				.findProjCrewLists(projCrewGetReq.getProjId(), projCrewGetReq.getStatus());
		for (ProjCrewMstrEntity ProjCrewListMstrEntity : projCrewListMstrEntities) {
			projCrewListResp.getProjCrewTOs().add(ProjCrewHandler.convertEntityToPOJO(ProjCrewListMstrEntity));
		}
		return projCrewListResp;
	}

	public void saveProjCrewLists(ProjCrewSaveReq projCrewSaveReq) {

		List<ProjCrewMstrEntity> projCrewMstrEntities = new ArrayList<ProjCrewMstrEntity>(
				ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		for (ProjCrewTO projCrewTO : projCrewSaveReq.getProjCrewTOs()) {
			projCrewMstrEntities
					.add(ProjCrewHandler.convertPOJOToEntity(projCrewTO, projRepository, projWorkShiftRepository));
		}
		projCrewListRepository.save(projCrewMstrEntities);

	}

	public void deleteProjCrewLists(ProjCrewDelReq projCrewDelReq) {
		projCrewListRepository.deactivateProjCrewLists(projCrewDelReq.getProjCrewIds(), projCrewDelReq.getStatus());
	}

	public ProjCostItemResp getProjCostItemsOnly(ProjCostItemGetReq projCostItemGetReq) {
		ProjCostItemResp projCostItemResp = new ProjCostItemResp();
		List<ProjCostItemEntity> projCostItemEntites = projCostItemRepository
				.findCostCodeItems(projCostItemGetReq.getProjId(), projCostItemGetReq.getStatus());
		for (ProjCostItemEntity projCostItemEntity : projCostItemEntites) {
			projCostItemResp.getProjCostItemTOs()
					.add(ProjCostItemHandler.convertCostItemEntityToPOJO(projCostItemEntity));
		}
		return projCostItemResp;
	}

	public Map<Long, LabelKeyTO> getProjectCostItems(ProjectLibOnLoadReq req) {
		Map<Long, LabelKeyTO> costMap = null;
		List<ProjCostItemEntity> projCostItemEntites = projCostItemRepository.findCostCodeItems(req.getProjId(),
				ApplicationConstants.STATUS_ACTIVE);
		if (CommonUtil.isListHasData(projCostItemEntites)) {
			costMap = new HashMap<Long, LabelKeyTO>();
			for (ProjCostItemEntity projCostItemEntity : projCostItemEntites) {
				costMap.put(projCostItemEntity.getId(),
						ProjCostItemHandler.convertCostItemEntityTOLabelKeyTO(projCostItemEntity));
			}
		}
		return costMap;
	}

	public Map<Long, LabelKeyTO> getProjPlantClasses(ProjectLibOnLoadReq req) {
		Map<Long, LabelKeyTO> projPlantClassMap = null;
		List<ProjPlantClassMstrEntity> projEmpClassMstrEntities = projPlantClassRepository
				.findProjPlantClasses(req.getProjId(), ApplicationConstants.STATUS_ACTIVE);
		if (CommonUtil.isListHasData(projEmpClassMstrEntities)) {
			projPlantClassMap = new HashMap<Long, LabelKeyTO>();
			for (ProjPlantClassMstrEntity projEmpClassMstrEntity : projEmpClassMstrEntities) {
				projPlantClassMap.put(projEmpClassMstrEntity.getId(),
						ProjPlantClassHandler.convertEntityTOLabelKeyTO(projEmpClassMstrEntity));
			}
		}
		return projPlantClassMap;
	}

	public Map<Long, LabelKeyTO> getProjEmpClassMap(ProjectLibOnLoadReq projectLibOnLoadReq) {
		Map<Long, LabelKeyTO> projEmpClassMap = null;

		List<ProjEmpClassMstrEntity> projEmpClassMstrEntites = projEmpClassRepository
				.findProjEmpClasses(projectLibOnLoadReq.getProjId(), ApplicationConstants.STATUS_ACTIVE);
		if (CommonUtil.isListHasData(projEmpClassMstrEntites)) {
			projEmpClassMap = new HashMap<Long, LabelKeyTO>();
			for (ProjEmpClassMstrEntity projEmpClassMstrEntity : projEmpClassMstrEntites) {
				projEmpClassMap.put(projEmpClassMstrEntity.getId(),
						ProjEmpClassHandler.convertEntityTOLabelKeyTO(projEmpClassMstrEntity));
			}
		}
		return projEmpClassMap;
	}

	public Map<Long, LabelKeyTO> getUserProjEmpClassMap(List<Long> projId) {
		Map<Long, LabelKeyTO> projEmpClassMap = null;
		List<ProjEmpClassMstrEntity> projEmpClassMstrEntites = projEmpClassRepository.getUserProjEmpClasses(projId,
				ApplicationConstants.STATUS_ACTIVE);
		if (CommonUtil.isListHasData(projEmpClassMstrEntites)) {
			projEmpClassMap = new HashMap<Long, LabelKeyTO>();
			for (ProjEmpClassMstrEntity projEmpClassMstrEntity : projEmpClassMstrEntites) {
				projEmpClassMap.put(projEmpClassMstrEntity.getId(),
						ProjEmpClassHandler.convertEntityTOLabelKeyTO(projEmpClassMstrEntity));
			}
		}
		return projEmpClassMap;

	}

	public Map<Long, ProjCostItemTO> getProjCostItemMap(ProjCostItemGetReq projCostItemGetReq) {
		Map<Long, ProjCostItemTO> costItemMap = null;
		List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository
				.findMultiProjCostItems(projCostItemGetReq.getProjIds(), ApplicationConstants.STATUS_ACTIVE);
		if (CommonUtil.isListHasData(projCostItemEntities)) {
			costItemMap = new HashMap<Long, ProjCostItemTO>();
			for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
				if (projCostItemEntity.getItem() == CommonConstants.ITEM_VALUE) {
					costItemMap.put(projCostItemEntity.getId(),
							ProjCostItemHandler.populateProjCostITems(projCostItemEntity, true));
				}
			}
		}
		return costItemMap;

	}

	public Map<Long, ProjCrewTO> getMultipleProjsCrewMap(ProjCrewGetReq projCrewGetReq) {
		Map<Long, ProjCrewTO> crewMap = null;
		List<ProjCrewMstrEntity> projCrewMstrEntities = projCrewListRepository
				.findMultipleProjsCrewMap(projCrewGetReq.getProjIds(), ApplicationConstants.STATUS_ACTIVE);
		if (CommonUtil.isListHasData(projCrewMstrEntities)) {
			crewMap = new HashMap<Long, ProjCrewTO>();
			for (ProjCrewMstrEntity projCrewMstrEntity : projCrewMstrEntities) {
				if (projCrewMstrEntity.getProjId() != null && projCrewMstrEntity.getId() != null) {
					crewMap.put(projCrewMstrEntity.getProjId().getProjectId(),
							ProjCrewHandler.papulateProjCrewMap(projCrewMstrEntities));
				}
			}
		}
		return crewMap;

	}

	public ProjSowItemsMapResp getProjSowItemsMap(ProjSOWItemGetReq projSOWItemGetReq) {

		ProjSowItemsMapResp projSowItemsMapResp = new ProjSowItemsMapResp();

		List<ProjSOWItemEntity> projSOWItemEntites = projSOWItemRepository.findSOWItems(projSOWItemGetReq.getProjId(),
				ApplicationConstants.STATUS_ACTIVE);
		LabelKeyTO labelKeyTO = null;

		if (CommonUtil.isListHasData(projSOWItemEntites)) {
			for (ProjSOWItemEntity projSOWItemEntity : projSOWItemEntites) {
				labelKeyTO = new LabelKeyTO();

				labelKeyTO.setCode(projSOWItemEntity.getCode());
				labelKeyTO.setName(projSOWItemEntity.getName());

				if (CommonUtil.objectNotNull(projSOWItemEntity.getProjCostItemEntity())) {
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_CODE,
							projSOWItemEntity.getProjCostItemEntity().getCode());
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_NAME,
							projSOWItemEntity.getProjCostItemEntity().getName());
				}

				if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSORItemEntity())) {
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOR_CODE,
							projSOWItemEntity.getProjSORItemEntity().getCode());
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOR_NAME,
							projSOWItemEntity.getProjSORItemEntity().getName());
				}

				if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity())) {
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOE_CODE,
							projSOWItemEntity.getProjSOEItemEntity().getCode());
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOE_NAME,
							projSOWItemEntity.getProjSOEItemEntity().getName());
					labelKeyTO.setUnitOfMeasure(
							projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity().getName());
				}

				projSowItemsMapResp.getSowItemMap().put(projSOWItemEntity.getId(), labelKeyTO);
			}
		}
		return projSowItemsMapResp;
	}

	public ProjSOWItemResp getMultiProjSOWDetails(ProjSOWItemGetReq projSOWItemGetReq) {
		ProjSOWItemResp projSOWItemResp = new ProjSOWItemResp();
		List<ProjSOWItemEntity> projSOWItemEntities = projSOWItemRepository
				.findMultiProjSOWDetails(projSOWItemGetReq.getProjIds(), projSOWItemGetReq.getStatus());
		Map<Long, ProjSOWItemEntity> projSOWItemMap = new HashMap<Long, ProjSOWItemEntity>();
		for (ProjSOWItemEntity projSOWItemEntity : projSOWItemEntities) {
			projSOWItemMap.put(projSOWItemEntity.getProjSOEItemEntity().getId(), projSOWItemEntity);
		}

		List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository
				.findMultiProjSOEDetails(projSOWItemGetReq.getProjIds(), projSOWItemGetReq.getStatus());
		for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
			projSOWItemResp.getProjSOWItemTOs().add(ProjSOWItemHandler.populateSOWITems(projSOEItemEntity,
					projSOWItemMap, scheduleActivityDataRepository));
		}
		return projSOWItemResp;
	}

	public ProjCrewResp getMultipleProjsCrewList(ProjCrewGetReq projCrewGetReq) {
		ProjCrewResp projCrewResp = new ProjCrewResp();
		List<ProjCrewMstrEntity> ProjCrewMstrEntites = projCrewListRepository
				.findMultipleProjsCrewList(projCrewGetReq.getProjIds(), projCrewGetReq.getStatus());
		for (ProjCrewMstrEntity projCrewMstrEntity : ProjCrewMstrEntites) {
			projCrewResp.getProjCrewTOs().add(ProjCrewHandler.convertEntityToPOJO(projCrewMstrEntity));
		}
		return projCrewResp;
	}

	public ProjCostItemResp getMultiProjCostDetails(ProjCostItemGetReq projCostItemGetReq) {
		ProjCostItemResp projCostItemResp = new ProjCostItemResp();
		List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository
				.findMultiProjCostDetails(projCostItemGetReq.getProjIds(), projCostItemGetReq.getStatus());
		for (ProjCostItemEntity projCostCodeItemEntity : projCostItemEntities) {
			projCostItemResp.getProjCostItemTOs()
					.add(ProjCostItemHandler.populateProjCostITems(projCostCodeItemEntity, true));
		}
		return projCostItemResp;
	}

	public ProjStoreStockResp getMultipleProjsStoreList(ProjStoreStockGetReq projStoreStockGetReq) {
		ProjStoreStockResp projStoreStockResp = new ProjStoreStockResp();
		List<ProjStoreStockMstrEntity> projStoreStockMstrEntities = projStoreStockRepository
				.findMultipleProjsStoreList(projStoreStockGetReq.getProjIds(), projStoreStockGetReq.getStatus());
		for (ProjStoreStockMstrEntity projStoreStockMstrEntity : projStoreStockMstrEntities) {
			projStoreStockResp.getProjStoreStockTOs()
					.add(ProjStoreStockHandler.convertEntityToPOJO(projStoreStockMstrEntity));
		}
		return projStoreStockResp;
	}

	public SOWTotalActualQuantitiesResp getSOWTotalActualQuantities(ProjSOWItemGetReq projSOWItemGetReq) {
		Long projId = projSOWItemGetReq.getProjId();
		SOWTotalActualQuantitiesResp totalActualQuantitiesResp = new SOWTotalActualQuantitiesResp();
		totalActualQuantitiesResp.setActualRevisedMap(totalActualProcRepository.findTotalActualQuantities(projId));
		return totalActualQuantitiesResp;
	}

	public ProjWorkShiftResp getMultipleProjWorkShifts(ProjWorkShiftGetReq workShiftGetReq) {
		ProjWorkShiftResp projWorkShiftResp = new ProjWorkShiftResp();
		List<ProjWorkShiftMstrEntity> workShiftMstrEntities = projWorkShiftRepository
				.findProjWorkShifts(workShiftGetReq.getProjIds(), workShiftGetReq.getStatus());
		for (ProjWorkShiftMstrEntity projWorkShiftMstrEntity : workShiftMstrEntities) {
			projWorkShiftResp.getProjWorkShiftTOs()
					.add(ProjWorkShiftHandler.convertEntityToPOJO(projWorkShiftMstrEntity));
		}
		return projWorkShiftResp;
	}

	@Override
	public void saveDefaultProjLeaveTypes(Long clientId) {
		ClientRegEntity clientReg = clientRegRepository.findOne(clientId);
		int count = projLeaveTypeRepository.leaveTypesForClient(clientReg);
		if (count <= 0) {
			List<ProjLeaveTypeEntity> defaultTypes = projLeaveTypeRepository.findDefaultProjLeaveTypes();
			projLeaveTypeRepository.save(getDefaultProjLeaveTypes(clientId, clientReg, defaultTypes));
		}
	}

	private List<ProjLeaveTypeEntity> getDefaultProjLeaveTypes(Long clientId, ClientRegEntity clientReg,
			List<ProjLeaveTypeEntity> defaultTypes) {
		List<ProcureCatgDtlEntity> procureTypes = procureCatgRepository.getProcureByType("Manpower", clientId, 1);
		List<ProjLeaveTypeEntity> newTypes = new ArrayList<>();
		for (ProjLeaveTypeEntity defaultEntity : defaultTypes) {
			ProjLeaveTypeEntity newEntity = new ProjLeaveTypeEntity();
			newEntity.setCode(defaultEntity.getCode());
			newEntity.setEvidenceForm(defaultEntity.isEvidenceForm());
			newEntity.setMaxAllowEvent(defaultEntity.getMaxAllowEvent());
			newEntity.setMaxAllowYear(defaultEntity.getMaxAllowYear());
			newEntity.setMedicalForm(defaultEntity.isMedicalForm());
			newEntity.setName(defaultEntity.getName());
			newEntity.setPriorApproval(defaultEntity.isPriorApproval());
			newEntity.setStatus(defaultEntity.getStatus());
			List<ProjLeaveCategoryType> projLeaveCategoryTypes = new ArrayList<>();
			for (ProcureCatgDtlEntity procureCatgDtlEntity : procureTypes) {
				ProjLeaveCategoryType projLeaveCategoryType = new ProjLeaveCategoryType();
				projLeaveCategoryType.setPayType("Unpaid");
				projLeaveCategoryType.setProcureCatgDtlEntity(procureCatgDtlEntity);
				projLeaveCategoryType.setProjLeaveTypeEntity(newEntity);
				projLeaveCategoryTypes.add(projLeaveCategoryType);
			}
			newEntity.setProjLeaveCategoryTypes(projLeaveCategoryTypes);
			newEntity.setCountryCode("Global");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			newEntity.setEffectiveFrom(cal.getTime());
			newEntity.setClientRegEntity(clientReg);
			newTypes.add(newEntity);
		}
		return newTypes;
	}

	@Override
	public ProjLeaveTypeResp getGlobalLeaveTypes() {
		ClientRegEntity clientReg = clientRegRepository.findOne(AppUserUtils.getClientId());
		ProjLeaveTypeResp resp = new ProjLeaveTypeResp();
		List<ProjLeaveTypeTO> leaveTypeTOs = resp.getProjLeaveTypeTOs();
		if (clientReg != null) {
			List<ProjLeaveTypeEntity> globalTypes = projLeaveTypeRepository.findByCountryCodeAndClient("Global",
					clientReg);
			for (ProjLeaveTypeEntity entity : globalTypes) {
				ProjLeaveTypeTO leaveTypeTO = ProjLeaveTypeHandler.convertEntityToPOJO(entity, false);
				leaveTypeTOs.add(leaveTypeTO);
			}
		}
		return resp;
	}

	@Override
	public List<String> getEffectiveDatesForCountry(String countryCode) {
		return projLeaveTypeRepository.findUniqueEffectiveDatesForCountry(countryCode, AppUserUtils.getClientId());
	}

	@Override
	public ProjPMCMItemResp getProjPMCMDetails(ProjPMCMItemGetReq projPMCMItemGetReq) {
		System.out.println("ProjLibServiceImpl:getProjPMCMDetails:ProjId:" + projPMCMItemGetReq.getProjId());
		System.out.println("ProjLibServiceImpl:getProjPMCMDetails:ClientId:" + projPMCMItemGetReq.getClientId());
		System.out.println("ProjLibServiceImpl:getProjPMCMDetails:Status:" + projPMCMItemGetReq.getStatus());

		// -- Project Cost Details
		HashMap<String, String> projCostItemEntitiesMap = new HashMap<String, String>();
		List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository
				.findCostCodeItems(projPMCMItemGetReq.getProjId(), projPMCMItemGetReq.getStatus());
		System.out.println("projCostItemEntities Size :" + projCostItemEntities.size());
		System.out.println("projCostItemEntities :" + projCostItemEntities);

		for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
			System.out.println("CostItemEntity Code:" + projCostItemEntity.getCode());
			System.out.println("CostItemEntity Name:" + projCostItemEntity.getName());
			projCostItemEntitiesMap.put(projCostItemEntity.getCode(), projCostItemEntity.getName());
		}
		// ------------------------------
		ProjPMCMItemResp projPMCMItemResp = new ProjPMCMItemResp();
		System.out.println(" *** ProjLibServiceImpl:ProjStatusDate : " + projPMCMItemGetReq.getProjStatusDate());
		Date projStatusDate = CommonUtil.convertStringToDate(projPMCMItemGetReq.getProjStatusDate());
		List<ProjPMCMItemEntity> projPMCMItemEntities = projPMCMItemRepository
				.findPMCMDetails(projPMCMItemGetReq.getProjId(), projStatusDate, projPMCMItemGetReq.getStatus());
		System.out.println(
				"ProjectLibServiceImpl:getProjPMCMDetails:projPMCMItemEntities: Size:" + projPMCMItemEntities.size());
		System.out.println("ProjPMCMItemHandler.projCostItemEntitiesMap Size:" + projCostItemEntitiesMap.size());
		System.out.println(projCostItemEntitiesMap);

		for (ProjPMCMItemEntity projPMCMItemEntity : projPMCMItemEntities) {
			System.out.println("projPMCMItemEntity:" + projPMCMItemEntity.getId());
			projPMCMItemResp.getProjPMCMItemTOs().add(ProjPMCMItemHandler.populateSORITems(projPMCMItemEntity, true));
		}

		if (projCostItemEntitiesMap != null && projCostItemEntitiesMap.size() > 0) {
			projPMCMItemResp.setProjCostItemEntitiesMap(projCostItemEntitiesMap);
		}
		System.out.println("getProjPMCMDetails : projPMCMItemResp");
		System.out.println(projPMCMItemResp);
		return projPMCMItemResp;
	}
	
	public void deactivateProjPMCMDetails(ProjPMCMItemGetReq projPMCMItemGetReq) {
		System.out.println("projPMCMItemGetReq.getPmIds()1247 "+projPMCMItemGetReq.getPmIds());
		projPMCMItemRepository.deactivatePMCMDetails(projPMCMItemGetReq.getStatus(), projPMCMItemGetReq.getPmIds());
	}

	@Override
	public ProjPMCMItemResp getProjPMCMDetailsById(ProjPMCMItemGetReq projPMCMItemGetReq) {
		System.out.println("ProjLibServieImpl: ProjPMCMDetailsById: PmId:" + projPMCMItemGetReq.getPmId());
		System.out.println("ProjLibServieImpl: ProjPMCMDetailsById: ProjId:" + projPMCMItemGetReq.getProjId());
		ProjPMCMItemResp projPMCMItemResp = new ProjPMCMItemResp();
		List<ProjPMCMItemEntity> projPMCMItemEntities = projPMCMItemRepository
				.findPMCMDetailsById(projPMCMItemGetReq.getProjId(), projPMCMItemGetReq.getPmId());
		for (ProjPMCMItemEntity projPMCMItemEntity : projPMCMItemEntities) {
			projPMCMItemResp.getProjPMCMItemTOs().add(ProjPMCMItemHandler.populateSORITems(projPMCMItemEntity, true));
		}
		return projPMCMItemResp;
	}

	@Override
	public void saveProjPMCMDetails(ProjPMCMItemSaveReq projPMCMItemSaveReq) {
		projPMCMItemRepository.save(ProjPMCMItemHandler.populateEntitisFromPOJO(projPMCMItemSaveReq, projRepository));
	}

	@Override
	public ProjPMCMItemResp getProjReportPMCMDetails(ProjPMCMItemGetReq projPMCMItemGetReq) {
		System.out.println("ProjLibServiceImpl:getProjPMCMDetails:ProjId:" + projPMCMItemGetReq.getProjId());
		System.out.println("ProjLibServiceImpl:getProjPMCMDetails:ClientId:" + projPMCMItemGetReq.getClientId());
		System.out.println("ProjLibServiceImpl:getProjPMCMDetails:Status:" + projPMCMItemGetReq.getStatus());
		System.out.println(
				"ProjectLibController:getProjPMCMDetails:getProjStatusDate:" + projPMCMItemGetReq.getProjStatusDate());
		System.out.println(
				"ProjectLibController:getProjPMCMDetails:getProjIds:" + projPMCMItemGetReq.getProjIds().size());
		System.out.println(projPMCMItemGetReq.getProjIds());
		// -- Project Cost Details

		ProjPMCMItemResp projPMCMItemResp = new ProjPMCMItemResp();
		System.out.println(" *** ProjLibServiceImpl:ProjStatusDate : " + projPMCMItemGetReq.getProjStatusDate());
		Date pmFromStatusDate = CommonUtil.convertStringToDate(projPMCMItemGetReq.getPmFromStatusDate());
		Date projStatusDate = CommonUtil.convertStringToDate(projPMCMItemGetReq.getProjStatusDate());

		System.out.println("pmFromStatusDate :" + pmFromStatusDate);
		System.out.println("projStatusDate :" + projStatusDate);

		List<ProjPMCMItemEntity> projPMCMItemEntities = projPMCMItemRepository
				.findMultiplePMCMDetails(projPMCMItemGetReq.getProjIds(), projStatusDate);
		System.out.println(
				"ProjectLibServiceImpl:getProjPMCMDetails:projPMCMItemEntities: Size:" + projPMCMItemEntities.size());
		System.out.println(projPMCMItemEntities);
		// Setting PMCM Values
		for (ProjPMCMItemEntity projPMCMItemEntity : projPMCMItemEntities) {
			projPMCMItemResp.getProjPMCMItemTOs().add(ProjPMCMItemHandler.populateSORITems(projPMCMItemEntity, true));
		}
		System.out.println("getProjPMCMDetails : projPMCMItemResp");
		System.out.println(projPMCMItemResp);
		// --- for cost codes ---
		System.out.println("@@@@@@@@ Before Hitting Cost Code Details @@@@@@@@@@@@@@");
		HashMap<String, String> costItemEntitiesMap = getProjCostItemEntitiesMap(projPMCMItemGetReq.getProjIds(),
				new Integer(1));

		System.out.println("@@@@@@@@ Result costItemEntitiesMap Size :   " + costItemEntitiesMap.size());
		System.out.println(costItemEntitiesMap);
		/*
		 * if(costItemEntitiesMap!=null && costItemEntitiesMap.size()>0) {
		 * projPMCMItemResp.setProjCostItemEntitiesMap(costItemEntitiesMap); }
		 */
		// ----------------- report Started ---#############################
		Map<ProjPMCMItemTO, List<ReportPMCMValueTO>> progressHrsMap = processProgressPeriodicalResponse(
				projPMCMItemEntities);
		System.out.println("progressHrsMap");
		System.out.println(projPMCMItemEntities);
		Set<ProjPMCMItemTO> progressKeySet = progressHrsMap.keySet();
		System.out.println("progressKeySet");
		System.out.println(progressKeySet);
		for (ProjPMCMItemTO key : progressKeySet) {
			System.out.println("key");
			System.out.println(key);
			if (costItemEntitiesMap != null && costItemEntitiesMap.size() > 0) {
				key.setPmCostCodeName(costItemEntitiesMap.get(key.getPmCostCodeId()));
			}

			List<ReportPMCMValueTO> hrsList = progressHrsMap.get(key);
			System.out.println("hrsList");
			System.out.println(hrsList);
			for (Iterator<ReportPMCMValueTO> hrsIterator = hrsList.iterator(); hrsIterator.hasNext();) {
				ReportPMCMValueTO hr = hrsIterator.next();
				System.out.println("hr");
				if (hr != null && hr.getDate() != null && pmFromStatusDate != null) {
					System.out.println(hr);
					if (hr.getDate().before(pmFromStatusDate)) {
						key.addPrevHrs(hr);
						hrsIterator.remove();
					} else {
						key.addCurrentHrs(hr);
					}
				} else {
					System.out.println("Got NULL Values - hr!=null && hr.getDate()!=null && fromDate!=null");
				}
			}
		}
		for (Map.Entry<ProjPMCMItemTO, List<ReportPMCMValueTO>> entry : progressHrsMap.entrySet()) {
			entry.getKey().setHrsList(entry.getValue());
		}

		List<ProjPMCMItemTO> projPMCMReportTOList = progressKeySet.stream().collect(Collectors.toList());
		System.out.println("projPMCMReportTOList");
		System.out.println(projPMCMReportTOList);

		System.out.println("@@@@@@@@ Result projPMCMReportTOList Size :   " + projPMCMReportTOList.size());
		System.out.println(projPMCMReportTOList);
		if (projPMCMReportTOList != null && projPMCMReportTOList.size() > 0) {
			projPMCMItemResp.setProjPMCMReportTOList(projPMCMReportTOList);
			System.out.println("@@@@@@@@ After Result getProjPMCMReportTOList Size :   "
					+ projPMCMItemResp.getProjPMCMReportTOList().size());
			System.out.println(projPMCMItemResp.getProjPMCMReportTOList());
		}

		// --- for Cost % ---

		/*
		 * System.out.println("**** BEFORE Starting COST PERCENTAGE **********");
		 * ProjPMCPItemGetReq projPMCPItemGetReq = new ProjPMCPItemGetReq();
		 * projPMCPItemGetReq.setProjIds(projPMCMItemGetReq.getProjIds());
		 * projPMCPItemGetReq.setStatus(new Integer(1)); if(projPMCPItemGetReq !=null &&
		 * projPMCPItemGetReq.getProjIds()!=null &&
		 * projPMCPItemGetReq.getProjIds().size()>0) {
		 * System.out.println("ProjectLibController:getProjPMCMDetails:getProjIds:"+
		 * projPMCMItemGetReq.getProjIds().size());
		 * System.out.println(projPMCMItemGetReq.getProjIds());
		 * ProjPMCPCostStatementsResp projPMCPCostStatementsResp =
		 * getProjPMCPCostStatements( projPMCPItemGetReq); if(projPMCPCostStatementsResp
		 * !=null && projPMCPCostStatementsResp.getProjCostStmtDtlTOs()!=null &&
		 * projPMCPCostStatementsResp.getProjCostStmtDtlTOs().size()>0) {
		 * projPMCMItemResp.setProjCostStmtDtlTOs(projPMCPCostStatementsResp.
		 * getProjCostStmtDtlTOs()); }else{
		 * System.out.println("projPMCPCostStatementsResp ProjCostStmtDtlTOs() is NULL"
		 * ); } }else{ System.out.println("projPMCPItemGetReq.getProjIds is NULL"); }
		 */

		// ----------------------------------
		return projPMCMItemResp;
	}

	private PeriodCostTO copyMainValues(CostReportTO costReportTO) {
		PeriodCostTO periodCostTO = new PeriodCostTO();
		periodCostTO.setEpsId(costReportTO.getEpsId());
		periodCostTO.setEpsName(costReportTO.getEpsName());
		periodCostTO.setProjId(costReportTO.getProjId());
		periodCostTO.setProjName(costReportTO.getProjName());
		periodCostTO.setCostItemId(costReportTO.getCostItemId());
		periodCostTO.setCostItemCode(costReportTO.getCostItemCode());
		periodCostTO.setCostItemName(costReportTO.getCostItemName());
		periodCostTO.setCostSubGroupId(costReportTO.getCostSubGroupId());
		periodCostTO.setCostSubGroupCode(costReportTO.getCostSubGroupCode());
		periodCostTO.setCostSubGroupName(costReportTO.getCostSubGroupName());
		periodCostTO.setCurrencyCode(costReportTO.getCurrencyCode());
		return periodCostTO;
	}

	/*
	 * private com.rjtech.projectlib.dto.CostValuesTO
	 * getPprojectLibCostValuesTO(com.rjtech.reports.cost.resp.CostValuesTO
	 * costValuesTO) { com.rjtech.projectlib.dto.CostValuesTO costValuesTORtn = new
	 * com.rjtech.projectlib.dto.CostValuesTO();
	 * costValuesTORtn.setCostAmount(costValuesTO.getCostAmount());
	 * costValuesTORtn.setCostId(costValuesTO.getCostId());
	 * costValuesTORtn.setDate(costValuesTO.getDate());
	 * costValuesTORtn.setEarnedValue(costValuesTO.getEarnedValue());
	 * costValuesTORtn.setEstimateValue(costValuesTO.getEstimateValue());
	 * costValuesTORtn.setPlannedValue(costValuesTO.getPlannedValue()); return
	 * costValuesTORtn; }
	 */

	public HashMap<String, BigDecimal> getOriginalCostBudgetTotalMap(ProjPMCPItemGetReq projPMCPItemGetReq) {
		System.out.println("*** at getOriginalCostBudgetTotalMap before calling  getProjPMCPCostStatements");

		ProjPMCPCostStatementsResp projPMCPCostStatementsResp = null;

		HashMap<String, BigDecimal> originalCostBudgetTotalMap = new HashMap<String, BigDecimal>();
		System.out.println("isCallFromActualCostModule  :" + projPMCPItemGetReq.isCallFromActualCostModule());
		if (!projPMCPItemGetReq.isCallFromActualCostModule()) {
			System.out.println("Inside IFF isCallFromActualCostModule");
			projPMCPCostStatementsResp = getProjPMCPCostStatements(projPMCPItemGetReq);
			List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs = projPMCPCostStatementsResp.getProjCostStmtDtlTOs();

			System.out.println("projCostStmtDtlTOs Size  :" + projCostStmtDtlTOs.size());

			for (ProjCostStmtDtlTOCopy projCostStmtDtlTO : projCostStmtDtlTOs) {
				System.out.println(
						"CostStmtDtlTO Id:" + projCostStmtDtlTO.getId() + ":Code:" + projCostStmtDtlTO.getCode()
								+ ":CostId:" + projCostStmtDtlTO.getCostId() + "CostId:" + projCostStmtDtlTO.getName());
				System.out.println("getOriginalCostBudget:" + projCostStmtDtlTO.getOriginalCostBudget().getTotal());
				originalCostBudgetTotalMap.put(projCostStmtDtlTO.getCode(),
						projCostStmtDtlTO.getOriginalCostBudget().getTotal());
				getCostBudgetTotalMap(projCostStmtDtlTO, originalCostBudgetTotalMap);
			}
		}
		return originalCostBudgetTotalMap;
	}

	private void getCostBudgetTotalMap(ProjCostStmtDtlTOCopy projCostStmtDtlTOCopy,
			HashMap<String, BigDecimal> originalCostBudgetTotalMap) {
		for (ProjCostStmtDtlTOCopy child : projCostStmtDtlTOCopy.getProjCostStmtDtlTOCopys()) {
			System.out.println(" : " + child.getCode() + " : OriginalCostBudget().getTotal : "
					+ child.getOriginalCostBudget().getTotal());
			originalCostBudgetTotalMap.put(child.getCode(), child.getOriginalCostBudget().getTotal());
			if (!child.isItem()) {
				if (child.getProjCostStmtDtlTOCopys().size() > 0) {
					getCostBudgetTotalMap(child, originalCostBudgetTotalMap);
				}
			}
		}
	}

	@Override
	public ProjPMCPItemResp getProjReportPMCPDetails(ProjPMCPItemGetReq projPMCPItemGetReq) {

		System.out.println("*** Started getProjReportPMCPDetails");
		System.out.println("ProjLibServiceImpl:getProjReportPMCPDetails:ProjId:" + projPMCPItemGetReq.getProjId());
		System.out.println("ProjLibServiceImpl:getProjReportPMCPDetails:ClientId:" + projPMCPItemGetReq.getClientId());
		System.out.println("ProjLibServiceImpl:getProjReportPMCPDetails:Status:" + projPMCPItemGetReq.getStatus());
		System.out.println(
				"ProjectLibController:getProjReportPMCPDetails:getFromDate:" + projPMCPItemGetReq.getFromDate());
		System.out.println("ProjectLibController:getProjReportPMCPDetails:getToDate:" + projPMCPItemGetReq.getToDate());
		System.out.println(
				"ProjectLibController:getProjReportPMCPDetails:getProjIds:" + projPMCPItemGetReq.getProjIds().size());
		System.out.println(projPMCPItemGetReq.getProjIds());

		// below hashmap used Actual % Module Level

		HashMap<String, PeriodCostTO> actualCostModuleMap = new HashMap<String, PeriodCostTO>();

		// -- Project Cost Details
		CostReportReq costReportReq = new CostReportReq();

		costReportReq.setFromDate(projPMCPItemGetReq.getFromDate());
		costReportReq.setToDate(projPMCPItemGetReq.getToDate());
		costReportReq.setProjIds(projPMCPItemGetReq.getProjIds());

		System.out.println("ProjectLibController:getProjPMCPDetails: FromDate:" + costReportReq.getFromDate());
		System.out.println("ProjectLibController:getProjPMCPDetails: ToDate:" + costReportReq.getToDate());

		ProjPMCPItemResp projPMCMItemResp = new ProjPMCPItemResp();

		Date fromForReport = new Date(0);
		Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
		costReportReq.setFromDate(CommonUtil.convertDateToString(fromForReport));
		costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));

		System.out.println("ProjectLibController:getProjPMCPDetails: FromDate:" + costReportReq.getFromDate());
		System.out.println("ProjectLibController:getProjPMCPDetails: ToDate:" + costReportReq.getToDate());

		DateWiseCostReportResp resp = getDateWisePlanActualEarned(costReportReq);

		System.out.println("CostReportResps size :" + resp.getCostReportResps().size());

		List<com.rjtech.projectlib.dto.CostReportTO> costReportResps = new ArrayList<>();

		Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());

		// if(!projPMCPItemGetReq.isCallFromActualCostModule())
		// {
		HashMap<String, BigDecimal> originalCostBudgetTotalMap = getOriginalCostBudgetTotalMap(projPMCPItemGetReq);
		System.out.println(
				"getProjReportPMCPDetails : originalCostBudgetTotalMap Size : " + originalCostBudgetTotalMap.size());
		System.out.println(originalCostBudgetTotalMap);
		// }

		System.out.println("resp.getCostReportResps() Size : " + resp.getCostReportResps().size());
		Map<Long, PeriodCostTO> map = new HashMap<>();
		for (CostReportTO costReportTO : resp.getCostReportResps()) {
			if (costReportTO.getCostAmount() != 0 || costReportTO.getEarnedValue() != 0) {
				PeriodCostTO periodCostTO = copyMainValues(costReportTO);
				map.computeIfAbsent(periodCostTO.getCostItemId(), v -> periodCostTO);
				map.computeIfPresent(periodCostTO.getCostItemId(), (k, v) -> {
					com.rjtech.reports.cost.resp.CostValuesTO costValuesTO = new com.rjtech.reports.cost.resp.CostValuesTO(
							costReportTO.getDate(), periodCostTO.getCostItemId(), costReportTO.getCostAmount(),
							costReportTO.getEarnedValue());
					if (costReportTO.getDateObj().before(fromDate)) {
						v.setTotalPrevCost(v.getTotalPrevCost() + costReportTO.getCostAmount());
						v.setTotalPrevEarned(v.getTotalPrevEarned() + costReportTO.getEarnedValue());
						// v.getPrevValues().add(getPprojectLibCostValuesTO(costValuesTO));
					} else {
						v.setTotalReportCost(v.getTotalReportCost() + costReportTO.getCostAmount());
						v.setTotalReportEarned(v.getTotalReportEarned() + costReportTO.getEarnedValue());
						// v.getReportValues().add(getPprojectLibCostValuesTO(costValuesTO));
					}
					// ---- mapping Budget details
					// System.out.println(" ProjLiServiceImpl: EpsId : "+v.getEpsId()+" : EpsName :
					// "+v.getEpsName() +" ProjId : "+v.getProjId()+" : ProjName :
					// "+v.getProjName());
					// System.out.println(" CostItemCode : "+v.getCostItemCode()+" : CostItemName :
					// "+v.getCostItemName()+" : CostItemName : "+v.getCostItemName());

					// ----------------------------
					if (costReportTO.getDateObj().before(fromDate)) {
						v.setTotalPreviousAmount(v.getTotalPreviousAmount() + costReportTO.getCostAmount());
						if (costReportTO.getEmpId() != null && costReportTO.getEmpId().longValue() > 0) {
							v.setPrevManpowerCost(costReportTO.getCostAmount());
						} else if (costReportTO.getPlantId() != null && costReportTO.getPlantId().longValue() > 0) {
							v.setPrevPlantCost(costReportTO.getCostAmount());
						} else if (costReportTO.getMatId() != null && costReportTO.getMatId().longValue() > 0) {
							v.setPrevMaterialCost(costReportTO.getCostAmount());
						}
					} else if (costReportTO.getDateObj().after(fromDate) && costReportTO.getDateObj().before(toDate)) {
						v.setTotalReportingAmount(v.getTotalReportingAmount() + costReportTO.getCostAmount());
						if (costReportTO.getEmpId() != null && costReportTO.getEmpId().longValue() > 0) {
							v.setReportingManpowerCost(costReportTO.getCostAmount());
						} else if (costReportTO.getPlantId() != null && costReportTO.getPlantId().longValue() > 0) {
							v.setReportingPlantCost(costReportTO.getCostAmount());
						} else if (costReportTO.getMatId() != null && costReportTO.getMatId().longValue() > 0) {
							v.setReportingMaterialCost(costReportTO.getCostAmount());
						}
					}

					v.setUptoDateManpowerCost(v.getPrevManpowerCost() + v.getReportingManpowerCost());
					v.setUptoDatePlantCost(v.getPrevPlantCost() + v.getReportingPlantCost());
					v.setUptoDateMaterialCost(v.getPrevMaterialCost() + v.getReportingMaterialCost());

					v.setTotalUptoDateAmount(v.getTotalPreviousAmount() + v.getTotalReportingAmount());

					v.setCurrentDate(costReportTO.getDate());
					// setting budget Cost
					// System.out.println(":getCostItemCode : "+v.getCostItemCode());

					// System.out.println(":get budget from Map :
					// "+originalCostBudgetTotalMap.get(v.getCostItemCode()));
					v.setBudget(originalCostBudgetTotalMap.get(v.getCostItemCode()));
					// System.out.println("*** getBudget : "+v.getBudget());

					// This map is used in Actual Cost %
					System.out.println("Setting actualCostModuleMap getCostItemCode :" + v.getCostItemCode());
					System.out.println(v);
					actualCostModuleMap.put(v.getCostItemCode(), v);

					return v;
				});

				costReportResps.add(getProjectLibCostReportTO(costReportTO));
			}
		}

		List<PeriodCostTO> periodCostTOs = new ArrayList<>(map.values());

		projPMCMItemResp.setPeriodCostTOs(periodCostTOs);

		if (projPMCMItemResp.getPeriodCostTOs() != null)
			System.out.println(
					"ProjLibServiceImpl:getProjReportPMCPDetails size: " + projPMCMItemResp.getPeriodCostTOs().size());
		else
			System.out.println("ProjLibServiceImpl:getProjReportPMCPDetails : is NULL");

		if (costReportResps != null)
			System.out.println(
					"ProjLibServiceImpl:getProjReportPMCPDetails getCostReportResps size: " + costReportResps.size());
		else
			System.out.println("ProjLibServiceImpl:getProjReportPMCPDetails :getCostReportResps : is NULL");

		projPMCMItemResp.setCostReportResps(costReportResps);

		// This is Used in Actual Cost % Module
		projPMCMItemResp.setActualCostModuleMap(actualCostModuleMap);

		System.out.println("*** 1 Final Result getPeriodCostTOs : " + projPMCMItemResp.getPeriodCostTOs().size());
		System.out.println("*** 2 Final Result setCostReportResps : " + projPMCMItemResp.getCostReportResps().size());
		System.out
				.println("*** 3 Final Result setCostReportResps : " + projPMCMItemResp.getActualCostModuleMap().size());
		System.out.println(projPMCMItemResp.getActualCostModuleMap());
		return projPMCMItemResp;
	}

	// CMCP Report
	/*
	 * public DateWiseCostReportResp getDateWisePlanActualEarned(CostReportReq
	 * costReportReq) {
	 * costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId(
	 * ))); DateWiseCostReportResp resp =
	 * getDatewiseActualCostDetails(costReportReq); Map<String, Double> earnedValues
	 * = new HashMap<>(); if (!resp.getCostReportResps().isEmpty()) { Date fromDate
	 * = CommonUtil.convertStringToDate(costReportReq.getFromDate()); Date toDate =
	 * CommonUtil.convertStringToDate(costReportReq.getToDate()); List<Object[]>
	 * dailyEarnedValues = projSowTotalActualRepository
	 * .getDailyEarnedValuePerCostCode(costReportReq.getProjIds(), fromDate,
	 * toDate); for (Object[] dailyEarned : dailyEarnedValues) { Long costId =
	 * (Long) dailyEarned[0]; Date date = (Date) dailyEarned[1]; double value =
	 * (double) dailyEarned[2]; BigDecimal rate = (BigDecimal) dailyEarned[3]; if
	 * (rate != null) earnedValues.merge(CommonUtil.convertDateToString(date) + "-"
	 * + costId, value * rate.doubleValue(), Double::sum); } } Map<String,
	 * CostReportTO> map = new HashMap<>(); for (CostReportTO costReportResp :
	 * resp.getCostReportResps()) { String key = costReportResp.getDate() + "-" +
	 * costReportResp.getCostItemId();
	 * costReportResp.setEarnedValue(CommonUtil.ifNullGetDefaultValue(earnedValues.
	 * get(key))); if (map.containsKey(key)) { CostReportTO val = map.get(key);
	 * val.setCostAmount(val.getCostAmount() + costReportResp.getCostAmount());
	 * val.setEarnedValue(costReportResp.getEarnedValue()); } else { map.put(key,
	 * costReportResp); } } DateWiseCostReportResp newResp = new
	 * DateWiseCostReportResp(); newResp.getCostReportResps() .addAll(map
	 * .values().stream().sorted(Comparator.comparing(CostReportTO::getDateObj)
	 * .thenComparing(CostReportTO::getProjId).thenComparing(CostReportTO::
	 * getCostItemId)) .collect(Collectors.toList())); return newResp; }
	 */

	// PMCP Report

	public DateWiseCostReportResp getDateWisePlanActualEarned(CostReportReq costReportReq) {
		costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));
		DateWiseCostReportResp resp = getDatewiseActualCostDetails(costReportReq);
		Map<String, Double> earnedValues = new HashMap<>();
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

	// for CMCP Report

	public DateWiseCostReportResp getDatewiseActualCostDetails(CostReportReq costReportReq) {

		if (costReportReq.getCostcodeIds() == null || costReportReq.getCostcodeIds().size() <= 0) // costReportReq.getCostcodeIds()!=null
																									// &&
		{
			List<Long> costcodeIds = projCostItemRepositoryCopy.findMultiProjCostIds(costReportReq.getProjIds());
			System.out.println("costcodeIds : size:" + costcodeIds.size());
			costReportReq.setCostcodeIds(costcodeIds);
		}
		if (costReportReq.getCmpIds() == null || costReportReq.getCmpIds().size() <= 0) // costReportReq.getCmpIds()!=null
																						// &&
		{
			costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));
		}
		if (costReportReq.getCategories() == null || costReportReq.getCategories().size() <= 0) // costReportReq.getCategories()!=null
																								// &&
		{
			ArrayList<String> categories = new ArrayList<String>();
			categories.add("direct");
			categories.add("in-direct");
			costReportReq.setCategories(categories);
		}
		System.out.println("CostCodeActualDetailsServiceImpl:costReportReq:Categories().size:"
				+ costReportReq.getCategories().size());
		System.out.println("CostCodeActualDetailsServiceImpl:costReportReq: CostcodeIds().size:"
				+ costReportReq.getCostcodeIds().size());
		System.out.println(
				"CostCodeActualDetailsServiceImpl:costReportReq: CmpIds().size:" + costReportReq.getCmpIds().size());
		System.out.println(
				"CostCodeActualDetailsServiceImpl:costReportReq: ProjIds().size:" + costReportReq.getProjIds().size());

		System.out.println("FromDate :" + costReportReq.getFromDate());
		System.out.println("ToDate :" + costReportReq.getToDate());

		System.out.println("22 CostCodeActualDetailsServiceImpl:costReportReq:Categories().size:"
				+ costReportReq.getCategories().size());
		System.out.println("22 CostCodeActualDetailsServiceImpl:costReportReq: CostcodeIds().size:"
				+ costReportReq.getCostcodeIds().size());
		System.out.println(
				"22 CostCodeActualDetailsServiceImpl:costReportReq: CmpIds().size:" + costReportReq.getCmpIds().size());
		System.out.println("22 CostCodeActualDetailsServiceImpl:costReportReq: ProjIds().size:"
				+ costReportReq.getProjIds().size());

		DateWiseCostReportResp costReportResp = new DateWiseCostReportResp();
		List<CostReportTO> list = costReportResp.getCostReportResps();
		list.addAll(getEmpData(costReportReq));
		list.addAll(getPlantData(costReportReq));
		list.addAll(getMaterialActual(costReportReq));
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
			for (Map.Entry<Date, Double> hrsPerDay : hrsPerDays.entrySet()) {
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
		for (Map.Entry<Long, Map<Date, Map<Long, List<String>>>> entry : paidLeavesByProj.entrySet()) {
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
							ProjCostItemEntity costCode = projCostItemRepositoryCopy
									.findOne(rate.getLeaveCostItemId());
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

	private List<CostReportTO> getPlantData(CostReportReq costReportReq) {
		List<CostReportTO> list = new ArrayList<>();
		list.addAll(findPlantMobRates(costReportReq));
		list.addAll(findPlantDeMobRates(costReportReq));
		list.addAll(findPlantWorkDairyDetails(costReportReq));
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
			Map<Date, PlantChargeOutRateTO> plantRateWithDates = chargeRates.get(plantReg.getId());
			PlantChargeOutRateTO plantRate = null;
			Double usedHrs = (Double) workDairyHr[4];
			Double idleHrs = (Double) workDairyHr[5];
			if (plantRateWithDates != null && !plantRateWithDates.values().isEmpty()) {
				Set<Date> effectiveDates = plantRateWithDates.keySet().stream()
						.collect(Collectors.toCollection(TreeSet::new));
				List<Date> mappedDate = effectiveDates.stream()
						.filter(d -> d.getTime() == workDairyDate.getTime() || d.before(workDairyDate))
						.collect(Collectors.toList());
				if (mappedDate.isEmpty())
					continue;
				Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
				plantRate = plantRateWithDates.get(effectiveDate);
			}
			if (CommonUtil.isNonBlankDouble(usedHrs)) {
				CostReportTO plantInfoReportClone = new CostReportTO();
				BeanUtils.copyProperties(resp, plantInfoReportClone);
				plantInfoReportClone.setType("Used WorkDairy");
				plantInfoReportClone.setQuantity(usedHrs);
				if (plantRate != null && plantRate.getNormalRate() != null) {
					double pRate;
					if (plantDtl.getShiftName() != null && plantDtl.getShiftName().toLowerCase().contains("single")) {
						pRate = plantRate.getNormalRate().doubleValue();
					} else {
						pRate = plantRate.getDoubleRate().doubleValue();
					}
					Double amount = Double.valueOf(0);
					if (CommonUtil.isNonBlankDouble(pRate)) {
						amount = ((Double) usedHrs) * pRate;
					}
					plantInfoReportClone.setPlantRateType(plantRate.getPlantRateType());
					plantInfoReportClone.setCostAmount(amount);
					plantInfoReportClone.setRatePerUnit(pRate);
				}
				list.add(plantInfoReportClone);
			}
			if (CommonUtil.isNonBlankDouble(idleHrs)) {
				CostReportTO plantInfoReportClone = new CostReportTO();
				BeanUtils.copyProperties(resp, plantInfoReportClone);
				plantInfoReportClone.setType("Idle WorkDairy");
				plantInfoReportClone.setQuantity(idleHrs);
				if (plantRate != null && plantRate.getIdleRate() != null) {
					Double amount = Double.valueOf(0);
					if (CommonUtil.objectNotNull(plantRate.getIdleRate())
							&& CommonUtil.isNonBlankDouble(plantRate.getIdleRate().doubleValue())) {
						amount = ((Double) idleHrs) * plantRate.getIdleRate().doubleValue();
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

	private void setProjectInfo(ProjMstrEntity projMstr, CostItemReportTO resp) {
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

	// ------ end of Report Code
	private com.rjtech.projectlib.dto.CostReportTO getProjectLibCostReportTO(
			com.rjtech.reports.cost.resp.CostReportTO costReportTOInput) {
		com.rjtech.projectlib.dto.CostReportTO costReportTORtn = new com.rjtech.projectlib.dto.CostReportTO();

		costReportTORtn.setCategory(costReportTOInput.getCategory());
		costReportTORtn.setCmpId(costReportTOInput.getCmpId());
		costReportTORtn.setCmpName(costReportTOInput.getCmpName());
		costReportTORtn.setCostAmount(costReportTOInput.getCostAmount());
		costReportTORtn.setCostItemCode(costReportTOInput.getCostItemCode());
		costReportTORtn.setCostItemId(costReportTOInput.getCostItemId());
		costReportTORtn.setCostItemName(costReportTOInput.getCostItemName());
		costReportTORtn.setCostSubGroupCode(costReportTOInput.getCostSubGroupCode());
		costReportTORtn.setCostSubGroupId(costReportTOInput.getCostSubGroupId());
		costReportTORtn.setCostSubGroupName(costReportTOInput.getCostSubGroupName());
		costReportTORtn.setCurrencyCode(costReportTOInput.getCurrencyCode());
		costReportTORtn.setDate(costReportTOInput.getDate());
		costReportTORtn.setEarnedValue(costReportTOInput.getEarnedValue());
		costReportTORtn.setEmpFirstName(costReportTOInput.getCategory());
		costReportTORtn.setEmpId(costReportTOInput.getEmpId());
		costReportTORtn.setEmpLastName(costReportTOInput.getCategory());
		costReportTORtn.setEpsId(costReportTOInput.getEpsId());
		costReportTORtn.setEpsName(costReportTOInput.getEpsName());
		costReportTORtn.setInvoiceDate(costReportTOInput.getInvoiceDate());
		costReportTORtn.setInvoiceNumber(costReportTOInput.getInvoiceNumber());
		costReportTORtn.setMaterialSubGroupCode(costReportTOInput.getMaterialSubGroupCode());
		costReportTORtn.setMaterialSubGroupId(costReportTOInput.getMaterialSubGroupId());
		costReportTORtn.setMaterialSubGroupName(costReportTOInput.getMaterialSubGroupName());
		costReportTORtn.setMatId(costReportTOInput.getMatId());
		costReportTORtn.setPlantDesc(costReportTOInput.getPlantDesc());
		costReportTORtn.setPlantId(costReportTOInput.getPlantId());
		costReportTORtn.setPlantMake(costReportTOInput.getPlantMake());
		costReportTORtn.setPlantModel(costReportTOInput.getPlantModel());
		costReportTORtn.setPlantRateType(costReportTOInput.getPlantRateType());
		costReportTORtn.setPlantRegNumber(costReportTOInput.getPlantRegNumber());
		costReportTORtn.setProjId(costReportTOInput.getProjId());
		costReportTORtn.setProjName(costReportTOInput.getProjName());
		costReportTORtn.setRatePerUnit(costReportTOInput.getRatePerUnit());
		costReportTORtn.setResourceId(costReportTOInput.getResourceId());
		costReportTORtn.setType(costReportTOInput.getType());
		costReportTORtn.setUnitOfMesure(costReportTOInput.getUnitOfMesure());

		return costReportTORtn;
	}

	@Override
	public ProjPMCPCostStatementsResp getProjPMCPCostStatements(ProjPMCPItemGetReq projPMCPItemGetReq) {

		ProjPMCPCostStatementsResp projPMCPCostStatementsResp = null;
		System.out.println("*** getProjPMCPCostStatements Started");

		System.out.println("ProjLibServiceImpl:getProjPMCMDetails:ProjId:" + projPMCPItemGetReq.getProjId());
		System.out.println("ProjLibServiceImpl:getProjPMCMDetails:ClientId:" + projPMCPItemGetReq.getClientId());
		System.out.println("ProjLibServiceImpl:getProjPMCMDetails:Status:" + projPMCPItemGetReq.getStatus());
		System.out.println(
				"ProjectLibController:getProjPMCMDetails:getProjStatusDate:" + projPMCPItemGetReq.getFromDate());
		System.out.println("ProjectLibController:getProjPMCMDetails:getToDate:" + projPMCPItemGetReq.getToDate());
		System.out.println(
				"ProjectLibController:getProjPMCMDetails:getProjIds:" + projPMCPItemGetReq.getProjIds().size());
		System.out.println(projPMCPItemGetReq.getProjIds());
		// -- Project Cost Details
		// ProjPMCMItemResp projPMCMItemResp = new ProjPMCMItemResp();
		System.out.println(" *** ProjLibServiceImpl:ProjStatusDate : " + projPMCPItemGetReq.getFromDate());
		Date pmFromStatusDate = CommonUtil.convertStringToDate(projPMCPItemGetReq.getFromDate());
		Date projStatusDate = CommonUtil.convertStringToDate(projPMCPItemGetReq.getFromDate());

		System.out.println("pmFromStatusDate :" + pmFromStatusDate);
		System.out.println("projStatusDate :" + projStatusDate);
		// --- for Cost % ---

		System.out.println("**** BEFORE Starting COST PERCENTAGE **********");

		if (projPMCPItemGetReq != null && projPMCPItemGetReq.getProjIds() != null
				&& projPMCPItemGetReq.getProjIds().size() > 0) {
			System.out.println(
					"ProjectLibController:getProjPMCMDetails:getProjIds:" + projPMCPItemGetReq.getProjIds().size());
			System.out.println(projPMCPItemGetReq.getProjIds());

			projPMCPCostStatementsResp = getCostStatements(projPMCPItemGetReq);

			if (projPMCPCostStatementsResp != null && projPMCPCostStatementsResp.getProjCostStmtDtlTOs() != null
					&& projPMCPCostStatementsResp.getProjCostStmtDtlTOs().size() > 0) {
				projPMCPCostStatementsResp.setProjCostStmtDtlTOs(projPMCPCostStatementsResp.getProjCostStmtDtlTOs());
			} else {
				System.out.println("projPMCPCostStatementsResp ProjCostStmtDtlTOs() is NULL");
			}
		} else {
			System.out.println("projPMCPItemGetReq.getProjIds is NULL");
		}

		// Here Updating the Actual Percentage Values
		projPMCPItemGetReq.setCallFromActualCostModule(true);
		System.out.println(
				"ProjectLibController:getProjPMCMDetails:getProjStatusDate:" + projPMCPItemGetReq.getFromDate());
		System.out.println("ProjectLibController:getProjPMCMDetails:getToDate:" + projPMCPItemGetReq.getToDate());

		updateProjPMCPActualCostDetails(projPMCPCostStatementsResp, projPMCPItemGetReq);
		return projPMCPCostStatementsResp;
	}

	public void updateProjPMCPActualCostDetails(ProjPMCPCostStatementsResp projPMCPCostStatementsResp,
			ProjPMCPItemGetReq projPMCPItemGetReq) {
		System.out.println("updateProjPMCPActualCostDetails");

		ProjPMCPItemResp projPMCPItemResp = getProjReportPMCPDetails(projPMCPItemGetReq);
		HashMap<String, PeriodCostTO> actualCostModuleMap = projPMCPItemResp.getActualCostModuleMap();
		System.out
				.println("** updateProjPMCPActualCostDetails : actualCostModuleMap:Size:" + actualCostModuleMap.size());
		System.out.println(actualCostModuleMap);
		System.out.println("** updateProjPMCPActualCostDetails : getProjCostStmtDtlTOs:Size:"
				+ projPMCPCostStatementsResp.getProjCostStmtDtlTOs().size());

		for (ProjCostStmtDtlTOCopy projCostStmtDtlTO : projPMCPCostStatementsResp.getProjCostStmtDtlTOs()) {
			System.out.println("CostStmtDtlTO Id:" + projCostStmtDtlTO.getId() + ":Code:" + projCostStmtDtlTO.getCode()
					+ ":CostId:" + projCostStmtDtlTO.getCostId() + "CostId:" + projCostStmtDtlTO.getName());
			System.out.println("getOriginalCostBudget:" + projCostStmtDtlTO.getOriginalCostBudget().getTotal());

			projCostStmtDtlTO.setActualCostPrevPeriod(actualCostModuleMap.get(projCostStmtDtlTO.getCode()));
			projCostStmtDtlTO.setActualCostReportPeriod(actualCostModuleMap.get(projCostStmtDtlTO.getCode()));
			projCostStmtDtlTO.setActualCostUpToDatePeriod(actualCostModuleMap.get(projCostStmtDtlTO.getCode()));

			updateWithActualCostDetails(projCostStmtDtlTO, actualCostModuleMap);
		}
	}

	private void updateWithActualCostDetails(ProjCostStmtDtlTOCopy projCostStmtDtlTOCopy,
			HashMap<String, PeriodCostTO> actualCostModuleMap) {
		System.out.println("updateWithActualCostDetails");
		for (ProjCostStmtDtlTOCopy child : projCostStmtDtlTOCopy.getProjCostStmtDtlTOCopys()) {
			System.out.println(" : " + child.getCode() + " : OriginalCostBudget().getTotal : "
					+ child.getOriginalCostBudget().getTotal());
			child.setActualCostPrevPeriod(actualCostModuleMap.get(child.getCode()));
			child.setActualCostReportPeriod(actualCostModuleMap.get(child.getCode()));
			child.setActualCostUpToDatePeriod(actualCostModuleMap.get(child.getCode()));

			// originalCostBudgetTotalMap.put(child.getCode(),child.getOriginalCostBudget().getTotal());
			if (!child.isItem()) {
				if (child.getProjCostStmtDtlTOCopys().size() > 0) {
					updateWithActualCostDetails(child, actualCostModuleMap);
				}
			}
		}
	}

	private Map<ProjPMCMItemTO, List<ReportPMCMValueTO>> processProgressPeriodicalResponse(
			List<ProjPMCMItemEntity> entities) {

		System.out.println("processProgressPeriodicalResponse:entities Size:" + entities.size());
		Map<ProjPMCMItemTO, List<ReportPMCMValueTO>> plantHrsMap = new HashMap<>();
		for (ProjPMCMItemEntity progressEntity : entities) {
			ProjPMCMItemTO reportTO = new ProjPMCMItemTO();
			List<ReportPMCMValueTO> reportHoursTOs = new ArrayList<>();

			reportTO = (ProjPMCMItemTO) ProjPMCMItemHandler.populateSORITems(progressEntity, false);

			System.out.println("progressEntity:PmStatusDate" + progressEntity.getPmStatusDate() + " :PmContractAmount: "
					+ progressEntity.getPmContractAmount());
			reportHoursTOs
					.add(new ReportPMCMValueTO(progressEntity.getPmStatusDate(), progressEntity.getPmContractAmount()));
			updateMap(plantHrsMap, reportTO, reportHoursTOs);
		}
		return plantHrsMap;
	}

	private void updateMap(Map<ProjPMCMItemTO, List<ReportPMCMValueTO>> plantHrsMap, ProjPMCMItemTO plantActualHrsTO,
			List<ReportPMCMValueTO> reportHoursTOs) {
		if (plantHrsMap.containsKey(plantActualHrsTO)) {
			List<ReportPMCMValueTO> existingHrs = plantHrsMap.get(plantActualHrsTO);
			int itemIndex = -1;
			for (ReportPMCMValueTO newReportHrs : reportHoursTOs) {
				itemIndex = existingHrs.indexOf(newReportHrs);
				if (itemIndex != -1) {
					if (newReportHrs != null && newReportHrs.getValue() != null) {
						existingHrs.get(itemIndex).add(newReportHrs);
					} else {
						System.out.println("newReportHrs is NULL");
					}

				} else {
					existingHrs.add(newReportHrs);
				}
			}
		} else {
			plantHrsMap.put(plantActualHrsTO, reportHoursTOs);
		}
	}

	public HashMap<String, String> getProjCostItemEntitiesMap(List<Long> projIds, Integer status) {

		// -- Project Cost Details
		HashMap<String, String> projCostItemEntitiesMap = new HashMap<String, String>();
		List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository.findMultiProjCostItems(projIds, status);
		System.out.println("projCostItemEntities Size :" + projCostItemEntities.size());
		System.out.println("projCostItemEntities :" + projCostItemEntities);

		for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
			System.out.println("CostItemEntity Code:" + projCostItemEntity.getCode());
			System.out.println("CostItemEntity Name:" + projCostItemEntity.getName());
			projCostItemEntitiesMap.put(projCostItemEntity.getCode(), projCostItemEntity.getName());
		}
		// ------------------------------
		return projCostItemEntitiesMap;
	}

	// for Actual Coset %

	public ProjPMCPCostStatementsResp getCostStatements(ProjPMCPItemGetReq projPMCPItemGetReq) {

		System.out.println("ProjLibServiceImpl:getCostStatements:ProjId:" + projPMCPItemGetReq.getProjId());
		System.out.println("ProjLibServiceImpl:getCostStatements:ClientId:" + projPMCPItemGetReq.getClientId());
		System.out.println("ProjLibServiceImpl:getCostStatements:Status:" + projPMCPItemGetReq.getStatus());
		System.out
				.println("ProjLibServiceImpl:getCostStatements:ProjIds:size:" + projPMCPItemGetReq.getProjIds().size());

		List<Long> projIds = projPMCPItemGetReq.getProjIds();
		ProjPMCPCostStatementsResp projPMCPCostStatementsResp = new ProjPMCPCostStatementsResp();
		for (Long projId : projIds) {
			System.out.println("In Loop projId : " + projId);
			projPMCPItemGetReq.setProjId(projId);
			List<ProjCostStmtDtlTOCopy> costStatementsSummaryTOs = getProjCostStatements(projPMCPItemGetReq)
					.getProjCostStmtDtlTOs();
			projPMCPCostStatementsResp.getProjCostStmtDtlTOs().addAll(costStatementsSummaryTOs);
		}

		return projPMCPCostStatementsResp;
	}

	public ProjPMCPCostStatementsResp getProjCostStatements(ProjPMCPItemGetReq projPMCPItemGetReq) {
		System.out.println("ProjCostStatements projPMCPItemGetReq ProjId : " + projPMCPItemGetReq.getProjId());
		ProjPMCPCostStatementsResp projPMCPCostStatementsResp = new ProjPMCPCostStatementsResp();
		Map<Long, ProjCostStmtDtlEntity> projCostStmtMap = new HashMap<>();

		List<ProjCostItemEntity> projCostItemEntities = projCostItemRepositoryCopy
				.findCostDetails(projPMCPItemGetReq.getProjId(), StatusCodes.ACTIVE.getValue());
		System.out.println("projCostItemEntities:size:" + projCostItemEntities.size());

		List<ProjCostStmtDtlEntity> pojCostStmtDtlEntities = projCostStatementsRepository
				.findProjCostStatements(projPMCPItemGetReq.getProjId(), projPMCPItemGetReq.getStatus());

		System.out.println("pojCostStmtDtlEntities:size:" + pojCostStmtDtlEntities.size());
		for (ProjCostStmtDtlEntity projCostStmtDtlEntity : pojCostStmtDtlEntities) {
			projCostStmtMap.put(projCostStmtDtlEntity.getProjCostItemEntity().getId(), projCostStmtDtlEntity);
		}

		Map<Long, CostActualHoursTO> actualHrs = actualAmountService.getCostStmt(projPMCPItemGetReq.getProjId());
		System.out.println("actualHrs:size:" + actualHrs.size());
		ProjEstimateEntity projEstimate = projEstimateRepository
				.findEstimateCostEstimate(projPMCPItemGetReq.getProjId());

		Map<Long, BigDecimal> earnedValues = getEarnedValueForCostId(projPMCPItemGetReq.getProjId());

		for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
			/*
			 * ProjCostStmtDtlTO costStmts =
			 * ProjCostStmtDtlHandler.populateProjCostStmts(projCostItemEntity,
			 * projCostStmtMap);
			 */
			ProjCostStmtDtlTOCopy costStmts = ProjCostStmtDtlHandlerCopy.populateProjCostStmts(projCostItemEntity,
					projCostStmtMap);
			for (ProjCostStmtDtlTOCopy projCostStmtDtlTO : costStmts.getProjCostStmtDtlTOCopys()) {
				if (earnedValues.get(projCostStmtDtlTO.getCostId()) != null) {
					projCostStmtDtlTO.setEarnedValue(earnedValues.get(projCostStmtDtlTO.getCostId()));
				}
				calculateValues(projCostStmtDtlTO, actualHrs, projEstimate);
				for (ProjCostStmtDtlTOCopy childCostStmts : projCostStmtDtlTO.getProjCostStmtDtlTOCopys()) {
					if (earnedValues.get(childCostStmts.getCostId()) != null) {
						childCostStmts.setEarnedValue(earnedValues.get(childCostStmts.getCostId()));
					}
					calculateValues(childCostStmts, actualHrs, projEstimate);
				}
			}
			projPMCPCostStatementsResp.getProjCostStmtDtlTOs().add(costStmts);
		}
		return projPMCPCostStatementsResp;
	}

	private void calculateValues(ProjCostStmtDtlTOCopy childCostStmt, Map<Long, CostActualHoursTO> actualHrs,
			ProjEstimateEntity projEstimate) {
		if (childCostStmt != null && childCostStmt.getCostId() != null) {
			CostActualHoursTO actualData = actualHrs.get(childCostStmt.getCostId());
			ProjCostBudgetTOCopy budgetObj = childCostStmt.getActualCostBudget();
			setActualBudgetValues(budgetObj, actualData, actualHrs.get(childCostStmt.getId()));
			childCostStmt.setActualCostBudget(budgetObj);
			if (projEstimate != null) {
				childCostStmt.setEstimateType(projEstimate.getFormulaType());
				calculatePerformance(childCostStmt);
			}
		}
	}

	private void setActualBudgetValues(ProjCostBudgetTOCopy budgetObj, CostActualHoursTO actualData,
			CostActualHoursTO actualIdData) {
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
		if (actualIdData != null) {
			if (budgetObj.getOtherCost() != null) {
				budgetObj.setOtherCost(budgetObj.getOtherCost().add(BigDecimal.valueOf(actualIdData.getOthersCost())));
			} else {
				budgetObj.setOtherCost(BigDecimal.valueOf(actualIdData.getOthersCost()));
			}
		}
	}

	private void calculatePerformance(ProjCostStmtDtlTOCopy childCostStmt) {
		ProjCostBudgetTOCopy original = childCostStmt.getOriginalCostBudget();
		if (original == null)
			return;

		ProjCostBudgetTOCopy actual = childCostStmt.getActualCostBudget();
		BigDecimal actualTotal = (CommonUtil.ifNullGetDefaultValue(actual.getLabourCost()))
				.add(CommonUtil.ifNullGetDefaultValue(actual.getMaterialCost()))
				.add(CommonUtil.ifNullGetDefaultValue(actual.getPlantCost()))
				.add(CommonUtil.ifNullGetDefaultValue(actual.getOtherCost()));
		ProjCostBudgetTOCopy revised = childCostStmt.getRevisedCostBudget();
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
		if (!originalTotal.equals(BigDecimal.ZERO)) {
			childCostStmt.setSpentCost(
					actualTotal.divide(originalTotal, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
			if (childCostStmt.getEarnedValue() != null && !BigDecimal.ZERO.equals(childCostStmt.getEarnedValue()))
				childCostStmt.setWorkProgress(childCostStmt.getEarnedValue().divide(originalTotal, RoundingMode.HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
		}
		if (!actualTotal.equals(BigDecimal.ZERO) && childCostStmt.getEarnedValue() != null
				&& !BigDecimal.ZERO.equals(childCostStmt.getEarnedValue())) {
			childCostStmt
					.setProductivityFactor(childCostStmt.getEarnedValue().divide(actualTotal, RoundingMode.HALF_UP));
		}
	}

	private Map<Long, BigDecimal> getEarnedValueForCostId(long projId) {
		Map<Long, BigDecimal> earnedValues = new HashMap<>();
		List<Object[]> resp = projSowTotalActualRepository.getEarnedValuePerCostCode(projId);
		for (Object[] object : resp) {
			if ((BigDecimal) object[2] != null && object[1] != null) {
				BigDecimal rate = ((BigDecimal) object[2]).multiply(BigDecimal.valueOf((Double) object[1]));
				earnedValues.merge((Long) object[0], rate, BigDecimal::add);
			}
		}
		return earnedValues;
	}

	// This function works for approval process of SOE Item
	public ProjSOEItemResp projSOEApproval(ProjSOEItemGetReq projSOEItemGetReq) {
		ProjSOEActivityLogEntity projSOEActivityLogEntity = new ProjSOEActivityLogEntity();
		ProjSOEItemResp projSOEItemResp = new ProjSOEItemResp();
		ProjSOEItemTO projSOEItemTO = null;
		String approvalMode = "";
		String approvalType = "";
		String status = "";
		char isExternalApprovalRequired = 'N';
		String comments = null;
		String activity_description = null;
		String activity_type = projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")
				|| projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL") ? "SUBMIT_FOR_APPROVAL"
						: "APPROVAL";

		if (projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL")
				|| projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")) {
			approvalMode = "SUBMITTED";
			approvalType = projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL") ? "EXTERNAL"
					: "INTERNAL";
		} else {
			approvalMode = "APPROVED";
			approvalType = projSOEItemGetReq.getSoeStatus().equals("EXTERNAL_APPROVAL") ? "EXTERNAL" : "INTERNAL";
		}
		System.out.println("projSOEItemGetReq data:");
		System.out.println(projSOEItemGetReq); // soeIds
		Long loggedInUser = AppUserUtils.getUserId();

		if (approvalType.equals("INTERNAL")) {
			System.out.println("if condition of INTERNAL block of projSOEApproval function");
			// projSOEActivityLogEntity.setProjSOEItemEntity( projSOEItemEntityCopy );
			if (projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")) {
				status = "SUBMITTED_FOR_INTERNAL_APPROVAL";
		//		projSOETrackDeatils(projSOEItemGetReq);
				isExternalApprovalRequired = 'N';
				// toUserEntity.setUserId( projSOEItemGetReq.getInternalApprovalUserId() );
			} else {
				status = (projSOEItemGetReq.getIsExternalApprovalRequired() == 'N') ? "FINALIZED" : "INTERNAL_APPROVED";
				isExternalApprovalRequired = projSOEItemGetReq.getIsExternalApprovalRequired();
				comments = projSOEItemGetReq.getInternalApproverComments();
			}
			System.out.println("get request soe status" + projSOEItemGetReq.getSoeStatus());
			System.out.println("get request soe status" + String.valueOf(isExternalApprovalRequired));
			for (Long soeItemId : projSOEItemGetReq.getSoeIds()) {
				if (projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")
						|| projSOEItemGetReq.getSoeStatus().equals("INTERNAL_APPROVAL")) {
					if (projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")) {
						projSOEItemRepository.updateSOEInternalApproverDetailsById(soeItemId, status,
								projSOEItemGetReq.getInternalApprovalUserId(), approvalMode, loggedInUser);
					} else {
						Integer soe_status = (status.equals("FINALIZED")) ? StatusCodes.ACTIVE.getValue()
								: StatusCodes.DEFAULT.getValue();
						// System.out.println(soe_status);
						projSOEItemRepository.updateSOEInternalApprovalDetailsById(soeItemId, status,
								Character.valueOf(isExternalApprovalRequired), approvalMode, comments, soe_status);
					}
					if (status.equals("FINALIZED")) {
						projSOWItemRepository.updateStatusBySOEId(soeItemId, StatusCodes.ACTIVE.getValue().intValue());
					}
				}

			}
			List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository
					.findSOEDetails(projSOEItemGetReq.getProjId());

			/*
			 * List<ProjSOEItemEntity> projectSOEParentDtls =
			 * projSOEItemRepository.findSOEParentIds(projSOEItemGetReq.getSoeParentIds());
			 * for (ProjSOEItemEntity projSOEItemEntity : projectSOEParentDtls) {
			 * sendSubmitInternalMail(projSOEItemEntity); }
			 */
			 
			SoeNotificationsEntity notificationEntity = new SoeNotificationsEntity();
			notificationEntity.setDate(new Date());
			notificationEntity.setNotificationMsg(approvalMode);
			notificationEntity.setNotificationStatus(projSOEItemGetReq.getSoeStatus());
			notificationEntity.setStatus(1);
			soeNotificationRepository.save(notificationEntity);
			for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
				notificationEntity.setProjSOEItemEntity(projSOEItemEntity);
			}
		} else {
			System.out.println("else condition of INTERNAL block of projSOEApproval");
			status = approvalMode.equals("SUBMITTED") ? "SUBMITTED_FOR_EXTERNAL_APPROVAL" : "FINALIZED";
			activity_description = approvalMode.equals("SUBMITTED") ? "Submitted for External Approval"
					: "Approved Externally";
			if (projSOEItemGetReq.getSoeStatus().equals("EXTERNAL_APPROVAL")) {
				comments = projSOEItemGetReq.getExternalApproverComments();
			}
			for (Long soeItemId : projSOEItemGetReq.getSoeIds()) {
				if (projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL")
						|| projSOEItemGetReq.getSoeStatus().equals("EXTERNAL_APPROVAL")) {
					// projSOEItemRepository.updateSOEExternalApprovalDetailsById( soeItemId,
					// status, projSOEItemGetReq.getExternalApprovalUserId(), approvalMode, comments
					// );
					if (projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL")) {
						projSOEItemRepository.updateSOEExternalApproverDetailsById(soeItemId, status,
								projSOEItemGetReq.getExternalApprovalUserId(), approvalMode);
					} else {
						projSOEItemRepository.updateSOEExternalApprovalDetailsById(soeItemId, status, approvalMode,
								comments, StatusCodes.ACTIVE.getValue());
					}

					if (status.equals("FINALIZED")) {
						projSOWItemRepository.updateStatusBySOEId(soeItemId, StatusCodes.ACTIVE.getValue().intValue());
					}
				}
				List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository
						.findSOEDetails(projSOEItemGetReq.getProjId());
				/*
				 * List<ProjSOEItemEntity> projectSOEItemEntitiyess =
				 * projSOEItemRepository.findSOEParentIds(projSOEItemGetReq.getSoeParentIds());
				 * for(ProjSOEItemEntity projSOEItemEntitys : projectSOEItemEntitiyess) {
				 * sendSubmitInternalMail(projSOEItemEntitys); }
				 */		
				SoeNotificationsEntity notificationEntity = new SoeNotificationsEntity();
				notificationEntity.setDate(new Date());
				notificationEntity.setNotificationMsg(approvalMode);
				notificationEntity.setNotificationStatus(projSOEItemGetReq.getSoeStatus());
				notificationEntity.setStatus(1);
		        soeNotificationRepository.save(notificationEntity);
				for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
					notificationEntity.setProjSOEItemEntity(projSOEItemEntity);	
				}
			}
			// projSOEItemRepository.updateSOEExternalApprovalDetailsById(
			// projSOEItemGetReq.getSoeId(), status,
			// projSOEItemGetReq.getExternalApprovalUserId(), approvalMode, comments );
		}
		System.out.println("projSOEItemGetReq.getProjId()2821 "+projSOEItemGetReq.getProjId());
		System.out.println("projSOEItemGetReq.getSoeIds()2822 "+projSOEItemGetReq.getSoeIds());
		
		List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository
				.findMultiProjSOEItemsById(projSOEItemGetReq.getProjId(), projSOEItemGetReq.getSoeIds(), 0);
		System.out.println("projSOEItemEntities2824 "+projSOEItemEntities.size());
		for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
            System.out.println("projSOEItemEntity2825 "+projSOEItemEntity);
			ProjSOEItemEntity projSOEItemEntityCopy = new ProjSOEItemEntity();
			projSOEItemEntityCopy.setId(projSOEItemEntity.getId());

			UserMstrEntity fromUserEntity = new UserMstrEntity();
			fromUserEntity.setUserId(projSOEItemGetReq.getLoggedInUser());
			UserMstrEntity toUserEntity = new UserMstrEntity();
			if (approvalType.equals("INTERNAL")) {
				System.out.println("if condition of INTERNAL block of projSOEApproval function inside the for loop");
				// projSOEActivityLogEntity.setProjSOEItemEntity( projSOEItemEntityCopy )
				System.out.println("projSOEItemGetReq.getResubmitStatus()2846 "+projSOEItemGetReq.getResubmitStatus());
				if (projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")) {
					activity_description = "Submitted for Internal Approval";
					toUserEntity.setUserId(projSOEItemGetReq.getInternalApprovalUserId());
					projSOEActivityLogEntity.setToUser(toUserEntity);
				}
				else {
					isExternalApprovalRequired = projSOEItemGetReq.getIsExternalApprovalRequired();
					comments = projSOEItemGetReq.getInternalApproverComments();
					activity_description = "Approved Internally";
				}
				// projSOEItemRepository.updateSOEInternalApprovalDetailsById(
				// projSOEItemGetReq.getSoeId(), status,
				// Character.valueOf(isExternalApprovalRequired),
				// projSOEItemGetReq.getInternalApprovalUserId(), approvalMode, comments );
			} else {
				System.out.println("else condition of INTERNAL block of projSOEApproval function inside the for loop");
				activity_description = approvalMode.equals("SUBMITTED") ? "Submitted for External Approval"
						: "Approved Externally";
				if (projSOEItemGetReq.getSoeStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL")) {
					toUserEntity.setUserId(projSOEItemGetReq.getExternalApprovalUserId());
					projSOEActivityLogEntity.setToUser(toUserEntity);
				} 
					
					
			}
			if(CommonUtil.objectNotNull(projSOEItemGetReq.getResubmitStatus())) {
				if(projSOEItemGetReq.getResubmitStatus().equals("Resubmitted For Internal Approval")) {
					activity_description = "Resubmitted For Internal Approval";
	            }else
	            	if(projSOEItemGetReq.getResubmitStatus().equals("Resubmitted For External Approval")) {
	            		activity_description = "Resubmitted For External Approval";
	            	}
			}	
			
			boolean update = false;
			if(CommonUtil.isListHasData(projSOEItemGetReq.getViewSoeIds())) {
				List<ProjSOEActivityLogEntity> projSOEActivityLogEntites = projSOEActivityLogRepository.getActivityDetailsBySoeIds(projSOEItemGetReq.getViewSoeIds());
				for(ProjSOEActivityLogEntity  projSOEActivityLogEntitys : projSOEActivityLogEntites) {
					if(projSOEActivityLogEntitys.getDescription().equals(activity_description)) {
						update = true;
					}
					
						
				}
			}
			
			if(!update) {
				projSOEActivityLogEntity.setProjSOEItemEntity(projSOEItemEntityCopy);
				projSOEActivityLogEntity.setDescription(activity_description);
				projSOEActivityLogEntity.setFromUser(fromUserEntity);
				projSOEActivityLogEntity.setActivityType(activity_type);
				projSOEActivityLogRepository.save(projSOEActivityLogEntity);
			}
			projSOEItemResp.getProjSOEItemTOs()
            .add(ProjSOEItemHandler.populateProjSOEITems(projSOEItemEntity, true, userRoleMapRepository));
		}
		
		List<ProjSOEItemEntity> projectSOEItemEntities = projSOEItemRepository
				.findMultiProjSOEItemsById(projSOEItemGetReq.getProjId(), projSOEItemGetReq.getSoeIds(), 1);
		for (ProjSOEItemEntity projectSOEItemEntity : projectSOEItemEntities) {
			ProjSOEItemEntity projSOEItemEntityCopie = new ProjSOEItemEntity(); //
			UserMstrEntity fromUserEntity = new UserMstrEntity();
			fromUserEntity.setUserId(projSOEItemGetReq.getLoggedInUser());
			/*
			 * UserMstrEntity toUserEntity = new UserMstrEntity();
			 * toUserEntity.setUserId(projSOEItemGetReq.getInternalApprovalUserId());
			 * projSOEActivityLogEntity.setToUser(toUserEntity);
			 */
			projSOEItemEntityCopie.setId(projectSOEItemEntity.getId());
			projSOEActivityLogEntity.setProjSOEItemEntity(projSOEItemEntityCopie);
			projSOEActivityLogEntity.setDescription("FINALIZED");
			projSOEActivityLogEntity.setFromUser(fromUserEntity);
			projSOEActivityLogEntity.setActivityType("FINALIZED");
			projSOEActivityLogRepository.save(projSOEActivityLogEntity);
		}
		 
		// projSOEItemRepository.updateSOEInternalApprovalDetailsById(
		// projSOEItemGetReq.getProjId(), projSOEItemGetReq.getStatus() );
		return projSOEItemResp;
	}
	

	// This function works for Returned With Comments functionality of SOE item
	public ProjSOEItemResp returnSOEWithComments(ProjSOEItemGetReq projSOEItemGetReq) {
		ProjSOEItemResp projSOEItemResp = new ProjSOEItemResp();
		String soeReturnType = projSOEItemGetReq.getSoeStatus().equals("RETURN_FROM_INTERNAL_APPROVER") ? "INTERNAL"
				: "EXTERNAL";
		ProjSOEActivityLogEntity projSOEActivityLogEntity = new ProjSOEActivityLogEntity();
		String soe_status = "";
		UserMstrEntity toUserEntity = new UserMstrEntity();
		UserMstrEntity fromUserEntity = new UserMstrEntity();
		fromUserEntity.setUserId(projSOEItemGetReq.getLoggedInUser());

		for (Long soeItemId : projSOEItemGetReq.getSoeIds()) {
			System.out.println("soeItemId:" + soeItemId);
			ProjSOEItemEntity projSOEItemEntityData = projSOEItemRepository.findOne(soeItemId);
			System.out.println(projSOEItemEntityData);
			soe_status = projSOEItemEntityData.getSoeItemStatus().equals("SUBMITTED_FOR_INTERNAL_APPROVAL")
					? "RETURNED_FROM_INTERNAL_APPROVER"
					: "RETURNED_FROM_EXTERNAL_APPROVER";
			System.out.println("soe status:" + soe_status);
			String activity_description = projSOEItemEntityData.getSoeItemStatus()
					.equals("SUBMITTED_FOR_INTERNAL_APPROVAL") ? "Returned with comments from Internal Approver"
							: "Returned with comments from External Approver";
			Integer isItemReturned = 0;
			if (projSOEItemEntityData.getIsItemReturned() != null
					&& soeItemId.compareTo(projSOEItemGetReq.getSoeId()) == 0) {
				if (projSOEItemEntityData.getIsItemReturned() == 0) {
					projSOEItemRepository.updateApprovalStatusBySoeId(soeItemId, soe_status, 1);
				}
			} else {
				projSOEItemRepository.updateApprovalStatusBySoeId(soeItemId, soe_status);
			}

			boolean update = false;
			if(CommonUtil.isListHasData(projSOEItemGetReq.getViewSoeIds())) {
				List<ProjSOEActivityLogEntity> projSOEActivityLogEntites = projSOEActivityLogRepository.getActivityDetailsBySoeIds(projSOEItemGetReq.getViewSoeIds());
				for(ProjSOEActivityLogEntity  projSOEActivityLogEntitys : projSOEActivityLogEntites) {
					if(projSOEActivityLogEntitys.getDescription().equals(activity_description)) {
					//	System.out.println("projSOEActivityLogEntitys.getDescription()2894 "+projSOEActivityLogEntitys.getDescription());
						update = true;
					}
						
				}
			}
			
			if(!update) {
				ProjSOEItemEntity projSOEItemEntity = new ProjSOEItemEntity();
				projSOEItemEntity.setId(soeItemId);
				projSOEActivityLogEntity.setProjSOEItemEntity(projSOEItemEntity);
				projSOEActivityLogEntity.setDescription(activity_description);
				projSOEActivityLogEntity.setFromUser(fromUserEntity);
				projSOEActivityLogEntity.setToUser(projSOEItemEntityData.getCreatedBy());
				projSOEActivityLogEntity.setUserComments(projSOEItemGetReq.getComments());
				projSOEActivityLogEntity.setActivityType("RETURNED_WITH_COMMENTS");
				projSOEActivityLogRepository.save(projSOEActivityLogEntity);
			}

		projSOEItemResp.getProjSOEItemTOs()
					.add(ProjSOEItemHandler.populateProjSOEITems(projSOEItemEntityData, true, userRoleMapRepository));
		}
		projSOEItemRepository.updateApprovalStatusBySoeId(projSOEItemGetReq.getSoeId(), soe_status);
		if (soe_status == "RETURNED_FROM_INTERNAL_APPROVER" || soe_status == "RETURNED_FROM_EXTERNAL_APPROVER") {
			List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository
					.findSOEDetails(projSOEItemGetReq.getProjId());
			/*
			 * List<ProjSOEItemEntity> projectSOEItemEntitiyess =
			 * projSOEItemRepository.findSOEParentIds(projSOEItemGetReq.getSoeParentIds());
			 * for(ProjSOEItemEntity projSOEItemEntitys : projectSOEItemEntitiyess) {
			 * sendReturnInternalMail(projSOEItemEntitys, projSOEItemGetReq); }
			 */
			SoeNotificationsEntity notificationEntity = new SoeNotificationsEntity();
			notificationEntity.setDate(new Date());
			notificationEntity.setNotificationMsg(soe_status);
			notificationEntity.setNotificationStatus(projSOEItemGetReq.getSoeStatus());
			System.out.println("IprojSOEItemGetReq.getSoeStatus()" + projSOEItemGetReq.getSoeStatus());
			notificationEntity.setStatus(1);
			soeNotificationRepository.save(notificationEntity);
			for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
				notificationEntity.setProjSOEItemEntity(projSOEItemEntity);
		//		sendReturnInternalMail(projSOEItemEntity, projSOEItemGetReq);
			}
		}
		return projSOEItemResp;
	}

	// This function to display the soe activity log. This function displays all the
	// activities done in the Schedule of Estimates
	public ProjSOEActivityLogResp viewActivityRecords(ProjSOEItemGetReq projSOEItemGetReq) {
		ProjSOEActivityLogResp projSOEActivityLogResp = new ProjSOEActivityLogResp();
		List<ProjSOEActivityLogEntity> projSOEActivityEntities = projSOEActivityLogRepository
				.getActivityDetailsBySoeIds(projSOEItemGetReq.getSoeIds());
		ProjSOEActivityLogTO projSOEActivityLogTO = null;
		System.out.println("viewActivityRecords function from ProjLibServiceImpl class");
		System.out.println(projSOEActivityEntities.size());
		for (ProjSOEActivityLogEntity projSOEActivityEntity : projSOEActivityEntities) {
			projSOEActivityLogTO = new ProjSOEActivityLogTO();
			projSOEActivityLogTO.setActivityType(projSOEActivityEntity.getActivityType());
			projSOEActivityLogTO.setDescription(projSOEActivityEntity.getDescription());
			projSOEActivityLogTO.setSoeId(projSOEActivityEntity.getProjSOEItemEntity().getId());
			if (projSOEActivityEntity.getToUser() != null) {
				projSOEActivityLogTO.setToUserId(projSOEActivityEntity.getToUser().getUserId());
				projSOEActivityLogTO.setToUserDisplayName(projSOEActivityEntity.getToUser().getDisplayName());
			}
			if (projSOEActivityEntity.getFromUser() != null) {
				projSOEActivityLogTO.setFromUserId(projSOEActivityEntity.getFromUser().getUserId());
				projSOEActivityLogTO.setFromUserDisplayName(projSOEActivityEntity.getFromUser().getDisplayName());
			}
			projSOEActivityLogTO.setCreatedOn(projSOEActivityEntity.getCreatedOn());

			projSOEActivityLogResp.getProjSOEActivityLogTOs().add(projSOEActivityLogTO);
		}
		System.out.println("end start");
		System.out.println(projSOEActivityLogResp.getProjSOEActivityLogTOs().size());
		System.out.println("end stop");
		return projSOEActivityLogResp;
	}

	
	private void sendSubmitInternalMail(ProjSOEItemEntity projSOEItemEntity) {
        String shift = null;
        String apprName = null;
        String description = null;
        String text = null;
        String toEmail = null;
        String ccEmail = "";
        String epsName = null;
        String projName = null;
        String toUserName = null;
        
        projName = projSOEItemEntity.getProjMstrEntity().getProjName();
        epsName =  projSOEItemEntity.getProjMstrEntity().getCode();
        toEmail =  projSOEItemEntity.getInternalApproverUserId().getEmail();
        toUserName = projSOEItemEntity.getInternalApproverUserId().getDisplayName();
        apprName = projSOEItemEntity.getInternalApproverUserId().getUserName();
        description = "I have submitted my SOE through " + pot + ", as per details mentioned here below.";
        text = "This is for your approval please.";
        String subject = "Internal Approval of SOE " +"SOE"+"-"+epsName+"-"+projSOEItemEntity.getId()+ " dated "
                + CommonUtil.convertDateToString(projSOEItemEntity.getUpdatedOn());
        String body = "<html><body><p>" + toUserName + ",</p>" + "<p>" + description + "</p>"
                + "<table border='1'><tr><td>EPS </td><td>" + epsName + "</td></tr><tr><td>Project </td><td>"
                + projName + "</td></tr><tr><td>Notification Number</td><td>" + "SOE-"+epsName+"-"+projSOEItemEntity.getId()+"</td></tr><tr><td>Date</td><td>"
                + CommonUtil.convertDateToString(projSOEItemEntity.getUpdatedOn()) + "</td></tr></table><br>" + text + "<p>Regards,</p>" + "<p>"
                + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
        commonEmail.sendEmailNotification(toEmail, ccEmail, subject, body);
       
   }
	 

	private void sendReturnInternalMail(ProjSOEItemEntity projSOEItemEntity, ProjSOEItemGetReq projSOEItemGetReq) {
		String shift = null;
		String apprName = null;
		String description = null;
		String text = null;
		String toEmail = null;
		String ccEmail = "";
		String epsName = null;
		String projName = null;
		String toUserName = null;
		projName = projSOEItemEntity.getProjMstrEntity().getProjName();
		epsName = projSOEItemEntity.getProjMstrEntity().getCode();
		// toEmail = projSOEItemEntity.getInternalApproverUserId().getEmail();
		toEmail = projSOEItemEntity.getUpdatedBy().getEmail();
		System.out.println("toEmaillllllllllllllllllllll" + toEmail);
		toUserName = projSOEItemEntity.getInternalApproverUserId().getDisplayName();
		apprName = projSOEItemEntity.getInternalApproverUserId().getUserName();

		description = " Reference your notification ID SOE-" + epsName + "-" + projSOEItemEntity.getId() + "dated"
				+ CommonUtil.convertDateToString(projSOEItemEntity.getUpdatedOn())
				+ "<br><br> I have transmitted my decision for <b>Schedule Of Estimates - "
				+ projSOEItemGetReq.getSoeStatus() + "</b> through " + pot + ", as per details mentioned here below.";
		text = "This is for your approval please.";
		String subject = "Approver decision for Schedule Of Estimates- " + projSOEItemGetReq.getSoeStatus();
		String body = "<html><body><p>" + AppUserUtils.getName() + ",</p>" + "<p>" + description + "</p>"
				+ "<table border='1'><tr><td>EPS </td><td>" + epsName + "</td></tr><tr><td>Project </td><td>" + projName
				+ "</td></tr><tr><td>Record Status</td><td>" + projSOEItemEntity.getSoeItemStatus()
				+ "</td></tr><tr><td>Notification ID</td><td>" + "SOE-" + epsName + "-" + projSOEItemEntity.getId()
				+ "</td></tr><tr><td>Approver Decision</td><td>" + projSOEItemGetReq.getSoeStatus()
				+ "</td></tr><tr><td>Date</td><td>" + CommonUtil.convertDateToString(projSOEItemEntity.getUpdatedOn())
				+ "</td></tr></table><br>" + text + "<p>Regards,</p>" + "<p>" + toUserName + "<br/>"
				+ AppUserUtils.getDisplayRole() + "</p></body></html>";
		commonEmail.sendEmailNotification(toEmail, ccEmail, subject, body);

	}

	// This function works for approval process for SOR
	public ProjSORItemResp projSORApproval(ProjSORItemGetReq projSORItemGetReq) {
		ProjSORItemResp projSORItemResp = new ProjSORItemResp();
     	List<ProjSORItemTO> projSORItemsTo = projSORItemGetReq.getProjSORItemTOs();	
		String approvalMode = "";
		String approvalType = "";
		String status = "";
		char isExternalApprovalRequired = 'N';
		String comments = null;
		String activity_description = null;
		String activity_type = projSORItemGetReq.getSorStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")
				|| projSORItemGetReq.getSorStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL") ? "SUBMIT_FOR_APPROVAL"
						: "APPROVAL";

		if (projSORItemGetReq.getSorStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL")
				|| projSORItemGetReq.getSorStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")) {
			approvalMode = "SUBMITTED";
			approvalType = projSORItemGetReq.getSorStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL") ? "EXTERNAL"
					: "INTERNAL";
		} else {
			approvalMode = "APPROVED";
			approvalType = projSORItemGetReq.getSorStatus().equals("EXTERNAL_APPROVAL") ? "EXTERNAL" : "INTERNAL";
		}
		System.out.println("ProjSORItemGetReq data:");
		System.out.println(projSORItemGetReq);
		List<Long> sorItemsList = new ArrayList<Long>();
		Long loggedInUser = AppUserUtils.getUserId();

		if (approvalType.equals("INTERNAL")) {
			System.out.println("if condition of INTERNAL block of projSORApproval function");
			// projSOEActivityLogEntity.setProjSOEItemEntity( projSOEItemEntityCopy );
			if (projSORItemGetReq.getSorStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")) {
				status = "SUBMITTED_FOR_INTERNAL_APPROVAL";
				isExternalApprovalRequired = 'N';
				// toUserEntity.setUserId( projSOEItemGetReq.getInternalApprovalUserId() );
			} else {
				status = (projSORItemGetReq.getIsExternalApprovalRequired() == 'N') ? "FINALIZED" : "INTERNAL_APPROVED";
				isExternalApprovalRequired = projSORItemGetReq.getIsExternalApprovalRequired();
				comments = projSORItemGetReq.getComments();
			}
			System.out.println("IsExternalApprovalRequired" + projSORItemGetReq.getIsExternalApprovalRequired());
			// List<ProjSORItemEntity> projSORItemChildEntities =
			// projSORItemRepository.getSORChildItems( projSORItemGetReq.getSorIds() );
			// for( ProjSORItemEntity sorItemEntity : projSORItemGetReq.getSorIds() )
			for (Long sorItemId : projSORItemGetReq.getSorIds()) {
				// Long sorItemId = sorItemEntity.getId();
				if (projSORItemGetReq.getSorStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")) {
					projSORItemRepository.updateSORInternalApprovalDetailsById(sorItemId, status,
							projSORItemGetReq.getInternalApprovalUserId(), approvalMode, loggedInUser);
				} else {
					if (status.equals("FINALIZED")) {
						projSORItemRepository.updateSORInternalApproverDetailsById(sorItemId, status, approvalMode,
								comments, StatusCodes.ACTIVE.getValue());
					} else {
						projSORItemRepository.updateSORIntApproverDetailsById(sorItemId, status,
								Character.valueOf(isExternalApprovalRequired), approvalMode, comments);
					}
				}
				sorItemsList.add(sorItemId);
			}
		} else {
			System.out.println("else condition of INTERNAL block of projSORApproval");
			status = approvalMode.equals("SUBMITTED") ? "SUBMITTED_FOR_EXTERNAL_APPROVAL" : "FINALIZED";
			activity_description = approvalMode.equals("SUBMITTED") ? "Submitted for External Approval"
					: "Approved Externally";
			if (projSORItemGetReq.getSorStatus().equals("EXTERNAL_APPROVAL")) {
				comments = projSORItemGetReq.getComments();
			}
			// List<ProjSORItemEntity> projSORItemChildEntities =
			// projSORItemRepository.getSORChildItems( projSORItemGetReq.getSorIds() );
			// for( ProjSORItemEntity sorItemEntity : projSORItemGetReq.getSorIds() )
			for (Long sorItemId : projSORItemGetReq.getSorIds()) {
				// Long sorItemId = sorItemEntity.getId();
				ProjSORItemEntity sorItemEntity = projSORItemRepository.findOne(sorItemId);
				System.out.print("soritemid:");
				System.out.println(sorItemId);
				if (projSORItemGetReq.getSorStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL")) {
					projSORItemRepository.updateSORExternalApprovalDetailsById(sorItemId, status,
							projSORItemGetReq.getExternalApprovalUserId(), approvalMode);
				} else {
					projSORItemRepository.updateSORExternalApproverDetailsById(sorItemId, status, approvalMode,
							comments, StatusCodes.ACTIVE.getValue());
				}
				sorItemsList.add(sorItemId);
				projSORItemResp.getProjSORItemTOs().add(ProjSORItemHandler.populateSORITems(sorItemEntity, true));
			}
			// projSOEItemRepository.updateSOEExternalApprovalDetailsById(
			// projSOEItemGetReq.getSoeId(), status,
			// projSOEItemGetReq.getExternalApprovalUserId(), approvalMode, comments );
		}

		// List<ProjSORItemEntity> projSORItemEntities =
		// projSORItemRepository.findMultiProjSORItemsById(
		// projSORItemGetReq.getProjId(), projSORItemGetReq.getSorIds(), 1 );
		// System.out.println("sorItemsList size:"+sorItemsList.size());
		ProjSORActivityLogEntity projSORActivityLogEntity = new ProjSORActivityLogEntity();
		ProjSORItemEntity projSORItemEntityCopy = new ProjSORItemEntity();
		UserMstrEntity fromUserEntity = new UserMstrEntity();
		fromUserEntity.setUserId(AppUserUtils.getUserId());
		for (Long sorItem : sorItemsList) {
			
			projSORItemEntityCopy.setId(sorItem);
			UserMstrEntity toUserEntity = new UserMstrEntity();
			if (approvalType.equals("INTERNAL")) {
				System.out.println("if condition of INTERNAL block of projSOEApproval function inside the for loop");
				// projSOEActivityLogEntity.setProjSOEItemEntity( projSORItemEntityCopy );
				if (projSORItemGetReq.getSorStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL")) {
					activity_description = "Submitted for Internal Approval";
					toUserEntity.setUserId(projSORItemGetReq.getInternalApprovalUserId());
					projSORActivityLogEntity.setToUser(toUserEntity);
				} else {
					isExternalApprovalRequired = projSORItemGetReq.getIsExternalApprovalRequired();
					comments = projSORItemGetReq.getComments();
					activity_description = "Approved Internally";
				}
				// projSORItemRepository.updateSOEInternalApprovalDetailsById(
				// projSOEItemGetReq.getSorId(), status,
				// Character.valueOf(isExternalApprovalRequired),
				// projSOEItemGetReq.getInternalApprovalUserId(), approvalMode, comments );
			} else {
				activity_description = approvalMode.equals("SUBMITTED") ? "Submitted for External Approval"
						: "Approved Externally";
				if (projSORItemGetReq.getSorStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL")) {
					toUserEntity.setUserId(projSORItemGetReq.getExternalApprovalUserId());
					projSORActivityLogEntity.setToUser(toUserEntity);
				}
			}

			
			// projSORItemResp.getProjSORItemTOs().add( ProjSORItemHandler.populateSORITems(
			// projSORItemEntity, true ) );
		}
	//	boolean update;
		boolean update = false;
		System.out.println("projSORItemGetReq.getViewSORIds()3241 "+projSORItemGetReq.getSorIds());
		List<ProjSORActivityLogEntity> projSORActivityLogEntites = projSORActivityLogRepository.getActivityDetailsBySorIds(projSORItemGetReq.getSorIds());
		for(ProjSORActivityLogEntity projSORActivityLogEntitys :projSORActivityLogEntites) {
			System.out.println("projSORActivityLogEntitys.getActivityType()3245 "+projSORActivityLogEntitys.getDescription());
			System.out.println("activity_description3246 "+activity_description);
			if(projSORActivityLogEntitys.getDescription().equals(activity_description)) {
				update = true;
			}
		}
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeee "+update);
		if(!update) {
			projSORActivityLogEntity.setProjSORItemEntity(projSORItemEntityCopy);
			projSORActivityLogEntity.setDescription(activity_description);
			projSORActivityLogEntity.setFromUser(fromUserEntity);
			projSORActivityLogEntity.setActivityType(activity_type);
			projSORActivityLogRepository.save(projSORActivityLogEntity);
		}
		
		
		// projSOEItemRepository.updateSOEInternalApprovalDetailsById(
		// projSOEItemGetReq.getProjId(), projSOEItemGetReq.getStatus() );
		List<ProjSORItemEntity> projSORItemEntities = projSORItemRepository
				.findSORDetailsByProjId(projSORItemGetReq.getProjId());
		
		for(ProjSORItemEntity projSORItemEntity :projSORItemEntities) {			
			projSORItemResp.getProjSORItemTOs().add(ProjSORItemHandler.populateSORITems(
					 projSORItemEntity, true ));
		}
		projSORTrackRepository.save(
				ProjSORItemHandler.populateEntitiesFromPOJO(projSORItemsTo, projRepository, measurementRepository, projSORItemRepository, projSORItemGetReq.getSorStatus()));
		return projSORItemResp;
	}

	// This function works for Returned With Comments for SOR
	public ProjSORItemResp returnSORWithComments(ProjSORItemGetReq projSORItemGetReq) {
		ProjSORItemResp projSORItemResp = new ProjSORItemResp();
		List<ProjSORItemTO> projSORItemsTo = projSORItemGetReq.getProjSORItemTOs();
		String sorReturnType = projSORItemGetReq.getSorStatus().equals("RETURN_FROM_INTERNAL_APPROVER") ? "INTERNAL"
				: "EXTERNAL";
		ProjSORActivityLogEntity projSORActivityLogEntity = new ProjSORActivityLogEntity();
		Long sorId = projSORItemGetReq.getSorId();

		String sor_status = "";
		String activity_description ="";
		ProjSORItemEntity projSORItemEntityData = new ProjSORItemEntity();
		UserMstrEntity toUserEntity = new UserMstrEntity();
		UserMstrEntity fromUserEntity = new UserMstrEntity();
		fromUserEntity.setUserId(AppUserUtils.getUserId());

		for (Long sorItemId : projSORItemGetReq.getSorIds()) {
			projSORItemEntityData = projSORItemRepository.findOne(sorItemId);
			sor_status = projSORItemEntityData.getSorItemStatus().equals("SUBMITTED_FOR_INTERNAL_APPROVAL")
					? "RETURNED_FROM_INTERNAL_APPROVER"
					: "RETURNED_FROM_EXTERNAL_APPROVER";
			activity_description = projSORItemEntityData.getSorItemStatus()
					.equals("SUBMITTED_FOR_INTERNAL_APPROVAL") ? "Returned with comments from Internal Approver"
							: "Returned with comments from External Approver";

			if (sorItemId.compareTo(sorId) == 0) {
				projSORItemRepository.updateApprovalStatusAndReturnStatusBySorId(sorItemId, sor_status, 1);
			} else {
				projSORItemRepository.updateApprovalStatusBySorId(sorItemId, sor_status);
			}
			boolean update= false;
			System.out.println("projSORItemGetReq.getViewSORIds()3300 "+projSORItemGetReq.getSorIds());
			List<ProjSORActivityLogEntity> projSORActivityLogEntites = projSORActivityLogRepository.getActivityDetailsBySorIds(projSORItemGetReq.getSorIds());
			for(ProjSORActivityLogEntity projSORActivityLogEntitys :projSORActivityLogEntites) {		
			   if(projSORActivityLogEntitys.getDescription().equals(activity_description)) {
				   update = true;
			   }			
			}
			System.out.println("updateeeeeeeeeeeeeeeeeeeeeeee3313 "+update);
			if(!update) {
				ProjSORItemEntity projSORItemEntity = new ProjSORItemEntity();
				projSORItemEntity.setId(sorItemId);
				projSORActivityLogEntity.setProjSORItemEntity(projSORItemEntity);
				projSORActivityLogEntity.setDescription(activity_description);
				projSORActivityLogEntity.setFromUser(fromUserEntity);
				projSORActivityLogEntity.setToUser(projSORItemEntityData.getCreatedBy());
				projSORActivityLogEntity.setUserComments(projSORItemGetReq.getComments());
				projSORActivityLogEntity.setActivityType("RETURNED_WITH_COMMENTS");
				projSORActivityLogRepository.save(projSORActivityLogEntity);
			}	
			projSORItemResp.getProjSORItemTOs().add(ProjSORItemHandler.populateSORITems(projSORItemEntityData, true));
			
		}
		projSORTrackRepository.save(
				ProjSORItemHandler.populateEntitiesFromPOJO(projSORItemsTo, projRepository, measurementRepository, projSORItemRepository, activity_description));
		projSORItemRepository.updateApprovalStatusBySorId(projSORItemGetReq.getSorId(), sor_status);
		
		return projSORItemResp;
	}

	// This function deals with display of view records functionality
	public ProjSORActivityLogResp viewSORActivityLog(ProjSORItemGetReq projSORItemGetReq) {
		ProjSORActivityLogResp projSORActivityLogResp = new ProjSORActivityLogResp();
		List<ProjSORActivityLogEntity> projSORActivityLogEntities = projSORActivityLogRepository
				.getActivityDetailsBySorIds(projSORItemGetReq.getSorIds());

		for (ProjSORActivityLogEntity projSORActivityLogEntity : projSORActivityLogEntities) {
			ProjSORActivityLogTO projSORActivityLogTO = new ProjSORActivityLogTO();
			projSORActivityLogTO.setDescription(projSORActivityLogEntity.getDescription());
			projSORActivityLogTO.setActivityType(projSORActivityLogEntity.getActivityType());
			projSORActivityLogTO.setSorId(projSORActivityLogEntity.getProjSORItemEntity().getId());
			if (projSORActivityLogEntity.getToUser() != null) {
				projSORActivityLogTO.setToUserId(projSORActivityLogEntity.getToUser().getUserId());
				projSORActivityLogTO.setToUserDisplayName(projSORActivityLogEntity.getToUser().getDisplayName());
			}
			if (projSORActivityLogEntity.getFromUser() != null) {
				projSORActivityLogTO.setFromUserId(projSORActivityLogEntity.getFromUser().getUserId());
				projSORActivityLogTO.setFromUserDisplayName(projSORActivityLogEntity.getFromUser().getDisplayName());
			}
			projSORActivityLogTO.setCreatedOn(projSORActivityLogEntity.getCreatedOn());
			projSORActivityLogResp.getProjSORActivityLogTOs().add(projSORActivityLogTO);
		}
		return projSORActivityLogResp;
	}

	public ProjSOWItemResp getAllProjSOWDetails(ProjSOWItemGetReq projSOWItemGetReq) {
		ProjSOWItemResp projSOWItemResp = new ProjSOWItemResp();
		List<ProjSOWItemEntity> projSOWItemEntities = projSOWItemRepository
				.findSOWDetails(projSOWItemGetReq.getProjId());
		System.out.println("projSOWItemEntities size:" + projSOWItemEntities.size());
		Map<Long, ProjSOWItemEntity> projSOWItemMap = new HashMap<Long, ProjSOWItemEntity>();
		for (ProjSOWItemEntity projSOWItemEntity : projSOWItemEntities) {
			projSOWItemMap.put(projSOWItemEntity.getProjSOEItemEntity().getId(), projSOWItemEntity);
		}

		List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository
				.findSOEDetails(projSOWItemGetReq.getProjId());
		System.out.println("for loop from getProjSOWDetails function:");
		System.out.println("projSOWItemEntities size:" + projSOEItemEntities.size());
		for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
			ProjSOWItemTO projSOWItemTOres = ProjSOWItemHandler.populateAllSOWITems(projSOEItemEntity, projSOWItemMap,
					scheduleActivityDataRepository);
			System.out.println("projSOItemTOres size:" + projSOWItemTOres.getChildSOWItemTOs().size());
			System.out.println(projSOWItemTOres);
			if (projSOWItemTOres.getChildSOWItemTOs().size() > 0) {
				projSOWItemResp.getProjSOWItemTOs().add(projSOWItemTOres);
			}
		}
		System.out.println("end loop begin");
		System.out.println(projSOWItemResp.getProjSOWItemTOs().size());
		System.out.println("end loop end");
		ProjSowItemsMapResp projSowItemsMapResp = new ProjSowItemsMapResp();

		List<ProjSOWItemEntity> projSOWItemEntites = projSOWItemRepository.findSOWItems(projSOWItemGetReq.getProjId());
		LabelKeyTO labelKeyTO = null;

		if (CommonUtil.isListHasData(projSOWItemEntites)) {
			System.out.println("if condition of projSOWItemEntites");
			System.out.println(projSOWItemEntites.size());
			for (ProjSOWItemEntity projSOWItemEntity : projSOWItemEntites) {
				System.out.println(projSOWItemEntity);
				labelKeyTO = new LabelKeyTO();
				labelKeyTO.setId(projSOWItemEntity.getId());
				labelKeyTO.setCode(projSOWItemEntity.getCode());
				labelKeyTO.setName(projSOWItemEntity.getName());

				if (CommonUtil.objectNotNull(projSOWItemEntity.getProjCostItemEntity())) {
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_CODE,
							projSOWItemEntity.getProjCostItemEntity().getCode());
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_NAME,
							projSOWItemEntity.getProjCostItemEntity().getName());
				}

				if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSORItemEntity())) {
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOR_CODE,
							projSOWItemEntity.getProjSORItemEntity().getCode());
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOR_NAME,
							projSOWItemEntity.getProjSORItemEntity().getName());
				}

				if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity())) {
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOE_CODE,
							projSOWItemEntity.getProjSOEItemEntity().getCode());
					labelKeyTO.getDisplayNamesMap().put(CommonConstants.SOE_NAME,
							projSOWItemEntity.getProjSOEItemEntity().getName());
					labelKeyTO.setUnitOfMeasure(
							projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity().getName());
				}

				projSowItemsMapResp.getSowItemMap().put(projSOWItemEntity.getId(), labelKeyTO);
			}
			System.out.println("out of loop size:" + projSowItemsMapResp.getSowItemMap().size());
			System.out.println("==========");
		}
		return projSOWItemResp;
	}
}
