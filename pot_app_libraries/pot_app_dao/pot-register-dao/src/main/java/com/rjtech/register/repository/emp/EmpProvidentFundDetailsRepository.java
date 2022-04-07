package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.rjtech.register.repository.RegisterBaseRepository;
import com.rjtech.centrallib.model.SuperProvidentFundEntity;

public interface EmpProvidentFundDetailsRepository extends RegisterBaseRepository<SuperProvidentFundEntity, Long> {

   
    @Query("SELECT SPF FROM SuperProvidentFundEntity SPF WHERE SPF.CountryProvinceCodeEntity.clientId.clientId=:clientId AND SPF.CountryProvinceCodeEntity.countrycode=:countryId AND SPF.CountryProvinceCodeEntity.provisionName=:provinceId AND SPF.CountryProvinceCodeEntity.status=:payTypeId")
    public List<SuperProvidentFundEntity> getEmpPayDeductionCodes(@Param("clientId") Long clientId,@Param("countryId") String countryId,@Param("provinceId") String provinceId,@Param("payTypeId") Integer payTypeId);

}
