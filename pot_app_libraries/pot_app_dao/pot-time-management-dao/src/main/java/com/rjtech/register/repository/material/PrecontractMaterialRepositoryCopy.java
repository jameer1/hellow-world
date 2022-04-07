package com.rjtech.register.repository.material;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;

//import com.rjtech.procurement.model.PreContractsMaterialDtlEntityCopy;

public interface PrecontractMaterialRepositoryCopy
        extends JpaRepository<PreContractsMaterialDtlEntity, Long> {

}
