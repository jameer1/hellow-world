package com.rjtech.projectlib.repository;

import java.util.Date;
import java.util.List;

import com.rjtech.projectlib.model.ProjPMCMItemEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface ProjPMCMItemRepository extends ProjLibBaseRepository<ProjPMCMItemEntity, Long> {

    @Query("SELECT PMCM FROM ProjPMCMItemEntity PMCM WHERE PMCM.projPMCMItemEntity.id IS NULL and PMCM.projMstrEntity.projectId=?1 and PMCM.pmStatusDate=?2 and PMCM.status =?3 ORDER BY PMCM.code")
    public List<ProjPMCMItemEntity> findPMCMDetails(@Param("projId") Long projId, @Param("projStatusDate") Date projStatusDate, @Param("status") Integer status);

    @Query("SELECT PMCM FROM ProjPMCMItemEntity PMCM WHERE PMCM.projMstrEntity.projectId=?1 and PMCM.id=?2 ORDER BY  PMCM.code")
    public List<ProjPMCMItemEntity> findPMCMDetailsById(@Param("projId") Long projId,@Param("projId") Long pmId);

    @Query("SELECT PMCM FROM ProjPMCMItemEntity PMCM WHERE PMCM.projMstrEntity.projectId in ?1 and PMCM.pmStatusDate <= ?2 and PMCM.item=1 ORDER BY  PMCM.code")
    public List<ProjPMCMItemEntity> findMultiplePMCMDetails(@Param("projIds") List<Long> projIds, @Param("pmStatusDate") Date pmStatusDate);
    
    @Modifying
    @Query("UPDATE ProjPMCMItemEntity PMCM SET PMCM.status =:status where PMCM.id in :pmIds")
    void deactivatePMCMDetails(@Param("status") Integer status, @Param("pmIds") List<Long> pmIds);
}
