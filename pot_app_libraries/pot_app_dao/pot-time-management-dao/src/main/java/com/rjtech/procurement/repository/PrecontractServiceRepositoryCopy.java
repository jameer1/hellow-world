package com.rjtech.procurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.procurement.model.PreContractsServiceDtlEntity;

//import com.rjtech.procurement.model.PreContractsServiceDtlEntityCopy;

public interface PrecontractServiceRepositoryCopy extends JpaRepository<PreContractsServiceDtlEntity, Long> {

}
