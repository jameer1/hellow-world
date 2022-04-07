package com.rjtech.register.service.impl.material;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.material.ledger.MaterialLedgerResTo;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
/*import com.rjtech.procurement.model.PreContractEntityCopy;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntityCopy;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;*/
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
import com.rjtech.register.material.model.MaterialProjDocketEntity;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
//import com.rjtech.register.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.register.repository.material.MaterialDeliveryDocketRepository;
import com.rjtech.register.repository.material.MaterialDockSchItemRepository;
import com.rjtech.register.repository.material.MaterialProjDocketRepository;
import com.rjtech.register.repository.material.PrecontractMaterialRepositoryCopy;
import com.rjtech.register.service.material.MaterialDeliveryDocketSevice;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "materialDeliveryDocketSevice")
@RJSService(modulecode = "materialDeliveryDocketSevice")
@Transactional
public class MaterialDeliveryDocketSeviceImpl implements MaterialDeliveryDocketSevice {

    @Autowired
    private EPSProjService epsProjService;

    @Autowired
    private MaterialDeliveryDocketRepository materialDeliveryDocketRepository;

    @Autowired
    private MaterialDockSchItemRepository materialDockSchItemRepository;

    @Autowired
    private MaterialProjDocketRepository materialProjDocketRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;
    
    @Autowired
    private MaterialClassRepository materialClassRepository;
    
    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private ProjStoreStockRepositoryCopy projStoreStockRepository;

    @Autowired
    private PrecontractMaterialRepositoryCopy preContractMaterialRepository;
    
