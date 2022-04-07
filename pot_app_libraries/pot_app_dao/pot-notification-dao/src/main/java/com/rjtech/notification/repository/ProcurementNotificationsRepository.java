package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.notification.model.ProcurementNotificationsEntity;

public interface ProcurementNotificationsRepository
        extends NotificationsRepository<ProcurementNotificationsEntity, Long> {

    @Query("SELECT P FROM ProcurementNotificationsEntity P WHERE (:id is null or P.id=:id) "
            + "AND (:projId IS NULL OR P.projId.projectId=:projId)" + "AND  P.status=:status")
    List<ProcurementNotificationsEntity> findProcurementNotifications(@Param("id") Long id,
            @Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT N FROM ProcurementNotificationsEntity N  WHERE N.projId.projectId=:projId "
    		+ "AND (:code IS NULL OR N.code=:code) "
    		//+ "AND (:notificationStatus IS NOT NULL AND N.notificationStatus=:notificationStatus )  "
    		+ "AND (:notificationStatus IS NULL OR N.notificationStatus=:notificationStatus) "
    		+ "AND N.status=:status "
    		+ "AND TRUNC(N.date) between :fromDate AND :toDate ORDER BY N.date DESC")
    List<ProcurementNotificationsEntity> findProcureNotifications1(@Param("projId") Long projId,
            @Param("code") String code, @Param("notificationStatus") String notificationStatus,
            @Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT N FROM ProcurementNotificationsEntity N  WHERE (:code IS NULL OR N.code=:code) "
    		//+ "AND (:notificationStatus IS NOT NULL AND N.notificationStatus=:notificationStatus )  "
    		+ "AND (:notificationStatus IS NULL OR N.notificationStatus=:notificationStatus) "
    		+ "AND N.status=:status "
    		+ "AND TRUNC(N.date) between :fromDate AND :toDate ORDER BY N.date DESC")
    List<ProcurementNotificationsEntity> findProcureNotificationsForAll(@Param("code") String code, 
            @Param("notificationStatus") String notificationStatus,@Param("status") Integer status, 
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Modifying
    @Query("UPDATE ProcurementNotificationsEntity P SET  P.notificationStatus=:notificationStatus WHERE P.id = :id")
    public void updateNotificationStatus(@Param("id") Long id, @Param("notificationStatus") String notificationStatus);

    @Query("SELECT COUNT(T) FROM WorkDairyNotificationsEntity T  WHERE (:clientId IS NULL OR T.clientId.clientId=:clientId) AND T.status=1 AND T.createdOn between :fromDate AND :toDate")
    Integer countProcurment(@Param("clientId") Long clientId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT P FROM ProcurementNotificationsEntity P WHERE P.preContractEntity.id=:contractId AND  P.status=:status ORDER BY P.id")
    ProcurementNotificationsEntity findPreContractNotificationDetails(@Param("contractId") Long contractId,
            @Param("status") Integer status);

}
