package com.rjtech.register.repository.emp;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpLeaveReqApprEntity;
import com.rjtech.register.emp.model.EmpTransferReqApprEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpLeaveReqApprRepository extends RegisterBaseRepository<EmpLeaveReqApprEntity, Long> {

    @Query("SELECT T FROM EmpLeaveReqApprEntity T WHERE T.empRegisterDtlEntity.id=:empRegId AND ( ( :apprStatus IS NOT NULL AND T.apprStatus=:apprStatus ) OR ( :apprStatus IS NULL AND T.apprStatus IS NOT NULL ) ) AND T.status=:status AND"
            + " T.reqDate between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    public List<EmpLeaveReqApprEntity> findEmpLeaveReqs(@Param("empRegId") Long empRegId,
            @Param("status") Integer status, @Param("apprStatus") String apprStatus, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT T FROM EmpLeaveReqApprEntity T WHERE UPPER(T.apprStatus)=:apprStatus AND T.reqDate between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    public List<EmpLeaveReqApprEntity> findEmpLeaveApprs(@Param("apprStatus") String apprStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    	//to get the list of leave approval records for the selected employee to the approval....
    @Query("SELECT T FROM EmpLeaveReqApprEntity T WHERE T.apprStatus is not null and T.reqDate between :fromDate AND :toDate AND T.projMstrEntity.projectId=:projId AND T.createdBy.userId=:getUserId AND T.empRegisterDtlEntity.id=:empRegId ORDER BY T.updatedOn DESC")
    public List<EmpLeaveReqApprEntity> findAllEmpLeaveApprs(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("projId") Long projId, @Param("getUserId") Long getUserId, @Param("empRegId") Long empRegId);

    @Query("SELECT T FROM EmpTransferReqApprEntity T  WHERE T.toProjMstrEntity.projectId in :toProjIds  AND ( (:reqUserId IS NOT NULL AND T.reqUserMstrEntity.userId=:reqUserId ) OR :reqUserId IS NULL)  AND T.status=:status AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmpTransferReqApprEntity> findEmpReqTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("reqUserId") Long reqUserId, @Param("status") Integer status, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT T FROM EmpTransferReqApprEntity T  WHERE T.toProjMstrEntity.projectId in :toProjIds AND ( (:apprUserId IS NOT NULL AND T.apprUserMstrEntity.userId=:apprUserId ) OR :apprUserId IS NULL)  AND  (T.apprStatus IS NOT NULL AND T.apprStatus=:apprStatus)  AND   T.status=:status AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmpTransferReqApprEntity> findEmpApprTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("apprUserId") Long apprUserId, @Param("apprStatus") String apprStatus,
            @Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT T FROM EmpTransferReqApprEntity T  WHERE T.toProjMstrEntity.projectId in :toProjIds AND ( (:apprUserId IS NOT NULL AND T.apprUserMstrEntity.userId=:apprUserId ) OR :apprUserId IS NULL)    AND   T.status=:status AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmpTransferReqApprEntity> findEmpAllTranfers(@Param("toProjIds") List<Long> toProjIds,
            @Param("apprUserId") Long apprUserId, @Param("status") Integer status, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT T FROM EmpLeaveReqApprEntity T WHERE T.empRegisterDtlEntity.id=:empRegId AND T.latest=true  AND ( T.apprStatus='Not Approved'   OR   T.apprStatus='Pending For Approval' )")
    List<EmpLeaveReqApprEntity> findLatestEmpLeaveRequest(@Param("empRegId") Long empRegId);
    
    @Query("SELECT T FROM EmpLeaveReqApprEntity T WHERE T.empNotificationsEntity.id=:notifyId")
    public EmpLeaveReqApprEntity findEmpLeaveReqApprRecord(@Param("notifyId") Long notifyId);

    @Query("SELECT T FROM EmpLeaveReqApprEntity T WHERE T.apprStatus is not null and T.reqDate between :fromDate AND :toDate  AND T.projMstrEntity.projectId=:projId AND T.empRegisterDtlEntity.id=:empRegId ORDER BY T.updatedOn DESC")
    public List<EmpLeaveReqApprEntity> findAllEmpLeaveApprs(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("projId") Long projId, @Param("empRegId") Long empRegId);
    
    @Query("SELECT T FROM EmpLeaveReqApprEntity T WHERE UPPER(T.apprStatus)=:apprStatus AND T.reqDate between :fromDate AND :toDate AND T.projMstrEntity.projectId=:projId AND T.empRegisterDtlEntity.id=:empRegId ORDER BY T.updatedOn DESC")
    public List<EmpLeaveReqApprEntity> findEmpLeaveApprs(@Param("apprStatus") String apprStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("projId") Long projId, @Param("empRegId") Long empRegId);

}
