package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.centrallib.dto.StockAndStoreTO;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.req.StockSaveReq;
import com.rjtech.centrallib.resp.StockResp;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class StockHandler {

    public static StockResp convertEntityToPOJO(List<StockMstrEntity> entities) {
        StockResp stockResp = new StockResp();
        StockAndStoreTO classificationTO = null;
        for (StockMstrEntity entity : entities) {
            classificationTO = convertEntityToPOJO(entity);
            stockResp.getStockAndStoreTOs().add(classificationTO);
        }
        return stockResp;
    }

    public static StockAndStoreTO convertEntityToPOJO(StockMstrEntity entity) {
        StockAndStoreTO classificationTO = new StockAndStoreTO();
        classificationTO.setId(entity.getId());
        ClientRegEntity clientRegEntity = entity.getClientId();
        if (null != clientRegEntity) {
            classificationTO.setClientId(clientRegEntity.getClientId());
        }
        classificationTO.setCode(entity.getCode());
        classificationTO.setDesc(entity.getName());
        classificationTO.setCategory(entity.getCategory());

        classificationTO.setStatus(entity.getStatus());
        return classificationTO;
    }

    public static List<StockMstrEntity> convertPOJOToEntity(StockSaveReq stockSaveReq) {
        List<StockMstrEntity> stockMstrEntitys = new ArrayList<StockMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        StockMstrEntity stockMstrEntity = null;
        for (StockAndStoreTO stockAndStoreTO : stockSaveReq.getStockAndStoreTOs()) {
            stockMstrEntity = convertPOJOToEntity(stockAndStoreTO);
            stockMstrEntitys.add(stockMstrEntity);
        }
        return stockMstrEntitys;
    }

    public static StockMstrEntity convertPOJOToEntity(StockAndStoreTO stockAndStoreTO) {
        StockMstrEntity stockMstrEntity = new StockMstrEntity();
        if (CommonUtil.isNonBlankLong(stockAndStoreTO.getId())) {
            stockMstrEntity.setId(stockAndStoreTO.getId());
        }
        stockMstrEntity.setCode(stockAndStoreTO.getCode());
        stockMstrEntity.setName(stockAndStoreTO.getDesc());
        stockMstrEntity.setCategory(stockAndStoreTO.getCategory());

        stockMstrEntity.setStatus(stockAndStoreTO.getStatus());
        return stockMstrEntity;
    }

}
