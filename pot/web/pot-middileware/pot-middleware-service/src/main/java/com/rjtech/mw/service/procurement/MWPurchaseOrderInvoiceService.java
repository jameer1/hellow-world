package com.rjtech.mw.service.procurement;

import org.springframework.web.bind.annotation.RequestBody;

import com.rjtech.common.resp.AppResp;
import com.rjtech.procurement.req.POProcureTypeReq;
import com.rjtech.procurement.req.PurchaseOrderGetReq;
import com.rjtech.procurement.req.PurchaseOrderInvoiceSaveReq;
import com.rjtech.procurement.resp.PurchaseOrderInvoiceResp;
import com.rjtech.procurement.resp.PurchaseOrderResp;

public interface MWPurchaseOrderInvoiceService {

    PurchaseOrderInvoiceResp getPurchaseOrderInvoices(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp getApproveInvoice(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp getReleasePaymentInvoice(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp savePurchaseOrderInvoices(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    AppResp saveInvoiceParticulars(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    AppResp saveInvoiceAmount(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    AppResp saveInvoiceAssignCostCodes(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    AppResp saveInvoiceVendorBankDetails(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    PurchaseOrderResp saveManpowerInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    PurchaseOrderResp savePlantInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    PurchaseOrderResp saveMaterialInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    PurchaseOrderInvoiceResp getInvoiceParticulars(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp getInvoiceAmount(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp getInvoiceAssignCostCodes(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp getInvoiceVendorBankDetails(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderResp getSOWPurchaseOrders(@RequestBody POProcureTypeReq poProcureTypeReq);
}
