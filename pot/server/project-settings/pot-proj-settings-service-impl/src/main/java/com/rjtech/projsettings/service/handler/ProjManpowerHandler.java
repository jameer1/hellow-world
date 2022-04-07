package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjManpowerTO;
import com.rjtech.projsettings.model.ProjManpowerEntity;
import com.rjtech.common.utils.StatusCodes;

public class ProjManpowerHandler {
    public static ProjManpowerTO convertEntityToPOJO(ProjManpowerEntity entity) {
        ProjManpowerTO projManpowerTO = new ProjManpowerTO();

        projManpowerTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getProjMstrEntity()))
            projManpowerTO.setProjId(entity.getProjMstrEntity().getProjectId());

        projManpowerTO.setOriginalQty(entity.getOriginalQty());
        projManpowerTO.setRevisedQty(entity.getRevisedQty());
        projManpowerTO.setEstimateComplete(entity.getEstimateComplete());
        projManpowerTO.setProjEmpCategory(entity.getProjEmpCategory());
        if (CommonUtil.isNotBlankDate(entity.getStartDate())) {
            projManpowerTO.setStartDate(CommonUtil.convertDateToString(entity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getFinishDate())) {
            projManpowerTO.setFinishDate(CommonUtil.convertDateToString(entity.getFinishDate()));
        }
        EmpClassMstrEntity empClass = entity.getEmpClassMstrEntity();
        if (CommonUtil.objectNotNull(empClass)) {
            projManpowerTO.setEmpClassId(empClass.getId());
            projManpowerTO.setEmpClassTO(ProjManpowerHandler.convertEmpClassEntityToPOJO(empClass));
        }
        if (CommonUtil.objectNotNull(entity.getMeasurmentMstrEntity())) {
            projManpowerTO.setMeasureId(entity.getMeasurmentMstrEntity().getId());
            projManpowerTO
                    .setMeasureUnitTO(MeasurementHandler.convertMeasurePOJOFromEnity(entity.getMeasurmentMstrEntity()));
        }

        projManpowerTO.setStatus(entity.getStatus());
        projManpowerTO.setItemStatus( entity.getItemStatus() );
        if( CommonUtil.objectNotNull( projManpowerTO.getApproverUserId() ) )
        {
        	projManpowerTO.setApproverUserId( entity.getApproverUserId().getUserId() );
        	projManpowerTO.setApproverComments( entity.getApproverComments() );
        }
        if( CommonUtil.objectNotNull( projManpowerTO.getOriginatorUserId() ) )
        {
        	projManpowerTO.setOriginatorUserId( entity.getOriginatorUserId().getUserId() );
        }
        if( CommonUtil.objectNotNull( projManpowerTO.getReturnedByUserId() ) )
        {
        	projManpowerTO.setReturnedByUserId( entity.getReturnedByUserId().getUserId() );
        	projManpowerTO.setReturnedComments( entity.getReturnedComments() );
        }
        projManpowerTO.setIsItemReturned( entity.getIsItemReturned() );
        return projManpowerTO;
    }

    public static EmpClassTO convertEmpClassEntityToPOJO(EmpClassMstrEntity entity) {
        EmpClassTO empClassTO = new EmpClassTO();
        empClassTO.setId(entity.getId());
        empClassTO.setCode(entity.getCode());
        empClassTO.setName(entity.getName());
        return empClassTO;
    }

    public static List<ProjManpowerEntity> convertPOJOToEntity(List<ProjManpowerTO> projManpowerTOs,
            EPSProjRepository epsProjRepository, EmpClassRepository empClassRepository,
            MeasurementRepository measurementRepository) {
        List<ProjManpowerEntity> projManpowerEntites = new ArrayList<ProjManpowerEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjManpowerEntity entity = null;
        for (ProjManpowerTO projManpowerTO : projManpowerTOs) {
            entity = new ProjManpowerEntity();

            if (CommonUtil.isNonBlankLong(projManpowerTO.getId())) {
                entity.setId(projManpowerTO.getId());
            }
            if (CommonUtil.isNonBlankLong(projManpowerTO.getProjId())) {
                ProjMstrEntity mstrEntity = epsProjRepository.findOne(projManpowerTO.getProjId());
                entity.setProjMstrEntity(mstrEntity);
            }
            if (CommonUtil.objectNotNull(projManpowerTO.getEmpClassTO())
                    && CommonUtil.isNonBlankLong(projManpowerTO.getEmpClassTO().getId())) {
                EmpClassMstrEntity classMstrEntity = empClassRepository.findOne(projManpowerTO.getEmpClassTO().getId());
                entity.setEmpClassMstrEntity(classMstrEntity);
            }

            if (CommonUtil.isNonBlankLong(projManpowerTO.getMeasureId())) {
                MeasurmentMstrEntity measurmentMstrEntity = measurementRepository
                        .findOne(projManpowerTO.getMeasureId());
                entity.setMeasurmentMstrEntity(measurmentMstrEntity);
            }
            entity.setOriginalQty(projManpowerTO.getOriginalQty());
            entity.setRevisedQty(projManpowerTO.getRevisedQty());
            entity.setEstimateComplete(projManpowerTO.getEstimateComplete());
            if (CommonUtil.isNotBlankStr(projManpowerTO.getStartDate())) {
                entity.setStartDate(CommonUtil.convertStringToDate(projManpowerTO.getStartDate()));
            }
            if (CommonUtil.isNotBlankStr(projManpowerTO.getFinishDate())) {
                entity.setFinishDate(CommonUtil.convertStringToDate(projManpowerTO.getFinishDate()));
            }
            
            entity.setProjEmpCategory(projManpowerTO.getProjEmpCategory());
            /*if( CommonUtil.isBlankLong( projManpowerTO.getId() ) ) {
            	entity.setItemStatus( "DRAFT" );
            	entity.setStatus( projManpowerTO.getStatus() );
            }*/
            String item_status = CommonUtil.isBlankLong( projManpowerTO.getId() ) ? "DRAFT" : projManpowerTO.getItemStatus();
            Integer mpd_status = CommonUtil.isBlankLong( projManpowerTO.getId() ) ? StatusCodes.DEFAULT.getValue() : projManpowerTO.getStatus();
            entity.setItemStatus( item_status );
            entity.setStatus( mpd_status );
            projManpowerEntites.add(entity);
        }
        return projManpowerEntites;
    }

}
