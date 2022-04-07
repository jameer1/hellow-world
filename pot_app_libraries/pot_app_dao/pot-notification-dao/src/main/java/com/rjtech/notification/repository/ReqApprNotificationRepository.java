package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.ReqApprNotificationEntity;

@Repository
public interface ReqApprNotificationRepository extends JpaRepository<ReqApprNotificationEntity, Long> {

	/*
    @Query("SELECT N FROM ReqApprNotificationEntity N  WHERE (:projId IS NULL OR N.projId=:projId) "
    		+ "AND  (:notifyCode IS NULL OR N.notifyCode=:notifyCode) AND (:notificationStatus IS NOT NULL "
    		+ "AND N.notificationStatus=:notificationStatus )  AND N.status=:status "
    		+ "AND N.createdOn between :fromDate AND :toDate ORDER BY N.updatedOn DESC")
    List<ReqApprNotificationEntity> findProcureNotifications(@Param("projId") Long projId,
            @Param("notifyCode") String notifyCode, @Param("notificationStatus") String notificationStatus,
            @Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    */
	 @Query("SELECT N FROM ReqApprNotificationEntity N  WHERE N.projId.projectId=:projId "
	    		+ "AND (:notifyCode IS NULL OR N.notifyCode=:notifyCode) "
	    		//+ "AND (:notificationStatus IS NOT NULL AND N.notificationStatus=:notificationStatus )  "
	    		+ "AND (:notificationStatus IS NULL OR N.notificationStatus=:notificationStatus) "
	    		+ "AND N.status=:status "
	    		+ "AND N.createdOn between :fromDate AND :toDate ORDER BY N.updatedOn DESC")
	    List<ReqApprNotificationEntity> findProcureNotifications(@Param("projId") Long projId,
	            @Param("notifyCode") String notifyCode, @Param("notificationStatus") String notificationStatus,
	            @Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT N FROM ReqApprNotificationEntity N  WHERE (:projId IS NULL OR N.projId.projectId=:projId) AND  (:notificationStatus IS NOT NULL AND N.notificationStatus=:notificationStatus )  AND   N.status=:status")
    List<ReqApprNotificationEntity> findProcureNotificationsByProjId(@Param("projId") Long projId,
            @Param("notificationStatus") String notificationStatus, @Param("status") Integer status);
}
