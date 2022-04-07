package com.rjtech.timemanagement.workdairy.service.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.TimeManagentStatus;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
//import com.rjtech.register.material.model.MaterialPODeliveryDocketEntityCopy;
import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntity;
//import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntityCopy;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
//import com.rjtech.register.material.model.MaterialProjDtlEntityCopy;
import com.rjtech.register.repository.material.MaterialDockSchItemRepositoryCopy;
import com.rjtech.register.service.handler.material.MaterialProjDtlHandlerCopy;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyMaterialCostTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyMaterialDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyMaterialStatusTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialDtlEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialStatusEntity;
import com.rjtech.timemanagement.workdairy.repository.MaterialWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.WorkDairyRepository;

public class MaterialWorkDairyHandler {
    
    public static WorkDairyMaterialDtlEntity convertPOJOToEntity(WorkDairyMaterialDtlTO workDairyMaterialDtlTO,
            MaterialDockSchItemRepositoryCopy materialDockSchItemRepository, MaterialWorkDairyRepository materialWorkdairyRepository) {
        WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity = new WorkDairyMaterialDtlEntity();
        if (CommonUtil.isNonBlankLong(workDairyMaterialDtlTO.getId())) {
            workDairyMaterialDtlEntity.setId(workDairyMaterialDtlTO.getId());
        }
        workDairyMaterialDtlEntity.setSourceType(workDairyMaterialDtlTO.getSourceType());
        workDairyMaterialDtlEntity.setDocketType(workDairyMaterialDtlTO.getDocketType());
        workDairyMaterialDtlEntity.setDocketNum(workDairyMaterialDtlTO.getDocketNum());
        workDairyMaterialDtlEntity.setComments(workDairyMaterialDtlTO.getApprComments());
        workDairyMaterialDtlEntity.setSupplierDocket(workDairyMaterialDtlTO.isSupplierDocket());
        workDairyMaterialDtlEntity.setOpeningStock(new BigDecimal(workDairyMaterialDtlTO.getOpeningStock()));
        workDairyMaterialDtlEntity.setClosingStock(new BigDecimal(workDairyMaterialDtlTO.getClosingStock()));
        if (CommonUtil.isNotBlankStr(workDairyMaterialDtlTO.getDocketDate())) {
            workDairyMaterialDtlEntity
                    .setDocketDate(CommonUtil.convertStringToDate(workDairyMaterialDtlTO.getDocketDate()));
        }

        if (workDairyMaterialDtlTO.getProjDocketSchId() != null)
            workDairyMaterialDtlEntity.setProjDocketSchId(
                    materialDockSchItemRepository.findOne(workDairyMaterialDtlTO.getProjDocketSchId()));

        if (workDairyMaterialDtlTO.getDeliveryDocketId() != null) {
            WorkDairyMaterialDtlEntity tempWorkDairyMaterialDtlEntity = null;
            if (CommonUtil.isBlankLong(workDairyMaterialDtlTO.getId())) 
                tempWorkDairyMaterialDtlEntity = workDairyMaterialDtlEntity;
            else
                tempWorkDairyMaterialDtlEntity = materialWorkdairyRepository.findOne(workDairyMaterialDtlTO.getId());
            List<MaterialPODeliveryDocketEntity> existingMaterialPoDocks = tempWorkDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity();
            Long deliveryDocketId[] = workDairyMaterialDtlTO.getDeliveryDocketId();
            for (final Long delDockId : deliveryDocketId) {
                Optional<MaterialPODeliveryDocketEntity> materialPoOptional = existingMaterialPoDocks
                        .stream().filter(o -> o.getId().equals(delDockId)).findAny();
                MaterialPODeliveryDocketEntity materialPoDeliveryDocket = null;
                if (materialPoOptional.isPresent())
                    materialPoDeliveryDocket = materialPoOptional.get();
                if (materialPoDeliveryDocket == null) {
                    MaterialPODeliveryDocketEntity materialPODeliveryDocketEntity = new MaterialPODeliveryDocketEntity();
                    materialPODeliveryDocketEntity.setId(delDockId);
                    workDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity().add(materialPODeliveryDocketEntity);
                } 
            }
        }
        
        if (workDairyMaterialDtlTO.getReceivedQuantity() == null)
            workDairyMaterialDtlEntity.setReceivedQty(workDairyMaterialDtlTO.getReceivedQty());
        else
            workDairyMaterialDtlEntity
                    .setReceivedQty(BigDecimal.valueOf(Double.valueOf(workDairyMaterialDtlTO.getReceivedQuantity())));
        workDairyMaterialDtlEntity.setDefectComments(workDairyMaterialDtlTO.getDefectComments());

        workDairyMaterialDtlEntity.setDeliveryPlace(workDairyMaterialDtlTO.getDeliveryPlace());

        WorkDairyEntity workDairyEntity = new WorkDairyEntity();
        workDairyEntity.setId(workDairyMaterialDtlTO.getWorkDairyId());
        workDairyMaterialDtlEntity.setWorkDairyEntity(workDairyEntity);
        if (workDairyMaterialDtlTO.isConsumpitonUpdated()) {
            workDairyMaterialDtlEntity.setConsumpitonUpdated(workDairyMaterialDtlTO.isConsumpitonUpdated());
        }

        workDairyMaterialDtlEntity.setStatus(workDairyMaterialDtlTO.getStatus());
        return workDairyMaterialDtlEntity;
    }

