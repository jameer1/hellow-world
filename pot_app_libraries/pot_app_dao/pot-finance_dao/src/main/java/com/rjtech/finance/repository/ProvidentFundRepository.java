package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.ProvidentFundEntity;

@Repository
public interface ProvidentFundRepository extends FinanceBaseRepository<ProvidentFundEntity, Long> {

    @Query("SELECT TCE FROM ProvidentFundEntity TCE  WHERE   TCE.codeTypesEntity.id=:codeTypeCountryProvisionId AND "
            + " TCE.status=:status ORDER BY TCE.code")
    List<ProvidentFundEntity> findProvidentFund(@Param("codeTypeCountryProvisionId") Long codeTypeCountryProvisionId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProvidentFundEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :providentFundIds")
    public void deleteProvidentFund(@Param("providentFundIds") List<Long> providentFundIds,
            @Param("status") Integer status);
}
