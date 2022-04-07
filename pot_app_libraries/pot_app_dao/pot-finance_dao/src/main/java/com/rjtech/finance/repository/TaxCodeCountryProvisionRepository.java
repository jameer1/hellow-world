package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.TaxCodeCountryProvisionEntity;

@Repository
public interface TaxCodeCountryProvisionRepository extends FinanceBaseRepository<TaxCodeCountryProvisionEntity, Long> {

    @Query(" SELECT TCE FROM TaxCodeCountryProvisionEntity TCE  WHERE  TCE.taxCountryProvisionEntity.id=:countryProvisionId AND "
            + " TCE.status=:status ORDER BY TCE.taxCodesEntity.code")
    List<TaxCodeCountryProvisionEntity> findTaxCodeCountryProvisions(
            @Param("countryProvisionId") Long countryProvisionId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE TaxCodeCountryProvisionEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :taxCodeCountryProvisionIds")
    public void deactivateTaxCodeCountryProvision(
            @Param("taxCodeCountryProvisionIds") List<Long> taxCodeCountryProvisionIds,
            @Param("status") Integer status);
}
