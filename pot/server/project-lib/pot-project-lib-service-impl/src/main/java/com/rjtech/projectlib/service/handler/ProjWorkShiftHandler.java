package com.rjtech.projectlib.service.handler;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjWorkShiftTO;
import com.rjtech.projectlib.model.ProjWorkShiftMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;

public class ProjWorkShiftHandler {

    public static ProjWorkShiftTO convertEntityToPOJO(ProjWorkShiftMstrEntity entity) {
        ProjWorkShiftTO projWorkShiftTO = null;
        projWorkShiftTO = new ProjWorkShiftTO();
        projWorkShiftTO.setId(entity.getId());
        projWorkShiftTO.setCode(entity.getCode());
        projWorkShiftTO.setStartTime(entity.getStartTime());
        projWorkShiftTO.setFinishTime(entity.getFinishTime());

        projWorkShiftTO.setStartHours(entity.getStartHours());
        projWorkShiftTO.setStartMinutes(entity.getStartMinutes());
        projWorkShiftTO.setFinishHours(entity.getFinishHours());
        projWorkShiftTO.setFinishMinutes(entity.getFinishMinutes());

        ProjMstrEntity project = entity.getProjectId();
        if (project != null)
            projWorkShiftTO.setProjectId(project.getProjectId());

        projWorkShiftTO.setStatus(entity.getStatus());

        return projWorkShiftTO;
    }

    public static ProjWorkShiftMstrEntity convertPOJOToEntity(ProjWorkShiftTO projWorkShiftTO,
            EPSProjRepository epsProjRepository) {
        ProjWorkShiftMstrEntity entity = null;
        entity = new ProjWorkShiftMstrEntity();
        if (CommonUtil.isNonBlankLong(projWorkShiftTO.getId())) {
            entity.setId(projWorkShiftTO.getId());
        }
        entity.setProjectId(epsProjRepository.findOne(projWorkShiftTO.getProjectId()));
        entity.setCode(projWorkShiftTO.getCode());
        entity.setStartTime(projWorkShiftTO.getStartTime());
        entity.setFinishTime(projWorkShiftTO.getFinishTime());

        entity.setStartHours(projWorkShiftTO.getStartHours());
        entity.setStartMinutes(projWorkShiftTO.getStartMinutes());
        entity.setFinishHours(projWorkShiftTO.getFinishHours());
        entity.setFinishMinutes(projWorkShiftTO.getFinishMinutes());

        entity.setStatus(projWorkShiftTO.getStatus());

        return entity;
    }

}
