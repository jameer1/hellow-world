package com.rjtech.procurement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.procurement.constans.ProcurementURLConstants;
import com.rjtech.procurement.req.POProcureTypeReq;
import com.rjtech.procurement.req.PurchaseOrderGetReq;
import com.rjtech.procurement.req.PurchaseOrderInvoiceSaveReq;
import com.rjtech.procurement.resp.PurchaseOrderInvoiceResp;
import com.rjtech.procurement.resp.PurchaseOrderResp;
import com.rjtech.procurement.service.PurchaseOrdeService;

@RestController
@RequestMapping(ProcurementURLConstants.PURCHASE_ORDER_PARH_URL)
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrdeService purchaseOrdeService;
    
    @RequestMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDERS_BY_PROCURETYPE, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPOByProcureType(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(purchaseOrdeService.getPOByProcureType(poProcureTypeReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDERS_BY_PRE_CONTRACT_TYPE, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPOByPreContranctType(@RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(purchaseOrdeService.getPOByPreContranctType(purchaseOrderGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PO_ITEM_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> gePOItemDetails(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(purchaseOrdeService.getPOItemDetails(poProcureTypeReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PO_ITEMS_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPOScheudleItemsByProject(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(purchaseOrdeService.getPOScheudleItemsByProject(poProcureTypeReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDER_INVOICES, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getPurchaseOrderInvoices(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<PurchaseOrderInvoiceResp>(
                purchaseOrdeService.getPurchaseOrderInvoices(purchaseOrderGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_APPROVE_INVOICE, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getApproveInvoice(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrdeService.getApproveInvoice(purchaseOrderGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_RELEASE_PAYMENT_INVOICE, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getReleasePaymentInvoice(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<PurchaseOrderInvoiceResp>(
                purchaseOrdeService.getReleasePaymentInvoice(purchaseOrderGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_INVOICE_PARTICULARS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getInvoiceParticulars(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<PurchaseOrderInvoiceResp>(
                purchaseOrdeService.getInvoiceParticulars(purchaseOrderGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_INVOICE_AMOUNT, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getInvoiceAmount(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrdeService.getInvoiceAmount(purchaseOrderGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_INVOICE_ASSIGN_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getInvoiceAssignCostCodes(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<PurchaseOrderInvoiceResp>(
                purchaseOrdeService.getInvoiceAssignCostCodes(purchaseOrderGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_INVOICE_VENDOR_BANK_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getInvoiceVendorBankDetails(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<PurchaseOrderInvoiceResp>(
                purchaseOrdeService.getInvoiceVendorBankDetails(purchaseOrderGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PURCHASE_ORDER_INVOICES, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> savePurchaseOrderInvoices(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        purchaseOrdeService.savePurchaseOrderInvoices(purchaseOrderInvoiceSaveReq);
        PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
        purchaseOrderGetReq.setPurchaseId(purchaseOrderInvoiceSaveReq.getPurchaseOrderInvoiceDtlTO().getPurId());
        purchaseOrderGetReq.setProjId(purchaseOrderInvoiceSaveReq.getPurchaseOrderInvoiceDtlTO().getProjId());
        purchaseOrderGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = purchaseOrdeService
                .getPurchaseOrderInvoices(purchaseOrderGetReq);
        purchaseOrderInvoiceResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrderInvoiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_MANPOWER_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getManpowerInvoiceDocket(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(purchaseOrdeService.getManpowerInvoiceDocket(poProcureTypeReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PLANT_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPlantInvoiceDocket(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(purchaseOrdeService.getPlantInvoiceDocket(poProcureTypeReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_MATERIAL_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getMaterialInvoiceDocket(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(purchaseOrdeService.getMaterialInvoiceDocket(poProcureTypeReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_MANPOWER_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveManpowerInvoiceDocket(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        purchaseOrdeService.saveManpowerInvoiceDocket(purchaseOrderInvoiceSaveReq);
        AppResp appResp = CommonUtil.getSaveAppResp();
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PLANT_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> savePlantInvoiceDocket(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        purchaseOrdeService.savePlantInvoiceDocket(purchaseOrderInvoiceSaveReq);
        POProcureTypeReq poProcureTypeReq = new POProcureTypeReq();
        poProcureTypeReq.setPurId(purchaseOrderInvoiceSaveReq.getPurId());
        LabelKeyTOResp labelKeyTOResp = purchaseOrdeService.getPlantInvoiceDocket(poProcureTypeReq);
        return new ResponseEntity<LabelKeyTOResp>(labelKeyTOResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_INVOICE_PARTICULARS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> saveInvoiceParticulars(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        purchaseOrdeService.saveInvoiceParticulars(purchaseOrderInvoiceSaveReq);
        PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
        purchaseOrderGetReq.setPurchaseId(purchaseOrderInvoiceSaveReq.getPurId());
        purchaseOrderGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = purchaseOrdeService
                .getInvoiceParticulars(purchaseOrderGetReq);
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrderInvoiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_INVOICE_AMOUNT, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveInvoiceAmount(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        purchaseOrdeService.saveInvoiceAmount(purchaseOrderInvoiceSaveReq);
        AppResp appResp = CommonUtil.getSaveAppResp();
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_INVOICE_ASSIGN_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveInvoiceAssignCostCodes(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        purchaseOrdeService.saveInvoiceAssignCostCodes(purchaseOrderInvoiceSaveReq);
        AppResp appResp = CommonUtil.getSaveAppResp();
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_INVOICE_VENDOR_BANK_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveInvoiceVendorBankDetails(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        purchaseOrdeService.saveInvoiceVendorBankDetails(purchaseOrderInvoiceSaveReq);
        AppResp appResp = CommonUtil.getSaveAppResp();
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_MATERIAL_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> saveMaterialInvoiceDocket(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        purchaseOrdeService.saveMaterialInvoiceDocket(purchaseOrderInvoiceSaveReq);
        POProcureTypeReq poProcureTypeReq = new POProcureTypeReq();
        poProcureTypeReq.setPurId(purchaseOrderInvoiceSaveReq.getPurId());
        LabelKeyTOResp labelKeyTOResp = purchaseOrdeService.getMaterialInvoiceDocket(poProcureTypeReq);
        return new ResponseEntity<LabelKeyTOResp>(labelKeyTOResp, HttpStatus.OK);
    }

    /*@RequestMapping(value = ProcurementURLConstants.SAVE_SERVICE_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> saveServiceInvoiceDocket(@RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
    purchaseOrdeService.saveServiceInvoiceDocket(purchaseOrderInvoiceSaveReq);
    POProcureTypeReq poProcureTypeReq = new POProcureTypeReq();
    poProcureTypeReq.setPurId(purchaseOrderInvoiceSaveReq.getPurId());
    LabelKeyTOResp labelKeyTOResp = purchaseOrdeService.getServiceInvoiceDocket(poProcureTypeReq);
    return new ResponseEntity<LabelKeyTOResp>(labelKeyTOResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.SAVE_SOW_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> saveSowInvoiceDocket(@RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
    purchaseOrdeService.saveSowInvoiceDocket(purchaseOrderInvoiceSaveReq);
    POProcureTypeReq poProcureTypeReq = new POProcureTypeReq();
    poProcureTypeReq.setPurId(purchaseOrderInvoiceSaveReq.getPurId());
    LabelKeyTOResp labelKeyTOResp = purchaseOrdeService.getSowInvoiceDocket(poProcureTypeReq);
    return new ResponseEntity<LabelKeyTOResp>(labelKeyTOResp, HttpStatus.OK);
    }*/

    @RequestMapping(value = ProcurementURLConstants.GET_SOW_PURCHASE_ORDERS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> getSOWPurchaseOrders(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<PurchaseOrderResp>(purchaseOrdeService.getSOWPurchaseOrders(poProcureTypeReq),
                HttpStatus.OK);
    }

}