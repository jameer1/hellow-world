package com.rjtech.register.service.handler.emp;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.emp.dto.EmpMedicalHistoryTO;
import com.rjtech.register.emp.model.EmpMedicalHistoryEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.model.ProjDocFileEntity;

public class EmpMedicalHistoryHandler {

    public static EmpMedicalHistoryTO convertEntityToPOJO(EmpMedicalHistoryEntity entity) {
        EmpMedicalHistoryTO empMedicalHistoryTO = new EmpMedicalHistoryTO();

        empMedicalHistoryTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            empMedicalHistoryTO.setEmpRegId(entity.getEmpRegisterDtlEntity().getId());
        }
        if (CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity())) {
            empMedicalHistoryTO.setEmpProjId(entity.getEmpProjRigisterEntity().getId());
        }
        empMedicalHistoryTO.setProjEmpRegisterTO(
                EmpEnrollmentDtlHandler.convertMobilizationDateEntityTO(entity.getEmpProjRigisterEntity()));

        if (CommonUtil.isNotBlankDate(entity.getFromDate())) {
            empMedicalHistoryTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        }
        empMedicalHistoryTO.setRecordId(entity.getRecordId());
        empMedicalHistoryTO.setItem(entity.getItem());
        empMedicalHistoryTO.setParticular(entity.getParticular());
        empMedicalHistoryTO.setComments(entity.getComments());
        empMedicalHistoryTO.setFileName(entity.getFileName());
                
        empMedicalHistoryTO.setStatus(entity.getStatus());
        return empMedicalHistoryTO;
    }

    public static EmpMedicalHistoryEntity convertPOJOToEntity(EmpMedicalHistoryTO empMedicalHistoryTO,
            EmpRegisterRepository empRegisterRepository, EmpProjRegisterRepository empProjRegisterRepository, ProjDocFileEntity projDocFileEntity) {
        EmpMedicalHistoryEntity entity = new EmpMedicalHistoryEntity();

        if (CommonUtil.isNonBlankLong(empMedicalHistoryTO.getId())) {
            entity.setId(empMedicalHistoryTO.getId());
        }
        if (CommonUtil.isNonBlankLong(empMedicalHistoryTO.getEmpRegId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                    .findOne(empMedicalHistoryTO.getEmpRegId());
            entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        if (CommonUtil.isNonBlankLong(empMedicalHistoryTO.getEmpProjId())) {
            EmpProjRigisterEntity empProjRigisterEntity = empProjRegisterRepository
                    .findOne(empMedicalHistoryTO.getEmpProjId());
            entity.setEmpProjRigisterEntity(empProjRigisterEntity);
        }

        if (CommonUtil.isNotBlankStr(empMedicalHistoryTO.getFromDate())) {
            entity.setFromDate(CommonUtil.convertStringToDate(empMedicalHistoryTO.getFromDate()));
        }
        entity.setRecordId(empMedicalHistoryTO.getRecordId());
        entity.setItem(empMedicalHistoryTO.getItem());
        entity.setParticular(empMedicalHistoryTO.getParticular());
        entity.setComments(empMedicalHistoryTO.getComments());
        entity.setFileName( empMedicalHistoryTO.getFileName() );
        entity.setProjDocFileEntity( projDocFileEntity );
        entity.setStatus(empMedicalHistoryTO.getStatus());        
        return entity;
    }
}
