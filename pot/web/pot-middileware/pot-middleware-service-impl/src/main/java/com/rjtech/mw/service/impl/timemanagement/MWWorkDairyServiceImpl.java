package com.rjtech.mw.service.impl.timemanagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.material.ledger.LedgerResTo;
import com.rjtech.material.ledger.MaterialLedgerResTo;
import com.rjtech.mw.service.timemanagement.MWWorkDairyService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.register.constans.RegisterURLConstants;
//import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.rjs.core.annotations.RJSService;
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

@Service(value = "mwWorkDairyService")
@RJSService(modulecode = "mwWorkDairyService")
@Transactional
public class MWWorkDairyServiceImpl extends RestConfigServiceImpl implements MWWorkDairyService {

    public WorkDairyResp getWorkDairyForClientApproval(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_WORK_DAIRY_FOR_CLIENT_APPROVAL);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyResp.class);
    }

    public WorkDairyResp getWorkDairyForInternalApproval(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_WORK_DAIRY_FOR_INTERNAL_APPROVAL);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyResp.class);
    }

    public WorkDairyResp getWorkDairyById(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_WORK_DAIRY_BY_ID);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyResp.class);
    }

    public WorkDairyProjSettingResp getProjSettingsForWorkDairy(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PROJ_SETTINGS_FOR_WORK_DAIRY);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyProjSettingResp.class);
    }

    public WorkDairyResp getWorkDairy(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_WORK_DAIRY);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyResp.class);
    }

    public WorkDairyResp createWorkDairy(WorkDairySaveReq workDairySaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairySaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.CREATE_WORK_DAIRY);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyResp.class);
    }

    public WorkDairyRegResp getEmpRegDetails(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_EMP_REG_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyRegResp.class);
    }

    public WorkDairyRegResp getPlantRegDetails(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PLANT_REG_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyRegResp.class);
    }

    public WorkDairyDetailResp getWorkDairyDetails(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_WORK_DAIRY_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyDetailResp.class);
    }

    public WorkDairyDetailResp saveWorkDairyDetails(WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyDtlSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SAVE_WORK_DAIRY_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyDetailResp.class);
    }

    public WorkDairyCostCodeResp getWorkDairyCostCodes(@RequestBody WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_WORK_DAIRY_COST_CODES);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyCostCodeResp.class);

    }

    public WorkDairyCostCodeResp getWorkDairyCrewCostCodes(@RequestBody WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_WORK_DAIRY_CREW_COST_CODES);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyCostCodeResp.class);

    }

    public WorkDairyDetailResp saveWorkDairyCostCodes(@RequestBody WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyCostCodeSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SAVE_WORK_DAIRY_COST_CODES);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyDetailResp.class);

    }

    public WorkDairyCostCodeResp saveWorkDairyCrewCostCodes(
            @RequestBody WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyCostCodeSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SAVE_WORK_DAIRY_CREW_COST_CODES);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyCostCodeResp.class);

    }

    public WorkDairyEmpResp getWorkDairyEmpDetails(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_WORK_DAIRY_EMP_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyEmpResp.class);
    }

    public WorkDairyEmpResp saveWorkDairyEmpDetails(WorkDairyEmpSaveReq workDairyEmpSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyEmpSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SAVE_WORK_DAIRY_EMP_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyEmpResp.class);
    }

    public WorkDairyPlantResp getWorkDairyPlantDetails(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_WORK_DAIRY_PLANT_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyPlantResp.class);
    }

    public WorkDairyPlantResp saveWorkDairyPlantDetails(WorkDairyPlantSaveReq workDairyPlantSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyPlantSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SAVE_WORK_DAIRY_PLANT_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyPlantResp.class);
    }

    public WorkDairyMaterialResp saveWorkDairyMaterialDetails(WorkDairyMaterialSaveReq workDairyMaterialSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyMaterialSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SAVE_WORK_DAIRY_MATERIAL_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyMaterialResp.class);
    }

    /*public WorkDairyProgressResp saveWorkDairyProgressDetails(WorkDairyProgressSaveReq workDairyProgressSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyProgressSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SAVE_WORK_DAIRY_PROGRESS_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyProgressResp.class);
    }*/
    
    public WorkDairyProgressResp saveWorkDairyProgressDetails( MultipartFile[] files, WorkDairyProgressSaveReq workDairyProgressSaveReq ) {
        ResponseEntity<String> strResponse = null;
        System.out.println("I am saveWorkDairyProgressDetails function from MWWorkDairyServiceImpl class");
        System.out.println(workDairyProgressSaveReq);
        /*strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyProgressSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SAVE_WORK_DAIRY_PROGRESS_DETAILS);*/
        strResponse = constructPOSTRestTemplateWithMultipartFiles( getTimeManagementExchangeUrl(
                ( WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SAVE_WORK_DAIRY_PROGRESS_DETAILS ) ), files,
                AppUtils.toJson( workDairyProgressSaveReq ), "workDairyProgressStr" );
        System.out.println(strResponse.getBody());
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyProgressResp.class);
    }
    
    /*
     * public EmpMedicalHistoryResp saveEmpMedicalHistory( MultipartFile[] files, EmpMedicalHistorySaveReq empMedicalHistorySaveReq ) {
    	System.out.print("saveEmpMedicalHistory function of MWEmpRegisterServiceImpl");
    	System.out.println(empMedicalHistorySaveReq);
        ResponseEntity<String> strResponse = null;
        strResponse = constructPOSTRestTemplateWithMultipartFiles( getRegisterExchangeUrl(
                ( RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_MEDICAL_HISTORY ) ), files,
                AppUtils.toJson( empMedicalHistorySaveReq ), "empMedicalHistoryStr" );
        System.out.println(strResponse);
        return AppUtils.fromJson( strResponse.getBody(), EmpMedicalHistoryResp.class );
    }
     */
    
    public WorkDairyDetailResp saveMoreSowCostCodes(WorkDairyProgressSaveReq workDairyProgressSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyProgressSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SAVE_MORE_SOW_COST_CODES);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyDetailResp.class);
    }

    public WorkDairyCopyResp copyWorkDairy(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.COPY_WORK_DAIRY);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyCopyResp.class);
    }

    public WorkDairyDetailResp submitWorkDairy(WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyDtlSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.SUBMIT_WORK_DAIRY);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyDetailResp.class);
    }

    public WorkDairyDetailResp approveWorkDairy(WorkDairyDtlSaveReq workDairyDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyDtlSaveReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.APPROVE_WORK_DAIRY);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyDetailResp.class);
    }

    public LabelKeyTOResp getProjSettingsWorkDairyDetails(WorkDairyGetReq workDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyGetReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PROJ_SETTINGS_WORK_DAIRY_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    @Override
    public List<MaterialLedgerResTo> getMaterialLedger(MaterialFilterReq materialFilterReq) {

        List<MaterialLedgerResTo> materialLedgerList = new ArrayList<MaterialLedgerResTo>();
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + RegisterURLConstants.GET_PROJ_DOCKETS);
        LedgerResTo workDairyConsumption = AppUtils.fromJson(strResponse.getBody(), LedgerResTo.class);

        strResponse = null;

        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_DOCKETS);

        LedgerResTo docketResponse = AppUtils.fromJson(strResponse.getBody(), LedgerResTo.class);

        Map<String, MaterialLedgerResTo> deliveryDockets = docketResponse.getDocketLedgerMap();
        if (workDairyConsumption.getLedgerRes() == null || workDairyConsumption.getLedgerRes().isEmpty()) {
            Set<String> docketKeys = deliveryDockets.keySet();
            docketKeys.forEach((key) -> {
                materialLedgerList.add(deliveryDockets.get(key));
            });
        } else {
            workDairyConsumption.getLedgerRes().forEach((wdConsumption) -> {
                if (deliveryDockets.containsKey(wdConsumption.getDocketId()))
                    materialLedgerList.add(deliveryDockets.get(wdConsumption.getDocketId()));
                if((materialFilterReq.getStoreIds().contains(wdConsumption.getStockId())) || (materialFilterReq.getProjStoreIds().contains(wdConsumption.getProjStockId()))) {
                materialLedgerList.add(wdConsumption);
                }
            });
            Set<String> docketKeys = deliveryDockets.keySet();

            docketKeys.forEach((key) -> {
                if (!materialLedgerList.contains(deliveryDockets.get(key)))
                    materialLedgerList.add(deliveryDockets.get(key));
            });
        }

        materialLedgerList.sort(Comparator.comparing(MaterialLedgerResTo::getDate).reversed());

        return materialLedgerList;
    }

    @Override
    public List<MaterialLedgerResTo> getInTransitMaterials(MaterialFilterReq materialFilterReq) {

        List<MaterialLedgerResTo> materialLedgerList = new ArrayList<MaterialLedgerResTo>();
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_IN_TRANSIT_MATERIALS);
        LedgerResTo workDairyConsumption = AppUtils.fromJson(strResponse.getBody(), LedgerResTo.class);

        strResponse = null;

        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL
                        + RegisterURLConstants.GET_PROJ_MATERIAL_STORE_TRANSIT_CONSUMPTION);

        LedgerResTo docketResponse = AppUtils.fromJson(strResponse.getBody(), LedgerResTo.class);

        Map<String, MaterialLedgerResTo> deliveryDockets = docketResponse.getDocketLedgerMap();
        if (workDairyConsumption.getLedgerRes().size() == 0) {
            Set<String> docketKeys = deliveryDockets.keySet();
            docketKeys.forEach((key) -> {
                materialLedgerList.add(deliveryDockets.get(key));
            });
        } else {
            workDairyConsumption.getLedgerRes().forEach((wdConsumption) -> {
                if (deliveryDockets.containsKey(wdConsumption.getDocketId()))
                    materialLedgerList.add(deliveryDockets.get(wdConsumption.getDocketId()));
                materialLedgerList.add(wdConsumption);
            });
            Set<String> docketKeys = deliveryDockets.keySet();

            docketKeys.forEach((key) -> {
                if (!materialLedgerList.contains(deliveryDockets.get(key)))
                    materialLedgerList.add(deliveryDockets.get(key));
            });
        }

        materialLedgerList.sort(Comparator.comparing(MaterialLedgerResTo::getDate).reversed());

        return materialLedgerList;
    }

    @Override
    public List<MaterialLedgerResTo> getStockPiledMaterialsMaterials(MaterialFilterReq materialFilterReq) {

        List<MaterialLedgerResTo> materialLedgerList = new ArrayList<MaterialLedgerResTo>();
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_STOCK_PILED_MATERIALS);
        LedgerResTo workDairyConsumption = AppUtils.fromJson(strResponse.getBody(), LedgerResTo.class);

        strResponse = null;

        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL
                        + RegisterURLConstants.GET_PROJ_MATERIAL_STOCK_PILED_CONSUMPTION);

        LedgerResTo docketResponse = AppUtils.fromJson(strResponse.getBody(), LedgerResTo.class);

        Map<String, MaterialLedgerResTo> deliveryDockets = docketResponse.getDocketLedgerMap();
        if (workDairyConsumption.getLedgerRes().size() == 0) {
            Set<String> docketKeys = deliveryDockets.keySet();
            docketKeys.forEach((key) -> {
                materialLedgerList.add(deliveryDockets.get(key));
            });
        } else {
            workDairyConsumption.getLedgerRes().forEach((wdConsumption) -> {
                if (deliveryDockets.containsKey(wdConsumption.getDocketId()))
                    materialLedgerList.add(deliveryDockets.get(wdConsumption.getDocketId()));
                materialLedgerList.add(wdConsumption);
            });
            Set<String> docketKeys = deliveryDockets.keySet();

            docketKeys.forEach((key) -> {
                if (!materialLedgerList.contains(deliveryDockets.get(key)))
                    materialLedgerList.add(deliveryDockets.get(key));
            });
        }

        materialLedgerList.sort(Comparator.comparing(MaterialLedgerResTo::getDate).reversed());

        return materialLedgerList;
    }

    @Override
    public List<MaterialLedgerResTo> getInventoryReport(MaterialFilterReq materialFilterReq) {
        List<MaterialLedgerResTo> materialLedgerList = new ArrayList<MaterialLedgerResTo>();
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + "getInventoryReport");
        LedgerResTo workDairyConsumption = AppUtils.fromJson(strResponse.getBody(), LedgerResTo.class);

        strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_DOCKETS);

        LedgerResTo docketResponse = AppUtils.fromJson(strResponse.getBody(), LedgerResTo.class);

        Map<String, MaterialLedgerResTo> deliveryDockets = docketResponse.getDocketLedgerMap();
        if (workDairyConsumption.getLedgerRes() == null || workDairyConsumption.getLedgerRes().isEmpty()) {
            Set<String> docketKeys = deliveryDockets.keySet();
            docketKeys.forEach((key) -> {
                materialLedgerList.add(deliveryDockets.get(key));
            });
        } else {
            workDairyConsumption.getLedgerRes().forEach((wdConsumption) -> {
                if (deliveryDockets.containsKey(wdConsumption.getDocketId()))
                    materialLedgerList.add(deliveryDockets.get(wdConsumption.getDocketId()));
                materialLedgerList.add(wdConsumption);
            });
            Set<String> docketKeys = deliveryDockets.keySet();

            docketKeys.forEach((key) -> {
                if (!materialLedgerList.contains(deliveryDockets.get(key)))
                    materialLedgerList.add(deliveryDockets.get(key));
            });
        }

        materialLedgerList.sort(Comparator.comparing(MaterialLedgerResTo::getDate).reversed());

        return materialLedgerList;
    }
    
    public WorkDiaryResp getCreatedWorkDiaries(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(employeeAttendanceRecordSheetsSearchReq),
        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_CREATED_WORK_DIARIES);
        return AppUtils.fromJson(strResponse.getBody(), WorkDiaryResp.class);
    }
    
    public WorkDiaryResp getSubmittedWorkDiaries(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(employeeAttendanceRecordSheetsSearchReq),
        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_SUBMITTED_WORK_DIARIES);
        return AppUtils.fromJson(strResponse.getBody(), WorkDiaryResp.class);
    }

	@Override
	public List<WorkDairyApprStatusReportTO> getWorkDairyApprovalReport(WorkDairyApprovalGetReq workDairyApprovalGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyApprovalGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_WORK_DAIRY_APPROVAL_REPORT);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<PlantActualHrsTO> getPlantsPeriodicalReport(WorkDairyPlantsGetReq workDairyPlantsGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyPlantsGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PLANTS_PERIODICAL_REPORT);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<LabelKeyTO> getPlantDateWiseReport(WorkDairyPlantsGetReq plantsGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantsGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PLANT_DATE_WISE_REPORT);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<LabelKeyTO> getPlantCostCodeWiseReport(WorkDairyPlantsGetReq plantsGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantsGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PLANT_COSTCODE_WISE_REPORT);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<LabelKeyTO> getPlantsIdlePeriodicalReport(WorkDairyPlantsGetReq workDairyPlantsGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyPlantsGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PLANTS_IDLE_PERIODICAL_REPORT);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<LabelKeyTO> getPlantsStandardActual(WorkDairyPlantsGetReq workDairyPlantsGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyPlantsGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PLANTS_STANDARD_ACTUAL);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<LabelKeyTO> getCurrentActivePlants(WorkDairyPlantsGetReq workDairyPlantsGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(workDairyPlantsGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_CURRENT_ACTIVE_PLANTS);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}