    public static WorkDairyMaterialDtlTO convertEntityToPOJO(WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity,
            MaterialWorkDairyRepository materialWorkDairyRepository) {
        WorkDairyMaterialDtlTO workDairyMaterialDtlTO = new WorkDairyMaterialDtlTO();
        workDairyMaterialDtlTO.setId(workDairyMaterialDtlEntity.getId());
        if (CommonUtil.objectNotNull(workDairyMaterialDtlEntity.getWorkDairyEntity())) {
            workDairyMaterialDtlTO.setWorkDairyId(workDairyMaterialDtlEntity.getWorkDairyEntity().getId());
        }
        workDairyMaterialDtlTO.setSourceType(workDairyMaterialDtlEntity.getSourceType());
        workDairyMaterialDtlTO.setDocketType(workDairyMaterialDtlEntity.getDocketType());
        workDairyMaterialDtlTO.setDocketNum(workDairyMaterialDtlEntity.getDocketNum());
        workDairyMaterialDtlTO.setOpeningStock((workDairyMaterialDtlEntity.getOpeningStock() == null) ? 0
                : workDairyMaterialDtlEntity.getOpeningStock().doubleValue());
        workDairyMaterialDtlTO.setClosingStock((workDairyMaterialDtlEntity.getClosingStock() == null) ? 0
                : workDairyMaterialDtlEntity.getClosingStock().doubleValue());
        workDairyMaterialDtlTO.setSupplierDocket(workDairyMaterialDtlEntity.isSupplierDocket());
        if (CommonUtil.isNotBlankDate(workDairyMaterialDtlEntity.getDocketDate())) {
            workDairyMaterialDtlTO
                    .setDocketDate(CommonUtil.convertDateToString(workDairyMaterialDtlEntity.getDocketDate()));
        }
        MaterialProjDocketSchItemsEntity workDairyMaterialSchItem = workDairyMaterialDtlEntity.getProjDocketSchId();
        if (workDairyMaterialSchItem != null) {
            workDairyMaterialDtlTO.setProjDocketSchId(workDairyMaterialSchItem.getId());
            workDairyMaterialDtlTO.setTransitQty(materialWorkDairyRepository.getDocketConsumption(workDairyMaterialSchItem));
        }
        if (CommonUtil.objectNotNull(workDairyMaterialDtlEntity.getMaterialPODeliveryDocketEntity())) {
            List<MaterialPODeliveryDocketEntity> materialPODeliveryDocketEntity = workDairyMaterialDtlEntity
                    .getMaterialPODeliveryDocketEntity();
            List<Long> deliveryDocket = new ArrayList<>();
            for (MaterialPODeliveryDocketEntity materialPoDelDocEntity : materialPODeliveryDocketEntity) {
                deliveryDocket.add(materialPoDelDocEntity.getId());
            }
            workDairyMaterialDtlTO.setDeliveryDocketId(deliveryDocket.toArray(new Long[deliveryDocket.size()]));
        }

        if (CommonUtil.objectNotNull(workDairyMaterialDtlEntity.getMaterialProjDtlEntity())) {
            List<MaterialProjDtlEntity> materialProjDtlEntity = workDairyMaterialDtlEntity
                    .getMaterialProjDtlEntity();
            List<Long> scheduleItemId = new ArrayList<Long>();
            for (MaterialProjDtlEntity materialPoDel : materialProjDtlEntity) {
                scheduleItemId.add(materialPoDel.getId());
                workDairyMaterialDtlTO.getMaterialProjDtlTO()
                        .add(MaterialProjDtlHandlerCopy.convertEntityToPOJO(materialPoDel));
            }
            workDairyMaterialDtlTO.setScheduleItemId(scheduleItemId.toArray(new Long[scheduleItemId.size()]));
        }
        workDairyMaterialDtlTO.setReceivedQty(workDairyMaterialDtlEntity.getReceivedQty());
        workDairyMaterialDtlTO.setDefectComments(workDairyMaterialDtlEntity.getDefectComments());
        workDairyMaterialDtlTO.setDeliveryPlace(workDairyMaterialDtlEntity.getDeliveryPlace());
        workDairyMaterialDtlTO.setApprComments(workDairyMaterialDtlEntity.getComments());
        workDairyMaterialDtlTO.setConsumpitonUpdated(workDairyMaterialDtlEntity.isConsumpitonUpdated());

        workDairyMaterialDtlTO.setStatus(workDairyMaterialDtlEntity.getStatus());
        return workDairyMaterialDtlTO;
    }

