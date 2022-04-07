package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.RegularPayAllowanceEntity;
import com.rjtech.finance.repository.FinanceBaseRepository;

@Repository
public interface RegularAllowanceRepository extends FinanceBaseRepository<RegularPayAllowanceEntity, Long> {

    @Query("SELECT TCE FROM RegularPayAllowanceEntity TCE  WHERE  TCE.codeTypesEntity.id=:codeTypeCountryProvisionId AND "
            + " TCE.status=:status ORDER BY TCE.code")
    List<RegularPayAllowanceEntity> findRegularAllowance(
            @Param("codeTypeCountryProvisionId") Long codeTypeCountryProvisionId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE RegularPayAllowanceEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :regAllowanceIds")
    public void deleteRegularAllowance(@Param("regAllowanceIds") List<Long> regAllowanceIds,
            @Param("status") Integer status);
}
