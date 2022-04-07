package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;

public interface ProjCostStatementsRepository extends ProjSettingsBaseRepository<ProjCostStmtDtlEntity, Long> {

	@Query("SELECT PJCS FROM ProjCostStmtDtlEntity PJCS WHERE PJCS.projMstrEntity.projectId=:projId AND PJCS.status=:status AND PJCS.status=0")
    public List<ProjCostStmtDtlEntity> findProjCostStatements(@Param("projId") Long projId, @Param("status") Integer status);
	
	@Query("SELECT PJCS FROM ProjCostStmtDtlEntity PJCS WHERE PJCS.projMstrEntity.projectId=:projId AND PJCS.status NOT IN (2,3)")
    public List<ProjCostStmtDtlEntity> findProjCostStatements(@Param("projId") Long projId);

    @Query("SELECT PJCS From ProjCostStmtDtlEntity PJCS JOIN PJCS.projCostBudgetEntites eps2 WHERE eps2.labourCost >0 AND PJCS.projMstrEntity.projectId=:projId  AND PJCS.status=:status")
    public List<ProjCostStmtDtlEntity> findProjManpowerCostStatements(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PJCS From ProjCostStmtDtlEntity PJCS JOIN PJCS.projCostBudgetEntites eps2 WHERE eps2.materialCost >0 AND PJCS.projMstrEntity.projectId=:projId  AND PJCS.status=:status")
    public List<ProjCostStmtDtlEntity> findProjMaterialCostStatements(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PJCS From ProjCostStmtDtlEntity PJCS JOIN PJCS.projCostBudgetEntites eps2 WHERE eps2.plantCost >0 AND PJCS.projMstrEntity.projectId=:projId  AND PJCS.status=:status")
    public List<ProjCostStmtDtlEntity> findProjPlantCostStatements(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PJCS From ProjCostStmtDtlEntity PJCS JOIN PJCS.projCostBudgetEntites eps2 WHERE eps2.otherCost >0 AND PJCS.projMstrEntity.projectId=:projId  AND PJCS.status=:status")
    public List<ProjCostStmtDtlEntity> findProjServiceCostStatements(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE  projCost.projId.projectId =:projId AND projCost.projCostItemEntity IS NULL AND projCost.status=:status  ORDER BY  projCost.code")
    public List<ProjCostItemEntity> findProjCostDetails(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query(value = "SELECT PCB.budgetType,sum(PCB.labourCost),sum(PCB.plantCost),sum(PCB.materialCost),sum(PCB.otherCost) FROM ProjCostStmtDtlEntity PJCS JOIN PJCS.projCostBudgetEntites PCB WHERE  PJCS.projMstrEntity.projectId=:projId AND PJCS.status=:status GROUP BY PJCS.id,PCB.budgetType ORDER BY PCB.budgetType")
    public List<Object[]> findProjCostStatementsSummary(@Param("projId") Long projId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProjCostStmtDtlEntity cost  SET cost.status=:status  where cost.id in :projCostStmtsIds")
    void deactivateCostStmtDetails(@Param("projCostStmtsIds") List<Long> projCostStmtsIds,
            @Param("status") Integer status);

    @Query("SELECT PJCS.projCostItemEntity.id, to_char(MIN(PJCS.projCostItemEntity.startDate), 'DD-MON-YYYY'), "
            + "to_char(MAX(PJCS.projCostItemEntity.finishDate),'DD-MON-YYYY'), SUM(CASE WHEN PCB.budgetType = 1 THEN COALESCE(PCB.total,0) END),"
            + "SUM(CASE WHEN PCB.budgetType=2 THEN COALESCE(PCB.total,0) END), SUM(CASE WHEN PCB.budgetType=3 THEN COALESCE(PCB.total,0) END),"
            + "SUM(CASE WHEN PCB.budgetType=4 THEN COALESCE(PCB.total,0) END) From ProjCostStmtDtlEntity PJCS "
            + "JOIN PJCS.projCostBudgetEntites PCB WHERE PJCS.projMstrEntity.projectId = :projId AND PJCS.status =:status GROUP BY PJCS.projCostItemEntity.id")
    public List<Object[]> getProjScheduleCostCodes(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT DISTINCT PJCS, PGV.currency FROM ProjCostStmtDtlEntity PJCS JOIN PJCS.projCostBudgetEntites PCB "
            + " LEFT JOIN ProjGeneralMstrEntity PGV ON PGV.projMstrEntity = PJCS.projMstrEntity AND PGV.status=1 AND PGV.isLatest='Y' "
            + " WHERE PJCS.projMstrEntity.projectId in (:projIds) AND PCB.budgetType in (:types) order by PJCS.projMstrEntity ")
    public List<Object[]> getBudgetsByType(@Param("projIds") List<Long> projIds, @Param("types") List<Long> types);
    
    @Modifying
    @Query("UPDATE ProjCostStmtDtlEntity PCSD SET PCSD.itemStatus=:itemStatus,PCSD.approverUserId.userId=:approverUserId,PCSD.originatorUserId.userId=:originatorUserId WHERE PCSD.id in :costStatementIds")
    public void updateApproverDetailsByIds( @Param("costStatementIds") List<Long> costStatementIds, @Param("approverUserId") Long approverUserId, @Param("originatorUserId") Long originatorUserId, @Param("itemStatus") String itemStatus );
    
    @Modifying
    @Query("UPDATE ProjCostStmtDtlEntity PCSD SET PCSD.itemStatus=:itemStatus,PCSD.status=:status WHERE PCSD.id in :costStatementIds")
    public void updateApprovalStatusByIds( @Param("costStatementIds") List<Long> costStatementIds, @Param("itemStatus") String itemStatus, @Param("status") Integer status );
    
    @Modifying
    @Query("UPDATE ProjCostStmtDtlEntity PCSD SET PCSD.itemStatus=:itemStatus,PCSD.returnedByUserId.userId=:returnedByUserId,PCSD.isItemReturned=:isItemReturned,PCSD.returnedComments=:comments WHERE PCSD.id=:costStatementId")
    public void updateReturnItemDetailsById( @Param("costStatementId") Long costStatementId, @Param("returnedByUserId") Long returnedByUserId , @Param("isItemReturned") Integer isItemReturned, @Param("comments") String comments, @Param("itemStatus") String itemStatus );
    
    @Modifying
    @Query("UPDATE ProjCostStmtDtlEntity PCSD SET PCSD.itemStatus=:itemStatus WHERE PCSD.id=:costStatementId")
    public void updateBudgetCostStmtItemStatusById( @Param("costStatementId") Long costStatementId, @Param("itemStatus") String itemStatus );

}
