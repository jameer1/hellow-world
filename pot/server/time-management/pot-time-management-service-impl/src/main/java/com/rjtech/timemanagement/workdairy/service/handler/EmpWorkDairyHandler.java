package com.rjtech.timemanagement.workdairy.service.handler;

import java.util.List;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.EmpWageMstrEntity;
import com.rjtech.centrallib.repository.EmpWageRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.TimeManagentStatus;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepositoryCopy;
import com.rjtech.register.utils.RegisterCommonUtils;
//import com.rjtech.timemanagement.register.emp.model.EmpProjRigisterEntityCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyEmpCostDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyEmpDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyEmpWageTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpDtlEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpWageEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.repository.WorkDairyRepository;

public class EmpWorkDairyHandler {

    public static WorkDairyEmpDtlEntity convertPOJOToEntity(WorkDairyEmpDtlTO workDairyEmpDtlTO,
            EmpRegisterRepositoryCopy empRegisterRepository) {
        WorkDairyEmpDtlEntity workDairyEmpDtlEntity = new WorkDairyEmpDtlEntity();
        if (CommonUtil.isNonBlankLong(workDairyEmpDtlTO.getId())) {
            workDairyEmpDtlEntity.setId(workDairyEmpDtlTO.getId());
        }
        WorkDairyEntity workDairyEntity = new WorkDairyEntity();
        workDairyEntity.setId(workDairyEmpDtlTO.getWorkDairyId());
        workDairyEmpDtlEntity.setWorkDairyEntity(workDairyEntity);
        if (CommonUtil.isNonBlankLong(workDairyEmpDtlTO.getEmpRegId())) {
            EmpRegisterDtlEntity empReg = empRegisterRepository.findOne(workDairyEmpDtlTO.getEmpRegId());
            workDairyEmpDtlEntity.setEmpRegId(empReg);
        }
        workDairyEmpDtlEntity.setStatus(workDairyEmpDtlTO.getStatus());

        return workDairyEmpDtlEntity;
    }

    public static WorkDairyEmpDtlTO convertEntityToPOJO(WorkDairyEmpDtlEntity workDairyEmpDtlEntity,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy) {
        WorkDairyEmpDtlTO workDairyEmpDtlTO = new WorkDairyEmpDtlTO();
        workDairyEmpDtlTO.setId(workDairyEmpDtlEntity.getId());
        if (CommonUtil.objectNotNull(workDairyEmpDtlEntity.getWorkDairyEntity())) {
            workDairyEmpDtlTO.setWorkDairyId(workDairyEmpDtlEntity.getWorkDairyEntity().getId());
        }
        EmpRegisterDtlEntity empReg = workDairyEmpDtlEntity.getEmpRegId();
        if (empReg != null) {
            workDairyEmpDtlTO.setEmpRegId(empReg.getId());
            workDairyEmpDtlTO.setCode(empReg.getCode());
            EmpClassMstrEntity empClass = empReg.getEmpClassMstrEntity();
            workDairyEmpDtlTO.setClassType(empClass.getName());
            workDairyEmpDtlTO.setEmpClassId(empClass.getId());
            workDairyEmpDtlTO.setCmpCatgName(empReg.getCompanyMstrEntity().getName());
            workDairyEmpDtlTO.setFirstName(empReg.getFirstName());
            workDairyEmpDtlTO.setLastName(empReg.getLastName());
            workDairyEmpDtlTO.setGender(empReg.getGender());
            workDairyEmpDtlTO.setProcureCatg(empReg.getProcureCatgDtlEntity().getName());
            workDairyEmpDtlTO.setApprStatus(workDairyEmpDtlEntity.getWorkDairyEntity().getApprStatus());
            workDairyEmpDtlTO.setUnitOfMeasure("hours");
            if (projEmpClassRepositoryCopy != null) {
                workDairyEmpDtlTO.setEmpCategory(projEmpClassRepositoryCopy
                        .getEmpCategoryNameByEmpClassId(empClass.getId(), empReg.getProjMstrEntity().getProjectId()));
                List<EmpProjRigisterEntity> projRegisters = empReg.getProjEmpRigisterEntities();
                for (EmpProjRigisterEntity projReg : projRegisters) {
                    if (projReg.getIsLatest().equals(RegisterCommonUtils.IS_LATEST_Y)) {
                        workDairyEmpDtlTO.setMobilizationDate(projReg.getMobilaizationDate());
                        break;
                    }
                }
            }
        }
        workDairyEmpDtlTO.setStatus(workDairyEmpDtlEntity.getStatus());
        return workDairyEmpDtlTO;
    }

