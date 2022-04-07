package com.rjtech.procurement.repository;

import java.math.BigDecimal;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.WorkDairyMaterialDtlEntityCopy;

@Repository
public interface MaterialWorkDairyRepositoryCopy extends JpaRepository<WorkDairyMaterialDtlEntityCopy, Long> {
	
	@Query("SELECT t FROM WorkDairyMaterialDtlEntityCopy t  WHERE t.projDocketSchId.materialProjDtlEntity.purchaseId.id=:purchaseId AND t.isSupplierDocket=:isSupplierDocket AND t.docketDate between :fromDate AND :toDate")
	List<WorkDairyMaterialDtlEntityCopy> getinvoiceMaterialDetails(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("purchaseId") Long purchaseId, @Param("isSupplierDocket") boolean isSupplierDocket);

}

