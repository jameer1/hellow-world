package com.rjtech.register.repository.fixeassets;


import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.FixedAssetPurchaseRecordsDtlEntity;
import com.rjtech.register.fixedassets.model.STCORRReturnsDtlEntity;
import com.rjtech.register.fixedassets.model.SubSequentRentalRecepitsDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface STCORRReturnsRepository extends RegisterBaseRepository<STCORRReturnsDtlEntity, Long> {

    @Query("SELECT FGV FROM STCORRReturnsDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status ORDER BY FGV.id ASC")
    List<STCORRReturnsDtlEntity> findAllSTCORRReturns(@Param("fixedAssetid") Long fixedAssetid, @Param("status") int status);

    @Modifying
    @Query("UPDATE STCORRReturnsDtlEntity SRD  SET SRD.status=:status  where SRD.id in :stcorrReturnsIds")
    void deactivateShortTermRecors(@Param("stcorrReturnsIds") List<Long> stcorrReturnsIds, @Param("status") int status);

    @Query("SELECT T FROM STCORRReturnsDtlEntity T WHERE  T.status=:status AND T.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND to_char(T.startDate,'DD-Mon-YYYY') BETWEEN :fromDate AND :toDate")
    List<STCORRReturnsDtlEntity> findSTCORRRetrunsFilter(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("fixedAssetid") Long fixedAssetid, @Param("status") Integer status);

    @Modifying
    @Query("DELETE from STCORRReturnsDtlEntity rv  where rv.id in :stcorrReturnsIds")
    void stcorrReturnsDelete(@Param("stcorrReturnsIds") List<Long> stcorrReturnsIds);
    
    @Query("SELECT FGV.SubSequentRentalReceiptsDtlEntityList FROM STCORRReturnsDtlEntity FGV  WHERE FGV.id = :id")
    List<SubSequentRentalRecepitsDtlEntity> getSubSequentRentalRecepits(@Param("id") Long id);
    
    @Query("SELECT FGV FROM STCORRReturnsDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid in (:fixedassetids) AND FGV.status=:status")
    public List<STCORRReturnsDtlEntity> findAllProjectShortTerm(@Param("fixedassetids") List<Integer> fixedassetids,
            @Param("status") Integer status);

}