    public static WorkDairyEmpWageTO convertWageEntityToPOJO(WorkDairyEmpWageEntity workDairyEmpWageEntity) {
        WorkDairyEmpWageTO workDairyEmpWageTO = new WorkDairyEmpWageTO();
        workDairyEmpWageTO.setId(workDairyEmpWageEntity.getId());
        EmpWageMstrEntity empWage = workDairyEmpWageEntity.getWageId();
        if (empWage != null)
            workDairyEmpWageTO.setWageId(empWage.getId());

        if (CommonUtil.objectNotNull(workDairyEmpWageEntity.getWorkDairyEmpDtlEntity())) {
            workDairyEmpWageTO.setEmpDtlId(workDairyEmpWageEntity.getWorkDairyEmpDtlEntity().getId());
        }

        workDairyEmpWageTO.setCode(workDairyEmpWageEntity.getWageId().getName());
        workDairyEmpWageTO.setUsedTotal(workDairyEmpWageEntity.getUsedTotal());
        workDairyEmpWageTO.setIdleTotal(workDairyEmpWageEntity.getIdleTotal());

        workDairyEmpWageTO.setApprComments(workDairyEmpWageEntity.getApprComments());
        workDairyEmpWageTO.setApprStatus(workDairyEmpWageEntity.getApprStatus());
        workDairyEmpWageTO.setStatus(workDairyEmpWageEntity.getStatus());

        return workDairyEmpWageTO;
    }

    public static WorkDairyEmpWageEntity convertWagePOJOToEntity(WorkDairyEmpWageTO workDairyEmpWageTO,
            EmpWageRepository empWageRepository) {
        WorkDairyEmpWageEntity workDairyEmpWageEntity = new WorkDairyEmpWageEntity();
        if (CommonUtil.isNonBlankLong(workDairyEmpWageTO.getId())) {
            workDairyEmpWageEntity.setId(workDairyEmpWageTO.getId());
        }
        workDairyEmpWageEntity.setWageId(empWageRepository.findOne(workDairyEmpWageTO.getWageId()));
        if (CommonUtil.isNonBlankLong(workDairyEmpWageTO.getEmpDtlId())) {
            WorkDairyEmpDtlEntity workDairyEmpDtlEntity = new WorkDairyEmpDtlEntity();
            workDairyEmpDtlEntity.setId(workDairyEmpWageTO.getEmpDtlId());
            workDairyEmpWageEntity.setWorkDairyEmpDtlEntity(workDairyEmpDtlEntity);
        }
        workDairyEmpWageEntity.setUsedTotal(workDairyEmpWageTO.getUsedTotal());
        workDairyEmpWageEntity.setIdleTotal(workDairyEmpWageTO.getIdleTotal());

        workDairyEmpWageEntity.setApprComments(workDairyEmpWageTO.getApprComments());
        workDairyEmpWageEntity.setApprStatus(workDairyEmpWageTO.getApprStatus());
        workDairyEmpWageEntity.setStatus(workDairyEmpWageTO.getStatus());

        return workDairyEmpWageEntity;
    }

