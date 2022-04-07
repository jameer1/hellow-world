package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjNoteBookEntity;

public interface ProjNoteBookRepository extends ProjSettingsBaseRepository<ProjNoteBookEntity, Long> {
    @Query("SELECT PJI FROM ProjNoteBookEntity PJI WHERE PJI.projMstrEntity.projectId=:projId AND PJI.status=:status")
    public List<ProjNoteBookEntity> findProjNoteBook(@Param("projId") Long projId, @Param("status") Integer status);

}
