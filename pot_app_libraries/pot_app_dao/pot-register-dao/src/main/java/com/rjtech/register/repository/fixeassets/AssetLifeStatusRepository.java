package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.AssetLifeStatusDtlEntity;
import com.rjtech.register.fixedassets.model.FixedAssetPurchaseRecordsDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;
@Repository
public interface AssetLifeStatusRepository extends RegisterBaseRepository<AssetLifeStatusDtlEntity, Long> {

    @Query("SELECT FGV FROM AssetLifeStatusDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:subid AND FGV.status=:status ")
    List<AssetLifeStatusDtlEntity> findAllAssetLifeStatus(@Param("subid") Long subid, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE AssetLifeStatusDtlEntity SRD  SET SRD.status=:status  where SRD.id in :assetLifeStatusIds")
    void deactivateAssetLifeStatus(@Param("assetLifeStatusIds") List<Long> assetLifeStatusIds,
            @Param("status") Integer status);

    @Modifying
    @Query("DELETE from AssetLifeStatusDtlEntity rv  where rv.id in :assetLifeStatusIds")
    void assetLifeStatusDelete(@Param("assetLifeStatusIds") List<Long> assetLifeStatusIds);
    
    @Query("SELECT FGV FROM AssetLifeStatusDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid in (:fixedassetids) AND FGV.status=:status")
    public List<AssetLifeStatusDtlEntity> findAllProjectAssetLifeStatus(@Param("fixedassetids") List<Integer> fixedassetids,
            @Param("status") Integer status);

}
