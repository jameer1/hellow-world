package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.PersonalTaxEntity;

@Repository
public interface PersonalTaxRepository extends FinanceBaseRepository<PersonalTaxEntity, Long> {

    @Query("SELECT TCE FROM PersonalTaxEntity TCE  WHERE TCE.taxCodeCountryProvisionEntity.id=:taxCodeCountryProvisionId "
            + " AND TCE.status=:status")
    List<PersonalTaxEntity> findPersonalTax(@Param("taxCodeCountryProvisionId") Long taxCodeCountryProvisionId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE PersonalTaxEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :personalTaxIds")
    public void deletePersonalTaxRates(@Param("personalTaxIds") List<Long> personalTaxIds,
            @Param("status") Integer status);
}
