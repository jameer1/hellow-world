package com.rjtech.projschedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ResourceAssignmentDataValueEntity;

public interface ResourceAssignmentDataValueRepositoryCopy
		extends JpaRepository<ResourceAssignmentDataValueEntity, Long> {

	@Query("SELECT TR FROM ResourceAssignmentDataValueEntity TR "
			+ "WHERE TR.resourceAssignmentDataEntity.scheduleActivityDataSetEntity.projMstrEntity.id IN :projectIds "
			+ "AND TR.resourceAssignmentDataEntity.scheduleActivityDataSetEntity.type = 'R' "
			+ "AND TR.resourceAssignmentDataEntity.scheduleActivityDataSetEntity.current = 'Y' "
			+ "AND TR.forecastDate BETWEEN :fromDate AND :toDate")
	public List<ResourceAssignmentDataValueEntity> findAllBaselines(@Param("projectIds") List<Long> projectIds,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	
	  @Query("SELECT TR FROM ResourceAssignmentDataValueEntity TR " 
			  + "WHERE TR.resourceAssignmentDataEntity.referenceId = :resourceId "
			  + "AND TR.forecastDate BETWEEN :fromDate AND :toDate") 
	  public  List<ResourceAssignmentDataValueEntity> findbudget(@Param("resourceId") Long resourceId,@Param("fromDate") Date fromDate,
			  @Param("toDate") Date toDate);
	  
	 

}
