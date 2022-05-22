package com.rjtech.mw.controller.procurement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.procurement.req.*;
import com.rjtech.procurement.resp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.centrallib.dto.MaterialClassTO;
import com.rjtech.centrallib.dto.PlantClassTO;
import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.centrallib.dto.ServiceClassTO;
import com.rjtech.centrallib.dto.StockAndStoreTO;
import com.rjtech.centrallib.req.CompanyFilterReq;
import com.rjtech.centrallib.req.CompanyGetReq;
import com.rjtech.centrallib.req.EmpClassFilterReq;
import com.rjtech.centrallib.req.MaterialClassGetReq;
import com.rjtech.centrallib.req.PlantClassFilterReq;
import com.rjtech.centrallib.req.ProcureCatgFilterReq;
import com.rjtech.centrallib.req.ServiceFiltterReq;
import com.rjtech.centrallib.req.StockFilterReq;
import com.rjtech.centrallib.resp.CompanyResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.ProcureCatgResp;
import com.rjtech.centrallib.resp.ProcurementCompanyResp;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.CountryInfoTO;
import com.rjtech.common.dto.CurrencyTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.WorkFlowStatusTO;
import com.rjtech.common.req.UserGetReq;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.resp.UserModulePermissionResp;
import com.rjtech.common.utils.ActionCodes;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodes;
import com.rjtech.common.utils.ProcurmentWorkFlowStatus;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.finance.dto.VendorBankAccountDetailsTO;
import com.rjtech.finance.dto.VendorBankAccountDtlsListTO;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.common.MWCommonService;
import com.rjtech.mw.service.procurement.MWProcurementService;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.user.MWUserService;
import com.rjtech.procurement.constans.ProcurementURLConstants;
import com.rjtech.procurement.dto.PreContractCmpTO;
import com.rjtech.procurement.dto.PurchaseOrderDetailsTO;
import com.rjtech.projectlib.dto.ProjStoreStockTO;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.req.ProjStoreStockGetReq;
import com.rjtech.projectlib.resp.ProjSowItemsMapResp;
import com.rjtech.projsettings.dto.ProjCostStmtDtlTO;
import com.rjtech.projsettings.req.ProjCostStatementsGetReq;
import com.rjtech.projsettings.resp.ProjCostStaementsResp;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;

@RestController
@RequestMapping(ProcurementURLConstants.PROCUREMENT_PARH_URL)
public class MWProcurementController {

    @Autowired
    private MWProcurementService mwProcurementService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @Autowired
    private MWProjectSettingsService mwProjectSettingsService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @Autowired
    private MWUserService mwUserService;

    @Autowired
    private MWCommonService mwCommonService;

