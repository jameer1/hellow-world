package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.RevenueSalesDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface RevenueSalesRepository extends RegisterBaseRepository<RevenueSalesDtlEntity, Long> {

    @Query("SELECT RSP FROM RevenueSalesDtlEntity RSP  WHERE RSP.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND RSP.status=:status ")
    List<RevenueSalesDtlEntity> findAllRevenueSales(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE RevenueSalesDtlEntity RSP  SET RSP.status=:status  where RSP.id in :revenueSalesIds")
    void deactivateRevenueSales(@Param("revenueSalesIds") List<Long> revenueSalesIds, @Param("status") Integer status);

    @Modifying
    @Query("DELETE from RevenueSalesDtlEntity rv  where rv.id in :revenueSalesIds")
    void revenueSalesDelete(@Param("revenueSalesIds") List<Long> revenueSalesIds);

}
