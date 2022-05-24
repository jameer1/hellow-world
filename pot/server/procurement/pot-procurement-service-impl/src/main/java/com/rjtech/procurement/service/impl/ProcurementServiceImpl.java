package com.rjtech.procurement.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.AddressRepository;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.ContactsRepository;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.repository.ServiceRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.EmailTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.WorkFlowStatusTO;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.service.impl.CommonEmailServiceImpl;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ProcurementCatg;
import com.rjtech.common.utils.ProcurmentStageStatus;
import com.rjtech.common.utils.ProcurmentWorkFlowStatus;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.common.utils.WorkFlowStatus;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.notification.dto.ProcurementNotificationsTO;
import com.rjtech.notification.dto.ReqApprNotificationTO;
import com.rjtech.notification.model.ProcurementNotificationsEntity;
import com.rjtech.notification.model.ProcurementNotificationsEntityCopy;
import com.rjtech.notification.repository.ProcurementAddltionalTimeRepository;
import com.rjtech.notification.repository.ProcurementNormalTimeRepository;
import com.rjtech.notification.repository.copy.ProcurementNotificationsRepositoryCopy;
import com.rjtech.notification.repository.copy.ReqApprNotificationRepositoryCopy;
import com.rjtech.notification.service.handler.ProcurementNotificationsHandler;
import com.rjtech.notification.service.handler.ReqApprNotificationHandler;
import com.rjtech.procurement.dto.DocumentTransmittalTO;
import com.rjtech.procurement.dto.InvoiceMaterialTo;
import com.rjtech.procurement.dto.PreContractCmpDistributionDocTO;
import com.rjtech.procurement.dto.PreContractCmpDocsTO;
import com.rjtech.procurement.dto.PreContractCmpTO;
import com.rjtech.procurement.dto.PreContractCostCodeTO;
import com.rjtech.procurement.dto.PreContractDistributionDocTO;
import com.rjtech.procurement.dto.PreContractDocsTO;
import com.rjtech.procurement.dto.PreContractEmpDtlTO;
import com.rjtech.procurement.dto.PreContractMaterialCmpTO;
import com.rjtech.procurement.dto.PreContractMaterialDtlTO;
import com.rjtech.procurement.dto.PreContractPlantCmpTO;
import com.rjtech.procurement.dto.PreContractPlantDtlTO;
import com.rjtech.procurement.dto.PreContractServiceCmpTO;
import com.rjtech.procurement.dto.PreContractServiceDtlTO;
import com.rjtech.procurement.dto.PreContractTO;
import com.rjtech.procurement.dto.PreContractsEmpCmpTO;
import com.rjtech.procurement.dto.PrecontractSowCmpTO;
import com.rjtech.procurement.dto.PrecontractSowDtlTO;
import com.rjtech.procurement.dto.ProcurementPoRepeatpoTO;
import com.rjtech.procurement.dto.ProcurementSubCategory;
import com.rjtech.procurement.dto.PurchaseOrderDetailsTO;
import com.rjtech.procurement.dto.PurchaseOrderTO;
import com.rjtech.procurement.dto.SearchManpowerDocketTO;
import com.rjtech.procurement.dto.TermsAndConditionsTO;
import com.rjtech.procurement.model.DocumentTransmittalMessageEntity;
import com.rjtech.procurement.model.MaterialPODeliveryDocketEntityCopy;
import com.rjtech.procurement.model.PreContractCmpDistributionDocEntity;
import com.rjtech.procurement.model.PreContractCmpDocEntity;
import com.rjtech.procurement.model.PreContractDistributionDocEntity;
import com.rjtech.procurement.model.PreContractDocEntity;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractReqApprEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PreContractsEmpCmpEntity;
import com.rjtech.procurement.model.PreContractsEmpDtlEntity;
import com.rjtech.procurement.model.PreContractsMaterialCmpEntity;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;
import com.rjtech.procurement.model.PreContractsPlantCmpEntity;
import com.rjtech.procurement.model.PreContractsPlantDtlEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;
import com.rjtech.procurement.model.PrecontractSowCmpEntity;
import com.rjtech.procurement.model.PrecontractSowDtlEntity;
import com.rjtech.procurement.model.PurchaseOrderDetailsEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
import com.rjtech.procurement.model.PurchaseOrderRepeatEntity;
import com.rjtech.procurement.model.TermsAndConditionsEntity;
import com.rjtech.procurement.model.WorkDairyMaterialDtlEntityCopy;
import com.rjtech.procurement.repository.CopyEmpProjectPODtlRepository;
import com.rjtech.procurement.repository.CopyMaterialDeliveryDocketRepository;
import com.rjtech.procurement.repository.CopyMaterialProjRepository;
import com.rjtech.procurement.repository.CopyPlantProjPORepository;
import com.rjtech.procurement.repository.DocumentTransmittalMsgRepository;
import com.rjtech.procurement.repository.MaterialWorkDairyRepositoryCopy;
import com.rjtech.procurement.repository.PreContractCmpRepository;
import com.rjtech.procurement.repository.PrecontractCmpDistributionDocRepository;
import com.rjtech.procurement.repository.PrecontractCmpDocRepository;
import com.rjtech.procurement.repository.PrecontractDistributionDocRepository;
import com.rjtech.procurement.repository.PrecontractDocRepository;
import com.rjtech.procurement.repository.PrecontractEmpCmpRepository;
import com.rjtech.procurement.repository.PrecontractEmpRepository;
import com.rjtech.procurement.repository.PrecontractMaterialCmpRepository;
import com.rjtech.procurement.repository.PrecontractMaterialRepository;
import com.rjtech.procurement.repository.PrecontractPlantCmpRepository;
import com.rjtech.procurement.repository.PrecontractPlantRepository;
import com.rjtech.procurement.repository.PrecontractRepository;
import com.rjtech.procurement.repository.PrecontractReqApprRepository;
import com.rjtech.procurement.repository.PrecontractServiceCmpRepository;
import com.rjtech.procurement.repository.PrecontractServiceRepository;
import com.rjtech.procurement.repository.PrecontractSowCmpRepository;
import com.rjtech.procurement.repository.PrecontractSowRepository;
//import com.rjtech.procurement.repository.ProcurementAddltionalTimeRepository;
//import com.rjtech.procurement.repository.ProcurementNormalTimeRepository;
import com.rjtech.procurement.repository.PurchaseOrderDetailsRepository;
import com.rjtech.procurement.repository.PurchaseOrderProcRepository;
import com.rjtech.procurement.repository.PurchaseOrderRepeatRepository;
import com.rjtech.procurement.repository.PurchaseOrderRepository;
import com.rjtech.procurement.repository.TermsAndConditionsRepository;
import com.rjtech.procurement.req.DocumentTransmittalMsgReq;
import com.rjtech.procurement.req.GetTermsAndConditionsReq;
import com.rjtech.procurement.req.InvoiceMaterialGetReq;
import com.rjtech.procurement.req.POProcureTypeReq;
import com.rjtech.procurement.req.PreContractCmpDelReq;
import com.rjtech.procurement.req.PreContractCmpGetReq;
import com.rjtech.procurement.req.PreContractCmpSaveReq;
import com.rjtech.procurement.req.PreContractEmpSaveReq;
import com.rjtech.procurement.req.PreContractListSaveReq;
import com.rjtech.procurement.req.PreContractMaterialSaveReq;
import com.rjtech.procurement.req.PreContractPlantSaveReq;
import com.rjtech.procurement.req.PreContractServiceSaveReq;
import com.rjtech.procurement.req.PreContractSowDtlsSaveReq;
import com.rjtech.procurement.req.PreContratCompnayDocSaveReq;
import com.rjtech.procurement.req.PrecontractDistributionDocSaveReq;
import com.rjtech.procurement.req.PrecontractDocGetReq;
import com.rjtech.procurement.req.PrecontractDocSaveReq;
import com.rjtech.procurement.req.ProcurementDelReq;
import com.rjtech.procurement.req.ProcurementFilterReq;
import com.rjtech.procurement.req.ProcurementGetReq;
import com.rjtech.procurement.req.ProcurementPoRepeatpoSaveReq;
import com.rjtech.procurement.req.ProcurementSaveReq;
import com.rjtech.procurement.req.ProcurementSubCatReq;
import com.rjtech.procurement.req.PurchaseOrderGetReq;
import com.rjtech.procurement.req.PurchaseOrderRepeatSaveReq;
import com.rjtech.procurement.req.PurchaseOrderSaveReq;
import com.rjtech.procurement.req.RepeatPurchaseOrderGetReq;
import com.rjtech.procurement.req.SaveTermsAndConditionsReq;
import com.rjtech.procurement.req.SearchInvoiceMaterialsReq;
import com.rjtech.procurement.req.SinglePurchaseOrderSaveReq;
import com.rjtech.procurement.resp.InvoiceMaterialResp;
import com.rjtech.procurement.resp.PreContractCmpDistributionDocResp;
import com.rjtech.procurement.resp.PreContractCmpDocResp;
import com.rjtech.procurement.resp.PreContractCmpResp;
import com.rjtech.procurement.resp.PreContractDistributionDocResp;
import com.rjtech.procurement.resp.PreContractDocResp;
import com.rjtech.procurement.resp.PreContractEmpResp;
import com.rjtech.procurement.resp.PreContractMaterialResp;
import com.rjtech.procurement.resp.PreContractPlantResp;
import com.rjtech.procurement.resp.PreContractReqApprResp;
import com.rjtech.procurement.resp.PreContractResp;
import com.rjtech.procurement.resp.PreContractServiceResp;
import com.rjtech.procurement.resp.PreContractSowResp;
import com.rjtech.procurement.resp.PreContractStatusResp;
import com.rjtech.procurement.resp.ProcurementSubCatResp;
import com.rjtech.procurement.resp.PurchaseOrderResp;
import com.rjtech.procurement.resp.RepeatPurchaseOrderResp;
import com.rjtech.procurement.resp.SearchManpowerResp;
import com.rjtech.procurement.resp.TermsAndConditionsResp;
import com.rjtech.procurement.service.ProcurementService;
import com.rjtech.procurement.service.handler.PrecontractCmpDocHandler;
import com.rjtech.procurement.service.handler.PrecontractCmpHandler;
import com.rjtech.procurement.service.handler.PrecontractDistributionDocHandler;
import com.rjtech.procurement.service.handler.PrecontractDocumentHandler;
import com.rjtech.procurement.service.handler.PrecontractEmpHandler;
import com.rjtech.procurement.service.handler.PrecontractHandler;
import com.rjtech.procurement.service.handler.PrecontractMaterialHandler;
import com.rjtech.procurement.service.handler.PrecontractPlantHandler;
import com.rjtech.procurement.service.handler.PrecontractServicesHandler;
import com.rjtech.procurement.service.handler.PrecontractSowDtlHandler;
import com.rjtech.procurement.service.handler.ProcurementPoRepeatpoHandler;
import com.rjtech.procurement.service.handler.PurchaseOrderDetailsHandler;
import com.rjtech.procurement.service.handler.PurchaseOrderHandler;
import com.rjtech.procurement.service.handler.TermsAndConditionsHandler;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.copy.ProjStoreStockMstrEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.projsettings.repository.copy.ProjCostStatementsRepositoryCopy;
//import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;


@Service(value = "procurementService")
@RJSService(modulecode = "procurementService")
@Transactional
public class ProcurementServiceImpl implements ProcurementService {
	
	private static String pot = "\"Project on Track\"";
	
	@Autowired
	private PrecontractRepository precontractRepository;

	@Autowired
	private PreContractCmpRepository preContractCmpRepository;

	@Autowired
	private PrecontractEmpRepository precontractEmpRepository;

	@Autowired
	private PrecontractPlantRepository precontractPlantRepository;

	@Autowired
	private PrecontractMaterialRepository precontractMaterialRepository;

	@Autowired
	private PrecontractServiceRepository precontractServiceRepository;

	@Autowired
	private PrecontractSowRepository precontractSowRepository;

	@Autowired
	private PrecontractDocRepository precontractDocRepository;

	@Autowired
	private PrecontractDistributionDocRepository precontractDitributionDocRepository;

	@Autowired
	private PrecontractCmpDistributionDocRepository precontractCmpDistributionDocRepository;

	@Autowired
	private PrecontractCmpDocRepository precontractCmpDocRepository;

	@Autowired
	private PrecontractReqApprRepository precontractReqApprRepository;

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private PrecontractEmpCmpRepository precontractEmpCmpRepository;

	@Autowired
	private PrecontractPlantCmpRepository precontractPlantCmpRepository;

	@Autowired
	private PrecontractMaterialCmpRepository precontractMaterialCmpRepository;

	@Autowired
	private PrecontractServiceCmpRepository precontractServiceCmpRepository;

	@Autowired
	private ReqApprNotificationRepositoryCopy reqApprNotificationRepository;

	@Autowired
	private PurchaseOrderProcRepository purchaseOrderProcRepository;

	@Autowired
	private PrecontractSowCmpRepository precontractSowCmpRepository;

	@Autowired
	private DocumentTransmittalMsgRepository documentTransmittalMsgRepository;

	//@Autowired
	//private ProcurementPoRepeatpoRepository procurementporepeatporepository;

	@Autowired
	private PurchaseOrderRepeatRepository purchaseOrderRepeatRepository;

	@Autowired
	private EPSProjService epsProjService;

	@Autowired
	private ProjDocFolderRepository projDocFolderRepository;

	@Autowired
	private PurchaseOrderDetailsRepository poDetailsRepository;

	@Autowired
	private TermsAndConditionsRepository termsAndConditionsRepository;

	@Autowired
	private TermsAndConditionsHandler termsAndConditionsHandler;

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private EPSProjRepository epsRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ContactsRepository contactsRepository;

	@Autowired
	private ProcureCatgRepository procureCatgRepository;

	@Autowired
	private EmpClassRepository empClassRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy;

	@Autowired
	private MaterialClassRepository materialClassRepository;

	@Autowired
	private PlantClassRepository plantClassRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private ProjSOWItemRepositoryCopy projSOWItemRepositoryCopy;

	@Autowired
	@Qualifier("projGeneralRepositoryProcurementCopy")
	private ProjGeneralRepositoryCopy projGeneralRepositoryCopy;

	@Autowired
	private PrecontractDocumentHandler precontractDocumentHandler;

	@Autowired
	private ProjCostStatementsRepositoryCopy projCostStatementsRepositoryCopy;
	
	@Autowired
	private UploadUtil uploadUtil;
	
	@Autowired
	private ProjDocFileRepository projDocFileRepo;	

	@Autowired
	private CopyEmpProjectPODtlRepository copyEmpProjectPODtlRepository;
	
	@Autowired
	private CopyPlantProjPORepository copyPlantProjPORepository;
	
	@Autowired
	private CopyMaterialProjRepository copyMaterialProjRepository;
	
	@Autowired
	private CopyMaterialDeliveryDocketRepository copyMaterialDeliveryDocketRepository;
	
	@Autowired
	private MaterialWorkDairyRepositoryCopy materialWorkDairyRepositoryCopy;
	
	@Autowired
	private ProcurementNotificationsRepositoryCopy procurementNotificationsRepositoryCopy;
	
	@Autowired
	private ProcurementNormalTimeRepository procurementNormalTimeRepository;
	
	@Autowired
	ProcurementAddltionalTimeRepository procurementAddltionalTimeRepository;
	

	@Autowired
	private CommonEmailServiceImpl commonEmail;

