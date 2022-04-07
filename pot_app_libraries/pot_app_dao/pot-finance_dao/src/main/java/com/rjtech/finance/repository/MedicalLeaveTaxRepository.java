package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.MedicalTaxEntity;

@Repository
public interface MedicalLeaveTaxRepository extends FinanceBaseRepository<MedicalTaxEntity, Long> {

    @Query("SELECT TCE FROM MedicalTaxEntity TCE  WHERE TCE.taxCodeCountryProvisionEntity.id=:taxCodeCountryProvisionId "
            + " AND TCE.status=:status")
    List<MedicalTaxEntity> findMedicalLeaveTax(@Param("taxCodeCountryProvisionId") Long taxCodeCountryProvisionId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE MedicalTaxEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :medicalLeaveIds")
    public void deleteMedicalLeaveTax(@Param("medicalLeaveIds") List<Long> medicalLeaveIds,
            @Param("status") Integer status);
}
