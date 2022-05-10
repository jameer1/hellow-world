package com.rjtech.timemanagement.workdairy.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.Instant;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.EmpWageRepository;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.centrallib.repository.WeatherRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.service.impl.CommonEmailServiceImpl;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.TimeManagentStatus;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.material.ledger.MaterialLedgerResTo;
import com.rjtech.notification.dto.WorkDairyNotificationsTO;
import com.rjtech.notification.model.WorkDairyAdditionalTimeEntity;
import com.rjtech.notification.model.WorkDairyNotificationsEntity;
//import com.rjtech.notification.model.WorkDairyNotificationsEntityCopy;
import com.rjtech.notification.repository.WorkDairyAdditionalTimeRepositoryCopy;
import com.rjtech.notification.repository.WorkDairyNotificationRepositoryCopy;
import com.rjtech.notification.service.handler.WorkDairyNotificationsHandlerCopy;
//import com.rjtech.procurement.model.MaterialProjDtlEntityCopy;
import com.rjtech.procurement.model.PreContractEntity;
//import com.rjtech.procurement.model.PreContractEntityCopy;
import com.rjtech.procurement.model.PreContractsMaterialCmpEntity;
//import com.rjtech.procurement.model.PreContractsMaterialCmpEntityCopy;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;
//import com.rjtech.procurement.model.PreContractsMaterialDtlEntityCopy;
import com.rjtech.procurement.model.PurchaseOrderEntity;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;
import com.rjtech.procurement.repository.PrecontractMaterialCmpRepositoryCopy;
import com.rjtech.procurement.repository.PurchaseOrderRepositoryCopy;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.projectlib.model.ProjSOEItemEntity;
//import com.rjtech.projectlib.model.ProjSOEItemEntityCopy;
import com.rjtech.projectlib.model.ProjSORItemEntity;
//import com.rjtech.projectlib.model.ProjSORItemEntityCopy;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.projectlib.model.ProjWorkShiftMstrEntity;
//import com.rjtech.projectlib.model.ProjWorkShiftMstrEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjCrewRepositoryCopy;
import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
//import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.projectlib.repository.ProjWorkShiftRepositoryCopy;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.projschedule.model.ResourceAssignmentDataEntity;
import com.rjtech.projschedule.repository.ResourceAssignmentDataRepositoryCopy;
import com.rjtech.projsettings.model.WorkDairyNormalTimeEntity;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.material.dto.MaterialPODeliveryDocketTO;
import com.rjtech.register.material.dto.MaterialProjDtlTO;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
//import com.rjtech.register.material.model.MaterialPODeliveryDocketEntityCopy;
import com.rjtech.register.material.model.MaterialProjDocketEntity;
//import com.rjtech.register.material.model.MaterialProjDocketEntityCopy;
import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntity;
//import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntityCopy;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
//import com.rjtech.register.material.model.MaterialProjDtlEntityCopy;
//import com.rjtech.register.material.model.MaterialProjDtlEntityCopy;
import com.rjtech.register.material.model.MaterialProjLedgerEntity;
//import com.rjtech.register.material.model.MaterialProjLedgerEntityCopy;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.plant.model.PlantChargeOutRatesEntityCopy;
import com.rjtech.register.plant.model.PlantRegProjEntity;
//import com.rjtech.register.plant.model.PlantRegProjEntityCopy;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
//import com.rjtech.register.plant.model.PlantRegisterDtlEntityCopy;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.repository.emp.EmpRegisterRepositoryCopy;
import com.rjtech.register.repository.material.MaterialDeliveryDocketRepositoryCopy;
import com.rjtech.register.repository.material.MaterialDockSchItemRepositoryCopy;
import com.rjtech.register.repository.material.MaterialProjRepositoryCopy;
import com.rjtech.register.repository.material.PrecontractMaterialRepositoryCopy;
import com.rjtech.register.repository.plant.PlantChargeOutRateRepositoryCopy;
import com.rjtech.register.repository.plant.PlantRegProjRepositoryCopy;
import com.rjtech.register.repository.plant.PlantRegisterRepositoryCopy;
import com.rjtech.register.service.handler.material.MaterialProjDtlHandlerCopy;
import com.rjtech.register.utils.RegisterCommonUtils;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceEntity;
import com.rjtech.timemanagement.attendance.model.PlantAttendanceEntity;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceRepository;
import com.rjtech.timemanagement.attendence.repository.PlantAttendanceRepository;
import com.rjtech.timemanagement.manpower.reports.dto.ReportHoursTO;
import com.rjtech.timemanagement.plants.reports.dto.PlantActualHrsTO;
import com.rjtech.timemanagement.progress.reports.dto.ProgressReportTO;
import com.rjtech.timemanagement.progress.reports.dto.ReportValueTO;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpWorkDtlEntity;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetEmpDtlRepository;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyApprStatusReportTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyCostCodeTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyEmpCostDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyEmpDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyEmpWageTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyMaterialDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyMaterialStatusTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyPlantCostDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyPlantDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyPlantStatusTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyProgressDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;
import com.rjtech.timemanagement.workdairy.model.WorkDairyCostCodeEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyCrewCostCodeEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpDtlEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpWageEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialDtlEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialStatusEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantDtlEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantStatusEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyProgressDtlEntity;
import com.rjtech.timemanagement.workdairy.repository.EmpCostWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.EmpRegisterDtlRepository;
import com.rjtech.timemanagement.workdairy.repository.EmpWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.MaterialCostWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.MaterialWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.PlantCostWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.PlantStatusWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.PlantWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.ProgressWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.ProjGeneralMstrRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.ProjSettingsWorkDairyProcRepository;
import com.rjtech.timemanagement.workdairy.repository.ProjWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.WorkDairyCostCodeRepository;
import com.rjtech.timemanagement.workdairy.repository.WorkDairyCrewCostCodeRepository;
import com.rjtech.timemanagement.workdairy.repository.WorkDairyProcRepository;
import com.rjtech.timemanagement.workdairy.repository.WorkDairyRepository;
import com.rjtech.timemanagement.workdairy.req.WorkDairyApprovalGetReq;
import com.rjtech.timemanagement.workdairy.req.WorkDairyCostCodeSaveReq;
import com.rjtech.timemanagement.workdairy.req.WorkDairyDtlSaveReq;
import com.rjtech.timemanagement.workdairy.req.WorkDairyEmpSaveReq;
import com.rjtech.timemanagement.workdairy.req.WorkDairyGetReq;
import com.rjtech.timemanagement.workdairy.req.WorkDairyMaterialSaveReq;
import com.rjtech.timemanagement.workdairy.req.WorkDairyPlantSaveReq;
import com.rjtech.timemanagement.workdairy.req.WorkDairyPlantsGetReq;
import com.rjtech.timemanagement.workdairy.req.WorkDairyProgressSaveReq;
import com.rjtech.timemanagement.workdairy.req.WorkDairySaveReq;
import com.rjtech.timemanagement.workdairy.resp.WorkDairyCopyResp;
import com.rjtech.timemanagement.workdairy.resp.WorkDairyCostCodeResp;
import com.rjtech.timemanagement.workdairy.resp.WorkDairyDetailResp;
import com.rjtech.timemanagement.workdairy.resp.WorkDairyEmpResp;
import com.rjtech.timemanagement.workdairy.resp.WorkDairyMaterialResp;
import com.rjtech.timemanagement.workdairy.resp.WorkDairyPlantResp;
import com.rjtech.timemanagement.workdairy.resp.WorkDairyProgressResp;
import com.rjtech.timemanagement.workdairy.resp.WorkDairyProjSettingResp;
import com.rjtech.timemanagement.workdairy.resp.WorkDairyRegResp;
import com.rjtech.timemanagement.workdairy.resp.WorkDairyResp;
import com.rjtech.timemanagement.workdairy.resp.WorkDiaryResp;
import com.rjtech.timemanagement.workdairy.service.WorkDairyService;
import com.rjtech.timemanagement.workdairy.service.handler.EmpWorkDairyHandler;
import com.rjtech.timemanagement.workdairy.service.handler.MaterialWorkDairyHandler;
import com.rjtech.timemanagement.workdairy.service.handler.PlantWorkDairyHandler;
import com.rjtech.timemanagement.workdairy.service.handler.ProgressWorkDairyHandler;
import com.rjtech.timemanagement.workdairy.service.handler.WorkDairyCostCodeHandler;
import com.rjtech.timemanagement.workdairy.service.handler.WorkDairyHandler;
import com.rjtech.user.repository.CommonUserProjectsRepository;
@Service(value = "workDairyService")
@RJSService(modulecode = "workDairyService")
@Transactional
public class WorkDairyServiceImpl implements WorkDairyService {

    private static final Logger log = LoggerFactory.getLogger(WorkDairyServiceImpl.class);
    
    private static String pot = "\"Project on Track\"";

    @Autowired
    private EPSProjService epsProjService;

    @Autowired
    private CommonEmailServiceImpl commonEmail;

    @Autowired
    private ProjCrewRepositoryCopy projCrewListRepository;

    @Autowired
    private WorkDairyNotificationRepositoryCopy workDairyNotificationRepository;

    @Autowired
    private WorkDairyRepository workDairyRepository;

    @Autowired
    private WorkDairyProcRepository workDairyProcRepository;

    @Autowired
    private WorkDairyCostCodeRepository workDairyCostCodeRepository;

    @Autowired
    private EmpCostWorkDairyRepository empCostWorkDairyRepository;

    @Autowired
    private PlantCostWorkDairyRepository plantCostWorkDairyRepository;

    @Autowired
    private MaterialCostWorkDairyRepository materialCostWorkDairyRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProjStoreStockRepositoryCopy projStoreStockRepository;

    @Autowired
    private EmpRegisterRepositoryCopy empRegisterRepository;

    @Autowired
    private ProjWorkShiftRepositoryCopy projWorkShiftRepository;

    @Autowired
    private ProjGeneralMstrRepositoryCopy projGeneralRepositoryCopy;

    @Autowired
    private EmpRegisterDtlRepository empRegisterDtlRepository;

    @Autowired
    private ProjWorkDairyRepositoryCopy projWorkDairyRepository;

    @Autowired
    private PlantAttendanceRepository plantAttendenceRepository;

    @Autowired
    private PlantStatusWorkDairyRepository plantStatusWorkDairyRepository;

    @Autowired
    private WorkDairyCrewCostCodeRepository workDairyCrewCostCodeRepository;

    @Autowired
    private EmpWorkDairyRepository empWorkDairyRepository;

    @Autowired
    private PlantWorkDairyRepository plantWorkDairyRepository;

    @Autowired
    private MaterialWorkDairyRepository materialWorkDairyRepository;

    @Autowired
    private MaterialProjRepositoryCopy materialProjRepository;

    @Autowired
    private MaterialDockSchItemRepositoryCopy materialDockSchItemRepository;

    @Autowired
    private ProgressWorkDairyRepository progressWorkDairyRepository;

    @Autowired
    private ProjSettingsWorkDairyProcRepository projSettingsWorkDairyProcRepository;

    @Autowired
    private ProjCostItemRepositoryCopy projCostItemRepository;

    @Autowired
    private PrecontractMaterialCmpRepositoryCopy preContractMaterialCmpRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private PurchaseOrderRepositoryCopy purchaseOrderRepositoryCopy;

    @Autowired
    private PrecontractMaterialRepositoryCopy precontractMaterialRepositoryCopy;

    @Autowired
    private MaterialClassRepository materialClassRepository;

    @Autowired
    private EmpWageRepository empWageRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private PurchaseOrderRepositoryCopy purchaseOrderRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PlantRegisterRepositoryCopy plantRegisterRepository;

    @Autowired
    private ProjSOWItemRepositoryCopy projSOWItemRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ClientRegRepository clientRegRepository;

    @Autowired
    private PlantRegProjRepositoryCopy plantRegProjRepositoryCopy;

    @Autowired
    private EmpAttendanceRepository empAttendenceRepository;

    @Autowired
    private TimeSheetEmpDtlRepository timeSheetEmpDtlRepository;

    @Autowired
    private PlantChargeOutRateRepositoryCopy plantChargeOutRateRepository;

    @Autowired
    private ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy;

    @Autowired
    private MaterialDeliveryDocketRepositoryCopy materialDeliveryDocketRepositoryCopy;

    @Autowired
    private PrecontractMaterialRepositoryCopy preContractMaterialRepository;

    @Autowired
    private WorkDairyAdditionalTimeRepositoryCopy workDairyAdditionalTimeRepository;
    
    @Autowired
    ProjSOWItemRepositoryCopy projSOWItemRepositoryCopy;
    
    @Autowired
    private ResourceAssignmentDataRepositoryCopy resourceAssignmentDataRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private CommonUserProjectsRepository userProjectsRepository;

    public WorkDairyResp getWorkDairyForClientApproval(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyEntity> workDairyEntities = null;
        workDairyEntities = workDairyRepository.findWorkDairyForClientApproval(workDairyGetReq.getProjId(),
                CommonUtil.convertStringToDate(workDairyGetReq.getWorkDairyDate()), workDairyGetReq.getCrewId(),
                workDairyGetReq.getStatus());
        WorkDairyResp workDairyResp = new WorkDairyResp();

        for (WorkDairyEntity workDairyEntity : workDairyEntities) {
            workDairyResp.setWorkDairyTO(WorkDairyHandler.convertEntityToPOJO(workDairyEntity));
        }
        if (CommonUtil.isListHasData(workDairyEntities)) {
            workDairyGetReq.setWorkDairyId(workDairyEntities.get(0).getId());
            workDairyResp.setWorkDairyCostCodeTOs(getWorkDairyCostCodes(workDairyGetReq).getWorkDairyCostCodeTOs());
        } else {
            workDairyResp.setWorkDairyCostCodeTOs(
                    getWorkDairyCrewCostCodes(workDairyGetReq, false).getWorkDairyCostCodeTOs());

        }
        workDairyResp.setEmpRegMap(getEmpRegMap(workDairyGetReq));
        workDairyResp.setPlantRegMap(getPlantRegMap(workDairyGetReq));
        return workDairyResp;
    }

    public WorkDairyResp getWorkDairyForInternalApproval(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyEntity> workDairyEntities = null;
        workDairyEntities = workDairyRepository.findWorkDairyForInternalApproval(workDairyGetReq.getProjId(),
                CommonUtil.convertStringToDate(workDairyGetReq.getWorkDairyDate()), workDairyGetReq.getCrewId(),
                workDairyGetReq.getStatus());
        WorkDairyResp workDairyResp = new WorkDairyResp();

        for (WorkDairyEntity workDairyEntity : workDairyEntities) {
            workDairyResp.setWorkDairyTO(WorkDairyHandler.convertEntityToPOJO(workDairyEntity));
        }
        if (CommonUtil.isListHasData(workDairyEntities)) {
            workDairyGetReq.setWorkDairyId(workDairyEntities.get(0).getId());
            workDairyResp.setWorkDairyCostCodeTOs(getWorkDairyCostCodes(workDairyGetReq).getWorkDairyCostCodeTOs());
        } else {
            workDairyResp.setWorkDairyCostCodeTOs(
                    getWorkDairyCrewCostCodes(workDairyGetReq, false).getWorkDairyCostCodeTOs());

        }
        workDairyResp.setEmpRegMap(getEmpRegMap(workDairyGetReq));
        workDairyResp.setPlantRegMap(getPlantRegMap(workDairyGetReq));
        return workDairyResp;
    }

    public WorkDairyProjSettingResp getProjSettingsForWorkDairy(WorkDairyGetReq workDairyGetReq) {
        List<Object[]> projSettings = projGeneralRepositoryCopy.getProjSettingsForWorkdairy(
                epsProjRepository.findOne(workDairyGetReq.getProjId()),
                clientRegRepository.findOne(AppUserUtils.getClientId()));
        WorkDairyProjSettingResp workDairyProjSettingResp = new WorkDairyProjSettingResp();

        projSettings.forEach((setting) -> {
            LabelKeyTO labelKeyTo = new LabelKeyTO();
            labelKeyTo.setId(Long.valueOf(((Integer) setting[0]).toString()));
            labelKeyTo.setCode((String) setting[1]);
            workDairyProjSettingResp.setLabelKeyTO(labelKeyTo);
        });
        return workDairyProjSettingResp;
    }

    public WorkDairyResp getWorkDairy(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyEntity> workDairyEntities = new ArrayList<WorkDairyEntity>();
        WorkDairyResp workDairyResp = new WorkDairyResp();
        workDairyEntities = workDairyRepository.findWorkDairy(workDairyGetReq.getProjId(),
                CommonUtil.convertStringToDate(workDairyGetReq.getWorkDairyDate()), workDairyGetReq.getCrewId(),
                workDairyGetReq.getStatus());
        for (WorkDairyEntity workDairyEntity : workDairyEntities) {
            workDairyResp.setWorkDairyTO(WorkDairyHandler.convertEntityToPOJO(workDairyEntity));
        }
        if (CommonUtil.isListHasData(workDairyEntities)) {
            workDairyGetReq.setWorkDairyId(workDairyEntities.get(0).getId());
            workDairyResp.setWorkDairyCostCodeTOs(getWorkDairyCostCodes(workDairyGetReq).getWorkDairyCostCodeTOs());
        } else {
            List<WorkDairyCostCodeEntity> workDairyCostCodeEntities = workDairyCostCodeRepository
                    .findWorkDairyCostCodesByCrewAndProj(epsProjRepository.findOne(workDairyGetReq.getProjId()),
                            projCrewListRepository.findOne(workDairyGetReq.getCrewId()));
            for (WorkDairyCostCodeEntity workDairyCostCodeEntity : workDairyCostCodeEntities) {
                workDairyResp.getWorkDairyCostCodeTOs()
                        .add(WorkDairyCostCodeHandler.convertMstrEntityToPOJO(workDairyCostCodeEntity));
            }
        }
        workDairyResp.setEmpRegMap(getEmpRegMap(workDairyGetReq));
        workDairyResp.setPlantRegMap(getPlantRegMap(workDairyGetReq));
        return workDairyResp;
    }

    public WorkDairyResp createWorkDairy(WorkDairySaveReq workDairySaveReq) {
        WorkDairyTO workDairyTO = workDairySaveReq.getWorkDairyTO();
        WorkDairyEntity workDairyEntity = WorkDairyHandler.convertPOJOToEntity(workDairyTO, epsProjRepository,
                projCrewListRepository, projWorkShiftRepository, weatherRepository, purchaseOrderRepository,
                loginRepository);
        workDairyEntity = workDairyRepository.save(workDairyEntity);
        WorkDairyResp workDairyResp = new WorkDairyResp();
        workDairyResp.setWorkDairyTO(WorkDairyHandler.convertEntityToPOJO(workDairyEntity));
        List<WorkDairyCostCodeEntity> workDairyCostCodeEntities = new ArrayList<WorkDairyCostCodeEntity>();
        for (WorkDairyCostCodeTO workDairyCostCodeTO : workDairySaveReq.getWorkDairyTO().getWorkDairyCostCodeTOs()) {
            WorkDairyCostCodeEntity workDairyCostCodeEntity = WorkDairyCostCodeHandler.convertMstrPOJOToEntity(
                    workDairyCostCodeTO, epsProjRepository, projCostItemRepository, projCrewListRepository);
            workDairyCostCodeEntity.setWorkDairyEntity(workDairyEntity);
            workDairyCostCodeEntities.add(workDairyCostCodeEntity);
        }
        workDairyCostCodeRepository.save(workDairyCostCodeEntities);
        return workDairyResp;
    }

    public void saveWorkDairyCostCodes(WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq) {
        saveCostCodes(workDairyCostCodeSaveReq.getWorkDairyCostCodeTOs());
        if (CommonUtil.isListHasData(workDairyCostCodeSaveReq.getDeleteWorkDairyCostIds())) {
            workDairyCostCodeRepository.deleteWorkdairyCostCodes(workDairyCostCodeSaveReq.getWorkDairyId(),
                    workDairyCostCodeSaveReq.getDeleteWorkDairyCostIds());
        }
        updateWorkDairyDetailCostCodes(workDairyCostCodeSaveReq);
    }

    private void saveCostCodes(List<WorkDairyCostCodeTO> workDairyCostCodeTOs) {
        List<WorkDairyCostCodeEntity> workDairyCostCodeEntities = new ArrayList<WorkDairyCostCodeEntity>();
        List<Long> addedCostCodeIds = new ArrayList<Long>();
        for (WorkDairyCostCodeTO workDairyCostCodeTO : workDairyCostCodeTOs) {
            if ((workDairyCostCodeTO.getId() == null
                    || workDairyCostCodeRepository.findOne(workDairyCostCodeTO.getId()) != null)
                    && workDairyCostCodeRepository.findWorkDairyCode(workDairyCostCodeTO.getWorkDairyId(),
                            workDairyCostCodeTO.getCostId()) == null
                    && !addedCostCodeIds.contains(workDairyCostCodeTO.getCostId())) {
                addedCostCodeIds.add(workDairyCostCodeTO.getCostId());
                workDairyCostCodeEntities.add(WorkDairyCostCodeHandler.convertMstrPOJOToEntity(workDairyCostCodeTO,
                        epsProjRepository, projCostItemRepository, projCrewListRepository));
            }
        }
        workDairyCostCodeRepository.save(workDairyCostCodeEntities);
    }

    public void saveWorkDairyCrewCostCodes(WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq) {
        List<WorkDairyCrewCostCodeEntity> workDairyCostCodeEntities = new ArrayList<WorkDairyCrewCostCodeEntity>();
        for (WorkDairyCostCodeTO workDairyCostCodeTO : workDairyCostCodeSaveReq.getWorkDairyCostCodeTOs()) {
            workDairyCostCodeEntities.add(WorkDairyCostCodeHandler.convertPOJOToEntity(workDairyCostCodeTO,
                    projCrewListRepository, epsProjRepository, workDairyCostCodeRepository));
        }
        workDairyCrewCostCodeRepository.save(workDairyCostCodeEntities);
        if (CommonUtil.isListHasData(workDairyCostCodeSaveReq.getDeleteWorkDairyCostIds())) {
            workDairyCrewCostCodeRepository.deleteByIdIn(workDairyCostCodeSaveReq.getDeleteWorkDairyCostIds());
        }

    }

