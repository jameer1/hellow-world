package com.rjtech.projschedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ScheduleActivityDataSetEntity;

public interface ScheduleActivityDataSetRepository extends JpaRepository<ScheduleActivityDataSetEntity, Long> {
	
	@Query("SELECT T FROM ScheduleActivityDataSetEntity T  WHERE T.projMstrEntity.projectId =:projectId AND T.type =:type ORDER BY T.createdOn")
	public List<ScheduleActivityDataSetEntity> findAllBy(@Param("projectId") Long projectId, @Param("type") String type);
	
	@Modifying
	@Query("UPDATE ScheduleActivityDataSetEntity T SET T.current='N' where T.projMstrEntity.projectId=:projectId AND T.type =:type")
	public void updateCurrent(@Param("projectId") Long projectId, @Param("type") String type);
	
	@Modifying
	@Query("UPDATE ScheduleActivityDataSetEntity T SET T.current='N', T.baseline='N' where T.projMstrEntity.projectId=:projectId AND T.type =:type")
	public void updateCurrentBaseline(@Param("projectId") Long projectId, @Param("type") String type);
	
	@Query("SELECT T FROM ScheduleActivityDataSetEntity T WHERE T.projMstrEntity.projectId=:projectId AND T.type =:type AND T.current='Y'")
	public ScheduleActivityDataSetEntity findOneCurrent(@Param("projectId") Long projectId, @Param("type") String type);

}
