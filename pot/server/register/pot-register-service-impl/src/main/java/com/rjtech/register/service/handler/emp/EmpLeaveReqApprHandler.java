package com.rjtech.register.service.handler.emp;

import java.util.Date;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.emp.dto.EmpLeaveReqApprDetailTO;
import com.rjtech.register.emp.dto.EmpLeaveReqApprTO;
import com.rjtech.register.emp.dto.EmpPublicHolidayTO;
import com.rjtech.register.emp.model.EmpLeaveReqApprDetailEntity;
import com.rjtech.register.emp.model.EmpLeaveReqApprEntity;
import com.rjtech.register.emp.model.EmpNotificationsEntity;
import com.rjtech.register.emp.model.EmpPublicHolidayEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpLeaveReqApprHandler {

    public static EmpLeaveReqApprTO convertEntityToPOJO(EmpLeaveReqApprEntity entity) {

        EmpLeaveReqApprTO empLeaveReqApprTO = new EmpLeaveReqApprTO();

        empLeaveReqApprTO.setId(entity.getId());
        System.out.println(" entity.getId() " + entity.getId());
        System.out.println(" entity.getReqDate() " + entity.getReqDate());
        if (CommonUtil.objectNotNull(entity.getProjMstrEntity())) {
            empLeaveReqApprTO.setEmpProjId(entity.getProjMstrEntity().getProjectId());
        }
        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            empLeaveReqApprTO.setEmpRegId(entity.getEmpRegisterDtlEntity().getId());
        }
        empLeaveReqApprTO.setReqCode(generateReqCode(entity));
        empLeaveReqApprTO.setApprCode(generateApprCode(entity));
        if (CommonUtil.objectNotNull(entity.getParentEmpLeaveReqApprEntity())) {
            empLeaveReqApprTO.setParentId(entity.getParentEmpLeaveReqApprEntity().getId());
        }
        empLeaveReqApprTO.setStartDate(CommonUtil.convertDateToString(entity.getStartDate()));
        empLeaveReqApprTO.setEndDate(CommonUtil.convertDateToString(entity.getEndDate()));
        empLeaveReqApprTO.setTotalDays(entity.getTotalDays());
        empLeaveReqApprTO.setLatest(entity.isLatest());
        empLeaveReqApprTO.setReqDate(CommonUtil.convertDateToString(entity.getReqDate()));
        empLeaveReqApprTO.setApprDate(CommonUtil.convertDateToString(entity.getApprDate()));
        empLeaveReqApprTO.setApprUserTO(EmpRegisterDtlHandler.convertUserEntityToPOJO(entity.getApprUserEntity()));
        empLeaveReqApprTO.setReasonForLeave(entity.getReasonForLeave());
        if (CommonUtil.objectNotNull(entity.getEmpNotificationsEntity())) {
            String code = generateNotifyCode(entity.getEmpNotificationsEntity());
            empLeaveReqApprTO.setNotifyCode(code);
            empLeaveReqApprTO.setNotifyId(entity.getEmpNotificationsEntity().getId());
            empLeaveReqApprTO.setNotifyDate(CommonUtil.convertDateToString(entity.getEmpNotificationsEntity().getDate()));
            empLeaveReqApprTO.setNotifyMsg(entity.getEmpNotificationsEntity().getNotificationMsg());
            
            if (!entity.getReqDate().equals(entity.getEmpNotificationsEntity().getDate())
            		&& (entity.getEmpNotificationsEntity().getNotificationMsg() != null  
            				&& entity.getEmpNotificationsEntity().getNotificationMsg().equalsIgnoreCase("Additional Time for Request"))
            		&& entity.getEmpNotificationsEntity().getNotificationStatus().equalsIgnoreCase("Approved")) {
            	empLeaveReqApprTO.setAddlTimeFlag(true);
            } else {
            	empLeaveReqApprTO.setAddlTimeFlag(false);
            }
        }

        empLeaveReqApprTO.setApprStatus(entity.getApprStatus());
        empLeaveReqApprTO.setApprComments(entity.getApprComments());
        empLeaveReqApprTO.setStatus(entity.getStatus());

        return empLeaveReqApprTO;
    }

    private static String generateReqCode(EmpLeaveReqApprEntity entity) {
        return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc().concat("-" + ModuleCodesPrefixes.REQUEST_PREFIX.getDesc())
                .concat("-" + entity.getProjMstrEntity().getCode()).concat("-" + entity.getId());
    }

    private static String generateApprCode(EmpLeaveReqApprEntity entity) {
        return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc().concat("-" + ModuleCodesPrefixes.APPROVE_PREFIX.getDesc())
                .concat("-" + entity.getProjMstrEntity().getCode()).concat("-" + entity.getId());
    }

    private static String generateNotifyCode(EmpNotificationsEntity entity) {
        return ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
                .concat("-" + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc())
                .concat("-" + entity.getProjMstrEntity().getCode()).concat("-" + entity.getId());
    }

    public static EmpLeaveReqApprEntity convertPOJOToEntity(EmpLeaveReqApprTO empLeaveReqApprTO,
            EPSProjRepository epsProjRepository, EmpRegisterRepository empRegisterRepository) {
        EmpLeaveReqApprEntity entity = new EmpLeaveReqApprEntity();

        if (CommonUtil.isNonBlankLong(empLeaveReqApprTO.getId())) {
            entity.setId(empLeaveReqApprTO.getId());
        }

        entity.setId(empLeaveReqApprTO.getId());

        if (CommonUtil.isNonBlankLong(empLeaveReqApprTO.getEmpProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(empLeaveReqApprTO.getEmpProjId());
            entity.setProjMstrEntity(projMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(empLeaveReqApprTO.getEmpRegId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository.findOne(empLeaveReqApprTO.getEmpRegId());
            entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        if (CommonUtil.isNonBlankLong(empLeaveReqApprTO.getApprUserTO().getUserId())) {
            UserMstrEntity apprUserEntity = new UserMstrEntity();
            apprUserEntity.setUserId(empLeaveReqApprTO.getApprUserTO().getUserId());
            entity.setApprUserEntity(apprUserEntity);
        }
		if(CommonUtil.isNonBlankLong(empLeaveReqApprTO.getReqUserTO().getUserId())) {
			UserMstrEntity reqUserEntity = new UserMstrEntity();
			reqUserEntity.setUserId(empLeaveReqApprTO.getReqUserTO().getUserId());
			entity.setReqUserEntity(reqUserEntity);
		
		}
        entity.setReqCode(empLeaveReqApprTO.getReqCode());
        entity.setApprCode(empLeaveReqApprTO.getApprCode());
        if (CommonUtil.isNotBlankStr(empLeaveReqApprTO.getReqDate())) {
            entity.setReqDate(CommonUtil.convertStringToDate(empLeaveReqApprTO.getReqDate()));
        } else {
            entity.setReqDate(new Date());
        }
        entity.setApprDate(CommonUtil.convertStringToDate(empLeaveReqApprTO.getApprDate()));
        entity.setLatest(empLeaveReqApprTO.isLatest());

        entity.setStartDate(CommonUtil.convertStringToDate(empLeaveReqApprTO.getStartDate()));
        entity.setEndDate(CommonUtil.convertStringToDate(empLeaveReqApprTO.getEndDate()));
        entity.setTotalDays(empLeaveReqApprTO.getTotalDays());

        entity.setReasonForLeave(empLeaveReqApprTO.getReasonForLeave());
        entity.setApprStatus(empLeaveReqApprTO.getApprStatus());
        entity.setApprComments(empLeaveReqApprTO.getApprComments());
        entity.setStatus(empLeaveReqApprTO.getStatus());
        if (CommonUtil.isNonBlankLong(empLeaveReqApprTO.getParentId())) {
            EmpLeaveReqApprEntity parentEmpLeaveReqApprEntity = new EmpLeaveReqApprEntity();
            parentEmpLeaveReqApprEntity.setId(empLeaveReqApprTO.getParentId());
            entity.setParentEmpLeaveReqApprEntity(parentEmpLeaveReqApprEntity);
        }

        if (CommonUtil.isNonBlankLong(empLeaveReqApprTO.getNotifyId())) {
            EmpNotificationsEntity empNotificationsEntity = new EmpNotificationsEntity();
            empNotificationsEntity.setId(empLeaveReqApprTO.getNotifyId());
            entity.setEmpNotificationsEntity(empNotificationsEntity);
        }

        entity.setStatus(empLeaveReqApprTO.getStatus());
        return entity;
    }

    public static EmpLeaveReqApprEntity populateEmpLeaveReApprEntity(EmpLeaveReqApprTO empLeaveReqApprTO,
            EPSProjRepository epsProjRepository, EmpRegisterRepository empRegisterRepository) {
        EmpLeaveReqApprEntity empLeaveReqApprEntity = null;
        EmpLeaveReqApprDetailEntity empLeaveReqApprDetailEntity = null;
        EmpPublicHolidayEntity empPublicHolidayEntity = null;
        empLeaveReqApprEntity = convertPOJOToEntity(empLeaveReqApprTO, epsProjRepository, empRegisterRepository);
        for (EmpLeaveReqApprDetailTO empLeaveReqApprDetailTO : empLeaveReqApprTO.getEmpLeaveReqApprDetailTOs()) {
            empLeaveReqApprDetailEntity = EmpLeaveReqApprDetailHandler.convertPOJOToEntity(empLeaveReqApprDetailTO);
            empLeaveReqApprDetailEntity.setEmpLeaveReqApprEntity(empLeaveReqApprEntity);
            empLeaveReqApprEntity.getEmpLeaveReqApprDetailEntities().add(empLeaveReqApprDetailEntity);

        }
        for (EmpPublicHolidayTO empPublicHolidayTO : empLeaveReqApprTO.getEmpPublicHolidayTOs()) {
            empPublicHolidayEntity = EmpPublicHolidayHandler.convertPOJOToEntity(empPublicHolidayTO);
            empPublicHolidayEntity.setEmpLeaveReqApprEntity(empLeaveReqApprEntity);
            empLeaveReqApprEntity.getEmpPublicHolidayEntities().add(empPublicHolidayEntity);
        }
        for (EmpPublicHolidayTO empPublicHolidayTO : empLeaveReqApprTO.getEmpRosterDays()) {
            empPublicHolidayEntity = EmpPublicHolidayHandler.convertPOJOToEntity(empPublicHolidayTO);
            empPublicHolidayEntity.setEmpLeaveReqApprEntity(empLeaveReqApprEntity);
            empLeaveReqApprEntity.getEmpPublicHolidayEntities().add(empPublicHolidayEntity);
        }
        return empLeaveReqApprEntity;
    }

}
