package com.rjtech.procurement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsEmpDtlEntity;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;

public interface PrecontractServiceRepository extends ProcurementBaseRepository<PreContractsServiceDtlEntity, Long> {

    @Query("SELECT PCS FROM \r\n"
    		+ "com.rjtech.procurement.model.PreContractsServiceDtlEntity PCS  WHERE PCS.preContractEntity.id=:contractId AND  PCS.status=:status ORDER BY PCS.id")
    List<PreContractsServiceDtlEntity> findPreContractServices(@Param("contractId") Long contractId,
            @Param("status") Integer status);

    @Query("SELECT PCS FROM \r\n"
    		+ "com.rjtech.procurement.model.PreContractsServiceDtlEntity PCS JOIN FETCH "
            + "PCS.preContractsServiceCmpEntities SCM WHERE PCS.preContractEntity.id=:contractId  AND  "
            + "SCM.preContractsCmpEntity.id=:serviceCmpId AND SCM.quantity > 0  AND PCS.status=:status ORDER BY PCS.id")
    List<PreContractsServiceDtlEntity> findPreContractServiceDtlByCmpId(@Param("contractId") Long contractId,
            @Param("serviceCmpId") Long serviceCmpId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE \r\n"
    		+ "com.rjtech.procurement.model.PreContractsServiceDtlEntity PRC SET PRC.status=:status  WHERE PRC.id in :serviceDtlIds")
    void deactivateServiceDetails(@Param("serviceDtlIds") List<Long> serviceDtlIds, @Param("status") Integer status);

    @Query("SELECT PCS.projcostStatement.projCostItemEntity.id,SUM(PCS.quantity * PCS.estimateCost)  FROM  PreContractsServiceDtlEntity PCS WHERE "
            + "PCS.preContractEntity.id = :precontractId AND PCS.latest = 1 GROUP BY PCS.projcostStatement.projCostItemEntity.id")
    List<Object[]> getServiceCostSummary(@Param("precontractId") Long precontractId);
    
    @Query("SELECT PCE FROM com.rjtech.procurement.model.PreContractsServiceDtlEntity PCE  WHERE PCE.startDate=:fromDate and PCE.finishDate=:toDate and PCE.procureSubCatgId.procureType='Services'"
            + " and PCE.procureSubCatgId.name=:pocSubCatName and  PCE.preContractEntity.id=:precontractId  "
            + " ORDER BY PCE.id")
    List<PreContractsServiceDtlEntity> searchServiceDtlsByCriteria(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("precontractId") Long precontractId,
 		 @Param("pocSubCatName") String pocSubCatName);//,// @Param("payableCat") String payableCat,
       	  //@Param("unitsOfMeasure") String unitsOfMeasure);

}
