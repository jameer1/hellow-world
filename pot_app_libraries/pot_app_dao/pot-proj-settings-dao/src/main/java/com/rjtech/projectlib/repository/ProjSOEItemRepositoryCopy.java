package com.rjtech.projectlib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjSOEItemEntity;

//import com.rjtech.projectlib.model.ProjSOEItemEntityCopy;

@Repository
public interface ProjSOEItemRepositoryCopy extends JpaRepository<ProjSOEItemEntity, Long> {

    @Query("SELECT SOE FROM ProjSOEItemEntity SOE WHERE SOE.status=1 AND SOE.projMstrEntity.projectId=:projId AND SOE.code=:code")
    public ProjSOEItemEntity findBy(@Param("projId") Long projId, @Param("code") String code);
    
    @Query("SELECT COUNT(*) FROM ProjSOEItemEntity SOE WHERE SOE.status=1 AND SOE.item=1 AND SOE.projMstrEntity.projectId=:projId AND SOE.code=:code")
    public int countBy(@Param("projId") Long projId, @Param("code") String code);

}
