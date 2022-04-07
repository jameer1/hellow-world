package com.rjtech.projectlib.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.dto.TotalActualTO;
import com.rjtech.timemanagement.workdairy.model.WorkDairyProgressDtlEntity;
//import com.rjtech.workdairy.WorkDairyProgressDtlEntityCopy;

@Repository
public interface ProjSowTotalActualRepository extends JpaRepository<WorkDairyProgressDtlEntity, Long> {

    @Query("SELECT WDPD.sowId.id, SUM(WDPD.value) FROM WorkDairyProgressDtlEntity WDPD "
            + "WHERE lower(WDPD.apprStatus) = 'approved'  AND  WDPD.sowId.projMstrEntity.projectId = :projId  GROUP  BY WDPD.sowId")
    List<Object[]> findTotalActualQuantitiesByProjId(@Param("projId") Long projId);

    @Query("SELECT PCSW.sowId.id, SUM(PCSW.quantity) FROM  PrecontractSowDtlEntity PCSW WHERE "
            + "PCSW.preContractEntity.contarctStageStatus = 'Purchase Order' AND "
            + "PCSW.preContractEntity.purchaseOrderStatus = 'PO Issued' AND "
            + "PCSW.sowId.projMstrEntity.projectId = :projId GROUP BY PCSW.sowId")

    List<Object[]> findPrecontractTotalActualQuantitiesByProjId(@Param("projId") Long projId);

    @Query("SELECT WDPD.sowId.id, SUM(WDPD.value) FROM WorkDairyProgressDtlEntity WDPD WHERE WDPD.sowId.projMstrEntity.projectId = :projId "
            + "AND WDPD.workDairyId.workDairyDate < :startDate AND ((lower(WDPD.apprStatus)='approved' AND WDPD.workDairyId.clientApproval = 0) "
            + "OR (lower(WDPD.apprStatus) = 'client approved' and WDPD.workDairyId.clientApproval = 1)) GROUP BY WDPD.sowId.id")
    List<Object[]> getActualQuantitiesBeforeStartDate(@Param("projId") Long projId, @Param("startDate") Date startDate);

    @Query("SELECT WDPD.sowId.id, SUM(WDPD.value) FROM WorkDairyProgressDtlEntity WDPD WHERE WDPD.sowId.projMstrEntity.projectId=:projId "
            + "AND WDPD.workDairyId.workDairyDate BETWEEN :startDate AND :endDate AND ((lower(WDPD.apprStatus) = 'approved' AND "
            + "WDPD.workDairyId.clientApproval=0) OR (lower(WDPD.apprStatus)='client approved' AND WDPD.workDairyId.clientApproval=1)) GROUP BY WDPD.sowId.id")
    List<Object[]> getActualQuantitiesBetweenStartDateAndDate(@Param("projId") Long projId,
            @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT WDPD.sowId.id, SUM(WDPD.value) FROM WorkDairyProgressDtlEntity WDPD WHERE WDPD.sowId.projMstrEntity.projectId = :projId "
            + "AND WDPD.workDairyId.purCode=:contractCode AND WDPD.workDairyId.workDairyDate < :startDate AND ((lower(WDPD.apprStatus)='approved' "
            + "AND WDPD.workDairyId.clientApproval = 0) OR (lower(WDPD.apprStatus) = 'client approved' and WDPD.workDairyId.clientApproval = 1)) "
            + "GROUP BY WDPD.sowId.id")
    List<Object[]> getSubContractActualQuantitiesBeforeStartDate(@Param("projId") Long projId,
            @Param("startDate") Date startDate, @Param("contractCode") String contractCode);

    @Query("SELECT WDPD.sowId.id, SUM(WDPD.value) FROM WorkDairyProgressDtlEntity WDPD WHERE WDPD.sowId.projMstrEntity.projectId=:projId "
            + "AND WDPD.workDairyId.purCode=:contractCode AND WDPD.workDairyId.workDairyDate BETWEEN :startDate AND :endDate AND "
            + "((lower(WDPD.apprStatus) = 'approved' AND WDPD.workDairyId.clientApproval=0) OR (lower(WDPD.apprStatus)='client approved' AND "
            + "WDPD.workDairyId.clientApproval=1)) GROUP BY WDPD.sowId.id")
    List<Object[]> getSubContractActualQuantitiesBetweenStartDateAndDate(@Param("projId") Long projId,
            @Param("startDate") Date startDate, @Param("endDate") Date endDate,
            @Param("contractCode") String contractCode);

