package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.SoeAddtionalTimeApprEntity;

public interface SoeAddltionalTimeRepository extends JpaRepository<SoeAddtionalTimeApprEntity, Long> {
	
	/*
	 * @Query("SELECT PAT FROM SoeAddltionalTimeRepository PAT WHERE (PAT.projId=:projId) AND (PAT.notificationStatus=:notificationStatus) "
	 * +
	 * "AND (PAT.notificationMessage=:notificationMessage) AND (PAT.status=:status)"
	 * ) List<SoeAddltionalTimeRepository>
	 * findProcNotificationsByStatus(@Param("projId") Long projId,
	 * 
	 * @Param("notificationStatus") String
	 * notificationStatus, @Param("notificationMessage") String notificationMessage,
	 * 
	 * @Param("status") Integer status);
	 */
	
	
	@Query("SELECT SAT FROM SoeAddtionalTimeApprEntity SAT WHERE SAT.soeNotificationsEntity.id=:soeId")
	public SoeAddtionalTimeApprEntity findSoeAddlTimeDetails(@Param("soeId") Long soeId);
	
	@Query("SELECT SAT FROM SoeAddtionalTimeApprEntity SAT WHERE SAT.projId =:projId AND SAT.status =:status")
    public SoeAddtionalTimeApprEntity findOne(@Param("projId") Long projId,@Param("status") String status);

    
	
}