    public static WorkDairyMaterialCostEntity convertCostPOJOToEntity(WorkDairyMaterialCostTO workDairyMaterialCostTO,
            WorkDairyRepository workDairyRepository, ProjCostItemRepositoryCopy projCostItemRepository) {
        WorkDairyMaterialCostEntity workDairyMaterialCostEntity = new WorkDairyMaterialCostEntity();
        workDairyMaterialCostEntity.setId(workDairyMaterialCostTO.getId());
        if (CommonUtil.isNonBlankLong(workDairyMaterialCostTO.getMaterialStatusId())) {
            WorkDairyMaterialStatusEntity workDairyMaterialStatusEntity = new WorkDairyMaterialStatusEntity();
            workDairyMaterialStatusEntity.setId(workDairyMaterialCostTO.getMaterialStatusId());
            workDairyMaterialCostEntity.setWorkDairyMaterialStatusEntity(workDairyMaterialStatusEntity);
        }
        workDairyMaterialCostEntity
                .setWorkDairyId(workDairyRepository.findOne(workDairyMaterialCostTO.getWorkDairyId()));
        if (CommonUtil.isNonBlankLong(workDairyMaterialCostTO.getCostId())) {
            ProjCostItemEntity projCost = projCostItemRepository.findOne(workDairyMaterialCostTO.getCostId());
            workDairyMaterialCostEntity.setCostId(projCost);
        }
        workDairyMaterialCostEntity.setQuantity(workDairyMaterialCostTO.getQuantity());
        workDairyMaterialCostEntity.setStatus(workDairyMaterialCostTO.getStatus());

        return workDairyMaterialCostEntity;
    }

    public static WorkDairyMaterialCostTO convertCostEntityToPOJO(
            WorkDairyMaterialCostEntity workDairyMaterialCostEntity) {
        WorkDairyMaterialCostTO workDairyMaterialCostTO = new WorkDairyMaterialCostTO();
        workDairyMaterialCostTO.setId(workDairyMaterialCostEntity.getId());
        if (CommonUtil.objectNotNull(workDairyMaterialCostEntity.getWorkDairyMaterialStatusEntity())) {
            workDairyMaterialCostTO
                    .setMaterialStatusId(workDairyMaterialCostEntity.getWorkDairyMaterialStatusEntity().getId());
        }
        WorkDairyEntity workDairy = workDairyMaterialCostEntity.getWorkDairyId();
        if (workDairy != null)
            workDairyMaterialCostTO.setWorkDairyId(workDairy.getId());
        ProjCostItemEntity costCode = workDairyMaterialCostEntity.getCostId();
        if (costCode != null)
            workDairyMaterialCostTO.setCostId(costCode.getId());
        workDairyMaterialCostTO.setQuantity(workDairyMaterialCostEntity.getQuantity());
        workDairyMaterialCostTO.setStatus(workDairyMaterialCostEntity.getStatus());

        return workDairyMaterialCostTO;
    }

    public static WorkDairyMaterialStatusTO convertMaterialStatusEntityToPOJO(
            WorkDairyMaterialStatusEntity workDairyMaterialStatusEntity) {
        WorkDairyMaterialStatusTO workDairyMaterialStatusTO = new WorkDairyMaterialStatusTO();
        workDairyMaterialStatusTO.setId(workDairyMaterialStatusEntity.getId());

        if (CommonUtil.objectNotNull(workDairyMaterialStatusEntity.getWorkDairyMaterialDtlEntity())) {
            workDairyMaterialStatusTO
                    .setMaterialDtlId(workDairyMaterialStatusEntity.getWorkDairyMaterialDtlEntity().getId());
        }
        if (CommonUtil.objectNotNull(workDairyMaterialStatusEntity.getUserMstrEntity())) {
            workDairyMaterialStatusTO.setUserId(workDairyMaterialStatusEntity.getUserMstrEntity().getUserId());
        }
        workDairyMaterialStatusTO.setTotal(workDairyMaterialStatusEntity.getTotal());

        workDairyMaterialStatusTO.setApprStatus(workDairyMaterialStatusEntity.getApprStatus());
        workDairyMaterialStatusTO.setStatus(workDairyMaterialStatusEntity.getStatus());
        workDairyMaterialStatusTO.setComments(workDairyMaterialStatusEntity.getComments());
        return workDairyMaterialStatusTO;
    }

