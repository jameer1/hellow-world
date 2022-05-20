package com.rjtech.procurement.service;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.procurement.req.POProcureTypeReq;
import com.rjtech.procurement.req.PurchaseOrderGetReq;
import com.rjtech.procurement.req.PurchaseOrderInvoiceSaveReq;
import com.rjtech.procurement.resp.PurchaseOrderInvoiceResp;
import com.rjtech.procurement.resp.PurchaseOrderResp;

public interface PurchaseOrdeService {

    LabelKeyTOResp getPOByProcureType(POProcureTypeReq poProcureTypeReq);

    LabelKeyTOResp getPOItemDetails(POProcureTypeReq procureTypeReq);

    LabelKeyTOResp getPOScheudleItemsByProject(POProcureTypeReq poProcureTypeReq);

    void updatePurchaseOrderSummary(POProcureTypeReq procureTypeReq);

    PurchaseOrderInvoiceResp getPurchaseOrderInvoices(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp getApproveInvoice(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp getReleasePaymentInvoice(PurchaseOrderGetReq purchaseOrderGetReq);

    void savePurchaseOrderInvoices(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    LabelKeyTOResp getManpowerInvoiceDocket(POProcureTypeReq poProcureTypeReq);

    LabelKeyTOResp getPlantInvoiceDocket(POProcureTypeReq poProcureTypeReq);

    LabelKeyTOResp getMaterialInvoiceDocket(POProcureTypeReq poProcureTypeReq);

    void saveManpowerInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    void savePlantInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    void saveMaterialInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    void saveSowInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    void saveInvoiceParticulars(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    void saveInvoiceAmount(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    void saveInvoiceAssignCostCodes(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    void saveInvoiceVendorBankDetails(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq);

    PurchaseOrderInvoiceResp getInvoiceParticulars(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp getInvoiceAmount(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp getInvoiceAssignCostCodes(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderInvoiceResp getInvoiceVendorBankDetails(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderResp getSOWPurchaseOrders(POProcureTypeReq poProcureTypeReq);

	LabelKeyTOResp getPOByPreContranctType(PurchaseOrderGetReq poGetReq);
}
