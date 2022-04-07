package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjSOETrackRecordsEntity;

@Repository
public interface ProjSOETrackRepository extends ProjLibBaseRepository<ProjSOETrackRecordsEntity, Long> {
	
	@Query("SELECT STR FROM ProjSOETrackRecordsEntity STR WHERE STR.projMstrEntity.projectId=:projId AND STR.soeItemStatus=:status AND STR.projSOETrackRecordsEntity.id IS NULL ORDER BY STR.code")
	public List<ProjSOETrackRecordsEntity> findSOEDetails(@Param("projId") Long projId, @Param("status") String status);
	 
}
