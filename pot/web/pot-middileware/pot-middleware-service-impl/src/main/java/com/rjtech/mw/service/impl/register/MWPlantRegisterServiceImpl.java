package com.rjtech.mw.service.impl.register;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.register.MWPlantRegisterService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.plant.req.PlantChargeOutRatesSaveReq;
import com.rjtech.register.plant.req.PlantDeactivateReq;
import com.rjtech.register.plant.req.PlantDeploymentSaveReq;
import com.rjtech.register.plant.req.PlantDepriciationSalvageSaveReq;
import com.rjtech.register.plant.req.PlantLogRecordsSaveReq;
import com.rjtech.register.plant.req.PlantProjPODeliverySaveReq;
import com.rjtech.register.plant.req.PlantProjRepairGetReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantRegisterDeactivateReq;
import com.rjtech.register.plant.req.PlantRegisterDtlSaveReq;
import com.rjtech.register.plant.req.PlantRegisterGetReq;
import com.rjtech.register.plant.req.PlantRepairSaveReq;
import com.rjtech.register.plant.req.PlantRepairsResp;
import com.rjtech.register.plant.req.PlantServiceHistorySaveReq;
import com.rjtech.register.plant.req.PlantTranferReqApprSaveReq;
import com.rjtech.register.plant.req.PlantTransReq;
import com.rjtech.register.plant.resp.PlantChargeOutRatesResp;
import com.rjtech.register.plant.resp.PlantCurrentStatusResp;
import com.rjtech.register.plant.resp.PlantDeploymentResp;
import com.rjtech.register.plant.resp.PlantDepriciationSalvageResp;
import com.rjtech.register.plant.resp.PlantDocketDtlsResp;
import com.rjtech.register.plant.resp.PlantLogRecordsResp;
import com.rjtech.register.plant.resp.PlantNotificationResp;
import com.rjtech.register.plant.resp.PlantPOResp;
import com.rjtech.register.plant.resp.PlantProjPODeliveryResp;
import com.rjtech.register.plant.resp.PlantProjectSearchResp;
import com.rjtech.register.plant.resp.PlantRegisterDtlResp;
import com.rjtech.register.plant.resp.PlantRegistersDtlOnLoadResp;
import com.rjtech.register.plant.resp.PlantServiceHistoryResp;
import com.rjtech.register.plant.resp.PlantTransferReqApprResp;
import com.rjtech.register.plant.resp.ProjPlantRegMapResp;
import com.rjtech.rjs.core.annotations.RJSService;
import org.springframework.web.multipart.MultipartFile;
import com.rjtech.register.plant.resp.PlantProcureDeliveryResp;

