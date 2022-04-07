package com.rjtech.mw.service.register;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.plant.req.PlantChargeOutRatesSaveReq;
import com.rjtech.register.plant.req.PlantDeactivateReq;
import com.rjtech.register.plant.req.PlantDeploymentSaveReq;
import com.rjtech.register.plant.req.PlantDepriciationSalvageSaveReq;
import com.rjtech.register.plant.req.PlantLogRecordsSaveReq;
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
import org.springframework.web.multipart.MultipartFile;
import com.rjtech.register.plant.resp.PlantProcureDeliveryResp;
import com.rjtech.register.plant.req.PlantProjPODeliverySaveReq;

public interface MWPlantRegisterService {

    PlantRegistersDtlOnLoadResp savePlantRegisterDtls(PlantRegisterDtlSaveReq plantRegisterDtlSaveReq);

    PlantRegisterDtlResp plantRegistersDeactivate(PlantRegisterDeactivateReq plantRegisterDeactivateReq);

    PlantRegistersDtlOnLoadResp plantRegistersOnLoad(PlantRegisterGetReq plantRegisterGetReq);

    PlantRegistersDtlOnLoadResp getPlantsNotInUserProjects(PlantRegisterGetReq plantRegisterGetReq);

    PlantChargeOutRatesResp getPlantChargeOutRates(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantProjectSearchResp plantProjectPOSearch(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantDocketDtlsResp plantDocketOnLoad(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantPOResp getPlantPOByProjId(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantChargeOutRatesResp savePlantChargeOutRates(PlantChargeOutRatesSaveReq plantChargeOutRatesSaveReq);

    PlantRepairsResp getPlantRepairs(PlantProjectDtlGetReq plantProjectDtlGetReq);

    LabelKeyTOResp getPlantMaterialProjDocketDetails(PlantProjRepairGetReq plantProjRepairGetReq);

    PlantRepairsResp savePlantRepairs(PlantRepairSaveReq plantRepairsSaveReq);

    /*
     * The below methods are used in plant service history
     */
    PlantServiceHistoryResp getPlantServiceHistory(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantServiceHistoryResp savePlantServiceHistory(PlantServiceHistorySaveReq plantServiceHistorySaveReq);

    PlantDepriciationSalvageResp getPlantDepriciationSalvages(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantDepriciationSalvageResp savePlantDepriciationSalvages(
            PlantDepriciationSalvageSaveReq plantDepriciationSalvageSaveReq);

    LabelKeyTOResp getPOByProcureType(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantDocketDtlsResp getPlantDockets(PlantProjectDtlGetReq plantProjectDtlGetReq);

    /*
     * the below methods are used in plant log tab
     */
    PlantLogRecordsResp getPlantLogRecords(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantLogRecordsResp savePlantLogRecords(PlantLogRecordsSaveReq plantLogRecordsSaveReq);

    PlantLogRecordsResp plantLogRecordsDeactivate(PlantDeactivateReq plantDeactivateReq);

    PlantProjPODeliveryResp getPlantProcureDeliveryDtls(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantCurrentStatusResp getPlantCurrentStatus(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantDeploymentResp savePlantDeployment(PlantDeploymentSaveReq plantDeploymentSaveReq);

    PlantDeploymentResp getPlantDeploymentOnLoad(PlantProjectDtlGetReq plantProjectDtlGetReq);

    LabelKeyTOResp getPlantAttendence(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantNotificationResp getPlantNotifications(NotificationsGetReq notificationsGetReq);

    PlantTransferReqApprResp getPlantTransfers(PlantTransReq plantTransReq);

    PlantTransferReqApprResp getPlantTransferDetails(PlantTransReq plantTransReq);

    LabelKeyTOResp getPlantTransferReqDetails(PlantTransReq plantTransReq);

    PlantTransferReqApprResp savePlantTransfers(PlantTranferReqApprSaveReq plantReqForTransSaveReq);

    ProjPlantRegMapResp getMultiProjPlantListMap(PlantRegisterGetReq plantRegisterGetReq);

    PlantRegisterDtlResp getPlantsInUserProjects(PlantRegisterGetReq plantRegisterGetReq);

    public LabelKeyTOResp getProjSettingsPlantTransferCheck(PlantTransReq plantTransReq);

    ResponseEntity<ByteArrayResource> downloadPlantDocment(long plantPOId);
    
    PlantProcureDeliveryResp savePlantProcureDelivery( MultipartFile[] files, PlantProjPODeliverySaveReq plantPODeliverySaveReq );

    PlantChargeOutRatesResp savePlantPayableRates(PlantChargeOutRatesSaveReq plantChargeOutRatesSaveReq);
}
