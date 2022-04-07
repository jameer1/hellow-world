package com.rjtech.projschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ResourceCurveMappingEntity;

public interface ResourceCurveMappingRepository extends JpaRepository<ResourceCurveMappingEntity, Long>{

	@Query("SELECT T FROM ResourceCurveMappingEntity T "
			+ "WHERE T.referenceId=:referenceId "
			+ "AND T.referenceType=:referenceType")
	public ResourceCurveMappingEntity findBy(@Param("referenceId") Long referenceId, @Param("referenceType") String referenceType);
}
