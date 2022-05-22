package com.rjtech.procurement.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;


import com.rjtech.common.utils.*;
import com.rjtech.notification.dto.ReqApprNotificationTO;
import com.rjtech.notification.service.handler.ReqApprNotificationHandler;
import com.rjtech.procurement.dto.PreContractCmpTO;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.req.*;
import com.rjtech.procurement.resp.*;
import com.rjtech.procurement.service.handler.PrecontractCmpHandler;
import com.rjtech.procurement.service.handler.PrecontractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.WorkFlowStatusTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.procurement.constans.ProcurementURLConstants;
import com.rjtech.procurement.dto.PreContractTO;
import com.rjtech.procurement.dto.PurchaseOrderDetailsTO;
import com.rjtech.procurement.service.ProcurementService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.document.dto.ProjDocFileTO;


@RestController
@RequestMapping(ProcurementURLConstants.PROCUREMENT_PARH_URL)
@MultipartConfig
public class ProcurementController {
	


    @Autowired
    private ProcurementService procurementService;

    @RequestMapping(value = ProcurementURLConstants.GET_INTERNAL_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getInteranlPreContracts(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        return new ResponseEntity<PreContractResp>(procurementService.getInteranlPreContracts(procurementFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACTS_FOR_RFQ, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getPreContractsForRfq(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        return new ResponseEntity<PreContractResp>(procurementService.getPreContractsForRfq(procurementFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_EXTERNAL_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getExteranlPreContracts(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        return new ResponseEntity<>(procurementService.getExteranlPreContracts(procurementFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_WORKFLOW_STATUS, method = RequestMethod.POST)
    public ResponseEntity<PreContractStatusResp> getWorkFlowStatus() {
        return new ResponseEntity<PreContractStatusResp>(procurementService.getWorkFlowStatus(), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_INTERNAL_PRE_CONTRACT_DETAILS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getInternalPreContractDetailsById(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractResp>(
                procurementService.getInternalPreContractDetailsById(procurementGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_EXTERNAL_PRE_CONTRACT_DETAILS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getExternalPreContractDetailsById(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractResp>(
                procurementService.getExternalPreContractDetailsById(procurementGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_CMP_QUOTE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<PreContractOnLoadResp> getPreContractCmpQuoteDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {

        PreContractOnLoadResp preContractOnLoadResp = new PreContractOnLoadResp();

        List<WorkFlowStatusTO> workFlowStatusTOs = new ArrayList<WorkFlowStatusTO>();
        WorkFlowStatusTO workFlowStatusTO = null;
        for (WorkFlowStatus workFlowStatus : WorkFlowStatus.values()) {
            workFlowStatusTO = new WorkFlowStatusTO();
            workFlowStatusTO.setValue(workFlowStatus.getValue());
            workFlowStatusTO.setDesc(workFlowStatus.getDesc());
            workFlowStatusTOs.add(workFlowStatusTO);
        }
        preContractOnLoadResp.setWorkFlowStatusTOs(workFlowStatusTOs);

        ProcurementGetReq procureGetReq = new ProcurementGetReq();
        procureGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        if (CommonUtil.isNonBlankLong(procurementGetReq.getContractId())) {
            PreContractResp preContractResp = procurementService.getPreContractCmpQuoteDetails(procurementGetReq);
            if (CommonUtil.objectNotNull(preContractResp.getPreContractTOs())) {
                preContractOnLoadResp.setPreContractTO(preContractResp.getPreContractTOs().get(0));
                // for RepeatPO
                // preContractOnLoadResp.setRepeatPOPreContractTO(procurementService.getRepeatPOPreContractTO(preContractResp.getPreContractTOs().get(0)));
            }
        } else {
            PreContractTO contractTO = new PreContractTO();
            contractTO.setStatus(StatusCodes.ACTIVE.getValue());
            contractTO.setProjId(procurementGetReq.getProjId());
            procurementGetReq.setClientId(AppUserUtils.getClientId());
            preContractOnLoadResp.setPreContractTO(contractTO);
        }
        return new ResponseEntity<PreContractOnLoadResp>(preContractOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_PLANTS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractPlantResp> getPreContractPlantDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractPlantResp>(procurementService.getPreContractPlants(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_MATERIALS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractMaterialResp> getPreContractMaterialDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractMaterialResp>(
                procurementService.getPreContractMaterials(procurementGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_SERVICES_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractServiceResp> getPreContratServiceDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractServiceResp>(procurementService.getPreContractServices(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_SOW_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<PreContractSowResp> getPreContratSOWTypes(@RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractSowResp>(procurementService.getPreContractSOWTypes(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractDocResp> getPreContractDocDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractDocResp>(procurementService.getPreContractDocs(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_DISTRIBUTION_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractDistributionDocResp> getPreContractDistributionDocs(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractDistributionDocResp>(
                procurementService.getPreContractDistributionDocs(procurementGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_CMP_DISTRIBUTION_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpDistributionDocResp> getPreContractCmpDistributionDocs(
            @RequestBody PrecontractDocGetReq precontractDocGetReq) {
        return new ResponseEntity<PreContractCmpDistributionDocResp>(
                procurementService.getPreContractCmpDistributionDocs(precontractDocGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_CMP_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpDocResp> getPreContractCmpDocDetails(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractCmpDocResp>(procurementService.getPreContractCmpDocs(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_CMP_DOCS_BY_TYPE, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpDocResp> gPreContractCmpDocsByType(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractCmpDocResp>(
                procurementService.gPreContractCmpDocsByType(procurementGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_REQ_APPR, method = RequestMethod.POST)
    public ResponseEntity<PreContractReqApprResp> getPreContractReqApprs(
            @RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<PreContractReqApprResp>(procurementService.getPreContractReqApprs(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_INTERNAL_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> saveInternalPreContrancts(
            @RequestBody ProcurementSaveReq procurementSaveReq) {
        Long contractId = procurementService.saveInternalPreContrancts(procurementSaveReq);

        ProcurementGetReq procurementReq = new ProcurementGetReq();
        procurementReq.setProjId(procurementSaveReq.getProjId());
        procurementReq.setContractId(contractId);
        procurementReq.setStatus(StatusCodes.ACTIVE.getValue());
        PreContractResp precontractResp = procurementService.getInternalPreContractDetailsById(procurementReq);

        precontractResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<PreContractResp>(precontractResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACTS_LIST, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> savePreContractsList(
            @RequestBody PreContractListSaveReq preContractListSaveReq) {
        procurementService.savePreContranctsList(preContractListSaveReq);

        PreContractResp preContractResp = procurementService
                .getLatestPreContracts(preContractListSaveReq.getProcurementFilterReq());
        preContractResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PreContractResp>(preContractResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_EXTERNAL_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> saveExternalPreContrancts(
            @RequestBody ProcurementSaveReq procurementSaveReq) {

        Long contractId = procurementService.saveExternalPreContrancts(procurementSaveReq);

        ProcurementGetReq procurementReq = new ProcurementGetReq();
        procurementReq.setProjId(procurementSaveReq.getProjId());
        procurementReq.setContractId(contractId);
        procurementReq.setStatus(StatusCodes.ACTIVE.getValue());

        if (WorkFlowStatus.APPROVED.getValue().equals(procurementSaveReq.getApprvStatus())) {
            List<LabelKeyTO> labelKeyTOs = procurementService.getPruchaseOrdersByPrecontract(procurementReq);
            POProcureTypeReq poProcureTypeReq = new POProcureTypeReq();
            poProcureTypeReq.setLabelKeyTOs(labelKeyTOs);
            procurementService.updatePurchaseOrderSummar(poProcureTypeReq);
        }
        PreContractResp precontractResp = procurementService.getExternalPreContractDetailsById(procurementReq);

        precontractResp.cloneAppResp(CommonUtil.getSaveAppResp());
        //ResponseEntity<PreContractResp>
        return new ResponseEntity<PreContractResp>(precontractResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.SUBMIT_BID_QUOTATION, method = RequestMethod.POST)
    public ResponseEntity<AppResp> submitBidQuotation(@RequestBody ProcurementFilterReq procurementFilterReq) {
        procurementService.submitBidQuotation(procurementFilterReq);
        AppResp appResp = CommonUtil.getSaveAppResp();
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_COMPANIES, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpResp> savePreContratCompanies(
            @RequestBody PreContractCmpSaveReq preContractCmpSaveReq) {
        procurementService.savePreContratCompanies(preContractCmpSaveReq);

        PreContractCmpGetReq preContractCmpGetReq = new PreContractCmpGetReq();
        preContractCmpGetReq.setPreContractId(preContractCmpSaveReq.getPreContractId());
        preContractCmpGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        PreContractCmpResp preContractCmpResp = procurementService.getPreContractCompanies(preContractCmpGetReq);
        preContractCmpResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PreContractCmpResp>(preContractCmpResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_COMPANIES, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpResp> getPreContractCompanies(
            @RequestBody PreContractCmpGetReq preContractCmpGetReq) {
        return new ResponseEntity<PreContractCmpResp>(procurementService.getPreContractCompanies(preContractCmpGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACTS_EMP_TYPES, method = RequestMethod.POST)
    public ResponseEntity<PreContractEmpResp> savePreContratEmpTypes(
            @RequestBody PreContractEmpSaveReq preContractEmpSaveReq) {
        procurementService.savePreContratEmpTypes(preContractEmpSaveReq);

        ProcurementGetReq procurementGetReq = new ProcurementGetReq();
        procurementGetReq.setContractId(preContractEmpSaveReq.getPreContractId());
        procurementGetReq.setExternal(preContractEmpSaveReq.isExternal());
        procurementGetReq.setProjId(preContractEmpSaveReq.getProjId());
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        PreContractEmpResp preContractEmpResp = procurementService.getPreContractEmpTypes(procurementGetReq);
        preContractEmpResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PreContractEmpResp>(preContractEmpResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACTS_PLANTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractPlantResp> savePreContratPlants(
            @RequestBody PreContractPlantSaveReq preContractPlantSaveReq) {
        procurementService.savePreContratPlants(preContractPlantSaveReq);
        ProcurementFilterReq procurementFilterReq = new ProcurementFilterReq();
        procurementFilterReq.setProjId(preContractPlantSaveReq.getProjId());

        ProcurementGetReq procurementGetReq = new ProcurementGetReq();
        procurementGetReq.setContractId(preContractPlantSaveReq.getPreContractId());
        procurementGetReq.setExternal(preContractPlantSaveReq.isExternal());
        procurementGetReq.setProjId(preContractPlantSaveReq.getProjId());
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        PreContractPlantResp preContractPlantResp = procurementService.getPreContractPlants(procurementGetReq);
        preContractPlantResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PreContractPlantResp>(preContractPlantResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACTS_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<PreContractMaterialResp> savePreContratMaterials(
            @RequestBody PreContractMaterialSaveReq preContractMaterialSaveReq) {
        procurementService.savePreContratMaterials(preContractMaterialSaveReq);
        ProcurementFilterReq procurementFilterReq = new ProcurementFilterReq();
        procurementFilterReq.setProjId(preContractMaterialSaveReq.getProjId());

        ProcurementGetReq procurementGetReq = new ProcurementGetReq();
        procurementGetReq.setContractId(preContractMaterialSaveReq.getPreContractId());
        procurementGetReq.setExternal(preContractMaterialSaveReq.isExternal());
        procurementGetReq.setProjId(preContractMaterialSaveReq.getProjId());
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        PreContractMaterialResp preContractMaterialResp = procurementService.getPreContractMaterials(procurementGetReq);
        preContractMaterialResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PreContractMaterialResp>(preContractMaterialResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACTS_SERVICES, method = RequestMethod.POST)
    public ResponseEntity<PreContractServiceResp> savePreContratMaterials(
            @RequestBody PreContractServiceSaveReq preContractServiceSaveReq) {
        procurementService.savePreContratServices(preContractServiceSaveReq);
        ProcurementFilterReq procurementFilterReq = new ProcurementFilterReq();
        procurementFilterReq.setProjId(preContractServiceSaveReq.getProjId());

        ProcurementGetReq procurementGetReq = new ProcurementGetReq();
        procurementGetReq.setContractId(preContractServiceSaveReq.getPreContractId());
        procurementGetReq.setExternal(preContractServiceSaveReq.isExternal());
        procurementGetReq.setProjId(preContractServiceSaveReq.getProjId());
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        PreContractServiceResp preContractServiceResp = procurementService.getPreContractServices(procurementGetReq);
        preContractServiceResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PreContractServiceResp>(preContractServiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.DEACTIVATE_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> deactivatePreContracts(@RequestBody ProcurementDelReq procurementDelReq) {
        procurementService.deactivatePreContranctsList(procurementDelReq);
        ProcurementFilterReq procurementFilterReq = new ProcurementFilterReq();
        procurementFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        PreContractResp preContractResp = procurementService.getInteranlPreContracts(procurementFilterReq);
        preContractResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<PreContractResp>(preContractResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.DEACTIVATE_INTERNAL_PRE_CONTRACTS, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PreContractResp> deactivateInternalPreContrancts(
            @RequestBody ProcurementDelReq procurementDelReq) {

        procurementService.deactivatePreContrancts(procurementDelReq);
        ProcurementFilterReq procurementFilterReq = new ProcurementFilterReq();
        procurementFilterReq.setProjId(procurementDelReq.getProjId());

        PreContractResp PrecontractResp = procurementService.getInteranlPreContracts(procurementFilterReq);
        PrecontractResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<PreContractResp>(PrecontractResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.DEACTIVATE_EXTERNAL_PRE_CONTRACTS, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PreContractResp> deactivateExternalPreContrancts(
            @RequestBody ProcurementDelReq procurementDelReq) {

        procurementService.deactivatePreContrancts(procurementDelReq);
        ProcurementFilterReq procurementFilterReq = new ProcurementFilterReq();
        procurementFilterReq.setProjId(procurementDelReq.getProjId());

        PreContractResp PrecontractResp = procurementService.getExteranlPreContracts(procurementFilterReq);
        PrecontractResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<PreContractResp>(PrecontractResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_INTERNAL_PRE_CONTRACT_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PreContractOnLoadResp> preContractInternalOnLoadResp(
            @RequestBody ProcurementGetReq procurementGetReq) {
        PreContractOnLoadResp preContractOnLoadResp = new PreContractOnLoadResp();

        List<WorkFlowStatusTO> workFlowStatusTOs = new ArrayList<WorkFlowStatusTO>();
        WorkFlowStatusTO workFlowStatusTO = null;
        for (WorkFlowStatus workFlowStatus : WorkFlowStatus.values()) {
            workFlowStatusTO = new WorkFlowStatusTO();
            workFlowStatusTO.setValue(workFlowStatus.getValue());
            workFlowStatusTO.setDesc(workFlowStatus.getDesc());
            workFlowStatusTOs.add(workFlowStatusTO);
        }
        preContractOnLoadResp.setWorkFlowStatusTOs(workFlowStatusTOs);

        ProcurementGetReq procureGetReq = new ProcurementGetReq();
        procureGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        if (CommonUtil.isNonBlankLong(procurementGetReq.getContractId())) {
            PreContractResp preContractResp = procurementService.getInternalPreContractDetailsById(procurementGetReq);
            if (CommonUtil.objectNotNull(preContractResp.getPreContractTOs())) {
                preContractOnLoadResp.setPreContractTO(preContractResp.getPreContractTOs().get(0));
                // for RepeatPO
                //preContractOnLoadResp.setRepeatPOPreContractTO(procurementService.getRepeatPOPreContractTO(preContractResp.getPreContractTOs().get(0)));
            }
        } else {
            PreContractTO contractTO = new PreContractTO();
            contractTO.setStatus(StatusCodes.ACTIVE.getValue());
            contractTO.setProjId(procurementGetReq.getProjId());
            procurementGetReq.setClientId(AppUserUtils.getClientId());
            preContractOnLoadResp.setPreContractTO(contractTO);
        }
        return new ResponseEntity<PreContractOnLoadResp>(preContractOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_EXTERNAL_PRE_CONTRACT_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PreContractOnLoadResp> preContractExternalOnLoadResp(
            @RequestBody ProcurementGetReq procurementGetReq) {
        System.out.println("ProcurementController:preContractExternalOnLoadResp: ContractId :"+procurementGetReq.getContractId());

        PreContractOnLoadResp preContractOnLoadResp = new PreContractOnLoadResp();
        System.out.println("Started : ProcurementController:preContractExternalOnLoadResp ");
        List<WorkFlowStatusTO> workFlowStatusTOs = new ArrayList<WorkFlowStatusTO>();
        WorkFlowStatusTO workFlowStatusTO = null;
        for (WorkFlowStatus workFlowStatus : WorkFlowStatus.values()) {
            workFlowStatusTO = new WorkFlowStatusTO();
            workFlowStatusTO.setValue(workFlowStatus.getValue());
            workFlowStatusTO.setDesc(workFlowStatus.getDesc());
            workFlowStatusTOs.add(workFlowStatusTO);
        }
        preContractOnLoadResp.setWorkFlowStatusTOs(workFlowStatusTOs);

        ProcurementGetReq procureGetReq = new ProcurementGetReq();
        procureGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        if (CommonUtil.isNonBlankLong(procurementGetReq.getContractId())) {
            System.out.println("InSide isNonBlankLong ContractId");
            PreContractResp preContractResp = procurementService.getExternalPreContractDetailsById(procurementGetReq);
            System.out.println(" tPreContractTOs size : "+preContractResp.getPreContractTOs().size());

            if (CommonUtil.objectNotNull(preContractResp.getPreContractTOs())) {
                preContractOnLoadResp.setPreContractTO(preContractResp.getPreContractTOs().get(0));
                // for RepeatPO
                System.out.println("Before Calling RepeatPO : ProcurementController:preContractExternalOnLoadResp ");
                PreContractTO preContractTO = preContractResp.getPreContractTOs().get(0);
                //preContractOnLoadResp.setRepeatPOPreContractTO(procurementService.getRepeatPOPreContractTO(preContractTO));
            }
        } else {
            PreContractTO contractTO = new PreContractTO();
            contractTO.setStatus(StatusCodes.ACTIVE.getValue());
            contractTO.setProjId(procurementGetReq.getProjId());
            procurementGetReq.setClientId(AppUserUtils.getClientId());
            preContractOnLoadResp.setPreContractTO(contractTO);
        }

        return new ResponseEntity<PreContractOnLoadResp>(preContractOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_POPUP_EMP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PreContractEmpResp> preContractPopupEmpOnLoad(
            @RequestBody ProcurementGetReq procurementGetReq) {

        PreContractEmpResp preContractEmpResp = new PreContractEmpResp();
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        preContractEmpResp.setPreContractId(procurementGetReq.getContractId());

        preContractEmpResp.getPreContractEmpDtlTOs()
                .addAll(procurementService.getPreContractEmpTypes(procurementGetReq).getPreContractEmpDtlTOs());
        return new ResponseEntity<PreContractEmpResp>(preContractEmpResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_POPUP_PLANT_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PreContractPlantResp> preContractPopupPlantOnLoad(
            @RequestBody ProcurementGetReq procurementGetReq) {

        PreContractPlantResp preContractPlantResp = new PreContractPlantResp();
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        preContractPlantResp.setPreContractId(procurementGetReq.getContractId());

        preContractPlantResp.getPreContractPlantDtlTOs()
                .addAll(procurementService.getPreContractPlants(procurementGetReq).getPreContractPlantDtlTOs());
        return new ResponseEntity<PreContractPlantResp>(preContractPlantResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_POPUP_MATERIAL_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PreContractMaterialResp> preContractPopupMaterialOnLoad(
            @RequestBody ProcurementGetReq procurementGetReq) {

        PreContractMaterialResp preContractMaterialResp = new PreContractMaterialResp();
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        preContractMaterialResp.setPreContractId(procurementGetReq.getContractId());

        preContractMaterialResp.getPreContractMaterialDtlTOs()
                .addAll(procurementService.getPreContractMaterials(procurementGetReq).getPreContractMaterialDtlTOs());
        return new ResponseEntity<PreContractMaterialResp>(preContractMaterialResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_POPUP_SERVICE_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PreContractServiceResp> preContractPopupServiceOnLoad(
            @RequestBody ProcurementGetReq procurementGetReq) {

        PreContractServiceResp preContractServiceResp = new PreContractServiceResp();
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        preContractServiceResp.setPreContractId(procurementGetReq.getContractId());

        preContractServiceResp.getPreContractServiceDtlTOs()
                .addAll(procurementService.getPreContractServices(procurementGetReq).getPreContractServiceDtlTOs());
        return new ResponseEntity<PreContractServiceResp>(preContractServiceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractDocResp> savePreContranctDocs(MultipartFile[] files,
            String precontractDocSaveReqStr) throws IOException {
    	System.out.println("savePreContranctDocs function of ProcurementController precontractDocSaveReqStr string:"+precontractDocSaveReqStr);
        PrecontractDocSaveReq precontractDocSaveReq = AppUtils.fromJson(precontractDocSaveReqStr,
                PrecontractDocSaveReq.class);
        System.out.println("result:"+precontractDocSaveReq);
        procurementService.savePreContranctDocs(files, precontractDocSaveReq);
        PreContractDocResp preContractDocResp = new PreContractDocResp();
        if (CommonUtil.isListHasData(precontractDocSaveReq.getPreContractDocsTOs())) {
            ProcurementGetReq procurementGetReq = new ProcurementGetReq();
            procurementGetReq.setContractId(precontractDocSaveReq.getPreContractDocsTOs().get(0).getPreContractId());
            procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());
            preContractDocResp = procurementService.getPreContractDocs(procurementGetReq);
        }
        preContractDocResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PreContractDocResp>(preContractDocResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_DISTRIBUTION_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractDistributionDocResp> savePreContractDistributionDocs(
            @RequestBody PrecontractDistributionDocSaveReq precontractDocSaveReq) {
        procurementService.savePreContractDistributionDocs(precontractDocSaveReq);
        ProcurementGetReq procurementGetReq = new ProcurementGetReq();
        procurementGetReq.setContractId(precontractDocSaveReq.getContractId());
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PreContractDistributionDocResp preContractDocResp = procurementService
                .getPreContractDistributionDocs(procurementGetReq);
        preContractDocResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PreContractDistributionDocResp>(preContractDocResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SEND_PRE_CONTRACT_DOCS_TO_COMPANIES, method = RequestMethod.POST)
    public ResponseEntity<PreContractDistributionDocResp> sendPreContractDocsToCompanies(
            @RequestBody PrecontractDistributionDocSaveReq precontractDocSaveReq) {
        procurementService.sendPreContractDocsToCompanies(precontractDocSaveReq);
        ProcurementGetReq procurementGetReq = new ProcurementGetReq();
        procurementGetReq.setContractId(precontractDocSaveReq.getContractId());
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PreContractDistributionDocResp preContractDocResp = procurementService
                .getPreContractDistributionDocs(procurementGetReq);
        preContractDocResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PreContractDistributionDocResp>(preContractDocResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_COMPANY_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpDocResp> savePreContratCompnayDocs(MultipartFile[] files,
            String precontractCompanyDocsSaveReqStr) throws IOException {
    	System.out.println("savePreContratCompnayDocs function of ProcurementController precontractDocSaveReqStr string:"+precontractCompanyDocsSaveReqStr);
    	PreContratCompnayDocSaveReq preContratCompnayDocSaveReq = AppUtils.fromJson(precontractCompanyDocsSaveReqStr,
    			PreContratCompnayDocSaveReq.class);
    	procurementService.savePreContratCompnayDocs(files,preContratCompnayDocSaveReq);
        ProcurementGetReq procurementGetReq = new ProcurementGetReq();
        procurementGetReq.setContractId(preContratCompnayDocSaveReq.getContractId());
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PreContractCmpDocResp preContractCmpDocResp = procurementService.getPreContractCmpDocs(procurementGetReq);
        preContractCmpDocResp.cloneAppResp(CommonUtil.getSaveAppResp());
        
        /*procurementService.savePreContratCompnayDocs(preContratCompnayDocSaveReq);
        ProcurementGetReq procurementGetReq = new ProcurementGetReq();
        procurementGetReq.setContractId(preContratCompnayDocSaveReq.getContractId());
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PreContractCmpDocResp preContractCmpDocResp = procurementService.getPreContractCmpDocs(procurementGetReq);
        preContractCmpDocResp.cloneAppResp(CommonUtil.getSaveAppResp());*/
        return new ResponseEntity<PreContractCmpDocResp>(preContractCmpDocResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_INVOICE_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<InvoiceMaterialResp> getPurchaseOrders(@RequestBody InvoiceMaterialGetReq invoiceMaterialGetReq) {
        return new ResponseEntity<InvoiceMaterialResp>(procurementService.getInvoiceMaterials(invoiceMaterialGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDERS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> getPurchaseOrders(@RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<PurchaseOrderResp>(procurementService.getPurchaseOrders(purchaseOrderGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getPurchaseOrderDetails(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {
        return new ResponseEntity<PreContractResp>(procurementService.getPurchaseOrderDetails(purchaseOrderGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PURCHASE_ORDERS, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> savePurchaseOrders(
            @RequestBody PurchaseOrderSaveReq purchaseOrderSaveReq) {
        procurementService.savePurchaseOrders(purchaseOrderSaveReq);

        PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
        purchaseOrderGetReq.setProjId(263L);
   //     purchaseOrderGetReq.setProjId(purchaseOrderSaveReq.getProjId()); previous one
        purchaseOrderGetReq.getProjIds().add(purchaseOrderGetReq.getProjId());
   //     purcheaseOrderGetReq.setProjIds(purchaseOrderGetReq.getProjId());    
        purchaseOrderGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PurchaseOrderResp purchaseOrderResp = procurementService.getPurchaseOrders(purchaseOrderGetReq);

        purchaseOrderResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<PurchaseOrderResp>(purchaseOrderResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProcurementURLConstants.REGENERATE_PURCHASE_ORDER, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> regeneratePurchaseOrders(
            @RequestBody PurchaseOrderRepeatSaveReq purchaseOrderRepeatSaveReq) {

        System.out.println("ProcurementController : regeneratePurchaseOrders");
        System.out.println(purchaseOrderRepeatSaveReq.toString());


        procurementService.repeatPurchaseOrder(purchaseOrderRepeatSaveReq);

        PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
        purchaseOrderGetReq.setProjId(purchaseOrderRepeatSaveReq.getProjId());
        purchaseOrderGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        purchaseOrderGetReq.getProjIds().add(purchaseOrderGetReq.getProjId());
        System.out.println(purchaseOrderGetReq.toString());
        PurchaseOrderResp purchaseOrderResp = procurementService.getPurchaseOrders(purchaseOrderGetReq);
        purchaseOrderResp.cloneAppResp(CommonUtil.getSaveAppResp());

        System.out.println("ProcurementController : purchaseOrderResp");
        return new ResponseEntity<PurchaseOrderResp>(purchaseOrderResp, HttpStatus.OK);
    }


    //RepeatPo
    //@RequestMapping(value = ProcurementURLConstants.SAVE_PROCUREMENT_PO_REPEATPO, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> saveprocurementporepeatpo_XX(
            @RequestBody ProcurementPoRepeatpoSaveReq procurementporepeatposavereq) {

            //PurchaseOrderRepeatSaveReq purchaseOrderRepeatSaveReq = new PurchaseOrderRepeatSaveReq();
            //@RequestBody ProcurementSaveReq procurementSaveReq) {

            ProcurementSaveReq procurementSaveReq = new ProcurementSaveReq();

            procurementSaveReq.setProjId(procurementSaveReq.getProjId());
            procurementSaveReq.setApprvStatus(WorkFlowStatus.IN_PROCESS.getValue());
            procurementSaveReq.setPreContractTO(procurementporepeatposavereq.getPreContractTO());

            Long contractId = procurementService.repeatPOSaveExternalPreContrancts(procurementSaveReq);

            ProcurementGetReq procurementReq = new ProcurementGetReq();
            procurementReq.setProjId(procurementSaveReq.getProjId());
            procurementReq.setContractId(contractId);
            procurementReq.setStatus(StatusCodes.ACTIVE.getValue());

            if (WorkFlowStatus.APPROVED.getValue().equals(procurementSaveReq.getApprvStatus())) {
                List<LabelKeyTO> labelKeyTOs = procurementService.getPruchaseOrdersByPrecontract(procurementReq);
                POProcureTypeReq poProcureTypeReq = new POProcureTypeReq();
                poProcureTypeReq.setLabelKeyTOs(labelKeyTOs);
                procurementService.updatePurchaseOrderSummar(poProcureTypeReq);
            }

            PreContractResp precontractResp = procurementService.getExternalPreContractDetailsById(procurementReq);

            precontractResp.cloneAppResp(CommonUtil.getSaveAppResp());

            PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
            purchaseOrderGetReq.setProjId(procurementporepeatposavereq.getProjId());
            purchaseOrderGetReq.setStatus(StatusCodes.ACTIVE.getValue());
            purchaseOrderGetReq.getProjIds().add(purchaseOrderGetReq.getProjId());
            PurchaseOrderResp purchaseOrderResp = procurementService.getPurchaseOrders(purchaseOrderGetReq);
            purchaseOrderResp.cloneAppResp(CommonUtil.getSaveAppResp());
            purchaseOrderResp.setPreContractResp(precontractResp);

            //ResponseEntity<PreContractResp>
            return new ResponseEntity<PurchaseOrderResp>(purchaseOrderResp, HttpStatus.OK);
            //return new ResponseEntity<PreContractResp>(precontractResp, HttpStatus.OK);

            /*procurementService.saveprocurementporepeatpo(procurementporepeatposavereq);

            PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
            purchaseOrderGetReq.setProjId(procurementporepeatposavereq.getProjId());
            purchaseOrderGetReq.setStatus(StatusCodes.ACTIVE.getValue());
            purchaseOrderGetReq.getProjIds().add(purchaseOrderGetReq.getProjId());
            PurchaseOrderResp purchaseOrderResp = procurementService.getPurchaseOrders(purchaseOrderGetReq);
            purchaseOrderResp.cloneAppResp(CommonUtil.getSaveAppResp());

            return new ResponseEntity<PurchaseOrderResp>(purchaseOrderResp, HttpStatus.OK);*/
    }

    //RepeatPo Save
    @RequestMapping(value = ProcurementURLConstants.SAVE_PROCUREMENT_PO_REPEATPO, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> saveprocurementporepeatpo(
            @RequestBody ProcurementPoRepeatpoSaveReq procurementporepeatposavereq) {

    	procurementService.saveprocurementporepeatpo(procurementporepeatposavereq);

        PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
        purchaseOrderGetReq.setProjId(procurementporepeatposavereq.getProjId());
        purchaseOrderGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        purchaseOrderGetReq.getProjIds().add(purchaseOrderGetReq.getProjId());
        PurchaseOrderResp purchaseOrderResp = procurementService.getPurchaseOrders(purchaseOrderGetReq);
        purchaseOrderResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<PurchaseOrderResp>(purchaseOrderResp, HttpStatus.OK);
    }

    //RepeatPo Get
    @RequestMapping(value = ProcurementURLConstants.GET_REPEAT_PURCHASE_ORDERS, method = RequestMethod.POST)
    public ResponseEntity<RepeatPurchaseOrderResp> getRepeatPurchaseOrders(
            @RequestBody RepeatPurchaseOrderGetReq repeatPurchaseOrderGetReq) {
        System.out.println(" ** Procurement Controller: getRepeatPurchaseOrder: ProjId: "+repeatPurchaseOrderGetReq.getProjId() + " : ContractId : "+repeatPurchaseOrderGetReq.getContractId());

        //procurementService.getRepeatPurchaseOrders(repeatPurchaseOrderGetReq);

        /*PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
        purchaseOrderGetReq.setProjId(repeatPurchaseOrderGetReq.getProjId());
        purchaseOrderGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        purchaseOrderGetReq.getProjIds().add(purchaseOrderGetReq.getProjId());*/
        RepeatPurchaseOrderResp purchaseOrderResp = procurementService.getRepeatPurchaseOrders(repeatPurchaseOrderGetReq);
        //purchaseOrderResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<RepeatPurchaseOrderResp>(purchaseOrderResp, HttpStatus.OK);
    }


    @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_SOW_TYPES, method = RequestMethod.POST)
    public ResponseEntity<PreContractSowResp> savePreContractSowDtls(
            @RequestBody PreContractSowDtlsSaveReq preContractSowDtlsSaveReq) {
        procurementService.savePreContratSOWTypes(preContractSowDtlsSaveReq);

        ProcurementGetReq procurementGetReq = new ProcurementGetReq();
        procurementGetReq.setContractId(preContractSowDtlsSaveReq.getPreContractId());
        procurementGetReq.setExternal(preContractSowDtlsSaveReq.isExternal());
        procurementGetReq.setProjId(preContractSowDtlsSaveReq.getProjId());
        procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        PreContractSowResp preContractSowResp = procurementService.getPreContractSOWTypes(procurementGetReq);
        preContractSowResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<PreContractSowResp>(preContractSowResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_LATEST_PRE_CONTRACTS, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getLatestPreContracts(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        return new ResponseEntity<PreContractResp>(procurementService.getLatestPreContracts(procurementFilterReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_INTERNAL_PRE_CONTRACT_ONLOAD_REPORT, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getLatestPreContractsReport(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        return new ResponseEntity<PreContractResp>(procurementService.getLatestPreContractsReport(procurementFilterReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_EMP_TASK_REPORT, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getLatestPreContractsempTaskreport(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        return new ResponseEntity<PreContractResp>(procurementService.getLatestPreContractsempTaskreport(procurementFilterReq),
                HttpStatus.OK);
    }
    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_COST_CODE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<PreContractResp> getLatestPreContractsCostCodeReports(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        return new ResponseEntity<PreContractResp>(procurementService.getLatestPreContractsCostCodeReports(procurementFilterReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_RFQS, method = RequestMethod.POST)
    public ResponseEntity<PreContractCmpResp> getPreContractRFQs(
            @RequestBody ProcurementFilterReq procurementFilterReq) {
        return new ResponseEntity<PreContractCmpResp>(procurementService.getPreContractRFQs(procurementFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PROJ_SETTINGS_FOR_PROCUREMENT, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTO> getProjSettingsForProcurement(@RequestBody ProcurementGetReq procurementGetReq) {
        return new ResponseEntity<LabelKeyTO>(procurementService.getProjSettingsForProcurement(procurementGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.GET_PRE_CONTRACT_COST_CODE_SUMMARY, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPreContarctCostCodeSummary(
            @RequestBody ProcurementGetReq procurementGetReq) {
    	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+procurementGetReq.getContractType());
        return new ResponseEntity<>(procurementService.getPreContarctCostCodeSummary(procurementGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_SINGLE_PURCHASE_ORDER, method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResp> saveSinglePurchaseOrder(
            @RequestBody SinglePurchaseOrderSaveReq singlePurchaseOrderSaveReq) {
        PurchaseOrderResp response = procurementService.saveSinglePurchaseOrder(singlePurchaseOrderSaveReq);
        return new ResponseEntity<PurchaseOrderResp>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_PURCHASE_ORDER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<SinglePurchaseOrderResp> savePurchaseOrderDetails(
            @RequestBody PurchaseOrderDetailsTO purchaseOrderDetailsTO) {
        procurementService.savePurchaseOrderDetails(purchaseOrderDetailsTO);
        SinglePurchaseOrderResp singlePurchaseOrderResp = new SinglePurchaseOrderResp();
        return new ResponseEntity<SinglePurchaseOrderResp>(singlePurchaseOrderResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProcurementURLConstants.SAVE_TRANSMITTAL_MSG, method = RequestMethod.POST)
    public ResponseEntity<DocumentTransmittalMsgResp> saveTransmittalMsg(
            @RequestBody DocumentTransmittalMsgReq documentTransmittalMsgReq) {
        procurementService.saveTransmittalMsg(documentTransmittalMsgReq);
        DocumentTransmittalMsgResp documentTransmittalMsgResp = new DocumentTransmittalMsgResp();
        return new ResponseEntity<DocumentTransmittalMsgResp>(documentTransmittalMsgResp, HttpStatus.OK);

    }

    @PostMapping(value = ProcurementURLConstants.GET_PURCHASE_ORDERS_BY_PRECONTRACT_CMP_ID_AND_PROJ_ID)
    public ResponseEntity<PurchaseOrderResp> getPurchaseOrdersByPrecontractCmpIdAndProjId(
            @RequestBody PurchaseOrderGetReq purchaseOrderGetReq) {

        PurchaseOrderResp purchaseOrderResp = procurementService
                .getPurchaseOrdersByPrecontractCmpIdAndProjId(purchaseOrderGetReq);
        return new ResponseEntity<>(purchaseOrderResp, HttpStatus.OK);
    }

    @PostMapping(value = ProcurementURLConstants.SAVE_TERMS_AND_CONDITIONS)
    public ResponseEntity<TermsAndConditionsResp> saveTermsAndConditions(
            @RequestBody SaveTermsAndConditionsReq saveTermsAndConditionsReq) {

        TermsAndConditionsResp termsAndConditionsResp = procurementService
                .saveTermsAndConditions(saveTermsAndConditionsReq);
        return new ResponseEntity<TermsAndConditionsResp>(termsAndConditionsResp, HttpStatus.OK);
    }

    @PostMapping(value = ProcurementURLConstants.GET_TERMS_AND_CONDITIONS)
    public ResponseEntity<TermsAndConditionsResp> getTermsAndConditions(
            @RequestBody GetTermsAndConditionsReq getTermsAndConditionsReq) {
        TermsAndConditionsResp termsAndConditionsResp = procurementService
                .getTermsAndConditions(getTermsAndConditionsReq);
        return new ResponseEntity<TermsAndConditionsResp>(termsAndConditionsResp, HttpStatus.OK);
    }
    
    @GetMapping(value = ProcurementURLConstants.DOWNLOAD_PROCUREMENT_DOCS)
    public String[] downloadProcurementDocs( @RequestParam("recordId") Long recordId, @RequestParam("category") String category ) throws IOException {
    	ProjDocFileTO fileTo = procurementService.downloadProcurementDocs( recordId, category );
    	String file_info[] = { fileTo.getName(), fileTo.getFileSize(), fileTo.getFileType(), fileTo.getFolderPath() };
    	return file_info;
    }
    @PostMapping(value = ProcurementURLConstants.GET_PROCUREMENT_SUBCATEGORY_LIST)
    public ResponseEntity<ProcurementSubCatResp> getProcurementSubcategoryList(@RequestBody ProcurementSubCatReq procurementCat) {
    	ProcurementSubCatResp procurementSubCatList = procurementService
                .getProcurementSubCatList(procurementCat);
        return new ResponseEntity<ProcurementSubCatResp>(procurementSubCatList, HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.SEARCH_INVOICE_MATERIALS_PCNAME, method = RequestMethod.POST)
    public ResponseEntity<InvoiceMaterialResp> searchInvoiceMaterialsByPCName(@RequestBody SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
        return new ResponseEntity<InvoiceMaterialResp>(procurementService.searchInvoiceMaterialsByPCName(searchInvoiceMaterialsReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.SEARCH_INVOICE_MANPOWER_PCNAME, method = RequestMethod.POST)
    public ResponseEntity<SearchManpowerResp> searchInvoiceMByPCName(@RequestBody SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
        return new ResponseEntity<SearchManpowerResp>(procurementService.searchInvoiceManpowerByPCName(searchInvoiceMaterialsReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.SEARCH_INVOICE_PLANTS_BY_PCNAME, method = RequestMethod.POST)
    public ResponseEntity<SearchManpowerResp> searchInvoicePlantsByPCName(@RequestBody SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
        return new ResponseEntity<SearchManpowerResp>(procurementService.searchInvoicePlantsByPCName(searchInvoiceMaterialsReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.SEARCH_INVOICE_SERVICES_PCNAME, method = RequestMethod.POST)
    public ResponseEntity<SearchManpowerResp> searchInvoiceServicesByPCName(@RequestBody SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
        return new ResponseEntity<SearchManpowerResp>(procurementService.searchInvoiceServicesByPCName(searchInvoiceMaterialsReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProcurementURLConstants.SEARCH_INVOICE_SUB_PCNAME, method = RequestMethod.POST)
    public ResponseEntity<InvoiceMaterialResp> searchInvoiceSubByPCName(@RequestBody SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
        return new ResponseEntity<InvoiceMaterialResp>(procurementService.searchInvoiceSubByPCName(searchInvoiceMaterialsReq),
                HttpStatus.OK);
    }
    
}