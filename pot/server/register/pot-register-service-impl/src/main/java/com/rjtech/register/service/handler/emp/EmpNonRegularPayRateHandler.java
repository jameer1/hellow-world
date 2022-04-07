package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.finance.repository.NonRegularAllowanceRepository;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
//import com.rjtech.projsettings.model.ProjGeneralMstrEntityCopy;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.emp.dto.EmpPaybleRateDetailTO;
import com.rjtech.register.emp.dto.EmpPaybleRateTO;
import com.rjtech.register.emp.model.EmpNonRegularPayDetailEntity;
import com.rjtech.register.emp.model.EmpNonRegularPayRateEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpNonRegularPayRateHandler {

    public static EmpNonRegularPayRateEntity populateEmpNonRegularPayEntity(EmpPaybleRateTO empPaybleRateTO,
            NonRegularAllowanceRepository nonRegularAllowanceRepository,
            EmpProjRegisterRepository empProjRegisterRepository, EmpRegisterRepository empRegisterRepository,
            ProjGeneralRepositoryCopy projGeneralRepository) {
        EmpNonRegularPayRateEntity empNonRegularPayRateEntity = null;
        EmpNonRegularPayDetailEntity empNonRegularPayDetailEntity = null;
        empNonRegularPayRateEntity = convertPOJOToEntity(empPaybleRateTO, empProjRegisterRepository,
                empRegisterRepository, projGeneralRepository);
        for (EmpPaybleRateDetailTO empPaybleRateDetailTO : empPaybleRateTO.getEmpPaybleRateDetailTOs()) {
            empNonRegularPayDetailEntity = EmpNonRegularPayDetailHandler.convertPOJOToEntity(empPaybleRateDetailTO,
                    nonRegularAllowanceRepository);
            empNonRegularPayDetailEntity.setEmpNonRegularPayRateEntity(empNonRegularPayRateEntity);
            empNonRegularPayRateEntity.getEmpNonRegularPayDetailEntities().add(empNonRegularPayDetailEntity);
        }
        return empNonRegularPayRateEntity;
    }

    public static EmpPaybleRateTO convertEntityToPOJO(EmpNonRegularPayRateEntity entity) {
        EmpPaybleRateTO empRegularPaybleRateTO = new EmpPaybleRateTO();

        empRegularPaybleRateTO.setId(entity.getId());
        if (CommonUtil.isNotBlankDate(entity.getFromDate())) {
            empRegularPaybleRateTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        } else if (CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity())) {
            empRegularPaybleRateTO.setFromDate(
                    CommonUtil.convertDateToString(entity.getEmpProjRigisterEntity().getMobilaizationDate()));
        }
        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            empRegularPaybleRateTO.setEmpRegId(entity.getEmpRegisterDtlEntity().getId());
        }
        empRegularPaybleRateTO.setBasicPay(entity.getBasicPay());
        if (CommonUtil.objectNotNull(entity.getProjGeneralMstrEntity())) {
            empRegularPaybleRateTO.setProjGenerealId(entity.getProjGeneralMstrEntity().getId());
        }
        empRegularPaybleRateTO.setUnitPayRate(entity.getUnitOfPayRate());
        if (CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity())) {
            empRegularPaybleRateTO.setEmpProjId(entity.getEmpProjRigisterEntity().getId());
        }
        empRegularPaybleRateTO.setPayCycle(entity.getPayCycle());
        empRegularPaybleRateTO.setProjEmpRegisterTO(
                EmpEnrollmentDtlHandler.convertMobilizationDateEntityTO(entity.getEmpProjRigisterEntity()));
        if (CommonUtil.isNotBlankDate(entity.getFromDate())) {
            empRegularPaybleRateTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getToDate())) {
            empRegularPaybleRateTO.setToDate(CommonUtil.convertDateToString(entity.getToDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getPayRollDate())) {
            empRegularPaybleRateTO.setPayRollDate(CommonUtil.convertDateToString(entity.getPayRollDate()));
        }
        empRegularPaybleRateTO.setLatest(entity.isLatest());

        empRegularPaybleRateTO.setStatus(entity.getStatus());
        return empRegularPaybleRateTO;
    }

    public static EmpNonRegularPayRateEntity convertPOJOToEntity(EmpPaybleRateTO empPaybleRateTO,
            EmpProjRegisterRepository empProjRegisterRepository, EmpRegisterRepository empRegisterRepository,
            ProjGeneralRepositoryCopy projGeneralRepository) {
        EmpNonRegularPayRateEntity entity = new EmpNonRegularPayRateEntity();

        if (CommonUtil.isNonBlankLong(empPaybleRateTO.getId())) {
            entity.setId(empPaybleRateTO.getId());
        }
        if (CommonUtil.isNotBlankStr(empPaybleRateTO.getFromDate())) {
            entity.setFromDate(CommonUtil.convertStringToDate(empPaybleRateTO.getFromDate()));
        }
        if (CommonUtil.isNotBlankStr(empPaybleRateTO.getToDate())) {
            entity.setToDate(CommonUtil.convertStringToDate(empPaybleRateTO.getToDate()));
        }
        if (CommonUtil.isNotBlankStr(empPaybleRateTO.getPayRollDate())) {
            entity.setPayRollDate(CommonUtil.convertStringToDate(empPaybleRateTO.getPayRollDate()));
        }
        if (CommonUtil.isNonBlankLong(empPaybleRateTO.getEmpProjId())) {
            EmpProjRigisterEntity empProjRigisterEntity = empProjRegisterRepository
                    .findOne(empPaybleRateTO.getEmpProjId());
            entity.setEmpProjRigisterEntity(empProjRigisterEntity);
        }
        if (CommonUtil.isNonBlankLong(empPaybleRateTO.getEmpRegId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository.findOne(empPaybleRateTO.getEmpRegId());
            entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        if (CommonUtil.isNonBlankLong(empPaybleRateTO.getProjGenerealId())) {
            ProjGeneralMstrEntity projGeneralMstrEntity = projGeneralRepository
                    .findOne(empPaybleRateTO.getProjGenerealId());
            entity.setProjGeneralMstrEntity(projGeneralMstrEntity);
        }
        entity.setBasicPay(empPaybleRateTO.getBasicPay());
        entity.setUnitOfPayRate(empPaybleRateTO.getUnitPayRate());
        entity.setPayCycle(empPaybleRateTO.getPayCycle());
        entity.setLatest(empPaybleRateTO.isLatest());
        entity.setStatus(empPaybleRateTO.getStatus());
        return entity;
    }

}
