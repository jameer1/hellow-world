package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.NonRegularPayAllowanceEntity;

@Repository
public interface NonRegularAllowanceRepository extends FinanceBaseRepository<NonRegularPayAllowanceEntity, Long> {

    @Query("SELECT TCE FROM NonRegularPayAllowanceEntity TCE  WHERE  "
            + " TCE.codeTypesEntity.id=:codeTypeCountryProvisionId AND " + " TCE.status=:status ORDER BY TCE.code")
    List<NonRegularPayAllowanceEntity> findNonRegularAllowance(
            @Param("codeTypeCountryProvisionId") Long codeTypeCountryProvisionId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE NonRegularPayAllowanceEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :nonRegAllowanceIds")
    public void deleteNonRegularAllowance(@Param("nonRegAllowanceIds") List<Long> nonRegAllowanceIds,
            @Param("status") Integer status);
}
