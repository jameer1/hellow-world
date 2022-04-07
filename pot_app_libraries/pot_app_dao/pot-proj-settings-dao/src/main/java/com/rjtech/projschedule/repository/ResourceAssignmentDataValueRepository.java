package com.rjtech.projschedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ResourceAssignmentDataValueEntity;

public interface ResourceAssignmentDataValueRepository extends JpaRepository<ResourceAssignmentDataValueEntity, Long>{

	@Query("SELECT T.forecastDate, SUM(T.budgetUnits) FROM ResourceAssignmentDataValueEntity T WHERE "
			+ "T.resourceAssignmentDataEntity.referenceId in (:resIds) AND "
			+ "T.resourceAssignmentDataEntity.scheduleActivityDataSetEntity.id=:datasetId AND "
			+ "T.forecastDate BETWEEN T.resourceAssignmentDataEntity.startDate AND T.resourceAssignmentDataEntity.finishDate "
			+ "GROUP BY T.forecastDate "
			+ "ORDER BY T.forecastDate")
	public List<Object[]> getDateWiseForecastForResourceId(@Param("datasetId") Long datasetId, @Param("resIds") List<Long> resIds);
	
	@Query("SELECT T FROM ResourceAssignmentDataValueEntity T "
			+ "WHERE T.resourceAssignmentDataEntity.scheduleActivityDataSetEntity.projMstrEntity.id IN :projectIds "
			+ "AND T.resourceAssignmentDataEntity.scheduleActivityDataSetEntity.type = 'R' "
			+ "AND T.resourceAssignmentDataEntity.scheduleActivityDataSetEntity.current = 'Y' "
			+ "AND T.forecastDate BETWEEN :fromDate AND :toDate")
	public List<ResourceAssignmentDataValueEntity> findAllBaselines(@Param("projectIds") List<Long> projectIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	@Query("SELECT T FROM ResourceAssignmentDataValueEntity T "
			+ "WHERE T.resourceAssignmentDataEntity.scheduleActivityDataSetEntity.projMstrEntity.id IN :projectIds "
			+ "AND T.resourceAssignmentDataEntity.scheduleActivityDataSetEntity.type = 'R' "
			+ "AND T.resourceAssignmentDataEntity.scheduleActivityDataSetEntity.current = 'Y' "
			+ "AND T.forecastDate BETWEEN :fromDate AND :toDate")
	public List<ResourceAssignmentDataValueEntity> findAllCurrent(@Param("projectIds") List<Long> projectIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
