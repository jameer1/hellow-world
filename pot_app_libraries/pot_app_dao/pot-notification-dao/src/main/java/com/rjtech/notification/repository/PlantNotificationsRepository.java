package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.PlantNotificationsEntity;

@Repository
public interface PlantNotificationsRepository extends NotificationsRepository<PlantNotificationsEntity, Long> {

    @Query("SELECT T FROM PlantNotificationsEntity T  WHERE "
            + "(T.notificationStatus=:notificationStatus )  AND (T.notificationMsg!=:additionalTime) AND "
            + "T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<PlantNotificationsEntity> findPlantNotifications(
            @Param("notificationStatus") String notificationStatus, @Param("additionalTime") String additionalTime,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM PlantNotificationsEntity T  WHERE "
            + "((T.notificationStatus=:notificationPendingStatus ) OR (T.notificationStatus=:notificationApprStatus )"
            + "OR  (T.notificationStatus=:notificationRejtStatus)) AND "
            + "T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<PlantNotificationsEntity> findPlantNotificationsAll(
            @Param("notificationPendingStatus") String notificationPendingStatus, @Param("notificationApprStatus") String notificationApprStatus,
            @Param("notificationRejtStatus") String notificationRejtStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM PlantNotificationsEntity T  WHERE (T.projMstrEntity.projectId=:projId) AND "
            + "((T.notificationStatus=:notificationPendingStatus ) OR (T.notificationStatus=:notificationApprStatus )"
            + "OR  (T.notificationStatus=:notificationRejtStatus) OR (T.notificationStatus=:notificationAddlTimeReqStatus)) AND "
            + "T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<PlantNotificationsEntity> findEmployeeNotificationsAllByProjId(@Param("projId") Long projId, 
            @Param("notificationPendingStatus") String notificationPendingStatus, @Param("notificationApprStatus") String notificationApprStatus,
            @Param("notificationRejtStatus") String notificationRejtStatus, @Param("notificationAddlTimeReqStatus") String notificationAddlTimeReqStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM PlantNotificationsEntity T  WHERE (T.projMstrEntity.projectId=:projId) AND "
            + "(T.notificationStatus=:notificationStatus )  AND (T.notificationMsg!=:additionalTime) AND "
            + "T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<PlantNotificationsEntity> findPlantNotificationsByProjectId(@Param("projId") Long projId,
            @Param("notificationStatus") String notificationStatus, @Param("additionalTime") String additionalTime,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT T FROM PlantNotificationsEntity T  WHERE "
            + "(T.notificationStatus=:notificationStatus )  AND (T.notificationMsg=:additionalTime) AND "
            + "T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<PlantNotificationsEntity> findPlantNotificationsForAdditionalTime(
            @Param("notificationStatus") String notificationStatus, @Param("additionalTime") String additionalTime,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM PlantNotificationsEntity T  WHERE (T.projMstrEntity.projectId=:projId) AND "
            + "(T.notificationStatus=:notificationStatus )  AND (T.notificationMsg=:additionalTime) AND "
            + "T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<PlantNotificationsEntity> findPlantNotificationsForAdditionalTimeByProjectId(@Param("projId") Long projId,
            @Param("notificationStatus") String notificationStatus, @Param("additionalTime") String additionalTime,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Modifying
    @Query("UPDATE PlantNotificationsEntity T SET  T.notificationStatus=:notificationStatus WHERE T.id = :id")
    public void updateNotificationStatus(@Param("id") Long id, @Param("notificationStatus") String notificationStatus);

    @Query("SELECT T FROM PlantNotificationsEntity T  WHERE T.projMstrEntity.projectId=:projId AND T.notificationStatus=:notificationStatus AND   T.notificationMsg=:additionalTime")
    List<PlantNotificationsEntity> findPlantNotificationsByProjId(@Param("projId") Long projId,
            @Param("notificationStatus") String notifyStatus, @Param("additionalTime") String additionalTime);
}
