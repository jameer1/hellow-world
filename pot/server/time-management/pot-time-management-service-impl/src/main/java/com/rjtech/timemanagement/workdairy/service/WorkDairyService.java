package com.rjtech.timemanagement.workdairy.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.material.ledger.MaterialLedgerResTo;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
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

public interface WorkDairyService {

    public WorkDairyResp getWorkDairyForInternalApproval(WorkDairyGetReq workDairyGetReq);

    public WorkDairyResp getWorkDairyForClientApproval(WorkDairyGetReq workDairyGetReq);

    public WorkDairyProjSettingResp getProjSettingsForWorkDairy(WorkDairyGetReq workDairyGetReq);

    public WorkDairyResp getWorkDairy(WorkDairyGetReq workDairyGetReq);

    public WorkDairyResp getWorkDairyById(WorkDairyGetReq workDairyGetReq);

    public WorkDairyResp createWorkDairy(WorkDairySaveReq workDairySaveReq);

    public WorkDairyCostCodeResp getWorkDairyCostCodes(WorkDairyGetReq workDairyGetReq);

    public WorkDairyCostCodeResp getWorkDairyCrewCostCodes(WorkDairyGetReq workDairyGetReq,
            boolean findProjWorkDairyEntries);

    public WorkDairyCostCodeResp getTodayWorkDairyCrewCostCodes(WorkDairyGetReq workDairyGetReq);

    public void saveWorkDairyCostCodes(WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq);

    public void saveWorkDairyCrewCostCodes(WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq);

    public WorkDairyRegResp getEmpRegDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyRegResp getPlantRegDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyDetailResp getWorkDairyDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyEmpResp getWorkDairyEmpDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyPlantResp getWorkDairyPlantDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyMaterialResp getWorkDairyMaterialDetails(WorkDairyGetReq workDairyGetReq);

    public WorkDairyProgressResp getWorkDairyProgressDetails(WorkDairyGetReq workDairyGetReq);

    public AppResp saveWorkDairyDetails(WorkDairyDtlSaveReq workDairyDtlSaveReq);

    public AppResp submitWorkDairy(WorkDairyDtlSaveReq workDairyDtlSaveReq);

    public void approveWorkDairy(WorkDairyDtlSaveReq workDairyDtlSaveReq);

    public AppResp saveWorkDairyEmpDetails(WorkDairyEmpSaveReq workDairyEmpSaveReq);

    public AppResp saveWorkDairyPlantDetails(WorkDairyPlantSaveReq workDairyPlantSaveReq);

    public void saveWorkDairyMaterialDetails(WorkDairyMaterialSaveReq workDairyMaterialSaveReq);

    public void saveWorkDairyProgressDetails( MultipartFile[] files, WorkDairyProgressSaveReq workDairyProgressSaveReq ) throws IOException;

    public void saveMoreSowCostCodes(WorkDairyProgressSaveReq workDairyCostCodeSaveReq);

    public WorkDairyCopyResp copyWorkDairy(WorkDairyGetReq workDairyGetReq);

    public LabelKeyTOResp getProjSettingsWorkDairyDetails(WorkDairyGetReq workDairyGetReq);

    LabelKeyTOResp getPlantUtilisationRecords(PlantProjectDtlGetReq plantProjectDtlGetReq);

    public LabelKeyTOResp getMaterialConsumption(MaterialFilterReq materialFilterReq);

    List<LabelKeyTO> getPlantCostCodeWiseReport(WorkDairyPlantsGetReq plantsGetReq);

    List<LabelKeyTO> getPlantDateWiseReport(WorkDairyPlantsGetReq plantsGetReq);

    List<LabelKeyTO> getCurrentActivePlants(WorkDairyPlantsGetReq workDairyPlantsGetReq);

    List<LabelKeyTO> getPlantsStandardActual(WorkDairyPlantsGetReq workDairyPlantsGetReq);

    List<PlantActualHrsTO> getPlantsPeriodicalReport(WorkDairyPlantsGetReq workDairyPlantsGetReq);

    List<LabelKeyTO> getPlantsIdlePeriodicalReport(WorkDairyPlantsGetReq workDairyPlantsGetReq);

    List<MaterialLedgerResTo> getProjectDockets(MaterialFilterReq materialFilterReq);
    
    List<MaterialLedgerResTo> getInventoryReport(MaterialFilterReq materialFilterReq);
    
    List<MaterialLedgerResTo> getInTransitItems(MaterialFilterReq materialFilterReq);
    
    List<MaterialLedgerResTo> getStockPiledItems(MaterialFilterReq materialFilterReq);

    List<ProgressReportTO> getProgressDateWiseRecords(WorkDairyPlantsGetReq plantsGetReq);

    List<WorkDairyApprStatusReportTO> getWorkDairyApprovalReport(WorkDairyApprovalGetReq workDairyApprovalGetReq);

    List<ProgressReportTO> getProgressPeriodicalRecords(WorkDairyPlantsGetReq plantsGetReq);

    List<ProgressReportTO> getProgressActualRecords(WorkDairyPlantsGetReq plantsGetReq);

    List<ProgressReportTO> getSorProgressClaimRecords(WorkDairyPlantsGetReq plantsGetReq);
    
    public WorkDiaryResp getCreatedWorkDiaries(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
    
    public WorkDiaryResp getSubmittedWorkDiaries(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
    
    WorkDiaryResp deleteWorkDiary( WorkDairyGetReq workDairyGetReq );

}
