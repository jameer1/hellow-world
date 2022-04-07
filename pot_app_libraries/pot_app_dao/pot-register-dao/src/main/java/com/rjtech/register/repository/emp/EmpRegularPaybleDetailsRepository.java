package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.RegularPayAllowanceEntity;
import com.rjtech.register.emp.model.EmpRegularPayRateEntity;
import com.rjtech.register.repository.RegisterBaseRepository;
//import com.rjtech.centrallib.model.RegularPayAllowanceEntity;

public interface EmpRegularPaybleDetailsRepository extends RegisterBaseRepository<RegularPayAllowanceEntity, Long> {

   
    //@Query("SELECT FRA FROM RegularPayAllowanceEntity FRA WHERE FRA.CountryProvinceCodeEntity.clientId.clientId=:clientId AND FRA.CountryProvinceCodeEntity.countrycode=:countryId AND FRA.CountryProvinceCodeEntity.provisionName=:provinceId AND FRA.CountryProvinceCodeEntity.status=:payTypeId")
	@Query("SELECT FRA FROM com.rjtech.centrallib.model.RegularPayAllowanceEntity FRA WHERE FRA.CountryProvinceCodeEntity.clientId.clientId=:clientId AND FRA.CountryProvinceCodeEntity.countrycode=:countryId AND FRA.CountryProvinceCodeEntity.provisionName=:provinceId AND FRA.CountryProvinceCodeEntity.status=:payTypeId")
	public List<RegularPayAllowanceEntity> getEmpRegularPaycodes(@Param("clientId") Long clientId,@Param("countryId") String countryId,@Param("provinceId") String provinceId,@Param("payTypeId") Integer payTypeId);

}
