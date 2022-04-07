package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
//import com.rjtech.projsettings.model.ProjGeneralMstrEntityCopy;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.emp.dto.EmpPayDeductionTO;
import com.rjtech.register.emp.model.EmpPayDeductionEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpPayDeductionHandler {

    public static EmpPayDeductionTO convertEntityToPOJO(EmpPayDeductionEntity entity) {
        EmpPayDeductionTO empPayDeductionTO = new EmpPayDeductionTO();

        empPayDeductionTO.setId(entity.getId());
        if (CommonUtil.isNotBlankDate(entity.getFromDate())) {
            empPayDeductionTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getToDate())) {
            empPayDeductionTO.setToDate(CommonUtil.convertDateToString(entity.getToDate()));
        }
        if (CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity())) {
            empPayDeductionTO.setEmpProjId(entity.getEmpProjRigisterEntity().getId());
            empPayDeductionTO.setProjEmpRegisterTO(
                    EmpEnrollmentDtlHandler.convertMobilizationDateEntityTO(entity.getEmpProjRigisterEntity()));
        }
        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            empPayDeductionTO.setEmpRegId(entity.getEmpRegisterDtlEntity().getId());
        }
        if (CommonUtil.objectNotNull(entity.getProjGeneralMstrEntity())) {
            empPayDeductionTO.setProjGenId(entity.getProjGeneralMstrEntity().getId());
        }
        empPayDeductionTO.setBasicPay(entity.getBasicPay());
        empPayDeductionTO.setUnitPayRate(entity.getUnitOfPayRate());
        empPayDeductionTO.setPayCycle(entity.getPayCycle());
        empPayDeductionTO.setLatest(entity.isLatest());
        empPayDeductionTO.setStatus(entity.getStatus());
        return empPayDeductionTO;
    }

    public static EmpPayDeductionEntity convertPOJOToEntity(EmpPayDeductionTO empPayDeductionTO,
            EmpProjRegisterRepository empProjRegisterRepository, EmpRegisterRepository empRegisterRepository,
            ProjGeneralRepositoryCopy projGeneralRepository) {
        EmpPayDeductionEntity entity = new EmpPayDeductionEntity();

        /*if (CommonUtil.isNonBlankLong(empPayDeductionTO.getId())) {
        entity.setId(empPayDeductionTO.getId());
        }*/
        if (CommonUtil.isNotBlankStr(empPayDeductionTO.getFromDate())) {
            entity.setFromDate(CommonUtil.convertStringToDate(empPayDeductionTO.getFromDate()));
        }
        if (CommonUtil.isNotBlankStr(empPayDeductionTO.getToDate())) {
            entity.setToDate(CommonUtil.convertStringToDate(empPayDeductionTO.getToDate()));
        }
        if (CommonUtil.isNonBlankLong(empPayDeductionTO.getEmpProjId())) {
            EmpProjRigisterEntity empProjRigisterEntity = empProjRegisterRepository
                    .findOne(empPayDeductionTO.getEmpProjId());
            entity.setEmpProjRigisterEntity(empProjRigisterEntity);
        }
        if (CommonUtil.isNonBlankLong(empPayDeductionTO.getEmpRegId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository.findOne(empPayDeductionTO.getEmpRegId());
            entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        if (CommonUtil.isNonBlankLong(empPayDeductionTO.getProjGenId())) {
            ProjGeneralMstrEntity projGeneralMstrEntity = projGeneralRepository
                    .findOne(empPayDeductionTO.getProjGenId());
            entity.setProjGeneralMstrEntity(projGeneralMstrEntity);
        }
        entity.setLatest(empPayDeductionTO.isLatest());
        entity.setBasicPay(empPayDeductionTO.getBasicPay());
        entity.setUnitOfPayRate(empPayDeductionTO.getUnitPayRate());
        entity.setPayCycle(empPayDeductionTO.getPayCycle());
        entity.setStatus(empPayDeductionTO.getStatus());
        return entity;
    }

}
