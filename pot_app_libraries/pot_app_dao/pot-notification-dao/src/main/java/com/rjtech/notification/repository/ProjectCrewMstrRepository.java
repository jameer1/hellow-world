package com.rjtech.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjCrewMstrEntity;

//import com.rjtech.notification.model.ProjCrewMstrEntity;


@Repository
public interface ProjectCrewMstrRepository  extends JpaRepository<ProjCrewMstrEntity, Long> {

    @Query("SELECT CRW FROM ProjCrewMstrEntity CRW  WHERE  CRW.projId.projectId in :projId  AND  CRW.id=:crewId")
    List<ProjCrewMstrEntity> findCrewName(@Param("projId") Long projId, @Param("crewId") Long crewId);

	
}
