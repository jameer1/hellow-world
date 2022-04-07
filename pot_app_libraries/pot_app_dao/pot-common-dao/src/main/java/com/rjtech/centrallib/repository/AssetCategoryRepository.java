package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.AssetCategoryMstrEntity;

public interface AssetCategoryRepository extends CentralLibRepository<AssetCategoryMstrEntity, Long> {

    @Query("SELECT T FROM AssetCategoryMstrEntity T WHERE  (T.clientId.clientId =:clientId OR T.clientId IS NULL) AND T.assetCategoryMstrEntity IS NULL AND T.status=:status  ORDER BY  T.code")
    public List<AssetCategoryMstrEntity> findAllAssetCategories(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM AssetCategoryMstrEntity T WHERE  T.clientId.clientId =:clientId  AND T.assetCategoryMstrEntity IS NULL AND T.status=:status  ORDER BY  T.code")
    public List<AssetCategoryMstrEntity> findAllAssetCategoriesForClient(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE AssetCategoryMstrEntity T  SET T.status=:status  where T.id in :assetIds or T.assetCategoryMstrEntity.id in :assetIds")
    void deactivateAssetCategories(@Param("assetIds") List<Long> assetIds, @Param("status") Integer status);
}
