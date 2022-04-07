package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.EmployeeNotificationsEntity;

@Repository
public interface EmployeeLeaveNotificationsRepository
        extends NotificationsRepository<EmployeeNotificationsEntity, Long> {

	@Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE ((T.notifyStatus=:notificationPendingStatus) "
			+ " OR (T.notifyStatus=:notificationApprStatus) OR (T.notifyStatus=:notificationRejtStatus) )  AND (T.empStatus=:empStatus) "
			+ " AND T.status=:status AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmployeeNotificationsEntity> findEmployeeLeaveNotificationsAll(@Param("notificationPendingStatus") String notificationPendingStatus, 
    		@Param("notificationApprStatus") String notificationApprStatus, @Param("notificationRejtStatus") String notificationRejtStatus, 
            @Param("empStatus") String empStatus,  @Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
	
	@Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE (T.projMstrEntity.projectId=:projId) AND (T.notifyStatus=:notifyStatus )  AND (T.empStatus=:empStatus) AND T.status=:status AND T.createdOn between :fromDate AND :toDate  ORDER BY T.updatedOn DESC")
    List<EmployeeNotificationsEntity> findEmployeeLeaveNotificationsAllByProjId(@Param("projId") Long projId,
            @Param("notifyStatus") String notifyStatus, @Param("empStatus") String empStatus,
            @Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	@Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE (T.projMstrEntity.projectId=:projId) AND (T.notifyStatus=:notifyStatus )  AND (T.empStatus=:empStatus) AND T.status=:status AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmployeeNotificationsEntity> findEmployeeLeaveNotifications(@Param("projId") Long projId,
            @Param("notifyStatus") String notifyStatus, @Param("empStatus") String empStatus,
            @Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE T.projMstrEntity.projectId=:projId AND T.notifyStatus=:notifyStatus AND T.notificationMsg=:type AND T.empStatus=:empStatus AND  T.status=:status")
    List<EmployeeNotificationsEntity> findEmployeeLeaveNotificationsByProjId(@Param("projId") Long projId,
            @Param("notifyStatus") String notifyStatus, @Param("empStatus") String empStatus,
            @Param("type") String type, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE EmployeeNotificationsEntity T SET  T.notifyStatus=:notifyStatus WHERE T.id = :id AND T.empStatus=:empStatus")
    public void updateNotificationStatus(@Param("id") Long id, @Param("notifyStatus") String notifyStatus,
            @Param("empStatus") String empStatus);

    @Modifying
    @Query("UPDATE EmployeeNotificationsEntity T SET T.type='Approver decision notification For leave Request', T.notifyStatus='Addtional Time For Approved' WHERE T.id =:notificationId")
    void addtionalTimeApproved(@Param("notificationId") Long notificationId);

}