@Service(value = "MWPlantRegisterService")
@RJSService(modulecode = "MWPlantRegisterService")
@Transactional
public class MWPlantRegisterServiceImpl extends RestConfigServiceImpl implements MWPlantRegisterService {
    public PlantRegistersDtlOnLoadResp savePlantRegisterDtls(PlantRegisterDtlSaveReq plantRegisterDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantRegisterDtlSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PLANT_REGISTERS);
        return AppUtils.fromJson(strResponse.getBody(), PlantRegistersDtlOnLoadResp.class);
    }

    public PlantRegisterDtlResp plantRegistersDeactivate(PlantRegisterDeactivateReq plantRegisterDeactivateReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantRegisterDeactivateReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.PLANT_REGISTERS_DEACTIVATE);
        return AppUtils.fromJson(strResponse.getBody(), PlantRegisterDtlResp.class);
    }

    public PlantRegistersDtlOnLoadResp plantRegistersOnLoad(PlantRegisterGetReq plantRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.PLANT_REGISTERS_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), PlantRegistersDtlOnLoadResp.class);
    }

    public PlantTransferReqApprResp getPlantTransfers(PlantTransReq plantTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_TRANSFERS);
        return AppUtils.fromJson(strResponse.getBody(), PlantTransferReqApprResp.class);
    }

    public PlantTransferReqApprResp getPlantTransferDetails(PlantTransReq plantTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_TRANSFER_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), PlantTransferReqApprResp.class);
    }

    public LabelKeyTOResp getPlantTransferReqDetails(PlantTransReq plantTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_TRANSFER_REQUEST_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public PlantTransferReqApprResp savePlantTransfers(PlantTranferReqApprSaveReq plantReqForTransSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantReqForTransSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PLANT_TRANSFERS);
        return AppUtils.fromJson(strResponse.getBody(), PlantTransferReqApprResp.class);
    }

    public PlantChargeOutRatesResp getPlantChargeOutRates(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_CHARGEOUT_RATES);
        return AppUtils.fromJson(strResponse.getBody(), PlantChargeOutRatesResp.class);
    }

    public PlantLogRecordsResp getPlantLogRecords(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_LOG_RECORDS);
        return AppUtils.fromJson(strResponse.getBody(), PlantLogRecordsResp.class);
    }

    public PlantProjPODeliveryResp getPlantProcureDeliveryDtls(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_REG_PLANT_PROCURE_DELIVERY);
        System.out.println("response from getPlantProcureDeliveryDtls function from MWPantRegisterServiceImpl class");
        System.out.println(strResponse);
        return AppUtils.fromJson(strResponse.getBody(), PlantProjPODeliveryResp.class);
    }

    public PlantProjectSearchResp plantProjectPOSearch(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.PLANT_PROJECT_PO_SEARCH);
        return AppUtils.fromJson(strResponse.getBody(), PlantProjectSearchResp.class);
    }

    public PlantDocketDtlsResp plantDocketOnLoad(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.PLANT_DOCKET_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), PlantDocketDtlsResp.class);
    }

    public PlantPOResp getPlantPOByProjId(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJECT_PLANT_PO_By_PROJID);
        return AppUtils.fromJson(strResponse.getBody(), PlantPOResp.class);
    }

    public PlantChargeOutRatesResp savePlantChargeOutRates(PlantChargeOutRatesSaveReq plantChargeOutRatesSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantChargeOutRatesSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PLANT_CHARGEOUT_RATES);
        return AppUtils.fromJson(strResponse.getBody(), PlantChargeOutRatesResp.class);
    }

    public PlantLogRecordsResp savePlantLogRecords(PlantLogRecordsSaveReq plantLogRecordsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantLogRecordsSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PLANT_LOG_RECORDS);
        return AppUtils.fromJson(strResponse.getBody(), PlantLogRecordsResp.class);
    }

    public PlantRepairsResp getPlantRepairs(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_REPAIRS);
        return AppUtils.fromJson(strResponse.getBody(), PlantRepairsResp.class);
    }

    public LabelKeyTOResp getPlantMaterialProjDocketDetails(PlantProjRepairGetReq plantProjRepairGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjRepairGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_MATERIAL_PROJ_DOCKET_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public PlantRepairsResp savePlantRepairs(PlantRepairSaveReq plantRepairsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantRepairsSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PLANT_REPAIRS);
        return AppUtils.fromJson(strResponse.getBody(), PlantRepairsResp.class);
    }

    public PlantServiceHistoryResp getPlantServiceHistory(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_SERVICE_HISTORY);
        return AppUtils.fromJson(strResponse.getBody(), PlantServiceHistoryResp.class);
    }

    public PlantServiceHistoryResp savePlantServiceHistory(PlantServiceHistorySaveReq plantServiceHistorySaveReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantServiceHistorySaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PLANT_SERVICE_HISTORY);
        return AppUtils.fromJson(strResponse.getBody(), PlantServiceHistoryResp.class);
    }

    public PlantProjectSearchResp savePlantProjectPODtls(PlantProjPODeliverySaveReq plantProjectPOSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectPOSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PLANT_PROJRCT_PO_DTLS);
        return AppUtils.fromJson(strResponse.getBody(), PlantProjectSearchResp.class);
    }

    public PlantDepriciationSalvageResp getPlantDepriciationSalvages(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_DEPRISIATION_SALVAGE);
        return AppUtils.fromJson(strResponse.getBody(), PlantDepriciationSalvageResp.class);
    }

    public PlantDepriciationSalvageResp savePlantDepriciationSalvages(
            PlantDepriciationSalvageSaveReq plantDepriciationSalvageSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantDepriciationSalvageSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PLANT_DEPRISIATION_SALVAGE);
        return AppUtils.fromJson(strResponse.getBody(), PlantDepriciationSalvageResp.class);
    }

    public LabelKeyTOResp getPOByProcureType(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PURCHASE_ORDERS_BY_PROCURETYPE);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public PlantDocketDtlsResp getPlantDockets(PlantProjectDtlGetReq plantProjectDtlGetReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_DOCKET_DTLS);
        return AppUtils.fromJson(strResponse.getBody(), PlantDocketDtlsResp.class);
    }

    public PlantLogRecordsResp plantLogRecordsDeactivate(PlantDeactivateReq plantDeactivateReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantDeactivateReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.PLANT_LOG_RECORDS_DEACTIVATE);
        return AppUtils.fromJson(strResponse.getBody(), PlantLogRecordsResp.class);
    }

    public PlantProjPODeliveryResp getRegPlantProcureDeliveryDetails(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_REG_PLANT_PROCURE_DELIVERY);
        return AppUtils.fromJson(strResponse.getBody(), PlantProjPODeliveryResp.class);
    }

    public PlantCurrentStatusResp getPlantCurrentStatus(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_CURRENT_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), PlantCurrentStatusResp.class);
    }

    public PlantDeploymentResp savePlantDeployment(PlantDeploymentSaveReq plantDeploymentSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantDeploymentSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PLANT_DEPLOYMENT);
        return AppUtils.fromJson(strResponse.getBody(), PlantDeploymentResp.class);
    }

    public PlantDeploymentResp getPlantDeploymentOnLoad(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_DEPLOYMENT);
        return AppUtils.fromJson(strResponse.getBody(), PlantDeploymentResp.class);
    }

    public PlantRegistersDtlOnLoadResp getPlantsNotInUserProjects(PlantRegisterGetReq plantRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANTS_NOT_IN_USER_PROJECTS);
        return AppUtils.fromJson(strResponse.getBody(), PlantRegistersDtlOnLoadResp.class);
    }

    public LabelKeyTOResp getPlantAttendence(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantProjectDtlGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_ATTENDENCE);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public PlantNotificationResp getPlantNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANT_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), PlantNotificationResp.class);
    }

    public ProjPlantRegMapResp getMultiProjPlantListMap(PlantRegisterGetReq plantRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MULTI_PROJ_PLANT_LIST_MAP);
        return AppUtils.fromJson(strResponse.getBody(), ProjPlantRegMapResp.class);
    }

    public LabelKeyTOResp getProjSettingsPlantTransferCheck(PlantTransReq plantTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_SETTINGS_PLANT_TRANSFER);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public PlantRegisterDtlResp getPlantsInUserProjects(PlantRegisterGetReq plantRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PLANTS_IN_USER_PROJECTS);
        return AppUtils.fromJson(strResponse.getBody(), PlantRegisterDtlResp.class);
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadPlantDocment(long plantPOId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("plantPOId", plantPOId);

        ResponseEntity<ByteArrayResource> response = null;
        String url = AppUtils.getUrl(getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.DOWNLOAD_REG_PLANT_PROCURE_DELIVERY),
                paramsMap);
        response = constructGETRestTemplate(url, ByteArrayResource.class);
        return response;
    }

    public PlantProcureDeliveryResp savePlantProcureDelivery( MultipartFile[] files, PlantProjPODeliverySaveReq plantPODeliverySaveReq ) {
    	ResponseEntity<String> strResponse = null;
        System.out.println("savePlantProcureDelivery function of MWPlantRegisterServiceImpl");
        System.out.println(plantPODeliverySaveReq);
        strResponse = constructPOSTRestTemplateWithMultipartFiles(getRegisterExchangeUrl(
                (RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_REG_PLANT_PROCURE_DELIVERY)), files,
                AppUtils.toJson(plantPODeliverySaveReq), "plantProcureDeliveryStr");
        System.out.println(strResponse);
        return AppUtils.fromJson( strResponse.getBody(), PlantProcureDeliveryResp.class );
    }
    
    public PlantChargeOutRatesResp savePlantPayableRates(PlantChargeOutRatesSaveReq plantChargeOutRatesSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantChargeOutRatesSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PLANT_PAYABLE_RATES);
        return AppUtils.fromJson(strResponse.getBody(), PlantChargeOutRatesResp.class);
    }
}
