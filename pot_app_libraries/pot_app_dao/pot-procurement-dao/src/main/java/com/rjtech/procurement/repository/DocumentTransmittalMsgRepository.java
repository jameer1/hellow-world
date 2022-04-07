package com.rjtech.procurement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.rjtech.procurement.model.DocumentTransmittalMessageEntity;

@Repository
public interface DocumentTransmittalMsgRepository
        extends ProcurementBaseRepository<DocumentTransmittalMessageEntity, Long> {

    @Query("SELECT S FROM DocumentTransmittalMessageEntity S  WHERE S.projId.projectId=:projId AND  S.status=:status")
    DocumentTransmittalMessageEntity findTransmittalMsgDetails(@Param("projId") Long projId,
            @Param("status") Integer status);
    
    @Query("SELECT S FROM DocumentTransmittalMessageEntity S  WHERE S.contractId.id=:contractId ")
    List<DocumentTransmittalMessageEntity> findvendor(@Param("contractId") Long contractId);
}
