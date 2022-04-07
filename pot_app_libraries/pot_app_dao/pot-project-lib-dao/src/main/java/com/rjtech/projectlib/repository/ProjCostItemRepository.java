package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjCostItemEntity;

@Repository
public interface ProjCostItemRepository extends ProjLibBaseRepository<ProjCostItemEntity, Long> {
    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE  projCost.projId.projectId =:projId AND projCost.projCostItemEntity IS NULL AND projCost.status=:status ORDER BY projCost.code")
    public List<ProjCostItemEntity> findCostDetails(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE  projCost.projId.projectId =:projId AND projCost.id=:costId AND projCost.status=:status  ORDER BY  projCost.code")
    public List<ProjCostItemEntity> findCostDetailsById(@Param("projId") Long projId, @Param("costId") Long costId,
            @Param("status") Integer status);

    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE  projCost.projId.projectId =:projId AND projCost.item=1 AND projCost.status=:status  ORDER BY  projCost.code")
    public List<ProjCostItemEntity> findCostCodeItems(@Param("projId") Long projId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProjCostItemEntity cost  SET cost.status=:status  where cost.id in :costIds or cost.projCostItemEntity.id in :costIds")
    void deactivateProjCostDetails(@Param("costIds") List<Long> costIds, @Param("status") Integer status);

    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE  projCost.projId.projectId =:projId   ORDER BY  projCost.code")
    public List<ProjCostItemEntity> findAllCostCodeItems(@Param("projId") Long projId);

    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE  projCost.projId.projectId in :projIds AND projCost.projCostItemEntity IS NULL AND projCost.status=:status  ORDER BY  projCost.code")
    public List<ProjCostItemEntity> findMultiProjCostDetails(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE  projCost.projId.projectId in :projIds AND projCost.status=:status  ORDER BY  projCost.code")
    public List<ProjCostItemEntity> findMultiProjCostItems(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

    @Query(value = "SELECT CCS_ID from proj_cost_code_item WHERE CCS_EPM_ID = :projId AND CCS_STATUS = :status AND CCS_ID in("
            //+ "SELECT PCMD_CCS_ID FROM pre_contracts_material_dtl UNION "
            //+ "SELECT PCP_CCS_ID FROM pre_contracts_plant_dtl UNION "
            //+ "SELECT PCS_CCS_ID FROM pre_contracts_service_dtl UNION "
            + "SELECT TSWD_CCS_ID FROM time_sheet_emp_date_wise UNION "
            + "SELECT TSDE_CCS_ID FROM time_sheet_emp_expenses UNION "
            + "SELECT WDCC_CCS_ID FROM work_dairy_crew_cost_codes UNION "
            + "SELECT WDCC_CCS_ID FROM work_dairy_cost_codes UNION "
            // + "SELECT PJL_CCS_ID FROM proj_leave_type_mstr UNION " // Removed cost code from proj_leave_type_mstr as part of entity cleanup
            + "SELECT PJCS_CCS_ID FROM project_cost_stmt UNION "
            + "SELECT SOW_CCS_ID FROM scope_of_work)", nativeQuery = true)
    public List<Long> getListOfProjCostDetailsUsedInOtherModules(@Param("projId") Long projId,
            @Param("status") Integer status);
    
    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE projCost.projId.projectId =:projId AND projCost.projCostItemEntity IS NULL AND projCost.status IN (0,1) ORDER BY projCost.code")
    public List<ProjCostItemEntity> findCostDetailsByProjId( @Param("projId") Long projId );
    
    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE  projCost.projId.projectId =:projId AND projCost.id=:costId AND projCost.status IN (0,1)  ORDER BY  projCost.code")
    public List<ProjCostItemEntity> findCostDetailsById(@Param("projId") Long projId, @Param("costId") Long costId);
}
