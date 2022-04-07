package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.AttendenceNotificationsEntity;

@Repository
public interface AttendenceNotificationsRepository
        extends NotificationsRepository<AttendenceNotificationsEntity, Long> {

    @Query("SELECT T FROM AttendenceNotificationsEntity T  WHERE T.notificationStatus=:notificationStatus  "
            + " AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<AttendenceNotificationsEntity> findAttendenceNotifications(@Param("notificationStatus") String notificationStatus, 
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM AttendenceNotificationsEntity T  WHERE T.projCrewMstrEntity.projId.projectId=:projId"
            + " AND T.notificationStatus=:notificationStatus "
            + " AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<AttendenceNotificationsEntity> findAttendenceNotificationsByProjId(@Param("projId") Long projId,
            @Param("notificationStatus") String notificationStatus, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM AttendenceNotificationsEntity T  WHERE (lower(T.notificationStatus) = lower(:notificationPendingStatus) "  
    		+ " OR lower(T.notificationStatus) = lower(:notificationApprovedStatus)) "
            + " AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<AttendenceNotificationsEntity> findAttendenceNotificationsAll(
            @Param("notificationPendingStatus") String notificationPendingStatus, @Param("notificationApprovedStatus") String notificationApprovedStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM AttendenceNotificationsEntity T  WHERE T.projCrewMstrEntity.projId.projectId=:projId AND"
    		+ " (lower(T.notificationStatus) = lower(:notificationPendingStatus) "  
    		+ " OR lower(T.notificationStatus) = lower(:notificationApprovedStatus)) "
            + " AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<AttendenceNotificationsEntity> findAttendenceNotificationsAllByProjId(@Param("projId") Long projId, 
            @Param("notificationPendingStatus") String notificationPendingStatus, @Param("notificationApprovedStatus") String notificationApprovedStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT COUNT(T) FROM AttendenceNotificationsEntity T  WHERE  T.fromUserId.userId=:userId OR  T.toUserId.userId=:userId"
            + " AND   (T.status=1 AND T.createdOn between :fromDate AND :toDate)")
    Integer countAttendance(@Param("userId") Long userId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM AttendenceNotificationsEntity T  WHERE T.projCrewMstrEntity.projId.projectId=:projId"
            + " AND T.projCrewMstrEntity.id=:crewId "
    		+ " AND T.type=:type "
            + " AND (T.fromDate between :fromDate AND :toDate "
            + " OR T.toDate between :fromDate AND :toDate)")
    List<AttendenceNotificationsEntity> findAll(@Param("projId") Long projId,
            @Param("crewId") Long crewId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("type") String type);
    
    @Query("SELECT T FROM AttendenceNotificationsEntity T  WHERE T.projCrewMstrEntity.projId.projectId=:projId"
            + " AND T.notificationStatus=:notificationStatus "
            + " AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<AttendenceNotificationsEntity> findAttendenceAddlTimeNotifications(@Param("projId") Long projId,
            @Param("notificationStatus") String notificationStatus, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

}
