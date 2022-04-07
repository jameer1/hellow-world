package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjPayRollCycleTO;
import com.rjtech.projsettings.model.ProjPayRollCycleEntity;

public class ProjPayRollCycleHandler {

    public static ProjPayRollCycleTO convertEntityToPOJO(ProjPayRollCycleEntity entity) {
        ProjPayRollCycleTO projPayRollCycleTO = new ProjPayRollCycleTO();
        projPayRollCycleTO.setId(entity.getId());
        projPayRollCycleTO.setProjId(entity.getProjMstrEntity().getProjectId());
        projPayRollCycleTO.setPayRollType(entity.getPayRollType());
        projPayRollCycleTO.setPayRollTypeValue(entity.getPayRollTypeValue());
        projPayRollCycleTO.setPayRoll(entity.getPayRoll());

        if (CommonUtil.objectNotNull(entity.getProcureCatgDtlEntity())) {
            ProcureMentCatgTO procureMentCatgTO = new ProcureMentCatgTO();
            procureMentCatgTO.setProCatgId(entity.getProcureCatgDtlEntity().getId());
            procureMentCatgTO.setCode(entity.getProcureCatgDtlEntity().getCode());
            procureMentCatgTO.setDesc(entity.getProcureCatgDtlEntity().getName());
            projPayRollCycleTO.setProcureMentCatgTO(procureMentCatgTO);
        }
        projPayRollCycleTO.setProjEmpCatg(entity.getEmpCatg());
        projPayRollCycleTO.setStatus(entity.getStatus());

        return projPayRollCycleTO;

    }

    public static List<ProjPayRollCycleEntity> convertPOJOToEntity(List<ProjPayRollCycleTO> projPayRollCycleTOs,
            EPSProjRepository epsProjRepository, ProcureCatgRepository procureCatgRepository) {
        List<ProjPayRollCycleEntity> projPayRollCycleEntites = new ArrayList<ProjPayRollCycleEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjPayRollCycleEntity entity = null;
        for (ProjPayRollCycleTO projPayRollCycleTO : projPayRollCycleTOs) {
            entity = new ProjPayRollCycleEntity();
            if (CommonUtil.isNonBlankLong(projPayRollCycleTO.getId())) {
                entity.setId(projPayRollCycleTO.getId());
            }
            if (CommonUtil.isNonBlankLong(projPayRollCycleTO.getProjId())) {
                ProjMstrEntity projEntity = epsProjRepository.findOne(projPayRollCycleTO.getProjId());
                entity.setProjMstrEntity(projEntity);
            }
            entity.setEmpCatg(projPayRollCycleTO.getProjEmpCatg());
            if (CommonUtil.objectNotNull(projPayRollCycleTO.getProcureMentCatgTO())) {
                ProcureCatgDtlEntity catgDtlEntity = procureCatgRepository
                        .findOne(projPayRollCycleTO.getProcureMentCatgTO().getProCatgId());
                entity.setProcureCatgDtlEntity(catgDtlEntity);
            }
            entity.setPayRollType(projPayRollCycleTO.getPayRollType());
            entity.setPayRollTypeValue(projPayRollCycleTO.getPayRollTypeValue());
            entity.setPayRoll(projPayRollCycleTO.getPayRoll());
            entity.setStatus(projPayRollCycleTO.getStatus());
            projPayRollCycleEntites.add(entity);
        }
        return projPayRollCycleEntites;
    }

}
