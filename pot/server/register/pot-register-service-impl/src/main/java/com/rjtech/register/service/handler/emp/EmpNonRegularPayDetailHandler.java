package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.finance.model.NonRegularPayAllowanceEntity;
//import com.rjtech.finance.model.NonRegularPayAllowanceEntityCopy;
import com.rjtech.finance.repository.NonRegularAllowanceRepository;
import com.rjtech.register.emp.dto.EmpPaybleRateDetailTO;
import com.rjtech.register.emp.model.EmpNonRegularPayDetailEntity;
import com.rjtech.register.emp.model.EmpNonRegularPayRateEntity;

public class EmpNonRegularPayDetailHandler {

    public static EmpPaybleRateDetailTO convertEntityToPOJO(EmpNonRegularPayDetailEntity entity) {

        EmpPaybleRateDetailTO empRegularPaybleRateDetailTO = new EmpPaybleRateDetailTO();
        empRegularPaybleRateDetailTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getRegularPayAllowanceEntity())) {
            empRegularPaybleRateDetailTO.setFinanceRegId(entity.getRegularPayAllowanceEntity().getId());
        }
        if (CommonUtil.objectNotNull(entity.getEmpNonRegularPayRateEntity())) {
            empRegularPaybleRateDetailTO.setEmpPaybaleRateId(entity.getEmpNonRegularPayRateEntity().getId());
        }
        empRegularPaybleRateDetailTO.setPercentage(entity.getBonusPay());
        empRegularPaybleRateDetailTO.setFixedAmount(entity.getFixedPay());
        empRegularPaybleRateDetailTO.setApplicable(entity.getApplicable());
        empRegularPaybleRateDetailTO.setComments(entity.getComments());
        empRegularPaybleRateDetailTO.setStatus(entity.getStatus());
        return empRegularPaybleRateDetailTO;
    }

    public static EmpNonRegularPayDetailEntity convertPOJOToEntity(EmpPaybleRateDetailTO empPaybleRateDetailTO,
            NonRegularAllowanceRepository nonRegularAllowanceRepository) {
        EmpNonRegularPayDetailEntity entity = new EmpNonRegularPayDetailEntity();

        if (CommonUtil.isNonBlankLong(empPaybleRateDetailTO.getFinanceRegId())) {
            NonRegularPayAllowanceEntity allowanceEntity = nonRegularAllowanceRepository
                    .findOne(empPaybleRateDetailTO.getFinanceRegId());
            entity.setRegularPayAllowanceEntity(allowanceEntity);
        }
        entity.setBonusPay(empPaybleRateDetailTO.getPercentage());
        entity.setFixedPay(empPaybleRateDetailTO.getFixedAmount());
        if (CommonUtil.isNonBlankLong(empPaybleRateDetailTO.getEmpPaybaleRateId())) {
            EmpNonRegularPayRateEntity empNonRegularPaybleRateEntity = new EmpNonRegularPayRateEntity();
            empNonRegularPaybleRateEntity.setId(empPaybleRateDetailTO.getEmpPaybaleRateId());
            entity.setEmpNonRegularPayRateEntity(empNonRegularPaybleRateEntity);
        }
        entity.setApplicable(empPaybleRateDetailTO.getApplicable());
        entity.setComments(empPaybleRateDetailTO.getComments());
        entity.setStatus(empPaybleRateDetailTO.getStatus());
        return entity;
    }
}
