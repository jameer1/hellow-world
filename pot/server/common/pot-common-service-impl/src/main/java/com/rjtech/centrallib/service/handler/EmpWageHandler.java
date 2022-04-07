package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.EmployeeWageRateTO;
import com.rjtech.centrallib.model.EmpWageMstrEntity;
import com.rjtech.centrallib.req.EmpWageSaveReq;
import com.rjtech.centrallib.resp.EmpWageResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class EmpWageHandler {

    public static EmpWageResp convertEntityToPOJO(List<EmpWageMstrEntity> entities) {
        EmpWageResp empWageResp = new EmpWageResp();
        EmployeeWageRateTO classificationTO = null;
        for (EmpWageMstrEntity entity : entities) {
            classificationTO = new EmployeeWageRateTO();
            classificationTO.setWageRateId(entity.getId());
            if (CommonUtil.objectNotNull(entity.getClientId())) {
                classificationTO.setClientId(entity.getClientId().getClientId());
            }
            classificationTO.setCode(entity.getCode());
            classificationTO.setName(entity.getName());
            classificationTO.setWageFactor(entity.getWageFactor());
            classificationTO.setStatus(entity.getStatus());
            empWageResp.getEmployeeWageRateTOs().add(classificationTO);
        }
        return empWageResp;
    }

    public static List<EmpWageMstrEntity> convertPOJOToEntity(EmpWageSaveReq empWageSaveReq) {
        List<EmpWageMstrEntity> empWageMstrEntitys = new ArrayList<EmpWageMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        EmpWageMstrEntity empWageMstrEntity = null;
        for (EmployeeWageRateTO employeeWageRateTO : empWageSaveReq.getEmployeeWageRateTOs()) {
            empWageMstrEntity = new EmpWageMstrEntity();
            if (CommonUtil.isNonBlankLong(employeeWageRateTO.getWageRateId())) {
                empWageMstrEntity.setId(employeeWageRateTO.getWageRateId());
            }
            empWageMstrEntity.setCode(employeeWageRateTO.getCode());
            empWageMstrEntity.setName(employeeWageRateTO.getName());
            empWageMstrEntity.setWageFactor(employeeWageRateTO.getWageFactor());

            empWageMstrEntity.setStatus(employeeWageRateTO.getStatus());

            empWageMstrEntitys.add(empWageMstrEntity);
        }
        return empWageMstrEntitys;
    }

}
