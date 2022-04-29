package com.rjtech.register.repository.emp;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import com.rjtech.register.EmpLeaveReqApprEntity;
import com.rjtech.register.emp.model.EmpLeaveReqApprEntity;
public interface EmpLeaveReqApprRepository extends NotificationsRepository<EmpLeaveReqApprEntity, Long> {

    @Query("SELECT T FROM EmpLeaveReqApprEntity T WHERE T.empNotificationsEntity.id=:notifyId")
    public EmpLeaveReqApprEntity findEmpLeaveReqApprRecord(@Param("notifyId") Long notifyId);
    
    @Query("SELECT T FROM EmpLeaveReqApprEntity T WHERE T.apprStatus is not null and T.reqDate between :fromDate AND :toDate  AND T.projMstrEntity.projectId=:projId AND T.empRegisterDtlEntity.id=:empRegId ORDER BY T.updatedOn DESC")
    public List<EmpLeaveReqApprEntity> findAllEmpLeaveApprs(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("projId") Long projId, @Param("empRegId") Long empRegId);
    
    @Query("SELECT T FROM EmpLeaveReqApprEntity T WHERE UPPER(T.apprStatus)=:apprStatus AND T.reqDate between :fromDate AND :toDate AND T.projMstrEntity.projectId=:projId AND T.empRegisterDtlEntity.id=:empRegId ORDER BY T.updatedOn DESC")
    public List<EmpLeaveReqApprEntity> findEmpLeaveApprs(@Param("apprStatus") String apprStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("projId") Long projId, @Param("empRegId") Long empRegId);

}
