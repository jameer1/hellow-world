package com.rjtech.mw.service.impl.procurement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rjtech.common.resp.AppResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.procurement.MWPurchaseOrderInvoiceService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.procurement.constans.ProcurementURLConstants;
import com.rjtech.procurement.req.POProcureTypeReq;
import com.rjtech.procurement.req.PurchaseOrderGetReq;
import com.rjtech.procurement.req.PurchaseOrderInvoiceSaveReq;
import com.rjtech.procurement.resp.PurchaseOrderInvoiceResp;
import com.rjtech.procurement.resp.PurchaseOrderResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwPurchaseOrderInvoiceService")
@RJSService(modulecode = "mwPurchaseOrderInvoiceService")
@Transactional
public class MWPurchaseOrderInvoiceServiceImpl extends RestConfigServiceImpl implements MWPurchaseOrderInvoiceService {

    public PurchaseOrderInvoiceResp getPurchaseOrderInvoices(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_PURCHASE_ORDER_INVOICES);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderInvoiceResp.class);
    }

    public PurchaseOrderInvoiceResp savePurchaseOrderInvoices(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderInvoiceSaveReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.SAVE_PURCHASE_ORDER_INVOICES);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderInvoiceResp.class);
    }

    public AppResp saveInvoiceParticulars(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderInvoiceSaveReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.SAVE_INVOICE_PARTICULARS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public AppResp saveInvoiceAmount(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderInvoiceSaveReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.SAVE_INVOICE_AMOUNT);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public AppResp saveInvoiceAssignCostCodes(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderInvoiceSaveReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL
                        + ProcurementURLConstants.SAVE_INVOICE_ASSIGN_COST_CODES);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public AppResp saveInvoiceVendorBankDetails(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderInvoiceSaveReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL
                        + ProcurementURLConstants.SAVE_INVOICE_VENDOR_BANK_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public PurchaseOrderResp saveManpowerInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderInvoiceSaveReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.SAVE_MANPOWER_INVOICE_DOCKET);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderResp.class);
    }

    public PurchaseOrderResp savePlantInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderInvoiceSaveReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.SAVE_PLANT_INVOICE_DOCKET);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderResp.class);
    }

    public PurchaseOrderResp saveMaterialInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderInvoiceSaveReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.SAVE_MATERIAL_INVOICE_DOCKET);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderResp.class);
    }

    public PurchaseOrderInvoiceResp getInvoiceParticulars(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_INVOICE_PARTICULARS);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderInvoiceResp.class);
    }

    public PurchaseOrderInvoiceResp getInvoiceAmount(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_INVOICE_AMOUNT);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderInvoiceResp.class);
    }

    public PurchaseOrderInvoiceResp getInvoiceAssignCostCodes(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL
                        + ProcurementURLConstants.GET_INVOICE_ASSIGN_COST_CODES);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderInvoiceResp.class);
    }

    public PurchaseOrderInvoiceResp getInvoiceVendorBankDetails(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL
                        + ProcurementURLConstants.GET_INVOICE_VENDOR_BANK_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderInvoiceResp.class);
    }

    public PurchaseOrderInvoiceResp getApproveInvoice(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_APPROVE_INVOICE);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderInvoiceResp.class);
    }

    public PurchaseOrderInvoiceResp getReleasePaymentInvoice(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_RELEASE_PAYMENT_INVOICE);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderInvoiceResp.class);
    }

    public PurchaseOrderResp getSOWPurchaseOrders(@RequestBody POProcureTypeReq poProcureTypeReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(poProcureTypeReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_SOW_PURCHASE_ORDERS);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderResp.class);
    }

}