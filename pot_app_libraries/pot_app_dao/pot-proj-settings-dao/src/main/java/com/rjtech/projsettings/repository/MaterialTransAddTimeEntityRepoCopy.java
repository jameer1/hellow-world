package com.rjtech.projsettings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projsettings.model.MaterialTransAddtionalTimeApprEntity;

//import com.rjtech.projsettings.model.MaterialTransAddtionalTimeApprEntityCopy;

@Repository
public interface MaterialTransAddTimeEntityRepoCopy
        extends JpaRepository<MaterialTransAddtionalTimeApprEntity, Long> {

    @Query("SELECT MTAT FROM MaterialTransAddtionalTimeApprEntity MTAT "
            + "WHERE MTAT.projMaterialTransEntity.projId.projectId = :projId "
            + "AND MTAT.status = 1 AND MTAT.latest = true "
            + "AND MTAT.projMaterialTransEntity.materialType = 'SUBMIT_TYPE'")
    MaterialTransAddtionalTimeApprEntity getMaterialAdditionalTime(@Param("projId") Long projId);
}
