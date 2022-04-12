package com.rjtech.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.eps.model.ProjGeneralMstrEntityCopy;

public interface ProjGeneralRepositoryCopyCopy extends CommonBaseRepository<ProjGeneralMstrEntityCopy, Long> {

	@Query("SELECT PGV FROM com.rjtech.eps.model.ProjGeneralMstrEntityCopy PGV WHERE PGV.projMstrEntity.projectId=:projId AND PGV.status=:status AND PGV.isLatest='Y' ")
    public List<com.rjtech.eps.model.ProjGeneralMstrEntityCopy> findProjGenerals(@Param("projId") Long projId, @Param("status") Integer status);
}