    @Query("SELECT wdpr.sowId.id, MIN(wdpr.workDairyId.workDairyDate) FROM WorkDairyProgressDtlEntity wdpr WHERE wdpr.workDairyId.projId.projectId =:projId AND "
            + "((lower(wdpr.workDairyId.apprStatus) = 'approved' AND wdpr.workDairyId.clientApproval=0) "
            + "OR (lower(wdpr.workDairyId.apprStatus) = 'client approved' AND wdpr.workDairyId.clientApproval=1)) GROUP BY wdpr.sowId.id")
    List<Object[]> sowWorkDairyMinDate(@Param("projId") Long projId);

    @Query("SELECT wdpr.sowId.id, wdpr.workDairyId.workDairyDate, SUM(wdpr.value) FROM WorkDairyProgressDtlEntity wdpr WHERE "
            + "wdpr.workDairyId.projId.projectId =:projId AND "
            + "((lower(wdpr.apprStatus) = 'approved' AND wdpr.workDairyId.clientApproval=0) "
            + "OR (lower(wdpr.apprStatus) = 'client approved' AND wdpr.workDairyId.clientApproval=1)) GROUP BY wdpr.sowId.id,wdpr.workDairyId.workDairyDate")
    List<Object[]> getSowActualHrsForSchedules(@Param("projId") Long projId);

    @Query("SELECT WDPR.sowId.projCostItemEntity.id, WDPR.value, "
    		+ " CASE WHEN WDPR.sowId.projSORItemEntity.totalRateIfNoSubCategory IS NULL THEN "
    		+ " (COALESCE(WDPR.sowId.projSORItemEntity.labourRate,0) + COALESCE(WDPR.sowId.projSORItemEntity.materialRate)"
            + " + COALESCE(WDPR.sowId.projSORItemEntity.plantRate) + COALESCE(WDPR.sowId.projSORItemEntity.othersRate)) "
            + " ELSE WDPR.sowId.projSORItemEntity.totalRateIfNoSubCategory END "
            + " FROM WorkDairyProgressDtlEntity WDPR WHERE WDPR.workDairyId.projId.projectId =:projId"
            + " AND ((lower(WDPR.apprStatus) = 'approved' AND WDPR.workDairyId.clientApproval=0) "
            + " OR (lower(WDPR.apprStatus) = 'client approved' AND WDPR.workDairyId.clientApproval=1)) ")
    List<Object[]> getEarnedValuePerCostCode(@Param("projId") Long projId);

    @Query("SELECT WDPR.workDairyId.projId.projectId, WDPR.sowId.projCostItemEntity.id, "
            + " WDPR.value, COALESCE(WDPR.sowId.projSORItemEntity.quantity,0), COALESCE(WDPR.sowId.projSORItemEntity.manPowerHrs,0) "
            + " FROM WorkDairyProgressDtlEntity WDPR WHERE WDPR.workDairyId.projId.projectId in (:projIds)"
            + " AND ((lower(WDPR.apprStatus) = 'approved' AND WDPR.workDairyId.clientApproval=0) "
            + " OR (lower(WDPR.apprStatus) = 'client approved' AND WDPR.workDairyId.clientApproval=1)) ")
    List<Object[]> getEarnedValueForManpower(@Param("projIds") List<Long> projIds);

