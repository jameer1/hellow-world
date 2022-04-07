package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.finance.model.ProvidentFundEntity;
import com.rjtech.finance.repository.ProvidentFundRepository;
//import com.rjtech.finance.model.ProvidentFundEntityCopy;
//import com.rjtech.finance.repository.ProvidentFundRepositoryCopy;
import com.rjtech.register.emp.dto.EmpPayDeductionDetailTO;
import com.rjtech.register.emp.dto.EmpPayDeductionTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.model.EmpPayDeductionDtlEntity;
import com.rjtech.register.emp.model.EmpPayDeductionEntity;
import com.rjtech.register.emp.resp.EmpPayDeductionResp;

public class EmpPayDeductionDetailHandler {

    public static EmpPayDeductionDetailTO convertEntityToPOJO(EmpPayDeductionDtlEntity entity) {

        EmpPayDeductionDetailTO empPayDeductionDetailTO = new EmpPayDeductionDetailTO();
        empPayDeductionDetailTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getProvidentFundEntity())) {
            empPayDeductionDetailTO.setFinanceDeductionId(entity.getProvidentFundEntity().getId());
        }
        empPayDeductionDetailTO.setPercentage(entity.getPercentage());
        empPayDeductionDetailTO.setFixedAmount(entity.getFixedPay());
        empPayDeductionDetailTO.setApplicable(entity.getApplicable());
        if (CommonUtil.objectNotNull(entity.getEmpPayDeductionEntity())) {
            empPayDeductionDetailTO.setPayDeductionId(entity.getEmpPayDeductionEntity().getId());
        }
        empPayDeductionDetailTO.setStatus(entity.getStatus());
        return empPayDeductionDetailTO;
    }

    public static EmpPayDeductionDtlEntity convertPOJOToEntity(EmpPayDeductionDetailTO empPayDeductionDetailTO,
            ProvidentFundRepository providentFundRepository) {
        EmpPayDeductionDtlEntity entity = new EmpPayDeductionDtlEntity();

        /*if (CommonUtil.isNonBlankLong(empPayDeductionDetailTO.getId())) {
        entity.setId(empPayDeductionDetailTO.getId());
        }*/
        if (CommonUtil.isNonBlankLong(empPayDeductionDetailTO.getFinanceDeductionId())) {
            ProvidentFundEntity providentFundEntity = providentFundRepository
                    .findOne(empPayDeductionDetailTO.getFinanceDeductionId());
            entity.setProvidentFundEntity(providentFundEntity);
        }
        entity.setPercentage(empPayDeductionDetailTO.getPercentage());
        entity.setFixedPay(empPayDeductionDetailTO.getFixedAmount());
        entity.setApplicable(empPayDeductionDetailTO.getApplicable());

        if (CommonUtil.isNonBlankLong(empPayDeductionDetailTO.getPayDeductionId())) {
            EmpPayDeductionEntity empPayDeductionEntity = new EmpPayDeductionEntity();
            empPayDeductionEntity.setId(empPayDeductionDetailTO.getPayDeductionId());
            entity.setEmpPayDeductionEntity(empPayDeductionEntity);
        }
        entity.setStatus(empPayDeductionDetailTO.getStatus());
        return entity;
    }

    public static void populatePayDeductionTO(EmpPayDeductionResp empPayDeductionResp,
            ProjEmpRegisterTO projEmpRegisterTO) {
        EmpPayDeductionTO empPayDeductionTO = new EmpPayDeductionTO();
        empPayDeductionTO.setFromDate(projEmpRegisterTO.getMobilaizationDate());
        empPayDeductionTO.setEmpProjId(projEmpRegisterTO.getId());
        empPayDeductionTO.setEmpRegId(projEmpRegisterTO.getEmpId());
        empPayDeductionTO.setProjEmpRegisterTO(projEmpRegisterTO);
        empPayDeductionResp.getEmpPayDeductionTOs().add(empPayDeductionTO);
    }
}
