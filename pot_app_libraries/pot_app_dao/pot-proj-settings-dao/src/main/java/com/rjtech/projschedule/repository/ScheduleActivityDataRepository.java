package com.rjtech.projschedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ScheduleActivityDataEntity;

public interface ScheduleActivityDataRepository extends JpaRepository<ScheduleActivityDataEntity, Long> {
	
	@Query("SELECT T FROM ScheduleActivityDataEntity T WHERE T.scheduleActivityDataSetEntity.id=:ScheduleActivityDataSetId ORDER BY T.wbsPath,T.id")
	public List<ScheduleActivityDataEntity> findAllBy(@Param("ScheduleActivityDataSetId") Long ScheduleActivityDataSetId);

	@Query("SELECT MIN(T.startDate) FROM ScheduleActivityDataEntity T WHERE T.sow.id=:sowId " +
			"AND T.scheduleActivityDataSetEntity.type='A' " +
			"AND T.scheduleActivityDataSetEntity.baseline='Y' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public Date findMinimumStartDateOfBaselineBy(@Param("projectId") Long projectId, @Param("sowId") Long sowId);
	
	@Query("SELECT MAX(T.finishDate) FROM ScheduleActivityDataEntity T WHERE T.sow.id=:sowId " +
			"AND T.scheduleActivityDataSetEntity.type='A' " +
			"AND T.scheduleActivityDataSetEntity.baseline='Y' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public Date findMaximumFinishDateOfBaselineBy(@Param("projectId") Long projectId, @Param("sowId") Long sowId);
	
	@Query("SELECT T.startDate FROM ScheduleActivityDataEntity T WHERE T.code=:activityCode " +
			"AND T.scheduleActivityDataSetEntity.type='A' " +
			"AND T.scheduleActivityDataSetEntity.baseline='Y' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public Date findStartDateOfBaselineBy(@Param("projectId") Long projectId, @Param("activityCode") String activityCode);
	
	@Query("SELECT T.finishDate FROM ScheduleActivityDataEntity T WHERE T.code=:activityCode " +
			"AND T.scheduleActivityDataSetEntity.type='A' " +
			"AND T.scheduleActivityDataSetEntity.baseline='Y' " +
			"AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId ")
	public Date findFinishDateOfBaselineBy(@Param("projectId") Long projectId, @Param("activityCode") String activityCode);
}
