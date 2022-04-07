package com.rjtech.procurement.service.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.repository.ServiceRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.CurrencyTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.WorkFlowStatusTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.ProcurmentStageStatus;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.WorkFlowStatus;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.dto.ReqApprNotificationTO;
import com.rjtech.notification.model.ProcurementAddtionalTimeApprEntity;
import com.rjtech.notification.model.ProcurementNormalTimeEntity;
import com.rjtech.notification.repository.ProcurementAddltionalTimeRepository;
import com.rjtech.notification.repository.ProcurementNormalTimeRepository;
import com.rjtech.procurement.dto.DocumentTransmittalTO;
import com.rjtech.procurement.dto.PreContractCmpTO;
import com.rjtech.procurement.dto.PreContractEmpDtlTO;
import com.rjtech.procurement.dto.PreContractMaterialDtlTO;
import com.rjtech.procurement.dto.PreContractPlantDtlTO;
import com.rjtech.procurement.dto.PreContractReqApprTO;
import com.rjtech.procurement.dto.PreContractServiceDtlTO;
import com.rjtech.procurement.dto.PreContractTO;
import com.rjtech.procurement.dto.PrecontractSowDtlTO;
import com.rjtech.procurement.model.DocumentTransmittalMessageEntity;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractReqApprEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PreContractsEmpDtlEntity;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;
import com.rjtech.procurement.model.PreContractsPlantDtlEntity;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;
import com.rjtech.procurement.model.PrecontractSowDtlEntity;
//import com.rjtech.procurement.model.ProcurementNormalTimeEntity;
//import com.rjtech.procurement.model.ProcurementAddtionalTimeApprEntity;
import com.rjtech.procurement.repository.PrecontractRepository;
//import com.rjtech.procurement.repository.ProcurementAddltionalTimeRepository;
//import com.rjtech.procurement.repository.ProcurementNormalTimeRepository;
import com.rjtech.procurement.req.ProcurementGetReq;
import com.rjtech.procurement.resp.PreContractReqApprResp;
import com.rjtech.procurement.resp.PreContractResp;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.projsettings.repository.copy.ProjCostStatementsRepositoryCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class PrecontractHandler {

	private static final Logger log = LoggerFactory.getLogger(PrecontractHandler.class);

    public static PreContractResp populatePreContractsWithProjSettings(List<PreContractEntity> entities, 
    		ProcurementNormalTimeRepository procurementNormalTimeRepository, ProcurementAddltionalTimeRepository procurementAddltionalTimeRepository) {
        PreContractResp precontractResp = new PreContractResp();
        String contractStageStatus = null;
        Date reqDate = null;
        for (PreContractEntity preContractEntity : entities) {
            if (preContractEntity.getContarctStageStatus().equalsIgnoreCase("Stage 1 Approval")) {
            	contractStageStatus = "Stage 1 Internal Approval";
            } else if (preContractEntity.getContarctStageStatus().equalsIgnoreCase("Stage 2 Approval")){
            	contractStageStatus = "Stage 2 Internal Approval";
            }
        	log.info("----------------- preContractEntity.getPreContarctStatus() " + preContractEntity.getPreContarctStatus());
        	log.info("----------------- preContractEntity.getContarctStageStatus() " + preContractEntity.getContarctStageStatus());
        	if ((preContractEntity.getPreContarctStatus() == 2 && preContractEntity.getContarctStageStatus().equalsIgnoreCase("Stage 1 Approval")) || 
        			(preContractEntity.getPreContarctStatus() == 5 && preContractEntity.getContarctStageStatus().equalsIgnoreCase("Stage 2 Approval"))) {
        		log.info("================= preContractEntity.getProjId().getProjectId() " + preContractEntity.getProjId().getProjectId());
        		log.info("================= contractStageStatus " + contractStageStatus);
	        	ProcurementNormalTimeEntity procurementNormalTime = procurementNormalTimeRepository
	        			.findCutOffDaysForProject(preContractEntity.getProjId().getProjectId(), contractStageStatus);
	        	reqDate = preContractEntity.getPreContractReqApprEntities().get(0).getReqDate();
	        	ProcurementAddtionalTimeApprEntity procurementAddlTimeRepository = procurementAddltionalTimeRepository
	        			.findNormalTimeId(preContractEntity.getId(), contractStageStatus);
	        	log.info("________ preContractEntity.getId() " + preContractEntity.getId());
	        	if(procurementAddlTimeRepository != null) {
	        		log.info("Additional Time Id is " + procurementAddlTimeRepository.getId());
	        		reqDate = procurementAddlTimeRepository.getRequisitionDate();
	        		
	        	} else {
	        		log.info("NO RECORDS FOUND...");
	        	}
	        	log.info("Requisition Date Time is " + reqDate);
	        	
	            precontractResp.getPreContractTOs().add(populatePrecontractNormTimeTO(preContractEntity, procurementNormalTime, reqDate));
        	} else {
        		precontractResp.getPreContractTOs().add(populatePrecontractTO(preContractEntity));
        	}
            
        }
        return precontractResp;
    }
    
    public static PreContractTO populatePrecontractNormTimeTO(PreContractEntity preContractEntity, 
    		ProcurementNormalTimeEntity procurementNormalTime, Date reqDate) {
    	PreContractTO precontractTO = convertPreContractEntityTOPOJO(preContractEntity);
        CurrencyTO currencyTO = new CurrencyTO();
        currencyTO.setCode(preContractEntity.getCurrencyCode());
        precontractTO.setCurrencyCode(preContractEntity.getCurrencyCode());
        PreContractReqApprTO preContractReqApprTO = null;
        for (PreContractReqApprEntity preContractReqApprEntity : preContractEntity.getPreContractReqApprEntities()) {
            if (preContractReqApprEntity.isLatest()) {
                preContractReqApprTO = PrecontractReqApprHandler.convertRequestEntityToPOJO(preContractReqApprEntity);
                precontractTO.setPreContractReqApprTO(preContractReqApprTO);
            }
        }
        precontractTO.getProcurementNormalTimeTOs().add(ProcurementNormalTimeHandler.convertEntityToTO(procurementNormalTime, reqDate));
        return precontractTO;
    }
    
    public static PreContractResp populatePreContracts(List<PreContractEntity> entities,ProcurementNormalTimeRepository procurementNormalTimeRepository, ProcurementAddltionalTimeRepository procurementAddltionalTimeRepository) {
        PreContractResp precontractResp = new PreContractResp();
        String contractStageStatus = null;
        Date reqDate = null;
        //List<ProcurementAddtionalTimeApprEntity> procurementAdditionalTime = new ArrayList<>();
        for (PreContractEntity preContractEntity : entities) {
        	 if (preContractEntity.getContarctStageStatus().equalsIgnoreCase("Stage 1 Approval")) {
             	contractStageStatus = "Stage 1 Internal Approval";
             } else if (preContractEntity.getContarctStageStatus().equalsIgnoreCase("Stage 2 Approval")){
             	contractStageStatus = "Stage 2 Internal Approval";
             }
        	 ProcurementNormalTimeEntity procurementNormalTime = procurementNormalTimeRepository
         			.findCutOffDaysForProject(preContractEntity.getProjId().getProjectId(), contractStageStatus);
        	 ProcurementAddtionalTimeApprEntity procurementAddlTimeRepository = procurementAddltionalTimeRepository
	        			.findNormalTimeId(preContractEntity.getId(), contractStageStatus);
        	 if(procurementNormalTime != null || procurementAddlTimeRepository != null) {
        		 if(procurementAddlTimeRepository != null) {
            		 reqDate = procurementAddlTimeRepository.getRequisitionDate();
            	 }else { 
            		 if(preContractEntity.getPreContractReqApprEntities().size() > 0) {
            		 reqDate = preContractEntity.getPreContractReqApprEntities().get(0).getReqDate();
            		 }
            	 }
        	 if(preContractEntity.getPreContractReqApprEntities().size() > 0) {
        		 precontractResp.getPreContractTOs().add(populatePrecontractNormTimeTO(preContractEntity,procurementNormalTime,reqDate));
        	 }
        	 }else {
        		 precontractResp.getPreContractTOs().add(populatePrecontractTO(preContractEntity));
        	 }
        }
        return precontractResp;
    }
    
    public static PreContractResp populatePreContractsReport(List<PreContractEntity> entities, boolean addExternal) {
    	 PreContractResp precontractResp = new PreContractResp();
         PreContractTO precontractTO = null;
         for (PreContractEntity preContractEntity : entities) {
             precontractTO = populatePrecontractTO(preContractEntity);
             if (addExternal) {
                 precontractTO.setPreContractCmpTOs(PrecontractCmpHandler
                         .populatePreContractCompanies(preContractEntity.getPreContractsCmpEntities()));
             }
             addChilds(precontractTO, preContractEntity, addExternal);
             precontractResp.getPreContractTOs().add(precontractTO);
         }
        return precontractResp;
    }
    
    public static PreContractResp populatePreContractDetails(List<PreContractEntity> entities, boolean addExternal) {
        PreContractResp precontractResp = new PreContractResp();
        PreContractTO precontractTO = null;
        for (PreContractEntity preContractEntity : entities) {
            precontractTO = populatePrecontractTO(preContractEntity);
            if (addExternal) {
                precontractTO.setPreContractCmpTOs(PrecontractCmpHandler
                        .populatePreContractCompanies(preContractEntity.getPreContractsCmpEntities()));
            }
            addChilds(precontractTO, preContractEntity, addExternal);
            precontractResp.getPreContractTOs().add(precontractTO);
        }
        return precontractResp;
    }

    public static PreContractResp populatePreContractCmpQuoteDetails(List<PreContractEntity> entities,
            ProcurementGetReq procurementGetReq, boolean addExternal) {
        PreContractResp precontractResp = new PreContractResp();
        PreContractTO precontractTO = null;
        for (PreContractEntity preContractEntity : entities) {
            precontractTO = populatePrecontractTO(preContractEntity);
            PreContractCmpTO preContractCmpTO = new PreContractCmpTO();
            preContractCmpTO.setId(procurementGetReq.getPreContractCmpId());
            precontractTO.getPreContractCmpTOs().add(preContractCmpTO);
            addChilds(precontractTO, preContractEntity, addExternal);
            precontractResp.getPreContractTOs().add(precontractTO);
        }
        return precontractResp;
    }

    public static PreContractTO populatePrecontractTO(PreContractEntity preContractEntity) {
        PreContractTO precontractTO = convertPreContractEntityTOPOJO(preContractEntity);
        CurrencyTO currencyTO = new CurrencyTO();
        currencyTO.setCode(preContractEntity.getCurrencyCode());
        precontractTO.setCurrencyCode(preContractEntity.getCurrencyCode());
        PreContractReqApprTO preContractReqApprTO = null;
        for (PreContractReqApprEntity preContractReqApprEntity : preContractEntity.getPreContractReqApprEntities()) {
            if (preContractReqApprEntity.isLatest()) {
                preContractReqApprTO = PrecontractReqApprHandler.convertRequestEntityToPOJO(preContractReqApprEntity);
                precontractTO.setPreContractReqApprTO(preContractReqApprTO);
            }
        }
        return precontractTO;
    }

    public static void addChilds(PreContractTO precontractTO, PreContractEntity preContractEntity,
            boolean addExternal) {
        Map<Long, Boolean> companyMap = new HashMap<>();
        List<PreContractCmpTO> preContractCmpTOs = precontractTO.getPreContractCmpTOs();
        for (PreContractCmpTO preContractCmpTO : preContractCmpTOs) {
            companyMap.put(preContractCmpTO.getId(), true);
        }
        precontractTO.setPreContractEmpDtlTOs(PrecontractEmpHandler.populatePreContractEmpTypes(companyMap,
                preContractEntity.getPreContractsEmpDtlEntities(), addExternal));

        precontractTO.getPreContractPlantDtlTOs().addAll(PrecontractPlantHandler.populatePreContractPlants(companyMap,
                preContractEntity.getPreContractsPlantDtlEntities(), addExternal));

        precontractTO.getPreContractMaterialDtlTOs().addAll(PrecontractMaterialHandler.populatePreContractMaterials(
                companyMap, preContractEntity.getPreContractsMaterialDtlEntities(), addExternal));

        
        precontractTO.getPreContractServiceDtlTOs().addAll(PrecontractServicesHandler.populatePreContractServices(
                companyMap, preContractEntity.getPreContractsServiceDtlEntities(), addExternal));

        precontractTO.getPrecontractSowDtlTOs().addAll(PrecontractSowDtlHandler.populatePreContractSowTypes(companyMap,
                preContractEntity.getPrecontractSowDtlEntities(), addExternal));
    }

    public static PreContractTO convertPreContractEntityTOPOJO(PreContractEntity preContractEntity) {
        PreContractTO precontractTO = new PreContractTO();
        precontractTO.setId(preContractEntity.getId());
        ProjMstrEntity project = preContractEntity.getProjId();
        if (AppUtils.isNotNull(project)) {
            precontractTO.setProjId(project.getProjectId());
        }
        precontractTO.setCode(generatePrecontractCode(preContractEntity));
        precontractTO.setDesc(preContractEntity.getDesc());
        precontractTO.setPreContractType(preContractEntity.getPreContractType());
        precontractTO.setEpsName(preContractEntity.getProjId().getParentProjectMstrEntity().getProjName());
        precontractTO.setProjName(preContractEntity.getProjId().getProjName());
        precontractTO.setAllowMultiplePurchaseOrders(preContractEntity.isAllowMultiplePurchaseOrders());
        precontractTO.setResEmp(preContractEntity.getCreatedBy().getFirstName()+" "+preContractEntity.getCreatedBy().getLastName());
        WorkFlowStatusTO workFlowStatusTO = new WorkFlowStatusTO();
        workFlowStatusTO.setValue(preContractEntity.getPreContarctStatus());
        workFlowStatusTO.setDesc(WorkFlowStatus.getDescValue(preContractEntity.getPreContarctStatus()));
        precontractTO.setWorkFlowStatusTO(workFlowStatusTO);
        for (PreContractsCmpEntity contractsCmpEntity : preContractEntity.getPreContractsCmpEntities()) {
            if (StatusCodes.ACTIVE.getValue().equals(contractsCmpEntity.getStatus())
                    && CommonConstants.BIDDING_CLOSED.equalsIgnoreCase(contractsCmpEntity.getBiddingStatus())) {
                CompanyMstrEntity company = contractsCmpEntity.getCompanyId();
                if (company != null) {
                    precontractTO.getExternalWorkFlowMap().put(company.getId(), contractsCmpEntity.getCmpStatus());
                }
            }
        }
        precontractTO.setContractStageStatus(preContractEntity.getContarctStageStatus());
        precontractTO.setPurchaseOrderStatus(preContractEntity.getPurchaseOrderStatus());
        if (CommonUtil.isNotBlankDate(preContractEntity.getCloseDate())) {
            precontractTO.setCloseDate(CommonUtil.convertDateToString(preContractEntity.getCloseDate()));
        }
        if (CommonUtil.isNotBlankDate(preContractEntity.getRevisedCloseDate())) {
            precontractTO.setRevisedCloseDate(CommonUtil.convertDateToString(preContractEntity.getRevisedCloseDate()));
        }
        UserMstrEntity reqUser = preContractEntity.getReqUserId();
        if (AppUtils.isNotNull(reqUser))
            precontractTO.setReqUsrId(reqUser.getUserId());
        precontractTO.setStatus(preContractEntity.getStatus());
        precontractTO.setIsLatest(preContractEntity.getIsLatest());
        return precontractTO;
    }

    public static PreContractReqApprResp populatePreContractReqs(
            List<PreContractReqApprEntity> preContractReqApprEntities) {
        PreContractReqApprResp preContractReqResp = new PreContractReqApprResp();
        for (PreContractReqApprEntity preContractReqApprEntity : preContractReqApprEntities) {
            if (preContractReqApprEntity.isLatest()) {
                preContractReqResp.getPreContractReqApprTOs()
                        .add(PrecontractReqApprHandler.convertRequestEntityToPOJO(preContractReqApprEntity));
            }
        }
        return preContractReqResp;
    }

    public static PreContractEntity convertPreContractPOJOToEntity(PreContractTO contractTO, boolean version,
            boolean addExteranl, EPSProjRepository epsProjRepository, LoginRepository loginRepository,
            ProcureCatgRepository procureCatgRepository, EmpClassRepository empClassRepository,
            StockRepository stockRepository, ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy,
            MaterialClassRepository materialClassRepository, PlantClassRepository plantClassRepository,
            ServiceRepository serviceRepository, ProjSOWItemRepositoryCopy projSOWItemRepositoryCopy,
            ProjCostStatementsRepositoryCopy projCostStatementsRepositoryCopy) {
        PreContractEntity entity = new PreContractEntity();
        if (CommonUtil.isNonBlankLong(contractTO.getId())) {
            entity.setId(contractTO.getId());
            entity.setPreContarctStatus(contractTO.getWorkFlowStatusTO().getValue());
        } else {
            entity.setPreContarctStatus(WorkFlowStatus.DRAFT.getValue());
        }
        populatePrecontractEntity(entity, contractTO, epsProjRepository, loginRepository);
        PreContractReqApprEntity contractReqApprEntity = null;
        if (WorkFlowStatus.IN_PROCESS.getValue().equals(contractTO.getWorkFlowStatusTO().getValue())) {

            LabelKeyTO reqUserLabelkeyTO = new LabelKeyTO();
            reqUserLabelkeyTO.setId(AppUserUtils.getUserId());
            contractTO.getPreContractReqApprTO().getReqUserLabelkeyTO().setId(AppUserUtils.getUserId());
            PreContractReqApprTO contractReqApprTO = null;
            if (contractTO.getPreContractReqApprTO().getReqUserLabelkeyTO().getId() != null
                    && contractTO.getPreContractReqApprTO().getReqUserLabelkeyTO().getId() > 0) {
                contractReqApprTO = new PreContractReqApprTO();
                contractReqApprTO.setId(null);
                contractReqApprTO.setReqDate(CommonUtil.convertDateToString(new Date()));
                contractReqApprTO.setLatest(true);
                contractReqApprTO.setReqUserLabelkeyTO(reqUserLabelkeyTO);
                contractReqApprTO.setApprUserLabelkeyTO(contractTO.getPreContractReqApprTO().getApprUserLabelkeyTO());
                contractReqApprTO.setReqComments(contractTO.getPreContractReqApprTO().getReqComments());
                contractReqApprTO.setApprComments(contractTO.getPreContractReqApprTO().getApprComments());
                contractReqApprTO.setProjId(contractTO.getProjId());
                contractReqApprTO.setPreContractId(contractTO.getId());
                contractReqApprEntity = PrecontractReqApprHandler.convertReqApprovalPOJOToEntity(contractReqApprTO,
                        loginRepository);
                contractReqApprEntity.setPreContractEntity(entity);
                entity.getPreContractReqApprEntities().add(contractReqApprEntity);
                contractTO.getPreContractReqApprTO().setLatest(false);
            }
            contractTO.setPreContractReqApprTO(contractReqApprTO);
            contractTO.getPreContractReqApprTO().setReqUserLabelkeyTO(reqUserLabelkeyTO);
        } else if (WorkFlowStatus.APPROVED.getValue().equals(contractTO.getWorkFlowStatusTO().getValue())
                || WorkFlowStatus.ON_HOLD.getValue().equals(contractTO.getWorkFlowStatusTO().getValue())
                || WorkFlowStatus.REJECTED.getValue().equals(contractTO.getWorkFlowStatusTO().getValue())
                || WorkFlowStatus.SEND_BACK_REQUEST.getValue().equals(contractTO.getWorkFlowStatusTO().getValue())) {
            LabelKeyTO apprUserLabelkeyTO = new LabelKeyTO();
            apprUserLabelkeyTO.setId(AppUserUtils.getUserId());
            contractTO.getPreContractReqApprTO().setApprUserLabelkeyTO(apprUserLabelkeyTO);
            contractTO.getPreContractReqApprTO().setApprDate(CommonUtil.convertDateToString(new Date()));
            PreContractReqApprEntity preContractReqApprEntity = new PreContractReqApprEntity();
            preContractReqApprEntity.setId(contractTO.getPreContractReqApprTO().getId());
            preContractReqApprEntity.setApprUserId(loginRepository.findOne(AppUserUtils.getUserId()));
            preContractReqApprEntity.setApprDate(new Date());
            preContractReqApprEntity.setReqUserId(
                    loginRepository.findOne(contractTO.getPreContractReqApprTO().getReqUserLabelkeyTO().getId()));
            preContractReqApprEntity
                    .setReqDate(CommonUtil.convertStringToDate(contractTO.getPreContractReqApprTO().getReqDate()));
            preContractReqApprEntity.setReqcomments(contractTO.getPreContractReqApprTO().getReqComments());
            preContractReqApprEntity.setApprComments(contractTO.getPreContractReqApprTO().getApprComments());
            preContractReqApprEntity.setStatus(contractTO.getPreContractReqApprTO().getStatus());
            preContractReqApprEntity.setLatest(true);
            preContractReqApprEntity.setPreContractEntity(entity);
            entity.getPreContractReqApprEntities().add(preContractReqApprEntity);
        }
        if (!CommonUtil.isListHasData(entity.getPreContractReqApprEntities())) {
            contractTO.getPreContractReqApprTO().setLatest(true);
        }

        entity.getPreContractsEmpDtlEntities()
                .addAll(PrecontractEmpHandler.populatePreContractsEmpDtlEntities(contractTO.getPreContractEmpDtlTOs(),
                        version, addExteranl, projCostStatementsRepositoryCopy, procureCatgRepository,
                        empClassRepository,null,null));

        entity.getPreContractsPlantDtlEntities()
                .addAll(PrecontractPlantHandler.populatePreContractPlantEntities(contractTO.getPreContractPlantDtlTOs(),
                        version, addExteranl, procureCatgRepository, projCostStatementsRepositoryCopy,
                        plantClassRepository,stockRepository,projStoreStockRepositoryCopy));

        entity.getPreContractsMaterialDtlEntities()
                .addAll(PrecontractMaterialHandler.populatePreContractMaterialEntities(
                        contractTO.getPreContractMaterialDtlTOs(), version, addExteranl, procureCatgRepository,
                        stockRepository, projCostStatementsRepositoryCopy, projStoreStockRepositoryCopy,
                        materialClassRepository));

        entity.getPreContractsServiceDtlEntities()
                .addAll(PrecontractServicesHandler.populatePreContractServiceEntities(
                        contractTO.getPreContractServiceDtlTOs(), version, addExteranl, procureCatgRepository,
                        projCostStatementsRepositoryCopy, serviceRepository,  stockRepository, projStoreStockRepositoryCopy,
                        projSOWItemRepositoryCopy));

        entity.getPrecontractSowDtlEntities().addAll(PrecontractSowDtlHandler.populatePreContractsSowDtlEntities(
                contractTO.getPrecontractSowDtlTOs(), version, addExteranl, projSOWItemRepositoryCopy));

        return entity;
    }

    public static PreContractEntity createExternalApproval(PreContractTO contractTO,
            EPSProjRepository epsProjRepository, LoginRepository loginRepository,
            ProcureCatgRepository procureCatgRepository, EmpClassRepository empClassRepository,
            StockRepository stockRepository, ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy,
            MaterialClassRepository materialClassRepository, PlantClassRepository plantClassRepository,
            ServiceRepository serviceRepository, ProjSOWItemRepositoryCopy projSOWItemRepositoryCopy,
            ProjCostStatementsRepositoryCopy projCostStatementsRepositoryCopy) {
        PreContractEntity entity = new PreContractEntity();
        populatePrecontractEntity(entity, contractTO, epsProjRepository, loginRepository);
        entity.setPreContarctStatus(WorkFlowStatus.DRAFT.getValue());
        PreContractsEmpDtlEntity preContractsEmpDtlEntity = null;
        entity.setIsLatest(Boolean.TRUE);
        entity.setContarctStageStatus(ProcurmentStageStatus.STAGE1_APPROVAL.getDesc());
        List<PreContractEmpDtlTO> preContractEmpDtlTOs = contractTO.getPreContractEmpDtlTOs();

        for (PreContractEmpDtlTO contractEmpDtlTO : preContractEmpDtlTOs) {
            contractEmpDtlTO.setId(null);
            preContractsEmpDtlEntity = PrecontractEmpHandler.convertEmpPOJOToEntity(contractEmpDtlTO,
                    procureCatgRepository, empClassRepository, projCostStatementsRepositoryCopy,null,null);
            preContractsEmpDtlEntity.setPreContractEntity(entity);
            entity.getPreContractsEmpDtlEntities().add(preContractsEmpDtlEntity);
        }

        PreContractsPlantDtlEntity preContractsPlantDtlEntity = null;
        List<PreContractPlantDtlTO> preContractPlantDtlTOs = contractTO.getPreContractPlantDtlTOs();
        for (PreContractPlantDtlTO contractPlantDtlTO : preContractPlantDtlTOs) {
            contractPlantDtlTO.setId(null);
            preContractsPlantDtlEntity = PrecontractPlantHandler.convertPlantDtlPOJOToEntity(contractPlantDtlTO,
                    procureCatgRepository, projCostStatementsRepositoryCopy, plantClassRepository,null,null);
            preContractsPlantDtlEntity.setPreContractEntity(entity);
            entity.getPreContractsPlantDtlEntities().add(preContractsPlantDtlEntity);
        }

        PreContractsMaterialDtlEntity preContractsMaterialDtlEntity = null;
        List<PreContractMaterialDtlTO> preContractMaterialDtlTOs = contractTO.getPreContractMaterialDtlTOs();
        for (PreContractMaterialDtlTO contractMaterialDtlTO : preContractMaterialDtlTOs) {
            contractMaterialDtlTO.setId(null);
            preContractsMaterialDtlEntity = PrecontractMaterialHandler.convertMaterialPOJOToEntity(
                    contractMaterialDtlTO, procureCatgRepository, stockRepository, projCostStatementsRepositoryCopy,
                    projStoreStockRepositoryCopy, materialClassRepository);
            preContractsMaterialDtlEntity.setPreContractEntity(entity);
            entity.getPreContractsMaterialDtlEntities().add(preContractsMaterialDtlEntity);
        }

        PreContractsServiceDtlEntity preContractsServiceDtlEntity = null;
        List<PreContractServiceDtlTO> preContractServiceDtlTOs = contractTO.getPreContractServiceDtlTOs();
        for (PreContractServiceDtlTO contractServiceDtlTO : preContractServiceDtlTOs) {
            contractServiceDtlTO.setId(null);
            preContractsServiceDtlEntity = PrecontractServicesHandler.convertServicePOJOToEntity(contractServiceDtlTO,
                    procureCatgRepository, projCostStatementsRepositoryCopy, serviceRepository, stockRepository, projStoreStockRepositoryCopy,projSOWItemRepositoryCopy);
            preContractsServiceDtlEntity.setPreContractEntity(entity);
            entity.getPreContractsServiceDtlEntities().add(preContractsServiceDtlEntity);
        }

        PrecontractSowDtlEntity precontractSowDtlEntity = null;
        List<PrecontractSowDtlTO> precontractSowDtlTOs = contractTO.getPrecontractSowDtlTOs();
        for (PrecontractSowDtlTO precontractSowDtlTO : precontractSowDtlTOs) {
            precontractSowDtlTO.setId(null);
            precontractSowDtlEntity = PrecontractSowDtlHandler.convertSowDtlPOJOToEntity(precontractSowDtlTO,
                    projSOWItemRepositoryCopy);
            precontractSowDtlEntity.setPreContractEntity(entity);
            entity.getPrecontractSowDtlEntities().add(precontractSowDtlEntity);
        }
        List<PreContractReqApprEntity> preContractReqApprEntities = new ArrayList<>();
        PreContractReqApprEntity contractReqApprEntity = PrecontractReqApprHandler
                .convertReqApprovalPOJOToEntity(contractTO.getPreContractReqApprTO(), loginRepository);
        contractReqApprEntity.setId(null);
        contractReqApprEntity.setLatest(true);
        contractReqApprEntity.setPreContractEntity(entity);
        preContractReqApprEntities.add(contractReqApprEntity);
        entity.setPreContractReqApprEntities(preContractReqApprEntities);
        entity.setStatus(contractTO.getStatus());
        return entity;
    }

    public static ReqApprNotificationTO populateNotificationTO(PreContractTO preContractTO) {
        ReqApprNotificationTO notificationTO = new ReqApprNotificationTO();
        log.info("AppUserUtils.getUserId() " + AppUserUtils.getUserId());
        log.info("AppUserUtils.getClientId() " + AppUserUtils.getClientId());
        log.info("preContractTO.getProjId() " + preContractTO.getProjId());
        log.info("CommonConstants.APPR_STATUS_PENDING " + CommonConstants.APPR_STATUS_PENDING);
        notificationTO.setReqUserId(AppUserUtils.getUserId());
        log.info("notificationTO.getReqUserId() " + notificationTO.getReqUserId());
        notificationTO.setClientId(AppUserUtils.getClientId());
        log.info("notificationTO.getClientId() " + notificationTO.getClientId());
        notificationTO.setProjId(preContractTO.getProjId());
        log.info("notificationTO.getProjId() " + notificationTO.getProjId());
        notificationTO.setNotificationStatus(CommonConstants.APPR_STATUS_PENDING);
        log.info("notificationTO.getNotificationStatus() " + notificationTO.getNotificationStatus());
        // TODO: Generate notification code with another approach
        // notificationTO.setNotifyCode(procurmentProcRepository.generatePOTSeqCode(preContractTO.getClientId(),preContractTO.getProjId(),
        // ModuleCodesPrefixes.PROCURE_NOTIFICATION.getDesc().concat("-"+ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc()),
        // ModuleCodes.PROCUREMENT.getDesc()));
        notificationTO.setReqCode(preContractTO.getPreContractReqApprTO().getCode());
        log.info("notificationTO.getReqCode() " + notificationTO.getReqCode());
        notificationTO.setApprUserId(preContractTO.getPreContractReqApprTO().getApprUserLabelkeyTO().getId());
        log.info("notificationTO.getApprUserId() " + notificationTO.getApprUserId());
        notificationTO.setStage(preContractTO.getContractStageStatus());
        log.info("notificationTO.getStage() " + notificationTO.getStage());
        return notificationTO;
    }

    public static List<PreContractEntity> convertPOJOToEntity(List<PreContractTO> preContractTOs,
            LoginRepository loginRepository, EPSProjRepository epsProjRepository) {
        List<PreContractEntity> preContractEntities = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        PreContractEntity entity = null;
        for (PreContractTO preContractTO : preContractTOs) {
            entity = new PreContractEntity();
            if (CommonUtil.isNonBlankLong(preContractTO.getId())) {
                entity.setId(preContractTO.getId());
            }
            populatePrecontractEntity(entity, preContractTO, epsProjRepository, loginRepository);
            entity.setReqUserId(loginRepository.findOne(AppUserUtils.getUserId()));
            entity.setContarctStageStatus(ProcurmentStageStatus.STAGE1_REQUEST.getDesc());
            if (CommonUtil.isBlankInteger(preContractTO.getApproveStatus())) {
                entity.setPreContarctStatus(WorkFlowStatus.DRAFT.getValue());
            } else {
                entity.setPreContarctStatus(preContractTO.getApproveStatus());
            }
            preContractEntities.add(entity);
        }
        return preContractEntities;
    }

    public static DocumentTransmittalTO convertTransmittalEntityTOPOJO(
            DocumentTransmittalMessageEntity transmittalMessageEntity) {
        DocumentTransmittalTO transmittalTO = new DocumentTransmittalTO();
        transmittalTO.setId(transmittalMessageEntity.getId());
        PreContractEntity preContractEntity = transmittalMessageEntity.getContractId();
        if (null != preContractEntity) {
            transmittalTO.setContractId(preContractEntity.getId());
        }

        ProjMstrEntity projMstrEntity = transmittalMessageEntity.getProjId();
        if (null != projMstrEntity) {
            transmittalTO.setProjId(projMstrEntity.getProjectId());
        }

        transmittalTO.setDesc(transmittalMessageEntity.getDesc());
        transmittalTO.setIssuedBy(transmittalMessageEntity.getIssuedBy());
        transmittalTO.setAcceptedBy(transmittalMessageEntity.getAcceptedBy());
        transmittalTO.setSign(transmittalMessageEntity.getSign());
        transmittalTO.setName(transmittalMessageEntity.getName());
        transmittalTO.setDesignation(transmittalMessageEntity.getDesignation());
        transmittalTO.setCompanyRep(transmittalMessageEntity.getCompanyRep());
        transmittalTO.setVendorRep(transmittalMessageEntity.getVendorRep());
        transmittalTO.setVendorName(transmittalMessageEntity.getVendorName());
        transmittalTO.setVendorSign(transmittalMessageEntity.getVendorSign());
        transmittalTO.setVendorDesignation(transmittalMessageEntity.getVendorDesignation());
        transmittalTO.setStatus(transmittalMessageEntity.getStatus());

        return transmittalTO;
    }

    public static DocumentTransmittalMessageEntity convertTransmittalPOJOToEntity(
            DocumentTransmittalTO documentTransmittalTO, EPSProjRepository epsRepository,
            PrecontractRepository precontractRepository) {
        DocumentTransmittalMessageEntity transmittalMessageEntity = new DocumentTransmittalMessageEntity();
        if (CommonUtil.isNonBlankLong(documentTransmittalTO.getId())) {
            transmittalMessageEntity.setId(documentTransmittalTO.getId());
        }

        PreContractEntity preContractEntity = precontractRepository.findOne(documentTransmittalTO.getContractId());
        if (null != preContractEntity) {
            transmittalMessageEntity.setContractId(preContractEntity);
        }

        ProjMstrEntity projMstrEntity = epsRepository.findOne(documentTransmittalTO.getProjId());
        if (null != projMstrEntity) {
            transmittalMessageEntity.setProjId(projMstrEntity);
        }

        transmittalMessageEntity.setDesc(documentTransmittalTO.getDesc());
        transmittalMessageEntity.setIssuedBy(documentTransmittalTO.getIssuedBy());
        transmittalMessageEntity.setAcceptedBy(documentTransmittalTO.getAcceptedBy());
        transmittalMessageEntity.setSign(documentTransmittalTO.getSign());
        transmittalMessageEntity.setName(documentTransmittalTO.getName());
        transmittalMessageEntity.setDesignation(documentTransmittalTO.getDesignation());
        transmittalMessageEntity.setCompanyRep(documentTransmittalTO.getCompanyRep());
        transmittalMessageEntity.setVendorRep(documentTransmittalTO.getVendorRep());
        transmittalMessageEntity.setVendorName(documentTransmittalTO.getVendorName());
        transmittalMessageEntity.setVendorSign(documentTransmittalTO.getVendorSign());
        transmittalMessageEntity.setVendorDesignation(documentTransmittalTO.getVendorDesignation());
        transmittalMessageEntity.setStatus(documentTransmittalTO.getStatus());

        return transmittalMessageEntity;
    }

    private static PreContractEntity populatePrecontractEntity(PreContractEntity entity, PreContractTO contractTO,
            EPSProjRepository epsProjRepository, LoginRepository loginRepository) {
        entity.setProjId(epsProjRepository.findOne(contractTO.getProjId()));
        entity.setDesc(contractTO.getDesc());
        entity.setStatus(contractTO.getStatus());
        entity.setPreContractType(contractTO.getPreContractType());
        if (CommonUtil.isNotBlankStr(contractTO.getCloseDate())) {
            entity.setCloseDate(CommonUtil.convertStringToDate(contractTO.getCloseDate()));
        }
        if (CommonUtil.isNotBlankStr(contractTO.getRevisedCloseDate())) {
            entity.setRevisedCloseDate(CommonUtil.convertStringToDate(contractTO.getRevisedCloseDate()));
        }
        entity.setContarctStageStatus(contractTO.getContractStageStatus());
        entity.setPurchaseOrderStatus(contractTO.getPurchaseOrderStatus());
        entity.setCurrencyCode(contractTO.getCurrencyCode());
        entity.setIsLatest(Boolean.TRUE);
        entity.setReqUserId(loginRepository.findOne(AppUserUtils.getUserId()));
        entity.setAllowMultiplePurchaseOrders(contractTO.isAllowMultiplePurchaseOrders());
        return entity;
    }

    public static String generatePrecontractCode(PreContractEntity preContractEntity) {
        ProjMstrEntity project = preContractEntity.getProjId();
        if (AppUtils.isNotNull(project)) {
            return (ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + project.getCode() + "-"
                    + AppUtils.formatNumberToString(preContractEntity.getId()));
        }
        return null;
    }

}
