package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjProgressClaimePeriodTO;
import com.rjtech.projsettings.model.ProgressClaimPeriodCycleEntity;

public class ProjProgressClaimePeriodHandler {

    public static ProjProgressClaimePeriodTO convertEntityToPOJO(
            ProgressClaimPeriodCycleEntity projProgressClaimePeriodEntity) {
        ProjProgressClaimePeriodTO projProgressClaimePeriodTO = new ProjProgressClaimePeriodTO();
        projProgressClaimePeriodTO.setId(projProgressClaimePeriodEntity.getId());
        projProgressClaimePeriodTO.setProjId(projProgressClaimePeriodEntity.getProjId().getProjectId());
        projProgressClaimePeriodTO.setStartDay(projProgressClaimePeriodEntity.getStartDay());
        projProgressClaimePeriodTO.setFinishDay(projProgressClaimePeriodEntity.getFinishDay());
        projProgressClaimePeriodTO.setProjType(projProgressClaimePeriodEntity.getProjType());

        projProgressClaimePeriodTO.setStatus(projProgressClaimePeriodEntity.getStatus());

        return projProgressClaimePeriodTO;

    }

    public static List<ProgressClaimPeriodCycleEntity> convertPOJOToEntity(
            List<ProjProgressClaimePeriodTO> projProgressClaimePeriodTOs, EPSProjRepository epsProjRepository) {
        List<ProgressClaimPeriodCycleEntity> ProjProgressClaimePeriodEntities = new ArrayList<ProgressClaimPeriodCycleEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProgressClaimPeriodCycleEntity projProgressClaimePeriodEntity = null;
        for (ProjProgressClaimePeriodTO projProgressClaimePeriodTO : projProgressClaimePeriodTOs) {
            projProgressClaimePeriodEntity = new ProgressClaimPeriodCycleEntity();

            if (CommonUtil.isNonBlankLong(projProgressClaimePeriodTO.getId())) {
                projProgressClaimePeriodEntity.setId(projProgressClaimePeriodTO.getId());
            }
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projProgressClaimePeriodTO.getProjId());

            if (null != projMstrEntity) {
                projProgressClaimePeriodEntity.setProjId(projMstrEntity);
            }
            projProgressClaimePeriodEntity.setProjType(projProgressClaimePeriodTO.getProjType());
            projProgressClaimePeriodEntity.setStartDay(projProgressClaimePeriodTO.getStartDay());
            projProgressClaimePeriodEntity.setFinishDay(projProgressClaimePeriodTO.getFinishDay());

            projProgressClaimePeriodEntity.setStatus(projProgressClaimePeriodTO.getStatus());

            ProjProgressClaimePeriodEntities.add(projProgressClaimePeriodEntity);
        }
        return ProjProgressClaimePeriodEntities;
    }

}
