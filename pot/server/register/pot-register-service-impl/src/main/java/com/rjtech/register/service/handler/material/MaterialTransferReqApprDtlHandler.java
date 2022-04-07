package com.rjtech.register.service.handler.material;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.material.dto.MaterialTransferReqApprDtlTO;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.material.model.MaterialTransferReqApprDtlEntity;
import com.rjtech.register.material.model.MaterialTransferReqApprEntity;
import com.rjtech.register.repository.material.MaterialProjRepository;

public class MaterialTransferReqApprDtlHandler {

    public static MaterialTransferReqApprDtlTO convertEntityToPOJO(MaterialTransferReqApprDtlEntity entity) {

        MaterialTransferReqApprDtlTO materialTransferReqApprDtlTO = new MaterialTransferReqApprDtlTO();

        materialTransferReqApprDtlTO.setId(entity.getId());
        materialTransferReqApprDtlTO.setMaterialTransId(entity.getMaterialTransferReqApprEntity().getId());
        MaterialProjDtlEntity materialProjDtlEntity = entity.getMaterialId();
        if (null != materialProjDtlEntity) {
            materialTransferReqApprDtlTO.setMaterialId(materialProjDtlEntity.getId());
        }
        materialTransferReqApprDtlTO.setReqComments(entity.getReqComments());
        materialTransferReqApprDtlTO.setApprComments(entity.getApprComments());
        materialTransferReqApprDtlTO.setApprStatus(entity.getApprStatus());
        materialTransferReqApprDtlTO.setReqDate(CommonUtil.convertDateToString(entity.getReqDate()));
        materialTransferReqApprDtlTO.setApprDate(CommonUtil.convertDateToString(entity.getApprDate()));
        materialTransferReqApprDtlTO.setReqQty(entity.getReqQty());
        materialTransferReqApprDtlTO.setApprQty(entity.getApprQty());
        materialTransferReqApprDtlTO.setStatus(entity.getStatus());
        return materialTransferReqApprDtlTO;
    }

    public static MaterialTransferReqApprDtlEntity convertPOJOToEntity(
            MaterialTransferReqApprDtlTO materialTransferReqApprDtlTO, MaterialProjRepository materialProjRepository) {
        MaterialTransferReqApprDtlEntity entity = new MaterialTransferReqApprDtlEntity();
        if (CommonUtil.isNonBlankLong(materialTransferReqApprDtlTO.getId())) {
            entity.setId(materialTransferReqApprDtlTO.getId());
        }
        MaterialProjDtlEntity materialProjDtlEntity = materialProjRepository
                .findOne(materialTransferReqApprDtlTO.getMaterialId());
        if (null != materialProjDtlEntity) {
            entity.setMaterialId(materialProjDtlEntity);
        }
        if (CommonUtil.isNonBlankLong(materialTransferReqApprDtlTO.getMaterialTransId())) {
            MaterialTransferReqApprEntity materialTransferReqApprEntity = new MaterialTransferReqApprEntity();
            materialTransferReqApprEntity.setId(materialTransferReqApprDtlTO.getMaterialTransId());
            entity.setMaterialTransferReqApprEntity(materialTransferReqApprEntity);
        }
        entity.setReqComments(materialTransferReqApprDtlTO.getReqComments());
        entity.setApprComments(materialTransferReqApprDtlTO.getApprComments());
        entity.setReqDate(CommonUtil.convertStringToDate(materialTransferReqApprDtlTO.getReqDate()));
        entity.setApprDate(CommonUtil.convertStringToDate(materialTransferReqApprDtlTO.getApprDate()));
        entity.setReqQty(materialTransferReqApprDtlTO.getReqQty());
        entity.setApprQty(materialTransferReqApprDtlTO.getApprQty());
        entity.setApprStatus(materialTransferReqApprDtlTO.getApprStatus());
        entity.setStatus(materialTransferReqApprDtlTO.getStatus());
        return entity;
    }
}
