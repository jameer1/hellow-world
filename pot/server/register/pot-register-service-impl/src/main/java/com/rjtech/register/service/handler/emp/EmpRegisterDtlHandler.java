package com.rjtech.register.service.handler.emp;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.user.dto.UserTO;

public class EmpRegisterDtlHandler {

    public static EmpRegisterDtlTO convertEntityToPOJO(EmpRegisterDtlEntity entity) {
        EmpRegisterDtlTO empRegisterDtlTO = new EmpRegisterDtlTO();

        empRegisterDtlTO.setId(entity.getId());
        empRegisterDtlTO.setEmpCode(entity.getCode());
        if (CommonUtil.objectNotNull(entity.getProjMstrEntity())) {
            empRegisterDtlTO.setProjId(entity.getProjMstrEntity().getProjectId());
            empRegisterDtlTO.setCode(entity.getProjMstrEntity().getCode());
            empRegisterDtlTO.setName(entity.getProjMstrEntity().getProjName());
            if (CommonUtil.objectNotNull(entity.getProjMstrEntity().getParentProjectMstrEntity())) {
                empRegisterDtlTO.setParentCode(entity.getProjMstrEntity().getParentProjectMstrEntity().getCode());
                empRegisterDtlTO.setParentName(entity.getProjMstrEntity().getParentProjectMstrEntity().getProjName());
            }
        }
        empRegisterDtlTO.setFirstName(entity.getFirstName());
        empRegisterDtlTO.setLastName(entity.getLastName());
        empRegisterDtlTO.setGender(entity.getGender());
        CompanyMstrEntity companyMstrEntity = entity.getCompanyMstrEntity();
        if (CommonUtil.objectNotNull(companyMstrEntity)) {
            empRegisterDtlTO.setCmpId(companyMstrEntity.getId());
            empRegisterDtlTO.setCmpCode(companyMstrEntity.getCode());
            empRegisterDtlTO.setCmpName(companyMstrEntity.getName());
        }
        if (CommonUtil.isNotBlankDate(entity.getDob())) {
            empRegisterDtlTO.setDob(CommonUtil.convertDateToString(entity.getDob()));
        }
        EmpClassMstrEntity empClassMstrEntity = entity.getEmpClassMstrEntity();
        if (CommonUtil.objectNotNull(empClassMstrEntity)) {
            empRegisterDtlTO.setEmpClassId(empClassMstrEntity.getId());
            empRegisterDtlTO.setEmpClassName(empClassMstrEntity.getName());
        }
        ProcureCatgDtlEntity catgDtlEntity = entity.getProcureCatgDtlEntity();
        if (CommonUtil.objectNotNull(catgDtlEntity)) {
            empRegisterDtlTO.setProcurecatgId(catgDtlEntity.getId());
            empRegisterDtlTO.setProcurecatgName(catgDtlEntity.getName());
            LabelKeyTO procurelabelKeyTO = new LabelKeyTO();
            procurelabelKeyTO.setId(catgDtlEntity.getId());
            empRegisterDtlTO.setProcureLabelKeyTO(procurelabelKeyTO);
        }
        empRegisterDtlTO.setLocation(entity.getLocation());
        empRegisterDtlTO.setEmpStatus(entity.getEmpStatus());

        empRegisterDtlTO.setStatus(entity.getStatus());

        return empRegisterDtlTO;
    }

    public static void convertPOJOToEntity(EmpRegisterDtlEntity entity, EmpRegisterDtlTO empRegisterDtlTO,
            EPSProjRepository epsProjRepository, CompanyRepository companyRepository,
            EmpClassRepository empClassRepository, ProcureCatgRepository procureCatgRepository) {

        if (CommonUtil.isNonBlankLong(empRegisterDtlTO.getId())) {
            entity.setId(empRegisterDtlTO.getId());
        }
        if (CommonUtil.isNonBlankLong(empRegisterDtlTO.getProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(empRegisterDtlTO.getProjId());
            entity.setProjMstrEntity(projMstrEntity);
        }
        entity.setCode(empRegisterDtlTO.getEmpCode());
        entity.setFirstName(empRegisterDtlTO.getFirstName());
        entity.setLastName(empRegisterDtlTO.getLastName());
        entity.setGender(empRegisterDtlTO.getGender());
        if (CommonUtil.isNotBlankStr(empRegisterDtlTO.getDob())) {
            entity.setDob(CommonUtil.convertStringToDate(empRegisterDtlTO.getDob()));
        }
        entity.setLocation(empRegisterDtlTO.getLocation());
        entity.setEmpStatus(empRegisterDtlTO.getEmpStatus());
        if (CommonUtil.isNonBlankLong(empRegisterDtlTO.getCmpId())) {
            CompanyMstrEntity companyMstrEntity = companyRepository.findOne(empRegisterDtlTO.getCmpId());
            entity.setCompanyMstrEntity(companyMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(empRegisterDtlTO.getEmpClassId())) {
            EmpClassMstrEntity empClassMstrEntity = empClassRepository.findOne(empRegisterDtlTO.getEmpClassId());
            entity.setEmpClassMstrEntity(empClassMstrEntity);
        }
        if (CommonUtil.objectNotNull(empRegisterDtlTO.getProcureLabelKeyTO())) {
            ProcureCatgDtlEntity procureCatgDtlEntity = procureCatgRepository
                    .findOne(empRegisterDtlTO.getProcureLabelKeyTO().getId());
            entity.setProcureCatgDtlEntity(procureCatgDtlEntity);
        }
        entity.setStatus(empRegisterDtlTO.getStatus());

    }

    public static EmpRegisterDtlTO convertEmpRegEntityToPOJO(EmpRegisterDtlEntity entity) {
        EmpRegisterDtlTO empRegisterDtlTO = new EmpRegisterDtlTO();
        empRegisterDtlTO.setId(entity.getId());
        empRegisterDtlTO.setCode(entity.getCode());
        if (CommonUtil.objectNotNull(entity.getProjMstrEntity())) {
            empRegisterDtlTO.setProjId(entity.getProjMstrEntity().getProjectId());
        }
        if (CommonUtil.objectNotNull(entity.getCompanyMstrEntity())) {
            empRegisterDtlTO.setCmpId(entity.getCompanyMstrEntity().getId());
        }
        empRegisterDtlTO.setFirstName(entity.getFirstName());
        empRegisterDtlTO.setLastName(entity.getLastName());
        if (CommonUtil.isNotBlankDate(entity.getDob())) {
            empRegisterDtlTO.setDob(CommonUtil.convertDateToString(entity.getDob()));
        }
        if (CommonUtil.objectNotNull(entity.getEmpClassMstrEntity())) {
            empRegisterDtlTO.setEmpClassId(entity.getEmpClassMstrEntity().getId());
        }
        return empRegisterDtlTO;
    }

    public static UserTO convertUserEntityToPOJO(UserMstrEntity entity) {
        UserTO userTO = new UserTO();
        userTO.setUserId(entity.getUserId());
        com.rjtech.register.model.EmpRegisterDtlEntityCopy empRegDtlEntity = entity.getEmpRegId();
        userTO.setEmpRegId((empRegDtlEntity != null) ? empRegDtlEntity.getId() : null);
        userTO.setEmpCode(entity.getEmpCode());
        userTO.setEmpDesg(entity.getEmpDesg());
        userTO.setFirstName(entity.getFirstName());
        userTO.setLastName(entity.getLastName());
        userTO.setPhone(entity.getPhoneNo());
        userTO.setEmail(entity.getEmail());
        return userTO;
    }

}
