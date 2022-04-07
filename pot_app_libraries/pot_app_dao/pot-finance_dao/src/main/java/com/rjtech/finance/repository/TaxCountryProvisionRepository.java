package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.TaxCountryProvisionEntity;

@Repository
public interface TaxCountryProvisionRepository extends FinanceBaseRepository<TaxCountryProvisionEntity, Long> {

    @Query(" SELECT TCE FROM TaxCountryProvisionEntity TCE  WHERE TCE.countryName=:countryName   AND TCE.clientId.clientId=:clientId AND TCE.status=:status")
    public List<TaxCountryProvisionEntity> findTaxCodesByCountry(@Param("countryName") String countryName,
            @Param("clientId") Long clientId, @Param("status") Integer status);

    @Query(" SELECT TCE FROM TaxCountryProvisionEntity TCE  WHERE ((:taxCodeId IS NULL AND TCE.countryName=:countryName)  OR  TCE.id=:taxCodeId ) AND TCE.clientId.clientId=:clientId AND TCE.status=:status")
    public List<TaxCountryProvisionEntity> findTaxCountryProvision(@Param("countryName") String countryName,
            @Param("taxCodeId") Long taxCodeId, @Param("clientId") Long clientId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE TaxCountryProvisionEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :taxCountryProvisionIds")
    public void deactivateTaxCountryProvision(@Param("taxCountryProvisionIds") List<Long> taxCountryProvisionIds,
            @Param("status") Integer status);

    @Query("SELECT TCE FROM TaxCountryProvisionEntity TCE WHERE TCE.clientId.clientId=:clientId AND TCE.status=:status")
    List<TaxCountryProvisionEntity> findAllCountryProvinces(@Param("clientId") Long clientId,
            @Param("status") Integer status);
}
