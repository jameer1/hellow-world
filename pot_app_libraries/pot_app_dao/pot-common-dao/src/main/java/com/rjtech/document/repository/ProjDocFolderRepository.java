package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.document.model.ProjDocFolderEntity;

@Repository
public interface ProjDocFolderRepository extends JpaRepository<ProjDocFolderEntity, Long> {
    
    @Modifying
    @Query("UPDATE ProjDocFolderEntity FDA SET FDA.status=:status  where FDA.id in :pojDocFolderIds ")
    void deactivateProjDocFolders(@Param("pojDocFolderIds") List<Long> pojDocFolderIds,
            @Param("status") Integer status);

    //@Query("SELECT T FROM ProjDocFolderEntity T WHERE T.status=:status and T.projId.projectId=:projectId and T.projDocFolderEntity IS NULL")
    @Query("SELECT T FROM ProjDocFolderEntity T WHERE T.status=:status and T.projDocFolderEntity IS NULL")    
    List<ProjDocFolderEntity> findProjDocFolders(@Param("status") Integer status);
    
    @Query("SELECT T FROM ProjDocFolderEntity T WHERE T.projId.projectId=:projId and T.projDocFolderEntity.id =:parentId and  T.status=:status")
    List<ProjDocFolderEntity> findProjDocSubFolders(@Param("projId") Long projId,@Param("parentId") Long parentId, @Param("status") Integer status);

    @Query("SELECT T FROM ProjDocFolderEntity T WHERE T.projId IS NULL and T.name= :name")
    ProjDocFolderEntity findByNameAndProjId(@Param("name") String name);
    
    @Query("SELECT T FROM ProjDocFolderEntity T WHERE T.id=:pdfId")
    ProjDocFolderEntity findByFoldId(@Param("pdfId") Long pdfId);    
}
