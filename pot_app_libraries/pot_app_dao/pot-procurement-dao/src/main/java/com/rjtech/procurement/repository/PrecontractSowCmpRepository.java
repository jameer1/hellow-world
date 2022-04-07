package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PrecontractSowCmpEntity;

public interface PrecontractSowCmpRepository extends ProcurementBaseRepository<PrecontractSowCmpEntity, Long> {

    @Query("SELECT t FROM PrecontractSowCmpEntity t  WHERE t.preContractsCmpEntity.id=:sowCmpId")
    List<PrecontractSowCmpEntity> precontractSowCmpEntities(@Param("sowCmpId") Long sowCmpId);

}
