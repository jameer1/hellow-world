package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjLeaveApprTO;
import com.rjtech.projsettings.model.LeaveAddtionalTimeApprEntity;

public class ProjLeaveApprHandler {

    public static List<LeaveAddtionalTimeApprEntity> convertPOJOToEntity(List<ProjLeaveApprTO> projLeaveApprTOs) {
        List<LeaveAddtionalTimeApprEntity> projLeaveApprEntities = new ArrayList<LeaveAddtionalTimeApprEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        LeaveAddtionalTimeApprEntity entity = null;
        for (ProjLeaveApprTO projLeaveApprTO : projLeaveApprTOs) {
            entity = new LeaveAddtionalTimeApprEntity();

            if (CommonUtil.isNonBlankLong(projLeaveApprTO.getId())) {
                entity.setId(projLeaveApprTO.getId());
            }
            //  entity.setNotificationId(projLeaveApprTO.getNotificationId());
            if (CommonUtil.isNotBlankStr(projLeaveApprTO.getRequisitionDate())) {
                entity.setRequisitionDate(CommonUtil.convertStringToDate(projLeaveApprTO.getRequisitionDate()));
            }

            entity.setRequester(projLeaveApprTO.getRequester());

            entity.setNotificationNumber(projLeaveApprTO.getNotificationNumber());

            entity.setStatus(projLeaveApprTO.getStatus());
            entity.setLatest(true);
            projLeaveApprEntities.add(entity);
        }
        return projLeaveApprEntities;
    }

}
