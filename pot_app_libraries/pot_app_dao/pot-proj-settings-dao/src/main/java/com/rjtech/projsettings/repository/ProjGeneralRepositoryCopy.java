package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projsettings.model.ProjGeneralMstrEntity;

//import com.rjtech.projsettings.model.copy.ProjGeneralMstrEntityCopy;

@Repository("projGeneralRepositoryProcurementCopy")
public interface ProjGeneralRepositoryCopy extends JpaRepository<ProjGeneralMstrEntity, Long> {

    @Query("SELECT pgm.currency FROM ProjGeneralMstrEntity pgm WHERE pgm.projMstrEntity.projectId = :projId "
            + "AND pgm.status = 1 AND pgm.isLatest = 'Y'")
    String getCurrencyForProject(@Param("projId") Long projId);
    
    @Query("SELECT PGV FROM ProjGeneralMstrEntity PGV WHERE PGV.projMstrEntity.projectId=:projId AND PGV.status=:status")
    List<ProjGeneralMstrEntity> getGeneralValues(@Param("projId") Long projId,@Param("status") Integer status);
    
    @Query("SELECT PGV FROM ProjGeneralMstrEntity PGV WHERE PGV.profitCentreEntity.id=:id")
    public List<ProjGeneralMstrEntity> findProfitCentresByProfitCenter(@Param("id") Long id);
    
    

}