/*
 * package com.rjtech.notification.repository;
 * 
 * import java.util.Date; import java.util.List;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.jpa.repository.Modifying; import
 * org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.query.Param; import
 * org.springframework.stereotype.Repository;
 * 
 * import com.rjtech.notification.model.TimeSheetNotificationsEntity;
 * 
 * //import com.rjtech.notification.model.TimeSheetNotificationsEntityCopy;
 * 
 * @Repository public interface TimeSheetNotificationsRepositoryCopy extends
 * JpaRepository<TimeSheetNotificationsEntity, Long> {
 * 
 * @Query("SELECT T FROM TimeSheetNotificationsEntity T  WHERE T.timeSheetEntity.id=:timeSheetId "
 * +
 * "AND (T.notificationStatus=:notificationStatus) AND (T.notificationMsg=:notificationMsg)"
 * ) List<TimeSheetNotificationsEntity> findNotificationId(@Param("timeSheetId")
 * Long timeSheetId,
 * 
 * @Param("notificationStatus") String
 * notificationStatus,@Param("notificationMsg") String notificationMsg);
 * 
 * @Modifying
 * 
 * @Query("UPDATE TimeSheetNotificationsEntity T SET T.notificationStatus=:apprStatus WHERE T.id=:notificationId"
 * ) void updateNotificationStatus(@Param("apprStatus") String
 * apprStatus, @Param("notificationId") Long notificationId); }
 * 
 * package com.rjtech.notification.repository;
 * 
 * import java.util.List;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.query.Param; import
 * org.springframework.stereotype.Repository;
 * 
 * import com.rjtech.notification.model.TimeSheetNotificationsEntityCopy;
 * 
 * @Repository public interface TimeSheetNotificationsRepositoryCopy extends
 * JpaRepository<TimeSheetNotificationsEntityCopy, Long> {
 * 
 * @Query("SELECT PGV FROM TimeSheetNotificationsRepositoryCopy T where T.id=:notificationId"
 * ) public TimeSheetNotificationsEntityCopy
 * indNotificationId(@Param("notificationId") Integer notificationId);
 * 
 * }
 */