package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.CompanyTaxEntity;

@Repository
public interface CompanyTaxRepository extends FinanceBaseRepository<CompanyTaxEntity, Long> {

    @Query("SELECT TCE FROM CompanyTaxEntity TCE  WHERE TCE.taxCodeCountryProvisionEntity.id=:taxCodeCountryProvisionId "
            + " AND TCE.status=:status")
    List<CompanyTaxEntity> findCompanyTax(@Param("taxCodeCountryProvisionId") Long taxCodeCountryProvisionId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE CompanyTaxEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :companyTaxIds")
    public void deleteCompanyTax(@Param("companyTaxIds") List<Long> companyTaxIds, @Param("status") Integer status);
}
