package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.FixedAssetPurchaseRecordsDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface FixedAssetPurchaseRecordsRepository
        extends RegisterBaseRepository<FixedAssetPurchaseRecordsDtlEntity, Long> {

    @Query("SELECT FGV FROM FixedAssetPurchaseRecordsDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status  ")
    public List<FixedAssetPurchaseRecordsDtlEntity> findAllAssetPurchaase(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE FixedAssetPurchaseRecordsDtlEntity FGA  SET FGA.status=:status  where FGA.id in :purachaseIds")
    public void deactivatePurchaseRecord(@Param("purachaseIds") List<Long> purachaseIds,
            @Param("status") Integer status);

    @Modifying
    @Query("DELETE from FixedAssetPurchaseRecordsDtlEntity rv  where rv.id in :purachaseIds")
    public void purachaseAcqulistitonDelete(@Param("purachaseIds") List<Long> purachaseIds);
    
    @Query("SELECT FGV FROM FixedAssetPurchaseRecordsDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.projMstrEntity.projectId=:projId AND FGV.status=:status")
    public List<FixedAssetPurchaseRecordsDtlEntity> findAllProjectAssetPurchase(@Param("projId") Long projId,
            @Param("status") Integer status);

}
