
package com.rjtech.mw.controller.timemanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.centrallib.dto.WeatherTO;
import com.rjtech.centrallib.req.EmpWagesFilterReq;
import com.rjtech.centrallib.req.WeatherFilterReq;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.material.ledger.LedgerResTo;
import com.rjtech.mw.controller.central.handler.WagFactorHandler;
import com.rjtech.mw.projLib.handler.ProjLibCostItemHandler;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.register.MWMaterialService;
import com.rjtech.mw.service.timemanagement.MWWorkDairyService;
import com.rjtech.projectlib.dto.ProjWorkShiftTO;
import com.rjtech.projectlib.req.ProjCostItemGetReq;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.req.ProjWorkShiftGetReq;
import com.rjtech.projectlib.resp.ProjSowItemsMapResp;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.constants.WorkDairyURLConstants;
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
import com.rjtech.common.utils.AppUtils;

@RestController
@RequestMapping(WorkDairyURLConstants.WORK_DAIRY_PARH_URL)
public class MWWorkDairyController {

    @Autowired
    private MWWorkDairyService mwWorkDairyService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @Autowired
    private MWMaterialService mwMaterialService;

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_FOR_CLIENT_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyResp> getWorkDairyForClientApproval(@RequestBody WorkDairyGetReq workDairyGetReq) {
        WorkDairyResp workDairyResp = new WorkDairyResp();
        workDairyResp = mwWorkDairyService.getWorkDairyForClientApproval(workDairyGetReq);
        populateWorkDairyMaps(workDairyGetReq, workDairyResp);
        return new ResponseEntity<WorkDairyResp>(workDairyResp, HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_FOR_INTERNAL_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyResp> getWorkDairyForInternalApproval(@RequestBody WorkDairyGetReq workDairyGetReq) {
        WorkDairyResp workDairyResp = new WorkDairyResp();
        workDairyResp = mwWorkDairyService.getWorkDairyForInternalApproval(workDairyGetReq);
        populateWorkDairyMaps(workDairyGetReq, workDairyResp);
        return new ResponseEntity<WorkDairyResp>(workDairyResp, HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyResp> getWorkDairyById(@RequestBody WorkDairyGetReq workDairyGetReq) {
        WorkDairyResp workDairyResp = new WorkDairyResp();
        workDairyResp = mwWorkDairyService.getWorkDairyById(workDairyGetReq);
        populateWorkDairyMaps(workDairyGetReq, workDairyResp);
        return new ResponseEntity<WorkDairyResp>(workDairyResp, HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_PROJ_SETTINGS_FOR_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyProjSettingResp> getProjSettingsForWorkDairy(
            @RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyProjSettingResp>(
                mwWorkDairyService.getProjSettingsForWorkDairy(workDairyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyResp> getWorkDairy(@RequestBody WorkDairyGetReq workDairyGetReq) {
        WorkDairyResp workDairyResp = new WorkDairyResp();
        workDairyResp = mwWorkDairyService.getWorkDairy(workDairyGetReq);
        populateWorkDairyMaps(workDairyGetReq, workDairyResp);
        return new ResponseEntity<WorkDairyResp>(workDairyResp, HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.CREATE_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyResp> createWorkDairy(@RequestBody WorkDairySaveReq workDairySaveReq) {
        WorkDairyResp workDairyResp = new WorkDairyResp();
        MaterialGetReq materialGetReq = new MaterialGetReq();
        materialGetReq.setProjId(workDairySaveReq.getProjId());
        materialGetReq.setStatus(workDairySaveReq.getStatus());
        workDairyResp = mwWorkDairyService.createWorkDairy(workDairySaveReq);
        workDairyResp.setMaterialProjResp(mwMaterialService.getProjMaterialSchItems(materialGetReq));
        return new ResponseEntity<WorkDairyResp>(workDairyResp, HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> getWorkDairyDetails(@RequestBody WorkDairyGetReq workDairyGetReq) {
        WorkDairyDetailResp workDairyDetailResp = new WorkDairyDetailResp();
        workDairyDetailResp = mwWorkDairyService.getWorkDairyDetails(workDairyGetReq);
        MaterialGetReq materialGetReq = new MaterialGetReq();
        materialGetReq.setProjId(workDairyGetReq.getProjId());
        materialGetReq.setStatus(workDairyGetReq.getStatus());
        workDairyDetailResp.setMaterialProjResp(mwMaterialService.getProjMaterialSchItems(materialGetReq));
        return new ResponseEntity<WorkDairyDetailResp>(workDairyDetailResp, HttpStatus.OK);
    }
    

    @RequestMapping(value = WorkDairyURLConstants.GET_EMP_REG_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyRegResp> getEmpRegDetails(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyRegResp>(mwWorkDairyService.getEmpRegDetails(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_PLANT_REG_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyRegResp> getPlantRegDetails(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyRegResp>(mwWorkDairyService.getPlantRegDetails(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyCostCodeResp> getWorkDairyCostCodes(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyCostCodeResp>(mwWorkDairyService.getWorkDairyCostCodes(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_CREW_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyCostCodeResp> getWorkDairyCrewCostCodes(
            @RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyCostCodeResp>(mwWorkDairyService.getWorkDairyCrewCostCodes(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> saveWorkDairyDetails(
            @RequestBody WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        WorkDairyDetailResp workDairyDetailResp = mwWorkDairyService.saveWorkDairyDetails(workDairyDtlSaveReq);
        MaterialGetReq materialGetReq = new MaterialGetReq();
        materialGetReq.setProjId(workDairyDtlSaveReq.getProjId());
        materialGetReq.setStatus(workDairyDtlSaveReq.getStatus());
        workDairyDetailResp.setMaterialProjResp(mwMaterialService.getProjMaterialSchItems(materialGetReq));
        return new ResponseEntity<WorkDairyDetailResp>(workDairyDetailResp, HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.SUBMIT_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> submitWorkDairy(@RequestBody WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        return new ResponseEntity<WorkDairyDetailResp>(mwWorkDairyService.submitWorkDairy(workDairyDtlSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.APPROVE_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> approveWorkDairy(@RequestBody WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        return new ResponseEntity<WorkDairyDetailResp>(mwWorkDairyService.approveWorkDairy(workDairyDtlSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> saveWorkDairyCostCodes(
            @RequestBody WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq) {
        return new ResponseEntity<WorkDairyDetailResp>(
                mwWorkDairyService.saveWorkDairyCostCodes(workDairyCostCodeSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_CREW_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyCostCodeResp> saveWorkDairyCrewCostCodes(
            @RequestBody WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq) {
        return new ResponseEntity<WorkDairyCostCodeResp>(
                mwWorkDairyService.saveWorkDairyCrewCostCodes(workDairyCostCodeSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_EMP_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyEmpResp> getWorkDairyEmpDetails(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyEmpResp>(mwWorkDairyService.getWorkDairyEmpDetails(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_EMP_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyEmpResp> saveWorkDairyEmpDetails(
            @RequestBody WorkDairyEmpSaveReq workDairyEmpSaveReq) {
        return new ResponseEntity<WorkDairyEmpResp>(mwWorkDairyService.saveWorkDairyEmpDetails(workDairyEmpSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyPlantResp> getWorkDairyPlantDetails(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyPlantResp>(mwWorkDairyService.getWorkDairyPlantDetails(workDairyGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyPlantResp> saveWorkDairyPlantDetails(
            @RequestBody WorkDairyPlantSaveReq workDairyPlantSaveReq) {
        return new ResponseEntity<WorkDairyPlantResp>(
                mwWorkDairyService.saveWorkDairyPlantDetails(workDairyPlantSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyMaterialResp> saveWorkDairyMaterialDetails(
            @RequestBody WorkDairyMaterialSaveReq workDairyMaterialSaveReq) {
        return new ResponseEntity<WorkDairyMaterialResp>(
                mwWorkDairyService.saveWorkDairyMaterialDetails(workDairyMaterialSaveReq), HttpStatus.OK);
    }

    /*@RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_PROGRESS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyProgressResp> saveWorkDairyProgressDetails(
            @RequestBody WorkDairyProgressSaveReq workDairyProgressSaveReq) {
        return new ResponseEntity<WorkDairyProgressResp>(
                mwWorkDairyService.saveWorkDairyProgressDetails(workDairyProgressSaveReq), HttpStatus.OK);
    }*/
    
    @RequestMapping(value = WorkDairyURLConstants.SAVE_WORK_DAIRY_PROGRESS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyProgressResp> saveWorkDairyProgressDetails( String workDairyProgressStr, MultipartFile[] files ) {
    	WorkDairyProgressSaveReq workDairyProgressSaveReq = AppUtils.fromJson( workDairyProgressStr,
    			WorkDairyProgressSaveReq.class );
    	System.out.println("I am saveWorkDairyProgressDetails function from MWWorkDairyController class");
    	System.out.println(workDairyProgressStr);
    	System.out.println("workDairyProgressSaveReq result:");
    	System.out.println(workDairyProgressSaveReq);
        return new ResponseEntity<WorkDairyProgressResp>( 
        		mwWorkDairyService.saveWorkDairyProgressDetails( files, workDairyProgressSaveReq ), HttpStatus.OK );
    }

    @RequestMapping(value = WorkDairyURLConstants.SAVE_MORE_SOW_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyDetailResp> saveMoreSowCostCodes(
            @RequestBody WorkDairyProgressSaveReq workDairyProgressSaveReq) {
        return new ResponseEntity<WorkDairyDetailResp>(
                mwWorkDairyService.saveMoreSowCostCodes(workDairyProgressSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.COPY_WORK_DAIRY, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyCopyResp> copyWorkDairy(@RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<WorkDairyCopyResp>(mwWorkDairyService.copyWorkDairy(workDairyGetReq), HttpStatus.OK);
    }

    private void populateWorkDairyMaps(WorkDairyGetReq workDairyGetReq, WorkDairyResp workDairyResp) {
        Map<Long, LabelKeyTO> costCodeMap = new HashMap<Long, LabelKeyTO>();
        Map<Long, LabelKeyTO> empWageFactorMap = new HashMap<Long, LabelKeyTO>();

        ProjCostItemGetReq projCostItemGetReq = new ProjCostItemGetReq();
        projCostItemGetReq.setProjId(workDairyGetReq.getProjId());
        projCostItemGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        costCodeMap = ProjLibCostItemHandler
                .getLableKeyTO(mwProjLibService.getProjCostItemsOnly(projCostItemGetReq).getProjCostItemTOs());

        EmpWagesFilterReq empWagesFilterReq = new EmpWagesFilterReq();
        empWagesFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        empWageFactorMap = WagFactorHandler
                .getLableKeyTOs(mwCentralLiblService.getEmpWages(empWagesFilterReq).getEmployeeWageRateTOs());
        workDairyResp.setCostCodeMap(costCodeMap);
        workDairyResp.setEmpWageFactorMap(empWageFactorMap);

        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projGetReq.setProjId(workDairyGetReq.getProjId());
        ProjSowItemsMapResp projSowItemsMapResp = mwProjLibService.getProjSowItemsMap(projGetReq);
        workDairyResp.setSowMap(projSowItemsMapResp.getSowItemMap());

        WeatherFilterReq weatherFilterReq = new WeatherFilterReq();
        weatherFilterReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> weatherMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO weatherTOLabelKeyTO = null;
        List<WeatherTO> weatherTOs = mwCentralLiblService.getWeatherDetails(weatherFilterReq).getWeatherTOs();

        for (WeatherTO weatherTO : weatherTOs) {
            weatherTOLabelKeyTO = new LabelKeyTO();
            weatherTOLabelKeyTO.setId(weatherTO.getId());
            weatherTOLabelKeyTO.setCode(weatherTO.getCode());
            weatherMap.put(weatherTO.getId(), weatherTOLabelKeyTO);
        }
        workDairyResp.setWeatherMap(weatherMap);

        ProjWorkShiftGetReq projWorkShifGetReq = new ProjWorkShiftGetReq();
        projWorkShifGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projWorkShifGetReq.setProjId(workDairyGetReq.getProjId());
        Map<Long, LabelKeyTO> projShiftMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO shiftLabelKeyTO = null;
        List<ProjWorkShiftTO> projWorkShiftTOs = mwProjLibService.getProjWorkShifts(projWorkShifGetReq)
                .getProjWorkShiftTOs();

        for (ProjWorkShiftTO projWorkShiftTO : projWorkShiftTOs) {
            shiftLabelKeyTO = new LabelKeyTO();
            shiftLabelKeyTO.setId(projWorkShiftTO.getId());
            shiftLabelKeyTO.setCode(projWorkShiftTO.getCode());
            projShiftMap.put(projWorkShiftTO.getId(), shiftLabelKeyTO);
        }
        workDairyResp.setProjShiftMap(projShiftMap);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_PROJ_SETTINGS_WORK_DAIRY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjSettingsWorkDairyDetails(
            @RequestBody WorkDairyGetReq workDairyGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwWorkDairyService.getProjSettingsWorkDairyDetails(workDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = WorkDairyURLConstants.GET_MATERIAL_LEDGER, method = RequestMethod.POST)
    public ResponseEntity<LedgerResTo> getMaterialLedger(@RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();
        ledgerResTo.setLedgerRes(mwWorkDairyService.getMaterialLedger(materialFilterReq));
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }
    
    @PostMapping(value = "getInventoryReport")
    public ResponseEntity<LedgerResTo> getInventoryReport(@RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();
        ledgerResTo.setLedgerRes(mwWorkDairyService.getMaterialLedger(materialFilterReq));
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }
    
    @RequestMapping(value = WorkDairyURLConstants.GET_IN_TRANSIT_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<LedgerResTo> getInTransitMaterials(@RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();
        ledgerResTo.setLedgerRes(mwWorkDairyService.getInTransitMaterials(materialFilterReq));
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }
    
    @RequestMapping(value = WorkDairyURLConstants.GET_STOCK_PILED_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<LedgerResTo> getStockPiledMaterials(@RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();
        ledgerResTo.setLedgerRes(mwWorkDairyService.getStockPiledMaterialsMaterials(materialFilterReq));
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }
    
    @RequestMapping(value = WorkDairyURLConstants.GET_CREATED_WORK_DIARIES, method = RequestMethod.POST)
    public ResponseEntity<WorkDiaryResp> getCreatedWorkDiaries(
            @RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	WorkDiaryResp workDiaryResp = mwWorkDairyService.getCreatedWorkDiaries(employeeAttendanceRecordSheetsSearchReq);
        return new ResponseEntity<WorkDiaryResp>(workDiaryResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = WorkDairyURLConstants.GET_SUBMITTED_WORK_DIARIES, method = RequestMethod.POST)
    public ResponseEntity<WorkDiaryResp> getSubmittedWorkDiaries(
            @RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	WorkDiaryResp workDiaryResp = mwWorkDairyService.getSubmittedWorkDiaries(employeeAttendanceRecordSheetsSearchReq);
        return new ResponseEntity<WorkDiaryResp>(workDiaryResp, HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_WORK_DAIRY_APPROVAL_REPORT)
    public ResponseEntity<List<WorkDairyApprStatusReportTO>> getWorkDairyApprovalReport(
            @RequestBody WorkDairyApprovalGetReq workDairyApprovalGetReq) {
    	List<WorkDairyApprStatusReportTO> resp = mwWorkDairyService.getWorkDairyApprovalReport(workDairyApprovalGetReq);
    	return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_PLANTS_PERIODICAL_REPORT)
    public ResponseEntity<List<PlantActualHrsTO>> getPlantsPeriodicalReport(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(mwWorkDairyService.getPlantsPeriodicalReport(plantsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_PLANT_DATE_WISE_REPORT)
    public ResponseEntity<List<LabelKeyTO>> getPlantDateWiseReport(@RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(mwWorkDairyService.getPlantDateWiseReport(plantsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_PLANT_COSTCODE_WISE_REPORT)
    public ResponseEntity<List<LabelKeyTO>> getPlantCostCodeWiseReport(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(mwWorkDairyService.getPlantCostCodeWiseReport(plantsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_PLANTS_IDLE_PERIODICAL_REPORT)
    public ResponseEntity<List<LabelKeyTO>> getPlantsIdlePeriodicalReport(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(mwWorkDairyService.getPlantsIdlePeriodicalReport(plantsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_PLANTS_STANDARD_ACTUAL)
    public ResponseEntity<List<LabelKeyTO>> getPlantsStandardActual(@RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(mwWorkDairyService.getPlantsStandardActual(plantsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_CURRENT_ACTIVE_PLANTS)
    public ResponseEntity<List<LabelKeyTO>> getCurrentActivePlants(@RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(mwWorkDairyService.getCurrentActivePlants(plantsGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_PROJ_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<LedgerResTo> getProjectDockets(@RequestBody MaterialFilterReq materialFilterReq) {
        LedgerResTo ledgerResTo = new LedgerResTo();
        ledgerResTo.setLedgerRes(mwWorkDairyService.getProjectDockets(materialFilterReq));
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_PROGRESS_PERIODICAL_RECORDS)
    public ResponseEntity<List<ProgressReportTO>> getProgressPeriodicalRecords(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(mwWorkDairyService.getProgressPeriodicalRecords(plantsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_PROGRESS_DATEWISE_RECORDS)
    public ResponseEntity<List<ProgressReportTO>> getProgressDateWiseRecords(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(mwWorkDairyService.getProgressDateWiseRecords(plantsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_PROGRESS_ACTUAL_RECORDS)
    public ResponseEntity<List<ProgressReportTO>> getProgressActualRecords(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(mwWorkDairyService.getProgressActualRecords(plantsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.GET_SOR_PROGRESS_CLAIM_RECORDS)
    public ResponseEntity<List<ProgressReportTO>> getSorProgressClaimRecords(
            @RequestBody WorkDairyPlantsGetReq plantsGetReq) {
        return new ResponseEntity<>(mwWorkDairyService.getSorProgressClaimRecords(plantsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = WorkDairyURLConstants.DELETE_WORK_DAIRY)
    public ResponseEntity<WorkDiaryResp> deleteWorkDiary( @RequestBody WorkDairyGetReq workDairyGetReq ) {
        return new ResponseEntity<WorkDiaryResp>( mwWorkDairyService.deleteWorkDiary( workDairyGetReq ), HttpStatus.OK );
    }
}
