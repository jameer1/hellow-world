package com.rjtech.projsettings.service.handler;

import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projsettings.dto.ResourceCurveTO;

public class ProjResourceCurveHandler {
    public static ResourceCurveTO convertEntityToPOJO(ResourceCurveEntity entity) {
        ResourceCurveTO projResourceCurveTO = new ResourceCurveTO();
        projResourceCurveTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getClientId()))
            projResourceCurveTO.setClientId(entity.getClientId().getClientId());
        projResourceCurveTO.setCurveType(entity.getCurveType());
        projResourceCurveTO.setStatus(entity.getStatus());
        return projResourceCurveTO;
    }

}
