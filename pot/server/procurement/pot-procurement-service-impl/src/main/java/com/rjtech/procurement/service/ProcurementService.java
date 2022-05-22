package com.rjtech.procurement.service;

import java.io.IOException;
import java.util.List;

import com.rjtech.procurement.dto.PreContractTO;
import com.rjtech.procurement.req.*;
import com.rjtech.procurement.resp.*;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.procurement.dto.PurchaseOrderDetailsTO;
import com.rjtech.document.dto.ProjDocFileTO;

public interface ProcurementService {

    PreContractResp getInteranlPreContracts(ProcurementFilterReq procurementFilterReq);

    PreContractResp getPreContractsForRfq(ProcurementFilterReq procurementFilterReq);

    PreContractResp getExteranlPreContracts(ProcurementFilterReq procurementFilterReq);

    PreContractStatusResp getWorkFlowStatus();

    PreContractResp getInternalPreContractDetailsById(ProcurementGetReq procurementGetReq);

    PreContractResp getExternalPreContractDetailsById(ProcurementGetReq procurementGetReq);

    PreContractResp getPreContractCmpQuoteDetails(ProcurementGetReq procurementGetReq);

    PreContractEmpResp getPreContractEmpTypes(ProcurementGetReq procurementGetReq);

    PreContractPlantResp getPreContractPlants(ProcurementGetReq procurementGetReq);

    PreContractMaterialResp getPreContractMaterials(ProcurementGetReq procurementGetReq);

    PreContractServiceResp getPreContractServices(ProcurementGetReq procurementGetReq);

    PreContractSowResp getPreContractSOWTypes(ProcurementGetReq procurementGetReq);

    void savePreContranctDocs(MultipartFile[] files, PrecontractDocSaveReq precontractDocSaveReq) throws IOException;

    void savePreContractDistributionDocs(PrecontractDistributionDocSaveReq precontractDocSaveReq);

    void sendPreContractDocsToCompanies(PrecontractDistributionDocSaveReq precontractDocSaveReq);

    PreContractDocResp getPreContractDocs(ProcurementGetReq procurementGetReq);

    PreContractDistributionDocResp getPreContractDistributionDocs(ProcurementGetReq procurementGetReq);

    PreContractCmpDistributionDocResp getPreContractCmpDistributionDocs(PrecontractDocGetReq precontractDocGetReq);

    PreContractCmpDocResp getPreContractCmpDocs(ProcurementGetReq procurementGetReq);

    PreContractCmpDocResp gPreContractCmpDocsByType(ProcurementGetReq procurementGetReq);

    PreContractReqApprResp getPreContractReqApprs(ProcurementGetReq procurementGetReq);

    Long saveInternalPreContrancts(ProcurementSaveReq procurementSaveReq);

    void savePreContranctsList(PreContractListSaveReq preContractListSaveReq);

    Long saveExternalPreContrancts(ProcurementSaveReq procurementSaveReq);

    Long repeatPOSaveExternalPreContrancts(ProcurementSaveReq procurementSaveReq);

    List<LabelKeyTO> getPruchaseOrdersByPrecontract(ProcurementGetReq procurementGetReq);

    void updatePurchaseOrderSummar(POProcureTypeReq poProcureTypeReq);

    void submitBidQuotation(ProcurementFilterReq procurementFilterReq);

    void savePreContratCompanies(PreContractCmpSaveReq preContractCmpSaveReq);

    PreContractCmpResp getPreContractCompanies(PreContractCmpGetReq preContractCmpGetReq);

    void savePreContratEmpTypes(PreContractEmpSaveReq preContractEmpSaveReq);

    void savePreContratPlants(PreContractPlantSaveReq preContractPlantSaveReq);

    void savePreContratMaterials(PreContractMaterialSaveReq preContractMaterialSaveReq);

    void savePreContratServices(PreContractServiceSaveReq preContractServiceSaveReq);

    void savePreContratSOWTypes(PreContractSowDtlsSaveReq preContractSowDtlsSaveReq);

    void deactivatePreContrancts(ProcurementDelReq procurementDelReq);

    void deactivatePreContranctsList(ProcurementDelReq procurementDelReq);

    void deactivatePreContranctDetails(ProcurementDelReq procurementDelReq);

    void deactivateRfqCompanies(PreContractCmpDelReq preContractCmpDelReq);

    PurchaseOrderResp getPurchaseOrders(PurchaseOrderGetReq purchaseOrderGetReq);

    PreContractResp getPurchaseOrderDetails(PurchaseOrderGetReq purchaseOrderGetReq);

    void savePurchaseOrders(PurchaseOrderSaveReq purchaseOrderSaveReq);

    void repeatPurchaseOrder(PurchaseOrderRepeatSaveReq purchaseOrderRepeatSaveReq);

    // Repeat PO
    //PreContractTO getRepeatPOPreContractTO(PreContractTO preContractTO);
    //Repeatpo
    void saveprocurementporepeatpo(ProcurementPoRepeatpoSaveReq procurementporepeatposavereq);

    // Repeatpo Get
    RepeatPurchaseOrderResp getRepeatPurchaseOrders(RepeatPurchaseOrderGetReq repeatPurchaseOrderGetReq);

    PreContractResp getLatestPreContracts(ProcurementFilterReq procurementFilterReq);

    PreContractCmpResp getPreContractRFQs(ProcurementFilterReq procurementFilterReq);

    void savePreContratCompnayDocs( MultipartFile[] files, PreContratCompnayDocSaveReq preContratCompnayDocSaveReq ) throws IOException;

    LabelKeyTO getProjSettingsForProcurement(ProcurementGetReq procurementGetReq);

    LabelKeyTOResp getPreContarctCostCodeSummary(ProcurementGetReq procurementGetReq);

    PurchaseOrderResp saveSinglePurchaseOrder(SinglePurchaseOrderSaveReq singlePurchaseOrderSaveReq);

    void savePurchaseOrderDetails(PurchaseOrderDetailsTO purchaseOrderDetailsTO);

    void saveTransmittalMsg(DocumentTransmittalMsgReq documentTransmittalMsgReq);

    PurchaseOrderResp getPurchaseOrdersByPrecontractCmpIdAndProjId(PurchaseOrderGetReq purchaseOrderGetReq);

    TermsAndConditionsResp saveTermsAndConditions(SaveTermsAndConditionsReq saveTermsAndConditionsReq);

    TermsAndConditionsResp getTermsAndConditions(GetTermsAndConditionsReq getTermsAndConditionsReq);
    
    ProjDocFileTO downloadProcurementDocs( Long recordId, String category ) throws IOException;
    
    PreContractResp getLatestPreContractsReport(ProcurementFilterReq procurementFilterReq);
    
    PreContractResp getLatestPreContractsCostCodeReports(ProcurementFilterReq procurementFilterReq);

    PreContractResp getLatestPreContractsempTaskreport(ProcurementFilterReq procurementFilterReq);
     
    InvoiceMaterialResp getInvoiceMaterials(InvoiceMaterialGetReq invoiceMaterialGetReq);
    
    ProcurementSubCatResp getProcurementSubCatList(ProcurementSubCatReq procuementCat);

    InvoiceMaterialResp searchInvoiceMaterialsByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq);

  	SearchManpowerResp searchInvoiceManpowerByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq);

  	SearchManpowerResp searchInvoicePlantsByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq);

  	SearchManpowerResp searchInvoiceServicesByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq);

  	InvoiceMaterialResp searchInvoiceSubByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq);
    
}