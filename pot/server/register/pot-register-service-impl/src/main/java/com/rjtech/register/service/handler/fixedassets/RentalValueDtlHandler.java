package com.rjtech.register.service.handler.fixedassets;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.RentalValueDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.model.RentalValueDtlEntity;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;

public class RentalValueDtlHandler {

    public static RentalValueDtlTO convertEntityToPOJO(RentalValueDtlEntity entity) {

        RentalValueDtlTO rentalValueDtlTO = new RentalValueDtlTO();
        rentalValueDtlTO.setId(entity.getId());
        rentalValueDtlTO.setEffectiveDate(CommonUtil.convertDateToString(entity.getEffectiveDate()));
        rentalValueDtlTO.setAssetCurrentLifeSataus(entity.getAssetCurrentLifeSataus());
        rentalValueDtlTO.setOwenerShipStatus(entity.getOwenerShipStatus());
        rentalValueDtlTO.setRevenueCollectionCycle(entity.getRevenueCollectionCycle());
        rentalValueDtlTO.setFixedOrRentIncome(entity.getFixedOrRentIncome());
        rentalValueDtlTO.setEstimatedRentPerCycle(entity.getEstimatedRentPerCycle());
        rentalValueDtlTO.setApplicableRevenue(entity.getApplicableRevenue());
        ClientRegEntity clientRegEntity = entity.getClientId();
        if (null != clientRegEntity) {
            rentalValueDtlTO.setClientId(clientRegEntity.getClientId());
        }

        FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = entity.getFixedAssetsRegisterDtlEntity();

        if (null != fixedAssetsRegisterDtlEntity) {
            rentalValueDtlTO.setFixedAssetid(fixedAssetsRegisterDtlEntity.getFixedAssetid());
        }
        return rentalValueDtlTO;
    }

    public static RentalValueDtlEntity convertPOJOToEntity(RentalValueDtlEntity entity,
            RentalValueDtlTO rentalValueDtlTO, FixedAssetsRegisterRepository fixedAssetsRegisterRepository) {
        if (CommonUtil.isNonBlankLong(rentalValueDtlTO.getId())) {
            entity.setId(rentalValueDtlTO.getId());
        }

        entity.setEffectiveDate(CommonUtil.convertStringToDate(rentalValueDtlTO.getEffectiveDate()));
        entity.setAssetCurrentLifeSataus(rentalValueDtlTO.getAssetCurrentLifeSataus());
        entity.setOwenerShipStatus(rentalValueDtlTO.getOwenerShipStatus());
        entity.setRevenueCollectionCycle(rentalValueDtlTO.getRevenueCollectionCycle());
        entity.setFixedOrRentIncome(rentalValueDtlTO.getFixedOrRentIncome());
        entity.setEstimatedRentPerCycle(rentalValueDtlTO.getEstimatedRentPerCycle());
        entity.setApplicableRevenue(rentalValueDtlTO.getApplicableRevenue());
        entity.setStatus(rentalValueDtlTO.getStatus());
        if (CommonUtil.isNonBlankLong(rentalValueDtlTO.getFixedAssetid())) {
            FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity = fixedAssetsRegisterRepository
                    .findOne(rentalValueDtlTO.getFixedAssetid());
            entity.setFixedAssetsRegisterDtlEntity(fixedAssetsRegisterDtlEntity);
        }

        return entity;

    }

}
