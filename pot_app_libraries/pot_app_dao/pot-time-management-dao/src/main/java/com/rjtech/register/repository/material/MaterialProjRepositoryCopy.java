package com.rjtech.register.repository.material;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.material.model.MaterialProjDtlEntity;

//import com.rjtech.register.material.model.MaterialProjDtlEntityCopy;

public interface MaterialProjRepositoryCopy extends JpaRepository<MaterialProjDtlEntity, Long> {

    @Query("SELECT T FROM MaterialProjDtlEntity T  WHERE T.projId.projectId=:projId AND "
            + "T.preContractsMaterialCmpEntity.id =:preContractItemCompId AND T.status=:status")
    public List<MaterialProjDtlEntity> getProjMaterialSchItem(@Param("projId") Long projId,
            @Param("preContractItemCompId") Long preContractItemCompId, @Param("status") Integer status);

}
