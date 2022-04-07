package com.rjtech.timemanagement.workdairy.service.handler;

import java.util.List;

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.TimeManagentStatus;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.model.ProjSOEItemEntity;
//import com.rjtech.projectlib.model.ProjSOEItemEntityCopy;
import com.rjtech.projectlib.model.ProjSORItemEntity;
//import com.rjtech.projectlib.model.ProjSORItemEntityCopy;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyProgressDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyProgressDtlEntity;
import com.rjtech.timemanagement.workdairy.repository.WorkDairyRepository;
import com.rjtech.document.model.ProjDocFileEntity;

public class ProgressWorkDairyHandler {

    public static WorkDairyProgressDtlEntity convertPOJOToEntity(WorkDairyProgressDtlTO workDairyProgressDtlTO,
            WorkDairyRepository workDairyRepository, ProjSOWItemRepositoryCopy projSOWItemRepository,
            ProjCostItemRepositoryCopy projCostItemRepository, ProjDocFileEntity projDocFileEntity) {
        WorkDairyProgressDtlEntity workDairyProgressDtlEntity = new WorkDairyProgressDtlEntity();
        if (CommonUtil.isNonBlankLong(workDairyProgressDtlTO.getId())) {
            workDairyProgressDtlEntity.setId(workDairyProgressDtlTO.getId());
        }
        workDairyProgressDtlEntity.setWorkDairyId(workDairyRepository.findOne(workDairyProgressDtlTO.getWorkDairyId()));
        workDairyProgressDtlEntity.setApprStatus(workDairyProgressDtlTO.getApprStatus());
        if (CommonUtil.isNonBlankLong(workDairyProgressDtlTO.getSowId())) {
            workDairyProgressDtlEntity.setSowId(projSOWItemRepository.findOne(workDairyProgressDtlTO.getSowId()));
        }
        if (CommonUtil.isNonBlankLong(workDairyProgressDtlTO.getCostId())) {
            workDairyProgressDtlEntity.setCostId(projCostItemRepository.findOne(workDairyProgressDtlTO.getCostId()));
        }
        workDairyProgressDtlEntity.setValue(workDairyProgressDtlTO.getValue());
        workDairyProgressDtlEntity.setApprStatus(workDairyProgressDtlTO.getApprStatus());
        workDairyProgressDtlEntity.setApprComments(workDairyProgressDtlTO.getApprComments());
        workDairyProgressDtlEntity.setStatus(workDairyProgressDtlTO.getStatus());
        if( workDairyProgressDtlTO.getFileName() != null )
        {
        	workDairyProgressDtlEntity.setFileName( workDairyProgressDtlTO.getFileName() );
        	workDairyProgressDtlEntity.setProjDocFile( projDocFileEntity );
        }
        
        return workDairyProgressDtlEntity;
    }

    public static WorkDairyProgressDtlTO convertEntityToPOJO(WorkDairyProgressDtlEntity workDairyProgressDtlEntity) {
        WorkDairyProgressDtlTO workDairyProgressDtlTO = new WorkDairyProgressDtlTO();
        workDairyProgressDtlTO.setId(workDairyProgressDtlEntity.getId());
        WorkDairyEntity workDairy = workDairyProgressDtlEntity.getWorkDairyId();
        if (workDairy != null)
            workDairyProgressDtlTO.setWorkDairyId(workDairy.getId());
        workDairyProgressDtlTO.setApprStatus(workDairyProgressDtlEntity.getApprStatus());
        ProjSOWItemEntity sow = workDairyProgressDtlEntity.getSowId();
        if (sow != null) {
            workDairyProgressDtlTO.setSowId(sow.getId());
            workDairyProgressDtlTO.setSowCode(sow.getCode());
            workDairyProgressDtlTO.setSowDesc(sow.getName());
        }
        
        ProjSOEItemEntity soe = sow.getProjSOEItemEntity();
        if (soe != null) {
            workDairyProgressDtlTO.setSoeId(soe.getId());
            workDairyProgressDtlTO.setSoeCode(soe.getCode());
        }
        
        ProjSORItemEntity sor = sow.getProjSORItemEntity();
        if (sor != null) {
            workDairyProgressDtlTO.setSorId(sor.getId());
            workDairyProgressDtlTO.setSorCode(sor.getCode());
        }
        
        ProjCostItemEntity costCode = workDairyProgressDtlEntity.getCostId();
        if (costCode != null) {
            workDairyProgressDtlTO.setCostId(costCode.getId());
            workDairyProgressDtlTO.setCostCode(costCode.getCode());
        }
        
        MeasurmentMstrEntity measure = soe.getMeasurmentMstrEntity();
        
        if (measure != null) {
            workDairyProgressDtlTO.setMeasureId(measure.getId());
            workDairyProgressDtlTO.setMeasureCode(measure.getCode());    
        }

        workDairyProgressDtlTO.setValue(workDairyProgressDtlEntity.getValue());
        workDairyProgressDtlTO.setApprStatus(workDairyProgressDtlEntity.getApprStatus());
        workDairyProgressDtlTO.setApprComments(workDairyProgressDtlEntity.getApprComments());
        workDairyProgressDtlTO.setStatus(workDairyProgressDtlEntity.getStatus());

        return workDairyProgressDtlTO;
    }

