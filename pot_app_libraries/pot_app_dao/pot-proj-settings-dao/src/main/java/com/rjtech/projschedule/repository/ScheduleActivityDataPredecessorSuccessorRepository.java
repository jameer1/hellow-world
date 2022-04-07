package com.rjtech.projschedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ScheduleActivityDataPredecessorSuccessorEntity;

public interface ScheduleActivityDataPredecessorSuccessorRepository extends JpaRepository<ScheduleActivityDataPredecessorSuccessorEntity, Long> {

	 @Query("SELECT T FROM ScheduleActivityDataPredecessorSuccessorEntity T WHERE T.type = 'P' AND T.scheduleActivityData.id =:scheduleActivityDataId")
	 public List<ScheduleActivityDataPredecessorSuccessorEntity> findPredecessors(@Param("scheduleActivityDataId") Long scheduleActivityDataId);
	 
	 @Query("SELECT T FROM ScheduleActivityDataPredecessorSuccessorEntity T WHERE T.type = 'S' AND T.scheduleActivityData.id =:scheduleActivityDataId")
	 public List<ScheduleActivityDataPredecessorSuccessorEntity> findSucessors(@Param("scheduleActivityDataId") Long scheduleActivityDataId);
}
