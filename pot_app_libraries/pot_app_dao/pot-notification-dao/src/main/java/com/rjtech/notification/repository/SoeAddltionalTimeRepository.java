package com.rjtech.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.notification.model.SoeAddtionalTimeApprEntity;

public interface SoeAddltionalTimeRepository extends JpaRepository<SoeAddtionalTimeApprEntity, Long> {
	
	@Query("SELECT SAT FROM SoeAddtionalTimeApprEntity SAT WHERE SAT.projId =:projId AND SAT.notificationStatus =:status")
	List <SoeAddtionalTimeApprEntity> findSoeAddlTimeDetails(@Param("projId") Long projId, @Param("status") String status);   
	
	@Query("SELECT SAT FROM SoeAddtionalTimeApprEntity SAT WHERE SAT.projId =:projId")
	public SoeAddtionalTimeApprEntity findProjId(@Param("projId")Long projId);
	
}