package com.rjtech.timemanagement.attendence.service.handler;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.projectlib.repository.ProjCrewRepositoryCopy;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
//import com.rjtech.register.plant.model.PlantRegisterDtlEntityCopy;
import com.rjtech.register.repository.plant.PlantRegisterRepositoryCopy;
import com.rjtech.timemanagement.attendance.dto.PlantAttendanceDtlTO;
import com.rjtech.timemanagement.attendance.dto.PlantAttendanceMstrTO;
import com.rjtech.timemanagement.attendance.dto.PlantAttendanceTO;
import com.rjtech.timemanagement.attendance.model.PlantAttendanceDtlEntity;
import com.rjtech.timemanagement.attendance.model.PlantAttendanceEntity;
import com.rjtech.timemanagement.attendance.model.PlantAttendanceMstrEntity;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
public class PlantAttendanceHandler {

    public static PlantAttendanceMstrTO convertMstrEntityToPOJO(PlantAttendanceMstrEntity plantAttendenceMstrEntity) {
        PlantAttendanceMstrTO plantAttendenceTO = new PlantAttendanceMstrTO();
        plantAttendenceTO.setId(plantAttendenceMstrEntity.getId());
        plantAttendenceTO.setCode(plantAttendenceMstrEntity.getProjMstrEntity().getCode());
        plantAttendenceTO.setAttendanceMonth(plantAttendenceMstrEntity.getAttendanceMonth());
        LocalDate todaydate = LocalDate.now();
        LocalDate dates = todaydate.withDayOfMonth(1);
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sm.format(plantAttendenceMstrEntity.getAttendanceMonth());

		  if(strDate != dates.toString()) { 
			  plantAttendenceTO.setStatus(2); 
		  }
		 
		  if(strDate.equals(dates.toString())) {
			  plantAttendenceTO.setStatus(1);
		  } 
    //    plantAttendenceTO.setStatus(plantAttendenceMstrEntity.getStatus());
        plantAttendenceTO.setCreatedBy(plantAttendenceMstrEntity.getCreatedBy().getDisplayName());
        plantAttendenceTO.setAttendenceName(plantAttendenceMstrEntity.getCode());
        plantAttendenceTO.setCopyTemplate(plantAttendenceMstrEntity.isCopyTemplate());
        
        ProjMstrEntity projMstrEntity = plantAttendenceMstrEntity.getProjMstrEntity();
        ProjCrewMstrEntity projCrewMstrEntity = plantAttendenceMstrEntity.getProjCrewMstrEntity();
        
        if (null != projMstrEntity) {
        	plantAttendenceTO.setProjId(projMstrEntity.getProjectId());
        	plantAttendenceTO.setCode(projMstrEntity.getCode());
        	plantAttendenceTO.setParentCode(projMstrEntity.getParentProjectMstrEntity().getCode());
        	plantAttendenceTO.setName(projMstrEntity.getProjName());
        	plantAttendenceTO.setParentName(projMstrEntity.getParentProjectMstrEntity().getProjName());
        	plantAttendenceTO.setParentProjId(projMstrEntity.getParentProjectMstrEntity().getProjectId());
        }
        if (null != projCrewMstrEntity) {
        	plantAttendenceTO.setCrewId(projCrewMstrEntity.getId());
        	plantAttendenceTO.setCrewName(projCrewMstrEntity.getCode());
        }

        return plantAttendenceTO;
    }

    public static PlantAttendanceEntity convertPOJOToEntity(PlantAttendanceTO plantAttendanceTO,
            ProjCrewRepositoryCopy projCrewRepository, PlantRegisterRepositoryCopy plantRegisterRepository) {
        PlantAttendanceEntity plantAttendenceEntity = new PlantAttendanceEntity();
        if (CommonUtil.isNonBlankLong(plantAttendanceTO.getId())) {
            plantAttendenceEntity.setId(plantAttendanceTO.getId());
        }
        if (CommonUtil.isNonBlankLong(plantAttendanceTO.getAttandanceId())) {
            PlantAttendanceMstrEntity plantAttendanceMstrEntity = new PlantAttendanceMstrEntity();
            plantAttendanceMstrEntity.setId(plantAttendanceTO.getAttandanceId());
            plantAttendenceEntity.setPlantAttendanceMstrEntity(plantAttendanceMstrEntity);
        }

        if (CommonUtil.isNonBlankLong(plantAttendanceTO.getCrewId())) {
            ProjCrewMstrEntity projCrewMstrEntity = projCrewRepository.findOne(plantAttendanceTO.getCrewId());
            plantAttendenceEntity.setProjCrewMstrEntity(projCrewMstrEntity);
        }

        if (CommonUtil.isNonBlankLong(plantAttendanceTO.getPlantId())) {
            PlantRegisterDtlEntity plantRegisterDtlEntity = plantRegisterRepository
                    .findOne(plantAttendanceTO.getPlantId());
            plantAttendenceEntity.setPlantRegisterDtlEntity(plantRegisterDtlEntity);
        }

        plantAttendenceEntity.setStatus(plantAttendanceTO.getStatus());

        return plantAttendenceEntity;
    }

