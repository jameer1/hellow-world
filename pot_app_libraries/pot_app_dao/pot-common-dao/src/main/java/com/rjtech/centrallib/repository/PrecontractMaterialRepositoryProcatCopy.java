package com.rjtech.centrallib.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.PreContractsMaterialDtlEntityProCatCopy;

public interface PrecontractMaterialRepositoryProcatCopy extends CentralLibRepository<PreContractsMaterialDtlEntityProCatCopy, Long> {

    @Query("SELECT DISTINCT PC.materialId.id FROM PreContractsMaterialDtlEntityProCatCopy PC  WHERE  PC.materialId.id IS NOT NULL")
    List<Long> getAllMatirealIds();

}