    public static WorkDairyMaterialStatusEntity convertMaterialStatusPOJOToEntity(
            WorkDairyMaterialStatusTO workDairyMaterialStatusTO, LoginRepository loginRepository) {
        WorkDairyMaterialStatusEntity workDairyMaterialStatusEntity = new WorkDairyMaterialStatusEntity();
        if (CommonUtil.isNonBlankLong(workDairyMaterialStatusTO.getId())) {
            workDairyMaterialStatusEntity.setId(workDairyMaterialStatusTO.getId());
        }
        if (CommonUtil.isNonBlankLong(workDairyMaterialStatusTO.getMaterialDtlId())) {
            WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity = new WorkDairyMaterialDtlEntity();
            workDairyMaterialDtlEntity.setId(workDairyMaterialStatusTO.getMaterialDtlId());
            workDairyMaterialStatusEntity.setWorkDairyMaterialDtlEntity(workDairyMaterialDtlEntity);
        }
        workDairyMaterialStatusEntity.setTotal(workDairyMaterialStatusTO.getTotal());

        workDairyMaterialStatusEntity.setComments(workDairyMaterialStatusTO.getComments());
        workDairyMaterialStatusEntity.setApprStatus(workDairyMaterialStatusTO.getApprStatus());
        if (CommonUtil.isNonBlankLong(workDairyMaterialStatusTO.getUserId())) {
            UserMstrEntity userMstrEntity = loginRepository.findOne(workDairyMaterialStatusTO.getUserId());
            workDairyMaterialStatusEntity.setUserMstrEntity(userMstrEntity);
        }
        workDairyMaterialStatusEntity.setStatus(workDairyMaterialStatusTO.getStatus());

        return workDairyMaterialStatusEntity;
    }

    public static void poupluateMaterialStatus(WorkDairyTO workDairyTO,
            WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity, WorkDairyMaterialStatusTO workDairyMaterialStatusTO,
            WorkDairyRepository workDairyRepository, ProjCostItemRepositoryCopy projCostItemRepository,
            LoginRepository loginRepository) {
        if ((TimeManagentStatus.APPROVED.getName().equalsIgnoreCase(workDairyTO.getApprStatus())
                && !workDairyTO.isClientApproval())
                && TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName()
                        .equalsIgnoreCase(workDairyMaterialStatusTO.getApprStatus())) {
            workDairyMaterialStatusTO.setApprStatus(workDairyTO.getApprStatus());
            populateMaterialStatus(workDairyMaterialDtlEntity, workDairyMaterialStatusTO, workDairyRepository,
                    projCostItemRepository, loginRepository);
        } else if ((TimeManagentStatus.CLIENT_APPROVED.getName().equalsIgnoreCase(workDairyTO.getApprStatus())
                && workDairyTO.isClientApproval())
                && TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                        .equalsIgnoreCase(workDairyMaterialStatusTO.getApprStatus())) {
            workDairyMaterialStatusTO.setApprStatus(workDairyTO.getApprStatus());
            populateMaterialStatus(workDairyMaterialDtlEntity, workDairyMaterialStatusTO, workDairyRepository,
                    projCostItemRepository, loginRepository);
        } else if ((TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                .equalsIgnoreCase(workDairyTO.getApprStatus()) && workDairyTO.isClientApproval())
                && TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName()
                        .equalsIgnoreCase(workDairyMaterialStatusTO.getApprStatus())) {
            if (workDairyTO.isNewRequired()) {
                workDairyMaterialStatusTO.setApprStatus(TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName());
                workDairyMaterialDtlEntity.getWorkDairyMaterialStatusEntities().add(createMatrialStatus(
                        workDairyMaterialStatusTO, workDairyRepository, projCostItemRepository, loginRepository));
            }
            workDairyMaterialStatusTO.setApprStatus(TimeManagentStatus.APPROVED.getName());
            populateMaterialStatus(workDairyMaterialDtlEntity, workDairyMaterialStatusTO, workDairyRepository,
                    projCostItemRepository, loginRepository);
        } else if ((TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                .equalsIgnoreCase(workDairyTO.getApprStatus()) && workDairyTO.isClientApproval())
                && TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                        .equalsIgnoreCase(workDairyMaterialStatusTO.getApprStatus())) {
            populateMaterialStatus(workDairyMaterialDtlEntity, workDairyMaterialStatusTO, workDairyRepository,
                    projCostItemRepository, loginRepository);
        } else if (TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName().equalsIgnoreCase(workDairyTO.getApprStatus())) {
            if (CommonUtil.isBlankStr(workDairyMaterialStatusTO.getApprStatus())) {
                if (workDairyTO.isNewRequired()) {
                    workDairyMaterialDtlEntity.getWorkDairyMaterialStatusEntities().add(createMatrialStatus(
                            workDairyMaterialStatusTO, workDairyRepository, projCostItemRepository, loginRepository));
                    workDairyMaterialStatusTO.setUserId(workDairyTO.getInternalApprUserId());
                    workDairyMaterialStatusTO.setApprStatus(workDairyTO.getApprStatus());
                    populateMaterialStatus(workDairyMaterialDtlEntity, workDairyMaterialStatusTO, workDairyRepository,
                            projCostItemRepository, loginRepository);
                }
            } else {
                populateMaterialStatus(workDairyMaterialDtlEntity, workDairyMaterialStatusTO, workDairyRepository,
                        projCostItemRepository, loginRepository);
            }
        } else if (CommonUtil.isBlankStr(workDairyTO.getApprStatus())
                && CommonUtil.isBlankStr(workDairyMaterialStatusTO.getApprStatus())) {
            populateMaterialStatus(workDairyMaterialDtlEntity, workDairyMaterialStatusTO, workDairyRepository,
                    projCostItemRepository, loginRepository);
        }
    }

