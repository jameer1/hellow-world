package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.ProjLeaveCategoryType;
import com.rjtech.centrallib.model.ProjLeaveTypeEntity;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjLeaveCategoryTO;
import com.rjtech.projectlib.dto.ProjLeaveTypeTO;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class ProjLeaveTypeHandler {

    public static ProjLeaveTypeTO convertEntityToPOJO(ProjLeaveTypeEntity entity, boolean idRequired) {
        ProjLeaveTypeTO projLeaveTypeTO = new ProjLeaveTypeTO();
        if (idRequired)
            projLeaveTypeTO.setId(entity.getId());
        projLeaveTypeTO.setCode(entity.getCode());
        projLeaveTypeTO.setName(entity.getName());
        List<ProjLeaveCategoryType> types = entity.getProjLeaveCategoryTypes();
        if (CommonUtil.isListHasData(types)) {
            for (ProjLeaveCategoryType type : types) {
              if(type.getProcureCatgDtlEntity().getStatus()!=null && type.getProcureCatgDtlEntity().getStatus()==ApplicationConstants.STATUS_ACTIVE) {
                  ProjLeaveCategoryTO categoryTO = new ProjLeaveCategoryTO();
                  if (idRequired)
                      categoryTO.setId(type.getId());
                  categoryTO.setPayType(type.getPayType());
                  categoryTO.setProcureId(type.getProcureCatgDtlEntity().getId());
                  categoryTO.setProcureType(type.getProcureCatgDtlEntity().getName());

                  projLeaveTypeTO.getLeaveCategoryTOs().add(categoryTO);
              }
            }
        }
        projLeaveTypeTO.setPriorApproval(entity.isPriorApproval());
        projLeaveTypeTO.setMedicalForm(entity.isMedicalForm());
        projLeaveTypeTO.setEvidenceForm(entity.isEvidenceForm());
        projLeaveTypeTO.setMaxAllowYear(entity.getMaxAllowYear());
        projLeaveTypeTO.setMaxAllowEvent(entity.getMaxAllowEvent());
        projLeaveTypeTO.setCountryCode(entity.getCountryCode());
        projLeaveTypeTO.setEffectiveFrom(CommonUtil.getMMMYYYFormat(entity.getEffectiveFrom()));
        projLeaveTypeTO.setStatus(entity.getStatus());
        return projLeaveTypeTO;
    }

    public static List<ProjLeaveTypeEntity> convertPOJOToEntity(List<ProjLeaveTypeTO> projLeaveTypeTOs,
            ProcureCatgRepository procureCatgRepository, ClientRegRepository clientRegRepository) {
        List<ProjLeaveTypeEntity> projLeaveTypeEntity = new ArrayList<>();
        for (ProjLeaveTypeTO leaveTypeTO : projLeaveTypeTOs) {
            ProjLeaveTypeEntity entity = new ProjLeaveTypeEntity();
            if (CommonUtil.isNonBlankLong(leaveTypeTO.getId())) {
                entity.setId(leaveTypeTO.getId());
            }
            entity.setCode(leaveTypeTO.getCode());
            entity.setName(leaveTypeTO.getName());
            entity.setCountryCode(leaveTypeTO.getCountryCode());
            ClientRegEntity clientRegEntity = clientRegRepository.findOne(AppUserUtils.getClientId());
            entity.setClientRegEntity(clientRegEntity);
            entity.setEffectiveFrom(CommonUtil.getMMMYYYDate(leaveTypeTO.getEffectiveFrom()));
            List<ProjLeaveCategoryTO> categoryTOs = leaveTypeTO.getLeaveCategoryTOs();
            for (ProjLeaveCategoryTO categoryTO : categoryTOs) {
                ProjLeaveCategoryType type = new ProjLeaveCategoryType();
                if (CommonUtil.isNonBlankLong(categoryTO.getId())) {
                    type.setId(categoryTO.getId());
                }
                type.setPayType(categoryTO.getPayType());
                type.setProjLeaveTypeEntity(entity);
                ProcureCatgDtlEntity procureCatgDtlEntity = procureCatgRepository.findOne(categoryTO.getProcureId());
                type.setProcureCatgDtlEntity(procureCatgDtlEntity);
                entity.getProjLeaveCategoryTypes().add(type);
            }
            entity.setPriorApproval(leaveTypeTO.isPriorApproval());
            entity.setMedicalForm(leaveTypeTO.isMedicalForm());
            entity.setEvidenceForm(leaveTypeTO.isEvidenceForm());
            entity.setMaxAllowYear(leaveTypeTO.getMaxAllowYear());
            entity.setMaxAllowEvent(leaveTypeTO.getMaxAllowEvent());
            entity.setStatus(leaveTypeTO.getStatus());
            projLeaveTypeEntity.add(entity);
        }
        return projLeaveTypeEntity;
    }

}