    public void updateWorkDairyDetailCostCodes(WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq) {
        List<WorkDairyCostCodeTO> workDairyCostCodeTOs = new ArrayList<WorkDairyCostCodeTO>();
        Map<Long, Boolean> costCodesMap = new HashMap<Long, Boolean>();
        for (WorkDairyCostCodeTO workDairyCostCodeTO : workDairyCostCodeSaveReq.getWorkDairyCostCodeTOs()) {
            if (CommonUtil.isBlankLong(workDairyCostCodeTO.getId())) {
                workDairyCostCodeTOs.add(workDairyCostCodeTO);
            }
        }
        for (Long costId : workDairyCostCodeSaveReq.getDeleteCostIds()) {
            costCodesMap.put(costId, true);
        }
        List<WorkDairyEmpDtlEntity> workDairyEmpDtlEntities = empWorkDairyRepository
                .findWorkDairyEmpDetails(workDairyCostCodeSaveReq.getWorkDairyId(), StatusCodes.ACTIVE.getValue());
        WorkDairyEmpCostEntity workDairyEmpCostEntity = null;
        double usedTotal = 0;
        double idleTotal = 0;
        for (WorkDairyEmpDtlEntity workDairyEmpDtlEntity : workDairyEmpDtlEntities) {
            for (WorkDairyEmpWageEntity workDairyEmpWageEntity : workDairyEmpDtlEntity.getWorkDairyEmpWageEntities()) {
                usedTotal = 0;
                idleTotal = 0;
                for (WorkDairyEmpCostEntity empCostEntity : workDairyEmpWageEntity.getWorkDairyEmpCostEntities()) {
                    if (costCodesMap.get(empCostEntity.getCostId().getId()) == null) {
                        usedTotal = usedTotal + empCostEntity.getUsedTime();
                        idleTotal = idleTotal + empCostEntity.getIdleTime();
                    }
                }
                workDairyEmpWageEntity.setIdleTotal(usedTotal);
                workDairyEmpWageEntity.setUsedTotal(idleTotal);
                for (WorkDairyCostCodeTO workDairyCostCodeTO : workDairyCostCodeTOs) {
                    workDairyEmpCostEntity = new WorkDairyEmpCostEntity();
                    workDairyEmpCostEntity.setCostId(projCostItemRepository.findOne(workDairyCostCodeTO.getCostId()));
                    workDairyEmpCostEntity
                            .setWorkDairyId(workDairyRepository.findOne(workDairyCostCodeTO.getWorkDairyId()));
                    workDairyEmpCostEntity.setStatus(workDairyCostCodeTO.getStatus());
                    workDairyEmpCostEntity.setWorkDairyEmpWageEntity(workDairyEmpWageEntity);
                    workDairyEmpWageEntity.getWorkDairyEmpCostEntities().add(workDairyEmpCostEntity);
                }
            }
        }

        List<WorkDairyPlantDtlEntity> workDairyPlantDtlEntities = plantWorkDairyRepository
                .findWorkDairyPlantDetails(workDairyCostCodeSaveReq.getWorkDairyId(), StatusCodes.ACTIVE.getValue());
        WorkDairyPlantCostEntity workDairyPlantCostEntity = null;
        for (WorkDairyPlantDtlEntity workDairyPlantDtlEntity : workDairyPlantDtlEntities) {
            for (WorkDairyPlantStatusEntity workDairyPlantStatusEntity : workDairyPlantDtlEntity
                    .getWorkDairyPlantStatusEntities()) {
                usedTotal = 0;
                idleTotal = 0;
                for (WorkDairyPlantCostEntity plantCostEntity : workDairyPlantStatusEntity
                        .getWorkDairyPlantCostEntities()) {
                    if (costCodesMap.get(plantCostEntity.getCostId().getId()) == null) {
                        usedTotal = usedTotal + plantCostEntity.getUsedTime();
                        idleTotal = idleTotal + plantCostEntity.getIdleTime();
                    }
                }
                workDairyPlantStatusEntity.setIdleTotal(usedTotal);
                workDairyPlantStatusEntity.setUsedTotal(idleTotal);
                for (WorkDairyCostCodeTO workDairyCostCodeTO : workDairyCostCodeTOs) {
                    workDairyPlantCostEntity = new WorkDairyPlantCostEntity();
                    workDairyPlantCostEntity.setCostId(projCostItemRepository.findOne(workDairyCostCodeTO.getCostId()));
                    workDairyPlantCostEntity
                            .setWorkDairyId(workDairyRepository.findOne(workDairyCostCodeTO.getWorkDairyId()));
                    workDairyPlantCostEntity.setStatus(workDairyCostCodeTO.getStatus());
                    workDairyPlantCostEntity.setWorkDairyPlantStatusEntity(workDairyPlantStatusEntity);
                    workDairyPlantStatusEntity.getWorkDairyPlantCostEntities().add(workDairyPlantCostEntity);
                }
            }
        }
        List<WorkDairyMaterialDtlEntity> workDairyMaterialDtlEntities = materialWorkDairyRepository
                .findWorkDairyMaterialDetails(workDairyCostCodeSaveReq.getWorkDairyId(), StatusCodes.ACTIVE.getValue());
        WorkDairyMaterialCostEntity workDairyMaterialCostEntity = null;
        for (WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity : workDairyMaterialDtlEntities) {
            for (WorkDairyMaterialStatusEntity workDairyMaterialStatusEntity : workDairyMaterialDtlEntity
                    .getWorkDairyMaterialStatusEntities()) {
                usedTotal = 0;
                for (WorkDairyMaterialCostEntity materialCostEntity : workDairyMaterialStatusEntity
                        .getWorkDairyMaterialCostEntities()) {
                    if (costCodesMap.get(materialCostEntity.getCostId().getId()) == null) {
                        usedTotal = usedTotal + materialCostEntity.getQuantity();
                    }
                }
                workDairyMaterialStatusEntity.setTotal(usedTotal);
                for (WorkDairyCostCodeTO workDairyCostCodeTO : workDairyCostCodeTOs) {
                    workDairyMaterialCostEntity = new WorkDairyMaterialCostEntity();
                    workDairyMaterialCostEntity
                            .setCostId(projCostItemRepository.findOne(workDairyCostCodeTO.getCostId()));
                    workDairyMaterialCostEntity
                            .setWorkDairyId(workDairyRepository.findOne(workDairyCostCodeTO.getWorkDairyId()));
                    workDairyMaterialCostEntity.setStatus(workDairyCostCodeTO.getStatus());
                    workDairyMaterialCostEntity.setWorkDairyMaterialStatusEntity(workDairyMaterialStatusEntity);
                    workDairyMaterialStatusEntity.getWorkDairyMaterialCostEntities().add(workDairyMaterialCostEntity);
                }
            }
        }

        empWorkDairyRepository.save(workDairyEmpDtlEntities);
        plantWorkDairyRepository.save(workDairyPlantDtlEntities);
        materialWorkDairyRepository.save(workDairyMaterialDtlEntities);

        if (CommonUtil.isListHasData(workDairyCostCodeSaveReq.getDeleteCostIds())) {
            empCostWorkDairyRepository.deleteEmpCostCodes(workDairyCostCodeSaveReq.getWorkDairyId(),
                    workDairyCostCodeSaveReq.getDeleteCostIds());
            plantCostWorkDairyRepository.deletePlantCostCodes(workDairyCostCodeSaveReq.getWorkDairyId(),
                    workDairyCostCodeSaveReq.getDeleteCostIds());
            materialCostWorkDairyRepository.deleteMaterialCostCodes(workDairyCostCodeSaveReq.getWorkDairyId(),
                    workDairyCostCodeSaveReq.getDeleteCostIds());
            progressWorkDairyRepository.deleteProgressCostCodes(workDairyCostCodeSaveReq.getWorkDairyId(),
                    workDairyCostCodeSaveReq.getDeleteCostIds());
        }

    }

    public WorkDairyCostCodeResp getWorkDairyCrewCostCodes(WorkDairyGetReq workDairyGetReq,
            boolean includeProjDefaultWorkDairyEntries) {
        WorkDairyCostCodeResp workDairyCostCodeResp = new WorkDairyCostCodeResp();
        // include default work dairy cost codes while selecting crew.
        if (includeProjDefaultWorkDairyEntries) {
            // Fetch recently used work dairies of crew
            List<WorkDairyCrewCostCodeEntity> workDairyCostCodeEntities = workDairyCrewCostCodeRepository
                    .findRecentWorkDairyCostCodes(workDairyGetReq.getProjId(), workDairyGetReq.getCrewId(),
                            workDairyGetReq.getStatus());
            boolean todayCostCodes = false;
            if (CommonUtil.isListHasData(workDairyCostCodeEntities)) {
                // Verify recently used cost codes belongs to today
                if (CommonUtil.removeTimeFromDate(workDairyCostCodeEntities.get(0).getCreatedOn())
                        .equals(CommonUtil.removeTimeFromDate(CommonUtil.getNow()))) {
                    todayCostCodes = true;
                }
            }
            // If recently used cost codes not belongs to today, save them in DB
            // To identify cost code is not belongs to today we are making Id as null value
            for (WorkDairyCrewCostCodeEntity workDairyCostCodeEntity : workDairyCostCodeEntities) {
                WorkDairyCostCodeTO costCode = WorkDairyCostCodeHandler.convertEntityToPOJO(workDairyCostCodeEntity);
                if (!todayCostCodes) {
                    costCode.setId(null);
                }
                workDairyCostCodeResp.getWorkDairyCostCodeTOs().add(costCode);
            }
            List<ProjCostItemEntity> defaultCostCodes = projCostItemRepository
                    .findCostDetails(workDairyGetReq.getProjId(), workDairyGetReq.getStatus());
            // Verify project default cost code is already included in cost codes, if not
            // add default cost code and save it.
            verifyAndAddDefaultCostCodes(defaultCostCodes, workDairyCostCodeResp, workDairyGetReq.getCrewId());
            // Iterate and save cost codes which are not belongs to today and which are
            // default cost codes
            for (WorkDairyCostCodeTO workDairyCostCodeTo : workDairyCostCodeResp.getWorkDairyCostCodeTOs()) {
                // if id is null then only we will save it in DB.
                if (workDairyCostCodeTo.getId() == null) {
                    WorkDairyCrewCostCodeEntity savedCostCode = workDairyCrewCostCodeRepository
                            .save(WorkDairyCostCodeHandler.convertPOJOToEntity(workDairyCostCodeTo,
                                    projCrewListRepository, epsProjRepository, workDairyCostCodeRepository));
                    workDairyCostCodeTo.setId(savedCostCode.getId());
                }
            }
        } else {
            List<WorkDairyCrewCostCodeEntity> workDairyCostCodeEntities = workDairyCrewCostCodeRepository
                    .findWorkDairyCostCodes(workDairyGetReq.getProjId(), workDairyGetReq.getCrewId(),
                            workDairyGetReq.getStatus());
            for (WorkDairyCrewCostCodeEntity workDairyCostCodeEntity : workDairyCostCodeEntities) {
                workDairyCostCodeResp.getWorkDairyCostCodeTOs()
                        .add(WorkDairyCostCodeHandler.convertEntityToPOJO(workDairyCostCodeEntity));
            }
        }
        return workDairyCostCodeResp;
    }

    public WorkDairyCostCodeResp getTodayWorkDairyCrewCostCodes(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyCrewCostCodeEntity> workDairyCostCodeEntities = workDairyCrewCostCodeRepository
                .findWorkDairyCostCodesByDate(workDairyGetReq.getProjId(), workDairyGetReq.getCrewId(),
                        workDairyGetReq.getStatus(), new Date());
        WorkDairyCostCodeResp workDairyCostCodeResp = new WorkDairyCostCodeResp();
        for (WorkDairyCrewCostCodeEntity workDairyCostCodeEntity : workDairyCostCodeEntities) {
            workDairyCostCodeResp.getWorkDairyCostCodeTOs()
                    .add(WorkDairyCostCodeHandler.convertEntityToPOJO(workDairyCostCodeEntity));
        }
        return workDairyCostCodeResp;
    }

    public Map<Long, LabelKeyTO> getEmpRegMap(WorkDairyGetReq workDairyGetReq) {
        Map<Long, LabelKeyTO> empRegmap = new HashMap<Long, LabelKeyTO>();
        List<Object[]> workDairyEmpDtls = empRegisterDtlRepository.findWorkDairyEmpReg(
                epsProjRepository.findOne(workDairyGetReq.getProjId()),
                clientRegRepository.findOne(AppUserUtils.getClientId()),
                CommonUtil.convertStringToDate(workDairyGetReq.getWorkDairyDate()));
        workDairyEmpDtls.forEach((workDairyEmp) -> {
            LabelKeyTO labelKeyto = new LabelKeyTO();
            labelKeyto.setId(Long.valueOf((String) workDairyEmp[0]));
            labelKeyto.setCode((String) workDairyEmp[1]);
            Map<String, String> displayMap = new HashMap<String, String>();
            displayMap.put(CommonConstants.FIRST_NAME, (String) workDairyEmp[2]);
            displayMap.put(CommonConstants.LAST_NAME, (String) workDairyEmp[3]);
            displayMap.put(CommonConstants.GENDER, (String) workDairyEmp[4]);
            displayMap.put(CommonConstants.CLASS_TYPE, (String) workDairyEmp[5]);
            displayMap.put(CommonConstants.COMPANY_CATG_NAME, (String) workDairyEmp[6]);
            displayMap.put(CommonConstants.PROCURE_CATG, (String) workDairyEmp[7]);
            labelKeyto.setDisplayNamesMap(displayMap);
            labelKeyto.setUnitOfMeasure("hours");
            empRegmap.put(labelKeyto.getId(), labelKeyto);
        });
        return empRegmap;
    }

    public Map<Long, LabelKeyTO> getPlantRegMap(WorkDairyGetReq workDairyGetReq) {
        Map<Long, LabelKeyTO> plantRegmap = new HashMap<Long, LabelKeyTO>();
        //TODO check this query after Plant Attendance 
        List<Object[]> workDairyPlantDtls = plantRegProjRepositoryCopy.getPlantsForWorkDairy(
                epsProjRepository.findOne(workDairyGetReq.getProjId()),
                clientRegRepository.findOne(AppUserUtils.getClientId()),
                CommonUtil.convertStringToDate(workDairyGetReq.getWorkDairyDate()));
        workDairyPlantDtls.forEach((workDairyPlant) -> {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId((Long) workDairyPlant[0]);
            labelKeyTO.setCode((String) workDairyPlant[1]);
            labelKeyTO.setName((String) workDairyPlant[2]);
            labelKeyTO.setUnitOfMeasure("");
            Map<String, String> displayMap = new HashMap<String, String>();
            displayMap.put(CommonConstants.PLANT_REG_NO, (String) workDairyPlant[3]);
            displayMap.put(CommonConstants.PLANT_MANFACTURE, (String) workDairyPlant[4]);
            displayMap.put(CommonConstants.PLANT_MODEL, (String) workDairyPlant[5]);
            displayMap.put(CommonConstants.CLASS_TYPE, (String) workDairyPlant[6]);
            displayMap.put(CommonConstants.COMPANY_CATG_NAME, (String) workDairyPlant[7]);
            displayMap.put(CommonConstants.PROCURE_CATG, (String) workDairyPlant[8]);
            labelKeyTO.setDisplayNamesMap(displayMap);
            plantRegmap.put(labelKeyTO.getId(), labelKeyTO);
        });
        return plantRegmap;
    }

    public WorkDairyRegResp getEmpRegDetails(WorkDairyGetReq workDairyGetReq) {
        WorkDairyRegResp workDairyRegResp = new WorkDairyRegResp();
        List<LabelKeyTO> labelKeyList = new ArrayList<LabelKeyTO>();
        List<WorkDairyEntity> workDairyList = workDairyRepository.findWorkDairy(workDairyGetReq.getProjId(),
                CommonUtil.convertStringToDate(workDairyGetReq.getWorkDairyDate()), workDairyGetReq.getCrewId(), 1);
        if (!workDairyList.isEmpty()) {

            List<EmpAttendanceEntity> empAttendenceList = empAttendenceRepository.getEmpByAttendenceDate(
                    epsProjRepository.findOne(workDairyGetReq.getProjId()),
                    projCrewListRepository.findOne(workDairyGetReq.getCrewId()),
                    clientRegRepository.findOne(AppUserUtils.getClientId()),
                    CommonUtil.convertStringToDate(workDairyGetReq.getWorkDairyDate()));

            empAttendenceList.forEach((workDairyEmp) -> {
                LabelKeyTO labelKeyto = new LabelKeyTO();
                EmpRegisterDtlEntity empRegister = workDairyEmp.getEmpId();
                labelKeyto.setId(empRegister.getId());
                labelKeyto.setCode(empRegister.getCode());
                Map<String, String> displayMap = new HashMap<String, String>();
                displayMap.put(CommonConstants.FIRST_NAME, empRegister.getFirstName());
                displayMap.put(CommonConstants.LAST_NAME, empRegister.getLastName());
                displayMap.put(CommonConstants.GENDER, empRegister.getGender());
                displayMap.put(CommonConstants.CLASS_TYPE, empRegister.getEmpClassMstrEntity().getName());
                displayMap.put(CommonConstants.COMPANY_CATG_NAME, empRegister.getCompanyMstrEntity().getName());
                displayMap.put(CommonConstants.PROCURE_CATG, empRegister.getProcureCatgDtlEntity().getName());
                labelKeyto.setDisplayNamesMap(displayMap);
                labelKeyto.setUnitOfMeasure("hours");
                labelKeyList.add(labelKeyto);
            });
        }
        workDairyRegResp.setLabelKeyTOs(labelKeyList);
        return workDairyRegResp;
    }

    public WorkDairyRegResp getPlantRegDetails(WorkDairyGetReq workDairyGetReq) {
        WorkDairyRegResp workDairyRegResp = new WorkDairyRegResp();
        List<LabelKeyTO> labelKeyList = new ArrayList<>();
        List<WorkDairyEntity> workDairyList = workDairyRepository.findWorkDairy(workDairyGetReq.getProjId(),
                CommonUtil.convertStringToDate(workDairyGetReq.getWorkDairyDate()), workDairyGetReq.getCrewId(), 1);
        if (!workDairyList.isEmpty()) {
            List<PlantAttendanceEntity> plantAttendenceEntities = plantAttendenceRepository.getPlantsByAttendanceDate(
                    epsProjRepository.findOne(workDairyGetReq.getProjId()),
                    projCrewListRepository.findOne(workDairyGetReq.getCrewId()),
                    clientRegRepository.findOne(AppUserUtils.getClientId()),
                    CommonUtil.convertStringToDate(workDairyGetReq.getWorkDairyDate()));

            plantAttendenceEntities.forEach((plantAttend) -> {
                PlantRegisterDtlEntity plantRegister = plantAttend.getPlantRegisterDtlEntity();
                PlantMstrEntity plantMstr = plantRegister.getPlantClassMstrId();
                LabelKeyTO labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(plantRegister.getId());
                labelKeyTO.setCode(plantRegister.getAssertId());
                labelKeyTO.setName(plantRegister.getDesc());
                labelKeyTO.setUnitOfMeasure("");
                Map<String, String> displayMap = new HashMap<String, String>();
                displayMap.put(CommonConstants.PLANT_REG_NO, plantRegister.getRegNumber());
                displayMap.put(CommonConstants.PLANT_MANFACTURE, plantRegister.getManfacture());
                displayMap.put(CommonConstants.PLANT_MODEL, plantRegister.getModel());
                displayMap.put(CommonConstants.CLASS_TYPE, plantMstr.getName());
                displayMap.put(CommonConstants.COMPANY_CATG_NAME, plantRegister.getCmpId().getName());
                ProcureCatgDtlEntity procureCatgDtlEntity = plantRegister.getProcurecatgId();
                if (procureCatgDtlEntity != null)
                    displayMap.put(CommonConstants.PROCURE_CATG, plantRegister.getProcurecatgId().getName());
                labelKeyTO.setDisplayNamesMap(displayMap);
                labelKeyList.add(labelKeyTO);
            });
        }
        workDairyRegResp.setLabelKeyTOs(labelKeyList);
        return workDairyRegResp;
    }

    public WorkDairyDetailResp getWorkDairyDetails(WorkDairyGetReq workDairyGetReq) {
        WorkDairyDetailResp workDairyDetailResp = new WorkDairyDetailResp();
        workDairyDetailResp.setWorkDairyTO(getWorkDairyById(workDairyGetReq).getWorkDairyTO());
        workDairyDetailResp.setWorkDairyCostCodeTOs(getWorkDairyCostCodes(workDairyGetReq).getWorkDairyCostCodeTOs());
        workDairyDetailResp.setWorkDairyEmpDtlTOs(getWorkDairyEmpDetails(workDairyGetReq).getWorkDairyEmpDtlTOs());
        workDairyDetailResp.setWorkDairyPlantDtlTOs(getWorkDairyPlantDetails(workDairyGetReq).getWorkDairyPlantDtlTOs());
        workDairyDetailResp.setWorkDairyMaterialDtlTOs(getWorkDairyMaterialDetails(workDairyGetReq).getWorkDairyMaterialDtlTOs());
        workDairyDetailResp.setWorkDairyProgressDtlTOs(getWorkDairyProgressDetails(workDairyGetReq).getWorkDairyProgressDtlTOs());
        workDairyDetailResp.setTimeFlag(workDairyProjectSettings(workDairyGetReq));
        return workDairyDetailResp;

    }
    
    public WorkDairyCopyResp copyWorkDairy(WorkDairyGetReq workDairyGetReq) {
        if (workDairyGetReq.getWorkDairyId() == null) {
            List<WorkDairyEntity> workDairyEntities = workDairyRepository.findWorkDairy(workDairyGetReq.getProjId(),
                    CommonUtil.convertStringToDate(workDairyGetReq.getWorkDairyDate()), workDairyGetReq.getCrewId(), 1);
            if (!workDairyEntities.isEmpty())
                workDairyGetReq.setWorkDairyId(workDairyEntities.get(0).getId());
        }
        WorkDairyCopyResp workDairyCopyResp = new WorkDairyCopyResp();
        workDairyCopyResp.setWorkDairyTO(getWorkDairyById(workDairyGetReq).getWorkDairyTO());
        workDairyCopyResp.setWorkDairyCostCodeTOs(getWorkDairyCostCodes(workDairyGetReq).getWorkDairyCostCodeTOs());
        workDairyCopyResp.setWorkDairyEmpDtlTOs(getWorkDairyEmpDetailsForCopy(workDairyGetReq).getWorkDairyEmpDtlTOs());
        workDairyCopyResp.setWorkDairyPlantDtlTOs(getWorkDairyPlantDetailsForCopy(workDairyGetReq).getWorkDairyPlantDtlTOs());
        workDairyCopyResp.setWorkDairyMaterialDtlTOs(getWorkDairyMaterialDetailsForCopy(workDairyGetReq).getWorkDairyMaterialDtlTOs());
        workDairyCopyResp.setWorkDairyProgressDtlTOs(getWorkDairyProgressDetails(workDairyGetReq).getWorkDairyProgressDtlTOs());
        if (workDairyGetReq.getWorkDairyId() != null)
        	workDairyCopyResp.setTimeFlag(workDairyProjectSettings(workDairyGetReq));

        return workDairyCopyResp;
    }
    
    public WorkDairyResp getWorkDairyById(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyEntity> workDairyEntities = workDairyRepository
                .findWorkDairyById(workDairyGetReq.getWorkDairyId(), workDairyGetReq.getStatus());
        WorkDairyResp workDairyResp = new WorkDairyResp();
        for (WorkDairyEntity workDairyEntity : workDairyEntities) {
            workDairyResp.setWorkDairyTO(WorkDairyHandler.convertEntityToPOJO(workDairyEntity));
        }
        return workDairyResp;
    }

    public WorkDairyCostCodeResp getWorkDairyCostCodes(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyCostCodeEntity> workDairyCostCodeEntities = workDairyCostCodeRepository
                .findWorkDairyCostCodes(workDairyGetReq.getWorkDairyId(), workDairyGetReq.getStatus());
        WorkDairyCostCodeResp workDairyCostCodeResp = new WorkDairyCostCodeResp();
        for (WorkDairyCostCodeEntity workDairyCostCodeEntity : workDairyCostCodeEntities) {
            workDairyCostCodeResp.getWorkDairyCostCodeTOs()
                    .add(WorkDairyCostCodeHandler.convertMstrEntityToPOJO(workDairyCostCodeEntity));
        }
        return workDairyCostCodeResp;
    }

    public WorkDairyEmpResp getWorkDairyEmpDetails(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyEmpDtlEntity> workDairyEmpDtlEntities = empWorkDairyRepository
                .findWorkDairyEmpDetails(workDairyGetReq.getWorkDairyId(), workDairyGetReq.getStatus());
        WorkDairyEmpResp workDairyEmpResp = new WorkDairyEmpResp();
        WorkDairyEmpDtlTO workDairyEmpDtlTO = null;
        WorkDairyEmpWageTO workDairyEmpWageTO = null;
        for (WorkDairyEmpDtlEntity workDairyEmpDtlEntity : workDairyEmpDtlEntities) {
            workDairyEmpDtlTO = EmpWorkDairyHandler.convertEntityToPOJO(workDairyEmpDtlEntity,
                    projEmpClassRepositoryCopy);
            workDairyEmpResp.getWorkDairyEmpDtlTOs().add(workDairyEmpDtlTO);
            for (WorkDairyEmpWageEntity workDairyEmpWageEntity : workDairyEmpDtlEntity.getWorkDairyEmpWageEntities()) {
                workDairyEmpWageTO = EmpWorkDairyHandler.convertWageEntityToPOJO(workDairyEmpWageEntity);
                workDairyEmpDtlTO.getWorkDairyEmpWageTOs().add(workDairyEmpWageTO);
                for (WorkDairyEmpCostEntity workDairyEmpCostEntity : workDairyEmpWageEntity
                        .getWorkDairyEmpCostEntities()) {
                    workDairyEmpWageTO.getWorkDairyEmpCostDtlTOs()
                            .add(EmpWorkDairyHandler.convertCostEntityToPOJO(workDairyEmpCostEntity));
                }
            }
        }
        return workDairyEmpResp;
    }
    
