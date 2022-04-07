package com.rjtech.notification.repository.copy;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import com.rjtech.notification.model.ProcurementNotificationsEntity;
import com.rjtech.notification.model.ProcurementNotificationsEntityCopy;

public interface ProcurementNotificationsRepositoryCopy
        extends NotificationsRepository<ProcurementNotificationsEntityCopy, Long> {
	
	@Query("SELECT P FROM ProcurementNotificationsEntityCopy P WHERE P.preContractId=:contractId AND  P.status=:status ORDER BY P.id")
    ProcurementNotificationsEntityCopy findPreContractNotificationDetails(@Param("contractId") Long contractId,
            @Param("status") Integer status);
	
	
	/*	
	
    @Query("SELECT P FROM ProcurementNotificationsEntity P WHERE (:id is null or P.id=:id) "
            + "AND (:projId IS NULL OR P.projId.projectId=:projId)" + "AND  P.status=:status")
    List<ProcurementNotificationsEntity> findProcurementNotifications(@Param("id") Long id,
            @Param("projId") Long projId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProcurementNotificationsEntity P SET  P.notificationStatus=:notificationStatus WHERE P.id = :id")
    public void updateNotificationStatus(@Param("id") Long id, @Param("notificationStatus") String notificationStatus);

    @Query("SELECT COUNT(T) FROM WorkDairyNotificationsEntity T  WHERE (:clientId IS NULL OR T.clientId.clientId=:clientId) AND T.status=1 AND T.createdOn between :fromDate AND :toDate")
    Integer countProcurment(@Param("clientId") Long clientId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
*/
}
