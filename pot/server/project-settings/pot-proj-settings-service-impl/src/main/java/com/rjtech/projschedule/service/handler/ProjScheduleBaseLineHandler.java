package com.rjtech.projschedule.service.handler;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projschedule.dto.ProjScheduleBaseLineTO;
import com.rjtech.projschedule.model.ProjScheduleBaseLineEntity;

public class ProjScheduleBaseLineHandler {

    public static ProjScheduleBaseLineTO convertEntityToPOJO(ProjScheduleBaseLineEntity projScheduleBaseLineEntity) {
        ProjScheduleBaseLineTO projScheduleBaseLineTO = new ProjScheduleBaseLineTO();

        projScheduleBaseLineTO.setId(projScheduleBaseLineEntity.getId());
        projScheduleBaseLineTO.setName(projScheduleBaseLineEntity.getName());
        if (CommonUtil.isNotBlankDate(projScheduleBaseLineEntity.getDate())) {
            projScheduleBaseLineTO.setDate(CommonUtil.convertDateToString(projScheduleBaseLineEntity.getDate()));
        }
        ProjMstrEntity project = projScheduleBaseLineEntity.getProjId();
        if (project != null) {
            projScheduleBaseLineTO.setCode(generateCode(projScheduleBaseLineEntity));
            projScheduleBaseLineTO.setProjId(project.getProjectId());
        }
        projScheduleBaseLineTO.setScheduleItemType(projScheduleBaseLineEntity.getScheduleItemType());
        projScheduleBaseLineTO.setScheduleType(projScheduleBaseLineEntity.getScheduleType());
        projScheduleBaseLineTO.setTimeScale(projScheduleBaseLineEntity.getTimeScale());
        projScheduleBaseLineTO.setStatus(projScheduleBaseLineEntity.getStatus());
        return projScheduleBaseLineTO;

    }

    public static ProjScheduleBaseLineEntity convertPOJOToEntity(ProjScheduleBaseLineTO projScheduleBaseLineTO,
            EPSProjRepository epsProjRepository) {
        ProjScheduleBaseLineEntity projScheduleBaseLineEntity = new ProjScheduleBaseLineEntity();
        if (CommonUtil.isNonBlankLong(projScheduleBaseLineTO.getId())) {
            projScheduleBaseLineEntity.setId(projScheduleBaseLineTO.getId());
        }
        projScheduleBaseLineEntity.setCode(projScheduleBaseLineTO.getCode());
        projScheduleBaseLineEntity.setName(projScheduleBaseLineTO.getName());
        if (CommonUtil.isNotBlankStr(projScheduleBaseLineTO.getDate())) {
            projScheduleBaseLineEntity.setDate(CommonUtil.convertStringToDate(projScheduleBaseLineTO.getDate()));
        }
        projScheduleBaseLineEntity.setProjId(epsProjRepository.findOne(projScheduleBaseLineTO.getProjId()));
        projScheduleBaseLineEntity.setScheduleItemType(projScheduleBaseLineTO.getScheduleItemType());
        projScheduleBaseLineEntity.setScheduleType(projScheduleBaseLineTO.getScheduleType());
        projScheduleBaseLineEntity.setTimeScale(projScheduleBaseLineTO.getTimeScale());
        projScheduleBaseLineEntity.setStatus(projScheduleBaseLineTO.getStatus());
        return projScheduleBaseLineEntity;
    }

    private static String generateCode(ProjScheduleBaseLineEntity entity) {
        return ModuleCodesPrefixes.PROJ_SCHEDULE_PREFIX.getDesc().concat("-").concat(entity.getProjId().getCode())
                .concat("-").concat(String.valueOf(entity.getId()));

    }

}
