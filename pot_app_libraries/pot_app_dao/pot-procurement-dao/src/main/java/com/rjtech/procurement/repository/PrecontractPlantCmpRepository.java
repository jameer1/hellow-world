package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsPlantCmpEntity;

public interface PrecontractPlantCmpRepository extends ProcurementBaseRepository<PreContractsPlantCmpEntity, Long> {

    @Query("SELECT t FROM PreContractsPlantCmpEntity t  WHERE t.preContractsCmpEntity.id=:plantCmpId")
    List<PreContractsPlantCmpEntity> preContractsPlantCmpEntities(@Param("plantCmpId") Long plantCmpId);

}
