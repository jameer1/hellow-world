package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.emp.dto.EmpPaybleRateDetailTO;
import com.rjtech.register.emp.model.EmpRegularPayRateDetailEntity;
import com.rjtech.register.emp.model.EmpRegularPayRateEntity;

public class EmpRegularPayRateDetailHandler {

    public static EmpPaybleRateDetailTO convertEntityToPOJO(EmpRegularPayRateDetailEntity entity) {

        EmpPaybleRateDetailTO empRegularPayRateDetailTO = new EmpPaybleRateDetailTO();
        empRegularPayRateDetailTO.setId(entity.getId());
        empRegularPayRateDetailTO.setFinanceRegId(entity.getFinanceRegId());
        empRegularPayRateDetailTO.setFinanceRegId(entity.getFinanceRegId());
        empRegularPayRateDetailTO.setPercentage(entity.getPercentage());
        empRegularPayRateDetailTO.setFixedAmount(entity.getFixedAmount());
        if (CommonUtil.objectNotNull(entity.getEmpRegularPayRateEntity())) {
            empRegularPayRateDetailTO.setEmpPaybaleRateId(entity.getEmpRegularPayRateEntity().getId());
        }
        empRegularPayRateDetailTO.setApplicable(entity.getApplicable());
        empRegularPayRateDetailTO.setComments(entity.getComments());
        empRegularPayRateDetailTO.setStatus(entity.getStatus());
        return empRegularPayRateDetailTO;
    }

    public static EmpRegularPayRateDetailEntity convertPOJOToEntity(EmpPaybleRateDetailTO empRegularPayRateDetailTO) {
        EmpRegularPayRateDetailEntity entity = new EmpRegularPayRateDetailEntity();

        /*	if (CommonUtil.isNonBlankLong(empRegularPayRateDetailTO.getId())) {
        entity.setId(empRegularPayRateDetailTO.getId());
        	}
        */ 
        System.out.println("==EmpRegularPayRateDetailHandler=====convertPOJOToEntity");
        entity.setFinanceRegId(empRegularPayRateDetailTO.getFinanceRegId());
        System.out.println("==EmpRegularPayRateDetailHandler=====entity.setFinanceRegId"+entity.getFinanceRegId());
        entity.setPercentage(empRegularPayRateDetailTO.getPercentage());
        System.out.println("==EmpRegularPayRateDetailHandler=====entity.getPercentage"+entity.getPercentage());
        entity.setFixedAmount(empRegularPayRateDetailTO.getFixedAmount());
        if (CommonUtil.isNonBlankLong(empRegularPayRateDetailTO.getEmpPaybaleRateId())) {
            EmpRegularPayRateEntity empRegularPayRateEntity = new EmpRegularPayRateEntity();
            empRegularPayRateEntity.setId(empRegularPayRateDetailTO.getEmpPaybaleRateId());
            entity.setEmpRegularPayRateEntity(empRegularPayRateEntity);
        }
        entity.setApplicable(empRegularPayRateDetailTO.getApplicable());
        System.out.println("==EmpRegularPayRateDetailHandler=====entity.getApplicable"+entity.getApplicable());
        entity.setComments(empRegularPayRateDetailTO.getComments());
        System.out.println("==EmpRegularPayRateDetailHandler=====entity.getComments"+entity.getComments());
        entity.setStatus(empRegularPayRateDetailTO.getStatus());
        System.out.println("==EmpRegularPayRateDetailHandler=====entity.getStatus"+entity.getStatus());
        return entity;
    }
}
