package com.rjtech.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.TimeSheetAdditionalTimeEntity;

@Repository
public interface TimeSheetAdditionalTimeRepository extends JpaRepository<TimeSheetAdditionalTimeEntity, Long> {
	
    @Query("SELECT T FROM com.rjtech.notification.model.TimeSheetAdditionalTimeEntity T WHERE (T.projId=:projId) AND (T.notificationStatus=:notificationStatus) "
            + "AND (T.notificationMsg=:notificationMsg) AND (T.status=:status)")
    List<TimeSheetAdditionalTimeEntity> findTimeSheetNotificationsByStatus(@Param("projId") Long projId,
            @Param("notificationStatus") String notificationStatus, @Param("notificationMsg") String notificationMsg,
            @Param("status") Integer status);
    /*
    @Query("SELECT T FROM TimeSheetAdditionalTimeEntity T WHERE (T.notificationStatus=:notificationStatus)  "
    		+ "AND (T.notificationMsg=:notificationMsg) AND (T.status=:status)")
    List<TimeSheetAdditionalTimeEntity> findTimeSheetNotificationsByStatusForCrew1(@Param("notificationStatus") String notificationStatus, 
    		@Param("notificationMsg") String notificationMsg, @Param("status") Integer status);
    */
    @Query("SELECT T FROM com.rjtech.notification.model.TimeSheetAdditionalTimeEntity T WHERE T.projId=:projId AND T.empId is not null AND T.notificationStatus=:notificationStatus "
    		+ "AND T.notificationMsg=:notificationMsg AND T.status=:status")
    List<TimeSheetAdditionalTimeEntity> findTimeSheetNotificationsByStatusForCrew(@Param("projId") Long projId, 
    		@Param("notificationStatus") String notificationStatus, @Param("notificationMsg") String notificationMsg, 
    		@Param("status") Integer status);
    
    /*
    @Query("SELECT T FROM TimeSheetAdditionalTimeEntity T WHERE (T.crewId=null) AND (T.notificationStatus=:notificationStatus) "
            + "AND (T.notificationMsg=:notificationMsg) AND (T.status=:status)")
    List<TimeSheetAdditionalTimeEntity> findTimeSheetNotificationsByStatusForCrew(@Param("notificationStatus") String notificationStatus, 
    		@Param("notificationMsg") String notificationMsg, @Param("status") Integer status);
   */
}
