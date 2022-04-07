package com.rjtech.register.service.handler.plant;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.model.PlantProjPODtlEntity;
import com.rjtech.register.plant.model.PlantRegProjEntity;
import com.rjtech.register.repository.plant.PlantRegisterRepository;
import com.rjtech.centrallib.model.PlantMstrEntity;

public class PlantRegProjDtlHandler {

    public static PlantRegProjTO convertEntityToPOJO(PlantRegProjEntity entity) {
        PlantRegProjTO plantRegProjTO = new PlantRegProjTO();
        plantRegProjTO.setId(entity.getId());
        plantRegProjTO.setDeploymentId(entity.getDeploymentId());
        if (CommonUtil.objectNotNull(entity.getPlantProjPODtlEntity())) {
            plantRegProjTO.setPlantPOId(entity.getPlantProjPODtlEntity().getId());            
        }
        if (CommonUtil.objectNotNull(entity.getPlantRegisterDtlEntity().getAssertId())) {
        	plantRegProjTO.setAssertId(entity.getPlantRegisterDtlEntity().getAssertId());
        }
        plantRegProjTO.setLatest(entity.getIsLatest());
        plantRegProjTO.setPlantId(entity.getPlantRegisterDtlEntity().getId());
        ProjMstrEntity proj = entity.getProjId();
        if (CommonUtil.objectNotNull(proj)) {
            plantRegProjTO.setProjId(proj.getProjectId());
            plantRegProjTO.setName(proj.getProjName());
            ProjMstrEntity parentProj = proj.getParentProjectMstrEntity();
            if (CommonUtil.objectNotNull(parentProj)) {
                plantRegProjTO.setParentCode(parentProj.getCode());
                plantRegProjTO.setParentName(parentProj.getProjName());
            }
        }
        if (CommonUtil.isNotBlankDate(entity.getDeMobDate())) {
            plantRegProjTO.setDeMobDate(CommonUtil.convertDateToString(entity.getDeMobDate()));
        }
		
		  if (CommonUtil.isNotBlankDate(entity.getMobDate())) {
		  plantRegProjTO.setMobDate(CommonUtil.convertDateToString(entity.getMobDate())
		  ); }
		  
		  if (entity.getIsLatest() == "Y") {
			  plantRegProjTO.setMobDate(CommonUtil.convertDateToString(entity.getCreatedOn())
			  ); }
		  
		  PlantMstrEntity plantMstrEntity = entity.getPlantRegisterDtlEntity().getPlantClassMstrId();
		  if (CommonUtil.objectNotNull(plantMstrEntity)) {
			  plantRegProjTO.setPlantMasterName(plantMstrEntity.getCode());
		  }
		/*
		 * if (CommonUtil.isNotBlankDate(entity.getCreatedOn())) {
		 * plantRegProjTO.setMobDate(CommonUtil.convertDateToString(entity.getCreatedOn(
		 * ))); }
		 */
        if (CommonUtil.isNotBlankDate(entity.getAnticipatedDeMobilisationDate())) {
            plantRegProjTO.setAnticipatedDeMobDate(CommonUtil.convertDateToString(entity.getAnticipatedDeMobDate()));
        }

        if (CommonUtil.isNotBlankDate(entity.getCommissionDate())) {
            plantRegProjTO.setCommissionDate(CommonUtil.convertDateToString(entity.getCommissionDate()));
        }
        plantRegProjTO.setPlantDeliveryStatus(entity.getPlantDeliveryStatus());
        plantRegProjTO.setAssignStatus(entity.getAssignStatus());
        plantRegProjTO.setPostDeMobStatus(entity.getPostDeMobStatus());
        plantRegProjTO.setOdoMeter(entity.getOdoMeter());
        plantRegProjTO.setDeMobOdoMeter(entity.getDeMobOdoMeter());
        plantRegProjTO.setComments(entity.getComments());
        plantRegProjTO.setStatus(entity.getStatus());
        return plantRegProjTO;
    }

    public static PlantRegProjEntity convertPOJOToEntity(PlantRegProjTO plantRegProjTO,
            PlantRegisterRepository plantRegisterRepository, EPSProjRepository epsProjRepository) {
        PlantRegProjEntity plantRegProjEntity = new PlantRegProjEntity();
        plantRegProjEntity.setId(plantRegProjTO.getId());
        plantRegProjEntity.setDeploymentId(plantRegProjTO.getDeploymentId());
        plantRegProjEntity.setPlantRegisterDtlEntity(
                (plantRegProjTO.getPlantId() != null) ? plantRegisterRepository.findOne(plantRegProjTO.getPlantId())
                        : null);
        plantRegProjEntity.setIsLatest(plantRegProjTO.getLatest());
        plantRegProjEntity.setProjId(
                (plantRegProjTO.getProjId() != null) ? epsProjRepository.findOne(plantRegProjTO.getProjId()) : null);
        if (CommonUtil.isNotBlankStr(plantRegProjTO.getDeMobDate())) {
            plantRegProjEntity.setDeMobDate(CommonUtil.convertStringToDate(plantRegProjTO.getDeMobDate()));
        }
        if (CommonUtil.isNotBlankStr(plantRegProjTO.getMobDate())) {
            plantRegProjEntity.setMobDate(CommonUtil.convertStringToDate(plantRegProjTO.getMobDate()));
        }
        if (CommonUtil.isNotBlankStr(plantRegProjTO.getAnticipatedDeMobDate())) {
            plantRegProjEntity
                    .setAnticipatedDeMobDate(CommonUtil.convertStringToDate(plantRegProjTO.getAnticipatedDeMobDate()));
        }

        if (CommonUtil.isNotBlankStr(plantRegProjTO.getCommissionDate())) {
            plantRegProjEntity.setCommissionDate(CommonUtil.convertStringToDate(plantRegProjTO.getCommissionDate()));
        }
        if (CommonUtil.isNonBlankLong(plantRegProjTO.getPlantPOId())) {
            PlantProjPODtlEntity plantProjPODtlEntity = new PlantProjPODtlEntity();
            plantProjPODtlEntity.setId(plantRegProjTO.getPlantPOId());
            plantRegProjEntity.setPlantProjPODtlEntity(plantProjPODtlEntity);
        }
        plantRegProjEntity.setPlantDeliveryStatus(plantRegProjTO.getPlantDeliveryStatus());
        plantRegProjEntity.setAssignStatus(plantRegProjTO.getAssignStatus());
        plantRegProjEntity.setPostDeMobStatus(plantRegProjTO.getPostDeMobStatus());
        plantRegProjEntity.setOdoMeter(plantRegProjTO.getOdoMeter());
        plantRegProjEntity.setDeMobOdoMeter(plantRegProjTO.getDeMobOdoMeter());
        plantRegProjEntity.setComments(plantRegProjTO.getComments());
        plantRegProjEntity.setStatus(plantRegProjTO.getStatus());
        return plantRegProjEntity;
    }

}
