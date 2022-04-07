package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.model.EmployeeNotificationsEntity;
import com.rjtech.notification.model.EmployeeNotificationsEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjEmpTransTO;
import com.rjtech.projsettings.model.EmpTransNormalTimeEntity;

public class ProjEmpTransHandler {

    public static ProjEmpTransTO convertEntityToPOJO(EmpTransNormalTimeEntity entity) {
        ProjEmpTransTO projEmpTransTO = new ProjEmpTransTO();

        projEmpTransTO.setId(entity.getId());

        ProjMstrEntity projMstrEntity = entity.getProjId();
        if (CommonUtil.objectNotNull(projMstrEntity)) {
            projEmpTransTO.setProjId(projMstrEntity.getProjectId());
        }
        projEmpTransTO.setEmptransType(entity.getEmptransType());
        if (CommonUtil.isNonBlankInteger(entity.getCutOffDays())) {
            projEmpTransTO.setCutOffDays(entity.getCutOffDays());
        }
        projEmpTransTO.setCutOffTime(entity.getCutOffTime());
        projEmpTransTO.setDefaultStatus(entity.getDefaultStatus());
        if (CommonUtil.isNonBlankInteger(entity.getCutOffHours())) {
            projEmpTransTO.setCutOffHours(entity.getCutOffHours());
        }
        if (CommonUtil.isZeroOrGreater(entity.getCutOffMinutes())) {
            projEmpTransTO.setCutOffMinutes(entity.getCutOffMinutes());
        }
        projEmpTransTO.setStatus(entity.getStatus());
        return projEmpTransTO;
    }

    public static List<EmpTransNormalTimeEntity> convertPOJOToEntity(List<ProjEmpTransTO> projEmpTransTOs,
            EPSProjRepository epsProjRepository) {
        List<EmpTransNormalTimeEntity> projEmpTransEntites = new ArrayList<EmpTransNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjEmpTransTO projEmpTransTO : projEmpTransTOs) {

            projEmpTransEntites.add(convertOnePojoToOneEntity(projEmpTransTO, epsProjRepository));
        }
        return projEmpTransEntites;
    }

    public static EmpTransNormalTimeEntity convertOnePojoToOneEntity(ProjEmpTransTO projEmpTransTO,
            EPSProjRepository epsProjRepository) {
        EmpTransNormalTimeEntity entity = new EmpTransNormalTimeEntity();

        if (CommonUtil.isNonBlankLong(projEmpTransTO.getId())) {
            entity.setId(projEmpTransTO.getId());
        }

        entity.setId(projEmpTransTO.getId());
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projEmpTransTO.getProjId());
        if (CommonUtil.objectNotNull(projMstrEntity)) {
            entity.setProjId(projMstrEntity);
        }
        entity.setEmptransType(projEmpTransTO.getEmptransType());
        if (CommonUtil.isNonBlankInteger(projEmpTransTO.getCutOffDays())) {
            entity.setCutOffDays(projEmpTransTO.getCutOffDays());
        } else {
            entity.setCutOffDays(0);
        }
        entity.setCutOffTime(projEmpTransTO.getCutOffTime());
        entity.setDefaultStatus(projEmpTransTO.getDefaultStatus());
        if (CommonUtil.isNonBlankInteger(projEmpTransTO.getCutOffHours())) {
            entity.setCutOffHours(projEmpTransTO.getCutOffHours());
        } else {
            entity.setCutOffHours(0);
        }
        if (CommonUtil.isNonBlankInteger(projEmpTransTO.getCutOffMinutes())) {
            entity.setCutOffMinutes(projEmpTransTO.getCutOffMinutes());
        } else {
            entity.setCutOffMinutes(0);
        }

        entity.setStatus(projEmpTransTO.getStatus());

        return entity;
    }
    
    public static String generateCode(EmployeeNotificationsEntityCopy employeeNotificationsEntity) {
        if (employeeNotificationsEntity != null)
            return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
                    .concat("-" + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc()
                            .concat("-" + employeeNotificationsEntity.getForProject().getCode())
                            .concat("-" + employeeNotificationsEntity.getId()));
        else
            return "";
    }

    public static String generateReqCode(EmployeeNotificationsEntityCopy employeeNotificationsEntity) {
        if (employeeNotificationsEntity != null)
            return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
                    .concat("-" + ModuleCodesPrefixes.REQUEST_PREFIX.getDesc()
                            .concat("-" + employeeNotificationsEntity.getForProject().getCode())
                            .concat("-" + employeeNotificationsEntity.getId()));
        else
            return "";
    }

}
