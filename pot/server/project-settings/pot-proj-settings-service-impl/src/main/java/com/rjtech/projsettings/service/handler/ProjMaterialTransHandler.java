package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjMaterialTransTO;
import com.rjtech.projsettings.model.MaterialTransNormalTimeEntity;

public class ProjMaterialTransHandler {

    public static ProjMaterialTransTO convertEntityToPOJO(MaterialTransNormalTimeEntity entity) {
        ProjMaterialTransTO projMaterialTransTO = new ProjMaterialTransTO();

        projMaterialTransTO.setId(entity.getId());
        ProjMstrEntity projMstrEntity = entity.getProjId();

        if (null != projMstrEntity) {
            projMaterialTransTO.setProjId(projMstrEntity.getProjectId());
        }
        projMaterialTransTO.setMaterialType(entity.getMaterialType());
        if (CommonUtil.isZeroOrGreater(entity.getCutOffDays())) {
            projMaterialTransTO.setCutOffDays(entity.getCutOffDays());
        }
        projMaterialTransTO.setCutOffTime(entity.getCutOffTime());
        projMaterialTransTO.setDefaultStatus(entity.getDefaultStatus());
        if (CommonUtil.isZeroOrGreater(entity.getCutOffHours())) {
            projMaterialTransTO.setCutOffHours(entity.getCutOffHours());
        }
        if (CommonUtil.isZeroOrGreater(entity.getCutOffMinutes())) {
            projMaterialTransTO.setCutOffMinutes(entity.getCutOffMinutes());
        }
        projMaterialTransTO.setStatus(entity.getStatus());

        return projMaterialTransTO;

    }

    public static List<MaterialTransNormalTimeEntity> convertPOJOToEntity(
            List<ProjMaterialTransTO> projMaterialTransTOs, EPSProjRepository epsProjRepository) {
        List<MaterialTransNormalTimeEntity> projMaterialTransEntites = new ArrayList<MaterialTransNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        for (ProjMaterialTransTO projMaterialTransTO : projMaterialTransTOs) {

            convertOnePojoToOneEntity(projMaterialTransTO, epsProjRepository);

            projMaterialTransEntites.add(convertOnePojoToOneEntity(projMaterialTransTO, epsProjRepository));
        }
        return projMaterialTransEntites;
    }

    public static MaterialTransNormalTimeEntity convertOnePojoToOneEntity(ProjMaterialTransTO projMaterialTransTO,
            EPSProjRepository epsProjRepository) {

        MaterialTransNormalTimeEntity entity = new MaterialTransNormalTimeEntity();

        if (CommonUtil.isNonBlankLong(projMaterialTransTO.getId())) {
            entity.setId(projMaterialTransTO.getId());
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projMaterialTransTO.getProjId());
        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }

        entity.setMaterialType(projMaterialTransTO.getMaterialType());
        if (CommonUtil.isNonBlankInteger(projMaterialTransTO.getCutOffDays())) {
            entity.setCutOffDays(projMaterialTransTO.getCutOffDays());
        } else {
            entity.setCutOffDays(0);
        }
        entity.setCutOffTime(projMaterialTransTO.getCutOffTime());
        entity.setDefaultStatus(projMaterialTransTO.getDefaultStatus());
        if (CommonUtil.isNonBlankInteger(projMaterialTransTO.getCutOffHours())) {
            entity.setCutOffHours(projMaterialTransTO.getCutOffHours());
        } else {
            entity.setCutOffHours(0);
        }
        if (CommonUtil.isNonBlankInteger(projMaterialTransTO.getCutOffMinutes())) {
            entity.setCutOffMinutes(projMaterialTransTO.getCutOffMinutes());
        } else {
            entity.setCutOffMinutes(0);
        }
        entity.setStatus(projMaterialTransTO.getStatus());
        return entity;
    }

}
