package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.model.PlantNotificationsEntityCopy;
import com.rjtech.notification.repository.PlantNotificationsRepositoryCopy;
import com.rjtech.projsettings.dto.ProjPlantTransApprTO;
import com.rjtech.projsettings.model.PlantTransAddtionalTimeApprEntity;

public class ProjPlantTransApprHandler {

    public static List<PlantTransAddtionalTimeApprEntity> convertPOJOToEntity(
            List<ProjPlantTransApprTO> projPlantTransApprTOs,
            PlantNotificationsRepositoryCopy plantNotificationsRepository) {
        List<PlantTransAddtionalTimeApprEntity> projPlantTransApprEntites = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        PlantTransAddtionalTimeApprEntity entity = null;
        for (ProjPlantTransApprTO projPlantTransApprTO : projPlantTransApprTOs) {
            entity = new PlantTransAddtionalTimeApprEntity();

            if (CommonUtil.isNonBlankLong(projPlantTransApprTO.getId())) {
                entity.setId(projPlantTransApprTO.getId());
            }
            entity.setLatest(true);
            if (CommonUtil.isNotBlankStr(projPlantTransApprTO.getRequisitionDate())) {
                entity.setRequisitionDate(CommonUtil.convertStringToDate(projPlantTransApprTO.getRequisitionDate()));
            }
            if (CommonUtil.isNonBlankLong(projPlantTransApprTO.getNotificationId())) {
                PlantNotificationsEntityCopy plantNotificationsEntity = plantNotificationsRepository
                        .findOne(projPlantTransApprTO.getNotificationId());
                entity.setPlantNotificationsEntity(plantNotificationsEntity);
            }

            entity.setStatus(projPlantTransApprTO.getStatus());

            projPlantTransApprEntites.add(entity);
        }
        return projPlantTransApprEntites;
    }
}
