package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsEmpCmpEntity;

public interface PrecontractEmpCmpRepository extends ProcurementBaseRepository<PreContractsEmpCmpEntity, Long> {

    @Query("SELECT t FROM PreContractsEmpCmpEntity t  WHERE t.preContractsCmpEntity.id=:empCmpId")
    List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities(@Param("empCmpId") Long empCmpId);

}
