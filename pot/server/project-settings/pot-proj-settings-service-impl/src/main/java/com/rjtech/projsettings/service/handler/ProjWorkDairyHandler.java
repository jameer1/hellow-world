package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjWorkDairyMstrTO;
import com.rjtech.projsettings.model.WorkDairyNormalTimeEntity;
import com.rjtech.projsettings.repository.ProjWorkDairyRepository;

public class ProjWorkDairyHandler {

    public static ProjWorkDairyMstrTO convertEntityToPOJO(WorkDairyNormalTimeEntity entity) {
        ProjWorkDairyMstrTO projWorkDairyMstrTO = new ProjWorkDairyMstrTO();

        ProjMstrEntity projMstrEntity = entity.getProjId();
        projWorkDairyMstrTO.setId(entity.getId());
        if (null != projMstrEntity) {
            projWorkDairyMstrTO.setProjId(projMstrEntity.getProjectId());
        }
        projWorkDairyMstrTO.setCutOffDays(entity.getCutOffDays());
        projWorkDairyMstrTO.setCutOffTime(entity.getCutOffTime());
        projWorkDairyMstrTO.setApprType(entity.getApprType());
        projWorkDairyMstrTO.setApprTypeId(entity.getApprTypeId());
        projWorkDairyMstrTO.setDefaultStatus(entity.getDefaultStatus());
        projWorkDairyMstrTO.setCutOffHours(entity.getCutOffHours());
        projWorkDairyMstrTO.setCutOffMinutes(entity.getCutOffMinutes());

        projWorkDairyMstrTO.setStatus(entity.getStatus());

        return projWorkDairyMstrTO;

    }

    public static List<WorkDairyNormalTimeEntity> convertPOJOToEntity(List<ProjWorkDairyMstrTO> projWorkDairyMstrTOs,
            EPSProjRepository epsProjRepository) {
        List<WorkDairyNormalTimeEntity> projWorkDairyMstrEntities = new ArrayList<WorkDairyNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        WorkDairyNormalTimeEntity entity = null;
        for (ProjWorkDairyMstrTO projWorkDairyMstrTO : projWorkDairyMstrTOs) {
            entity = convertPOJOToEntity(projWorkDairyMstrTO, epsProjRepository);
            projWorkDairyMstrEntities.add(entity);
        }
        return projWorkDairyMstrEntities;
    }

    public static WorkDairyNormalTimeEntity convertPOJOToEntity(ProjWorkDairyMstrTO projWorkDairyMstrTO,
            EPSProjRepository epsProjRepository) {
        WorkDairyNormalTimeEntity entity = new WorkDairyNormalTimeEntity();

        if (CommonUtil.isNonBlankLong(projWorkDairyMstrTO.getId())) {
            entity.setId(projWorkDairyMstrTO.getId());
        }

        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projWorkDairyMstrTO.getProjId());

        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }
        if (CommonUtil.isNonBlankInteger(projWorkDairyMstrTO.getCutOffDays())) {
            entity.setCutOffDays(projWorkDairyMstrTO.getCutOffDays());
        } else {
            entity.setCutOffDays(0);
        }
        entity.setCutOffTime(projWorkDairyMstrTO.getCutOffTime());
        entity.setApprType(projWorkDairyMstrTO.getApprType());
        entity.setApprTypeId(projWorkDairyMstrTO.getApprTypeId());
        entity.setDefaultStatus(projWorkDairyMstrTO.getDefaultStatus());
        if (CommonUtil.isNonBlankInteger(projWorkDairyMstrTO.getCutOffHours())) {
            entity.setCutOffHours(projWorkDairyMstrTO.getCutOffHours());
        } else {
            entity.setCutOffHours(0);
        }
        if (CommonUtil.isNonBlankInteger(projWorkDairyMstrTO.getCutOffMinutes())) {
            entity.setCutOffMinutes(projWorkDairyMstrTO.getCutOffMinutes());
        } else {
            entity.setCutOffMinutes(0);
        }

        entity.setStatus(projWorkDairyMstrTO.getStatus());
        return entity;
    }

}
