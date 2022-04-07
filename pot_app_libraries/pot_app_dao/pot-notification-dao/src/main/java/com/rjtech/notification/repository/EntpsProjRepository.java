package com.rjtech.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.eps.model.ProjMstrEntity;

@Repository
public interface EntpsProjRepository extends JpaRepository<ProjMstrEntity, Long> {
	@Query("SELECT proj FROM ProjMstrEntity proj WHERE  proj.projectId=:projId")
	public List<ProjMstrEntity> getProjCode(@Param("projId") Long projId);

}
