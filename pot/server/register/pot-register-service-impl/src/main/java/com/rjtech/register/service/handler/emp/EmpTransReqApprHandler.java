package com.rjtech.register.service.handler.emp;

import java.util.Date;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.emp.dto.EmpTransferReqApprTO;
import com.rjtech.register.emp.model.EmpNotificationsEntity;
import com.rjtech.register.emp.model.EmpTransferReqApprEntity;

public class EmpTransReqApprHandler {
    private EmpTransReqApprHandler() {
    }

    public static EmpTransferReqApprTO convertEntityToPOJO(EmpTransferReqApprEntity entity) {
        EmpTransferReqApprTO empReqTransTO = new EmpTransferReqApprTO();
        empReqTransTO.setId(entity.getId());
        empReqTransTO.setApprCode(entity.getApprCode());
        if (CommonUtil.objectNotNull(entity.getToProjMstrEntity())) {
            empReqTransTO.setToProjId(entity.getToProjMstrEntity().getProjectId());
            empReqTransTO.setToProjName(entity.getToProjMstrEntity().getProjName());
        }
        if (CommonUtil.objectNotNull(entity.getFromProjMstrEntity())) {
            empReqTransTO.setFromProjId(entity.getFromProjMstrEntity().getProjectId());
            empReqTransTO.setFromProjName(entity.getFromProjMstrEntity().getProjName());
        }
        if (CommonUtil.isNotBlankDate(entity.getApprDate())) {
            empReqTransTO.setApprDate(CommonUtil.convertDateToString(entity.getApprDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getCreatedOn())) {
            empReqTransTO.setReqDate(CommonUtil.convertDateToString(entity.getReqDate()));
        }
        empReqTransTO.setReqComments(entity.getReqComments());
        empReqTransTO.setApprComments(entity.getApprComments());
        empReqTransTO.setApprStatus(entity.getApprStatus());
        empReqTransTO.setApprCurrentProj(entity.getApprCurrentProj());
        empReqTransTO.setReqCurrentProj(entity.getReqCurrentProj());
        if (entity.getEmpNotificationsEntity() != null) {
            empReqTransTO.setReqCode(generateReqCode(entity.getEmpNotificationsEntity()));
            empReqTransTO.setReqNotifyCode(generateCode(entity.getEmpNotificationsEntity()));
            empReqTransTO.setNotifyId(entity.getEmpNotificationsEntity().getId());
            empReqTransTO.setNotifyDate(CommonUtil.convertDateToString(entity.getEmpNotificationsEntity().getDate()));
            empReqTransTO.setNotifyMsg(entity.getEmpNotificationsEntity().getNotificationMsg());
            
            if(!entity.getReqDate().equals(entity.getEmpNotificationsEntity().getDate()) 
            		&& entity.getEmpNotificationsEntity().getNotificationMsg().equalsIgnoreCase("Additional Time for Request") 
            		&& entity.getEmpNotificationsEntity().getNotificationStatus().equalsIgnoreCase("Approved")) {
            	empReqTransTO.setAddlTimeFlag(true);
            } else {
            	empReqTransTO.setAddlTimeFlag(false);
            }
        }

        if (CommonUtil.objectNotNull(entity.getReqUserMstrEntity())) {
            empReqTransTO.setReqUserTO(EmpRegisterDtlHandler.convertUserEntityToPOJO(entity.getReqUserMstrEntity()));
        }
        if (CommonUtil.objectNotNull(entity.getApprUserMstrEntity())) {
            empReqTransTO.setApprUserTO(EmpRegisterDtlHandler.convertUserEntityToPOJO(entity.getApprUserMstrEntity()));
        }
        empReqTransTO.setStatus(entity.getStatus());
        return empReqTransTO;
    }

    public static EmpTransferReqApprEntity convertPOJOToEntity(EmpTransferReqApprTO empReqTransTO,
            EPSProjRepository epsProjRepository, LoginRepository loginRepository) {
        EmpTransferReqApprEntity entity = new EmpTransferReqApprEntity();
        if (CommonUtil.isNonBlankLong(empReqTransTO.getId())) {
            entity.setId(empReqTransTO.getId());
        }
        if (CommonUtil.isNonBlankLong(empReqTransTO.getToProjId())) {
            ProjMstrEntity toProjMstrEntity = epsProjRepository.findOne(empReqTransTO.getToProjId());
            entity.setToProjMstrEntity(toProjMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(empReqTransTO.getFromProjId())) {
            ProjMstrEntity fromProjMstrEntity = epsProjRepository.findOne(empReqTransTO.getFromProjId());
            entity.setFromProjMstrEntity(fromProjMstrEntity);
        }
        if (CommonUtil.isBlankStr(empReqTransTO.getReqDate())) {
            entity.setReqDate(new Date());
        } else {
            entity.setReqDate(CommonUtil.convertStringToDate(empReqTransTO.getReqDate()));
        }
        if (CommonUtil.isNotBlankStr(empReqTransTO.getReqCode())) {
            entity.setReqCode(empReqTransTO.getReqCode());
        }
        if (CommonUtil.isNotBlankStr(empReqTransTO.getApprCode())) {
            entity.setApprCode(empReqTransTO.getApprCode());
        }
        if (CommonUtil.isNotBlankStr(empReqTransTO.getApprDate())) {
            entity.setApprDate(CommonUtil.convertStringToDate(empReqTransTO.getApprDate()));
        }
        if (CommonUtil.isNonBlankLong(empReqTransTO.getNotifyId())) {
            EmpNotificationsEntity empNotificationsEntity = new EmpNotificationsEntity();
            empNotificationsEntity.setId(empReqTransTO.getNotifyId());
            entity.setEmpNotificationsEntity(empNotificationsEntity);
        }

        if (CommonUtil.objectNotNull(empReqTransTO.getReqUserTO())) {
            UserMstrEntity reqUserMstrEntity = loginRepository.findOne(empReqTransTO.getReqUserTO().getUserId());
            entity.setReqUserMstrEntity(reqUserMstrEntity);
        }
        if (CommonUtil.objectNotNull(empReqTransTO.getApprUserTO())) {
            UserMstrEntity apprUserMstrEntity = loginRepository.findOne(empReqTransTO.getApprUserTO().getUserId());
            entity.setApprUserMstrEntity(apprUserMstrEntity);
        }
        entity.setApprStatus(empReqTransTO.getApprStatus());
        entity.setApprCurrentProj(empReqTransTO.getApprCurrentProj());
        entity.setReqCurrentProj(empReqTransTO.getReqCurrentProj());
        entity.setStatus(empReqTransTO.getStatus());
        return entity;
    }

    public static String generateCode(EmpNotificationsEntity employeeNotificationsEntity) {
        if (employeeNotificationsEntity != null)
            return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
                    .concat("-" + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc()
                            .concat("-" + employeeNotificationsEntity.getProjMstrEntity().getCode())
                            .concat("-" + employeeNotificationsEntity.getId()));
        else
            return "";
    }

    public static String generateReqCode(EmpNotificationsEntity employeeNotificationsEntity) {
        if (employeeNotificationsEntity != null)
            return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
                    .concat("-" + ModuleCodesPrefixes.REQUEST_PREFIX.getDesc()
                            .concat("-" + employeeNotificationsEntity.getProjMstrEntity().getCode())
                            .concat("-" + employeeNotificationsEntity.getId()));
        else
            return "";
    }

}
