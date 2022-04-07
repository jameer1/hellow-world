package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.EmployeeWiseCycleEntity;

@Repository
public interface EmployeeTypeRepository extends FinanceBaseRepository<EmployeeWiseCycleEntity, Long> {

    @Query("SELECT TCE FROM EmployeeWiseCycleEntity TCE  WHERE TCE.codeTypesEntity.id=:codeTypeCountryProvisionId AND TCE.status=:status")
    List<EmployeeWiseCycleEntity> findEmployeeTypes(
            @Param("codeTypeCountryProvisionId") Long codeTypeCountryProvisionId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE EmployeeWiseCycleEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :taxIds")
    public void deleteEmployeeType(@Param("taxIds") List<Long> taxIds, @Param("status") Integer status);
}
