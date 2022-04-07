package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.WorkDairyNotificationsEntity;

@Repository
public interface WorkDairyNotificationRepository extends NotificationsRepository<WorkDairyNotificationsEntity, Long> {

    @Query("SELECT T FROM WorkDairyNotificationsEntity T  WHERE (T.workDairyEntity.projId.projectId=:projectId)"
            + " AND  ( T.notificationMsg!=:notificationMsg) " + "AND (T.notificationStatus=:notificationStatus)"
            + " AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<WorkDairyNotificationsEntity> findWorkDairyNotifications(@Param("projectId") Long projectId,
            @Param("notificationMsg") String notificationMsg, @Param("notificationStatus") String notifyStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM WorkDairyNotificationsEntity T "
    		//+ " INNER JOIN WorkDairyAdditionalTimeEntity T1 "
    		//+ " on T1.workDairyEntity.id = T.workDairyEntity.id"
    		+ " WHERE ((T.notificationMsg!=:notificationAddlTimeMsg) OR (T.notificationMsg!=:notificationSubmitTimeMsg)) "
            + " AND ((T.notificationStatus=:notificationPendingStatus) OR (T.notificationStatus=:notificationApprIntlStatus) "
            + " OR (T.notificationStatus=:notificationApprClntStatus)  OR (T.notificationStatus=:notificationAddlTimeApprStatus))"
            + " AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<WorkDairyNotificationsEntity> findWorkDairyNotificationsAll(
            @Param("notificationAddlTimeMsg") String notificationAddlTimeMsg, @Param("notificationSubmitTimeMsg") String notificationSubmitTimeMsg,
            @Param("notificationPendingStatus") String notificationPendingStatus, @Param("notificationApprIntlStatus") String notificationApprIntlStatus, 
            @Param("notificationApprClntStatus") String notificationApprClntStatus, @Param("notificationAddlTimeApprStatus") String notificationAddlTimeApprStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM WorkDairyNotificationsEntity T  WHERE (T.workDairyEntity.projId.projectId=:projectId) AND "
    		+ "((T.notificationMsg!=:notificationAddlTimeMsg) OR (T.notificationMsg!=:notificationSubmitTimeMsg))"
            + "AND ((T.notificationStatus=:notificationPendingStatus) OR (T.notificationStatus=:notificationApprIntlStatus) "
            + " OR (T.notificationStatus=:notificationApprClntStatus)  OR (T.notificationStatus=:notificationAddlTimeApprStatus))"
            + " AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<WorkDairyNotificationsEntity> findWorkDairyNotificationsAllByProjId(@Param("projectId") Long projectId,
    		@Param("notificationAddlTimeMsg") String notificationAddlTimeMsg, @Param("notificationSubmitTimeMsg") String notificationSubmitTimeMsg,
            @Param("notificationPendingStatus") String notificationPendingStatus, @Param("notificationApprIntlStatus") String notificationApprIntlStatus, 
            @Param("notificationApprClntStatus") String notificationApprClntStatus, @Param("notificationAddlTimeApprStatus") String notificationAddlTimeApprStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT T FROM WorkDairyNotificationsEntity T  WHERE (T.workDairyEntity.projId.projectId=:projectId)"
            + " AND  (T.notificationMsg = :notificationMsg ) " + "AND (T.notificationStatus = :notificationStatus )"
            + " AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<WorkDairyNotificationsEntity> findWorkDairyNotificationsByInternalApproval(@Param("projectId") Long projectId,
            @Param("notificationStatus") String notificationMsg, @Param("notificationMsg") String notifyStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT COUNT(T) FROM WorkDairyNotificationsEntity T  WHERE (T.clientId.clientId=:clientId) AND T.status=1 AND T.createdOn between :fromDate AND :toDate")
    Integer countWorkDairy(@Param("clientId") Long clientId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT T FROM WorkDairyNotificationsEntity T WHERE T.notificationStatus=:notificationStatus AND T.notificationMsg=:notificationMsg AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<WorkDairyNotificationsEntity> findWorkDairyNotificationsNoProjId(@Param("notificationStatus") String notificationStatus,
    			@Param("notificationMsg") String notificationMsg, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
