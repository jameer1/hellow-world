package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjProcurementTO;
import com.rjtech.projsettings.model.ProcurementNormalTimeEntity;

public class ProjProcurementHandler {

    public static ProjProcurementTO convertEntityToPOJO(ProcurementNormalTimeEntity entity) {
        ProjProcurementTO projProcurementTO = new ProjProcurementTO();
        projProcurementTO.setId(entity.getId());
        projProcurementTO.setProcureType(entity.getProcureType());
        projProcurementTO.setCutOffTime(entity.getCutOffTime());
        projProcurementTO.setCutOffDays(entity.getCutOffDays());
        ProjMstrEntity project = entity.getProjId();
        if (project != null)
            projProcurementTO.setProjId(project.getProjectId());
        projProcurementTO.setDefaultStatus(entity.getDefaultStatus());
        projProcurementTO.setCutOffHours(entity.getCutOffHours());
        projProcurementTO.setCutOffMinutes(entity.getCutOffMinutes());

        projProcurementTO.setStatus(entity.getStatus());
        return projProcurementTO;

    }

    public static List<ProcurementNormalTimeEntity> convertPOJOToEntity(List<ProjProcurementTO> projProcurementTOs,
            EPSProjRepository epsProjRepository) {
        List<ProcurementNormalTimeEntity> projProcurementEntites = new ArrayList<ProcurementNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjProcurementTO projProcurementTO : projProcurementTOs) {

            projProcurementEntites.add(convertOnePojoToOneEntity(projProcurementTO, epsProjRepository));
        }
        return projProcurementEntites;
    }

    public static ProcurementNormalTimeEntity convertOnePojoToOneEntity(ProjProcurementTO projProcurementTO,
            EPSProjRepository epsProjRepository) {
        ProcurementNormalTimeEntity entity = new ProcurementNormalTimeEntity();

        if (CommonUtil.isNonBlankLong(projProcurementTO.getId())) {
            entity.setId(projProcurementTO.getId());
        }
        entity.setProcureType(projProcurementTO.getProcureType());
        entity.setCutOffTime(projProcurementTO.getCutOffTime());
        if (CommonUtil.isNonBlankInteger(projProcurementTO.getCutOffDays())) {
            entity.setCutOffDays(projProcurementTO.getCutOffDays());
        } else {
            entity.setCutOffDays(0);
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projProcurementTO.getProjId());

        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }
        entity.setDefaultStatus(projProcurementTO.getDefaultStatus());
        if (CommonUtil.isNonBlankInteger(projProcurementTO.getCutOffHours())) {
            entity.setCutOffHours(projProcurementTO.getCutOffHours());
        } else {
            entity.setCutOffHours(0);
        }
        if (CommonUtil.isNonBlankInteger(projProcurementTO.getCutOffMinutes())) {
            entity.setCutOffMinutes(projProcurementTO.getCutOffMinutes());
        } else {
            entity.setCutOffMinutes(0);
        }

        entity.setStatus(projProcurementTO.getStatus());
        return entity;
    }

}
