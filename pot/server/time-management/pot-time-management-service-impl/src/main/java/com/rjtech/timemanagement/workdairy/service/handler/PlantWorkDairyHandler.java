package com.rjtech.timemanagement.workdairy.service.handler;

import java.util.List;

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.TimeManagentStatus;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
//import com.rjtech.register.plant.model.PlantRegisterDtlEntityCopy;
import com.rjtech.register.repository.plant.PlantRegisterRepositoryCopy;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyPlantCostDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyPlantDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyPlantStatusTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantDtlEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantStatusEntity;
import com.rjtech.timemanagement.workdairy.repository.WorkDairyRepository;

public class PlantWorkDairyHandler {

    public static WorkDairyPlantDtlEntity convertPOJOToEntity(WorkDairyPlantDtlTO workDairyPlantDtlTO,
            PlantRegisterRepositoryCopy plantRegisterRepository, LoginRepository loginRepository) {
        WorkDairyPlantDtlEntity workDairyPlantDtlEntity = new WorkDairyPlantDtlEntity();
        if (CommonUtil.isNonBlankLong(workDairyPlantDtlTO.getId())) {
            workDairyPlantDtlEntity.setId(workDairyPlantDtlTO.getId());
        }
        WorkDairyEntity workDairyEntity = new WorkDairyEntity();
        workDairyEntity.setId(workDairyPlantDtlTO.getWorkDairyId());
        workDairyPlantDtlEntity.setWorkDairyEntity(workDairyEntity);
        workDairyPlantDtlEntity.setApprStatus(workDairyPlantDtlTO.getApprStatus());
        if (CommonUtil.isNonBlankLong(workDairyPlantDtlTO.getPlantRegId())) {
            PlantRegisterDtlEntity plant = plantRegisterRepository.findOne(workDairyPlantDtlTO.getPlantRegId());
            workDairyPlantDtlEntity.setPlantRegId(plant);
        }
        workDairyPlantDtlEntity.setShiftName(workDairyPlantDtlTO.getShiftName());
        if (workDairyPlantDtlTO.getApprUserId() != null)
            workDairyPlantDtlEntity.setApprUserId(loginRepository.findOne(workDairyPlantDtlTO.getApprUserId()));
        workDairyPlantDtlEntity.setStatus(workDairyPlantDtlTO.getStatus());

        return workDairyPlantDtlEntity;
    }

    public static WorkDairyPlantDtlTO convertEntityToPOJO(WorkDairyPlantDtlEntity workDairyPlantDtlEntity) {
        WorkDairyPlantDtlTO workDairyPlantDtlTO = new WorkDairyPlantDtlTO();
        workDairyPlantDtlTO.setId(workDairyPlantDtlEntity.getId());
        if (CommonUtil.objectNotNull(workDairyPlantDtlEntity.getWorkDairyEntity())) {
            workDairyPlantDtlTO.setWorkDairyId(workDairyPlantDtlEntity.getWorkDairyEntity().getId());
        }
        PlantRegisterDtlEntity plantReg = workDairyPlantDtlEntity.getPlantRegId();
        if (plantReg != null) {
            workDairyPlantDtlTO.setPlantRegId(plantReg.getId());
            workDairyPlantDtlTO.setCode(plantReg.getAssertId());
            workDairyPlantDtlTO.setPlantRegNum(plantReg.getRegNumber());

            PlantMstrEntity plantMstrEntity = plantReg.getPlantClassMstrId();
            if (plantMstrEntity != null) {
                workDairyPlantDtlTO.setName(plantMstrEntity.getName());
                workDairyPlantDtlTO.setClassType(plantMstrEntity.getName());
                workDairyPlantDtlTO.setPlantClassId(plantMstrEntity.getId());
                MeasurmentMstrEntity measureMstr = plantMstrEntity.getMeasurmentMstrEntity();
                if (measureMstr != null)
                    workDairyPlantDtlTO.setMeasureUnit(measureMstr.getName());
            }
            workDairyPlantDtlTO.setManufacture(plantReg.getManfacture());
            workDairyPlantDtlTO.setModel(plantReg.getModel());
            workDairyPlantDtlTO.setCmpCategoryName(plantReg.getCmpId().getName());
            ProcureCatgDtlEntity procureCatg = plantReg.getProcurecatgId();
            if (procureCatg != null)
                workDairyPlantDtlTO.setProcureCatg(procureCatg.getName());
        }
        workDairyPlantDtlTO.setShiftName(workDairyPlantDtlEntity.getShiftName());
        workDairyPlantDtlTO.setApprStatus(workDairyPlantDtlEntity.getApprStatus());
        UserMstrEntity apprUser = workDairyPlantDtlEntity.getApprUserId();
        if (apprUser != null)
            workDairyPlantDtlTO.setApprUserId(apprUser.getUserId());
        workDairyPlantDtlTO.setStatus(workDairyPlantDtlEntity.getStatus());

        return workDairyPlantDtlTO;
    }

