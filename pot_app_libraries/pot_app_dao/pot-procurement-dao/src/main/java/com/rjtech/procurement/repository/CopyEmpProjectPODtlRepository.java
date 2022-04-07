package com.rjtech.procurement.repository;

import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.EmpProjRegisterPODtlEntityCopy;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CopyEmpProjectPODtlRepository extends ProcurementBaseRepository<EmpProjRegisterPODtlEntityCopy, Long> {
	
	 @Query("SELECT count(PER.purchaseSchItemId) FROM EmpProjRegisterPODtlEntityCopy PER  WHERE PER.purchaseSchItemId=:schId")
	    public Integer findtotalschItems(@Param("schId") Long schId);
}
