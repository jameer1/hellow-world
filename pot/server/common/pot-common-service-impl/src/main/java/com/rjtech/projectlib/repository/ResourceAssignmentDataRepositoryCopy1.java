package com.rjtech.projectlib.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projectlib.model.ResourceAssignmentDataEntityCopy;

public interface ResourceAssignmentDataRepositoryCopy1 extends JpaRepository<ResourceAssignmentDataEntityCopy, Long> {

	@Query("SELECT T FROM ResourceAssignmentDataEntityCopy T WHERE " +
			"T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId " +
			"AND T.scheduleActivityDataSetEntity.current='Y' " +
			"AND T.scheduleActivityDataSetEntity.type='R' ")
	public List<ResourceAssignmentDataEntityCopy> findByBaseline(@Param("projectId") Long projectId);
	
	//commented
}