    public LabelKeyTOResp getMaterialDeliveryDocketDetails(MaterialGetReq materialGetReq) {
        final List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();
        List<Object[]> materialDockets = new ArrayList<Object[]>();
        ProjMstrEntity project = epsProjRepository.findOne(materialGetReq.getProjId());
        if (!materialGetReq.isSupplierDocket())
            materialDockets = materialDeliveryDocketRepository.getMaterialProjectDockets(project);
        else
            materialDockets = materialDeliveryDocketRepository.getMaterialSupplierDockets(project);

        materialDockets.forEach((materialDocket) -> {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId((Long) materialDocket[0]);
            Long preContractId = (Long) materialDocket[1];
            Map<String, String> displayMap = new HashMap<String, String>();
            if (preContractId != null) {
                PreContractsMaterialDtlEntity preContractMaterialDtl = preContractMaterialRepository
                        .findOne((Long) materialDocket[1]);
                PreContractEntity preContract = preContractMaterialDtl.getPreContractEntity();
                labelKeyTO.setCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                        + preContract.getProjId().getCode() + "-" + preContract.getId() + "-"
                        + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc() + "-"
                        + AppUtils.formatNumberToString(preContractMaterialDtl.getId()));
                displayMap.put(CommonConstants.PUR_CODE,
                        ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + project.getCode() + "-"
                                + preContract.getId() + "-" + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-"
                                + (Long) materialDocket[6]);
                displayMap.put("purId", String.valueOf((Long) materialDocket[6]));
            }
            labelKeyTO.setName((String) materialDocket[2]);
            labelKeyTO.setUnitOfMeasure((String) materialDocket[3]);
            displayMap.put(CommonConstants.CLASS_TYPE, (String) materialDocket[4]);
            displayMap.put(CommonConstants.COMPANY_CATG_NAME, (String) materialDocket[5]);
            displayMap.put(CommonConstants.DELIVERY_DOCKET_NO, ((String) materialDocket[7]));
            displayMap.put(CommonConstants.DELIVERY_DOCKET_DATE, ((Date) materialDocket[8]).toString());
            MaterialPODeliveryDocketEntity materialPoDeliveryDocket = materialDeliveryDocketRepository
                    .findOne(((Long) materialDocket[12]));
            if (materialPoDeliveryDocket.getStockId() != null) {
                displayMap.put(CommonConstants.LOCATION, materialPoDeliveryDocket.getStockId().getName());
                displayMap.put("stockId", materialPoDeliveryDocket.getStockId().getId().toString());
            } else {
                displayMap.put(CommonConstants.LOCATION, materialPoDeliveryDocket.getProjStockId().getDesc());
                displayMap.put("projStockId", materialPoDeliveryDocket.getProjStockId().getId().toString());
            }
            displayMap.put("calculatedReceivedQty", ((BigDecimal) materialDocket[9]).toString());
            displayMap.put(CommonConstants.DEFECT_COMMENTS, (String) materialDocket[10]);
            displayMap.put(CommonConstants.COMMENTS, (String) materialDocket[11]);
            displayMap.put(CommonConstants.DELIVERY_DOCKET_ID, ((Long) materialDocket[12]).toString());

            if (materialDocket.length > 13) {
                if (materialDocket[14] != null)
                    displayMap.put(CommonConstants.TRANSIT_QTY, ((BigDecimal) materialDocket[14]).toString());
                else
                    displayMap.put(CommonConstants.TRANSIT_QTY, "0");
                displayMap.put(CommonConstants.UNIT_OF_RATE, ((BigDecimal) materialDocket[15]).toString());
                displayMap.put(CommonConstants.SCH_ITEM_CMP_ID, ((Long) materialDocket[16]).toString());
                displayMap.put("cmpId", ((Long) materialDocket[17]).toString());
                displayMap.put("purClassId", ((Long) materialDocket[18]).toString());
                displayMap.put("projDocketSchId", ((Long) materialDocket[19]).toString());
            }

            labelKeyTO.setDisplayNamesMap(displayMap);
            labelKeyTOs.add(labelKeyTO);
        });

        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    public LabelKeyTOResp getMaterialSchItemDeliveryDockets(MaterialFilterReq materialFilterReq) {
        List<LabelKeyTO> labelKeyTos = new ArrayList<LabelKeyTO>();
        List<MaterialPODeliveryDocketEntity> materialDeliverDocket = new ArrayList<MaterialPODeliveryDocketEntity>();
        if (materialFilterReq.getFromDate() != null)
            materialDeliverDocket = materialDeliveryDocketRepository.getMaterialDeliveryDockets(
                    getProjectsFromReq(materialFilterReq),
                    CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                    CommonUtil.convertStringToDate(materialFilterReq.getToDate()));
        else
            materialDeliverDocket = materialDeliveryDocketRepository
                    .getMaterialDeliveryDockets(getProjectsFromReq(materialFilterReq));

        materialDeliverDocket.forEach((material) -> {
            MaterialProjDtlEntity materialProjDtl = material.getMaterialProjDtlEntity();
            MaterialClassMstrEntity materialClassMstr = materialProjDtl.getMaterialClassId();
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(materialProjDtl.getId());
            PreContractsMaterialDtlEntity preContractMaterialDtl = materialProjDtl.getPreContractMterialId();
            PreContractEntity preContract = preContractMaterialDtl.getPreContractEntity();
            labelKeyTO.setCode(
                    ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + preContract.getProjId().getCode() + "-"
                            + preContract.getId() + "-" + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc()
                            + "-" + AppUtils.formatNumberToString(preContractMaterialDtl.getId()));
            labelKeyTO.setName(materialProjDtl.getPreContractMaterialName());
            labelKeyTO.setUnitOfMeasure(materialClassMstr.getMeasurmentMstrEntity().getCode());
            Map<String, String> displayMap = new HashMap<String, String>();
            ProjMstrEntity projMstrEntity = materialProjDtl.getProjId();
            displayMap.put(CommonConstants.PROJ_ID, projMstrEntity.getProjectId().toString());
            displayMap.put("projParentName", projMstrEntity.getParentProjectMstrEntity().getProjName());
            displayMap.put("projName", projMstrEntity.getProjName());
            displayMap.put(CommonConstants.PUR_CODE,
                    ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + materialProjDtl.getProjId().getCode()
                            + "-" + preContract.getId() + "-" + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-"
                            + materialProjDtl.getPurchaseId().getId());
            displayMap.put(CommonConstants.CLASS_TYPE, materialClassMstr.getId().toString());
            displayMap.put("materialClassName", materialClassMstr.getName());
            displayMap.put("materialUnitOfMeasure", materialClassMstr.getMeasurmentMstrEntity().getName());
            displayMap.put(CommonConstants.CMP_NAME, materialProjDtl.getCompanyMstrEntity().getName());
            displayMap.put(CommonConstants.DELIVERY_DOCKET_NO, material.getDocketNumber());
            displayMap.put(CommonConstants.DELIVERY_DOCKET_DATE,
                    CommonUtil.convertDateToString(material.getDocketDate()));
            StockMstrEntity stockMstr = material.getStockId();
            ProjStoreStockMstrEntity projStoreStock = material.getProjStockId();
            displayMap.put(CommonConstants.DELIVERY_PLACE,
                    ((stockMstr == null) ? projStoreStock.getDesc() : stockMstr.getName()));
            displayMap.put(CommonConstants.QTY, CommonUtil.getAmountInDecimalFormat(material.getReceivedQty()));
            displayMap.put(CommonConstants.UNIT_OF_RATE, materialProjDtl.getRate().toString());
            displayMap.put(CommonConstants.RECEIVED_BY, material.getReceivedBy());
            displayMap.put(CommonConstants.DEFECT_COMMENTS, material.getDefectComments());
            displayMap.put(CommonConstants.COMMENTS, material.getComments());
            displayMap.put(CommonConstants.SUPPLY_DELIVRY_DATE, (material.getSupplyDeliveryDate() == null) ? ""
                    : CommonUtil.convertDateToString(material.getSupplyDeliveryDate()));
            displayMap.put(CommonConstants.DELIVERY_CATAGORY,
                    (projStoreStock != null) ? projStoreStock.getCategory() : stockMstr.getCategory());
            displayMap.put(CommonConstants.DELIVERY_DOCKET_ID, material.getId().toString());
            displayMap.put("fileKey", material.getFileKey());
            displayMap.put("assetType", materialProjDtl.getAssetType());
            labelKeyTO.setDisplayNamesMap(displayMap);
            labelKeyTos.add(labelKeyTO);
        });

        return populateCummulativeTotal(labelKeyTos);
    }