    public static WorkDairyPlantCostDtlTO convertCostEntityToPOJO(WorkDairyPlantCostEntity workDairyPlantCostEntity) {
        WorkDairyPlantCostDtlTO workDairyPlantCostDtlTO = new WorkDairyPlantCostDtlTO();
        workDairyPlantCostDtlTO.setId(workDairyPlantCostEntity.getId());
        if (CommonUtil.objectNotNull(workDairyPlantCostEntity.getWorkDairyPlantStatusEntity())) {
            workDairyPlantCostDtlTO.setPlantStatusId(workDairyPlantCostEntity.getWorkDairyPlantStatusEntity().getId());
        }
        WorkDairyEntity workDairy = workDairyPlantCostEntity.getWorkDairyId();
        if (workDairy != null)
            workDairyPlantCostDtlTO.setWorkDairyId(workDairy.getId());
        ProjCostItemEntity costCode = workDairyPlantCostEntity.getCostId();
        if (costCode != null)
            workDairyPlantCostDtlTO.setCostId(costCode.getId());
        workDairyPlantCostDtlTO.setUsedTime(workDairyPlantCostEntity.getUsedTime());
        workDairyPlantCostDtlTO.setIdleTime(workDairyPlantCostEntity.getIdleTime());
        workDairyPlantCostDtlTO.setOldUsedTime(workDairyPlantCostEntity.getUsedTime());
        workDairyPlantCostDtlTO.setOldIdleTime(workDairyPlantCostEntity.getIdleTime());
        workDairyPlantCostDtlTO.setStatus(workDairyPlantCostEntity.getStatus());

        return workDairyPlantCostDtlTO;
    }

    public static WorkDairyPlantCostEntity convertCostPOJOToEntity(WorkDairyPlantCostDtlTO workDairyPlantCostDtlTO,
            WorkDairyRepository workDairyRepository, ProjCostItemRepositoryCopy projCostItemRepository) {
        WorkDairyPlantCostEntity workDairyPlantCostEntity = new WorkDairyPlantCostEntity();
        workDairyPlantCostEntity.setId(workDairyPlantCostDtlTO.getId());

        if (CommonUtil.isNonBlankLong(workDairyPlantCostDtlTO.getPlantStatusId())) {
            WorkDairyPlantStatusEntity workDairyPlantStatusEntity = new WorkDairyPlantStatusEntity();
            workDairyPlantStatusEntity.setId(workDairyPlantCostDtlTO.getPlantStatusId());
            workDairyPlantCostEntity.setWorkDairyPlantStatusEntity(workDairyPlantStatusEntity);
        }
        workDairyPlantCostEntity.setWorkDairyId(workDairyRepository.findOne(workDairyPlantCostDtlTO.getWorkDairyId()));
        workDairyPlantCostEntity.setCostId(projCostItemRepository.findOne(workDairyPlantCostDtlTO.getCostId()));
        workDairyPlantCostEntity.setIdleTime(workDairyPlantCostDtlTO.getIdleTime());
        workDairyPlantCostEntity.setUsedTime(workDairyPlantCostDtlTO.getUsedTime());
        workDairyPlantCostEntity.setIsLatest(true);
        workDairyPlantCostEntity.setStatus(workDairyPlantCostDtlTO.getStatus());

        return workDairyPlantCostEntity;
    }

    public static WorkDairyPlantStatusTO convertPlantStatusEntityToPOJO(
            WorkDairyPlantStatusEntity workDairyPlantStatusEntity) {
        WorkDairyPlantStatusTO workDairyPlantStatusTO = new WorkDairyPlantStatusTO();
        workDairyPlantStatusTO.setId(workDairyPlantStatusEntity.getId());

        if (CommonUtil.objectNotNull(workDairyPlantStatusEntity.getWorkDairyPlantDtlEntity())) {
            workDairyPlantStatusTO.setPlantDtlId(workDairyPlantStatusEntity.getWorkDairyPlantDtlEntity().getId());
        }
        workDairyPlantStatusTO.setUsedTotal(workDairyPlantStatusEntity.getUsedTotal());
        workDairyPlantStatusTO.setIdleTotal(workDairyPlantStatusEntity.getIdleTotal());

        workDairyPlantStatusTO.setApprStatus(workDairyPlantStatusEntity.getApprStatus());
        UserMstrEntity user = workDairyPlantStatusEntity.getUserId();
        if (user != null)
            workDairyPlantStatusTO.setUserId(user.getUserId());
        workDairyPlantStatusTO.setStatus(workDairyPlantStatusEntity.getStatus());
        workDairyPlantStatusTO.setComments(workDairyPlantStatusEntity.getComments());
        return workDairyPlantStatusTO;
    }

