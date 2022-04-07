package com.rjtech.projsettings.service.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.constans.ProjEstimateConstants;
import com.rjtech.projsettings.dto.ProjCostBudgetTO;
import com.rjtech.projsettings.dto.ProjCostStmtDtlTO;
import com.rjtech.projsettings.model.ProjCostBudgetEntity;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;

public class ProjCostBudgetHandler {
    public static ProjCostBudgetTO convertEntityToPOJO(ProjCostBudgetEntity entity) {
        ProjCostBudgetTO projCostBudgetTO = new ProjCostBudgetTO();

        projCostBudgetTO.setId(entity.getId());
        projCostBudgetTO.setBudgetType(entity.getBudgetType());
        projCostBudgetTO.setLabourCost(entity.getLabourCost());
        projCostBudgetTO.setMaterialCost(entity.getMaterialCost());
        projCostBudgetTO.setOtherCost(entity.getOtherCost());
        projCostBudgetTO.setPlantCost(entity.getPlantCost());
        projCostBudgetTO.setTotal(entity.getTotal());

        projCostBudgetTO.setStatus(entity.getStatus());
        return projCostBudgetTO;

    }

    public static List<ProjCostBudgetEntity> populateCostBudgetEntities(ProjCostStmtDtlTO projCostStmtDtlTO,
            ProjCostStmtDtlEntity projCostentity) {
        List<ProjCostBudgetEntity> projCostBudgetEntites = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        if (projCostStmtDtlTO.getOriginalCostBudget() != null) {
            ProjCostBudgetEntity entity = convertPOJOToEntity(projCostStmtDtlTO.getOriginalCostBudget(), projCostentity,
                    1L);
            projCostBudgetEntites.add(entity);
        }

        if (projCostStmtDtlTO.getRevisedCostBudget() != null) {
            ProjCostBudgetEntity entity = convertPOJOToEntity(projCostStmtDtlTO.getRevisedCostBudget(), projCostentity,
                    2L);
            projCostBudgetEntites.add(entity);
        }
        System.out.println("projCostStmtDtlTO.getEstimateCompleteBudget() " + projCostStmtDtlTO.getEstimateCompleteBudget());
        System.out.println("projCostStmtDtlTO.getEstimateType() " + projCostStmtDtlTO.getEstimateType());
        if (projCostStmtDtlTO.getEstimateCompleteBudget() != null && ProjEstimateConstants.NEW_ESTIMATE.toLowerCase()
        		.equalsIgnoreCase(projCostStmtDtlTO.getEstimateType())) {
                //.equalsIgnoreCase(projCostStmtDtlTO.getEstimateCompleteBudget().getEstimateType())) {
            ProjCostBudgetEntity entity = convertPOJOToEntity(projCostStmtDtlTO.getEstimateCompleteBudget(),
                    projCostentity, 4L);
            projCostBudgetEntites.add(entity);
        }

        return projCostBudgetEntites;

    }

    public static ProjCostBudgetEntity convertPOJOToEntity(ProjCostBudgetTO projCostBudgetTO,
            ProjCostStmtDtlEntity projCostentity, long budgetType) {
        ProjCostBudgetEntity entity = new ProjCostBudgetEntity();

        if (CommonUtil.isNonBlankLong(projCostBudgetTO.getId())) {
            entity.setId(projCostBudgetTO.getId());
        }

        entity.setBudgetType(projCostBudgetTO.getBudgetType() != null ? projCostBudgetTO.getBudgetType() : budgetType);
        entity.setLabourCost(
                projCostBudgetTO.getLabourCost() != null ? projCostBudgetTO.getLabourCost() : new BigDecimal(0));
        entity.setMaterialCost(
                projCostBudgetTO.getMaterialCost() != null ? projCostBudgetTO.getMaterialCost() : new BigDecimal(0));
        entity.setOtherCost(
                projCostBudgetTO.getOtherCost() != null ? projCostBudgetTO.getOtherCost() : new BigDecimal(0));
        entity.setPlantCost(
                projCostBudgetTO.getPlantCost() != null ? projCostBudgetTO.getPlantCost() : new BigDecimal(0));
        entity.setTotal(entity.getLabourCost().add(entity.getMaterialCost()).add(entity.getOtherCost())
                .add(entity.getPlantCost()));

        entity.setStatus(projCostBudgetTO.getStatus());
        entity.setProjCostStmtDtlEntity(projCostentity);

        return entity;
    }
}
