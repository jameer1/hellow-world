package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpRegularPayRateEntity;
import com.rjtech.register.repository.RegisterBaseRepository;
import com.rjtech.centrallib.model.NonRegularPayAllowanceEntity;

public interface EmpNonRegularPaybleDetailsRepository extends RegisterBaseRepository<NonRegularPayAllowanceEntity, Long> {

   
    @Query("SELECT FNRA FROM com.rjtech.centrallib.model.NonRegularPayAllowanceEntity FNRA WHERE FNRA.CountryProvinceCodeEntity.clientId.clientId=:clientId AND FNRA.CountryProvinceCodeEntity.countrycode=:countryId AND FNRA.CountryProvinceCodeEntity.provisionName=:provinceId AND FNRA.CountryProvinceCodeEntity.status=:payTypeId")
    public List<NonRegularPayAllowanceEntity> getEmpRegularPaycodes(@Param("clientId") Long clientId,@Param("countryId") String countryId,@Param("provinceId") String provinceId,@Param("payTypeId") Integer payTypeId);

}
