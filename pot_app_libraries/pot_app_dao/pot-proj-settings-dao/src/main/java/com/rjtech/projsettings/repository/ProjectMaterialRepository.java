package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projsettings.model.ProjectMaterialBudgetEntity;

public interface ProjectMaterialRepository extends ProjSettingsBaseRepository<ProjectMaterialBudgetEntity, Long> {

    @Query("SELECT PMD FROM ProjectMaterialBudgetEntity PMD WHERE  PMD.projMstrEntity.projectId=:projId AND PMD.status=:status")
    public List<ProjectMaterialBudgetEntity> findProjectMaterials(@Param("projId") Long projId,
            @Param("status") Integer status);
	
	@Query("SELECT PMD FROM ProjectMaterialBudgetEntity PMD WHERE PMD.projMstrEntity.projectId=:projId AND PMD.status NOT IN (2,3)")
    public List<ProjectMaterialBudgetEntity> findProjectMaterials( @Param("projId") Long projId );

    @Query("SELECT MAP.materialClassId.id, SUM(WDMS.total) " 
            + "FROM " + "WorkDairyMaterialStatusEntity WDMS "
            + "LEFT JOIN WDMS.workDairyMaterialDtlEntity.materialProjDtlEntity MAP "
            + "WHERE WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId = :projId "
            + "AND WDMS.workDairyMaterialDtlEntity.workDairyEntity.reqUserId.status = 1 "
            + "AND (((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = true) "
            + "AND (WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Client Approved')) OR "
            + "((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = false) AND "
            + "(WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Approved'))) "
            + "GROUP BY MAP.materialClassId.id")
    public List<Object[]> getProjectMaterialActualHrs(@Param("projId") ProjMstrEntity projId);
    
    @Query("SELECT T FROM MaterialClassMstrEntity T WHERE  T.clientId = :clientId "
            + "AND T.materialClassMstrEntity IS NULL AND T.status=:status  AND "
            + "T NOT IN (SELECT PMB.materialClassMstrEntity FROM ProjectMaterialBudgetEntity PMB "
            + "WHERE PMB.projMstrEntity = :projId)"
            + "ORDER BY  T.code")
    public List<MaterialClassMstrEntity> getMaterialsForBudget(@Param("projId") ProjMstrEntity projId, 
            @Param("clientId") ClientRegEntity clientId,
            @Param("status") Integer status);
    
    @Query("SELECT PMD FROM ProjectMaterialBudgetEntity PMD WHERE PMD.projMstrEntity.projectId=:projId AND PMD.materialClassMstrEntity.id=:resId")
    public ProjectMaterialBudgetEntity findBy(@Param("projId") Long projId, @Param("resId") Long resId);

    @Modifying
    @Query("UPDATE ProjectMaterialBudgetEntity PMD SET PMD.itemStatus=:itemStatus,PMD.approverUserId.userId=:approverUserId,PMD.originatorUserId.userId=:originatorUserId WHERE PMD.id in :materialIds")
    public void updateApproverDetailsByIds( @Param("materialIds") List<Long> materialIds, @Param("approverUserId") Long approverUserId, @Param("originatorUserId") Long originatorUserId, @Param("itemStatus") String itemStatus );
    
    @Modifying
    @Query("UPDATE ProjectMaterialBudgetEntity PMD SET PMD.itemStatus=:itemStatus,PMD.status=:status WHERE PMD.id in :materialIds")
    public void updateApprovalStatusByIds( @Param("materialIds") List<Long> materialIds, @Param("itemStatus") String itemStatus, @Param("status") Integer status );
    
    @Modifying
    @Query("UPDATE ProjectMaterialBudgetEntity PMD SET PMD.itemStatus=:itemStatus,PMD.returnedByUserId.userId=:returnedByUserId,PMD.isItemReturned=:isItemReturned,PMD.returnedComments=:comments WHERE PMD.id=:materialId")
    public void updateReturnItemDetailsById( @Param("materialId") Long materialId, @Param("returnedByUserId") Long returnedByUserId , @Param("isItemReturned") Integer isItemReturned, @Param("comments") String comments, @Param("itemStatus") String itemStatus );
    
    @Modifying
    @Query("UPDATE ProjectMaterialBudgetEntity PMD SET PMD.itemStatus=:itemStatus WHERE PMD.id=:materialId")
    public void updateBudgetMaterialItemStatusById( @Param("materialId") Long materialId, @Param("itemStatus") String itemStatus );
}