    public static WorkDairyPlantStatusEntity convertPlantStatusPOJOToEntity(
            WorkDairyPlantStatusTO workDairyPlantStatusTO, LoginRepository loginRepository) {
        WorkDairyPlantStatusEntity workDairyPlantStatusEntity = new WorkDairyPlantStatusEntity();
        if (CommonUtil.isNonBlankLong(workDairyPlantStatusTO.getId())) {
            workDairyPlantStatusEntity.setId(workDairyPlantStatusTO.getId());
        }
        if (CommonUtil.isNonBlankLong(workDairyPlantStatusTO.getPlantDtlId())) {
            WorkDairyPlantDtlEntity workDairyPlantDtlEntity = new WorkDairyPlantDtlEntity();
            workDairyPlantDtlEntity.setId(workDairyPlantStatusTO.getPlantDtlId());
            workDairyPlantStatusEntity.setWorkDairyPlantDtlEntity(workDairyPlantDtlEntity);
        }
        workDairyPlantStatusEntity.setUsedTotal(workDairyPlantStatusTO.getUsedTotal());
        workDairyPlantStatusEntity.setIdleTotal(workDairyPlantStatusTO.getIdleTotal());

        workDairyPlantStatusEntity.setComments(workDairyPlantStatusTO.getComments());
        workDairyPlantStatusEntity.setApprStatus(workDairyPlantStatusTO.getApprStatus());
        if (workDairyPlantStatusTO.getUserId() != null)
            workDairyPlantStatusEntity.setUserId(loginRepository.findOne(workDairyPlantStatusTO.getUserId()));
        workDairyPlantStatusEntity.setStatus(workDairyPlantStatusTO.getStatus());

        return workDairyPlantStatusEntity;
    }

    public static void savePlantStatus(WorkDairyTO workDairyTO, List<WorkDairyPlantDtlEntity> workDairyPlantDtlEntities,
            WorkDairyPlantDtlTO workDairyPlantDtlTO, WorkDairyRepository workDairyRepository,
            ProjCostItemRepositoryCopy projCostItemRepository, PlantRegisterRepositoryCopy plantRegisterRepository,
            LoginRepository loginRepository) {
        WorkDairyPlantDtlEntity workDairyPlantDtlEntity;
        workDairyPlantDtlTO.setWorkDairyId(workDairyTO.getId());
        workDairyPlantDtlEntity = convertPOJOToEntity(workDairyPlantDtlTO, plantRegisterRepository, loginRepository);
        workDairyPlantDtlEntities.add(workDairyPlantDtlEntity);
        for (WorkDairyPlantStatusTO workDairyPlantStatusTO : workDairyPlantDtlTO.getWorkDairyPlantStatusTOs()) {
            if ((TimeManagentStatus.APPROVED.getName().equalsIgnoreCase(workDairyTO.getApprStatus())
                    && !workDairyTO.isClientApproval())
                    && TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName()
                            .equalsIgnoreCase(workDairyPlantStatusTO.getApprStatus())) {
                workDairyPlantStatusTO.setApprStatus(workDairyTO.getApprStatus());
                populatePlantWorkDariy(workDairyPlantDtlEntity, workDairyPlantStatusTO, workDairyRepository,
                        projCostItemRepository, loginRepository);
            } else if ((TimeManagentStatus.CLIENT_APPROVED.getName().equalsIgnoreCase(workDairyTO.getApprStatus())
                    && workDairyTO.isClientApproval())
                    && TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                            .equalsIgnoreCase(workDairyPlantStatusTO.getApprStatus())) {
                workDairyPlantStatusTO.setApprStatus(workDairyTO.getApprStatus());
                populatePlantWorkDariy(workDairyPlantDtlEntity, workDairyPlantStatusTO, workDairyRepository,
                        projCostItemRepository, loginRepository);
            } else if ((TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                    .equalsIgnoreCase(workDairyTO.getApprStatus()) && workDairyTO.isClientApproval())
                    && TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName()
                            .equalsIgnoreCase(workDairyPlantStatusTO.getApprStatus())) {
                if (workDairyTO.isNewRequired()) {
                    workDairyPlantStatusTO.setApprStatus(TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName());
                    workDairyPlantDtlEntity.getWorkDairyPlantStatusEntities().add(createPlantStatus(
                            workDairyPlantStatusTO, workDairyRepository, projCostItemRepository, loginRepository));
                }
                workDairyPlantStatusTO.setApprStatus(TimeManagentStatus.APPROVED.getName());
                populatePlantWorkDariy(workDairyPlantDtlEntity, workDairyPlantStatusTO, workDairyRepository,
                        projCostItemRepository, loginRepository);
            } else if ((TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                    .equalsIgnoreCase(workDairyTO.getApprStatus()) && workDairyTO.isClientApproval())
                    && TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                            .equalsIgnoreCase(workDairyPlantStatusTO.getApprStatus())) {
                populatePlantWorkDariy(workDairyPlantDtlEntity, workDairyPlantStatusTO, workDairyRepository,
                        projCostItemRepository, loginRepository);
            } else if (TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName().equalsIgnoreCase(workDairyTO.getApprStatus())) {
                if (CommonUtil.isBlankStr(workDairyPlantStatusTO.getApprStatus())) {
                    if (workDairyTO.isNewRequired()) {
                        workDairyPlantDtlEntity.getWorkDairyPlantStatusEntities().add(createPlantStatus(
                                workDairyPlantStatusTO, workDairyRepository, projCostItemRepository, loginRepository));
                        workDairyPlantStatusTO.setUserId(workDairyTO.getInternalApprUserId());
                        workDairyPlantStatusTO.setApprStatus(workDairyTO.getApprStatus());
                        populatePlantWorkDariy(workDairyPlantDtlEntity, workDairyPlantStatusTO, workDairyRepository,
                                projCostItemRepository, loginRepository);
                    }
                } else {
                    populatePlantWorkDariy(workDairyPlantDtlEntity, workDairyPlantStatusTO, workDairyRepository,
                            projCostItemRepository, loginRepository);
                }
            } else if (CommonUtil.isBlankStr(workDairyTO.getApprStatus())
                    && CommonUtil.isBlankStr(workDairyPlantStatusTO.getApprStatus())) {
                populatePlantWorkDariy(workDairyPlantDtlEntity, workDairyPlantStatusTO, workDairyRepository,
                        projCostItemRepository, loginRepository);
            }
        }
    }