    private WorkDairyEmpResp getWorkDairyEmpDetailsForCopy(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyEmpDtlEntity> workDairyEmpDtlEntities = empWorkDairyRepository
                .findWorkDairyEmpDetails(workDairyGetReq.getWorkDairyId(), workDairyGetReq.getStatus());
        WorkDairyEmpResp workDairyEmpResp = new WorkDairyEmpResp();
        WorkDairyEmpDtlTO workDairyEmpDtlTO = null;
        WorkDairyEmpWageTO workDairyEmpWageTO = null;
        for (WorkDairyEmpDtlEntity workDairyEmpDtlEntity : workDairyEmpDtlEntities) {
            workDairyEmpDtlTO = EmpWorkDairyHandler.convertEntityToPOJO(workDairyEmpDtlEntity,
                    projEmpClassRepositoryCopy);
            workDairyEmpResp.getWorkDairyEmpDtlTOs().add(workDairyEmpDtlTO);
            List<WorkDairyEmpWageEntity> WorkDairyEmpWageEntities = workDairyEmpDtlEntity.getWorkDairyEmpWageEntities()
                    .stream().filter(workDairyEmpWageEtity -> {
                        return workDairyEmpWageEtity.getApprStatus() == null;
                    }).collect(Collectors.toList());
            for (WorkDairyEmpWageEntity workDairyEmpWageEntity : WorkDairyEmpWageEntities) {
                workDairyEmpWageTO = EmpWorkDairyHandler.convertWageEntityToPOJO(workDairyEmpWageEntity);
                workDairyEmpDtlTO.getWorkDairyEmpWageTOs().add(workDairyEmpWageTO);
                for (WorkDairyEmpCostEntity workDairyEmpCostEntity : workDairyEmpWageEntity
                        .getWorkDairyEmpCostEntities()) {
                	for(Long costIds :workDairyGetReq.getCostIds()) {
						  if(costIds.equals(workDairyEmpCostEntity.getCostId().getId())) {
							  workDairyEmpWageTO.getWorkDairyEmpCostDtlTOs()
	                            .add(EmpWorkDairyHandler.convertCostEntityToPOJO(workDairyEmpCostEntity));
						  }	
						  
					}
                  
                }
            }
        }
        return workDairyEmpResp;
    }
    
    public WorkDairyPlantResp getWorkDairyPlantDetails(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyPlantDtlEntity> workDairyPlantDtlEntities = plantWorkDairyRepository
                .findWorkDairyPlantDetails(workDairyGetReq.getWorkDairyId(), workDairyGetReq.getStatus());
        WorkDairyPlantResp workDairyPlantResp = new WorkDairyPlantResp();
        WorkDairyPlantDtlTO workDairyPlantDtlTO = null;
        WorkDairyPlantStatusTO workDairyPlantStatusTO = null;

        for (WorkDairyPlantDtlEntity workDairyPlantDtlEntity : workDairyPlantDtlEntities) {
            workDairyPlantDtlTO = PlantWorkDairyHandler.convertEntityToPOJO(workDairyPlantDtlEntity);
            workDairyPlantResp.getWorkDairyPlantDtlTOs().add(workDairyPlantDtlTO);
            for (WorkDairyPlantStatusEntity workDairyPlantStatusEntity : workDairyPlantDtlEntity
                    .getWorkDairyPlantStatusEntities()) {
                workDairyPlantStatusTO = PlantWorkDairyHandler
                        .convertPlantStatusEntityToPOJO(workDairyPlantStatusEntity);
                workDairyPlantDtlTO.getWorkDairyPlantStatusTOs().add(workDairyPlantStatusTO);
                for (WorkDairyPlantCostEntity workDairyPlantCostEntity : workDairyPlantStatusEntity
                        .getWorkDairyPlantCostEntities()) {
                    workDairyPlantStatusTO.getWorkDairyPlantCostDtlTOs()
                            .add(PlantWorkDairyHandler.convertCostEntityToPOJO(workDairyPlantCostEntity));
                }
            }
        }
        return workDairyPlantResp;
    }

    private WorkDairyPlantResp getWorkDairyPlantDetailsForCopy(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyPlantDtlEntity> workDairyPlantDtlEntities = plantWorkDairyRepository
                .findWorkDairyPlantDetails(workDairyGetReq.getWorkDairyId(), workDairyGetReq.getStatus());
        WorkDairyPlantResp workDairyPlantResp = new WorkDairyPlantResp();
        WorkDairyPlantDtlTO workDairyPlantDtlTO = null;
        WorkDairyPlantStatusTO workDairyPlantStatusTO = null;

        for (WorkDairyPlantDtlEntity workDairyPlantDtlEntity : workDairyPlantDtlEntities) {
            workDairyPlantDtlTO = PlantWorkDairyHandler.convertEntityToPOJO(workDairyPlantDtlEntity);
            workDairyPlantResp.getWorkDairyPlantDtlTOs().add(workDairyPlantDtlTO);

            List<WorkDairyPlantStatusEntity> WorkDairyPlantEntities = workDairyPlantDtlEntity
                    .getWorkDairyPlantStatusEntities().stream().filter(workDairyPlantEtity -> {
                        return workDairyPlantEtity.getApprStatus() == null;
                    }).collect(Collectors.toList());

            for (WorkDairyPlantStatusEntity workDairyPlantStatusEntity : WorkDairyPlantEntities) {
                workDairyPlantStatusTO = PlantWorkDairyHandler
                        .convertPlantStatusEntityToPOJO(workDairyPlantStatusEntity);
                workDairyPlantDtlTO.getWorkDairyPlantStatusTOs().add(workDairyPlantStatusTO);
                for (WorkDairyPlantCostEntity workDairyPlantCostEntity : workDairyPlantStatusEntity
                        .getWorkDairyPlantCostEntities()) {
                	for(Long costIds :workDairyGetReq.getCostIds()) {
						  if(costIds.equals(workDairyPlantCostEntity.getCostId().getId())) {
							  workDairyPlantStatusTO.getWorkDairyPlantCostDtlTOs()
	                            .add(PlantWorkDairyHandler.convertCostEntityToPOJO(workDairyPlantCostEntity));
						  }	
						  
					}
                }
            }
        }
        return workDairyPlantResp;
    }
    
    public WorkDairyMaterialResp getWorkDairyMaterialDetails(WorkDairyGetReq workDairyGetReq) {
        return mapWorkDairyMaterialDetials(materialWorkDairyRepository
                .findWorkDairyMaterialDetails(workDairyGetReq.getWorkDairyId(), workDairyGetReq.getStatus()));
    }
    
    public WorkDairyMaterialResp getWorkDairyMaterialDetailsForCopy(WorkDairyGetReq workDairyGetReq) {
    	/*
        List<WorkDairyMaterialDtlEntity> workDairyMaterialDtlEntities = materialWorkDairyRepository
                .findWorkDairyMaterialDetails(workDairyGetReq.getWorkDairyId(), workDairyGetReq.getStatus());
        List<WorkDairyMaterialDtlEntity> workDairyMaterialDtlEntitesActual = new ArrayList<WorkDairyMaterialDtlEntity>();
        workDairyMaterialDtlEntities.forEach(workDairyMat -> {
            workDairyMaterialDtlEntitesActual.add(
                    materialWorkDairyRepository.findWorkDairyMaterialDetails(workDairyMat.getProjDocketSchId()).get(0));
        });
       
        return mapWorkDairyMaterialDetials(workDairyMaterialDtlEntitesActual);
         */
    	return mapWorkDairyMaterialCopyDetials(materialWorkDairyRepository
                .findWorkDairyMaterialDetails(workDairyGetReq.getWorkDairyId(), workDairyGetReq.getStatus()),workDairyGetReq);
    }

    public WorkDairyProgressResp getWorkDairyProgressDetails(WorkDairyGetReq workDairyGetReq) {
        List<WorkDairyProgressDtlEntity> workDairyProgressDtlEntities = progressWorkDairyRepository
                .findWorkDairyProgressDetails(workDairyGetReq.getWorkDairyId(), workDairyGetReq.getStatus());
        WorkDairyProgressResp workDairyProgressResp = new WorkDairyProgressResp();
        for (WorkDairyProgressDtlEntity workDairyProgressDtlEntity : workDairyProgressDtlEntities) {
            workDairyProgressResp.getWorkDairyProgressDtlTOs()
                    .add(ProgressWorkDairyHandler.convertEntityToPOJO(workDairyProgressDtlEntity));
        }
        return workDairyProgressResp;
    }
    
    private boolean workDairyProjectSettings(WorkDairyGetReq workDairyGetReq) {
        WorkDairyEntity workDairyEntity = workDairyRepository.findOne(workDairyGetReq.getWorkDairyId());
        Date workDairyDate = workDairyEntity.getWorkDairyDate();
        Date newDate = new Date();
        ProjMstrEntity projMstr = epsProjRepository.findOne(workDairyGetReq.getProjId());
        String apprStatus = (workDairyGetReq.getApprStatus() == null) ? "Original Submission"
                : workDairyGetReq.getApprStatus();
        log.info("apprStatus " + apprStatus);
        WorkDairyNormalTimeEntity workDairyNormal = projWorkDairyRepository.getWorkDairyProjSettings(projMstr,
                apprStatus);
        int hours = workDairyNormal.getCutOffHours();
        log.info("getCutOffHours " + hours);
        hours += workDairyNormal.getCutOffDays() * 24;
        log.info("getCutOffDays " + hours);
        hours += workDairyNormal.getCutOffMinutes() / 60;
        log.info("getCutOffMinutes " + hours);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -hours);
        Date dateToCompare = calendar.getTime();
        log.info("Calendar.HOUR_OF_DAY " + Calendar.HOUR_OF_DAY);
        
        log.info("workDairyDate " + workDairyDate);
        log.info("dateToCompare " + dateToCompare);
        log.info("workDairyDate.compareTo(dateToCompare) " + workDairyDate.compareTo(dateToCompare));
        