    @Query("SELECT WDPR.sowId.projCostItemEntity.id, WDPR.workDairyId.workDairyDate, "
            + " WDPR.value, CASE WHEN WDPR.sowId.projSORItemEntity.totalRateIfNoSubCategory IS NULL THEN "
    		+ " (COALESCE(WDPR.sowId.projSORItemEntity.labourRate,0) + COALESCE(WDPR.sowId.projSORItemEntity.materialRate)"
            + " + COALESCE(WDPR.sowId.projSORItemEntity.plantRate) + COALESCE(WDPR.sowId.projSORItemEntity.othersRate)) "
    		+ " ELSE WDPR.sowId.projSORItemEntity.totalRateIfNoSubCategory END "
            + " FROM WorkDairyProgressDtlEntity WDPR WHERE WDPR.workDairyId.projId.projectId in (:projIds) AND "
            + " WDPR.workDairyId.workDairyDate between :fromDate and :toDate "
            + " AND ((lower(WDPR.apprStatus) = 'approved' AND WDPR.workDairyId.clientApproval=0) "
            + " OR (lower(WDPR.apprStatus) = 'client approved' AND WDPR.workDairyId.clientApproval=1)) ")
    List<Object[]> getDailyEarnedValuePerCostCode(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T.sowId.tangibleClassificationEntity.id, T.workDairyId.workDairyDate, SUM(COALESCE(T.value, 0)) FROM WorkDairyProgressDtlEntity T "
    		+ "WHERE T.workDairyId.projId.projectId=:projId "
    		+ "AND T.sowId.tangibleClassificationEntity.id in (:resIds) "
    		+ "AND (( T.workDairyId.clientApproval=0 AND lower(T.apprStatus)='approved' AND lower(T.workDairyId.apprStatus)='approved') "
            + "OR (T.workDairyId.clientApproval=1 AND lower(T.apprStatus)='client approved' AND lower(T.workDairyId.apprStatus)='client approved')) "
            + "GROUP BY T.sowId.tangibleClassificationEntity.id, T.workDairyId.workDairyDate "
            + "ORDER BY T.workDairyId.workDairyDate")
    List<Object[]> getActualTangibles(@Param("projId") Long projId, @Param("resIds") List<Long> resIds);
    
    
    @Query("SELECT WDPR.workDairyId.projId,WDPR.sowId.projCostItemEntity,WDPR.workDairyId.workDairyDate,PGV.currency "
            + " FROM WorkDairyProgressDtlEntity WDPR "
            + " LEFT JOIN ProjGeneralMstrEntity PGV ON PGV.projMstrEntity = WDPR.workDairyId.projId AND PGV.status=1 AND PGV.isLatest='Y' "
            +" WHERE WDPR.workDairyId.projId.projectId in (:projIds) AND "
            + " WDPR.workDairyId.workDairyDate between :fromDate and :toDate "
            + " AND ((lower(WDPR.apprStatus) = 'approved' AND WDPR.workDairyId.clientApproval=0) "
            + " OR (lower(WDPR.apprStatus) = 'client approved' AND WDPR.workDairyId.clientApproval=1)) ")
    List<Object[]> getDailyEarnedValue(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    default Map<Long, TotalActualTO> findTotalActualQuantities(Long projId) {

        Map<Long, TotalActualTO> map = new HashMap<>();

        List<Object[]> queryResult = findTotalActualQuantitiesByProjId(projId);
        calculateResult(queryResult, map);

        queryResult = findPrecontractTotalActualQuantitiesByProjId(projId);
        calculateResult(queryResult, map);

        return map;
    }

    default void calculateResult(List<Object[]> result, Map<Long, TotalActualTO> map) {
        Long sowid = null;
        Long totalCost = null;
        for (Object[] obj : result) {
            sowid = (Long) obj[0];
            if (obj[1] instanceof Double) {
                totalCost = ((Double) obj[1]).longValue();
            } else {
                totalCost = (Long) obj[1];
            }

            if (map.containsKey(sowid)) {
                TotalActualTO actualTO = map.get(sowid);
                actualTO.setActualQuantity(actualTO.getActualQuantity() + totalCost);
            } else {
                TotalActualTO toObj = new TotalActualTO();
                toObj.setId(sowid);
                toObj.setActualQuantity(totalCost);
                map.put(sowid, toObj);
            }
        }

    }

    Logger log = LoggerFactory.getLogger(ProjSowTotalActualRepository.class);

    default Map<Long, Double> findManpowerEarnedValueByProj(List<Long> projIds) {
        Map<Long, Double> map = new HashMap<>();
        List<Object[]> resp = getEarnedValueForManpower(projIds);
        resp.forEach(r -> {
            if ((BigDecimal) r[3] != null && r[2] != null && (Long) r[4] != null) {
                // Earned Value = (SOR  Item Manhours/SOR  Item Qty) * SOW Qty
                log.info("value {}, totalQty {} manhrs {} ", r[2], r[3], r[4]);
                double totalQty = ((BigDecimal) r[3]).doubleValue();
                double value = (Double) r[2];
                double manhours = ((Long) r[4]).doubleValue();
                map.merge((Long) r[0], (manhours / totalQty) * value, Double::sum);
            }
        });
        return map;
    }

}
