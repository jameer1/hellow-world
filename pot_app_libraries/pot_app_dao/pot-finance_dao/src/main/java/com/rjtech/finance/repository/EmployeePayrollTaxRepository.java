package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.EmployeePayRollTaxEntity;

@Repository
public interface EmployeePayrollTaxRepository extends FinanceBaseRepository<EmployeePayRollTaxEntity, Long> {

    @Query("SELECT TCE FROM EmployeePayRollTaxEntity TCE  WHERE TCE.taxCodeCountryProvisionEntity.id=:taxCodeCountryProvisionId "
            + " AND TCE.status=:status")
    List<EmployeePayRollTaxEntity> findEmployeePayrollTax(
            @Param("taxCodeCountryProvisionId") Long taxCodeCountryProvisionId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE EmployeePayRollTaxEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :employeePayTaxIds")
    public void deleteEmployeePayroll(@Param("employeePayTaxIds") List<Long> employeePayTaxIds,
            @Param("status") Integer status);
}
