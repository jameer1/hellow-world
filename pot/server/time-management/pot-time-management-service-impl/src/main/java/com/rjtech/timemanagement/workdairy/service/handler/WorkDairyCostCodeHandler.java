package com.rjtech.timemanagement.workdairy.service.handler;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjCrewRepositoryCopy;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyCostCodeTO;
import com.rjtech.timemanagement.workdairy.model.WorkDairyCostCodeEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyCrewCostCodeEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.repository.WorkDairyCostCodeRepository;

public class WorkDairyCostCodeHandler {

    public static WorkDairyCrewCostCodeEntity convertPOJOToEntity(WorkDairyCostCodeTO workDairyCostCodeTO,
            ProjCrewRepositoryCopy projCrewRepository, EPSProjRepository epsProjRepository,
            WorkDairyCostCodeRepository workDairyCostCodeRepository) {
        WorkDairyCrewCostCodeEntity crewCostCodeEntity = new WorkDairyCrewCostCodeEntity();
        if (CommonUtil.isNonBlankLong(workDairyCostCodeTO.getId())) {
            crewCostCodeEntity.setId(workDairyCostCodeTO.getId());
        }
        if (CommonUtil.isNonBlankLong(workDairyCostCodeTO.getCrewId())) {
            crewCostCodeEntity.setCrewId(projCrewRepository.findOne(workDairyCostCodeTO.getCrewId()));
        }
        crewCostCodeEntity.setProjId(epsProjRepository.findOne(workDairyCostCodeTO.getProjId()));
        if (workDairyCostCodeTO.getCostId() != null)
            crewCostCodeEntity.setCostId(workDairyCostCodeRepository.findOne(workDairyCostCodeTO.getCostId()));
        crewCostCodeEntity.setStatus(workDairyCostCodeTO.getStatus());
        return crewCostCodeEntity;
    }

    public static WorkDairyCostCodeTO convertEntityToPOJO(WorkDairyCrewCostCodeEntity workDairyCostCodeEntity) {
        WorkDairyCostCodeTO workDairyCostCodeTO = new WorkDairyCostCodeTO();
        workDairyCostCodeTO.setId(workDairyCostCodeEntity.getId());
        WorkDairyCostCodeEntity costCode = workDairyCostCodeEntity.getCostId();
        if (costCode != null)
            workDairyCostCodeTO.setCostId(costCode.getId());
        ProjCrewMstrEntity crew = workDairyCostCodeEntity.getCrewId();
        if (crew != null)
            workDairyCostCodeTO.setCrewId(crew.getId());
        ProjMstrEntity project = workDairyCostCodeEntity.getProjId();
        if (project != null)
            workDairyCostCodeTO.setProjId(project.getProjectId());
        workDairyCostCodeTO.setStatus(workDairyCostCodeEntity.getStatus());
        return workDairyCostCodeTO;
    }

    public static WorkDairyCostCodeEntity convertMstrPOJOToEntity(WorkDairyCostCodeTO workDairyCostCodeTO,
            EPSProjRepository epsProjRepository, ProjCostItemRepositoryCopy projCostItemRepository,
            ProjCrewRepositoryCopy projCrewRepository) {
        WorkDairyCostCodeEntity workDairyCostCodeEntity = new WorkDairyCostCodeEntity();
        if (CommonUtil.isNonBlankLong(workDairyCostCodeTO.getId())) {
            workDairyCostCodeEntity.setId(workDairyCostCodeTO.getId());
        }
        if (CommonUtil.isNonBlankLong(workDairyCostCodeTO.getWorkDairyId())) {
            WorkDairyEntity workDairyEntity = new WorkDairyEntity();
            workDairyEntity.setId(workDairyCostCodeTO.getWorkDairyId());
            workDairyCostCodeEntity.setWorkDairyEntity(workDairyEntity);
        }
        workDairyCostCodeEntity.setCrewId(projCrewRepository.findOne(workDairyCostCodeTO.getCrewId()));
        workDairyCostCodeEntity.setProjId(epsProjRepository.findOne(workDairyCostCodeTO.getProjId()));
        if (workDairyCostCodeTO.getCostId() != null)
            workDairyCostCodeEntity.setCostId(projCostItemRepository.findOne(workDairyCostCodeTO.getCostId()));
        workDairyCostCodeEntity.setStatus(workDairyCostCodeTO.getStatus());
        return workDairyCostCodeEntity;
    }

    public static WorkDairyCostCodeTO convertMstrEntityToPOJO(WorkDairyCostCodeEntity workDairyCostCodeEntity) {
        WorkDairyCostCodeTO workDairyCostCodeTO = new WorkDairyCostCodeTO();
        workDairyCostCodeTO.setId(workDairyCostCodeEntity.getId());
        workDairyCostCodeTO.setWorkDairyId(workDairyCostCodeEntity.getWorkDairyEntity().getId());
        ProjCostItemEntity costCode = workDairyCostCodeEntity.getCostId();
        if (costCode != null) {
            workDairyCostCodeTO.setCostId(costCode.getId());
            workDairyCostCodeTO.setCode(costCode.getCode());
        }
        ProjCrewMstrEntity crew = workDairyCostCodeEntity.getCrewId();
        if (crew != null)
            workDairyCostCodeTO.setCrewId(crew.getId());
        ProjMstrEntity project = workDairyCostCodeEntity.getProjId();
        if (project != null)
            workDairyCostCodeTO.setProjId(project.getProjectId());
        workDairyCostCodeTO.setStatus(workDairyCostCodeEntity.getStatus());
        return workDairyCostCodeTO;
    }

}
