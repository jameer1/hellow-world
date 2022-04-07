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

}