	public PreContractResp getLatestPreContracts(ProcurementFilterReq procurementFilterReq) {
		List<PreContractEntity> preContractEntites = null;
		List<PurchaseOrderEntity> purchaseOrderEntities = new ArrayList<PurchaseOrderEntity>();
		Date toDate = null;
		Date fromDate = null;

		if (CommonUtil.isNotBlankStr(procurementFilterReq.getFromDate())
				&& CommonUtil.isNotBlankStr(procurementFilterReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(procurementFilterReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(procurementFilterReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		List<Long> projIds = null;
		if (CommonUtil.isListHasData(procurementFilterReq.getProjIds())) {
			projIds = procurementFilterReq.getProjIds();
		} else {
			projIds = epsProjService.getUserProjIds();
		}
		Long reqUsrId = null;
		if (procurementFilterReq.isLoginUser()) {
			reqUsrId = AppUserUtils.getUserId();
		}
		purchaseOrderEntities = purchaseOrderRepository
				.findAllPurchaseOrders(projIds);
		preContractEntites = precontractRepository.findLatestPreContracts(procurementFilterReq.getStatus(), reqUsrId,
				projIds, fromDate, toDate);
		PreContractResp preContractResp = PrecontractHandler.populatePreContracts(preContractEntites,procurementNormalTimeRepository,procurementAddltionalTimeRepository);
		for(PreContractTO preContractTO:preContractResp.getPreContractTOs()) {
			for(PurchaseOrderEntity purchaseOrderEntity:purchaseOrderEntities) {

				if(purchaseOrderEntity.getPreContractsCmpEntity().getPreContractEntity().getId() == preContractTO.getId()) {
					preContractTO.setPoStatus(purchaseOrderEntity.getStatus());
				}else {
					preContractTO.setPoStatus(1);
				}
			}
		}
		return preContractResp;
	}

	public PreContractResp getLatestPreContractsReport(ProcurementFilterReq procurementFilterReq) {
		List<PreContractEntity> preContractEntites = null;

		Date toDate = null;
		Date fromDate = null;

		if (CommonUtil.isNotBlankStr(procurementFilterReq.getFromDate())
				&& CommonUtil.isNotBlankStr(procurementFilterReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(procurementFilterReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(procurementFilterReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		List<Long> projIds = null;
		if (CommonUtil.isListHasData(procurementFilterReq.getProjIds())) {
			projIds = procurementFilterReq.getProjIds();
		} else {
			projIds = epsProjService.getUserProjIds();
		}
		Long reqUsrId = null;
		if (procurementFilterReq.isLoginUser()) {
			reqUsrId = AppUserUtils.getUserId();
		}
		preContractEntites = precontractRepository.findLatestPreContracts(procurementFilterReq.getStatus(), reqUsrId,
				projIds, fromDate, toDate);
		List<Long> projectIds = new ArrayList<Long>();
		List<Long> precontractIds = new ArrayList<Long>();
		for(PreContractEntity preContractEnti: preContractEntites) {
			projectIds.add(preContractEnti.getProjId().getProjectId());
			precontractIds.add(preContractEnti.getId());
		}
		List<PreContractEntity> precontractEntitiesByIds = precontractRepository.findPreContractDetailsByIds(
				precontractIds, projectIds);
		PreContractResp preContractResp = new PreContractResp();
		preContractResp = PrecontractHandler.populatePreContractsReport(precontractEntitiesByIds,false);
		
		for(PreContractTO preContractTO:preContractResp.getPreContractTOs()) {
			Integer totalEstimateBudget = 0;
			for(PreContractEmpDtlTO preContractEmpDtlTO:preContractTO.getPreContractEmpDtlTOs()) {
				totalEstimateBudget+=preContractEmpDtlTO.getEstimateEmpBudget().intValue();
			}
			for(PreContractPlantDtlTO preContractPlantDtlTO:preContractTO.getPreContractPlantDtlTOs()) {
				totalEstimateBudget+=preContractPlantDtlTO.getEstimatePlantBudget().intValue();
			}
			for(PreContractMaterialDtlTO preContractMaterialDtlTO:preContractTO.getPreContractMaterialDtlTOs()) {
				totalEstimateBudget+=preContractMaterialDtlTO.getEstimateMatreialCost().intValue();
			}
			for(PreContractServiceDtlTO preContractServiceDtlTO:preContractTO. getPreContractServiceDtlTOs()) {
				totalEstimateBudget+=preContractServiceDtlTO.getEstimateServiceCost().intValue();
			}
			for(PrecontractSowDtlTO precontractSowDtlTO:preContractTO.getPrecontractSowDtlTOs()) {
				totalEstimateBudget+=precontractSowDtlTO.getEstimateSowCost().intValue();
			}
			preContractTO.setEstimateTotalCost(totalEstimateBudget);
		}
		return preContractResp;
	}
	
	public PreContractResp getLatestPreContractsempTaskreport(ProcurementFilterReq procurementFilterReq) {
		List<PreContractEntity> preContractEntites = null;
		List<PurchaseOrderEntity> purchaseOrderEntities = new ArrayList<PurchaseOrderEntity>();
		Date toDate = null;
		Date fromDate = null;

		if (CommonUtil.isNotBlankStr(procurementFilterReq.getFromDate())
				&& CommonUtil.isNotBlankStr(procurementFilterReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(procurementFilterReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(procurementFilterReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		List<Long> projIds = null;
		if (CommonUtil.isListHasData(procurementFilterReq.getProjIds())) {
			projIds = procurementFilterReq.getProjIds();
		} else {
			projIds = epsProjService.getUserProjIds();
		}
		Long reqUsrId = null;
		if (procurementFilterReq.isLoginUser()) {
			reqUsrId = AppUserUtils.getUserId();
		}
		purchaseOrderEntities = purchaseOrderRepository
				.findAllPurchaseOrders(projIds);
		preContractEntites = precontractRepository.findLatestPreContracts(procurementFilterReq.getStatus(), reqUsrId,
				projIds, fromDate, toDate);
		List<Long> projectIds = new ArrayList<Long>();
		List<Long> precontractIds = new ArrayList<Long>();
		for(PreContractEntity preContractEnti: preContractEntites) {
			projectIds.add(preContractEnti.getProjId().getProjectId());
			precontractIds.add(preContractEnti.getId());
		}
		List<PreContractEntity> precontractEntitiesByIds = precontractRepository.findPreContractDetailsByIds(
				precontractIds, projectIds);
		PreContractResp preContractResp = new PreContractResp();
		preContractResp = PrecontractHandler.populatePreContractsReport(precontractEntitiesByIds,false);
		for(PreContractTO preContractTO:preContractResp.getPreContractTOs()) {
			if(preContractTO.getContractStageStatus().equals("Stage 1 Approval")) {
				List<PreContractReqApprEntity> preContractEntitesStage1 = precontractReqApprRepository
						.findPreContractReqApprs(preContractTO.getId(),1);
				preContractTO.setResEmp(preContractEntitesStage1.get(0).getApprUserId().getFirstName()+" "+preContractEntitesStage1.get(0).getApprUserId().getLastName());
			}else if(preContractTO.getContractStageStatus().equals("RFQ/Tendering") || preContractTO.getContractStageStatus().equals("Stage 2 Request")) {
				List<PreContractsCmpEntity> preContractsCmpEntities = preContractCmpRepository
						.findPreContractCompnies(preContractTO.getId(),1);
				if (preContractsCmpEntities.size() > 0) {
				preContractTO.setResEmp(preContractsCmpEntities.get(0).getUpdatedBy().getFirstName()+" "+preContractsCmpEntities.get(0).getUpdatedBy().getLastName());
				} else {
					List<PreContractReqApprEntity> preContractEntitesStage2req = precontractReqApprRepository
							.findPreContractReqApprs(preContractTO.getId(),1);
					preContractTO.setResEmp(preContractEntitesStage2req.get(0).getApprUserId().getFirstName()+" "+preContractEntitesStage2req.get(0).getApprUserId().getLastName());
				}
			}else if(preContractTO.getContractStageStatus().equals("Stage 2 Approval")){
				List<PreContractReqApprEntity> preContractEntitesStage2 = precontractReqApprRepository
						.findPreContractReqApprs(preContractTO.getId(),1);
				preContractTO.setResEmp(preContractEntitesStage2.get(0).getApprUserId().getFirstName()+" "+preContractEntitesStage2.get(0).getApprUserId().getLastName());
			}else if(preContractTO.getContractStageStatus().equals("Purchase Order")){
				List<PurchaseOrderEntity> purchaseOrders = purchaseOrderRepository.getPurchaseOrdersByPrecontract(preContractTO.getId());
				preContractTO.setResEmp(purchaseOrders.get(0).getUpdatedBy().getFirstName()+" "+purchaseOrders.get(0).getUpdatedBy().getLastName());
			}
			Integer totalEstimateBudget = 0;
			for(PreContractEmpDtlTO preContractEmpDtlTO:preContractTO.getPreContractEmpDtlTOs()) {
				totalEstimateBudget+=preContractEmpDtlTO.getEstimateEmpBudget().intValue();
			}
			for(PreContractPlantDtlTO preContractPlantDtlTO:preContractTO.getPreContractPlantDtlTOs()) {
				totalEstimateBudget+=preContractPlantDtlTO.getEstimatePlantBudget().intValue();
			}
			for(PreContractMaterialDtlTO preContractMaterialDtlTO:preContractTO.getPreContractMaterialDtlTOs()) {
				totalEstimateBudget+=preContractMaterialDtlTO.getEstimateMatreialCost().intValue();
			}
			for(PreContractServiceDtlTO preContractServiceDtlTO:preContractTO. getPreContractServiceDtlTOs()) {
				totalEstimateBudget+=preContractServiceDtlTO.getEstimateServiceCost().intValue();
			}
			for(PrecontractSowDtlTO precontractSowDtlTO:preContractTO.getPrecontractSowDtlTOs()) {
				totalEstimateBudget+=precontractSowDtlTO.getEstimateSowCost().intValue();
			}
			preContractTO.setEstimateTotalCost(totalEstimateBudget);
		}
		List<PreContractTO> preContractTOss = new ArrayList<PreContractTO>();
		for(PreContractTO preContractTOs:preContractResp.getPreContractTOs()) {
			if(preContractTOs.getPurchaseOrderStatus() == null){
				preContractTOss.add(preContractTOs);
			}
		}
		preContractResp.setPreContractTOs(preContractTOss);
		return preContractResp;
	}

	public PreContractResp getLatestPreContractsCostCodeReports(ProcurementFilterReq procurementFilterReq) {
		List<PreContractEntity> preContractEntites = null;
		List<PurchaseOrderEntity> purchaseOrderEntities = new ArrayList<PurchaseOrderEntity>();
		Date toDate = null;
		Date fromDate = null;

		if (CommonUtil.isNotBlankStr(procurementFilterReq.getFromDate())
				&& CommonUtil.isNotBlankStr(procurementFilterReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(procurementFilterReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(procurementFilterReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		List<Long> projIds = null;
		if (CommonUtil.isListHasData(procurementFilterReq.getProjIds())) {
			projIds = procurementFilterReq.getProjIds();
		} else {
			projIds = epsProjService.getUserProjIds();
		}
		Long reqUsrId = null;
		if (procurementFilterReq.isLoginUser()) {
			reqUsrId = AppUserUtils.getUserId();
		}
		purchaseOrderEntities = purchaseOrderRepository
				.findAllPurchaseOrders(projIds);
		preContractEntites = precontractRepository.findLatestPreContracts(procurementFilterReq.getStatus(), reqUsrId,
				projIds, fromDate, toDate);
		List<Long> projectIds = new ArrayList<Long>();
		List<Long> precontractIds = new ArrayList<Long>();
		for(PreContractEntity preContractEnti: preContractEntites) {
			projectIds.add(preContractEnti.getProjId().getProjectId());
			precontractIds.add(preContractEnti.getId());
		}
		List<PreContractEntity> precontractEntitiesByIds = precontractRepository.findPreContractDetailsByIds(
				precontractIds, projectIds);
		PreContractResp preContractResp = new PreContractResp();
		preContractResp = PrecontractHandler.populatePreContractsReport(precontractEntitiesByIds,false);
		
		List<PreContractCostCodeTO> preContractCostCodeTOs = new ArrayList<PreContractCostCodeTO>();
		
		  List<PreContractTO> preContractTOss = new ArrayList<PreContractTO>();
		  for(PreContractTO preContractTOs:preContractResp.getPreContractTOs()) {
		  if(preContractTOs.getPurchaseOrderStatus() == null){
		  preContractTOss.add(preContractTOs); } }
		  preContractResp.setPreContractTOs(preContractTOss);
		 
		for(PreContractTO preContractTO:preContractResp.getPreContractTOs()) {
			for(PreContractEmpDtlTO preContractEmpDtlTO:preContractTO.getPreContractEmpDtlTOs()) {
				PreContractCostCodeTO preContractCostCodeEmp = new PreContractCostCodeTO();
				preContractCostCodeEmp.setId(preContractTO.getId());
				preContractCostCodeEmp.setDesc(preContractTO.getDesc());
				preContractCostCodeEmp.setProjCode(preContractTO.getProjCode());
				preContractCostCodeEmp.setPreContractType(preContractTO.getPreContractType());
				preContractCostCodeEmp.setContractStageStatus(preContractTO.getContractStageStatus());
				preContractCostCodeEmp.setCode(preContractTO.getCode());
				preContractCostCodeEmp.setProjId(preContractTO.getProjId());
				preContractCostCodeEmp.setCostCodeId(preContractEmpDtlTO.getCostCode());
				preContractCostCodeEmp.setCostCodeDesc(preContractEmpDtlTO.getCostCodeDesc());
				preContractCostCodeEmp.setEstimateBudget(preContractEmpDtlTO.getEstimateEmpBudget().intValue());
				preContractCostCodeTOs.add(preContractCostCodeEmp);
			}
			for(PreContractPlantDtlTO preContractPlantDtlTO:preContractTO.getPreContractPlantDtlTOs()) {
				PreContractCostCodeTO preContractCostCodePlant = new PreContractCostCodeTO();
				preContractCostCodePlant.setId(preContractTO.getId());
				preContractCostCodePlant.setDesc(preContractTO.getDesc());
				preContractCostCodePlant.setProjCode(preContractTO.getProjCode());
				preContractCostCodePlant.setPreContractType(preContractTO.getPreContractType());
				preContractCostCodePlant.setContractStageStatus(preContractTO.getContractStageStatus());
				preContractCostCodePlant.setCode(preContractTO.getCode());
				preContractCostCodePlant.setProjId(preContractTO.getProjId());
				preContractCostCodePlant.setCostCodeId(preContractPlantDtlTO.getCostCode());
				preContractCostCodePlant.setCostCodeDesc(preContractPlantDtlTO.getCostCodeDesc());
				preContractCostCodePlant.setEstimateBudget(preContractPlantDtlTO.getEstimatePlantBudget().intValue());
				preContractCostCodeTOs.add(preContractCostCodePlant);
			}
			for(PreContractMaterialDtlTO preContractMaterialDtlTO:preContractTO.getPreContractMaterialDtlTOs()) {
				PreContractCostCodeTO preContractCostCodeMaterial = new PreContractCostCodeTO();
				preContractCostCodeMaterial.setId(preContractTO.getId());
				preContractCostCodeMaterial.setDesc(preContractTO.getDesc());
				preContractCostCodeMaterial.setProjCode(preContractTO.getProjCode());
				preContractCostCodeMaterial.setPreContractType(preContractTO.getPreContractType());
				preContractCostCodeMaterial.setContractStageStatus(preContractTO.getContractStageStatus());
				preContractCostCodeMaterial.setCode(preContractTO.getCode());
				preContractCostCodeMaterial.setProjId(preContractTO.getProjId());
				preContractCostCodeMaterial.setCostCodeId(preContractMaterialDtlTO.getCostCode());
				preContractCostCodeMaterial.setCostCodeDesc(preContractMaterialDtlTO.getCostCodeDesc());
				preContractCostCodeMaterial.setEstimateBudget(preContractMaterialDtlTO.getEstimateMatreialCost().intValue());
				preContractCostCodeTOs.add(preContractCostCodeMaterial);
			}
			for(PreContractServiceDtlTO preContractServiceDtlTO:preContractTO. getPreContractServiceDtlTOs()) {
				PreContractCostCodeTO preContractCostCodeSevice = new PreContractCostCodeTO();
				preContractCostCodeSevice.setId(preContractTO.getId());
				preContractCostCodeSevice.setDesc(preContractTO.getDesc());
				preContractCostCodeSevice.setProjCode(preContractTO.getProjCode());
				preContractCostCodeSevice.setPreContractType(preContractTO.getPreContractType());
				preContractCostCodeSevice.setContractStageStatus(preContractTO.getContractStageStatus());
				preContractCostCodeSevice.setCode(preContractTO.getCode());
				preContractCostCodeSevice.setProjId(preContractTO.getProjId());
				preContractCostCodeSevice.setCostCodeId(preContractServiceDtlTO.getCostCode());
				preContractCostCodeSevice.setCostCodeDesc(preContractServiceDtlTO.getCostCodeDesc());
				preContractCostCodeSevice.setEstimateBudget(preContractServiceDtlTO.getEstimateServiceCost().intValue());
				preContractCostCodeTOs.add(preContractCostCodeSevice);
			}
			for(PrecontractSowDtlTO precontractSowDtlTO:preContractTO.getPrecontractSowDtlTOs()) {
				PreContractCostCodeTO preContractCostCodeSOW = new PreContractCostCodeTO();
				preContractCostCodeSOW.setId(preContractTO.getId());
				preContractCostCodeSOW.setDesc(preContractTO.getDesc());
				preContractCostCodeSOW.setProjCode(preContractTO.getProjCode());
				preContractCostCodeSOW.setPreContractType(preContractTO.getPreContractType());
				preContractCostCodeSOW.setContractStageStatus(preContractTO.getContractStageStatus());
				preContractCostCodeSOW.setCode(preContractTO.getCode());
				preContractCostCodeSOW.setProjId(preContractTO.getProjId());
				preContractCostCodeSOW.setCostCodeId(precontractSowDtlTO.getCostCode());
				preContractCostCodeSOW.setCostCodeDesc(precontractSowDtlTO.getCostCodeDesc());
				preContractCostCodeSOW.setEstimateBudget(precontractSowDtlTO.getEstimateSowCost().intValue());
				preContractCostCodeTOs.add(preContractCostCodeSOW);
			}
		}
		preContractResp.setPreContractCostCodeTOs(preContractCostCodeTOs);
		System.out.println("Total records:- "+preContractResp.getPreContractCostCodeTOs().size());
		return preContractResp;
	}

	public PreContractResp getInteranlPreContracts(ProcurementFilterReq procurementFilterReq) {
		Date toDate = null;
		Date fromDate = null;
		Integer approveStatus = null;
		List<PreContractEntity> preContractEntites = null;

		if (CommonUtil.isBlankInteger(procurementFilterReq.getApproveStatus())) {
			approveStatus = ProcurmentWorkFlowStatus.DRAFT.getValue();
		} else {
			approveStatus = procurementFilterReq.getApproveStatus();
		}

		if (CommonUtil.isNotBlankStr(procurementFilterReq.getFromDate())
				&& CommonUtil.isNotBlankStr(procurementFilterReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(procurementFilterReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(procurementFilterReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		List<Long> projIds = null;
		if (CommonUtil.isListHasData(procurementFilterReq.getProjIds())) {
			projIds = procurementFilterReq.getProjIds();
		} else {
			projIds = epsProjService.getUserProjIds();
		}
		Long reqUsrId = null;
		if (procurementFilterReq.isLoginUser()) {
			reqUsrId = AppUserUtils.getUserId();
		}
		preContractEntites = precontractRepository.findInternalPreContracts(procurementFilterReq.getStatus(), reqUsrId,
				approveStatus, projIds, fromDate, toDate);
		return PrecontractHandler.populatePreContracts(preContractEntites,procurementNormalTimeRepository,procurementAddltionalTimeRepository);
	}

	public PreContractCmpResp getPreContractRFQs(ProcurementFilterReq procurementFilterReq) {
		Date toDate = null;
		Date fromDate = null;
		String biddingStatus = null;
		List<PreContractsCmpEntity> preContractsCmpEntities = null;
		if (CommonUtil.isBlankStr(procurementFilterReq.getBiddingStatus())) {
			biddingStatus = CommonConstants.BIDDING_OPEN;
		} else {
			biddingStatus = procurementFilterReq.getBiddingStatus();
		}
		if (CommonUtil.isNotBlankStr(procurementFilterReq.getFromDate())
				&& CommonUtil.isNotBlankStr(procurementFilterReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(procurementFilterReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(procurementFilterReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		List<Long> projIds = null;
		if (CommonUtil.isListHasData(procurementFilterReq.getProjIds())) {
			projIds = procurementFilterReq.getProjIds();
		} else {
			projIds = epsProjService.getUserProjIds();
		}
		Long reqUsrId = null;
		if (procurementFilterReq.isLoginUser()) {
			reqUsrId = AppUserUtils.getUserId();
		}
		preContractsCmpEntities = preContractCmpRepository.findPreContractRFQs(procurementFilterReq.getStatus(),
				biddingStatus, reqUsrId, projIds, fromDate, toDate);
		return PrecontractCmpHandler.populatePreContractCmpResp(preContractsCmpEntities);
	}

	public PreContractResp getExteranlPreContracts(ProcurementFilterReq procurementFilterReq) {
		Date toDate = null;
		Date fromDate = null;
		List<PreContractEntity> preContractEntites = null;
		Integer approveStatus = null;

		if (CommonUtil.isBlankInteger(procurementFilterReq.getApproveStatus())) {
			approveStatus = ProcurmentWorkFlowStatus.DRAFT.getValue();
		} else {
			approveStatus = procurementFilterReq.getApproveStatus();
		}

		if (CommonUtil.isNotBlankStr(procurementFilterReq.getFromDate())
				&& CommonUtil.isNotBlankStr(procurementFilterReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(procurementFilterReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(procurementFilterReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		List<Long> projIds = null;
		if (CommonUtil.isListHasData(procurementFilterReq.getProjIds())) {
			projIds = procurementFilterReq.getProjIds();
		} else {
			projIds = epsProjService.getUserProjIds();
		}
		Long reqUsrId = null;
		if (procurementFilterReq.isLoginUser()) {
			reqUsrId = AppUserUtils.getUserId();
		}
		preContractEntites = precontractRepository.findExternalPreContracts(procurementFilterReq.getStatus(), reqUsrId,
				approveStatus, projIds, fromDate, toDate);
		for (PreContractEntity preentity : preContractEntites) {
			preentity.setPreContarctStatus(approveStatus);
		}
		return PrecontractHandler.populatePreContracts(preContractEntites,procurementNormalTimeRepository,procurementAddltionalTimeRepository);
	}

	public PreContractResp getPreContractsForRfq(ProcurementFilterReq procurementFilterReq) {
		Date date  = new Date();
		List<PreContractEntity> preContractEntites = precontractRepository.findPreContractsForRfq(
				procurementFilterReq.getProjId(), procurementFilterReq.getApproveStatus(),
				procurementFilterReq.getStatus(),date);
		return PrecontractHandler.populatePreContracts(preContractEntites,procurementNormalTimeRepository,procurementAddltionalTimeRepository);
	}

	public PreContractStatusResp getWorkFlowStatus() {
		PreContractStatusResp preContractStatusResp = new PreContractStatusResp();
		WorkFlowStatusTO workFlowStatusTO = null;
		for (WorkFlowStatus workFlowStatus : WorkFlowStatus.values()) {
			workFlowStatusTO = new WorkFlowStatusTO();
			workFlowStatusTO.setValue(workFlowStatus.getValue());
			workFlowStatusTO.setDesc(workFlowStatus.getDesc());
			preContractStatusResp.getWorkFlowStatusTOs().add(workFlowStatusTO);
		}
		return preContractStatusResp;
	}

	public PreContractResp getInternalPreContractDetailsById(ProcurementGetReq procurementGetReq) {
		System.out.println("ContractId:"+procurementGetReq.getContractId()+" getProjId"+procurementGetReq.getProjId()+" status:"+procurementGetReq.getStatus());
		List<PreContractEntity> preContractEntites = precontractRepository.findPreContractDetailsById(
				procurementGetReq.getContractId(), procurementGetReq.getProjId(), procurementGetReq.getStatus());
		return PrecontractHandler.populatePreContractDetails(preContractEntites, false);

	}

	public PreContractResp getExternalPreContractDetailsById(ProcurementGetReq procurementGetReq) {
		List<PreContractEntity> preContractEntites = precontractRepository.findPreContractDetailsById(
				procurementGetReq.getContractId(), procurementGetReq.getProjId(), procurementGetReq.getStatus());
		PreContractResp preContractResp = PrecontractHandler.populatePreContractDetails(preContractEntites, true);
		for(PreContractTO preContractTO:preContractResp.getPreContractTOs()) {
			List<PurchaseOrderEntity> purchaseOrders = purchaseOrderRepository.getPurchaseOrdersByPrecontract(preContractTO.getId());
			if(purchaseOrders.size()>0) {
			preContractTO.setPaymentDays(purchaseOrders.get(0).getPaymentInDays());
			}
		}
		return preContractResp;
	}

	public PreContractResp getPreContractCmpQuoteDetails(ProcurementGetReq procurementGetReq) {
		List<PreContractEntity> preContractEntites = precontractRepository.findPreContractDetailsById(
				procurementGetReq.getContractId(), procurementGetReq.getProjId(), procurementGetReq.getStatus());
		return PrecontractHandler.populatePreContractCmpQuoteDetails(preContractEntites, procurementGetReq, true);
	}

	public PreContractEmpResp getPreContractEmpTypes(ProcurementGetReq procurementGetReq) {
		List<PreContractsEmpDtlEntity> preContractsEmpDtlEntities = precontractEmpRepository
				.findPreContractEmpTypes(procurementGetReq.getContractId(), procurementGetReq.getStatus());
		return PrecontractEmpHandler.getPreContractEmpTOs(preContractsEmpDtlEntities, procurementGetReq.isExternal());
	}

	public PreContractPlantResp getPreContractPlants(ProcurementGetReq procurementGetReq) {
		List<PreContractsPlantDtlEntity> preContractsPlantDtlEntities = precontractPlantRepository
				.findPreContractPlants(procurementGetReq.getContractId(), procurementGetReq.getStatus());
		return PrecontractPlantHandler.getPreContractPlantTOs(preContractsPlantDtlEntities,
				procurementGetReq.isExternal());
	}

	public PreContractMaterialResp getPreContractMaterials(ProcurementGetReq procurementGetReq) {
		List<PreContractsMaterialDtlEntity> preContractsMaterialDtlEntities = precontractMaterialRepository
				.findPreContractMaterials(procurementGetReq.getContractId(), procurementGetReq.getStatus());
		return PrecontractMaterialHandler.getPreContractMaterialTOs(preContractsMaterialDtlEntities,
				procurementGetReq.isExternal());
	}

	public PreContractServiceResp getPreContractServices(ProcurementGetReq procurementGetReq) {
		List<PreContractsServiceDtlEntity> preContractsServiceDtlEntities = precontractServiceRepository
				.findPreContractServices(procurementGetReq.getContractId(), procurementGetReq.getStatus());
		return PrecontractServicesHandler.getPreContractServiceTOs(preContractsServiceDtlEntities,
				procurementGetReq.isExternal());
	}

	public PreContractSowResp getPreContractSOWTypes(ProcurementGetReq procurementGetReq) {
		List<PrecontractSowDtlEntity> precontractSowDtlEntities = precontractSowRepository
				.findPrecontractSowTypes(procurementGetReq.getContractId(), procurementGetReq.getStatus());
		return PrecontractSowDtlHandler.getPreContractSowTOs(precontractSowDtlEntities, procurementGetReq.isExternal());
	}

	public Long saveInternalPreContrancts(ProcurementSaveReq procurementSaveReq) {
		PreContractTO preContractTO = procurementSaveReq.getPreContractTO();
		if (CommonUtil.isNonBlankInteger(procurementSaveReq.getApprvStatus())) {
			WorkFlowStatusTO workFlowStatusTO = new WorkFlowStatusTO();
			workFlowStatusTO.setValue(procurementSaveReq.getApprvStatus());
			preContractTO.setWorkFlowStatusTO(workFlowStatusTO);
			ReqApprNotificationTO notificationTO = PrecontractHandler.populateNotificationTO(preContractTO);
			reqApprNotificationRepository.save(
					ReqApprNotificationHandler.convertPOJOToEntity(notificationTO, loginRepository, epsRepository));
			// Start insert procurement notification
						System.out.println("procurementSaveReq.getApprvStatus() " + procurementSaveReq.getApprvStatus());
						if (procurementSaveReq.getApprvStatus() == 2) { 
							ProcurementNotificationsEntityCopy procNotEntity = procurementNotificationsRepositoryCopy
									.findPreContractNotificationDetails(procurementSaveReq.getPreContractTO().getId(),procurementSaveReq.getPreContractTO().getStatus());
							if(procNotEntity == null) {
								ProcurementNotificationsEntityCopy procurementNotificationsEntity = null;
								System.out.println("procurementSaveReq.getPreContractTO().getId() " + procurementSaveReq.getPreContractTO().getId());
								PreContractEntity preContractEntity = precontractRepository.findOne(procurementSaveReq.getPreContractTO().getId());
								List<PreContractsEmpDtlEntity> preContractsEmpDtlEntity = precontractEmpRepository
										.findPreContractEmpTypes(procurementSaveReq.getPreContractTO().getId(),procurementSaveReq.getPreContractTO().getStatus());
								
								List<PreContractsMaterialDtlEntity> preContractsMaterialDtlEntity = precontractMaterialRepository
										.findPreContractMaterials(procurementSaveReq.getPreContractTO().getId(),procurementSaveReq.getPreContractTO().getStatus());
								
								List<PreContractsPlantDtlEntity> preContractsPlantDtlEntity = precontractPlantRepository
										.findPreContractPlants(procurementSaveReq.getPreContractTO().getId(),procurementSaveReq.getPreContractTO().getStatus());
								
								List<PreContractsServiceDtlEntity> preContractsServiceDtlEntity = precontractServiceRepository
										.findPreContractServices(procurementSaveReq.getPreContractTO().getId(),procurementSaveReq.getPreContractTO().getStatus());
								
								List<PrecontractSowDtlEntity> precontractSowDtlEntity = precontractSowRepository
										.findPrecontractSowTypes(procurementSaveReq.getPreContractTO().getId(),procurementSaveReq.getPreContractTO().getStatus());
								
								String empDtl = null, matDtl = null, pltDtl = null, srvDtl = null, sowDtl = null;
								if (preContractsEmpDtlEntity.size() != 0) {
									empDtl = "Manpower";
								}
								
								if (preContractsMaterialDtlEntity.size() != 0) {
									matDtl = "Materials";
								}
								
								if (preContractsPlantDtlEntity.size() != 0) {
									pltDtl = "Plants";
								}
								
								if (preContractsServiceDtlEntity.size() != 0) {
									srvDtl = "Services";
								}
								
								if (precontractSowDtlEntity.size() != 0) {
									sowDtl = "Project Sub Contract";
								}

								String procureCategory = Stream.of(empDtl, matDtl, pltDtl, srvDtl, sowDtl).filter(s -> s != null).collect(Collectors.joining(", "));

								ProcurementNotificationsTO procurementNotificationsTO = new ProcurementNotificationsTO();

								procurementNotificationsTO.setModuleCode("PREQ"+preContractEntity.getId());
								procurementNotificationsTO.setNotifyRefId(null);
								procurementNotificationsTO.setProjId(preContractEntity.getProjId().getProjectId());
								procurementNotificationsTO.setProcureCatg(procureCategory);
								procurementNotificationsTO.setProcureStage(preContractEntity.getContarctStageStatus());
								procurementNotificationsTO.setNotificationStatus("Request for Stage 1 Procurement Approval");
								procurementNotificationsTO.setStatus(preContractEntity.getStatus());
								procurementNotificationsTO.setPreContractId(preContractEntity.getId());
								procurementNotificationsTO.setToUserId(procurementSaveReq.getPreContractTO().getPreContractReqApprTO().getApprUserLabelkeyTO().getId());
								procurementNotificationsTO.setApprStatus(procurementSaveReq.getApprvStatus());
								procurementNotificationsTO.setReqComments(procurementSaveReq.getPreContractTO().getPreContractReqApprTO().getReqComments());

								procurementNotificationsEntity = ProcurementNotificationsHandler
										.convertPOJOToEntity(procurementNotificationsTO, loginRepository, epsRepository);
								ProcurementNotificationsEntityCopy procurementNotificationsEntity1 = procurementNotificationsRepositoryCopy.save(procurementNotificationsEntity);
								// end insert
							    sendEmail(procurementNotificationsEntity1);
							} else {
								procNotEntity.setNotificationStatus("Request for Stage 1 Procurement Approval");
								procNotEntity.setApprStatus(procurementSaveReq.getApprvStatus());
								procNotEntity.setReqComments(procurementSaveReq.getPreContractTO().getPreContractReqApprTO().getReqComments());
							}
						} else {
							// Update Procurement Notification
							System.out.println("Updating Notification Table");
							System.out.println("procurementSaveReq.getPreContractTO().getId() " + procurementSaveReq.getPreContractTO().getId());
							System.out.println("procurementSaveReq.getPreContractTO().getStatus() " + procurementSaveReq.getPreContractTO().getStatus());
							ProcurementNotificationsEntityCopy procNotEntity = procurementNotificationsRepositoryCopy
									.findPreContractNotificationDetails(procurementSaveReq.getPreContractTO().getId(),procurementSaveReq.getPreContractTO().getStatus());
							System.out.println("Notification is not EMPTY " + (procNotEntity != null));
							if (procNotEntity != null) {
								procNotEntity.setNotificationStatus("Approval for Stage 1 Procurement");
								procNotEntity.setProcureStage("Stage 1 Approval");
								procNotEntity.setReqComments(procurementSaveReq.getPreContractTO().getPreContractReqApprTO().getApprComments());
								procNotEntity.setApprStatus(procurementSaveReq.getApprvStatus());
								sendEmail(procNotEntity);
							}
						}
			sendEmail(WorkFlowStatus.getDescValue(procurementSaveReq.getApprvStatus()));
		}
		PreContractEntity preContractEntity = PrecontractHandler.convertPreContractPOJOToEntity(preContractTO,
				procurementSaveReq.isVersion(), false, epsRepository, loginRepository, procureCatgRepository,
				empClassRepository, stockRepository, projStoreStockRepositoryCopy, materialClassRepository,
				plantClassRepository, serviceRepository, projSOWItemRepositoryCopy, projCostStatementsRepositoryCopy);

		if (procurementSaveReq.getApprvStatus().equals(WorkFlowStatus.IN_PROCESS.getValue()))
			preContractEntity.setContarctStageStatus(ProcurmentStageStatus.STAGE1_APPROVAL.getDesc());
		else if (WorkFlowStatus.APPROVED.getValue().equals(procurementSaveReq.getApprvStatus()))
			preContractEntity.setContarctStageStatus(ProcurmentStageStatus.RFQ_TENDERING.getDesc());

		preContractEntity = precontractRepository.save(preContractEntity);
		return preContractEntity.getId();
	}

	private void sendEmail(String apprStatus) {
		EmailTO emailTO = new EmailTO();
		emailTO.setFromEmail("koti@rajutech.com");
		emailTO.setToEmail("sravan@rajutech.com");
		emailTO.setSubject("PROCUREMENT " + apprStatus);
		emailTO.setTemplateName("mailtemplate/mailtemplate.vm");
	}
	 
	private void sendEmail(ProcurementNotificationsEntityCopy procurementNotificationsEntity1) {
		String apprName = "";
        String epsName = "";
        String projName = "";
        String toEmail = null;
        String toSubject = null;
        String stage = null;
        String procCateg = null;
        String apprStage = null;
        String approvalStage = null;
        String reqId = null;
        String notId = null;
        String prString = null;
        String text = null;
        String reqDate = null;
        String approverDecision = null;

        if (null != procurementNotificationsEntity1) {
        	
	        	if(procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Request") || 
	        			procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Approval")) {
	        		approvalStage = "1";
	        	} else {
	        		approvalStage = "2";
	        	}

	        	if(procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Request")) { 
	            	prString = "PRS1";
	            } else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Approval")) {
	            	prString = "PAS1";
	            } else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 2 Request")) {
	            	prString = "PRS2";
	            } else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 2 Approval")) {
	            	prString = "PAS2";
	            }
	        	
	        	if (procurementNotificationsEntity1.getApprStatus() == 3) {
	        		approverDecision = "Sent Back To Requestor";
	        	} else if (procurementNotificationsEntity1.getApprStatus() == 4) {
	        		approverDecision = "On Hold";
	        	} else if (procurementNotificationsEntity1.getApprStatus() == 5) {
	        		approverDecision = "Approved";
	        	} else if (procurementNotificationsEntity1.getApprStatus() == 6) {
	        		approverDecision = "Rejected";
	        	}

	        	epsName = procurementNotificationsEntity1.getProjId().getParentProjectMstrEntity().getProjName();
	        	projName = procurementNotificationsEntity1.getProjId().getProjName();
	        	procCateg = procurementNotificationsEntity1.getProcureCatg();
	        	apprName = procurementNotificationsEntity1.getToUserId().getFirstName() 
	        			+ " " + procurementNotificationsEntity1.getToUserId().getLastName();
	        	apprStage = approvalStage;
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy");
	        	reqDate = dateFormat.format(procurementNotificationsEntity1.getDate());
	        	reqId = procurementNotificationsEntity1.getModuleCode() + " dated " + reqDate;
	        	notId = prString + "-" + procurementNotificationsEntity1.getId() + " dated " + reqDate;
	        	toEmail = procurementNotificationsEntity1.getToUserId().getEmail();
	        	
	        	stage = procurementNotificationsEntity1.getProcureStage();
	        	
	        	if (procurementNotificationsEntity1.getApprStatus() == 2) {
	        		toSubject = "Request for " + stage + " for Procurement";
		        	text = "<html><body><p>" + apprName + ",</p>"
		                    + "<p>I have submitted my request for Resources Procurement through " + pot + ", as per details mentioned here below.</p>"
		                    + "<table border='1'>"
		                    + "<tr><td>EPS </td><td>" + epsName + "</td></tr>"
		                    + "<tr><td>Project </td><td>"+ projName + "</td></tr>"
		                    + "<tr><td>Procurement Category </td><td>" + procCateg + "</td></tr>"
		                    + "<tr><td>Approval  Stage</td><td>" + apprStage + "</td></tr>"
		                    + "<tr><td>Requisition ID</td><td>" + reqId + "</td></tr>"
		                    + "<tr><td>Notification ID</td><td>"+ notId +"</td></tr>"
		                    + "</table><br>This is for your approval please." + "<p>Regards,</p>" + "<p>"
	                    + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
	        	} else if (procurementNotificationsEntity1.getApprStatus() == 3 || procurementNotificationsEntity1.getApprStatus() == 4 
	        			|| procurementNotificationsEntity1.getApprStatus() == 5 || procurementNotificationsEntity1.getApprStatus() == 6) {
	        		toSubject = "Approver decision for Procurement Stage 1";
	        		text = "<html><body><p>" + apprName + ",</p>"
		                    + "<p>Reference your notification ID" + reqId +" </p>"
		                    + "<p>I have conveyed my decision for Procurement through " + pot + ", as per details mentioned here below.</p>"
		                    + "<table border='1'>"
		                    + "<tr><td>EPS </td><td>" + epsName + "</td></tr>"
		                    + "<tr><td>Project </td><td>"+ projName + "</td></tr>"
		                    + "<tr><td>Procurement Category </td><td>" + procCateg + "</td></tr>"
		                    + "<tr><td>Approval  Stage</td><td>" + apprStage + "</td></tr>"
		                    + "<tr><td>Requisition ID</td><td>" + reqId + "</td></tr>"
		                    + "<tr><td>Notification ID</td><td>"+ notId +"</td></tr>"
		                    + "<tr><td>Approver Decision</td><td>"+ approverDecision +"</td></tr>"
		                    + "</table><br>This is for your Information please." + "<p>Regards,</p>" + "<p>"
		                    + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
	        	}
	        	if (CommonUtil.isNotBlankStr(toEmail))
	                commonEmail.sendEmailNotification(toEmail, "", toSubject, text);
        }
        
	}

	private void sendEmail(ProcurementNotificationsEntity procurementNotificationsEntity1) {
		String apprName = "";
        String epsName = "";
        String projName = "";
        String toEmail = null;
        String toSubject = null;
        String stage = null;
        String procCateg = null;
        String apprStage = null;
        String approvalStage = null;
        String reqId = null;
        String notId = null;
        String prString = null;
        String text = null;
        String reqDate = null;
        String approverDecision = null;

        if (null != procurementNotificationsEntity1) {
        	
	        	if(procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Request") || 
	        			procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Approval")) {
	        		approvalStage = "1";
	        	} else {
	        		approvalStage = "2";
	        	}

	        	if(procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Request")) { 
	            	prString = "PRS1";
	            } else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Approval")) {
	            	prString = "PAS1";
	            } else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 2 Request")) {
	            	prString = "PRS2";
	            } else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 2 Approval")) {
	            	prString = "PAS2";
	            }
	        	
	        	if (procurementNotificationsEntity1.getApprStatus() == 3) {
	        		approverDecision = "Sent Back To Requestor";
	        	} else if (procurementNotificationsEntity1.getApprStatus() == 4) {
	        		approverDecision = "On Hold";
	        	} else if (procurementNotificationsEntity1.getApprStatus() == 5) {
	        		approverDecision = "Approved";
	        	} else if (procurementNotificationsEntity1.getApprStatus() == 6) {
	        		approverDecision = "Rejected";
	        	}

	        	epsName = procurementNotificationsEntity1.getProjId().getParentProjectMstrEntity().getProjName();
	        	projName = procurementNotificationsEntity1.getProjId().getProjName();
	        	procCateg = procurementNotificationsEntity1.getProcureCatg();
	        	apprName = procurementNotificationsEntity1.getToUserId().getFirstName() 
	        			+ " " + procurementNotificationsEntity1.getToUserId().getLastName();
	        	apprStage = approvalStage;
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy");
	        	reqDate = dateFormat.format(procurementNotificationsEntity1.getDate());
	        	reqId = procurementNotificationsEntity1.getModuleCode() + " dated " + reqDate;
	        	notId = prString + "-" + procurementNotificationsEntity1.getId() + " dated " + reqDate;
	        	toEmail = procurementNotificationsEntity1.getToUserId().getEmail();
	        	
	        	stage = procurementNotificationsEntity1.getProcureStage();
	        	
	        	if (procurementNotificationsEntity1.getApprStatus() == 2) {
	        		toSubject = "Request for " + stage + " for Procurement";
		        	text = "<html><body><p>" + apprName + ",</p>"
		                    + "<p>I have submitted my request for Resources Procurement through " + pot + ", as per details mentioned here below.</p>"
		                    + "<table border='1'>"
		                    + "<tr><td>EPS </td><td>" + epsName + "</td></tr>"
		                    + "<tr><td>Project </td><td>"+ projName + "</td></tr>"
		                    + "<tr><td>Procurement Category </td><td>" + procCateg + "</td></tr>"
		                    + "<tr><td>Approval  Stage</td><td>" + apprStage + "</td></tr>"
		                    + "<tr><td>Requisition ID</td><td>" + reqId + "</td></tr>"
		                    + "<tr><td>Notification ID</td><td>"+ notId +"</td></tr>"
		                    + "</table><br>This is for your approval please." + "<p>Regards,</p>" + "<p>"
	                    + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
	        	} else if (procurementNotificationsEntity1.getApprStatus() == 3 || procurementNotificationsEntity1.getApprStatus() == 4 
	        			|| procurementNotificationsEntity1.getApprStatus() == 5 || procurementNotificationsEntity1.getApprStatus() == 6) {
	        		toSubject = "Approver decision for Procurement Stage 1";
	        		text = "<html><body><p>" + apprName + ",</p>"
		                    + "<p>Reference your notification ID" + reqId +" </p>"
		                    + "<p>I have conveyed my decision for Procurement through " + pot + ", as per details mentioned here below.</p>"
		                    + "<table border='1'>"
		                    + "<tr><td>EPS </td><td>" + epsName + "</td></tr>"
		                    + "<tr><td>Project </td><td>"+ projName + "</td></tr>"
		                    + "<tr><td>Procurement Category </td><td>" + procCateg + "</td></tr>"
		                    + "<tr><td>Approval  Stage</td><td>" + apprStage + "</td></tr>"
		                    + "<tr><td>Requisition ID</td><td>" + reqId + "</td></tr>"
		                    + "<tr><td>Notification ID</td><td>"+ notId +"</td></tr>"
		                    + "<tr><td>Approver Decision</td><td>"+ approverDecision +"</td></tr>"
		                    + "</table><br>This is for your Information please." + "<p>Regards,</p>" + "<p>"
		                    + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
	        	}
	        	if (CommonUtil.isNotBlankStr(toEmail))
	                commonEmail.sendEmailNotification(toEmail, "", toSubject, text);
        }
        
	}

	// VIZ

	public PreContractTO updateRepeatPOPreContractTO(PreContractTO preContractTO)
	{
		PreContractTO repeatPOPreContractTO = new PreContractTO();

		//repeatPOPreContractTO.setId(preContractTO.getId()); Auto Generated
		repeatPOPreContractTO.setProjId(preContractTO.getProjId());
		repeatPOPreContractTO.setWorkFlowStatusTO(preContractTO.getWorkFlowStatusTO());
		repeatPOPreContractTO.setDesc(preContractTO.getDesc());
		repeatPOPreContractTO.setReqUsrId(preContractTO.getReqUsrId());
		repeatPOPreContractTO.setPreContractType(preContractTO.getPreContractType());
		repeatPOPreContractTO.setPreContractDocsTOs(preContractTO.getPreContractDocsTOs());
		repeatPOPreContractTO.setPreContractCmpTOs(preContractTO.getPreContractCmpTOs());
		repeatPOPreContractTO.setPreContractEmpDtlTOs(preContractTO.getPreContractEmpDtlTOs());
		repeatPOPreContractTO.setPreContractPlantDtlTOs(preContractTO.getPreContractPlantDtlTOs());
		repeatPOPreContractTO.setPreContractMaterialDtlTOs(preContractTO.getPreContractMaterialDtlTOs());
		repeatPOPreContractTO.setPreContractServiceDtlTOs(preContractTO.getPreContractServiceDtlTOs());
		repeatPOPreContractTO.setPrecontractSowDtlTOs(preContractTO.getPrecontractSowDtlTOs());
		repeatPOPreContractTO.setReqCode(preContractTO.getReqCode());
		repeatPOPreContractTO.setResponeDate(preContractTO.getResponeDate());
		repeatPOPreContractTO.setPreContractReqApprTOs(preContractTO.getPreContractReqApprTOs());
		repeatPOPreContractTO.setPreContractReqApprTO(preContractTO.getPreContractReqApprTO());
		repeatPOPreContractTO.setCloseDate(preContractTO.getCloseDate());
		repeatPOPreContractTO.setRevisedCloseDate(preContractTO.getRevisedCloseDate());
		repeatPOPreContractTO.setCurrentDate(preContractTO.getCurrentDate());
		repeatPOPreContractTO.setApproveStatus(preContractTO.getApproveStatus());
		repeatPOPreContractTO.setContractStageStatus(preContractTO.getContractStageStatus());
		repeatPOPreContractTO.setPurchaseOrderStatus(preContractTO.getPurchaseOrderStatus());
		repeatPOPreContractTO.setAllowMultiplePurchaseOrders(preContractTO.isAllowMultiplePurchaseOrders());
		repeatPOPreContractTO.setIsLatest(preContractTO.getIsLatest());
		repeatPOPreContractTO.setCurrencyCode(preContractTO.getCurrencyCode());
		repeatPOPreContractTO.setProjCode(preContractTO.getProjCode());
		repeatPOPreContractTO.setExternalWorkFlowMap(preContractTO.getExternalWorkFlowMap());
		repeatPOPreContractTO.setDesc(preContractTO.getDesc());
		repeatPOPreContractTO.setStatus(preContractTO.getStatus());

		return repeatPOPreContractTO;
	}

	// VIZ
	public Long repeatPOSaveExternalPreContrancts(ProcurementSaveReq procurementSaveReq) {

		System.out.println("Started : repeatPOSaveExternalPreContrancts ");

		System.out.println(" PreContractTO : "+procurementSaveReq.getPreContractTO().toString());

		PreContractTO preContractTO = updateRepeatPOPreContractTO(procurementSaveReq.getPreContractTO());
		System.out.println(" ProjId : "+preContractTO.getProjId());
		/*if(preContractTO.getProjId() == null)
		{
			preContractTO.setProjId(procurementSaveReq.getProjId());
			System.out.println(" ProjId : "+preContractTO.getProjId());
			preContractTO.setStatus(procurementSaveReq.getStatus());
			System.out.println(" getStatus : "+procurementSaveReq.getStatus());
		}*/

		//preContractTO.setStatus(procurementSaveReq.getStatus());
		//preContractTO.setDesc(procurementSaveReq.);
		System.out.println("procurementSaveReq ApprvStatus : "+CommonUtil.isNonBlankInteger(procurementSaveReq.getApprvStatus()));

		if (CommonUtil.isNonBlankInteger(procurementSaveReq.getApprvStatus())) {
			WorkFlowStatusTO workFlowStatusTO = new WorkFlowStatusTO();
			if (WorkFlowStatus.APPROVED.getValue().equals(procurementSaveReq.getApprvStatus())) {
				workFlowStatusTO.setValue(5);
			}else{
				workFlowStatusTO.setValue(2);
			}


			// Setting ID as null to generate
			if(procurementSaveReq.getPreContractTO()!=null)
			{
				System.out.println(" ** procurementSaveReq preContractTO getId : "+procurementSaveReq.getPreContractTO().getId());
			}
			System.out.println(" ** preContractTO getId : "+preContractTO.getId());

			preContractTO.setWorkFlowStatusTO(workFlowStatusTO);


			/*ReqApprNotificationTO notificationTO = PrecontractHandler.populateNotificationTO(preContractTO);
			reqApprNotificationRepository.save(
					ReqApprNotificationHandler.convertPOJOToEntity(notificationTO, loginRepository, epsRepository));*/
		}

		if (procurementSaveReq.getApprvStatus().equals(WorkFlowStatus.IN_PROCESS.getValue()))
			preContractTO.setContractStageStatus(ProcurmentStageStatus.REPEAT_PO_APPROVAL.getDesc());
		else if (procurementSaveReq.getApprvStatus().equals(WorkFlowStatus.APPROVED.getValue()))
			preContractTO.setContractStageStatus(ProcurmentStageStatus.REPEAT_PO_APPROVED.getDesc());

		PreContractEntity preContractEntity = precontractRepository
				.save(PrecontractHandler.convertPreContractPOJOToEntity(preContractTO, procurementSaveReq.isVersion(),
						true, epsRepository, loginRepository, procureCatgRepository, empClassRepository,
						stockRepository, projStoreStockRepositoryCopy, materialClassRepository, plantClassRepository,
						serviceRepository, projSOWItemRepositoryCopy, projCostStatementsRepositoryCopy));

		List<PreContractsCmpEntity> contractsCmpEntities = new ArrayList<>();
		List<PreContractCmpTO> preContractCmpTOs = preContractTO.getPreContractCmpTOs();
		for (PreContractCmpTO preContractCmpTO : preContractCmpTOs) {
			contractsCmpEntities.add(PrecontractCmpHandler.convertPOJOToEntity(preContractCmpTO,
					procurementSaveReq.getApprvStatus(), companyRepository, addressRepository, contactsRepository));
		}
		preContractCmpRepository.save(contractsCmpEntities);

		System.out.println(" preContractEntity  Id  : "+preContractEntity.getId());
		return preContractEntity.getId();

	}

	public Long saveExternalPreContrancts(ProcurementSaveReq procurementSaveReq) {

		PreContractTO preContractTO = procurementSaveReq.getPreContractTO();
		if (CommonUtil.isNonBlankInteger(procurementSaveReq.getApprvStatus())) {
			WorkFlowStatusTO workFlowStatusTO = new WorkFlowStatusTO();
			workFlowStatusTO.setValue(5);
			preContractTO.setWorkFlowStatusTO(workFlowStatusTO);

			ReqApprNotificationTO notificationTO = PrecontractHandler.populateNotificationTO(preContractTO);
			reqApprNotificationRepository.save(
					ReqApprNotificationHandler.convertPOJOToEntity(notificationTO, loginRepository, epsRepository));
		}
		if (procurementSaveReq.getApprvStatus().equals(WorkFlowStatus.IN_PROCESS.getValue()))
			preContractTO.setContractStageStatus(ProcurmentStageStatus.STAGE2_APPROVAL.getDesc());
		else if (procurementSaveReq.getApprvStatus().equals(WorkFlowStatus.APPROVED.getValue()))
			preContractTO.setContractStageStatus(ProcurmentStageStatus.STAGE2_APPROVED.getDesc());
		
		if (procurementSaveReq.getApprvStatus() == 2) { 
			ProcurementNotificationsEntityCopy procNotEntity = procurementNotificationsRepositoryCopy
					.findPreContractNotificationDetails(procurementSaveReq.getPreContractTO().getId(),procurementSaveReq.getPreContractTO().getStatus());
			
			if(procNotEntity != null) {
				procNotEntity.setProcureStage(ProcurmentStageStatus.STAGE2_APPROVAL.getDesc());
				procNotEntity.setNotificationStatus("Request for Stage 2  Procurement Approval");
				procNotEntity.setApprStatus(procurementSaveReq.getApprvStatus());
				procNotEntity.setReqComments(procurementSaveReq.getPreContractTO().getPreContractReqApprTO().getReqComments());
				
				sendEmail(procNotEntity);
			} 
		} else {
			ProcurementNotificationsEntityCopy procNotEntity = procurementNotificationsRepositoryCopy
					.findPreContractNotificationDetails(procurementSaveReq.getPreContractTO().getId(),procurementSaveReq.getPreContractTO().getStatus());
		//	log.info("Notification is not EMPTY " + (procNotEntity != null));
			if (procNotEntity != null) {
				procNotEntity.setNotificationStatus("Approval for Stage 2 Procurement ");
				procNotEntity.setProcureStage("Stage 2 Approval");
				procNotEntity.setReqComments(procurementSaveReq.getPreContractTO().getPreContractReqApprTO().getApprComments());
				procNotEntity.setApprStatus(procurementSaveReq.getApprvStatus());
			    sendEmail(procNotEntity);
			}
		}
		
		PreContractEntity preContractEntity = precontractRepository
				.save(PrecontractHandler.convertPreContractPOJOToEntity(preContractTO, procurementSaveReq.isVersion(),
						true, epsRepository, loginRepository, procureCatgRepository, empClassRepository,
						stockRepository, projStoreStockRepositoryCopy, materialClassRepository, plantClassRepository,
						serviceRepository, projSOWItemRepositoryCopy, projCostStatementsRepositoryCopy));

		List<PreContractsCmpEntity> contractsCmpEntities = new ArrayList<>();
		List<PreContractCmpTO> preContractCmpTOs = preContractTO.getPreContractCmpTOs();
		for (PreContractCmpTO preContractCmpTO : preContractCmpTOs) {
			contractsCmpEntities.add(PrecontractCmpHandler.convertPOJOToEntity(preContractCmpTO,
					procurementSaveReq.getApprvStatus(), companyRepository, addressRepository, contactsRepository));
		}
		preContractCmpRepository.save(contractsCmpEntities);

		return preContractEntity.getId();

	}

	public List<LabelKeyTO> getPruchaseOrdersByPrecontract(ProcurementGetReq procurementGetReq) {
		List<PurchaseOrderEntity> purchaseOrders = purchaseOrderRepository
				.getPurchaseOrdersByPrecontract(procurementGetReq.getContractId());
		List<LabelKeyTO> poList = new ArrayList<>();
		for (PurchaseOrderEntity po : purchaseOrders) {
			LabelKeyTO labelKeyTO = new LabelKeyTO();
			labelKeyTO.setId(po.getId());
			labelKeyTO.setCode(PurchaseOrderHandler.generatePurchaseOrderCode(po,false));
			poList.add(labelKeyTO);
		}
		return poList;
	}

	public void updatePurchaseOrderSummar(POProcureTypeReq poProcureTypeReq) {
		for (LabelKeyTO labelKeyTO : poProcureTypeReq.getLabelKeyTOs()) {
			purchaseOrderProcRepository.updatePurchaseOrderSummary(labelKeyTO.getId());
		}
	}

	public void submitBidQuotation(ProcurementFilterReq procurementFilterReq) {
		preContractCmpRepository.updateCmpBidStatus(procurementFilterReq.getPreContractCmpId(),
				procurementFilterReq.getBiddingStatus(), procurementFilterReq.getQuoteRefCode());
		precontractRepository.updategetContarctStageStatus(procurementFilterReq.getId(),
				procurementFilterReq.getContarctStageStatus());
	}

	public void deactivatePreContrancts(ProcurementDelReq procurementDelReq) {
		precontractRepository.deactivatePrecontacts(procurementDelReq.getContractIds(), procurementDelReq.getStatus());

	}

	public void deactivatePreContranctsList(ProcurementDelReq procurementDelReq) {
		if (CommonUtil.isListHasData(procurementDelReq.getContractIds())) {
			precontractRepository.deactivatePreContranctList(procurementDelReq.getContractIds(),
					procurementDelReq.getStatus());
		}
	}

	public void deactivatePreContranctDetails(ProcurementDelReq procurementDelReq) {
		if (ProcurementCatg.MAN_POWER.getDesc().equalsIgnoreCase(procurementDelReq.getItemType())) {
			precontractEmpRepository.deactivateManPowerDetails(procurementDelReq.getContractIds(),
					procurementDelReq.getStatus());
			return;
		}

		if (ProcurementCatg.PLANT.getDesc().equalsIgnoreCase(procurementDelReq.getItemType())) {
			precontractPlantRepository.deactivatePlantDetails(procurementDelReq.getContractIds(),
					procurementDelReq.getStatus());
			return;
		}
		if (ProcurementCatg.MATERIAL.getDesc().equalsIgnoreCase(procurementDelReq.getItemType())) {
			precontractMaterialRepository.deactivateMaterialDetails(procurementDelReq.getContractIds(),
					procurementDelReq.getStatus());
			return;
		}
		if (ProcurementCatg.SERVICES.getDesc().equalsIgnoreCase(procurementDelReq.getItemType())) {
			precontractServiceRepository.deactivateServiceDetails(procurementDelReq.getContractIds(),
					procurementDelReq.getStatus());
		}
	}

	public void savePreContranctDocs(MultipartFile[] files, PrecontractDocSaveReq precontractDocSaveReq)
			throws IOException {
		// TODO check and delete the DTO, not using - PreContractDocContentTO
		//final String procurementFolderName = "Reference Documents";
		final String procurementFolderName = precontractDocSaveReq.getCategory();
		ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId(procurementFolderName);
		System.out.println("Parent Name:"+folder.getProjDocFolderEntity().getName());
		String parent_folder_name = folder.getProjDocFolderEntity().getName();
		Long projId = precontractDocSaveReq.getProjId();
		if (folder == null) {
			folder = new ProjDocFolderEntity();
			folder.setName(procurementFolderName);
			
			if (projId != null) {
				folder.setProjId(epsRepository.findOne(projId));
			}
			folder.setStatus(StatusCodes.ACTIVE.getValue());
			folder = projDocFolderRepository.save(folder);
		}
		else
		{
			System.out.println("else condition");
		}
		System.out.println("category name:"+precontractDocSaveReq.getCategory());
		String dir_path = folder.getUploadFolder();
		System.out.println("category name:"+dir_path);
		String[] extras = { String.valueOf( precontractDocSaveReq.getClientId() ), String.valueOf( precontractDocSaveReq.getProjId() ) };
		String folder_location = "";
		for(int i=0;i<extras.length;i++)
		{
			folder_location = folder_location + "/" +extras[i];
		}
		for (PreContractDocsTO preContractDocsTO : precontractDocSaveReq.getPreContractDocsTOs()) {
			PreContractDocEntity preContractDocEntity = null;
			// We are passing multiple files as an array, to find which file is belongs to
			// which object based on fileName and index
			Integer fileIndex = preContractDocsTO.getFileObjectIndex();
			if (fileIndex != null
					&& files[fileIndex].getOriginalFilename().equalsIgnoreCase(preContractDocsTO.getName())) {
				System.out.println("File Name:"+preContractDocsTO.getName()+" projd id:"+preContractDocsTO.getProjId());
				ProjDocFileTO projDocFileTO = new ProjDocFileTO();
				projDocFileTO.setFolderId(folder.getId());
				projDocFileTO.setMultipartFile(files[fileIndex]);
				projDocFileTO.setName(preContractDocsTO.getName());
				//projDocFileTO.setVersion(preContractDocsTO.getVersion());
				projDocFileTO.setVersion("1");
				projDocFileTO.setFileType(preContractDocsTO.getFileType());
				projDocFileTO.setFileSize(String.valueOf(preContractDocsTO.getFileSize()));
				projDocFileTO.setStatus(preContractDocsTO.getStatus());
				projDocFileTO.setDescription(preContractDocsTO.getDescription());
				projDocFileTO.setFromSource("Procurement");
				projDocFileTO.setProjId(preContractDocsTO.getProjId());
				projDocFileTO.setFolderPath(folder_location);
				preContractDocsTO.setProjDocFileTO(projDocFileTO);
			}

			preContractDocEntity = precontractDocRepository.save(precontractDocumentHandler
					.convertDocsPOJOToEntity(preContractDocsTO, precontractDocRepository, precontractRepository));
			if( fileIndex != null && files[fileIndex].getOriginalFilename().equalsIgnoreCase( preContractDocsTO.getName() ) ) {
				if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
				{
					// Upload pre-contract docs to server				
					uploadUtil.uploadFile( files[fileIndex], "Procurement", dir_path, extras );
				}
				else
				{
					// Upload pre-contract docs to AWS S3
					precontractDocumentHandler.uploadPreconreactDocsToAwsS3(preContractDocEntity, preContractDocsTO);
				}
			}
		}
	}

	public void savePreContractDistributionDocs(PrecontractDistributionDocSaveReq precontractDocSaveReq) {
		List<PreContractDistributionDocEntity> preContractDocEntities = new ArrayList<>();
		PreContractDistributionDocEntity preContractDocEntity = null;
		for (PreContractDistributionDocTO preContractDocsTO : precontractDocSaveReq
				.getPreContractDistributionDocTOs()) {
			preContractDocEntity = PrecontractDistributionDocHandler.convertDocsPOJOToEntity(preContractDocsTO,
					precontractRepository);
			preContractDocEntities.add(preContractDocEntity);
		}
		precontractDitributionDocRepository.save(preContractDocEntities);
	}

	public void sendPreContractDocsToCompanies(PrecontractDistributionDocSaveReq precontractDocSaveReq) {
		PreContractCmpDistributionDocEntity preContractCmpDistributionDocStatusEntity = null;
		List<PreContractDistributionDocEntity> preContractDocEntities = new ArrayList<>();
		PreContractDistributionDocEntity preContractDocEntity = null;
		for (PreContractDistributionDocTO preContractDocsTO : precontractDocSaveReq
				.getPreContractDistributionDocTOs()) {
			preContractDocEntity = PrecontractDistributionDocHandler.convertDocsPOJOToEntity(preContractDocsTO,
					precontractRepository);
			preContractDocEntity.setDistributionStatus(true);
			preContractDocEntities.add(preContractDocEntity);
			for (PreContractCmpDistributionDocTO preContractCmpDistributionDocTO : precontractDocSaveReq
					.getPreContractCmpDistributionDocTOs()) {
				preContractCmpDistributionDocStatusEntity = new PreContractCmpDistributionDocEntity();
				PreContractCmpDistributionDocEntity preContractCmpDistributionDoc = precontractCmpDistributionDocRepository.findcmpdoc(preContractCmpDistributionDocTO.getPreContractCmpId());
				if(preContractCmpDistributionDoc != null) {
					continue;
				}
				PreContractsCmpEntity preContractCmpId = preContractCmpRepository
						.findOne(preContractCmpDistributionDocTO.getPreContractCmpId());
				if (null != preContractCmpId) {
					preContractCmpDistributionDocStatusEntity.setPreContractCmpId(preContractCmpId);
				}
				preContractCmpDistributionDocStatusEntity.setPreContractDistributionDocEntity(preContractDocEntity);
				preContractCmpDistributionDocStatusEntity.setTransmit(preContractCmpDistributionDocTO.isTransmit());
				preContractCmpDistributionDocStatusEntity.setStatus(StatusCodes.ACTIVE.getValue());
				preContractDocEntity.getPreContractCmpDistributionDocEntities()
						.add(preContractCmpDistributionDocStatusEntity);
			}
		}
		precontractDitributionDocRepository.save(preContractDocEntities);

	}

	public void savePreContratCompnayDocs( MultipartFile[] files, PreContratCompnayDocSaveReq preContratCompnayDocSaveReq ) throws IOException {
		List<PreContractCmpDocEntity> preContractCmpDocEntities = new ArrayList<>(
				ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		/*PreContractCmpDocEntity preContractCmpDocEntity = null;
		for (PreContractCmpDocsTO preContractCmpDocsTO : preContratCompnayDocSaveReq.getPreContractCmpDocsTOs()) {
			preContractCmpDocEntity = PrecontractCmpDocHandler.convertCmpDocsPOJOToEntity(preContractCmpDocsTO);
			preContractCmpDocEntities.add(preContractCmpDocEntity);
		}
		precontractCmpDocRepository.save(preContractCmpDocEntities);*/
		//final String procurementFolderName = "RFQ-Correspondance From Vendor";
		final String procurementFolderName = preContratCompnayDocSaveReq.getFolderCategory();
		ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId(procurementFolderName);
		System.out.println(folder);
		System.out.println("Folder path:"+folder.getUploadFolder()+"id:"+folder.getId());
		String dir_path = folder.getUploadFolder();
		String[] extras = { String.valueOf( preContratCompnayDocSaveReq.getClientId() ), String.valueOf( preContratCompnayDocSaveReq.getProjId() ) };
		String folder_location = "";
		for(int i=0;i<extras.length;i++)
		{
			folder_location = folder_location + "/" +extras[i];
		}
		if (folder == null) {
			System.out.println("folder null condition");
			/*folder = new ProjDocFolderEntity();
			folder.setName(procurementFolderName);
			Long projId = precontractDocSaveReq.getProjId();
			if (projId != null) {
				folder.setProjId(epsRepository.findOne(projId));
			}
			folder.setStatus(StatusCodes.ACTIVE.getValue());
			folder = projDocFolderRepository.save(folder);*/
		}
		else
		{
			System.out.println("else condition");
		}
		System.out.println("category name:"+preContratCompnayDocSaveReq.getFolderCategory());
		PreContractCmpDocEntity preContractCmpDocEntity = null;
		Long userId = preContratCompnayDocSaveReq.getUserId();
		ProjDocFileEntity resProjDocFileEntity = null;
		for (PreContractCmpDocsTO preContractCmpDocsTO : preContratCompnayDocSaveReq.getPreContractCmpDocsTOs()) {
			// We are passing multiple files as an array, to find which file is belongs to
			// which object based on fileName and index
			Integer fileIndex = preContractCmpDocsTO.getFileObjectIndex();
			if (fileIndex != null
					&& files[fileIndex].getOriginalFilename().equalsIgnoreCase(preContractCmpDocsTO.getName())) {
				System.out.println("File Name:"+preContractCmpDocsTO.getName());
				ProjDocFileTO projDocFileTO = new ProjDocFileTO();
				resProjDocFileEntity = new ProjDocFileEntity();
				projDocFileTO.setFolderId(folder.getId());
				projDocFileTO.setMultipartFile(files[fileIndex]);
				projDocFileTO.setName(preContractCmpDocsTO.getName());
				//projDocFileTO.setVersion(preContractCmpDocsTO.getVersion());
				projDocFileTO.setFileType(preContractCmpDocsTO.getMimeType());
				projDocFileTO.setFileSize(String.valueOf(preContractCmpDocsTO.getFileSize()));
				projDocFileTO.setStatus(preContractCmpDocsTO.getStatus());
				projDocFileTO.setProjId(preContractCmpDocsTO.getProjId());
				projDocFileTO.setDescription(preContractCmpDocsTO.getDescription());
				projDocFileTO.setFromSource("Procurement");
				projDocFileTO.setFolderPath(folder_location);
				projDocFileTO.setUserId( userId );
				resProjDocFileEntity = projDocFileRepo.save( PrecontractCmpDocHandler.convertPOJOToProjDocFileEntity( projDocFileTO ) );
				if(procurementFolderName.equalsIgnoreCase("RFQ-Correspondance From Vendor"))
					preContractCmpDocsTO.setFromVendor(true);
				preContractCmpDocsTO.setProjDocFileTO( projDocFileTO );
			}
						
			if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
			{
				// Upload pre-contract docs to server
				uploadUtil.uploadFile( files[fileIndex], "Procurement", dir_path, extras );
			}
			preContractCmpDocEntity = PrecontractCmpDocHandler.convertCmpDocsPOJOToEntity(preContractCmpDocsTO,resProjDocFileEntity);
			preContractCmpDocEntities.add(preContractCmpDocEntity);
		}
		precontractCmpDocRepository.save( preContractCmpDocEntities );
	}

	public PreContractDocResp getPreContractDocs(ProcurementGetReq procurementGetReq) {
		List<PreContractDocEntity> preContractEntites = precontractDocRepository
				.findPreContractDocs(procurementGetReq.getContractId(), procurementGetReq.getStatus());
		return precontractDocumentHandler.getPreContractDocs(preContractEntites);
	}

	public PreContractDistributionDocResp getPreContractDistributionDocs(ProcurementGetReq procurementGetReq) {
		List<PreContractDistributionDocEntity> preContractDistributionDocEntities = precontractDitributionDocRepository
				.findPreContractDistributionDocs(procurementGetReq.getContractId(), procurementGetReq.getStatus());
		return PrecontractDistributionDocHandler.getPreContractDistributionDocTOs(preContractDistributionDocEntities,precontractDocRepository);
	}

	public PreContractCmpDistributionDocResp getPreContractCmpDistributionDocs(
			PrecontractDocGetReq precontractDocGetReq) {
		PreContractCmpDistributionDocResp preContractCmpDistributionDocResp = new PreContractCmpDistributionDocResp();
		List<PreContractCmpDistributionDocEntity> preContractDistributionDocEntities = precontractCmpDistributionDocRepository
				.findPreContractCmpDistributionDocs(precontractDocGetReq.getDistributionDocId(),
						precontractDocGetReq.getStatus());
		for (PreContractCmpDistributionDocEntity preContractDistributionDocEntity : preContractDistributionDocEntities) {
			preContractCmpDistributionDocResp.getPreContractCmpDistributionDocTOs()
					.add(PrecontractDistributionDocHandler
							.convertCmpDistributionDocEntityToPOJO(preContractDistributionDocEntity));
		}
		/*
		 * for(PreContractCmpTO preContractCmpTO:
		 * preContractCmpDistributionDocResp.getPreContractCmpTOsCopy()) {
		 * 
		 * System.out.println("$$$%%%***************             "+preContractsCmpEntity
		 * .getCompanyId().getId()); }
		 */
		return preContractCmpDistributionDocResp;
	}

	public PreContractCmpDocResp getPreContractCmpDocs(ProcurementGetReq procurementGetReq) {
		if(!(procurementGetReq.isFromVendor())) {
			List<DocumentTransmittalMessageEntity> documentTransmittalMessageEntity = null;
			List<PreContractDocEntity> preContractEntites = null;
			List<PreContractDistributionDocEntity> preContractDistributionDocEntity = precontractDitributionDocRepository
												.findPreContractDistributionDocs(procurementGetReq.getPreContractCmpId(), procurementGetReq.getStatus());
			if(preContractDistributionDocEntity.size() > 0) {
				preContractEntites = precontractDocRepository
						.findPreContractDocs(procurementGetReq.getPreContractCmpId(), procurementGetReq.getStatus());
				documentTransmittalMessageEntity = documentTransmittalMsgRepository.findvendor(procurementGetReq.getPreContractCmpId());
				return PrecontractCmpDocHandler.getPreContractCmpDocsvendor(preContractEntites,documentTransmittalMessageEntity,preContractDistributionDocEntity);
			}
		//	return PrecontractCmpDocHandler.getPreContractCmpDocsvendor(preContractEntites,documentTransmittalMessageEntity,preContractDistributionDocEntity);
		}
		List<PreContractCmpDocEntity> preContractEntites = precontractCmpDocRepository
				.findPreContractCmpDocs(procurementGetReq.getPreContractCmpId(), procurementGetReq.getStatus());
		return PrecontractCmpDocHandler.getPreContractCmpDocs(preContractEntites);
		
	}

	public PreContractCmpDocResp gPreContractCmpDocsByType(ProcurementGetReq procurementGetReq) {
		List<PreContractCmpDocEntity> preContractEntites = precontractCmpDocRepository.findPreContractCmpDocsByType(
				procurementGetReq.getPreContractCmpId(), procurementGetReq.isFromVendor(),
				procurementGetReq.getStatus());
		return PrecontractCmpDocHandler.getPreContractCmpDocs(preContractEntites);
	}

	public PreContractReqApprResp getPreContractReqApprs(ProcurementGetReq procurementGetReq) {
		List<PreContractReqApprEntity> preContractEntites = precontractReqApprRepository
				.findPreContractReqApprs(procurementGetReq.getContractId(), procurementGetReq.getStatus());
		return PrecontractHandler.populatePreContractReqs(preContractEntites);

	}

	public void savePreContratCompanies(PreContractCmpSaveReq preContractCmpSaveReq) {
		List<PreContractsCmpEntity> preContractsCmpEntities = new ArrayList<>();
		PreContractsCmpEntity preContractsCmpEntity = null;
		PreContractEntity preContractEntity = precontractRepository.findOne(preContractCmpSaveReq.getPreContractId());

		for (PreContractCmpTO preContractCmpTO : preContractCmpSaveReq.getPreContractCmpTOs()) {
			preContractsCmpEntity = PrecontractCmpHandler.convertPOJOToEntity(preContractCmpTO,
					preContractCmpSaveReq.getApprstatus(), companyRepository, addressRepository, contactsRepository);
			preContractsCmpEntity.setPreContractEntity(preContractEntity);
			preContractsCmpEntities.add(preContractsCmpEntity);
		}
		if (CommonUtil.isNotBlankStr(preContractCmpSaveReq.getCloseDate())) {
			preContractEntity.setCloseDate(CommonUtil.convertStringToDate(preContractCmpSaveReq.getCloseDate()));
		}
		if (CommonUtil.isNotBlankStr(preContractCmpSaveReq.getRevisedCloseDate())) {
			preContractEntity
					.setRevisedCloseDate(CommonUtil.convertStringToDate(preContractCmpSaveReq.getRevisedCloseDate()));
		}
		PreContractReqApprEntity preContractReqApprEntity = new PreContractReqApprEntity();
		if (CommonUtil.isListHasData(preContractEntity.getPreContractReqApprEntities())) {
			preContractReqApprEntity = preContractEntity.getPreContractReqApprEntities().get(0);
		}
		boolean flag = false;
		if (preContractCmpSaveReq.getReqUsrId() != null || preContractCmpSaveReq.getApprUsrId() != null) {
			PreContractReqApprEntity contractReqApprEntity = null;
			if (preContractCmpSaveReq.getReqUsrId() != null && !preContractCmpSaveReq.getReqUsrId()
					.equals(preContractReqApprEntity.getReqUserId().getUserId())) {
				contractReqApprEntity = new PreContractReqApprEntity();
				contractReqApprEntity.setReqUserId(loginRepository.findOne(preContractCmpSaveReq.getReqUsrId()));
				flag = true;
			}
			if (preContractCmpSaveReq.getApprUsrId() != null && !preContractCmpSaveReq.getApprUsrId()
					.equals(preContractReqApprEntity.getApprUserId().getUserId())) {
				if (!flag) {
					contractReqApprEntity = new PreContractReqApprEntity();
					contractReqApprEntity.setReqUserId(preContractReqApprEntity.getReqUserId());
				}
				contractReqApprEntity.setApprUserId(loginRepository.findOne(preContractCmpSaveReq.getApprUsrId()));
			} else {
				contractReqApprEntity = new PreContractReqApprEntity();
				contractReqApprEntity.setReqUserId(loginRepository.findOne(AppUserUtils.getUserId()));
				contractReqApprEntity.setLatest(true);
				contractReqApprEntity.setApprUserId(preContractReqApprEntity.getApprUserId());
				contractReqApprEntity.setReqDate(new Date());
			}
			if (!flag) {
				contractReqApprEntity.setStatus(StatusCodes.ACTIVE.getValue());
				contractReqApprEntity.setPreContractEntity(preContractEntity);
				contractReqApprEntity.setLatest(true);
				preContractEntity.getPreContractReqApprEntities().add(contractReqApprEntity);
				preContractReqApprEntity.setLatest(false);
				preContractEntity.setReqUserId(preContractReqApprEntity.getReqUserId());
				contractReqApprEntity.setReqDate(new Date());
			}
		}
		preContractEntity.setContarctStageStatus(ProcurmentStageStatus.RFQ_TENDERING.getDesc());
		preContractCmpRepository.save(preContractsCmpEntities);
	}

	public PreContractCmpResp getPreContractCompanies(PreContractCmpGetReq preContractCmpGetReq) {
		List<PreContractsCmpEntity> preContractsCmpEntities = preContractCmpRepository
				.findPreContractCompnies(preContractCmpGetReq.getPreContractId(), preContractCmpGetReq.getStatus());
		return PrecontractCmpHandler.populatePreContractCmpResp(preContractsCmpEntities);
	}

	public void savePreContratEmpTypes(PreContractEmpSaveReq preContractEmpSaveReq) {
		precontractEmpRepository.save(PrecontractEmpHandler.populatePreContractsEmpDtlEntities(
				preContractEmpSaveReq.getPreContractEmpDtlTOs(), preContractEmpSaveReq.isVersion(),
				preContractEmpSaveReq.isExternal(), projCostStatementsRepositoryCopy, procureCatgRepository,
				empClassRepository,stockRepository, projStoreStockRepositoryCopy));
		if (CommonUtil.isListHasData(preContractEmpSaveReq.getEmpDtlIds())) {
			precontractEmpRepository.deactivateManPowerDetails(preContractEmpSaveReq.getEmpDtlIds(),
					StatusCodes.DEACIVATE.getValue());
		}
	}

	public void savePreContratPlants(PreContractPlantSaveReq preContractPlantSaveReq) {
		precontractPlantRepository.save(PrecontractPlantHandler.populatePreContractPlantEntities(
				preContractPlantSaveReq.getPreContractPlantDtlTOs(), preContractPlantSaveReq.isVersion(),
				preContractPlantSaveReq.isExternal(), procureCatgRepository, projCostStatementsRepositoryCopy,
				plantClassRepository, stockRepository, projStoreStockRepositoryCopy));
		if (CommonUtil.isListHasData(preContractPlantSaveReq.getPlantDtlIds())) {
			precontractPlantRepository.deactivatePlantDetails(preContractPlantSaveReq.getPlantDtlIds(),
					StatusCodes.DEACIVATE.getValue());
		}
	}

	public void savePreContratMaterials(PreContractMaterialSaveReq preContractMaterialSaveReq) {
		precontractMaterialRepository.save(PrecontractMaterialHandler.populatePreContractMaterialEntities(
				preContractMaterialSaveReq.getPreContractMaterialDtlTOs(), preContractMaterialSaveReq.isVersion(),
				preContractMaterialSaveReq.isExternal(), procureCatgRepository, stockRepository,
				projCostStatementsRepositoryCopy, projStoreStockRepositoryCopy, materialClassRepository));
		if (CommonUtil.isListHasData(preContractMaterialSaveReq.getMaterialDtlIds())) {
			precontractMaterialRepository.deactivateMaterialDetails(preContractMaterialSaveReq.getMaterialDtlIds(),
					StatusCodes.DEACIVATE.getValue());
		}

	}

	public void savePreContratServices(PreContractServiceSaveReq preContractServiceSaveReq) {
		precontractServiceRepository.save(PrecontractServicesHandler.populatePreContractServiceEntities(
				preContractServiceSaveReq.getPreContractServiceDtlTOs(), preContractServiceSaveReq.isVersion(),
				preContractServiceSaveReq.isExternal(), procureCatgRepository, projCostStatementsRepositoryCopy,
				serviceRepository, stockRepository, projStoreStockRepositoryCopy, projSOWItemRepositoryCopy));
		if (CommonUtil.isListHasData(preContractServiceSaveReq.getServiceDtlIds())) {
			precontractServiceRepository.deactivateServiceDetails(preContractServiceSaveReq.getServiceDtlIds(),
					StatusCodes.DEACIVATE.getValue());
		}
	}

	public void savePreContratSOWTypes(PreContractSowDtlsSaveReq preContractSowDtlsSaveReq) {
		precontractSowRepository.save(PrecontractSowDtlHandler.populatePreContractsSowDtlEntities(
				preContractSowDtlsSaveReq.getPrecontractSowDtlTOs(), preContractSowDtlsSaveReq.isVersion(),
				preContractSowDtlsSaveReq.isExternal(), projSOWItemRepositoryCopy));
		if (CommonUtil.isListHasData(preContractSowDtlsSaveReq.getSowDtlIds())) {
			precontractServiceRepository.deactivateServiceDetails(preContractSowDtlsSaveReq.getSowDtlIds(),
					StatusCodes.DEACIVATE.getValue());
		}
	}

	public PurchaseOrderResp getPurchaseOrders(PurchaseOrderGetReq purchaseOrderGetReq) {

		List<PurchaseOrderEntity> purchaseOrderEntities = new ArrayList<PurchaseOrderEntity>();
		if(purchaseOrderGetReq.getStatus()==2) {
			 purchaseOrderEntities = purchaseOrderRepository
				.findAllPurchaseOrders(purchaseOrderGetReq.getProjIds());
		} else {
			 purchaseOrderEntities = purchaseOrderRepository
					.findPurchaseOrders(purchaseOrderGetReq.getProjIds(), purchaseOrderGetReq.getStatus());
		}
		PurchaseOrderResp purchaseOrderResp = new PurchaseOrderResp();
		for (PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
			purchaseOrderResp.getPurchaseOrderTOs().add(PurchaseOrderHandler.convertEntityToPOJO(purchaseOrderEntity));
		}
		return purchaseOrderResp;
	}

	public PreContractResp getPurchaseOrderDetails(PurchaseOrderGetReq purchaseOrderGetReq) {

		System.out.println("Started : ProcurementServiceImpl:getPurchaseOrderDetails");
		System.out.println("** isRepeatPO : getPurchaseId : " + purchaseOrderGetReq.getPurchaseId() +"PreContractId : "+purchaseOrderGetReq.getPreContractId() +" : ProjId : "+purchaseOrderGetReq.getProjId()+" : Status : "+purchaseOrderGetReq.getStatus());
		List<PreContractsCmpEntity> preContractsCmpEntities = preContractCmpRepository
				.findPreContractCompnies(purchaseOrderGetReq.getPreContractId(), purchaseOrderGetReq.getStatus());

		List<PreContractEntity> preContractEntites = precontractRepository.findPreContractDetailsById(
				purchaseOrderGetReq.getPreContractId(), purchaseOrderGetReq.getProjId(),
				purchaseOrderGetReq.getStatus());

		List<PreContractsEmpDtlEntity> preContractsEmpDtlEntities = precontractEmpRepository
				.findPreContractEmpDtlByCmpId(purchaseOrderGetReq.getPreContractId(),
						purchaseOrderGetReq.getContractCmpId(), purchaseOrderGetReq.getStatus());

		List<PreContractsPlantDtlEntity> preContractsPlantDtlEntities = precontractPlantRepository
				.findPreContractPlantDtlByCmpId(purchaseOrderGetReq.getPreContractId(),
						purchaseOrderGetReq.getContractCmpId(), purchaseOrderGetReq.getStatus());

		List<PreContractsMaterialDtlEntity> preContractsMaterialDtlEntities = precontractMaterialRepository
				.findPreContractMaterialDtlByCmpId(purchaseOrderGetReq.getPreContractId(),
						purchaseOrderGetReq.getContractCmpId(), purchaseOrderGetReq.getStatus());

		List<PreContractsServiceDtlEntity> preContractsServiceDtlEntities = precontractServiceRepository
				.findPreContractServiceDtlByCmpId(purchaseOrderGetReq.getPreContractId(),
						purchaseOrderGetReq.getContractCmpId(), purchaseOrderGetReq.getStatus());

		List<PrecontractSowDtlEntity> precontractSowDtlEntities = precontractSowRepository.findPreContractSowDtlByCmpId(
				purchaseOrderGetReq.getPreContractId(), purchaseOrderGetReq.getContractCmpId(),
				purchaseOrderGetReq.getStatus());


		List<PurchaseOrderRepeatEntity> procurementPoRepeatpoEntities = null;
		System.out.println(" purchaseOrderGetReq PreContractId : "+purchaseOrderGetReq.getPreContractId());
		System.out.println(" purchaseOrderGetReq PurchaseId(): "+purchaseOrderGetReq.getPurchaseId());

		PreContractResp preContractResp = new PreContractResp();
		PreContractTO preContractTO = null;
		System.out.println("preContractEntites : "+preContractEntites.size());
		for (PreContractEntity preContractEntity : preContractEntites) {
			preContractTO = PurchaseOrderHandler.populatePreContractPurchaseOrder(preContractEntity);
		}

		System.out.println("preContractsCmpEntities : "+preContractsCmpEntities.size());

		PreContractCmpResp preContractCmpResp = PrecontractCmpHandler
				.populatePreContractCmpResp(preContractsCmpEntities);
		if(preContractCmpResp!=null && preContractTO!=null)
		{
			if(preContractCmpResp.getPreContractCmpTOs()!=null)
			{
				preContractTO.setPreContractCmpTOs(preContractCmpResp.getPreContractCmpTOs());
			}else
				System.out.println("preContractCmpResp PreContractCmpTOs  is NULL");

		}else{
			System.out.println("preContractCmpResp or preContractTO is NULL");
		}

		PreContractEmpResp preContractEmpResp = PrecontractEmpHandler.getPreContractEmpTOs(preContractsEmpDtlEntities,
				true);
		for(PreContractEmpDtlTO preContractEmpDtlTO:preContractEmpResp.getPreContractEmpDtlTOs()) {
			Integer totalempSchItems = copyEmpProjectPODtlRepository.findtotalschItems(preContractEmpDtlTO.getId());
			preContractEmpDtlTO.setActualQty(totalempSchItems);
		}
		preContractTO.setPreContractEmpDtlTOs(preContractEmpResp.getPreContractEmpDtlTOs());

		PreContractPlantResp preContractPlantResp = PrecontractPlantHandler
				.getPreContractPlantTOs(preContractsPlantDtlEntities, true);
		for(PreContractPlantDtlTO preContractPlantDtlTO:preContractPlantResp.getPreContractPlantDtlTOs()) {
			Integer totalplantSchItems = copyPlantProjPORepository.findtotalschItems(preContractPlantDtlTO.getId());
			preContractPlantDtlTO.setActualQty(totalplantSchItems);
		}
		preContractTO.setPreContractPlantDtlTOs(preContractPlantResp.getPreContractPlantDtlTOs());

		PreContractMaterialResp preContractMaterialResp = PrecontractMaterialHandler
				.getPreContractMaterialTOs(preContractsMaterialDtlEntities, true);
		for(PreContractMaterialDtlTO preContractMaterialDtlTO:preContractMaterialResp.getPreContractMaterialDtlTOs()) {
			Integer totalmatSchItems = copyMaterialProjRepository.findtotalschItems(preContractMaterialDtlTO.getId());
			preContractMaterialDtlTO.setActualQty(totalmatSchItems);
		}
		preContractTO.setPreContractMaterialDtlTOs(preContractMaterialResp.getPreContractMaterialDtlTOs());

		PreContractServiceResp preContractServiceResp = PrecontractServicesHandler
				.getPreContractServiceTOs(preContractsServiceDtlEntities, true);
		for(PreContractServiceDtlTO preContractServiceDtlTO:preContractServiceResp.getPreContractServiceDtlTOs()) {
			Integer totalservSchItems = 0;
			Integer totalmanSchItems = copyEmpProjectPODtlRepository.findtotalschItems(preContractServiceDtlTO.getId());
			Integer totalplntvSchItems = copyPlantProjPORepository.findtotalschItems(preContractServiceDtlTO.getId());
			Integer totalmateSchItems = copyMaterialProjRepository.findtotalschItems(preContractServiceDtlTO.getId());
			totalservSchItems = totalmanSchItems + totalplntvSchItems + totalmateSchItems;
			preContractServiceDtlTO.setActualQty(totalservSchItems);
		}
		preContractTO.setPreContractServiceDtlTOs(preContractServiceResp.getPreContractServiceDtlTOs());

		PreContractSowResp preContractSowResp = PrecontractSowDtlHandler.getPreContractSowTOs(precontractSowDtlEntities,
				true);
		preContractTO.setPrecontractSowDtlTOs(preContractSowResp.getPrecontractSowDtlTOs());

		boolean isRepeatPOCreated =  isRepeatPOCreated(purchaseOrderGetReq.getProjId(),purchaseOrderGetReq.getPreContractId());
		System.out.println("isRepeatPOCreated : "+isRepeatPOCreated);
		preContractTO.setRepeatPOCreated(isRepeatPOCreated);

		System.out.println("** isRepeatPO : getPurchaseId : " + purchaseOrderGetReq.getPurchaseId()+" : ProjId : "+purchaseOrderGetReq.getProjId() +"PreContractId : "+purchaseOrderGetReq.getPreContractId() );
		boolean  isRepeatPOAlreadyExist = isRepeatPOAlreadyExist(purchaseOrderGetReq.getProjId(),purchaseOrderGetReq.getPreContractId(),false);
		System.out.println("isRepeatPOAlreadyExist : "+isRepeatPOAlreadyExist);
		if(isRepeatPOAlreadyExist==true)
		{
			System.out.println("setting updated values for RepeatPO : "+purchaseOrderGetReq.isRepeatPO());
			getRepeatPOPreContractTO(preContractTO);

			// Check is this record already Approved or not?
			boolean isApproved = isRepeatPOAlreadyExist(purchaseOrderGetReq.getProjId(),purchaseOrderGetReq.getPreContractId(),true);
			System.out.println("RepeatPOApproved : isApproved : "+isApproved);
			preContractTO.setRepeatPOApproved(isApproved);
			// check is PO Generated or not?
			//if(isApproved)
			//{

			//}

			boolean isRepeatPOInitiated = isRepeatPOAlreadyExist(purchaseOrderGetReq.getProjId(),purchaseOrderGetReq.getPreContractId(),false);
			System.out.println("RepeatPOApproved : isRepeatPOInitiated : "+isApproved);
			preContractTO.setRepeatPOInitiated(isRepeatPOInitiated);
			preContractResp.getPreContractTOs().add(preContractTO);
		}else{
			preContractResp.getPreContractTOs().add(preContractTO);
		}


		return preContractResp;
	}

	public PurchaseOrderRepeatEntity getPurchaseOrderRepeatEntity(String contractItemType,
																  Long contractId,Long contractCmpId,Long contractItemId,Long contractItemDetailId)
	{
		PurchaseOrderRepeatEntity purchaseOrderRepeatEntity = new PurchaseOrderRepeatEntity();
		System.out.println(" ** Before fetching --> "+
				" contractItemType :" +contractItemType
				+" contractId :" +contractId
				+" contractCmpId :" +contractCmpId
				+" contractItemId :" +contractItemId
				+" contractItemDetailId :" +contractItemDetailId);

		List<PurchaseOrderRepeatEntity> purchaseOrderRepeatEntities = purchaseOrderRepeatRepository.findRepeatPO(contractItemType,
				contractId,contractCmpId,contractItemId,contractItemDetailId);
		System.out.println("After fetching from DB --> purchaseOrderRepeatEntities size : "+purchaseOrderRepeatEntities.size());
		if(purchaseOrderRepeatEntities!=null && purchaseOrderRepeatEntities.size()>0)
		{
			System.out.println("purchaseOrderRepeatEntities size : "+purchaseOrderRepeatEntities.size());
			purchaseOrderRepeatEntity =  purchaseOrderRepeatEntities.get(0);
		}else{
			System.out.println("purchaseOrderRepeatEntity is NULL");
		}
		return purchaseOrderRepeatEntity;
	}

	// for checking is repeat PO generated or not?
	public boolean isRepeatPOCreated(Long projId, Long contractId)
	{
		System.out.println("** isRepeatPOCreated : PreContractId : "+contractId +" : ProjId : "+projId);

		boolean isRepeatPOCreated = false;
		PurchaseOrderRepeatEntity purchaseOrderRepeatEntity = null;
		System.out.println(" ** Before fetching --> "
				+" contractId :" +contractId
				+" projId :" +projId);

		if(projId!=null && contractId!=null )
		{
			List<PurchaseOrderRepeatEntity> purchaseOrderRepeatEntities = purchaseOrderRepeatRepository.findRepeatPOByContractId(projId,contractId);
			System.out.println("After fetching from DB --> purchaseOrderRepeatEntities size : "+purchaseOrderRepeatEntities.size());
			if(purchaseOrderRepeatEntities!=null && purchaseOrderRepeatEntities.size()>0)
			{
				System.out.println("purchaseOrderRepeatEntities size : "+purchaseOrderRepeatEntities.size());
				purchaseOrderRepeatEntity =  purchaseOrderRepeatEntities.get(0);
				if(purchaseOrderRepeatEntity.getRepeatPOId()!=null
						&& purchaseOrderRepeatEntity.getRepeatPOId()!=null
						&& purchaseOrderRepeatEntity.getRepeatPOId().longValue()>0)
				{
					isRepeatPOCreated = true;
				}
			}
		}else
		{
			System.out.println("input values for isRepeatPOAlreadyExist are NULL");
		}
		System.out.println("isRepeatPOCreated : "+isRepeatPOCreated);

		return isRepeatPOCreated;
	}

	// for getting Approved or Not
	public boolean isRepeatPOAlreadyExist(Long projId, Long contractId, boolean checkIsApproved)
	{
		System.out.println("** isRepeatPO : PreContractId : "+contractId +" : ProjId : "+projId);

		boolean isRepeatPOExist = false;
		boolean isRepeatPOApproved = false;
		PurchaseOrderRepeatEntity purchaseOrderRepeatEntity = null;
		System.out.println(" ** Before fetching --> "
				+" contractId :" +contractId
				+" projId :" +projId);

		if(projId!=null && contractId!=null )
		{
			//List<PurchaseOrderRepeatEntity> purchaseOrderRepeatEntities = purchaseOrderRepeatRepository.findRepeatPOByContractId(purchaseId,projId,contractId);
			List<PurchaseOrderRepeatEntity> purchaseOrderRepeatEntities = purchaseOrderRepeatRepository.findRepeatPOByContractId(projId,contractId);
			System.out.println("After fetching from DB --> purchaseOrderRepeatEntities size : "+purchaseOrderRepeatEntities.size());
			if(purchaseOrderRepeatEntities!=null && purchaseOrderRepeatEntities.size()>0)
			{
				System.out.println("purchaseOrderRepeatEntities size : "+purchaseOrderRepeatEntities.size());
				isRepeatPOExist = true;
				purchaseOrderRepeatEntity =  purchaseOrderRepeatEntities.get(0);
				if(checkIsApproved )
				{
					if(purchaseOrderRepeatEntity.getApprovedBy()!=null
							&& purchaseOrderRepeatEntity.getApprovedBy()!=null
							&& purchaseOrderRepeatEntity.getApprovedBy().longValue()>0)
					{
						isRepeatPOApproved = true;
					}
				}
			}else{
				isRepeatPOExist = false;
				System.out.println("purchaseOrderRepeatEntity is NULL");
			}
		}else
		{
			System.out.println("input values for isRepeatPOAlreadyExist are NULL");
		}

		System.out.println("checkIsApproved : "+checkIsApproved+"isRepeatPOApproved : "+isRepeatPOApproved+"isRepeatPOExist : "+isRepeatPOExist);

		if(checkIsApproved )
		{
			return isRepeatPOApproved;
		}else{
			return isRepeatPOExist;
		}
	}

	public void setDeliveryPlacesFromRepeatPO(PreContractMaterialDtlTO materialDtl,PurchaseOrderRepeatEntity repeatPO)
	{
		System.out.println("setDeliveryPlacesFromRepeatPO");

		String deliveryPlace = repeatPO.getDeliveryPlace();

		if(deliveryPlace!=null)
		{
			System.out.println("deliveryPlace : "+deliveryPlace);
			String projStoreId = "";
			String storeId = "";

			projStoreId = deliveryPlace.substring(0,deliveryPlace.indexOf(":"));
			storeId = deliveryPlace.substring(deliveryPlace.indexOf(":")+1,deliveryPlace.length());
			System.out.println("projStoreId : "+projStoreId);
			System.out.println("storeId : "+storeId);

			if(projStoreId != null && projStoreId.length()>0)
			{
				Long projStoreIdLong = 0l;
				try{
						projStoreIdLong = Long.parseLong(projStoreId);
					}catch (NumberFormatException e) {
					System.out.println("Exception projStoreIdLong : "+e.getMessage());
					}
				System.out.println("projStoreIdLong : "+projStoreIdLong);

				if(projStoreIdLong!=null & projStoreIdLong.longValue()>0)
				{
					ProjStoreStockMstrEntity projStoreStock = projStoreStockRepositoryCopy.findOne(projStoreIdLong);
					if (projStoreStock != null) {
						LabelKeyTO projStoreLabelKey = new LabelKeyTO();
						projStoreLabelKey.setId(projStoreStock.getId());
						materialDtl.setProjStoreLabelKey(projStoreLabelKey);
					}
				}

			}else
				System.out.println("projStoreId is NULL");

			if(storeId!=null && storeId.length()>0)
			{
				Long storeIdLong = 0l;
				try{
					storeIdLong = Long.parseLong(storeId);
				}catch (NumberFormatException e) {
					System.out.println("Exception storeIdLong : "+e.getMessage());
				}
				System.out.println("storeIdLong : "+storeIdLong);

				StockMstrEntity stockMstrEntity = stockRepository.findOne(storeIdLong);
				if (stockMstrEntity != null) {
					LabelKeyTO storeLabelKey = new LabelKeyTO();
					storeLabelKey.setId(stockMstrEntity.getId());
					materialDtl.setStoreLabelKey(storeLabelKey);
				}
			}else
				System.out.println("storeId is NULL");

		}else
			System.out.println("deliveryPlace is NULL");
	}
	// VIZ
	// Repeat PO
	//@Override
	public void getRepeatPOPreContractTO(PreContractTO preContractTO)
	{
		//PreContractTO repeatPOPreContractTO = new PreContractTO();
		System.out.println("Started : getRepeatPOPreContractTO");

		System.out.println(" *** getRepeatPOPreContractTO : isRepeatPOApproved : "+preContractTO.isRepeatPOApproved());
		System.out.println(" *** getRepeatPOPreContractTO : isRepeatPOInitiated : "+preContractTO.isRepeatPOInitiated());

		System.out.println("ProjCode : "+preContractTO.getProjCode());
		System.out.println("getId : "+preContractTO.getId());

		System.out.println("*** Started getRepeatPOPreContractTO ");
		System.out.println(" PreContractMaterialDtlTOs size : "+preContractTO.getPreContractMaterialDtlTOs().size());
		System.out.println(" PreContractEmpDtlTOs size : "+preContractTO.getPreContractEmpDtlTOs().size());
		System.out.println(" PreContractPlantDtlTOs size : "+preContractTO.getPreContractPlantDtlTOs().size());
		System.out.println(" PreContractServiceDtlTOs size : "+preContractTO.getPreContractServiceDtlTOs().size());
		System.out.println(" PrecontractSowDtlTOs size : "+preContractTO.getPrecontractSowDtlTOs().size());



		// for 1 Material
		if (CommonUtil.isListHasData(preContractTO.getPreContractMaterialDtlTOs()))
		{
			System.out.println(" PreContractMaterialDtlTOs size : "+preContractTO.getPreContractMaterialDtlTOs().size());
			for (PreContractMaterialDtlTO materialDtl : (List<PreContractMaterialDtlTO>) preContractTO.getPreContractMaterialDtlTOs()) {
				PurchaseOrderRepeatEntity repeatPO = new PurchaseOrderRepeatEntity();
				for (PreContractMaterialCmpTO materialCmp : (List<PreContractMaterialCmpTO>) materialDtl.getPreContractMaterialCmpTOs()) {
					System.out.println("Before saving materialCmp.getQuantity :" + materialCmp.getQuantity() + "Is Bidding :" + materialCmp.isApproveFlag());
					String contractItemType = "Material";
					Long contractId = materialDtl.getPreContractId();
					Long contractCmpId = materialCmp.getPreContractCmpId();
					Long contractItemId = materialCmp.getId();
					Long contractItemDetailId = materialCmp.getPreContractMaterialId();

					System.out.println(" Material--> contractItemType :" + contractItemType+ " contractId :" + contractId+ " contractCmpId :" + contractCmpId
							+ " contractItemId :" + contractItemId+ " contractItemDetailId :" + contractItemDetailId);

					repeatPO = getPurchaseOrderRepeatEntity(contractItemType,
							contractId, contractCmpId, contractItemId, contractItemDetailId);

					if (repeatPO != null) {
						if (repeatPO.getQuantity() != null) {
							materialCmp.setQuantity((int) (long) repeatPO.getQuantity());
						}
						if (repeatPO.getIsBid() != null) {
							materialCmp.setApproveFlag(repeatPO.getIsBid().intValue() == 1 ? true : false);
						}
					} else
						System.out.println("repeatPO is NULL");

					System.out.println("**Before saving materialCmp.getQuantity :" + materialCmp.getQuantity() + " : Is Bidding :" + materialCmp.isApproveFlag());
				}
				if (repeatPO != null) {
					materialDtl.setStartDate(CommonUtil.convertDateToString(repeatPO.getStartDate()));
					materialDtl.setFinishDate(CommonUtil.convertDateToString(repeatPO.getFinishDate()));
					// Setting setDeliveryPlacesFromRepeatPO
					setDeliveryPlacesFromRepeatPO(materialDtl,repeatPO);
					//TODO for Place of Delivery
					System.out.println("materialDtl  StartDate :" + materialDtl.getStartDate() + " : FinishDate :" + materialDtl.getFinishDate());
				}

				// Setting Dates
			}
		} // End of IF Condition
		//=======================================
		// 2 for Employee PreContractEmpDtlTO
		if (CommonUtil.isListHasData(preContractTO.getPreContractEmpDtlTOs()))
		{
			System.out.println(" getPreContractEmpDtlTOs size : "+preContractTO.getPreContractEmpDtlTOs().size());
			for (PreContractEmpDtlTO empDtl : (List<PreContractEmpDtlTO>) preContractTO.getPreContractEmpDtlTOs()) {
				PurchaseOrderRepeatEntity repeatPO = new PurchaseOrderRepeatEntity();
				for (PreContractsEmpCmpTO empCmp : (List<PreContractsEmpCmpTO>) empDtl.getPreContractsEmpCmpTOs()) {
					System.out.println("Before saving empCmp.getQuantity :" + empCmp.getQuantity() + "Is Bidding :" + empCmp.isApproveFlag());
					String contractItemType = "Employee";
					Long contractId = empDtl.getPreContractId();
					Long contractCmpId = empCmp.getPreContractCmpId();
					Long contractItemId = empCmp.getId();
					Long contractItemDetailId = empCmp.getPreContractEmpDtlId();

					System.out.println(" Employee--> contractItemType :" + contractItemType+ " contractId :" + contractId+ " contractCmpId :" + contractCmpId
							+ " contractItemId :" + contractItemId+ " contractItemDetailId :" + contractItemDetailId);

					repeatPO = getPurchaseOrderRepeatEntity(contractItemType,
							contractId, contractCmpId, contractItemId, contractItemDetailId);

					if (repeatPO != null) {
						if (repeatPO.getQuantity() != null) {
							empCmp.setQuantity((int) (long) repeatPO.getQuantity());
						}
						if (repeatPO.getIsBid() != null) {
							empCmp.setApproveFlag(repeatPO.getIsBid().intValue() == 1 ? true : false);
						}

					} else
						System.out.println("repeatPO is NULL");

					System.out.println("**Before saving EmployeeCmp.getQuantity :" + empCmp.getQuantity() + " : Is Bidding :" + empCmp.isApproveFlag());
				}
				if (repeatPO != null) {
					empDtl.setStartDate(CommonUtil.convertDateToString(repeatPO.getStartDate()));
					empDtl.setFinishDate(CommonUtil.convertDateToString(repeatPO.getFinishDate()));
					empDtl.setDeliveryPlace(repeatPO.getDeliveryPlace());
					System.out.println("EmployeeDtl  StartDate :" + empDtl.getStartDate() + " : FinishDate :" + empDtl.getFinishDate());
				}

				// Setting Dates
			}
		} // End of IF Condition
		//=======================================
		// 3 for Plant
		if (CommonUtil.isListHasData(preContractTO.getPreContractPlantDtlTOs()))
		{
			System.out.println(" getPreContractPlantDtlTOs size : "+preContractTO.getPreContractPlantDtlTOs().size());
			for (PreContractPlantDtlTO plantDtl : (List<PreContractPlantDtlTO>) preContractTO.getPreContractPlantDtlTOs()) {
				PurchaseOrderRepeatEntity repeatPO = new PurchaseOrderRepeatEntity();
				for (PreContractPlantCmpTO plantCmp : (List<PreContractPlantCmpTO>) plantDtl.getPreContractPlantCmpTOs()) {
					System.out.println("Before saving PlantCmp.getQuantity :" + plantCmp.getQuantity() + "Is Bidding :" + plantCmp.isApproveFlag());
					String contractItemType = "Plant";
					Long contractId = plantDtl.getPreContractId();
					Long contractCmpId = plantCmp.getPreContractCmpId();
					Long contractItemId = plantCmp.getId();
					Long contractItemDetailId = plantCmp.getPrePlantDtlId();

					System.out.println(" Plant--> contractItemType :" + contractItemType+ " contractId :" + contractId+ " contractCmpId :" + contractCmpId
							+ " contractItemId :" + contractItemId+ " contractItemDetailId :" + contractItemDetailId);

					repeatPO = getPurchaseOrderRepeatEntity(contractItemType,
							contractId, contractCmpId, contractItemId, contractItemDetailId);

					if (repeatPO != null) {
						if (repeatPO.getQuantity() != null) {
							plantCmp.setQuantity((int) (long) repeatPO.getQuantity());
						}
						if (repeatPO.getIsBid() != null) {
							plantCmp.setApproveFlag(repeatPO.getIsBid().intValue() == 1 ? true : false);
						}
					} else
						System.out.println("repeatPO is NULL");

					System.out.println("**Before saving PlantCmp.getQuantity :" + plantCmp.getQuantity() + " : Is Bidding :" + plantCmp.isApproveFlag());
				}
				if (repeatPO != null) {
					plantDtl.setStartDate(CommonUtil.convertDateToString(repeatPO.getStartDate()));
					plantDtl.setFinishDate(CommonUtil.convertDateToString(repeatPO.getFinishDate()));
					plantDtl.setDeliveryPlace(repeatPO.getDeliveryPlace());
					System.out.println("PlantDtl  StartDate :" + plantDtl.getStartDate() + " : FinishDate :" + plantDtl.getFinishDate());
				}

				// Setting Dates
			}
		} // End of IF Condition
		//=======================================
		// 4 for Service
		if (CommonUtil.isListHasData(preContractTO.getPreContractServiceDtlTOs()))
		{
			System.out.println(" getPreContractServiceDtlTOs size : "+preContractTO.getPreContractServiceDtlTOs().size());
			for (PreContractServiceDtlTO serviceDtl : (List<PreContractServiceDtlTO>) preContractTO.getPreContractServiceDtlTOs()) {
				PurchaseOrderRepeatEntity repeatPO = new PurchaseOrderRepeatEntity();
				for (PreContractServiceCmpTO serviceCmp : (List<PreContractServiceCmpTO>) serviceDtl.getPreContractServiceCmpTOs()) {
					System.out.println("Before saving ServiceCmp.getQuantity :" + serviceCmp.getQuantity() + "Is Bidding :" + serviceCmp.isApproveFlag());
					String contractItemType = "Service";
					Long contractId = serviceDtl.getPreContractId();
					Long contractCmpId = serviceCmp.getPreContractCmpId();
					Long contractItemId = serviceCmp.getId();
					Long contractItemDetailId = serviceCmp.getServiceDtlId();

					System.out.println(" Service --> contractItemType :" + contractItemType+ " contractId :" + contractId+ " contractCmpId :" + contractCmpId
							+ " contractItemId :" + contractItemId+ " contractItemDetailId :" + contractItemDetailId);

					repeatPO = getPurchaseOrderRepeatEntity(contractItemType,
							contractId, contractCmpId, contractItemId, contractItemDetailId);

					if (repeatPO != null) {
						if (repeatPO.getQuantity() != null) {
							serviceCmp.setQuantity((int) (long) repeatPO.getQuantity());
						}
						if (repeatPO.getIsBid() != null) {
							serviceCmp.setApproveFlag(repeatPO.getIsBid().intValue() == 1 ? true : false);
						}
					} else
						System.out.println("repeatPO is NULL");

					System.out.println("**Before saving ServiceCmp.getQuantity :" + serviceCmp.getQuantity() + " : Is Bidding :" + serviceCmp.isApproveFlag());
				}
				if (repeatPO != null) {
					serviceDtl.setStartDate(CommonUtil.convertDateToString(repeatPO.getStartDate()));
					serviceDtl.setFinishDate(CommonUtil.convertDateToString(repeatPO.getFinishDate()));
					serviceDtl.setDeliveryPlace(repeatPO.getDeliveryPlace());
					System.out.println("ServiceDtl  StartDate :" + serviceDtl.getStartDate() + " : FinishDate :" + serviceDtl.getFinishDate());
				}

				// Setting Dates
			}
		} // End of IF Condition
		//=======================================
		// 5 for Sow
		if (CommonUtil.isListHasData(preContractTO.getPrecontractSowDtlTOs()))
		{
			System.out.println(" getPrecontractSowDtlTOs size : "+preContractTO.getPrecontractSowDtlTOs().size());
			for (PrecontractSowDtlTO sowDtl : (List<PrecontractSowDtlTO>) preContractTO.getPrecontractSowDtlTOs()) {
				PurchaseOrderRepeatEntity repeatPO = new PurchaseOrderRepeatEntity();
				for (PrecontractSowCmpTO sowCmp : (List<PrecontractSowCmpTO>) sowDtl.getPrecontractSowCmpTOs()) {
					System.out.println("Before saving SowCmp.getQuantity :" + sowCmp.getQuantity() + "Is Bidding :" + sowCmp.isApproveFlag());
					String contractItemType = "Sow";
					Long contractId = sowDtl.getPreContractId();
					Long contractCmpId = sowCmp.getPreContractCmpId();
					Long contractItemId = sowCmp.getId();
					Long contractItemDetailId = sowCmp.getPreContractSowDtlId();

					System.out.println(" Sow --> contractItemType :" + contractItemType+ " contractId :" + contractId+ " contractCmpId :" + contractCmpId
							+ " contractItemId :" + contractItemId+ " contractItemDetailId :" + contractItemDetailId);

					repeatPO = getPurchaseOrderRepeatEntity(contractItemType,
							contractId, contractCmpId, contractItemId, contractItemDetailId);

					if (repeatPO != null) {
						if (repeatPO.getQuantity() != null) {
							sowCmp.setQuantity(repeatPO.getQuantity());
						}
						if (repeatPO.getIsBid() != null) {
							sowCmp.setApproveFlag(repeatPO.getIsBid().intValue() == 1 ? true : false);
						}
					} else
						System.out.println("repeatPO is NULL");

					System.out.println("**Before saving Sow Cmp.getQuantity :" + sowCmp.getQuantity() + " : Is Bidding :" + sowCmp.isApproveFlag());
				}
				if (repeatPO != null) {
					sowDtl.setStartDate(CommonUtil.convertDateToString(repeatPO.getStartDate()));
					sowDtl.setFinishDate(CommonUtil.convertDateToString(repeatPO.getFinishDate()));
					sowDtl.setDeliveryPlace(repeatPO.getDeliveryPlace());
					System.out.println("Sow Dtl  StartDate :" + sowDtl.getStartDate() + " : FinishDate :" + sowDtl.getFinishDate());
				}

				// Setting Dates
			}
		} // End of IF Condition
		//=======================================


		//return repeatPOPreContractTO;
	}
	public void savePurchaseOrders(PurchaseOrderSaveReq purchaseOrderSaveReq) {
		List<PurchaseOrderEntity> purchaseOrderEntities = new ArrayList<>();
		for (PurchaseOrderTO purchaseOrderTO : purchaseOrderSaveReq.getPurchaseOrderTOs()) {
			purchaseOrderEntities.add(PurchaseOrderHandler.convertPOJOToEntity(purchaseOrderTO, poDetailsRepository,
					purchaseOrderRepository, epsRepository, loginRepository));
		}
		purchaseOrderRepository.save(purchaseOrderEntities);
	}

	public void repeatPurchaseOrder(PurchaseOrderRepeatSaveReq purchaseOrderRepeatSaveReq) {
		PreContractsCmpEntity preContractsCmpEntity = null;

		System.out.println("ProcurementServiceImpl:repeatPurchaseOrder:PreContractCmpId:"+purchaseOrderRepeatSaveReq.getPreContractCmpId());

		preContractsCmpEntity = preContractCmpRepository.getOne(purchaseOrderRepeatSaveReq.getPreContractCmpId());

		PreContractsCmpEntity contractsCmpEntity = new PreContractsCmpEntity();
		contractsCmpEntity.setCompanyId(preContractsCmpEntity.getCompanyId());
		PreContractEntity preContractEntity = new PreContractEntity();
		preContractEntity.setId(preContractsCmpEntity.getPreContractEntity().getId());
		contractsCmpEntity.setPreContractEntity(preContractEntity);
		contractsCmpEntity.setStatus(StatusCodes.ACTIVE.getValue());
		contractsCmpEntity.setCmpStatus(WorkFlowStatus.APPROVED.getValue());

		contractsCmpEntity = preContractCmpRepository.save(contractsCmpEntity);

		repeatPurchaseOrder(contractsCmpEntity, purchaseOrderRepeatSaveReq);
	}


	// This is Wrong.
	public void repeatPurchaseOrder(PreContractsCmpEntity preContractsCmpEntity,
									PurchaseOrderRepeatSaveReq purchaseOrderRepeatSaveReq) {

		PurchaseOrderEntity purchaseOrderEntity = null;
		PreContractTO preContractTO = purchaseOrderRepeatSaveReq.getPreContractTO();
		purchaseOrderEntity = PurchaseOrderHandler.populatePurchaseOrderEntity(
				purchaseOrderRepeatSaveReq.getPreContractTO(), preContractsCmpEntity, epsRepository, loginRepository);
		purchaseOrderEntity
				.setParentId(purchaseOrderRepository.findOne(purchaseOrderRepeatSaveReq.getPurchaseOrderId()));
		PurchaseOrderEntity parentPurchaseOrderEntity = purchaseOrderRepository
				.findOne(purchaseOrderRepeatSaveReq.getPurchaseOrderId());
		if (CommonUtil.isBlankDate(purchaseOrderEntity.getStartDate()))
			purchaseOrderEntity.setStartDate(parentPurchaseOrderEntity.getStartDate());
		if (CommonUtil.isBlankDate(purchaseOrderEntity.getFinsihDate()))
			purchaseOrderEntity.setFinsihDate(parentPurchaseOrderEntity.getFinsihDate());
		purchaseOrderEntity.setAmount(parentPurchaseOrderEntity.getAmount());
		purchaseOrderEntity.setProcureType(parentPurchaseOrderEntity.getProcureType());
		purchaseOrderRepository.save(purchaseOrderEntity);

		if (CommonUtil.isListHasData(preContractTO.getPreContractEmpDtlTOs())) {

			List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities = new ArrayList<>();
			PreContractsEmpCmpEntity preContractsEmpCmpEntity = null;
			List<PreContractEmpDtlTO> preContractEmpDtlTOs = preContractTO.getPreContractEmpDtlTOs();
			for (PreContractEmpDtlTO preContractEmpDtlTO : preContractEmpDtlTOs) {
				for (PreContractsEmpCmpTO preContractsEmpCmpTO : preContractEmpDtlTO.getPreContractsEmpCmpTOs()) {
					preContractsEmpCmpEntity = PrecontractEmpHandler.convertCmpEmpPOJOToEntity(preContractsEmpCmpTO,
							true);
					preContractsEmpCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
					preContractsEmpCmpEntities.add(preContractsEmpCmpEntity);
				}

			}
			precontractEmpCmpRepository.save(preContractsEmpCmpEntities);
		}

		if (CommonUtil.isListHasData(preContractTO.getPreContractPlantDtlTOs())) {
			List<PreContractsPlantCmpEntity> preContractsPlantCmpEntities = new ArrayList<>();
			PreContractsPlantCmpEntity preContractsPlantCmpEntity = null;
			List<PreContractPlantDtlTO> preContractPlantDtlTOs = preContractTO.getPreContractPlantDtlTOs();
			for (PreContractPlantDtlTO preContractPlantDtlTO : preContractPlantDtlTOs) {
				for (PreContractPlantCmpTO preContractPlantCmpTO : preContractPlantDtlTO.getPreContractPlantCmpTOs()) {
					preContractsPlantCmpEntity = PrecontractPlantHandler
							.convertPlantCmpPOJOToEntity(preContractPlantCmpTO, true);
					preContractsPlantCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
					preContractsPlantCmpEntities.add(preContractsPlantCmpEntity);
				}

			}
			precontractPlantCmpRepository.save(preContractsPlantCmpEntities);
		}

		if (CommonUtil.isListHasData(preContractTO.getPreContractMaterialDtlTOs())) {
			List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities = new ArrayList<>();
			PreContractsMaterialCmpEntity preContractsMaterialCmpEntity = null;
			List<PreContractMaterialDtlTO> preContractMaterialDtlTOs = preContractTO.getPreContractMaterialDtlTOs();

			for (PreContractMaterialDtlTO preContractMaterialDtlTO : preContractMaterialDtlTOs) {
				for (PreContractMaterialCmpTO preContractMaterialCmpTO : preContractMaterialDtlTO
						.getPreContractMaterialCmpTOs()) {
					preContractsMaterialCmpEntity = PrecontractMaterialHandler
							.convertCmpMaterialPOJOToEntity(preContractMaterialCmpTO, true);
					preContractsMaterialCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
					preContractsMaterialCmpEntities.add(preContractsMaterialCmpEntity);
				}
			}
			precontractMaterialCmpRepository.save(preContractsMaterialCmpEntities);
		}
		if (CommonUtil.isListHasData(preContractTO.getPreContractServiceDtlTOs())) {
			List<PreContractsServiceCmpEntity> preContractsServiceCmpEntities = new ArrayList<>();
			PreContractsServiceCmpEntity preContractsServiceCmpEntity = null;
			List<PreContractServiceDtlTO> preContractServiceDtlTOs = preContractTO.getPreContractServiceDtlTOs();
			for (PreContractServiceDtlTO preContractServiceDtlTO : preContractServiceDtlTOs) {
				for (PreContractServiceCmpTO preContractServiceCmpTO : preContractServiceDtlTO
						.getPreContractServiceCmpTOs()) {
					preContractsServiceCmpEntity = PrecontractServicesHandler
							.convertServiceCmpPOJOToEntity(preContractServiceCmpTO, true);
					preContractsServiceCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
					preContractsServiceCmpEntities.add(preContractsServiceCmpEntity);
				}
				precontractServiceCmpRepository.save(preContractsServiceCmpEntities);
			}
		}
		if (CommonUtil.isListHasData(preContractTO.getPrecontractSowDtlTOs())) {
			List<PrecontractSowCmpEntity> precontractSowCmpEntities = new ArrayList<>();
			PrecontractSowCmpEntity precontractSowCmpEntity = null;
			List<PrecontractSowDtlTO> precontractSowDtlTOs = preContractTO.getPrecontractSowDtlTOs();
			for (PrecontractSowDtlTO precontractSowDtlTO : precontractSowDtlTOs) {
				for (PrecontractSowCmpTO precontractSowCmpTO : precontractSowDtlTO.getPrecontractSowCmpTOs()) {
					precontractSowCmpEntity = PrecontractSowDtlHandler.convertSowCmpPOJOToEntity(precontractSowCmpTO,
							true);
					precontractSowCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
					precontractSowCmpEntities.add(precontractSowCmpEntity);
				}
				precontractSowCmpRepository.save(precontractSowCmpEntities);
			}
		}
	}

	public void displayingExistingPODetails(List<PurchaseOrderRepeatEntity> purchaseOrderRepeatEntities)
	{
		for (PurchaseOrderRepeatEntity purchaseOrderRepeaty : purchaseOrderRepeatEntities) {
			System.out.println("PurchaseOrderRepeatEntity Id :"+purchaseOrderRepeaty.getId()
					+" :ParentPOId: "+purchaseOrderRepeaty.getParentPOId()
					+" :StartDate: "+purchaseOrderRepeaty.getStartDate()
					+" :FinishDate: "+purchaseOrderRepeaty.getFinishDate()
					+" :DeliveryPlace: "+purchaseOrderRepeaty.getDeliveryPlace()
					+" :Quantity: "+purchaseOrderRepeaty.getQuantity()
					+" :IsBid: "+purchaseOrderRepeaty.getIsBid()
					+" :getContractItemDetailId: "+purchaseOrderRepeaty.getContractItemDetailId()
					+" :getContractItemType: "+purchaseOrderRepeaty.getContractItemType()
			);
		}

	}

	public void deleteExistingPODetails(List<PurchaseOrderRepeatEntity> purchaseOrderRepeatEntities)
	{
		for (PurchaseOrderRepeatEntity purchaseOrderRepeaty : purchaseOrderRepeatEntities) {
			purchaseOrderRepeatRepository.delete(purchaseOrderRepeaty);
		}

	}

	public RepeatPurchaseOrderResp getRepeatPurchaseOrders(RepeatPurchaseOrderGetReq repeatPurchaseOrderGetReq)
	{
		System.out.println("*** Started getRepeatPurchaseOrders");
		System.out.println(" ** ProcurementServiceImpl : getRepeatPurchaseOrder: ProjId: "+repeatPurchaseOrderGetReq.getProjId()
				+ " : Contract Id : "+repeatPurchaseOrderGetReq.getContractId()
				+ " : PurchaseOrder Id : "+repeatPurchaseOrderGetReq.getPurchaseOrderId());
		RepeatPurchaseOrderResp repeatPurchaseOrderResp = new RepeatPurchaseOrderResp();

		List<PreContractEntity> preContractEntites = precontractRepository.findPreContractDetailsById(
				repeatPurchaseOrderGetReq.getContractId(), repeatPurchaseOrderGetReq.getProjId(), 1);

		System.out.println(" preContractEntites Size :"+preContractEntites.size());

		PreContractResp preContractResp = PrecontractHandler.populatePreContractDetails(preContractEntites, true);
		boolean isRepeatPOApproved = false;
		boolean isRepeatPOInitiated = false;
		boolean isRepeatPOCreated = false;
		for (PreContractTO preContractTO : preContractResp.getPreContractTOs()) {

			getRepeatPOPreContractTO(preContractTO);

			// Check is this record already Approved or not?
			isRepeatPOApproved = isRepeatPOAlreadyExist(preContractTO.getProjId(),preContractTO.getId(),true);
			System.out.println("RepeatPOApproved : isApproved : "+isRepeatPOApproved);
			preContractTO.setRepeatPOApproved(isRepeatPOApproved);

			isRepeatPOInitiated = isRepeatPOAlreadyExist(preContractTO.getProjId(),preContractTO.getId(),false);
			System.out.println("RepeatPOApproved : isRepeatPOInitiated : "+isRepeatPOApproved);
			preContractTO.setRepeatPOInitiated(isRepeatPOInitiated);

			isRepeatPOCreated = isRepeatPOCreated(preContractTO.getProjId(),preContractTO.getId());
			System.out.println("isRepeatPOCreated : isRepeatPOCreated : "+isRepeatPOCreated);
			preContractTO.setRepeatPOCreated(isRepeatPOCreated);

			repeatPurchaseOrderResp.setRepeatPOApproved(isRepeatPOApproved);
			repeatPurchaseOrderResp.setRepeatPOInitiated(isRepeatPOInitiated);
			repeatPurchaseOrderResp.setRepeatPOCreated(isRepeatPOCreated);

		}

		System.out.println("PreContractResp PreContractTOs Size : "+preContractResp.getPreContractTOs().size());
		repeatPurchaseOrderResp.setPreContractTO(preContractResp.getPreContractTOs().get(0));

		//repeatPurchaseOrderResp.setRepeatPOApproved(preContractResp.getPreContractTOs().get(0));
		//repeatPurchaseOrderResp.setRepeatPOInitiated(preContractResp.getPreContractTOs().get(0));

		System.out.println(" *** isRepeatPOApproved : "+preContractResp.getPreContractTOs().get(0).isRepeatPOApproved());
		System.out.println(" *** isRepeatPOApproved : "+preContractResp.getPreContractTOs().get(0).isRepeatPOApproved());
		System.out.println(" *** isRepeatPOCreated : "+preContractResp.getPreContractTOs().get(0).isRepeatPOCreated());

		System.out.println("preContractEntites Size : "+preContractEntites.size());

		return repeatPurchaseOrderResp;
	}

	// VIZ This is for Repeat PurchaseOrder Id
	public RepeatPurchaseOrderResp getRepeatPurchaseOrdersBackUp(RepeatPurchaseOrderGetReq repeatPurchaseOrderGetReq)
	{
		//RepeatPurchaseOrderResp repeatPurchaseOrderResp = new RepeatPurchaseOrderResp();
		System.out.println("*** Started getRepeatPurchaseOrders");
		System.out.println(" ** ProcurementServiceImpl : getRepeatPurchaseOrder: ProjId: "+repeatPurchaseOrderGetReq.getProjId()
				+ " : Contract Id : "+repeatPurchaseOrderGetReq.getContractId()
				+ " : PurchaseOrder Id : "+repeatPurchaseOrderGetReq.getPurchaseOrderId());

		System.out.println("PurchaseOrderId:"+ repeatPurchaseOrderGetReq.getPurchaseOrderId());
		System.out.println("ProjId:"+ repeatPurchaseOrderGetReq.getProjId());
		System.out.println("RepeatPurchaseOrderId:"+ repeatPurchaseOrderGetReq.getRepeatPurchaseOrderId());
		System.out.println("ContractItemType:"+ repeatPurchaseOrderGetReq.getContractItemType());
		System.out.println("ContractId:"+ repeatPurchaseOrderGetReq.getContractId());
		System.out.println("ContractCmpId:"+ repeatPurchaseOrderGetReq.getContractCmpId());
		System.out.println("ContractItemDetailId:"+ repeatPurchaseOrderGetReq.getContractItemDetailId());
		System.out.println("ContractItemId:"+ repeatPurchaseOrderGetReq.getContractItemId());
		System.out.println("ProjIds size:"+ repeatPurchaseOrderGetReq.getProjIds().size());

		// System.out.println("PreContractId : "+purchaseOrderGetReq.getPreContractId() +" : ProjId : "+purchaseOrderGetReq.getProjId()+" : Status : "+purchaseOrderGetReq.getStatus());
		// PreContractResp getPurchaseOrderDetails(PurchaseOrderGetReq purchaseOrderGetReq)

		PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();

		purchaseOrderGetReq.setClientId(repeatPurchaseOrderGetReq.getClientId()); // but no values getting
		purchaseOrderGetReq.setProjId(repeatPurchaseOrderGetReq.getProjId());
		purchaseOrderGetReq.setPurchaseId(repeatPurchaseOrderGetReq.getPurchaseOrderId());
		purchaseOrderGetReq.setPreContractId(repeatPurchaseOrderGetReq.getContractId());
		purchaseOrderGetReq.setContractCmpId(repeatPurchaseOrderGetReq.getContractCmpId());
		purchaseOrderGetReq.setPreContractCode(repeatPurchaseOrderGetReq.getPreContractCode());
		purchaseOrderGetReq.setProjIds(repeatPurchaseOrderGetReq.getProjIds());
		purchaseOrderGetReq.setItemType(repeatPurchaseOrderGetReq.getItemType());
		purchaseOrderGetReq.setApproveStatus(repeatPurchaseOrderGetReq.getApproveStatus());
		purchaseOrderGetReq.setPaymentStatus(repeatPurchaseOrderGetReq.getPaymentStatus());
		purchaseOrderGetReq.setLoginUser(repeatPurchaseOrderGetReq.isLoginUser());
		purchaseOrderGetReq.setPrecontractCmpIds(repeatPurchaseOrderGetReq.getPrecontractCmpIds());
		purchaseOrderGetReq.setStatus(new Integer(1));

		//PreContractOnLoadResp preContractOnLoadResp = new PreContractOnLoadResp();
		//PreContractOnLoadResp preContractOnLoadResp = new PreContractOnLoadResp();

		//PreContractResp preContractResp = getPurchaseOrderDetails(purchaseOrderGetReq);

		//PreContractTO preContractTO =  preContractResp.getPreContractTOs().get(0);
		//preContractOnLoadResp.setPreContractTO(preContractTO);

		//PreContractTO repeatPOPreContractTO = getRepeatPOPreContractTO(preContractResp.getPreContractTOs().get(0));

		// Adding RepeatPO Data
		//System.out.println("Before calling New Implementation");
		//preContractResp.getRepeatPOPreContractTOs().add(repeatPOPreContractTO);
		//System.out.println("After Adding preContractResp RepeatPOPreContractTOs size :  "+preContractResp.getRepeatPOPreContractTOs().size());

		//populatePrecontractPOMaps(purchaseOrderGetReq, preContractOnLoadResp);

		//-- Capturing the returned values ---
		/*RepeatPurchaseOrderResp repeatPurchaseOrderResp = new RepeatPurchaseOrderResp();

		if(preContractResp.getPreContractTOs().get(0)!=null)
		{
			repeatPurchaseOrderResp.setPreContractTO(preContractResp.getPreContractTOs().get(0));
		}else
			System.out.println(" preContractTO is NULL and not able to sent in response");

		if(getRepeatPOPreContractTO(preContractResp.getPreContractTOs().get(0)) != null)
		{
			repeatPurchaseOrderResp.setRepeatPOPreContractTO(getRepeatPOPreContractTO(preContractResp.getPreContractTOs().get(0)));
		}else
			System.out.println(" repeatPOPreContractTO is NULL and not able to sent in response");*/



		/*repeatPurchaseOrderResp.setPreContractTO(preContractOnLoadResp.getPreContractTO());
		repeatPurchaseOrderResp.setPreContractReqApprTOs(preContractOnLoadResp.getPreContractReqApprTOs());
		repeatPurchaseOrderResp.setWorkFlowStatusTOs(preContractOnLoadResp.getWorkFlowStatusTOs());
		repeatPurchaseOrderResp.setPreContractEmpDtlTOs(preContractOnLoadResp.getPreContractEmpDtlTOs());
		repeatPurchaseOrderResp.setProjEmpClassMap(preContractOnLoadResp.getProjEmpClassMap());
		repeatPurchaseOrderResp.setProjPlantMap(preContractOnLoadResp.getProjPlantMap());
		repeatPurchaseOrderResp.setProjMaterialClassMap(preContractOnLoadResp.getProjMaterialClassMap());
		repeatPurchaseOrderResp.setProjServiceMap(preContractOnLoadResp.getProjServiceMap());
		repeatPurchaseOrderResp.setProjStoreMap(preContractOnLoadResp.getProjStoreMap());
		repeatPurchaseOrderResp.setStoreMap(preContractOnLoadResp.getStoreMap());
		repeatPurchaseOrderResp.setProjCostItemMap(preContractOnLoadResp.getProjCostItemMap());
		repeatPurchaseOrderResp.setCompanyMap(preContractOnLoadResp.getCompanyMap());
		repeatPurchaseOrderResp.setUsersMap(preContractOnLoadResp.getUsersMap());
		repeatPurchaseOrderResp.setProcureCategoryMap(preContractOnLoadResp.getProcureCategoryMap());
		repeatPurchaseOrderResp.setAddressMap(preContractOnLoadResp.getAddressMap());
		repeatPurchaseOrderResp.setContactsMap(preContractOnLoadResp.getContactsMap());
		repeatPurchaseOrderResp.setUserProjMap(preContractOnLoadResp.getUserProjMap());
		repeatPurchaseOrderResp.setProjSOWMap(preContractOnLoadResp.getProjSOWMap());
		repeatPurchaseOrderResp.setCurrencyList(preContractOnLoadResp.getCurrencyList());*/


		return null;
	}

	public HashMap<String,String> getMaterialDeliveryPlace(PreContractMaterialDtlTO materialDtl)
	{
		HashMap<String,String> deliveryPlaceMap = new HashMap<String,String>();

		if (materialDtl.getProjStoreLabelKey() != null
				&& CommonUtil.isNonBlankLong(materialDtl.getProjStoreLabelKey().getId())) {
			ProjStoreStockMstrEntity preContract = projStoreStockRepositoryCopy
					.findOne(materialDtl.getProjStoreLabelKey().getId());
			//preContractsMaterialDtlEntity.setProjStoreId(preContract);
			if(preContract!=null)
			{
				deliveryPlaceMap.put("ProjStoreId",Long.toString(preContract.getId()));
			}
		}

		if (materialDtl.getStoreLabelKey() != null
				&& CommonUtil.isNonBlankLong(materialDtl.getStoreLabelKey().getId())) {
			StockMstrEntity stockMstrEntity = stockRepository.findOne(materialDtl.getStoreLabelKey().getId());
			if(stockMstrEntity!=null)
			{
				deliveryPlaceMap.put("StoreId",Long.toString(stockMstrEntity.getId()));
			}
		}
		System.out.println("getDeliveryPlace:deliveryPlaceMap:"+deliveryPlaceMap);

		return deliveryPlaceMap;
	}


	public void saveprocurementporepeatpo(ProcurementPoRepeatpoSaveReq procurementporepeatposavereq)
	{
		System.out.println("** Started saveprocurementporepeatpo : getApprovedBy : "+procurementporepeatposavereq.getApprovedBy());

		PreContractsCmpEntity preContractsCmpEntity = null;
		PurchaseOrderRepeatEntity purchaseOrderRepeatEntity = null;
		List<PurchaseOrderRepeatEntity> purchaseOrderRepeatSaveEntities = new ArrayList<PurchaseOrderRepeatEntity>();

		System.out.println("Started : saveprocurementporepeatpo PreContractCmpId : "+procurementporepeatposavereq.getPreContractCmpId());
		System.out.println("procurementporepeatposavereq:"+procurementporepeatposavereq.toString());
		System.out.println("procurementporepeatposavereq:"+procurementporepeatposavereq.getProcurementPoRepeatpoTO().toString());
		System.out.println(" PreContractCmpId:"+procurementporepeatposavereq.getPreContractCmpId());
		Long rpeatPOProjId = procurementporepeatposavereq.getProjId();
		System.out.println(" ** ProjId:"+rpeatPOProjId);

		Long purchaseOrderEntityId = procurementporepeatposavereq.getPurchaseOrderId();
		System.out.println("purchaseOrderEntityId :" + purchaseOrderEntityId);

		// Deleting Already Existing Data for that PO
		List<PurchaseOrderRepeatEntity> purchaseOrderRepeatEntities = purchaseOrderRepeatRepository.findPurchaseOrderRepeatByPO(purchaseOrderEntityId);
		System.out.println("Deleting Existing Records for PO :"+purchaseOrderEntityId+" : Size:" + purchaseOrderRepeatEntities.size());
		if(purchaseOrderRepeatEntities!=null && purchaseOrderRepeatEntities.size()>0)
		{
			deleteExistingPODetails(purchaseOrderRepeatEntities);
		}

		preContractsCmpEntity = preContractCmpRepository.getOne(procurementporepeatposavereq.getPreContractCmpId());

		//System.out.println("preContractsCmpEntity:"+preContractsCmpEntity.toString());
		//System.out.println("PurchaseOrderId :"+ procurementporepeatposavereq.getPurchaseOrderId());
		//System.out.println("PreContractCmpId :"+ procurementporepeatposavereq.getPreContractCmpId());

		ProcurementPoRepeatpoTO preContractTO = procurementporepeatposavereq.getProcurementPoRepeatpoTO();
		PreContractTO preContractTO2 = procurementporepeatposavereq.getPreContractTO();
		Long preContractTOId = preContractTO2.getId();
		// fetch data



		//System.out.println("preContractTO :" + preContractTO.toString());
		//System.out.println("PreContractMaterialDtlTOs Size :" + preContractTO.getPreContractMaterialDtlTOs());
		//System.out.println("CommonUtil.isListHasData(preContractTO.getPreContractMaterialDtlTOs()) : "+ CommonUtil.isListHasData(preContractTO.getPreContractMaterialDtlTOs()));

		// 1) This is for Material
		for (PreContractMaterialDtlTO preContractMaterialDtlTO : preContractTO.getPreContractMaterialDtlTOs()) {
			HashMap<String,String> deliveryPlaceMap = getMaterialDeliveryPlace(preContractMaterialDtlTO);
			purchaseOrderRepeatEntity = ProcurementPoRepeatpoHandler.getRepeatPOPreContractMaterial(preContractMaterialDtlTO, true,purchaseOrderEntityId,deliveryPlaceMap);
			System.out.println("Before saving to new purchaseOrderRepeatRepository");
			//
			// Setting Project Id
			purchaseOrderRepeatEntity.setProjId(rpeatPOProjId);
			//purchaseOrderRepeatRepository.saveAndFlush(purchaseOrderRepeatEntity);
			if(procurementporepeatposavereq.getApprovedBy()!=null && procurementporepeatposavereq.getApprovedBy().longValue()>0)
			{
				purchaseOrderRepeatEntity.setApprovedBy(procurementporepeatposavereq.getApprovedBy());
			}
			purchaseOrderRepeatSaveEntities.add(purchaseOrderRepeatEntity);
		}

		// -- for getPreContractEmpDtlTOs
		//System.out.println("getPreContractEmpDtlTOs Size :" + preContractTO.getPreContractEmpDtlTOs().size());
		//System.out.println(preContractTO.getPreContractEmpDtlTOs().toString());
		//System.out.println(" CommonUtil.isListHasData(preContractTO.getPreContractEmpDtlTOs()) : "+ CommonUtil.isListHasData(preContractTO.getPreContractEmpDtlTOs()));

		List<PreContractEmpDtlTO> preContractEmpDtlTOs = preContractTO.getPreContractEmpDtlTOs();

		// 2) This is for Emp
		for (PreContractEmpDtlTO preContractEmpDtlTO : preContractEmpDtlTOs) {
			System.out.println(" ItemCode : "+preContractEmpDtlTO.getItemCode());
			System.out.println(" ItemDesc : "+preContractEmpDtlTO.getItemDesc());
			System.out.println(" Quantity : "+preContractEmpDtlTO.getQuantity());
			System.out.println(" EstimateCost : "+preContractEmpDtlTO.getEstimateCost());
			System.out.println(" StartDate : "+preContractEmpDtlTO.getStartDate());
			System.out.println(" FinishDate : "+preContractEmpDtlTO.getFinishDate());

			purchaseOrderRepeatEntity = ProcurementPoRepeatpoHandler.getRepeatPOPreContractEmpDtl(preContractEmpDtlTO, true,purchaseOrderEntityId);
			System.out.println("Before saving to new purchaseOrderRepeatRepository");
			//purchaseOrderRepeatSaveEntities.add(purchaseOrderRepeatEntity);
			// Setting Project Id
			purchaseOrderRepeatEntity.setProjId(rpeatPOProjId);
			//purchaseOrderRepeatRepository.saveAndFlush(purchaseOrderRepeatEntity);
			if(procurementporepeatposavereq.getApprovedBy()!=null && procurementporepeatposavereq.getApprovedBy().longValue()>0)
			{
				purchaseOrderRepeatEntity.setApprovedBy(procurementporepeatposavereq.getApprovedBy());
			}
			purchaseOrderRepeatSaveEntities.add(purchaseOrderRepeatEntity);
		}

		// 3) This is for Plant
		List<PreContractPlantDtlTO> preContractPlantDtlTOs = preContractTO.getPreContractPlantDtlTOs();

		for (PreContractPlantDtlTO preContractPlantDtlTO : preContractPlantDtlTOs) {
			System.out.println(" ItemCode : "+preContractPlantDtlTO.getItemCode());
			System.out.println(" ItemDesc : "+preContractPlantDtlTO.getItemDesc());
			System.out.println(" Quantity : "+preContractPlantDtlTO.getQuantity());
			System.out.println(" EstimateCost : "+preContractPlantDtlTO.getEstimateCost());
			System.out.println(" StartDate : "+preContractPlantDtlTO.getStartDate());
			System.out.println(" FinishDate : "+preContractPlantDtlTO.getFinishDate());

			purchaseOrderRepeatEntity = ProcurementPoRepeatpoHandler.getRepeatPOPreContractPlantDtl(preContractPlantDtlTO, true,purchaseOrderEntityId);
			System.out.println("Before saving to new purchaseOrderRepeatRepository purchaseOrderRepeatEntity ");
			//purchaseOrderRepeatSaveEntities.add(purchaseOrderRepeatEntity);
			// Setting Project Id
			purchaseOrderRepeatEntity.setProjId(rpeatPOProjId);
			//purchaseOrderRepeatRepository.saveAndFlush(purchaseOrderRepeatEntity);
			if(procurementporepeatposavereq.getApprovedBy()!=null && procurementporepeatposavereq.getApprovedBy().longValue()>0)
			{
				purchaseOrderRepeatEntity.setApprovedBy(procurementporepeatposavereq.getApprovedBy());
			}
			purchaseOrderRepeatSaveEntities.add(purchaseOrderRepeatEntity);
		}

		// 4) This is for Contract Service
		List<PreContractServiceDtlTO> preContractServiceDtlTOs = preContractTO.getPreContractServiceDtlTOs();

		for (PreContractServiceDtlTO preContractServiceDtlTO : preContractServiceDtlTOs) {
			System.out.println(" ItemCode : "+preContractServiceDtlTO.getItemCode());
			System.out.println(" ItemDesc : "+preContractServiceDtlTO.getItemDesc());
			System.out.println(" Quantity : "+preContractServiceDtlTO.getQuantity());
			System.out.println(" EstimateCost : "+preContractServiceDtlTO.getEstimateCost());
			System.out.println(" StartDate : "+preContractServiceDtlTO.getStartDate());
			System.out.println(" FinishDate : "+preContractServiceDtlTO.getFinishDate());

			purchaseOrderRepeatEntity = ProcurementPoRepeatpoHandler.getRepeatPOPreContractServiceDtl(preContractServiceDtlTO, true,purchaseOrderEntityId);
			System.out.println("Before saving to new getRepeatPOPreContractServiceDtl ");
			//purchaseOrderRepeatSaveEntities.add(purchaseOrderRepeatEntity);
			// Setting Project Id
			purchaseOrderRepeatEntity.setProjId(rpeatPOProjId);
			//purchaseOrderRepeatRepository.saveAndFlush(purchaseOrderRepeatEntity);
			if(procurementporepeatposavereq.getApprovedBy()!=null && procurementporepeatposavereq.getApprovedBy().longValue()>0)
			{
				purchaseOrderRepeatEntity.setApprovedBy(procurementporepeatposavereq.getApprovedBy());
			}
			purchaseOrderRepeatSaveEntities.add(purchaseOrderRepeatEntity);
		}

		// 5) This is for Sow Details
		List<PrecontractSowDtlTO> precontractSowDtlTOs = preContractTO.getPrecontractSowDtlTOs();

		for (PrecontractSowDtlTO precontractSowDtlTO : precontractSowDtlTOs) {

			System.out.println(" ItemCode : "+precontractSowDtlTO.getItemCode());
			System.out.println(" ItemDesc : "+precontractSowDtlTO.getItemDesc());
			System.out.println(" Quantity : "+precontractSowDtlTO.getQuantity());
			System.out.println(" EstimateCost : "+precontractSowDtlTO.getEstimateCost());
			System.out.println(" StartDate : "+precontractSowDtlTO.getStartDate());
			System.out.println(" FinishDate : "+precontractSowDtlTO.getFinishDate());

			purchaseOrderRepeatEntity = ProcurementPoRepeatpoHandler.getRepeatPOPrecontractSowDtl(precontractSowDtlTO, true,purchaseOrderEntityId);
			System.out.println("Before saving to new getRepeatPOPreContractServiceDtl ");
			//purchaseOrderRepeatSaveEntities.add(purchaseOrderRepeatEntity);
			// Setting Project Id
			purchaseOrderRepeatEntity.setProjId(rpeatPOProjId);
			//purchaseOrderRepeatRepository.saveAndFlush(purchaseOrderRepeatEntity);
			if(procurementporepeatposavereq.getApprovedBy()!=null && procurementporepeatposavereq.getApprovedBy().longValue()>0)
			{
				purchaseOrderRepeatEntity.setApprovedBy(procurementporepeatposavereq.getApprovedBy());
			}
			purchaseOrderRepeatSaveEntities.add(purchaseOrderRepeatEntity);
		}


		// Finally Saving
		if(purchaseOrderRepeatSaveEntities!=null && purchaseOrderRepeatSaveEntities.size()>0)
		{
			System.out.println("purchaseOrderRepeatSaveEntities size :"+purchaseOrderRepeatSaveEntities.size());
			displayBeforeSaving(purchaseOrderRepeatSaveEntities);
			purchaseOrderRepeatRepository.save(purchaseOrderRepeatSaveEntities);
		}else{
			System.out.println("No Values for purchaseOrderRepeatSaveEntities");
		}


		System.out.println("At the END, After Saving the data");

		System.out.println("Ended : saveprocurementporepeatpo ");
	}

	public void displayBeforeSaving(PurchaseOrderRepeatEntity procurementPoRepeatpoEntity)
	{
		System.out.println("displayBeforeSaving procurementporepeatpoentities");

		System.out.println("getId : "+procurementPoRepeatpoEntity.getId());
		//System.out.println("getPurchaseorderentity : "+procurementPoRepeatpoEntity.getPurchaseorderentity());
		//System.out.println("getParentscheduleid : "+procurementPoRepeatpoEntity.getParentscheduleid());
		System.out.println("getStartDate : "+procurementPoRepeatpoEntity.getStartDate());
		System.out.println("getFinishDate : "+procurementPoRepeatpoEntity.getFinishDate());
		//System.out.println("getDeliveryplace : "+procurementPoRepeatpoEntity.getDeliveryplace());
		System.out.println("getQuantity : "+procurementPoRepeatpoEntity.getQuantity());
		//System.out.println("getBid : "+procurementPoRepeatpoEntity.getBid());
		System.out.println("getCreatedBy : "+procurementPoRepeatpoEntity.getCreatedBy());
		System.out.println("getCreatedOn : "+procurementPoRepeatpoEntity.getCreatedOn());
		System.out.println("getUpdatedBy : "+procurementPoRepeatpoEntity.getUpdatedBy());
		System.out.println("getUpdatedOn : "+procurementPoRepeatpoEntity.getUpdatedOn());
		//System.out.println("getStatus : "+procurementPoRepeatpoEntity.getStatus());

	}

	public void displayBeforeSaving(List<PurchaseOrderRepeatEntity> procurementporepeatpoentities)
	{
		System.out.println("*** displayBeforeSaving procurementporepeatpoentities Size : "+procurementporepeatpoentities.size());
		for (PurchaseOrderRepeatEntity procurementPoRepeatpoEntity : procurementporepeatpoentities) {

			System.out.println("getId : "+procurementPoRepeatpoEntity.getId());
			System.out.println("getPurchaseorderentity : "+procurementPoRepeatpoEntity.getId());
			System.out.println("getContractItemDetailId : "+procurementPoRepeatpoEntity.getContractItemDetailId());
			System.out.println("getContractItemType : "+procurementPoRepeatpoEntity.getContractItemType());
			System.out.println("getContractItemId : "+procurementPoRepeatpoEntity.getContractItemId());

			System.out.println("getStartDate : "+procurementPoRepeatpoEntity.getStartDate());
			System.out.println("getFinishDate : "+procurementPoRepeatpoEntity.getFinishDate());
			System.out.println("getDeliveryplace : "+procurementPoRepeatpoEntity.getDeliveryPlace());
			System.out.println("getQuantity : "+procurementPoRepeatpoEntity.getQuantity());
			System.out.println("getBid : "+procurementPoRepeatpoEntity.getIsBid());
			System.out.println("getCreatedBy : "+procurementPoRepeatpoEntity.getCreatedBy());
			System.out.println("getCreatedOn : "+procurementPoRepeatpoEntity.getCreatedOn());
			System.out.println("getUpdatedBy : "+procurementPoRepeatpoEntity.getUpdatedBy());
			System.out.println("getUpdatedOn : "+procurementPoRepeatpoEntity.getUpdatedOn());
			//System.out.println("getStatus : "+procurementPoRepeatpoEntity.getStatus());
		}
	}
	//Repeatpo
	public void saveprocurementporepeatpo2(ProcurementPoRepeatpoSaveReq procurementporepeatposavereq){

		System.out.println("Started : saveprocurementporepeatpo PreContractCmpId : "+procurementporepeatposavereq.getPreContractCmpId());
		PreContractsCmpEntity preContractsCmpEntity = null;
		preContractsCmpEntity = preContractCmpRepository.getOne(procurementporepeatposavereq.getPreContractCmpId());
		//PreContractCmpId : 425
		System.out.println("preContractsCmpEntity:"+preContractsCmpEntity.toString());
		PreContractsCmpEntity contractsCmpEntity = new PreContractsCmpEntity();
		contractsCmpEntity.setCompanyId(preContractsCmpEntity.getCompanyId());
		PreContractEntity preContractEntity = new PreContractEntity();
		preContractEntity.setId(preContractsCmpEntity.getPreContractEntity().getId());
		contractsCmpEntity.setPreContractEntity(preContractEntity);
		contractsCmpEntity.setStatus(StatusCodes.ACTIVE.getValue());
		contractsCmpEntity.setCmpStatus(WorkFlowStatus.APPROVED.getValue());

		System.out.println("contractsCmpEntity:"+contractsCmpEntity.toString());

		System.out.println("Before Saving into preContractCmpRepository");
		contractsCmpEntity = preContractCmpRepository.save(contractsCmpEntity);
		//repeatPurchaseOrder(contractsCmpEntity, procurementporepeatposavereq);

		PurchaseOrderEntity purchaseOrderEntity = null;

		ProcurementPoRepeatpoTO preContractTO = procurementporepeatposavereq.getProcurementPoRepeatpoTO();
		System.out.println("preContractTO :" + preContractTO.toString());
		//ProcurementPoRepeatpoTO ID 542
		purchaseOrderEntity = PurchaseOrderHandler.populatePurchaseOrderEntityRepeat(procurementporepeatposavereq.getProcurementPoRepeatpoTO(), preContractsCmpEntity, epsRepository, loginRepository);

		purchaseOrderEntity.setParentId(purchaseOrderRepository.findOne(procurementporepeatposavereq.getPurchaseOrderId()));
		System.out.println("purchaseOrderEntity:"+purchaseOrderEntity.toString());
		PurchaseOrderEntity parentPurchaseOrderEntity = purchaseOrderRepository.findOne(procurementporepeatposavereq.getPurchaseOrderId());
		System.out.println("parentPurchaseOrderEntity:"+parentPurchaseOrderEntity.toString());
		if (CommonUtil.isBlankDate(purchaseOrderEntity.getStartDate()))
			purchaseOrderEntity.setStartDate(parentPurchaseOrderEntity.getStartDate());
		if (CommonUtil.isBlankDate(purchaseOrderEntity.getFinsihDate()))
			purchaseOrderEntity.setFinsihDate(parentPurchaseOrderEntity.getFinsihDate());
		purchaseOrderEntity.setAmount(parentPurchaseOrderEntity.getAmount());
		purchaseOrderEntity.setProcureType(parentPurchaseOrderEntity.getProcureType());


		System.out.println("Before Saving into purchaseOrderRepository");
		System.out.println("purchaseOrderEntity:"+purchaseOrderEntity.toString());
		purchaseOrderRepository.save(purchaseOrderEntity);

		System.out.println("CommonUtil.isListHasData(preContractTO.getPreContractEmpDtlTOs()) : "+CommonUtil.isListHasData(preContractTO.getPreContractEmpDtlTOs()));
		if (CommonUtil.isListHasData(preContractTO.getPreContractEmpDtlTOs())) {
			System.out.println(" PreContractEmpDtlTOs size : "+preContractTO.getPreContractEmpDtlTOs().size());
			List<PurchaseOrderRepeatEntity> procurementporepeatpoentity = new ArrayList<>();
			PurchaseOrderRepeatEntity preContractsEmpCmpEntity = null;
			for (PreContractEmpDtlTO preContractEmpDtlTO : preContractTO.getPreContractEmpDtlTOs()) {

				preContractsEmpCmpEntity = ProcurementPoRepeatpoHandler.convertRepeatpoPOJOToEntity(preContractEmpDtlTO,
						true ,parentPurchaseOrderEntity);
				procurementporepeatpoentity.add(preContractsEmpCmpEntity);

				System.out.println("Before Saving into purchaseOrderRepeatRepository");
				System.out.println(procurementporepeatpoentity.toString());
				purchaseOrderRepeatRepository.save(procurementporepeatpoentity);
			}

		}

		System.out.println("CommonUtil.isListHasData(preContractTO.getPreContractPlantDtlTOs()) : "+CommonUtil.isListHasData(preContractTO.getPreContractPlantDtlTOs()));
		if (CommonUtil.isListHasData(preContractTO.getPreContractPlantDtlTOs())) {

			List<PurchaseOrderRepeatEntity> procurementporepeatpoentity = new ArrayList<>();
			PurchaseOrderRepeatEntity preContractsPlantCmpEntity = null;
			for (PreContractPlantDtlTO preContractPlantDtlTO : preContractTO.getPreContractPlantDtlTOs()) {
				//for (PreContractPlantCmpTO preContractPlantCmpTO : preContractPlantDtlTO.getPreContractPlantCmpTOs()) {
				preContractsPlantCmpEntity = ProcurementPoRepeatpoHandler.convertRepeatpoPOJOToEntity(preContractPlantDtlTO, true,parentPurchaseOrderEntity);
				//preContractsPlantCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
				procurementporepeatpoentity.add(preContractsPlantCmpEntity);
				//	}
				System.out.println("Before Saving into procurementporepeatpoentity");
				System.out.println(procurementporepeatpoentity.toString());
				purchaseOrderRepeatRepository.save(procurementporepeatpoentity);
			}

			//purchaseOrderRepeatRepository.save(procurementporepeatpoentity);
		}

		System.out.println("CommonUtil.isListHasData(preContractTO.getPreContractMaterialDtlTOs()) : "+ CommonUtil.isListHasData(preContractTO.getPreContractMaterialDtlTOs()));
		if (CommonUtil.isListHasData(preContractTO.getPreContractMaterialDtlTOs())) {

			List<PurchaseOrderRepeatEntity> procurementporepeatpoentity = new ArrayList<>();
			PurchaseOrderRepeatEntity preContractsMaterialCmpEntity = null;
			for (PreContractMaterialDtlTO preContractMaterialDtlTO : preContractTO.getPreContractMaterialDtlTOs()) {
				//for (PreContractMaterialCmpTO preContractMaterialCmpTO : preContractMaterialDtlTO.getPreContractMaterialCmpTOs()) {
				preContractsMaterialCmpEntity = ProcurementPoRepeatpoHandler.convertRepeatpoPOJOToEntity(preContractMaterialDtlTO, true,parentPurchaseOrderEntity);
				//preContractsMaterialCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
				procurementporepeatpoentity.add(preContractsMaterialCmpEntity);
				//}
				System.out.println("Before Saving into procurementporepeatpoentity");
				System.out.println(procurementporepeatpoentity.toString());
				purchaseOrderRepeatRepository.save(procurementporepeatpoentity);
			}


		}
		System.out.println("CommonUtil.isListHasData(preContractTO.getPreContractServiceDtlTOs()) : "+ CommonUtil.isListHasData(preContractTO.getPreContractServiceDtlTOs()));
		if (CommonUtil.isListHasData(preContractTO.getPreContractServiceDtlTOs())) {

			List<PurchaseOrderRepeatEntity> procurementporepeatpoentity = new ArrayList<>();
			PurchaseOrderRepeatEntity preContractsServiceCmpEntity = null;
			for (PreContractServiceDtlTO preContractServiceDtlTO : preContractTO.getPreContractServiceDtlTOs()) {
				//for (PreContractServiceCmpTO preContractServiceCmpTO : preContractServiceDtlTO
				//			.getPreContractServiceCmpTOs()) {
				preContractsServiceCmpEntity = ProcurementPoRepeatpoHandler.convertRepeatpoPOJOToEntity(preContractServiceDtlTO, true,parentPurchaseOrderEntity);
				//preContractsServiceCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
				procurementporepeatpoentity.add(preContractsServiceCmpEntity);
				//	}
				System.out.println("Before Saving into procurementporepeatpoentity");
				System.out.println(procurementporepeatpoentity.toString());
				purchaseOrderRepeatRepository.save(procurementporepeatpoentity);
			}

			//purchaseOrderRepeatRepository.save(procurementporepeatpoentity);
		}

		System.out.println(" CommonUtil.isListHasData(preContractTO.getPrecontractSowDtlTOs()) : "+CommonUtil.isListHasData(preContractTO.getPrecontractSowDtlTOs()));
		if (CommonUtil.isListHasData(preContractTO.getPrecontractSowDtlTOs())) {

			List<PurchaseOrderRepeatEntity> procurementporepeatpoentity = new ArrayList<>();
			PurchaseOrderRepeatEntity precontractSowCmpEntity = null;
			for (PrecontractSowDtlTO precontractSowDtlTO : preContractTO.getPrecontractSowDtlTOs()) {
				//for (PrecontractSowCmpTO precontractSowCmpTO : precontractSowDtlTO.getPrecontractSowCmpTOs()) {
				precontractSowCmpEntity = ProcurementPoRepeatpoHandler.convertRepeatpoPOJOToEntity(precontractSowDtlTO,
						true,parentPurchaseOrderEntity);
				//precontractSowCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
				procurementporepeatpoentity.add(precontractSowCmpEntity);
				//}
				System.out.println("Before Saving into procurementporepeatpoentity");
				System.out.println(procurementporepeatpoentity.toString());
				purchaseOrderRepeatRepository.save(procurementporepeatpoentity);
			}
		}
		System.out.println("End Of saveprocurementporepeatpo");
	}




	/*public void saveprocurementporepeatpo1(PreContractsCmpEntity preContractsCmpEntity,
			PurchaseOrderRepeatSaveReq purchaseOrderRepeatSaveReq) {

		//PurchaseOrderEntity purchaseOrderEntity = null;
		PreContractTO preContractTO = purchaseOrderRepeatSaveReq.getPreContractTO();
		purchaseOrderEntity = PurchaseOrderHandler.populatePurchaseOrderEntity(
				purchaseOrderRepeatSaveReq.getPreContractTO(), preContractsCmpEntity, epsRepository, loginRepository);
		purchaseOrderEntity
				.setParentId(purchaseOrderRepository.findOne(purchaseOrderRepeatSaveReq.getPurchaseOrderId()));
		PurchaseOrderEntity parentPurchaseOrderEntity = purchaseOrderRepository
				.findOne(purchaseOrderRepeatSaveReq.getPurchaseOrderId());
		if (CommonUtil.isBlankDate(purchaseOrderEntity.getStartDate()))
			purchaseOrderEntity.setStartDate(parentPurchaseOrderEntity.getStartDate());
		if (CommonUtil.isBlankDate(purchaseOrderEntity.getFinsihDate()))
			purchaseOrderEntity.setFinsihDate(parentPurchaseOrderEntity.getFinsihDate());
		purchaseOrderEntity.setAmount(parentPurchaseOrderEntity.getAmount());
		purchaseOrderEntity.setProcureType(parentPurchaseOrderEntity.getProcureType());
		//purchaseOrderRepository.save(purchaseOrderEntity);

		if (CommonUtil.isListHasData(preContractTO.getPreContractEmpDtlTOs())) {

			List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities = new ArrayList<>();
			PreContractsEmpCmpEntity preContractsEmpCmpEntity = null;
			for (PreContractEmpDtlTO preContractEmpDtlTO : preContractTO.getPreContractEmpDtlTOs()) {
				for (PreContractsEmpCmpTO preContractsEmpCmpTO : preContractEmpDtlTO.getPreContractsEmpCmpTOs()) {
					preContractsEmpCmpEntity = PrecontractEmpHandler.convertCmpEmpPOJOToEntity(preContractsEmpCmpTO,
							true);
					preContractsEmpCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
					preContractsEmpCmpEntities.add(preContractsEmpCmpEntity);
				}

			}
			precontractEmpCmpRepository.save(preContractsEmpCmpEntities);
		}

		if (CommonUtil.isListHasData(preContractTO.getPreContractPlantDtlTOs())) {
			List<PreContractsPlantCmpEntity> preContractsPlantCmpEntities = new ArrayList<>();
			PreContractsPlantCmpEntity preContractsPlantCmpEntity = null;
			for (PreContractPlantDtlTO preContractPlantDtlTO : preContractTO.getPreContractPlantDtlTOs()) {
				for (PreContractPlantCmpTO preContractPlantCmpTO : preContractPlantDtlTO.getPreContractPlantCmpTOs()) {
					preContractsPlantCmpEntity = PrecontractPlantHandler
							.convertPlantCmpPOJOToEntity(preContractPlantCmpTO, true);
					preContractsPlantCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
					preContractsPlantCmpEntities.add(preContractsPlantCmpEntity);
				}

			}
			precontractPlantCmpRepository.save(preContractsPlantCmpEntities);
		}

		if (CommonUtil.isListHasData(preContractTO.getPreContractMaterialDtlTOs())) {
			List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities = new ArrayList<>();
			PreContractsMaterialCmpEntity preContractsMaterialCmpEntity = null;
			for (PreContractMaterialDtlTO preContractMaterialDtlTO : preContractTO.getPreContractMaterialDtlTOs()) {
				for (PreContractMaterialCmpTO preContractMaterialCmpTO : preContractMaterialDtlTO
						.getPreContractMaterialCmpTOs()) {
					preContractsMaterialCmpEntity = PrecontractMaterialHandler
							.convertCmpMaterialPOJOToEntity(preContractMaterialCmpTO, true);
					preContractsMaterialCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
					preContractsMaterialCmpEntities.add(preContractsMaterialCmpEntity);
				}
			}
			precontractMaterialCmpRepository.save(preContractsMaterialCmpEntities);
		}
		if (CommonUtil.isListHasData(preContractTO.getPreContractServiceDtlTOs())) {
			List<PreContractsServiceCmpEntity> preContractsServiceCmpEntities = new ArrayList<>();
			PreContractsServiceCmpEntity preContractsServiceCmpEntity = null;
			for (PreContractServiceDtlTO preContractServiceDtlTO : preContractTO.getPreContractServiceDtlTOs()) {
				for (PreContractServiceCmpTO preContractServiceCmpTO : preContractServiceDtlTO
						.getPreContractServiceCmpTOs()) {
					preContractsServiceCmpEntity = PrecontractServicesHandler
							.convertServiceCmpPOJOToEntity(preContractServiceCmpTO, true);
					preContractsServiceCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
					preContractsServiceCmpEntities.add(preContractsServiceCmpEntity);
				}
				precontractServiceCmpRepository.save(preContractsServiceCmpEntities);
			}
		}
		if (CommonUtil.isListHasData(preContractTO.getPrecontractSowDtlTOs())) {
			List<PrecontractSowCmpEntity> precontractSowCmpEntities = new ArrayList<>();
			PrecontractSowCmpEntity precontractSowCmpEntity = null;
			for (PrecontractSowDtlTO precontractSowDtlTO : preContractTO.getPrecontractSowDtlTOs()) {
				for (PrecontractSowCmpTO precontractSowCmpTO : precontractSowDtlTO.getPrecontractSowCmpTOs()) {
					precontractSowCmpEntity = PrecontractSowDtlHandler.convertSowCmpPOJOToEntity(precontractSowCmpTO,
							true);
					precontractSowCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
					precontractSowCmpEntities.add(precontractSowCmpEntity);
				}
				precontractSowCmpRepository.save(precontractSowCmpEntities);
			}
		}*/
	//}

	public void deleteCompanyForRepeatPurchaseOrder(Long preContractCmpId) {
		preContractCmpRepository.delete(preContractCmpId);
	}

	public void savePreContranctsList(PreContractListSaveReq preContractListSaveReq) {
		precontractRepository.save(PrecontractHandler.convertPOJOToEntity(preContractListSaveReq.getPreContractTOs(),
				loginRepository, epsRepository));
	}

	public LabelKeyTO getProjSettingsForProcurement(ProcurementGetReq procurementGetReq) {
		LabelKeyTO labelKeyTO = new LabelKeyTO();
		labelKeyTO.setName(projGeneralRepositoryCopy.getCurrencyForProject(procurementGetReq.getProjId()));
		return labelKeyTO;
	}

	public LabelKeyTOResp getPreContarctCostCodeSummary(ProcurementGetReq procurementGetReq) {
		Long precontractId = procurementGetReq.getContractId();
		String contractType = procurementGetReq.getContractType();		
		Map<Long, Double> costSummaryMap = new HashMap<>();
		if(contractType.equals(CommonConstants.PRE_CONTRACT_MANPOWER)) {
			List<Object[]> manpowerCost = precontractEmpRepository.getManpowerCostSummary(precontractId);
			addCostSummary(manpowerCost, costSummaryMap);
		}
		if(contractType.equals(CommonConstants.PRE_CONTRACT_MATERIALS)) {
			List<Object[]> materialCost = precontractMaterialRepository.getMaterialCostSummary(precontractId);
			addCostSummary(materialCost, costSummaryMap);
		}
		if(contractType.equals(CommonConstants.PRE_CONTRACT_PLANTS)) {
			List<Object[]> plantCost = precontractPlantRepository.getPlantCostSummary(precontractId);
			addCostSummary(plantCost, costSummaryMap);
		}
		if(contractType.equals(CommonConstants.PRE_CONTRACT_SERVICES)) {
			List<Object[]> serviceCost = precontractServiceRepository.getServiceCostSummary(precontractId);
			addCostSummary(serviceCost, costSummaryMap);
		}
		if(contractType.equals(CommonConstants.PRE_CONTRACT_PROJECT_SUB_CONTRACT)) {
			List<Object[]> sowCost = precontractSowRepository.getSowCostSummary(precontractId);
			addCostSummary(sowCost, costSummaryMap);
		}
		LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
		Set<Long> costIds = costSummaryMap.keySet();
		if (procurementGetReq.getCostIds() != null) {
			if (!costIds.isEmpty()) {
			List<Object[]> finalCostSummary = projCostStatementsRepositoryCopy.getPrecontractCostCodeSummary(costIds);
			LabelKeyTO labelKeyTO;
			Long costId;
			List<LabelKeyTO> finalList = new ArrayList<>();
			List<Long> cost = new ArrayList<>();
			cost.addAll(procurementGetReq.getCostIds());
			for (Object[] costSummary : finalCostSummary) {
				if(String.valueOf(((Long) costSummary[0])).equalsIgnoreCase(String.valueOf(cost.get(0)))) {
				System.out.println(procurementGetReq.getCostIds());
				labelKeyTO = new LabelKeyTO();
				costId = (Long) costSummary[0];
				labelKeyTO.setId(costId);
				labelKeyTO.setCode((String) costSummary[1]);
				labelKeyTO.setName((String) costSummary[2]);
				labelKeyTO.getDisplayNamesMap().put(CommonConstants.ESTIMATE_AMOUNT,
						String.valueOf(((BigDecimal) costSummary[3]).doubleValue()));
				labelKeyTO.getDisplayNamesMap().put(CommonConstants.BUDGET_AMOUNT,
						String.valueOf(costSummaryMap.get(costId)));
				finalList.add(labelKeyTO);
			}
			}

			labelKeyTOResp.setLabelKeyTOs(finalList);
			}
			return labelKeyTOResp;
		}
		if (!costIds.isEmpty()) {
			List<Object[]> finalCostSummary = projCostStatementsRepositoryCopy.getPrecontractCostCodeSummary(costIds);
			LabelKeyTO labelKeyTO;
			Long costId;
			List<LabelKeyTO> finalList = new ArrayList<>();
			for (Object[] costSummary : finalCostSummary) {
				labelKeyTO = new LabelKeyTO();
				costId = (Long) costSummary[0];
				labelKeyTO.setId(costId);
				labelKeyTO.setCode((String) costSummary[1]);
				labelKeyTO.setName((String) costSummary[2]);
				labelKeyTO.getDisplayNamesMap().put(CommonConstants.ESTIMATE_AMOUNT,
						String.valueOf(((BigDecimal) costSummary[3]).doubleValue()));
				labelKeyTO.getDisplayNamesMap().put(CommonConstants.BUDGET_AMOUNT,
						String.valueOf(costSummaryMap.get(costId)));
				finalList.add(labelKeyTO);
			}

			labelKeyTOResp.setLabelKeyTOs(finalList);
		}

		return labelKeyTOResp;
	}

	public void deactivateRfqCompanies(PreContractCmpDelReq preContractCmpDelReq) {
		preContractCmpRepository.deactivateCompanies(preContractCmpDelReq.getPreContractCmpIds(),
				preContractCmpDelReq.getStatus());
	}

	public PurchaseOrderEntity getRepeatPOAmountDates(Long purchaseOrderEntityId)
	{
		PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();

		List<PurchaseOrderRepeatEntity> purchaseOrderRepeatEntities = purchaseOrderRepeatRepository.findPurchaseOrderRepeatByPO(purchaseOrderEntityId);
		System.out.println("Fetching PO Amount,Start and Finish Date for PO :"+purchaseOrderEntityId+" : Size:" + purchaseOrderRepeatEntities.size());
		HashMap<String,String> itemsMap = new HashMap<String,String>();

		if(purchaseOrderRepeatEntities!=null && purchaseOrderRepeatEntities.size()>0)
		{
			List<Date> repeatPOItemDates = new ArrayList<Date>();
			List<LocalDate> repeatPOItemLocalDates = new ArrayList<LocalDate>();
			double amount = 0;
			double total = 0;
			for (PurchaseOrderRepeatEntity repeatPOEntity : purchaseOrderRepeatEntities) {
				if(repeatPOEntity.getStartDate()!=null)
				{
					repeatPOItemDates.add(repeatPOEntity.getStartDate());
					repeatPOItemLocalDates.add(Instant.ofEpochMilli(repeatPOEntity.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				}
				if(repeatPOEntity.getFinishDate()!=null)
				{
					repeatPOItemDates.add(repeatPOEntity.getFinishDate());
					repeatPOItemLocalDates.add(Instant.ofEpochMilli(repeatPOEntity.getFinishDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				}

				if(repeatPOEntity.getContractItemRate() != null && repeatPOEntity.getQuantity() != null)
				{
					total = total + (repeatPOEntity.getContractItemRate().doubleValue()*repeatPOEntity.getQuantity().doubleValue());
					System.out.println("amount : "+amount);
					if(repeatPOEntity.getContractItemType()!=null && repeatPOEntity.getContractItemType().length()>0)
					{
						itemsMap.put(repeatPOEntity.getContractItemType(),repeatPOEntity.getContractItemType());
					}
				}

			}
			int itemsCount = itemsMap.keySet().size();
			System.out.println("total : "+total);
			System.out.println("Items Size  : "+itemsCount+": Items : "+itemsMap.keySet());
			amount = total/itemsCount;
			System.out.println("amount : "+amount);
			purchaseOrderEntity.setAmount(new BigDecimal(amount, MathContext.DECIMAL64));
			System.out.println("purchaseOrderEntity Amount : "+purchaseOrderEntity.getAmount());

			System.out.println("repeatPOItemDates Size: "+repeatPOItemDates.size());
			System.out.println("repeatPOItemLocalDates Size: "+repeatPOItemLocalDates.size());
			// https://howtodoinjava.com/java/date-time/localdate-to-date/
			LocalDate maxDate = repeatPOItemLocalDates.stream().max( Comparator.comparing( LocalDate::toEpochDay )).get();
			LocalDate minDate = repeatPOItemLocalDates.stream().min( Comparator.comparing( LocalDate::toEpochDay )).get();
			System.out.println("maxDate = " + maxDate);
			System.out.println("minDate = " + minDate);

			Date startDate = Date.from(minDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date finishDate = Date.from(maxDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

			System.out.println("startDate = " + startDate);
			System.out.println("finishDate = " + finishDate);

			purchaseOrderEntity.setStartDate(startDate);
			purchaseOrderEntity.setFinsihDate(finishDate);
		}

		return purchaseOrderEntity;
	}


	public PurchaseOrderEntity repeatPOfromParentPO(PurchaseOrderEntity parentPurchaseOrderEntity)
	{
		PurchaseOrderEntity repeatPurchaseOrderEntity = new PurchaseOrderEntity();
		System.out.println("Copying Data repeatPOfromParentPO");
		PurchaseOrderEntity repeatPOEntity = getRepeatPOAmountDates(parentPurchaseOrderEntity.getId());
		//repeatPurchaseOrderEntity.setId(parentPurchaseOrderEntity.getId());
		repeatPurchaseOrderEntity.setParentId(parentPurchaseOrderEntity);
		repeatPurchaseOrderEntity.setProjId(parentPurchaseOrderEntity.getProjId());
		repeatPurchaseOrderEntity.setApprUserId(parentPurchaseOrderEntity.getApprUserId());
		repeatPurchaseOrderEntity.setStartDate(repeatPOEntity.getStartDate());
		repeatPurchaseOrderEntity.setFinsihDate(repeatPOEntity.getFinsihDate());
		repeatPurchaseOrderEntity.setPaymentInDays(parentPurchaseOrderEntity.getPaymentInDays());
		repeatPurchaseOrderEntity.setAmount(repeatPOEntity.getAmount());
		repeatPurchaseOrderEntity.setStatus(parentPurchaseOrderEntity.getStatus());
		repeatPurchaseOrderEntity.setCreatedBy(parentPurchaseOrderEntity.getCreatedBy());
		repeatPurchaseOrderEntity.setCreatedOn(parentPurchaseOrderEntity.getCreatedOn());
		repeatPurchaseOrderEntity.setUpdatedBy(parentPurchaseOrderEntity.getUpdatedBy());
		repeatPurchaseOrderEntity.setUpdatedOn(parentPurchaseOrderEntity.getUpdatedOn());
		repeatPurchaseOrderEntity.setPreContractsCmpEntity(parentPurchaseOrderEntity.getPreContractsCmpEntity());
		repeatPurchaseOrderEntity.setProcureType(parentPurchaseOrderEntity.getProcureType());
		repeatPurchaseOrderEntity.setCompleteProcureType(parentPurchaseOrderEntity.getCompleteProcureType());
		repeatPurchaseOrderEntity.setPoDetailsEntity(parentPurchaseOrderEntity.getPoDetailsEntity());
		repeatPurchaseOrderEntity.setDelivered(parentPurchaseOrderEntity.getDelivered());

		return repeatPurchaseOrderEntity;
	}

	// This is for Updating Generated Purchase ID
	public void updateGeneratedRepeatPO(Long repeatPOId,Long repeatParentPOId){
		System.out.println("updateGeneratedRepeatPO");

		List<PurchaseOrderRepeatEntity> purchaseOrderRepeatEntities = purchaseOrderRepeatRepository.findPurchaseOrderRepeatByPO(repeatParentPOId);
		System.out.println("updateGeneratedRepeatPO :"+purchaseOrderRepeatEntities+" : Size:" + purchaseOrderRepeatEntities.size());
		if(purchaseOrderRepeatEntities!=null && purchaseOrderRepeatEntities.size()>0)
		{
			for (PurchaseOrderRepeatEntity purchaseOrderRepeatEntity : purchaseOrderRepeatEntities) {
				purchaseOrderRepeatEntity.setRepeatPOId(repeatPOId);
			}
		}

	}

	public PurchaseOrderResp saveSinglePurchaseOrder(SinglePurchaseOrderSaveReq singlePurchaseOrderSaveReq) {
		System.out.println("** Started : ProcurementServiceImpl:saveSinglePurchaseOrder");

		System.out.println("ClientId: "+singlePurchaseOrderSaveReq.getClientId());
		System.out.println("ProjId: "+singlePurchaseOrderSaveReq.getProjId());
		System.out.println("PurchaseOrderId: "+singlePurchaseOrderSaveReq.getPurchaseOrderId());
		System.out.println("ContractId: "+singlePurchaseOrderSaveReq.getContractId());

		System.out.println("TypeOfPO: "+singlePurchaseOrderSaveReq.getTypeOfPO());
		System.out.println(" SinglePurchaseOrderTOs size : "+singlePurchaseOrderSaveReq.getSinglePurchaseOrderTOs().size());
		//System.out.println(" PoDetailsTO Id : "+singlePurchaseOrderSaveReq.getPoDetailsTO().getId());
		System.out.println(" ProjId: "+singlePurchaseOrderSaveReq.getProjId());

		PurchaseOrderResp purchaseOrderResp = new PurchaseOrderResp();
		if(singlePurchaseOrderSaveReq.getTypeOfPO()!=null && singlePurchaseOrderSaveReq.getTypeOfPO().equalsIgnoreCase("RepeatPO"))
		{
			// for RepeatPO Logic
			PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(singlePurchaseOrderSaveReq.getPurchaseOrderId());
			Long parentPOId = purchaseOrderEntity.getId();
			System.out.println("Parent purchaseOrderEntity : "+parentPOId);

			/*PurchaseOrderEntity repeatPOEntity = purchaseOrderEntity;
			repeatPOEntity.setId(null);
			repeatPOEntity.setParentId(purchaseOrderEntity);*/

			PurchaseOrderEntity repeatPOEntity = repeatPOfromParentPO(purchaseOrderEntity);
			System.out.println("repeatPOEntity  : "+repeatPOEntity.getId());
			System.out.println("repeatPOEntity : "+repeatPOEntity.getParentId());

			List<PurchaseOrderEntity> purchaseOrderEntities = new ArrayList<PurchaseOrderEntity>();
			purchaseOrderEntities.add(repeatPOEntity);

			List<PurchaseOrderEntity> poList = purchaseOrderRepository.save(purchaseOrderEntities);

			System.out.println(" poList Size : "+poList.size());
			for (PurchaseOrderEntity rptPurchaseOrderEntity : poList) {
				System.out.println(" *** rptPurchaseOrderEntity Id : "+rptPurchaseOrderEntity.getId());
				// for updating to RepeatPO
				if(rptPurchaseOrderEntity.getId()!=null)
				{
					updateGeneratedRepeatPO(rptPurchaseOrderEntity.getId(),singlePurchaseOrderSaveReq.getPurchaseOrderId());
				}
				purchaseOrderResp.getPurchaseOrderTOs().add(PurchaseOrderHandler.convertEntityToPOJO(rptPurchaseOrderEntity));
			}
			System.out.println("** Repeat Ended : ProcurementServiceImpl:saveSinglePurchaseOrder : Size : "+purchaseOrderResp.getPurchaseOrderTOs().size());
		}else{
			PreContractEntity preContractEntity = precontractRepository
					.findOne(singlePurchaseOrderSaveReq.getPreContractTO().getId());

			List<PurchaseOrderEntity> purchaseOrderEntities = PurchaseOrderHandler.createPurchaseOrder(
					singlePurchaseOrderSaveReq.getPreContractTO(), singlePurchaseOrderSaveReq.getApprovedCompanyMap(),
					preContractCmpRepository, singlePurchaseOrderSaveReq.getPoDetailsTO(), poDetailsRepository,
					singlePurchaseOrderSaveReq.getPoStartDate(), singlePurchaseOrderSaveReq.getPoFinishtDate(),
					singlePurchaseOrderSaveReq.getPoProcureType(), epsRepository, loginRepository);
			List<PurchaseOrderEntity> poList = purchaseOrderRepository.save(purchaseOrderEntities);

			preContractEntity.setContarctStageStatus(ProcurmentStageStatus.PO_ORDER.getDesc());
			preContractEntity.setPurchaseOrderStatus(ProcurmentStageStatus.PO_ISSUED.getDesc());


			for (PurchaseOrderEntity purchaseOrderEntity : poList) {
				purchaseOrderResp.getPurchaseOrderTOs().add(PurchaseOrderHandler.convertEntityToPOJO(purchaseOrderEntity));
			}
		}

		System.out.println("** Ended : ProcurementServiceImpl:saveSinglePurchaseOrder : Size : "+purchaseOrderResp.getPurchaseOrderTOs().size());
		return purchaseOrderResp;
	}

	public void saveTransmittalMsg(DocumentTransmittalMsgReq documentTransmittalMsgReq) {
		documentTransmittalMsgRepository.save(PrecontractHandler.convertTransmittalPOJOToEntity(
				documentTransmittalMsgReq.getDocumentTransmittalTO(), epsRepository, precontractRepository));
		sendEmail(documentTransmittalMsgReq.getDocumentTransmittalTO());
	}
	
	private void sendEmail(DocumentTransmittalTO documentTransmittalTO) {
		if(documentTransmittalTO.getAcceptedBy() != null) {
        String toEmail = documentTransmittalTO.getAcceptedBy();
        String ccEmail = "";
        
        String toSubject = "Tender Documents";
        String text = "<html><body><p>This is to inform  you that  we had   transmitted  the following documents  to your nominated Email address  in  relation to  RFQ / Bidding. \r\n"
        		+ "Please acknowledge receipt of those  documents.</body></html>";
        commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
		}
    }
	
	public void savePurchaseOrderDetails(PurchaseOrderDetailsTO purchaseOrderDetailsTO) {
		PurchaseOrderDetailsEntity poDetailsEntity = poDetailsRepository
				.save(PurchaseOrderDetailsHandler.convertPOJOToEntity(purchaseOrderDetailsTO, poDetailsRepository));
		if (purchaseOrderDetailsTO.getPurchaseOrderId() != null) {
			PurchaseOrderEntity po = purchaseOrderRepository.findOne(purchaseOrderDetailsTO.getPurchaseOrderId());
			if (po != null) {
				po.setPoDetailsEntity(poDetailsEntity);
			}
		}
	}

	public LabelKeyTOResp getProjSettingsProcurementDetails(POProcureTypeReq procureTypeReq) {
		LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
		if (CommonUtil.isNonBlankLong(procureTypeReq.getProjId())
				&& CommonUtil.isNotBlankStr(procureTypeReq.getApprStatus())) {
			/*
			 * labelKeyTOs =
			 * settingsProcurmentProcRepository.getProSettingsProurementDetails(
			 * procureTypeReq.getProjId(), procureTypeReq.getPurId(),
			 * procureTypeReq.getProcureType(), procureTypeReq.getApprStatus());
			 * labelKeyTOResp.getLabelKeyTOs().addAll(labelKeyTOs);
			 */

		}
		return labelKeyTOResp;
	}

	@Transactional(readOnly = true)
	public PurchaseOrderResp getPurchaseOrdersByPrecontractCmpIdAndProjId(PurchaseOrderGetReq purchaseOrderGetReq) {
		List<PurchaseOrderEntity> purchaseOrderEntities = purchaseOrderRepository.findPOByPrecontractCmpIdAndProjId(
				purchaseOrderGetReq.getProjId(), purchaseOrderGetReq.getPrecontractCmpIds(),
				StatusCodes.ACTIVE.getValue());
		PurchaseOrderResp purchaseOrderResp = new PurchaseOrderResp();
		for (PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
			purchaseOrderResp.getPurchaseOrderTOs().add(PurchaseOrderHandler.convertEntityToPOJO(purchaseOrderEntity));
		}
		return purchaseOrderResp;
	}

	public TermsAndConditionsResp saveTermsAndConditions(SaveTermsAndConditionsReq saveTermsAndConditionsReq) {

		TermsAndConditionsTO termsAndConditionsTO = saveTermsAndConditionsReq.getTermsAndConditionsTO();
		TermsAndConditionsEntity termsAndConditionsEntity = termsAndConditionsHandler
				.convertPOJOToEntity(termsAndConditionsTO);
		termsAndConditionsRepository.save(termsAndConditionsEntity);

		GetTermsAndConditionsReq getTermsAndConditionsReq = new GetTermsAndConditionsReq();
		if (termsAndConditionsEntity.getPurchaseOrderEntity() != null
				&& CommonUtil.isNonBlankLong(termsAndConditionsEntity.getPurchaseOrderEntity().getId())) {
			getTermsAndConditionsReq.setPoId(termsAndConditionsEntity.getPurchaseOrderEntity().getId());
		} else if (termsAndConditionsEntity.getPreContractEntity() != null
				&& CommonUtil.isNonBlankLong(termsAndConditionsEntity.getPreContractEntity().getId())) {
			getTermsAndConditionsReq.setPreContractId(termsAndConditionsEntity.getPreContractEntity().getId());
		}
		return getTermsAndConditions(getTermsAndConditionsReq);
	}

	public TermsAndConditionsResp getTermsAndConditions(GetTermsAndConditionsReq getTermsAndConditionsReq) {
		List<TermsAndConditionsEntity> termsAndConditionsEntities = new ArrayList<>();
		if (CommonUtil.isNonBlankLong(getTermsAndConditionsReq.getPoId())) {
			termsAndConditionsEntities = termsAndConditionsRepository.findByPoId(getTermsAndConditionsReq.getPoId());
		} else if (CommonUtil.isNonBlankLong(getTermsAndConditionsReq.getPreContractId())) {
			termsAndConditionsEntities = termsAndConditionsRepository
					.findByPrecontractId(getTermsAndConditionsReq.getPreContractId());
		}
		TermsAndConditionsResp response = new TermsAndConditionsResp();
		response.setTermsAndConditionsTOs(termsAndConditionsHandler.convertEntitiesToPOJO(termsAndConditionsEntities));
		return response;
	}

	private void addCostSummary(List<Object[]> cost, Map<Long, Double> costMap) {
		Long key;
		for (Object[] array : cost) {

			key = (Long) array[0];
			Double value = Double.valueOf(((BigDecimal) array[1]).doubleValue());
			if (costMap.containsKey(key)) {
				costMap.put(key, Double.valueOf(Double.sum(costMap.get(key), value)));
			} else {
				costMap.put(key, value);
			}
		}
	}
	
	public ProjDocFileTO downloadProcurementDocs( Long recordId, String category ) throws IOException {
		ProjDocFileTO projDocFileTO = new ProjDocFileTO();
		int status = 1;
		ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
        byte[] fileBytes = null;      
        System.out.println("downloadProcurementDocs from ProcurementServiceImpl:"+recordId+" category:"+category);
        
    	if( category.equals("PreContract List-Reference Documents") || category.equals("PreContract List-Reference Documents") )
        {
        	PreContractDocEntity preContractEntity = precontractDocRepository.findOne( recordId );
        	projDocFileEntity = preContractEntity.getProjDocFileEntity();
        }
        System.out.println(projDocFileEntity);
        String folder_path = ApplicationConstants.FILE_DIRECTORY + "/" + projDocFileEntity.getFolderId().getUploadFolder() + projDocFileEntity.getFolderPath() + "/" + projDocFileEntity.getName();
        System.out.println("folder path:");
        System.out.println(folder_path);
        projDocFileTO.setFileType( projDocFileEntity.getFileType() );   
        projDocFileTO.setFolderPath( folder_path );
        projDocFileTO.setName( projDocFileEntity.getName() );
        projDocFileTO.setFileSize( projDocFileEntity.getFileSize() );
        System.out.println(projDocFileTO);
        return projDocFileTO;
    }
	
	public InvoiceMaterialResp getInvoiceMaterials(InvoiceMaterialGetReq invoiceMaterialGetReq) {
		
		List<InvoiceMaterialTo> invoiceMaterialTos = new ArrayList<InvoiceMaterialTo>();
		List<MaterialPODeliveryDocketEntityCopy> materialPODeliveryDocketEntityCopies = 
				copyMaterialDeliveryDocketRepository.getInvoiceMaterials(CommonUtil.convertStringToDate(invoiceMaterialGetReq.getInvoceFromDate()),CommonUtil.convertStringToDate(invoiceMaterialGetReq.getInvoceToDate()),invoiceMaterialGetReq.getPurchaseId());
		List<WorkDairyMaterialDtlEntityCopy> workDairyMaterialDtlEntityCopies = 
				materialWorkDairyRepositoryCopy.getinvoiceMaterialDetails(CommonUtil.convertStringToDate(invoiceMaterialGetReq.getInvoceFromDate()),CommonUtil.convertStringToDate(invoiceMaterialGetReq.getInvoceToDate()),invoiceMaterialGetReq.getPurchaseId(),true);
		Integer totaValue = 0;
		for(MaterialPODeliveryDocketEntityCopy materialPODeliveryDocketEntityCopy: materialPODeliveryDocketEntityCopies) {
			InvoiceMaterialTo invoiceMaterialTo = new InvoiceMaterialTo();
			invoiceMaterialTo.setDocketNum(materialPODeliveryDocketEntityCopy.getDocketNumber());
			invoiceMaterialTo.setDocketDate(CommonUtil.getStrFromDateDDMMYYYY(materialPODeliveryDocketEntityCopy.getDocketDate()));
			invoiceMaterialTo.setMatReceiveDate(CommonUtil.getStrFromDateDDMMYYYY(materialPODeliveryDocketEntityCopy.getSupplyDeliveryDate()));
			invoiceMaterialTo.setSchId(materialPODeliveryDocketEntityCopy.getMaterialProjDtlEntityCopy().getPreContractMaterialSchCode());
			invoiceMaterialTo.setSchDesc(materialPODeliveryDocketEntityCopy.getMaterialProjDtlEntityCopy().getPreContractMaterialSchDesc());
			invoiceMaterialTo.setMatClassItem(materialPODeliveryDocketEntityCopy.getMaterialProjDtlEntityCopy().getMaterialClassId().getName());
			invoiceMaterialTo.setUntiOfMeasure(materialPODeliveryDocketEntityCopy.getMaterialProjDtlEntityCopy().getMaterialClassId().getMeasurmentMstrEntity().getName());
			invoiceMaterialTo.setReceiverComments(materialPODeliveryDocketEntityCopy.getComments());
			invoiceMaterialTo.setProgressQty(materialPODeliveryDocketEntityCopy.getReceivedQty().intValue());
			Integer rateValue = 0;
			for (PreContractsMaterialCmpEntity preContractsMaterialCmpEntity : materialPODeliveryDocketEntityCopy
					.getMaterialProjDtlEntityCopy().getPreContractMterialId().getPreContractsMaterialCmpEntities()) {
				if (preContractsMaterialCmpEntity.getPreContractsCmpEntity().getCompanyId()
						.getId().equals(invoiceMaterialGetReq.getCompanyId())) {
					invoiceMaterialTo.setContractQty(preContractsMaterialCmpEntity.getQuantity());
					invoiceMaterialTo.setVendorRate(preContractsMaterialCmpEntity.getRate().intValue());
					rateValue = preContractsMaterialCmpEntity.getRate().intValue();
				}
			}
			totaValue = totaValue + (materialPODeliveryDocketEntityCopy.getReceivedQty().intValue() * rateValue);
			Integer claimedQty = 0;
			invoiceMaterialTo.setClaimedQty(claimedQty);
			invoiceMaterialTos.add(invoiceMaterialTo);
		}
		/*
		 * for(WorkDairyMaterialDtlEntityCopy workDairyMaterialDtlEntityCopy:
		 * workDairyMaterialDtlEntityCopies) {
		 * 
		 * InvoiceMaterialTo invoiceMaterialTo = new InvoiceMaterialTo();
		 * invoiceMaterialTo.setDocketNum(workDairyMaterialDtlEntityCopy.getDocketNum())
		 * ; invoiceMaterialTo.setDocketDate(CommonUtil.getStrFromDateDDMMYYYY(
		 * workDairyMaterialDtlEntityCopy.getDocketDate()));
		 * invoiceMaterialTo.setMatReceiveDate(CommonUtil.getStrFromDateDDMMYYYY(
		 * workDairyMaterialDtlEntityCopy.getDocketDate()));
		 * invoiceMaterialTo.setSchId(workDairyMaterialDtlEntityCopy.getProjDocketSchId(
		 * ).getMaterialProjDtlEntity().getPreContractMaterialSchCode());
		 * invoiceMaterialTo.setSchDesc(workDairyMaterialDtlEntityCopy.
		 * getProjDocketSchId().getMaterialProjDtlEntity().getPreContractMaterialSchDesc
		 * ()); invoiceMaterialTo.setMatClassItem(workDairyMaterialDtlEntityCopy.
		 * getProjDocketSchId().getMaterialProjDtlEntity().getMaterialClassId().getName(
		 * )); invoiceMaterialTo.setUntiOfMeasure(workDairyMaterialDtlEntityCopy.
		 * getProjDocketSchId().getMaterialProjDtlEntity().getMaterialClassId().
		 * getMeasurmentMstrEntity().getName());
		 * invoiceMaterialTo.setReceiverComments(workDairyMaterialDtlEntityCopy.
		 * getComments());
		 * invoiceMaterialTo.setProgressQty(workDairyMaterialDtlEntityCopy.
		 * getReceivedQty().intValue()); Integer rateValue = 0; for
		 * (PreContractsMaterialCmpEntity preContractsMaterialCmpEntity :
		 * workDairyMaterialDtlEntityCopy.getProjDocketSchId()
		 * .getMaterialProjDtlEntity().getPreContractMterialId().
		 * getPreContractsMaterialCmpEntities()) { if
		 * (preContractsMaterialCmpEntity.getPreContractsCmpEntity().getCompanyId()
		 * .getId().equals(invoiceMaterialGetReq.getCompanyId())) {
		 * invoiceMaterialTo.setContractQty(preContractsMaterialCmpEntity.getQuantity())
		 * ; invoiceMaterialTo.setVendorRate(preContractsMaterialCmpEntity.getRate().
		 * intValue()); rateValue = preContractsMaterialCmpEntity.getRate().intValue();
		 * } } totaValue = totaValue +
		 * (workDairyMaterialDtlEntityCopy.getReceivedQty().intValue() * rateValue);
		 * Integer claimedQty = 0; invoiceMaterialTo.setClaimedQty(claimedQty);
		 * 
		 * invoiceMaterialTos.add(invoiceMaterialTo);
		 * 
		 * }
		 */
		System.out.println("entered hertryhfjdgytrsefgve999   " + invoiceMaterialTos.size());
		InvoiceMaterialResp invoiceMaterialResp = new InvoiceMaterialResp();
		invoiceMaterialResp.setTotalValue(totaValue);
		invoiceMaterialResp.setInvoiceMaterialTos(invoiceMaterialTos);
		
		return invoiceMaterialResp;
		
	}
	
	@Override
	public ProcurementSubCatResp getProcurementSubCatList(ProcurementSubCatReq procuementCat) {
		ProcurementSubCatResp proCatResp = new ProcurementSubCatResp();
		System.out.println("Started getProcurementSubCatList in ProcurementServiceImpl with Procurement Cat type =" + procuementCat.getProcurementType());
		
		List<ProcureCatgDtlEntity> procurementSubCatList = procureCatgRepository.findProcurementsByType(procuementCat.getProcurementType().trim());
		List<ProcurementSubCategory> listOfProSubCat = new ArrayList<ProcurementSubCategory>();
		if(!CollectionUtils.isEmpty(procurementSubCatList)) {
			for(ProcureCatgDtlEntity procurementSubCat : procurementSubCatList) {
				ProcurementSubCategory proSubCat = new ProcurementSubCategory();
				proSubCat.setProcCatId(procurementSubCat.getId());
				proSubCat.setProcurSubCatCode(procurementSubCat.getCode());
				proSubCat.setProcurementType(procurementSubCat.getProcureType());
				proSubCat.setProcurSubCatName(procurementSubCat.getName());
				proSubCat.setStatus(procurementSubCat.getStatus()==1 ? "Active" : "InActive");
				listOfProSubCat.add(proSubCat);
				}
			proCatResp.setProcurementSubCatList(listOfProSubCat);
			//proCatResp.setStatus(procuementCat);
		}
		
		return proCatResp;
	}

	@Override
	public InvoiceMaterialResp searchInvoiceMaterialsByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
		// TODO Auto-generated method stub
	
			List<InvoiceMaterialTo> invoiceMaterialTos = new ArrayList<InvoiceMaterialTo>();
			List<MaterialPODeliveryDocketEntityCopy> materialPODeliveryDocketEntityCopies =
					copyMaterialDeliveryDocketRepository.searchInvoiceMaterialsByPCName(CommonUtil.convertStringToDate(searchInvoiceMaterialsReq.getInvoceFromDate()),CommonUtil.convertStringToDate(searchInvoiceMaterialsReq.getInvoceToDate()),searchInvoiceMaterialsReq.getPurchaseId(),
												searchInvoiceMaterialsReq.getPcName());//,searchInvoiceMaterialsReq.getPocSubCatName(),searchInvoiceMaterialsReq.getPayableCat(),searchInvoiceMaterialsReq.getUnitsOfMeasure());
			Integer totaValue = 0;
			for(MaterialPODeliveryDocketEntityCopy materialPODeliveryDocketEntityCopy: materialPODeliveryDocketEntityCopies) {
				InvoiceMaterialTo invoiceMaterialTo = new InvoiceMaterialTo();
				invoiceMaterialTo.setDocketNum(materialPODeliveryDocketEntityCopy.getDocketNumber());
				invoiceMaterialTo.setDocketDate(CommonUtil.getStrFromDateDDMMYYYY(materialPODeliveryDocketEntityCopy.getDocketDate()));
				invoiceMaterialTo.setMatReceiveDate(CommonUtil.getStrFromDateDDMMYYYY(materialPODeliveryDocketEntityCopy.getSupplyDeliveryDate()));
				invoiceMaterialTo.setSchId(materialPODeliveryDocketEntityCopy.getMaterialProjDtlEntityCopy().getPreContractMaterialSchCode());
				invoiceMaterialTo.setSchDesc(materialPODeliveryDocketEntityCopy.getMaterialProjDtlEntityCopy().getPreContractMaterialSchDesc());
				invoiceMaterialTo.setMatClassItem(materialPODeliveryDocketEntityCopy.getMaterialProjDtlEntityCopy().getMaterialClassId().getName());
				invoiceMaterialTo.setUntiOfMeasure(materialPODeliveryDocketEntityCopy.getMaterialProjDtlEntityCopy().getMaterialClassId().getMeasurmentMstrEntity().getName());
				invoiceMaterialTo.setReceiverComments(materialPODeliveryDocketEntityCopy.getComments());
				invoiceMaterialTo.setProgressQty(materialPODeliveryDocketEntityCopy.getReceivedQty().intValue());
				
				Integer rateValue = 0;
				for (PreContractsMaterialCmpEntity preContractsMaterialCmpEntity : materialPODeliveryDocketEntityCopy
						.getMaterialProjDtlEntityCopy().getPreContractMterialId().getPreContractsMaterialCmpEntities()) {
					if (preContractsMaterialCmpEntity.getPreContractsCmpEntity().getCompanyId()
							.getId().equals(searchInvoiceMaterialsReq.getCompanyId())) {
						invoiceMaterialTo.setContractQty(preContractsMaterialCmpEntity.getQuantity());
						invoiceMaterialTo.setVendorRate(preContractsMaterialCmpEntity.getRate().intValue());
						rateValue = preContractsMaterialCmpEntity.getRate().intValue();
					}
				}
				totaValue = totaValue + (materialPODeliveryDocketEntityCopy.getReceivedQty().intValue() * rateValue);
				Integer claimedQty = 0;
				invoiceMaterialTo.setClaimedQty(claimedQty);
				invoiceMaterialTos.add(invoiceMaterialTo);
			}
			
			System.out.println("Final Result for search invoice Materials size =   " + invoiceMaterialTos.size());
			InvoiceMaterialResp invoiceMaterialResp = new InvoiceMaterialResp();
			invoiceMaterialResp.setTotalValue(totaValue);
			invoiceMaterialResp.setInvoiceMaterialTos(invoiceMaterialTos);
			
			return invoiceMaterialResp;
		
	}

	@Override
	public SearchManpowerResp searchInvoiceManpowerByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
		// TODO Auto-generated method stub
		SearchManpowerResp searchManpowerResp = new SearchManpowerResp();
		List<SearchManpowerDocketTO> seDocketTOs = new ArrayList<>();
		List<PreContractsEmpDtlEntity> preEmpDtlEntities =
				precontractEmpRepository.searchManPowerDtlsByCriteria(CommonUtil.convertStringToDate(searchInvoiceMaterialsReq.getInvoceFromDate()),CommonUtil.convertStringToDate(searchInvoiceMaterialsReq.getInvoceToDate()),searchInvoiceMaterialsReq.getPrecontractId(),
											//searchInvoiceMaterialsReq.getPcName(),
											searchInvoiceMaterialsReq.getPocSubCatName(),
											//searchInvoiceMaterialsReq.getPayableCat(),
											searchInvoiceMaterialsReq.getUnitsOfMeasure());
		Integer totaValue = 0;
		for(PreContractsEmpDtlEntity preDtlEntity: preEmpDtlEntities) {
			SearchManpowerDocketTO searchManpowerDocketTO = new SearchManpowerDocketTO();
			searchManpowerDocketTO.setDocketId(preDtlEntity.getId());
			searchManpowerDocketTO.setAmount(preDtlEntity.getEstimateCost());
			searchManpowerDocketTO.setClaimedQuantity(preDtlEntity.getQuantity());
			searchManpowerDocketTO.setProcessQuantity(preDtlEntity.getQuantity());
			searchManpowerDocketTO.setProgressQuantity(preDtlEntity.getQuantity());
			searchManpowerDocketTO.setQuantity(preDtlEntity.getQuantity().longValue());
			searchManpowerDocketTO.setItemCode(preDtlEntity.getDesc());
			searchManpowerDocketTO.setItemDesc(preDtlEntity.getDesc());
			searchManpowerDocketTO.setFinishDate(preDtlEntity.getFinishDate());
			searchManpowerDocketTO.setDocketDate(preDtlEntity.getStartDate());
			searchManpowerDocketTO.setUnitOfMeasure(preDtlEntity.getUnitMeasure());
			searchManpowerDocketTO.setMaterildDtoId(preDtlEntity.getId().toString());
			seDocketTOs.add(searchManpowerDocketTO);
			}
		searchManpowerResp.setSearchManpowerTOs(seDocketTOs);
		return searchManpowerResp;
	}

	@Override
	public SearchManpowerResp searchInvoicePlantsByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
		// TODO Auto-generated method stub
		SearchManpowerResp searchManpowerResp = new SearchManpowerResp();
		List<SearchManpowerDocketTO> seDocketTOs = new ArrayList<>();
		List<PreContractsPlantDtlEntity> prePlantDtlEntities =
				precontractPlantRepository.searchPlantsDtlsByCriteria(CommonUtil.convertStringToDate(searchInvoiceMaterialsReq.getInvoceFromDate()),
						CommonUtil.convertStringToDate(searchInvoiceMaterialsReq.getInvoceToDate()),
						searchInvoiceMaterialsReq.getPrecontractId(),
											//searchInvoiceMaterialsReq.getPcName(),
											searchInvoiceMaterialsReq.getPocSubCatName(),
											//searchInvoiceMaterialsReq.getPayableCat(),
											searchInvoiceMaterialsReq.getUnitsOfMeasure());
		Integer totaValue = 0;
		for(PreContractsPlantDtlEntity preDtlEntity: prePlantDtlEntities) {
			SearchManpowerDocketTO searchManpowerDocketTO = new SearchManpowerDocketTO();
			searchManpowerDocketTO.setDocketId(preDtlEntity.getId());
			searchManpowerDocketTO.setAmount(preDtlEntity.getEstimateCost());
			searchManpowerDocketTO.setClaimedQuantity(preDtlEntity.getQuantity());
			searchManpowerDocketTO.setProcessQuantity(preDtlEntity.getQuantity());
			searchManpowerDocketTO.setProgressQuantity(preDtlEntity.getQuantity());
			searchManpowerDocketTO.setQuantity(preDtlEntity.getQuantity().longValue());
			searchManpowerDocketTO.setItemCode(preDtlEntity.getDesc());
			searchManpowerDocketTO.setItemDesc(preDtlEntity.getDesc());
			searchManpowerDocketTO.setFinishDate(preDtlEntity.getFinishDate());
			searchManpowerDocketTO.setDocketDate(preDtlEntity.getStartDate());
			searchManpowerDocketTO.setUnitOfMeasure(preDtlEntity.getUnitMeasure());
			searchManpowerDocketTO.setMaterildDtoId(preDtlEntity.getId().toString());
			seDocketTOs.add(searchManpowerDocketTO);
			}
		searchManpowerResp.setSearchManpowerTOs(seDocketTOs);
		return searchManpowerResp;
	}

	@Override
	public SearchManpowerResp searchInvoiceServicesByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
		// TODO Auto-generated method stub
				SearchManpowerResp searchManpowerResp = new SearchManpowerResp();
				List<SearchManpowerDocketTO> seDocketTOs = new ArrayList<>();
				List<PreContractsServiceDtlEntity> preServicesDtlEntities =
						precontractServiceRepository.searchServiceDtlsByCriteria(CommonUtil.convertStringToDate(searchInvoiceMaterialsReq.getInvoceFromDate()),CommonUtil.convertStringToDate(searchInvoiceMaterialsReq.getInvoceToDate()),searchInvoiceMaterialsReq.getPrecontractId(),
													//searchInvoiceMaterialsReq.getPcName(),
													searchInvoiceMaterialsReq.getPocSubCatName());
													//searchInvoiceMaterialsReq.getPayableCat(),
													//searchInvoiceMaterialsReq.getUnitsOfMeasure()
				Integer totaValue = 0;
				for(PreContractsServiceDtlEntity preDtlEntity: preServicesDtlEntities) {
					SearchManpowerDocketTO searchManpowerDocketTO = new SearchManpowerDocketTO();
					searchManpowerDocketTO.setDocketId(preDtlEntity.getId());
					searchManpowerDocketTO.setAmount(preDtlEntity.getEstimateCost());
					searchManpowerDocketTO.setClaimedQuantity(preDtlEntity.getQuantity());
					searchManpowerDocketTO.setProcessQuantity(preDtlEntity.getQuantity());
					searchManpowerDocketTO.setProgressQuantity(preDtlEntity.getQuantity());
					searchManpowerDocketTO.setQuantity(preDtlEntity.getQuantity().longValue());
					searchManpowerDocketTO.setItemCode(preDtlEntity.getDesc());
					searchManpowerDocketTO.setItemDesc(preDtlEntity.getDesc());
					searchManpowerDocketTO.setFinishDate(preDtlEntity.getFinishDate());
					searchManpowerDocketTO.setDocketDate(preDtlEntity.getStartDate());
					searchManpowerDocketTO.setMaterildDtoId(preDtlEntity.getId().toString());
					searchManpowerDocketTO.setUnitOfMeasure("");
					seDocketTOs.add(searchManpowerDocketTO);
					}
				searchManpowerResp.setSearchManpowerTOs(seDocketTOs);
				return searchManpowerResp;
	}

	@Override
	public InvoiceMaterialResp searchInvoiceSubByPCName(SearchInvoiceMaterialsReq searchInvoiceMaterialsReq) {
		// TODO Auto-generated method stub
		return null;
	}
	 
	
	  
	
}