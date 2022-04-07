package com.rjtech.notification.service.handler;

import java.util.Date;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.dto.EmployeeNotificationsTO;
import com.rjtech.notification.model.EmployeeNotificationsEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class EmployeeNotificationsHandler {
    private EmployeeNotificationsHandler() {
    }

    public static EmployeeNotificationsTO convertEntityToPOJO(EmployeeNotificationsEntity employeeNotificationsEntity) {

        EmployeeNotificationsTO employeeNotificationsTO = new EmployeeNotificationsTO();
        employeeNotificationsTO.setId(employeeNotificationsEntity.getId());
        if (CommonUtil.isNotBlankDate(employeeNotificationsEntity.getDate())) {
            employeeNotificationsTO.setDate(CommonUtil.convertDateToString(employeeNotificationsEntity.getDate()));
        }
        employeeNotificationsTO.setCode(EmployeeLeaveNotificationsHandler.generateCode(employeeNotificationsEntity));

        UserMstrEntity userMstrEntity = employeeNotificationsEntity.getApprUserId();
        UserMstrEntity userMstrReqEntity = employeeNotificationsEntity.getReqUserId();
        ProjMstrEntity projMstrEntity = employeeNotificationsEntity.getProjMstrEntity();
        Long forProjMstrEntity = employeeNotificationsEntity.getForProject();
        if (null != userMstrEntity) {
            employeeNotificationsTO.setApprUserId(userMstrEntity.getUserId());
            employeeNotificationsTO.setToUserName(employeeNotificationsEntity.getApprUserId().getDisplayName());
        }
        if (null != userMstrReqEntity) {
            employeeNotificationsTO.setReqUserId(userMstrReqEntity.getUserId());
            employeeNotificationsTO.setFromUserName(employeeNotificationsEntity.getReqUserId().getDisplayName());
        }
        if (null != projMstrEntity) {
            employeeNotificationsTO.setProjId(projMstrEntity.getProjectId());
        }
        if (null != forProjMstrEntity) {
            employeeNotificationsTO.setForProject(forProjMstrEntity);
        }
        
        employeeNotificationsTO.setNotifyStatus(employeeNotificationsEntity.getNotifyStatus());
        employeeNotificationsTO.setType(employeeNotificationsEntity.getType());
        employeeNotificationsTO.setStatus(employeeNotificationsEntity.getStatus());
        employeeNotificationsTO
                .setRequistionCode(EmployeeLeaveNotificationsHandler.generateReqCode(employeeNotificationsEntity));
        return employeeNotificationsTO;
    }

    public static EmployeeNotificationsEntity convertPOJOToEntity(EmployeeNotificationsTO employeeNotificationsTO,
            EPSProjRepository epsProjRepository, LoginRepository loginRepository,
            ClientRegRepository clientRegRepository) {
        EmployeeNotificationsEntity employeeNotificationsEntity = new EmployeeNotificationsEntity();
        if (CommonUtil.isNonBlankLong(employeeNotificationsTO.getId())) {
            employeeNotificationsEntity.setId(employeeNotificationsTO.getId());
        }

        if (CommonUtil.isNotBlankStr(employeeNotificationsTO.getDate())) {
            employeeNotificationsEntity.setDate(CommonUtil.convertStringToDate(employeeNotificationsTO.getDate()));
        } else {
            employeeNotificationsEntity.setDate(new Date());
        }
        
        if (CommonUtil.isNonBlankLong(employeeNotificationsTO.getProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(employeeNotificationsTO.getProjId());
            employeeNotificationsEntity.setProjMstrEntity(projMstrEntity);
        }
        /*
        if (CommonUtil.isNonBlankLong(employeeNotificationsTO.getForProject())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(employeeNotificationsTO.getForProject());
            employeeNotificationsEntity.setProjMstrEntity(projMstrEntity);
        }
         */
        
        if (CommonUtil.isNonBlankLong(employeeNotificationsTO.getForProject())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(employeeNotificationsTO.getForProject());
            employeeNotificationsEntity.setForProject(projMstrEntity.getProjectId());
        }
        
        if (CommonUtil.isNonBlankLong(employeeNotificationsTO.getApprUserId())) {
            UserMstrEntity userMstrEntity = loginRepository.findOne(employeeNotificationsTO.getApprUserId());
            employeeNotificationsEntity.setApprUserId(userMstrEntity);
        }

        if (CommonUtil.isNonBlankLong(AppUserUtils.getUserId())) {
            UserMstrEntity userMstrEntity = loginRepository.findOne(AppUserUtils.getUserId());
            employeeNotificationsEntity.setReqUserId(userMstrEntity);
        }

        if (CommonUtil.isNonBlankLong(AppUserUtils.getClientId())) {
            ClientRegEntity clientRegEntity = clientRegRepository.findOne(AppUserUtils.getClientId());
            employeeNotificationsEntity.setClientId(clientRegEntity);
        }

        employeeNotificationsEntity.setNotifyStatus(employeeNotificationsTO.getNotifyStatus());
        employeeNotificationsEntity.setStatus(employeeNotificationsTO.getStatus());
        employeeNotificationsEntity
                .setStartDate(CommonUtil.convertDDMMYYYYStringToDate(employeeNotificationsTO.getStartDate()));
        employeeNotificationsEntity
                .setEndDate(CommonUtil.convertDDMMYYYYStringToDate(employeeNotificationsTO.getEndDate()));
        employeeNotificationsEntity.setRefempID(employeeNotificationsTO.getNotifyRefEmpId());

        return employeeNotificationsEntity;
    }

}
