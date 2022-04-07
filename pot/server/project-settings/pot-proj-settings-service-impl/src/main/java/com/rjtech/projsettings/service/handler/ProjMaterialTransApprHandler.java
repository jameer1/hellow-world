package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjMaterialTransApprTO;
import com.rjtech.projsettings.model.MaterialTransAddtionalTimeApprEntity;

public class ProjMaterialTransApprHandler {

    public static List<MaterialTransAddtionalTimeApprEntity> convertPOJOToEntity(
            List<ProjMaterialTransApprTO> projMaterialTransApprTOs) {
        List<MaterialTransAddtionalTimeApprEntity> projMaterialTransApprEntites = new ArrayList<MaterialTransAddtionalTimeApprEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        MaterialTransAddtionalTimeApprEntity entity = null;
        for (ProjMaterialTransApprTO projMaterialTransApprTO : projMaterialTransApprTOs) {
            entity = new MaterialTransAddtionalTimeApprEntity();

            if (CommonUtil.isNonBlankLong(projMaterialTransApprTO.getId())) {
                entity.setId(projMaterialTransApprTO.getId());
            }
            entity.setLatest(true);
            if (CommonUtil.isNotBlankStr(projMaterialTransApprTO.getRequisitionDate())) {
                entity.setRequisitionDate(CommonUtil.convertStringToDate(projMaterialTransApprTO.getRequisitionDate()));
            }

            entity.setStatus(projMaterialTransApprTO.getStatus());
            projMaterialTransApprEntites.add(entity);
        }
        return projMaterialTransApprEntites;
    }

}
