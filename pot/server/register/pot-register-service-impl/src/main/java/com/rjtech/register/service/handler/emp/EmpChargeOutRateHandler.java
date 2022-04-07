package com.rjtech.register.service.handler.emp;

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
//import com.rjtech.projsettings.model.ProjGeneralMstrEntityCopy;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.emp.dto.EmpChargeOutRateTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.emp.resp.EmpChargeOutRateResp;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class EmpChargeOutRateHandler {

    public static EmpChargeOutRateTO convertEntityToPOJO(EmpChargeOutRateEntity entity) {

        EmpChargeOutRateTO empChargeOutRateTO = new EmpChargeOutRateTO();
        empChargeOutRateTO.setId(entity.getId());

        if (CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity())) {
            empChargeOutRateTO.setEmpProjId(entity.getEmpProjRigisterEntity().getId());
        }

        if (CommonUtil.isNotBlankDate(entity.getFromDate())) {
            empChargeOutRateTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getToDate())) {
            empChargeOutRateTO.setToDate(CommonUtil.convertDateToString(entity.getToDate()));
        }
        empChargeOutRateTO.setLatest(entity.isLatest());

        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            empChargeOutRateTO.setEmpRegId(entity.getEmpRegisterDtlEntity().getId());
        }
        if (CommonUtil.objectNotNull(entity.getMeasurmentMstrEntity())) {
            empChargeOutRateTO.setMeasureId(entity.getMeasurmentMstrEntity().getId());
        }
        if (CommonUtil.objectNotNull(entity.getProjGeneralMstrEntity())) {
            empChargeOutRateTO.setProjGenId(entity.getProjGeneralMstrEntity().getId());

            empChargeOutRateTO.setProjCurrency( entity.getProjGeneralMstrEntity().getCurrency() );
        }
        if (CommonUtil.objectNotNull(entity.getLeaveCostItemEntity())) {
            empChargeOutRateTO.setLeaveCostItemId(entity.getLeaveCostItemEntity().getId());
            empChargeOutRateTO.setLeaveCostItemCode(entity.getLeaveCostItemEntity().getCode());
        }
        if (CommonUtil.objectNotNull(entity.getMobCostItemEntity())) {
            empChargeOutRateTO.setMobCostItemId(entity.getMobCostItemEntity().getId());
            empChargeOutRateTO.setMobCostItemCode(entity.getMobCostItemEntity().getCode());
        }
        if (CommonUtil.objectNotNull(entity.getDeMobCostItemEntity())) {
            empChargeOutRateTO.setDeMobCostItemId(entity.getDeMobCostItemEntity().getId());
            empChargeOutRateTO.setDeMobCostItemCode(entity.getDeMobCostItemEntity().getCode());
        }
        empChargeOutRateTO.setNormalRate(entity.getNormalRate());
        empChargeOutRateTO.setIdleRate(entity.getIdleRate());
        empChargeOutRateTO.setPaidLeaveRate(entity.getPaidLeaveRate());
        empChargeOutRateTO.setMobRate(entity.getMobRate());
        empChargeOutRateTO.setDeMobRate(entity.getDeMobRate());
        empChargeOutRateTO.setComments(entity.getComments());
        if (CommonUtil.objectNotNull(entity.getEmpProjRigisterEntity())) {
            empChargeOutRateTO.setProjEmpRegisterTO(EmpEnrollmentDtlHandler.convertMobilizationDateEntityTO(entity.getEmpProjRigisterEntity()));
        }

        empChargeOutRateTO.setStatus(entity.getStatus());

        return empChargeOutRateTO;
    }

    public static EmpChargeOutRateEntity convertPOJOToEntity(EmpChargeOutRateTO empChargeOutRateTO,
            EmpRegisterRepository empRegisterRepository, MeasurementRepository measurementRepository,
            ProjGeneralRepositoryCopy projGeneralRepository, ProjCostItemRepositoryCopy projCostItemRepository,
            EmpProjRegisterRepository empProjRegisterRepository) {
        EmpChargeOutRateEntity entity = new EmpChargeOutRateEntity();
        if (CommonUtil.isNotBlankStr(empChargeOutRateTO.getFromDate())) {
            entity.setFromDate(CommonUtil.convertStringToDate(empChargeOutRateTO.getFromDate()));
        }
        if (CommonUtil.isNotBlankStr(empChargeOutRateTO.getToDate())) {
            entity.setToDate(CommonUtil.convertStringToDate(empChargeOutRateTO.getToDate()));
        }
        if (CommonUtil.objectNotNull(empChargeOutRateTO.getProjEmpRegisterTO())) {
            if (CommonUtil.isNonBlankLong(empChargeOutRateTO.getProjEmpRegisterTO().getEmpId())) {
                EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                        .findOne(empChargeOutRateTO.getProjEmpRegisterTO().getEmpId());
                entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
            }
            if (CommonUtil.isNonBlankLong(empChargeOutRateTO.getProjEmpRegisterTO().getEmpId())) {
                EmpProjRigisterEntity empProjRigisterEntity = empProjRegisterRepository
                        .findOne(empChargeOutRateTO.getProjEmpRegisterTO().getId());
                entity.setEmpProjRigisterEntity(empProjRigisterEntity);
            }
        }
        if (CommonUtil.isNonBlankLong(empChargeOutRateTO.getMeasureId())) {
            MeasurmentMstrEntity measurmentMstrEntity = measurementRepository
                    .findOne(empChargeOutRateTO.getMeasureId());
            entity.setMeasurmentMstrEntity(measurmentMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(empChargeOutRateTO.getProjGenId())) {
            ProjGeneralMstrEntity projGeneralMstrEntity = projGeneralRepository
                    .findOne(empChargeOutRateTO.getProjGenId());
            entity.setProjGeneralMstrEntity(projGeneralMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(empChargeOutRateTO.getLeaveCostItemId())) {
            ProjCostItemEntity projCostItemEntity = projCostItemRepository
                    .findOne(empChargeOutRateTO.getLeaveCostItemId());
            entity.setLeaveCostItemEntity(projCostItemEntity);
        }
        if (CommonUtil.isNonBlankLong(empChargeOutRateTO.getMobCostItemId())) {
            ProjCostItemEntity projCostItemEntity = projCostItemRepository
                    .findOne(empChargeOutRateTO.getMobCostItemId());
            entity.setMobCostItemEntity(projCostItemEntity);
        }
        if (CommonUtil.isNonBlankLong(empChargeOutRateTO.getDeMobCostItemId())) {
            ProjCostItemEntity projCostItemEntity = projCostItemRepository
                    .findOne(empChargeOutRateTO.getDeMobCostItemId());
            entity.setDeMobCostItemEntity(projCostItemEntity);
        }
        entity.setNormalRate(empChargeOutRateTO.getNormalRate());
        entity.setIdleRate(empChargeOutRateTO.getIdleRate());
        entity.setPaidLeaveRate(empChargeOutRateTO.getPaidLeaveRate());
        entity.setMobRate(empChargeOutRateTO.getMobRate());
        entity.setDeMobRate(empChargeOutRateTO.getDeMobRate());
        entity.setComments(empChargeOutRateTO.getComments());
        entity.setLatest(empChargeOutRateTO.isLatest());
        entity.setStatus(empChargeOutRateTO.getStatus());
        return entity;
    }

    public static void populateChargeOutRateTO(EmpChargeOutRateResp empChargeOutRateResp,
            ProjEmpRegisterTO projEmpRegisterTO) {
        EmpChargeOutRateTO empChargeOutRateTO = new EmpChargeOutRateTO();
        empChargeOutRateTO.setEmpRegId(projEmpRegisterTO.getEmpId());
        empChargeOutRateTO.setEmpProjId(projEmpRegisterTO.getId());
        empChargeOutRateTO.setFromDate(projEmpRegisterTO.getMobilaizationDate());
        empChargeOutRateTO.setProjEmpRegisterTO(projEmpRegisterTO);
        empChargeOutRateTO.setLatest(true);
        empChargeOutRateResp.getEmpChargeOutRateTOs().add(empChargeOutRateTO);
    }
}
