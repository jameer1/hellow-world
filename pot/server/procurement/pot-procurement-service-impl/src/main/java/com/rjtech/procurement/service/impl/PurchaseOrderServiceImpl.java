package com.rjtech.procurement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.procurement.dto.InvoiceAmountTO;
import com.rjtech.procurement.dto.InvoiceAssignCostCodesTO;
import com.rjtech.procurement.dto.InvoiceParticularsTO;
import com.rjtech.procurement.dto.InvoiceVendorBankTO;
import com.rjtech.procurement.dto.ManpowerInvoiceDocketItemTO;
import com.rjtech.procurement.dto.MaterialInvoiceDocketItemTO;
import com.rjtech.procurement.dto.PlantInvoiceDocketItemTO;
import com.rjtech.procurement.dto.SowInvoiceDocketItemTO;
import com.rjtech.procurement.model.InvoiceAmountEntity;
import com.rjtech.procurement.model.InvoiceAssignCostCodesEntity;
import com.rjtech.procurement.model.InvoiceParticularsEntity;
import com.rjtech.procurement.model.InvoiceVendorBankEntity;
import com.rjtech.procurement.model.MapowerInvoiceDocketItemEntity;
import com.rjtech.procurement.model.MaterialInvoiceDocketItemEntity;
import com.rjtech.procurement.model.PlantInvoiceDocketItemEntity;
import com.rjtech.procurement.model.PreContractEntity;
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
import com.rjtech.procurement.model.PurchaseOrderEntity;
import com.rjtech.procurement.model.PurchaseOrderInvoiceDtlEntity;
import com.rjtech.procurement.model.SowInvoiceDocketItemEntity;
import com.rjtech.procurement.repository.ApproveInvoiceRepository;
import com.rjtech.procurement.repository.CopyEmpProjRegisterRepository;
import com.rjtech.procurement.repository.CopyEmpProjectPODtlRepository;
import com.rjtech.procurement.repository.CopyMaterialDeliveryDocketRepository;
import com.rjtech.procurement.repository.CopyMaterialProjRepository;
import com.rjtech.procurement.repository.CopyPlantProjPORepository;
import com.rjtech.procurement.repository.CopyPlantRegisterRepository;
import com.rjtech.procurement.repository.InvoiceAmountRepository;
import com.rjtech.procurement.repository.InvoiceAssignCostCodesRepository;
import com.rjtech.procurement.repository.InvoiceBankAcoountDetailsRepository;
import com.rjtech.procurement.repository.InvoiceParticularsRepository;
import com.rjtech.procurement.repository.ManpowerInvoiceDocketRepository;
import com.rjtech.procurement.repository.MaterialInvoiceDocketRepository;
import com.rjtech.procurement.repository.PlantInvoiceDocketRepository;
import com.rjtech.procurement.repository.PrecontractEmpCmpRepository;
import com.rjtech.procurement.repository.PrecontractMaterialCmpRepository;
import com.rjtech.procurement.repository.PrecontractPlantCmpRepository;
import com.rjtech.procurement.repository.PrecontractServiceCmpRepository;
import com.rjtech.procurement.repository.PrecontractSowCmpRepository;
import com.rjtech.procurement.repository.PurchaseOrderInvoiceRepository;
import com.rjtech.procurement.repository.PurchaseOrderProcRepository;
import com.rjtech.procurement.repository.PurchaseOrderRepository;
import com.rjtech.procurement.repository.SowInvoiceDocketRepository;
import com.rjtech.procurement.req.POProcureTypeReq;
import com.rjtech.procurement.req.PurchaseOrderGetReq;
import com.rjtech.procurement.req.PurchaseOrderInvoiceSaveReq;
import com.rjtech.procurement.resp.PurchaseOrderInvoiceResp;
import com.rjtech.procurement.resp.PurchaseOrderResp;
import com.rjtech.procurement.service.PurchaseOrdeService;
import com.rjtech.procurement.service.handler.InvoiceDocketItemHandler;
import com.rjtech.procurement.service.handler.PrecontractEmpHandler;
//import com.rjtech.procurement.service.PurchaseOrdeService;
//import com.rjtech.procurement.service.handler.InvoiceDocketItemHandler;
//import com.rjtech.procurement.service.handler.PrecontractEmpHandler;
import com.rjtech.procurement.service.handler.PrecontractPlantHandler;
import com.rjtech.procurement.service.handler.PrecontractServicesHandler;
import com.rjtech.procurement.service.handler.ProjectInvoiceHandler;
import com.rjtech.procurement.service.handler.PurchaseOrderHandler;
import com.rjtech.procurement.service.handler.PurchaseOrderInvoiceHandler;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.copy.ProjStoreStockMstrEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "purchaseOrdeService")
@RJSService(modulecode = "purchaseOrdeService")
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrdeService {

    @Autowired
    private PurchaseOrderProcRepository purchaseOrderProcRepository;

    @Autowired
    private PurchaseOrderInvoiceRepository purchaseOrderInvoiceRepository;

    @Autowired
    private ManpowerInvoiceDocketRepository manpowerInvoiceDocketRepository;

    @Autowired
    private PlantInvoiceDocketRepository plantInvoiceDocketRepository;

    @Autowired
    private MaterialInvoiceDocketRepository materialInvoiceDocketRepository;

    @Autowired
    private SowInvoiceDocketRepository sowInvoiceDocketRepository;

    @Autowired
    private InvoiceParticularsRepository invoiceParticularsRepository;

    @Autowired
    private InvoiceAmountRepository invoiceAmountRepository;

    @Autowired
    private InvoiceAssignCostCodesRepository assignCostCodesRepository;

    @Autowired
    private InvoiceBankAcoountDetailsRepository bankAcoountDetailsRepository;

    @Autowired
    private ApproveInvoiceRepository approveInvoiceRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private ProjCostItemRepositoryCopy projCostItemRepository;

    @Autowired
    private ProjSOWItemRepositoryCopy projSOWItemRepository;

    @Autowired
    private CopyEmpProjRegisterRepository copyEmpProjRegisterRepository;

    @Autowired
    private CopyEmpProjectPODtlRepository copyEmpProjectPODtlRepository;

    @Autowired
    private CopyMaterialProjRepository copyMaterialProjRepository;

    @Autowired
    private CopyMaterialDeliveryDocketRepository copyMaterialDeliveryDocketRepository;

    @Autowired
    private CopyPlantProjPORepository copyPlantProjPORepository;

    @Autowired
    private CopyPlantRegisterRepository copyPlantRegisterRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PrecontractEmpCmpRepository precontractEmpCmpRepository;

    @Autowired
    private PrecontractPlantCmpRepository precontractPlantCmpRepository;

    @Autowired
    private PrecontractMaterialCmpRepository precontractMaterialCmpRepository;

    @Autowired
    private PrecontractServiceCmpRepository precontractServiceCmpRepository;

    @Autowired
    private PrecontractSowCmpRepository precontractSowCmpRepository;

    public LabelKeyTOResp getPOByProcureType(POProcureTypeReq procureTypeReq) {
        List<Long> projIds = new ArrayList<>();
        projIds.add(procureTypeReq.getProjId());
        List<PurchaseOrderEntity> purchaseOrderEntities = new ArrayList<PurchaseOrderEntity>();
        System.out.println("getPOByProcureType function of PurchaseOrderServiceImpl");
        if (procureTypeReq.getWorkDairyDate() == null) {
            String[] procureTypes = procureTypeReq.getProcureType().split(",");
            if (procureTypes.length == 1) {
                purchaseOrderEntities = purchaseOrderRepository.findPOByProcureType(projIds, procureTypeReq.getStatus(),
                        CommonUtil.appendLikeOperator(procureTypeReq.getProcureType()));
            } else {
                purchaseOrderEntities = purchaseOrderRepository.findPOByProcureTypes(projIds, procureTypes);
            }
        }

        else
            purchaseOrderEntities = purchaseOrderRepository.findPOByProcureType(projIds, procureTypeReq.getStatus(),
                    CommonUtil.appendLikeOperator(procureTypeReq.getProcureType()),
                    CommonUtil.convertStringToDate(procureTypeReq.getWorkDairyDate()));
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        for (PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
        	System.out.println(purchaseOrderEntity);
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(purchaseOrderEntity.getId());
            boolean isRepeatPO = false;
            if (AppUtils.isNotNull(purchaseOrderEntity.getParentId()))
            {
                isRepeatPO = true;
            }
            labelKeyTO.setCode(PurchaseOrderHandler.generatePurchaseOrderCode(purchaseOrderEntity,isRepeatPO));
            labelKeyTO.setPoDescription( purchaseOrderEntity.getPreContractsCmpEntity().getPreContractEntity().getDesc() );
            if (CommonUtil.objectNotNull(purchaseOrderEntity.getPreContractsCmpEntity()) && CommonUtil
                    .objectNotNull(purchaseOrderEntity.getPreContractsCmpEntity().getCompanyMstrEntity())) {
                CompanyMstrEntity company = purchaseOrderEntity.getPreContractsCmpEntity().getCompanyMstrEntity();
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_CODE, company.getCode());
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_NAME, company.getName());
            }
			if(purchaseOrderEntity.getFinsihDate()!= null && purchaseOrderEntity.getStartDate()!=null) {
			  labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_START_DATE,
			  CommonUtil.getYYYYMMDDFormat(purchaseOrderEntity.getStartDate()));
			  labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_FINISH_DATE,
			  CommonUtil.getYYYYMMDDFormat(purchaseOrderEntity.getFinsihDate()));
			}
			 
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_AMOUNT,
                    String.valueOf(purchaseOrderEntity.getAmount()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_PAYMENT_IN_DAYS,
                    String.valueOf(purchaseOrderEntity.getPaymentInDays()));
            labelKeyTOResp.getLabelKeyTOs().add(labelKeyTO);
        }
        return labelKeyTOResp;
    }
    
    @Override
    public LabelKeyTOResp getPOByPreContranctType(PurchaseOrderGetReq poGetReq) {
        List<Long> projIds = new ArrayList<>();
        projIds.add(poGetReq.getProjId());
        List<PurchaseOrderEntity> purchaseOrderEntities = new ArrayList<PurchaseOrderEntity>();
        System.out.println("getPOByPreContranctType function of PurchaseOrderServiceImpl");
        
        purchaseOrderEntities = purchaseOrderRepository.findPOByPreContractType(projIds, poGetReq.getStatus(),poGetReq.getPreContractType());
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        for (PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
        	System.out.println(purchaseOrderEntity);
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(purchaseOrderEntity.getId());
            boolean isRepeatPO = false;
            if (AppUtils.isNotNull(purchaseOrderEntity.getParentId()))
            {
                isRepeatPO = true;
            }
            labelKeyTO.setCode(PurchaseOrderHandler.generatePurchaseOrderCode(purchaseOrderEntity,isRepeatPO));
            labelKeyTO.setPoDescription( purchaseOrderEntity.getPreContractsCmpEntity().getPreContractEntity().getDesc() );
            if (CommonUtil.objectNotNull(purchaseOrderEntity.getPreContractsCmpEntity()) && CommonUtil
                    .objectNotNull(purchaseOrderEntity.getPreContractsCmpEntity().getCompanyMstrEntity())) {
                CompanyMstrEntity company = purchaseOrderEntity.getPreContractsCmpEntity().getCompanyMstrEntity();
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_CODE, company.getCode());
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_NAME, company.getName());
            }
			if(purchaseOrderEntity.getFinsihDate()!= null && purchaseOrderEntity.getStartDate()!=null) {
			  labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_START_DATE,
			  CommonUtil.getYYYYMMDDFormat(purchaseOrderEntity.getStartDate()));
			  labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_FINISH_DATE,
			  CommonUtil.getYYYYMMDDFormat(purchaseOrderEntity.getFinsihDate()));
			}
			 
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_AMOUNT,
                    String.valueOf(purchaseOrderEntity.getAmount()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_PAYMENT_IN_DAYS,
                    String.valueOf(purchaseOrderEntity.getPaymentInDays()));
            labelKeyTOResp.getLabelKeyTOs().add(labelKeyTO);
        }
        return labelKeyTOResp;
    }

    public LabelKeyTOResp getPOItemDetails(POProcureTypeReq procureTypeReq) {
        PreContractsCmpEntity preContractsCmpEntity = purchaseOrderRepository
                .getPurchaseOrderPrecontractCmp(procureTypeReq.getPurId());
        List<LabelKeyTO> labelKeyTOs = new ArrayList<>();
        switch (procureTypeReq.getProcureType()) {
            case "E":
                List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities = precontractEmpCmpRepository
                        .preContractsEmpCmpEntities(preContractsCmpEntity.getId());
                for (PreContractsEmpCmpEntity empCmpEntity : preContractsEmpCmpEntities) {
                	
                    PreContractsEmpDtlEntity empDtl = empCmpEntity.getContractsEmpDtlEntity();

                    LabelKeyTO labelKeyTO = new LabelKeyTO();
                    labelKeyTO.setId(empDtl.getId());
                    labelKeyTO.setCode(PrecontractEmpHandler.generateCode(empDtl));
                    labelKeyTO.setName(empDtl.getDesc());
                    Integer totalempschItems = copyEmpProjectPODtlRepository.findtotalschItems(empDtl.getId());
                    
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CLASS_ID,
                            String.valueOf(empDtl.getProjEmpClassId().getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_ID,
                            String.valueOf(empDtl.getCostStatement().getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_START_DATE,
                            empDtl.getStartDate().toString());
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_FINISH_DATE,
                            empDtl.getFinishDate().toString());
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_PLACE, empDtl.getDeliveryPlace());

                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.QTY,
                            String.valueOf(empCmpEntity.getQuantity()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_QUANTITY,
                            String.valueOf(totalempschItems));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.UNIT_OF_RATE,
                            String.valueOf(empCmpEntity.getRate()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_ITEM_CMP_ID,
                            String.valueOf(empCmpEntity.getId()));

                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_CMP_ID,
                            String.valueOf(preContractsCmpEntity.getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_ID,
                            String.valueOf(preContractsCmpEntity.getCompanyId().getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCUREMENT_MASTER_TYPE, "E");
                    labelKeyTOs.add(labelKeyTO);
                }
                getServiceSchItems(preContractsCmpEntity, labelKeyTOs);
                break;
            case "P":
                List<PreContractsPlantCmpEntity> preContractsPlantCmpEntities = precontractPlantCmpRepository
                        .preContractsPlantCmpEntities(preContractsCmpEntity.getId());
                for (PreContractsPlantCmpEntity plantCmp : preContractsPlantCmpEntities) {
                    PreContractsPlantDtlEntity plantDtl = plantCmp.getPreContractsPlantDtlEntity();

                    LabelKeyTO labelKeyTO = new LabelKeyTO();
                    labelKeyTO.setId(plantDtl.getId());
                    labelKeyTO.setName(plantDtl.getDesc());
                    labelKeyTO.setCode(PrecontractPlantHandler.generatePlantCode(plantDtl));
                    Integer totalPlantschItems = copyPlantProjPORepository.findtotalschItems(plantDtl.getId());

                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CLASS_ID,
                            String.valueOf(plantDtl.getPlantId().getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_ID,
                            String.valueOf(plantDtl.getProjcostStatement().getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_START_DATE,
                            plantDtl.getStartDate().toString());
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_FINISH_DATE,
                            plantDtl.getFinishDate().toString());
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_PLACE, plantDtl.getDeliveryPlace());
                    
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_QUANTITY,
                            String.valueOf(totalPlantschItems));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.QTY, String.valueOf(plantCmp.getQuantity()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.UNIT_OF_RATE,
                            String.valueOf(plantCmp.getRate()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_ITEM_CMP_ID,
                            String.valueOf(plantCmp.getId()));

                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_CMP_ID,
                            String.valueOf(preContractsCmpEntity.getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_ID,
                            String.valueOf(preContractsCmpEntity.getCompanyId().getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCUREMENT_MASTER_TYPE, "P");

                    labelKeyTOs.add(labelKeyTO);

                }
                getServiceSchItems(preContractsCmpEntity, labelKeyTOs);
                break;
            case "M":
                List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities = precontractMaterialCmpRepository
                        .preContractsMaterialCmpEntities(preContractsCmpEntity.getId());
                for (PreContractsMaterialCmpEntity matCmpEntity : preContractsMaterialCmpEntities) {
                    PreContractsMaterialDtlEntity matDtlEntity = matCmpEntity.getPreContractsMaterialDtlEntity();

                    LabelKeyTO labelKeyTO = new LabelKeyTO();
                    labelKeyTO.setId(matDtlEntity.getId());
                    labelKeyTO.setName(matDtlEntity.getDesc());
                    Integer totalMatschItems = copyMaterialProjRepository.findtotalschItems(matDtlEntity.getId());

                    PreContractEntity preContractEntity = matDtlEntity.getPreContractEntity();
                    labelKeyTO.setCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                            + preContractEntity.getProjId().getCode() + "-" + preContractEntity.getId() + "-"
                            + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc() + "-"
                            + AppUtils.formatNumberToString(matDtlEntity.getId()));

                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CLASS_ID,
                            String.valueOf(matDtlEntity.getMaterialId().getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_ID,
                            String.valueOf(matDtlEntity.getProjcostStatement().getId()));
                    StockMstrEntity stockEntity = matDtlEntity.getStoreId();
                    ProjStoreStockMstrEntity projStoreEntity = matDtlEntity.getProjStoreId();
                    String deliveryPlace = null;
                    String deliveryCategory = null;
                    if (stockEntity != null) {
                        labelKeyTO.getDisplayNamesMap().put(CommonConstants.STOCK_ID,
                                String.valueOf(stockEntity.getId()));
                        deliveryPlace = stockEntity.getName();
                        deliveryCategory = stockEntity.getCategory();
                    }
                    if (projStoreEntity != null) {
                        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJ_STOCK_ID,
                                String.valueOf(projStoreEntity.getId()));
                        if (deliveryPlace == null)
                            deliveryPlace = projStoreEntity.getDesc();
                        if (deliveryCategory == null)
                            deliveryCategory = projStoreEntity.getCategory();
                    }
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_PLACE, deliveryPlace);
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_CATAGORY, deliveryCategory);
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_START_DATE,
                            matDtlEntity.getStartDate().toString());
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_FINISH_DATE,
                            matDtlEntity.getFinishDate().toString());

                    labelKeyTO.getDisplayNamesMap().put("purClassUnitOfMeasure",
                            matDtlEntity.getMaterialId().getMeasurmentMstrEntity().getName());
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_QUANTITY,
                            String.valueOf(totalMatschItems));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.QTY,
                            String.valueOf(matCmpEntity.getQuantity()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.UNIT_OF_RATE,
                            String.valueOf(matCmpEntity.getRate()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_ITEM_CMP_ID,
                            String.valueOf(matCmpEntity.getId()));
                    labelKeyTO.getDisplayNamesMap().put("recievedQty",
                            (matCmpEntity.getRecievedQty() == null) ? null : matCmpEntity.getRecievedQty().toString());

                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_CMP_ID,
                            String.valueOf(preContractsCmpEntity.getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_ID,
                            String.valueOf(preContractsCmpEntity.getCompanyId().getId()));
                    labelKeyTO.getDisplayNamesMap().put("cmpName",
                            String.valueOf(preContractsCmpEntity.getCompanyId().getName()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCUREMENT_MASTER_TYPE, "M");
                    labelKeyTOs.add(labelKeyTO);
                }
                getServiceSchItems(preContractsCmpEntity, labelKeyTOs);
                break;
            case "S":
                getServiceSchItems(preContractsCmpEntity, labelKeyTOs);
                break;
            case "SOW":
                List<PrecontractSowCmpEntity> precontractSowCmpEntities = precontractSowCmpRepository
                        .precontractSowCmpEntities(preContractsCmpEntity.getId());
                for (PrecontractSowCmpEntity sowCmp : precontractSowCmpEntities) {
                    PrecontractSowDtlEntity sowDtlEntity = sowCmp.getPrecontractSowDtlEntity();

                    LabelKeyTO labelKeyTO = new LabelKeyTO();
                    labelKeyTO.setId(sowDtlEntity.getId());
                    labelKeyTO.setName(sowDtlEntity.getItemDesc());

                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CLASS_ID,
                            String.valueOf(sowDtlEntity.getSowId().getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_START_DATE,
                            sowDtlEntity.getStartDate().toString());
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_FINISH_DATE,
                            sowDtlEntity.getFinishDate().toString());
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_PLACE,
                            sowDtlEntity.getDeliveryPlace());

                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.QTY, String.valueOf(sowCmp.getQuantity()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.UNIT_OF_RATE, String.valueOf(sowCmp.getRate()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_ITEM_CMP_ID,
                            String.valueOf(sowCmp.getId()));

                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_CMP_ID,
                            String.valueOf(preContractsCmpEntity.getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_ID,
                            String.valueOf(preContractsCmpEntity.getCompanyId().getId()));
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCUREMENT_MASTER_TYPE, "SOW");
                    labelKeyTOs.add(labelKeyTO);
                }

                break;
            default:
                break;
        }

        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    private void getServiceSchItems(PreContractsCmpEntity preContractsCmpEntity, List<LabelKeyTO> labelKeyTOs) {
        List<PreContractsServiceCmpEntity> preContractsServiceCmpEntities = precontractServiceCmpRepository
                .preContractsServiceCmpEntities(preContractsCmpEntity.getId());
        for (PreContractsServiceCmpEntity svcCmp : preContractsServiceCmpEntities) {
            PreContractsServiceDtlEntity svcDtlEntity = svcCmp.getPreContractsServiceDtlEntity();
            
            Integer totalservschItems = 0;
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(svcDtlEntity.getId());
            labelKeyTO.setName(svcDtlEntity.getDesc());
            labelKeyTO.setCode(PrecontractServicesHandler.generateCode(svcDtlEntity));
            
            Integer  totalservEmpschItems = copyEmpProjectPODtlRepository.findtotalschItems(svcDtlEntity.getId());
            Integer totalservPlantschItems = copyPlantProjPORepository.findtotalschItems(svcDtlEntity.getId());
            Integer totalServMatschItems = copyMaterialProjRepository.findtotalschItems(svcDtlEntity.getId());
           
            totalservschItems = totalservEmpschItems + totalservPlantschItems + totalServMatschItems;
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CLASS_ID,
                    String.valueOf(svcDtlEntity.getServiceId().getId()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_ID,
                    String.valueOf(svcDtlEntity.getProjcostStatement().getId()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_START_DATE, svcDtlEntity.getStartDate().toString());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_FINISH_DATE,
                    svcDtlEntity.getFinishDate().toString());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_PLACE, svcDtlEntity.getDeliveryPlace());
            
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_QUANTITY,
                    String.valueOf(totalservschItems));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.QTY, String.valueOf(svcCmp.getQuantity()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.UNIT_OF_RATE, String.valueOf(svcCmp.getRate()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_ITEM_CMP_ID, String.valueOf(svcCmp.getId()));

            labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_CMP_ID,
                    String.valueOf(preContractsCmpEntity.getId()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_ID,
                    String.valueOf(preContractsCmpEntity.getCompanyId().getId()));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCUREMENT_MASTER_TYPE, "S");
            labelKeyTOs.add(labelKeyTO);
        }
    }

    public LabelKeyTOResp getPOScheudleItemsByProject(POProcureTypeReq procureTypeReq) {
        List<LabelKeyTO> labelKeyTOs = purchaseOrderProcRepository
                .getPOScheudleItemsByProject(procureTypeReq.getProjId(), procureTypeReq.getProcureType());
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    public void updatePurchaseOrderSummary(POProcureTypeReq procureTypeReq) {
        purchaseOrderProcRepository.updatePurchaseOrderSummary(procureTypeReq.getPurId());
    }

    public PurchaseOrderInvoiceResp getPurchaseOrderInvoices(PurchaseOrderGetReq purchaseOrderGetReq) {
        List<PurchaseOrderInvoiceDtlEntity> purchaseOrderInvoiceDtlEntities = purchaseOrderInvoiceRepository
                .findPurchaseOrderInvoices(purchaseOrderGetReq.getPurchaseId(), purchaseOrderGetReq.getProjId(),
                        purchaseOrderGetReq.getStatus());
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = new PurchaseOrderInvoiceResp();
        for (PurchaseOrderInvoiceDtlEntity purchaseOrderInvoiceDtlEntity : purchaseOrderInvoiceDtlEntities) {
            purchaseOrderInvoiceResp.getPurchaseOrderInvoiceDtlTOs()
                    .add(PurchaseOrderInvoiceHandler.convertEntityToPOJO(purchaseOrderInvoiceDtlEntity));
        }
        return purchaseOrderInvoiceResp;
    }

    public void savePurchaseOrderInvoices(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        PurchaseOrderInvoiceDtlEntity purchaseOrderInvoiceDtlEntity = PurchaseOrderInvoiceHandler.convertPOJOToEntity(
                purchaseOrderInvoiceSaveReq.getPurchaseOrderInvoiceDtlTO(), purchaseOrderRepository, epsProjRepository,
                projCostItemRepository, loginRepository);
        purchaseOrderInvoiceRepository.save(purchaseOrderInvoiceDtlEntity);

    }

    public LabelKeyTOResp getManpowerInvoiceDocket(POProcureTypeReq poProcureTypeReq) {
        List<LabelKeyTO> labelKeyTOs = purchaseOrderProcRepository
                .getManpowerInvoiceDockets(poProcureTypeReq.getPurId(), poProcureTypeReq.getProjId());
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    public LabelKeyTOResp getPlantInvoiceDocket(POProcureTypeReq poProcureTypeReq) {
        List<LabelKeyTO> labelKeyTOs = purchaseOrderProcRepository.getPlantInvoiceDockets(poProcureTypeReq.getPurId(),
                poProcureTypeReq.getProjId());
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    public LabelKeyTOResp getMaterialInvoiceDocket(POProcureTypeReq poProcureTypeReq) {
        List<LabelKeyTO> labelKeyTOs = purchaseOrderProcRepository
                .getMaterialInvoiceDockets(poProcureTypeReq.getPurId(), poProcureTypeReq.getProjId());
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    public void saveManpowerInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        List<MapowerInvoiceDocketItemEntity> invoiceDocketItemEntities = new ArrayList<>();
        for (ManpowerInvoiceDocketItemTO invoiceDocketItemTO : purchaseOrderInvoiceSaveReq
                .getManpowerInvoiceDocketItemTOs()) {
            invoiceDocketItemTO.setProjId(purchaseOrderInvoiceSaveReq.getProjId());
            invoiceDocketItemTO.setPurId(purchaseOrderInvoiceSaveReq.getPurId());
            invoiceDocketItemEntities.add(
                    InvoiceDocketItemHandler.convertPOJOToEntity(invoiceDocketItemTO, copyEmpProjRegisterRepository,
                            epsProjRepository, purchaseOrderRepository, copyEmpProjectPODtlRepository));
        }
        manpowerInvoiceDocketRepository.save(invoiceDocketItemEntities);
    }

    public void savePlantInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        List<PlantInvoiceDocketItemEntity> invoiceDocketItemEntities = new ArrayList<>();
        for (PlantInvoiceDocketItemTO invoiceDocketItemTO : purchaseOrderInvoiceSaveReq
                .getPlantInvoiceDocketItemTOs()) {
            invoiceDocketItemEntities
                    .add(InvoiceDocketItemHandler.convertPOJOToEntity(invoiceDocketItemTO, epsProjRepository,
                            purchaseOrderRepository, copyPlantProjPORepository, copyPlantRegisterRepository));
        }
        plantInvoiceDocketRepository.save(invoiceDocketItemEntities);
    }

    public void saveMaterialInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        List<MaterialInvoiceDocketItemEntity> invoiceDocketItemEntities = new ArrayList<>();
        for (MaterialInvoiceDocketItemTO invoiceDocketItemTO : purchaseOrderInvoiceSaveReq
                .getMaterialInvoiceDocketItemTOs()) {
            invoiceDocketItemEntities
                    .add(InvoiceDocketItemHandler.convertPOJOToEntity(invoiceDocketItemTO, epsProjRepository,
                            purchaseOrderRepository, copyMaterialProjRepository, copyMaterialDeliveryDocketRepository));
        }
        materialInvoiceDocketRepository.save(invoiceDocketItemEntities);
    }

    public void saveSowInvoiceDocket(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        List<SowInvoiceDocketItemEntity> invoiceDocketItemEntities = new ArrayList<>();
        for (SowInvoiceDocketItemTO invoiceDocketItemTO : purchaseOrderInvoiceSaveReq.getSowInvoiceDocketItemTOs()) {
            invoiceDocketItemEntities.add(InvoiceDocketItemHandler.convertPOJOToEntity(invoiceDocketItemTO,
                    purchaseOrderRepository, epsProjRepository, projSOWItemRepository));
        }
        sowInvoiceDocketRepository.save(invoiceDocketItemEntities);
    }

    public void saveInvoiceParticulars(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {

        List<InvoiceParticularsEntity> invoiceParticularsEntites = new ArrayList<>();
        for (InvoiceParticularsTO invoiceParticularsTO : purchaseOrderInvoiceSaveReq.getInvoiceParticularsTOs()) {
            invoiceParticularsEntites.add(ProjectInvoiceHandler.convertPOJOToEntity(invoiceParticularsTO,
                    purchaseOrderRepository, epsProjRepository));
        }
        invoiceParticularsRepository.save(invoiceParticularsEntites);
    }

    public void saveInvoiceAmount(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        List<InvoiceAmountEntity> invoiceAmountEntities = new ArrayList<>();
        for (InvoiceAmountTO invoiceAmountTO : purchaseOrderInvoiceSaveReq.getInvoiceAmountTOs()) {
            invoiceAmountEntities.add(ProjectInvoiceHandler.convertPOJOToEntity(invoiceAmountTO, epsProjRepository,
                    purchaseOrderRepository));
        }
        invoiceAmountRepository.save(invoiceAmountEntities);

    }

    public void saveInvoiceAssignCostCodes(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        List<InvoiceAssignCostCodesEntity> assignCostCodesEntities = new ArrayList<>();
        for (InvoiceAssignCostCodesTO assignCostCodesTO : purchaseOrderInvoiceSaveReq.getAssignCostCodesTOs()) {
            assignCostCodesEntities.add(ProjectInvoiceHandler.convertPOJOToEntity(assignCostCodesTO, epsProjRepository,
                    purchaseOrderRepository, projCostItemRepository));
        }
        assignCostCodesRepository.save(assignCostCodesEntities);

    }

    public void saveInvoiceVendorBankDetails(PurchaseOrderInvoiceSaveReq purchaseOrderInvoiceSaveReq) {
        List<InvoiceVendorBankEntity> vendorBankEntities = new ArrayList<>();
        for (InvoiceVendorBankTO vendorBankTO : purchaseOrderInvoiceSaveReq.getVendorBankTOs()) {
            vendorBankEntities.add(ProjectInvoiceHandler.convertPOJOToEntity(vendorBankTO, purchaseOrderRepository,
                    epsProjRepository, loginRepository));
        }
        bankAcoountDetailsRepository.save(vendorBankEntities);

    }

    public PurchaseOrderInvoiceResp getInvoiceParticulars(PurchaseOrderGetReq purchaseOrderGetReq) {
        List<InvoiceParticularsEntity> invoiceParticularsEntities = invoiceParticularsRepository
                .findInvoiceParticulars(purchaseOrderGetReq.getPurchaseId(), purchaseOrderGetReq.getStatus());
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = new PurchaseOrderInvoiceResp();
        for (InvoiceParticularsEntity invoiceParticularsEntity : invoiceParticularsEntities) {
            purchaseOrderInvoiceResp.getInvoiceParticularsTOs()
                    .add(ProjectInvoiceHandler.convertEntityToPOJO(invoiceParticularsEntity));
        }
        return purchaseOrderInvoiceResp;
    }

    public PurchaseOrderInvoiceResp getInvoiceAmount(PurchaseOrderGetReq purchaseOrderGetReq) {
        List<InvoiceAmountEntity> invoiceAmountEntities = invoiceAmountRepository
                .findInvoiceAmount(purchaseOrderGetReq.getPurchaseId(), purchaseOrderGetReq.getStatus());
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = new PurchaseOrderInvoiceResp();
        for (InvoiceAmountEntity invoiceAmountEntity : invoiceAmountEntities) {
            purchaseOrderInvoiceResp.getInvoiceAmountTOs()
                    .add(ProjectInvoiceHandler.convertEntityToPOJO(invoiceAmountEntity));
        }
        return purchaseOrderInvoiceResp;
    }

    public PurchaseOrderInvoiceResp getInvoiceAssignCostCodes(PurchaseOrderGetReq purchaseOrderGetReq) {
        List<InvoiceAssignCostCodesEntity> assignCostCodesEntities = assignCostCodesRepository
                .findInvoiceCostVodes(purchaseOrderGetReq.getPurchaseId(), purchaseOrderGetReq.getStatus());
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = new PurchaseOrderInvoiceResp();
        for (InvoiceAssignCostCodesEntity assignCostCodesEntity : assignCostCodesEntities) {
            purchaseOrderInvoiceResp.getAssignCostCodesTOs()
                    .add(ProjectInvoiceHandler.convertEntityToPOJO(assignCostCodesEntity));
        }
        return purchaseOrderInvoiceResp;
    }

    public PurchaseOrderInvoiceResp getInvoiceVendorBankDetails(PurchaseOrderGetReq purchaseOrderGetReq) {
        List<InvoiceVendorBankEntity> vendorBankEntities = bankAcoountDetailsRepository
                .findInvoiceBankDtls(purchaseOrderGetReq.getPurchaseId(), purchaseOrderGetReq.getStatus());
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = new PurchaseOrderInvoiceResp();
        for (InvoiceVendorBankEntity vendorBankEntity : vendorBankEntities) {
            purchaseOrderInvoiceResp.getVendorBankTOs()
                    .add(ProjectInvoiceHandler.convertEntityToPOJO(vendorBankEntity));
        }
        return purchaseOrderInvoiceResp;
    }

    public PurchaseOrderInvoiceResp getApproveInvoice(PurchaseOrderGetReq purchaseOrderGetReq) {
        String approveStatus = null;
        List<PurchaseOrderInvoiceDtlEntity> purchaseOrderInvoiceDtlEntities = null;

        if (CommonUtil.isBlankStr(purchaseOrderGetReq.getApproveStatus())) {
            approveStatus = CommonConstants.PENDING_APPROVAL;
        } else {
            approveStatus = purchaseOrderGetReq.getApproveStatus();
        }

        Long reqUserId = null;
        if (purchaseOrderGetReq.isLoginUser()) {
            reqUserId = AppUserUtils.getUserId();
        }
        purchaseOrderInvoiceDtlEntities = approveInvoiceRepository.findPostInvoices(purchaseOrderGetReq.getStatus(),
                reqUserId, approveStatus, purchaseOrderGetReq.getProjId());
        return PurchaseOrderInvoiceHandler.populatePostInvoices(purchaseOrderInvoiceDtlEntities);
    }

    public PurchaseOrderInvoiceResp getReleasePaymentInvoice(PurchaseOrderGetReq purchaseOrderGetReq) {
        String approveStatus = null;
        String paymentStatus = null;
        List<PurchaseOrderInvoiceDtlEntity> purchaseOrderInvoiceDtlEntities = null;

        if (CommonUtil.isBlankStr(purchaseOrderGetReq.getApproveStatus())) {
            approveStatus = CommonConstants.APPROVED;
        } else {
            approveStatus = purchaseOrderGetReq.getApproveStatus();
        }

        if (CommonUtil.isBlankStr(purchaseOrderGetReq.getPaymentStatus())) {
            paymentStatus = CommonConstants.YET_TO_RELEASE;
        } else {
            paymentStatus = purchaseOrderGetReq.getPaymentStatus();
        }

        Long reqUserId = null;
        if (purchaseOrderGetReq.isLoginUser()) {
            reqUserId = AppUserUtils.getUserId();
        }
        purchaseOrderInvoiceDtlEntities = approveInvoiceRepository.findReleasePaymentInvoices(
                purchaseOrderGetReq.getStatus(), reqUserId, approveStatus, paymentStatus,
                purchaseOrderGetReq.getProjId());
        return PurchaseOrderInvoiceHandler.populatePostInvoices(purchaseOrderInvoiceDtlEntities);
    }

    public PurchaseOrderResp getSOWPurchaseOrders(POProcureTypeReq procureTypeReq) {
        List<Long> projIds = new ArrayList<>();
        projIds.add(procureTypeReq.getProjId());
        List<PurchaseOrderEntity> purchaseOrderEntities = purchaseOrderRepository.findPOByProcureType(projIds,
                procureTypeReq.getStatus(), CommonUtil.appendLikeOperator(procureTypeReq.getProcureType()));
        PurchaseOrderResp purchaseOrderResp = new PurchaseOrderResp();
        for (PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
            purchaseOrderResp.getPurchaseOrderTOs().add(PurchaseOrderHandler.convertEntityToPOJO(purchaseOrderEntity));
        }
        return purchaseOrderResp;
    }

}