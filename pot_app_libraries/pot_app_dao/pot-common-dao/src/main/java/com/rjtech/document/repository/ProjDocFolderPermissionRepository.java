package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.document.model.ProjDocFolderPermissionEntity;

@Repository
public interface ProjDocFolderPermissionRepository extends JpaRepository<ProjDocFolderPermissionEntity, Long> {

    @Query("SELECT T FROM ProjDocFolderPermissionEntity T WHERE  T.folder.id=:folderId and T.status=:status")
    List<ProjDocFolderPermissionEntity> findProjDocFolderPermissions(@Param("folderId") Long folderId,
            @Param("status") Integer status);

    @Query("SELECT T FROM ProjDocFolderPermissionEntity T WHERE  T.userId.userId = :userId")
    List<ProjDocFolderPermissionEntity> findProjDocFolderPermissionsOfUser(@Param("userId") Long userId);
    
    @Query("SELECT T FROM ProjDocFolderPermissionEntity T WHERE  T.userId.userId = :userId and T.status=:status")
    List<ProjDocFolderPermissionEntity> findProjDocFolderPermissionsOfUseronly(@Param("userId") Long userId,@Param("status") Integer status);

    @Query("SELECT T FROM ProjDocFolderPermissionEntity T WHERE  T.folder.id=:folderId and T.userId.userId = :userId and T.status=:status")
    ProjDocFolderPermissionEntity findProjDocFolderPermissionsAndUser(@Param("folderId") Long folderId, @Param("userId") Long userId, @Param("status") Integer status);

    @Query("SELECT count(*) FROM ProjDocFolderPermissionEntity T WHERE  T.userId.userId = :userId and T.status=:status and T.folder.id=:id")
    Long findProjDocFolderPermissionsCountOfUserById(@Param("userId") Long userId,@Param("status") Integer status,@Param("id") Long id);
}
