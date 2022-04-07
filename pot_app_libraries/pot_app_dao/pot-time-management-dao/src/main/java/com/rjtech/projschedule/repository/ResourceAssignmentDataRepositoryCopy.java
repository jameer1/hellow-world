package com.rjtech.projschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.rjtech.projschedule.model.ResourceAssignmentDataEntity;

public interface ResourceAssignmentDataRepositoryCopy extends JpaRepository<ResourceAssignmentDataEntity, Long>{

	@Query("SELECT T FROM ResourceAssignmentDataEntity T WHERE "
			+ "T.scheduleActivityDataSetEntity.type = 'R' "
			+ "AND T.scheduleActivityDataSetEntity.baseline = 'Y' "
			+ "AND T.scheduleActivityDataSetEntity.projMstrEntity.projectId=:projectId "
			+ "AND T.referenceId=:referenceId ")
	public List<ResourceAssignmentDataEntity> findBy(@Param("projectId") Long projectId, @Param("referenceId") Long referenceId);
}
