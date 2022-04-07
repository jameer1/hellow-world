package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjSORItemEntity;
/*
 * This file is used for the Project library module functionality  
 */
@Repository
public interface ProjSORItemRepositoryCopy extends ProjLibBaseRepository<ProjSORItemEntity, Long> {
    @Query("SELECT SOR FROM ProjSORItemEntity SOR WHERE SOR.projMstrEntity.projectId=:projId AND SOR.projSORItemEntity.id IS NULL AND SOR.status=:status ORDER BY  SOR.code")
    public List<ProjSORItemEntity> findSORDetails(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT SOR FROM ProjSORItemEntity SOR WHERE SOR.projMstrEntity.projectId=:projId AND SOR.id=:sorId AND SOR.status NOT IN (2,3) ORDER BY  SOR.code")
    public List<ProjSORItemEntity> findSORDetailsById(@Param("projId") Long projId, @Param("sorId") Long sorId);

    @Modifying
    @Query("UPDATE ProjSORItemEntity SOR  SET SOR.status=:status where SOR.id in :sorIds or SOR.projSORItemEntity.id in :sorIds")
    void deactivateSORDetails(@Param("sorIds") List<Long> sorIds, @Param("status") Integer status);

    @Query("SELECT SOR FROM ProjSORItemEntity SOR WHERE SOR.projMstrEntity.projectId=:projId ORDER BY  SOR.code")
    public List<ProjSORItemEntity> findAllSORDetails(@Param("projId") Long projId);
    
    @Modifying
    @Query("UPDATE ProjSORItemEntity SOR SET SOR.sorItemStatus=:sorItemStatus,SOR.internalApproverUserId.userId=:internalApprovalUserId,SOR.internalApprovalStatus=:internalApprovalStatus,SOR.originatorUserId.userId=:originatorUserId where SOR.id=:sorId")
    public void updateSORInternalApprovalDetailsById( @Param("sorId") Long sorId, @Param("sorItemStatus") String sorItemStatus, @Param("internalApprovalUserId") Long internalApprovalUserId, @Param("internalApprovalStatus") String internalApprovalStatus, @Param("originatorUserId") Long originatorUserId );
    
    @Modifying
    @Query("UPDATE ProjSORItemEntity SOR SET SOR.sorItemStatus=:sorItemStatus,SOR.internalApprovalStatus=:internalApprovalStatus,SOR.internalApproverComments=:comments,SOR.status=:status where SOR.id=:sorId")
    public void updateSORInternalApproverDetailsById( @Param("sorId") Long sorId, @Param("sorItemStatus") String sorItemStatus, @Param("internalApprovalStatus") String internalApprovalStatus, @Param("comments") String comments, @Param("status") Integer status );
    
    @Modifying
    @Query("UPDATE ProjSORItemEntity SOR SET SOR.sorItemStatus=:sorItemStatus,SOR.isExternalApprovalRequired=:isExternalApprovalRequired,SOR.internalApprovalStatus=:internalApprovalStatus,SOR.internalApproverComments=:comments where SOR.id=:sorId")
    public void updateSORIntApproverDetailsById( @Param("sorId") Long sorId, @Param("sorItemStatus") String sorItemStatus, @Param("isExternalApprovalRequired") Character isExternalApprovalRequired, @Param("internalApprovalStatus") String internalApprovalStatus, @Param("comments") String comments );
    
    @Modifying
    @Query("UPDATE ProjSORItemEntity SOR SET SOR.sorItemStatus=:sorItemStatus,SOR.externalApproverUserId.userId=:externalApprovalUserId,SOR.externalApprovalStatus=:externalApprovalStatus where SOR.id=:sorId")
    public void updateSORExternalApprovalDetailsById( @Param("sorId") Long sorId, @Param("sorItemStatus") String sorItemStatus, @Param("externalApprovalUserId") Long externalApprovalUserId, @Param("externalApprovalStatus") String externalApprovalStatus );
    
    @Modifying
    @Query("UPDATE ProjSORItemEntity SOR SET SOR.sorItemStatus=:sorItemStatus,SOR.externalApprovalStatus=:externalApprovalStatus,SOR.externalApproverComments=:externalApproverComments,SOR.status=:status where SOR.id=:sorId")
    public void updateSORExternalApproverDetailsById( @Param("sorId") Long sorId, @Param("sorItemStatus") String sorItemStatus, @Param("externalApprovalStatus") String externalApprovalStatus, @Param("externalApproverComments") String externalApproverComments, @Param("status") Integer status );
    
    @Modifying
    @Query("UPDATE ProjSORItemEntity SOR SET SOR.sorItemStatus=:sorItemStatus where SOR.id=:sorId")
    public void updateApprovalStatusBySorId( @Param("sorId") Long sorId, @Param("sorItemStatus") String sorItemStatus );
    
    //findMultiProjSORItemsById
    @Query("SELECT SOR FROM ProjSORItemEntity SOR WHERE SOR.projMstrEntity.projectId=:projId AND SOR.id in :sorIds AND SOR.status=:status ORDER BY SOR.code")
    public List<ProjSORItemEntity> findMultiProjSORItemsById( @Param("projId") Long projId, @Param("sorIds") List<Long> sorIds, @Param("status") Integer status );
    
    @Query("SELECT SOR FROM ProjSORItemEntity SOR WHERE SOR.projSORItemEntity.id in :sorIds ORDER BY SOR.code")
    public List<ProjSORItemEntity> getSORChildItems( @Param("sorIds") List<Long> sorIds );
    
    @Modifying
    @Query("UPDATE ProjSORItemEntity SOR SET SOR.sorItemStatus=:sorItemStatus,SOR.isItemReturned=:returnStatus where SOR.id=:sorId")
    public void updateApprovalStatusAndReturnStatusBySorId( @Param("sorId") Long sorId, @Param("sorItemStatus") String sorItemStatus, @Param("returnStatus") Integer returnStatus );
    
    @Query("SELECT SOR FROM ProjSORItemEntity SOR WHERE SOR.projMstrEntity.projectId=:projId AND SOR.projSORItemEntity.id IS NULL AND SOR.status NOT IN (2,3) ORDER BY SOR.code")
    public List<ProjSORItemEntity> findSORDetailsByProjId( @Param("projId") Long projId );
    
    @Query("SELECT SOR FROM ProjSORItemEntity SOR WHERE SOR.projMstrEntity.projectId=:projId AND SOR.projSORItemEntity.id IS NULL AND SOR.status IN (0,1) ORDER BY  SOR.code")
    public List<ProjSORItemEntity> findSORDetails(@Param("projId") Long projId);
    
}
