package com.rjtech.mw.service.impl.procurement;

import javax.servlet.annotation.MultipartConfig;

import com.rjtech.procurement.req.*;
import com.rjtech.procurement.resp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.procurement.MWProcurementService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.procurement.constans.ProcurementURLConstants;
import com.rjtech.procurement.dto.PurchaseOrderDetailsTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.io.IOException;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.VendorBankAccountDetailsTO;
import com.rjtech.finance.dto.VendorBankAccountDtlsListTO;

import org.springframework.core.io.ByteArrayResource;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwProcurementService")
@RJSService(modulecode = "mwProcurementService")
@Transactional
@MultipartConfig
public class MWProcurementServiceImpl extends RestConfigServiceImpl implements MWProcurementService {

	private static final Logger log = LoggerFactory.getLogger(MWProcurementServiceImpl.class);
	
    public PreContractResp getInteranlPreContracts(ProcurementFilterReq procurementFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementFilterReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_INTERNAL_PRE_CONTRACTS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }
    
    public LabelKeyTOResp getProjSettingsInternalPreContracts(ProcurementFilterReq procurementFilterReq) {
    	ResponseEntity<String> strResponse = null;
    	strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementFilterReq), 
    			ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PROJ_SETTING_INTERNAL_PRE_CONTRACTS);
    	return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    	
    }

    public PreContractResp getPreContractsForRfq(ProcurementFilterReq procurementFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementFilterReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACTS_FOR_RFQ);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }

    public PreContractResp getExteranlPreContracts(ProcurementFilterReq procurementFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementFilterReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_EXTERNAL_PRE_CONTRACTS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }

    public PreContractStatusResp getWorkFlowStatus() {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(null,
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_WORKFLOW_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractStatusResp.class);
    }

    public PreContractEmpResp getPreContractEmpDetails(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_PRE_CONTRACT_EMP_TYPES_BY_ID);
        return AppUtils.fromJson(strResponse.getBody(), PreContractEmpResp.class);
    }

    public PreContractPlantResp getPreContractPlantDetails(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_PLANTS_BY_ID);
        return AppUtils.fromJson(strResponse.getBody(), PreContractPlantResp.class);
    }

    public PreContractMaterialResp getPreContractMaterialDetails(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_PRE_CONTRACT_MATERIALS_BY_ID);
        return AppUtils.fromJson(strResponse.getBody(), PreContractMaterialResp.class);
    }

    public PreContractServiceResp getPreContratServiceDetails(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_SERVICES_BY_ID);
        return AppUtils.fromJson(strResponse.getBody(), PreContractServiceResp.class);
    }

    public PreContractSowResp getPreContratSOWTypes(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_SOW_BY_ID);
        return AppUtils.fromJson(strResponse.getBody(), PreContractSowResp.class);
    }

    public PreContractDocResp savePreContratDocuments(MultipartFile[] files,
            PrecontractDocSaveReq precontractDocSaveReq) {
        ResponseEntity<String> strResponse = null;
        System.out.println("savePreContratDocuments function of MWProcurementServiceImpl");
        System.out.println(precontractDocSaveReq);
        strResponse = constructPOSTRestTemplateWithMultipartFiles(getProcurementExchangeUrl(
                (ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PRE_CONTRACT_DOCS)), files,
                AppUtils.toJson(precontractDocSaveReq), "precontractDocSaveReqStr");
        System.out.println(strResponse);
        return AppUtils.fromJson(strResponse.getBody(), PreContractDocResp.class);

    }

    public PreContractDistributionDocResp savePreContractDistributionDocs(
            PrecontractDistributionDocSaveReq distributionDocStatusSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(distributionDocStatusSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.SAVE_PRE_CONTRACT_DISTRIBUTION_DOCS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractDistributionDocResp.class);

    }

    public PreContractDistributionDocResp sendPreContractDocsToCompanies(
            PrecontractDistributionDocSaveReq distributionDocStatusSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(distributionDocStatusSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.SEND_PRE_CONTRACT_DOCS_TO_COMPANIES);
        return AppUtils.fromJson(strResponse.getBody(), PreContractDistributionDocResp.class);

    }

    public PreContractDocResp getPreContractDocDetails(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_DOCS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractDocResp.class);
    }

    public PreContractDistributionDocResp getPreContractDistributionDocs(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_PRE_CONTRACT_DISTRIBUTION_DOCS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractDistributionDocResp.class);
    }

    public PreContractCmpDistributionDocResp getPreContractCmpDistributionDocs(
            @RequestBody PrecontractDocGetReq precontractDocGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(precontractDocGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_PRE_CONTRACT_CMP_DISTRIBUTION_DOCS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractCmpDistributionDocResp.class);
    }

    public PreContractCmpDocResp getPreContractCmpDocDetails(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_CMP_DOCS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractCmpDocResp.class);
    }

    public PreContractCmpDocResp gPreContractCmpDocsByType(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_PRE_CONTRACT_CMP_DOCS_BY_TYPE);
        return AppUtils.fromJson(strResponse.getBody(), PreContractCmpDocResp.class);
    }

    public PreContractTypeResp getPreContractTypes(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_TYPES);
        return AppUtils.fromJson(strResponse.getBody(), PreContractTypeResp.class);
    }

    public PreContractReqApprResp getPreContractReqApprs(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_REQ_APPR);
        return AppUtils.fromJson(strResponse.getBody(), PreContractReqApprResp.class);
    }

    public PreContractCmpResp getPreContractCompanies(PreContractCmpGetReq preContractCmpGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(preContractCmpGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_COMPANIES);
        return AppUtils.fromJson(strResponse.getBody(), PreContractCmpResp.class);
    }

    public PreContractResp saveInternalPreContrats(ProcurementSaveReq procurementSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_INTERNAL_PRE_CONTRACTS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }

    public PreContractResp saveExternalPreContrats(ProcurementSaveReq procurementSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_EXTERNAL_PRE_CONTRACTS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }

    public AppResp submitBidQuotation(ProcurementFilterReq procurementFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementFilterReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SUBMIT_BID_QUOTATION);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public PreContractCmpResp savePreContratCompanies(PreContractCmpSaveReq preContractCmpSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(preContractCmpSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PRE_CONTRACT_COMPANIES);
        return AppUtils.fromJson(strResponse.getBody(), PreContractCmpResp.class);
    }

    public PreContractEmpResp savePreContratEmpTypes(PreContractEmpSaveReq preContractEmpSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(preContractEmpSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PRE_CONTRACTS_EMP_TYPES);
        return AppUtils.fromJson(strResponse.getBody(), PreContractEmpResp.class);
    }

    public PreContractPlantResp savePreContratPlants(PreContractPlantSaveReq procurementSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PRE_CONTRACTS_PLANTS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractPlantResp.class);
    }

    public PreContractMaterialResp savePreContratMaterials(PreContractMaterialSaveReq preContractMaterialSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(preContractMaterialSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PRE_CONTRACTS_MATERIALS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractMaterialResp.class);
    }

    public PreContractServiceResp savePreContratServices(PreContractServiceSaveReq preContractServiceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(preContractServiceSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PRE_CONTRACTS_SERVICES);
        return AppUtils.fromJson(strResponse.getBody(), PreContractServiceResp.class);
    }

    public PreContractSowResp savePreContractSOWTypes(PreContractSowDtlsSaveReq preContractSowDtlsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(preContractSowDtlsSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PRE_CONTRACT_SOW_TYPES);
        return AppUtils.fromJson(strResponse.getBody(), PreContractSowResp.class);
    }

    public PreContractResp deactivateInterrnalPreContrancts(ProcurementDelReq procurementDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementDelReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.DEACTIVATE_INTERNAL_PRE_CONTRACTS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }

    public PreContractResp deactivateExternalPreContrancts(ProcurementDelReq procurementDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementDelReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.DEACTIVATE_EXTERNAL_PRE_CONTRACTS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }

    public PreContractOnLoadResp getPreContractInternalOnLoadResp(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_INTERNAL_PRE_CONTRACT_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), PreContractOnLoadResp.class);
    }

    public PreContractOnLoadResp getPreContractExternalOnLoadResp(ProcurementGetReq procurementGetReq)

    {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_EXTERNAL_PRE_CONTRACT_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), PreContractOnLoadResp.class);
    }

    public PreContractOnLoadResp getPreContractCmpQuoteDetails(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_PRE_CONTRACT_CMP_QUOTE_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractOnLoadResp.class);
    }
    
    public InvoiceMaterialResp getInvoiceMaterials(InvoiceMaterialGetReq invoiceMaterialGetReq) {
    	 ResponseEntity<String> strResponse = null;
         strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(invoiceMaterialGetReq),
                 ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_INVOICE_MATERIALS);
         return AppUtils.fromJson(strResponse.getBody(), InvoiceMaterialResp.class);
    }
    public PurchaseOrderResp getPurchaseOrders(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PURCHASE_ORDERS);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderResp.class);
    }

    public PreContractResp getPurchaseOrderDetails(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PURCHASE_ORDER_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }

    public PurchaseOrderResp savePurchaseOrders(PurchaseOrderSaveReq purchaseOrderSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PURCHASE_ORDERS);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderResp.class);
    }

    public PurchaseOrderResp regeneratePurchaseOrders(PurchaseOrderRepeatSaveReq purchaseOrderRepeatSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderRepeatSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.REGENERATE_PURCHASE_ORDER);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderResp.class);
    }
    
    //Repeatpo
    public PurchaseOrderResp saveprocurementporepeatpo(ProcurementPoRepeatpoSaveReq procurementporepeatposavereq) {
        ResponseEntity<String> strResponse = null;
        log.info(" MW procurementporepeatposavereq");
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementporepeatposavereq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PROCUREMENT_PO_REPEATPO);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderResp.class);
    }

    // Get Repeat PO
    public RepeatPurchaseOrderResp getRepeatPurchaseOrders(RepeatPurchaseOrderGetReq repeatPurchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        System.out.println(" ** MW getRepeatPurchaseOrder: ProjId: "+repeatPurchaseOrderGetReq.getProjId() + " : ContractId : "+repeatPurchaseOrderGetReq.getContractId());
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(repeatPurchaseOrderGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_REPEAT_PURCHASE_ORDERS);
        return AppUtils.fromJson(strResponse.getBody(), RepeatPurchaseOrderResp.class);
    }

    public LabelKeyTOResp getPOByProcureType(POProcureTypeReq poProcureTypeReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(poProcureTypeReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL
                        + ProcurementURLConstants.GET_PURCHASE_ORDERS_BY_PROCURETYPE);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }
    
    @Override
    public LabelKeyTOResp getPOByPreContranctType(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL
                        + ProcurementURLConstants.GET_PURCHASE_ORDERS_BY_PRE_CONTRACT_TYPE);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public LabelKeyTOResp getPOScheudleItemsByProject(POProcureTypeReq poProcureTypeReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(poProcureTypeReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_PO_ITEMS_BY_PROJECT);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public LabelKeyTOResp getPOItemDetails(POProcureTypeReq poProcureTypeReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(poProcureTypeReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_PO_ITEM_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public PreContractResp getLatestPreContracts(ProcurementFilterReq procurementFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementFilterReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_LATEST_PRE_CONTRACTS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }
    
    public PreContractResp getLatestPreContractsReport(ProcurementFilterReq procurementFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementFilterReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_INTERNAL_PRE_CONTRACT_ONLOAD_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }
    
    public PreContractResp getLatestPreContractsempTaskreport(ProcurementFilterReq procurementFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementFilterReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_EMP_TASK_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }
    
    public PreContractResp getLatestPreContractsCostCodeReports(ProcurementFilterReq procurementFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementFilterReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_COST_CODE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }
    
    public PreContractCmpResp getPreContractsRFQs(ProcurementFilterReq procurementFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementFilterReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_PRE_CONTRACT_RFQS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractCmpResp.class);
    }

    public PreContractResp savePreContranctsList(PreContractListSaveReq preContractListSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(preContractListSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PRE_CONTRACTS_LIST);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }

    public PreContractCmpDocResp savePreContratCompnayDocs( MultipartFile[] files, PreContratCompnayDocSaveReq preContratCompnayDocSaveReq ) {        
    	ResponseEntity<String> strResponse = null;
        System.out.println("savePreContratCompnayDocs function of MWProcurementServiceImpl");
        System.out.println(preContratCompnayDocSaveReq);
        strResponse = constructPOSTRestTemplateWithMultipartFiles(getProcurementExchangeUrl(
                (ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PRE_CONTRACT_COMPANY_DOCS)), files,
                AppUtils.toJson(preContratCompnayDocSaveReq), "precontractCompanyDocsSaveReqStr");
        System.out.println(strResponse);
        return AppUtils.fromJson(strResponse.getBody(), PreContractCmpDocResp.class);
    }

    public LabelKeyTO getProjSettingsForProcurement(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_PROJ_SETTINGS_FOR_PROCUREMENT);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTO.class);
    }

    public LabelKeyTOResp getPreContarctCostCodeSummary(ProcurementGetReq procurementGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_PRE_CONTRACT_COST_CODE_SUMMARY);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public PreContractResp deactivatePreContrancts(ProcurementDelReq procurementDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementDelReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.DEACTIVATE_PRE_CONTRACTS);
        return AppUtils.fromJson(strResponse.getBody(), PreContractResp.class);
    }

    public LabelKeyTOResp getManpowerInvoiceDocket(POProcureTypeReq poProcureTypeReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(poProcureTypeReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_MANPOWER_INVOICE_DOCKET);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public LabelKeyTOResp getPlantInvoiceDocket(POProcureTypeReq poProcureTypeReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(poProcureTypeReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_PLANT_INVOICE_DOCKET);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public LabelKeyTOResp getMaterialInvoiceDocket(POProcureTypeReq poProcureTypeReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(poProcureTypeReq),
                ProcurementURLConstants.PURCHASE_ORDER_PARH_URL + ProcurementURLConstants.GET_MATERIAL_INVOICE_DOCKET);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public PurchaseOrderResp saveSinglePurchaseOrder(SinglePurchaseOrderSaveReq singlePurchaseOrderSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(singlePurchaseOrderSaveReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_SINGLE_PURCHASE_ORDER);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderResp.class);
    }

    public DocumentTransmittalMsgResp saveTransmittalMsg(DocumentTransmittalMsgReq documentTransmittalMsgReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(documentTransmittalMsgReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_TRANSMITTAL_MSG);
        return AppUtils.fromJson(strResponse.getBody(), DocumentTransmittalMsgResp.class);
    }

    public void savePurchaseOrderDetails(PurchaseOrderDetailsTO purchaseOrderDetailsTO) {
        getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderDetailsTO),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_PURCHASE_ORDER_DETAILS);
    }

    public PurchaseOrderResp getPurchaseOrdersByPrecontractCmpIdAndProjId(PurchaseOrderGetReq purchaseOrderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(purchaseOrderGetReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL
                        + ProcurementURLConstants.GET_PURCHASE_ORDERS_BY_PRECONTRACT_CMP_ID_AND_PROJ_ID);
        return AppUtils.fromJson(strResponse.getBody(), PurchaseOrderResp.class);
    }

    public TermsAndConditionsResp saveTermsAndConditions(SaveTermsAndConditionsReq saveTermsAndConditionsReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(saveTermsAndConditionsReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.SAVE_TERMS_AND_CONDITIONS);
        return AppUtils.fromJson(strResponse.getBody(), TermsAndConditionsResp.class);
    }

    public TermsAndConditionsResp getTermsAndConditions(GetTermsAndConditionsReq getTermsAndConditionsReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(getTermsAndConditionsReq),
                ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.GET_TERMS_AND_CONDITIONS);
        return AppUtils.fromJson(strResponse.getBody(), TermsAndConditionsResp.class);
    }
    
    public ResponseEntity<ByteArrayResource> downloadProcurementDocs( Long recordId, String category ) {
	    Map<String, Object> paramsMap = new HashMap<>();
	    String recId = String.valueOf(recordId);
	    paramsMap.put("recordId", recordId);
	    paramsMap.put("category", category);
	    ResponseEntity<String[]> response = null;
	    String url = AppUtils.getUrl(
	    		getProcurementExchangeUrl(
	                    ProcurementURLConstants.PROCUREMENT_PARH_URL + ProcurementURLConstants.DOWNLOAD_PROCUREMENT_DOCS),
	            paramsMap);
	    System.out.println(url);
	    response = constructGETRestTemplate( url, String[].class );
	    String type="";
	    String[] resp = response.getBody();
	    System.out.println(response.getBody());
	    String fileBasePath = resp[3];
	    System.out.println(fileBasePath);
	    Path fileName = null;    
        byte[] fileBytes = null;
        try
        {
        	Path path = Paths.get(fileBasePath);
    	    fileBytes = Files.readAllBytes(path);
    	    type = Files.probeContentType(path);
            fileName = path.getFileName();             
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
	    return ResponseEntity.ok().contentType(MediaType.parseMediaType(type))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(fileBytes));
    }
    
    
   	@Override
   	public ProcurementSubCatResp getProcurementSubcategoryList(ProcurementSubCatReq procurementCat) {
   		try {
   	    	ResponseEntity<String> strResponse = null;
   	        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(procurementCat),
   	                ProcurementURLConstants.PROCUREMENT_PARH_URL
   	                        + ProcurementURLConstants.GET_PROCUREMENT_SUBCATEGORY_LIST);
   	        return AppUtils.fromJson(strResponse.getBody(), ProcurementSubCatResp.class);
   	    	}catch(Exception e){
   	    		System.out.println("We are in error block");
   	        	e.printStackTrace();
   	        	return null;
   	        }
   	}

   	@Override
   	public InvoiceMaterialResp searchInvoiceMaterialsByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
   		try {
   	    	ResponseEntity<String> strResponse = null;
   	        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(searchInvoiceMaterialsReq),
   	                ProcurementURLConstants.PROCUREMENT_PARH_URL
   	                        + ProcurementURLConstants.SEARCH_INVOICE_MATERIALS_PCNAME);
   	        return AppUtils.fromJson(strResponse.getBody(), InvoiceMaterialResp.class);
   	    	}catch(Exception e){
   	        	e.printStackTrace();
   	        	return null;
   	        }
   	}
   	@Override
   	public SearchManpowerResp searchInvoiceManpowerByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
   		try {
   	    	ResponseEntity<String> strResponse = null;
   	        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(searchInvoiceMaterialsReq),
   	                ProcurementURLConstants.PROCUREMENT_PARH_URL
   	                        + ProcurementURLConstants.SEARCH_INVOICE_MANPOWER_PCNAME);
   	        return AppUtils.fromJson(strResponse.getBody(), SearchManpowerResp.class);
   	    	}catch(Exception e){
   	        	e.printStackTrace();
   	        	return null;
   	        }
   	}
   	@Override
   	public SearchManpowerResp searchInvoicePlantsByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
   		try {
   	    	ResponseEntity<String> strResponse = null;
   	        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(searchInvoiceMaterialsReq),
   	                ProcurementURLConstants.PROCUREMENT_PARH_URL
   	                        + ProcurementURLConstants.SEARCH_INVOICE_PLANTS_BY_PCNAME);
   	        return AppUtils.fromJson(strResponse.getBody(), SearchManpowerResp.class);
   	    	}catch(Exception e){
   	        	e.printStackTrace();
   	        	return null;
   	        }
   	}
   	@Override
   	public SearchManpowerResp searchInvoiceServicesByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
   		try {
   	    	ResponseEntity<String> strResponse = null;
   	        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(searchInvoiceMaterialsReq),
   	                ProcurementURLConstants.PROCUREMENT_PARH_URL
   	                        + ProcurementURLConstants.SEARCH_INVOICE_SERVICES_PCNAME);
   	        return AppUtils.fromJson(strResponse.getBody(), SearchManpowerResp.class);
   	    	}catch(Exception e){
   	        	e.printStackTrace();
   	        	return null;
   	        }
   	}
   	@Override
   	public SearchManpowerResp searchInvoiceSubByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
   		try {
   	    	ResponseEntity<String> strResponse = null;
   	        strResponse = getProcuremntPOSTRestTemplate(AppUtils.toJson(searchInvoiceMaterialsReq),
   	                ProcurementURLConstants.PROCUREMENT_PARH_URL
   	                        + ProcurementURLConstants.SEARCH_INVOICE_SUB_PCNAME);
   	        return AppUtils.fromJson(strResponse.getBody(), SearchManpowerResp.class);
   	    	}catch(Exception e){
   	        	e.printStackTrace();
   	        	return null;
   	        }
   	}

	@Override
	public VendorBankAccountDtlsListTO gtBankDetailsByProjId() {
		try {
   	    	ResponseEntity<String> strResponse = null;
   	        strResponse = getProcuremntPOSTRestTemplate(null,
   	                ProcurementURLConstants.PROCUREMENT_PARH_URL
   	                        + ProcurementURLConstants.GET_BANK_DETAILS_BY_PROJECTId);
   	        return AppUtils.fromJson(strResponse.getBody(), VendorBankAccountDtlsListTO.class);
   	    	}catch(Exception e){
   	        	e.printStackTrace();
   	        	return null;
   	        }
	}
}