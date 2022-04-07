package com.rjtech.timemanagament.controller;

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
import java.io.IOException;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.material.ledger.LedgerResTo;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.resp.MaterialProjConsumptionResp;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.constants.WorkDairyURLConstants;
import com.rjtech.timemanagement.plants.reports.dto.PlantActualHrsTO;
import com.rjtech.timemanagement.progress.reports.dto.ProgressReportTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyApprStatusReportTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;
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
import com.rjtech.common.utils.AppUtils;

@RestController
@RequestMapping(WorkDairyURLConstants.WORK_DAIRY_PARH_URL)
public class WorkdairyController {

    @Autowired
    private WorkDairyService workDairyService;

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyResp> getWorkDairyById(@RequestBody WorkDairyGetReq workDairyGetReq) {

        return new ResponseEntity<WorkDairyResp>(workDairyService.getWorkDairyById(workDairyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_FOR_CLIENT_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyResp> getWorkDairyForClientApproval(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyResp>(workDairyService.getWorkDairyForClientApproval(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_FOR_INTERNAL_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyResp> getWorkDairyForInternalApproval(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyResp>(workDairyService.getWorkDairyForInternalApproval(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_PROJ_SETTINGS_FOR_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyProjSettingResp> getProjSettingsForWorkDairy(
            @RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyProjSettingResp>(
                workDairyService.getProjSettingsForWorkDairy(workDairyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyResp> getWorkDairy(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyResp>(workDairyService.getWorkDairy(workDairyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.CREATE_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyResp> createWorkDairy(@RequestBody WorkDairySaveReq workDairySaveReq) {
        WorkDairyResp workDairyResp = new WorkDairyResp();
        workDairyResp = workDairyService.createWorkDairy(workDairySaveReq);
        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        WorkDairyTO workDairyTO = workDairySaveReq.getWorkDairyTO();
        workDairyGetReq.setProjId(workDairyTO.getProjId());
        workDairyGetReq.setCrewId(workDairyTO.getCrewId());
        workDairyGetReq.setWorkDairyDate(workDairyTO.getWorkDairyDate());
        WorkDairyRegResp workDairyRegResp = workDairyService.getEmpRegDetails(workDairyGetReq);
        workDairyResp.setEmpRegDetails(workDairyRegResp.getLabelKeyTOs());
        workDairyRegResp = workDairyService.getPlantRegDetails(workDairyGetReq);
        workDairyResp.setPlantRegDetails(workDairyRegResp.getLabelKeyTOs());

        return new ResponseEntity<WorkDairyResp>(workDairyResp, HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_EMP_REG_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyRegResp> getEmpRegDetails(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyRegResp>(workDairyService.getEmpRegDetails(workDairyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_PLANT_REG_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyRegResp> getPlantRegDetails(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyRegResp>(workDairyService.getPlantRegDetails(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> getWorkDairyDetails(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyDetailResp>(workDairyService.getWorkDairyDetails(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyCostCodeResp> getWorkDairyCostCodes(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyCostCodeResp>(workDairyService.getWorkDairyCostCodes(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_CREW_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyCostCodeResp> getWorkDairyCrewCostCodes(
            @RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyCostCodeResp>(
                workDairyService.getWorkDairyCrewCostCodes(workDairyGetReq, false), HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyCostCodeResp> saveWorkDairyCostCodes(
            @RequestBody WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq) {
        workDairyService.saveWorkDairyCostCodes(workDairyCostCodeSaveReq);
        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setProjId(workDairyCostCodeSaveReq.getProjId());
        workDairyGetReq.setWorkDairyId(workDairyCostCodeSaveReq.getWorkDairyId());
        workDairyGetReq.setCrewId(workDairyCostCodeSaveReq.getCrewId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        return new ResponseEntity<WorkDairyCostCodeResp>(workDairyService.getWorkDairyCostCodes(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_CREW_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyCostCodeResp> saveWorkDairyCrewCostCodes(
            @RequestBody WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq) {
        workDairyService.saveWorkDairyCrewCostCodes(workDairyCostCodeSaveReq);
        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setProjId(workDairyCostCodeSaveReq.getProjId());
        workDairyGetReq.setCrewId(workDairyCostCodeSaveReq.getCrewId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        return new ResponseEntity<WorkDairyCostCodeResp>(
                workDairyService.getTodayWorkDairyCrewCostCodes(workDairyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> saveWorkDairyDetails(
            @RequestBody WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        AppResp appResp = workDairyService.saveWorkDairyDetails(workDairyDtlSaveReq);
        if (CommonUtil.objectNotNull(appResp)) {
            WorkDairyDetailResp workDairyDetailResp = new WorkDairyDetailResp();
            workDairyDetailResp.cloneAppResp(appResp);
            return new ResponseEntity<WorkDairyDetailResp>(workDairyDetailResp, HttpStatus.OK);
        }
        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setWorkDairyId(workDairyDtlSaveReq.getWorkDairyTO().getId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        return new ResponseEntity<WorkDairyDetailResp>(workDairyService.getWorkDairyDetails(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.SUBMIT_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> submitWorkDairy(@RequestBody WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        AppResp appResp = workDairyService.submitWorkDairy(workDairyDtlSaveReq);
        if (CommonUtil.objectNotNull(appResp)) {
            WorkDairyDetailResp workDairyDetailResp = new WorkDairyDetailResp();
            workDairyDetailResp.cloneAppResp(appResp);
            return new ResponseEntity<WorkDairyDetailResp>(workDairyDetailResp, HttpStatus.OK);
        }
        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setWorkDairyId(workDairyDtlSaveReq.getWorkDairyTO().getId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        workDairyGetReq.setProjId(workDairyDtlSaveReq.getWorkDairyTO().getProjId());
        return new ResponseEntity<WorkDairyDetailResp>(workDairyService.getWorkDairyDetails(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.APPROVE_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> approveWorkDairy(@RequestBody WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        workDairyService.approveWorkDairy(workDairyDtlSaveReq);
        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setWorkDairyId(workDairyDtlSaveReq.getWorkDairyTO().getId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        workDairyGetReq.setProjId(workDairyDtlSaveReq.getWorkDairyTO().getProjId());
        return new ResponseEntity<WorkDairyDetailResp>(workDairyService.getWorkDairyDetails(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_EMP_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyEmpResp> getWorkDairyEmpDetails(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyEmpResp>(workDairyService.getWorkDairyEmpDetails(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyPlantResp> getWorkDairyPlantDetails(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyPlantResp>(workDairyService.getWorkDairyPlantDetails(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyMaterialResp> getWorkDairyMaterialDetails(
            @RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyMaterialResp>(workDairyService.getWorkDairyMaterialDetails(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_PROGRESS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyProgressResp> getWorkDairyProgressDetails(
            @RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyProgressResp>(workDairyService.getWorkDairyProgressDetails(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_EMP_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyEmpResp> saveWorkDairyEmpDetails(
            @RequestBody WorkDairyEmpSaveReq workDairyEmpSaveReq) {
        AppResp appResp = workDairyService.saveWorkDairyEmpDetails(workDairyEmpSaveReq);
        if (CommonUtil.objectNotNull(appResp)) {
            WorkDairyEmpResp workDairyEmpResp = new WorkDairyEmpResp();
            workDairyEmpResp.cloneAppResp(appResp);
            return new ResponseEntity<WorkDairyEmpResp>(workDairyEmpResp, HttpStatus.OK);
        }
        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setWorkDairyId(workDairyEmpSaveReq.getWorkDairyTO().getId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        workDairyGetReq.setProjId(workDairyEmpSaveReq.getWorkDairyTO().getProjId());
        return new ResponseEntity<WorkDairyEmpResp>(workDairyService.getWorkDairyEmpDetails(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyPlantResp> saveWorkDairyPlantDetails(
            @RequestBody WorkDairyPlantSaveReq workDairyPlantSaveReq) {
        AppResp appResp = workDairyService.saveWorkDairyPlantDetails(workDairyPlantSaveReq);
        if (CommonUtil.objectNotNull(appResp)) {
            WorkDairyPlantResp workDairyPlantResp = new WorkDairyPlantResp();
            workDairyPlantResp.cloneAppResp(appResp);
            return new ResponseEntity<WorkDairyPlantResp>(workDairyPlantResp, HttpStatus.OK);
        }
        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setWorkDairyId(workDairyPlantSaveReq.getWorkDairyTO().getId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        return new ResponseEntity<WorkDairyPlantResp>(workDairyService.getWorkDairyPlantDetails(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyMaterialResp> saveWorkDairyMaterialDetails(
            @RequestBody WorkDairyMaterialSaveReq workDairyMaterialSaveReq) {
        workDairyService.saveWorkDairyMaterialDetails(workDairyMaterialSaveReq);
        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setWorkDairyId(workDairyMaterialSaveReq.getWorkDairyTO().getId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        return new ResponseEntity<WorkDairyMaterialResp>(workDairyService.getWorkDairyMaterialDetails(workDairyGetReq),
                HttpStatus.OK);
    }

    /*@RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_PROGRESS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyProgressResp> saveWorkDairyProgressDetails(
            @RequestBody WorkDairyProgressSaveReq workDairyProgressSaveReq) {
        workDairyService.saveWorkDairyProgressDetails(workDairyProgressSaveReq);

        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setWorkDairyId(workDairyProgressSaveReq.getWorkDairyTO().getId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        return new ResponseEntity<WorkDairyProgressResp>(workDairyService.getWorkDairyProgressDetails(workDairyGetReq),
                HttpStatus.OK);
    }*/
    /*
     * @RequestMapping(value = RegisterURLConstants.SAVE_EMP_MEDICAL_HISTORY, method = RequestMethod.POST)
     public ResponseEntity<EmpMedicalHistoryResp> saveEmpMedicalHistory( MultipartFile[] files, 
             String empMedicalHistoryStr ) throws IOException {
    	 EmpMedicalHistorySaveReq empMedicalHistorySaveReq = AppUtils.fromJson( empMedicalHistoryStr,
    			 EmpMedicalHistorySaveReq.class );
    	 System.out.println("saveEmpMedicalHistory function from EmpMedicalHistoryContoller");
    	 System.out.println(empMedicalHistorySaveReq);
         empMedicalHistoryService.saveEmpMedicalHistory( files, empMedicalHistorySaveReq );

         ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
         projEmpRegisterGetReq.setEmpId( empMedicalHistorySaveReq.getEmpId() );
         projEmpRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
         EmpMedicalHistoryResp empMedicalHistoryResp = empMedicalHistoryService
                 .getEmpMedicalHistory(projEmpRegisterGetReq);
         empMedicalHistoryResp.cloneAppResp(CommonUtil.getSaveAppResp());
         return new ResponseEntity<EmpMedicalHistoryResp>(empMedicalHistoryResp, HttpStatus.OK);
     }
     */
    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_PROGRESS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyProgressResp> saveWorkDairyProgressDetails( String workDairyProgressStr, MultipartFile[] files ) throws IOException {
    	System.out.println("workDairyProgressStr from WorkdairyController class"+workDairyProgressStr);
    	System.out.println("files from WorkdairyController class"+files.length);
    	WorkDairyProgressSaveReq workDairyProgressSaveReq = AppUtils.fromJson( workDairyProgressStr,
    			WorkDairyProgressSaveReq.class );
    	System.out.println("saveWorkDairyProgressDetails function from WorkdairyController class");
        workDairyService.saveWorkDairyProgressDetails( files, workDairyProgressSaveReq );

        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setWorkDairyId(workDairyProgressSaveReq.getWorkDairyTO().getId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        return new ResponseEntity<WorkDairyProgressResp>(workDairyService.getWorkDairyProgressDetails(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_MORE_SOW_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> saveMoreSowCostCodes(
            @RequestBody WorkDairyProgressSaveReq workDairyProgressSaveReq) {
        workDairyService.saveMoreSowCostCodes(workDairyProgressSaveReq);

        WorkDairyGetReq workDairyGetReq = new WorkDairyGetReq();
        workDairyGetReq.setWorkDairyId(workDairyProgressSaveReq.getWorkDairyTO().getId());
        workDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        workDairyGetReq.setProjId(workDairyProgressSaveReq.getProjId());
        workDairyGetReq.setApprStatus(workDairyProgressSaveReq.getWorkDairyTO().getApprStatus());
        return new ResponseEntity<WorkDairyDetailResp>(workDairyService.getWorkDairyDetails(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.COPY_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyCopyResp> copyWorkDairy(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyCopyResp>(workDairyService.copyWorkDairy(workDairyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_PROJ_SETTINGS_WORK_DAIRY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjSettingsWorkDairyDetails(
            @RequestBody WorkDairyGetReq workDairyGetReq) {
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp = workDairyService.getProjSettingsWorkDairyDetails(workDairyGetReq);
        return new ResponseEntity<LabelKeyTOResp>(labelKeyTOResp, HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_PLANT_UTILAIZATION_RECORDS)
    public ResponseEntity<LabelKeyTOResp> getPlantUtilisationRecords(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        return new ResponseEntity<>(workDairyService.getPlantUtilisationRecords(plantProjectDtlGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<LedgerResTo> getProjectDockets(@RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();
        ledgerResTo.setLedgerRes(workDairyService.getProjectDockets(materialFilterReq));
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }
    
    @PostMapping(value = "getInventoryReport")
    public ResponseEntity<LedgerResTo> getInventoryReport(@RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();
        ledgerResTo.setLedgerRes(workDairyService.getInventoryReport(materialFilterReq));
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_IN_TRANSIT_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<LedgerResTo> getInTransitItems(@RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();
        ledgerResTo.setLedgerRes(workDairyService.getInTransitItems(materialFilterReq));
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_STOCK_PILED_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<LedgerResTo> getStockPiledItems(@RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();
        ledgerResTo.setLedgerRes(workDairyService.getStockPiledItems(materialFilterReq));
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_MATERIAL_CONSUMPTION, method = RequestMethod.POST)
    public ResponseEntity<MaterialProjConsumptionResp> getMaterialProjConsumption(
            @RequestBody MaterialFilterReq materialFilterReq) {
        MaterialProjConsumptionResp materialProjConsumptionResp = new MaterialProjConsumptionResp();
        materialProjConsumptionResp
                .setLabelKeyTOs(workDairyService.getMaterialConsumption(materialFilterReq).getLabelKeyTOs());
        return new ResponseEntity<MaterialProjConsumptionResp>(materialProjConsumptionResp, HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_PLANT_COSTCODE_WISE_REPORT)
    public ResponseEntity<List<LabelKeyTO>> getPlantCostCodeWiseReport(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(workDairyService.getPlantCostCodeWiseReport(plantsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_PLANT_DATE_WISE_REPORT)
    public ResponseEntity<List<LabelKeyTO>> getPlantDateWiseReport(@RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(workDairyService.getPlantDateWiseReport(plantsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_CURRENT_ACTIVE_PLANTS)
    public ResponseEntity<List<LabelKeyTO>> getCurrentActivePlants(@RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(workDairyService.getCurrentActivePlants(plantsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_PLANTS_STANDARD_ACTUAL)
    public ResponseEntity<List<LabelKeyTO>> getPlantsStandardActual(@RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(workDairyService.getPlantsStandardActual(plantsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_PLANTS_PERIODICAL_REPORT)
    public ResponseEntity<List<PlantActualHrsTO>> getPlantsPeriodicalReport(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(workDairyService.getPlantsPeriodicalReport(plantsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_PLANTS_IDLE_PERIODICAL_REPORT)
    public ResponseEntity<List<LabelKeyTO>> getPlantsIdlePeriodicalReport(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(workDairyService.getPlantsIdlePeriodicalReport(plantsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_PROGRESS_DATEWISE_RECORDS)
    public ResponseEntity<List<ProgressReportTO>> getProgressDateWiseRecords(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(workDairyService.getProgressDateWiseRecords(plantsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_APPROVAL_REPORT)
    public ResponseEntity<List<WorkDairyApprStatusReportTO>> getWorkDairyApprovalReport(
            @RequestBody WorkDairyApprovalGetReq workDairyApprovalGetReq) {
        return new ResponseEntity<>(workDairyService.getWorkDairyApprovalReport(workDairyApprovalGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_PROGRESS_PERIODICAL_RECORDS)
    public ResponseEntity<List<ProgressReportTO>> getProgressPeriodicalRecords(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(workDairyService.getProgressPeriodicalRecords(plantsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_PROGRESS_ACTUAL_RECORDS)
    public ResponseEntity<List<ProgressReportTO>> getProgressActualRecords(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(workDairyService.getProgressActualRecords(plantsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_SOR_PROGRESS_CLAIM_RECORDS)
    public ResponseEntity<List<ProgressReportTO>> getSorProgressClaimRecords(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(workDairyService.getSorProgressClaimRecords(plantsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = WorkDairyURLConstants.GET_CREATED_WORK_DIARIES)
    public ResponseEntity<WorkDiaryResp> getCreatedWorkDiaries(
            @RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
        return new ResponseEntity<>(workDairyService.getCreatedWorkDiaries(employeeAttendanceRecordSheetsSearchReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_SUBMITTED_WORK_DIARIES)
    public ResponseEntity<WorkDiaryResp> getSubmittedWorkDiaries(
            @RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
        return new ResponseEntity<>(workDairyService.getSubmittedWorkDiaries(employeeAttendanceRecordSheetsSearchReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.DELETE_WORK_DAIRY)
    public ResponseEntity<WorkDiaryResp> deleteWorkDiary( @RequestBody WorkDairyGetReq workDairyGetReq ) {
    	return new ResponseEntity<WorkDiaryResp>( workDairyService.deleteWorkDiary( workDairyGetReq ), HttpStatus.OK );
    }
}