    public static void saveProgressStatus(WorkDairyTO workDairyTO,
            List<WorkDairyProgressDtlEntity> workDairyProgressDtlEntities,
            WorkDairyProgressDtlTO workDairyProgressDtlTO, WorkDairyRepository workDairyRepository,
            ProjSOWItemRepositoryCopy projSOWItemRepository, ProjCostItemRepositoryCopy projCostItemRepository, ProjDocFileEntity projDocFileEntity) {
        workDairyProgressDtlTO.setWorkDairyId(workDairyTO.getId());
        if ((TimeManagentStatus.APPROVED.getName().equalsIgnoreCase(workDairyTO.getApprStatus())
                && !workDairyTO.isClientApproval())
                && TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName()
                        .equalsIgnoreCase(workDairyProgressDtlTO.getApprStatus())) {
            workDairyProgressDtlTO.setApprStatus(workDairyTO.getApprStatus());
            workDairyProgressDtlEntities.add(convertPOJOToEntity(workDairyProgressDtlTO, workDairyRepository,
                    projSOWItemRepository, projCostItemRepository, null));
        } else if ((TimeManagentStatus.CLIENT_APPROVED.getName().equalsIgnoreCase(workDairyTO.getApprStatus())
                && workDairyTO.isClientApproval())
                && TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                        .equalsIgnoreCase(workDairyProgressDtlTO.getApprStatus())) {
            workDairyProgressDtlTO.setApprStatus(workDairyTO.getApprStatus());
            workDairyProgressDtlEntities.add(convertPOJOToEntity(workDairyProgressDtlTO, workDairyRepository,
                    projSOWItemRepository, projCostItemRepository, null));
        } else if ((TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                .equalsIgnoreCase(workDairyTO.getApprStatus()) && workDairyTO.isClientApproval())
                && TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName()
                        .equalsIgnoreCase(workDairyProgressDtlTO.getApprStatus())) {
            if (workDairyTO.isNewRequired()) {
                workDairyProgressDtlTO.setApprStatus(TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName());
                WorkDairyProgressDtlEntity workDairyProgressDtlEntity = new WorkDairyProgressDtlEntity();
                workDairyProgressDtlEntity = convertPOJOToEntity(workDairyProgressDtlTO, workDairyRepository,
                        projSOWItemRepository, projCostItemRepository, null);
                workDairyProgressDtlEntity.setId(null);
                workDairyProgressDtlEntities.add(workDairyProgressDtlEntity);
            }
            workDairyProgressDtlTO.setApprStatus(TimeManagentStatus.APPROVED.getName());
            workDairyProgressDtlEntities.add(convertPOJOToEntity(workDairyProgressDtlTO, workDairyRepository,
                    projSOWItemRepository, projCostItemRepository, null));
        } else if ((TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                .equalsIgnoreCase(workDairyTO.getApprStatus()) && workDairyTO.isClientApproval())
                && TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                        .equalsIgnoreCase(workDairyProgressDtlTO.getApprStatus())) {
            workDairyProgressDtlEntities.add(convertPOJOToEntity(workDairyProgressDtlTO, workDairyRepository,
                    projSOWItemRepository, projCostItemRepository, null));
        } else if (TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName().equalsIgnoreCase(workDairyTO.getApprStatus())) {
            if (CommonUtil.isBlankStr(workDairyProgressDtlTO.getApprStatus())) {
                if (workDairyTO.isNewRequired()) {
                    WorkDairyProgressDtlEntity workDairyProgressDtlEntity = convertPOJOToEntity(workDairyProgressDtlTO,
                            workDairyRepository, projSOWItemRepository, projCostItemRepository, null);
                    workDairyProgressDtlEntity.setId(null);
                    workDairyProgressDtlEntities.add(workDairyProgressDtlEntity);
                    workDairyProgressDtlTO.setUserId(workDairyTO.getInternalApprUserId());
                    workDairyProgressDtlTO.setApprStatus(workDairyTO.getApprStatus());
                    workDairyProgressDtlEntities.add(convertPOJOToEntity(workDairyProgressDtlTO, workDairyRepository,
                            projSOWItemRepository, projCostItemRepository, null));
                }
            } else {
                workDairyProgressDtlEntities.add(convertPOJOToEntity(workDairyProgressDtlTO, workDairyRepository,
                        projSOWItemRepository, projCostItemRepository, null));
            }
        } else if (CommonUtil.isBlankStr(workDairyTO.getApprStatus())
                && CommonUtil.isBlankStr(workDairyProgressDtlTO.getApprStatus())) {
            workDairyProgressDtlEntities.add(convertPOJOToEntity(workDairyProgressDtlTO, workDairyRepository,
                    projSOWItemRepository, projCostItemRepository, projDocFileEntity));
        }        
    }

}
