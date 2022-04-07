package com.rjtech.procurement.repository;

import com.rjtech.procurement.model.MaterialProjDtlEntityCopy;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CopyMaterialProjRepository extends ProcurementBaseRepository<MaterialProjDtlEntityCopy, Long> {
	@Query("SELECT count(PER.purchaseSchItemId) FROM MaterialProjDtlEntityCopy PER  WHERE PER.purchaseSchItemId=:schId")
    public Integer findtotalschItems(@Param("schId") Long schId);
}
