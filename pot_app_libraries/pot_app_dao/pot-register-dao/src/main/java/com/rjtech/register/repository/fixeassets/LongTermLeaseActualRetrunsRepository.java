package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.fixedassets.model.FixedAssetPurchaseRecordsDtlEntity;
import com.rjtech.register.fixedassets.model.LongTermLeaseActualRetrunsDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface LongTermLeaseActualRetrunsRepository
        extends RegisterBaseRepository<LongTermLeaseActualRetrunsDtlEntity, Long> {

    @Query("SELECT FGV FROM LongTermLeaseActualRetrunsDtlEntity FGV WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status ORDER BY FGV.id ASC")
    List<LongTermLeaseActualRetrunsDtlEntity> findAllLongTermLeaseActualRetruns(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);
    
    @Query("SELECT FGV FROM LongTermLeaseActualRetrunsDtlEntity FGV WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status AND FGV.createdOn in (select max(FGV.createdOn) from LongTermLeaseActualRetrunsDtlEntity FGV WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status)")
    List<LongTermLeaseActualRetrunsDtlEntity> findAllLongTermLeaseActualRetrunsLastRecord(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);
    
    @Modifying
    @Query("UPDATE LongTermLeaseActualRetrunsDtlEntity SRD  SET SRD.status=:status  where SRD.id in :longTermLeaseAcutalIds")
    void longTermLeaseActualRetrunsDeactivate(@Param("longTermLeaseAcutalIds") List<Long> longTermLeaseAcutalIds, @Param("status") Integer status);

    @Modifying
    @Query("DELETE from LongTermLeaseActualRetrunsDtlEntity rv  where rv.id in :longTermLeaseAcutalIds")
    void delateLongTermLeaseActualRetruns(@Param("longTermLeaseAcutalIds") List<Long> longTermLeaseAcutalIds);
    
    @Query("SELECT FGV FROM LongTermLeaseActualRetrunsDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid in (:fixedassetids) AND FGV.status=:status")
    public List<LongTermLeaseActualRetrunsDtlEntity> findAllProjectLongTermActualRetruns(@Param("fixedassetids") List<Integer> fixedassetids,
            @Param("status") Integer status);

}
