package com.rjtech.projschedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ResourceAssignmentDataEntity;

public interface ResourceAssignmentDataRepository extends JpaRepository<ResourceAssignmentDataEntity, Long> {

	@Query("SELECT T FROM ResourceAssignmentDataEntity T WHERE T.scheduleActivityDataSetEntity.id=:resourceAssignmentDataSetId ORDER BY T.startDate")
	public List<ResourceAssignmentDataEntity> findAllBy(@Param("resourceAssignmentDataSetId") Long resourceAssignmentDataSetId);
	
	@Query("SELECT MIN(T.startDate) FROM ResourceAssignmentDataEntity T WHERE T.referenceId=:referenceId " +
			"AND T.scheduleActivityDataSetEntity.type='R' " +
			"AND T.scheduleActivityDataSetEntity.baseline='Y' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public Date findMinimumStartDateOfBaselineBy(@Param("projectId") Long projectId, @Param("referenceId") Long referenceId);
	
	@Query("SELECT MAX(T.finishDate) FROM ResourceAssignmentDataEntity T WHERE T.referenceId=:referenceId " +
			"AND T.scheduleActivityDataSetEntity.type='R' " +
			"AND T.scheduleActivityDataSetEntity.baseline='Y' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public Date findMaximumFinishDateOfBaselineBy(@Param("projectId") Long projectId, @Param("referenceId") Long referenceId);
	
	@Query("SELECT MIN(T.startDate) FROM ResourceAssignmentDataEntity T WHERE " +
			"T.scheduleActivityDataSetEntity.type='R' " +
			"AND T.scheduleActivityDataSetEntity.baseline='Y' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public Date findMinimumStartDateOfBaselineBy(@Param("projectId") Long projectId);
	
	@Query("SELECT MAX(T.finishDate) FROM ResourceAssignmentDataEntity T WHERE " +
			"T.scheduleActivityDataSetEntity.type='R' " +
			"AND T.scheduleActivityDataSetEntity.baseline='Y' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public Date findMaximumFinishDateOfBaselineBy(@Param("projectId") Long projectId);
	
	@Query("SELECT MIN(T.startDate) FROM ResourceAssignmentDataEntity T WHERE " +
			"T.scheduleActivityDataSetEntity.type='R' " +
			"AND T.scheduleActivityDataSetEntity.current='Y' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public Date findMinimumStartDateOfCurrentBy(@Param("projectId") Long projectId);
	
	@Query("SELECT MAX(T.finishDate) FROM ResourceAssignmentDataEntity T WHERE " +
			"T.scheduleActivityDataSetEntity.type='R' " +
			"AND T.scheduleActivityDataSetEntity.current='Y' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public Date findMaximumFinishDateOfCurrentBy(@Param("projectId") Long projectId);

	@Query("SELECT T FROM ResourceAssignmentDataEntity T WHERE " +
			"T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId " +
			"AND T.scheduleActivityDataSetEntity.current='Y' " +
			"AND T.scheduleActivityDataSetEntity.type='R' ")
	public List<ResourceAssignmentDataEntity> findByBaseline(@Param("projectId") Long projectId);
	
	@Query("SELECT T FROM ResourceAssignmentDataEntity T WHERE T.referenceId in (:referenceIds) " +
			"AND T.scheduleActivityDataSetEntity.type='R' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public List<ResourceAssignmentDataEntity> findAllBy(@Param("projectId") Long projectId, @Param("referenceIds") List<Long> referenceIds);
	
	//commented
}