    public static PlantAttendanceTO convertEntityToPOJO(PlantAttendanceEntity plantAttendenceEntity) {
        PlantAttendanceTO plantAttendenceTO = new PlantAttendanceTO();
        plantAttendenceTO.setId(plantAttendenceEntity.getId());

        if (CommonUtil.objectNotNull(plantAttendenceEntity.getPlantAttendanceMstrEntity()))
            plantAttendenceTO.setAttandanceId(plantAttendenceEntity.getPlantAttendanceMstrEntity().getId());
        if (CommonUtil.objectNotNull(plantAttendenceEntity.getProjCrewMstrEntity()))
            plantAttendenceTO.setCrewId(plantAttendenceEntity.getProjCrewMstrEntity().getId());

        PlantRegisterDtlEntity plantRegisterDtl = plantAttendenceEntity.getPlantRegisterDtlEntity();
        if (CommonUtil.objectNotNull(plantRegisterDtl)) {
            plantAttendenceTO.setPlantId(plantRegisterDtl.getId());
            plantAttendenceTO.setPlantCode(plantRegisterDtl.getAssertId());
            plantAttendenceTO.setPlantName(plantRegisterDtl.getDesc());
            if (CommonUtil.objectNotNull(plantRegisterDtl.getPlantClassMstrId()))
                plantAttendenceTO.setClassType(plantRegisterDtl.getPlantClassMstrId().getName());
            if (CommonUtil.objectNotNull(plantRegisterDtl.getCmpId()))
                plantAttendenceTO.setCmpCatgName(plantRegisterDtl.getCmpId().getName());
            if (CommonUtil.objectNotNull(plantRegisterDtl.getProcurecatgId()))
                plantAttendenceTO.setProcureCatg(plantRegisterDtl.getProcurecatgId().getName());
            plantAttendenceTO.setPlntManfature(plantRegisterDtl.getManfacture());
            plantAttendenceTO.setPlntModel(plantRegisterDtl.getModel());
            plantAttendenceTO.setPlntRegNo(plantRegisterDtl.getRegNumber());
        }
        plantAttendenceTO.setStatus(plantAttendenceEntity.getStatus());

        return plantAttendenceTO;
    }

    public static PlantAttendanceDtlTO convertDtlEntityToPOJO(PlantAttendanceDtlEntity plantAttendanceDtlEntity) {
        PlantAttendanceDtlTO plantAttendanceDtlTO = new PlantAttendanceDtlTO();
        plantAttendanceDtlTO.setId(plantAttendanceDtlEntity.getId());

        plantAttendanceDtlTO.setPlantDtlId(plantAttendanceDtlEntity.getPlantAttendanceEntity().getId());

        plantAttendanceDtlTO.setAttendanceDate(plantAttendanceDtlEntity.getAttendanceDate());
        LabelKeyTO attendanceTypeTO = new LabelKeyTO();
        attendanceTypeTO.setCode(plantAttendanceDtlEntity.getProjAttdCode());
        plantAttendanceDtlTO.setAttendanceTypeTO(attendanceTypeTO);

        plantAttendanceDtlTO.setStatus(plantAttendanceDtlEntity.getStatus());

        return plantAttendanceDtlTO;
    }

    public static PlantAttendanceDtlEntity convertDtlPOJOToEntity(PlantAttendanceDtlEntity plantAttendanceDtlEntity,
            PlantAttendanceDtlTO plantAttendanceDtlTO) {
        if (CommonUtil.isNonBlankLong(plantAttendanceDtlTO.getPlantDtlId())) {
            PlantAttendanceEntity plantAttendanceEntity = new PlantAttendanceEntity();
            plantAttendanceEntity.setId(plantAttendanceDtlTO.getPlantDtlId());
            plantAttendanceDtlEntity.setPlantAttendanceEntity(plantAttendanceEntity);
        }

        plantAttendanceDtlEntity.setAttendanceDate(plantAttendanceDtlTO.getAttendanceDate());
        if (CommonUtil.objectNotNull(plantAttendanceDtlTO.getAttendanceTypeTO())) {
            plantAttendanceDtlEntity.setProjAttdCode(plantAttendanceDtlTO.getAttendanceTypeTO().getCode());
        }
        plantAttendanceDtlEntity.setStatus(plantAttendanceDtlTO.getStatus());

        return plantAttendanceDtlEntity;
    }
}
