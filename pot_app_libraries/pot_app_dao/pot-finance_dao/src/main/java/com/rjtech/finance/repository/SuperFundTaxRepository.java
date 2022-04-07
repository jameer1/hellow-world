package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.TaxOnSuperFundEntity;

@Repository
public interface SuperFundTaxRepository extends FinanceBaseRepository<TaxOnSuperFundEntity, Long> {

    @Query("SELECT TCE FROM TaxOnSuperFundEntity TCE  WHERE TCE.taxCodeCountryProvisionEntity.id=:taxCodeCountryProvisionId "
            + " AND TCE.status=:status")
    List<TaxOnSuperFundEntity> findSuperFund(@Param("taxCodeCountryProvisionId") Long taxCodeCountryProvisionId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE TaxOnSuperFundEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :superFundTaxIds")
    public void deleteSuperfundTax(@Param("superFundTaxIds") List<Long> superFundTaxIds,
            @Param("status") Integer status);
}
