package com.rjtech.procurement.service.handler;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.procurement.dto.PurchaseOrderDetailsTO;
import com.rjtech.procurement.model.PurchaseOrderDetailsEntity;
import com.rjtech.procurement.repository.PurchaseOrderDetailsRepository;

public class PurchaseOrderDetailsHandler {

    private PurchaseOrderDetailsHandler() {

    }

    public static PurchaseOrderDetailsEntity convertPOJOToEntity(PurchaseOrderDetailsTO poDetailsTO,
            PurchaseOrderDetailsRepository poDetailRepository) {
        PurchaseOrderDetailsEntity poDetailsEntity;
        if (CommonUtil.isNonBlankLong(poDetailsTO.getId())) {
            poDetailsEntity = poDetailRepository.findOne(poDetailsTO.getId());
        } else {
            poDetailsEntity = new PurchaseOrderDetailsEntity();
        }
        poDetailsEntity.setAcceptedBy(poDetailsTO.getAcceptedBy());
        poDetailsEntity.setDesc(poDetailsTO.getDesc());
        poDetailsEntity.setIssuedBy(poDetailsTO.getIssuedBy());
        poDetailsEntity.setIssuerDesignation(poDetailsTO.getIssuerDesignation());
        poDetailsEntity.setIssuerName(poDetailsTO.getIssuerName());
        poDetailsEntity.setIssuerSign(poDetailsTO.getIssuerSign());
        poDetailsEntity.setVendorDesignation(poDetailsTO.getVendorDesignation());
        poDetailsEntity.setVendorName(poDetailsTO.getVendorName());
        poDetailsEntity.setVendorSign(poDetailsTO.getVendorSign());
        poDetailsEntity.setIssuedManPowerQuantity(poDetailsTO.getIssuedManPowerQuantity());
        poDetailsEntity.setIssuedMaterialsQuantity(poDetailsTO.getIssuedMaterialsQuantity());
        poDetailsEntity.setIssuedPlantsQuantity(poDetailsTO.getIssuedPlantsQuantity());
        poDetailsEntity.setIssuedServicesQuantity(poDetailsTO.getIssuedServicesQuantity());
        poDetailsEntity.setIssuedSOWQuantity(poDetailsTO.getIssuedSOWQuantity());
        return poDetailsEntity;
    }

    public static PurchaseOrderDetailsTO convertEntityToPOJO(PurchaseOrderDetailsEntity poDetailsEntity) {
        PurchaseOrderDetailsTO poDetailsTO = new PurchaseOrderDetailsTO();
        poDetailsTO.setAcceptedBy(poDetailsEntity.getAcceptedBy());
        poDetailsTO.setDesc(poDetailsEntity.getDesc());
        poDetailsTO.setId(poDetailsEntity.getId());
        poDetailsTO.setIssuedBy(poDetailsEntity.getIssuedBy());
        poDetailsTO.setIssuerDesignation(poDetailsEntity.getIssuerDesignation());
        poDetailsTO.setIssuerName(poDetailsEntity.getIssuerName());
        poDetailsTO.setIssuerSign(poDetailsEntity.getIssuerSign());
        poDetailsTO.setVendorDesignation(poDetailsEntity.getVendorDesignation());
        poDetailsTO.setVendorName(poDetailsEntity.getVendorName());
        poDetailsTO.setVendorSign(poDetailsEntity.getVendorSign());
        poDetailsTO.setIssuedManPowerQuantity(poDetailsEntity.getIssuedManPowerQuantity());
        poDetailsTO.setIssuedMaterialsQuantity(poDetailsEntity.getIssuedMaterialsQuantity());
        poDetailsTO.setIssuedPlantsQuantity(poDetailsEntity.getIssuedPlantsQuantity());
        poDetailsTO.setIssuedServicesQuantity(poDetailsEntity.getIssuedServicesQuantity());
        poDetailsTO.setIssuedSOWQuantity(poDetailsEntity.getIssuedSOWQuantity());
        return poDetailsTO;
    }

}
