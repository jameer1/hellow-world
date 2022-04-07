package com.rjtech.common.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ResourceCurveTO;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class ResourceCurveHandler {

    public static ResourceCurveTO convertEntityToPOJO(ResourceCurveEntity entity) {
        ResourceCurveTO resourceCurveTO = new ResourceCurveTO();

        resourceCurveTO.setId(entity.getId());
        ClientRegEntity client = entity.getClientId();
        if (client != null)
            resourceCurveTO.setClientId(client.getClientId());
        resourceCurveTO.setDefaultFlag(entity.isDefaultFlag());
        resourceCurveTO.setCurveType(entity.getCurveType());
        resourceCurveTO.setCatg(entity.getCatg());
        resourceCurveTO.setRange1(entity.getRange1());
        resourceCurveTO.setRange2(entity.getRange2());
        resourceCurveTO.setRange3(entity.getRange3());
        resourceCurveTO.setRange4(entity.getRange4());
        resourceCurveTO.setRange5(entity.getRange5());
        resourceCurveTO.setRange6(entity.getRange6());
        resourceCurveTO.setRange7(entity.getRange7());
        resourceCurveTO.setRange8(entity.getRange8());
        resourceCurveTO.setRange9(entity.getRange9());
        resourceCurveTO.setRange10(entity.getRange10());
        resourceCurveTO.setTotal(entity.getTotal());

        resourceCurveTO.setStatus(entity.getStatus());
        return resourceCurveTO;

    }

    public static List<ResourceCurveEntity> convertPOJOsToEntitys(List<ResourceCurveTO> resourceCurveTOs) {
        List<ResourceCurveEntity> resourceCurveEntites = new ArrayList<ResourceCurveEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        for (ResourceCurveTO resourceCurveTO : resourceCurveTOs) {
            resourceCurveEntites.add(convertPOJOTOEntity(resourceCurveTO));
        }
        return resourceCurveEntites;
    }

    public static ResourceCurveEntity convertPOJOTOEntity(ResourceCurveTO resourceCurveTO) {

        ResourceCurveEntity entity = new ResourceCurveEntity();
        if (CommonUtil.isNonBlankLong(resourceCurveTO.getId())) {
            entity.setId(resourceCurveTO.getId());
        }

        entity.setCurveType(resourceCurveTO.getCurveType());
        entity.setCatg(resourceCurveTO.getCatg());
        entity.setRange1(resourceCurveTO.getRange1());
        entity.setRange2(resourceCurveTO.getRange2());
        entity.setRange3(resourceCurveTO.getRange3());
        entity.setRange4(resourceCurveTO.getRange4());
        entity.setRange5(resourceCurveTO.getRange5());
        entity.setRange6(resourceCurveTO.getRange6());
        entity.setRange7(resourceCurveTO.getRange7());
        entity.setRange8(resourceCurveTO.getRange8());
        entity.setRange9(resourceCurveTO.getRange9());
        entity.setRange10(resourceCurveTO.getRange10());
        entity.setTotal(resourceCurveTO.getTotal());
        entity.setStatus(resourceCurveTO.getStatus());

        return entity;

    }

}
