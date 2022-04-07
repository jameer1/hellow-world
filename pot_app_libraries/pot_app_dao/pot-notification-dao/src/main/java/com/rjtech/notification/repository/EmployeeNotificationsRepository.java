package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.EmployeeNotificationsEntity;

@Repository
public interface EmployeeNotificationsRepository extends NotificationsRepository<EmployeeNotificationsEntity, Long> {

    @Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE ((T.notifyStatus=:notificationPendingStatus) OR "
            + "(T.notifyStatus=:notificationApprStatus) OR (T.notifyStatus=:notificationRejtStatus)) AND "
            + "T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmployeeNotificationsEntity> findEmployeeNotificationsAll(@Param("notificationPendingStatus") String notificationPendingStatus, 
    		@Param("notificationApprStatus") String notificationApprStatus, @Param("notificationRejtStatus") String notificationRejtStatus, 
    		@Param("fromDate") Date fromDate, 
            @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE (T.projMstrEntity.projectId=:projId) AND ((T.notifyStatus=:notificationPendingStatus) OR "
            + "(T.notifyStatus=:notificationApprStatus) OR (T.notifyStatus=:notificationRejtStatus)) AND "
            + "(T.notificationMsg!=:notificationAddlTimeReqStatus) AND  T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmployeeNotificationsEntity> findEmployeeNotificationsAllByProjId(@Param("projId") Long projId,
    		@Param("notificationPendingStatus") String notificationPendingStatus, @Param("notificationApprStatus") String notificationApprStatus, 
    		@Param("notificationRejtStatus") String notificationRejtStatus, @Param("notificationAddlTimeReqStatus") String notificationAddlTimeReqStatus, 
    		@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE (T.notifyStatus=:notifyStatus)"
            + " AND (T.notificationMsg!=:additionalTime) AND  T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmployeeNotificationsEntity> findEmployeeNotifications(@Param("notifyStatus") String notifyStatus, 
            @Param("additionalTime") String additionalTime, @Param("fromDate") Date fromDate, 
            @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE (T.projMstrEntity.projectId=:projId) AND (T.notifyStatus=:notifyStatus)"
            + " AND (T.notificationMsg!=:additionalTime) AND  T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmployeeNotificationsEntity> findEmployeeNotificationsByProjectId(@Param("projId") Long projId,
            @Param("notifyStatus") String notifyStatus, @Param("additionalTime") String additionalTime,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE  T.projMstrEntity.projectId=:projId AND T.notifyStatus=:notifyStatus  AND T.notificationMsg=:type")
    List<EmployeeNotificationsEntity> findEmployeeNotificationsByProjId(@Param("projId") Long projId,
            @Param("notifyStatus") String notifyStatus, @Param("type") String type);

    @Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE (T.projMstrEntity.projectId=:projId) AND (T.notifyStatus=:notifyStatus)"
            + " AND (T.notificationMsg=:additionalTime) AND  T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmployeeNotificationsEntity> findEmployeeNotificationsForAdditionalTime(@Param("projId") Long projId,
            @Param("notifyStatus") String notifyStatus, @Param("additionalTime") String additionalTime,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM EmployeeNotificationsEntity T  WHERE (T.projMstrEntity.projectId=:projId) AND (T.notifyStatus=:notifyStatus)"
            + " AND (T.notificationMsg=:additionalTime) AND  T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<EmployeeNotificationsEntity> findEmployeeNotificationsForAdditionalTime1(@Param("projId") Long projId,
            @Param("notifyStatus") String notifyStatus, @Param("additionalTime") String additionalTime,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Modifying
    @Query("UPDATE EmployeeNotificationsEntity T SET  T.notifyStatus=:notifyStatus WHERE T.id = :id AND T.empStatus=:empStatus")
    public void updateNotificationStatus(@Param("id") Long id, @Param("notifyStatus") String notifyStatus,
            @Param("empStatus") String empStatus);

    @Query("SELECT COUNT(T) FROM EmployeeNotificationsEntity T  WHERE (:clientId IS NULL OR T.clientId.clientId=:clientId)"
            + " AND (T.apprUserId.userId=:userId OR  T.reqUserId.userId=:userId) "
            + " AND   T.status=1 AND T.createdOn between :fromDate AND :toDate")
    Integer countEmployeeTransfer(@Param("clientId") Long clientId, @Param("userId") Long userId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
