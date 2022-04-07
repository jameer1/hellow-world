package com.rjtech.register.service.handler.material;

import java.math.BigDecimal;

import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.register.material.model.MaterialSchLocCountEntity;
import com.rjtech.register.repository.material.CopyProjStoreStockRepository;

public class MaterialSchLocationHandler {
    public static void convertPOJOToEntity(MaterialSchLocCountEntity entity, BigDecimal totalQty, BigDecimal avlQty,
            LabelKeyTO stockLabelKeyTO, LabelKeyTO projStockLabelKeyTO, StockRepository stockRepository,
            CopyProjStoreStockRepository projStoreStockRepository) {

        if (CommonUtil.objectNotNull(stockLabelKeyTO)) {
            StockMstrEntity stockMstrEntity = stockRepository.findOne(stockLabelKeyTO.getId());
            if (null != stockMstrEntity) {
                entity.setStockId(stockMstrEntity);
            }
            entity.setStockCode(stockLabelKeyTO.getCode());
        }

        if (CommonUtil.objectNotNull(projStockLabelKeyTO)) {
            ProjStoreStockMstrEntity projStoreStockMstrEntity = projStoreStockRepository
                    .findOne(projStockLabelKeyTO.getId());
            if (null != projStoreStockMstrEntity) {
                entity.setProjStockId(projStoreStockMstrEntity);
            }
            entity.setProjStockCode(projStockLabelKeyTO.getCode());
        }

        entity.setTotalQty(totalQty);
        entity.setAvlQty(avlQty);

    }

}
