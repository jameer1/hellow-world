package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rjtech.centrallib.model.CountryProvinceCodeEntity;

@Repository
public interface CountryProvinceCodeRepository extends CentralLibRepository<CountryProvinceCodeEntity, Long> {
	
	   

    @Query("SELECT CPC FROM CountryProvinceCodeEntity CPC  WHERE CPC.clientId.clientId IS NULL OR CPC.clientId.clientId=:clientId  AND CPC.status=:status")
    List<CountryProvinceCodeEntity> getCountryProvinceCodes(@Param("clientId") Long clientId,@Param("status") Integer status);

    @Modifying
    @Query("UPDATE CountryProvinceCodeEntity SRD  SET SRD.status=:status  where SRD.id in :countryProvinceIds")
    void deactivateCountryProvinceCodes(@Param("countryProvinceIds") List<Long> countryProvinceIds, @Param("status") Integer status);
}