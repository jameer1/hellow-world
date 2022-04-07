package com.rjtech.timemanagement.attendence.service.handler;

import com.rjtech.centrallib.model.ProjLeaveTypeEntity;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
//import com.rjtech.projectlib.model.ProjLeaveTypeEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.timemanagement.attendance.dto.EmpAttendanceDtlTO;
import com.rjtech.timemanagement.attendance.dto.EmpAttendanceMstrTO;
import com.rjtech.timemanagement.attendance.dto.EmpAttendanceTO;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceDtlEntity;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceEntity;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceMstrEntity;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceRepository;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.workdairy.repository.EmpRegisterDtlRepository;
import com.rjtech.timemanagement.workdairy.repository.ProjCrewMstrRepository;
import java.time.LocalDate;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
public class EmpAttendanceHandler {

    public static EmpAttendanceMstrEntity convertPOJOToEntity(EmpAttendanceMstrTO empAttendenceTO,
            EPSProjRepository epsProjRepository, ProjCrewMstrRepository projCrewMstrRepository) {
        EmpAttendanceMstrEntity empAttendenceEntity = new EmpAttendanceMstrEntity();
        if (CommonUtil.isNonBlankLong(empAttendenceTO.getId())) {
            empAttendenceEntity.setId(empAttendenceTO.getId());
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(empAttendenceTO.getProjId());
        ProjCrewMstrEntity projCrewMstrEntity = projCrewMstrRepository.findOne(empAttendenceTO.getCrewId());
        if (null != projMstrEntity) {
            empAttendenceEntity.setProjId(projMstrEntity);
        }
        if (null != projCrewMstrEntity) {
            empAttendenceEntity.setCrewId(projCrewMstrEntity);
        }

        empAttendenceEntity.setCode(empAttendenceTO.getCode());
        empAttendenceEntity.setAttendenceMonth(empAttendenceTO.getAttendenceMonth());

        empAttendenceEntity.setStatus(empAttendenceTO.getStatus());

        return empAttendenceEntity;
    }

    public static EmpAttendanceMstrTO convertEntityToPOJO(EmpAttendanceMstrEntity empAttendenceEntity) {
        EmpAttendanceMstrTO empAttendanceMstrTO = new EmpAttendanceMstrTO();
        empAttendanceMstrTO.setId(empAttendenceEntity.getId());
        empAttendanceMstrTO.setCode(empAttendenceEntity.getProjId().getCode());
        empAttendanceMstrTO.setAttendenceMonth(empAttendenceEntity.getAttendenceMonth());
        LocalDate todaydate = LocalDate.now();
        LocalDate dates = todaydate.withDayOfMonth(1);
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sm.format(empAttendenceEntity.getAttendenceMonth());

		  if(strDate != dates.toString()) { 
			  empAttendanceMstrTO.setStatus(2); 
		  }
		 
		  if(strDate.equals(dates.toString())) {
			  empAttendanceMstrTO.setStatus(1);
		  } 
  
        empAttendanceMstrTO.setCreatedBy(empAttendenceEntity.getCreatedBy().getDisplayName());
        empAttendanceMstrTO.setAttendenceName(empAttendenceEntity.getCode());
        
        ProjMstrEntity projMstrEntity = empAttendenceEntity.getProjId();
        ProjCrewMstrEntity projCrewMstrEntity = empAttendenceEntity.getCrewId();
        if (null != projMstrEntity) {
            empAttendanceMstrTO.setProjId(projMstrEntity.getProjectId());
            empAttendanceMstrTO.setCode(projMstrEntity.getCode());
            empAttendanceMstrTO.setParentCode(projMstrEntity.getParentProjectMstrEntity().getCode());
            empAttendanceMstrTO.setName(projMstrEntity.getProjName());
            empAttendanceMstrTO.setParentName(projMstrEntity.getParentProjectMstrEntity().getProjName());
            empAttendanceMstrTO.setParentProjId(projMstrEntity.getParentProjectMstrEntity().getProjectId());
        }
        if (null != projCrewMstrEntity) {
            empAttendanceMstrTO.setCrewId(projCrewMstrEntity.getId());
            empAttendanceMstrTO.setCrewName(projCrewMstrEntity.getCode());
        }

        

        return empAttendanceMstrTO;
    } 

    public static EmpAttendanceTO convertAttandanceEntityToPOJO(EmpAttendanceEntity empAttendenceEntity) {
        EmpAttendanceTO empAttendanceTO = new EmpAttendanceTO();
        empAttendanceTO.setId(empAttendenceEntity.getId());
        ProjCrewMstrEntity projCrewMstrEntity = empAttendenceEntity.getCrewId();
        if (null != projCrewMstrEntity) {
            empAttendanceTO.setCrewId(projCrewMstrEntity.getId());
        }
        EmpRegisterDtlEntity empRegisterDtlEntity = empAttendenceEntity.getEmpId();
        if (null != empRegisterDtlEntity) {
            empAttendanceTO.setEmpId(empRegisterDtlEntity.getId());
            empAttendanceTO.setEmpCode(empRegisterDtlEntity.getCode());
            empAttendanceTO.setEmpFirstName(empRegisterDtlEntity.getFirstName());
            empAttendanceTO.setEmpLastName(empRegisterDtlEntity.getLastName());
            if (CommonUtil.objectNotNull(empRegisterDtlEntity.getEmpClassMstrEntity()))
                empAttendanceTO.setEmpClassType(empRegisterDtlEntity.getEmpClassMstrEntity().getName());
            if (CommonUtil.objectNotNull(empRegisterDtlEntity.getCompanyMstrEntity()))
                empAttendanceTO.setEmpCmpCatgName(empRegisterDtlEntity.getCompanyMstrEntity().getName());
        }
        empAttendanceTO.setAttandanceId(empAttendenceEntity.getEmpAttendanceMstrEntity().getId());
        empAttendanceTO.setStatus(empAttendenceEntity.getStatus());
        return empAttendanceTO;
    }

    public static EmpAttendanceEntity convertAttandancePOJOToEntity(EmpAttendanceTO empAttendanceTO,
            ProjCrewMstrRepository projCrewMstrRepository, EmpRegisterDtlRepository empRegisterDtlRepository) {
        EmpAttendanceEntity empAttendanceEntity = new EmpAttendanceEntity();
        empAttendanceEntity.setId(empAttendanceTO.getId());
        ProjCrewMstrEntity projCrewMstrEntity = projCrewMstrRepository.findOne(empAttendanceTO.getCrewId());
        if (null != projCrewMstrEntity) {
            empAttendanceEntity.setCrewId(projCrewMstrEntity);
        }
        EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterDtlRepository.findOne(empAttendanceTO.getEmpId());
        if (null != empRegisterDtlEntity) {
            empAttendanceEntity.setEmpId(empRegisterDtlEntity);
        }

        EmpAttendanceMstrEntity attendanceMstrEntity = new EmpAttendanceMstrEntity();
        attendanceMstrEntity.setId(empAttendanceTO.getAttandanceId());
        empAttendanceEntity.setEmpAttendanceMstrEntity(attendanceMstrEntity);

        empAttendanceEntity.setStatus(empAttendanceTO.getStatus());

        return empAttendanceEntity;
    }

    public static EmpAttendanceDtlTO convertDtlEntityToPOJO(EmpAttendanceDtlEntity empAttendenceDtlEntity) {
        EmpAttendanceDtlTO attendenceDtlTO = new EmpAttendanceDtlTO();
        attendenceDtlTO.setId(empAttendenceDtlEntity.getId());

        attendenceDtlTO.setEmpDtlId(empAttendenceDtlEntity.getEmpAttendanceEntity().getId());

        attendenceDtlTO.setAttendenceDate(empAttendenceDtlEntity.getAttendanceDate());
        LabelKeyTO attendanceTypeTO = attendenceDtlTO.getAttendanceTypeTO();
        ProjLeaveTypeEntity projLeaveTypeEntity = empAttendenceDtlEntity.getProjLeaveId();
        if (null != projLeaveTypeEntity) {
            attendanceTypeTO.setId(projLeaveTypeEntity.getId());
        }
        attendanceTypeTO.setCode(empAttendenceDtlEntity.getProjAttdCode());

        attendenceDtlTO.setStatus(empAttendenceDtlEntity.getStatus());

        return attendenceDtlTO;
    }

    public static EmpAttendanceDtlEntity convertDtlPOJOToEntity(EmpAttendanceDtlEntity empAttendenceDtlEntity,
            EmpAttendanceDtlTO empAttendenceDtlTO, EmpAttendanceRepository empAttendanceRepository) {
        if (CommonUtil.isNonBlankLong(empAttendenceDtlTO.getEmpDtlId())) {
            EmpAttendanceEntity attendenceEntity = empAttendanceRepository.findOne(empAttendenceDtlTO.getEmpDtlId());
            empAttendenceDtlEntity.setEmpAttendanceEntity(attendenceEntity);
        }
        empAttendenceDtlEntity.setAttendanceDate(empAttendenceDtlTO.getAttendenceDate());
        empAttendenceDtlEntity.setProjAttdCode(empAttendenceDtlTO.getAttendanceTypeTO().getCode());
        empAttendenceDtlEntity.setStatus(empAttendenceDtlTO.getStatus());
        return empAttendenceDtlEntity;
    }

}
