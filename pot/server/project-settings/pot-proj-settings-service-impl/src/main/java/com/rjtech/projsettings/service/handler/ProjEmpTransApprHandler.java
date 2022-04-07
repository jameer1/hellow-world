package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.model.EmployeeNotificationsEntity;
import com.rjtech.notification.repository.EmployeeNotificationsRepositoryCopy;
import com.rjtech.projsettings.dto.ProjEmpTransApprTO;
import com.rjtech.projsettings.model.EmpTransAddtionalTimeApprEntity;
import com.rjtech.projsettings.model.EmpTransNormalTimeEntity;
import com.rjtech.projsettings.repository.ProjEmpTransRepository;

public class ProjEmpTransApprHandler {

    public static List<EmpTransAddtionalTimeApprEntity> convertPOJOToEntity(
            List<ProjEmpTransApprTO> projEmpTransApprTOs, ProjEmpTransRepository projEmpTransRepository,
            EmployeeNotificationsRepositoryCopy empNotificationsRepository) {
        List<EmpTransAddtionalTimeApprEntity> projEmpTransApprEntites = new ArrayList<EmpTransAddtionalTimeApprEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        EmpTransAddtionalTimeApprEntity entity = null;
        for (ProjEmpTransApprTO projEmpTransApprTO : projEmpTransApprTOs) {
            entity = new EmpTransAddtionalTimeApprEntity();

            if (CommonUtil.isNonBlankLong(projEmpTransApprTO.getId())) {
                entity.setId(projEmpTransApprTO.getId());
            }
            entity.setLatest(true);
            EmpTransNormalTimeEntity empTransNormalTimeEntity = projEmpTransRepository
                    .findOne(projEmpTransApprTO.getEmpTransId());
            if (null != empTransNormalTimeEntity) {
                entity.setEmpTransNormalTimeEntity(empTransNormalTimeEntity);
            }
            if (CommonUtil.isNotBlankStr(projEmpTransApprTO.getRequisitionDate())) {
                entity.setRequisitionDate(CommonUtil.convertStringToDate(projEmpTransApprTO.getRequisitionDate()));
            }
            if (CommonUtil.isNonBlankLong(projEmpTransApprTO.getNotificationId())) {
                EmployeeNotificationsEntity employeeNotificationsEntity = empNotificationsRepository
                        .findOne(projEmpTransApprTO.getNotificationId());
                entity.setEmployeeNotificationsEntity(employeeNotificationsEntity);
            }
            entity.setStatus(projEmpTransApprTO.getStatus());

            projEmpTransApprEntites.add(entity);
        }
        return projEmpTransApprEntites;
    }
}
