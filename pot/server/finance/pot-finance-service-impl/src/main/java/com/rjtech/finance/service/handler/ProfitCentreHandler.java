
package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.ProfitCentreTO;
import com.rjtech.finance.model.ProfitCentreEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class ProfitCentreHandler {

    public static ProfitCentreTO populateProfitCentre(ProfitCentreEntity profitCentreEntity) {
        ProfitCentreTO profitCentreTO = new ProfitCentreTO();

        if (CommonUtil.objectNotNull(profitCentreEntity)) {
            profitCentreTO.setId(profitCentreEntity.getId());
            profitCentreTO.setCode(profitCentreEntity.getCode());
            profitCentreTO.setName(profitCentreEntity.getName());
            profitCentreTO.setVendorPayCycle(profitCentreEntity.getVendorPayCycle());
            profitCentreTO.setCyclePeriodStartFrom(profitCentreEntity.getCyclePeriodStartFrom());
            profitCentreTO.setCycleDueDate(profitCentreEntity.getCycleDueDate());
            ProfitCentreEntity parent = profitCentreEntity.getProfitCentreEntity();
            if (parent != null)
                profitCentreTO.setParentId(parent.getId());
            if (profitCentreEntity.isItem()) {
                profitCentreTO.setItem(true);
            } else {
                profitCentreTO.setItem(false);
            }
            if (CommonUtil.objectNotNull(profitCentreEntity)) {
                profitCentreTO.setParentName(profitCentreEntity.getName());
            }

            if (CommonUtil.isListHasData(profitCentreEntity.getChildEntities()))

            {
                profitCentreTO.getChildProfitCentreTOs().addAll(addChilds(profitCentreEntity, profitCentreTO));
            }
            profitCentreTO.setStatus(profitCentreEntity.getStatus());
        }
        return profitCentreTO;
    }

    public static List<ProfitCentreTO> addChilds(ProfitCentreEntity profitCentreEntity, ProfitCentreTO profitCentreTO) {

        List<ProfitCentreTO> childProfitCentreTOs = new ArrayList<ProfitCentreTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        if (CommonUtil.isNonBlankLong(profitCentreTO.getParentId())) {

            for (ProfitCentreEntity childEntity : profitCentreEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childProfitCentreTOs.add(populateProfitCentre(childEntity));
                }
            }
        } else {
            ProfitCentreEntity parent = profitCentreEntity.getProfitCentreEntity();
            if (parent != null)
                profitCentreTO.setParentId(parent.getId());
            for (ProfitCentreEntity childEntity : profitCentreEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childProfitCentreTOs.add(populateProfitCentre(childEntity));
                }

            }
        }
        return childProfitCentreTOs;
    }

    public static List<ProfitCentreEntity> populateEntitiesFromPOJO(List<ProfitCentreTO> profitCentreTOs,
            ClientRegRepository clientRegRepository) {
        List<ProfitCentreEntity> profitCentreEntities = new ArrayList<ProfitCentreEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProfitCentreEntity entity = null;

        for (ProfitCentreTO profitCentreTO : profitCentreTOs) {
            entity = new ProfitCentreEntity();
            convertPOJOToEntity(entity, profitCentreTO, clientRegRepository);
            profitCentreEntities.add(entity);
        }

        return profitCentreEntities;
    }

    public static ProfitCentreEntity convertPOJOToEntity(ProfitCentreEntity profitCentreEntity,
            ProfitCentreTO profitCentreTO, ClientRegRepository clientRegRepository) {

        if (CommonUtil.isNonBlankLong(profitCentreTO.getId())) {
            profitCentreEntity.setId(profitCentreTO.getId());
        }
        profitCentreEntity.setCode(profitCentreTO.getCode());
        profitCentreEntity.setClientId(clientRegRepository.findOne(AppUserUtils.getClientId()));
        profitCentreEntity.setName(profitCentreTO.getName());
        profitCentreEntity.setItem(profitCentreTO.isItem());
        profitCentreEntity.setVendorPayCycle(profitCentreTO.getVendorPayCycle());
        profitCentreEntity.setCyclePeriodStartFrom(profitCentreTO.getCyclePeriodStartFrom());
        profitCentreEntity.setCycleDueDate(profitCentreTO.getCycleDueDate());
        if (CommonUtil.isNonBlankLong(profitCentreTO.getParentId())) {
            ProfitCentreEntity parentEntity = new ProfitCentreEntity();
            parentEntity.setId(profitCentreTO.getParentId());
            profitCentreEntity.setProfitCentreEntity(parentEntity);
        }
        profitCentreEntity.setStatus(profitCentreTO.getStatus());
        ProfitCentreEntity chaildEntity = null;

        for (ProfitCentreTO childTO : profitCentreTO.getChildProfitCentreTOs()) {
            chaildEntity = new ProfitCentreEntity();
            chaildEntity.setProfitCentreEntity(profitCentreEntity);
            profitCentreEntity.getChildEntities().add(convertPOJOToEntity(chaildEntity, childTO, clientRegRepository));
        }
        return profitCentreEntity;
    }

}