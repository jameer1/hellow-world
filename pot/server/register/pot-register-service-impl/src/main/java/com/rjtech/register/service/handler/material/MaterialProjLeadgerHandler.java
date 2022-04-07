package com.rjtech.register.service.handler.material;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.material.dto.MaterialProjLedgerTO;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.material.model.MaterialProjLedgerEntity;

public class MaterialProjLeadgerHandler {

    public static MaterialProjLedgerTO convertEntityToPOJO(MaterialProjLedgerEntity entity) {
        MaterialProjLedgerTO materialProjLedgerTO = new MaterialProjLedgerTO();

        materialProjLedgerTO.setId(entity.getId());
        MaterialProjDtlEntity materialProjDtlEntity = entity.getMaterialProjDtlEntity();
        if (null != materialProjDtlEntity) {
            materialProjLedgerTO.setRegMaterialId(materialProjDtlEntity.getId());
        }
        materialProjLedgerTO.setOpenBalance(entity.getOpenBalance());
        materialProjLedgerTO.setCredit(entity.getCredit());
        materialProjLedgerTO.setDebit(entity.getDebit());
        materialProjLedgerTO.setAvailableBalance(entity.getAvailableBalance());
        materialProjLedgerTO.setTransit(entity.getTransit());
        materialProjLedgerTO.setTotal(entity.getTotal());
        if (CommonUtil.isNotBlankDate(entity.getEffectiveDate())) {
            materialProjLedgerTO.setEffectiveDate(CommonUtil.convertDateToString(entity.getEffectiveDate()));
        }
        materialProjLedgerTO.setStatus(entity.getStatus());

        return materialProjLedgerTO;
    }
}
