package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractReqApprEntity;

public interface PrecontractReqApprRepository extends ProcurementBaseRepository<PreContractReqApprEntity, Long> {

    @Query("SELECT PRA FROM com.rjtech.procurement.model.PreContractReqApprEntity PRA  WHERE PRA.preContractEntity.id =:contractId AND  PRA.status=:status")
    List<PreContractReqApprEntity> findPreContractReqApprs(@Param("contractId") Long contractId,
            @Param("status") Integer status);

}
