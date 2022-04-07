package com.rjtech.register.service.handler.material;

import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.register.material.dto.MaterialPODeliveryDocketTO;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
//import com.rjtech.register.material.model.MaterialPODeliveryDocketEntityCopy;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
//import com.rjtech.register.material.model.MaterialProjDtlEntityCopy;

public class MaterialPODeliveryDocketHandlerCopy {

    public static MaterialPODeliveryDocketEntity convertPOJOToEntity(
            MaterialPODeliveryDocketTO materialPODeliveryDocketTO, StockRepository stockRepository,
            ProjStoreStockRepositoryCopy projStoreStockRepository) {
        MaterialPODeliveryDocketEntity entity = new MaterialPODeliveryDocketEntity();

        if (CommonUtil.isNonBlankLong(materialPODeliveryDocketTO.getId())) {
            entity.setId(materialPODeliveryDocketTO.getId());
        }

        entity.setDocketNumber(materialPODeliveryDocketTO.getDocketNumber());

        if (CommonUtil.isNotBlankStr(materialPODeliveryDocketTO.getSupplyDeliveryDate())) {
            entity.setSupplyDeliveryDate(
                    CommonUtil.convertStringToDate(materialPODeliveryDocketTO.getSupplyDeliveryDate()));
        }

        if (CommonUtil.isNotBlankStr(materialPODeliveryDocketTO.getDocketDate())) {
            entity.setDocketDate(CommonUtil.convertStringToDate(materialPODeliveryDocketTO.getDocketDate()));
        }

        if (CommonUtil.objectNotNull(materialPODeliveryDocketTO.getStockLabelKeyTO().getId())) {
            StockMstrEntity stockMstrEntity = stockRepository
                    .findOne(materialPODeliveryDocketTO.getStockLabelKeyTO().getId());
            entity.setStockId(stockMstrEntity);
            entity.setStockCode(stockMstrEntity.getCode());
        }

        if (CommonUtil.objectNotNull(materialPODeliveryDocketTO.getProjStockLabelKeyTO().getId())) {
            ProjStoreStockMstrEntity projStoreStockMstrEntity = projStoreStockRepository
                    .findOne(materialPODeliveryDocketTO.getProjStockLabelKeyTO().getId());
            if (null != projStoreStockMstrEntity) {
                entity.setProjStockId(projStoreStockMstrEntity);
            }
            entity.setProjStockCode(materialPODeliveryDocketTO.getProjStockLabelKeyTO().getCode());
        }
        entity.setReceivedQty(materialPODeliveryDocketTO.getReceivedQty());
        entity.setReceivedBy(materialPODeliveryDocketTO.getReceivedBy());
        entity.setDefectComments(materialPODeliveryDocketTO.getDefectComments());
        entity.setComments(materialPODeliveryDocketTO.getComments());
        entity.setSourceType(materialPODeliveryDocketTO.getSourceType());
        entity.setLocType(materialPODeliveryDocketTO.getLocType());
        entity.setStatus(materialPODeliveryDocketTO.getStatus());
        entity.setSupplierDocket(materialPODeliveryDocketTO.isSupplierDocket());
        if (CommonUtil.isNonBlankLong(materialPODeliveryDocketTO.getRegMaterialId())) {
            MaterialProjDtlEntity materialProjDtlEntity = new MaterialProjDtlEntity();
            materialProjDtlEntity.setId(materialPODeliveryDocketTO.getRegMaterialId());
            entity.setMaterialProjDtlEntity(materialProjDtlEntity);
        }

        return entity;
    }

}
