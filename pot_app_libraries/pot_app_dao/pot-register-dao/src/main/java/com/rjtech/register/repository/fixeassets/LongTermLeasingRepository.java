package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.FixedAssetPurchaseRecordsDtlEntity;
import com.rjtech.register.fixedassets.model.LongTermLeasingDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface LongTermLeasingRepository extends RegisterBaseRepository<LongTermLeasingDtlEntity, Long> {

    @Query("SELECT FGV FROM LongTermLeasingDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status ORDER BY FGV.id ASC")
    List<LongTermLeasingDtlEntity> findAllLongTermLeasing(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") int status);
    
    @Query("SELECT FGV FROM LongTermLeasingDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status AND FGV.currentStatus like :currentStatus%")
    List<LongTermLeasingDtlEntity> findAllLongTermLeasingActiveAllRecord(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") int status,@Param("currentStatus") String currentStatus);
    
    @Query("SELECT FGV FROM LongTermLeasingDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status AND FGV.createdOn in (select max(FGV.createdOn) from LongTermLeasingDtlEntity FGV WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status)")
    List<LongTermLeasingDtlEntity> findAllLongTermLeasingLastRecord(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") int status);

    @Modifying
    @Query("UPDATE LongTermLeasingDtlEntity SRD  SET SRD.status=:status  where SRD.id in :longTermLeasingIds")
    void deactivateLongTermLeasing(@Param("longTermLeasingIds") List<Long> longTermLeasingIds,
            @Param("status") Integer status);

    @Modifying
    @Query("DELETE from LongTermLeasingDtlEntity rv  where rv.id in :longTermLeasingIds")
    void longTermLeasingDelete(@Param("longTermLeasingIds") List<Long> longTermLeasingIds);
    
    @Query("SELECT FGV FROM LongTermLeasingDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid in (:fixedassetids) AND FGV.status=:status")
    public List<LongTermLeasingDtlEntity> findAllProjectLongTermLeasing(@Param("fixedassetids") List<Integer> fixedassetids,
            @Param("status") Integer status);
    
    @Query("SELECT count(FGV) FROM LongTermLeasingDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid")
    public Integer getCountOfLongTermLeasing(@Param("fixedAssetid") Long fixedAssetid);

}
