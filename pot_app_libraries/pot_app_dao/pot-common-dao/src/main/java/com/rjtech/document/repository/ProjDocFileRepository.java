package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import com.rjtech.document.model.ProjDocFileEntity;

@Repository
public interface ProjDocFileRepository extends JpaRepository<ProjDocFileEntity, Long> {

    @Query("SELECT T FROM ProjDocFileEntity T WHERE  T.folderId.id=:folderId and T.status=:status")
    List<ProjDocFileEntity> findProjDocFiles(@Param("folderId") Long folderId, @Param("status") Integer status);

    @Query("SELECT count(T) FROM ProjDocFileEntity T WHERE  T.folderId.id=:folderId and T.status=:status")
    int findCountProjDocFilesByFolder(@Param("folderId") Long folderId, @Param("status") Integer status);
    
    @Modifying
    @Query("UPDATE ProjDocFileEntity PFE SET PFE.updatedBy.userId=:userId,PFE.createdBy.userId=:userId WHERE PFE.id = :id")
    void updateCreatedUserAndUpdatedUserById( @Param("id") Long id, @Param("userId") Long userId );
    
    @Query("SELECT T FROM ProjDocFileEntity T WHERE T.id=:id and T.status=:status")
    ProjDocFileEntity findProjectDocFilesById(@Param("id") Long id, @Param("status") Integer status);
    
    @Query("SELECT T FROM ProjDocFileEntity T WHERE T.projectId.projectId=:projId and T.folderId.name=:folderName and T.status=1")
    List<ProjDocFileEntity> findProjDocsByProjectIdAndFolder( @Param("projId") Long projId, @Param("folderName") String folderName );
    
    @Query("SELECT T FROM ProjDocFileEntity T WHERE T.projectId.clientId.clientId=:crmId and T.folderId.name=:folderName and T.status=1")
    List<ProjDocFileEntity> findProjDocsByCrmIdAndFolder( @Param("crmId") Long crmId, @Param("folderName") String folderName );
}
