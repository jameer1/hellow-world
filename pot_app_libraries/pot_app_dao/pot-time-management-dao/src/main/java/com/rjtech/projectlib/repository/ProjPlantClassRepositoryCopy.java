package com.rjtech.projectlib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjPlantClassMstrEntity;

//import com.rjtech.projectlib.model.ProjPlantClassMstrEntityCopy;

@Repository
public interface ProjPlantClassRepositoryCopy extends JpaRepository<ProjPlantClassMstrEntity, Long> {

    @Query("SELECT PPC FROM ProjPlantClassMstrEntity PPC WHERE PPC.projId.projectId = :projId "
            + " and PPC.plantMstrEntity.id = :plantId AND  PPC.status=:status ")
    ProjPlantClassMstrEntity getUserProjPlantClasses(@Param("projId") Long projId, @Param("plantId") Long plantId,
            @Param("status") Integer status);

}
