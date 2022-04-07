package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjLeaveRequestTO;
import com.rjtech.projsettings.model.LeaveNormalTimeEntity;

public class ProjLeaveRequestHandler {

    public static ProjLeaveRequestTO convertEntityToPOJO(LeaveNormalTimeEntity projLeaveRequestEntity) {
        ProjLeaveRequestTO projLeaveRequestTO = new ProjLeaveRequestTO();
        if (CommonUtil.objectNotNull(projLeaveRequestEntity)) {
            projLeaveRequestTO.setId(projLeaveRequestEntity.getId());
        }
        projLeaveRequestTO.setCutOffDays(projLeaveRequestEntity.getCutOffDays());

        ProjMstrEntity projMstrEntity = projLeaveRequestEntity.getProjId();
        if (null != projMstrEntity) {
            projLeaveRequestTO.setProjId(projMstrEntity.getProjectId());
        }
        projLeaveRequestTO.setProjId(projLeaveRequestEntity.getProjId().getProjectId());
        projLeaveRequestTO.setCutOffHours(projLeaveRequestEntity.getCutOffHours());
        projLeaveRequestTO.setCutOffMinutes(projLeaveRequestEntity.getCutOffMinutes());
        projLeaveRequestTO.setType(projLeaveRequestEntity.getType());

        projLeaveRequestTO.setStatus(projLeaveRequestEntity.getStatus());

        return projLeaveRequestTO;

    }

    public static List<LeaveNormalTimeEntity> convertPOJOToEntity(List<ProjLeaveRequestTO> projLeaveRequestTOs,
            EPSProjRepository epsProjRepository) {
        List<LeaveNormalTimeEntity> projLeaveRequestEntities = new ArrayList<LeaveNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjLeaveRequestTO projLeaveRequestTO : projLeaveRequestTOs) {

            projLeaveRequestEntities.add(convertOnePOJOToOneEntity(projLeaveRequestTO, epsProjRepository));
        }
        return projLeaveRequestEntities;
    }

    public static LeaveNormalTimeEntity convertOnePOJOToOneEntity(ProjLeaveRequestTO projLeaveRequestTO,
            EPSProjRepository epsProjRepository) {
        LeaveNormalTimeEntity projLeaveRequestEntity = new LeaveNormalTimeEntity();

        if (CommonUtil.isNonBlankLong(projLeaveRequestTO.getId())) {
            projLeaveRequestEntity.setId(projLeaveRequestTO.getId());
        }
        if (CommonUtil.isNonBlankInteger(projLeaveRequestTO.getCutOffDays())) {
            projLeaveRequestEntity.setCutOffDays(projLeaveRequestTO.getCutOffDays());
        } else {
            projLeaveRequestEntity.setCutOffDays(0);
        }

        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projLeaveRequestTO.getProjId());
        if (null != projMstrEntity) {
            projLeaveRequestEntity.setProjId(projMstrEntity);
        }

        if (CommonUtil.isNonBlankInteger(projLeaveRequestTO.getCutOffHours())) {
            projLeaveRequestEntity.setCutOffHours(projLeaveRequestTO.getCutOffHours());
        } else {
            projLeaveRequestEntity.setCutOffHours(0);
        }
        if (CommonUtil.isNonBlankInteger(projLeaveRequestTO.getCutOffMinutes())) {
            projLeaveRequestEntity.setCutOffMinutes(projLeaveRequestTO.getCutOffMinutes());
        } else {
            projLeaveRequestEntity.setCutOffMinutes(0);
        }
        projLeaveRequestEntity.setType(projLeaveRequestTO.getType());

        projLeaveRequestEntity.setStatus(projLeaveRequestTO.getStatus());
        return projLeaveRequestEntity;
    }

}
