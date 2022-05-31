package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.SoeNotificationsEntity;

@Repository
public interface SoeNotificationRepository extends NotificationsRepository<SoeNotificationsEntity, Long> {
     
	@Query("SELECT S FROM SoeNotificationsEntity S WHERE S.projSOEItemEntity.projMstrEntity.projectId in :projIds AND"
            + " S.createdOn between :fromDate AND :toDate ORDER BY S.updatedOn DESC")
    List<SoeNotificationsEntity> findSoeNotificationsByProjId(@Param("projIds") List<Long> projIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
    @Query("SELECT S FROM SoeNotificationsEntity S WHERE "
            + " S.createdOn between :fromDate AND :toDate ORDER BY S.updatedOn DESC")
    List<SoeNotificationsEntity> findSoeNotificationsAll(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	 
    @Query("SELECT COUNT(S) FROM SoeNotificationsEntity S  WHERE (:clientId IS NULL OR S.clientId.clientId=:clientId)"
            + " AND (S.updatedBy.userId=:userId OR  S.updatedBy.userId=:userId) "
            + " AND   S.status=1 AND S.createdOn between :fromDate AND :toDate")
    Integer countSoe(@Param("clientId") Long clientId, @Param("userId") Long userId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT S FROM com.rjtech.notification.model.SoeNotificationsEntity S WHERE "
            + " S.projSOEItemEntity.id =:notifyId")
    List<SoeNotificationsEntity> findSoe(@Param("notifyId") Long notifyId);
    
    @Query("SELECT S FROM SoeNotificationsEntity S WHERE S.projSOEItemEntity.projMstrEntity.projectId =:projId AND"
            + " S.notificationMsg =:notifyStatus")
    List<SoeNotificationsEntity> findSoeNotificationsByNotifyStatus(@Param("projId") Long projId, @Param("notifyStatus") String notifyStatus);
}
