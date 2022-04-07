package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.MortgagePaymentDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface MortgagePaymentRepository extends RegisterBaseRepository<MortgagePaymentDtlEntity, Long> {

    @Query("SELECT FGV FROM MortgagePaymentDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status")
    List<MortgagePaymentDtlEntity> findAllMortgageePayments(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE MortgagePaymentDtlEntity MPD SET MPD.status=:status  where MPD.id in :mortagageePaymentsIds ")
    void deactivateSubAsset(@Param("mortagageePaymentsIds") List<Long> mortagageePaymentsIds,
            @Param("status") Integer status);

    @Modifying
    @Query("DELETE from MortgagePaymentDtlEntity rv  where rv.id in :mortagageePaymentsIds")
    void mortgageePaymentsDelete(@Param("mortagageePaymentsIds") List<Long> mortagageePaymentsIds);

    @Query("select count(MPDE) FROM MortgagePaymentDtlEntity MPDE WHERE MPDE.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid")
    public Integer getCountOfMortgagePayment(@Param("fixedAssetid") Long fixedAssetid);
}
