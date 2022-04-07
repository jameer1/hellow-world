package com.rjtech.procurement.repository;

import com.rjtech.procurement.model.PlantProjPODtlEntityCopy;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CopyPlantProjPORepository extends ProcurementBaseRepository<PlantProjPODtlEntityCopy, Long> {
	
	 @Query("SELECT count(PER.purchaseSchItemId) FROM PlantProjPODtlEntityCopy PER  WHERE PER.purchaseSchItemId=:schId")
	    public Integer findtotalschItems(@Param("schId") Long schId);
}
