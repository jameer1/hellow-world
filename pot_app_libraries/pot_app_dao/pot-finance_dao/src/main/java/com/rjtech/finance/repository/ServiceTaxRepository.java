package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.ServiceTaxEntity;

@Repository
public interface ServiceTaxRepository extends FinanceBaseRepository<ServiceTaxEntity, Long> {

    @Query("SELECT TCE FROM ServiceTaxEntity TCE  WHERE TCE.taxCodeCountryProvisionEntity.id=:taxCodeCountryProvisionId "
            + " AND TCE.status=:status")
    List<ServiceTaxEntity> findServiceTax(@Param("taxCodeCountryProvisionId") Long taxCodeCountryProvisionId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ServiceTaxEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :serviceTaxIds")
    public void deleteServiceTax(@Param("serviceTaxIds") List<Long> serviceTaxIds, @Param("status") Integer status);
}