    private LabelKeyTOResp populateCummulativeTotal(List<LabelKeyTO> labelKeyTOs) {
        long schItemId = 0l;
        long nextSchItemId = 0l;
        int count = 0;
        double qty = 0;
        double totalQty = 0;
        for (LabelKeyTO labelKeyTO : labelKeyTOs) {
            qty = 0;
            if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(CommonConstants.QTY))) {
                qty = Double.valueOf(labelKeyTO.getDisplayNamesMap().get(CommonConstants.QTY));
            }
            nextSchItemId = 0l;
            if (count > 0) {
                if (CommonUtil.isNonBlankLong(labelKeyTO.getId())) {
                    nextSchItemId = labelKeyTO.getId();
                }
                if (schItemId == nextSchItemId) {
                    totalQty = totalQty + qty;
                    labelKeyTO.getDisplayNamesMap().put(RegisterConstants.SCH_CUMMULATIVE_QTY,
                            CommonUtil.getAmountInDecimalFormat(totalQty));
                    schItemId = nextSchItemId;
                } else {
                    totalQty = qty;
                    if (CommonUtil.isNonBlankLong(labelKeyTO.getId())) {
                        schItemId = labelKeyTO.getId();
                    }
                    labelKeyTO.getDisplayNamesMap().put(RegisterConstants.SCH_CUMMULATIVE_QTY,
                            CommonUtil.getAmountInDecimalFormat(totalQty));
                }
            } else {
                totalQty = 0;
                if (CommonUtil.isNonBlankLong(labelKeyTO.getId())) {
                    schItemId = labelKeyTO.getId();
                }
                totalQty = qty;
                labelKeyTO.getDisplayNamesMap().put(RegisterConstants.SCH_CUMMULATIVE_QTY,
                        CommonUtil.getAmountInDecimalFormat(totalQty));
            }
            count++;
        }
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    @Override
    public List<MaterialLedgerResTo> getSupplierDeliveryDockets(MaterialFilterReq materialFilterReq) {
        List<MaterialLedgerResTo> materialLedgerResToList = new ArrayList<MaterialLedgerResTo>();
        List<Object[]> supplierDockets = getSupplyDeliveryDockets(materialFilterReq);

        supplierDockets.forEach((supplierDocket) -> {
            materialLedgerResToList.add(getDeliverySupplyLedgerResTo(supplierDocket));
        });

        return materialLedgerResToList;
    }

    private List<Object[]> getSupplyDeliveryDockets(MaterialFilterReq materialFilterReq) {
        if (!materialFilterReq.getStoreIds().isEmpty() || !materialFilterReq.getProjStoreIds().isEmpty()) {
        	if(!stockRepository.findAll(materialFilterReq.getStoreIds()).isEmpty() && !projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()).isEmpty()) {
            return materialDeliveryDocketRepository.getDocketsForInventoryReport(getProjectsFromReq(materialFilterReq),
                CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                CommonUtil.convertStringToDate(materialFilterReq.getToDate()), 
                materialClassRepository.findAll(materialFilterReq.getMaterialIds()), 
                stockRepository.findAll(materialFilterReq.getStoreIds()), 
                projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()));
        	}else if(!stockRepository.findAll(materialFilterReq.getStoreIds()).isEmpty() && projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()).isEmpty()) {
        		return materialDeliveryDocketRepository.getDocketsForStockReport(getProjectsFromReq(materialFilterReq),
                        CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                        CommonUtil.convertStringToDate(materialFilterReq.getToDate()), 
                        materialClassRepository.findAll(materialFilterReq.getMaterialIds()), 
                        stockRepository.findAll(materialFilterReq.getStoreIds()));
        	}else if(stockRepository.findAll(materialFilterReq.getStoreIds()).isEmpty() && !projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()).isEmpty()) {
        		return materialDeliveryDocketRepository.getDocketsForStoreReport(getProjectsFromReq(materialFilterReq),
                        CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                        CommonUtil.convertStringToDate(materialFilterReq.getToDate()), 
                        materialClassRepository.findAll(materialFilterReq.getMaterialIds()), 
                        projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds())); 
        	}
        }
            return materialDeliveryDocketRepository.getSupplierDeliveryDockets(getProjectsFromReq(materialFilterReq),
                CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                CommonUtil.convertStringToDate(materialFilterReq.getToDate()));
    }

    private MaterialLedgerResTo getDeliverySupplyLedgerResTo(Object[] supplierDocket) {
        MaterialLedgerResTo materialLedgerResTo = new MaterialLedgerResTo();
        materialLedgerResTo.setCurrency((String) supplierDocket[0]);
        materialLedgerResTo.setDate((Date) supplierDocket[1]);
        materialLedgerResTo.setDocketNumber((String) supplierDocket[2]);
        materialLedgerResTo.setEps((String) supplierDocket[3]);
        materialLedgerResTo.setExternalProjTransfer(null);
        if ((Boolean) supplierDocket[15]) {
            materialLedgerResTo.setDocketType("Supplier Docket");
            materialLedgerResTo.setIssued(((BigDecimal) supplierDocket[4]).toString());
        } else {
            materialLedgerResTo.setDocketType("Delivery Docket");
            materialLedgerResTo.setSupplied(((BigDecimal) supplierDocket[4]).toString());
        }
        materialLedgerResTo.setProjName((String) supplierDocket[5]);
        PreContractsMaterialDtlEntity preContractMaterialDtl = preContractMaterialRepository
                .findOne((Long) supplierDocket[6]);
        PreContractEntity preContract = preContractMaterialDtl.getPreContractEntity();
        materialLedgerResTo.setPurchaseOrderCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                + ((String) supplierDocket[7]) + "-" + preContract.getId() + "-"
                + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-" + (Long) supplierDocket[8]);
        materialLedgerResTo.setRate(((BigDecimal) supplierDocket[9]).toString());
        materialLedgerResTo.setResourceMaterial((String) supplierDocket[10]);
        materialLedgerResTo.setResourceSubGroup((String) supplierDocket[11]);
        materialLedgerResTo.setScheduleItemId(
                ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + preContract.getProjId().getCode() + "-"
                        + preContract.getId() + "-" + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc()
                        + "-" + AppUtils.formatNumberToString(preContractMaterialDtl.getId()));
        MaterialPODeliveryDocketEntity materialPoDeliveryDocket = materialDeliveryDocketRepository
                .findOne(((Long) supplierDocket[12]));
        if (materialPoDeliveryDocket.getStockId() != null) {
            materialLedgerResTo.setStoreLocation(materialPoDeliveryDocket.getStockId().getName());
            materialLedgerResTo.setStockId(materialPoDeliveryDocket.getStockId().getId());
        } else {
            materialLedgerResTo.setStoreLocation(materialPoDeliveryDocket.getProjStockId().getDesc());
            materialLedgerResTo.setProjStockId(materialPoDeliveryDocket.getProjStockId().getId());
        }
        materialLedgerResTo.setSupplierName((String) supplierDocket[13]);
        materialLedgerResTo.setUnitOfMeasure((String) supplierDocket[14]);
        materialLedgerResTo.setDocketId(materialLedgerResTo.getDocketType() + "-" + ((Long) supplierDocket[16]) + "-"
                + materialPoDeliveryDocket.getId());
        materialLedgerResTo.setMaterialClassId((Long) supplierDocket[16]);
        return materialLedgerResTo;
    }

    @Override
    public List<MaterialLedgerResTo> getDeliveryDockets(MaterialFilterReq materialFilterReq) {
        List<MaterialLedgerResTo> materialLedgerResToList = new ArrayList<MaterialLedgerResTo>();
        List<Object[]> deliveryDockets = getMaterialDeliveryDockets(materialFilterReq);
        deliveryDockets.forEach((deliveryDocket) -> {
            materialLedgerResToList.add(getDeliveryLedgerResTo(deliveryDocket));
        });
        return materialLedgerResToList;
    }

    private List<Object[]> getMaterialDeliveryDockets(MaterialFilterReq materialFilterReq) {
        if (!materialFilterReq.getStoreIds().isEmpty() || !materialFilterReq.getProjStoreIds().isEmpty()) {
        	if(!stockRepository.findAll(materialFilterReq.getStoreIds()).isEmpty() && !projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()).isEmpty()) {
            return materialDockSchItemRepository.getDocketsForInventoryReports(getProjectsFromReq(materialFilterReq),
                    CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                    CommonUtil.convertStringToDate(materialFilterReq.getToDate()),
                    materialClassRepository.findAll(materialFilterReq.getMaterialIds()),
                    stockRepository.findAll(materialFilterReq.getStoreIds()),
                    projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()));
        }else if(!stockRepository.findAll(materialFilterReq.getStoreIds()).isEmpty() && projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()).isEmpty()) {
        	return materialDockSchItemRepository.getDocketsForInventoryStockReports(getProjectsFromReq(materialFilterReq),
                    CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                    CommonUtil.convertStringToDate(materialFilterReq.getToDate()),
                    materialClassRepository.findAll(materialFilterReq.getMaterialIds()),
                    stockRepository.findAll(materialFilterReq.getStoreIds()));
        	
        }else if(stockRepository.findAll(materialFilterReq.getStoreIds()).isEmpty() && !projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()).isEmpty()) {
        	return materialDockSchItemRepository.getDocketsForInventoryStoreReports(getProjectsFromReq(materialFilterReq),
                    CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                    CommonUtil.convertStringToDate(materialFilterReq.getToDate()),
                    materialClassRepository.findAll(materialFilterReq.getMaterialIds()),
                    projStoreStockRepository.findAll(materialFilterReq.getProjStoreIds()));
        }
        
        }
            return materialDockSchItemRepository.getMaterialDeliveryDockets(getProjectsFromReq(materialFilterReq),
                CommonUtil.convertStringToDate(materialFilterReq.getFromDate()),
                CommonUtil.convertStringToDate(materialFilterReq.getToDate()));
    }

    private MaterialLedgerResTo getDeliveryLedgerResTo(Object[] deliveryDocket) {
        MaterialLedgerResTo materialLedgerResTo = new MaterialLedgerResTo();
        materialLedgerResTo.setCurrency((String) deliveryDocket[0]);
        materialLedgerResTo.setDate((Date) deliveryDocket[1]);
        materialLedgerResTo.setDocketType("Project Docket");
        materialLedgerResTo.setEps((String) deliveryDocket[2]);
        materialLedgerResTo.setExternalProjTransfer(null);
        materialLedgerResTo.setIssued(((BigDecimal) deliveryDocket[3]).toString());
        materialLedgerResTo.setProjName((String) deliveryDocket[4]);
        PreContractsMaterialDtlEntity preContractMaterialDtl = preContractMaterialRepository
                .findOne((Long) deliveryDocket[5]);
        PreContractEntity preContract = preContractMaterialDtl.getPreContractEntity();
        materialLedgerResTo.setPurchaseOrderCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                + ((String) deliveryDocket[6]) + "-" + preContract.getId() + "-"
                + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-" + (Long) deliveryDocket[7]);
        materialLedgerResTo.setRate(((BigDecimal) deliveryDocket[8]).toString());
        materialLedgerResTo.setResourceMaterial((String) deliveryDocket[9]);
        materialLedgerResTo.setResourceSubGroup((String) deliveryDocket[10]);
        materialLedgerResTo.setScheduleItemId(
                ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + preContract.getProjId().getCode() + "-"
                        + preContract.getId() + "-" + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc()
                        + "-" + AppUtils.formatNumberToString(preContractMaterialDtl.getId()));
        MaterialProjDocketEntity materialProjDocket = materialProjDocketRepository.findOne(((Long) deliveryDocket[11]));
        if (materialProjDocket.getToStockId() != null) {
            materialLedgerResTo.setStoreLocation(materialProjDocket.getToStockId().getName());
            materialLedgerResTo.setStockId(materialProjDocket.getToStockId().getId());
        } else if(materialProjDocket.getToProjStockId() != null){
            materialLedgerResTo.setStoreLocation(materialProjDocket.getToProjStockId().getDesc());
            materialLedgerResTo.setProjStockId(materialProjDocket.getToProjStockId().getId());
        }

        materialLedgerResTo.setSupplierName((String) deliveryDocket[12]);
        materialLedgerResTo.setUnitOfMeasure((String) deliveryDocket[13]);
        materialLedgerResTo.setDocketId(materialLedgerResTo.getDocketType() + "-" + ((Long) deliveryDocket[14]) + "-"
                + materialProjDocket.getId());
        materialLedgerResTo.setMaterialClassId((Long) deliveryDocket[14]);
        materialLedgerResTo.setDocketNumber(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                + materialProjDocket.getFromProjId().getCode().toUpperCase() + "-" + "SCH" + "-" + "PD" + "-"
                + materialProjDocket.getToProjId().getCode().toUpperCase() + "-" + materialProjDocket.getId());
        materialLedgerResTo.setOpeningStock(((BigDecimal) deliveryDocket[15]).toString());
        materialLedgerResTo.setClosingStock(((BigDecimal) deliveryDocket[16]).toString());
        return materialLedgerResTo;
    }

    private List<ProjMstrEntity> getProjectsFromReq(MaterialFilterReq materialFilterReq) {
        List<Long> projIds = null;
        if (CommonUtil.isListHasData(materialFilterReq.getProjList())) {
            projIds = materialFilterReq.getProjList();
        } else {
            projIds = epsProjService.getUserProjIds();
        }
        List<ProjMstrEntity> projIdObjs = new ArrayList<ProjMstrEntity>();
        projIds.forEach((projId) -> {
            projIdObjs.add(epsProjRepository.findOne(projId));
        });
        return projIdObjs;
    }

    @Override
    public List<MaterialLedgerResTo> getInTransitDetails(MaterialFilterReq materialFilterReq) {
        List<MaterialLedgerResTo> materialLedgerResToList = new ArrayList<MaterialLedgerResTo>();
        List<Object[]> supplierDockets = getSupplyDeliveryDockets(materialFilterReq);
        supplierDockets.forEach((supplierDocket) -> {
            MaterialPODeliveryDocketEntity materialPoDeliveryDocket = materialDeliveryDocketRepository
                    .findOne(((Long) supplierDocket[12]));
            if (materialPoDeliveryDocket.getStockId() != null) {
                StockMstrEntity stockMstrEntity = materialPoDeliveryDocket.getStockId();
                if (!stockMstrEntity.getCategory().equalsIgnoreCase("Stockpile"))
                    materialLedgerResToList.add(getDeliverySupplyLedgerResTo(supplierDocket));
            } else {
                ProjStoreStockMstrEntity projStockEntity = materialPoDeliveryDocket.getProjStockId();
                if (!projStockEntity.getCategory().equalsIgnoreCase("Stockpile"))
                    materialLedgerResToList.add(getDeliverySupplyLedgerResTo(supplierDocket));
            }
        });
        List<Object[]> deliveryDockets = getMaterialDeliveryDockets(materialFilterReq);
        deliveryDockets.forEach((deliveryDocket) -> {
            MaterialProjDocketEntity materialProjDocket = materialProjDocketRepository
                    .findOne(((Long) deliveryDocket[11]));
            if (materialProjDocket.getToStockId() != null) {
                StockMstrEntity stockMstrEntity = materialProjDocket.getToStockId();
                if (!stockMstrEntity.getCategory().equalsIgnoreCase("Stockpile"))
                    materialLedgerResToList.add(getInTransitMaterial(deliveryDocket, materialProjDocket));
            } else {
                ProjStoreStockMstrEntity projStockEntity = materialProjDocket.getToProjStockId();
                if (!projStockEntity.getCategory().equalsIgnoreCase("Stockpile"))
                    materialLedgerResToList.add(getInTransitMaterial(deliveryDocket, materialProjDocket));
            }
        });

        materialLedgerResToList.sort(Comparator.comparing(MaterialLedgerResTo::getDate).reversed());
        return materialLedgerResToList;
    }

    private MaterialLedgerResTo getInTransitMaterial(Object[] deliveryDocket,
            MaterialProjDocketEntity materialProjDocket) {
        MaterialLedgerResTo materialLedgerResTo = getDeliveryLedgerResTo(deliveryDocket);
        materialLedgerResTo.setOriginProject(materialProjDocket.getFromProjId().getProjName());
        materialLedgerResTo.setOriginLocation(
                (materialProjDocket.getOriginStockId() == null) ? materialProjDocket.getOriginProjStockId().getDesc()
                        : materialProjDocket.getOriginStockId().getName());
        materialLedgerResTo.setDestinationLocation(
                (materialProjDocket.getToStockId() == null) ? materialProjDocket.getToProjStockId().getDesc()
                        : materialProjDocket.getToStockId().getName());
        materialLedgerResTo.setDestinationProject(materialProjDocket.getToProjId().getProjName());
        materialLedgerResTo.setReceiverEmpName(materialProjDocket.getReceivedEmpEntity().getFirstName() + " "
                + materialProjDocket.getReceivedEmpEntity().getLastName());
        materialLedgerResTo.setIssuerEmpName(materialProjDocket.getIssuedEmpEntity().getFirstName() + " "
                + materialProjDocket.getIssuedEmpEntity().getLastName());
        return materialLedgerResTo;
    }

    @Override
    public List<MaterialLedgerResTo> getStockPiledDetails(MaterialFilterReq materialFilterReq) {
        List<MaterialLedgerResTo> materialLedgerResToList = new ArrayList<MaterialLedgerResTo>();
        List<Object[]> supplierDockets = getSupplyDeliveryDockets(materialFilterReq);
        supplierDockets.forEach((supplierDocket) -> {
            MaterialPODeliveryDocketEntity materialPoDeliveryDocket = materialDeliveryDocketRepository
                    .findOne(((Long) supplierDocket[12]));
            if (materialPoDeliveryDocket.getStockId() != null && !(Boolean) supplierDocket[15]) {
                StockMstrEntity stockMstrEntity = materialPoDeliveryDocket.getStockId();
                if (stockMstrEntity.getCategory().equalsIgnoreCase("Stockpile"))
                    materialLedgerResToList.add(getDeliverySupplyLedgerResTo(supplierDocket));
            } else if (!(Boolean) supplierDocket[15]) {
                ProjStoreStockMstrEntity projStockEntity = materialPoDeliveryDocket.getProjStockId();
                if (projStockEntity.getCategory().equalsIgnoreCase("Stockpile"))
                    materialLedgerResToList.add(getDeliverySupplyLedgerResTo(supplierDocket));
            }
        });
        List<Object[]> deliveryDockets = getMaterialDeliveryDockets(materialFilterReq);
        deliveryDockets.forEach((deliveryDocket) -> {
            MaterialProjDocketEntity materialProjDocket = materialProjDocketRepository
                    .findOne(((Long) deliveryDocket[11]));
            if (materialProjDocket.getToStockId() != null) {
                StockMstrEntity stockMstrEntity = materialProjDocket.getToStockId();
                if (stockMstrEntity.getCategory().equalsIgnoreCase("Stockpile"))
                    materialLedgerResToList.add(getDeliveryLedgerResTo(deliveryDocket));
            } else {
                ProjStoreStockMstrEntity projStockEntity = materialProjDocket.getToProjStockId();
                if (projStockEntity.getCategory().equalsIgnoreCase("Stockpile"))
                    materialLedgerResToList.add(getDeliveryLedgerResTo(deliveryDocket));
            }
        });

        materialLedgerResToList.sort(Comparator.comparing(MaterialLedgerResTo::getDate));
        populateCumulativeQty(materialLedgerResToList);
        return materialLedgerResToList;
    }

    private void populateCumulativeQty(List<MaterialLedgerResTo> materialList) {
        Map<String, Long> cumulativeQty = new HashMap<String, Long>();
        materialList.forEach((material) -> {
            Long locationId = (material.getStockId() == null) ? material.getProjStockId() : material.getStockId();
            String key = material.getMaterialClassId() + "-" + locationId;
            String suppliedOrIssued = (material.getSupplied() == null) ? material.getIssued() : material.getSupplied();
            if (cumulativeQty.containsKey(key)) {
                material.setCumulativeSupply((cumulativeQty.get(key) + Long.valueOf(suppliedOrIssued)));
                cumulativeQty.put(key, Long.valueOf(material.getCumulativeSupply()));
            } else {
                material.setCumulativeSupply(Long.valueOf(suppliedOrIssued));
                cumulativeQty.put(key, Long.valueOf(material.getCumulativeSupply()));
            }
        });
    }

    @Override
    public MaterialPODeliveryDocketEntity getMaterialProjEntity(Long id) {
        return materialDeliveryDocketRepository.findOne(id);
    }
}
