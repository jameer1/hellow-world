package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.PlantTransNormalTimeEntity;

public interface ProjPlantTransRepository extends ProjSettingsBaseRepository<PlantTransNormalTimeEntity, Long> {

    @Query("SELECT PTR FROM PlantTransNormalTimeEntity PTR WHERE (( :projId IS NULL AND PTR.projId.projectId IS NULL) OR PTR.projId.projectId=:projId) AND PTR.status=:status")
    public List<PlantTransNormalTimeEntity> findProjPlantTrans(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PTR FROM com.rjtech.projsettings.model.PlantTransNormalTimeEntity PTR WHERE PTR.isDefault='Y' AND PTR.projId.projectId IS NULL")
    public List<PlantTransNormalTimeEntity> findDefaultProjPlantTrans();
    
    @Query("SELECT PTR FROM PlantTransNormalTimeEntity PTR WHERE (( :projId IS NULL AND PTR.projId.projectId IS NULL) OR PTR.projId.projectId=:projId ) "
    		+ "AND PTR.status=:status")
    public PlantTransNormalTimeEntity findPlantTransNormalTimeByProjId(@Param("projId") Long projId, @Param("status") Integer status);
}
