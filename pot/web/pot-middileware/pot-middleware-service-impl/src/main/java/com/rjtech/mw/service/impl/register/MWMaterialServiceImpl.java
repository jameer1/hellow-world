package com.rjtech.mw.service.impl.register;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.register.MWMaterialService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.procurement.constans.ProcurementURLConstants;
import com.rjtech.register.constans.RegisterURLConstants;
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
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwMaterialService")
@RJSService(modulecode = "mwMaterialService")
@Transactional
public class MWMaterialServiceImpl extends RestConfigServiceImpl implements MWMaterialService {

    public MaterialSchItemsResp getMaterialSchItemsByProjectAndLoc(MaterialSchItemsReq req) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(req), RegisterURLConstants.REGISTER_PARH_URL
                + RegisterURLConstants.GET_MATERIAL_SCH_ITEMS_BY_PROJECT_AND_LOC);
        return AppUtils.fromJson(strResponse.getBody(), MaterialSchItemsResp.class);
    }

    public MaterialProjDocketResp getMaterialProjDockets(MaterialFilterReq materialFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_PROJ_DOCKETS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjDocketResp.class);
    }

    public MaterialProjDocketResp getMaterialProjDocketsByDockType(MaterialGetReq materialGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_PROJ_DOCKETS_BY_DOC_TYPE);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjDocketResp.class);
    }

    public MaterialSchItemsResp getMaterialSchItemsByProjDocket(MaterialGetReq materialGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_PROJ_DOCKET_SCH_ITEMS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialSchItemsResp.class);
    }

    public LabelKeyTOResp getMaterialDeliveryDocketDetails(MaterialGetReq materialGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_DELIVERY_DOCKET_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public MaterialProjResp getProjMaterialSchItems(MaterialGetReq materialGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_MATERIAL_SCH_ITEMS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjResp.class);
    }

    public MaterialProjResp getProjMaterialSch(MaterialFilterReq materialGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_MATERIAL_SCH_ITEMS_SEARCH);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjResp.class);
    }

    public MaterialProjResp getMaterialSchItemsByPurchaseOrder(MaterialGetReq materialGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_SCH_ITEMS_BY_PURCHASE_ORDER);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjResp.class);
    }

    public MaterialProjResp saveProjMaterialSchItems(MaterialProjSaveReq materialProjectSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialProjectSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PROJ_MATERIAL_SCH_ITEMS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjResp.class);
    }

    public MaterialDeliveryDocketResp saveProjMaterialSchDocketDetails(MultipartFile[] files, String materialProjSaveReq) {
    	ResponseEntity<String> strResponse = null;
		
    	strResponse = constructPOSTRestTemplateWithMultipartFiles(getRegisterExchangeUrl(
    			RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PROJ_MATERIAL_SCH_DOCKET_DETAILS), files, 
    			materialProjSaveReq, "materialReq");
        return AppUtils.fromJson(strResponse.getBody(), MaterialDeliveryDocketResp.class);
    }

    public MaterialDeliveryDocketResp getMaterialDeliveryDockets(MaterialGetReq materialGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_DELIVERY_DOCKETS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialDeliveryDocketResp.class);
    }

    public MaterialDeliveryDocketResp getMaterialSchItemDeliveryDockets(MaterialFilterReq materialFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_SCH_ITEM_DELIVERY_DOCKETS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialDeliveryDocketResp.class);
    }

    public MaterialProjConsumptionResp getMaterialProjLeadger(MaterialFilterReq materialFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_PROJ_LEDGERS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjConsumptionResp.class);
    }

    public MaterialProjDocketResp saveMaterialProjDocket(MaterialProjDocketSaveReq materialProjDocketSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialProjDocketSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_MATERIAL_PROJ_DOCKETS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjDocketResp.class);
    }

    public MaterialNotificationResp getMaterialNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialNotificationResp.class);
    }

    public MaterialProjConsumptionResp getMaterialProjConsumption(MaterialFilterReq materialFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_MATERIAL_CONSUMPTION);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjConsumptionResp.class);
    }

    public MaterialProjConsumptionResp getMaterialStoreTransitConsumption(MaterialFilterReq materialFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL
                        + RegisterURLConstants.GET_PROJ_MATERIAL_STORE_TRANSIT_CONSUMPTION);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjConsumptionResp.class);
    }

    public MaterialProjConsumptionResp getMaterialStockPiledConsumption(MaterialFilterReq materialFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL
                        + RegisterURLConstants.GET_PROJ_MATERIAL_STOCK_PILED_CONSUMPTION);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjConsumptionResp.class);
    }

    public MaterialProjConsumptionResp getMaterialDailyIssueSchItems(MaterialFilterReq materialFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_MATERIAL_DAILY_ISSUE_SCH_ITEMS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialProjConsumptionResp.class);
    }

    public MaterialTransferReqApprResp getMaterialTransfers(MaterialTransReq materialTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_TRANESFERS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialTransferReqApprResp.class);
    }

    public MaterialTransferReqApprResp getMaterialTransferDetails(MaterialTransReq materialTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_TRANESFER_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialTransferReqApprResp.class);
    }

    public LabelKeyTOResp getMaterialDetailsForTransfer(MaterialTransReq materialTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_DETAILS_FOR_TRANESFER);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public MaterialTransferReqApprResp saveMaterialTransfers(
            MaterialTransferReqApprSaveReq materialTransferReqApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialTransferReqApprSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_MATERIAL_TRANESFERS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialTransferReqApprResp.class);
    }

    public LabelKeyTOResp getProjSettingsMaterialTransferCheck(MaterialTransReq materialTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_SETTINGS_MATERIAL_TRANSFER);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public LabelKeyTOResp getMaterialSchDetailsForTransfer(MaterialGetReq materialGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(materialGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_SCH_DETAILS_FOR_TRANESFER);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }
    
    //This function to fetch the material project dockets based on project id
    public MaterialProjDocketResp getMaterialProjDocketsByProjId( MaterialGetReq materialGetReq ) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate( AppUtils.toJson( materialGetReq ),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MATERIAL_PROJ_DOCKETS_BY_PROJ_ID );
        return AppUtils.fromJson( strResponse.getBody(), MaterialProjDocketResp.class );
    }
}
