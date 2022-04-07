package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.AssetCostValueStatusDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;
@Repository
public interface AssetCostValueStatusRepository extends RegisterBaseRepository<AssetCostValueStatusDtlEntity, Long> {
    
    @Query("SELECT FGV FROM AssetCostValueStatusDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status ")
    List<AssetCostValueStatusDtlEntity> findAllAssetCostValueStatus(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);

   
    @Modifying
    @Query("UPDATE AssetCostValueStatusDtlEntity SRD  SET SRD.status=:status  where SRD.id in :assetCostValueStatusIds")
    void deactivateAssetCostValueStatus(@Param("assetCostValueStatusIds") List<Long> assetCostValueStatusIds,
            @Param("status") Integer status);

    @Modifying
    @Query("DELETE from AssetCostValueStatusDtlEntity rv  where rv.id in :assetCostValueStatusIds")
    void assetCostValueStatusDelete(@Param("assetCostValueStatusIds") List<Long> assetCostValueStatusIds);
    
    @Query("SELECT FGV FROM AssetCostValueStatusDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid in (:fixedassetids) AND FGV.status=:status")
    public List<AssetCostValueStatusDtlEntity> findAllProjectAssetCostValue(@Param("fixedassetids") List<Integer> fixedassetids,
            @Param("status") Integer status);

}
