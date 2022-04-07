package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.finance.repository.ProvidentFundRepository;
//import com.rjtech.finance.repository.ProvidentFundRepositoryCopy;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.emp.dto.EmpProvidentFundContributionTO;
import com.rjtech.register.emp.dto.EmpProvidentFundDetailTO;
import com.rjtech.register.emp.dto.EmpProvidentFundTO;
import com.rjtech.register.emp.dto.EmpProvidentFundTaxTO;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpProvidentFundContributionEntity;
import com.rjtech.register.emp.model.EmpProvidentFundDetailEntity;
import com.rjtech.register.emp.model.EmpProvidentFundEntity;
import com.rjtech.register.emp.model.EmpProvidentFundTaxEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpProvidentFundHandler {

    public static EmpProvidentFundEntity populateProvientFundEntity(EmpProvidentFundTO empProvidentFundDtlTO,
            ProvidentFundRepository providentFundRepository, EmpProjRegisterRepository empProjRegisterRepository,
            EmpRegisterRepository empRegisterRepository, ProjGeneralRepositoryCopy projGeneralRepository) {
        EmpProvidentFundEntity empProvidentFundEntity = null;
        EmpProvidentFundContributionEntity empProvidentFundContributionEntity = null;
        EmpProvidentFundDetailEntity empProvidentFundDetailEntity = null;
        EmpProvidentFundTaxEntity empProvidentFundTaxEntity = null;
        empProvidentFundEntity = convertPOJOToEntity(empProvidentFundDtlTO, empProjRegisterRepository,
                empRegisterRepository, projGeneralRepository);
        for (EmpProvidentFundDetailTO empProvidentFundDetailTO : empProvidentFundDtlTO.getEmpProvidentFundDetailTOs()) {
            empProvidentFundDetailEntity = EmpProvidentFundDetailHandler.convertPOJOToEntity(empProvidentFundDetailTO);
            empProvidentFundDetailEntity.setEmpProvidentFundEntity(empProvidentFundEntity);
            empProvidentFundEntity.getEmpProvidentFundDetailEntities().add(empProvidentFundDetailEntity);
        }
        for (EmpProvidentFundContributionTO empProvidentFundContributionTO : empProvidentFundDtlTO
                .getEmpProvidentFundContributionTOs()) {
            empProvidentFundContributionEntity = EmpProvidentFundDetailHandler
                    .convertContributionPOJOToEntity(empProvidentFundContributionTO, providentFundRepository);
            empProvidentFundContributionEntity.setEmpProvidentFundEntity(empProvidentFundEntity);
            empProvidentFundEntity.getEmpProvidentFundContributionEntities().add(empProvidentFundContributionEntity);
        }
        for (EmpProvidentFundTaxTO empProvidentFundTaxTO : empProvidentFundDtlTO.getEmpProvidentFundTaxTOs()) {
            empProvidentFundTaxEntity = EmpProvidentFundDetailHandler.convertTaxPOJOToEntity(empProvidentFundTaxTO);
            empProvidentFundTaxEntity.setEmpProvidentFundEntity(empProvidentFundEntity);
            empProvidentFundEntity.getEmpProvidentFundTaxEntities().add(empProvidentFundTaxEntity);
        }
        return empProvidentFundEntity;
    }

    public static EmpProvidentFundEntity convertPOJOToEntity(EmpProvidentFundTO empProvidentFundDtlTO,
            EmpProjRegisterRepository empProjRegisterRepository, EmpRegisterRepository empRegisterRepository,
            ProjGeneralRepositoryCopy projGeneralRepository) {
        EmpProvidentFundEntity entity = new EmpProvidentFundEntity();

        if (CommonUtil.isNonBlankLong(empProvidentFundDtlTO.getId())) {
            entity.setId(empProvidentFundDtlTO.getId());
        }
        if (CommonUtil.isNonBlankLong(empProvidentFundDtlTO.getEmpProjId())) {
            EmpProjRigisterEntity empProjRigisterEntity = empProjRegisterRepository
                    .findOne(empProvidentFundDtlTO.getEmpProjId());
            entity.setEmpProjRigisterEntity(empProjRigisterEntity);
        }
        if (CommonUtil.isNonBlankLong(empProvidentFundDtlTO.getEmpRegId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                    .findOne(empProvidentFundDtlTO.getEmpRegId());
            entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        if (CommonUtil.isNonBlankLong(empProvidentFundDtlTO.getProjGenId())) {
            ProjGeneralMstrEntity projGeneralMstrEntity = projGeneralRepository
                    .findOne(empProvidentFundDtlTO.getProjGenId());
            entity.setProjGeneralMstrEntity(projGeneralMstrEntity);
        }
        if (CommonUtil.isNotBlankStr(empProvidentFundDtlTO.getFromDate())) {
            entity.setFromDate(CommonUtil.convertStringToDate(empProvidentFundDtlTO.getFromDate()));
        }
        if (CommonUtil.isNotBlankStr(empProvidentFundDtlTO.getToDate())) {
            entity.setToDate(CommonUtil.convertStringToDate(empProvidentFundDtlTO.getToDate()));
        }
        entity.setPayCycle(empProvidentFundDtlTO.getPayCycle());
        entity.setLatest(empProvidentFundDtlTO.isLatest());
        entity.setStatus(empProvidentFundDtlTO.getStatus());

        return entity;
    }

    public static EmpProvidentFundTO convertEntityToPOJO(EmpProvidentFundEntity entity) {

        EmpProvidentFundTO empProvidentFundTO = new EmpProvidentFundTO();

        empProvidentFundTO.setId(entity.getId());
        empProvidentFundTO.setPayCycle(entity.getPayCycle());
        empProvidentFundTO.setProjEmpRegisterTO(
                EmpEnrollmentDtlHandler.convertMobilizationDateEntityTO(entity.getEmpProjRigisterEntity()));

        if (CommonUtil.objectNotNull(entity.getProjGeneralMstrEntity())) {
            empProvidentFundTO.setProjGenId(entity.getProjGeneralMstrEntity().getId());
        }
        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            empProvidentFundTO.setEmpRegId(entity.getEmpRegisterDtlEntity().getId());
        }
        if (CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity())) {
            empProvidentFundTO.setEmpProjId(entity.getEmpProjRigisterEntity().getId());
        }
        if (CommonUtil.isNotBlankDate(entity.getFromDate())) {
            empProvidentFundTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getToDate())) {
            empProvidentFundTO.setToDate(CommonUtil.convertDateToString(entity.getToDate()));
        }
        empProvidentFundTO.setLatest(entity.isLatest());
        empProvidentFundTO.setStatus(entity.getStatus());

        return empProvidentFundTO;
    }

}
