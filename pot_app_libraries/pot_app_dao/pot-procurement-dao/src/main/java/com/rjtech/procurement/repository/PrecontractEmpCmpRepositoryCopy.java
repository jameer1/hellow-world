package com.rjtech.procurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.PreContractsEmpCmpEntity;

//import com.rjtech.procurement.model.PreContractsEmpCmpEntityCopy;

@Repository
public interface PrecontractEmpCmpRepositoryCopy extends JpaRepository<PreContractsEmpCmpEntity, Long> {

}
