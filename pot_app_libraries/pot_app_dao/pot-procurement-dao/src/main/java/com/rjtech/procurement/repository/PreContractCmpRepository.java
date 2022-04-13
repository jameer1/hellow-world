package com.rjtech.procurement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsCmpEntity;

public interface PreContractCmpRepository extends ProcurementBaseRepository<PreContractsCmpEntity, Long> {

    @Query("SELECT PCC FROM com.rjtech.procurement.model.PreContractsCmpEntity PCC  WHERE PCC.preContractEntity.id=:contractId AND  PCC.status=:status ")
    List<PreContractsCmpEntity> findPreContractCompnies(@Param("contractId") Long contractId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE com.rjtech.procurement.model.PreContractsCmpEntity PCC SET PCC.status=:status  WHERE PCC.id in :preContractCmpIds")
    void deactivateCompanies(@Param("preContractCmpIds") List<Long> preContractCmpIds, @Param("status") Integer status);

    @Query("SELECT PCC FROM com.rjtech.procurement.model.PreContractsCmpEntity PCC  WHERE PCC.status=:status "
            + " AND PCC.biddingStatus=:biddingStatus  AND   PCC.preContractEntity.projId.projectId in :projIds AND  ( (:reqUserId IS NOT NULL AND  PCC.preContractEntity.reqUserId.userId =:reqUserId)  OR :reqUserId IS NULL )   "
            + " AND PCC.preContractEntity.isLatest= true AND PCC.preContractEntity.preContarctStatus = 5 AND  PCC.updatedOn between :fromDate AND :toDate  ORDER  BY PCC.updatedOn  DESC")
    List<PreContractsCmpEntity> findPreContractRFQs(@Param("status") Integer status,
            @Param("biddingStatus") String biddingStatus, @Param("reqUserId") Long reqUserId,
            @Param("projIds") List<Long> projIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Modifying
    @Query("UPDATE com.rjtech.procurement.model.PreContractsCmpEntity T SET T.cmpStatus=1, T.biddingStatus=:biddingStatus, T.quotedDate= current_timestamp(), "
            + "T.quoteRefCode=:quoteRefCode  WHERE T.id=:preContractCmpId")
    void updateCmpBidStatus(@Param("preContractCmpId") Long preContractCmpId,
            @Param("biddingStatus") String biddingStatus, @Param("quoteRefCode") String quoteRefCode);

}
