package com.rjtech.mw.service.procurement;

import com.rjtech.procurement.req.*;
import com.rjtech.procurement.resp.*;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.finance.dto.VendorBankAccountDetailsTO;
import com.rjtech.finance.dto.VendorBankAccountDtlsListTO;
import com.rjtech.procurement.dto.PurchaseOrderDetailsTO;

public interface MWProcurementService {

    PreContractResp getInteranlPreContracts(ProcurementFilterReq procurementFilterReq);
    
    LabelKeyTOResp getProjSettingsInternalPreContracts(ProcurementFilterReq procurementFilterReq);

    PreContractResp getPreContractsForRfq(ProcurementFilterReq procurementFilterReq);

    PreContractResp getExteranlPreContracts(ProcurementFilterReq procurementFilterReq);

    PreContractStatusResp getWorkFlowStatus();

    PreContractEmpResp getPreContractEmpDetails(ProcurementGetReq procurementGetReq);

    PreContractPlantResp getPreContractPlantDetails(ProcurementGetReq procurementGetReq);

    PreContractMaterialResp getPreContractMaterialDetails(ProcurementGetReq procurementGetReq);

    PreContractServiceResp getPreContratServiceDetails(ProcurementGetReq procurementGetReq);

    PreContractSowResp getPreContratSOWTypes(ProcurementGetReq procurementGetReq);

    PreContractDocResp getPreContractDocDetails(ProcurementGetReq procurementGetReq);

    PreContractDistributionDocResp getPreContractDistributionDocs(ProcurementGetReq procurementGetReq);

    PreContractCmpDistributionDocResp getPreContractCmpDistributionDocs(PrecontractDocGetReq precontractDocGetReq);

    PreContractCmpDocResp getPreContractCmpDocDetails(ProcurementGetReq procurementGetReq);

    PreContractCmpDocResp gPreContractCmpDocsByType(ProcurementGetReq procurementGetReq);

    PreContractTypeResp getPreContractTypes(ProcurementGetReq procurementGetReq);

    PreContractReqApprResp getPreContractReqApprs(ProcurementGetReq procurementGetReq);

    PreContractResp saveInternalPreContrats(ProcurementSaveReq procurementSaveReq);

    PreContractResp savePreContranctsList(PreContractListSaveReq preContractListSaveReq);

    PreContractResp saveExternalPreContrats(ProcurementSaveReq procurementSaveReq);

    AppResp submitBidQuotation(ProcurementFilterReq procurementFilterReq);

    PreContractCmpResp getPreContractCompanies(PreContractCmpGetReq preContractCmpGetReq);

    PreContractCmpResp savePreContratCompanies(PreContractCmpSaveReq preContractCmpSaveReq);

    PreContractEmpResp savePreContratEmpTypes(PreContractEmpSaveReq preContractEmpSaveReq);

    PreContractPlantResp savePreContratPlants(PreContractPlantSaveReq preContractPlantSaveReq);

    PreContractMaterialResp savePreContratMaterials(PreContractMaterialSaveReq preContractMaterialSaveReq);

    PreContractServiceResp savePreContratServices(PreContractServiceSaveReq preContractServiceSaveReq);

    PreContractSowResp savePreContractSOWTypes(PreContractSowDtlsSaveReq preContractSowDtlsSaveReq);

    PreContractResp deactivateInterrnalPreContrancts(ProcurementDelReq procurementDelReq);

    PreContractResp deactivatePreContrancts(ProcurementDelReq procurementDelReq);

    PreContractResp deactivateExternalPreContrancts(ProcurementDelReq procurementDelReq);

    PreContractOnLoadResp getPreContractInternalOnLoadResp(ProcurementGetReq procurementGetReq);

    PreContractOnLoadResp getPreContractExternalOnLoadResp(ProcurementGetReq procurementGetReq);

    PreContractOnLoadResp getPreContractCmpQuoteDetails(ProcurementGetReq procurementGetReq);

    PreContractDocResp savePreContratDocuments(MultipartFile[] files, PrecontractDocSaveReq precontractDocSaveReq);

    PreContractDistributionDocResp savePreContractDistributionDocs(
            PrecontractDistributionDocSaveReq distributionDocStatusSaveReq);

