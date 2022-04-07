package com.rjtech.procurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.PreContractsEmpDtlEntity;

//import com.rjtech.procurement.model.PreContractsEmpDtlEntityCopy;

@Repository
public interface PrecontractEmpRepositoryCopy extends JpaRepository<PreContractsEmpDtlEntity, Long> {

}
