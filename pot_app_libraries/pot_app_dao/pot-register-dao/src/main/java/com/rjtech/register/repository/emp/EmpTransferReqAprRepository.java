package com.rjtech.register.repository.emp;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpTransferReqApprEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpTransferReqAprRepository extends RegisterBaseRepository<EmpTransferReqApprEntity, Long> {

    @Query("SELECT T FROM EmpTransferReqApprEntity T WHERE T.toProjMstrEntity.projectId in :toProjIds AND"
            + " (T.reqUserMstrEntity.userId=:reqUserId)"
            + "  AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmpTransferReqApprEntity> findEmpReqTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("reqUserId") Long reqUserId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT T FROM EmpTransferReqApprEntity T WHERE T.fromProjMstrEntity.projectId in :toProjIds AND"
            + " ((T.apprUserMstrEntity.userId=:apprUserId ) OR :apprUserId IS NULL)  AND "
            + "(T.apprStatus=:apprStatus) AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmpTransferReqApprEntity> findEmpApprTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("apprUserId") Long apprUserId, @Param("apprStatus") String apprStatus,
             @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT T FROM EmpTransferReqApprEntity T WHERE T.fromProjMstrEntity.projectId in :toProjIds AND"
            + " (T.reqUserMstrEntity.userId=:apprUserId OR T.apprUserMstrEntity.userId=:apprUserId) AND"
            + " T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmpTransferReqApprEntity> findEmpAllTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("apprUserId") Long apprUserId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

}
