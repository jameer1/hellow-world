package com.rjtech.register.repository.material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntity;

//import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntityCopy;

public interface MaterialDockSchItemRepositoryCopy extends JpaRepository<MaterialProjDocketSchItemsEntity, Long> {

    @Query("SELECT T FROM MaterialProjDocketSchItemsEntity T WHERE T.id=:projDocketSchId  AND T.status=:status")
    public MaterialProjDocketSchItemsEntity findMaterialSchItemById(@Param("projDocketSchId") Long projDocketSchId,
            @Param("status") Integer status);

}
