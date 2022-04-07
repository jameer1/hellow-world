package com.rjtech.register.service.handler.material;

import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.material.dto.MaterialProjDocketTO;
import com.rjtech.register.material.model.MaterialProjDocketEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.repository.material.CopyProjStoreStockRepository;
import com.rjtech.register.utils.RegisterCommonUtils;

public class MaterialProjDocketHandler {

    public static MaterialProjDocketTO convertEntityToPOJO(MaterialProjDocketEntity entity) {
        MaterialProjDocketTO materialProjDocketTO = new MaterialProjDocketTO();
        
        materialProjDocketTO.setId(entity.getId());
        ProjMstrEntity fromProjId = entity.getFromProjId();
        ProjMstrEntity toProjId = entity.getToProjId();
        materialProjDocketTO.setProjdocketNum(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() 
                + "-" 
                + fromProjId.getCode().toUpperCase()
                + "-"
                + "SCH"
                + "-"
                + "PD"
                + "-"
                + toProjId.getCode().toUpperCase()
                + "-"
                + entity.getId());
        
        if (CommonUtil.isNotBlankDate(entity.getDocketDate())) {
            materialProjDocketTO.setProjdocketDate(CommonUtil.convertDateToString(entity.getDocketDate()));
        }
        materialProjDocketTO.getFromProjLabelkeyTO().setId(entity.getFromProjId().getProjectId());
        materialProjDocketTO.getFromProjLabelkeyTO().setName(entity.getFromProjId().getProjName());
        if (CommonUtil.objectNotNull(entity.getOriginStockId())) {
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_ID,
                    String.valueOf(entity.getOriginStockId().getId()));
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_CODE,
                    entity.getOriginStockCode());
        } else {
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_ID, "0");

        }
        if (CommonUtil.objectNotNull(entity.getOriginProjStockId())) {
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.PROJ_STOCK_SPM_ID,
                    String.valueOf(entity.getOriginProjStockId().getId()));
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.PROJ_STOCK_SPM_CODE,
                    entity.getOriginProjStockCode());
        } else {
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.PROJ_STOCK_SPM_ID,
                    "0");

        }
        materialProjDocketTO.getToProjLabelkeyTO().setId(toProjId.getProjectId());
        materialProjDocketTO.getToProjLabelkeyTO().setName(toProjId.getProjName());
        if (CommonUtil.objectNotNull(entity.getToStockId())) {
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_ID,
                    String.valueOf(entity.getToStockId().getId()));
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_CODE,
                    entity.getToStockCode());
        } else {
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_ID, "0");

        }
        if (CommonUtil.objectNotNull(entity.getToProjStockId())) {
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.PROJ_STOCK_SPM_ID,
                    String.valueOf(entity.getToProjStockId().getId()));
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.PROJ_STOCK_SPM_CODE,
                    entity.getToProjStockCode());
        } else {
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.PROJ_STOCK_SPM_ID, "0");

        }

        materialProjDocketTO.setSourceType(entity.getSourceType());

        materialProjDocketTO.getIssuedByLabelkeyTO().setId(entity.getIssuedEmpEntity().getId());
        materialProjDocketTO.getIssuedByLabelkeyTO().setCode(entity.getIssuedEmpEntity().getCode());
        materialProjDocketTO.getIssuedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.FIRST_NAME,
                entity.getIssuedEmpEntity().getFirstName());
        materialProjDocketTO.getIssuedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.LAST_NAME,
                entity.getIssuedEmpEntity().getLastName());
        if (CommonUtil.objectNotNull(entity.getIssuedEmpEntity().getEmpClassMstrEntity())) {
            materialProjDocketTO.getIssuedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.CLASS_TYPE,
                    entity.getIssuedEmpEntity().getEmpClassMstrEntity().getId().toString());
        }
        if (CommonUtil.objectNotNull(entity.getIssuedEmpEntity().getCompanyMstrEntity())) {
            materialProjDocketTO.getIssuedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.CMP_ID,
                    entity.getIssuedEmpEntity().getCompanyMstrEntity().getId().toString());
        }

        materialProjDocketTO.getReceivedByLabelkeyTO().setId(entity.getReceivedEmpEntity().getId());
        materialProjDocketTO.getReceivedByLabelkeyTO().setCode(entity.getReceivedEmpEntity().getCode());
        materialProjDocketTO.getReceivedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.FIRST_NAME,
                entity.getReceivedEmpEntity().getFirstName());
        materialProjDocketTO.getReceivedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.LAST_NAME,
                entity.getReceivedEmpEntity().getLastName());
        if (CommonUtil.objectNotNull(entity.getReceivedEmpEntity().getEmpClassMstrEntity())) {
            materialProjDocketTO.getReceivedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.CLASS_TYPE,
                    entity.getReceivedEmpEntity().getEmpClassMstrEntity().getId().toString());
        }

        if (CommonUtil.isNonBlankLong(entity.getReceivedEmpEntity().getCompanyMstrEntity().getId())) {
            materialProjDocketTO.getReceivedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.CMP_ID,
                    entity.getReceivedEmpEntity().getCompanyMstrEntity().getId().toString());
        }

        LabelKeyTO notifyLabelKeyTO = new LabelKeyTO();
        notifyLabelKeyTO.setId(entity.getNotifyId());
        notifyLabelKeyTO.setCode(entity.getNotifyCode());
        materialProjDocketTO.setNotifyLabelKeyTO(notifyLabelKeyTO);
        materialProjDocketTO.setApprStatus(entity.getApprStatus());
        materialProjDocketTO.setStatus(entity.getStatus());
        return materialProjDocketTO;
    }

    public static MaterialProjDocketEntity convertPOJOToEntity(MaterialProjDocketTO materialProjDocketTO,
            EPSProjRepository epsProjRepository, StockRepository stockRepository,
            CopyProjStoreStockRepository projStoreStockRepository, EmpRegisterRepository empRegisterRepository) {
        MaterialProjDocketEntity entity = new MaterialProjDocketEntity();

        if (CommonUtil.isNonBlankLong(materialProjDocketTO.getId())) {
            entity.setId(materialProjDocketTO.getId());
        }
        entity.setDocketNum(materialProjDocketTO.getProjdocketNum());

        if (CommonUtil.isNotBlankStr(materialProjDocketTO.getProjdocketNum())) {
            entity.setDocketStatus(RegisterCommonUtils.NEW);

        }
        entity.setDocketDate(CommonUtil.convertStringToDate(materialProjDocketTO.getProjdocketDate()));
        ProjMstrEntity projFromMstrEntity = epsProjRepository
                .findOne(materialProjDocketTO.getFromProjLabelkeyTO().getId());
        if (null != projFromMstrEntity) {
            entity.setFromProjId(projFromMstrEntity);
        }

        if (CommonUtil.isNotBlankStr(
                materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().get(CommonConstants.STOCK_SM_CODE))) {
            StockMstrEntity stockMstrEntity = stockRepository.findOne(Long.valueOf(materialProjDocketTO
                    .getFromProjLabelkeyTO().getDisplayNamesMap().get(CommonConstants.STOCK_SM_ID)));
            if (null != stockMstrEntity) {
                entity.setOriginStockId(stockMstrEntity);
            }
            entity.setOriginStockCode(materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap()
                    .get(CommonConstants.STOCK_SM_CODE));
            entity.setOriginStockCodeCategory(materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap()
                    .get(CommonConstants.STOCK_SM_CODE_CATEGORY));
        }

        if (CommonUtil.isNotBlankStr(materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap()
                .get(CommonConstants.PROJ_STOCK_SPM_ID))) {
            ProjStoreStockMstrEntity projStoreStockMstrEntity = projStoreStockRepository
                    .findOne(Long.valueOf(materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap()
                            .get(CommonConstants.PROJ_STOCK_SPM_ID)));
            if (null != projStoreStockMstrEntity) {
                entity.setOriginProjStockId(projStoreStockMstrEntity);
            }
            entity.setOriginProjStockCode(materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap()
                    .get(CommonConstants.PROJ_STOCK_SPM_CODE));
            entity.setOriginProjStockCodeCategory(materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap()
                    .get(CommonConstants.PROJ_STOCK_SPM_CATEGORY));
        }

        ProjMstrEntity projToMstrEntity = epsProjRepository.findOne(materialProjDocketTO.getToProjLabelkeyTO().getId());
        if (null != projToMstrEntity) {
            entity.setToProjId(projToMstrEntity);
        }

        if (CommonUtil.isNotBlankStr(
                materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().get(CommonConstants.STOCK_SM_CODE))) {
            StockMstrEntity stockToMstrEntity = stockRepository.findOne(Long.valueOf(
                    materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().get(CommonConstants.STOCK_SM_ID)));
            if (null != stockToMstrEntity) {
                entity.setToStockId(stockToMstrEntity);
            }
            entity.setToStockCode(
                    materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().get(CommonConstants.STOCK_SM_CODE));
            entity.setToStockCodeCategory(materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap()
                    .get(CommonConstants.STOCK_SM_CODE_CATEGORY));
        }

        if (CommonUtil.isNotBlankStr(materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap()
                .get(CommonConstants.PROJ_STOCK_SPM_ID))) {
            ProjStoreStockMstrEntity projStoreToStockMstrEntity = projStoreStockRepository
                    .findOne(Long.valueOf(materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap()
                            .get(CommonConstants.PROJ_STOCK_SPM_ID)));
            if (null != projStoreToStockMstrEntity) {
                entity.setToProjStockId(projStoreToStockMstrEntity);
            }
            entity.setToProjStockCode(materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap()
                    .get(CommonConstants.PROJ_STOCK_SPM_CODE));
            entity.setToProjStockCodeCategory(materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap()
                    .get(CommonConstants.PROJ_STOCK_SPM_CATEGORY));
        }

        entity.setSourceType(materialProjDocketTO.getSourceType());

        if (CommonUtil.isNonBlankLong(materialProjDocketTO.getIssuedByLabelkeyTO().getId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                    .findOne(materialProjDocketTO.getIssuedByLabelkeyTO().getId());
            entity.setIssuedEmpEntity(empRegisterDtlEntity);
        }

        if (CommonUtil.isNonBlankLong(materialProjDocketTO.getReceivedByLabelkeyTO().getId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                    .findOne(materialProjDocketTO.getReceivedByLabelkeyTO().getId());
            entity.setReceivedEmpEntity(empRegisterDtlEntity);
        }

        if (CommonUtil.objectNotNull(materialProjDocketTO.getNotifyLabelKeyTO())
                && CommonUtil.isNonBlankLong(materialProjDocketTO.getNotifyLabelKeyTO().getId())) {
            entity.setNotifyId(materialProjDocketTO.getNotifyLabelKeyTO().getId());
            entity.setNotifyCode(materialProjDocketTO.getNotifyLabelKeyTO().getCode());
        }

        entity.setApprStatus(materialProjDocketTO.getApprStatus());
        entity.setStatus(materialProjDocketTO.getStatus());

        return entity;
    }
}
