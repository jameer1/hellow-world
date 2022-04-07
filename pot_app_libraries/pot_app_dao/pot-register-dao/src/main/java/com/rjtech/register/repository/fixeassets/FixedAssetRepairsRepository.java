package com.rjtech.register.repository.fixeassets;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.FixedAssetPurchaseRecordsDtlEntity;
import com.rjtech.register.fixedassets.model.FixedAssetRepairsDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface FixedAssetRepairsRepository extends RegisterBaseRepository<FixedAssetRepairsDtlEntity, Long> {

    @Query("SELECT FGV FROM FixedAssetRepairsDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status")
    public List<FixedAssetRepairsDtlEntity> findAllAssetRepairs(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);
    @Modifying
    @Query("UPDATE FixedAssetRepairsDtlEntity FGA  SET FGA.status=:status  where FGA.id in :repairIds")
    public void deactivateRepairsRecord(@Param("repairIds") List<Long> repairIds, @Param("status") Integer status);

    @Modifying
    @Query("DELETE from FixedAssetRepairsDtlEntity rv  where rv.id in :repairIds")
    public void repairsDelete(@Param("repairIds") List<Long> repairIds);
    
    @Query("SELECT FGV FROM FixedAssetRepairsDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid in (:fixedassetids) AND FGV.status=:status")
    public List<FixedAssetRepairsDtlEntity> findAllProjectRepairs(@Param("fixedassetids") List<Integer> fixedassetids,
            @Param("status") Integer status);

}
