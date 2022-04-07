package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.notification.model.TimeSheetNotificationsEntity;

public interface TimeSheetNotificationsRepository extends NotificationsRepository<TimeSheetNotificationsEntity, Long> {

    @Query("SELECT T FROM TimeSheetNotificationsEntity T  WHERE T.timeSheetEntity.projMstrEntity.projectId=:projId AND "
            + "lower(T.notificationStatus) = lower(:notificationStatus) AND lower(T.notificationMsg) = lower(:notificationMsg) AND"
            + " (T.timeSheetEntity.weekStartDate between :fromDate AND :toDate OR T.timeSheetEntity.weekEndDate between :fromDate AND :toDate) ORDER BY T.updatedOn DESC")
    List<TimeSheetNotificationsEntity> findTimeSheetNotifications(@Param("projId") Long projId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("notificationStatus") String notificationStatus, @Param("notificationMsg") String notificationMsg);
    
    @Query("SELECT T FROM TimeSheetNotificationsEntity T  WHERE lower(T.notificationStatus) = lower(:notificationStatus)  "
            + "AND lower(T.notificationMsg) = lower(:notificationMsg) AND "
            + "(T.timeSheetEntity.weekStartDate between :fromDate AND :toDate OR T.timeSheetEntity.weekEndDate between :fromDate AND :toDate) ORDER BY T.updatedOn DESC")
    List<TimeSheetNotificationsEntity> findTimeSheetNotifications(@Param("fromDate") Date fromDate, 
    		@Param("toDate") Date toDate, @Param("notificationStatus") String notificationStatus, 
    		@Param("notificationMsg") String notificationMsg);
    
    @Query("SELECT T FROM TimeSheetNotificationsEntity T  WHERE T.timeSheetEntity.projMstrEntity.projectId=:projId AND  " 
    		+ "(lower(T.notificationStatus) = lower(:notificationPendingStatus) OR lower(T.notificationStatus) = lower(:notificationApprovedStatus)) "
            + "AND (lower(T.notificationMsg) = lower(:notificationAddlTimeMsg) OR lower(T.notificationMsg) = lower(:notificationSubmitTimeMsg)) AND "
            + "(T.timeSheetEntity.weekStartDate between :fromDate AND :toDate OR T.timeSheetEntity.weekEndDate between :fromDate AND :toDate) ORDER BY T.updatedOn DESC")
    List<TimeSheetNotificationsEntity> findTimeSheetNotificationsAll(@Param("projId") Long projId, @Param("fromDate") Date fromDate, 
    		@Param("toDate") Date toDate, @Param("notificationPendingStatus") String notificationPendingStatus, 
    		@Param("notificationApprovedStatus") String notificationApprovedStatus, @Param("notificationAddlTimeMsg") String notificationAddlTimeMsg,
    		@Param("notificationSubmitTimeMsg") String notificationSubmitTimeMsg);
    
    @Query("SELECT T FROM TimeSheetNotificationsEntity T  WHERE (lower(T.notificationStatus) = lower(:notificationPendingStatus) " 
    		+ "OR lower(T.notificationStatus) = lower(:notificationApprovedStatus)) "
            + "AND (lower(T.notificationMsg) = lower(:notificationAddlTimeMsg) OR lower(T.notificationMsg) = lower(:notificationSubmitTimeMsg)) AND "
            + "(T.timeSheetEntity.weekStartDate between :fromDate AND :toDate OR T.timeSheetEntity.weekEndDate between :fromDate AND :toDate) ORDER BY T.updatedOn DESC")
    List<TimeSheetNotificationsEntity> findTimeSheetNotificationsAll(@Param("fromDate") Date fromDate, 
    		@Param("toDate") Date toDate, @Param("notificationPendingStatus") String notificationPendingStatus, 
    		@Param("notificationApprovedStatus") String notificationApprovedStatus, @Param("notificationAddlTimeMsg") String notificationAddlTimeMsg,
    		@Param("notificationSubmitTimeMsg") String notificationSubmitTimeMsg);

    @Query("SELECT COUNT(T) FROM TimeSheetNotificationsEntity T  WHERE (T.clientId.clientId=:clientId)"
            + " AND   T.status=1 AND T.createdOn between :fromDate AND :toDate")
    Integer countTimeSheet(@Param("clientId") Long clientId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

}
