package com.rjtech.procurement.service.handler;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.dto.ManpowerInvoiceDocketItemTO;
import com.rjtech.procurement.dto.MaterialInvoiceDocketItemTO;
import com.rjtech.procurement.dto.PlantInvoiceDocketItemTO;
import com.rjtech.procurement.dto.SowInvoiceDocketItemTO;
import com.rjtech.procurement.model.EmpProjRegisterPODtlEntityCopy;
import com.rjtech.procurement.model.EmpProjRigisterEntityCopy;
import com.rjtech.procurement.model.MaterialPODeliveryDocketEntityCopy;
import com.rjtech.procurement.model.MaterialProjDtlEntityCopy;
import com.rjtech.procurement.model.PlantProjPODtlEntityCopy;
import com.rjtech.procurement.model.PlantRegisterDtlEntityCopy;
import com.rjtech.procurement.model.MapowerInvoiceDocketItemEntity;
import com.rjtech.procurement.model.MaterialInvoiceDocketItemEntity;
import com.rjtech.procurement.model.PlantInvoiceDocketItemEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
import com.rjtech.procurement.model.SowInvoiceDocketItemEntity;
import com.rjtech.procurement.repository.CopyEmpProjRegisterRepository;
import com.rjtech.procurement.repository.CopyEmpProjectPODtlRepository;
import com.rjtech.procurement.repository.CopyMaterialDeliveryDocketRepository;
import com.rjtech.procurement.repository.CopyMaterialProjRepository;
import com.rjtech.procurement.repository.CopyPlantProjPORepository;
import com.rjtech.procurement.repository.CopyPlantRegisterRepository;
import com.rjtech.procurement.repository.PurchaseOrderRepository;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.copy.ProjSOWItemEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;

public class InvoiceDocketItemHandler {

    public static MapowerInvoiceDocketItemEntity convertPOJOToEntity(ManpowerInvoiceDocketItemTO invoiceDocketItemTO,
            CopyEmpProjRegisterRepository copyEmpProjRegisterRepository, EPSProjRepository epsProjRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            CopyEmpProjectPODtlRepository copyEmpProjectPODtlRepository) {
        MapowerInvoiceDocketItemEntity entity = new MapowerInvoiceDocketItemEntity();
        if (CommonUtil.isNonBlankLong(invoiceDocketItemTO.getId())) {
            entity.setId(invoiceDocketItemTO.getId());
        }

        EmpProjRigisterEntityCopy copyEmpProjRigisterEntity = copyEmpProjRegisterRepository
                .findOne(invoiceDocketItemTO.getEmpId());
        if (null != copyEmpProjRigisterEntity) {
            entity.setEmpId(copyEmpProjRigisterEntity);
        }

        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(invoiceDocketItemTO.getProjId());
        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }

        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(invoiceDocketItemTO.getPurId());
        if (null != purchaseOrderEntity) {
            entity.setPurId(purchaseOrderEntity);
        }

        EmpProjRegisterPODtlEntityCopy copyEmpProjRegisterPODtlEntity = copyEmpProjectPODtlRepository
                .findOne(invoiceDocketItemTO.getEmpPOId());
        if (null != copyEmpProjRegisterPODtlEntity) {
            entity.setEmpPOId(copyEmpProjRegisterPODtlEntity);
        }

        return entity;
    }

    public static PlantInvoiceDocketItemEntity convertPOJOToEntity(PlantInvoiceDocketItemTO invoiceDocketItemTO,
            EPSProjRepository epsProjRepository, PurchaseOrderRepository purchaseOrderRepository,
            CopyPlantProjPORepository copyPlantProjPORepository,
            CopyPlantRegisterRepository copyPlantRegisterRepository) {
        PlantInvoiceDocketItemEntity entity = new PlantInvoiceDocketItemEntity();
        if (CommonUtil.isNonBlankLong(invoiceDocketItemTO.getId())) {
            entity.setId(invoiceDocketItemTO.getId());
        }

        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(invoiceDocketItemTO.getProjId());
        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }

        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(invoiceDocketItemTO.getPurId());
        if (null != purchaseOrderEntity) {
            entity.setPurId(purchaseOrderEntity);
        }

        PlantProjPODtlEntityCopy copyPlantProjPODtlEntity = copyPlantProjPORepository
                .findOne(invoiceDocketItemTO.getPlantPOId());
        if (null != copyPlantProjPODtlEntity) {
            entity.setPlantPOId(copyPlantProjPODtlEntity);
        }

        PlantRegisterDtlEntityCopy copyPlantRegisterDtlEntity = copyPlantRegisterRepository
                .findOne(invoiceDocketItemTO.getPlantId());
        if (null != copyPlantRegisterDtlEntity) {
            entity.setPlantId(copyPlantRegisterDtlEntity);
        }

        return entity;
    }

    public static MaterialInvoiceDocketItemEntity convertPOJOToEntity(MaterialInvoiceDocketItemTO invoiceDocketItemTO,
            EPSProjRepository epsProjRepository, PurchaseOrderRepository purchaseOrderRepository,
            CopyMaterialProjRepository copyMaterialProjRepository,
            CopyMaterialDeliveryDocketRepository copyMaterialDeliveryDocketRepository) {
        MaterialInvoiceDocketItemEntity entity = new MaterialInvoiceDocketItemEntity();
        if (CommonUtil.isNonBlankLong(invoiceDocketItemTO.getId())) {
            entity.setId(invoiceDocketItemTO.getId());
        }

        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(invoiceDocketItemTO.getProjId());
        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }

        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(invoiceDocketItemTO.getPurId());
        if (null != purchaseOrderEntity) {
            entity.setPurId(purchaseOrderEntity);
        }

        MaterialProjDtlEntityCopy copyMaterialProjDtlEntity = copyMaterialProjRepository
                .findOne(invoiceDocketItemTO.getMatId());
        if (null != copyMaterialProjDtlEntity) {
            entity.setMaterialId(copyMaterialProjDtlEntity);
        }

        MaterialPODeliveryDocketEntityCopy copyMaterialPODeliveryDocketEntity = copyMaterialDeliveryDocketRepository
                .findOne(invoiceDocketItemTO.getMatPOId());
        if (null != copyMaterialPODeliveryDocketEntity) {
            entity.setMaterialPOId(copyMaterialPODeliveryDocketEntity);
        }

        return entity;
    }

    public static SowInvoiceDocketItemEntity convertPOJOToEntity(SowInvoiceDocketItemTO invoiceDocketItemTO,
            PurchaseOrderRepository purchaseOrderRepository, EPSProjRepository epsProjRepository,
            ProjSOWItemRepositoryCopy projSOWItemRepository) {
        SowInvoiceDocketItemEntity entity = new SowInvoiceDocketItemEntity();
        if (CommonUtil.isNonBlankLong(invoiceDocketItemTO.getId())) {
            entity.setId(invoiceDocketItemTO.getId());
        }
        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(invoiceDocketItemTO.getPurId());
        if (null != purchaseOrderEntity) {
            entity.setPurId(purchaseOrderEntity);
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(invoiceDocketItemTO.getProjId());

        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }
        ProjSOWItemEntity projSOWItemEntity = projSOWItemRepository.findOne(invoiceDocketItemTO.getSowId());
        if (null != projSOWItemEntity) {
            entity.setSowId(projSOWItemEntity);
        }
        entity.setStatus(invoiceDocketItemTO.getStatus());
        return entity;
    }
}
