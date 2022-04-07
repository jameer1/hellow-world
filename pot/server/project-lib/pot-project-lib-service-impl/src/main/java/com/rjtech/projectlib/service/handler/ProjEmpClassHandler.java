package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.service.handler.EmpClassHandler;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjEmpClassTO;
import com.rjtech.projectlib.model.ProjEmpClassMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;

public class ProjEmpClassHandler {

    public static List<ProjEmpClassMstrEntity> convertPOJOToEntity(List<ProjEmpClassTO> projEmpClassTOs, Long projId,
            EmpClassRepository empClassRepository, EPSProjRepository epsProjRepository) {

        List<ProjEmpClassMstrEntity> projEmpClassMstrEntities = new ArrayList<ProjEmpClassMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjEmpClassMstrEntity entity = null;
        for (ProjEmpClassTO projEmpClassTO : projEmpClassTOs) {
            entity = new ProjEmpClassMstrEntity();
            if (CommonUtil.isNonBlankLong(projEmpClassTO.getId())) {
                entity.setId(projEmpClassTO.getId());
            }
            entity.setEmpClassMstrEntity(empClassRepository.findOne(projEmpClassTO.getEmpClassTO().getId()));
            entity.setProjectId(epsProjRepository.findOne(projId));
            entity.setTradeContrName(projEmpClassTO.getTradeContrName());
            entity.setUnionName(projEmpClassTO.getUnionWorkerName());
            entity.setProjEmpCategory(projEmpClassTO.getProjEmpCategory());
            entity.setStatus(projEmpClassTO.getStatus());

            projEmpClassMstrEntities.add(entity);
        }
        return projEmpClassMstrEntities;
    }

    public static LabelKeyTO convertEntityTOLabelKeyTO(ProjEmpClassMstrEntity projEmpClassMstrEntity) {

        LabelKeyTO labelKeyTO = new LabelKeyTO();
        if (CommonUtil.objectNotNull(projEmpClassMstrEntity.getEmpClassMstrEntity())) {
            labelKeyTO.setId(projEmpClassMstrEntity.getEmpClassMstrEntity().getId());
            labelKeyTO.setCode(projEmpClassMstrEntity.getTradeContrName());
            labelKeyTO.setName(projEmpClassMstrEntity.getUnionName());
        }

        labelKeyTO.getDisplayNamesMap().put(CommonConstants.EMP_CATG_NAME, projEmpClassMstrEntity.getProjEmpCategory());
        return labelKeyTO;
    }

    public static ProjEmpClassTO populateProjEmployees(EmpClassMstrEntity empClassMstrEntity,
            Map<Long, ProjEmpClassMstrEntity> projEmployeesMap) {
        ProjEmpClassTO projEmpClassTO = new ProjEmpClassTO();
        ProjEmpClassMstrEntity projEmpClassMstrEntity = null;
        projEmpClassTO.setEmpClassTO(EmpClassHandler.convertEmpClassEntityToPOJO(empClassMstrEntity));
        if (projEmployeesMap.get(empClassMstrEntity.getId()) != null) {
            projEmpClassMstrEntity = projEmployeesMap.get(empClassMstrEntity.getId());
            projEmpClassTO.setId(projEmpClassMstrEntity.getId());
            ProjMstrEntity project = projEmpClassMstrEntity.getProjectId();
            if (project != null)
                projEmpClassTO.setProjId(project.getProjectId());
            projEmpClassTO.setProjEmpCategory(projEmpClassMstrEntity.getProjEmpCategory());
            projEmpClassTO.setTradeContrName(projEmpClassMstrEntity.getTradeContrName());
            projEmpClassTO.setUnionWorkerName(projEmpClassMstrEntity.getUnionName());
            projEmpClassTO.setStatus(projEmpClassMstrEntity.getStatus());
        }

        return projEmpClassTO;
    }
}
