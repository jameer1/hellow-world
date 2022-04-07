package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.notification.model.MaterialNotificationsEntity;

public interface MaterialNotificationsRepository extends NotificationsRepository<MaterialNotificationsEntity, Long> {
	
	/*
    @Query("SELECT T FROM MaterialNotificationsEntity T  WHERE (:clientId IS NOT NULL AND T.clientId.clientId=:clientId) AND (:projId IS NULL OR T.projId.projectId=:projId) AND  (:code IS NULL OR T.code=:code) AND (:notificationStatus IS NOT NULL AND T.notificationStatus=:notificationStatus )  AND   T.status=:status AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
    List<MaterialNotificationsEntity> findMaterialNotifications(@Param("clientId") Long clientId,
            @Param("projId") Long projId, @Param("code") String code,
            @Param("notificationStatus") String notificationStatus, @Param("status") Integer status,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    */
	
	 @Query("SELECT T FROM MaterialNotificationsEntity T  WHERE ((T.notificationStatus=:notificationPendingStatus) "
	 		+ " OR (T.notificationStatus=:notificationApprStatus) OR (T.notificationStatus=:notificationRejtStatus)) "
	 		+ " AND T.status=:status AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
	    List<MaterialNotificationsEntity> findMaterialNotificationsAll(@Param("notificationPendingStatus") String notificationPendingStatus, 
	    		@Param("notificationApprStatus") String notificationApprStatus, @Param("notificationRejtStatus") String notificationRejtStatus, 
	    		@Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	 
	 @Query("SELECT T FROM MaterialNotificationsEntity T  WHERE (:clientId IS NOT NULL AND T.clientId.clientId=:clientId) AND (:projId IS NULL OR T.projId.projectId=:projId) AND  (:code IS NULL OR T.code=:code) AND (:notificationStatus IS NOT NULL AND T.notificationStatus=:notificationStatus )  AND   T.status=:status AND T.createdOn between :fromDate AND :toDate ORDER BY T.updatedOn DESC")
	    List<MaterialNotificationsEntity> findMaterialNotifications(@Param("clientId") Long clientId,
	            @Param("projId") Long projId, @Param("code") String code,
	            @Param("notificationStatus") String notificationStatus, @Param("status") Integer status,
	            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Modifying
    @Query("UPDATE MaterialNotificationsEntity T SET  T.notificationStatus=:notificationStatus WHERE T.id = :id")
    public void updateNotificationStatus(@Param("id") Long id, @Param("notificationStatus") String notificationStatus);

    @Query("SELECT T FROM MaterialNotificationsEntity T  WHERE  T.fromProjId.projectId=:projId  AND T.notificationStatus=:notificationStatus "
    		+ " AND T.status=:status and T.notificationMsg=:notificationMsg")
    List<MaterialNotificationsEntity> findMaterialNotificationsByProjId(@Param("projId") Long projId,
            @Param("notificationStatus") String notificationStatus, @Param("status") Integer status, @Param("notificationMsg") String notificationMsg);

}