    public static WorkDairyEmpCostDtlTO convertCostEntityToPOJO(WorkDairyEmpCostEntity workDairyEmpCostEntity) {
        WorkDairyEmpCostDtlTO workDairyEmpCostDtlTO = new WorkDairyEmpCostDtlTO();
        workDairyEmpCostDtlTO.setId(workDairyEmpCostEntity.getId());
        if (CommonUtil.objectNotNull(workDairyEmpCostEntity.getWorkDairyEmpWageEntity())) {
            workDairyEmpCostDtlTO.setWageCostId(workDairyEmpCostEntity.getWorkDairyEmpWageEntity().getId());
        }
        WorkDairyEntity workDairy = workDairyEmpCostEntity.getWorkDairyId();
        if (workDairy != null)
            workDairyEmpCostDtlTO.setWorkDairyId(workDairy.getId());
        ProjCostItemEntity costCode = workDairyEmpCostEntity.getCostId();
        if (costCode != null)
            workDairyEmpCostDtlTO.setCostId(costCode.getId());
        workDairyEmpCostDtlTO.setUsedTime(workDairyEmpCostEntity.getUsedTime());
        workDairyEmpCostDtlTO.setIdleTime(workDairyEmpCostEntity.getIdleTime());
        workDairyEmpCostDtlTO.setOldUsedTime(workDairyEmpCostEntity.getUsedTime());
        workDairyEmpCostDtlTO.setOldIdleTime(workDairyEmpCostEntity.getIdleTime());
        workDairyEmpCostDtlTO.setStatus(workDairyEmpCostEntity.getStatus());

        return workDairyEmpCostDtlTO;
    }

    public static WorkDairyEmpCostEntity convertSubmitCostPOJOToEntity(WorkDairyEmpCostDtlTO workDairyEmpCostDtlTO,
            WorkDairyRepository workDairyRepository, ProjCostItemRepositoryCopy projCostItemRepository) {
        WorkDairyEmpCostEntity workDairyEmpCostEntity = new WorkDairyEmpCostEntity();
        if (CommonUtil.isNonBlankLong(workDairyEmpCostDtlTO.getId())) {
            workDairyEmpCostEntity.setId(workDairyEmpCostDtlTO.getId());
        }

        if (CommonUtil.isNonBlankLong(workDairyEmpCostDtlTO.getWageCostId())) {
            WorkDairyEmpWageEntity workDairyEmpWageEntity = new WorkDairyEmpWageEntity();
            workDairyEmpWageEntity.setId(workDairyEmpCostDtlTO.getWageCostId());
            workDairyEmpCostEntity.setWorkDairyEmpWageEntity(workDairyEmpWageEntity);
        }
        workDairyEmpCostEntity.setWorkDairyId(workDairyRepository.findOne(workDairyEmpCostDtlTO.getWorkDairyId()));

        if (CommonUtil.isNonBlankLong(workDairyEmpCostDtlTO.getCostId())) {
            ProjCostItemEntity projCost = projCostItemRepository.findOne(workDairyEmpCostDtlTO.getCostId());
            workDairyEmpCostEntity.setCostId(projCost);
        }
        workDairyEmpCostEntity.setUsedTime(workDairyEmpCostDtlTO.getUsedTime());
        workDairyEmpCostEntity.setIdleTime(workDairyEmpCostDtlTO.getIdleTime());
        workDairyEmpCostEntity.setIsLatest(Boolean.FALSE);
        workDairyEmpCostEntity.setStatus(workDairyEmpCostDtlTO.getStatus());

        return workDairyEmpCostEntity;
    }

    public static WorkDairyEmpCostEntity convertCostPOJOToEntity(WorkDairyEmpCostDtlTO workDairyEmpCostDtlTO,
            WorkDairyRepository workDairyRepository, ProjCostItemRepositoryCopy projCostItemRepository) {
        WorkDairyEmpCostEntity workDairyEmpCostEntity = new WorkDairyEmpCostEntity();
        if (CommonUtil.isNonBlankLong(workDairyEmpCostDtlTO.getId())) {
            workDairyEmpCostEntity.setId(workDairyEmpCostDtlTO.getId());
        }

        if (CommonUtil.isNonBlankLong(workDairyEmpCostDtlTO.getWageCostId())) {
            WorkDairyEmpWageEntity workDairyEmpWageEntity = new WorkDairyEmpWageEntity();
            workDairyEmpWageEntity.setId(workDairyEmpCostDtlTO.getWageCostId());
            workDairyEmpCostEntity.setWorkDairyEmpWageEntity(workDairyEmpWageEntity);
        }
        workDairyEmpCostEntity.setWorkDairyId(workDairyRepository.findOne(workDairyEmpCostDtlTO.getWorkDairyId()));

        if (CommonUtil.isNonBlankLong(workDairyEmpCostDtlTO.getCostId())) {
            ProjCostItemEntity projCost = projCostItemRepository.findOne(workDairyEmpCostDtlTO.getCostId());
            workDairyEmpCostEntity.setCostId(projCost);
        }
        workDairyEmpCostEntity.setUsedTime(workDairyEmpCostDtlTO.getUsedTime());
        workDairyEmpCostEntity.setIdleTime(workDairyEmpCostDtlTO.getIdleTime());
        workDairyEmpCostEntity.setIsLatest(Boolean.TRUE);
        workDairyEmpCostEntity.setStatus(workDairyEmpCostDtlTO.getStatus());

        return workDairyEmpCostEntity;
    }

