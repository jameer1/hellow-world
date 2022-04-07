package com.rjtech.register.repository.material;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.material.model.MaterialNotificationsEntity;
import com.rjtech.register.material.model.MaterialTransferReqApprEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface MaterialTranesferReqApprRepository
        extends RegisterBaseRepository<MaterialTransferReqApprEntity, Long> {

    @Query("SELECT T FROM MaterialTransferReqApprEntity T  WHERE T.toProjId.projectId in :toProjIds  AND ( (:reqUserId IS NOT NULL AND T.reqUserEntity.userId=:reqUserId ) OR :reqUserId IS NULL)  AND T.apprStatus IS NOT NULL AND T.status=:status AND T.reqDate between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<MaterialTransferReqApprEntity> findMaterialReqTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("reqUserId") Long reqUserId, @Param("status") Integer status, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT T FROM MaterialTransferReqApprEntity T  WHERE T.toProjId.projectId in :toProjIds AND ( (:apprUserId IS NOT NULL AND T.apprUserEntity.userId=:apprUserId ) OR :apprUserId IS NULL)  AND  (T.apprStatus IS NOT NULL AND T.apprStatus=:apprStatus)  AND   T.status=:status AND T.reqDate between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<MaterialTransferReqApprEntity> findMaterialApprTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("apprUserId") Long apprUserId, @Param("apprStatus") String apprStatus,
            @Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT T FROM MaterialTransferReqApprEntity T  WHERE T.toProjId.projectId in :toProjIds AND ( (:apprUserId IS NOT NULL AND T.createdBy.userId=:apprUserId  ) OR :apprUserId IS NULL)    AND T.apprStatus IS NOT NULL AND    T.status=:status AND T.reqDate between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<MaterialTransferReqApprEntity> findMaterialAllTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("apprUserId") Long apprUserId, @Param("status") Integer status, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT MTRAD.materialId.id, MTRAD.materialId.preContractMterialId.id, "
            + "MTRAD.materialId.preContractMterialId.desc, " + "MTRAD.materialId.purchaseId.id, "
            + "MTRAD.materialId.materialClassId.code, " + "MTRAD.materialId.materialClassId.name, "
            + "MTRAD.materialId.materialClassId.materialClassMstrEntity.name, "
            + "MTRAD.materialId.materialClassId.measurmentMstrEntity.code, " + "MSCL.avlQty "
            + "FROM MaterialTransferReqApprEntity MTRA " + "JOIN MaterialTransferReqApprDtlEntity MTRAD ON "
            + "MTRAD.materialTransferReqApprEntity = MTRA "
            + "JOIN MaterialSchLocCountEntity MSCL ON MSCL.materialProjDtlEntity = MTRAD.materialId "
            + "WHERE MTRA.id = :reqUserId AND " + "MTRAD.materialId.materialClassId.item = true")
    List<Object[]> getTransferReqDetails(@Param("reqUserId") Long reqUserId);

    @Query("SELECT MTRAD FROM MaterialTransferReqApprEntity MTRAD WHERE "
            + "MTRAD.materialNotificationsEntity = :materialNotificationsEntity "
            + "AND MTRAD.apprStatus = 'Approved'")
    MaterialTransferReqApprEntity getMaterialTransferReqApprEntity(
            @Param("materialNotificationsEntity") MaterialNotificationsEntity materialNotificationsEntity);
    
}
