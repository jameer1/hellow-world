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


import com.rjtech.projectlib.model.WorkDairyProgressDtlEntityCopy1;

@Repository
public interface ProjSowTotalActualRepositoryCopy extends JpaRepository<WorkDairyProgressDtlEntityCopy1, Long> {



    @Query("SELECT WDPR.sowId.projCostItemEntity.id, WDPR.value, "
    		+ " CASE WHEN WDPR.sowId.projSORItemEntity.totalRateIfNoSubCategory IS NULL THEN "
    		+ " (COALESCE(WDPR.sowId.projSORItemEntity.labourRate,0) + COALESCE(WDPR.sowId.projSORItemEntity.materialRate)"
            + " + COALESCE(WDPR.sowId.projSORItemEntity.plantRate) + COALESCE(WDPR.sowId.projSORItemEntity.othersRate)) "
            + " ELSE WDPR.sowId.projSORItemEntity.totalRateIfNoSubCategory END "
            + " FROM WorkDairyProgressDtlEntityCopy1 WDPR WHERE WDPR.workDairyId.projId.projectId =:projId"
            + " AND ((lower(WDPR.apprStatus) = 'approved' AND WDPR.workDairyId.clientApproval=0) "
            + " OR (lower(WDPR.apprStatus) = 'client approved' AND WDPR.workDairyId.clientApproval=1)) ")
    List<Object[]> getEarnedValuePerCostCode(@Param("projId") Long projId);
    
   

  

}
