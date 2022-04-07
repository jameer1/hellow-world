package com.rjtech.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.notification.model.EmployeeNotificationsEntity;
import com.rjtech.notification.model.EmployeeNotificationsEntityCopy;
//import com.rjtech.notification.model.EmployeeNotificationsEntityCopy;

public interface EmployeeNotificationsRepositoryCopy extends JpaRepository<EmployeeNotificationsEntity, Long> {

    @Modifying
    @Query("UPDATE EmployeeNotificationsEntity T SET T.notifyStatus=:notificationStatus, T.type=:type WHERE T.id = :notificationId ")
    void addtionalTimeRequestApproved(@Param("notificationId") Long notificationId, @Param("type") String type,
            @Param("notificationStatus") String notificationStatus);

    @Query("SELECT T FROM EmployeeNotificationsEntity T WHERE T.id=:id AND T.empStatus=:empStatus")
    public EmployeeNotificationsEntityCopy findEmpTransReqApprRecord(@Param("id") Long id, @Param("empStatus") String empStatus);
    
    @Query("SELECT T FROM EmployeeNotificationsEntity T WHERE T.id=:id AND T.empStatus=:empStatus")
    public EmployeeNotificationsEntity findEmpLeaveReqApprRecord(@Param("id") Long id, @Param("empStatus") String empStatus);
    
    
}
