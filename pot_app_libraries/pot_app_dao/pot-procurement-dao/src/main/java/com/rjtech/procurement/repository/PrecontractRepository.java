package com.rjtech.procurement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.PreContractEntity;

@Repository
public interface PrecontractRepository extends ProcurementBaseRepository<PreContractEntity, Long> {

    @Query("SELECT PRC FROM com.rjtech.procurement.model.PreContractEntity PRC  WHERE PRC.projId.projectId =:projId  AND  PRC.preContarctStatus=:preContarctStatus "
            + " AND   PRC.contarctStageStatus  in ('Stage 1 Approval' ,'RFQ/Tendering' ,'Stage 2 Request') AND (PRC.revisedCloseDate >= :date OR PRC.revisedCloseDate IS NULL)  AND PRC.status=:status ORDER BY PRC.updatedOn DESC")
    List<PreContractEntity> findPreContractsForRfq(@Param("projId") Long projId,
            @Param("preContarctStatus") Integer preContarctStatus, @Param("status") Integer status, @Param("date") Date date);

    @Query("SELECT PRC FROM com.rjtech.procurement.model.PreContractEntity PRC  WHERE PRC.id=:contractId  AND PRC.projId.projectId =:projId  AND  PRC.status=:status ORDER BY PRC.updatedOn DESC")
    List<PreContractEntity> findPreContractDetailsById(@Param("contractId") Long contractId,
            @Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT PRC FROM com.rjtech.procurement.model.PreContractEntity PRC  WHERE PRC.id in (:contractIds)  AND PRC.projId.projectId in (:projIds)  AND  PRC.status=1 ORDER BY PRC.updatedOn DESC")
    List<PreContractEntity> findPreContractDetailsByIds(@Param("contractIds") List<Long> contractIds,
            @Param("projIds") List<Long> projIds);
    
    
    @Modifying
    @Query("UPDATE com.rjtech.procurement.model.PreContractEntity PRC SET PRC.status=:status  WHERE PRC.id in :contractIds")
    void deactivatePrecontacts(@Param("contractIds") List<Long> contractIds, @Param("status") Integer status);

    @Query("SELECT PRC FROM com.rjtech.procurement.model.PreContractEntity PRC WHERE PRC.status=:status AND ((:reqUserId IS NOT NULL AND PRC.reqUserId.userId =:reqUserId ) OR :reqUserId IS NULL)"
            + "   AND  PRC.projId.projectId in ( :projIds )  AND PRC.updatedOn between :fromDate AND :toDate  AND PRC.isLatest = true ORDER BY PRC.updatedOn DESC")
    List<PreContractEntity> findLatestPreContracts(@Param("status") Integer status, @Param("reqUserId") Long reqUserId,
            @Param("projIds") List<Long> projIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT PRC FROM \r\n"
    		+ "com.rjtech.procurement.model.PreContractEntity PRC WHERE PRC.status=:status AND ((:reqUserId IS NOT NULL AND PRC.reqUserId.userId =:reqUserId ) OR :reqUserId IS NULL) "
            + " AND ( :preContarctStatus IS NOT NULL AND PRC.preContarctStatus=:preContarctStatus ) AND  PRC.projId.projectId in ( :projIds ) "
            + " AND PRC.preContractsCmpEntities IS EMPTY AND PRC.updatedOn between :fromDate AND :toDate  ORDER BY PRC.updatedOn DESC")
    List<PreContractEntity> findInternalPreContracts(@Param("status") Integer status,
            @Param("reqUserId") Long reqUserId, @Param("preContarctStatus") Integer preContarctStatus,
            @Param("projIds") List<Long> projIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT DISTINCT PRC FROM com.rjtech.procurement.model.PreContractEntity PRC JOIN PRC.preContractsCmpEntities CMP WHERE PRC.status=:status AND "
            + "((:reqUserId IS NOT NULL AND PRC.reqUserId.userId =:reqUserId ) OR :reqUserId IS NULL)"
            + " AND ( :preContarctStatus IS NOT NULL AND CMP.cmpStatus = :preContarctStatus )"
            + " AND   PRC.contarctStageStatus  in ('Stage 2 Request', 'Stage 2 Approval', 'Stage 2 Approved', 'Purchase Order' ) AND  PRC.projId.projectId in ( :projIds ) "
            + " AND PRC.updatedOn between :fromDate AND :toDate ORDER BY PRC.updatedOn DESC")
    List<PreContractEntity> findExternalPreContracts(@Param("status") Integer status,
            @Param("reqUserId") Long reqUserId, @Param("preContarctStatus") Integer preContarctStatus,
            @Param("projIds") List<Long> projIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Modifying
    @Query("UPDATE com.rjtech.procurement.model.PreContractEntity T SET T.contarctStageStatus=:contarctStageStatus  WHERE T.id=:preContractId")
    void updategetContarctStageStatus(@Param("preContractId") Long preContractCmpId,
            @Param("contarctStageStatus") String contarctStageStatus);

    @Modifying
    @Query("UPDATE com.rjtech.procurement.model.PreContractEntity P  SET  P.status=:status WHERE  P.id in :contractIds")
    public void deactivatePreContranctList(@Param("contractIds") List<Long> contractIds,
            @Param("status") Integer status);

}
