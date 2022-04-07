package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.CodeTypesEntity;

@Repository
public interface CodeTypesRepository extends FinanceBaseRepository<CodeTypesEntity, Long> {

    @Query(" SELECT TCE FROM CodeTypesEntity TCE  WHERE  TCE.taxCountryProvisionEntity.id=:countryProvisionId AND "
            + " TCE.status=:status")
    List<CodeTypesEntity> findCodeTypes(@Param("countryProvisionId") Long countryProvisionId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE CodeTypesEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :taxIds")
    public void deactivateCodeTypes(@Param("taxIds") List<Long> taxIds, @Param("status") Integer status);
}
