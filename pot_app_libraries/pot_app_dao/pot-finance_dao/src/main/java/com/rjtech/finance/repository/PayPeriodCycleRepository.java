package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.PayRollEntity;

@Repository
public interface PayPeriodCycleRepository extends FinanceBaseRepository<PayRollEntity, Long> {

    @Query("SELECT UPR FROM PayRollEntity UPR  WHERE (UPR.clientId.clientId=:clientId ) AND  UPR.status=:status")
    List<PayRollEntity> findPayPeriodCycle(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE PayRollEntity UPR  SET  UPR.status=:status WHERE  UPR.id in :payPeriodIds")
    public void deactivatePayPeriodCycle(@Param("payPeriodIds") List<Long> payPeriodIds,
            @Param("status") Integer status);

}
