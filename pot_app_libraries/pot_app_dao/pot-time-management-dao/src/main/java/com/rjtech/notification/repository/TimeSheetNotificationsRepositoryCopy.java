package com.rjtech.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.TimeSheetNotificationsEntity;

//import com.rjtech.notification.model.TimeSheetNotificationsEntityCopy;

@Repository
public interface TimeSheetNotificationsRepositoryCopy extends JpaRepository<TimeSheetNotificationsEntity, Long> {

    @Modifying
    @Query("UPDATE TimeSheetNotificationsEntity T SET  T.notificationStatus=:apprStatus, T.timeSheetEntity.apprComments=:apprComments WHERE T.timeSheetEntity.projMstrEntity.code = :code")
    void findOneTimeSheetNotficationSubmit(@Param("code") String code, @Param("apprComments") String apprComments,
            @Param("apprStatus") String apprStatus);

    @Query("SELECT T from TimeSheetNotificationsEntity T WHERE (T.timeSheetEntity.id=:timeSheetId) AND (T.notificationStatus=:notificationStatus) AND (T.notificationMsg=:notificationMsg)")
    TimeSheetNotificationsEntity findTimeSheetNotificationsByMsg(@Param("timeSheetId") Long timeSheetId,@Param("notificationStatus") String notificationStatus,@Param("notificationMsg") String notificationMsg);
    
    @Query("SELECT T FROM TimeSheetNotificationsEntity T  WHERE T.timeSheetEntity.id=:timeSheetId "
    		+ "AND (T.notificationStatus=:notificationStatus) AND (T.notificationMsg=:notificationMsg)")
    List<TimeSheetNotificationsEntity> findNotificationId(@Param("timeSheetId") Long timeSheetId,
    @Param("notificationStatus") String notificationStatus,@Param("notificationMsg") String notificationMsg);
    
    @Modifying
    @Query("UPDATE TimeSheetNotificationsEntity T SET T.notificationStatus=:apprStatus WHERE T.id=:notificationId")
    void updateNotificationStatus(@Param("apprStatus") String apprStatus, @Param("notificationId") Long notificationId);
}