package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsMaterialCmpEntity;

public interface PrecontractMaterialCmpRepository
        extends ProcurementBaseRepository<PreContractsMaterialCmpEntity, Long> {

    @Query("SELECT t FROM com.rjtech.procurement.model.PreContractsMaterialCmpEntity t  WHERE t.preContractsCmpEntity.id=:materialCmpId")
    List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities(@Param("materialCmpId") Long materialCmpId);

}
