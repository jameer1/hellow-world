package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProgressClaimApprTO;
import com.rjtech.projsettings.model.ProgressClaimAddtionalTimeApprEntity;
import com.rjtech.projsettings.model.ProgressClaimNormalTimeEntity;
import com.rjtech.projsettings.repository.ProjProgressClaimRepository;

public class ProjProgressClaimApprHandler {

    public static List<ProgressClaimAddtionalTimeApprEntity> convertPOJOToEntity(
            List<ProjProgressClaimApprTO> projProgressClaimApprTOs,
            ProjProgressClaimRepository projProgressClaimRepository, LoginRepository loginRepository) {
        List<ProgressClaimAddtionalTimeApprEntity> ProjProgressClaimApprEntites = new ArrayList<ProgressClaimAddtionalTimeApprEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProgressClaimAddtionalTimeApprEntity entity = null;
        for (ProjProgressClaimApprTO projProgressClaimApprTO : projProgressClaimApprTOs) {
            entity = new ProgressClaimAddtionalTimeApprEntity();

            if (CommonUtil.isNonBlankLong(projProgressClaimApprTO.getId())) {
                entity.setId(projProgressClaimApprTO.getId());
            }

            entity.setId(projProgressClaimApprTO.getId());
            ProgressClaimNormalTimeEntity progressClaimNormalTimeEntity = projProgressClaimRepository
                    .findOne(projProgressClaimApprTO.getClaimId());
            entity.setClaimId(progressClaimNormalTimeEntity);
            UserMstrEntity userMstrEntity = loginRepository.findOne(projProgressClaimApprTO.getApprUserId());
            entity.setApprUserId(userMstrEntity);
            entity.setClaimPeriod(projProgressClaimApprTO.getClaimPeriod());

            entity.setStatus(projProgressClaimApprTO.getStatus());

            ProjProgressClaimApprEntites.add(entity);
        }
        return ProjProgressClaimApprEntites;
    }

}
