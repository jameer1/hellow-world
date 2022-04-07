package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.ProfitCentreEntity;

@Repository
public interface ProfitCentreRepository extends FinanceBaseRepository<ProfitCentreEntity, Long> {

    @Query("SELECT T FROM ProfitCentreEntity T WHERE  T.clientId.clientId=:clientId AND T.profitCentreEntity IS NULL AND T.status=:status  ORDER BY  T.code")
    public List<ProfitCentreEntity> findAllProfitCentres(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM ProfitCentreEntity T WHERE  (T.clientId.clientId=:clientId OR T.clientId.clientId IS NULL) AND T.profitCentreEntity IS NOT NULL AND T.item=true AND T.status=:status  ORDER BY  T.code")
    public List<ProfitCentreEntity> findAllProfitItems(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProfitCentreEntity T  SET T.status=:status  where T.id in :profitIds or T.profitCentreEntity.id in :profitIds")
    void deactivateProfitCentres(@Param("profitIds") List<Long> materialIds, @Param("status") Integer status);

    @Query("SELECT T FROM ProfitCentreEntity T WHERE  T.clientId.clientId=:clientId AND T.profitCentreEntity IS NULL "
            + "AND T.status=:status and ( LOWER(T.code) LIKE LOWER(:code) OR LOWER(T.name) LIKE LOWER(:name)) ORDER BY  T.code")
    public List<ProfitCentreEntity> findProfitCentresByName(@Param("code") String code, @Param("name") String name,
            @Param("clientId") Long clientId, @Param("status") Integer status);

}
