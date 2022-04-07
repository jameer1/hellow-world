package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.TaxCodesEntity;

@Repository
public interface TaxCodesRepository extends FinanceBaseRepository<TaxCodesEntity, Long> {

    @Query("SELECT TCE FROM TaxCodesEntity TCE  WHERE (TCE.clientId IS NULL OR TCE.clientId.clientId=:clientId ) AND  TCE.status=:status ORDER BY TCE.code")
    List<TaxCodesEntity> findTaxCodes(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE TaxCodesEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :taxCodeIds")
    public void deactivateTaxCodes(@Param("taxCodeIds") List<Long> taxCodeIds, @Param("status") Integer status);

    @Query("SELECT TCE FROM TaxCodesEntity TCE  WHERE TCE.clientId IS NULL OR TCE.clientId.clientId=:clientId ORDER BY TCE.code")
    List<TaxCodesEntity> findAllTaxCodes(@Param("clientId") Long clientId);
}