/*
	@Override
	public List<MaterialLedgerResTo> getProjectDockets(MaterialFilterReq materialFilterReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + RegisterURLConstants.GET_PROJ_DOCKETS);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}
*/
	
    @Override
    public List<MaterialLedgerResTo> getProjectDockets(MaterialFilterReq materialFilterReq) {
        List<MaterialLedgerResTo> materialLedgerList = new ArrayList<MaterialLedgerResTo>();
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                WorkDairyURLConstants.WORK_DAIRY_PARH_URL + "getProjectDockets");
        LedgerResTo workDairyConsumption = AppUtils.fromJson(strResponse.getBody(), LedgerResTo.class);

        strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_DOCKETS);

        LedgerResTo docketResponse = AppUtils.fromJson(strResponse.getBody(), LedgerResTo.class);

        Map<String, MaterialLedgerResTo> deliveryDockets = docketResponse.getDocketLedgerMap();
        if (workDairyConsumption.getLedgerRes() == null || workDairyConsumption.getLedgerRes().isEmpty()) {
            Set<String> docketKeys = deliveryDockets.keySet();
            docketKeys.forEach((key) -> {
                materialLedgerList.add(deliveryDockets.get(key));
            });
        } else {
            workDairyConsumption.getLedgerRes().forEach((wdConsumption) -> {
                if (deliveryDockets.containsKey(wdConsumption.getDocketId()))
                    materialLedgerList.add(deliveryDockets.get(wdConsumption.getDocketId()));
                materialLedgerList.add(wdConsumption);
            });
            Set<String> docketKeys = deliveryDockets.keySet();

          /*  docketKeys.forEach((key) -> {
                if (!materialLedgerList.contains(deliveryDockets.get(key)))
                    materialLedgerList.add(deliveryDockets.get(key));
            });*/
        }

        materialLedgerList.sort(Comparator.comparing(MaterialLedgerResTo::getDate).reversed());

        return materialLedgerList;
    }
	@Override
	public List<ProgressReportTO> getProgressPeriodicalRecords(WorkDairyPlantsGetReq plantsGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantsGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PROGRESS_PERIODICAL_RECORDS);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<ProgressReportTO> getProgressDateWiseRecords(WorkDairyPlantsGetReq plantsGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantsGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PROGRESS_DATEWISE_RECORDS);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<ProgressReportTO> getProgressActualRecords(WorkDairyPlantsGetReq plantsGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantsGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_PROGRESS_ACTUAL_RECORDS);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<ProgressReportTO> getSorProgressClaimRecords(WorkDairyPlantsGetReq plantsGetReq) {
		ResponseEntity<String> strResponse = null;
		 strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantsGetReq),
	        		WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.GET_SOR_PROGRESS_CLAIM_RECORDS);
		 return AppUtils.fromJson(strResponse.getBody(), List.class);
	}
	
	public WorkDiaryResp deleteWorkDiary( WorkDairyGetReq workDairyGetReq ) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate( AppUtils.toJson( workDairyGetReq ), WorkDairyURLConstants.WORK_DAIRY_PARH_URL + WorkDairyURLConstants.DELETE_WORK_DAIRY );
        return AppUtils.fromJson(strResponse.getBody(), WorkDiaryResp.class);
    }
}