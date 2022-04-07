package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjProgressClaimTO;
import com.rjtech.projsettings.model.ProgressClaimNormalTimeEntity;

public class ProjProgressClaimHandler {
    public static ProjProgressClaimTO convertEntityToPOJO(ProgressClaimNormalTimeEntity entity) {
        ProjProgressClaimTO projProgressClaimTO = new ProjProgressClaimTO();

        projProgressClaimTO.setId(entity.getId());
        projProgressClaimTO.setProjId(entity.getProjId().getProjectId());
        projProgressClaimTO.setClaimType(entity.getClaimType());
        projProgressClaimTO.setCutOffDays(entity.getCutOffDays());
        projProgressClaimTO.setCutOffTime(entity.getCutOffTime());

        projProgressClaimTO.setStatus(entity.getStatus());

        projProgressClaimTO.setCutOffHours(entity.getCutOffHours());
        projProgressClaimTO.setCutOffMinutes(entity.getCutOffMinutes());

        return projProgressClaimTO;

    }

    public static List<ProgressClaimNormalTimeEntity> convertPOJOToEntity(
            List<ProjProgressClaimTO> projProgressClaimTOs, EPSProjRepository epsProjRepository) {
        List<ProgressClaimNormalTimeEntity> ProjProgressClaimEntites = new ArrayList<ProgressClaimNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProgressClaimNormalTimeEntity entity = null;
        for (ProjProgressClaimTO projProgressClaimTO : projProgressClaimTOs) {
            entity = new ProgressClaimNormalTimeEntity();

            if (CommonUtil.isNonBlankLong(projProgressClaimTO.getId())) {
                entity.setId(projProgressClaimTO.getId());
            }

            entity.setId(projProgressClaimTO.getId());
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projProgressClaimTO.getProjId());

            if (null != projMstrEntity) {
                entity.setProjId(projMstrEntity);
            }
            entity.setClaimType(projProgressClaimTO.getClaimType());
            entity.setCutOffDays(projProgressClaimTO.getCutOffDays());
            entity.setCutOffTime(projProgressClaimTO.getCutOffTime());

            entity.setStatus(projProgressClaimTO.getStatus());

            entity.setCutOffHours(projProgressClaimTO.getCutOffHours());
            entity.setCutOffMinutes(projProgressClaimTO.getCutOffMinutes());

            ProjProgressClaimEntites.add(entity);
        }
        return ProjProgressClaimEntites;
    }

}
