package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.TermsAndConditionsEntity;

public interface TermsAndConditionsRepository extends ProcurementBaseRepository<TermsAndConditionsEntity, Long> {

    @Query("SELECT TC FROM TermsAndConditionsEntity TC WHERE TC.purchaseOrderEntity.id = :poId")
    public List<TermsAndConditionsEntity> findByPoId(@Param("poId") Long poId);

    @Query("SELECT TC FROM TermsAndConditionsEntity TC WHERE TC.preContractEntity.id = :precontractId")
    public List<TermsAndConditionsEntity> findByPrecontractId(@Param("precontractId") Long precontractId);

}
