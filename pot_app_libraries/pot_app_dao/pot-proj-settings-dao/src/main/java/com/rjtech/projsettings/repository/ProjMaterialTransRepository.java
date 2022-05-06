package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.MaterialTransNormalTimeEntity;

public interface ProjMaterialTransRepository extends ProjSettingsBaseRepository<MaterialTransNormalTimeEntity, Long> {

    @Query("SELECT PMT FROM MaterialTransNormalTimeEntity PMT WHERE  (( :projId IS NULL AND PMT.projId.projectId IS NULL) OR PMT.projId.projectId=:projId) AND PMT.status=:status")
    public List<MaterialTransNormalTimeEntity> findProjMaterialTrans(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PMT FROM com.rjtech.projsettings.model.MaterialTransNormalTimeEntity PMT WHERE  PMT.isDefault='Y'  AND PMT.projId.projectId IS NULL ")
    public List<MaterialTransNormalTimeEntity> findDefaultProjMaterialTrans();
    
    @Query("SELECT PMT FROM MaterialTransNormalTimeEntity PMT WHERE  (( :projId IS NULL AND PMT.projId.projectId IS NULL) OR PMT.projId.projectId=:projId) "
    		+ "AND PMT.status=:status AND PMT.materialType=:materialType")
    public MaterialTransNormalTimeEntity findMaterialInternalTransNormalTimeByProjId(@Param("projId") Long projId, @Param("status") Integer status, 
    		@Param("materialType") String materialType);
}