    public static void saveEmpWageStatus(WorkDairyTO workDairyTO, List<WorkDairyEmpDtlEntity> workDairyEmpDtlEntities,
            WorkDairyEmpDtlTO workDairyEmpDtlTO, WorkDairyRepository workDairyRepository,
            ProjCostItemRepositoryCopy projCostItemRepository, EmpRegisterRepositoryCopy empRegisterRepository,
            EmpWageRepository empWageRepository) {
        WorkDairyEmpDtlEntity workDairyEmpDtlEntity;
        workDairyEmpDtlTO.setWorkDairyId(workDairyTO.getId());
        workDairyEmpDtlEntity = convertPOJOToEntity(workDairyEmpDtlTO, empRegisterRepository);
        workDairyEmpDtlEntities.add(workDairyEmpDtlEntity);
        for (WorkDairyEmpWageTO workDairyEmpWageTO : workDairyEmpDtlTO.getWorkDairyEmpWageTOs()) {
            if ((TimeManagentStatus.APPROVED.getName().equalsIgnoreCase(workDairyTO.getApprStatus())
                    && !workDairyTO.isClientApproval())
                    && TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName()
                            .equalsIgnoreCase(workDairyEmpWageTO.getApprStatus())) {
                workDairyEmpWageTO.setApprStatus(workDairyTO.getApprStatus());
                populateEmpWorkDariy(workDairyEmpDtlEntity, workDairyEmpWageTO, workDairyRepository,
                        projCostItemRepository, empWageRepository);
            } else if ((TimeManagentStatus.CLIENT_APPROVED.getName().equalsIgnoreCase(workDairyTO.getApprStatus())
                    && workDairyTO.isClientApproval())
                    && TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                            .equalsIgnoreCase(workDairyEmpWageTO.getApprStatus())) {
                workDairyEmpWageTO.setApprStatus(workDairyTO.getApprStatus());
                populateEmpWorkDariy(workDairyEmpDtlEntity, workDairyEmpWageTO, workDairyRepository,
                        projCostItemRepository, empWageRepository);
            } else if ((TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                    .equalsIgnoreCase(workDairyTO.getApprStatus()) && workDairyTO.isClientApproval())
                    && TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName()
                            .equalsIgnoreCase(workDairyEmpWageTO.getApprStatus())) {
                if (workDairyTO.isNewRequired()) {
                    workDairyEmpWageTO.setApprStatus(TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName());
                    workDairyEmpDtlEntity.getWorkDairyEmpWageEntities().add(creatSubmitEmpStatus(workDairyEmpWageTO,
                            workDairyRepository, projCostItemRepository, empWageRepository));
                }
                workDairyEmpWageTO.setApprStatus(TimeManagentStatus.APPROVED.getName());
                populateEmpWorkDariy(workDairyEmpDtlEntity, workDairyEmpWageTO, workDairyRepository,
                        projCostItemRepository, empWageRepository);
            } else if ((TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                    .equalsIgnoreCase(workDairyTO.getApprStatus()) && workDairyTO.isClientApproval())
                    && TimeManagentStatus.SUBMIT_FOR_CLIENT_APPROVAL.getName()
                            .equalsIgnoreCase(workDairyEmpWageTO.getApprStatus())) {
                populateEmpWorkDariy(workDairyEmpDtlEntity, workDairyEmpWageTO, workDairyRepository,
                        projCostItemRepository, empWageRepository);
            } else if (TimeManagentStatus.SUBMIT_FOR_APPROVAL.getName().equalsIgnoreCase(workDairyTO.getApprStatus())) {
                if (CommonUtil.isBlankStr(workDairyEmpWageTO.getApprStatus())) {
                    if (workDairyTO.isNewRequired()) {
                        workDairyEmpDtlEntity.getWorkDairyEmpWageEntities().add(creatSubmitEmpStatus(workDairyEmpWageTO,
                                workDairyRepository, projCostItemRepository, empWageRepository));
                        workDairyEmpWageTO.setUserId(workDairyTO.getInternalApprUserId());
                        workDairyEmpWageTO.setApprStatus(workDairyTO.getApprStatus());
                        populateEmpWorkDariy(workDairyEmpDtlEntity, workDairyEmpWageTO, workDairyRepository,
                                projCostItemRepository, empWageRepository);
                    }
                } else {
                    populateEmpWorkDariy(workDairyEmpDtlEntity, workDairyEmpWageTO, workDairyRepository,
                            projCostItemRepository, empWageRepository);
                }
            } else if (CommonUtil.isBlankStr(workDairyTO.getApprStatus())
                    && CommonUtil.isBlankStr(workDairyEmpWageTO.getApprStatus())) {
                populateEmpWorkDariy(workDairyEmpDtlEntity, workDairyEmpWageTO, workDairyRepository,
                        projCostItemRepository, empWageRepository);
            }
        }
    }

