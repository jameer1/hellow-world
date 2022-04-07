package com.rjtech.projsettings.repository.copy;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;

//import com.rjtech.projsettings.model.copy.ProjCostStmtDtlEntityCopy;

public interface ProjCostStatementsRepositoryCopy extends JpaRepository<ProjCostStmtDtlEntity, Long> {

    @Query("SELECT PJCS.projCostItemEntity.id,PJCS.projCostItemEntity.code,PJCS.projCostItemEntity.name,"
            + "(COALESCE(PCB.total,COALESCE(PCB1.total,0))- COALESCE(PCB2.total,0))  FROM ProjCostStmtDtlEntity PJCS "
            + "LEFT JOIN ProjCostBudgetEntity PCB ON PCB.projCostStmtDtlEntity.id = PJCS.id AND  PCB.budgetType=1 "
            + "LEFT JOIN ProjCostBudgetEntity PCB1 ON PCB1.projCostStmtDtlEntity.id = PJCS.id AND  PCB1.budgetType=2 "
            + "LEFT JOIN ProjCostBudgetEntity PCB2 ON PCB2.projCostStmtDtlEntity.id = PJCS.id AND  PCB2.budgetType=3 "
            + "WHERE PJCS.projCostItemEntity.id in :projCostIds ORDER BY PJCS.projCostItemEntity.name")
    List<Object[]> getPrecontractCostCodeSummary(@Param("projCostIds") Set<Long> projCostIds);

}
