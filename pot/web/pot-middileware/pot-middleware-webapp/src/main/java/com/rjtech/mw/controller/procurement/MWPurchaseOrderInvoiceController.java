package com.rjtech.mw.controller.procurement;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.centrallib.req.CompanyFilterReq;
import com.rjtech.centrallib.resp.CompanyResp;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.procurement.MWPurchaseOrderInvoiceService;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.user.MWUserService;
import com.rjtech.procurement.constans.ProcurementURLConstants;
import com.rjtech.procurement.req.POProcureTypeReq;
import com.rjtech.procurement.req.PurchaseOrderGetReq;
import com.rjtech.procurement.req.PurchaseOrderInvoiceSaveReq;
import com.rjtech.procurement.resp.PurchaseOrderInvoiceResp;
import com.rjtech.procurement.resp.PurchaseOrderResp;
import com.rjtech.projsettings.dto.ProjCostStmtDtlTO;
import com.rjtech.projsettings.req.ProjCostStatementsGetReq;
import com.rjtech.projsettings.resp.ProjCostStaementsResp;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.dto.UserTO;
import com.rjtech.user.req.ClientReq;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;
import com.rjtech.user.resp.UserResp;

@RestController
@RequestMapping(ProcurementURLConstants.PURCHASE_ORDER_PARH_URL)
public class MWPurchaseOrderInvoiceController {

    @Autowired
    private MWPurchaseOrderInvoiceService mwPurchaseOrderInvoiceService;

    @Autowired
    private MWUserService mwUserService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @Autowired
    private MWProjectSettingsService mwProjectSettingsService;

