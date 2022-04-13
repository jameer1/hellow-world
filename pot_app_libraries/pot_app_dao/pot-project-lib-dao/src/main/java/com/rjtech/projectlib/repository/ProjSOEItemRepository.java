package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjSOEItemEntity;

@Repository
public interface ProjSOEItemRepository extends ProjLibBaseRepository<ProjSOEItemEntity, Long> {
    @Query("SELECT SOE FROM com.rjtech.projectlib.model.ProjSOEItemEntity SOE WHERE SOE.projMstrEntity.projectId=:projId AND SOE.projSOEItemEntity.id IS NULL AND SOE.status=:status  ORDER BY  SOE.code")
    public List<ProjSOEItemEntity> findSOEDetails(@Param("projId") Long projId, @Param("status") Integer status);
    
    @Query("SELECT SOE FROM com.rjtech.projectlib.model.ProjSOEItemEntity SOE WHERE SOE.projMstrEntity.projectId=:projId AND SOE.projSOEItemEntity.id IS NULL AND SOE.status NOT IN (2,3) ORDER BY SOE.code")
    public List<ProjSOEItemEntity> findSOEDetails(@Param("projId") Long projId);

    @Query("SELECT SOE FROM com.rjtech.projectlib.model.ProjSOEItemEntity SOE WHERE  SOE.projMstrEntity.projectId in :projIds AND SOE.projSOEItemEntity.id IS NULL AND SOE.status=:status  ORDER BY  SOE.code")
    public List<ProjSOEItemEntity> findMultiProjSOEDetails(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

    @Query("SELECT SOE FROM com.rjtech.projectlib.model.ProjSOEItemEntity SOE WHERE  SOE.projMstrEntity.projectId=:projId AND SOE.id=:soeId AND SOE.status=:status  ORDER BY  SOE.code")
    public List<ProjSOEItemEntity> findSOEDetailsById(@Param("projId") Long projId, @Param("soeId") Long soeId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE com.rjtech.projectlib.model.ProjSOEItemEntity SOE  SET SOE.status=:status  where SOE.id in :soeIds or SOE.projSOEItemEntity.id in :soeIds")
    void deactivateSOEDetails(@Param("soeIds") List<Long> soeIds, @Param("status") Integer status);

    @Query("SELECT SOE FROM com.rjtech.projectlib.model.ProjSOEItemEntity SOE WHERE  SOE.projMstrEntity.projectId=:projId ORDER BY  SOE.code")
    public List<ProjSOEItemEntity> findAllSOEDetails(@Param("projId") Long projId);
    
    @Modifying
    @Query("UPDATE com.rjtech.projectlib.model.ProjSOEItemEntity SOE SET SOE.soeItemStatus=:soeItemStatus,SOE.internalApproverUserId.userId=:internalApprovalUserId,SOE.internalApprovalStatus=:internalApprovalStatus,SOE.originatorUserId.userId=:originatorUserId where SOE.id=:soeId")
    public void updateSOEInternalApproverDetailsById( @Param("soeId") Long soeId, @Param("soeItemStatus") String soeItemStatus, @Param("internalApprovalUserId") Long internalApprovalUserId, @Param("internalApprovalStatus") String internalApprovalStatus, @Param("originatorUserId") Long originatorUserId );
    
    @Modifying
    @Query("UPDATE com.rjtech.projectlib.model.ProjSOEItemEntity SOE SET SOE.soeItemStatus=:soeItemStatus,SOE.isExternalApprovalRequired=:isExternalApprovalRequired,SOE.internalApprovalStatus=:internalApprovalStatus,SOE.internalApproverComments=:internalApproverComments,SOE.status=:status where SOE.id=:soeId")
    public void updateSOEInternalApprovalDetailsById( @Param("soeId") Long soeId, @Param("soeItemStatus") String soeItemStatus, @Param("isExternalApprovalRequired") Character isExternalApprovalRequired, @Param("internalApprovalStatus") String internalApprovalStatus, @Param("internalApproverComments") String internalApproverComments, @Param("status") Integer status );
    
    @Modifying
    @Query("UPDATE com.rjtech.projectlib.model.ProjSOEItemEntity SOE SET SOE.soeItemStatus=:soeItemStatus,SOE.externalApproverUserId.userId=:externalApprovalUserId,SOE.externalApprovalStatus=:externalApprovalStatus where SOE.id=:soeId")
    public void updateSOEExternalApproverDetailsById( @Param("soeId") Long soeId, @Param("soeItemStatus") String soeItemStatus, @Param("externalApprovalUserId") Long externalApprovalUserId, @Param("externalApprovalStatus") String externalApprovalStatus );
    
    @Modifying
    @Query("UPDATE com.rjtech.projectlib.model.ProjSOEItemEntity SOE SET SOE.soeItemStatus=:soeItemStatus,SOE.externalApprovalStatus=:externalApprovalStatus,SOE.externalApproverComments=:externalApproverComments,SOE.status=:status where SOE.id=:soeId")
    public void updateSOEExternalApprovalDetailsById( @Param("soeId") Long soeId, @Param("soeItemStatus") String soeItemStatus, @Param("externalApprovalStatus") String externalApprovalStatus, @Param("externalApproverComments") String externalApproverComments, @Param("status") Integer status );
    
    @Modifying
    @Query("UPDATE com.rjtech.projectlib.model.ProjSOEItemEntity SOE SET SOE.soeItemStatus=:soeItemStatus,SOE.isItemReturned=:isItemReturned where SOE.id=:soeId")
    public void updateApprovalStatusBySoeId( @Param("soeId") Long soeId, @Param("soeItemStatus") String soeItemStatus, @Param("isItemReturned") Integer isItemReturned );
    
    @Modifying
    @Query("UPDATE com.rjtech.projectlib.model.ProjSOEItemEntity SOE SET SOE.soeItemStatus=:soeItemStatus where SOE.id=:soeId")
    public void updateApprovalStatusBySoeId( @Param("soeId") Long soeId, @Param("soeItemStatus") String soeItemStatus );
    
    @Query("SELECT SOE FROM com.rjtech.projectlib.model.ProjSOEItemEntity SOE WHERE SOE.projMstrEntity.projectId=:projId AND SOE.id in :soeIds AND SOE.status=:status ORDER BY SOE.code")
    public List<ProjSOEItemEntity> findMultiProjSOEItemsById( @Param("projId") Long projId, @Param("soeIds") List<Long> soeIds, @Param("status") Integer status );
    
    @Query("SELECT SOE FROM com.rjtech.projectlib.model.ProjSOEItemEntity SOE WHERE  SOE.projMstrEntity.projectId=:projId AND SOE.id=:soeId AND SOE.status NOT IN (2,3) ORDER BY SOE.code")
    public List<ProjSOEItemEntity> findSOEItemsById( @Param("projId") Long projId, @Param("soeId") Long soeId );
    
    @Query("SELECT SOE FROM com.rjtech.projectlib.model.ProjSOEItemEntity SOE WHERE SOE.id in :parentSoeIds")
    public List<ProjSOEItemEntity> findSOEParentIds( @Param("parentSoeIds") List<Long> parentSoeIds);
    
    
}
