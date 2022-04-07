package com.rjtech.register.repository.plant;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.plant.model.PlantTransferReqApprEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface PlantTransferReqApprRepository extends RegisterBaseRepository<PlantTransferReqApprEntity, Long> {

    @Query("SELECT T FROM PlantTransferReqApprEntity T  WHERE T.toProjId.projectId in :toProjIds  AND ( (:reqUserId IS NOT NULL AND T.reqUserMstrEnitty.userId=:reqUserId ) "
            + " OR :reqUserId IS NULL)  AND T.apprStatus IS NOT NULL AND T.status=:status AND T.reqDate between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<PlantTransferReqApprEntity> findPlantReqTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("reqUserId") Long reqUserId, @Param("status") Integer status, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT T FROM PlantTransferReqApprEntity T  WHERE T.toProjId.projectId in :toProjIds AND ( (:apprUserId IS NOT NULL AND T.apprUserMstrEntity.userId=:apprUserId ) "
            + " OR :apprUserId IS NULL)  AND  (T.apprStatus IS NOT NULL AND T.apprStatus=:apprStatus) AND T.status=:status AND T.reqDate between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<PlantTransferReqApprEntity> findPlantApprTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("apprUserId") Long apprUserId, @Param("apprStatus") String apprStatus,
            @Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT T FROM PlantTransferReqApprEntity T  WHERE T.toProjId.projectId in :toProjIds AND ( (:apprUserId IS NOT NULL AND T.apprUserMstrEntity.userId = :apprUserId ) "
            + " OR :apprUserId IS NULL) AND T.apprStatus IS NOT NULL AND T.status=:status AND T.reqDate between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<PlantTransferReqApprEntity> findPlantAllTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("apprUserId") Long apprUserId, @Param("status") Integer status, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

}