    private static void populateEmpWorkDariy(WorkDairyEmpDtlEntity workDairyEmpDtlEntity,
            WorkDairyEmpWageTO workDairyEmpWageTO, WorkDairyRepository workDairyRepository,
            ProjCostItemRepositoryCopy projCostItemRepository, EmpWageRepository empWageRepository) {
        WorkDairyEmpWageEntity workDairyEmpWageEntity;
        WorkDairyEmpCostEntity workDairyEmpCostEntity;
        workDairyEmpWageEntity = convertWagePOJOToEntity(workDairyEmpWageTO, empWageRepository);
        workDairyEmpWageEntity.setWorkDairyEmpDtlEntity(workDairyEmpDtlEntity);
        workDairyEmpDtlEntity.getWorkDairyEmpWageEntities().add(workDairyEmpWageEntity);

        for (WorkDairyEmpCostDtlTO workDairyUsedCostDtlTO : workDairyEmpWageTO.getWorkDairyEmpCostDtlTOs()) {
            workDairyEmpCostEntity = convertCostPOJOToEntity(workDairyUsedCostDtlTO, workDairyRepository,
                    projCostItemRepository);
            workDairyEmpCostEntity.setApprStatus(workDairyEmpWageTO.getApprStatus());
            workDairyEmpCostEntity.setWorkDairyEmpWageEntity(workDairyEmpWageEntity);
            workDairyEmpWageEntity.getWorkDairyEmpCostEntities().add(workDairyEmpCostEntity);
        }
    }

    private static WorkDairyEmpWageEntity creatSubmitEmpStatus(WorkDairyEmpWageTO workDairyEmpWageTO,
            WorkDairyRepository workDairyRepository, ProjCostItemRepositoryCopy projCostItemRepository,
            EmpWageRepository empWageRepository) {
        WorkDairyEmpWageEntity workDairyEmpWageEntity = null;
        WorkDairyEmpCostEntity workDairyEmpCostEntity = null;
        workDairyEmpWageEntity = convertWagePOJOToEntity(workDairyEmpWageTO, empWageRepository);
        workDairyEmpWageEntity.setId(null);
        if (workDairyEmpWageTO.getApprStatus() != null)
        	workDairyEmpWageEntity.setApprStatus(workDairyEmpWageTO.getApprStatus());
        for (WorkDairyEmpCostDtlTO workDairyUsedCostDtlTO : workDairyEmpWageTO.getWorkDairyEmpCostDtlTOs()) {
            workDairyEmpCostEntity = convertSubmitCostPOJOToEntity(workDairyUsedCostDtlTO, workDairyRepository,
                    projCostItemRepository);
            workDairyEmpCostEntity.setId(null);
            workDairyEmpCostEntity.setWorkDairyEmpWageEntity(workDairyEmpWageEntity);
            workDairyEmpWageEntity.getWorkDairyEmpCostEntities().add(workDairyEmpCostEntity);
        }
        return workDairyEmpWageEntity;
    }

}
