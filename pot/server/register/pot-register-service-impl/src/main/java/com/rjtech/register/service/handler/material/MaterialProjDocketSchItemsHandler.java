package com.rjtech.register.service.handler.material;

import java.math.BigDecimal;

import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.register.material.dto.MaterialProjSchItemTO;
import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntity;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.material.model.MaterialSchLocCountEntity;
import com.rjtech.register.repository.material.MaterialProjRepository;

public class MaterialProjDocketSchItemsHandler {

    public static MaterialProjSchItemTO convertEntityToPOJO(MaterialProjDocketSchItemsEntity entity) {
        MaterialProjSchItemTO schItemTO = new MaterialProjSchItemTO();
        schItemTO.setId(entity.getId());

        if ( entity.getAvailableQty() == null || !entity.isWorkDairyEntry() ) {
            BigDecimal qty = ( entity.getQty() == null ) ? new BigDecimal("0") : entity.getQty();
			schItemTO.setWorkDairyAvlQty( qty.doubleValue() );
        } else {
            schItemTO.setWorkDairyAvlQty(entity.getAvailableQty().doubleValue());
        }
        MaterialProjDtlHandler.getSchItem(entity.getMaterialProjDtlEntity(), schItemTO);
        schItemTO.setProjDockId(entity.getMaterialProjDocketEntity().getId());
        StockMstrEntity stockMstrEntity = entity.getMaterialProjDocketEntity().getOriginStockId();
        Long stockId = null;
        if (CommonUtil.objectNotNull(stockMstrEntity)) {
            stockId = stockMstrEntity.getId();
            schItemTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().put("stockId", stockId.toString());
        }
        Long projStockId = null;
        ProjStoreStockMstrEntity projStoreStockMstr = entity.getMaterialProjDocketEntity().getOriginProjStockId();
        if (CommonUtil.objectNotNull(projStoreStockMstr)) {
            projStockId = projStoreStockMstr.getId();
            schItemTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().put("projStockId", projStockId.toString());
        }

        for (MaterialSchLocCountEntity materialSchLocCountEntity : entity.getMaterialProjDtlEntity()
                .getMaterialSchLocCountEntities()) {
            if (CommonUtil.objectNotNull(materialSchLocCountEntity.getStockId())
                    && CommonUtil.isNonBlankLong(materialSchLocCountEntity.getStockId().getId())
                    && CommonUtil.objectNotNull(stockId)) {
                if (materialSchLocCountEntity.getStockId().getId().equals(stockId)) {
                    schItemTO.setCurrentAvaiableQty(materialSchLocCountEntity.getAvlQty());
                    schItemTO.setTotalQty(materialSchLocCountEntity.getTotalQty());
                    break;
                }
            } else if (CommonUtil.objectNotNull(materialSchLocCountEntity.getProjStockId())
                    && CommonUtil.isNonBlankLong(materialSchLocCountEntity.getProjStockId().getId())
                    && CommonUtil.objectNotNull(projStockId)) {
                if (materialSchLocCountEntity.getProjStockId().getId().equals(projStockId)) {
                    schItemTO.setCurrentAvaiableQty(materialSchLocCountEntity.getAvlQty());
                    schItemTO.setTotalQty(materialSchLocCountEntity.getTotalQty());
                    break;
                }
            }
        }
        schItemTO.setOpeningStock(entity.getOpeningStock());
        schItemTO.setClosingStock(entity.getClosingStock());
        schItemTO.setIssueQty(entity.getQty());
        schItemTO.setComments(entity.getComments());
        schItemTO.setStatus(entity.getStatus());
        schItemTO.setTransitQty(entity.getTransitQty());
        return schItemTO;
    }

    public static void convertPOJOToEntity(MaterialProjDocketSchItemsEntity entity, MaterialProjSchItemTO schItemTO,
            MaterialProjRepository materialProjRepository) {
        if (CommonUtil.isNonBlankLong(schItemTO.getId())) {
            entity.setId(schItemTO.getId());
        }
        MaterialProjDtlEntity materialProjDtlEntity = materialProjRepository.findOne(schItemTO.getSchItemId());
        if (null != materialProjDtlEntity) {
            entity.setMaterialProjDtlEntity(materialProjDtlEntity);
        }
        entity.setOpeningStock(schItemTO.getOpeningStock());
        entity.setClosingStock(schItemTO.getClosingStock());
        entity.setAvailableQty(new BigDecimal(schItemTO.getWorkDairyAvlQty()));
        entity.setQty(schItemTO.getIssueQty());
        entity.setComments(schItemTO.getComments());
        if (entity.getAvailableQty() == null)
            entity.setAvailableQty(schItemTO.getIssueQty());
        entity.setStatus((schItemTO.getStatus() == null) ? 1 : schItemTO.getStatus());
    }
}
