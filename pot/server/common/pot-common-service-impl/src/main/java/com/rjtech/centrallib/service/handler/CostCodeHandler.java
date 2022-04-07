package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.CostCodeTO;
import com.rjtech.centrallib.model.CostMstrEntity;
import com.rjtech.centrallib.req.CostCodeSaveReq;
import com.rjtech.centrallib.resp.CostCodeResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class CostCodeHandler {

    public static CostCodeResp convertEntityToPOJO(List<CostMstrEntity> entities) {
        CostCodeResp costCodeResp = new CostCodeResp();
        CostCodeTO classificationTO = null;
        for (CostMstrEntity entity : entities) {
            classificationTO = convertEntityToPOJO(entity);
            costCodeResp.getCostCodeTOs().add(classificationTO);
        }
        return costCodeResp;
    }

    public static CostCodeTO convertEntityToPOJO(CostMstrEntity entity) {
        CostCodeTO classificationTO;
        classificationTO = new CostCodeTO();
        classificationTO.setId(entity.getId());
        classificationTO.setCode(entity.getCode());
        classificationTO.setName(entity.getName());

        classificationTO.setStatus(entity.getStatus());

        return classificationTO;
    }

    public static List<CostMstrEntity> convertPOJOToEntity(CostCodeSaveReq costCodeSaveReq) {
        List<CostMstrEntity> CostMstrEntitys = new ArrayList<CostMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        CostMstrEntity CostMstrEntity = null;
        for (CostCodeTO costCodeTO : costCodeSaveReq.getCostCodeTOs()) {
            CostMstrEntity = new CostMstrEntity();
            if (CommonUtil.isNonBlankLong(costCodeTO.getId())) {
                CostMstrEntity.setId(costCodeTO.getId());
            }
            CostMstrEntity.setCode(costCodeTO.getCode());
            CostMstrEntity.setName(costCodeTO.getName());

            CostMstrEntity.setStatus(costCodeTO.getStatus());

            CostMstrEntitys.add(CostMstrEntity);
        }
        return CostMstrEntitys;
    }

}
