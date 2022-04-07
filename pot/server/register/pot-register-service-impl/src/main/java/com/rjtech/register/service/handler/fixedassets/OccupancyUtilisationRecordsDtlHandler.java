package com.rjtech.register.service.handler.fixedassets;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.OccupancyUtilisationRecordsDtlTO;
import com.rjtech.register.fixedassets.model.OccupancyUtilisationRecordsDtlEntity;
import com.rjtech.register.fixedassets.model.SubAssetDtlEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class OccupancyUtilisationRecordsDtlHandler {

    public static OccupancyUtilisationRecordsDtlTO convertEntityToPOJO(
            OccupancyUtilisationRecordsDtlEntity occupancyUtilisationRecordsDtlEntity) {

        OccupancyUtilisationRecordsDtlTO occupancyUtilisationRecordsDtlTO = new OccupancyUtilisationRecordsDtlTO();
        occupancyUtilisationRecordsDtlTO.setId(occupancyUtilisationRecordsDtlEntity.getId());
        occupancyUtilisationRecordsDtlTO.setPastYear(
                CommonUtil.convertDateToDDMMYYYYString(occupancyUtilisationRecordsDtlEntity.getPastYear()));
        occupancyUtilisationRecordsDtlTO.setUnit(occupancyUtilisationRecordsDtlEntity.getUnit());
        occupancyUtilisationRecordsDtlTO.setOccupied(occupancyUtilisationRecordsDtlEntity.getOccupied());
        occupancyUtilisationRecordsDtlTO.setVacant(occupancyUtilisationRecordsDtlEntity.getVacant());
        occupancyUtilisationRecordsDtlTO.setUnderRepair(occupancyUtilisationRecordsDtlEntity.getUnderRepair());
        occupancyUtilisationRecordsDtlTO.setTotal(occupancyUtilisationRecordsDtlEntity.getTotal());

        occupancyUtilisationRecordsDtlTO
                .setClientId(occupancyUtilisationRecordsDtlEntity.getClientRegMstrEntity().getClientId());
        occupancyUtilisationRecordsDtlTO
                .setSubid(occupancyUtilisationRecordsDtlEntity.getSubAssetDtlEntity().getSubid());

        return occupancyUtilisationRecordsDtlTO;
    }

    public static OccupancyUtilisationRecordsDtlEntity convertPOJOToEntity(
            OccupancyUtilisationRecordsDtlEntity occupancyUtilisationRecordsDtlEntity,
            OccupancyUtilisationRecordsDtlTO occupancyUtilisationRecordsDtlTO) {

        if (CommonUtil.objectNotNull(occupancyUtilisationRecordsDtlTO)
                && CommonUtil.isNonBlankLong(occupancyUtilisationRecordsDtlTO.getId())) {
            occupancyUtilisationRecordsDtlEntity.setId(occupancyUtilisationRecordsDtlTO.getId());
        }

        occupancyUtilisationRecordsDtlEntity
                .setPastYear(CommonUtil.convertStringToDate(occupancyUtilisationRecordsDtlTO.getPastYear()));
        occupancyUtilisationRecordsDtlEntity.setUnit(occupancyUtilisationRecordsDtlTO.getUnit());
        occupancyUtilisationRecordsDtlEntity.setOccupied(occupancyUtilisationRecordsDtlTO.getOccupied());
        occupancyUtilisationRecordsDtlEntity.setVacant(occupancyUtilisationRecordsDtlTO.getVacant());
        occupancyUtilisationRecordsDtlEntity.setUnderRepair(occupancyUtilisationRecordsDtlTO.getUnderRepair());
        occupancyUtilisationRecordsDtlEntity.setTotal(occupancyUtilisationRecordsDtlTO.getTotal());

        occupancyUtilisationRecordsDtlEntity.setStatus(occupancyUtilisationRecordsDtlTO.getStatus());
        if (CommonUtil.isNonBlankLong(AppUserUtils.getClientId())) {
            ClientRegEntity clientRegMstrEntity = new ClientRegEntity();
            clientRegMstrEntity.setClientId(AppUserUtils.getClientId());
            occupancyUtilisationRecordsDtlEntity.setClientRegMstrEntity(clientRegMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(occupancyUtilisationRecordsDtlTO.getSubid())) {
            SubAssetDtlEntity subAssetDtlEntity = new SubAssetDtlEntity();
            subAssetDtlEntity.setSubid(occupancyUtilisationRecordsDtlTO.getSubid());
            occupancyUtilisationRecordsDtlEntity.setSubAssetDtlEntity(subAssetDtlEntity);
        }

        return occupancyUtilisationRecordsDtlEntity;
    }

}
