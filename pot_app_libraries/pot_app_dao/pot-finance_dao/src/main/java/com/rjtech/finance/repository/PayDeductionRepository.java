package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.PayDeductionEntity;

@Repository
public interface PayDeductionRepository extends FinanceBaseRepository<PayDeductionEntity, Long> {

    @Query("SELECT TCE FROM PayDeductionEntity TCE  WHERE TCE.codeTypesEntity.id=:codeTypeCountryProvisionId AND TCE.status=:status ORDER BY TCE.code")
    List<PayDeductionEntity> findPayDeduction(@Param("codeTypeCountryProvisionId") Long codeTypeCountryProvisionId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE PayDeductionEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :payDeductionIds")
    public void deletePayDeduction(@Param("payDeductionIds") List<Long> payDeductionIds,
            @Param("status") Integer status);
}