    @RequestMapping(value = ProcurementURLConstants.GET_INTERNAL_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getPreContracts(@RequestBody ProcurementFilterReq procurementFilterReq) {
        PreContractResp preContractResp = mwProcurementService.getInteranlPreContracts(procurementFilterReq);
        preContractResp.setUsersMap(getUsersMap(ModuleCodes.INTERNALREQAPPOVAL.getDesc()));
        preContractResp.setUserProjMap(getUsersProjectMap());
        return new ResponseEntity<PreContractResp>(preContractResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACTS_FOR_RFQ, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getPreContractsForRfq(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        PreContractResp preContractResp = new PreContractResp();
        preContractResp = mwProcurementService.getPreContractsForRfq(procurementFilterReq);
        return new ResponseEntity<PreContractResp>(preContractResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_EXTERNAL_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getExteranlPreContracts(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        PreContractResp preContractResp = mwProcurementService.getExteranlPreContracts(procurementFilterReq);
        preContractResp.setUsersMap(getUsersMap(ModuleCodes.EXTERNALREQAPPOVAL.getDesc()));
        preContractResp.setUserProjMap(getUsersProjectMap());
        return new ResponseEntity<PreContractResp>(preContractResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_WORKFLOW_STATUS, method = RequestMethod.POST)
    public ResponseEntity<String> getWorkFlowStatus(
            @RequestParam(required = false, value = "requestPage") boolean requestPage) {
        PreContractStatusResp preContractStatusResp = new PreContractStatusResp();
        WorkFlowStatusTO workFlowStatusTO = null;
        // If stage1 or stage2 request page show only DRAFT and RETURNED WITH COMMENTS
        if (requestPage) {
            workFlowStatusTO = new WorkFlowStatusTO();
            workFlowStatusTO.setValue(ProcurmentWorkFlowStatus.DRAFT.getValue());
            workFlowStatusTO.setDesc(ProcurmentWorkFlowStatus.DRAFT.getDesc());
            preContractStatusResp.getWorkFlowStatusTOs().add(workFlowStatusTO);

            workFlowStatusTO = new WorkFlowStatusTO();
            workFlowStatusTO.setValue(ProcurmentWorkFlowStatus.RETURNED_WITH_COMMENTS.getValue());
            workFlowStatusTO.setDesc(ProcurmentWorkFlowStatus.RETURNED_WITH_COMMENTS.getDesc());
            preContractStatusResp.getWorkFlowStatusTOs().add(workFlowStatusTO);
            
            workFlowStatusTO = new WorkFlowStatusTO();
            workFlowStatusTO.setValue(ProcurmentWorkFlowStatus.REJECTED.getValue());
            workFlowStatusTO.setDesc(ProcurmentWorkFlowStatus.REJECTED.getDesc());
            preContractStatusResp.getWorkFlowStatusTOs().add(workFlowStatusTO);
            
            workFlowStatusTO = new WorkFlowStatusTO();
            workFlowStatusTO.setValue(ProcurmentWorkFlowStatus.APPROVED.getValue());
            workFlowStatusTO.setDesc(ProcurmentWorkFlowStatus.APPROVED.getDesc());
            preContractStatusResp.getWorkFlowStatusTOs().add(workFlowStatusTO);
            
            workFlowStatusTO = new WorkFlowStatusTO();
            workFlowStatusTO.setValue(ProcurmentWorkFlowStatus.ON_HOLD_WITH_APPROVER.getValue());
            workFlowStatusTO.setDesc(ProcurmentWorkFlowStatus.ON_HOLD_WITH_APPROVER.getDesc());
            preContractStatusResp.getWorkFlowStatusTOs().add(workFlowStatusTO);
            
        } else {
            for (ProcurmentWorkFlowStatus workFlowStatus : ProcurmentWorkFlowStatus.values()) {
                workFlowStatusTO = new WorkFlowStatusTO();
                if (workFlowStatus.equals(ProcurmentWorkFlowStatus.DRAFT)
                        || workFlowStatus.equals(ProcurmentWorkFlowStatus.RETURNED_WITH_COMMENTS)) {
                    continue;
                }
                workFlowStatusTO.setValue(workFlowStatus.getValue());
                workFlowStatusTO.setDesc(workFlowStatus.getDesc());
                preContractStatusResp.getWorkFlowStatusTOs().add(workFlowStatusTO);
            }
        }
        return new ResponseEntity<String>(AppUtils.toJson(preContractStatusResp), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_EMP_TYPES_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractEmpResp> getPreContractEmpDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractEmpResp>(mwProcurementService.getPreContractEmpDetails(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_PLANTS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractPlantResp> getPreContractPlantDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractPlantResp>(
                mwProcurementService.getPreContractPlantDetails(procurementGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_MATERIALS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractMaterialResp> getPreContractMaterialDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractMaterialResp>(
                mwProcurementService.getPreContractMaterialDetails(procurementGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_SERVICES_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractServiceResp> getPreContratServiceDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractServiceResp>(
                mwProcurementService.getPreContratServiceDetails(procurementGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_SOW_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractSowResp> getPreContratSOWTypes(@RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractSowResp>(mwProcurementService.getPreContratSOWTypes(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractDocResp> getPreContractDocDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractDocResp>(mwProcurementService.getPreContractDocDetails(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_DISTRIBUTION_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractDistributionDocResp> getPreContractDistributionDocs(
            @RequestBody ProcurementGetReq procurementGetReq) {

        PreContractDistributionDocResp preContractDistributionDocResp = mwProcurementService
                .getPreContractDistributionDocs(procurementGetReq);

        PreContractCmpGetReq preContractCmpGetReq = new PreContractCmpGetReq();
        preContractCmpGetReq.setPreContractId(procurementGetReq.getContractId());
        preContractCmpGetReq.setStatus(procurementGetReq.getStatus());
        PreContractCmpResp preContractCmpResp = new PreContractCmpResp();
        preContractCmpResp = mwProcurementService.getPreContractCompanies(preContractCmpGetReq);
        List<Long> preContractCmpIds = new ArrayList<Long>();
        for (PreContractCmpTO preContractCmpTO : preContractCmpResp.getPreContractCmpTOs()) {
            preContractCmpIds.add(preContractCmpTO.getCompanyId());
        }
        ProcurementCompanyResp procurementCompanyResp = populatePreContractCmpMap(preContractCmpIds);
        preContractDistributionDocResp.setPreContractCmpTOs(preContractCmpResp.getPreContractCmpTOs());
        preContractDistributionDocResp.setPreContractCompanyMap(procurementCompanyResp.getCompanyMap());
        return new ResponseEntity<PreContractDistributionDocResp>(preContractDistributionDocResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_CMP_DISTRIBUTION_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpDistributionDocResp> getPreContractCmpDistributionDocs(
            @RequestBody PrecontractDocGetReq precontractDocGetReq) {
        return new ResponseEntity<PreContractCmpDistributionDocResp>(
                mwProcurementService.getPreContractCmpDistributionDocs(precontractDocGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_CMP_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpDocResp> getPreContractCmpDocDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        PreContractCmpDocResp preContractCmpDocResp = mwProcurementService
                .getPreContractCmpDocDetails(procurementGetReq);
        preContractCmpDocResp.setCompanyMap(getCompanyMap());
        return new ResponseEntity<PreContractCmpDocResp>(preContractCmpDocResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_CMP_DOCS_BY_TYPE, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpDocResp> gPreContractCmpDocsByType(
            @RequestBody ProcurementGetReq procurementGetReq) {
        PreContractCmpDocResp preContractCmpDocResp = mwProcurementService.gPreContractCmpDocsByType(procurementGetReq);
        preContractCmpDocResp.setCompanyMap(getCompanyMap());
        return new ResponseEntity<PreContractCmpDocResp>(preContractCmpDocResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_TYPES, method = RequestMethod.POST)
    public ResponseEntity<PreContractTypeResp> getPreContractTypes(@RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractTypeResp>(mwProcurementService.getPreContractTypes(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_REQ_APPR, method = RequestMethod.POST)
    public ResponseEntity<PreContractReqApprResp> getPreContractReqApprs(
            @RequestBody ProcurementGetReq procurementGetReq) {

        UserGetReq userGetReq = new UserGetReq();
        userGetReq.setModuleCode(ModuleCodes.PROCUREMENT.getDesc());
        userGetReq.setActionCode(ActionCodes.APPROVE.getDesc());
        userGetReq.setClientId(AppUserUtils.getClientId());

        Map<Long, LabelKeyTO> usersMap = new HashMap<Long, LabelKeyTO>();
        UserModulePermissionResp userModulePermissionResp = mwUserService.getUsersByModulePermission(userGetReq);
        for (LabelKeyTO userLabelKey : userModulePermissionResp.getUsers()) {
            usersMap.put(userLabelKey.getId(), userLabelKey);
        }
        PreContractReqApprResp preContractReqApprResp = mwProcurementService.getPreContractReqApprs(procurementGetReq);
        preContractReqApprResp.setUsersMap(usersMap);

        return new ResponseEntity<PreContractReqApprResp>(preContractReqApprResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_COMPANIES, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpResp> getPreContractCompanies(
            @RequestBody PreContractCmpGetReq preContractCmpGetReq) {
        PreContractCmpResp preContractCmpResp = new PreContractCmpResp();
        preContractCmpResp = mwProcurementService.getPreContractCompanies(preContractCmpGetReq);
        List<Long> preContractCmpIds = new ArrayList<Long>();
        for (PreContractCmpTO preContractCmpTO : preContractCmpResp.getPreContractCmpTOs()) {
            preContractCmpIds.add(preContractCmpTO.getCompanyId());
        }
        ProcurementCompanyResp procurementCompanyResp = populatePreContractCmpMap(preContractCmpIds);
        preContractCmpResp.setUserProjMap(getUsersProjectMap());
        preContractCmpResp.setCompanyMap(getCompanyMap());
        preContractCmpResp.setPreContractCompanyMap(procurementCompanyResp.getCompanyMap());
        return new ResponseEntity<PreContractCmpResp>(preContractCmpResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_COMPANIES, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpResp> savePreContratCompanies(
            @RequestBody PreContractCmpSaveReq preContractCmpSaveReq) {
        return new ResponseEntity<PreContractCmpResp>(
                mwProcurementService.savePreContratCompanies(preContractCmpSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_INTERNAL_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> saveInternalPreContrats(@RequestBody ProcurementSaveReq procurementSaveReq) {
        return new ResponseEntity<PreContractResp>(mwProcurementService.saveInternalPreContrats(procurementSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACTS_LIST, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> savePreContranctsList(
            @RequestBody PreContractListSaveReq preContractListSaveReq) {
        return new ResponseEntity<PreContractResp>(mwProcurementService.savePreContranctsList(preContractListSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_EXTERNAL_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> saveExternalPreContrats(@RequestBody ProcurementSaveReq procurementSaveReq) {
        return new ResponseEntity<PreContractResp>(mwProcurementService.saveExternalPreContrats(procurementSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SUBMIT_BID_QUOTATION, method = RequestMethod.POST)
    public ResponseEntity<AppResp> submitBidQuotation(@RequestBody ProcurementFilterReq procurementFilterReq) {
        return new ResponseEntity<AppResp>(mwProcurementService.submitBidQuotation(procurementFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACTS_EMP_TYPES, method = RequestMethod.POST)
    public ResponseEntity<PreContractEmpResp> savePreContratEmpTypes(
            @RequestBody PreContractEmpSaveReq preContractEmpSaveReq) {
        return new ResponseEntity<PreContractEmpResp>(
                mwProcurementService.savePreContratEmpTypes(preContractEmpSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACTS_PLANTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractPlantResp> savePreContratPlants(
            @RequestBody PreContractPlantSaveReq preContractPlantSaveReq) {
        return new ResponseEntity<PreContractPlantResp>(
                mwProcurementService.savePreContratPlants(preContractPlantSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACTS_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<PreContractMaterialResp> savePreContratMaterials(
            @RequestBody PreContractMaterialSaveReq preContractMaterialSaveReq) {
        return new ResponseEntity<PreContractMaterialResp>(
                mwProcurementService.savePreContratMaterials(preContractMaterialSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACTS_SERVICES, method = RequestMethod.POST)
    public ResponseEntity<PreContractServiceResp> savePreContratServices(
            @RequestBody PreContractServiceSaveReq preContractServiceSaveReq) {
        return new ResponseEntity<PreContractServiceResp>(
                mwProcurementService.savePreContratServices(preContractServiceSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_SOW_TYPES, method = RequestMethod.POST)
    public ResponseEntity<PreContractSowResp> savePreContractSOWTypes(
            @RequestBody PreContractSowDtlsSaveReq preContractSowDtlsSaveReq) {
        return new ResponseEntity<PreContractSowResp>(
                mwProcurementService.savePreContractSOWTypes(preContractSowDtlsSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.DEACTIVATE_PRE_CONTRACTS, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PreContractResp> deactivatePreContrancts(@RequestBody ProcurementDelReq procurementDelReq) {
        return new ResponseEntity<PreContractResp>(mwProcurementService.deactivatePreContrancts(procurementDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.DEACTIVATE_INTERNAL_PRE_CONTRACTS, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PreContractResp> deactivateInterrnalPreContrancts(
            @RequestBody ProcurementDelReq procurementDelReq) {
        return new ResponseEntity<PreContractResp>(
                mwProcurementService.deactivateInterrnalPreContrancts(procurementDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.DEACTIVATE_EXTERNAL_PRE_CONTRACTS, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PreContractResp> deactivateExternalPreContrancts(
            @RequestBody ProcurementDelReq procurementDelReq) {
        return new ResponseEntity<PreContractResp>(
                mwProcurementService.deactivateExternalPreContrancts(procurementDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.GET_INTERNAL_PRE_CONTRACT_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PreContractOnLoadResp> getPreContractInternalOnLoadResp(
            @RequestBody ProcurementGetReq procurementGetReq) {
        PreContractOnLoadResp preContractOnLoadResp = new PreContractOnLoadResp();
        preContractOnLoadResp = mwProcurementService.getPreContractInternalOnLoadResp(procurementGetReq);
        populatePrecontractMaps(procurementGetReq, preContractOnLoadResp);
        preContractOnLoadResp.setUsersMap(getUsersMap(ModuleCodes.INTERNALREQAPPOVAL.getDesc()));
        return new ResponseEntity<PreContractOnLoadResp>(preContractOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_EXTERNAL_PRE_CONTRACT_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PreContractOnLoadResp> getPreContractExternalOnLoadResp(
            @RequestBody ProcurementGetReq procurementGetReq) {

        PreContractOnLoadResp preContractOnLoadResp = new PreContractOnLoadResp();
        preContractOnLoadResp = mwProcurementService.getPreContractExternalOnLoadResp(procurementGetReq);
        preContractOnLoadResp.getCompanyMap().putAll(getCompanyMap());
        populatePrecontractMaps(procurementGetReq, preContractOnLoadResp);
        preContractOnLoadResp.setUsersMap(getUsersMap(ModuleCodes.EXTERNALREQAPPOVAL.getDesc()));

        return new ResponseEntity<PreContractOnLoadResp>(preContractOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_CMP_QUOTE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<PreContractOnLoadResp> getPreContractCmpQuoteDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        PreContractOnLoadResp preContractOnLoadResp = new PreContractOnLoadResp();
        preContractOnLoadResp = mwProcurementService.getPreContractCmpQuoteDetails(procurementGetReq);
        preContractOnLoadResp.setCompanyMap(getCompanyMap());
        populatePrecontractMaps(procurementGetReq, preContractOnLoadResp);
        return new ResponseEntity<PreContractOnLoadResp>(preContractOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractDocResp> savePreContractDistributionDocStatusList(MultipartFile[] files,
                                                                                       String precontractDocSaveReqStr) {
        PrecontractDocSaveReq precontractDocSaveReq = AppUtils.fromJson(precontractDocSaveReqStr,
                PrecontractDocSaveReq.class);
        System.out.println("savePreContractDistributionDocStatusList from MWProcurementController");
        System.out.println(precontractDocSaveReq);
        return new ResponseEntity<PreContractDocResp>(
                mwProcurementService.savePreContratDocuments(files, precontractDocSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SEND_PRE_CONTRACT_DOCS_TO_COMPANIES, method = RequestMethod.POST)
    public ResponseEntity<PreContractDistributionDocResp> sendPreContractDocsToCompanies(
            @RequestBody PrecontractDistributionDocSaveReq precontractDocSaveReq) {
        return new ResponseEntity<PreContractDistributionDocResp>(
                mwProcurementService.sendPreContractDocsToCompanies(precontractDocSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_DISTRIBUTION_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractDistributionDocResp> savePreContractDistributionDocs(
            @RequestBody PrecontractDistributionDocSaveReq precontractDocSaveReq) {
        return new ResponseEntity<PreContractDistributionDocResp>(
                mwProcurementService.savePreContractDistributionDocs(precontractDocSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_COMPANY_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpDocResp> savePreContratCompnayDocs( MultipartFile[] files, 
            String precontractCompanyDocsSaveReqStr ) {
    	System.out.println("savePreContratCompnayDocs from MWProcurementController");
    	PreContratCompnayDocSaveReq preContratCompnayDocSaveReq = AppUtils.fromJson(precontractCompanyDocsSaveReqStr,
    			PreContratCompnayDocSaveReq.class);
        return new ResponseEntity<PreContractCmpDocResp>(
                mwProcurementService.savePreContratCompnayDocs(files, preContratCompnayDocSaveReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_INVOICE_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<InvoiceMaterialResp> getPurchaseOrders(@RequestBody InvoiceMaterialGetReq invoiceMaterialGetReq) {
        return new ResponseEntity<InvoiceMaterialResp>(mwProcurementService.getInvoiceMaterials(invoiceMaterialGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDERS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> getPurchaseOrders(@RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {

        PurchaseOrderResp purchaseOrderResp = mwProcurementService.getPurchaseOrders(purchaseOrderGetReq);
        purchaseOrderResp.getCompanyMap().putAll(getCompanyMap());
        purchaseOrderResp.setUserProjMap(getUsersProjectMap());
        Map map = getUsersMap(ModuleCodes.EXTERNALREQAPPOVAL.getDesc());
        purchaseOrderResp.setUsersMap(map);

        return new ResponseEntity<PurchaseOrderResp>(purchaseOrderResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PURCHASE_ORDERS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> savePurchaseOrders(
            @RequestBody PurchaseOrderSaveReq purchaseOrderSaveReq) {
        return new ResponseEntity<PurchaseOrderResp>(mwProcurementService.savePurchaseOrders(purchaseOrderSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<PreContractOnLoadResp> getPurchaseOrderDetails(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        ProcurementGetReq procurementGetReq = new ProcurementGetReq();

        PreContractOnLoadResp preContractOnLoadResp = new PreContractOnLoadResp();
        PreContractResp preContractResp = mwProcurementService.getPurchaseOrderDetails(purchaseOrderGetReq);

        preContractOnLoadResp.setPreContractTO(preContractResp.getPreContractTOs().get(0));

        populatePrecontractPOMaps(purchaseOrderGetReq, preContractOnLoadResp);
        return new ResponseEntity<PreContractOnLoadResp>(preContractOnLoadResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.REGENERATE_PURCHASE_ORDER, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> regeneratePurchaseOrders(
            @RequestBody PurchaseOrderRepeatSaveReq purchaseOrderRepeatSaveReq) {
        return new ResponseEntity<PurchaseOrderResp>(
                mwProcurementService.regeneratePurchaseOrders(purchaseOrderRepeatSaveReq), HttpStatus.OK);
    }
    // Repeat Save
    @RequestMapping(value = ProcurementURLConstants.SAVE_PROCUREMENT_PO_REPEATPO, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> saveprocurementporepeatpo(
            @RequestBody ProcurementPoRepeatpoSaveReq procurementporepeatposavereq) {
        return new ResponseEntity<PurchaseOrderResp>(
                mwProcurementService.saveprocurementporepeatpo(procurementporepeatposavereq), HttpStatus.OK);
    }

    // Repeat Get
    @RequestMapping(value = ProcurementURLConstants.GET_REPEAT_PURCHASE_ORDERS, method = RequestMethod.POST)
    public ResponseEntity<RepeatPurchaseOrderResp> getRepeatPurchaseOrders(
            @RequestBody RepeatPurchaseOrderGetReq repeatPurchaseOrderGetReq) {
        System.out.println(" ** MW Controller -getRepeatPurchaseOrder: ProjId: "+repeatPurchaseOrderGetReq.getProjId() + " : ContractId : "+repeatPurchaseOrderGetReq.getContractId()
                + " : ContractId : "+repeatPurchaseOrderGetReq.getPurchaseOrderId()
                + " : ClientId : "+repeatPurchaseOrderGetReq.getClientId());

        RepeatPurchaseOrderResp repeatPurchaseOrderResp = mwProcurementService.getRepeatPurchaseOrders(repeatPurchaseOrderGetReq);

        PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
        purchaseOrderGetReq.setProjId(repeatPurchaseOrderGetReq.getProjId());
        purchaseOrderGetReq.setClientId(repeatPurchaseOrderGetReq.getClientId());

        PreContractOnLoadResp preContractOnLoadResp = new PreContractOnLoadResp();

        populatePrecontractPOMaps(purchaseOrderGetReq, preContractOnLoadResp);

        repeatPurchaseOrderResp.setProjEmpClassMap(preContractOnLoadResp.getProjEmpClassMap());
        repeatPurchaseOrderResp.setProjPlantMap(preContractOnLoadResp.getProjPlantMap());
        repeatPurchaseOrderResp.setProjMaterialClassMap(preContractOnLoadResp.getProjMaterialClassMap());
        repeatPurchaseOrderResp.setProjServiceMap(preContractOnLoadResp.getProjServiceMap());
        repeatPurchaseOrderResp.setProjSOWMap(preContractOnLoadResp.getProjSOWMap());
        repeatPurchaseOrderResp.setStoreMap(preContractOnLoadResp.getStoreMap());
        repeatPurchaseOrderResp.setProjStoreMap(preContractOnLoadResp.getProjStoreMap());
        repeatPurchaseOrderResp.setProjCostItemMap(preContractOnLoadResp.getProjCostItemMap());
        repeatPurchaseOrderResp.setProcureCategoryMap(preContractOnLoadResp.getProcureCategoryMap());
        repeatPurchaseOrderResp.setUserProjMap(preContractOnLoadResp.getUserProjMap());
        repeatPurchaseOrderResp.setCompanyMap(preContractOnLoadResp.getCompanyMap());
        repeatPurchaseOrderResp.setUserProjMap(getUsersProjectMap());
        System.out.println("getRepeatPurchaseOrders : UserProjMap:size: "+repeatPurchaseOrderResp.getUserProjMap().size());
        System.out.println("getRepeatPurchaseOrders : UserProjMap: "+repeatPurchaseOrderResp.getUserProjMap());
        return new ResponseEntity<RepeatPurchaseOrderResp>(repeatPurchaseOrderResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDERS_BY_PROCURETYPE, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPOByProcureType(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwProcurementService.getPOByProcureType(poProcureTypeReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDERS_BY_PRE_CONTRACT_TYPE, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPOByPreContranctType(@RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwProcurementService.getPOByPreContranctType(purchaseOrderGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PO_ITEM_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPOItemDetails(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwProcurementService.getPOItemDetails(poProcureTypeReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PO_ITEMS_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPOScheudleItemsByProject(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwProcurementService.getPOScheudleItemsByProject(poProcureTypeReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_MANPOWER_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getManpowerInvoiceDocket(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwProcurementService.getManpowerInvoiceDocket(poProcureTypeReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PLANT_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPlantInvoiceDocket(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwProcurementService.getPlantInvoiceDocket(poProcureTypeReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_MATERIAL_INVOICE_DOCKET, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getMaterialInvoiceDocket(@RequestBody POProcureTypeReq poProcureTypeReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwProcurementService.getMaterialInvoiceDocket(poProcureTypeReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_LATEST_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getLatestPreContracts(
            @RequestBody ProcurementFilterReq procurementFilterReq) {

        Map<Long, LabelKeyTO> userProjMap = getUsersProjectMap();
        PreContractResp preContractResp = mwProcurementService.getLatestPreContracts(procurementFilterReq);
        preContractResp.setUserProjMap(userProjMap);
        Map map = getUsersMap(ModuleCodes.INTERNALREQAPPOVAL.getDesc());
        map.putAll(getUsersMap(ModuleCodes.EXTERNALREQAPPOVAL.getDesc()));
        preContractResp.setUsersMap(map);
        return new ResponseEntity<PreContractResp>(preContractResp, HttpStatus.OK);

    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_INTERNAL_PRE_CONTRACT_ONLOAD_REPORT, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getLatestPreContractsReport(
            @RequestBody ProcurementFilterReq procurementFilterReq) {

        Map<Long, LabelKeyTO> userProjMap = getUsersProjectMap();
        PreContractResp preContractResp = mwProcurementService.getLatestPreContractsReport(procurementFilterReq);
        preContractResp.setUserProjMap(userProjMap);
        Map map = getUsersMap(ModuleCodes.INTERNALREQAPPOVAL.getDesc());
        map.putAll(getUsersMap(ModuleCodes.EXTERNALREQAPPOVAL.getDesc()));
        preContractResp.setUsersMap(map);
        return new ResponseEntity<PreContractResp>(preContractResp, HttpStatus.OK);

    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_EMP_TASK_REPORT, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getLatestPreContractsempTaskreport(
            @RequestBody ProcurementFilterReq procurementFilterReq) {

        Map<Long, LabelKeyTO> userProjMap = getUsersProjectMap();
        PreContractResp preContractResp = mwProcurementService.getLatestPreContractsempTaskreport(procurementFilterReq);
        preContractResp.setUserProjMap(userProjMap);
        Map map = getUsersMap(ModuleCodes.INTERNALREQAPPOVAL.getDesc());
        map.putAll(getUsersMap(ModuleCodes.EXTERNALREQAPPOVAL.getDesc()));
        preContractResp.setUsersMap(map);
        return new ResponseEntity<PreContractResp>(preContractResp, HttpStatus.OK);

    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_COST_CODE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getLatestPreContractsCostCodeReports(
            @RequestBody ProcurementFilterReq procurementFilterReq) {

        Map<Long, LabelKeyTO> userProjMap = getUsersProjectMap();
        PreContractResp preContractResp = mwProcurementService.getLatestPreContractsCostCodeReports(procurementFilterReq);
        preContractResp.setUserProjMap(userProjMap);
        Map map = getUsersMap(ModuleCodes.INTERNALREQAPPOVAL.getDesc());
        map.putAll(getUsersMap(ModuleCodes.EXTERNALREQAPPOVAL.getDesc()));
        preContractResp.setUsersMap(map);
        return new ResponseEntity<PreContractResp>(preContractResp, HttpStatus.OK);

    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_RFQS, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpResp> getPreContractsRFQs(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        PreContractCmpResp preContractCmpResp = mwProcurementService.getPreContractsRFQs(procurementFilterReq);

        UserGetReq userGetReq = new UserGetReq();
        userGetReq.setModuleCode(ModuleCodes.PROCUREMENT.getDesc());
        userGetReq.setActionCode(ActionCodes.APPROVE.getDesc());
        userGetReq.setClientId(AppUserUtils.getClientId());

        preContractCmpResp.setPrecontractReqApprMap(getUsersMap(ModuleCodes.INTERNALREQAPPOVAL.getDesc()));
        preContractCmpResp.setCompanyMap(getCompanyMap());
        preContractCmpResp.setUserProjMap(getUsersProjectMap());

        return new ResponseEntity<PreContractCmpResp>(preContractCmpResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PROJ_SETTINGS_FOR_PROCUREMENT, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTO> getProjSettingsForProcurement(@RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<LabelKeyTO>(mwProcurementService.getProjSettingsForProcurement(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_COST_CODE_SUMMARY, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPreContarctCostCodeSummary(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwProcurementService.getPreContarctCostCodeSummary(procurementGetReq),
                HttpStatus.OK);
    }

    private void populatePrecontractMaps(ProcurementGetReq procurementGetReq,
                                         PreContractOnLoadResp preContractOnLoadResp) {

        EmpClassFilterReq empClassFilterReq = new EmpClassFilterReq();
        empClassFilterReq.setClientId(AppUserUtils.getClientId());
        empClassFilterReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> projEmpClassMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO empclassLabelKeyTO = null;
        List<EmpClassTO> empClassTOs = mwCentralLiblService.getEmpClasses(empClassFilterReq).getEmpClassTOs();
        for (EmpClassTO empClassTO : empClassTOs) {
            empclassLabelKeyTO = new LabelKeyTO();
            empclassLabelKeyTO.setId(empClassTO.getId());
            empclassLabelKeyTO.setCode(empClassTO.getCode());
            empclassLabelKeyTO.setName(empClassTO.getName());
            // empclassLabelKeyTO.setUnitOfMeasure(empClassTO.getMeasureUnitTO().getName());
            if (CommonUtil.objectNotNull(empClassTO.getMeasureUnitTO())) {
                empclassLabelKeyTO.setUnitOfMeasure(empClassTO.getMeasureUnitTO().getName());
            }

            projEmpClassMap.put(empClassTO.getId(), empclassLabelKeyTO);
        }

        PlantClassFilterReq plantClassFilterReq = new PlantClassFilterReq();
        plantClassFilterReq.setClientId(AppUserUtils.getClientId());
        plantClassFilterReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> projPlantMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO plantLabelKeyTO = null;
        List<PlantClassTO> plantClassTOs = mwCentralLiblService.getPlantClasses(plantClassFilterReq).getPlantClassTOs();
        for (PlantClassTO plantClassTO : plantClassTOs) {
            plantLabelKeyTO = new LabelKeyTO();
            plantLabelKeyTO.setId(plantClassTO.getId());
            plantLabelKeyTO.setCode(plantClassTO.getCode());
            plantLabelKeyTO.setName(plantClassTO.getName());
            plantLabelKeyTO.setUnitOfMeasure(plantClassTO.getMeasureUnitTO().getName());
            if (CommonUtil.objectNotNull(plantClassTO.getMeasureUnitTO())) {
                plantLabelKeyTO.setUnitOfMeasure(plantClassTO.getMeasureUnitTO().getName());
            }

            projPlantMap.put(plantClassTO.getId(), plantLabelKeyTO);
        }

        MaterialClassGetReq materialClassGetReq = new MaterialClassGetReq();
        materialClassGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        Map<Long, LabelKeyTO> projMaterialClassMap = new HashMap<Long, LabelKeyTO>();

        MaterialClassResp materialClassResp = mwCentralLiblService.getMaterialItems(materialClassGetReq);
        LabelKeyTO materialLabelKeyTO = null;
        for (MaterialClassTO materialClassTO : materialClassResp.getMaterialClassTOs()) {
            materialLabelKeyTO = new LabelKeyTO();
            materialLabelKeyTO.setId(materialClassTO.getId());
            materialLabelKeyTO.setCode(materialClassTO.getCode());
            materialLabelKeyTO.setName(materialClassTO.getName());
            if (CommonUtil.objectNotNull(materialClassTO.getMeasureUnitTO())) {
                materialLabelKeyTO.setUnitOfMeasure(materialClassTO.getMeasureUnitTO().getName());
            }
            projMaterialClassMap.put(materialClassTO.getId(), materialLabelKeyTO);
        }

        ServiceFiltterReq serviceFiltterReq = new ServiceFiltterReq();
        serviceFiltterReq.setClientId(procurementGetReq.getClientId());
        serviceFiltterReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> projServiceMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO serviceLabelKeyTO = null;
        List<ServiceClassTO> serviceClassTOs = mwCentralLiblService.getServiceClasses(serviceFiltterReq)
                .getServiceClassTOs();
        for (ServiceClassTO serviceClassTO : serviceClassTOs) {
            serviceLabelKeyTO = new LabelKeyTO();
            serviceLabelKeyTO.setId(serviceClassTO.getId());
            serviceLabelKeyTO.setCode(serviceClassTO.getCode());
            serviceLabelKeyTO.setName(serviceClassTO.getName());
            projServiceMap.put(serviceClassTO.getId(), serviceLabelKeyTO);
        }

        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projGetReq.setProjId(procurementGetReq.getProjId());
        ProjSowItemsMapResp projSowItemsMapResp = mwProjLibService.getProjSowItemsMap(projGetReq);

        ProjStoreStockGetReq projStoreStockGetReq = new ProjStoreStockGetReq();
        projStoreStockGetReq.setProjId(procurementGetReq.getProjId());
        projStoreStockGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> projStoreMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO projStoreLabelKeyTO = null;
        List<ProjStoreStockTO> projStoreTOs = mwProjLibService.getProjStoreStocks(projStoreStockGetReq)
                .getProjStoreStockTOs();
        for (ProjStoreStockTO projStoreStockTO : projStoreTOs) {
            projStoreLabelKeyTO = new LabelKeyTO();
            projStoreLabelKeyTO.setId(projStoreStockTO.getId());
            projStoreLabelKeyTO.setCode(projStoreStockTO.getCode());
            projStoreLabelKeyTO.setName(projStoreStockTO.getDesc());
            projStoreMap.put(projStoreStockTO.getId(), projStoreLabelKeyTO);
        }

        StockFilterReq stockFilterReq = new StockFilterReq();
        stockFilterReq.setClientId(procurementGetReq.getClientId());
        stockFilterReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> storeMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO storeLabelKeyTO = null;
        List<StockAndStoreTO> stockAndStoreTOs = mwCentralLiblService.getStock(stockFilterReq).getStockAndStoreTOs();
        for (StockAndStoreTO stockAndStoreTO : stockAndStoreTOs) {
            storeLabelKeyTO = new LabelKeyTO();
            storeLabelKeyTO.setId(stockAndStoreTO.getId());
            storeLabelKeyTO.setCode(stockAndStoreTO.getCode());
            storeLabelKeyTO.setName(stockAndStoreTO.getDesc());
            storeMap.put(stockAndStoreTO.getId(), storeLabelKeyTO);
        }

        ProcureCatgFilterReq procureCatgFilterReq = new ProcureCatgFilterReq();
        procureCatgFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        procureCatgFilterReq.setClientId(procurementGetReq.getClientId());

        Map<Long, LabelKeyTO> procureCategoryMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO procureCategoryLabelKeyTO = null;
        ProcureCatgResp procureCatgResp = mwCentralLiblService.getProcureCatgs(procureCatgFilterReq);
        for (ProcureMentCatgTO procureMentCatgTO : procureCatgResp.getProcureMentCatgTOs()) {

            procureCategoryLabelKeyTO = new LabelKeyTO();
            procureCategoryLabelKeyTO.setId(procureMentCatgTO.getProCatgId());
            procureCategoryLabelKeyTO.setCode(procureMentCatgTO.getCode());
            procureCategoryLabelKeyTO.setName(procureMentCatgTO.getDesc());

            procureCategoryLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCURE_CODE,
                    procureMentCatgTO.getProcurement().getCode());
            procureCategoryLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCURE_NAME,
                    procureMentCatgTO.getProcurement().getName());
            procureCategoryMap.put(procureMentCatgTO.getProCatgId(), procureCategoryLabelKeyTO);
        }
        ProjCostStatementsGetReq projCostStatementsGetReq = new ProjCostStatementsGetReq();
        projCostStatementsGetReq.setProjId(procurementGetReq.getProjId());
        projCostStatementsGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        ProjCostStaementsResp projCostStaementsResp = mwProjectSettingsService
                .getProjCostCodeStmts(projCostStatementsGetReq);
        Map<Long, LabelKeyTO> projCostItemMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO costCodeLabelKeyTO = null;
        for (ProjCostStmtDtlTO projCostStmtDtlTO : projCostStaementsResp.getProjCostStmtDtlTOs()) {
            if (CommonUtil.isNonBlankLong(projCostStmtDtlTO.getId())) {
                costCodeLabelKeyTO = new LabelKeyTO();
                costCodeLabelKeyTO.setId(projCostStmtDtlTO.getId());
                costCodeLabelKeyTO.setCode(projCostStmtDtlTO.getCode());
                costCodeLabelKeyTO.setName(projCostStmtDtlTO.getName());
                projCostItemMap.put(projCostStmtDtlTO.getId(), costCodeLabelKeyTO);
            }

        }

        preContractOnLoadResp.setProjEmpClassMap(projEmpClassMap);
        preContractOnLoadResp.setProjPlantMap(projPlantMap);
        preContractOnLoadResp.setProjMaterialClassMap(projMaterialClassMap);
        preContractOnLoadResp.setProjServiceMap(projServiceMap);
        preContractOnLoadResp.setProjSOWMap(projSowItemsMapResp.getSowItemMap());
        preContractOnLoadResp.setStoreMap(storeMap);
        preContractOnLoadResp.setProjStoreMap(projStoreMap);
        preContractOnLoadResp.setProjCostItemMap(projCostItemMap);
        preContractOnLoadResp.setProcureCategoryMap(procureCategoryMap);
        preContractOnLoadResp.setUserProjMap(getUsersProjectMap());

        List<String> currencyList = preContractOnLoadResp.getCurrencyList();
        for (CountryInfoTO countryInfoTO : mwCommonService.getCountryInfoJSON().getCountryInfoTOs()) {
            if (CommonUtil.isNotBlankStr(countryInfoTO.getCurrencyCode())) {
                currencyList.add(countryInfoTO.getCurrencyCode());
            }
        }

    }

    private void populatePrecontractPOMaps(PurchaseOrderGetReq purchaseOrderGetReq,
                                           PreContractOnLoadResp preContractOnLoadResp) {
        System.out.println("Started:MWProcurementController:populatePrecontractPOMaps");
        EmpClassFilterReq empClassFilterReq = new EmpClassFilterReq();
        empClassFilterReq.setClientId(AppUserUtils.getClientId());
        empClassFilterReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> projEmpClassMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO empclassLabelKeyTO = null;
        List<EmpClassTO> empClassTOs = mwCentralLiblService.getEmpClasses(empClassFilterReq).getEmpClassTOs();
        for (EmpClassTO empClassTO : empClassTOs) {
            empclassLabelKeyTO = new LabelKeyTO();
            empclassLabelKeyTO.setId(empClassTO.getId());
            empclassLabelKeyTO.setCode(empClassTO.getCode());
            empclassLabelKeyTO.setName(empClassTO.getName());
            // TODO check why it is null
            if (empClassTO.getMeasureUnitTO() != null)
                empclassLabelKeyTO.setUnitOfMeasure(empClassTO.getMeasureUnitTO().getName());
            projEmpClassMap.put(empClassTO.getId(), empclassLabelKeyTO);
        }

        PlantClassFilterReq plantClassFilterReq = new PlantClassFilterReq();
        plantClassFilterReq.setClientId(AppUserUtils.getClientId());
        plantClassFilterReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> projPlantMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO plantLabelKeyTO = null;
        List<PlantClassTO> plantClassTOs = mwCentralLiblService.getPlantClasses(plantClassFilterReq).getPlantClassTOs();
        for (PlantClassTO plantClassTO : plantClassTOs) {
            plantLabelKeyTO = new LabelKeyTO();
            plantLabelKeyTO.setId(plantClassTO.getId());
            plantLabelKeyTO.setCode(plantClassTO.getCode());
            plantLabelKeyTO.setName(plantClassTO.getName());
            plantLabelKeyTO.setUnitOfMeasure(plantClassTO.getMeasureUnitTO().getName());
            projPlantMap.put(plantClassTO.getId(), plantLabelKeyTO);
        }

        MaterialClassGetReq materialClassGetReq = new MaterialClassGetReq();
        materialClassGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        Map<Long, LabelKeyTO> projMaterialClassMap = new HashMap<Long, LabelKeyTO>();

        MaterialClassResp materialClassResp = mwCentralLiblService.getMaterialItems(materialClassGetReq);
        LabelKeyTO materialLabelKeyTO = null;
        for (MaterialClassTO materialClassTO : materialClassResp.getMaterialClassTOs()) {
            materialLabelKeyTO = new LabelKeyTO();
            materialLabelKeyTO.setId(materialClassTO.getId());
            materialLabelKeyTO.setCode(materialClassTO.getCode());
            materialLabelKeyTO.setName(materialClassTO.getName());
            if (CommonUtil.objectNotNull(materialClassTO.getMeasureUnitTO())) {
                materialLabelKeyTO.setUnitOfMeasure(materialClassTO.getMeasureUnitTO().getName());
            }
            projMaterialClassMap.put(materialClassTO.getId(), materialLabelKeyTO);
        }

        ServiceFiltterReq serviceFiltterReq = new ServiceFiltterReq();
        serviceFiltterReq.setClientId(purchaseOrderGetReq.getClientId());
        serviceFiltterReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> projServiceMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO serviceLabelKeyTO = null;
        List<ServiceClassTO> serviceClassTOs = mwCentralLiblService.getServiceClasses(serviceFiltterReq)
                .getServiceClassTOs();
        for (ServiceClassTO serviceClassTO : serviceClassTOs) {
            serviceLabelKeyTO = new LabelKeyTO();
            serviceLabelKeyTO.setId(serviceClassTO.getId());
            serviceLabelKeyTO.setCode(serviceClassTO.getCode());
            serviceLabelKeyTO.setName(serviceClassTO.getName());
            projServiceMap.put(serviceClassTO.getId(), serviceLabelKeyTO);
        }

        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projGetReq.setProjId(purchaseOrderGetReq.getProjId());
        ProjSowItemsMapResp projSowItemsMapResp = mwProjLibService.getProjSowItemsMap(projGetReq);

        ProjStoreStockGetReq projStoreStockGetReq = new ProjStoreStockGetReq();
        projStoreStockGetReq.setProjId(purchaseOrderGetReq.getProjId());
        projStoreStockGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> projStoreMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO projStoreLabelKeyTO = null;
        List<ProjStoreStockTO> projStoreTOs = mwProjLibService.getProjStoreStocks(projStoreStockGetReq)
                .getProjStoreStockTOs();
        for (ProjStoreStockTO projStoreStockTO : projStoreTOs) {
            projStoreLabelKeyTO = new LabelKeyTO();
            projStoreLabelKeyTO.setId(projStoreStockTO.getId());
            projStoreLabelKeyTO.setCode(projStoreStockTO.getCode());
            projStoreLabelKeyTO.setName(projStoreStockTO.getDesc());
            projStoreMap.put(projStoreStockTO.getId(), projStoreLabelKeyTO);
        }

        StockFilterReq stockFilterReq = new StockFilterReq();
        stockFilterReq.setClientId(purchaseOrderGetReq.getClientId());
        stockFilterReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> storeMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO storeLabelKeyTO = null;
        List<StockAndStoreTO> stockAndStoreTOs = mwCentralLiblService.getStock(stockFilterReq).getStockAndStoreTOs();
        for (StockAndStoreTO stockAndStoreTO : stockAndStoreTOs) {
            storeLabelKeyTO = new LabelKeyTO();
            storeLabelKeyTO.setId(stockAndStoreTO.getId());
            storeLabelKeyTO.setCode(stockAndStoreTO.getCode());
            storeLabelKeyTO.setName(stockAndStoreTO.getDesc());
            storeMap.put(stockAndStoreTO.getId(), storeLabelKeyTO);
        }

        ProcureCatgFilterReq procureCatgFilterReq = new ProcureCatgFilterReq();
        procureCatgFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        procureCatgFilterReq.setClientId(purchaseOrderGetReq.getClientId());

        Map<Long, LabelKeyTO> procureCategoryMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO procureCategoryLabelKeyTO = null;
        ProcureCatgResp procureCatgResp = mwCentralLiblService.getProcureCatgs(procureCatgFilterReq);
        for (ProcureMentCatgTO procureMentCatgTO : procureCatgResp.getProcureMentCatgTOs()) {

            procureCategoryLabelKeyTO = new LabelKeyTO();
            procureCategoryLabelKeyTO.setId(procureMentCatgTO.getProCatgId());
            procureCategoryLabelKeyTO.setCode(procureMentCatgTO.getCode());
            procureCategoryLabelKeyTO.setName(procureMentCatgTO.getDesc());

            procureCategoryLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCURE_CODE,
                    procureMentCatgTO.getProcurement().getCode());
            procureCategoryLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCURE_NAME,
                    procureMentCatgTO.getProcurement().getName());
            procureCategoryMap.put(procureMentCatgTO.getProCatgId(), procureCategoryLabelKeyTO);
        }
        ProjCostStatementsGetReq projCostStatementsGetReq = new ProjCostStatementsGetReq();
        projCostStatementsGetReq.setProjId(purchaseOrderGetReq.getProjId());
        projCostStatementsGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        ProjCostStaementsResp projCostStaementsResp = mwProjectSettingsService
                .getProjCostCodeStmts(projCostStatementsGetReq);
        Map<Long, LabelKeyTO> projCostItemMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO costCodeLabelKeyTO = null;
        for (ProjCostStmtDtlTO projCostStmtDtlTO : projCostStaementsResp.getProjCostStmtDtlTOs()) {
            if (CommonUtil.isNonBlankLong(projCostStmtDtlTO.getId())) {
                costCodeLabelKeyTO = new LabelKeyTO();
                costCodeLabelKeyTO.setId(projCostStmtDtlTO.getId());
                costCodeLabelKeyTO.setCode(projCostStmtDtlTO.getCode());
                costCodeLabelKeyTO.setName(projCostStmtDtlTO.getName());
                projCostItemMap.put(projCostStmtDtlTO.getId(), costCodeLabelKeyTO);
            }

        }

        preContractOnLoadResp.setProjEmpClassMap(projEmpClassMap);
        preContractOnLoadResp.setProjPlantMap(projPlantMap);
        preContractOnLoadResp.setProjMaterialClassMap(projMaterialClassMap);
        preContractOnLoadResp.setProjServiceMap(projServiceMap);
        preContractOnLoadResp.setProjSOWMap(projSowItemsMapResp.getSowItemMap());
        preContractOnLoadResp.setStoreMap(storeMap);
        preContractOnLoadResp.setProjStoreMap(projStoreMap);
        preContractOnLoadResp.setProjCostItemMap(projCostItemMap);
        preContractOnLoadResp.setProcureCategoryMap(procureCategoryMap);
        preContractOnLoadResp.setUserProjMap(getUsersProjectMap());
        preContractOnLoadResp.setCompanyMap(getCompanyMap());

    }

    private Map<Long, LabelKeyTO> getUsersMap(String moduleCode) {

        Map<Long, LabelKeyTO> usersMap = new HashMap<Long, LabelKeyTO>();
        UserGetReq userGetReq = new UserGetReq();
        userGetReq.setModuleCode(moduleCode);
        userGetReq.setActionCode(ActionCodes.APPROVE.getDesc());
        userGetReq.setClientId(AppUserUtils.getClientId());

        UserModulePermissionResp userModulePermissionResp = mwUserService.getUsersByModulePermission(userGetReq);
        for (LabelKeyTO userLabelKey : userModulePermissionResp.getUsers()) {
            usersMap.put(userLabelKey.getId(), userLabelKey);
        }

        userGetReq = new UserGetReq();
        userGetReq.setModuleCode(moduleCode);
        userGetReq.setActionCode(ActionCodes.SUBMIT.getDesc());
        userGetReq.setClientId(AppUserUtils.getClientId());

        userModulePermissionResp = mwUserService.getUsersByModulePermission(userGetReq);
        for (LabelKeyTO userLabelKey : userModulePermissionResp.getUsers()) {
            usersMap.put(userLabelKey.getId(), userLabelKey);
        }
        return usersMap;
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

    private ProcurementCompanyResp populatePreContractCmpMap(List<Long> preContractCmpIds) {
        ProcurementCompanyResp procurementCompanyResp = new ProcurementCompanyResp();
        if (CommonUtil.isListHasData(preContractCmpIds)) {
            CompanyGetReq companyGetReq = new CompanyGetReq();
            companyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
            companyGetReq.setCmpIds(preContractCmpIds);
            procurementCompanyResp = mwCentralLiblService.getCompaniesDetailsByCmpIds(companyGetReq);
        }
        return procurementCompanyResp;
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_SINGLE_PURCHASE_ORDER, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> saveSinglePurchaseOrder(
            @RequestBody SinglePurchaseOrderSaveReq singlePurchaseOrderSaveReq) {
        return new ResponseEntity<PurchaseOrderResp>(
                mwProcurementService.saveSinglePurchaseOrder(singlePurchaseOrderSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_TRANSMITTAL_MSG, method = RequestMethod.POST)
    public ResponseEntity<DocumentTransmittalMsgResp> saveTransmittalMsg(
            @RequestBody DocumentTransmittalMsgReq documentTransmittalMsgReq) {
        return new ResponseEntity<DocumentTransmittalMsgResp>(
                mwProcurementService.saveTransmittalMsg(documentTransmittalMsgReq), HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PURCHASE_ORDER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<Void> savePurchaseOrderDetails(@RequestBody PurchaseOrderDetailsTO purchaseOrderDetailsTO) {
        mwProcurementService.savePurchaseOrderDetails(purchaseOrderDetailsTO);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @PostMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDERS_BY_PRECONTRACT_CMP_ID_AND_PROJ_ID)
    public ResponseEntity<PurchaseOrderResp> getPurchaseOrdersByPrecontractCmpIdAndProjId(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {

        PurchaseOrderResp purchaseOrderResp = mwProcurementService
                .getPurchaseOrdersByPrecontractCmpIdAndProjId(purchaseOrderGetReq);

        return new ResponseEntity<PurchaseOrderResp>(purchaseOrderResp, HttpStatus.OK);
    }

    @PostMapping(value = ProcurementURLConstants.SAVE_TERMS_AND_CONDITIONS)
    public ResponseEntity<TermsAndConditionsResp> saveTermsAndConditions(
            @RequestBody SaveTermsAndConditionsReq saveTermsAndConditionsReq) {

        TermsAndConditionsResp termsAndConditionsResp = mwProcurementService
                .saveTermsAndConditions(saveTermsAndConditionsReq);
        return new ResponseEntity<TermsAndConditionsResp>(termsAndConditionsResp, HttpStatus.OK);
    }

    @PostMapping(value = ProcurementURLConstants.GET_TERMS_AND_CONDITIONS)
    public ResponseEntity<TermsAndConditionsResp> getTermsAndConditions(
            @RequestBody GetTermsAndConditionsReq getTermsAndConditionsReq) {

        TermsAndConditionsResp termsAndConditionsResp = mwProcurementService
                .getTermsAndConditions(getTermsAndConditionsReq);
        return new ResponseEntity<TermsAndConditionsResp>(termsAndConditionsResp, HttpStatus.OK);
    }
    
    @GetMapping(value = ProcurementURLConstants.DOWNLOAD_PROCUREMENT_DOCS)
    public ResponseEntity<ByteArrayResource> downloadProcurementDocs( @RequestParam("recordId") Long recordId, @RequestParam("category") String category ) {
    	System.out.println("function:"+category);
    	System.out.println("downloadProcurementDocs function of MWProcurementController class");
        return mwProcurementService.downloadProcurementDocs( recordId, category );
    }
    
    @PostMapping(value = ProcurementURLConstants.GET_PROCUREMENT_SUBCATEGORY_LIST)
    public ResponseEntity<ProcurementSubCatResp> getProcurementSubcategoryList(@RequestBody ProcurementSubCatReq procurementSubCatReq) {
    	System.out.println("getProcurementSubcategoryList function of MWProcurementController class");
    	return new ResponseEntity<ProcurementSubCatResp>(mwProcurementService.getProcurementSubcategoryList( procurementSubCatReq ), HttpStatus.OK);
         
    }
    @RequestMapping(value = ProcurementURLConstants.SEARCH_INVOICE_MATERIALS_PCNAME, method = RequestMethod.POST)
    public ResponseEntity<InvoiceMaterialResp> searchInvoiceMaterialsByPCName(@RequestBody SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
        return new ResponseEntity<InvoiceMaterialResp>(mwProcurementService.searchInvoiceMaterialsByPCName(searchInvoiceMaterialsReq),
                HttpStatus.OK);
    }
   
    @RequestMapping(value = ProcurementURLConstants.SEARCH_INVOICE_MANPOWER_PCNAME, method = RequestMethod.POST)
    public ResponseEntity<SearchManpowerResp> searchInvoiceMByPCName(@RequestBody SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
        return new ResponseEntity<SearchManpowerResp>(mwProcurementService.searchInvoiceManpowerByPCName(searchInvoiceMaterialsReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.SEARCH_INVOICE_PLANTS_BY_PCNAME, method = RequestMethod.POST)
    public ResponseEntity<SearchManpowerResp> searchInvoicePlantsByPCName(@RequestBody SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
        return new ResponseEntity<SearchManpowerResp>(mwProcurementService.searchInvoicePlantsByPCName(searchInvoiceMaterialsReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.SEARCH_INVOICE_SERVICES_PCNAME, method = RequestMethod.POST)
    public ResponseEntity<SearchManpowerResp> searchInvoiceServicesByPCName(@RequestBody SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
        return new ResponseEntity<SearchManpowerResp>(mwProcurementService.searchInvoiceServicesByPCName(searchInvoiceMaterialsReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.SEARCH_INVOICE_SUB_PCNAME, method = RequestMethod.POST)
    public ResponseEntity<SearchManpowerResp> searchInvoiceSubByPCName(@RequestBody SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
        return new ResponseEntity<SearchManpowerResp>(mwProcurementService.searchInvoiceSubByPCName(searchInvoiceMaterialsReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_BANK_DETAILS_BY_PROJECTId, method = RequestMethod.POST)
    public ResponseEntity<VendorBankAccountDtlsListTO> gtBankDetailsByProjId() {
        return new ResponseEntity<VendorBankAccountDtlsListTO>(mwProcurementService.gtBankDetailsByProjId(),
                HttpStatus.OK);
    }
}