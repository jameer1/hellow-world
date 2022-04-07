package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.UnitPayRateEntity;

@Repository
public interface UnitPayRateRepository extends FinanceBaseRepository<UnitPayRateEntity, Long> {

    @Query("SELECT UPR FROM UnitPayRateEntity UPR  WHERE (UPR.clientId IS NULL OR UPR.clientId.clientId=:clientId ) AND  UPR.status=:status ORDER BY UPR.code")
    List<UnitPayRateEntity> findByClientId(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Query("SELECT UPR FROM UnitPayRateEntity UPR  WHERE (UPR.clientId IS NULL OR UPR.clientId.clientId=:clientId )  AND (:code IS NULL OR UPR.code like :code ) AND  (:name IS NULL OR UPR.name like :name )  AND UPR.status=:status ORDER BY UPR.code")
    List<UnitPayRateEntity> findUnitPayRate(@Param("clientId") Long clientId, @Param("code") String code,
            @Param("name") String name, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE UnitPayRateEntity UPR  SET  UPR.status=:status WHERE  UPR.id in :unitPayRateIds")
    public void deactivateUnitOfRates(@Param("unitPayRateIds") List<Long> unitPayRateIds,
            @Param("status") Integer status);

}
