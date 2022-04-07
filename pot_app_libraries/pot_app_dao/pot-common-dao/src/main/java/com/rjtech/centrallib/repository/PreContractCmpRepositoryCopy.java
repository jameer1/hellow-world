package com.rjtech.centrallib.repository;


import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.centrallib.model.PreContractsCmpEntityCLCopy;

public interface PreContractCmpRepositoryCopy extends JpaRepository<PreContractsCmpEntityCLCopy, Long> {

	 @Query("SELECT PCE.companyId.id FROM PreContractsCmpEntityCLCopy PCE WHERE  PCE.status=1")
	    List<Long> getPreContractCmpIds();
}
