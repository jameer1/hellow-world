package com.rjtech.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.notification.model.ProcurementAddtionalTimeApprEntity;

public interface ProcurementAddltionalTimeRepository extends JpaRepository<ProcurementAddtionalTimeApprEntity, Long> {
	
	@Query("SELECT PAT FROM ProcurementAddtionalTimeApprEntity PAT WHERE (PAT.projId=:projId) AND (PAT.notificationStatus=:notificationStatus) "
			+ "AND (PAT.notificationMessage=:notificationMessage) AND (PAT.status=:status)")
	List<ProcurementAddtionalTimeApprEntity> findProcNotificationsByStatus(@Param("projId") Long projId,
			@Param("notificationStatus") String notificationStatus, @Param("notificationMessage") String notificationMessage,
			@Param("status") Integer status);
	
	
	@Query("SELECT PAT FROM ProcurementAddtionalTimeApprEntity PAT WHERE (PAT.preContractEntity.id=:preContractId) AND (PAT.status=:status)")
	public ProcurementAddtionalTimeApprEntity findPreContractAddlTimeDetails(@Param("preContractId") Long preContractId, 
			@Param("status") Integer status);
	
	@Query("SELECT PAT FROM ProcurementAddtionalTimeApprEntity PAT WHERE PAT.preContractEntity.id=:procurementId "
			+ "AND PAT.procureStage=:procureStage AND PAT.notificationStatus='Approved' AND PAT.status=1")
	public ProcurementAddtionalTimeApprEntity findNormalTimeId(@Param("procurementId") Long procurementId, 
			@Param("procureStage") String procureStage);
    
	
}