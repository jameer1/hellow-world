package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.MaterialClassTO;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.service.handler.MaterialClassHandler;
import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjectMaterialDtlTO;
import com.rjtech.projsettings.model.ProjectMaterialBudgetEntity;

public class ProjectMaterialBudgetHandler {

    public static List<ProjectMaterialBudgetEntity> convertPOJOToEntity(
            List<ProjectMaterialDtlTO> projectMaterialDtlTOs, EPSProjRepository epsProjRepository,
            MaterialClassRepository materialClassRepository) {
        List<ProjectMaterialBudgetEntity> projectMaterialDtlEntites = new ArrayList<ProjectMaterialBudgetEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjectMaterialBudgetEntity entity = null;
        for (ProjectMaterialDtlTO projectMaterialDtlTO : projectMaterialDtlTOs) {
            entity = new ProjectMaterialBudgetEntity();

            if (CommonUtil.isNonBlankLong(projectMaterialDtlTO.getId())) {
                entity.setId(projectMaterialDtlTO.getId());
                entity.setStatus(projectMaterialDtlTO.getStatus());
            }
            else
            {
            	entity.setStatus( StatusCodes.DEFAULT.getValue() );
            }
            if (CommonUtil.isNonBlankLong(projectMaterialDtlTO.getProjId())) {
                ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projectMaterialDtlTO.getProjId());
                entity.setProjMstrEntity(projMstrEntity);
            }
            if (CommonUtil.isNonBlankLong(projectMaterialDtlTO.getMaterialId())) {
                MaterialClassMstrEntity materialClassMstrEntity = materialClassRepository
                        .findOne(projectMaterialDtlTO.getMaterialId());
                entity.setMaterialClassMstrEntity(materialClassMstrEntity);
            }
            entity.setEstimateComplete(projectMaterialDtlTO.getEstimateComplete());
            entity.setOriginalQty(projectMaterialDtlTO.getOriginalQty());
            entity.setRevisedQty(projectMaterialDtlTO.getRevisedQty());

            if (CommonUtil.isNotBlankStr(projectMaterialDtlTO.getStartDate())) {
                entity.setStartDate(CommonUtil.convertStringToDate(projectMaterialDtlTO.getStartDate()));
            }
            if (CommonUtil.isNotBlankStr(projectMaterialDtlTO.getFinishDate())) {
                entity.setFinishDate(CommonUtil.convertStringToDate(projectMaterialDtlTO.getFinishDate()));
            }
            
            /*if( CommonUtil.isBlankLong( projectMaterialDtlTO.getId() ) ) {
            	entity.setItemStatus( "DRAFT" );
            	entity.setStatus( StatusCodes.DEFAULT.getValue() );
            }*/
            String item_status = CommonUtil.isBlankLong( projectMaterialDtlTO.getId() ) ? "DRAFT" : projectMaterialDtlTO.getItemStatus();            
        	entity.setItemStatus( item_status );
        	entity.setIsItemReturned( 0 );            
            projectMaterialDtlEntites.add(entity);
        }
        return projectMaterialDtlEntites;
    }

    public static ProjectMaterialDtlTO populateProjMaterials(MaterialClassMstrEntity materialClassMstrEntity,
            ProjectMaterialBudgetEntity projectMaterialDtlEntity, boolean addChild) {
        ProjectMaterialDtlTO projectMaterialDtlTO = null;
        if (materialClassMstrEntity.isItem()) {
            //if (StatusCodes.ACTIVE.getValue().intValue() == projectMaterialDtlEntity.getStatus().intValue()) {
        	if (StatusCodes.ACTIVE.getValue().intValue() == projectMaterialDtlEntity.getStatus().intValue() || StatusCodes.DEFAULT.getValue().intValue() == projectMaterialDtlEntity.getStatus().intValue() ) {
                projectMaterialDtlTO = new ProjectMaterialDtlTO();
                projectMaterialDtlTO.setId(projectMaterialDtlEntity.getId());
                projectMaterialDtlTO.setItem(true);
                projectMaterialDtlTO.setName(materialClassMstrEntity.getName());
                projectMaterialDtlTO.setCode(materialClassMstrEntity.getCode());
                if (CommonUtil.isNotBlankDate(projectMaterialDtlEntity.getStartDate())) {
                    projectMaterialDtlTO
                            .setStartDate(CommonUtil.convertDateToString(projectMaterialDtlEntity.getStartDate()));
                }
                if (CommonUtil.isNotBlankDate(projectMaterialDtlEntity.getFinishDate())) {
                    projectMaterialDtlTO
                            .setFinishDate(CommonUtil.convertDateToString(projectMaterialDtlEntity.getFinishDate()));
                }
                MaterialClassTO materialClassTO = new MaterialClassTO();
                materialClassTO.setMeasureUnitTO(MeasurementHandler
                        .convertMeasurePOJOFromEnity(materialClassMstrEntity.getMeasurmentMstrEntity()));
                projectMaterialDtlTO.setMaterialClassTO(materialClassTO);
                projectMaterialDtlTO.setMaterialClassId( materialClassMstrEntity.getId() );
                projectMaterialDtlTO.setMaterialId(materialClassMstrEntity.getId());
                MaterialClassMstrEntity parent = materialClassMstrEntity.getMaterialClassMstrEntity();
                projectMaterialDtlTO.setParentId(parent.getId());
                projectMaterialDtlTO.setParentName(parent.getName());
                projectMaterialDtlTO.setParentCode(parent.getCode());
                projectMaterialDtlTO.setProjId(projectMaterialDtlEntity.getProjMstrEntity().getProjectId());
                projectMaterialDtlTO.setEstimateComplete(projectMaterialDtlEntity.getEstimateComplete());
                projectMaterialDtlTO.setOriginalQty(projectMaterialDtlEntity.getOriginalQty());
                projectMaterialDtlTO.setRevisedQty(projectMaterialDtlEntity.getRevisedQty());
                projectMaterialDtlTO.setStatus(projectMaterialDtlEntity.getStatus());
                projectMaterialDtlTO.setItemStatus( projectMaterialDtlEntity.getItemStatus() );
                
                if( projectMaterialDtlEntity.getApproverUserId() != null )
                {
                	projectMaterialDtlTO.setApproverUserId( projectMaterialDtlEntity.getApproverUserId().getUserId() );
                	projectMaterialDtlTO.setApproverComments( projectMaterialDtlEntity.getApproverComments() );
                }
                if( projectMaterialDtlEntity.getOriginatorUserId() != null )
                {
                	projectMaterialDtlTO.setOriginatorUserId( projectMaterialDtlEntity.getOriginatorUserId().getUserId() );
                }
                if( projectMaterialDtlEntity.getReturnedByUserId() != null )
                {
                	projectMaterialDtlTO.setReturnedByUserId( projectMaterialDtlEntity.getReturnedByUserId().getUserId() );
                	projectMaterialDtlTO.setReturnedComments( projectMaterialDtlEntity.getReturnedComments() );
                }
                projectMaterialDtlTO.setIsItemReturned( projectMaterialDtlEntity.getIsItemReturned() );
            }

        } else {
            projectMaterialDtlTO = new ProjectMaterialDtlTO();
            projectMaterialDtlTO.setId(materialClassMstrEntity.getId());
            projectMaterialDtlTO.setName(materialClassMstrEntity.getName());
            projectMaterialDtlTO.setCode(materialClassMstrEntity.getCode());
            projectMaterialDtlTO.setItem(false);
            MaterialClassMstrEntity parent = materialClassMstrEntity.getMaterialClassMstrEntity();
            if (parent != null) {
                projectMaterialDtlTO.setParentId(parent.getId());
                projectMaterialDtlTO.setParentName(parent.getName());
                projectMaterialDtlTO.setParentCode(parent.getCode());
            }
            projectMaterialDtlTO.setStatus(materialClassMstrEntity.getStatus());
        }
        if (CommonUtil.objectNotNull(projectMaterialDtlTO) && addChild) {
            List<ProjectMaterialDtlTO> childTOs = addChilds(materialClassMstrEntity, projectMaterialDtlTO,
                    projectMaterialDtlEntity);
            if (childTOs != null && childTOs.size() > 0) {
                projectMaterialDtlTO.getProjectMaterialDtlTOs().addAll(childTOs);
            }
        }
        return projectMaterialDtlTO;
    }

    public static List<ProjectMaterialDtlTO> addChilds(MaterialClassMstrEntity materialClassMstrEntity,
            ProjectMaterialDtlTO projectMaterialDtlTO, ProjectMaterialBudgetEntity projectMaterialDtlEntity) {
        List<ProjectMaterialDtlTO> projectMaterialDtlTOs = new ArrayList<ProjectMaterialDtlTO>();
        ProjectMaterialDtlTO projectMaterialChildTO = null;
        if (CommonUtil.objectNotNull(materialClassMstrEntity.getMaterialClassMstrEntity())) {
            if (materialClassMstrEntity.getMaterialClassMstrEntity().getId() == null) {
                for (MaterialClassMstrEntity childEntity : materialClassMstrEntity.getChildEntities()) {
                    /*if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                        projectMaterialChildTO = populateProjMaterials(childEntity, projectMaterialDtlEntity, true);
                        if (CommonUtil.objectNotNull(projectMaterialChildTO)) {
                            projectMaterialDtlTOs.add(projectMaterialChildTO);
                        }
                    }*/
                	if( StatusCodes.DEFAULT.getValue().intValue() == childEntity.getStatus().intValue() || StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue() ) {
                        projectMaterialChildTO = populateProjMaterials(childEntity, projectMaterialDtlEntity, true);
                        if (CommonUtil.objectNotNull(projectMaterialChildTO)) {
                            projectMaterialDtlTOs.add(projectMaterialChildTO);
                        }
                    }
                }
            }
        } else {
            if (CommonUtil.objectNotNull(materialClassMstrEntity.getMaterialClassMstrEntity())) {
                projectMaterialDtlTO.setParentId(materialClassMstrEntity.getMaterialClassMstrEntity().getId());
            }
            for (MaterialClassMstrEntity childEntity : materialClassMstrEntity.getChildEntities()) {
                /*if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    projectMaterialChildTO = populateProjMaterials(childEntity, projectMaterialDtlEntity, true);
                    if (CommonUtil.objectNotNull(projectMaterialChildTO)) {
                        projectMaterialDtlTOs.add(projectMaterialChildTO);
                    }
                }*/
            	if( StatusCodes.DEFAULT.getValue().intValue() == childEntity.getStatus().intValue() || StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue() ) {
            		projectMaterialChildTO = populateProjMaterials(childEntity, projectMaterialDtlEntity, true);
                    if (CommonUtil.objectNotNull(projectMaterialChildTO)) {
                        projectMaterialDtlTOs.add(projectMaterialChildTO);
                    }
                }
            }
        }
        if (projectMaterialDtlTOs.size() > 0) {
            return projectMaterialDtlTOs;
        } else {
            return null;
        }
    }

    public static ProjectMaterialDtlTO convertEntityToPOJO(ProjectMaterialBudgetEntity projectMaterialBudgetEntity) {
        ProjectMaterialDtlTO projectMaterialDtlTO = new ProjectMaterialDtlTO();
        projectMaterialDtlTO.setId(projectMaterialBudgetEntity.getId());
        if (CommonUtil.isNotBlankDate(projectMaterialBudgetEntity.getStartDate())) {
            projectMaterialDtlTO
                    .setStartDate(CommonUtil.convertDateToString(projectMaterialBudgetEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projectMaterialBudgetEntity.getFinishDate())) {
            projectMaterialDtlTO
                    .setFinishDate(CommonUtil.convertDateToString(projectMaterialBudgetEntity.getFinishDate()));
        }
        MaterialClassMstrEntity materialClassMstrEntity = projectMaterialBudgetEntity.getMaterialClassMstrEntity();
        if (CommonUtil.objectNotNull(materialClassMstrEntity)) {
            projectMaterialDtlTO.setMaterialClassId(materialClassMstrEntity.getId());
            projectMaterialDtlTO
                    .setMaterialClassTO(MaterialClassHandler.populateMaterialItems(materialClassMstrEntity, false));
        }
        projectMaterialDtlTO.setMaterialId(projectMaterialBudgetEntity.getId());
        projectMaterialDtlTO.setProjId(projectMaterialBudgetEntity.getProjMstrEntity().getProjectId());
        projectMaterialDtlTO.setEstimateComplete(projectMaterialBudgetEntity.getEstimateComplete());
        projectMaterialDtlTO.setOriginalQty(projectMaterialBudgetEntity.getOriginalQty());
        if (projectMaterialBudgetEntity.getRevisedQty() != null) {
            projectMaterialDtlTO.setRemainingQty(projectMaterialBudgetEntity.getRevisedQty());
        } else {
            projectMaterialDtlTO.setRemainingQty(projectMaterialBudgetEntity.getOriginalQty());
        }

        projectMaterialDtlTO.setRevisedQty(projectMaterialBudgetEntity.getRevisedQty());
        projectMaterialDtlTO.setStatus(projectMaterialBudgetEntity.getStatus());
        return projectMaterialDtlTO;
    }
}
