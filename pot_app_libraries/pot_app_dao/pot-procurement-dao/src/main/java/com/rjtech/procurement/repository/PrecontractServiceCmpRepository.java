package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsServiceCmpEntity;

public interface PrecontractServiceCmpRepository extends ProcurementBaseRepository<PreContractsServiceCmpEntity, Long> {

    @Query("SELECT t FROM com.rjtech.procurement.model.PreContractsServiceCmpEntity t  WHERE t.preContractsCmpEntity.id=:serviceCmpId")
    List<PreContractsServiceCmpEntity> preContractsServiceCmpEntities(@Param("serviceCmpId") Long serviceCmpId);

}
