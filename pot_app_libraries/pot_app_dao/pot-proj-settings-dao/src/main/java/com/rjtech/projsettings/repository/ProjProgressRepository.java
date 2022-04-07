package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projectlib.model.ProjSOEItemEntity;
//import com.rjtech.projectlib.model.ProjSOEItemEntityCopy;
import com.rjtech.projsettings.model.ProjProgressEntity;

public interface ProjProgressRepository extends ProjSettingsBaseRepository<ProjProgressEntity, Long> {
    @Query("SELECT PJMD FROM ProjProgressEntity PJMD WHERE  PJMD.projMstrEntity.projectId=:projId AND PJMD.status=:status")
    public List<ProjProgressEntity> findProjProgress(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT SOE FROM ProjSOEItemEntity SOE WHERE  SOE.projMstrEntity.projectId=:projId AND SOE.projSOEItemEntity.id IS NULL AND SOE.status=:status  ORDER BY  SOE.code")
    public List<ProjSOEItemEntity> findProjProgressSOEDetails(@Param("projId") Long projId,
            @Param("status") Integer status);

    /*@Query("SELECT PJMD FROM ProjProgressEntity PJMD WHERE BETWEEN  PJMD.fromDate=:fromDate AND PJMD.toDate=:toDate" ) 
    public List<ProjProgressEntity> findProjProgressSearch(@Param("toDate")
    @Temporal Date toDate, @Param("fromDate") @Temporal Date fromDate);*/

}
