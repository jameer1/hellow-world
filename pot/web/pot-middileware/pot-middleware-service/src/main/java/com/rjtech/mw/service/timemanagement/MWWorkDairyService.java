package com.rjtech.mw.service.timemanagement;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.material.ledger.MaterialLedgerResTo;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.plants.reports.dto.PlantActualHrsTO;
import com.rjtech.timemanagement.progress.reports.dto.ProgressReportTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyApprStatusReportTO;
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

public interface MWWorkDairyService {

    public WorkDairyResp getWorkDairyForClientApproval(WorkDairyGetReq workDairyGetReq);

    public WorkDairyResp getWorkDairyForInternalApproval(WorkDairyGetReq workDairyGetReq);

    public WorkDairyResp getWorkDairyById(WorkDairyGetReq workDairyGetReq);

    public WorkDairyProjSettingResp getProjSettingsForWorkDairy(WorkDairyGetReq workDairyGetReq);

    public WorkDairyResp getWorkDairy(WorkDairyGetReq workDairyGetReq);

    public WorkDairyResp createWorkDairy(WorkDairySaveReq workDairySaveReq);

    public WorkDairyCostCodeResp getWorkDairyCostCodes(WorkDairyGetReq workDairyGetReq);

    public WorkDairyCostCodeResp getWorkDairyCrewCostCodes(WorkDairyGetReq workDairyGetReq);

    public WorkDairyDetailResp saveWorkDairyCostCodes(WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq);

    public WorkDairyCostCodeResp saveWorkDairyCrewCostCodes(WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq);

    public WorkDairyRegResp getEmpRegDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyRegResp getPlantRegDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyDetailResp getWorkDairyDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyEmpResp getWorkDairyEmpDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyDetailResp saveWorkDairyDetails(WorkDairyDtlSaveReq workDairyDtlSaveReq);

    public WorkDairyEmpResp saveWorkDairyEmpDetails(WorkDairyEmpSaveReq workDairyEmpSaveReq);

    public WorkDairyPlantResp getWorkDairyPlantDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyPlantResp saveWorkDairyPlantDetails(WorkDairyPlantSaveReq workDairyPlantSaveReq);

    public WorkDairyMaterialResp saveWorkDairyMaterialDetails(WorkDairyMaterialSaveReq workDairyMaterialSaveReq);

    public WorkDairyProgressResp saveWorkDairyProgressDetails( MultipartFile[] files, WorkDairyProgressSaveReq workDairyProgressSaveReq);

    public WorkDairyDetailResp saveMoreSowCostCodes(WorkDairyProgressSaveReq workDairyProgressSaveReq);

    public WorkDairyCopyResp copyWorkDairy(WorkDairyGetReq workDairyGetReq);

    public WorkDairyDetailResp submitWorkDairy(WorkDairyDtlSaveReq workDairyDtlSaveReq);

    public WorkDairyDetailResp approveWorkDairy(WorkDairyDtlSaveReq workDairyDtlSaveReq);

    public LabelKeyTOResp getProjSettingsWorkDairyDetails(WorkDairyGetReq workDairyGetReq);

    public List<MaterialLedgerResTo> getMaterialLedger(MaterialFilterReq materialFilterReq);
    
    public List<MaterialLedgerResTo> getInventoryReport(MaterialFilterReq materialFilterReq);
    
    public List<MaterialLedgerResTo> getInTransitMaterials(MaterialFilterReq materialFilterReq);
    
    public List<MaterialLedgerResTo> getStockPiledMaterialsMaterials(MaterialFilterReq materialFilterReq);
    
    public WorkDiaryResp getCreatedWorkDiaries(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
    
    public WorkDiaryResp getSubmittedWorkDiaries(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
    
    public List<WorkDairyApprStatusReportTO> getWorkDairyApprovalReport(WorkDairyApprovalGetReq workDairyApprovalGetReq);
    
    public List<PlantActualHrsTO> getPlantsPeriodicalReport(WorkDairyPlantsGetReq workDairyPlantsGetReq);
    
    public List<LabelKeyTO> getPlantDateWiseReport(WorkDairyPlantsGetReq plantsGetReq);
    
    public List<LabelKeyTO> getPlantCostCodeWiseReport(WorkDairyPlantsGetReq plantsGetReq);
    
    public List<LabelKeyTO> getPlantsIdlePeriodicalReport(WorkDairyPlantsGetReq workDairyPlantsGetReq);
    
    public List<LabelKeyTO> getPlantsStandardActual(WorkDairyPlantsGetReq workDairyPlantsGetReq);
    
    public List<LabelKeyTO> getCurrentActivePlants(WorkDairyPlantsGetReq workDairyPlantsGetReq);
    
    public List<MaterialLedgerResTo> getProjectDockets(MaterialFilterReq materialFilterReq);
    
    public List<ProgressReportTO> getProgressPeriodicalRecords(WorkDairyPlantsGetReq plantsGetReq);
    
    public List<ProgressReportTO> getProgressDateWiseRecords(WorkDairyPlantsGetReq plantsGetReq);
    
    public List<ProgressReportTO> getProgressActualRecords(WorkDairyPlantsGetReq plantsGetReq);
    
    public List<ProgressReportTO> getSorProgressClaimRecords(WorkDairyPlantsGetReq plantsGetReq);
    
    public WorkDiaryResp deleteWorkDiary( WorkDairyGetReq workDairyGetReq );

}
