package com.rjtech.mw.service.register;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.register.material.req.MaterialProjDocketSaveReq;
import com.rjtech.register.material.req.MaterialProjSaveReq;
import com.rjtech.register.material.req.MaterialSchItemsReq;
import com.rjtech.register.material.req.MaterialTransReq;
import com.rjtech.register.material.req.MaterialTransferReqApprSaveReq;
import com.rjtech.register.material.resp.MaterialDeliveryDocketResp;
import com.rjtech.register.material.resp.MaterialNotificationResp;
import com.rjtech.register.material.resp.MaterialProjConsumptionResp;
import com.rjtech.register.material.resp.MaterialProjDocketResp;
import com.rjtech.register.material.resp.MaterialProjResp;
import com.rjtech.register.material.resp.MaterialSchItemsResp;
import com.rjtech.register.material.resp.MaterialTransferReqApprResp;

public interface MWMaterialService {

    MaterialProjDocketResp getMaterialProjDockets(MaterialFilterReq materialFilterReq);

    MaterialProjDocketResp getMaterialProjDocketsByDockType(MaterialGetReq materialGetReq);

    MaterialSchItemsResp getMaterialSchItemsByProjDocket(MaterialGetReq materialGetReq);

    LabelKeyTOResp getMaterialDeliveryDocketDetails(MaterialGetReq materialGetReq);

    MaterialProjResp getProjMaterialSchItems(MaterialGetReq materialGetReq);

    MaterialProjResp saveProjMaterialSchItems(MaterialProjSaveReq materialProjectSaveReq);

    MaterialProjResp getMaterialSchItemsByPurchaseOrder(MaterialGetReq materialGetReq);

    MaterialDeliveryDocketResp saveProjMaterialSchDocketDetails(MultipartFile[] files, String materialProjectSaveReq);

    MaterialDeliveryDocketResp getMaterialDeliveryDockets(MaterialGetReq materialGetReq);

    MaterialDeliveryDocketResp getMaterialSchItemDeliveryDockets(MaterialFilterReq materialFilterReq);

    MaterialProjConsumptionResp getMaterialProjLeadger(MaterialFilterReq materialFilterReq);

    MaterialProjDocketResp saveMaterialProjDocket(MaterialProjDocketSaveReq materialProjDocketSaveReq);

    MaterialNotificationResp getMaterialNotifications(NotificationsGetReq notificationsGetReq);

    MaterialProjResp getProjMaterialSch(MaterialFilterReq materialGetReq);

    MaterialSchItemsResp getMaterialSchItemsByProjectAndLoc(MaterialSchItemsReq req);

    MaterialProjConsumptionResp getMaterialProjConsumption(MaterialFilterReq materialFilterReq);

    MaterialProjConsumptionResp getMaterialStoreTransitConsumption(MaterialFilterReq materialFilterReq);

    MaterialProjConsumptionResp getMaterialStockPiledConsumption(MaterialFilterReq materialFilterReq);

    MaterialProjConsumptionResp getMaterialDailyIssueSchItems(MaterialFilterReq materialFilterReq);

    MaterialTransferReqApprResp getMaterialTransfers(MaterialTransReq materialTransReq);

    MaterialTransferReqApprResp getMaterialTransferDetails(MaterialTransReq materialTransReq);

    LabelKeyTOResp getMaterialDetailsForTransfer(MaterialTransReq materialTransReq);

    MaterialTransferReqApprResp saveMaterialTransfers(MaterialTransferReqApprSaveReq materialTransferReqApprSaveReq);

    public LabelKeyTOResp getProjSettingsMaterialTransferCheck(MaterialTransReq materialTransReq);

    LabelKeyTOResp getMaterialSchDetailsForTransfer(MaterialGetReq materialGetReq);

    MaterialProjDocketResp getMaterialProjDocketsByProjId( MaterialGetReq materialGetReq );
}