    private static void populateMaterialStatus(WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity,
            WorkDairyMaterialStatusTO workDairyMaterialStatusTO, WorkDairyRepository workDairyRepository,
            ProjCostItemRepositoryCopy projCostItemRepository, LoginRepository loginRepository) {
        WorkDairyMaterialStatusEntity workDairyMaterialStatusEntity;
        WorkDairyMaterialCostEntity workDairyMaterialCostEntity;
        workDairyMaterialStatusEntity = convertMaterialStatusPOJOToEntity(workDairyMaterialStatusTO, loginRepository);
        workDairyMaterialStatusEntity.setWorkDairyMaterialDtlEntity(workDairyMaterialDtlEntity);
        workDairyMaterialDtlEntity.getWorkDairyMaterialStatusEntities().add(workDairyMaterialStatusEntity);
        for (WorkDairyMaterialCostTO workDairyMaterialCostTO : workDairyMaterialStatusTO
                .getWorkDairyMaterialCostTOs()) {
            workDairyMaterialCostEntity = convertCostPOJOToEntity(workDairyMaterialCostTO, workDairyRepository,
                    projCostItemRepository);
            workDairyMaterialCostEntity.setWorkDairyMaterialStatusEntity(workDairyMaterialStatusEntity);
            workDairyMaterialStatusEntity.getWorkDairyMaterialCostEntities().add(workDairyMaterialCostEntity);
        }
    }

    private static WorkDairyMaterialStatusEntity createMatrialStatus(
            WorkDairyMaterialStatusTO workDairyMaterialStatusTO, WorkDairyRepository workDairyRepository,
            ProjCostItemRepositoryCopy projCostItemRepository, LoginRepository loginRepository) {
        WorkDairyMaterialStatusEntity workDairyMaterialStatusEntity = null;
        WorkDairyMaterialCostEntity workDairyMaterialCostEntity = null;
        workDairyMaterialStatusEntity = convertMaterialStatusPOJOToEntity(workDairyMaterialStatusTO, loginRepository);
        workDairyMaterialStatusEntity.setId(null);
        for (WorkDairyMaterialCostTO workDairyMaterialCostTO : workDairyMaterialStatusTO
                .getWorkDairyMaterialCostTOs()) {
            workDairyMaterialCostEntity = convertCostPOJOToEntity(workDairyMaterialCostTO, workDairyRepository,
                    projCostItemRepository);
            workDairyMaterialCostEntity.setId(null);
            workDairyMaterialCostEntity.setWorkDairyMaterialStatusEntity(workDairyMaterialStatusEntity);
            workDairyMaterialStatusEntity.getWorkDairyMaterialCostEntities().add(workDairyMaterialCostEntity);
        }
        return workDairyMaterialStatusEntity;
    }

}