        if (workDairyDate.compareTo(dateToCompare) >= 0) {
            return true;
        } else {
            String type = null;
            if ("Original Submission".equalsIgnoreCase(apprStatus) || "Internal Approval".equalsIgnoreCase(apprStatus)
                    || "External Approval".equalsIgnoreCase(apprStatus)) {
            	log.info("IF apprStatus " + apprStatus);
                type = apprStatus.split(" ")[0];
                log.info("type " + type);
            } else {
            	log.info("ELSE apprStatus " + apprStatus);
                type = apprStatus;
                log.info("type " + type);
            }
            log.info("workDairyGetReq.getCrewId() " + workDairyGetReq.getCrewId());
            log.info("type " + type);
            log.info("Work Diary ID " + workDairyGetReq.getWorkDairyId());
            List<WorkDairyAdditionalTimeEntity> workDairyAdditionalTimeEntity = workDairyAdditionalTimeRepository
                    .getWorkDairyDetails(workDairyGetReq.getCrewId(), type);
            for (WorkDairyAdditionalTimeEntity workDairyAdditionalTimeEntityCopy : workDairyAdditionalTimeEntity) {
            	if (workDairyAdditionalTimeEntityCopy.getApprovedDate() != null) { 
            		Calendar additionalTimeCal = Calendar.getInstance();
                    log.info("additionalTimeCal " + additionalTimeCal);
                    additionalTimeCal.setTime(workDairyAdditionalTimeEntityCopy.getApprovedDate());
                    log.info("workDairyAdditionalTimeEntityCopy " + workDairyAdditionalTimeEntityCopy);
                    additionalTimeCal.add(Calendar.HOUR_OF_DAY, hours);
                    log.info("hours " + hours);
                    log.info("Calendar.HOUR_OF_DAY " + Calendar.HOUR_OF_DAY);
                    log.info("workDairyAdditionalTimeEntity.size() " + workDairyAdditionalTimeEntity.size());
                    log.info("workDairyDate " + workDairyDate);
                    log.info("workDairyAdditionalTimeEntityCopy.getFromDate() " + workDairyAdditionalTimeEntityCopy.getFromDate());
                    log.info("workDairyDate.compareTo(workDairyAdditionalTimeEntityCopy.getFromDate()) " + workDairyDate.compareTo(workDairyAdditionalTimeEntityCopy.getFromDate()));
                    log.info("workDairyAdditionalTimeEntityCopy.getToDate() " + workDairyAdditionalTimeEntityCopy.getToDate());
                    log.info("workDairyDate.compareTo(workDairyAdditionalTimeEntityCopy.getToDate()) " + workDairyDate.compareTo(workDairyAdditionalTimeEntityCopy.getToDate()));
                    log.info("newDate " + newDate);
                    log.info("additionalTimeCal.getTime() " + additionalTimeCal.getTime());
                    log.info("newDate.compareTo(additionalTimeCal.getTime()) " +newDate.compareTo(additionalTimeCal.getTime()));
                    if ((workDairyAdditionalTimeEntity != null)
                            && workDairyDate.compareTo(workDairyAdditionalTimeEntityCopy.getFromDate()) >= 0
                            && workDairyDate.compareTo(workDairyAdditionalTimeEntityCopy.getToDate()) <= 0
                            && newDate.compareTo(additionalTimeCal.getTime()) <= 0) {
                        return true;
                    }
                    if(workDairyAdditionalTimeEntityCopy.getWorkDairyEntity().getId().equals(workDairyGetReq.getWorkDairyId()) && workDairyAdditionalTimeEntityCopy.getStatus().equals("Approved") && workDairyAdditionalTimeEntityCopy.getType().equals(type))
                    	return true;          
            	} 
            }
        }
        return false;
    }

    private WorkDairyMaterialResp mapWorkDairyMaterialDetials(
            List<WorkDairyMaterialDtlEntity> workDairyMaterialDtlEntities) {
        WorkDairyMaterialResp workDairyMaterialResp = new WorkDairyMaterialResp();
        WorkDairyMaterialDtlTO workDairyMaterialDtlTO = null;
        WorkDairyMaterialStatusTO workDairyMaterialStatusTO = null;
        for (WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity : workDairyMaterialDtlEntities) {
            workDairyMaterialDtlTO = MaterialWorkDairyHandler.convertEntityToPOJO(workDairyMaterialDtlEntity,
                    materialWorkDairyRepository);
            workDairyMaterialResp.getWorkDairyMaterialDtlTOs().add(workDairyMaterialDtlTO);
            for (WorkDairyMaterialStatusEntity workDairyMaterialStatusEntity : workDairyMaterialDtlEntity
                    .getWorkDairyMaterialStatusEntities()) {
                workDairyMaterialStatusTO = MaterialWorkDairyHandler
                        .convertMaterialStatusEntityToPOJO(workDairyMaterialStatusEntity);
                workDairyMaterialDtlTO.getWorkDairyMaterialStatusTOs().add(workDairyMaterialStatusTO);
                for (WorkDairyMaterialCostEntity workDairyMaterialCostEntity : workDairyMaterialStatusEntity
                        .getWorkDairyMaterialCostEntities()) {
                    workDairyMaterialStatusTO.getWorkDairyMaterialCostTOs()
                            .add(MaterialWorkDairyHandler.convertCostEntityToPOJO(workDairyMaterialCostEntity));
                }
            }
        }

        return workDairyMaterialResp;
    }
    
    private WorkDairyMaterialResp mapWorkDairyMaterialCopyDetials(
            List<WorkDairyMaterialDtlEntity> workDairyMaterialDtlEntities,WorkDairyGetReq workDairyGetReq) {
        WorkDairyMaterialResp workDairyMaterialResp = new WorkDairyMaterialResp();
        WorkDairyMaterialDtlTO workDairyMaterialDtlTO = null;
        WorkDairyMaterialStatusTO workDairyMaterialStatusTO = null;
        for (WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity : workDairyMaterialDtlEntities) {
            workDairyMaterialDtlTO = MaterialWorkDairyHandler.convertEntityToPOJO(workDairyMaterialDtlEntity,
                    materialWorkDairyRepository);
            workDairyMaterialResp.getWorkDairyMaterialDtlTOs().add(workDairyMaterialDtlTO);
            for (WorkDairyMaterialStatusEntity workDairyMaterialStatusEntity : workDairyMaterialDtlEntity
                    .getWorkDairyMaterialStatusEntities()) {
                workDairyMaterialStatusTO = MaterialWorkDairyHandler
                        .convertMaterialStatusEntityToPOJO(workDairyMaterialStatusEntity);
                workDairyMaterialDtlTO.getWorkDairyMaterialStatusTOs().add(workDairyMaterialStatusTO);
                for (WorkDairyMaterialCostEntity workDairyMaterialCostEntity : workDairyMaterialStatusEntity
                        .getWorkDairyMaterialCostEntities()) {
                	for(Long costIds :workDairyGetReq.getCostIds()) {
						  if(costIds.equals(workDairyMaterialCostEntity.getCostId().getId())) {
							  workDairyMaterialStatusTO.getWorkDairyMaterialCostTOs()
	                            .add(MaterialWorkDairyHandler.convertCostEntityToPOJO(workDairyMaterialCostEntity));
						  }	
						  
					}
                    
                }
            }
        }

        return workDairyMaterialResp;
    }
    
    public AppResp saveWorkDairyDetails(WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        WorkDairyTO workDairyTO = workDairyDtlSaveReq.getWorkDairyTO();
        if (CommonUtil.isBlankLong(workDairyTO.getId())) {
            WorkDairyEntity workDairyEntity = WorkDairyHandler.convertPOJOToEntity(workDairyTO, epsProjRepository,
                    projCrewListRepository, projWorkShiftRepository, weatherRepository, purchaseOrderRepository,
                    loginRepository);
            List<LabelKeyTO> labelKeyTOs = workDairyProcRepository.generateWorkDairyCode(AppUserUtils.getClientId(),
                    workDairyDtlSaveReq.getWorkDairyTO().getProjId(), workDairyDtlSaveReq.getWorkDairyTO().getCrewId());
            if (CommonUtil.isListHasData(labelKeyTOs)) {
                workDairyEntity.setCode(labelKeyTOs.get(0).getCode());
            }
            workDairyRepository.save(workDairyEntity);
        }
        saveCostCodes(workDairyDtlSaveReq.getWorkDairyTO().getWorkDairyCostCodeTOs());
        AppResp appResp = saveAllDetails(workDairyDtlSaveReq);
        if (CommonUtil.objectNotNull(appResp)) {
            return appResp;
        }
        return null;
    }

    public AppResp submitWorkDairy(WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        WorkDairyTO workDairyTO = workDairyDtlSaveReq.getWorkDairyTO();
        WorkDairyEntity workDairyEntity = WorkDairyHandler.convertPOJOToEntity(workDairyTO, epsProjRepository,
                projCrewListRepository, projWorkShiftRepository, weatherRepository, purchaseOrderRepository,
                loginRepository);
        if (CommonUtil.isBlankLong(workDairyTO.getId())) {

            List<LabelKeyTO> labelKeyTOs = workDairyProcRepository.generateWorkDairyCode(AppUserUtils.getClientId(),
                    workDairyDtlSaveReq.getWorkDairyTO().getProjId(), workDairyDtlSaveReq.getWorkDairyTO().getCrewId());
            if (CommonUtil.isListHasData(labelKeyTOs)) {
                workDairyEntity.setCode(labelKeyTOs.get(0).getCode());
            }
        }
        WorkDairyNotificationsTO workDiaryNotificationTO = new WorkDairyNotificationsTO();
        workDiaryNotificationTO.setNotificationMsg(workDairyTO.getNotificationMsg());
        workDiaryNotificationTO.setNotificationStatus(workDairyTO.getNotificationStatus());
        WorkDairyNotificationsEntity workDairyNotificationsEntity = WorkDairyNotificationsHandlerCopy
                .convertPOJOToEntity(workDiaryNotificationTO, workDairyRepository, workDairyTO);

        WorkDairyNotificationsEntity savedEntity = workDairyNotificationRepository.save(workDairyNotificationsEntity);
        workDairyRepository.save(workDairyEntity);
        sendWorkDairyEmail(savedEntity);
        AppResp appResp = saveAllDetails(workDairyDtlSaveReq);
        if (CommonUtil.objectNotNull(appResp)) {
            return appResp;
        }
        return null;
    }

    /**
     * Send work dairy mail notification for approval
     * 
     * @param savedEntity
     */
    private void sendWorkDairyEmail(WorkDairyNotificationsEntity savedEntity) {
    	String wdGeneratedCode = generateWorkDairyCode(savedEntity.getWorkDairyEntity() );
        String crewName = "";
        String code = null;
        WorkDairyEntity workDairyEntity = savedEntity.getWorkDairyEntity();
        String shift = null;
        String apprName = null;
        String description = null;
        String text = null;
        String toEmail = null;
        String ccEmail = "";
        String epsName = null;
        String projName = null;
        String toUserName = null;
        if (savedEntity.getNotificationStatus() == "Pending"
                && savedEntity.getWorkDairyEntity().isClientApproval() == true) {
            description = "I have submitted my work Diary through " + pot + ", as per details mentioned here below.";
            text = "This is for your approval .";
        } else {
            description = "I have submitted my work Diary through " + pot + ", as per details mentioned here below.";
            text = "This is for your approval please.";
        }

        if (workDairyEntity != null) {
            if (workDairyEntity.getInternalApprUserId() != null) {
            	toUserName = workDairyEntity.getInternalApprUserId().getDisplayName();
                apprName = workDairyEntity.getInternalApprUserId().getUserName();
            }
            ProjWorkShiftMstrEntity shiftId = workDairyEntity.getShiftId();
            if (shiftId != null)
                shift = shiftId.getCode();
            ProjCrewMstrEntity crewId = workDairyEntity.getCrewId();
            if (crewId != null)
                crewName = crewId.getCode();
            code = workDairyEntity.getCode();
            if (workDairyEntity.getProjId() != null)
                epsName = workDairyEntity.getProjId().getParentProjectMstrEntity().getProjName();
            if (workDairyEntity.getProjId() != null)
                projName = workDairyEntity.getProjId().getProjName();
            if (workDairyEntity.getInternalApprUserId() != null)
                toEmail = workDairyEntity.getInternalApprUserId().getEmail();
        }
        String notifyCode = WorkDairyNotificationsHandlerCopy.generateCode(savedEntity);
        String subject = "Request for Approval of Work Diary -  " + wdGeneratedCode + " Dated "
                + CommonUtil.convertDateToString(savedEntity.getDate());
        String body = "<html><body><p>" + toUserName + ",</p>" + "<p>" + description + "</p>"
                + "<table border='1'><tr><td>EPS </td><td>" + epsName + "</td></tr><tr><td>Project </td><td>"
                + projName + "</td></tr><tr><td>Crew </td><td>" + crewName + "</td></tr><tr><td>Shift</td><td>"
                + shift + "</td></tr><tr><td>Work Diary Number</td><td>" + wdGeneratedCode + "</td></tr><tr><td>Date</td><td>"
                + CommonUtil.convertDateToString(savedEntity.getDate()) + "</td></tr></table><br>" + text + "<p>Regards,</p>" + "<p>"
                + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
        commonEmail.sendEmailNotification(toEmail, ccEmail, subject, body);
    }

    public void approveWorkDairy(WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        WorkDairyNotificationsEntity workDairyNotificationsEntity = new WorkDairyNotificationsEntity();
        WorkDairyTO workDairyTO = workDairyDtlSaveReq.getWorkDairyTO();
        WorkDairyEntity workDairyEntity = WorkDairyHandler.convertPOJOToEntity(workDairyTO, epsProjRepository,
                projCrewListRepository, projWorkShiftRepository, weatherRepository, purchaseOrderRepository,
                loginRepository);
        workDairyNotificationsEntity.setNotificationMsg(workDairyTO.getNotificationMsg());
        workDairyNotificationsEntity.setNotificationStatus(workDairyTO.getNotificationStatus());

        workDairyRepository.save(workDairyEntity);

        String InternalNotificationMsg = "Request for Internal Approval";
        String externalNotificationMsg = "Request for External Approval";
        String notificationStatus = "Pending";
        String newNotificationStatus = "Approved";
        String newNotificationMsg = "Approved Internally";
        String newClientNotificationMsg = "Approved by Client";

        // create WorkDairy
        if ("Approved".equalsIgnoreCase(workDairyTO.getNotificationStatus()) && !workDairyTO.isClientApproval()) {
            WorkDairyNotificationsEntity entity = workDairyNotificationRepository
                    .findWorkDairyNotifications(notificationStatus, InternalNotificationMsg, workDairyTO.getId());
            entity.setNotificationStatus(newNotificationStatus);
            entity.setNotificationMsg(newNotificationMsg);
            //send mail
            sendInternalWorkDairy(entity);
        }

        // Internal WorkDairy 
        if (workDairyTO.isClientApproval() && "Approved".equalsIgnoreCase(workDairyTO.getNotificationStatus())) {

            //updating existing notification
            WorkDairyNotificationsEntity entity = workDairyNotificationRepository
                    .findWorkDairyNotifications(notificationStatus, InternalNotificationMsg, workDairyTO.getId());
            entity.setNotificationStatus(notificationStatus);
            entity.setNotificationMsg(newNotificationMsg);

            // new notification creation
            WorkDairyNotificationsEntity notificationsEntity = new WorkDairyNotificationsEntity();
            notificationsEntity.setWorkDairyEntity(workDairyEntity);
            notificationsEntity.setDate(new Date());
            notificationsEntity.setNotificationMsg(externalNotificationMsg);
            notificationsEntity.setNotificationStatus(notificationStatus);
            notificationsEntity.setStatus(workDairyTO.getStatus());
            workDairyNotificationRepository.save(notificationsEntity);
            //send mail
            sendInternalWorkDairy(entity);
        }
        //Client WorkDairy Approval
        else if ("Client Approved".equalsIgnoreCase(workDairyTO.getApprStatus())) {
            //updating existing notification
            WorkDairyNotificationsEntity updateEntity = workDairyNotificationRepository
                    .findWorkDairyNotifications(notificationStatus, newNotificationMsg, workDairyTO.getId());
            updateEntity.setNotificationStatus(newNotificationStatus);

            // new notification creation
            WorkDairyNotificationsEntity clientEntity = workDairyNotificationRepository
                    .findWorkDairyNotifications(notificationStatus, externalNotificationMsg, workDairyTO.getId());
            clientEntity.setNotificationMsg(newClientNotificationMsg);
            clientEntity.setNotificationStatus(newNotificationStatus);
            sendClientWorkDairy(updateEntity);
        }
        saveAllDetails(workDairyDtlSaveReq);
    }

    private void sendClientWorkDairy(WorkDairyNotificationsEntity updateEntity) {
    	String wdGeneratedCode = generateWorkDairyCode(updateEntity.getWorkDairyEntity() );
        WorkDairyEntity workDairyEntity = updateEntity.getWorkDairyEntity();
        String crewName = "";
        String code = null;
        String shift = null;
        String apprName = null;
        String description = null;
        String text = null;
        String toEmail = null;
        String ccEmail = "";
        String epsName = null;
        String projName = null;
        String toUserName = null;
        if (updateEntity.getNotificationStatus() == "Approved"
                && updateEntity.getWorkDairyEntity().isClientApproval() == false) {
            description = "I have approved   work Diary through " + pot + ", as per details mentioned here below.";
            text = "This is for your information please.";
            toEmail = workDairyEntity.getReqUserId().getEmail();
            ccEmail = "";
        } else {
            description = "I have submitted work Diary through " + pot + ", as per details mentioned here below.";
            text = "This is for Client representative approval please.";
            toEmail = workDairyEntity.getInternalApprUserId().getEmail();
            ccEmail = workDairyEntity.getReqUserId().getEmail();
        }

        if (workDairyEntity != null) {
            if (workDairyEntity.getInternalApprUserId() != null) {
            	toUserName = workDairyEntity.getInternalApprUserId().getDisplayName();
                apprName = workDairyEntity.getInternalApprUserId().getUserName();
            }    
            ProjWorkShiftMstrEntity shiftId = workDairyEntity.getShiftId();
            if (shiftId != null)
                shift = shiftId.getCode();
            ProjCrewMstrEntity crewId = workDairyEntity.getCrewId();
            if (crewId != null)
                crewName = crewId.getCode();
            code = workDairyEntity.getCode();
            if (workDairyEntity.getProjId() != null)
                epsName = workDairyEntity.getProjId().getParentProjectMstrEntity().getProjName();
            if (workDairyEntity.getProjId() != null)
                projName = workDairyEntity.getProjId().getProjName();
            if (workDairyEntity.getInternalApprUserId() != null)
                toEmail = workDairyEntity.getInternalApprUserId().getEmail();
        }
        String notifyCode = WorkDairyNotificationsHandlerCopy.generateCode(updateEntity);

        if (updateEntity.getWorkDairyEntity().isClientApproval() == true
                && updateEntity.getNotificationStatus() == "Approved") {
            String toSubject = "Client Approval of Work Diary - " + wdGeneratedCode + " dated  "
                    + CommonUtil.convertDateToString(updateEntity.getDate());
            String body = "<html><body><p>" + toUserName + ",</p>" + "<p>" + description + "</p>"
                    + "<table border='1'><tr><td>EPS </td><td>" + epsName + "</td></tr><tr><td>Project </td><td>"
                    + projName + "</td></tr><tr><td>Crew </td><td>" + crewName + "</td></tr><tr><td>Shift</td><td>"
                    + shift + "</td></tr><tr><td>Work DiaryNumber</td><td>" + wdGeneratedCode
                    + "</td></tr><tr><td>Date</td><td>" + CommonUtil.convertDateToString(updateEntity.getDate()) + "</td></tr></table><br>" + text
                    + "<p>Regards,</p>" + "<p>" + AppUserUtils.getUserName() + "<br/>"
                    + AppUserUtils.getDisplayRole() + "</p></body></html>";
            commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, body);
        }
    }

    private void sendInternalWorkDairy(WorkDairyNotificationsEntity entity) {
        String crewName = "";
        String code = null;
        String shift = null;
        String apprName = null;
        String description = null;
        String text = null;
        String toEmail = null;
        String ccEmail = "";
        String epsName = null;
        String projName = null;
        String toUserName = null;
        WorkDairyEntity workDairyEntity = entity.getWorkDairyEntity();

        if (entity.getNotificationStatus() == "Approved" && entity.getWorkDairyEntity().isClientApproval() == false) {
            description = "I have approved   work Diary through " + pot + ", as per details mentioned here below.";
            text = "This is for your information please.";
            toEmail = workDairyEntity.getReqUserId().getEmail();
            ccEmail = "";
        } else {
            description = "I have submitted work Diary through " + pot + ", as per details mentioned here below.";
            text = "This is for Client representative approval please.";
            toEmail = workDairyEntity.getClientApprUserId().getEmail();
            ccEmail = workDairyEntity.getReqUserId().getEmail();
        }

        if (workDairyEntity != null) {
            if (workDairyEntity.getInternalApprUserId() != null) {
            	toUserName = workDairyEntity.getInternalApprUserId().getDisplayName();
                apprName = workDairyEntity.getInternalApprUserId().getUserName();
            }        
            ProjWorkShiftMstrEntity shiftId = workDairyEntity.getShiftId();
            if (shiftId != null)
                shift = shiftId.getCode();
            ProjCrewMstrEntity crewId = workDairyEntity.getCrewId();
            if (crewId != null)
                crewName = crewId.getCode();
            code = workDairyEntity.getCode();
            if (workDairyEntity.getProjId() != null)
                epsName = workDairyEntity.getProjId().getParentProjectMstrEntity().getProjName();
            if (workDairyEntity.getProjId() != null)
                projName = workDairyEntity.getProjId().getProjName();
            if (workDairyEntity.getInternalApprUserId() != null)
                toEmail = workDairyEntity.getInternalApprUserId().getEmail();
        }
        String notifyCode = WorkDairyNotificationsHandlerCopy.generateCode(entity);

        String subject = "Internal Approval of Work Diary " + code + " dated "
                + CommonUtil.convertDateToString(entity.getDate());
        String body = "<html><body><p>" + toUserName + ",</p>" + "<p>" + description + "</p>"
                + "<table border='1'><tr><td>EPS </td><td>" + epsName + "</td></tr><tr><td>Project </td><td>"
                + projName + "</td></tr><tr><td>Crew </td><td>" + crewName + "</td></tr><tr><td>Shift</td><td>"
                + shift + "</td></tr><tr><td>Work Diary Number</td><td>" + code + "</td></tr><tr><td>Date</td><td>"
                + CommonUtil.convertDateToString(entity.getDate()) + "</td></tr></table><br>" + text + "<p>Regards,</p>" + "<p>"
                + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
        commonEmail.sendEmailNotification(toEmail, ccEmail, subject, body);
    }

    private AppResp saveAllDetails(WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        AppResp appResp = saveEmpDetails(workDairyDtlSaveReq.getWorkDairyEmpDtlTOs(),
                workDairyDtlSaveReq.getWorkDairyTO());
        if (CommonUtil.objectNotNull(appResp)) {
            return appResp;
        }
        appResp = savePlantDetails(workDairyDtlSaveReq.getWorkDairyPlantDtlTOs(), workDairyDtlSaveReq.getWorkDairyTO());
        if (CommonUtil.objectNotNull(appResp)) {
            return appResp;
        }
        saveMaterialDetails(workDairyDtlSaveReq.getWorkDairyMaterialDtlTOs(), workDairyDtlSaveReq.getWorkDairyTO());
        saveProgressDetails(workDairyDtlSaveReq.getWorkDairyProgressDtlTOs(), workDairyDtlSaveReq.getWorkDairyTO());
        return null;
    }

    public AppResp saveWorkDairyEmpDetails(WorkDairyEmpSaveReq workDairyEmpSaveReq) {

        AppResp appResp = saveEmpDetails(workDairyEmpSaveReq.getWorkDairyEmpDtlTOs(),
                workDairyEmpSaveReq.getWorkDairyTO());

        if (CommonUtil.objectNotNull(appResp)) {
            return appResp;
        }
        return null;
    }

    private AppResp saveEmpDetails(List<WorkDairyEmpDtlTO> workDairyEmpDtlTOs, WorkDairyTO workDairyTO) {
        List<WorkDairyEmpDtlEntity> workDairyEmpDtlEntities = new ArrayList<WorkDairyEmpDtlEntity>();
        log.info("workDairyTO.getApprStatus() " + workDairyTO.getApprStatus());
        if (CommonUtil.isNotBlankStr(workDairyTO.getApprStatus())) {
            if (workDairyTO.getApprStatus().equals("Approved")
                    || workDairyTO.getApprStatus().equals("SubmittedForApproval")) {
                List<WorkDairyEmpCostEntity> workDairyEmpCostEntites = empCostWorkDairyRepository
                        .findWorkDairyEmpTimeCost(workDairyTO.getId(), workDairyTO.getStatus());
                List<WorkDairyEmpCostEntity> copyWorkDairyEmpCostEntites = new ArrayList<WorkDairyEmpCostEntity>();
                if (CommonUtil.isListHasData(workDairyEmpCostEntites)) {
                    for (WorkDairyEmpCostEntity workDairyEmpCostEntity : workDairyEmpCostEntites) {
                        workDairyEmpCostEntity.setIsLatest(Boolean.FALSE);
                        copyWorkDairyEmpCostEntites.add(workDairyEmpCostEntity);
                    }
                    empCostWorkDairyRepository.save(copyWorkDairyEmpCostEntites);
                }
            }
        }
        
        if (CommonUtil.isBlankStr(workDairyTO.getApprStatus())) {
            AppResp appResp = validateEmpBookedHrs(workDairyEmpDtlTOs, workDairyTO);
            log.info("validateEmpBookedHrs appResp " + appResp);
            if (appResp != null) {
                return appResp;
            }
        }
        log.info("workDairyEmpDtlTOs size " + workDairyEmpDtlTOs.size());
        for (WorkDairyEmpDtlTO workDairyEmpDtlTO : workDairyEmpDtlTOs) {
        	log.info("======================================");
            EmpWorkDairyHandler.saveEmpWageStatus(workDairyTO, workDairyEmpDtlEntities, workDairyEmpDtlTO,
                    workDairyRepository, projCostItemRepository, empRegisterRepository, empWageRepository);
        }
        empWorkDairyRepository.save(workDairyEmpDtlEntities);
        return null;
    }

    public AppResp saveWorkDairyPlantDetails(WorkDairyPlantSaveReq workDairyPlantSaveReq) {
        AppResp appResp = savePlantDetails(workDairyPlantSaveReq.getWorkDairyPlantDtlTOs(),
                workDairyPlantSaveReq.getWorkDairyTO());
        if (CommonUtil.objectNotNull(appResp)) {
            return appResp;
        }
        return null;
    }

    private AppResp savePlantDetails(List<WorkDairyPlantDtlTO> workDairyPlantDtlTOs, WorkDairyTO workDairyTO) {
        List<WorkDairyPlantDtlEntity> workDairyPlantDtlEntities = new ArrayList<>();
        if (CommonUtil.isBlankStr(workDairyTO.getApprStatus())) {
            AppResp flag = validatePlantBookedHrs(workDairyPlantDtlTOs, workDairyTO);
            if (flag != null)
                return flag;
        }
        for (WorkDairyPlantDtlTO workDairyPlantDtlTO : workDairyPlantDtlTOs) {
            PlantWorkDairyHandler.savePlantStatus(workDairyTO, workDairyPlantDtlEntities, workDairyPlantDtlTO,
                    workDairyRepository, projCostItemRepository, plantRegisterRepository, loginRepository);
        }

        if (CommonUtil.isNotBlankStr(workDairyTO.getApprStatus())) {
            if (workDairyTO.getApprStatus().equals("Approved")
                    || workDairyTO.getApprStatus().equals("SubmittedForApproval")) {
                List<WorkDairyPlantCostEntity> workDairyPlantCostEntities = plantCostWorkDairyRepository
                        .findLatestWorkDairyCostDetails(workDairyTO.getId(), workDairyTO.getStatus());
                List<WorkDairyPlantCostEntity> copyWorkDairyEmpCostEntities = new ArrayList<WorkDairyPlantCostEntity>();
                if (CommonUtil.isListHasData(workDairyPlantCostEntities)) {
                    for (WorkDairyPlantCostEntity workDairyPlantCostEntity : workDairyPlantCostEntities) {
                        workDairyPlantCostEntity.setIsLatest(false);
                        copyWorkDairyEmpCostEntities.add(workDairyPlantCostEntity);
                    }
                    plantCostWorkDairyRepository.save(copyWorkDairyEmpCostEntities);
                }
            }
        }
        plantWorkDairyRepository.save(workDairyPlantDtlEntities);
        return null;
    }

    public void saveWorkDairyMaterialDetails(WorkDairyMaterialSaveReq workDairyMaterialSaveReq) {
        saveMaterialDetails(workDairyMaterialSaveReq.getWorkDairyMaterialDtlTOs(),
                workDairyMaterialSaveReq.getWorkDairyTO());
    }

    private void saveMaterialDetails(List<WorkDairyMaterialDtlTO> workDairyMaterialDtlTOs, WorkDairyTO workDairyTO) {
        List<WorkDairyMaterialDtlEntity> workDairyMaterialDtlEntities = new ArrayList<WorkDairyMaterialDtlEntity>();
        List<MaterialPODeliveryDocketEntity> poDeliveryDocketEntities = new ArrayList<>();
        List<MaterialProjDocketSchItemsEntity> projDocketSchItemsEntities = new ArrayList<>();
        Map<Long, BigDecimal> deliveryDocketConsumptionMap = new HashMap<>();
        Map<Long, BigDecimal> projDocketSchConsumptionMap = new HashMap<>();

        for (WorkDairyMaterialDtlTO workDairyMaterialDtlTO : workDairyMaterialDtlTOs) {
            populateMaterialWorkDairy(workDairyTO, workDairyMaterialDtlEntities, workDairyMaterialDtlTO,
                    poDeliveryDocketEntities, projDocketSchItemsEntities, deliveryDocketConsumptionMap,
                    projDocketSchConsumptionMap);
        }

        materialWorkDairyRepository.save(workDairyMaterialDtlEntities);
    }

    private void populateMaterialWorkDairy(WorkDairyTO workDairyTO,
            List<WorkDairyMaterialDtlEntity> workDairyMaterialDtlEntities,
            WorkDairyMaterialDtlTO workDairyMaterialDtlTO,
            List<MaterialPODeliveryDocketEntity> poDeliveryDocketEntities,
            List<MaterialProjDocketSchItemsEntity> projDocketSchItemsEntities,
            Map<Long, BigDecimal> deliveryDocketConsumptionMap, Map<Long, BigDecimal> projDocketSchConsumptionMap) {
        WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity = null;
        workDairyMaterialDtlTO.setWorkDairyId(workDairyTO.getId());
        double total = 0;
        workDairyMaterialDtlEntity = MaterialWorkDairyHandler.convertPOJOToEntity(workDairyMaterialDtlTO,
                materialDockSchItemRepository, materialWorkDairyRepository);

        for (WorkDairyMaterialStatusTO workDairyMaterialStatusTO : workDairyMaterialDtlTO
                .getWorkDairyMaterialStatusTOs()) {
            total = total + workDairyMaterialStatusTO.getTotal();
            MaterialWorkDairyHandler.poupluateMaterialStatus(workDairyTO, workDairyMaterialDtlEntity,
                    workDairyMaterialStatusTO, workDairyRepository, projCostItemRepository, loginRepository);
        }

        workDairyMaterialDtlEntity.setConsumptionQty(new BigDecimal(total));
        workDairyMaterialDtlTO.setTotalConsumpiton(total);
        if (workDairyMaterialDtlEntity.getProjDocketSchId() != null) {
            workDairyMaterialDtlEntity.getProjDocketSchId()
                    .setAvailableQty(workDairyMaterialDtlEntity.getClosingStock());
            workDairyMaterialDtlEntity.getProjDocketSchId().setWorkDairyEntry(true);
        }

        modifyConsumptionValues(workDairyMaterialDtlTO, workDairyTO);

        List<MaterialProjDtlTO> materialProjDtlTOs = workDairyMaterialDtlTO.getMaterialProjDtlTO();

        for (MaterialProjDtlTO materialProjDtlTO : materialProjDtlTOs) {
            if (!workDairyMaterialDtlTO.isConsumpitonUpdated()) {
                materialProjDtlTO.setWorkDairyCunsumptionQty(new BigDecimal(total));
            }
            if (CommonConstants.DELIVERY_DOCKET_TYPE.equalsIgnoreCase(workDairyMaterialDtlTO.getDocketType())) {
                MaterialProjDtlEntity materialProjDtlEntityCopy = materialProjRepository
                        .findOne(materialProjDtlTO.getMapId());
                if (CommonUtil.isNonBlankLong(workDairyMaterialDtlTO.getId())) {
                    WorkDairyMaterialDtlEntity tempWorkDairyMaterialDtlEntity = materialWorkDairyRepository
                            .findOne(workDairyMaterialDtlTO.getId());
                    workDairyMaterialDtlEntity.getMaterialProjDtlEntity()
                            .addAll(tempWorkDairyMaterialDtlEntity.getMaterialProjDtlEntity());
                    workDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity()
                            .addAll(tempWorkDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity());
                }

                if (!workDairyMaterialDtlEntity.getMaterialProjDtlEntity().stream()
                        .filter(o -> o.getId().equals(materialProjDtlTO.getMapId())).findFirst().isPresent()) {
                    workDairyMaterialDtlEntity.getMaterialProjDtlEntity().add(materialProjDtlEntityCopy);
                }
                if (workDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity().isEmpty())
                    workDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity()
                            .addAll(materialProjDtlEntityCopy.getMaterialPODeliveryDocketEntities());
                else {
                    final List<MaterialPODeliveryDocketEntity> workDairyMaterialPo = workDairyMaterialDtlEntity
                            .getMaterialPODeliveryDocketEntity();
                    List<MaterialPODeliveryDocketEntity> materialProjMaterialPoList = materialProjDtlEntityCopy
                            .getMaterialPODeliveryDocketEntities();
                    final List<MaterialPODeliveryDocketEntity> filteredList = new ArrayList<MaterialPODeliveryDocketEntity>();
                    materialProjMaterialPoList.forEach(materialPo -> {
                        if (!workDairyMaterialPo.stream().filter(o -> o.getId().equals(materialPo.getId())).findFirst()
                                .isPresent()) {
                            filteredList.add(materialPo);
                        }
                    });
                    workDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity().addAll(filteredList);
                }

            }

            if (workDairyMaterialDtlEntity.isSupplierDocket()) {
                log.info("Inside Supplier Docket");
                MaterialProjDtlEntity materialProjDtl = new MaterialProjDtlEntity();
                materialProjDtl.setId(materialProjDtlTO.getMapId());
                List<MaterialProjDtlEntity> materialProjDtlList = new ArrayList<>();
                LabelKeyTO labelKeyTo = materialProjDtlTO.getPurchaseSchLabelKeyTO();
                Map<String, String> materialSchItems = labelKeyTo.getDisplayNamesMap();
                if (CommonUtil.isNonBlankLong(Long.valueOf(materialSchItems.get("cmpId")))) {
                    CompanyMstrEntity companyMstrEntity = companyRepository
                            .findOne(Long.valueOf(materialSchItems.get("cmpId")));
                    materialProjDtl.setCompanyMstrEntity(companyMstrEntity);
                }
                ProjMstrEntity projMstrEntity = epsProjRepository.findOne(workDairyTO.getProjId());
                if (null != projMstrEntity) {
                    materialProjDtl.setProjId(projMstrEntity);
                }
                PurchaseOrderEntity copyPurchaseOrderEntity = purchaseOrderRepositoryCopy
                        .findOne(Long.valueOf(materialSchItems.get("purId")));
                if (null != copyPurchaseOrderEntity) {
                    materialProjDtl.setPurchaseId(copyPurchaseOrderEntity);
                }

                long cmpId = Long.parseLong(materialSchItems.get("schItemCmpId"));
                PreContractsMaterialCmpEntity preContractMaterialCmp = preContractMaterialCmpRepository
                        .findOne(cmpId);
                PreContractsMaterialDtlEntity copyPreContractsMaterialDtlEntity = preContractMaterialCmp
                        .getPreContractsMaterialDtlEntity();
                if (null != copyPreContractsMaterialDtlEntity) {
                    materialProjDtl.setPreContractMterialId(copyPreContractsMaterialDtlEntity);
                }
                materialProjDtl.setPreContractMaterialName(labelKeyTo.getName());
                materialProjDtl.setPreContractMaterialSchId(labelKeyTo.getId());
                materialProjDtl.setPreContractMaterialSchCode(labelKeyTo.getCode());
               
                if (materialProjDtlTO.getMapId() == null) {
                    updatePreContractMaterialCmp(preContractMaterialCmp, workDairyMaterialDtlTO.getReceivedQty());
                }
                PreContractsMaterialCmpEntity preContractsMaterialCmpEntity = preContractMaterialCmpRepository
                        .findOne(cmpId);
                materialProjDtl.setPreContractsMaterialCmpEntity(preContractsMaterialCmpEntity);
                materialProjDtl.setQuantity(BigDecimal.valueOf(Long.valueOf(materialSchItems.get("qty"))));
                materialProjDtl.setRate(BigDecimal.valueOf(Double.valueOf(materialSchItems.get("unitOfRate"))));
                MaterialClassMstrEntity materialClassMstrEntity = materialClassRepository
                        .findOne(Long.valueOf(materialSchItems.get("purClassId")));
                if (null != materialClassMstrEntity) {
                    materialProjDtl.setMaterialClassId(materialClassMstrEntity);
                }
                materialProjDtl.setStatus(1);
                try {
                    if (materialSchItems.get("purFinishDate") != null)
                        materialProjDtl.setSupplyDeliveryDate(
                                new SimpleDateFormat("dd-MMM-yyyy").parse(materialSchItems.get("purFinishDate")));
                } catch (ParseException e) {
                    try {
                        materialProjDtl.setSupplyDeliveryDate(
                                new SimpleDateFormat("dd-MM-yyyy").parse(materialSchItems.get("purFinishDate")));
                    } catch (ParseException e1) {
                        log.error("Parse Exception ", e);
                    }
                }
                materialProjDtl = materialProjRepository.save(materialProjDtl);
                materialProjDtl = materialProjRepository.findOne(materialProjDtl.getId());
                materialProjDtlList.add(materialProjDtl);
                workDairyMaterialDtlEntity.setMaterialProjDtlEntity(materialProjDtlList);

                List<MaterialPODeliveryDocketTO> materialPoDelDocTos = materialProjDtlTO
                        .getMaterialPODeliveryDocketTOs();
                for (MaterialPODeliveryDocketTO materialPoDelDocTo : materialPoDelDocTos) {
                    MaterialPODeliveryDocketEntity materialPoDeliveryDocketEntity = new MaterialPODeliveryDocketEntity();
                    if (materialPoDelDocTo.getId() != null)
                        materialPoDeliveryDocketEntity = materialDeliveryDocketRepositoryCopy
                                .findOne(materialPoDelDocTo.getId());
                    materialPoDeliveryDocketEntity.setDefectComments(materialPoDelDocTo.getDefectComments());
                    if (materialPoDelDocTo.getDocketDate().length() > 0) {
                        try {
                            Date docketDate = new SimpleDateFormat("dd-MMM-yyyy")
                                    .parse(materialPoDelDocTo.getDocketDate());
                            materialPoDeliveryDocketEntity.setDocketDate(docketDate);
                            materialPoDeliveryDocketEntity.setSupplyDeliveryDate(docketDate);
                        } catch (ParseException e) {
                            try {
                                Date docketDate = new SimpleDateFormat("dd-MM-yyyy")
                                        .parse(materialPoDelDocTo.getDocketDate());
                                materialPoDeliveryDocketEntity.setDocketDate(docketDate);
                                materialPoDeliveryDocketEntity.setSupplyDeliveryDate(docketDate);
                            } catch (ParseException e1) {
                                log.error("Parse Exception ", e);
                            }
                        }
                    }

                    materialPoDeliveryDocketEntity.setDocketNumber(materialPoDelDocTo.getDocketNumber());
                    materialPoDeliveryDocketEntity.setReceivedQty(materialPoDelDocTo.getReceivedQty());
                    materialPoDeliveryDocketEntity.setStatus(materialPoDelDocTo.getStatus());
                    materialPoDeliveryDocketEntity.setMaterialProjDtlEntity(materialProjDtl);
                    materialPoDeliveryDocketEntity.setSupplierDocket(true);
                    if (materialPoDelDocTo.getStockLabelKeyTO().getId() != null)
                        materialPoDeliveryDocketEntity.setStockId(
                                stockRepository.findOne(Long.valueOf(materialPoDelDocTo.getStockLabelKeyTO().getId())));
                    else if (materialPoDelDocTo.getProjStockLabelKeyTO().getId() != null)
                        materialPoDeliveryDocketEntity.setProjStockId(projStoreStockRepository
                                .findOne(Long.valueOf(materialPoDelDocTo.getProjStockLabelKeyTO().getId())));

                    MaterialProjDocketSchItemsEntity materialProjDocSchItemsEntity = new MaterialProjDocketSchItemsEntity();
                    if (materialPoDelDocTo.getProjDocketSchId() != null)
                        materialProjDocSchItemsEntity = materialDockSchItemRepository
                                .findOne(materialPoDelDocTo.getProjDocketSchId());
                    materialProjDocSchItemsEntity.setComments(workDairyMaterialDtlEntity.getComments());
                    materialProjDocSchItemsEntity.setMaterialProjDtlEntity(materialProjDtl);
                    materialProjDocSchItemsEntity.setStatus(1);
                    if (CommonUtil.isNonBlankLong(materialProjDtl.getId())) {
                        MaterialProjDtlEntity materialProjDtlEntity2 = materialProjRepository
                                .findOne(materialProjDtl.getId());
                        materialProjDocSchItemsEntity.setMaterialProjDtlEntity(materialProjDtlEntity2);
                    }
                    materialProjDocSchItemsEntity.setToProjMaterialProjDtlEntity(materialProjDtl);
                    MaterialProjDtlEntity materialDtlEntity = materialProjRepository
                            .findOne(materialProjDtl.getId());
                    if (null != materialDtlEntity) {
                        materialProjDocSchItemsEntity.setMaterialProjDtlEntity(materialDtlEntity);
                    }
                    materialProjDocSchItemsEntity.setQty(BigDecimal.valueOf(Long.valueOf(materialSchItems.get("qty"))));
                    materialProjDocSchItemsEntity.setSupplierDocket(true);
                    materialProjDocSchItemsEntity.setAvailableQty(workDairyMaterialDtlEntity.getClosingStock());
                    materialProjDocSchItemsEntity.setWorkDairyEntry(true);
                    workDairyMaterialDtlEntity.setProjDocketSchId(materialProjDocSchItemsEntity);
                    materialProjDocSchItemsEntity = materialDockSchItemRepository.save(materialProjDocSchItemsEntity);
                    materialPoDeliveryDocketEntity.setProjDocketSchId(materialProjDocSchItemsEntity.getId());
                    materialPoDeliveryDocketEntity = materialDeliveryDocketRepositoryCopy
                            .save(materialPoDeliveryDocketEntity);
                    List<MaterialPODeliveryDocketEntity> materialPoDeliveryDocketEntites = workDairyMaterialDtlEntity
                            .getMaterialPODeliveryDocketEntity();
                    if (materialPoDeliveryDocketEntites.isEmpty()) {
                        poDeliveryDocketEntities.add(materialPoDeliveryDocketEntity);
                        workDairyMaterialDtlEntity.setMaterialPODeliveryDocketEntity(poDeliveryDocketEntities);
                    } else {
                        final Long docketId = materialPoDeliveryDocketEntity.getId();
                        Optional<MaterialPODeliveryDocketEntity> materialPoOptional = materialPoDeliveryDocketEntites
                                .stream().filter(o -> o.getId().equals(docketId)).findAny();
                        MaterialPODeliveryDocketEntity materialPoDeliveryDocket = null;
                        if (materialPoOptional.isPresent()) {
                            materialPoDeliveryDocket = materialPoOptional.get();
                        }

                        if (materialPoDeliveryDocket == null) {
                            workDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity()
                                    .add(materialPoDeliveryDocketEntity);
                        } else {
                            workDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity()
                                    .remove(materialPoDeliveryDocketEntites.indexOf(materialPoDeliveryDocket));
                            workDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity()
                                    .add(materialPoDeliveryDocketEntity);
                        }
                    }
                    projDocketSchItemsEntities.add(materialProjDocSchItemsEntity);
                }
            }
            workDairyMaterialDtlEntities.add(workDairyMaterialDtlEntity);
        }
    }

    private void modifyConsumptionValues(WorkDairyMaterialDtlTO workDairyMaterialDtlTO, WorkDairyTO workDairyTo) {
        if (workDairyMaterialDtlTO.getProjDocketSchId() != null) {
            double closingStock = workDairyMaterialDtlTO.getClosingStock();
            MaterialProjDocketSchItemsEntity materialProjDockSch = materialDockSchItemRepository
                    .findOne(workDairyMaterialDtlTO.getProjDocketSchId());
            List<WorkDairyMaterialDtlEntity> workDairyMaterialDtlEntities = materialWorkDairyRepository
                    .findWorkDairyMaterialDetails(materialProjDockSch,
                            workDairyRepository.findOne(workDairyTo.getId()).getWorkDairyDate());
            workDairyMaterialDtlEntities.forEach(workDairyMaterial -> {
                workDairyMaterial.setOpeningStock(BigDecimal.valueOf(closingStock));
                workDairyMaterial.setClosingStock(
                        workDairyMaterial.getOpeningStock().subtract(workDairyMaterial.getConsumptionQty()));
            });
            if (!workDairyMaterialDtlEntities.isEmpty()) {
                materialProjDockSch.setAvailableQty(
                        workDairyMaterialDtlEntities.get(workDairyMaterialDtlEntities.size() - 1).getClosingStock());
                materialProjDockSch.setWorkDairyEntry(true);
            }

            materialDockSchItemRepository.save(materialProjDockSch);
            materialWorkDairyRepository.save(workDairyMaterialDtlEntities);
        }
    }

    private void updatePreContractMaterialCmp(PreContractsMaterialCmpEntity preContractMaterialCmp,
            BigDecimal recievedQty) {
        long calcRecievedQty = 0;
        if (preContractMaterialCmp.getRecievedQty() != null) {
            calcRecievedQty = preContractMaterialCmp.getRecievedQty() + Long.valueOf(recievedQty.toString());
        } else {
            calcRecievedQty = Long.valueOf(recievedQty.toString());
        }
        preContractMaterialCmp.setRecievedQty(calcRecievedQty);
        preContractMaterialCmpRepository.save(preContractMaterialCmp);
    }

    public MaterialProjDtlEntity populateProjMaterialEntity(MaterialProjDtlTO materialProjDtlTO,
            MaterialPODeliveryDocketEntity materialPODeliveryDocketEntity) {
        MaterialProjDtlEntity materialProjDtlEntity = MaterialProjDtlHandlerCopy.convertPOJOToEntity(
                materialProjDtlTO, epsProjRepository, precontractMaterialRepositoryCopy, materialClassRepository,
                purchaseOrderRepositoryCopy, companyRepository, preContractMaterialCmpRepository);
        MaterialProjLedgerEntity materialProjLedgerEntity = createProjMaterialLedger(materialProjDtlEntity,
                materialProjDtlTO);
        materialProjLedgerEntity.setMaterialPODeliveryDocketEntity(materialPODeliveryDocketEntity);
        materialProjDtlEntity.getMaterialProjLedgerEntities().add(materialProjLedgerEntity);
        return materialProjDtlEntity;
    }

    private MaterialProjLedgerEntity createProjMaterialLedger(MaterialProjDtlEntity materialProjDtlEntity,
            MaterialProjDtlTO materialProjDtlTO) {
        MaterialProjLedgerEntity materialProjLedgerEntity = new MaterialProjLedgerEntity();
        materialProjLedgerEntity.setMaterialProjDtlEntity(materialProjDtlEntity);
        materialProjLedgerEntity.setCredit(materialProjDtlTO.getIssueQty().setScale(4, RoundingMode.UP));
        materialProjLedgerEntity.setDebit(materialProjDtlTO.getWorkDairyCunsumptionQty().setScale(4, RoundingMode.UP));
        materialProjLedgerEntity.setOpenBalance(new BigDecimal(0));
        materialProjLedgerEntity.setAvailableBalance(new BigDecimal(0));
        materialProjLedgerEntity.setTransit(materialProjDtlTO.getIssueQty()
                .subtract(materialProjDtlTO.getWorkDairyCunsumptionQty().setScale(4, RoundingMode.UP)));
        materialProjLedgerEntity.setDocketTrasnitQty(materialProjDtlTO.getIssueQty()
                .subtract(materialProjDtlTO.getWorkDairyCunsumptionQty().setScale(4, RoundingMode.UP)));
        materialProjLedgerEntity.setDocketLatest(RegisterCommonUtils.IS_LATEST_Y);
        materialProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_Y);
        materialProjLedgerEntity.setTotal(materialProjDtlTO.getIssueQty()
                .subtract(materialProjDtlTO.getWorkDairyCunsumptionQty().setScale(4, RoundingMode.UP)));
        materialProjLedgerEntity.setStatus(StatusCodes.ACTIVE.getValue());
        materialProjLedgerEntity.setEffectiveDate(new Date());
        materialProjLedgerEntity.setDocketType(CommonConstants.DELIVERY_DOCKET_TYPE);
        return materialProjLedgerEntity;
    }

    public void saveWorkDairyProgressDetails( MultipartFile[] files, WorkDairyProgressSaveReq workDairyProgressSaveReq ) throws IOException {
        /*saveProgressDetails(workDairyProgressSaveReq.getWorkDairyProgressDtlTOs(),
                workDairyProgressSaveReq.getWorkDairyTO());*/
    	System.out.println("saveWorkDairyProgressDetails function from WorkDairyServiceImpl class");
    	List<WorkDairyProgressDtlEntity> workDairyProgressDtlEntities = new ArrayList<>();
    	//List<WorkDairyProgressDtlTO> workDairyProgressDtlTOs = workDairyProgressSaveReq.getWorkDairyProgressDtlTOs();
    	WorkDairyTO workDairyTO = workDairyProgressSaveReq.getWorkDairyTO();
    	ProjDocFolderEntity projDocFolderEntity = null;
    	ProjDocFileEntity projDocFileEntity = null;
    	ProjDocFileEntity resProjDocFileEntity = null;
    	UserMstrEntity userMstrEntity = null;
    	
        for( WorkDairyProgressDtlTO workDairyProgressDtlTO : workDairyProgressSaveReq.getWorkDairyProgressDtlTOs() ) {
        	Integer fileIndex = workDairyProgressDtlTO.getFileObjectIndex();
        	UploadUtil uploadUtil = new UploadUtil();
        	userMstrEntity = new UserMstrEntity();
        	userMstrEntity.setUserId( workDairyProgressSaveReq.getUserId() );
        	ProjMstrEntity projMstrEntity = new ProjMstrEntity();
        	
			if ( fileIndex != null
					&& files[fileIndex].getOriginalFilename().equalsIgnoreCase( workDairyProgressDtlTO.getFileName() ) ) {
				ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( workDairyProgressSaveReq.getFolderCategory() );
				System.out.println(folder);
				projDocFileEntity = new ProjDocFileEntity();
				projDocFolderEntity = new ProjDocFolderEntity();
				resProjDocFileEntity = new ProjDocFileEntity();
				//EmpProjRigisterEntity empProjRigisterEntity =  empProjRegisterRepository.findOne(workDairyTO.getProjId());
				//projDocFolderEntity.setId( folder.getId() );
				projMstrEntity.setProjectId( workDairyTO.getProjId() );
				
				projDocFileEntity.setName( workDairyProgressDtlTO.getFileName() );
				projDocFileEntity.setFolderId( folder );
				projDocFileEntity.setFileSize( uploadUtil.bytesIntoHumanReadable( files[fileIndex].getSize() ) );
				projDocFileEntity.setFileType( files[fileIndex].getContentType() );
				projDocFileEntity.setProjectId( projMstrEntity );		
				projDocFileEntity.setCreatedBy( userMstrEntity );
				projDocFileEntity.setUpdatedBy( userMstrEntity );
				projDocFileEntity.setFolderPath( "/"+String.valueOf( workDairyTO.getProjId() )+"/"+ String.valueOf( workDairyTO.getId() ) + "/" + String.valueOf( workDairyTO.getCrewId() ) );
				projDocFileEntity.setStatus( 1 );
				resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
				
				if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
				{
					System.out.println("if block of upload condition");
					String extras[] = { String.valueOf( workDairyTO.getProjId() ), String.valueOf( workDairyTO.getId() ), String.valueOf( workDairyTO.getCrewId() ) };
					// Upload pre-contract docs to server				
					uploadUtil.uploadFile( files[fileIndex], folder.getName(), folder.getUploadFolder(), extras );
				}
				//workDairyProgressDtlTO.setProjDocFile( resProjDocFileEntity );
			}
            ProgressWorkDairyHandler.saveProgressStatus( workDairyTO, workDairyProgressDtlEntities,
                    workDairyProgressDtlTO, workDairyRepository, projSOWItemRepository, projCostItemRepository, resProjDocFileEntity );
        }
        progressWorkDairyRepository.save(workDairyProgressDtlEntities);
        
        for (WorkDairyProgressDtlEntity workDairyProgressDtlEntity : workDairyProgressDtlEntities) {
        	WorkDairyEntity workDairyEntity = workDairyRepository.findOne(workDairyProgressDtlEntity.getWorkDairyId().getId());
        	if (workDairyEntity.isClientApproval()) {
        		if (workDairyEntity.getApprStatus() != null && workDairyEntity.getApprStatus().equalsIgnoreCase(TimeManagentStatus.CLIENT_APPROVED.getName())) {
        			ProjSOWItemEntity projSOWItemEntityCopy = projSOWItemRepositoryCopy.findOne(workDairyProgressDtlEntity.getSowId().getId());
        			projSOWItemEntityCopy.setActualQty(BigDecimal.valueOf(workDairyProgressDtlEntity.getValue()));
        			projSOWItemRepositoryCopy.save(projSOWItemEntityCopy);
        		}
        	} else {
        		if (workDairyEntity.getApprStatus() != null && workDairyEntity.getApprStatus().equalsIgnoreCase(TimeManagentStatus.APPROVED.getName())) {
        			ProjSOWItemEntity projSOWItemEntityCopy = projSOWItemRepositoryCopy.findOne(workDairyProgressDtlEntity.getSowId().getId());
        			projSOWItemEntityCopy.setActualQty(BigDecimal.valueOf(workDairyProgressDtlEntity.getValue()));
        			projSOWItemRepositoryCopy.save(projSOWItemEntityCopy);
        		}
        	}
        }
    }

    private void saveProgressDetails(List<WorkDairyProgressDtlTO> workDairyProgressDtlTOs, WorkDairyTO workDairyTO) {
        List<WorkDairyProgressDtlEntity> workDairyProgressDtlEntities = new ArrayList<>();
        for (WorkDairyProgressDtlTO workDairyProgressDtlTO : workDairyProgressDtlTOs) {
            ProgressWorkDairyHandler.saveProgressStatus(workDairyTO, workDairyProgressDtlEntities,
                    workDairyProgressDtlTO, workDairyRepository, projSOWItemRepository, projCostItemRepository, null);
        }
        progressWorkDairyRepository.save(workDairyProgressDtlEntities);
        
        for (WorkDairyProgressDtlEntity workDairyProgressDtlEntity : workDairyProgressDtlEntities) {
        	WorkDairyEntity workDairyEntity = workDairyRepository.findOne(workDairyProgressDtlEntity.getWorkDairyId().getId());
        	if (workDairyEntity.isClientApproval()) {
        		if (workDairyEntity.getApprStatus() != null && workDairyEntity.getApprStatus().equalsIgnoreCase(TimeManagentStatus.CLIENT_APPROVED.getName())) {
        			ProjSOWItemEntity projSOWItemEntityCopy = projSOWItemRepositoryCopy.findOne(workDairyProgressDtlEntity.getSowId().getId());
        			projSOWItemEntityCopy.setActualQty(BigDecimal.valueOf(workDairyProgressDtlEntity.getValue()));
        			projSOWItemRepositoryCopy.save(projSOWItemEntityCopy);
        		}
        	} else {
        		if (workDairyEntity.getApprStatus() != null && workDairyEntity.getApprStatus().equalsIgnoreCase(TimeManagentStatus.APPROVED.getName())) {
        			ProjSOWItemEntity projSOWItemEntityCopy = projSOWItemRepositoryCopy.findOne(workDairyProgressDtlEntity.getSowId().getId());
        			projSOWItemEntityCopy.setActualQty(BigDecimal.valueOf(workDairyProgressDtlEntity.getValue()));
        			projSOWItemRepositoryCopy.save(projSOWItemEntityCopy);
        		}
        	}
        }
    }
    
    public void saveMoreSowCostCodes(WorkDairyProgressSaveReq workDairyCostCodeSaveReq) {
        List<WorkDairyProgressDtlEntity> workDairyProgressDtlEntities = new ArrayList<WorkDairyProgressDtlEntity>();
        for (WorkDairyProgressDtlTO workDairyProgressDtlTO : workDairyCostCodeSaveReq.getWorkDairyProgressDtlTOs()) {
            ProgressWorkDairyHandler.saveProgressStatus(workDairyCostCodeSaveReq.getWorkDairyTO(),
                    workDairyProgressDtlEntities, workDairyProgressDtlTO, workDairyRepository, projSOWItemRepository,
                    projCostItemRepository, null);
        }
        progressWorkDairyRepository.save(workDairyProgressDtlEntities);
        saveWorkDairyCostCodes(workDairyCostCodeSaveReq.getWorkDairyCostCodeSaveReq());
    }


    private AppResp validateEmpBookedHrs(List<WorkDairyEmpDtlTO> workDairyEmpDtlTOs, WorkDairyTO workDairyTO) {
        AppResp appResp = new AppResp();
        List<EmpRegisterDtlEntity> empRegList = new ArrayList<EmpRegisterDtlEntity>();
        workDairyEmpDtlTOs.forEach((workDairyEmp) -> {
            empRegList.add(empRegisterDtlRepository.findOne(workDairyEmp.getEmpRegId()));
        });

        Map<Long, Double> bookedHrsMap = populateEmpBookedHrsMap(empRegList, workDairyTO);
        Double currentBookedHrs = 0.0;
        Double bookedHrs = 0.0;

        for (WorkDairyEmpDtlTO workDairyEmpDtlTO : workDairyEmpDtlTOs) {
            currentBookedHrs = 0.0;
            bookedHrs = 0.0;
            for (WorkDairyEmpWageTO workDairyEmpWageTO : workDairyEmpDtlTO.getWorkDairyEmpWageTOs()) {
                for (WorkDairyEmpCostDtlTO workDairyUsedCostDtlTO : workDairyEmpWageTO.getWorkDairyEmpCostDtlTOs()) {
                    currentBookedHrs = currentBookedHrs + workDairyUsedCostDtlTO.getUsedTime()
                            + workDairyUsedCostDtlTO.getIdleTime();
                }
            }
            bookedHrs = bookedHrsMap.get(workDairyEmpDtlTO.getEmpRegId());
            if (bookedHrs == null) {
                bookedHrs = 0.0;
            }
            bookedHrs = workDairyTO.getEmpMaxHrs() - bookedHrs;
            if (currentBookedHrs > bookedHrs) {
                EmpRegisterDtlEntity empReg = empRegisterDtlRepository.findOne(workDairyEmpDtlTO.getEmpRegId());
                String message = null;
                if (bookedHrs > 0)
                    message = "Cannot create workdairy, as hours added for employee " + empReg.getCode()
                            + ", cannot exceed " + bookedHrs + "hours.";
                else
                    message = "Cannot create workdairy, as employee, " + empReg.getCode()
                            + " has no hours left for the day.";
                appResp.setMessage(message);
                appResp.setStatus("Warning");
                return appResp;
            }

        }
        return null;
    }

    public Map<Long, Double> populateEmpBookedHrsMap(List<EmpRegisterDtlEntity> empRegList,
            WorkDairyTO workDairyTO) {
        Map<Long, Double> empBookedHrsMap = new HashMap<Long, Double>();
        Date workDairyDate = CommonUtil.convertStringToDate(workDairyTO.getWorkDairyDate());
        List<TimeSheetEmpWorkDtlEntity> timeSheetEmpList = timeSheetEmpDtlRepository
                .getEmpTimesheetHrsForTheDay(empRegList, workDairyDate);
        timeSheetEmpList.forEach((timesheetEmp) -> {
            Date weekStartDate = timesheetEmp.getTimeSheetEmpDtlEntity().getTimeSheetEntity().getWeekStartDate();
            Instant startDate = new Instant(weekStartDate);
            Instant endDate = new Instant(workDairyDate);
            Interval interval = new Interval(startDate, endDate);
            int diffInDays = (int) interval.toDuration().getStandardDays();
            Double hours = 0.0;
            switch (diffInDays) {
                case 0:
                    hours = timesheetEmp.getDay1();
                    break;
                case 1:
                    hours = timesheetEmp.getDay2();
                    break;
                case 2:
                    hours = timesheetEmp.getDay3();
                    break;
                case 3:
                    hours = timesheetEmp.getDay4();
                    break;
                case 4:
                    hours = timesheetEmp.getDay5();
                    break;
                case 5:
                    hours = timesheetEmp.getDay6();
                    break;
                case 6:
                    hours = timesheetEmp.getDay7();
                    break;
            }
            empBookedHrsMap.put(timesheetEmp.getTimeSheetEmpDtlEntity().getEmpRegisterDtlEntity().getId(), hours);
        });

        List<Object[]> empWorkDairy = empWorkDairyRepository.getBookedHoursForOtherCrew(
                epsProjRepository.findOne(workDairyTO.getProjId()),
                projCrewListRepository.findOne(workDairyTO.getCrewId()), workDairyDate, empRegList);
        empWorkDairy.forEach((empWd) -> {
            Long empId = (Long) empWd[0];
            Double hours = (Double) empWd[1];
            if (empBookedHrsMap.get(empId) != null) {
                hours = empBookedHrsMap.get(empId) + hours;
                empBookedHrsMap.put(empId, hours);
            } else {
                empBookedHrsMap.put(empId, hours);
            }
        });
        return empBookedHrsMap;
    }

    private AppResp validatePlantBookedHrs(List<WorkDairyPlantDtlTO> workDairyPlantDtlTOs, WorkDairyTO workDairyTO) {
        AppResp appResp = new AppResp();
        List<PlantRegisterDtlEntity> plantReg = new ArrayList<PlantRegisterDtlEntity>();
        workDairyPlantDtlTOs.forEach((workDairyPlant) -> {
            plantReg.add(plantRegisterRepository.findOne(workDairyPlant.getPlantRegId()));
        });

        Map<Long, Double> bookedHrsMap = populatePlantBookedHrsMap(workDairyTO, plantReg);
        Double currentBookedHrs = 0.0;
        Double bookedHrs = 0.0;
        for (WorkDairyPlantDtlTO workDairyPlantDtlTO : workDairyPlantDtlTOs) {
            currentBookedHrs = 0.0;
            bookedHrs = 0.0;
            for (WorkDairyPlantStatusTO workDairyPlantStatusTO : workDairyPlantDtlTO.getWorkDairyPlantStatusTOs()) {
                for (WorkDairyPlantCostDtlTO workDairyPlantCostDtlTO : workDairyPlantStatusTO
                        .getWorkDairyPlantCostDtlTOs()) {
                    currentBookedHrs = currentBookedHrs + workDairyPlantCostDtlTO.getUsedTime()
                            + workDairyPlantCostDtlTO.getIdleTime();
                }
            }
            bookedHrs = bookedHrsMap.get(workDairyPlantDtlTO.getPlantRegId());
            if (bookedHrs == null) {
                bookedHrs = 0.0;
            }

            bookedHrs = workDairyTO.getPlantMaxHrs() - bookedHrs;
            if (currentBookedHrs > bookedHrs) {
                PlantMstrEntity plantRegEntity = plantRegisterRepository.findOne(workDairyPlantDtlTO.getPlantRegId())
                        .getPlantClassMstrId();
                String message = null;
                if (bookedHrs > 0)
                    message = "Cannot create workdairy, as hours added for employee " + plantRegEntity.getCode()
                            + ", cannot exceed " + bookedHrs + "hours.";
                else
                    message = "Cannot create workdairy, as employee, " + plantRegEntity.getCode()
                            + " has no hours left for the day.";
                appResp.setMessage(message);
                appResp.setStatus("Warning");
                return appResp;
            }
        }
        return null;
    }

    public Map<Long, Double> populatePlantBookedHrsMap(WorkDairyTO workDairyTO,
            List<PlantRegisterDtlEntity> plantReg) {
        Map<Long, Double> plantBookedHrsMap = new HashMap<Long, Double>();
        List<Object[]> plantsInOtherCrews = plantWorkDairyRepository.getBookedHrsForOtherCrew(
                epsProjRepository.findOne(workDairyTO.getProjId()),
                projCrewListRepository.findOne(workDairyTO.getCrewId()),
                CommonUtil.convertStringToDate(workDairyTO.getWorkDairyDate()), plantReg);

        plantsInOtherCrews.forEach((plantInOtherCrew) -> {
            long plantRegId = (Long) plantInOtherCrew[0];
            Double plantHrs = ((Double) plantInOtherCrew[1]);
            if (plantBookedHrsMap.get(plantRegId) == null)
                plantBookedHrsMap.put(plantRegId, plantHrs);
            else
                plantBookedHrsMap.put(plantRegId, plantBookedHrsMap.get(plantRegId) + plantHrs);
        });
        return plantBookedHrsMap;
    }

    public LabelKeyTOResp getProjSettingsWorkDairyDetails(WorkDairyGetReq workDairyGetReq) {
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();
        if (CommonUtil.isNonBlankLong(workDairyGetReq.getProjId())
                && CommonUtil.isNotBlankStr(workDairyGetReq.getApprStatus())) {
            labelKeyTOs = projSettingsWorkDairyProcRepository.getProSettingsWorkDairyCheck(workDairyGetReq.getProjId(),
                    workDairyGetReq.getWorkDairyId(), workDairyGetReq.getApprStatus());
            labelKeyTOResp.getLabelKeyTOs().addAll(labelKeyTOs);
        }
        return labelKeyTOResp;
    }

    /**
     * While fetching cost codes for work dairy, the default cost codes for that
     * particular project should be shown.
     * 
     * @param defaultCostCodes
     * @param workDairyCostCodeResp
     * @param crewId
     */
    private void verifyAndAddDefaultCostCodes(List<ProjCostItemEntity> defaultCostCodes,
            WorkDairyCostCodeResp workDairyCostCodeResp, Long crewId) {
        for (ProjCostItemEntity dcc : defaultCostCodes) {
            if (dcc.isWorkDairyStatus() && StatusCodes.ACTIVE.getValue().equals(dcc.getStatus())) {
                boolean defaultCostcodeFound = false;
                // Search for project default cost code
                for (WorkDairyCostCodeTO costCode : workDairyCostCodeResp.getWorkDairyCostCodeTOs()) {
                    if (costCode.getCostId() != null && costCode.getCostId().equals(dcc.getId())) {
                        defaultCostcodeFound = true;
                        break;
                    }
                }
                // If default cost code not found then add it
                if (!defaultCostcodeFound) {
                    WorkDairyCostCodeTO workDairyCostCodeTO = new WorkDairyCostCodeTO();
                    // set Id as null to default cost code in-order to save it in DB
                    workDairyCostCodeTO.setId(null);
                    workDairyCostCodeTO.setCostId(dcc.getId());
                    workDairyCostCodeTO.setCrewId(crewId);
                    ProjMstrEntity project = dcc.getProjId();
                    if (project != null)
                        workDairyCostCodeTO.setProjId(project.getProjectId());
                    workDairyCostCodeTO.setStatus(dcc.getStatus());
                    workDairyCostCodeResp.getWorkDairyCostCodeTOs().add(workDairyCostCodeTO);
                }
            }
            // continue recursion for childs
            if (!dcc.getChildEntities().isEmpty()) {
                verifyAndAddDefaultCostCodes(dcc.getChildEntities(), workDairyCostCodeResp, crewId);
            }
        }
    }

    @Override
    public LabelKeyTOResp getPlantUtilisationRecords(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        LabelKeyTOResp resp = new LabelKeyTOResp();
        long plantId = plantProjectDtlGetReq.getPlantId();
        List<Object[]> workDairyUtilizes = plantStatusWorkDairyRepository.findWorkDairyByPlantId(plantId);
        List<LabelKeyTO> labels = resp.getLabelKeyTOs();
        for (Object[] object : workDairyUtilizes) {
            long projId = (Long) object[0];
            ProjMstrEntity projMstr = epsProjRepository.findOne(projId);
            PlantRegProjEntity plantReg = plantRegProjRepositoryCopy
                    .findByPlantRegisterDtlEntityAndProjId(plantRegisterRepository.findOne(plantId), projMstr);

            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(plantId);
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJ_ID, String.valueOf(projMstr.getProjectId()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_NAME, projMstr.getProjName());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_PARENT_NAME_KEY,
                    projMstr.getParentProjectMstrEntity().getProjName());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.DEPLOYMENT_ID,
                    String.valueOf(plantReg.getDeploymentId()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.MOB_DATE,
                    CommonUtil.convertDateToString(plantReg.getMobDate()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.DEMOB_DATE,
                    CommonUtil.convertDateToString(plantReg.getDeMobDate()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.MOB_ODO_METER, String.valueOf(plantReg.getOdoMeter()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.DEMOB_ODO_METER,
                    String.valueOf(plantReg.getDeMobOdoMeter()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.USED_TIME, (String.valueOf(object[1])));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.IDLE_TIME, (String.valueOf(object[2])));
            labels.add(labelKeyTO);
        }
        if (!labels.isEmpty()) {
            // Descending Sort
            labels.sort(Comparator.comparing(LabelKeyTO::getDisplayNamesMap,
                    (d1, d2) -> (Long.valueOf(d2.get(CommonConstants.DEPLOYMENT_ID)))
                            .compareTo(Long.valueOf(d1.get(CommonConstants.DEPLOYMENT_ID)))));
            double usedTotal = 0;
            double idleTotal = 0;
            // Calculate cumulative
            for (LabelKeyTO labelKeyTO : labels) {
                usedTotal += labelKeyTO.getDisplayNamesMap().get(CommonConstants.USED_TIME) != null
                        ? Double.valueOf(labelKeyTO.getDisplayNamesMap().get(CommonConstants.USED_TIME))
                        : 0;
                idleTotal += labelKeyTO.getDisplayNamesMap().get(CommonConstants.IDLE_TIME) != null
                        ? Double.valueOf(labelKeyTO.getDisplayNamesMap().get(CommonConstants.IDLE_TIME))
                        : 0;
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.USED_TOTAL_TIME, String.valueOf(usedTotal));
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.IDLE_TOTAL_TIME, String.valueOf(idleTotal));
            }
        }
        return resp;
    }

    private List<ProjMstrEntity> getProjectsFromReq(MaterialFilterReq materialFilterReq) {
        List<Long> projIds = null;
        if (CommonUtil.isListHasData(materialFilterReq.getProjList())) {
            projIds = materialFilterReq.getProjList();
        } else {
            projIds = epsProjService.getUserProjIds();
        }

        List<ProjMstrEntity> projIdObjs = new ArrayList<ProjMstrEntity>();
        projIds.forEach((projId) -> {
            projIdObjs.add(epsProjRepository.findOne(projId));
        });
        return projIdObjs;
    }

    @Override
    public List<MaterialLedgerResTo> getProjectDockets(MaterialFilterReq materialFilterReq) {
        List<MaterialLedgerResTo> materialLedgerResTos = new ArrayList<>();
        List<Object[]> workDairyMaterials = getWorkDairyMaterials(materialFilterReq);
        if (materialFilterReq.getCostCodeIds().isEmpty())
            workDairyMaterials
                    .forEach(workDairyMaterial -> materialLedgerResTos.add(getMaterialLedgers(workDairyMaterial)));
        else {
            Map<Long, List<MaterialLedgerResTo>> materialWiseItems = new HashMap<Long, List<MaterialLedgerResTo>>();
            workDairyMaterials.forEach(workDairyMaterial -> {
                MaterialLedgerResTo materialLedgerResTo = new MaterialLedgerResTo();
                materialLedgerResTo.setDate((Date) workDairyMaterial[0]);
                materialLedgerResTo.setEps((String) workDairyMaterial[1]);
                materialLedgerResTo.setProjName((String) workDairyMaterial[2]);
                materialLedgerResTo.setUnitOfMeasure((String) workDairyMaterial[3]);
                materialLedgerResTo.setResourceMaterial((String) workDairyMaterial[4]);
                materialLedgerResTo.setConsume(((BigDecimal) workDairyMaterial[5]).toString());
                materialLedgerResTo.setCostCodeDesc((String) workDairyMaterial[6]);
                materialLedgerResTo.setCostCodeName((String) workDairyMaterial[7]);
                materialLedgerResTo.setCostCodeGroup((String) workDairyMaterial[8]);
                materialLedgerResTo.setMaterialId((Long) workDairyMaterial[9]);
                materialLedgerResTo.setCostCodeId((Long) workDairyMaterial[10]);
                materialLedgerResTo.setEpsId((Long) workDairyMaterial[11]);
                List<Long> proj = new ArrayList<>();
                proj.add((long)workDairyMaterial[12]);
                materialLedgerResTo.setProjId(proj);
                if ((materialLedgerResTo.getDate()
                        .compareTo(CommonUtil.convertStringToDate(materialFilterReq.getFromDate())) >= 0)
                        && (materialLedgerResTo.getDate()
                                .compareTo(CommonUtil.convertStringToDate(materialFilterReq.getToDate())) <= 0)) {
                    materialLedgerResTos.add(materialLedgerResTo);
                }else {
                	materialLedgerResTos.add(materialLedgerResTo);
                }
                addMaterialToMap(materialWiseItems, materialLedgerResTo);
                calculateCumulativeConsumption(materialWiseItems, materialFilterReq);
            });

        }
       // materialLedgerResTos.sort(Comparator.comparing(MaterialLedgerResTo::getCumulativeConsumption));
        return materialLedgerResTos;
    }

    private void calculateCumulativeConsumption(Map<Long, List<MaterialLedgerResTo>> materialWiseItems,
            MaterialFilterReq materialFilterReq) {
        materialWiseItems.entrySet().forEach(item -> {
            List<MaterialLedgerResTo> sameIdMaterials = item.getValue();
            double previousConsumption = 0;
            double cumulativeConsumption = 0;
            for (int i = 0; i < sameIdMaterials.size(); i++) {
                MaterialLedgerResTo materialLedgerResTo = sameIdMaterials.get(i);
                cumulativeConsumption += Double.valueOf(materialLedgerResTo.getConsume());
                materialLedgerResTo.setCumulativeConsumption(String.valueOf(cumulativeConsumption));
                if (materialLedgerResTo.getDate()
                        .before(CommonUtil.convertStringToDate(materialFilterReq.getFromDate()))) {
                   // previousConsumption += Double.valueOf(materialLedgerResTo.getConsumption());
                    materialLedgerResTo.setPreviousPeriod(Double.valueOf(materialLedgerResTo.getConsume()));
                } else if ((materialLedgerResTo.getDate()
                        .compareTo(CommonUtil.convertStringToDate(materialFilterReq.getFromDate())) >= 0)
                        && (materialLedgerResTo.getDate()
                                .compareTo(CommonUtil.convertStringToDate(materialFilterReq.getToDate())) <= 0)) {
                	materialLedgerResTo.setConsumption(materialLedgerResTo.getConsume());
                }
                   // materialLedgerResTo.setPreviousPeriod(previousConsumption);
                if(materialLedgerResTo.getConsumption() != null) {
                    materialLedgerResTo.setUpToDatePeriod(materialLedgerResTo.getPreviousPeriod()
                            + Double.valueOf(materialLedgerResTo.getConsumption()));
                }else {
                	materialLedgerResTo.setUpToDatePeriod(materialLedgerResTo.getPreviousPeriod());
                }
               // }
            }
        });
    }

    private void addMaterialToMap(Map<Long, List<MaterialLedgerResTo>> materialWiseItems,
            MaterialLedgerResTo materialLedgerResTo) {
        if (materialWiseItems.containsKey(materialLedgerResTo.getMaterialId())) {
            materialWiseItems.get(materialLedgerResTo.getMaterialId()).add(materialLedgerResTo);
        } else {
            List<MaterialLedgerResTo> materialList = new ArrayList<MaterialLedgerResTo>();
            materialList.add(materialLedgerResTo);
            materialWiseItems.put(materialLedgerResTo.getMaterialId(), materialList);
        }
    }

    @Override
    public List<MaterialLedgerResTo> getInTransitItems(MaterialFilterReq materialFilterReq) {
        List<MaterialLedgerResTo> materialLedgerResTos = new ArrayList<MaterialLedgerResTo>();
        List<Object[]> workDairyMaterials = getWorkDairyMaterials(materialFilterReq);
        workDairyMaterials.forEach((workDairyMaterial) -> {
            WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity = materialWorkDairyRepository
                    .findOne((Long) workDairyMaterial[18]);
            String docketType = workDairyMaterialDtlEntity.getDocketType();
            StockMstrEntity stockMstrEntity = null;
            ProjStoreStockMstrEntity projStockMstrEntity = null;
            if (docketType.equals("Supplier Docket")) {
                MaterialPODeliveryDocketEntity materialPODeliveryDocketEntityCopy = workDairyMaterialDtlEntity
                        .getMaterialPODeliveryDocketEntity().get(0);
                stockMstrEntity = materialPODeliveryDocketEntityCopy.getStockId();
                projStockMstrEntity = materialPODeliveryDocketEntityCopy.getProjStockId();
            } else {
                MaterialProjDocketEntity materialProjDocketEntity = workDairyMaterialDtlEntity.getProjDocketSchId()
                        .getMaterialProjDocketEntity();
                stockMstrEntity = materialProjDocketEntity.getToStockId();
                projStockMstrEntity = materialProjDocketEntity.getToProjStockId();
            }
            if (stockMstrEntity != null && !stockMstrEntity.getCategory().equals("Stockpile"))
                materialLedgerResTos.add(getMaterialLedgers(workDairyMaterial));
            else if (projStockMstrEntity != null && !projStockMstrEntity.getCategory().equals("Stockpile"))
                materialLedgerResTos.add(getMaterialLedgers(workDairyMaterial));
        });
        materialLedgerResTos.sort(Comparator.comparing(MaterialLedgerResTo::getDate));
        populateCumulativeQty(materialLedgerResTos);
        return materialLedgerResTos;
    }

    private void populateCumulativeQty(List<MaterialLedgerResTo> materialList) {
        Map<String, Double> cumulativeQty = new HashMap<String, Double>();
        materialList.forEach((material) -> {
            Long locationId = (material.getStockId() == null) ? material.getProjStockId() : material.getStockId();
            String key = material.getMaterialClassId() + "-" + locationId + "-" + material.getDocketNumber();
            if (cumulativeQty.containsKey(key)) {
                material.setCumulativeConsumption(
                        (cumulativeQty.get(key) + Double.valueOf(material.getConsumption())) + "");
                cumulativeQty.put(key, Double.valueOf(material.getCumulativeConsumption()));
            } else {
                material.setCumulativeConsumption(material.getConsumption());
                cumulativeQty.put(key, Double.valueOf(material.getCumulativeConsumption()));
            }
        });
    }

    @Override
    public List<MaterialLedgerResTo> getStockPiledItems(MaterialFilterReq materialFilterReq) {
        List<MaterialLedgerResTo> materialLedgerResTos = new ArrayList<MaterialLedgerResTo>();
        List<Object[]> workDairyMaterials = getWorkDairyMaterials(materialFilterReq);
        workDairyMaterials.forEach((workDairyMaterial) -> {
            WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity = materialWorkDairyRepository
                    .findOne((Long) workDairyMaterial[18]);
            String docketType = workDairyMaterialDtlEntity.getDocketType();
            StockMstrEntity stockMstrEntity = null;
            ProjStoreStockMstrEntity projStockMstrEntity = null;
            if (!docketType.equals("Supplier Docket")) {
                MaterialProjDocketEntity materialProjDocketEntity = workDairyMaterialDtlEntity.getProjDocketSchId()
                        .getMaterialProjDocketEntity();
                stockMstrEntity = materialProjDocketEntity.getToStockId();
                projStockMstrEntity = materialProjDocketEntity.getToProjStockId();
            }
            if (stockMstrEntity != null && stockMstrEntity.getCategory().equals("Stockpile"))
                materialLedgerResTos.add(getMaterialLedgers(workDairyMaterial));
            else if (projStockMstrEntity != null && projStockMstrEntity.getCategory().equals("Stockpile"))
                materialLedgerResTos.add(getMaterialLedgers(workDairyMaterial));
        });
        materialLedgerResTos.sort(Comparator.comparing(MaterialLedgerResTo::getDate));
        populateCumulativeQty(materialLedgerResTos);
        return materialLedgerResTos;
    }

    private List<Object[]> getWorkDairyMaterials(MaterialFilterReq materialFilterReq) {
        if (!materialFilterReq.getCostCodeIds().isEmpty()) {
            return materialCostWorkDairyRepository.getMaterialsForReports(getProjectsFromReq(materialFilterReq),
                    projCostItemRepository.findAll(materialFilterReq.getCostCodeIds()),
                    materialClassRepository.findAll(materialFilterReq.getMaterialIds()),
                    CommonUtil.convertStringToDate(materialFilterReq.getToDate()));
        } else
            return materialCostWorkDairyRepository.getWorkDairyMaterialsForLedger(getProjectsFromReq(materialFilterReq),
                    CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                    CommonUtil.convertStringToDate(materialFilterReq.getToDate()));
    }

    private MaterialLedgerResTo getMaterialLedgers(Object[] workDairyMaterial) {
        MaterialLedgerResTo materialLedgerResTo = new MaterialLedgerResTo();
        materialLedgerResTo.setCurrency((String) workDairyMaterial[0]);
        materialLedgerResTo.setDate((Date) workDairyMaterial[1]);
        materialLedgerResTo.setDocketNumber((String) workDairyMaterial[2]);
        materialLedgerResTo.setDocketType((String) workDairyMaterial[3]);
        materialLedgerResTo.setEps((String) workDairyMaterial[4]);
        materialLedgerResTo.setIssued(((BigDecimal) workDairyMaterial[5]).toString());
        materialLedgerResTo.setProjName((String) workDairyMaterial[6]);
        PreContractsMaterialDtlEntity preContractMaterialDtl = preContractMaterialRepository
                .findOne((Long) workDairyMaterial[7]);
        PreContractEntity preContract = preContractMaterialDtl.getPreContractEntity();
        materialLedgerResTo.setPurchaseOrderCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                + ((String) workDairyMaterial[8]) + "-" + preContract.getId() + "-"
                + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-" + (Long) workDairyMaterial[9]);
        materialLedgerResTo.setRate(((BigDecimal) workDairyMaterial[10]).toString());
        materialLedgerResTo.setResourceMaterial((String) workDairyMaterial[11]);
        materialLedgerResTo.setResourceSubGroup((String) workDairyMaterial[12]);
        materialLedgerResTo.setScheduleItemId(
                ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + preContract.getProjId().getCode() + "-"
                        + preContract.getId() + "-" + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc()
                        + "-" + AppUtils.formatNumberToString(preContractMaterialDtl.getId()));
        materialLedgerResTo.setSupplierName((String) workDairyMaterial[13]);
        materialLedgerResTo.setUnitOfMeasure((String) workDairyMaterial[14]);
        materialLedgerResTo.setClosingStock(((BigDecimal) workDairyMaterial[15]).toString());
        materialLedgerResTo.setOpeningStock(((BigDecimal) workDairyMaterial[16]).toString());
        materialLedgerResTo.setConsumption(((BigDecimal) workDairyMaterial[17]).toString());
        WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity = materialWorkDairyRepository
                .findOne((Long) workDairyMaterial[18]);
        materialLedgerResTo.setMaterialId((Long) workDairyMaterial[19]);
        Long materialClassId = (Long) workDairyMaterial[20];
        materialLedgerResTo.setMaterialClassId(materialClassId);
        WorkDairyEntity workDairyEntity = workDairyMaterialDtlEntity.getWorkDairyEntity();
        materialLedgerResTo.setWorkDairyId("WD-" + workDairyEntity.getProjId().getCode() + "-"
                + workDairyEntity.getCrewId().getCode() + "-" + workDairyEntity.getId());
        String docketType = workDairyMaterialDtlEntity.getDocketType();
        StockMstrEntity stockMstrEntity = null;
        ProjStoreStockMstrEntity projStockMstrEntity = null;
        if (docketType.equals("Supplier Docket")) {
            MaterialPODeliveryDocketEntity materialPODeliveryDocketEntityCopy = workDairyMaterialDtlEntity
                    .getMaterialPODeliveryDocketEntity().get(0);
            stockMstrEntity = materialPODeliveryDocketEntityCopy.getStockId();
            projStockMstrEntity = materialPODeliveryDocketEntityCopy.getProjStockId();
            materialLedgerResTo.setDocketId(materialLedgerResTo.getDocketType() + "-" + materialClassId + "-"
                    + materialPODeliveryDocketEntityCopy.getId());
        } else {
            MaterialProjDocketEntity materialProjDocketEntity = workDairyMaterialDtlEntity.getProjDocketSchId()
                    .getMaterialProjDocketEntity();
            stockMstrEntity = materialProjDocketEntity.getToStockId();
            projStockMstrEntity = materialProjDocketEntity.getToProjStockId();
            materialLedgerResTo.setDocketId(materialLedgerResTo.getDocketType() + "-" + materialClassId + "-"
                    + materialProjDocketEntity.getId());
        }
        if (stockMstrEntity != null) {
            materialLedgerResTo.setStoreLocation(stockMstrEntity.getName());
            materialLedgerResTo.setStockId(stockMstrEntity.getId());
        } else {
            materialLedgerResTo.setStoreLocation(projStockMstrEntity.getDesc());
            materialLedgerResTo.setProjStockId(projStockMstrEntity.getId());
        }

        materialLedgerResTo.setSupplied("");
        return materialLedgerResTo;
    }

    public LabelKeyTOResp getMaterialConsumption(MaterialFilterReq materialFilterReq) {
        List<Long> projIds = materialFilterReq.getProjList();
        if (!CommonUtil.isListHasData(projIds)) {
            projIds = epsProjService.getUserProjIds();
        }

        List<Object[]> materials = new ArrayList<Object[]>();
        materials = materialCostWorkDairyRepository.getMaterialConsumption(projIds,
                CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                CommonUtil.convertStringToDate(materialFilterReq.getToDate()));

        List<LabelKeyTO> labelKeyTos = new ArrayList<LabelKeyTO>();
        materials.forEach(material -> {
            LabelKeyTO labelKeyto = new LabelKeyTO();
            labelKeyto.setId((Long) material[0]);
            PreContractsMaterialDtlEntity preContractMaterialDtlEntity = precontractMaterialRepositoryCopy
                    .findOne((Long) material[1]);
            PreContractEntity preContractEntity = preContractMaterialDtlEntity.getPreContractEntity();
            labelKeyto.setCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                    + preContractEntity.getProjId().getCode() + "-" + preContractEntity.getId() + "-"
                    + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc() + "-"
                    + AppUtils.formatNumberToString(preContractMaterialDtlEntity.getId()));
            Long purchaseOrderId = (Long) material[2];
            preContractEntity = purchaseOrderRepository.findOne(purchaseOrderId).getPreContractsCmpEntity()
                    .getPreContractEntity();
            labelKeyto.setName(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                    + preContractEntity.getProjId().getCode() + "-" + preContractEntity.getId() + "-"
                    + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-" + purchaseOrderId);
            Map<String, String> displayMap = new HashMap<String, String>();
            displayMap.put(RegisterConstants.PROJ_ID, ((Long) material[3]).toString());
            displayMap.put("projName", ((String) material[4]));
            displayMap.put("parentProjName", ((String) material[19]));
            WorkDairyEntity workDairyEntity = workDairyRepository.findOne((Long) material[5]);
            displayMap.put(RegisterConstants.WORK_DAIRY_CODE, "WD-" + workDairyEntity.getProjId().getCode() + "-"
                    + workDairyEntity.getCrewId().getCode() + "-" + workDairyEntity.getId());
            displayMap.put(RegisterConstants.REQ_USER, workDairyEntity.getReqUserId().getDisplayName());
            displayMap.put(RegisterConstants.STOCK_SOURCE_TYPE, (String) material[7]);
            displayMap.put(RegisterConstants.CLASS_TYPE, ((Long) material[8]).toString());
            displayMap.put("className", (String) material[9]);
            displayMap.put("classCode", (String) material[10]);
            displayMap.put("classTypeUnitOfMeasure", (String) material[20]);
            displayMap.put(RegisterConstants.CMP_ID, String.valueOf((Long) material[11]));
            displayMap.put("cmpName", (String) material[12]);
            displayMap.put(RegisterConstants.DELIVERY_DOCKET_TYPE, (String) material[13]);
            displayMap.put(RegisterConstants.DELIVERY_DOCKET_NO, (String) material[14]);
            displayMap.put(RegisterConstants.DELIVERY_DOCKET_DATE, ((Date) material[15]).toString());
            displayMap.put(RegisterConstants.UNIT_OF_RATE, ((BigDecimal) material[16]).toString());
            displayMap.put(RegisterConstants.CONSUMPTION_DATE, ((Date) material[17]).toString());
            displayMap.put(RegisterConstants.CONSUMPTION_QTY, String.valueOf((double) material[18]));
            labelKeyto.setDisplayNamesMap(displayMap);
            labelKeyTos.add(labelKeyto);
        });
        return populateCummulativeQty(labelKeyTos);
    }

    private LabelKeyTOResp populateCummulativeQty(List<LabelKeyTO> labelKeyTOs) {
        Date consumptionDate = null;
        Date consumptionNextDate = null;
        int count = 0;
        double totalQty = 0;
        double qty = 0;
        double docketTotal = 0;
        Long docketCountId = null;
        for (LabelKeyTO labelKeyTO : labelKeyTOs) {
            qty = 0;
            consumptionNextDate = null;
            if (count > 0) {

                if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE))) {
                    consumptionNextDate = CommonUtil.convertDDMMYYYYStringToDate(
                            labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE));
                }
                if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY))) {
                    qty = Double.valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                }
                if (consumptionDate.before(consumptionNextDate)) {
                    totalQty = totalQty + qty;
                    labelKeyTO.getDisplayNamesMap().put(RegisterConstants.SCH_CUMMULATIVE_QTY,
                            String.valueOf(totalQty));
                    consumptionDate = consumptionNextDate;
                } else {
                    totalQty = 0;
                    if (CommonUtil
                            .isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE))) {
                        consumptionDate = CommonUtil.convertDDMMYYYYStringToDate(
                                labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE));
                    }
                    if (CommonUtil
                            .isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY))) {
                        totalQty = Double
                                .valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                    }
                    labelKeyTO.getDisplayNamesMap().put(RegisterConstants.SCH_CUMMULATIVE_QTY,
                            String.valueOf(totalQty));
                }
            } else {
                totalQty = 0;
                if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE))) {
                    consumptionDate = CommonUtil.convertDDMMYYYYStringToDate(
                            labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE));
                }
                if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY))) {
                    totalQty = Double.valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                }
                labelKeyTO.getDisplayNamesMap().put(RegisterConstants.SCH_CUMMULATIVE_QTY, String.valueOf(totalQty));
            }
            if (count > 0) {
                if (docketCountId != null && docketCountId.equals(
                        Long.valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.DELIVERY_DOCKET_ID)))) {
                    docketTotal = docketTotal
                            + Double.valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                    labelKeyTO.getDisplayNamesMap().put(RegisterConstants.DOCKET_CUMMULATIVE_QTY,
                            String.valueOf(docketTotal));
                } else {
                    if (CommonUtil
                            .isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.DELIVERY_DOCKET_ID))) {
                        docketCountId = Long
                                .valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.DELIVERY_DOCKET_ID));
                    }

                    if (CommonUtil
                            .isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY))) {
                        docketTotal = Double
                                .valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                    }
                    labelKeyTO.getDisplayNamesMap().put(RegisterConstants.DOCKET_CUMMULATIVE_QTY,
                            String.valueOf(docketTotal));
                }
            } else {
                if (CommonUtil
                        .isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.DELIVERY_DOCKET_ID))) {
                    docketCountId = Long
                            .valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.DELIVERY_DOCKET_ID));
                }
                if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY))) {
                    docketTotal = Double
                            .valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                }
                labelKeyTO.getDisplayNamesMap().put(RegisterConstants.DOCKET_CUMMULATIVE_QTY,
                        String.valueOf(docketTotal));
            }
            count++;
        }
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    @Override
    public List<LabelKeyTO> getPlantCostCodeWiseReport(WorkDairyPlantsGetReq plantsGetReq) {
        List<WorkDairyPlantCostEntity> plantCostEntities = plantCostWorkDairyRepository.findPlantsByDate(
                plantsGetReq.getProjIds(), plantsGetReq.getCrewIds(), plantsGetReq.getCostCodeIds(),
                plantsGetReq.getWorkDairyDate());
        List<LabelKeyTO> labelKeyTOs = preparePlantCostCodeData(plantCostEntities);

        Map<String, String> currencyMap = projGeneralRepositoryCopy.getCurrencyForProjects(plantsGetReq.getProjIds())
                .stream().collect(Collectors.toMap(o -> String.valueOf(o[0]), o -> String.valueOf(o[1])));

        return labelKeyTOs.stream().map(o -> {
            String projId = o.getDisplayNamesMap().get("projId");
            o.getDisplayNamesMap().put("currency", currencyMap.get(projId) != null ? currencyMap.get(projId) : "");
            return o;
        }).collect(Collectors.toList());
    }

    @Override
    public List<LabelKeyTO> getPlantDateWiseReport(WorkDairyPlantsGetReq plantsGetReq) {
        Date fromDate = CommonUtil.convertStringToDate(plantsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(plantsGetReq.getToDate());

        List<WorkDairyPlantCostEntity> plantCostEntities = plantCostWorkDairyRepository.findPlantsBtwnDates(
                plantsGetReq.getProjIds(), plantsGetReq.getCrewIds(), plantsGetReq.getCmpIds(), fromDate, toDate);
        return preparePlantCostCodeData(plantCostEntities);
    }

    @Override
    public List<LabelKeyTO> getCurrentActivePlants(WorkDairyPlantsGetReq workDairyPlantsGetReq) {
        Date newDate = CommonUtil.removeTimeFromDate(new Date());
        List<PlantRegProjEntity> regProjEntities = plantRegProjRepositoryCopy
                .getCurrentActivePlants(workDairyPlantsGetReq.getProjIds(), workDairyPlantsGetReq.getCmpIds(), newDate);
        List<LabelKeyTO> resp = prepareCurrentActivePlants(regProjEntities);

        Map<String, String> currencyMap = projGeneralRepositoryCopy
                .getCurrencyForProjects(workDairyPlantsGetReq.getProjIds()).stream()
                .collect(Collectors.toMap(o -> String.valueOf(o[0]), o -> String.valueOf(o[1])));

        return resp.stream().map(o -> {
            String projId = o.getDisplayNamesMap().get("projId");
            o.getDisplayNamesMap().put("currency", currencyMap.get(projId) != null ? currencyMap.get(projId) : "");
            return o;
        }).collect(Collectors.toList());
    }

    @Override
    public List<LabelKeyTO> getPlantsStandardActual(WorkDairyPlantsGetReq workDairyPlantsGetReq) {
        Date fromDate = CommonUtil.convertStringToDate(workDairyPlantsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(workDairyPlantsGetReq.getToDate());

        List<WorkDairyPlantCostEntity> plantCostEntities = plantCostWorkDairyRepository.findPlantsBtwnDates(
                workDairyPlantsGetReq.getProjIds(), workDairyPlantsGetReq.getCrewIds(),
                workDairyPlantsGetReq.getCmpIds(), workDairyPlantsGetReq.getCostCodeIds(), fromDate, toDate);
        Map<String, String> standardHrsMap = projGeneralRepositoryCopy
                .getStandardHrsOfProjects(workDairyPlantsGetReq.getProjIds()).stream()
                .collect(Collectors.toMap(o -> String.valueOf(o[0]), o -> String.valueOf(o[1])));
        List<LabelKeyTO> labelKeyTOs = preparePlantCostCodeData(plantCostEntities);
        return labelKeyTOs.stream().map(o -> {
            String projId = o.getDisplayNamesMap().get("projId");
            o.getDisplayNamesMap().put("standardHrs",
                    standardHrsMap.get(projId) != null ? standardHrsMap.get(projId) : "0");
            return o;
        }).collect(Collectors.toList());
    }

    private List<LabelKeyTO> prepareCurrentActivePlants(List<PlantRegProjEntity> regProjEntities) {
        List<LabelKeyTO> labelKeyTOs = new ArrayList<>();
        for (PlantRegProjEntity entity : regProjEntities) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            Map<String, String> namesMap = labelKeyTO.getDisplayNamesMap();

            ProjMstrEntity projMstr = entity.getProjId();
            processProjectData(projMstr, namesMap);

            PlantRegisterDtlEntity plantDtl = entity.getPlantRegisterDtlEntity();
            processPlantDtlData(plantDtl, namesMap, projMstr.getProjectId());

            labelKeyTOs.add(labelKeyTO);
        }
        return labelKeyTOs;
    }

    private List<LabelKeyTO> preparePlantCostCodeData(List<WorkDairyPlantCostEntity> plantCostEntities) {
        List<LabelKeyTO> labelKeyTOs = new ArrayList<>();
        for (WorkDairyPlantCostEntity plantCostEntity : plantCostEntities) {
            if (plantCostEntity.getUsedTime() > 0 || plantCostEntity.getIdleTime() > 0) {
                LabelKeyTO labelKeyTO = new LabelKeyTO();
                Map<String, String> namesMap = labelKeyTO.getDisplayNamesMap();
                WorkDairyEntity workDairy = plantCostEntity.getWorkDairyId();
                processWorkDairyData(plantCostEntity, namesMap);
                ProjCrewMstrEntity crewMstr = workDairy.getCrewId();
                processCrewData(crewMstr, namesMap);
                ProjMstrEntity projMstr = workDairy.getProjId();
                if (null != projMstr) {
                    processProjectData(projMstr, namesMap);
                    PlantRegisterDtlEntity plantDtl = plantCostEntity.getWorkDairyPlantStatusEntity()
                            .getWorkDairyPlantDtlEntity().getPlantRegId();
                    processPlantDtlData(plantDtl, namesMap, projMstr.getProjectId());
                }
                ProjCostItemEntity costMstr = plantCostEntity.getCostId();
                processCostCodeData(costMstr, namesMap);
                labelKeyTOs.add(labelKeyTO);
            }
        }
        return labelKeyTOs;
    }

    private void processWorkDairyData(WorkDairyPlantCostEntity plantCostEntity, Map<String, String> namesMap) {
        WorkDairyEntity workDairy = plantCostEntity.getWorkDairyId();
        namesMap.put("workDairyDate", CommonUtil.convertDateToString(workDairy.getWorkDairyDate()));
        namesMap.put("usedHrs", String.valueOf(plantCostEntity.getUsedTime()));
        namesMap.put("idleHrs", String.valueOf(plantCostEntity.getIdleTime()));
    }

    private void processProjectData(ProjMstrEntity projMstr, Map<String, String> namesMap) {
        if (null != projMstr) {
            namesMap.put("projId", String.valueOf(projMstr.getProjectId()));
            namesMap.put("projName", String.valueOf(projMstr.getProjName()));
            ProjMstrEntity epsMstr = projMstr.getParentProjectMstrEntity();
            if (null != epsMstr) {
                namesMap.put("epsId", String.valueOf(epsMstr.getProjectId()));
                namesMap.put("epsName", epsMstr.getProjName());
            }
        }
    }

    private void processPlantDtlData(PlantRegisterDtlEntity plantDtl, Map<String, String> namesMap, long projId) {
        if (null != plantDtl) {
            PlantRegProjEntity regProjEntity = plantRegProjRepositoryCopy
                    .findByPlantRegisterDtlEntityAndProjId(plantDtl, epsProjRepository.findOne(projId));
            namesMap.put("plantMobDate", CommonUtil.convertDateToString(regProjEntity.getMobDate()));
            namesMap.put("plantExpectedDeMobDate",
                    CommonUtil.convertDateToString(regProjEntity.getAnticipatedDeMobDate()));
            namesMap.put("plantDeMobDate", CommonUtil.convertDateToString(regProjEntity.getDeMobDate()));
            namesMap.put("plantId", String.valueOf(plantDtl.getId()));
            namesMap.put("plantAssertId", plantDtl.getAssertId());
            namesMap.put("plantRegNum", plantDtl.getRegNumber());
            namesMap.put("plantName", plantDtl.getDesc());
            namesMap.put("plantModel", plantDtl.getModel());
            namesMap.put("plantManfacture", plantDtl.getManfacture());
            PlantChargeOutRatesEntityCopy chargeOutRates = plantChargeOutRateRepository
                    .findPlantChargeOutRate(plantDtl.getId(), projId);
            if (null != chargeOutRates) {
                if (chargeOutRates.getCategory().equalsIgnoreCase("WITHOUT FUEL")) {
                    namesMap.put("unitOfRate", String.valueOf(chargeOutRates.getRateWithOutFualNRShift()));
                } else if (chargeOutRates.getCategory().equalsIgnoreCase("WITH FUEL")) {
                    namesMap.put("unitOfRate", String.valueOf(chargeOutRates.getRateWithFualNRShift()));
                }
            }
            PlantMstrEntity plantClass = plantDtl.getPlantClassMstrId();
            if (null != plantClass) {
                namesMap.put("plantClassId", String.valueOf(plantDtl.getPlantClassMstrId().getId()));
                namesMap.put("plantClassName", String.valueOf(plantDtl.getPlantClassMstrId().getName()));
                namesMap.put("unitOfMeasure", plantDtl.getPlantClassMstrId().getMeasurmentMstrEntity().getName());
            }
            CompanyMstrEntity cmpMstr = plantDtl.getCmpId();
            namesMap.put("cmpId", String.valueOf(cmpMstr.getId()));
            namesMap.put("cmpName", cmpMstr.getName());
        }
    }

    private void processCostCodeData(ProjCostItemEntity costMstr, Map<String, String> namesMap) {
        if (null != costMstr) {
            namesMap.put("costId", String.valueOf(costMstr.getId()));
            namesMap.put("costCode", costMstr.getCode());
            namesMap.put("costName", costMstr.getName());
            ProjCostItemEntity parentCost = costMstr.getProjCostItemEntity();
            if (null != parentCost) {
                namesMap.put("parentCostId", String.valueOf(parentCost.getId()));
                namesMap.put("parentCostCode", parentCost.getCode());
                namesMap.put("parentCostName", parentCost.getName());
            }
        }
    }

    private void processCrewData(ProjCrewMstrEntity crewMstr, Map<String, String> namesMap) {
        if (null != crewMstr) {
            namesMap.put("crewId", String.valueOf(crewMstr.getId()));
            namesMap.put("crewName", crewMstr.getDesc());
        }
    }

    @Override
    public List<PlantActualHrsTO> getPlantsPeriodicalReport(WorkDairyPlantsGetReq workDairyPlantsGetReq) {
        List<Long> projIds = workDairyPlantsGetReq.getProjIds();
        List<Long> cmpIds = workDairyPlantsGetReq.getCmpIds();

        Date fromDate = CommonUtil.convertStringToDate(workDairyPlantsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(workDairyPlantsGetReq.getToDate());
        List<WorkDairyPlantCostEntity> plantCostEntities = plantCostWorkDairyRepository.findAllPlantsBefore(projIds,
                cmpIds, toDate);

        Map<PlantActualHrsTO, List<ReportHoursTO>> manPowerHrsMap = processPeriodicalReport(plantCostEntities);

        Set<PlantActualHrsTO> plantKeySet = manPowerHrsMap.keySet();
        ReportHoursTO hr;
        for (PlantActualHrsTO key : plantKeySet) {
            List<ReportHoursTO> hrsList = manPowerHrsMap.get(key);
            for (Iterator<ReportHoursTO> hrsIterator = hrsList.iterator(); hrsIterator.hasNext();) {
                hr = hrsIterator.next();
                if (hr.getDate().before(fromDate)) {
                    key.addPrevHrs(hr);
                    hrsIterator.remove();
                } else {
                    key.addCurrentHrs(hr);
                }
            }
        }

        for (Entry<PlantActualHrsTO, List<ReportHoursTO>> entry : manPowerHrsMap.entrySet()) {
            entry.getKey().setHrsList(entry.getValue());
        }

        return plantKeySet.stream().collect(Collectors.toList());
    }

    private Map<PlantActualHrsTO, List<ReportHoursTO>> processPeriodicalReport(
            List<WorkDairyPlantCostEntity> plantCostEntities) {
        Map<PlantActualHrsTO, List<ReportHoursTO>> plantHrsMap = new HashMap<>();
        for (WorkDairyPlantCostEntity plantCost : plantCostEntities) {
            if (plantCost.getUsedTime() > 0 || plantCost.getIdleTime() > 0) {
                PlantActualHrsTO plantActualHrsTO = new PlantActualHrsTO();
                List<ReportHoursTO> reportHoursTOs = new ArrayList<>();
                WorkDairyEntity workDairy = plantCost.getWorkDairyId();
                if (workDairy != null) {
                    ProjMstrEntity projMstr = workDairy.getProjId();
                    if (projMstr != null) {
                        processProjData(plantActualHrsTO, workDairy.getProjId());
                        PlantRegisterDtlEntity plantDtl = plantCost.getWorkDairyPlantStatusEntity()
                                .getWorkDairyPlantDtlEntity().getPlantRegId();

                        processPlantDtlData(plantDtl, plantActualHrsTO);
                    }
                    reportHoursTOs.add(new ReportHoursTO(workDairy.getWorkDairyDate(), plantCost.getUsedTime(),
                            plantCost.getIdleTime()));
                }
                ProjCostItemEntity costMstr = plantCost.getCostId();
                processCostDetails(costMstr, plantActualHrsTO);
                updateMap(plantHrsMap, plantActualHrsTO, reportHoursTOs);
            }
        }
        return plantHrsMap;
    }

    private void updateMap(Map<PlantActualHrsTO, List<ReportHoursTO>> plantHrsMap, PlantActualHrsTO plantActualHrsTO,
            List<ReportHoursTO> reportHoursTOs) {
        if (plantHrsMap.containsKey(plantActualHrsTO)) {
            List<ReportHoursTO> existingHrs = plantHrsMap.get(plantActualHrsTO);
            int itemIndex = -1;
            for (ReportHoursTO newReportHrs : reportHoursTOs) {
                itemIndex = existingHrs.indexOf(newReportHrs);
                if (itemIndex != -1) {
                    existingHrs.get(itemIndex).add(newReportHrs);
                } else {
                    existingHrs.add(newReportHrs);
                }
            }
        } else {
            plantHrsMap.put(plantActualHrsTO, reportHoursTOs);
        }
    }

    private void processProjData(PlantActualHrsTO plantActualHrsTO, ProjMstrEntity projMstr) {
        plantActualHrsTO.setProjId(projMstr.getProjectId());
        plantActualHrsTO.setProjName(projMstr.getProjName());
        ProjMstrEntity parent = projMstr.getParentProjectMstrEntity();
        if (parent != null) {
            plantActualHrsTO.setParentProjId(parent.getProjectId());
            plantActualHrsTO.setParentProjName(parent.getProjName());
        }
    }

    private void processPlantDtlData(PlantRegisterDtlEntity plantDtl, PlantActualHrsTO plantActualHrsTO) {
        if (null != plantDtl) {
            plantActualHrsTO.setPlantId(plantDtl.getId());
            plantActualHrsTO.setPlantAssertId(plantDtl.getAssertId());
            plantActualHrsTO.setPlantName(plantDtl.getAssertId());
            PlantMstrEntity plantClass = plantDtl.getPlantClassMstrId();
            if (null != plantClass) {
                plantActualHrsTO.setPlantClassId(plantClass.getId());
                plantActualHrsTO.setPlantTradeName(plantClass.getName());
                MeasurmentMstrEntity measure = plantClass.getMeasurmentMstrEntity();
                if (plantClass.getMeasurmentMstrEntity() != null)
                    plantActualHrsTO.setUnitOfMeasure(measure.getName());
            }
            CompanyMstrEntity cmpMstr = plantDtl.getCmpId();
            if (cmpMstr != null) {
                plantActualHrsTO.setCompanyId(cmpMstr.getId());
                plantActualHrsTO.setCompanyName(cmpMstr.getName());
            }
        }
    }

    private void processCostDetails(ProjCostItemEntity costMstr, PlantActualHrsTO plantActualHrsTO) {
        if (costMstr != null) {
            plantActualHrsTO.setCostCodeId(costMstr.getId());
            plantActualHrsTO.setCostCodeName(costMstr.getCode());
            plantActualHrsTO.setCostCodeDesc(costMstr.getName());

            ProjCostItemEntity costParent = costMstr.getProjCostItemEntity();
            if (costParent != null) {
                plantActualHrsTO.setParentCostCode(costParent.getCode());
                plantActualHrsTO.setParentCostCodeName(costParent.getName());
            }
        }
    }

    @Override
    public List<LabelKeyTO> getPlantsIdlePeriodicalReport(WorkDairyPlantsGetReq workDairyPlantsGetReq) {
        Date fromDate = CommonUtil.convertStringToDate(workDairyPlantsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(workDairyPlantsGetReq.getToDate());
        List<WorkDairyPlantCostEntity> plantCostEntities = plantCostWorkDairyRepository.findPlantsBtwnDates(
                workDairyPlantsGetReq.getProjIds(), workDairyPlantsGetReq.getCrewIds(),
                workDairyPlantsGetReq.getCmpIds(), workDairyPlantsGetReq.getCostCodeIds(),
                workDairyPlantsGetReq.getPlantIds(), fromDate, toDate);

        List<LabelKeyTO> labelKeyTOs = preparePlantCostCodeData(plantCostEntities);
        labelKeyTOs = labelKeyTOs.stream()
                .filter(label -> (label.getDisplayNamesMap().get("idleHrs") != null
                        && Double.parseDouble(label.getDisplayNamesMap().get("idleHrs")) > 0))
                .collect(Collectors.toList());

        Map<String, String> currencyMap = projGeneralRepositoryCopy
                .getCurrencyForProjects(workDairyPlantsGetReq.getProjIds()).stream()
                .collect(Collectors.toMap(o -> String.valueOf(o[0]), o -> String.valueOf(o[1])));

        return labelKeyTOs.stream().map(o -> {
            String projId = o.getDisplayNamesMap().get("projId");
            o.getDisplayNamesMap().put("currency", currencyMap.get(projId) != null ? currencyMap.get(projId) : "");
            return o;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProgressReportTO> getProgressDateWiseRecords(WorkDairyPlantsGetReq plantsGetReq) {
        Date fromDate = CommonUtil.convertStringToDate(plantsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(plantsGetReq.getToDate());

        List<WorkDairyProgressDtlEntity> entities = progressWorkDairyRepository.findProgressDateWise(
                plantsGetReq.getProjIds(), plantsGetReq.getCostCodeIds(), plantsGetReq.getSowIds(), fromDate, toDate);
        return processProgressDateWiseResponse(entities);
    }

    @Override
    public List<ProgressReportTO> getProgressActualRecords(WorkDairyPlantsGetReq plantsGetReq) {
        Date fromDate = CommonUtil.convertStringToDate(plantsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(plantsGetReq.getToDate());

        List<WorkDairyProgressDtlEntity> entities = progressWorkDairyRepository
                .findProgressActuals(plantsGetReq.getProjIds(), plantsGetReq.getSowIds(), fromDate, toDate);
        return processProgressDateWiseResponse(entities);
    }

    private List<ProgressReportTO> processProgressDateWiseResponse(List<WorkDairyProgressDtlEntity> entities) {
        List<ProgressReportTO> reportTOs = new ArrayList<>();
        for (WorkDairyProgressDtlEntity progressEntity : entities) {
            ProgressReportTO reportTO = new ProgressReportTO();

            WorkDairyEntity workDairy = progressEntity.getWorkDairyId();
            reportTO.setWorkDairyDate(CommonUtil.convertDateToString(workDairy.getWorkDairyDate()));

            processProjectData(workDairy.getProjId(), reportTO);
            processCostCodeData(progressEntity.getCostId(), reportTO);
            processSOWData(progressEntity.getSowId(), reportTO, false);
            reportTO.setCurrentValue(progressEntity.getValue());
            List<ResourceAssignmentDataEntity> assigndata = resourceAssignmentDataRepository.findBy(workDairy.getProjId().getProjectId(), progressEntity.getCostId().getId());
            for(ResourceAssignmentDataEntity dataentity: assigndata) {
            reportTO.setPlannedValue(dataentity.getBudgetUnits());
            }
            reportTOs.add(reportTO);
        }
        return reportTOs;
    }

    @Override
    public List<ProgressReportTO> getProgressPeriodicalRecords(WorkDairyPlantsGetReq plantsGetReq) {
        Date fromDate = CommonUtil.convertStringToDate(plantsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(plantsGetReq.getToDate());

        List<WorkDairyProgressDtlEntity> entities = progressWorkDairyRepository.findProgressDateWisee(
                plantsGetReq.getProjIds(), plantsGetReq.getCostCodeIds(), plantsGetReq.getSowIds());

        Map<ProgressReportTO, List<ReportValueTO>> progressHrsMap = processProgressPeriodicalResponse(entities);
        Set<ProgressReportTO> progressKeySet = progressHrsMap.keySet();
        for (ProgressReportTO key : progressKeySet) {
            List<ReportValueTO> hrsList = progressHrsMap.get(key);
            for (Iterator<ReportValueTO> hrsIterator = hrsList.iterator(); hrsIterator.hasNext();) {
                ReportValueTO hr = hrsIterator.next();
                if (hr.getDate().before(fromDate)) {
                    key.addPrevHrs(hr);
                    hrsIterator.remove();
                } else {
                    key.addCurrentHrs(hr);
                }
            }
        }
        for (Entry<ProgressReportTO, List<ReportValueTO>> entry : progressHrsMap.entrySet()) {
            entry.getKey().setHrsList(entry.getValue());
        }
        return progressKeySet.stream().collect(Collectors.toList());
    }

    private Map<ProgressReportTO, List<ReportValueTO>> processProgressPeriodicalResponse(
            List<WorkDairyProgressDtlEntity> entities) {
        Map<ProgressReportTO, List<ReportValueTO>> plantHrsMap = new HashMap<>();
        for (WorkDairyProgressDtlEntity progressEntity : entities) {
            ProgressReportTO reportTO = new ProgressReportTO();
            List<ReportValueTO> reportHoursTOs = new ArrayList<>();
            WorkDairyEntity workDairy = progressEntity.getWorkDairyId();
            reportTO.setWorkDairyDate(CommonUtil.convertDateToString(workDairy.getWorkDairyDate()));
            processProjectData(workDairy.getProjId(), reportTO);
            processCostCodeData(progressEntity.getCostId(), reportTO);
            processSOWData(progressEntity.getSowId(), reportTO, true);
            reportHoursTOs.add(new ReportValueTO(workDairy.getWorkDairyDate(), progressEntity.getValue()));
            updateMap(plantHrsMap, reportTO, reportHoursTOs);
        }
        return plantHrsMap;
    }

    private void updateMap(Map<ProgressReportTO, List<ReportValueTO>> plantHrsMap, ProgressReportTO plantActualHrsTO,
            List<ReportValueTO> reportHoursTOs) {
        if (plantHrsMap.containsKey(plantActualHrsTO)) {
            List<ReportValueTO> existingHrs = plantHrsMap.get(plantActualHrsTO);
            int itemIndex = -1;
            for (ReportValueTO newReportHrs : reportHoursTOs) {
                itemIndex = existingHrs.indexOf(newReportHrs);
                if (itemIndex != -1) {
                    existingHrs.get(itemIndex).add(newReportHrs);
                } else {
                    existingHrs.add(newReportHrs);
                }
            }
        } else {
            plantHrsMap.put(plantActualHrsTO, reportHoursTOs);
        }
    }

    private void processProjectData(ProjMstrEntity projMstr, ProgressReportTO reportTO) {
        if (null != projMstr) {
            reportTO.setProjId(projMstr.getProjectId());
            reportTO.setProjCode(projMstr.getCode());
            reportTO.setProjName(projMstr.getProjName());
            ProjMstrEntity epsMstr = projMstr.getParentProjectMstrEntity();
            if (null != epsMstr) {
                reportTO.setEpsId(epsMstr.getProjectId());
                reportTO.setEpsCode(epsMstr.getCode());
                reportTO.setEpsName(epsMstr.getProjName());
            }
        }
    }

    private void processCostCodeData(ProjCostItemEntity costMstr, ProgressReportTO reportTO) {
        if (null != costMstr) {
            reportTO.setCostId(costMstr.getId());
            reportTO.setCostCode(costMstr.getCode());
            reportTO.setCostName(costMstr.getName());
            ProjCostItemEntity parentCost = costMstr.getProjCostItemEntity();
            if (null != parentCost) {
                reportTO.setParentCostId(parentCost.getId());
                reportTO.setParentCostCode(parentCost.getCode());
                reportTO.setParentCostName(parentCost.getName());
            }
        }
    }

    private void processSOWData(ProjSOWItemEntity sowEntity, ProgressReportTO reportTO, boolean needSOR) {
        if (null != sowEntity) {
            reportTO.setSowId(sowEntity.getId());
            reportTO.setSowDesc(sowEntity.getName());
            processSOEData(sowEntity.getProjSOEItemEntity(), reportTO);
            if (needSOR) {
                processSORData(sowEntity.getProjSORItemEntity(), reportTO);
            }
        }
    }

    private void processSOEData(ProjSOEItemEntity soeEntity, ProgressReportTO reportTO) {
        if (null != soeEntity) {
            reportTO.setSoeId(soeEntity.getId());
            reportTO.setSoeDesc(soeEntity.getName());
            reportTO.setSoeCode(soeEntity.getCode());
            MeasurmentMstrEntity measure = soeEntity.getMeasurmentMstrEntity();
            if (null != measure) {
                reportTO.setUnitOfMeasure(measure.getCode());
            }
            ProjSOEItemEntity parent = soeEntity.getProjSOEItemEntity();
            if (null != parent) {
                reportTO.setParentSoeId(parent.getId());
                reportTO.setParentSoeDesc(parent.getName());
                reportTO.setParentSoeCode(parent.getCode());
            }
        }
    }

    private void processSORData(ProjSORItemEntity sorEntity, ProgressReportTO reportTO) {
        if (null != sorEntity) {
            reportTO.setSorId(sorEntity.getId());
            reportTO.setSorCode(sorEntity.getCode());
            reportTO.setSorDesc(sorEntity.getName());
            if (sorEntity.getTotalRateIfNoSubCategory() == null) {
	            BigDecimal total = sorEntity.getLabourRate().add(sorEntity.getPlantRate()).add(sorEntity.getMaterialRate())
	                    .add(sorEntity.getOthersRate());
	            reportTO.setSorTotal(total);
            } else {
            	reportTO.setSorTotal(sorEntity.getTotalRateIfNoSubCategory());
            }
            reportTO.setSorQuantity(sorEntity.getQuantity());
            ProjSORItemEntity parent = sorEntity.getProjSORItemEntity();
            if (null != parent) {
                reportTO.setParentSorId(parent.getId());
                reportTO.setParentSorDesc(parent.getName());
                reportTO.setParentSorCode(parent.getCode());
            }
        }
    }

    public List<WorkDairyApprStatusReportTO> getWorkDairyApprovalReport(
            WorkDairyApprovalGetReq workDairyApprovalGetReq) {

        List<Long> projIds = workDairyApprovalGetReq.getProjIds();
        Date fromDate = CommonUtil.convertStringToDate(workDairyApprovalGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(workDairyApprovalGetReq.getToDate());
        String apprStatus = workDairyApprovalGetReq.getApprStatus();

        List<WorkDairyEntity> workDairyList;
        if (apprStatus == null) {
            workDairyList = workDairyRepository.getWorkDairyApprovalReport(projIds, fromDate, toDate);
        } else if (apprStatus.contains("client")) {
            workDairyList = workDairyRepository.getWorkDairyClientApprovalReport(projIds, fromDate, toDate, apprStatus);
        } else if(apprStatus.equalsIgnoreCase("preparation")){
        	workDairyList = workDairyRepository.getWorkDairyApprovalReports(projIds, fromDate, toDate);
        }else {
            workDairyList = workDairyRepository.getWorkDairyApprovalReport(projIds, fromDate, toDate, apprStatus);
        }
        List<WorkDairyApprStatusReportTO> apprStatusReportTOs = new ArrayList<>();

        for (WorkDairyEntity workDairy : workDairyList) {
            WorkDairyApprStatusReportTO workDairyApprStatusReportTO = new WorkDairyApprStatusReportTO();

            ProjMstrEntity proj = workDairy.getProjId();
            ProjectTO projectTO = new ProjectTO();
            workDairyApprStatusReportTO.setProjId(proj.getProjectId());
            projectTO.setName(proj.getProjName());
            projectTO.setCode(proj.getCode());
            projectTO.setProjId(proj.getProjectId());
            ProjMstrEntity projParent = proj.getParentProjectMstrEntity();
            if (projParent != null) {
                projectTO.setParentName(projParent.getProjName());
                workDairyApprStatusReportTO.setParentProjId(projParent.getProjectId());
                projectTO.setParentCode(projParent.getCode());
            }
            workDairyApprStatusReportTO.setProjectTO(projectTO);
            ProjCrewMstrEntity crew = workDairy.getCrewId();
            if (crew != null) {
                workDairyApprStatusReportTO.setCrewId(crew.getId());
                workDairyApprStatusReportTO.setCrewCode(crew.getCode());
                workDairyApprStatusReportTO.setCrewName(crew.getDesc());
            }
            workDairyApprStatusReportTO.setCode(WorkDairyHandler.generateCode(workDairy));
            UserMstrEntity reqUser = workDairy.getReqUserId();
            if (reqUser != null) {
                workDairyApprStatusReportTO.setReqUserId(reqUser.getUserId());
                workDairyApprStatusReportTO.setReqUserName(reqUser.getDisplayName());
                workDairyApprStatusReportTO.setReqUserCode(reqUser.getEmpCode());
            }
            workDairyApprStatusReportTO.setClientApproval(workDairy.isClientApproval());
            UserMstrEntity apprUser = workDairy.isClientApproval() ? workDairy.getClientApprUserId()
                    : workDairy.getInternalApprUserId();
            if (apprUser != null) {
                workDairyApprStatusReportTO.setApprUserId(apprUser.getUserId());
                workDairyApprStatusReportTO.setApprUserName(apprUser.getDisplayName());
                workDairyApprStatusReportTO.setApprUserCode(apprUser.getEmpCode());
            }
            workDairyApprStatusReportTO.setApprStatus(workDairy.getApprStatus());
            workDairyApprStatusReportTO.setDate(workDairy.getWorkDairyDate());

            apprStatusReportTOs.add(workDairyApprStatusReportTO);
        }

        return apprStatusReportTOs;
    }

    @Override
    public List<ProgressReportTO> getSorProgressClaimRecords(WorkDairyPlantsGetReq plantsGetReq) {
        Date fromDate = CommonUtil.convertStringToDate(plantsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(plantsGetReq.getToDate());

        List<WorkDairyProgressDtlEntity> entities = progressWorkDairyRepository
                .findProgressActualsByContract(plantsGetReq.getProjIds(), plantsGetReq.getContractType(), toDate);
        Map<ProgressReportTO, List<ReportValueTO>> progressHrsMap = processProgressPeriodicalResponse(entities);
        Set<ProgressReportTO> progressKeySet = progressHrsMap.keySet();
        for (ProgressReportTO key : progressKeySet) {
            List<ReportValueTO> hrsList = progressHrsMap.get(key);
            for (Iterator<ReportValueTO> hrsIterator = hrsList.iterator(); hrsIterator.hasNext();) {

                ReportValueTO hr = hrsIterator.next();
                if (hr.getDate().before(fromDate)) {
                    key.addPrevHrs(hr);
                    hrsIterator.remove();
                } else {
                    key.addCurrentHrs(hr);
                }
            }
        }

        List<ProgressReportTO> finalList = progressKeySet.stream().collect(Collectors.toList());

        Map<String, String> currencyMap = projGeneralRepositoryCopy.getCurrencyForProjects(plantsGetReq.getProjIds())
                .stream().collect(Collectors.toMap(o -> String.valueOf(o[0]), o -> String.valueOf(o[1])));

        return finalList.stream().map(o -> {
            String projId = String.valueOf(o.getProjId());
            o.setCurrency(currencyMap.get(projId) != null ? currencyMap.get(projId) : "");
            return o;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MaterialLedgerResTo> getInventoryReport(MaterialFilterReq materialFilterReq) {
        List<Object[]> supplierDocketConsumption = materialCostWorkDairyRepository.getInventoryReportsSupplierDockets(
                getProjectsFromReq(materialFilterReq), CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                CommonUtil.convertStringToDate(materialFilterReq.getToDate()),
                materialClassRepository.findAll(materialFilterReq.getMaterialIds()),
                stockRepository.findAll(materialFilterReq.getStoreIds()),
                projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()));
        List<Object[]> docketConsumption = materialCostWorkDairyRepository.getInventoryReportsDockets(
                getProjectsFromReq(materialFilterReq), CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                CommonUtil.convertStringToDate(materialFilterReq.getToDate()),
                materialClassRepository.findAll(materialFilterReq.getMaterialIds()),
                stockRepository.findAll(materialFilterReq.getStoreIds()),
                projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()));
        docketConsumption.addAll(supplierDocketConsumption);
        List<MaterialLedgerResTo> materialLedgerResTos = new ArrayList<>();
        docketConsumption.forEach((docketCons) -> {
            materialLedgerResTos.add(getMaterialLedgers(docketCons));
        });
        return materialLedgerResTos;
    }
    
    public WorkDiaryResp getCreatedWorkDiaries(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	List<WorkDairyEntity> workDairyEntityList = null;
    	List<Long> userProjectsEntities = null;
    	Date fromDate = CommonUtil.convertStringToDate(employeeAttendanceRecordSheetsSearchReq.getFromDate());
    	Date toDate = CommonUtil.convertStringToDate(employeeAttendanceRecordSheetsSearchReq.getToDate());
   	
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		workDairyEntityList = workDairyRepository.findAll(AppUserUtils.getUserId(), fromDate, toDate,AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		workDairyEntityList = workDairyRepository.findAll(fromDate, toDate,AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		workDairyEntityList = workDairyRepository.findAll(AppUserUtils.getUserId(), fromDate, toDate, employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		workDairyEntityList = workDairyRepository.findAll(fromDate, toDate, employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());

    	List<WorkDairyTO> workDairyTOTOList = new ArrayList<WorkDairyTO>();
    	userProjectsEntities = userProjectsRepository
                .findUserProjIds(AppUserUtils.getUserId(), 1);
    	for (WorkDairyEntity workDairyEntity : workDairyEntityList)
    		workDairyTOTOList.add(WorkDairyHandler.convertEntityToPOJO1(workDairyEntity,userProjectsEntities));
    	WorkDiaryResp workDiaryResp = new WorkDiaryResp();
    	workDiaryResp.setWorkDairyTOs(workDairyTOTOList);
    	return workDiaryResp;
    }
    
    public WorkDiaryResp getSubmittedWorkDiaries(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	List<WorkDairyEntity> workDairyEntityList = null;
    	List<Long> userProjectsEntities = null;
    	Date fromDate = CommonUtil.convertStringToDate(employeeAttendanceRecordSheetsSearchReq.getFromDate());
    	Date toDate = CommonUtil.convertStringToDate(employeeAttendanceRecordSheetsSearchReq.getToDate());

    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		workDairyEntityList = workDairyRepository.findAllSubmitted(AppUserUtils.getUserId(), fromDate, toDate,AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		workDairyEntityList = workDairyRepository.findAllSubmitted(fromDate, toDate,AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		workDairyEntityList = workDairyRepository.findAllSubmitted(AppUserUtils.getUserId(), fromDate, toDate, employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		workDairyEntityList = workDairyRepository.findAllSubmitted(fromDate, toDate, employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());

    	List<WorkDairyTO> workDairyTOTOList = new ArrayList<WorkDairyTO>();
    	userProjectsEntities = userProjectsRepository
                .findUserProjIds(AppUserUtils.getUserId(), 1);			  	
    	for (WorkDairyEntity workDairyEntity : workDairyEntityList) 		
    	     workDairyTOTOList.add(WorkDairyHandler.convertEntityToPOJO1(workDairyEntity,userProjectsEntities)); 
    	WorkDiaryResp workDiaryResp = new WorkDiaryResp();
    	workDiaryResp.setWorkDairyTOs(workDairyTOTOList);
    	return workDiaryResp;
    }
    
    //function to update the selected workdairyprogress,workdairymanpower and workdairyplant for delete functionality
    public WorkDiaryResp deleteWorkDiary( WorkDairyGetReq workDairyGetReq ) {
    	WorkDiaryResp workDiaryResp = new WorkDiaryResp(); 
    	if( workDairyGetReq.getWorkDairyProgressIds().size() > 0 && workDairyGetReq.getWorkDairyDeleteType().equals("WORKDAIRY_PROGRESS") )
    	{
    		System.out.println("WORKDAIRY_PROGRESS condition");
    		progressWorkDairyRepository.updateWorkProgressStatusByIds( workDairyGetReq.getWorkDairyProgressIds(), StatusCodes.DEACIVATE.getValue() );
    	}
    	else if( workDairyGetReq.getWorkDairyManpowerIds().size() > 0 && workDairyGetReq.getWorkDairyDeleteType().equals("WORKDAIRY_MANPOWER") )
    	{
    		System.out.println("WORKDAIRY_MANPOWER condition");
    		empWorkDairyRepository.updateWorkDairyManpowerByIds( workDairyGetReq.getWorkDairyManpowerIds(), StatusCodes.DEACIVATE.getValue() );
    	}
    	else if( workDairyGetReq.getWorkDairyPlantIds().size() > 0 && workDairyGetReq.getWorkDairyDeleteType().equals("WORKDAIRY_PLANT") )
    	{
    		System.out.println("WORKDAIRY_PLANT condition");
    		plantWorkDairyRepository.updateWorkDairyPlantByIds( workDairyGetReq.getWorkDairyPlantIds(), StatusCodes.DEACIVATE.getValue() );
    	}
    	List<WorkDairyEntity> workDairyEntities = workDairyRepository.findWorkDairyById( workDairyGetReq.getWorkDairyId(), StatusCodes.ACTIVE.getValue() );
    	List<WorkDairyTO> workDairyTOList = new ArrayList<WorkDairyTO>();
    	for( WorkDairyEntity workDairyEntity : workDairyEntities )
    	{
    		workDairyTOList.add( WorkDairyHandler.convertEntityToPOJO( workDairyEntity ) ) ; 
    	}
    	workDiaryResp.setWorkDairyTOs( workDairyTOList );
    	return workDiaryResp;
    }
    
    public static String generateWorkDairyCode(WorkDairyEntity workDairyEntity ) {
        return  "WD-" + workDairyEntity.getProjId().getCode() + "-" + workDairyEntity.getCrewId().getCode() + "-"
                + workDairyEntity.getId();
    }
}
