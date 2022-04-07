package com.rjtech.procurement.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.MaterialPODeliveryDocketEntityCopy;

public interface CopyMaterialDeliveryDocketRepository
        extends ProcurementBaseRepository<MaterialPODeliveryDocketEntityCopy, Long> {
	
	@Query("SELECT t FROM MaterialPODeliveryDocketEntityCopy t  WHERE t.materialProjDtlEntityCopy.purchaseId.id=:purchaseId AND t.docketDate between :fromDate AND :toDate")
	List<MaterialPODeliveryDocketEntityCopy> getInvoiceMaterials(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("purchaseId") Long purchaseId);
}
