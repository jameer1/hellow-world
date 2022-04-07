package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;

public interface ProjStoreStockRepository extends ProjLibBaseRepository<ProjStoreStockMstrEntity, Long> {

    @Query("SELECT SPM FROM ProjStoreStockMstrEntity SPM  WHERE  SPM.projMstrEntity.projectId=:projId  AND  SPM.status=:status ORDER BY SPM.code")
    List<ProjStoreStockMstrEntity> findProjStoreStocks(@Param("projId") Long projId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProjStoreStockMstrEntity SPM  SET SPM.status=:status  where SPM.id in :projStoreStockIds")
    void deactivateProjStoreStocks(@Param("projStoreStockIds") List<Long> projStoreStockIds,
            @Param("status") Integer status);

    @Query("SELECT SPM FROM ProjStoreStockMstrEntity SPM  WHERE   SPM.projMstrEntity.projectId=:projId  ORDER BY SPM.code")
    List<ProjStoreStockMstrEntity> findAllProjStoreStocks(@Param("projId") Long projId);

    @Query("SELECT SPM FROM ProjStoreStockMstrEntity SPM  WHERE  SPM.projMstrEntity.projectId in :projIds  AND  SPM.status=:status ORDER BY SPM.code")
    List<ProjStoreStockMstrEntity> findMultipleProjsStoreList(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);
}