    PreContractDistributionDocResp sendPreContractDocsToCompanies(
            PrecontractDistributionDocSaveReq precontractDocSaveReq);

    PurchaseOrderResp getPurchaseOrders(PurchaseOrderGetReq purchaseOrderGetReq);

    PreContractResp getPurchaseOrderDetails(PurchaseOrderGetReq purchaseOrderGetReq);

    PurchaseOrderResp savePurchaseOrders(PurchaseOrderSaveReq purchaseOrderSaveReq);

    PurchaseOrderResp regeneratePurchaseOrders(PurchaseOrderRepeatSaveReq purchaseOrderRepeatSaveReq);
    
    //repeatpo save
    PurchaseOrderResp saveprocurementporepeatpo(ProcurementPoRepeatpoSaveReq procurementporepeatposavereq);

    // repeatpo get
    RepeatPurchaseOrderResp getRepeatPurchaseOrders(RepeatPurchaseOrderGetReq repeatPurchaseOrderGetReq) ;

    LabelKeyTOResp getPOScheudleItemsByProject(POProcureTypeReq poProcureTypeReq);

    LabelKeyTOResp getPOItemDetails(POProcureTypeReq poProcureTypeReq);

    LabelKeyTOResp getPOByProcureType(POProcureTypeReq poProcureTypeReq);

    LabelKeyTOResp getManpowerInvoiceDocket(POProcureTypeReq poProcureTypeReq);

    LabelKeyTOResp getPlantInvoiceDocket(POProcureTypeReq poProcureTypeReq);

    LabelKeyTOResp getMaterialInvoiceDocket(POProcureTypeReq poProcureTypeReq);

    PreContractResp getLatestPreContracts(ProcurementFilterReq procurementFilterReq);

    PreContractCmpResp getPreContractsRFQs(ProcurementFilterReq procurementFilterReq);

    PreContractCmpDocResp savePreContratCompnayDocs( MultipartFile[] files, PreContratCompnayDocSaveReq preContratCompnayDocSaveReq );

    LabelKeyTO getProjSettingsForProcurement(ProcurementGetReq procurementGetReq);

    LabelKeyTOResp getPreContarctCostCodeSummary(ProcurementGetReq procurementGetReq);

    PurchaseOrderResp saveSinglePurchaseOrder(SinglePurchaseOrderSaveReq singlePurchaseOrderSaveReq);

    void savePurchaseOrderDetails(PurchaseOrderDetailsTO purchaseOrderDetailsTO);

    DocumentTransmittalMsgResp saveTransmittalMsg(DocumentTransmittalMsgReq documentTransmittalMsgReq);

    PurchaseOrderResp getPurchaseOrdersByPrecontractCmpIdAndProjId(PurchaseOrderGetReq purchaseOrderGetReq);

    TermsAndConditionsResp saveTermsAndConditions(SaveTermsAndConditionsReq saveTermsAndConditionsReq);

    TermsAndConditionsResp getTermsAndConditions(GetTermsAndConditionsReq getTermsAndConditionsReq);
    
    ResponseEntity<ByteArrayResource> downloadProcurementDocs( Long recordId, String category );
    
    PreContractResp getLatestPreContractsReport(ProcurementFilterReq procurementFilterReq);
    
    PreContractResp getLatestPreContractsempTaskreport(ProcurementFilterReq procurementFilterReq);
    
    PreContractResp getLatestPreContractsCostCodeReports(ProcurementFilterReq procurementFilterReq);
     
    InvoiceMaterialResp getInvoiceMaterials(InvoiceMaterialGetReq invoiceMaterialGetReq);

	LabelKeyTOResp getPOByPreContranctType(PurchaseOrderGetReq purchaseOrderGetReq);
	
    ProcurementSubCatResp getProcurementSubcategoryList(ProcurementSubCatReq procurementCat);

	InvoiceMaterialResp searchInvoiceMaterialsByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq);

	SearchManpowerResp searchInvoiceManpowerByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq);

	SearchManpowerResp searchInvoicePlantsByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq);

	SearchManpowerResp searchInvoiceServicesByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq);

	SearchManpowerResp searchInvoiceSubByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq);

	VendorBankAccountDtlsListTO gtBankDetailsByProjId();
}
