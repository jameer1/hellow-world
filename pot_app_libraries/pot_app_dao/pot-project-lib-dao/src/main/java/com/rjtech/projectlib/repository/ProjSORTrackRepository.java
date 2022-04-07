package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjSORTrackRecordsEntity;
/*
 * This file used for the Schedules - SOR Items Schedule module
*/

@Repository
public interface ProjSORTrackRepository extends JpaRepository<ProjSORTrackRecordsEntity, Long> {

	/*
	 * @Query("SELECT SOR FROM ProjSORItemEntityCopy SOR WHERE SOR.projMstrEntity.projectId=:projectId AND SOR.code=:sorCode"
	 * ) public ProjSORItemEntityCopy findBy(@Param("projectId") Long
	 * projectId, @Param("sorCode") String sorCode);
	 * 
	 * @Query("SELECT SOR FROM ProjSORItemEntityCopy SOR WHERE SOR.projMstrEntity.projectId=:projId AND SOR.projSORItemEntity.id IS NULL AND SOR.status=:status ORDER BY  SOR.code"
	 * ) public List<ProjSORItemEntityCopy> findSORDetails(@Param("projId") Long
	 * projId, @Param("status") Integer status);
	 */
	
	@Query("SELECT STR FROM ProjSORTrackRecordsEntity STR WHERE STR.projMstrEntity.projectId=:projId AND STR.sorItemStatus=:status AND STR.projSORTrackRecordsEntity.id IS NULL ORDER BY STR.code")
	public List<ProjSORTrackRecordsEntity> findSTRDetails(@Param("projId") Long projId, @Param("status") String status);

}
