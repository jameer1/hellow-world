package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjSOWItemEntity;

//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;

@Repository
public interface ProjSOWItemRepositoryCopy extends JpaRepository<ProjSOWItemEntity, Long> {
	
	@Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE SOW.projMstrEntity.projectId=:projId AND SOW.item=1 AND SOW.status=:status ORDER BY SOW.code")
    public List<ProjSOWItemEntity> findSOWItems(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE SOW.projMstrEntity.projectId=:projId AND SOW.code=:code")
    public ProjSOWItemEntity findBy(@Param("projId") Long projId, @Param("code") String code);
    
    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE SOW.projSOEItemEntity.id=:soeId")
    public ProjSOWItemEntity findBy(@Param("soeId") Long soeId);
    
    @Query("SELECT SOW FROM ProjSOWItemEntity SOW WHERE "
    		+ "SOW.projMstrEntity.projectId=:projId "
    		+ "AND SOW.item=1 "
    		+ "AND SOW.status=:status "
    		+ "AND SOW.tangibleClassificationEntity IS NOT NULL "
    		+ "ORDER BY SOW.code")
    public List<ProjSOWItemEntity> findAllTangibleItems(@Param("projId") Long projId, @Param("status") Integer status);

}
