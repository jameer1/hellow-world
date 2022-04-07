package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.AssetMaintenanceCategoryMstrEntity;

public interface AssetMaintenanceCategoryRepository
        extends CentralLibRepository<AssetMaintenanceCategoryMstrEntity, Long> {

    @Query("SELECT T FROM AssetMaintenanceCategoryMstrEntity T WHERE  (T.clientId.clientId=:clientId OR T.clientId IS NULL) AND T.assetMaintenanceCategoryMstrEntity IS NULL AND T.status=:status  ORDER BY  T.code")
    public List<AssetMaintenanceCategoryMstrEntity> findAllAssetMaintenanceCategories(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM AssetMaintenanceCategoryMstrEntity T WHERE  T.clientId.clientId=:clientId AND T.assetMaintenanceCategoryMstrEntity IS NULL AND T.status=:status  ORDER BY  T.code")
    public List<AssetMaintenanceCategoryMstrEntity> findAllAssetMaintenanceCategoriesForClient(
            @Param("clientId") Long clientId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE AssetMaintenanceCategoryMstrEntity T  SET T.status=:status  where T.id in :assetIds or T.assetMaintenanceCategoryMstrEntity.id in :assetIds")
    void deactivateAssetMaintenanceCategories(@Param("assetIds") List<Long> assetIds, @Param("status") Integer status);
}
