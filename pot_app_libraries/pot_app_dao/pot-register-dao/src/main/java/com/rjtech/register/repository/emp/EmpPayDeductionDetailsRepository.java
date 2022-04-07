package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpRegularPayRateEntity;
import com.rjtech.register.repository.RegisterBaseRepository;
import com.rjtech.centrallib.model.PayDeductionCodeEntity;

public interface EmpPayDeductionDetailsRepository extends RegisterBaseRepository<PayDeductionCodeEntity, Long> {

   
    @Query("SELECT PDC FROM PayDeductionCodeEntity PDC WHERE PDC.financeCenterEntity.clientId.clientId=:clientId AND PDC.financeCenterEntity.countryCode=:countryId AND PDC.financeCenterEntity.provisionName=:provinceId AND PDC.financeCenterEntity.status=:payTypeId")
    public List<PayDeductionCodeEntity> getEmpPayDeductionCodes(@Param("clientId") Long clientId,@Param("countryId") String countryId,@Param("provinceId") String provinceId,@Param("payTypeId") Integer payTypeId);

}
