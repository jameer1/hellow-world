package com.rjtech.projectlib.service.handler;

import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjCrewTO;
import com.rjtech.projectlib.dto.ProjWorkShiftTO;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.projectlib.model.ProjWorkShiftMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjWorkShiftRepository;
import com.rjtech.projectlib.resp.ProjCrewResp;

public class ProjCrewHandler {

    public static ProjCrewTO convertEntityToPOJO(ProjCrewMstrEntity entity) {
        ProjCrewTO projCrewListTO = new ProjCrewTO();
        projCrewListTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getProjWorkShiftMstrEntity())) {
            projCrewListTO.setProjWorkShiftTO(convertWorkShiftEntityToPOJO(entity.getProjWorkShiftMstrEntity()));
        }
        projCrewListTO.setCode(entity.getCode());
        projCrewListTO.setDesc(entity.getDesc());
        ProjMstrEntity project = entity.getProjId();
        if (project != null)
            projCrewListTO.setProjId(project.getProjectId());
        projCrewListTO.setStatus(entity.getStatus());

        return projCrewListTO;
    }

    public static ProjCrewResp populateCrewMapResp(List<ProjCrewMstrEntity> projCrewMstrEntities) {
        ProjCrewResp projCrewResp = new ProjCrewResp();
        for (ProjCrewMstrEntity projCrewMstrEntity : projCrewMstrEntities) {
            projCrewResp.getProjCrewTOs().add(convertEntityToPOJO(projCrewMstrEntity));
        }
        return projCrewResp;
    }

    public static ProjCrewTO papulateProjCrewMap(List<ProjCrewMstrEntity> projCrewMstrEntities) {
        ProjCrewTO projCrewTO = new ProjCrewTO();
        for (ProjCrewMstrEntity projCrewMstrEntity : projCrewMstrEntities) {
            projCrewTO.getProjCrewTOs().add(convertEntityToPOJO(projCrewMstrEntity));
        }
        return projCrewTO;
    }

    private static ProjWorkShiftTO convertWorkShiftEntityToPOJO(ProjWorkShiftMstrEntity projWorkShiftMstrEntity) {
        ProjWorkShiftTO ProjWorkShiftTO = new ProjWorkShiftTO();
        ProjWorkShiftTO.setId(projWorkShiftMstrEntity.getId());
        ProjWorkShiftTO.setCode(projWorkShiftMstrEntity.getCode());

        return ProjWorkShiftTO;
    }

    public static ProjCrewMstrEntity convertPOJOToEntity(ProjCrewTO projCrewTO, EPSProjRepository projRepo,
            ProjWorkShiftRepository projWorkShiftRepository) {

        ProjCrewMstrEntity entity = new ProjCrewMstrEntity();
        if (projCrewTO.getId() != null) {
            entity.setId(projCrewTO.getId());
        }

        entity.setProjId(projRepo.findOne(projCrewTO.getProjId()));
        entity.setCode(projCrewTO.getCode());
        entity.setDesc(projCrewTO.getDesc());
        ProjWorkShiftTO shift = projCrewTO.getProjWorkShiftTO();
        if (CommonUtil.objectNotNull(shift)) {
            entity.setProjWorkShiftMstrEntity(projWorkShiftRepository.findOne(shift.getId()));
        }

        entity.setStatus(projCrewTO.getStatus());

        return entity;
    }

}
