package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.FixedAssetPurchaseRecordsDtlEntity;
import com.rjtech.register.fixedassets.model.SalesRecordDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface SalesRecordsRepository extends RegisterBaseRepository<SalesRecordDtlEntity, Long> {

    @Query("SELECT SRD FROM SalesRecordDtlEntity SRD WHERE SRD.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND SRD.status=:status ")
    List<SalesRecordDtlEntity> findAllSalesRecords(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE SalesRecordDtlEntity SRD  SET SRD.status=:status  where SRD.id in :salesRecordsIds")
    void deactivateSalesRecord(@Param("salesRecordsIds") List<Long> salesRecordsIds, @Param("status") Integer status);

    @Modifying
    @Query("DELETE from SalesRecordDtlEntity rv  where rv.id in :salesRecordsIds")
    void salesRecordDelete(@Param("salesRecordsIds") List<Long> salesRecordsIds);
    
    @Query("SELECT FGV FROM SalesRecordDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid in (:fixedassetids) AND FGV.status=:status")
    public List<SalesRecordDtlEntity> findAllProjectSaleRecords(@Param("fixedassetids") List<Integer> fixedassetids,
            @Param("status") Integer status);

}
