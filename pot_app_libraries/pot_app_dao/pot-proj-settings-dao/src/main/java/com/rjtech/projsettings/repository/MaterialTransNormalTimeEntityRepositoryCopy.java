package com.rjtech.projsettings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projsettings.model.MaterialTransNormalTimeEntity;

//import com.rjtech.projsettings.model.MaterialTransNormalTimeEntityCopy;

@Repository
public interface MaterialTransNormalTimeEntityRepositoryCopy
        extends JpaRepository<MaterialTransNormalTimeEntity, Long> {

    @Query("SELECT MTNT FROM MaterialTransNormalTimeEntity MTNT "
            + "WHERE MTNT.projId.projectId = :projId AND MTNT.materialType = :materialType")
    MaterialTransNormalTimeEntity findNormalTimeByProject(@Param("projId") Long projId,
            @Param("materialType") String materialType);
}