    private static void populatePlantWorkDariy(WorkDairyPlantDtlEntity workDairyPlantDtlEntity,
            WorkDairyPlantStatusTO workDairyPlantStatusTO, WorkDairyRepository workDairyRepository,
            ProjCostItemRepositoryCopy projCostItemRepository, LoginRepository loginRepository) {
        WorkDairyPlantStatusEntity workDairyPlantStatusEntity;
        WorkDairyPlantCostEntity workDairyPlantCostEntity;
        workDairyPlantStatusEntity = convertPlantStatusPOJOToEntity(workDairyPlantStatusTO, loginRepository);
        workDairyPlantStatusEntity.setWorkDairyPlantDtlEntity(workDairyPlantDtlEntity);
        workDairyPlantDtlEntity.getWorkDairyPlantStatusEntities().add(workDairyPlantStatusEntity);
        for (WorkDairyPlantCostDtlTO workDairyPlantCostDtlTO : workDairyPlantStatusTO.getWorkDairyPlantCostDtlTOs()) {
            workDairyPlantCostEntity = convertCostPOJOToEntity(workDairyPlantCostDtlTO, workDairyRepository,
                    projCostItemRepository);
            workDairyPlantCostEntity.setWorkDairyPlantStatusEntity(workDairyPlantStatusEntity);
            workDairyPlantStatusEntity.getWorkDairyPlantCostEntities().add(workDairyPlantCostEntity);
        }
    }

    private static WorkDairyPlantStatusEntity createPlantStatus(WorkDairyPlantStatusTO workDairyPlantStatusTO,
            WorkDairyRepository workDairyRepository, ProjCostItemRepositoryCopy projCostItemRepository,
            LoginRepository loginRepository) {
        WorkDairyPlantStatusEntity workDairyPlantStatusEntity = null;
        WorkDairyPlantCostEntity workDairyPlantCostEntity = null;
        workDairyPlantStatusEntity = convertPlantStatusPOJOToEntity(workDairyPlantStatusTO, loginRepository);
        workDairyPlantStatusEntity.setId(null);
        if (workDairyPlantStatusTO.getUserId() != null)
            workDairyPlantStatusEntity.setUserId(loginRepository.findOne(workDairyPlantStatusTO.getUserId()));
        workDairyPlantStatusEntity.setApprStatus(workDairyPlantStatusTO.getApprStatus());
        for (WorkDairyPlantCostDtlTO workDairyPlantCostDtlTO : workDairyPlantStatusTO.getWorkDairyPlantCostDtlTOs()) {
            workDairyPlantCostEntity = convertCostPOJOToEntity(workDairyPlantCostDtlTO, workDairyRepository,
                    projCostItemRepository);
            workDairyPlantCostEntity.setId(null);
            workDairyPlantCostEntity.setWorkDairyPlantStatusEntity(workDairyPlantStatusEntity);
            workDairyPlantStatusEntity.getWorkDairyPlantCostEntities().add(workDairyPlantCostEntity);
        }
        return workDairyPlantStatusEntity;
    }

}
