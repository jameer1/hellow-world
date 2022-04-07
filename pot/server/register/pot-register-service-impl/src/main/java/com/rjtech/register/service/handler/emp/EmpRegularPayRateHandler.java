package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
//import com.rjtech.finance.repository.ProjGeneralRepositoryCopy;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.projsettings.repository.ProjGeneralRepository;
//import com.rjtech.projsettings.model.ProjGeneralMstrEntityCopy;
//import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.emp.dto.EmpPaybleRateDetailTO;
import com.rjtech.register.emp.dto.EmpPaybleRateTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.emp.model.EmpRegularPayRateDetailEntity;
import com.rjtech.register.emp.model.EmpRegularPayRateEntity;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpRegularPayRateHandler {

    public static EmpRegularPayRateEntity populateEmpRegularPayEntity(EmpPaybleRateTO empPaybleRateTO,
            EmpRegisterRepository empRegisterRepository, ProjGeneralRepository projGeneralRepository,
            EmpProjRegisterRepository empProjRegisterRepository) {
    	 System.out.println("==EmpRegularPayRateHandler=====populateEmpRegularPayEntity");
        EmpRegularPayRateEntity empRegularPaybleRateEntity = null;
        EmpRegularPayRateDetailEntity empRegularPaybleRateDetailEntity = null;
        empRegularPaybleRateEntity = convertPOJOToEntity(empPaybleRateTO, empRegisterRepository, projGeneralRepository,
                empProjRegisterRepository);
        System.out.println("==EmpRegularPayRateHandler=====after /pojo");
        for (EmpPaybleRateDetailTO empRegularPaybleRateDetailTO : empPaybleRateTO.getEmpPaybleRateDetailTOs()) {
            empRegularPaybleRateDetailEntity = EmpRegularPayRateDetailHandler
                    .convertPOJOToEntity(empRegularPaybleRateDetailTO);
            empRegularPaybleRateDetailEntity.setEmpRegularPayRateEntity(empRegularPaybleRateEntity);
            empRegularPaybleRateEntity.getEmpRegularPayRateDetailEntities().add(empRegularPaybleRateDetailEntity);
        }
        return empRegularPaybleRateEntity;
    }

    public static EmpPaybleRateTO convertEntityToPOJO(EmpRegularPayRateEntity entity) {
    	 System.out.println("====starting====");
        EmpPaybleRateTO empRegularPaybleRateTO = new EmpPaybleRateTO();
        System.out.println("====convertEntityToPOJO====");
        empRegularPaybleRateTO.setId(entity.getId());
        System.out.println("====empRegularPaybleRateTO===="+empRegularPaybleRateTO.getId());
        if (CommonUtil.isNotBlankDate(entity.getFromDate())) {
            empRegularPaybleRateTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        } else if (CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity())) {
            empRegularPaybleRateTO.setFromDate(
                    CommonUtil.convertDateToString(entity.getEmpProjRigisterEntity().getMobilaizationDate()));
        }
        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            empRegularPaybleRateTO.setEmpRegId(entity.getEmpRegisterDtlEntity().getId());
        }
        if (CommonUtil.objectNotNull(entity.getProjGeneralMstrEntity())) {
            empRegularPaybleRateTO.setProjGenerealId(entity.getProjGeneralMstrEntity().getId());
        }
        if (CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity())) {
            empRegularPaybleRateTO.setEmpProjId(entity.getEmpProjRigisterEntity().getId());
        }
        empRegularPaybleRateTO.setBasicPay(entity.getBasicPay());
        empRegularPaybleRateTO.setUnitPayRate(entity.getUnitPayRate());
        empRegularPaybleRateTO.setPayCycle(entity.getPayCycle());
        empRegularPaybleRateTO.setProjEmpRegisterTO(
                EmpEnrollmentDtlHandler.convertMobilizationDateEntityTO(entity.getEmpProjRigisterEntity()));
        if (CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity())
                && CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity().getEmpRegisterDtlEntity())) {
            empRegularPaybleRateTO.setEmpRegId(entity.getEmpProjRigisterEntity().getEmpRegisterDtlEntity().getId());
        }
        empRegularPaybleRateTO.setLatest(entity.isLatest());

        empRegularPaybleRateTO.setStatus(entity.getStatus());
        return empRegularPaybleRateTO;
    }

    public static EmpRegularPayRateEntity convertPOJOToEntity(EmpPaybleRateTO empRegularPaybleRateTO,
            EmpRegisterRepository empRegisterRepository, ProjGeneralRepository projGeneralRepository,
            EmpProjRegisterRepository empProjRegisterRepository) {
    	 System.out.println("==EmpRegularPayRateHandler=====convertPOJOToEntity");
        EmpRegularPayRateEntity entity = new EmpRegularPayRateEntity();

        if (CommonUtil.isNonBlankLong(empRegularPaybleRateTO.getId())) {
            entity.setId(empRegularPaybleRateTO.getId());
        }
        System.out.println("==EmpRegularPayRateHandler=====convertPOJOToEntity===entity.setId"+entity.getId());
        if (CommonUtil.isNotBlankStr(empRegularPaybleRateTO.getFromDate())) {
            entity.setFromDate(CommonUtil.convertStringToDate(empRegularPaybleRateTO.getFromDate()));
        }
        System.out.println("==EmpRegularPayRateHandler=====empRegularPaybleRateTO.getToDate()");
        if (CommonUtil.isNotBlankStr(empRegularPaybleRateTO.getToDate())) {
            entity.setToDate(CommonUtil.convertStringToDate(empRegularPaybleRateTO.getToDate()));
        }
        System.out.println("==EmpRegularPayRateHandler=====empRegularPaybleRateTO.getEmpRegId())");
        if (CommonUtil.isNonBlankLong(empRegularPaybleRateTO.getEmpRegId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                    .findOne(empRegularPaybleRateTO.getEmpRegId());
            entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        System.out.println("==EmpRegularPayRateHandler=====empRegularPaybleRateTO.getProjGenerealId()");
        if (CommonUtil.isNonBlankLong(empRegularPaybleRateTO.getProjGenerealId())) {
            ProjGeneralMstrEntity projGeneralMstrEntity = projGeneralRepository
                    .findOne(empRegularPaybleRateTO.getProjGenerealId());
            entity.setProjGeneralMstrEntity(projGeneralMstrEntity);
        }
        System.out.println("==EmpRegularPayRateHandler=====empRegularPaybleRateTO.getEmpProjId())");
        if (CommonUtil.isNonBlankLong(empRegularPaybleRateTO.getEmpProjId())) {
            EmpProjRigisterEntity empProjRigisterEntity = empProjRegisterRepository
                    .findOne(empRegularPaybleRateTO.getEmpProjId());
            entity.setEmpProjRigisterEntity(empProjRigisterEntity);
        }
        System.out.println("==EmpRegularPayRateHandler==2222222===empRegularPaybleRateTO.getEmpProjId())");
        entity.setLatest(empRegularPaybleRateTO.isLatest());
        System.out.println("==EmpRegularPayRateHandler=====entity.setLatest"+entity.isLatest());
        entity.setBasicPay(empRegularPaybleRateTO.getBasicPay());
        System.out.println("==EmpRegularPayRateHandler=====setBasicPay"+entity.getBasicPay());
        entity.setUnitPayRate(empRegularPaybleRateTO.getUnitPayRate());
        System.out.println("==EmpRegularPayRateHandler=====entity.setUnitPayRate"+entity.getUnitPayRate());
        entity.setPayCycle(empRegularPaybleRateTO.getPayCycle());
        System.out.println("==EmpRegularPayRateHandler=====entity.getPayCycle"+entity.getPayCycle());
        entity.setStatus(empRegularPaybleRateTO.getStatus());
        System.out.println("==EmpRegularPayRateHandler=====entity.getStatus"+entity.getStatus());
        return entity;
    }

    public static void populateEmpPaybleTOFromEmpProjRegTO(EmpPaybleRateResp empPaybleRateResp,
            ProjEmpRegisterTO projEmpRegisterTO) {
        EmpPaybleRateTO empPaybleRateTO = new EmpPaybleRateTO();
        empPaybleRateTO.setEmpProjId(projEmpRegisterTO.getId());
        empPaybleRateTO.setEmpRegId(projEmpRegisterTO.getEmpId());
        empPaybleRateTO.setFromDate(projEmpRegisterTO.getMobilaizationDate());
        empPaybleRateTO.setProjEmpRegisterTO(projEmpRegisterTO);
        empPaybleRateResp.getEmpPaybleRateTOs().add(empPaybleRateTO);
    }

}
