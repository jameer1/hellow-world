package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.PlantClassTO;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjectPlantsDtlTO;
import com.rjtech.projsettings.model.ProjectPlantsDtlEntity;
import com.rjtech.common.utils.StatusCodes;

public class ProjectPlantsDtlHandler {

    public static ProjectPlantsDtlTO convertEntityToPOJO(ProjectPlantsDtlEntity entity) {

        ProjectPlantsDtlTO projectPlantsDtlTO = new ProjectPlantsDtlTO();

        projectPlantsDtlTO.setId(entity.getId());
        projectPlantsDtlTO.setOriginalQty(entity.getOriginalQty());
        projectPlantsDtlTO.setRevisedQty(entity.getRevisedQty());
        projectPlantsDtlTO.setEstimateComplete(entity.getEstimateComplete());

        if (CommonUtil.objectNotNull(entity.getProjMstrEntity())) {
            projectPlantsDtlTO.setProjId(entity.getProjMstrEntity().getProjectId());
        }
        if (CommonUtil.isNotBlankDate(entity.getStartDate())) {
            projectPlantsDtlTO.setStartDate(CommonUtil.convertDateToString(entity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getFinishDate())) {
            projectPlantsDtlTO.setFinishDate(CommonUtil.convertDateToString(entity.getFinishDate()));
        }

        PlantMstrEntity plantMstr = entity.getPlantMstrEntity();
        if (CommonUtil.objectNotNull(plantMstr)) {
        	projectPlantsDtlTO.setPlantClassId(plantMstr.getId());
            projectPlantsDtlTO.setPlantClassTO(ProjectPlantsDtlHandler.converPlantClassEntityToPOJO(plantMstr, true));
            projectPlantsDtlTO.setPlantId(entity.getPlantMstrEntity().getId());
        }

        if (CommonUtil.objectNotNull(entity.getMeasurmentMstrEntity())) {
            projectPlantsDtlTO.setMeasureId(entity.getMeasurmentMstrEntity().getId());
            projectPlantsDtlTO
                    .setMeasureUnitTO(MeasurementHandler.convertMeasurePOJOFromEnity(entity.getMeasurmentMstrEntity()));
        }
        projectPlantsDtlTO.setStatus(entity.getStatus());
        projectPlantsDtlTO.setItemStatus( entity.getItemStatus() );
        if( CommonUtil.objectNotNull( entity.getApproverUserId() ) )
        {
        	projectPlantsDtlTO.setApproverUserId( entity.getApproverUserId().getUserId() );
        	projectPlantsDtlTO.setApproverComments( entity.getApproverComments() );
        }
        if( CommonUtil.objectNotNull( entity.getOriginatorUserId() ) )
        {
        	projectPlantsDtlTO.setOriginatorUserId( entity.getOriginatorUserId().getUserId() );
        }
        if( CommonUtil.objectNotNull( entity.getReturnedByUserId() ) )
        {
        	projectPlantsDtlTO.setReturnedByUserId( entity.getReturnedByUserId().getUserId() );
        	projectPlantsDtlTO.setReturnedComments( entity.getReturnedComments() );
        }
        projectPlantsDtlTO.setIsItemReturned( entity.getIsItemReturned() );

        return projectPlantsDtlTO;

    }

    public static PlantClassTO converPlantClassEntityToPOJO(PlantMstrEntity entity, boolean includeMeasureTo) {
        PlantClassTO classificationTO = new PlantClassTO();
        classificationTO.setId(entity.getId());
        classificationTO.setCode(entity.getCode());
        classificationTO.setName(entity.getName());
        if (includeMeasureTo)
            classificationTO
                    .setMeasureUnitTO(MeasurementHandler.convertMeasurePOJOFromEnity(entity.getMeasurmentMstrEntity()));

        return classificationTO;
    }

    public static List<ProjectPlantsDtlEntity> convertPOJOToEntity(List<ProjectPlantsDtlTO> projectPlantsDtlTOs,
            EPSProjRepository epsProjRepository, PlantClassRepository plantClassRepository,
            MeasurementRepository measurementRepository) {
        List<ProjectPlantsDtlEntity> projectPlantsDtlEntites = new ArrayList<ProjectPlantsDtlEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjectPlantsDtlEntity entity = null;
        for (ProjectPlantsDtlTO projectPlantsDtlTO : projectPlantsDtlTOs) {
            entity = new ProjectPlantsDtlEntity();

            entity.setOriginalQty(projectPlantsDtlTO.getOriginalQty());
            entity.setRevisedQty(projectPlantsDtlTO.getRevisedQty());
            entity.setEstimateComplete(projectPlantsDtlTO.getEstimateComplete());            

            if (CommonUtil.isNonBlankLong(projectPlantsDtlTO.getId())) {
                entity.setId(projectPlantsDtlTO.getId());
            }
            if (CommonUtil.isNonBlankLong(projectPlantsDtlTO.getProjId())) {
                ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projectPlantsDtlTO.getProjId());
                entity.setProjMstrEntity(projMstrEntity);
            }
            if (CommonUtil.objectNotNull(projectPlantsDtlTO.getPlantClassTO())) {
                PlantMstrEntity plantMstrEntity = plantClassRepository
                        .findOne(projectPlantsDtlTO.getPlantClassTO().getId());
                entity.setPlantMstrEntity(plantMstrEntity);
            }
            if (CommonUtil.objectNotNull(projectPlantsDtlTO.getMeasureUnitTO())) {
                MeasurmentMstrEntity measurmentMstrEntity = measurementRepository
                        .findOne(projectPlantsDtlTO.getMeasureUnitTO().getId());
                entity.setMeasurmentMstrEntity(measurmentMstrEntity);
            }
            if (CommonUtil.isNotBlankStr(projectPlantsDtlTO.getStartDate())) {
                entity.setStartDate(CommonUtil.convertStringToDate(projectPlantsDtlTO.getStartDate()));
            }
            if (CommonUtil.isNotBlankStr(projectPlantsDtlTO.getFinishDate())) {
                entity.setFinishDate(CommonUtil.convertStringToDate(projectPlantsDtlTO.getFinishDate()));
            }
            /*if( CommonUtil.isBlankLong( projectPlantsDtlTO.getId() ) )
            {
            	entity.setStatus( StatusCodes.DEFAULT.getValue() );
            	entity.setItemStatus( "DRAFT" );
            }*/
            String item_status = CommonUtil.isBlankLong( projectPlantsDtlTO.getId() ) ? "DRAFT" : projectPlantsDtlTO.getItemStatus();
            Integer plant_status = CommonUtil.isBlankLong( projectPlantsDtlTO.getId() ) ? StatusCodes.DEFAULT.getValue() : projectPlantsDtlTO.getStatus();
            entity.setStatus( plant_status );
        	entity.setItemStatus( item_status );
            projectPlantsDtlEntites.add(entity);
        }
        return projectPlantsDtlEntites;
    }
}
