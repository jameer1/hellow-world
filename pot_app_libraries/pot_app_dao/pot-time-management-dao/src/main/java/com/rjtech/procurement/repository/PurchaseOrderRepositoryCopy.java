package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;

//import com.rjtech.procurement.model.PreContractsCmpEntityCopy;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;

public interface PurchaseOrderRepositoryCopy extends JpaRepository<PurchaseOrderEntity, Long> {

    PurchaseOrderEntity findByPreContractsCmpEntity(
            @Param("preContractsCmpEntity") PreContractsCmpEntity preContractsCmpEntity);
        
    @Query("SELECT PCSW.sowId.id, PUR.amount FROM PurchaseOrderEntity PUR  JOIN PUR.preContractsCmpEntity.preContractEntity PRC JOIN "
            + "PRC.precontractSowDtlEntities PCSW WHERE PRC.contarctStageStatus='purchase order' AND PRC.purchaseOrderStatus = 'po issued' "
            + "AND PUR.id= :poId")
    List<Object[]> getOriginalQtyOfPo(@Param("poId") Long poId);
}