    @RequestMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDER_INVOICES, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getPurchaseOrderInvoices(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = mwPurchaseOrderInvoiceService
                .getPurchaseOrderInvoices(purchaseOrderGetReq);
        purchaseOrderInvoiceResp.setUserProjMap(getUsersProjectMap());
        purchaseOrderInvoiceResp.setCompanyMap(getCompanyMap());
        purchaseOrderInvoiceResp.setUsersMap(getUsersMap());

        ProjCostStatementsGetReq projCostItemGetReq = new ProjCostStatementsGetReq();
        projCostItemGetReq.setProjId(purchaseOrderGetReq.getProjId());
        projCostItemGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        ProjCostStaementsResp projCostStaementsResp = mwProjectSettingsService.getProjCostCodeStmts(projCostItemGetReq);
        Map<Long, LabelKeyTO> projCostItemMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO costCodeLabelKeyTO = null;
        for (ProjCostStmtDtlTO projCostStmtDtlTO : projCostStaementsResp.getProjCostStmtDtlTOs()) {
            if (CommonUtil.isNonBlankLong(projCostStmtDtlTO.getCostId())) {
                costCodeLabelKeyTO = new LabelKeyTO();
                costCodeLabelKeyTO.setId(projCostStmtDtlTO.getCostId());
                costCodeLabelKeyTO.setCode(projCostStmtDtlTO.getCode());
                costCodeLabelKeyTO.setName(projCostStmtDtlTO.getName());
                projCostItemMap.put(projCostStmtDtlTO.getCostId(), costCodeLabelKeyTO);
            }

        }
        purchaseOrderInvoiceResp.setProjCostItemMap(projCostItemMap);
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrderInvoiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_APPROVE_INVOICE, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getApproveInvoice(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = mwPurchaseOrderInvoiceService
                .getApproveInvoice(purchaseOrderGetReq);
        purchaseOrderInvoiceResp.setUserProjMap(getUsersProjectMap());
        purchaseOrderInvoiceResp.setCompanyMap(getCompanyMap());
        purchaseOrderInvoiceResp.setUsersMap(getUsersMap());

        ProjCostStatementsGetReq projCostItemGetReq = new ProjCostStatementsGetReq();
        projCostItemGetReq.setProjId(purchaseOrderGetReq.getProjId());
        projCostItemGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        ProjCostStaementsResp projCostStaementsResp = mwProjectSettingsService.getProjCostCodeStmts(projCostItemGetReq);
        Map<Long, LabelKeyTO> projCostItemMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO costCodeLabelKeyTO = null;
        for (ProjCostStmtDtlTO projCostStmtDtlTO : projCostStaementsResp.getProjCostStmtDtlTOs()) {
            if (CommonUtil.isNonBlankLong(projCostStmtDtlTO.getCostId())) {
                costCodeLabelKeyTO = new LabelKeyTO();
                costCodeLabelKeyTO.setId(projCostStmtDtlTO.getCostId());
                costCodeLabelKeyTO.setCode(projCostStmtDtlTO.getCode());
                costCodeLabelKeyTO.setName(projCostStmtDtlTO.getName());
                projCostItemMap.put(projCostStmtDtlTO.getCostId(), costCodeLabelKeyTO);
            }

        }
        purchaseOrderInvoiceResp.setProjCostItemMap(projCostItemMap);
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrderInvoiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_RELEASE_PAYMENT_INVOICE, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getReleasePaymentInvoice(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = mwPurchaseOrderInvoiceService
                .getApproveInvoice(purchaseOrderGetReq);
        purchaseOrderInvoiceResp.setUserProjMap(getUsersProjectMap());
        purchaseOrderInvoiceResp.setCompanyMap(getCompanyMap());
        purchaseOrderInvoiceResp.setUsersMap(getUsersMap());

        ProjCostStatementsGetReq projCostItemGetReq = new ProjCostStatementsGetReq();
        projCostItemGetReq.setProjId(purchaseOrderGetReq.getProjId());
        projCostItemGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        ProjCostStaementsResp projCostStaementsResp = mwProjectSettingsService.getProjCostCodeStmts(projCostItemGetReq);
        Map<Long, LabelKeyTO> projCostItemMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO costCodeLabelKeyTO = null;
        for (ProjCostStmtDtlTO projCostStmtDtlTO : projCostStaementsResp.getProjCostStmtDtlTOs()) {
            if (CommonUtil.isNonBlankLong(projCostStmtDtlTO.getCostId())) {
                costCodeLabelKeyTO = new LabelKeyTO();
                costCodeLabelKeyTO.setId(projCostStmtDtlTO.getCostId());
                costCodeLabelKeyTO.setCode(projCostStmtDtlTO.getCode());
                costCodeLabelKeyTO.setName(projCostStmtDtlTO.getName());
                projCostItemMap.put(projCostStmtDtlTO.getCostId(), costCodeLabelKeyTO);
            }

        }
        purchaseOrderInvoiceResp.setProjCostItemMap(projCostItemMap);
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrderInvoiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PURCHASE_ORDER_INVOICES, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> savePurchaseOrderInvoices(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        mwPurchaseOrderInvoiceService.savePurchaseOrderInvoices(purchaseOrderInvoiceSaveReq);
        PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
        purchaseOrderGetReq.setPurchaseId(purchaseOrderInvoiceSaveReq.getPurId());
        purchaseOrderGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = mwPurchaseOrderInvoiceService
                .getPurchaseOrderInvoices(purchaseOrderGetReq);
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrderInvoiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_MANPOWER_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> saveManpowerInvoiceDocket(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        return new ResponseEntity<PurchaseOrderResp>(
                mwPurchaseOrderInvoiceService.saveManpowerInvoiceDocket(purchaseOrderInvoiceSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PLANT_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> savePlantInvoiceDocket(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        return new ResponseEntity<PurchaseOrderResp>(
                mwPurchaseOrderInvoiceService.savePlantInvoiceDocket(purchaseOrderInvoiceSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_MATERIAL_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> saveMaterialInvoiceDocket(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        return new ResponseEntity<PurchaseOrderResp>(
                mwPurchaseOrderInvoiceService.saveMaterialInvoiceDocket(purchaseOrderInvoiceSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_INVOICE_PARTICULARS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveInvoiceParticulars(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        mwPurchaseOrderInvoiceService.saveInvoiceParticulars(purchaseOrderInvoiceSaveReq);
        AppResp appResp = CommonUtil.getSaveAppResp();
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_INVOICE_AMOUNT, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveInvoiceAmount(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        mwPurchaseOrderInvoiceService.saveInvoiceAmount(purchaseOrderInvoiceSaveReq);
        AppResp appResp = CommonUtil.getSaveAppResp();
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_INVOICE_ASSIGN_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveInvoiceAssignCostCodes(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        mwPurchaseOrderInvoiceService.saveInvoiceAssignCostCodes(purchaseOrderInvoiceSaveReq);
        AppResp appResp = CommonUtil.getSaveAppResp();
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_INVOICE_VENDOR_BANK_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveInvoiceVendorBankDetails(
            @RequestBody PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        mwPurchaseOrderInvoiceService.saveInvoiceVendorBankDetails(purchaseOrderInvoiceSaveReq);
        AppResp appResp = CommonUtil.getSaveAppResp();
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_INVOICE_PARTICULARS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getInvoiceParticulars(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = mwPurchaseOrderInvoiceService
                .getInvoiceParticulars(purchaseOrderGetReq);
        purchaseOrderInvoiceResp.setUserProjMap(getUsersProjectMap());
        purchaseOrderInvoiceResp.setCompanyMap(getCompanyMap());
        purchaseOrderInvoiceResp.setUsersMap(getUsersMap());
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrderInvoiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_INVOICE_AMOUNT, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getInvoiceAmount(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = mwPurchaseOrderInvoiceService
                .getInvoiceAmount(purchaseOrderGetReq);
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrderInvoiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_INVOICE_ASSIGN_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getInvoiceAssignCostCodes(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = mwPurchaseOrderInvoiceService
                .getInvoiceAssignCostCodes(purchaseOrderGetReq);
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrderInvoiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_INVOICE_VENDOR_BANK_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderInvoiceResp> getInvoiceVendorBankDetails(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = mwPurchaseOrderInvoiceService
                .getInvoiceVendorBankDetails(purchaseOrderGetReq);
        return new ResponseEntity<PurchaseOrderInvoiceResp>(purchaseOrderInvoiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_SOW_PURCHASE_ORDERS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> getSOWPurchaseOrders(@RequestBody POProcureTypeReq poProcureTypeReq) {
        PurchaseOrderResp purchaseOrderResp = mwPurchaseOrderInvoiceService.getSOWPurchaseOrders(poProcureTypeReq);
        return new ResponseEntity<PurchaseOrderResp>(purchaseOrderResp, HttpStatus.OK);
    }

    private Map<Long, LabelKeyTO> getUsersProjectMap() {
        UserProjGetReq userProjGetReq = new UserProjGetReq();
        userProjGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        userProjGetReq.setClientId(AppUserUtils.getClientId());
        UserProjResp userProjResp = mwProjLibService.getUserProjects(userProjGetReq);
        Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO userProjLabelKeyTO = null;
        for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
            userProjLabelKeyTO = new LabelKeyTO();
            userProjLabelKeyTO.setId(userProjDetailsTO.getId());
            userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
            userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
            userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
        }
        return userProjMap;
    }

    private Map<Long, LabelKeyTO> getCompanyMap() {
        CompanyFilterReq companyFilterReq = new CompanyFilterReq();
        companyFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        CompanyResp companyResp = mwCentralLiblService.getCompanies(companyFilterReq);
        Map<Long, LabelKeyTO> companyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO companyLabelKeyTO = null;
        for (CompanyTO companyTO : companyResp.getCompanyTOs()) {
            companyLabelKeyTO = new LabelKeyTO();
            companyLabelKeyTO.setId(companyTO.getId());
            companyLabelKeyTO.setCode(companyTO.getCmpCode());
            companyLabelKeyTO.setName(companyTO.getCmpName());
            companyMap.put(companyTO.getId(), companyLabelKeyTO);
        }
        return companyMap;
    }

    private Map<Long, LabelKeyTO> getUsersMap() {
        ClientReq clientReq = new ClientReq();
        clientReq.setStatus(StatusCodes.ACTIVE.getValue());
        clientReq.setClientId(AppUserUtils.getClientId());

        UserResp userResp = mwUserService.getUsers(clientReq);
        Map<Long, LabelKeyTO> usersMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO usersLabelKeyTO = null;
        for (UserTO userTO : userResp.getUsers()) {

            usersLabelKeyTO = new LabelKeyTO();
            usersLabelKeyTO.setId(userTO.getUserId());
            usersLabelKeyTO.setCode(userTO.getUserName());
            usersLabelKeyTO.setName(userTO.getFirstName());
            usersLabelKeyTO.setUnitOfMeasure(userTO.getLastName());
            usersMap.put(userTO.getUserId(), usersLabelKeyTO);
        }
        return usersMap;
    }

}