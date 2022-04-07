package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.TaxPaymentReceiverEntity;

@Repository
public interface PaymentReceiverRepository extends FinanceBaseRepository<TaxPaymentReceiverEntity, Long> {

    @Query("SELECT TCE FROM TaxPaymentReceiverEntity TCE  WHERE  TCE.codeTypesEntity.id=:codeTypeCountryProvisionId AND "
            + " TCE.status=:status ORDER BY TCE.deptName")
    public List<TaxPaymentReceiverEntity> findPaymentReceiver(
            @Param("codeTypeCountryProvisionId") Long codeTypeCountryProvisionId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE TaxPaymentReceiverEntity TCE  SET  TCE.status=:status WHERE  TCE.id in :paymentReceiverIds")
    public void deletePaymentReceiver(@Param("paymentReceiverIds") List<Long> paymentReceiverIds,
            @Param("status") Integer status);
}
