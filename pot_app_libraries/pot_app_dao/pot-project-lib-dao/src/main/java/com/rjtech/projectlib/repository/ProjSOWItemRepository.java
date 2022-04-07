package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projectlib.model.ProjSOWItemEntity;

@Repository
public interface ProjSOWItemRepository extends ProjLibBaseRepository<ProjSOWItemEntity, Long> {
    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE  SOW.projMstrEntity.projectId=:projId  AND SOW.status=:status  ORDER BY  SOW.code")
    public List<ProjSOWItemEntity> findSOWDetails(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE  SOW.projMstrEntity.projectId=:projId AND SOW.id=:sowId AND SOW.status=:status  ORDER BY  SOW.code")
    public List<ProjSOWItemEntity> findSOWDetailsById(@Param("projId") Long projId, @Param("sowId") Long sowId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProjSOWItemEntity SOW  SET SOW.status=:status  where SOW.id in :sowIds")
    void deactivateSOWDetails(@Param("sowIds") List<Long> sowIds, @Param("status") Integer status);

    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE SOW.projMstrEntity.projectId=:projId AND SOW.item=1 AND SOW.status=:status ORDER BY SOW.code")
    public List<ProjSOWItemEntity> findSOWItems(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE  SOW.projMstrEntity.projectId in :projIds AND SOW.item=1 AND SOW.status=:status ORDER BY SOW.code ")
    public List<ProjSOWItemEntity> findMultiProjSOWDetails(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE SOW.projMstrEntity = :projId "
            + "AND SOW.projCostItemEntity IN :costCodeIds "
            + "AND SOW NOT IN (SELECT WDPE.sowId FROM WorkDairyProgressDtlEntity WDPE "
            + "where WDPE.workDairyId.id = :workDairy)")
    public List<ProjSOWItemEntity> getSowByCostCode(@Param("projId") ProjMstrEntity projId,
            @Param("costCodeIds") List<ProjCostItemEntity> costCodeIds,
            @Param("workDairy") Long workDairy);

    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE SOW.projMstrEntity = :projId "
            + "AND SOW.projCostItemEntity NOT IN :costCodeIds "
            + "AND SOW NOT IN (SELECT WDPE.sowId FROM WorkDairyProgressDtlEntity WDPE "
            + "where WDPE.workDairyId.id = :workDairy)")
    public List<ProjSOWItemEntity> getSowExceptCostCode(@Param("projId") ProjMstrEntity projId,
            @Param("costCodeIds") List<ProjCostItemEntity> costCodeIds,
            @Param("workDairy") Long workDairy);
    
    @Modifying
    @Query("UPDATE ProjSOWItemEntity SOW  SET SOW.status=:status  where SOW.projSOEItemEntity.id=:soeItemId")
    public void updateStatusBySOEId( @Param("soeItemId") Long soeItemId, @Param("status") Integer status );
    
    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE SOW.projMstrEntity.projectId=:projId AND SOW.status IN (0,1) ORDER BY SOW.code")
    public List<ProjSOWItemEntity> findSOWDetails( @Param("projId") Long projId );
    
    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE SOW.projMstrEntity.projectId=:projId AND SOW.item=1 AND SOW.status IN (0,1) ORDER BY SOW.code")
    public List<ProjSOWItemEntity> findSOWItems(@Param("projId") Long projId);

}
